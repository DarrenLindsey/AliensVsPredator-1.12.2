package org.avp.entity.xenomorph;

import java.util.EnumSet;

import javax.annotation.Nullable;

import org.avp.block.NaturalResinBlock;
import org.avp.config.AvpConfig;
import org.avp.registry.ModAttachments;
import org.avp.registry.ModTags;
import org.avp.world.hive.Hive;
import org.avp.world.hive.HiveData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Hive behaviour (1.12.2 EntityDrone.tickHiveBuildingAI + XenomorphHive, rebuilt):
 * - members drift back to their hive when there's nothing to hunt;
 * - a Drone with no hive in range digs a staircase down to the deepslate and founds one there;
 * - Drones coat the hive in resin, outwards from the core;
 * - Drones carry subdued hosts home and cocoon them on the hive walls.
 */
public final class HiveGoals
{
    private static boolean enabled()
    {
        return AvpConfig.server(AvpConfig.SERVER.hives);
    }

    /** Members drift home when idle; the Queen stays at the core. */
    public static class ReturnToHiveGoal extends Goal
    {
        private final XenomorphEntity xeno;
        @Nullable
        private BlockPos home;

        public ReturnToHiveGoal(XenomorphEntity xeno)
        {
            this.xeno = xeno;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            if (!enabled() || xeno.getTarget() != null || xeno.tickCount % 40 >= 2 || !(xeno.level() instanceof ServerLevel level))
            {
                return false;
            }
            Hive hive = xeno.hive();
            if (hive == null || hive.state() != Hive.State.ACTIVE)
            {
                return false;
            }
            boolean heir = xeno.getUUID().equals(hive.heir());
            int range = xeno.isQueen() || heir ? 4 : hive.buildRadius(level.getGameTime(), HiveData.maxRadius()) + 8;
            home = hive.core();
            return xeno.blockPosition().distSqr(home) > range * range;
        }

        @Override
        public boolean canContinueToUse()
        {
            return xeno.getTarget() == null && !xeno.getNavigation().isDone();
        }

        @Override
        public void start()
        {
            xeno.getNavigation().moveTo(home.getX() + 0.5, home.getY(), home.getZ() + 0.5, 0.9);
            xeno.setState("GOING HOME");
        }
    }

    /**
     * No hive within range: a Drone founds one. It claims the spot, then digs a staircase (3 high,
     * so its kin can follow and climb back out) down to the deepslate, and hollows out the first
     * chamber there.
     */
    public static class BurrowGoal extends Goal
    {
        private final DroneEntity drone;
        @Nullable
        private Hive hive;
        private int digTicks;
        private int stuck;

