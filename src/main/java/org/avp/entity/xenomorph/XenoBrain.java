package org.avp.entity.xenomorph;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import org.avp.block.StorageBlock;
import org.avp.block.entity.TurretBlockEntity;
import org.avp.config.AvpConfig;
import org.avp.entity.SeatEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Film-style Xenomorph behaviour ("smartXenomorphs" setting). Used by XenomorphEntity.addHunterGoals.
 *
 * - Senses: they SEE players in their line of sight and HEAR players who sprint, fight or walk
 *   nearby. Sneaking makes you much harder to notice. A player hiding in a locker can't be found.
 * - Investigate: a noise they can't pin down makes them go and look.
 * - Stalk: watched from a distance, they don't charge across the open - they slip out to the side
 *   and strike when you look away (or when you've stared too long). Packs come from different sides.
 * - Retreat: badly hurt or burning, they pull back into the dark; out of combat they heal.
 * - Learn: shot by a sentry turret, they remember it, warn the others and keep out of its range.
 * - Darkness: they wander towards dark places and may smash torches and lanterns.
 */
public final class XenoBrain
{
    private XenoBrain()
    {
    }

    // ------------------------------------------------------------------ perception

    /** Hidden in a locker (riding the locker's invisible seat)? */
    public static boolean isHidden(Player player)
    {
        return player.getVehicle() instanceof SeatEntity seat
                && player.level().getBlockState(seat.blockPosition()).getBlock() instanceof StorageBlock storage && storage.canHide();
    }

    /** Is the player looking at this Xenomorph (within ~30 degrees) with a clear view? */
    public static boolean isWatching(Player player, LivingEntity xeno)
    {
        Vec3 toXeno = xeno.getEyePosition().subtract(player.getEyePosition()).normalize();
        return player.getViewVector(1.0F).dot(toXeno) > 0.86 && player.hasLineOfSight(xeno);
    }

    /** How "loud" the player is right now: sprinting/fighting = full hearing range, walking = half, sneaking = a quarter. */
    private static double hearingRange(Player player)
    {
        double range = AvpConfig.server(AvpConfig.SERVER.xenoHearingRange);
        boolean fighting = player.tickCount - player.getLastHurtMobTimestamp() < 60 || player.hurtTime > 0;
        if (player.isSprinting() || fighting)
        {
            return range;
        }
        if (player.isShiftKeyDown())
        {
            return range * 0.25;
        }
        return player.walkDist - player.walkDistO > 0.01F ? range * 0.5 : range * 0.15;
    }

    private static double sightRange(Player player)
    {
        double range = AvpConfig.server(AvpConfig.SERVER.xenoSightRange);
        if (player.isInvisible())
        {
            return 3;
        }
        return player.isShiftKeyDown() ? range / 3 : range;
    }

    /** Darkness of a spot (lower = darker), for choosing where to lurk. */
    private static int lightAt(Level level, BlockPos pos)
    {
        return Math.max(level.getBrightness(LightLayer.BLOCK, pos), level.getBrightness(LightLayer.SKY, pos) - level.getSkyDarken());
    }

    /** Picks the darkest of a few random spots (null = none found). */
    @Nullable
    private static Vec3 darkSpot(XenomorphEntity xeno, @Nullable Vec3 awayFrom, int range)
    {
        Vec3 best = null;
        int bestLight = Integer.MAX_VALUE;
        for (int i = 0; i < 6; i++)
        {
            Vec3 spot = awayFrom != null ? DefaultRandomPos.getPosAway(xeno, range, 5, awayFrom) : DefaultRandomPos.getPos(xeno, range, 5);
            if (spot != null)
            {
                int light = lightAt(xeno.level(), BlockPos.containing(spot));
                if (light < bestLight)
                {
                    best = spot;
                    bestLight = light;
                }
            }
        }
        return best;
    }

    // ------------------------------------------------------------------ targeting

    /** Picks a player it can see or hear. Heard-but-not-seen players are investigated rather than attacked. */
    public static class SensePlayersGoal extends TargetGoal
    {
        private final XenomorphEntity xeno;
        private Player found;

        public SensePlayersGoal(XenomorphEntity xeno)
        {
            super(xeno, false, false);
            this.xeno = xeno;
            setFlags(EnumSet.of(Flag.TARGET));
        }

        @Override
        public boolean canUse()
        {
            if (xeno.getRandom().nextInt(5) != 0)
            {
                return false;
            }
            found = null;
            double range = Math.max(AvpConfig.server(AvpConfig.SERVER.xenoHearingRange), AvpConfig.server(AvpConfig.SERVER.xenoSightRange));
            List<Player> players = xeno.level().getEntitiesOfClass(Player.class, xeno.getBoundingBox().inflate(range),
                    p -> xeno.wantsToHunt(p) && !isHidden(p));
            players.sort(Comparator.comparingDouble(xeno::distanceToSqr));
            for (Player player : players)
            {
                double distance = xeno.distanceTo(player);
                boolean seen = distance <= sightRange(player) && xeno.getSensing().hasLineOfSight(player);
                boolean heard = distance <= hearingRange(player);
                if (seen || heard && distance < 6)
                {
                    found = player;
                    return true;
                }
                if (heard)
                {
                    xeno.investigate(player.position()); // something's there... go and look
                    xeno.sharePackInvestigation(player.position());
                }
            }
            return false;
        }

        @Override
        public void start()
        {
            mob.setTarget(found);
            super.start();
        }

        @Override
        public boolean canContinueToUse()
        {
            LivingEntity target = mob.getTarget();
            if (target instanceof Player player && isHidden(player))
            {
                xeno.investigate(player.position()); // lost them: search around the lockers
                return false;
            }
            return super.canContinueToUse();
        }
    }

    // ------------------------------------------------------------------ attacking

    /**
     * Melee, but with patience: from a distance, while the target watches it, the Xenomorph moves
     * to a flanking spot instead of charging - then strikes when the target looks away, when it's
     * close, or when it runs out of patience. Packs pick different sides.
     */
    public static class StalkAndStrikeGoal extends MeleeAttackGoal
    {
        private final XenomorphEntity xeno;
        private int patience;
        private Vec3 flank;
        private int repath;

        public StalkAndStrikeGoal(XenomorphEntity xeno, double speed)
        {
            super(xeno, speed, false);
            this.xeno = xeno;
        }

        @Override
        public void start()
        {
            super.start();
            int pack = xeno.packmates().size() + 1;
            // alone it's patient; in a pack of 3+ it loses patience fast and rushes in
            patience = pack >= 3 ? 15 + xeno.getRandom().nextInt(25) : 60 + xeno.getRandom().nextInt(80);
            flank = null;
        }

        @Override
        public void tick()
        {
            LivingEntity target = xeno.getTarget();
            if (target instanceof Player player && patience > 0 && xeno.distanceTo(player) > 7 && isWatching(player, xeno))
            {
                patience--;
                xeno.setState("STALKING");
                if (flank == null || --repath <= 0)
                {
                    flank = flankPoint(player);
                    repath = 40;
                }
                if (flank != null)
                {
                    xeno.getNavigation().moveTo(flank.x, flank.y, flank.z, 1.1);
                }
                xeno.getLookControl().setLookAt(player);
                return;
            }
            xeno.setState("HUNTING");
            super.tick();
        }

        /** A spot off to one side of the target, so the pack comes from different directions. */
        @Nullable
        private Vec3 flankPoint(Player target)
        {
            Vec3 fromTarget = xeno.position().subtract(target.position()).multiply(1, 0, 1).normalize();
            List<XenomorphEntity> pack = xeno.level().getEntitiesOfClass(XenomorphEntity.class, xeno.getBoundingBox().inflate(24),
                    x -> x.getTarget() == target);
            int index = Math.max(0, pack.indexOf(xeno));
            float side = (index % 2 == 0 ? 1 : -1) * (60F + 25F * (index / 2));
            Vec3 dir = fromTarget.yRot(side * Mth.DEG_TO_RAD);
            Vec3 point = target.position().add(dir.scale(8));
            BlockPos pos = BlockPos.containing(point);
            return xeno.getNavigation().createPath(pos, 1) != null ? Vec3.atBottomCenterOf(pos) : null;
        }
    }

    // ------------------------------------------------------------------ investigating

    /** Goes to the last place it heard something and looks around. */
    public static class InvestigateGoal extends Goal
    {
        private final XenomorphEntity xeno;
        private int lookAround;

        public InvestigateGoal(XenomorphEntity xeno)
        {
            this.xeno = xeno;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse()
        {
            return xeno.getTarget() == null && xeno.investigatePos() != null;
        }

        @Override
        public void start()
        {
            Vec3 at = xeno.investigatePos();
            xeno.getNavigation().moveTo(at.x, at.y, at.z, 0.9);
            xeno.setState("INVESTIGATING");
            lookAround = 60 + xeno.getRandom().nextInt(60);
        }

        @Override
        public boolean canContinueToUse()
        {
            return xeno.getTarget() == null && xeno.investigatePos() != null && lookAround > 0;
        }

        @Override
        public void tick()
        {
            if (xeno.getNavigation().isDone())
            {
                lookAround--;
                if (lookAround % 20 == 0)
                {
                    xeno.getLookControl().setLookAt(xeno.getX() + xeno.getRandom().nextGaussian() * 4, xeno.getEyeY(),
                            xeno.getZ() + xeno.getRandom().nextGaussian() * 4);
                }
            }
        }

        @Override
        public void stop()
        {
            xeno.investigate(null);
        }
    }

    // ------------------------------------------------------------------ retreating, danger

    /** Badly hurt or burning: break off and pull back somewhere dark. */
    public static class RetreatGoal extends Goal
    {
        private final XenomorphEntity xeno;
        private int time;

        public RetreatGoal(XenomorphEntity xeno)
        {
            this.xeno = xeno;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            if (!AvpConfig.server(AvpConfig.SERVER.xenosRetreatWhenHurt) || xeno.retreatCooldown() > 0)
            {
                return false;
            }
            boolean hurt = xeno.getHealth() < xeno.getMaxHealth() * 0.35F;
            return (hurt || xeno.isOnFire()) && xeno.getTarget() != null;
        }

        @Override
        public void start()
        {
            LivingEntity threat = xeno.getTarget();
            Vec3 spot = darkSpot(xeno, threat == null ? null : threat.position(), 16);
            if (spot != null)
            {
                xeno.getNavigation().moveTo(spot.x, spot.y, spot.z, 1.35);
            }
            time = 200;
            xeno.setState("RETREATING");
            xeno.setTarget(null);
            xeno.setRetreatCooldown(600);
        }

        @Override
        public boolean canContinueToUse()
        {
            return --time > 0 && !xeno.getNavigation().isDone();
        }

        @Override
        public void stop()
        {
            xeno.setState("LURKING");
        }
    }

    /** Keeps out of the range of a sentry turret it has learned about. */
    public static class AvoidTurretGoal extends Goal
    {
        private final XenomorphEntity xeno;

        public AvoidTurretGoal(XenomorphEntity xeno)
        {
            this.xeno = xeno;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            BlockPos turret = xeno.knownTurret();
            if (turret == null)
            {
                return false;
            }
            double range = xeno.level().getBlockEntity(turret) instanceof TurretBlockEntity t ? t.range() + 2 : 26;
            return xeno.distanceToSqr(Vec3.atCenterOf(turret)) < range * range;
        }

        @Override
        public void start()
        {
            xeno.setState("AVOIDING TURRET");
            Vec3 spot = darkSpot(xeno, Vec3.atCenterOf(xeno.knownTurret()), 20);
            if (spot != null)
            {
                xeno.getNavigation().moveTo(spot.x, spot.y, spot.z, 1.3);
            }
            if (xeno.getTarget() != null && xeno.getTarget().distanceToSqr(Vec3.atCenterOf(xeno.knownTurret())) < 24 * 24)
            {
                xeno.setTarget(null); // not worth walking into the guns
            }
        }

        @Override
        public boolean canContinueToUse()
        {
            return !xeno.getNavigation().isDone();
        }

        @Override
        public void stop()
        {
            xeno.setState("LURKING");
        }
    }

    /** Finds the turret that shot it (within 40 blocks), remembers it, and warns the pack. */
    public static void learnTurret(XenomorphEntity xeno)
    {
        if (!AvpConfig.server(AvpConfig.SERVER.xenosLearnTurrets))
        {
            return;
        }
        BlockPos center = xeno.blockPosition();
        BlockPos found = null;
        double best = Double.MAX_VALUE;
        int chunks = 3;
        for (int cx = -chunks; cx <= chunks; cx++)
        {
            for (int cz = -chunks; cz <= chunks; cz++)
            {
                var chunk = xeno.level().getChunkSource().getChunkNow((center.getX() >> 4) + cx, (center.getZ() >> 4) + cz);
                if (chunk == null)
                {
                    continue;
                }
                for (var be : chunk.getBlockEntities().values())
                {
                    if (be instanceof TurretBlockEntity turret)
                    {
                        double d = turret.getBlockPos().distSqr(center);
                        if (d < best && d < 40 * 40)
                        {
                            best = d;
                            found = turret.getBlockPos();
                        }
                    }
                }
            }
        }
        if (found == null)
        {
            return;
        }
        for (XenomorphEntity other : xeno.level().getEntitiesOfClass(XenomorphEntity.class, new AABB(center).inflate(16)))
        {
            other.rememberTurret(found);
        }
    }

    // ------------------------------------------------------------------ acid breach (Alien Resurrection)

    private static final java.util.Map<Level, java.util.Map<BlockPos, Float>> MELT = new java.util.WeakHashMap<>();

    /** Acid blood dripping on the meltable blocks around a bleeding Xenomorph. */
    public static void bleedOnto(XenomorphEntity xeno, float damage)
    {
        if (!AvpConfig.server(AvpConfig.SERVER.xenosAcidBreach) || !(xeno.level() instanceof net.minecraft.server.level.ServerLevel level))
        {
            return;
        }
        BlockPos origin = xeno.blockPosition();
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-1, 0, -1), origin.offset(1, 1, 1)))
        {
            if (level.getBlockState(pos).is(org.avp.registry.ModTags.ACID_MELTABLE))
            {
                melt(level, pos.immutable(), damage, xeno);
            }
        }
    }

    private static void melt(net.minecraft.server.level.ServerLevel level, BlockPos pos, float amount, XenomorphEntity source)
    {
        java.util.Map<BlockPos, Float> progress = MELT.computeIfAbsent(level, l -> new java.util.HashMap<>());
        float total = progress.getOrDefault(pos, 0F) + amount;
        float needed = AvpConfig.server(AvpConfig.SERVER.acidMeltAmount);
        level.sendParticles(net.minecraft.core.particles.ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8, 0.3, 0.4, 0.3, 0.01);
        level.playSound(null, pos, net.minecraft.sounds.SoundEvents.FIRE_EXTINGUISH, net.minecraft.sounds.SoundSource.HOSTILE, 0.8F, 0.6F);
        if (total >= needed)
        {
            progress.remove(pos);
            level.destroyBlockProgress(pos.hashCode(), pos, -1);
            level.destroyBlock(pos, false, source);
            level.sendParticles(net.minecraft.core.particles.ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 20, 0.3, 0.5, 0.3, 0.02);
            level.playSound(null, pos, net.minecraft.sounds.SoundEvents.LAVA_EXTINGUISH, net.minecraft.sounds.SoundSource.HOSTILE, 1.0F, 0.8F);
        }
        else
        {
            progress.put(pos, total);
            // the cracks you see when mining, growing as it melts
            level.destroyBlockProgress(pos.hashCode(), pos, Math.min(9, (int) (total / needed * 10)));
        }
    }

    /**
     * Stuck behind a meltable door on the way to its target, with a packmate close by:
     * it wounds the packmate every few seconds so its acid blood eats through the door.
     */
    public static class AcidBreachGoal extends Goal
    {
        private final XenomorphEntity xeno;
        private BlockPos door;
        private XenomorphEntity donor;
        private int timer;

        public AcidBreachGoal(XenomorphEntity xeno)
        {
            this.xeno = xeno;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse()
        {
            if (!AvpConfig.server(AvpConfig.SERVER.xenosAcidBreach) || xeno.getRandom().nextInt(10) != 0)
            {
                return false;
            }
            // its prey, a noise, or where a hive job is taking it (so it clears whatever blocks the hive's work)
            Vec3 goal = xeno.goalPos();
            if (goal == null)
            {
                return false;
            }
            net.minecraft.world.level.pathfinder.Path path = xeno.getNavigation().createPath(BlockPos.containing(goal), 1);
            if (path != null && path.canReach())
            {
                return false; // there's a way round
            }
            door = meltableTowards(goal);
            donor = door == null ? null : packmate();
            return door != null && donor != null;
        }

        /** The first meltable block between it and where it wants to go (within 3 blocks). */
        @Nullable
        private BlockPos meltableTowards(Vec3 goal)
        {
            Vec3 from = xeno.position().add(0, 0.5, 0);
            Vec3 dir = goal.subtract(from).normalize();
            for (double d = 0.5; d <= 3; d += 0.5)
            {
                for (double up = 0; up <= 1; up++)
                {
                    BlockPos pos = BlockPos.containing(from.add(dir.scale(d)).add(0, up, 0));
                    if (xeno.level().getBlockState(pos).is(org.avp.registry.ModTags.ACID_MELTABLE))
                    {
                        return pos;
                    }
                }
            }
            return null;
        }

        /** Another Xenomorph close by with blood to spare (only the one with the lowest id does the wounding). */
        @Nullable
        private XenomorphEntity packmate()
        {
            List<XenomorphEntity> near = xeno.level().getEntitiesOfClass(XenomorphEntity.class, xeno.getBoundingBox().inflate(4),
                    x -> x != xeno && x.isAlive() && x.getHealth() > 6 && !(x instanceof OvamorphEntity));
            if (near.isEmpty() || near.stream().anyMatch(x -> x.getId() < xeno.getId() && x.getTarget() == xeno.getTarget()))
            {
                return null;
            }
            near.sort(Comparator.comparingDouble(xeno::distanceToSqr));
            return near.get(0);
        }

        @Override
        public void start()
        {
            timer = 0;
            xeno.setState("ACID BREACH");
        }

        @Override
        public boolean canContinueToUse()
        {
            return door != null && donor != null && donor.isAlive() && donor.getHealth() > 4
                    && xeno.level().getBlockState(door).is(org.avp.registry.ModTags.ACID_MELTABLE);
        }

        @Override
        public void tick()
        {
            // bring the packmate to the door, and wound it there
            xeno.getLookControl().setLookAt(donor);
            donor.getNavigation().moveTo(door.getX() + 0.5, door.getY(), door.getZ() + 0.5, 1.0);
            if (xeno.distanceToSqr(donor) > 4)
            {
                xeno.getNavigation().moveTo(donor, 1.0);
                return;
            }
            xeno.getNavigation().stop();
            if (++timer >= AvpConfig.server(AvpConfig.SERVER.acidBreachInterval))
            {
                timer = 0;
                xeno.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
                // a plain wound (no attacker), so the donor doesn't turn on it
                donor.hurt(xeno.damageSources().generic(), 3.0F);
                if (xeno.level() instanceof net.minecraft.server.level.ServerLevel level && donor.distanceToSqr(Vec3.atCenterOf(door)) < 6)
                {
                    melt(level, door, 3.0F, donor);
                }
            }
        }
    }

    // ------------------------------------------------------------------ packs

    /** Idle pack members keep near their leader. */
    public static class FollowLeaderGoal extends Goal
    {
        private final XenomorphEntity xeno;
        private XenomorphEntity leader;

        public FollowLeaderGoal(XenomorphEntity xeno)
        {
            this.xeno = xeno;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            if (xeno.getTarget() != null || xeno.investigatePos() != null || xeno.isPackLeader() || xeno.tickCount % 20 >= 2)
            {
                return false;
            }
            leader = xeno.level() instanceof net.minecraft.server.level.ServerLevel server
                    && server.getEntity(xeno.packId()) instanceof XenomorphEntity l && l.isAlive() ? l : null;
            return leader != null && xeno.distanceToSqr(leader) > 10 * 10;
        }

        @Override
        public void start()
        {
            xeno.setState("FOLLOWING LEADER");
            xeno.navigateTo(leader, 1.0);
        }

        @Override
        public boolean canContinueToUse()
        {
            return leader != null && leader.isAlive() && xeno.getTarget() == null && xeno.distanceToSqr(leader) > 5 * 5
                    && !xeno.getNavigation().isDone();
        }

        @Override
        public void stop()
        {
            xeno.setState("LURKING");
        }
    }

    // ------------------------------------------------------------------ digging

    /**
     * No way through? Tear through the blocks in the way (up to "Dig Max Hardness"; never
     * avp:xeno_unbreakable; glass is smashed and iron doors are melted instead).
     */
    public static class DigThroughGoal extends Goal
    {
        private final XenomorphEntity xeno;
        private BlockPos block;
        private float progress;
        private int lastStage = -1;

        public DigThroughGoal(XenomorphEntity xeno)
        {
            this.xeno = xeno;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        private static boolean canDig(XenomorphEntity xeno, BlockPos pos)
        {
            BlockState state = xeno.level().getBlockState(pos);
            if (state.isAir() || state.is(org.avp.registry.ModTags.XENO_UNBREAKABLE) || state.is(org.avp.registry.ModTags.ACID_MELTABLE)
                    || state.is(org.avp.registry.ModTags.XENO_BREAKABLE) || !state.getFluidState().isEmpty()
                    || state.getCollisionShape(xeno.level(), pos).isEmpty())
            {
                return false;
            }
            float hardness = state.getDestroySpeed(xeno.level(), pos);
            return hardness >= 0 && hardness <= AvpConfig.server(AvpConfig.SERVER.xenoDigMaxHardness);
        }

        @Override
        public boolean canUse()
        {
            if (!AvpConfig.server(AvpConfig.SERVER.xenosDigThroughBlocks) || xeno.getRandom().nextInt(10) != 0
                    || !net.neoforged.neoforge.event.EventHooks.canEntityGrief((net.minecraft.server.level.ServerLevel) xeno.level(), xeno))
            {
                return false;
            }
            // its prey, a noise, or where a hive job is taking it (so it clears whatever blocks the hive's work)
            Vec3 goal = xeno.goalPos();
            if (goal == null || xeno.distanceToSqr(goal) < 2)
            {
                return false;
            }
            if (xeno.getTarget() == null && xeno.investigatePos() == null && !xeno.isStuck())
            {
                return false; // a hive job: climb or walk round if it can; only dig once it's really stuck
            }
            net.minecraft.world.level.pathfinder.Path path = xeno.getNavigation().createPath(BlockPos.containing(goal), 1);
            if (path != null && path.canReach())
            {
                return false; // there's a way round
            }
            // the first diggable block straight ahead towards the goal (feet level, then head level)
            Vec3 from = xeno.position().add(0, 0.2, 0);
            Vec3 dir = goal.subtract(from).normalize();
            int height = Math.max(1, Mth.ceil(xeno.getBbHeight()));
            for (double d = 0.6; d <= 2.0; d += 0.35)
            {
                for (int up = 0; up < height; up++)
                {
                    BlockPos pos = BlockPos.containing(from.add(dir.scale(d)).add(0, up, 0));
                    if (canDig(xeno, pos))
                    {
                        block = pos.immutable();
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void start()
        {
            progress = 0;
            lastStage = -1;
            xeno.setState("DIGGING");
            xeno.getNavigation().stop();
        }

        @Override
        public boolean canContinueToUse()
        {
            return block != null && canDig(xeno, block) && xeno.distanceToSqr(Vec3.atCenterOf(block)) < 3.5 * 3.5;
        }

        @Override
        public boolean requiresUpdateEveryTick()
        {
            return true; // digging progress is per tick
        }

        @Override
        public void tick()
        {
            if (block == null)
            {
                return; // just broke through: the goal stops on the next check
            }
            xeno.getLookControl().setLookAt(Vec3.atCenterOf(block));
            BlockState state = xeno.level().getBlockState(block);
            float hardness = Math.max(0.2F, state.getDestroySpeed(xeno.level(), block));
            // ~ a stone pickaxe: stone (1.5) takes about 1.5 seconds at speed 1.0
            progress += (float) (AvpConfig.server(AvpConfig.SERVER.xenoDigSpeed) / (hardness * 20F));
            if (xeno.tickCount % 6 == 0)
            {
                xeno.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
                xeno.level().playSound(null, block, state.getSoundType().getHitSound(), net.minecraft.sounds.SoundSource.HOSTILE, 0.6F, 0.8F);
            }
            int stage = Math.min(9, (int) (progress * 10));
            if (stage != lastStage)
            {
                xeno.level().destroyBlockProgress(xeno.getId(), block, stage);
                lastStage = stage;
            }
            if (progress >= 1F)
            {
                xeno.level().destroyBlockProgress(xeno.getId(), block, -1);
                xeno.level().destroyBlock(block, true, xeno);
                block = null;
            }
        }

        @Override
        public void stop()
        {
            if (block != null)
            {
                xeno.level().destroyBlockProgress(xeno.getId(), block, -1);
            }
            block = null;
            xeno.setState("LURKING");
        }
    }

    // ------------------------------------------------------------------ darkness

    /** Wanders, but prefers dark places. */
    public static class LurkGoal extends WaterAvoidingRandomStrollGoal
    {
        private final XenomorphEntity xeno;

        public LurkGoal(XenomorphEntity xeno, double speed)
        {
            super(xeno, speed);
            this.xeno = xeno;
        }

        @Nullable
        @Override
        protected Vec3 getPosition()
        {
            Vec3 dark = darkSpot(xeno, null, 12);
            return dark != null ? dark : super.getPosition();
        }
    }

    /** Now and then, smashes a torch or lantern nearby (setting). */
    public static class BreakLightsGoal extends Goal
    {
        private final XenomorphEntity xeno;
        private BlockPos light;

        public BreakLightsGoal(XenomorphEntity xeno)
        {
            this.xeno = xeno;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        private static boolean isLight(BlockState state)
        {
            return state.getBlock() instanceof TorchBlock || state.getBlock() instanceof LanternBlock || state.is(Blocks.JACK_O_LANTERN)
                    || AvpConfig.server(AvpConfig.SERVER.xenosCutPower) && state.getBlock() instanceof org.avp.block.PowerlineBlock; // "they cut the power"
        }

        @Override
        public boolean canUse()
        {
            if (!AvpConfig.server(AvpConfig.SERVER.xenosBreakLights) || xeno.getTarget() != null || xeno.getRandom().nextInt(80) != 0
                    || !net.neoforged.neoforge.event.EventHooks.canEntityGrief((net.minecraft.server.level.ServerLevel) xeno.level(), xeno))
            {
                return false;
            }
            BlockPos origin = xeno.blockPosition();
            for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-6, -2, -6), origin.offset(6, 3, 6)))
            {
                if (isLight(xeno.level().getBlockState(pos)))
                {
                    light = pos.immutable();
                    return true;
                }
            }
            return false;
        }

        @Override
        public void start()
        {
            xeno.getNavigation().moveTo(light.getX() + 0.5, light.getY(), light.getZ() + 0.5, 1.0);
        }

        @Override
        public boolean canContinueToUse()
        {
            return light != null && isLight(xeno.level().getBlockState(light)) && !xeno.getNavigation().isDone();
        }

        @Override
        public void tick()
        {
            if (light != null && xeno.distanceToSqr(Vec3.atCenterOf(light)) < 4.5)
            {
                xeno.level().destroyBlock(light, true, xeno);
                xeno.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
                light = null;
            }
        }
    }
}
