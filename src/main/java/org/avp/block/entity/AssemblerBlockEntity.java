package org.avp.block.entity;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.avp.config.AvpConfig;
import org.avp.crafting.AssemblyRecipe;
import org.avp.power.MachineEnergy;
import org.avp.registry.ModBlockEntities;
import org.avp.registry.ModRecipes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * The Assembler: builds schematics for players (from their inventory) and for machines.
 *
 * Automation (Applied Energistics 2, Create, hoppers, pipes):
 *  - Anything can insert schematic materials into its input buffer from any side.
 *  - When the buffer holds everything for a schematic, it builds it (using FE as usual).
 *  - Results wait in the output buffer, where anything can take them out - or, with
 *    "Auto-Output" on, are pushed into the neighbouring inventories (an AE2 Pattern
 *    Provider takes them straight back into the ME network).
 *  - Several schematics share the same materials (the reflective shapes), so you can
 *    "lock" the Assembler to one schematic; then only that one is built.
 *
 * For AE2: put a Pattern Provider against the Assembler, encode a Processing Pattern
 * with the schematic's materials as inputs and its result as output, and turn Auto-Output on.
 */
public class AssemblerBlockEntity extends BlockEntity
{
    public static final int CAPACITY = 20_000;
    public static final int INPUT_SLOTS = 18;
    public static final int OUTPUT_SLOTS = 9;

    private final MachineEnergy energy = new MachineEnergy(CAPACITY);
    private ItemStack lastMade = ItemStack.EMPTY;
    private int syncedEnergy = -1;
    private int syncedBuffers = -1;