        public BurrowGoal(DroneEntity drone)
        {
            this.drone = drone;
            setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        @Override
        public boolean canUse()
        {
            if (!enabled() || !(drone.level() instanceof ServerLevel level))
            {
                return false;
            }
            boolean forced = drone.takeForceFound();
            if (!forced && (drone.getTarget() != null || drone.tickCount % 100 >= 2))
            {
                return false;
            }
            HiveData data = HiveData.get(level);
            Hive mine = drone.hive();
            if (mine != null)
            {
                // carry on digging the hive's tunnel (if the founder died, another Drone takes over)
                if (mine.state() == Hive.State.FOUNDING && mine.digPos() != null)
                {
                    hive = mine;
                    return true;
                }
                return false;
            }
            if (!forced && (data.nearest(drone.blockPosition(), HiveData.spacing()).isPresent() || !drone.onGround()))
            {
                return false;
            }
            // claim it: the hive goes at the first deepslate under here
            BlockPos start = drone.blockPosition();
            hive = data.create(new BlockPos(start.getX(), deepslateLevel(level, start), start.getZ()), start, Hive.State.FOUNDING, level.getGameTime());
            hive.setDig(start, drone.getDirection());
            drone.setHiveId(hive.id());
            data.changed();
            return true;
        }

        /** Height of the first deepslate below, or ~24 blocks down if there's none (other dimensions). */
        private static int deepslateLevel(ServerLevel level, BlockPos from)
        {
            BlockPos.MutableBlockPos pos = from.mutable();
            for (int y = from.getY() - 4; y > level.getMinBuildHeight() + 6; y--)
            {
                pos.setY(y);
                BlockState state = level.getBlockState(pos);
                if (state.is(Blocks.DEEPSLATE))
                {
                    return y - 3;
                }
            }
            return Math.max(level.getMinBuildHeight() + 8, from.getY() - 24);
        }

        @Override
        public boolean canContinueToUse()
        {
            return hive != null && hive.state() == Hive.State.FOUNDING && drone.getTarget() == null && stuck < 400;
        }

        @Override
        public void start()
        {
            drone.setState("BURROWING");
            digTicks = 0;
            stuck = 0;
        }

        @Override
        public boolean requiresUpdateEveryTick()
        {
            return true;
        }

        @Override
        public void tick()
        {
            ServerLevel level = (ServerLevel) drone.level();
            BlockPos at = hive == null || hive.state() != Hive.State.FOUNDING ? null : hive.digPos();
            if (at == null)
            {
                return; // finished (or forgotten): the goal stops on the next check
            }
            Direction dir = hive.digDir();
            if (at.getY() <= hive.core().getY())
            {
                carveChamber(level, at);
                return;
            }
            BlockPos next = at.relative(dir).below();
            // keep to the tunnel
            drone.getMoveControl().setWantedPosition(at.getX() + 0.5, at.getY(), at.getZ() + 0.5, 0.8);
            if (drone.position().distanceToSqr(Vec3.atBottomCenterOf(at)) > 2.5)
            {
                stuck++;
                if (drone.tickCount % 20 == 0)
                {
                    drone.getNavigation().moveTo(at.getX() + 0.5, at.getY(), at.getZ() + 0.5, 0.9);
                }
                return;
            }
            // the tunnel is 2 wide and 4 high, so a Praetorian can use it too
            Direction side = dir.getClockWise();
            java.util.List<BlockPos> clear = new java.util.ArrayList<>();
            java.util.List<BlockPos> check = new java.util.ArrayList<>();
            for (BlockPos lane : new BlockPos[] { next, next.relative(side) })
            {
                for (int up = 3; up >= 0; up--)
                {
                    clear.add(lane.above(up));
                }
                check.add(lane.below());
            }
            check.addAll(clear);
            // nothing in the way may be a fluid or unbreakable, and the floor must hold
            for (BlockPos p : check)
            {
                BlockState s = level.getBlockState(p);
                if (!s.getFluidState().isEmpty() || s.is(ModTags.XENO_UNBREAKABLE) || s.getDestroySpeed(level, p) < 0)
                {
                    hive.setDig(at, dir.getClockWise()); // turn and try another way
                    stuck += 20;
                    return;
                }
            }
            for (BlockPos p : clear)
            {
                BlockState s = level.getBlockState(p);
                if (!s.isAir())
                {
                    float hardness = Math.max(0.5F, s.getDestroySpeed(level, p));
                    float speed = (float) (double) AvpConfig.server(AvpConfig.SERVER.xenoDigSpeed);
                    if (++digTicks % 5 == 0)
                    {
                        level.destroyBlockProgress(drone.getId(), p, (int) Math.min(9, digTicks * speed / (hardness * 6)));
                        drone.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
                    }
                    if (digTicks * speed >= hardness * 40)
                    {
                        level.destroyBlock(p, false, drone);
                        level.destroyBlockProgress(drone.getId(), p, -1);
                        digTicks = 0;
                    }
                    return;
                }
            }
            // step down; every 6 steps the stair turns, so it winds down rather than wandering off
            int depth = hive.entrance().getY() - next.getY();
            hive.setDig(next, depth % 6 == 0 ? dir.getClockWise() : dir);
            HiveData.get(level).changed();
            stuck = 0;
        }

        /**
         * At the deepslate: hollow out the hive's first chamber and open it. A dome 15 wide and 8 high,
         * so a Queen (2 x 5 blocks) has room to move.
         */
        private void carveChamber(ServerLevel level, BlockPos centre)
        {
            int r = 7, height = 8;
            for (BlockPos p : BlockPos.betweenClosed(centre.offset(-r, 0, -r), centre.offset(r, height - 1, r)))
            {
                double dx = (p.getX() - centre.getX()) / (double) r, dz = (p.getZ() - centre.getZ()) / (double) r;
                double dy = (p.getY() - centre.getY()) / (double) height; // 0 at the floor, ~1 at the top of the dome
                BlockState s = level.getBlockState(p);
                if (dx * dx + dz * dz + dy * dy <= 1.0 && !s.isAir() && s.getFluidState().isEmpty() && !s.is(ModTags.XENO_UNBREAKABLE)
                        && s.getDestroySpeed(level, p) >= 0 && !s.hasBlockEntity())
                {
                    level.setBlock(p, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                }
            }
            level.sendParticles(net.minecraft.core.particles.ParticleTypes.POOF, centre.getX() + 0.5, centre.getY() + 2, centre.getZ() + 0.5,
                    40, 3, 1.5, 3, 0.02);
            hive.setCore(centre.above());
            hive.setDig(null, Direction.NORTH);
            hive.setState(Hive.State.ACTIVE);
            HiveData.get(level).changed();
            level.playSound(null, centre, SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.HOSTILE, 1.0F, 0.6F);
            drone.setState("HIVE FOUNDED");
        }

        @Override
        public void stop()
        {
            if (hive != null && hive.state() == Hive.State.FOUNDING && stuck >= 400 && drone.level() instanceof ServerLevel level)
            {
                // couldn't get down (lava, water, bedrock...): found the hive where it got to
                BlockPos at = hive.digPos() == null ? drone.blockPosition() : hive.digPos();
                carveChamber(level, at);
            }
        }
    }

    /** Drones coat their hive in resin, from the core outwards. Well-fed Drones build faster. */
    public static class BuildResinGoal extends Goal
    {
        private final DroneEntity drone;
        @Nullable
        private BlockPos spot;
        private int ticks;

        public BuildResinGoal(DroneEntity drone)
        {
            this.drone = drone;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            double speed = AvpConfig.server(AvpConfig.SERVER.resinBuildSpeed);
            if (!enabled() || speed <= 0 || drone.getTarget() != null || drone.isVehicle() || drone.tickCount % 20 >= 2
                    || !(drone.level() instanceof ServerLevel level))
            {
                return false;
            }
            Hive hive = drone.hive();
            if (hive == null || hive.state() != Hive.State.ACTIVE)
            {
                return false;
            }
            int radius = hive.buildRadius(level.getGameTime(), HiveData.maxRadius());
            BlockPos core = hive.core();
            // somewhere in the hive, near where it has got to so far
            for (int tries = 0; tries < 8; tries++)
            {
                BlockPos p = core.offset(drone.getRandom().nextInt(radius * 2 + 1) - radius, drone.getRandom().nextInt(7) - 3,
                        drone.getRandom().nextInt(radius * 2 + 1) - radius);
                if (p.distSqr(core) <= radius * radius && level.getBlockState(p).isAir() && level.getBlockState(p.below()).isSolid())
                {
                    spot = p;
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean canContinueToUse()
        {
            return spot != null && ticks < 200 && drone.getTarget() == null;
        }

        @Override
        public void start()
        {
            ticks = 0;
            drone.getNavigation().moveTo(spot.getX() + 0.5, spot.getY(), spot.getZ() + 0.5, 0.8);
            drone.setState("BUILDING");
        }

        @Override
        public void tick()
        {
            ticks++;
            if (spot == null || drone.blockPosition().distSqr(spot) > 9)
            {
                return;
            }
            ServerLevel level = (ServerLevel) drone.level();
            double speed = AvpConfig.server(AvpConfig.SERVER.resinBuildSpeed) * (drone.hunger() > 70 ? 2.0 : 1.0);
            int coats = Math.max(1, (int) Math.round(3 * speed));
            int done = 0;
            for (int i = 0; i < 40 && done < coats; i++)
            {
                BlockPos p = drone.blockPosition().offset(drone.getRandom().nextInt(7) - 3, drone.getRandom().nextInt(6) - 2,
                        drone.getRandom().nextInt(7) - 3);
                if (exposed(level, p) && NaturalResinBlock.cover(level, p))
                {
                    done++;
                }
            }
            if (done > 0)
            {
                drone.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
                level.playSound(null, drone.blockPosition(), SoundEvents.SLIME_BLOCK_PLACE, SoundSource.HOSTILE, 0.6F, 0.6F);
                Hive hive = drone.hive();
                if (hive != null)
                {
                    hive.addResin(done);
                    HiveData.get(level).changed();
                }
            }
            spot = null;
        }

        /** Only blocks with a face open to the hive get coated. */
        private static boolean exposed(ServerLevel level, BlockPos pos)
        {
            for (Direction d : Direction.values())
            {
                if (level.getBlockState(pos.relative(d)).isAir())
                {
                    return true;
                }
            }
            return false;
        }
    }

    /** A Drone carrying a subdued host takes it home and cocoons it on the wall near the core. */
    public static class CarryHostGoal extends Goal
    {
        private final DroneEntity drone;

        public CarryHostGoal(DroneEntity drone)
        {
            this.drone = drone;
            setFlags(EnumSet.of(Flag.MOVE, Flag.TARGET));
        }

        @Override
        public boolean canUse()
        {
            return drone.getFirstPassenger() instanceof net.minecraft.world.entity.LivingEntity && drone.hive() != null;
        }

        @Override
        public void start()
        {
            drone.setTarget(null);
            drone.setState("CARRYING HOST");
        }

        @Override
        public void tick()
        {
            Hive hive = drone.hive();
            if (hive == null || !(drone.getFirstPassenger() instanceof net.minecraft.world.entity.LivingEntity host))
            {
                return;
            }
            BlockPos core = hive.core();
            if (drone.blockPosition().distSqr(core) > 16)
            {
                if (drone.tickCount % 20 < 2)
                {
                    drone.getNavigation().moveTo(core.getX() + 0.5, core.getY(), core.getZ() + 0.5, 0.9);
                }
                return;
            }
            cocoon((ServerLevel) drone.level(), host, core);
        }

        /** Puts the host against a wall near the core and seals it in. */
        private void cocoon(ServerLevel level, net.minecraft.world.entity.LivingEntity host, BlockPos core)
        {
            BlockPos spot = null;
            for (int tries = 0; tries < 30 && spot == null; tries++)
            {
                BlockPos p = core.offset(drone.getRandom().nextInt(9) - 4, drone.getRandom().nextInt(3) - 1, drone.getRandom().nextInt(9) - 4);
                if (level.getBlockState(p).isAir() && level.getBlockState(p.above()).isAir() && level.getBlockState(p.below()).isSolid())
                {
                    for (Direction d : Direction.Plane.HORIZONTAL)
                    {
                        if (level.getBlockState(p.relative(d)).isSolid())
                        {
                            spot = p;
                            break;
                        }
                    }
                }
            }
            if (spot == null)
            {
                spot = drone.blockPosition();
            }
            drone.releasing = true;
            host.stopRiding();
            drone.releasing = false;
            host.teleportTo(spot.getX() + 0.5, spot.getY(), spot.getZ() + 0.5);
            host.setData(ModAttachments.COCOONED, 0);
            host.setData(ModAttachments.COCOON_POS, spot);
            if (host instanceof Mob mob)
            {
                mob.setNoAi(true);
                mob.setPersistenceRequired();
                mob.setTarget(null);
            }
            else if (host instanceof net.minecraft.world.entity.player.Player player)
            {
                player.setData(ModAttachments.STRUGGLE, 0);
                player.displayClientMessage(net.minecraft.network.chat.Component.translatable("message.avp.cocooned"), false);
            }
            // resin around it
            for (Direction d : Direction.Plane.HORIZONTAL)
            {
                NaturalResinBlock.cover(level, spot.relative(d));
                NaturalResinBlock.cover(level, spot.above().relative(d));
            }
            level.playSound(null, spot, SoundEvents.SLIME_BLOCK_PLACE, SoundSource.HOSTILE, 1.0F, 0.5F);
            drone.setState("COCOONED HOST");
        }
    }

    /**
     * The larder: a hungry member feeds on cocooned prey that can't carry an embryo instead of
     * going out to hunt. Hosts (waiting for an egg, or already carrying an embryo) are left alone.
     */
    public static class FeedFromLarderGoal extends Goal
    {
        private final XenomorphEntity xeno;
        @Nullable
        private net.minecraft.world.entity.LivingEntity food;
        private int biteCooldown;

        public FeedFromLarderGoal(XenomorphEntity xeno)
        {
            this.xeno = xeno;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public static boolean isFood(net.minecraft.world.entity.LivingEntity e)
        {
            return e.isAlive() && e.hasData(ModAttachments.COCOONED) && !(e instanceof net.minecraft.world.entity.player.Player)
                    && !e.hasData(ModAttachments.EMBRYO) && !org.avp.entity.parasite.FacehuggerEntity.isValidHost(e);
        }

        @Override
        public boolean canUse()
        {
            if (!enabled() || !AvpConfig.server(AvpConfig.SERVER.hiveLarder) || xeno.getTarget() != null || !xeno.isHungry()
                    || xeno.isVehicle() || xeno.tickCount % 20 >= 2 || xeno.hive() == null)
            {
                return false;
            }
            food = xeno.level().getEntitiesOfClass(net.minecraft.world.entity.LivingEntity.class, xeno.getBoundingBox().inflate(24), FeedFromLarderGoal::isFood)
                    .stream().min(java.util.Comparator.comparingDouble(xeno::distanceToSqr)).orElse(null);
            return food != null;
        }

        @Override
        public boolean canContinueToUse()
        {
            return food != null && food.isAlive() && xeno.getTarget() == null && xeno.isHungry();
        }

        @Override
        public void start()
        {
            biteCooldown = 0;
            xeno.setState("FEEDING");
        }

        @Override
        public void tick()
        {
            if (food == null)
            {
                return;
            }
            xeno.getLookControl().setLookAt(food, 30F, 30F);
            if (xeno.distanceToSqr(food) > 6.25)
            {
                if (xeno.tickCount % 20 < 2)
                {
                    xeno.getNavigation().moveTo(food, 0.8);
                }
                return;
            }
            xeno.getNavigation().stop();
            biteCooldown -= 2; // ticked every other tick
            if (biteCooldown <= 0)
            {
                biteCooldown = 20;
                xeno.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
                xeno.doHurtTarget(food); // a kill feeds it (XenomorphEntity.killedEntity)
            }
        }

        @Override
        public void stop()
        {
            food = null;
        }
    }

    private HiveGoals()
    {
    }
}
