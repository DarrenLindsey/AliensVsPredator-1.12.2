package org.avp.entity.xenomorph.lab;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.avp.entity.xenomorph.XenomorphEntity;
import org.avp.power.MachineEnergy;
import org.avp.registry.ModBlockEntities;
import org.avp.registry.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * The Gene Sequencer and the Incubation Tank. The sequencer turns a DNA sample, an empty phial and up to
 * three splice items into an embryo template; the tank grows a template (and royal jelly) into a living
 * Xenomorph on top of itself, bonded to whoever placed the tank.
 */
public class LabBlockEntity extends BlockEntity implements MenuProvider
{
    public static final int JELLY_PER_GROWTH = 4;
    public final LabKind kind;
    private final MachineEnergy energy = new MachineEnergy(400_000);
    @Nullable
    private UUID owner;
    private int progress;
    private String status = "";

    public final ItemStackHandler items;

    public final ContainerData data = new ContainerData()
    {
        @Override
        public int get(int i)
        {
            return switch (i)
            {
                case 0 -> progress;
                case 1 -> needed();
                case 2 -> (int) (1000L * energy.getEnergyStored() / energy.getMaxEnergyStored());
                default -> 0;
            };
        }

        @Override
        public void set(int i, int v)
        {
        }

        @Override
        public int getCount()
        {
            return 3;
        }
    };

