package org.avp.block.conveyor.spline;

import org.avp.config.AvpConfig;
import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

/**
 * A Seegson hose anchor. The anchor a hose leaves from pumps: it draws fluid from the block behind it
 * (a tank, a machine, or a water source - endless) and sends it down the hose. The anchor a hose ends
 * at fills the block behind it.
 */
public class HoseAnchorBlockEntity extends SplineAnchorBlockEntity
{
    public final FluidTank tank = new FluidTank(4000)
    {
        @Override
        protected void onContentsChanged()
        {
            setChanged();
        }
    };
    private FluidStack shown = FluidStack.EMPTY;

    public HoseAnchorBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.HOSE_ANCHOR.get(), pos, state);
    }

    /** The fluid to tint the hose with (client). */
    public FluidStack shown()
    {
        return shown;
    }

    private Direction back()
    {
        return getBlockState().getValue(SplineAnchorBlock.FACING).getOpposite();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, HoseAnchorBlockEntity be)
    {
        if (level.isClientSide)
        {
            return;
        }
        int rate = AvpConfig.server(AvpConfig.SERVER.hoseRate);
        BlockPos behind = pos.relative(be.back());
        if (be.target != null)
        {
            // pump in
            if (level.getFluidState(behind).is(FluidTags.WATER) && level.getFluidState(behind).isSource())
            {
                be.tank.fill(new FluidStack(Fluids.WATER, rate), IFluidHandler.FluidAction.EXECUTE);
            }
            else
            {
                IFluidHandler from = level.getCapability(Capabilities.FluidHandler.BLOCK, behind, be.back().getOpposite());
                if (from != null)
                {
                    FluidStack can = from.drain(rate, IFluidHandler.FluidAction.SIMULATE);
                    if (!can.isEmpty())
                    {
                        int took = be.tank.fill(can, IFluidHandler.FluidAction.EXECUTE);
                        from.drain(took, IFluidHandler.FluidAction.EXECUTE);
                    }
                }
            }
            // push down the hose
            if (!be.tank.isEmpty() && level.isLoaded(be.target) && level.getBlockEntity(be.target) instanceof HoseAnchorBlockEntity end)
            {
                FluidStack send = be.tank.drain(rate, IFluidHandler.FluidAction.SIMULATE);
                int took = end.tank.fill(send, IFluidHandler.FluidAction.EXECUTE);
                be.tank.drain(took, IFluidHandler.FluidAction.EXECUTE);
            }
        }
        else if (!be.tank.isEmpty())
        {
            // an end: fill what's behind it
            IFluidHandler into = level.getCapability(Capabilities.FluidHandler.BLOCK, behind, be.back().getOpposite());
            if (into != null)
            {
                int took = into.fill(be.tank.getFluid().copy(), IFluidHandler.FluidAction.EXECUTE);
                be.tank.drain(took, IFluidHandler.FluidAction.EXECUTE);
            }
        }
        // what the hose shows
        FluidStack now = be.tank.isEmpty() ? FluidStack.EMPTY : be.tank.getFluid().copyWithAmount(1);
        if (level.getGameTime() % 20 == 0 && !FluidStack.isSameFluidSameComponents(now, be.shown))
        {
            be.shown = now;
            be.sync();
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Tank", tank.writeToNBT(registries, new CompoundTag()));
        if (!shown.isEmpty())
        {
            tag.put("Shown", shown.save(registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        tank.readFromNBT(registries, tag.getCompound("Tank"));
        shown = tag.contains("Shown") ? FluidStack.parseOptional(registries, tag.getCompound("Shown")) : FluidStack.EMPTY;
    }
}
