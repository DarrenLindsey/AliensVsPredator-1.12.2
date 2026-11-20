package org.avp.block.conveyor.spline;

import org.avp.power.PowerNetwork;
import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/** A Weyland-Yutani cable pylon: its spans join the power networks at both ends (see PowerNetwork). */
public class CablePylonBlockEntity extends SplineAnchorBlockEntity
{
    public CablePylonBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.CABLE_PYLON.get(), pos, state);
    }

    @Override
    protected void onLinked()
    {
        if (level != null)
        {
            PowerNetwork.invalidate(level);
        }
    }

    @Override
    protected void onUnlinked()
    {
        if (level != null)
        {
            PowerNetwork.invalidate(level);
        }
    }
}
