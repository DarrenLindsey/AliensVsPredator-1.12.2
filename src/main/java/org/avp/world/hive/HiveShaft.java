package org.avp.world.hive;

import java.util.ArrayList;
import java.util.List;

import org.avp.entity.xenomorph.XenomorphEntity;
import org.avp.registry.ModBlocks;
import org.avp.registry.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
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

    public static boolean canExcavate(ServerLevel level, List<BlockPos> layer)
    {
        return layer.stream().allMatch(pos -> canExcavate(level, pos));
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

    /** Replace exposed terrain just outside the opening. Leave obstacles and man-made blocks alone. */
    public static void lineMouth(ServerLevel level, BlockPos entrance)
    {
        int edge = RADIUS + 1;
        BlockPos ground = entrance.below();
        BlockState stone = ModBlocks.HIVESTONE.get().defaultBlockState();
        for (BlockPos pos : BlockPos.betweenClosed(ground.offset(-edge, 0, -edge), ground.offset(edge, 0, edge)))
        {
            if (Math.max(Math.abs(pos.getX() - ground.getX()), Math.abs(pos.getZ() - ground.getZ())) != edge || !level.isLoaded(pos))
            {
                continue;
            }
            for (int offset = 1; offset >= -1; offset--)
            {
                BlockPos surface = pos.above(offset);
                BlockState state = level.getBlockState(surface);
                if (state.is(ModTags.RESIN_COVERABLE) && canExcavate(level, surface) && level.getBlockState(surface.above()).isAir())
                {
                    level.setBlock(surface, stone, Block.UPDATE_ALL);
                    break;
                }
            }
        }
    }

    public static boolean isInside(XenomorphEntity xeno, BlockPos entrance)
    {
        double allowance = RADIUS + 0.5 - xeno.getBbWidth() / 2;
        return Math.abs(xeno.getX() - entrance.getX() - 0.5) <= allowance
                && Math.abs(xeno.getZ() - entrance.getZ() - 0.5) <= allowance;
    }

    /** Guides a Xeno from its chamber to the shaft and up to the surface. */
    public static void ascend(XenomorphEntity xeno, Hive hive, Direction exit)
    {
        BlockPos entrance = hive.entrance();
        boolean atMouth = xeno.getY() >= entrance.getY();
        double x = entrance.getX() + 0.5;
        double z = entrance.getZ() + 0.5;
        if (!atMouth && !isInside(xeno, entrance))
        {
            if (xeno.getNavigation().isDone() && xeno.tickCount % 10 == 0)
            {
                xeno.navigateTo(x, hive.core().getY() - 1, z, 1.0);
            }
            return;
        }
        xeno.getNavigation().stop();
        double wall = atMouth ? RADIUS + 1.75 : RADIUS + 0.45 - xeno.getBbWidth() / 2;
        double dx = x + exit.getStepX() * wall - xeno.getX();
        double dz = z + exit.getStepZ() * wall - xeno.getZ();
        // Keep against a shaft wall on the way up. Step outward only above the rim.
        double rise = xeno.getY() < entrance.getY() + 0.65 ? .32 : .02;
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
            xeno.getMoveControl().setWantedPosition(entrance.getX() + 0.5, entrance.getY(), entrance.getZ() + 0.5, 1.0);
            return true;
        }

        xeno.getNavigation().stop();
        Vec3 descent = new Vec3(Mth.clamp(dx * 0.3, -.22, .22), -.26, Mth.clamp(dz * 0.3, -.22, .22));
        xeno.setDeltaMovement(descent);
        return true;
    }
}