    private final ItemStackHandler input = new ItemStackHandler(INPUT_SLOTS)
    {
        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return isMaterial(stack);
        }

        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
            idle = 0;
        }
    };
    private final ItemStackHandler output = new ItemStackHandler(OUTPUT_SLOTS)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }
    };

    @Nullable
    private ResourceLocation locked;
    private boolean autoOutput;
    /** Ticks since the buffer last changed, so a half-delivered pattern isn't built too early. */
    private int idle;
    private String status = "";

    public AssemblerBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.ASSEMBLER.get(), pos, state);
    }

    public IEnergyStorage energyFor()
    {
        return energy.view(true, false, Integer.MAX_VALUE);
    }

    public MachineEnergy getEnergy()
    {
        return energy;
    }

    public ItemStack lastMade()
    {
        return lastMade;
    }

    @Nullable
    public ResourceLocation locked()
    {
        return locked;
    }

    public boolean autoOutput()
    {
        return autoOutput;
    }

    public String status()
    {
        return status;
    }

    public int bufferedInput()
    {
        return count(input);
    }

    public int bufferedOutput()
    {
        return count(output);
    }

    private static int count(IItemHandler handler)
    {
        int total = 0;
        for (int i = 0; i < handler.getSlots(); i++)
        {
            total += handler.getStackInSlot(i).getCount();
        }
        return total;
    }

    private List<RecipeHolder<AssemblyRecipe>> recipes()
    {
        return level == null ? List.of() : level.getRecipeManager().getAllRecipesFor(ModRecipes.ASSEMBLY.get());
    }

    /** Only things some schematic uses (the locked one, if locked) are let into the buffer. */
    private boolean isMaterial(ItemStack stack)
    {
        for (RecipeHolder<AssemblyRecipe> holder : recipes())
        {
            if ((locked == null || holder.id().equals(locked)) && holder.value().uses(stack))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * The automation side: inserting goes into the input buffer, extracting comes from the
     * output buffer. Same on every side.
     */
    public IItemHandler automation()
    {
        return new IItemHandler()
        {
            @Override
            public int getSlots()
            {
                return INPUT_SLOTS + OUTPUT_SLOTS;
            }

            @Override
            public ItemStack getStackInSlot(int slot)
            {
                return slot < INPUT_SLOTS ? input.getStackInSlot(slot) : output.getStackInSlot(slot - INPUT_SLOTS);
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
            {
                return slot < INPUT_SLOTS ? input.insertItem(slot, stack, simulate) : stack;
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate)
            {
                return slot < INPUT_SLOTS ? ItemStack.EMPTY : output.extractItem(slot - INPUT_SLOTS, amount, simulate);
            }

            @Override
            public int getSlotLimit(int slot)
            {
                return 64;
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack)
            {
                return slot < INPUT_SLOTS && input.isItemValid(slot, stack);
            }
        };
    }

    // ------------------------------------------------------------------ ticking

    public static void serverTick(Level level, BlockPos pos, BlockState state, AssemblerBlockEntity be)
    {
        be.idle++;
        if (level.getGameTime() % 10 == 0)
        {
            be.automate(level, pos);
            if (be.autoOutput)
            {
                be.pushOutput(level, pos);
            }
        }
        int buffered = be.bufferedInput() * 1000 + be.bufferedOutput();
        if (level.getGameTime() % 10 == 0 && (be.syncedEnergy != be.energy.getEnergyStored() || be.syncedBuffers != buffered))
        {
            be.syncedBuffers = buffered;
            be.setChanged();
            be.sync();
        }
    }

    /** Builds from the buffer, if it holds a complete set of materials. */
    private void automate(Level level, BlockPos pos)
    {
        if (bufferedInput() == 0 || idle < 5)
        {
            setStatus("");
            return;
        }
        RecipeHolder<AssemblyRecipe> chosen = null;
        int matches = 0;
        for (RecipeHolder<AssemblyRecipe> holder : recipes())
        {
            if (locked != null && !holder.id().equals(locked))
            {
                continue;
            }
            AssemblyRecipe recipe = holder.value();
            if (recipe.canAssemble(input) && (locked != null || recipe.usesEverything(input)))
            {
                chosen = holder;
                matches++;
            }
        }
        if (chosen == null)
        {
            setStatus("waiting");
            return;
        }
        if (matches > 1)
        {
            setStatus("ambiguous"); // e.g. the reflective shapes all use the same materials
            return;
        }
        AssemblyRecipe recipe = chosen.value();
        if (!ItemHandlerHelper.insertItemStacked(output, recipe.result().copy(), true).isEmpty())
        {
            setStatus("output_full");
            return;
        }
        if (!payFor(level, pos))
        {
            setStatus("no_power");
            return;
        }
        recipe.consume(input);
        ItemHandlerHelper.insertItemStacked(output, recipe.result().copy(), false);
        lastMade = recipe.result().copyWithCount(1);
        level.playSound(null, pos, SoundEvents.SMITHING_TABLE_USE, SoundSource.BLOCKS, 0.4F, 1.4F);
        setStatus("");
        sync();
    }

    /** Pushes finished items into neighbouring inventories (AE2 pattern providers, chests, Create...). */
    private void pushOutput(Level level, BlockPos pos)
    {
        for (int slot = 0; slot < OUTPUT_SLOTS; slot++)
        {
            ItemStack stack = output.getStackInSlot(slot);
            if (stack.isEmpty())
            {
                continue;
            }
            for (Direction dir : Direction.values())
            {
                IItemHandler target = level.getCapability(Capabilities.ItemHandler.BLOCK, pos.relative(dir), dir.getOpposite());
                if (target == null || level.getBlockEntity(pos.relative(dir)) instanceof net.minecraft.world.level.block.entity.HopperBlockEntity)
                {
                    continue; // hoppers usually feed the Assembler; sending results back up them would loop
                }
                ItemStack left = ItemHandlerHelper.insertItemStacked(target, output.extractItem(slot, 64, true), false);
                output.extractItem(slot, output.getStackInSlot(slot).getCount() - left.getCount(), false);
                if (output.getStackInSlot(slot).isEmpty())
                {
                    break;
                }
            }
        }
    }

    private boolean payFor(Level level, BlockPos pos)
    {
        int cost = AvpConfig.server(AvpConfig.SERVER.assemblerEnergyPerItem);
        if (cost <= 0 || !AvpConfig.server(AvpConfig.SERVER.machinesNeedPower)
                || AvpConfig.server(AvpConfig.SERVER.redstonePowersMachines) && level.hasNeighborSignal(pos))
        {
            return true;
        }
        return energy.consume(cost);
    }

    private void setStatus(String status)
    {
        if (!status.equals(this.status))
        {
            this.status = status;
            sync();
        }
    }

    // ------------------------------------------------------------------ player actions

    /**
     * Builds the schematic up to count times from the player's inventory.
     * Stops when materials or power run out.
     */
    public void assemble(Player player, RecipeHolder<AssemblyRecipe> holder, int count)
    {
        AssemblyRecipe recipe = holder.value();
        int made = 0;
        Component problem = null;
        while (made < count)
        {
            if (!recipe.canAssemble(player.getInventory()))
            {
                problem = made == 0 ? Component.translatable("message.avp.assembler.missing") : null;
                break;
            }
            if (!payFor(level, worldPosition))
            {
                problem = Component.translatable("message.avp.assembler.no_power", AvpConfig.server(AvpConfig.SERVER.assemblerEnergyPerItem));
                break;
            }
            recipe.consume(player.getInventory());
            player.getInventory().placeItemBackInInventory(recipe.result().copy());
            made++;
        }
        if (made > 0)
        {
            lastMade = recipe.result().copyWithCount(1);
            level.playSound(null, worldPosition, SoundEvents.SMITHING_TABLE_USE, SoundSource.BLOCKS, 0.6F, 1.3F);
            player.displayClientMessage(Component.translatable("message.avp.assembler.made",
                    made * recipe.result().getCount(), recipe.result().getHoverName()), true);
            setChanged();
            sync();
        }
        if (problem != null)
        {
            player.displayClientMessage(problem, true);
        }
    }

    /** Lock to one schematic for automation (or unlock with null). */
    public void lock(@Nullable ResourceLocation recipe)
    {
        locked = recipe != null && locked != null && locked.equals(recipe) ? null : recipe;
        idle = 0;
        setChanged();
        sync();
    }

    public void toggleAutoOutput()
    {
        autoOutput = !autoOutput;
        setChanged();
        sync();
    }

    /** Gives the player everything waiting in both buffers. */
    public void emptyBuffers(Player player)
    {
        for (ItemStackHandler handler : new ItemStackHandler[] { input, output })
        {
            for (int i = 0; i < handler.getSlots(); i++)
            {
                player.getInventory().placeItemBackInInventory(handler.extractItem(i, 64, false));
            }
        }
        sync();
    }

    public void dropContents(Level level, BlockPos pos)
    {
        for (ItemStackHandler handler : new ItemStackHandler[] { input, output })
        {
            for (int i = 0; i < handler.getSlots(); i++)
            {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), handler.getStackInSlot(i));
            }
        }
    }

    // ------------------------------------------------------------------ saving and syncing

    private void sync()
    {
        if (level != null && !level.isClientSide)
        {
            syncedEnergy = energy.getEnergyStored();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        writeShared(tag, registries);
        tag.put("Input", input.serializeNBT(registries));
        tag.put("Output", output.serializeNBT(registries));
    }

    private void writeShared(CompoundTag tag, HolderLookup.Provider registries)
    {
        tag.putInt("Energy", energy.getEnergyStored());
        if (!lastMade.isEmpty())
        {
            tag.put("LastMade", lastMade.save(registries));
        }
        if (locked != null)
        {
            tag.putString("Locked", locked.toString());
        }
        tag.putBoolean("AutoOutput", autoOutput);
        tag.putString("Status", status);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        energy.setStored(tag.getInt("Energy"));
        lastMade = tag.contains("LastMade") ? ItemStack.parseOptional(registries, tag.getCompound("LastMade")) : ItemStack.EMPTY;
        locked = tag.contains("Locked") ? ResourceLocation.tryParse(tag.getString("Locked")) : null;
        autoOutput = tag.getBoolean("AutoOutput");
        status = tag.getString("Status");
        if (tag.contains("Input"))
        {
            input.deserializeNBT(registries, tag.getCompound("Input"));
        }
        if (tag.contains("Output"))
        {
            output.deserializeNBT(registries, tag.getCompound("Output"));
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries); // small: a few dozen stacks at most
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /** The schematic it's locked to, if any (for the screen). */
    public Optional<RecipeHolder<AssemblyRecipe>> lockedRecipe()
    {
        return locked == null ? Optional.empty() : recipes().stream().filter(h -> h.id().equals(locked)).findFirst();
    }
}
