package org.avp.command;

import org.avp.world.hive.Hive;
import org.avp.world.hive.HiveData;
import org.avp.world.hive.Room;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;

/** Color-coded floor plans for the nearest hive whenever Xenomorph debug labels are enabled. */
final class HiveLayoutDebug
{
    private static final int RANGE = 64;
    private static final int SHAFT = 0x3377ff;
    private static final int CORE = 0xff2222;
    private static final int PASSAGE = 0xffffff;

    static void drawNearby(ServerPlayer player)
    {
        BlockPos at = player.blockPosition();
        Hive closest = null;
        double distance = RANGE * RANGE;
        for (Hive hive : HiveData.get(player.serverLevel()).all())
        {
            double candidate = Math.min(hive.core().distSqr(at), hive.entrance().distSqr(at));
            if (hive.hasVerticalShaft())
            {
                BlockPos entrance = hive.entrance();
                BlockPos bottom = hive.digPos() == null ? blueprintFloor(hive) : hive.digPos();
                int y = Mth.clamp(at.getY(), bottom.getY(), entrance.getY());
                candidate = Math.min(candidate, new BlockPos(entrance.getX(), y, entrance.getZ()).distSqr(at));
            }
            if (candidate <= distance)
            {
                closest = hive;
                distance = candidate;
            }
        }
        if (closest != null)
        {
            draw(player, closest, at);
        }
    }

    private static void draw(ServerPlayer player, Hive hive, BlockPos viewer)
    {
        BlockPos entrance = hive.entrance();
        BlockPos floor = blueprintFloor(hive);
        boolean atSurface = hive.hasVerticalShaft() && Math.abs(viewer.getY() - entrance.getY()) <= 8
                && entrance.getY() - floor.getY() > 8;
        if (hive.hasVerticalShaft())
        {
            drawShaft(player, hive, viewer, floor);
        }
        if (hive.blueprint() != null)
        {
            if (atSurface || Math.abs(viewer.getY() - floor.getY()) <= 16)
            {
                HiveBlueprintCommands.draw(player, atSurface ? entrance : floor, hive.blueprint().tiles());
            }
            return;
        }

        int surfaceY = atSurface ? entrance.getY() : Integer.MIN_VALUE;
        if (hive.state() != Hive.State.FOUNDING)
        {
            HiveBlueprintCommands.dust(player, onPlane(hive.core(), surfaceY), CORE, 2.5F);
        }
        for (Room room : hive.rooms())
        {
            int color = switch (room.type())
            {
                case QUEEN -> 0xbb55dd;
                case HATCHERY -> 0x33cc66;
                case LARDER -> 0xb87838;
                case MIDDEN -> 0xb38962;
                case GUARD_POST -> 0xffbb44;
                case CAVE -> 0x66cccc;
                case CRAWLWAY -> PASSAGE;
            };
            BlockPos centre = onPlane(room.centre(), surfaceY);
            BlockPos from = onPlane(room.from(), surfaceY);
            for (int i = 1; i < 8 && !from.equals(centre); i++)
            {
                float t = i / 8F;
                BlockPos segment = new BlockPos(Mth.lerpInt(t, from.getX(), centre.getX()),
                        Mth.lerpInt(t, from.getY(), centre.getY()), Mth.lerpInt(t, from.getZ(), centre.getZ()));
                HiveBlueprintCommands.dust(player, segment, PASSAGE, 1.25F);
            }
            for (int i = 0; i < 16 && room.radius() > 0; i++)
            {
                float angle = i * Mth.TWO_PI / 16;
                HiveBlueprintCommands.dust(player, centre.offset(Math.round(room.radius() * Mth.cos(angle)), 0,
                        Math.round(room.radius() * Mth.sin(angle))), color, 1.25F);
            }
            HiveBlueprintCommands.dust(player, centre, color, 2F);
        }
    }

    static BlockPos blueprintFloor(Hive hive)
    {
        BlockPos core = hive.core();
        BlockPos entrance = hive.entrance();
        if (hive.blueprint() == null || hive.state() == Hive.State.FOUNDING)
        {
            return new BlockPos(entrance.getX(), core.getY(), entrance.getZ());
        }
        return core.subtract(hive.blueprint().core(BlockPos.ZERO));
    }

    private static BlockPos onPlane(BlockPos pos, int y)
    {
        return y == Integer.MIN_VALUE ? pos : new BlockPos(pos.getX(), y, pos.getZ());
    }

    private static void drawShaft(ServerPlayer player, Hive hive, BlockPos viewer, BlockPos floor)
    {
        BlockPos entrance = hive.entrance();
        BlockPos bottom = hive.digPos() == null ? floor : hive.digPos();
        if (Math.abs(viewer.getY() - entrance.getY()) <= 12)
        {
            HiveBlueprintCommands.dust(player, entrance, SHAFT, 2.5F);
        }
        int high = Math.min(entrance.getY(), viewer.getY() + 12);
        int low = Math.max(bottom.getY(), viewer.getY() - 12);
        for (int y = high; y >= low; y -= 4)
        {
            BlockPos centre = new BlockPos(entrance.getX(), y, entrance.getZ());
            for (int dx = -2; dx <= 2; dx += 4)
            {
                HiveBlueprintCommands.dust(player, centre.offset(dx, 0, -2), SHAFT, 1.4F);
                HiveBlueprintCommands.dust(player, centre.offset(dx, 0, 2), SHAFT, 1.4F);
            }
        }
    }

    private HiveLayoutDebug() {}
}
