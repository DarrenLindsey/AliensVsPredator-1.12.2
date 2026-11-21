package org.avp.world.hive;

import java.util.ArrayList;
import java.util.List;

import org.avp.entity.xenomorph.XenomorphEntity;
import org.avp.registry.ModBlocks;
import org.avp.registry.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/** Geometry and travel for the straight shafts of newly founded hives. */
public final class HiveShaft
{
    private static final int RADIUS = 2; // Five blocks across, including room for the four-wide Dracomorph.

    private HiveShaft()
    {
    }

    public static List<BlockPos> layer(BlockPos centre)
    {
        List<BlockPos> positions = new ArrayList<>(25);
        for (BlockPos pos : BlockPos.betweenClosed(centre.offset(-RADIUS, 0, -RADIUS), centre.offset(RADIUS, 0, RADIUS)))
        {
            if (!pos.equals(centre))
            {
                positions.add(pos.immutable());
            }
        }
        positions.add(centre); // The founding Drone loses the block under its feet last.
        return positions;
    }

    public static boolean canExcavate(ServerLevel level, BlockPos pos)
    {
        if (!level.isInWorldBounds(pos) || !level.isLoaded(pos))
        {
            return false;
        }
        BlockState state = level.getBlockState(pos);
        if (state.hasBlockEntity() || state.is(ModTags.XENO_UNBREAKABLE) || state.getDestroySpeed(level, pos) < 0
                || !state.getFluidState().isEmpty())
        {
            return false;
        }
        for (Direction direction : Direction.values())
        {
            BlockPos neighbour = pos.relative(direction);
            if (!level.isLoaded(neighbour) || !level.getFluidState(neighbour).isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    public static List<BlockPos> rim(BlockPos entrance)
    {
        int edge = RADIUS + 1;
        BlockPos ground = entrance.below();
        List<BlockPos> rim = new ArrayList<>(8 * edge);
        for (BlockPos pos : BlockPos.betweenClosed(ground.offset(-edge, 0, -edge), ground.offset(edge, 0, edge)))
        {
            if (Math.max(Math.abs(pos.getX() - ground.getX()), Math.abs(pos.getZ() - ground.getZ())) == edge)
            {
                rim.add(pos.immutable());
            }
        }
        return rim;
    }

    /** Replace exposed terrain around one column of the mouth. */
    public static void lineRimBlock(ServerLevel level, BlockPos ground)
    {
        if (!level.isLoaded(ground))
        {
            return;
        }
        for (int offset = 1; offset >= -1; offset--)
        {
            BlockPos surface = ground.above(offset);
            BlockState state = level.getBlockState(surface);
            if ((state.is(BlockTags.DIRT) || state.is(ModTags.RESIN_COVERABLE))
                    && canExcavate(level, surface) && level.getBlockState(surface.above()).isAir())
            {
                level.setBlock(surface, ModBlocks.HIVESTONE.get().defaultBlockState(), Block.UPDATE_ALL);
                return;
            }
        }
    }

    public static boolean isInside(XenomorphEntity xeno, BlockPos entrance)
    {
        double allowance = RADIUS + 0.5 - xeno.getBbWidth() / 2;
        return Math.abs(xeno.getX() - entrance.getX() - 0.5) <= allowance
                && Math.abs(xeno.getZ() - entrance.getZ() - 0.5) <= allowance;
    }

    public static boolean isPastLip(XenomorphEntity xeno, BlockPos entrance, Direction side)
    {
        double distance = (xeno.getX() - entrance.getX() - .5) * side.getStepX()
                + (xeno.getZ() - entrance.getZ() - .5) * side.getStepZ();
        return distance >= RADIUS + .5 + xeno.getBbWidth() / 2;
    }

    @javax.annotation.Nullable
    public static Direction exitSide(ServerLevel level, XenomorphEntity xeno, BlockPos mouth, Direction preferred)
    {
        Direction first = preferred.getAxis().isHorizontal() ? preferred : Direction.NORTH;
        for (Direction side : List.of(first, first.getClockWise(), first.getCounterClockWise(), first.getOpposite()))
        {
            BlockPos landing = mouth.relative(side, Mth.ceil(RADIUS + 1 + xeno.getBbWidth() / 2));
            Vec3 at = Vec3.atBottomCenterOf(landing);
            if (level.isLoaded(landing) && level.getBlockState(landing.below()).isSolid()
                    && level.noCollision(xeno, xeno.getBoundingBox().move(at.subtract(xeno.position()))))
            {
                return side;
            }
        }
        return null;
    }

    /** Guides a Xeno from its chamber to the shaft and up to the surface. */
    public static void ascend(XenomorphEntity xeno, Hive hive, Direction exit)
    {
        BlockPos entrance = hive.entrance();
        boolean atMouth = xeno.getY() >= entrance.getY() + .08;
        double x = entrance.getX() + 0.5;
        double z = entrance.getZ() + 0.5;
        if (!atMouth && !isInside(xeno, entrance))
        {
            if (xeno.getNavigation().isDone() && xeno.tickCount % 10 == 0)
            {
                xeno.navigateTo(x, hive.core().getY() - (hive.blueprint() == null ? 1 : 0), z, 1.0);
            }
            return;
        }
        xeno.getNavigation().stop();
        if (isPastLip(xeno, entrance, exit) && xeno.getY() >= entrance.getY() - 0.1)
        {
            return; // Landing is ordinary gravity once the entire body has crossed the rim.
        }
        double wall = atMouth ? RADIUS + 1.4 + xeno.getBbWidth() / 2 : RADIUS + 0.42 - xeno.getBbWidth() / 2;
        double dx = x + exit.getStepX() * wall - xeno.getX();
        double dz = z + exit.getStepZ() * wall - xeno.getZ();
        // Keep against a shaft wall on the way up. Step outward only above the rim.
        double rise = atMouth ? (xeno.getY() < entrance.getY() + .7 ? .17 : .02) : .32;
        xeno.setDeltaMovement(Mth.clamp(dx * .45, -.34, .34), rise, Mth.clamp(dz * .45, -.34, .34));
    }

    /** Guides a returning Xeno to the mouth, then lowers it and any passenger down the cleared column. */
    public static boolean descend(XenomorphEntity xeno, Hive hive)
    {
        BlockPos entrance = hive.entrance();
        if (!hive.hasVerticalShaft() || hive.state() != Hive.State.ACTIVE || xeno.getY() <= hive.core().getY()
                || xeno.getBbWidth() >= 2 * RADIUS + 1)
        {
            return false;
        }

        double dx = entrance.getX() + 0.5 - xeno.getX();
        double dz = entrance.getZ() + 0.5 - xeno.getZ();
        if (!isInside(xeno, entrance))
        {
            if (xeno.getY() < entrance.getY() - 1)
            {
                return false;
            }
            if (dx * dx + dz * dz > 25)
            {
                if (xeno.getNavigation().isDone() || xeno.tickCount % 20 == 0)
                {
                    BlockPos lip = entrance.relative(Direction.getNearest(-dx, 0, -dz), RADIUS + 1);
                    xeno.navigateTo(lip.getX() + 0.5, lip.getY(), lip.getZ() + 0.5, 1.0);
                }
                return true;
            }
            xeno.getNavigation().stop();
            if (xeno.onGround())
            {
                xeno.setDeltaMovement(Mth.clamp(dx * .25, -.42, .42), .17, Mth.clamp(dz * .25, -.42, .42));
            }
            else
            {
                xeno.getMoveControl().setWantedPosition(entrance.getX() + 0.5, entrance.getY(), entrance.getZ() + 0.5, 1.0);
            }
            return true;
        }

        xeno.getNavigation().stop();
        Vec3 descent = new Vec3(Mth.clamp(dx * 0.3, -.22, .22), -.26, Mth.clamp(dz * 0.3, -.22, .22));
        xeno.setDeltaMovement(descent);
        return true;
    }
}
