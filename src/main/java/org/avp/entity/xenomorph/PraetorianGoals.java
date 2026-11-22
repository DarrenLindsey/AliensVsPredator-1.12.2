package org.avp.entity.xenomorph;

import java.util.EnumSet;

import javax.annotation.Nullable;

import org.avp.config.AvpConfig;
import org.avp.world.hive.Hive;
import org.avp.world.hive.HiveShaft;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** A royal guard escorts its own Queen and only fights close to her. */
final class PraetorianGoals
{
    private static final int INTERCEPT_RADIUS = 12;
    private static final int GUARD_LEASH = 14;
    private static final int FOLLOW_START = 8;
    private static final int FOLLOW_STOP = 6;

    @Nullable
    private static AdultXenomorphEntity queen(AdultXenomorphEntity guard, @Nullable Hive hive)
    {
        if (hive != null && hive.state() == Hive.State.ACTIVE && hive.queen() != null
                && guard.level() instanceof ServerLevel level
                && level.getEntity(hive.queen()) instanceof AdultXenomorphEntity queen
                && queen.isAlive() && queen.isQueen() && hive.id().equals(queen.hiveId()))
        {
            return queen;
        }
        return null;
    }

    private static boolean attackedQueen(AdultXenomorphEntity queen, LivingEntity target)
    {
        return queen.getLastHurtByMob() == target && queen.tickCount - queen.getLastHurtByMobTimestamp() <= 100;
    }

    static boolean canFight(AdultXenomorphEntity guard, LivingEntity target, @Nullable Hive hive)
    {
        AdultXenomorphEntity queen = AvpConfig.server(AvpConfig.SERVER.hives) ? queen(guard, hive) : null;
        return queen != null && canIntercept(guard, queen, target);
    }

    private static boolean canIntercept(AdultXenomorphEntity guard, AdultXenomorphEntity queen, LivingEntity target)
    {
        return target.level() == queen.level() && guard.distanceToSqr(queen) <= GUARD_LEASH * GUARD_LEASH
                && queen.distanceToSqr(target) <= INTERCEPT_RADIUS * INTERCEPT_RADIUS
                && XenomorphEntity.isPrey(target) && !PreyCapture.blocksAttack(guard, target)
                && (target instanceof Player || target instanceof Enemy || target == guard.getLastHurtByMob()
                        || attackedQueen(queen, target) || target instanceof Mob mob && mob.getTarget() == queen);
    }

    static final class ProtectQueenGoal extends Goal
    {
        private final AdultXenomorphEntity guard;
        private int nextScan;
        private LivingEntity threat;

        ProtectQueenGoal(AdultXenomorphEntity guard)
        {
            this.guard = guard;
            setFlags(EnumSet.of(Flag.TARGET));
        }

        @Override
        public boolean canUse()
        {
            if (guard.getTarget() != null || guard.tickCount < nextScan || !AvpConfig.server(AvpConfig.SERVER.hives)
                    || !(guard.level() instanceof ServerLevel level))
            {
                return false;
            }
            nextScan = guard.tickCount + 16 + guard.getRandom().nextInt(12);
            var queen = queen(guard, guard.hive());
            if (queen == null || guard.distanceToSqr(queen) > GUARD_LEASH * GUARD_LEASH)
            {
                return false;
            }
            LivingEntity attacker = queen.getLastHurtByMob();
            if (attacker != null && attackedQueen(queen, attacker) && canIntercept(guard, queen, attacker))
            {
                threat = attacker;
                return true;
            }

            double nearest = Double.MAX_VALUE;
            for (LivingEntity candidate : level.getEntitiesOfClass(LivingEntity.class,
                    queen.getBoundingBox().inflate(INTERCEPT_RADIUS, 6, INTERCEPT_RADIUS),
                    target -> canIntercept(guard, queen, target)))
            {
                double distance = queen.distanceToSqr(candidate);
                if (distance < nearest && guard.getSensing().hasLineOfSight(candidate))
                {
                    threat = candidate;
                    nearest = distance;
                }
            }
            return threat != null;
        }

        @Override
        public void start()
        {
            guard.setTarget(threat);
            if (guard.getTarget() == threat)
            {
                guard.setState("PROTECTING QUEEN");
            }
        }

        @Override
        public boolean canContinueToUse()
        {
            return threat != null && guard.getTarget() == threat && threat.isAlive();
        }

        @Override
        public void stop()
        {
            if (guard.getTarget() == threat)
            {
                guard.setTarget(null);
            }
            threat = null;
        }
    }

    static final class EscortQueenGoal extends Goal
    {
        private final AdultXenomorphEntity guard;
        private int nextCheck;
        private boolean usingShaft;
        private Hive hive;
        private Vec3 lastRoute;

        EscortQueenGoal(AdultXenomorphEntity guard)
        {
            this.guard = guard;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            if (guard.getTarget() != null || guard.tickCount < nextCheck || !AvpConfig.server(AvpConfig.SERVER.hives))
            {
                return false;
            }
            nextCheck = guard.tickCount + 12 + guard.getRandom().nextInt(9);
            hive = guard.hive();
            return hive != null && hive.state() == Hive.State.ACTIVE
                    && guard.distanceToSqr(anchor()) > FOLLOW_START * FOLLOW_START;
        }

        @Override
        public void start()
        {
            guard.setState(queen(guard, hive) == null ? "GUARDING THE CORE" : "ESCORTING QUEEN");
            route();
        }

        @Override
        public boolean canContinueToUse()
        {
            return guard.getTarget() == null && guard.hive() == hive && hive.state() == Hive.State.ACTIVE
                    && guard.distanceToSqr(anchor()) > FOLLOW_STOP * FOLLOW_STOP;
        }

        @Override
        public boolean requiresUpdateEveryTick()
        {
            return true; // use the shared descent step while in the shaft
        }

        @Override
        public void tick()
        {
            if (usingShaft || guard.tickCount >= nextCheck)
            {
                route();
            }
        }

        private void route()
        {
            Vec3 destination = anchor();
            if (destination.y <= hive.core().getY() + 2 && HiveShaft.descend(guard, hive))
            {
                usingShaft = true;
                lastRoute = null;
                return;
            }
            usingShaft = false;
            nextCheck = guard.tickCount + 20 + guard.getRandom().nextInt(7);
            if (lastRoute == null || destination.distanceToSqr(lastRoute) >= 4 || guard.getNavigation().isDone())
            {
                guard.navigateTo(destination.x, destination.y, destination.z, 1.0);
                lastRoute = destination;
            }
        }

        private Vec3 anchor()
        {
            var queen = queen(guard, hive);
            BlockPos core = hive.core();
            return queen == null ? Vec3.atBottomCenterOf(core) : queen.position();
        }

        @Override
        public void stop()
        {
            usingShaft = false;
            lastRoute = null;
            guard.stopJobNavigation();
            nextCheck = guard.tickCount + 8;
        }
    }

    private PraetorianGoals() {}
}
