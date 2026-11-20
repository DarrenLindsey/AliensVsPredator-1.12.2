package org.avp.pneumatic;

import java.util.ArrayList;
import java.util.List;

import org.avp.config.AvpConfig;
import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * A PneuPost station: 9 outgoing slots, 9 incoming slots, a name, and an auto-send address. Hoppers,
 * pipes and Joes put things in the outgoing side and take from the incoming side.
 */
public class PneumaticStationBlockEntity extends BlockEntity implements MenuProvider
{
    public final ItemStackHandler outbox = new ItemStackHandler(9)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }
    };
    public final ItemStackHandler inbox = new ItemStackHandler(9)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }
    };
    private String name = "STATION";
    private String autoTarget = "";
    private boolean wasPowered;
    private long lastSend;

    /** Automation: put into the outbox, take from the inbox. */
    public final IItemHandler automation = new IItemHandler()
    {
        @Override
        public int getSlots()
        {
            return 18;
        }

        @Override
        public ItemStack getStackInSlot(int slot)
        {
            return slot < 9 ? outbox.getStackInSlot(slot) : inbox.getStackInSlot(slot - 9);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
        {
            return slot < 9 ? outbox.insertItem(slot, stack, simulate) : stack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return slot >= 9 ? inbox.extractItem(slot - 9, amount, simulate) : ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot)
        {
            return 64;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return slot < 9;
        }
    };

    public PneumaticStationBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.PNEUMATIC_STATION.get(), pos, state);
    }

    public String name()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = clean(name, "STATION");
        setChanged();
    }

    public String autoTarget()
    {
        return autoTarget;
    }

    public void setAutoTarget(String target)
    {
        this.autoTarget = clean(target, "");
        setChanged();
    }

    private static String clean(String s, String fallback)
    {
        s = s.trim().toUpperCase(java.util.Locale.ROOT);
        if (s.length() > 20)
        {
            s = s.substring(0, 20);
        }
        return s.isEmpty() ? fallback : s;
    }

    public ItemStack receive(ItemStack stack)
    {
        return ItemHandlerHelper.insertItemStacked(inbox, stack, false);
    }

    /** Sends the outbox to a station by name. Returns a message (starting with ! on failure). */
    public String send(String target)
    {
        if (level == null)
        {
            return "!";
        }
        boolean any = false;
        for (int i = 0; i < 9; i++)
        {
            any |= !outbox.getStackInSlot(i).isEmpty();
        }
        if (!any)
        {
            return "!Nothing to send.";
        }
        TubeNetwork.Survey survey = TubeNetwork.survey(level, worldPosition);
        BlockPos dest = survey.stations().get(target);
        if (dest == null || dest.equals(worldPosition))
        {
            return "!No station called " + target + " on this line.";
        }
        if (survey.pressurised() == 0)
        {
            return "!No pressure: connect a powered compressor.";
        }
        List<BlockPos> route = TubeNetwork.route(level, worldPosition, dest);
        if (route == null)
        {
            return "!No route to " + target + ".";
        }
        List<ItemStack> cargo = new ArrayList<>();
        for (int i = 0; i < 9; i++)
        {
            ItemStack s = outbox.getStackInSlot(i);
            if (!s.isEmpty())
            {
                cargo.add(s.copy());
                outbox.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
        double speed = AvpConfig.server(AvpConfig.SERVER.capsuleSpeed) * Math.min(4, survey.pressurised());
        CapsuleEntity.launch(level, worldPosition, route, cargo, speed);
        lastSend = level.getGameTime();
        return "Canister away to " + target + " (" + route.size() + " m).";
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, PneumaticStationBlockEntity be)
    {
        boolean powered = level.hasNeighborSignal(pos);
        boolean pulse = powered && !be.wasPowered;
        be.wasPowered = powered;
        if (!be.autoTarget.isEmpty() && (pulse || level.getGameTime() - be.lastSend > 40 && level.getGameTime() % 20 == 0 && !powered))
        {
            if (pulse || be.autoSendReady())
            {
                be.send(be.autoTarget);
            }
        }
    }

    /** Auto-send waits for a full slot or a few seconds' quiet. */
    private boolean autoSendReady()
    {
        for (int i = 0; i < 9; i++)
        {
            ItemStack s = outbox.getStackInSlot(i);
            if (!s.isEmpty() && s.getCount() >= s.getMaxStackSize())
            {
                return true;
            }
        }
        return level != null && level.getGameTime() % 100 == 0;
    }

    @Override
    public Component getDisplayName()
    {
        return Component.literal("PneuPost: " + name);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        return new PneumaticStationMenu(id, inventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Out", outbox.serializeNBT(registries));
        tag.put("In", inbox.serializeNBT(registries));
        tag.putString("Name", name);
        tag.putString("Auto", autoTarget);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        outbox.deserializeNBT(registries, tag.getCompound("Out"));
        inbox.deserializeNBT(registries, tag.getCompound("In"));
        name = tag.contains("Name") ? tag.getString("Name") : "STATION";
        autoTarget = tag.getString("Auto");
    }
}
