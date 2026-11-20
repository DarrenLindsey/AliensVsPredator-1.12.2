package org.avp.refining;

import javax.annotation.Nullable;

import org.avp.power.MachineEnergy;
import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * A refining machine: an input slot, an output slot and a fluid slot (water buckets / acid vials),
 * a power buffer and (washer, vat) a tank. Hoppers and pipes put ore in from the top and sides and
 * take the product out of the bottom. A Hydro-Washer also draws water from water touching it.
 */
public class RefineryBlockEntity extends BlockEntity implements MenuProvider
{
    public static final int INPUT = 0, OUTPUT = 1, FLUID_SLOT = 2, TANK = 8000;

    public final RefineryKind kind;
    private final MachineEnergy energy;
    private int tank, progress;

    private final ItemStackHandler items = new ItemStackHandler(3)
    {
        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return switch (slot)
            {
                case INPUT -> !Refining.result(kind, stack).isEmpty();
                case FLUID_SLOT -> Refining.fluidValue(kind, stack) > 0;
                default -> false;
            };
        }

        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }
    };

    /** What hoppers and pipes see: insert ore / fluid items, extract only the product. */
    private final IItemHandler automation = new IItemHandler()
    {
        @Override
        public int getSlots()
        {
            return 3;
        }

        @Override
        public ItemStack getStackInSlot(int slot)
        {
            return items.getStackInSlot(slot);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
        {
            return slot == OUTPUT ? stack : items.insertItem(slot, stack, simulate);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return slot == OUTPUT ? items.extractItem(slot, amount, simulate) : ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot)
        {
            return 64;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return slot != OUTPUT && items.isItemValid(slot, stack);
        }
    };

    /** Pipes can fill a washer with water. */
    private final IFluidHandler fluids = new IFluidHandler()
    {
        @Override
        public int getTanks()
        {
            return 1;
        }

        @Override
        public FluidStack getFluidInTank(int t)
        {
            return kind.fluid == RefineryKind.Fluid.WATER && tank > 0 ? new FluidStack(Fluids.WATER, tank) : FluidStack.EMPTY;
        }

        @Override
        public int getTankCapacity(int t)
        {
            return TANK;
        }

        @Override
        public boolean isFluidValid(int t, FluidStack stack)
        {
            return kind.fluid == RefineryKind.Fluid.WATER && stack.getFluid().isSame(Fluids.WATER);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action)
        {
            if (!isFluidValid(0, resource))
            {
                return 0;
            }
            int take = Math.min(resource.getAmount(), TANK - tank);
            if (action.execute())
            {
                tank += take;
                setChanged();
            }
            return take;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action)
        {
            return FluidStack.EMPTY;
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action)
        {
            return FluidStack.EMPTY;
        }
    };

    /** Synced to the screen: progress, ticks needed, energy (per mille), FE/t, tank. */
    public final ContainerData data = new ContainerData()
    {
        @Override
        public int get(int i)
        {
            return switch (i)
            {
                case 0 -> progress;
                case 1 -> kind.ticks;
                case 2 -> (int) (1000L * energy.getEnergyStored() / Math.max(1, energy.getMaxEnergyStored()));
                case 3 -> kind.power;
                case 4 -> tank;
                default -> 0;
            };
        }

        @Override
        public void set(int i, int value)
        {
        }

        @Override
        public int getCount()
        {
            return 5;
        }
    };

    public RefineryBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.REFINERY.get(), pos, state);
        this.kind = state.getBlock() instanceof RefineryBlock block ? block.kind : RefineryKind.CRUSHER;
        this.energy = new MachineEnergy(Math.max(20_000, kind.power * kind.ticks * 4));
    }

    public IEnergyStorage energyFor()
    {
        return energy.view(true, false, Integer.MAX_VALUE);
    }

    public IItemHandler itemsFor(@Nullable Direction side)
    {
        return automation;
    }

    @Nullable
    public IFluidHandler fluidsFor()
    {
        return kind.fluid == RefineryKind.Fluid.WATER ? fluids : null;
    }

    public ItemStackHandler items()
    {
        return items;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, RefineryBlockEntity be)
    {
        // fluids in: buckets and vials, and (washer) water touching it
        ItemStack fluidItem = be.items.getStackInSlot(FLUID_SLOT);
        int value = Refining.fluidValue(be.kind, fluidItem);
        if (value > 0 && be.tank + value <= TANK)
        {
            be.tank += value;
            if (fluidItem.is(Items.WATER_BUCKET))
            {
                be.items.setStackInSlot(FLUID_SLOT, new ItemStack(Items.BUCKET));
            }
            else
            {
                fluidItem.shrink(1);
            }
            be.setChanged();
        }
        if (be.kind.fluid == RefineryKind.Fluid.WATER && be.tank < TANK && level.getGameTime() % 10 == 0)
        {
            for (Direction d : Direction.values())
            {
                if (level.getFluidState(pos.relative(d)).is(FluidTags.WATER) && level.getFluidState(pos.relative(d)).isSource())
                {
                    be.tank = Math.min(TANK, be.tank + 250);
                    break;
                }
            }
        }
        ItemStack input = be.items.getStackInSlot(INPUT);
        ItemStack result = Refining.result(be.kind, input);
        boolean fits = !result.isEmpty() && be.items.insertItem(OUTPUT, result.copy(), true).isEmpty();
        boolean wet = be.kind.fluid == RefineryKind.Fluid.NONE || be.tank >= be.kind.fluidPerJob;
        boolean working = fits && wet && be.energy.consume(be.kind.power);
        if (working)
        {
            if (++be.progress >= be.kind.ticks)
            {
                be.progress = 0;
                input.shrink(1);
                be.items.insertItem(OUTPUT, result, false);
                be.tank -= be.kind.fluidPerJob;
                level.playSound(null, pos, be.kind == RefineryKind.CRUSHER ? SoundEvents.GRINDSTONE_USE : SoundEvents.BREWING_STAND_BREW,
                        SoundSource.BLOCKS, 0.4F, 1.0F);
            }
            if (level instanceof ServerLevel server && level.getGameTime() % 8 == 0)
            {
                server.sendParticles(be.kind == RefineryKind.LEACH_VAT ? ParticleTypes.SNEEZE : be.kind == RefineryKind.FUSION
                        ? ParticleTypes.ELECTRIC_SPARK : ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.05, pos.getZ() + 0.5, 2, 0.2, 0.05, 0.2, 0.01);
            }
            be.setChanged();
        }
        else if (!fits && be.progress > 0)
        {
            be.progress = 0;
        }
        if (state.getValue(RefineryBlock.LIT) != working)
        {
            level.setBlock(pos, state.setValue(RefineryBlock.LIT, working), 3);
        }
    }

    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block.avp." + kind.id);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        return new RefineryMenu(id, inventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Items", items.serializeNBT(registries));
        tag.putInt("Energy", energy.getEnergyStored());
        tag.putInt("Tank", tank);
        tag.putInt("Progress", progress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        items.deserializeNBT(registries, tag.getCompound("Items"));
        energy.setStored(tag.getInt("Energy"));
        tank = tag.getInt("Tank");
        progress = tag.getInt("Progress");
    }
}
