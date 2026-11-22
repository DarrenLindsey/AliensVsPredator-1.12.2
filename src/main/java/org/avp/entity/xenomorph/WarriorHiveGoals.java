package org.avp.entity.xenomorph;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.avp.config.AvpConfig;
import org.avp.world.hive.Hive;
import org.avp.world.hive.Room;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

/** Local defense and patrol for the Warrior caste. */
final class WarriorHiveGoals
{
    private static final int SIGHT_RANGE = 18;
    private static final int CHASE_RANGE = 24;

    static boolean canFight(AdultXenomorphEntity warrior, LivingEntity target, Hive hive)
    {
        if (hive == null || !AvpConfig.server(AvpConfig.SERVER.hives) || !XenomorphEntity.isPrey(target) || target.level() != warrior.level()
                || warrior.distanceToSqr(target) > CHASE_RANGE * CHASE_RANGE)
        {
            return false;
        }
        boolean aggressor = target instanceof Player || target instanceof Enemy || target == warrior.getLastHurtByMob()
                || target instanceof Mob mob && mob.getTarget() instanceof XenomorphEntity member && hive.id().equals(member.hiveId());
        return aggressor && (warrior.isQueen() ? inRoyalChamber(hive, target.blockPosition())
                : hive.containsPatrolArea(target.blockPosition())) && !PreyCapture.blocksAttack(warrior, target);
    }

    static boolean inRoyalChamber(Hive hive, BlockPos pos)
    {
        if (hive.state() != Hive.State.ACTIVE)
        {
            return false;
        }
        for (Room room : hive.rooms())
        {
            if (room.type() == Room.Type.QUEEN && room.contains(pos))
            {
                return true;
            }
        }
        return pos.distSqr(hive.core()) <= 16;
    }

    /** Scan locally at staggered intervals. The current target is checked separately as it moves. */
    static final class DefendHiveGoal extends Goal
    {
        private final AdultXenomorphEntity warrior;
        private int nextScan;
        private LivingEntity intruder;

        DefendHiveGoal(AdultXenomorphEntity warrior)
        {
            this.warrior = warrior;
            setFlags(EnumSet.of(Flag.TARGET));
        }

        @Override
        public boolean canUse()
        {
            if (warrior.isBonded() || warrior.getTarget() != null || warrior.tickCount < nextScan
                    || !(warrior.level() instanceof ServerLevel level))
            {
                return false;
            }
            nextScan = warrior.tickCount + 16 + warrior.getRandom().nextInt(13);
            Hive hive = warrior.hive();
            if (hive == null || hive.state() != Hive.State.ACTIVE)
            {
                return false;
            }

            double closest = SIGHT_RANGE * SIGHT_RANGE;
            for (LivingEntity candidate : level.getEntitiesOfClass(LivingEntity.class,
                    warrior.getBoundingBox().inflate(SIGHT_RANGE, 8, SIGHT_RANGE),
                    target -> canFight(warrior, target, hive)))
            {
                double distance = warrior.distanceToSqr(candidate);
                if (distance < closest && warrior.getSensing().hasLineOfSight(candidate))
                {
                    closest = distance;
                    intruder = candidate;
                }
            }
            return intruder != null;
        }

        @Override
        public void start()
        {
            warrior.setTarget(intruder);
            if (warrior.getTarget() == intruder)
            {
                warrior.setState("DEFENDING HIVE");
            }
        }

        @Override
        public boolean canContinueToUse()
        {
            return warrior.getTarget() == intruder && intruder != null && intruder.isAlive();
        }

        @Override
        public void stop()
        {
            if (warrior.getTarget() == intruder)
            {
                warrior.setTarget(null);
            }
            intruder = null;
        }
    }

    /** Walk between finished rooms and the foot of the shaft, reusing each route until it ends. */
    static final class PatrolHiveGoal extends Goal
    {
        private final AdultXenomorphEntity warrior;
        private int nextRouteTick;
        private int nextWaypoint;
        private int stopAt;
        private Path path;

        PatrolHiveGoal(AdultXenomorphEntity warrior)
        {
            this.warrior = warrior;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            if (warrior.isBonded() || warrior.getTarget() != null || warrior.tickCount < nextRouteTick
                    || !AvpConfig.server(AvpConfig.SERVER.hives) || !(warrior.level() instanceof ServerLevel level))
            {
                return false;
            }
            nextRouteTick = warrior.tickCount + 30 + warrior.getRandom().nextInt(16);
            Hive hive = warrior.hive();
            if (hive == null || hive.state() != Hive.State.ACTIVE)
            {
                return false;
            }
            // An approaching guard uses the shared shaft return goal first.
            if (hive.hasVerticalShaft() && warrior.getY() > hive.shaftFloor().getY() + 9)
            {
                return false;
            }

            List<BlockPos> waypoints = new ArrayList<>();
            waypoints.add(hive.core());
            if (hive.hasVerticalShaft())
            {
                waypoints.add(hive.shaftFloor());
            }
            for (Room room : hive.rooms())
            {
                if (room.done() && room.type().isChamber() && !room.centre().equals(hive.core()))
                {
                    waypoints.add(room.centre());
                }
            }
            // Small startup hives still get a route around the Core.
            if (waypoints.size() == 1)
            {
                for (Direction direction : Direction.Plane.HORIZONTAL)
                {
                    waypoints.add(hive.core().relative(direction, 3));
                }
            }
            for (int i = 0; i < waypoints.size(); i++)
            {
                BlockPos position = waypoints.get(Math.floorMod(nextWaypoint++ + warrior.getId(), waypoints.size()));
                BlockPos step = standAt(level, hive, position);
                if (step != null && warrior.blockPosition().distSqr(step) > 4)
                {
                    path = warrior.getNavigation().createPath(step, 0);
                    return path != null && path.canReach();
                }
            }
            return false;
        }

        private BlockPos standAt(ServerLevel level, Hive hive, BlockPos anchor)
        {
            for (int dy = 1; dy >= -1; dy--)
            {
                BlockPos floor = anchor.above(dy);
                if (hive.containsPatrolArea(floor) && level.isLoaded(floor) && level.isEmptyBlock(floor)
                        && level.getBlockState(floor.below()).isFaceSturdy(level, floor.below(), Direction.UP))
                {
                    return floor;
                }
            }
            return null;
        }

        @Override
        public void start()
        {
            warrior.getNavigation().moveTo(path, 0.9);
            warrior.setState("PATROLLING HIVE");
            stopAt = warrior.tickCount + 160;
        }

        @Override
        public boolean canContinueToUse()
        {
            return warrior.hiveId() != null && warrior.getTarget() == null && warrior.tickCount < stopAt
                    && !warrior.getNavigation().isDone();
        }

        @Override
        public void stop()
        {
            path = null;
            warrior.stopJobNavigation();
            nextRouteTick = Math.max(nextRouteTick, warrior.tickCount + 18);
        }
    }

    private WarriorHiveGoals() {}
}
