package org.avp.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

/** A block you can be "inside" (locker, medpod) by riding a SeatEntity placed in it. */
public interface SeatBlock
{
    /** Someone was just seated (e.g. the locker closes its door behind them). */
    default void onSeated(Level level, BlockPos pos, Entity passenger)
    {
    }

    /**
     * Someone just got out (e.g. open the door).
     * @param ticksInside how long they were seated
     */
    default void onSeatLeft(Level level, BlockPos pos, Entity passenger, int ticksInside)
    {
    }

    /** Where to put someone who gets out, or null to let Minecraft decide. */
    @Nullable
    default BlockPos exitPos(Level level, BlockPos pos)
    {
        return null;
    }
}