    public LabBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.XENO_LAB.get(), pos, state);
        this.kind = state.getBlock() instanceof LabBlock b ? b.kind : LabKind.GENE_SEQUENCER;
        this.items = new ItemStackHandler(kind.slots)
        {
            @Override
            public boolean isItemValid(int slot, ItemStack stack)
            {
                return valid(slot, stack);
            }

            @Override
            protected void onContentsChanged(int slot)
            {
                setChanged();
            }
        };
    }

    boolean valid(int slot, ItemStack stack)
    {
        if (kind == LabKind.GENE_SEQUENCER)
        {
            return switch (slot)
            {
                case 0 -> stack.is(XenoLab.SAMPLE.get());
                case 1 -> stack.is(ModItems.PHIAL_EMPTY.get());
                case 2, 3, 4 -> XenoTrait.of(stack) != null;
                default -> false;
            };
        }
        return slot == 0 ? stack.is(XenoLab.TEMPLATE.get()) : stack.is(ModItems.ROYALJELLY.get());
    }

    public IEnergyStorage energyFor()
    {
        return energy.view(true, false, Integer.MAX_VALUE);
    }

    public void setOwner(UUID owner)
    {
        this.owner = owner;
        setChanged();
    }

    private int needed()
    {
        if (kind == LabKind.INCUBATION_TANK && XenoLab.isQueen(XenoLab.species(items.getStackInSlot(0))))
        {
            return kind.ticks * 5;
        }
        return kind.ticks;
    }

    public String status()
    {
        return status;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, LabBlockEntity be)
    {
        boolean working = be.kind == LabKind.GENE_SEQUENCER ? be.tickSequencer() : be.tickIncubator((ServerLevel) level);
        if (state.getValue(LabBlock.LIT) != working)
        {
            level.setBlock(pos, state.setValue(LabBlock.LIT, working), 3);
        }
    }

    // ------------------------------------------------------------------ sequencer

    private boolean tickSequencer()
    {
        ItemStack sample = items.getStackInSlot(0), phial = items.getStackInSlot(1), out = items.getStackInSlot(5);
        EntityType<?> species = XenoLab.species(sample);
        if (species == null || phial.isEmpty() || !out.isEmpty())
        {
            progress = 0;
            status = species == null ? "Insert a DNA sample" : phial.isEmpty() ? "Insert an empty phial" : "Take the template out";
            return false;
        }
        if (!energy.consume(kind.power))
        {
            status = "NO POWER";
            return false;
        }
        status = "Sequencing " + species.getDescription().getString();
        if (++progress < needed())
        {
            return true;
        }
        progress = 0;
        List<XenoTrait> traits = new ArrayList<>();
        for (int i = 2; i <= 4; i++)
        {
            XenoTrait t = XenoTrait.of(items.getStackInSlot(i));
            if (t != null && !traits.contains(t))
            {
                traits.add(t);
                items.extractItem(i, 1, false);
            }
        }
        int instability = Math.max(0, traits.size() - 1) - (traits.contains(XenoTrait.OBEDIENCE) ? 1 : 0) + (XenoLab.isQueen(species) ? 2 : 0);
        ItemStack template = new ItemStack(XenoLab.TEMPLATE.get());
        CompoundTag tag = new CompoundTag();
        tag.putString("Species", BuiltInRegistries.ENTITY_TYPE.getKey(species).toString());
        tag.putString("Traits", String.join(",", traits.stream().map(XenoTrait::id).toList()));
        tag.putInt("Instability", Math.max(0, instability));
        template.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        items.extractItem(0, 1, false);
        items.extractItem(1, 1, false);
        items.setStackInSlot(5, template);
        level.playSound(null, worldPosition, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 0.8F, 1.6F);
        return false;
    }

    // ------------------------------------------------------------------ incubation

    private boolean tickIncubator(ServerLevel level)
    {
        ItemStack template = items.getStackInSlot(0);
        EntityType<?> species = XenoLab.species(template);
        int jelly = XenoLab.isQueen(species) ? JELLY_PER_GROWTH * 4 : JELLY_PER_GROWTH;
        if (species == null)
        {
            progress = 0;
            status = "Insert an embryo template";
            return false;
        }
        if (items.getStackInSlot(1).getCount() < jelly)
        {
            status = "Needs " + jelly + " royal jelly";
            return false;
        }
        if (XenoLab.isQueen(species) && !(level.getBlockState(worldPosition.above()).getBlock() instanceof org.avp.entity.xenomorph.lab.QueenCellBlock))
        {
            status = "A Queen can only be grown into a Containment Cell (place one on top)";
            return false;
        }
        if (!energy.consume(kind.power))
        {
            status = "NO POWER";
            return false;
        }
        status = "Gestating " + species.getDescription().getString() + " (" + 100 * progress / needed() + "%)";
        if (level.getGameTime() % 10 == 0)
        {
            level.sendParticles(ParticleTypes.BUBBLE_POP, worldPosition.getX() + 0.5, worldPosition.getY() + 0.9, worldPosition.getZ() + 0.5, 3, 0.25, 0.2, 0.25, 0.01);
        }
        if (++progress < needed())
        {
            return true;
        }
        BlockPos at = worldPosition.above(XenoLab.isQueen(species) ? 2 : 1);
        if (!XenoLab.isQueen(species) && (!level.getBlockState(at).isAir() || !level.getBlockState(at.above()).isAir()))
        {
            status = "Clear the space above the tank";
            progress = needed() - 1;
            return false;
        }
        progress = 0;
        if (XenoLab.isQueen(species) && level.getBlockEntity(worldPosition.above()) instanceof QueenCellBlockEntity cell)
        {
            if (!cell.receiveQueen(species, owner))
            {
                status = "The Containment Cell is occupied";
                progress = needed() - 1;
                return false;
            }
        }
        else if (species.create(level) instanceof XenomorphEntity xeno)
        {
            xeno.moveTo(at.getX() + 0.5, at.getY(), at.getZ() + 0.5, level.random.nextFloat() * 360, 0);
            xeno.finalizeSpawn(level, level.getCurrentDifficultyAt(at), MobSpawnType.SPAWNER, null);
            UUID who = owner != null ? owner : UUID.randomUUID();
            XenoBond bond = xeno.bondTo(who);
            bond.traits.addAll(XenoLab.traits(template));
            bond.instability = XenoLab.data(template).getInt("Instability");
            bond.applyTraits();
            level.addFreshEntity(xeno);
            level.playSound(null, at, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1.2F, 0.6F);
            Player p = owner == null ? null : level.getPlayerByUUID(owner);
            if (p instanceof ServerPlayer sp)
            {
                sp.sendSystemMessage(Component.literal("[W-Y Genetics] Specimen viable: " + species.getDescription().getString()
                        + ". It has imprinted on you.").withStyle(net.minecraft.ChatFormatting.DARK_GREEN));
            }
        }
        items.extractItem(0, 1, false);
        items.extractItem(1, jelly, false);
        return false;
    }

    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block.avp." + kind.id);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
    {
        return new LabMenu(id, inv, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Items", items.serializeNBT(registries));
        tag.putInt("Energy", energy.getEnergyStored());
        tag.putInt("Progress", progress);
        if (owner != null)
        {
            tag.putUUID("Owner", owner);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        items.deserializeNBT(registries, tag.getCompound("Items"));
        energy.setStored(tag.getInt("Energy"));
        progress = tag.getInt("Progress");
        owner = tag.hasUUID("Owner") ? tag.getUUID("Owner") : null;
    }
}
