package org.avp.entity.xenomorph;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import org.avp.block.NaturalResinBlock;
import org.avp.config.AvpConfig;
import org.avp.registry.ModAttachments;
import org.avp.registry.ModTags;
import org.avp.world.hive.Hive;
import org.avp.world.hive.HiveData;
import org.avp.world.hive.HiveShaft;

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
 * - a Drone with no hive in range digs a vertical shaft and founds a hive below it;
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
        private boolean usingShaft;

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
            // Warriors and the like gather at the guard post
            if (!xeno.isQueen() && !heir && !(xeno instanceof DroneEntity) && xeno instanceof AdultXenomorphEntity)
            {
                java.util.Optional<org.avp.world.hive.Room> post = hive.rooms(org.avp.world.hive.Room.Type.GUARD_POST).stream()
                        .filter(org.avp.world.hive.Room::done).findFirst();
                if (post.isPresent())
                {
                    home = post.get().centre();
                    range = post.get().radius() + 2;
                }
            }
            return xeno.blockPosition().distSqr(home) > range * range;
        }

        @Override
        public boolean canContinueToUse()
        {
            return xeno.getTarget() == null && (usingShaft || !xeno.getNavigation().isDone());
        }

        @Override
        public void start()
        {
            xeno.setState("GOING HOME");
            routeHome();
        }

        @Override
        public boolean requiresUpdateEveryTick()
        {
            return true;
        }

        @Override
        public void tick()
        {
            if (usingShaft)
            {
                routeHome();
            }
        }

        private void routeHome()
        {
            Hive hive = xeno.hive();
            usingShaft = hive != null && HiveShaft.descend(xeno, hive);
            if (!usingShaft)
            {
                xeno.navigateTo(home.getX() + 0.5, home.getY(), home.getZ() + 0.5, 0.9);
            }
        }
    }

    /** Use a vertical exit when the target is on the surface. Kept separate from the creature's normal navigation. */
    public static class ExitShaftGoal extends Goal
    {
        private final XenomorphEntity xeno;
        @Nullable
        private Hive hive;
        private Direction exit = Direction.NORTH;
        private int expires;
        private int nextAttempt;

        public ExitShaftGoal(XenomorphEntity xeno)
        {
            this.xeno = xeno;
            setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        @Override
        public boolean canUse()
        {
            if (xeno.tickCount < nextAttempt || xeno.tickCount % 10 >= 2 || xeno.getTarget() == null || xeno.isFounding())
            {
                return false;
            }
            hive = xeno.hive();
            if (hive == null || !hive.hasVerticalShaft() || hive.state() != Hive.State.ACTIVE || xeno.getBbWidth() >= 5)
            {
                return false;
            }
            BlockPos entrance = hive.entrance();
            return xeno.getTarget().getY() >= entrance.getY() - 1 && xeno.getY() < entrance.getY()
                    && xeno.getY() >= hive.core().getY() - 2
                    && (HiveShaft.isInside(xeno, entrance) || hive.core().distSqr(xeno.blockPosition()) < 16 * 16);
        }

        @Override
        public void start()
        {
            BlockPos entrance = hive.entrance();
            exit = Direction.getNearest(xeno.getTarget().getX() - entrance.getX() - .5, 0,
                    xeno.getTarget().getZ() - entrance.getZ() - .5);
            if (exit.getAxis().isVertical())
            {
                exit = xeno.getDirection();
            }
            expires = xeno.tickCount + 100 + 8 * (entrance.getY() - hive.core().getY());
            xeno.getNavigation().stop();
        }

        @Override
        public boolean canContinueToUse()
        {
            return xeno.getTarget() != null && xeno.tickCount < expires && hive != null
                    && (xeno.getY() < hive.entrance().getY() || HiveShaft.isInside(xeno, hive.entrance())
                    || !xeno.onGround());
        }

        @Override
        public boolean requiresUpdateEveryTick()
        {
            return true;
        }

        @Override
        public void tick()
        {
            HiveShaft.ascend(xeno, hive, exit);
        }

        @Override
        public void stop()
        {
            nextAttempt = xeno.tickCount + 60;
            xeno.getNavigation().stop();
        }
    }

    /** A founding Drone excavates a straight shaft, then opens the chamber at its base. */
    public static class BurrowGoal extends Goal
    {
        private static final int BLOCKS_PER_BATCH = 6;
        private static final double SHAFT_DIG_MULTIPLIER = 2.0;

        private final DroneEntity drone;
        @Nullable
        private Hive hive;
        private int digTicks;
        private int stuck;
        private List<BlockPos> layer = List.of();
        private final List<BlockPos> work = new ArrayList<>(BLOCKS_PER_BATCH);

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
            HiveData data = HiveData.get(level);
            Hive mine = drone.hive();
            if (mine != null)
            {
                // carry on digging the hive's tunnel - this Drone's job, or anyone's if the digger is gone
                if (mine.state() != Hive.State.FOUNDING || mine.digPos() == null || drone.getTarget() != null)
                {
                    return false;
                }
                net.minecraft.world.entity.Entity digger = mine.digger() == null ? null : level.getEntity(mine.digger());
                boolean mineToDo = drone.getUUID().equals(mine.digger()) || digger == null || !digger.isAlive();
                if (mineToDo)
                {
                    if (!mine.hasVerticalShaft() && HiveShaft.canExcavate(level, HiveShaft.layer(mine.entrance().below())))
                    {
                        mine.beginShaft();
                        data.changed();
                    }
                    mine.setDigger(drone.getUUID());
                    hive = mine;
                    return true;
                }
                return false;
            }
            if (!forced && (drone.getTarget() != null || drone.tickCount % 100 >= 2))
            {
                return false;
            }
            if (!forced && (data.nearest(drone.blockPosition(), HiveData.spacing()).isPresent() || !drone.onGround()))
            {
                return false;
            }
            // claim it: the hive goes at the first deepslate under here
            BlockPos start = drone.blockPosition();
            if (!HiveShaft.canExcavate(level, HiveShaft.layer(start.below())))
            {
                return false;
            }
            hive = data.create(new BlockPos(start.getX(), deepslateLevel(level, start), start.getZ()), start, Hive.State.FOUNDING, level.getGameTime());
            hive.beginShaft();
            hive.setDigger(drone.getUUID());
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
            drone.setFounding(true); // the urge: no hunting, no hunger, until the hive is made
            digTicks = 0;
            stuck = 0;
            layer = List.of();
            work.clear();
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
            if (!hive.hasVerticalShaft() || at.getY() <= hive.core().getY())
            {
                finish(level, at);
                return;
            }
            BlockPos next = at.below();
            // Stay over the centre support. In an open cave the Drone may drop ahead of the cut.
            drone.getMoveControl().setWantedPosition(at.getX() + 0.5, at.getY(), at.getZ() + 0.5, 0.8);
            if (!HiveShaft.isInside(drone, at) || drone.getY() > at.getY() + 1.5)
            {
                stuck++;
                if (drone.tickCount % 20 == 0)
                {
                    drone.navigateTo(at.getX() + 0.5, at.getY(), at.getZ() + 0.5, 0.9);
                }
                return;
            }
            if (layer.isEmpty())
            {
                layer = HiveShaft.layer(next);
                if (!HiveShaft.canExcavate(level, layer))
                {
                    finish(level, at);
                    return;
                }
            }
            if (work.isEmpty())
            {
                for (BlockPos pos : layer)
                {
                    if (!level.getBlockState(pos).isAir())
                    {
                        work.add(pos);
                        if (work.size() == BLOCKS_PER_BATCH)
                        {
                            break;
                        }
                    }
                }
            }
            if (!work.isEmpty())
            {
                digBatch(level, at);
                return;
            }
            if (at.equals(hive.entrance()))
            {
                HiveShaft.lineMouth(level, at);
            }
            hive.setDig(next, Direction.NORTH);
            HiveData.get(level).changed();
            layer = List.of();
            stuck = 0;
        }

        private void digBatch(ServerLevel level, BlockPos at)
        {
            float hardness = 0.5F;
            for (BlockPos pos : work)
            {
                hardness = Math.max(hardness, level.getBlockState(pos).getDestroySpeed(level, pos));
            }
            double speed = AvpConfig.server(AvpConfig.SERVER.xenoDigSpeed)
                    * AvpConfig.server(AvpConfig.SERVER.foundingDigSpeed) * SHAFT_DIG_MULTIPLIER;
            if (++digTicks % 3 == 0)
            {
                level.destroyBlockProgress(drone.getId(), work.getFirst(), (int) Math.min(9, digTicks * speed / (hardness * 4)));
                drone.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
            }
            if (digTicks * speed < hardness * 40)
            {
                return;
            }
            clearCracks(level);
            if (!HiveShaft.canExcavate(level, work))
            {
                finish(level, at);
                return;
            }
            for (BlockPos pos : work)
            {
                if (!level.getBlockState(pos).isAir() && !level.destroyBlock(pos, false, drone))
                {
                    finish(level, at);
                    return;
                }
            }
            work.clear();
            digTicks = 0;
        }

        private void clearCracks(ServerLevel level)
        {
            if (!work.isEmpty())
            {
                level.destroyBlockProgress(drone.getId(), work.getFirst(), -1);
            }
        }

        private void finish(ServerLevel level, BlockPos at)
        {
            if (hive.hasVerticalShaft() && at.equals(hive.entrance()))
            {
                HiveData.get(level).remove(hive); // The first cut was blocked. Don't hollow out the surface.
                drone.setHiveId(null);
                hive = null;
                return;
            }
            carveChamber(level, at);
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
                if (dx * dx + dz * dz + dy * dy <= 1.0 && !s.isAir() && org.avp.world.hive.HivePlanner.diggable(level, p))
                {
                    level.setBlock(p, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                }
            }
            level.sendParticles(net.minecraft.core.particles.ParticleTypes.POOF, centre.getX() + 0.5, centre.getY() + 2, centre.getZ() + 0.5,
                    40, 3, 1.5, 3, 0.02);
            hive.setCore(centre.above());
            hive.setDig(null, Direction.NORTH);
            hive.setDigger(null);
            if (hive.rooms(org.avp.world.hive.Room.Type.QUEEN).isEmpty())
            {
                hive.rooms().add(0, new org.avp.world.hive.Room(org.avp.world.hive.Room.Type.QUEEN, centre, centre, r, height, true));
            }
            hive.setState(Hive.State.ACTIVE);
            hive.placeCoreBlock(level);
            HiveData.get(level).changed();
            level.playSound(null, centre, SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.HOSTILE, 1.0F, 0.6F);
            drone.setState("HIVE FOUNDED");
        }

        @Override
        public void stop()
        {
            drone.setFounding(false);
            if (!(drone.level() instanceof ServerLevel level))
            {
                return;
            }
            clearCracks(level);
            if (hive != null && hive.state() == Hive.State.FOUNDING && stuck >= 400)
            {
                // couldn't get down (lava, water, bedrock...): found the hive where it got to
                BlockPos at = hive.digPos() == null ? drone.blockPosition() : hive.digPos();
                finish(level, at);
            }
        }
    }

    /** Drones coat their hive in resin, from the core outwards. Well-fed Drones build faster. */
    public static class BuildResinGoal extends Goal
    {
        private final DroneEntity drone;
        @Nullable
        private BlockPos spot;
        @Nullable
        private org.avp.world.hive.Room room;
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
            // somewhere in one of its finished rooms (the galleries get coated on the way)
            java.util.List<org.avp.world.hive.Room> rooms = hive.rooms().stream().filter(r -> r.done() && r.type().isChamber()).toList();
            if (rooms.isEmpty())
            {
                return false;
            }
            room = rooms.get(drone.getRandom().nextInt(rooms.size()));
            int radius = Math.max(2, room.radius());
            for (int tries = 0; tries < 8; tries++)
            {
                BlockPos p = room.centre().offset(drone.getRandom().nextInt(radius * 2 + 1) - radius, drone.getRandom().nextInt(4),
                        drone.getRandom().nextInt(radius * 2 + 1) - radius);
                if (level.getBlockState(p).isAir() && level.getBlockState(p.below()).isSolid())
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
            drone.navigateTo(spot.getX() + 0.5, spot.getY(), spot.getZ() + 0.5, 0.8);
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
            // now and then, a ribbed resin column from floor to ceiling
            if (room != null && room.radius() >= 4 && room.pillars() < 3 && drone.getRandom().nextInt(8) == 0)
            {
                done += pillar(level, room);
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

        /** A resin column in the room, floor to ceiling. Returns the blocks placed. */
        private int pillar(ServerLevel level, org.avp.world.hive.Room room)
        {
            int r = (int) (room.radius() * 0.6);
            BlockPos base = room.centre().offset(drone.getRandom().nextInt(r * 2 + 1) - r, 0, drone.getRandom().nextInt(r * 2 + 1) - r);
            while (level.getBlockState(base).isAir() && base.getY() > room.centre().getY() - 3)
            {
                base = base.below();
            }
            base = base.above();
            int height = 0;
            while (level.getBlockState(base.above(height)).isAir() && height < room.height() + 3)
            {
                height++;
            }
            if (height < 2 || height > room.height() + 2 || !level.getBlockState(base.above(height)).isSolid())
            {
                return 0; // no ceiling over it
            }
            for (int y = 0; y < height; y++)
            {
                level.setBlock(base.above(y), org.avp.registry.ModBlocks.HIVERESIN_STANDARD.get().defaultBlockState(), 3);
            }
            room.addPillar();
            return height;
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
            return drone.getFirstPassenger() instanceof net.minecraft.world.entity.LivingEntity passenger && !(passenger instanceof OvamorphEntity)
                    && drone.hive() != null;
        }

        @Override
        public void start()
        {
            drone.setTarget(null);
            drone.setState("CARRYING HOST");
        }

        @Override
        public boolean requiresUpdateEveryTick()
        {
            return true;
        }

        @Override
        public void tick()
        {
            Hive hive = drone.hive();
            if (hive == null || !(drone.getFirstPassenger() instanceof net.minecraft.world.entity.LivingEntity host))
            {
                return;
            }
            if (HiveShaft.descend(drone, hive))
            {
                return;
            }
            if ((drone.tickCount & 1) != 0)
            {
                return;
            }
            boolean isHost = org.avp.entity.parasite.FacehuggerEntity.isValidHost(host) || host instanceof net.minecraft.world.entity.player.Player;
            // hosts go where the eggs are: the Queen's chamber when she's alive (she lays where she is), else the hatchery
            org.avp.world.hive.Room.Type roomType = !isHost ? org.avp.world.hive.Room.Type.LARDER
                    : hive.hasQueen(drone.level().getGameTime()) ? org.avp.world.hive.Room.Type.QUEEN : org.avp.world.hive.Room.Type.HATCHERY;
            org.avp.world.hive.Room room = hive.rooms(roomType).stream().filter(org.avp.world.hive.Room::done).findFirst()
                    .or(() -> hive.rooms(org.avp.world.hive.Room.Type.HATCHERY).stream().filter(org.avp.world.hive.Room::done).findFirst())
                    .orElse(null);
            BlockPos core = room != null ? room.centre() : hive.core();
            if (drone.blockPosition().distSqr(core) > (room != null ? Math.max(9, room.radius() * room.radius()) : 16))
            {
                if (drone.tickCount % 20 < 2)
                {
                    drone.navigateTo(core.getX() + 0.5, core.getY(), core.getZ() + 0.5, 0.9);
                }
                return;
            }
            cocoon((ServerLevel) drone.level(), host, core, room);
        }

        /** Puts the host against a wall near the core and seals it in. */
        private void cocoon(ServerLevel level, net.minecraft.world.entity.LivingEntity host, BlockPos core, @Nullable org.avp.world.hive.Room room)
        {
            BlockPos spot = room == null ? null : org.avp.world.hive.HivePlanner.floorSpot(level, room, true);
            for (int tries = 0; tries < 30 && spot == null; tries++) // no room yet: near the core
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
     * The colony at work: Drones dig the hive's next room (and the gallery to it), several at a
     * time, the way ants do. Fed Drones dig faster.
     */
    public static class DigHiveGoal extends Goal
    {
        private final DroneEntity drone;
        @Nullable
        private BlockPos block;
        private float progress;
        private int ticks;

        public DigHiveGoal(DroneEntity drone)
        {
            this.drone = drone;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse()
        {
            if (!enabled() || drone.getTarget() != null || drone.isVehicle() || drone.hunger() < 15 || drone.tickCount % 20 >= 2
                    || !(drone.level() instanceof ServerLevel level))
            {
                return false;
            }
            Hive hive = drone.hive();
            if (hive == null || hive.state() != Hive.State.ACTIVE)
            {
                return false;
            }
            block = org.avp.world.hive.HivePlanner.nextBlock(level, hive, Math.floorMod(drone.getId(), 3));
            return block != null;
        }

        @Override
        public boolean canContinueToUse()
        {
            return block != null && drone.getTarget() == null && ticks < 300;
        }

        /** Walk to the open side of the block (walking at the solid block itself found no path). */
        private void approach()
        {
            BlockPos stand = org.avp.world.hive.HivePlanner.openSide((ServerLevel) drone.level(), block);
            BlockPos to = stand != null ? stand : block;
            drone.navigateTo(to.getX() + 0.5, to.getY(), to.getZ() + 0.5, 0.9);
        }

        @Override
        public void start()
        {
            progress = 0;
            ticks = 0;
            drone.setState("DIGGING THE HIVE");
            approach();
        }

        @Override
        public boolean requiresUpdateEveryTick()
        {
            return true;
        }

        @Override
        public void tick()
        {
            if (block == null)
            {
                return;
            }
            ticks++;
            ServerLevel level = (ServerLevel) drone.level();
            Hive hive = drone.hive();
            if (hive == null || !org.avp.world.hive.HivePlanner.diggable(level, block))
            {
                if (hive != null)
                {
                    org.avp.world.hive.HivePlanner.advance(level, hive, block, false);
                }
                block = null;
                return;
            }
            if (drone.distanceToSqr(net.minecraft.world.phys.Vec3.atCenterOf(block)) > 4.0 * 4.0)
            {
                if (ticks % 20 == 0)
                {
                    approach();
                }
                return;
            }
            drone.getNavigation().stop();
            drone.getLookControl().setLookAt(net.minecraft.world.phys.Vec3.atCenterOf(block));
            BlockState state = level.getBlockState(block);
            float hardness = Math.max(0.3F, state.getDestroySpeed(level, block));
            float speed = (float) (double) AvpConfig.server(AvpConfig.SERVER.xenoDigSpeed) * (drone.hunger() > 70 ? 1.5F : 1.0F);
            progress += speed / (hardness * 20F);
            if (ticks % 6 == 0)
            {
                drone.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
                level.destroyBlockProgress(drone.getId(), block, Math.min(9, (int) (progress * 10)));
            }
            if (progress >= 1F)
            {
                level.destroyBlockProgress(drone.getId(), block, -1);
                level.destroyBlock(block, false, drone);
                hive.checkForLeaks(level, block, 1); // did that open onto water or lava?
                org.avp.world.hive.HivePlanner.advance(level, hive, block, true);
                block = null;
            }
        }

        @Override
        public void stop()
        {
            if (block != null && drone.level() instanceof ServerLevel level)
            {
                level.destroyBlockProgress(drone.getId(), block, -1);
                Hive hive = drone.hive();
                if (hive != null && ticks >= 300)
                {
                    org.avp.world.hive.HivePlanner.advance(level, hive, block, false); // couldn't get to it: skip
                }
            }
            block = null;
        }
    }

    /**
     * Builder Drones: they keep the hive to its plan - clearing anything placed where a room or gallery
 * should be open - and secrete hivestone to make it whole: floors over holes and drops,
     * floors under galleries that cross open space, seals over water and lava leaking in, a roof
     * over any room open to the sky - and pave the room floors in it.
     */
    public static class BuildHiveGoal extends Goal
    {
        private final DroneEntity drone;
        @Nullable
        private BlockPos job;
        /** The job is clearing a block out of the hive's planned space (not placing hivestone). */
        private boolean clearing;
        private int ticks;

        public BuildHiveGoal(DroneEntity drone)
        {
            this.drone = drone;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse()
        {
            if (!enabled() || drone.getTarget() != null || drone.isVehicle() || drone.tickCount % 20 >= 2 || !drone.isBuilder()
                    || !(drone.level() instanceof ServerLevel level))
            {
                return false;
            }
            Hive hive = drone.hive();
            if (hive == null || hive.state() != Hive.State.ACTIVE)
            {
                return false;
            }
            job = findJob(level, hive);
            return job != null;
        }

        /** A few random looks around the hive for something to build. Urgent jobs (holes, leaks, sky) first; paving last. */
        @Nullable
        private BlockPos findJob(ServerLevel level, Hive hive)
        {
            java.util.List<org.avp.world.hive.Room> rooms = hive.rooms();
            if (rooms.isEmpty())
            {
                return null;
            }
            net.minecraft.util.RandomSource random = drone.getRandom();
            clearing = false;
            // something placed where a finished room or gallery should be open (a player's wall, say): clear it
            for (int probe = 0; probe < 12; probe++)
            {
                org.avp.world.hive.Room room = rooms.get(random.nextInt(rooms.size()));
                java.util.List<BlockPos> space = org.avp.world.hive.HivePlanner.digList(room);
                if (!room.done() || space.isEmpty())
                {
                    continue;
                }
                BlockPos p = space.get(random.nextInt(space.size()));
                BlockState s = level.getBlockState(p);
                if (!s.isAir() && !s.is(org.avp.registry.ModBlocks.HIVERESIN_STANDARD.get()) // resin columns are meant to be there
                        && org.avp.world.hive.HivePlanner.diggable(level, p) && org.avp.world.hive.HivePlanner.openSide(level, p) != null)
                {
                    clearing = true;
                    return p;
                }
            }
            BlockPos paving = null;
            for (int probe = 0; probe < 24; probe++)
            {
                org.avp.world.hive.Room room = rooms.get(random.nextInt(rooms.size()));
                if (!room.done() && room != hive.project())
                {
                    continue;
                }
                int r = Math.max(1, room.radius());
                BlockPos c = room.centre();
                switch (probe % 4)
                {
                    case 0 -> { // a hole in a room's floor
                        BlockPos floor = c.offset(random.nextInt(r * 2 + 1) - r, -1, random.nextInt(r * 2 + 1) - r);
                        if (room.type().isChamber() && room.contains(floor.above()) && level.getBlockState(floor.above()).isAir() && fillable(level, floor))
                        {
                            return floor;
                        }
                    }
                    case 1 -> { // a gallery crossing open space: lay its floor
                        java.util.List<BlockPos> list = org.avp.world.hive.HivePlanner.digList(room);
                        if (!list.isEmpty())
                        {
                            BlockPos p = list.get(random.nextInt(Math.min(list.size(), Math.max(1, room.cursor()))));
                            BlockPos floor = p.below();
                            if (level.getBlockState(p).isAir() && !org.avp.world.hive.HivePlanner.digSet(room).contains(floor) && fillable(level, floor))
                            {
                                return floor;
                            }
                        }
                    }
                    case 2 -> { // water or lava getting in
                        BlockPos p = c.offset(random.nextInt(r * 2 + 3) - r - 1, random.nextInt(room.height() + 3) - 1, random.nextInt(r * 2 + 3) - r - 1);
                        if (!level.getFluidState(p).isEmpty() && !level.getBlockState(p).hasBlockEntity())
                        {
                            return p.immutable();
                        }
                    }
                    default -> { // open to the sky: roof it over; otherwise pave the floor
                        BlockPos roof = c.offset(random.nextInt(r * 2 + 1) - r, Math.max(2, room.height()), random.nextInt(r * 2 + 1) - r);
                        if (room.type().isChamber() && level.getBlockState(roof).isAir() && level.canSeeSky(roof))
                        {
                            return roof;
                        }
                        BlockPos floor = c.offset(random.nextInt(r * 2 + 1) - r, -1, random.nextInt(r * 2 + 1) - r);
                        BlockState s = level.getBlockState(floor);
                        if (paving == null && room.type().isChamber() && room.contains(floor.above()) && level.getBlockState(floor.above()).isAir()
                                && s.isSolid() && !s.is(org.avp.registry.ModBlocks.HIVESTONE.get()) && !s.is(org.avp.registry.ModBlocks.NATURAL_RESIN.get())
                                && !s.hasBlockEntity() && !s.is(ModTags.XENO_UNBREAKABLE) && s.getDestroySpeed(level, floor) >= 0)
                        {
                            paving = floor;
                        }
                    }
                }
            }
            return paving;
        }

        /** Open (air, a fluid, plants...) and safe to fill. */
        private static boolean fillable(ServerLevel level, BlockPos pos)
        {
            BlockState s = level.getBlockState(pos);
            return (s.isAir() || s.canBeReplaced() || !s.getFluidState().isEmpty()) && !s.hasBlockEntity() && level.isInWorldBounds(pos);
        }

        @Override
        public boolean canContinueToUse()
        {
            return job != null && ticks < 200 && drone.getTarget() == null;
        }

        @Override
        public void start()
        {
            ticks = 0;
            drone.setState("BUILDING HIVESTONE");
            drone.navigateTo(job.getX() + 0.5, job.getY() + 1, job.getZ() + 0.5, 0.9);
        }

        @Override
        public void tick()
        {
            ticks += 2; // ticked every other tick
            if (job == null || drone.distanceToSqr(net.minecraft.world.phys.Vec3.atCenterOf(job)) > 4.5 * 4.5)
            {
                if (job != null && ticks % 20 == 0)
                {
                    drone.navigateTo(job.getX() + 0.5, job.getY() + 1, job.getZ() + 0.5, 0.9);
                }
                return;
            }
            ServerLevel level = (ServerLevel) drone.level();
            drone.getLookControl().setLookAt(net.minecraft.world.phys.Vec3.atCenterOf(job));
            drone.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
            if (clearing)
            {
                // tear it out (no drops: it's crushed and carried off)
                if (org.avp.world.hive.HivePlanner.diggable(level, job))
                {
                    level.destroyBlock(job, false, drone);
                    Hive hive = drone.hive();
                    if (hive != null)
                    {
                        hive.checkForLeaks(level, job, 1);
                    }
                }
                job = null;
                return;
            }
            level.setBlock(job, org.avp.registry.ModBlocks.HIVESTONE.get().defaultBlockState(), 3);
            level.playSound(null, job, SoundEvents.MUD_PLACE, SoundSource.HOSTILE, 0.8F, 0.6F);
            level.sendParticles(net.minecraft.core.particles.ParticleTypes.ITEM_SLIME, job.getX() + 0.5, job.getY() + 1, job.getZ() + 0.5, 4, 0.3, 0.1, 0.3, 0.02);
            job = null;
        }
    }

    /**
     * Coater Drones: they climb the hive's walls and ceilings - big captured caves above all - and
     * spray resin over everything within reach, working their way across the surface.
     */
    public static class CoatWallsGoal extends Goal
    {
        private final DroneEntity drone;
        @Nullable
        private BlockPos target;
        private int ticks, sprays;

        public CoatWallsGoal(DroneEntity drone)
        {
            this.drone = drone;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse()
        {
            if (!enabled() || AvpConfig.server(AvpConfig.SERVER.resinBuildSpeed) <= 0 || drone.getTarget() != null || drone.isVehicle()
                    || drone.tickCount % 20 >= 2 || !drone.isCoater() || !(drone.level() instanceof ServerLevel level))
            {
                return false;
            }
            Hive hive = drone.hive();
            if (hive == null || hive.state() != Hive.State.ACTIVE)
            {
                return false;
            }
            java.util.List<org.avp.world.hive.Room> rooms = hive.rooms().stream().filter(r -> r.done() && r.type().isChamber()).toList();
            if (rooms.isEmpty())
            {
                return false;
            }
            // caves are the big job: look there more often
            org.avp.world.hive.Room room = rooms.stream().filter(r -> r.type() == org.avp.world.hive.Room.Type.CAVE).findAny()
                    .filter(r -> drone.getRandom().nextInt(3) > 0).orElse(rooms.get(drone.getRandom().nextInt(rooms.size())));
            target = findSurface(level, room.centre(), room.radius() + 4, room.height() + 6);
            sprays = 0;
            return target != null;
        }

        /** An uncoated wall or ceiling block near here, with open hive space in front of it. */
        @Nullable
        private BlockPos findSurface(ServerLevel level, BlockPos around, int radius, int height)
        {
            net.minecraft.util.RandomSource random = drone.getRandom();
            for (int tries = 0; tries < 40; tries++)
            {
                BlockPos p = around.offset(random.nextInt(radius * 2 + 1) - radius, random.nextInt(height + 2) - 1, random.nextInt(radius * 2 + 1) - radius);
                BlockState s = level.getBlockState(p);
                if (!s.isAir() && NaturalResinBlock.canCover(level, p, s))
                {
                    for (Direction d : Direction.values())
                    {
                        if (d != Direction.UP && level.getBlockState(p.relative(d)).isAir()) // walls and ceilings (floors are the builders')
                        {
                            return p.relative(d).immutable();
                        }
                    }
                }
            }
            return null;
        }

        @Override
        public boolean canContinueToUse()
        {
            return target != null && ticks < 240 && drone.getTarget() == null;
        }

        @Override
        public void start()
        {
            ticks = 0;
            drone.setState("COATING WALLS");
            drone.navigateTo(target.getX() + 0.5, target.getY(), target.getZ() + 0.5, 0.9);
        }

        @Override
        public void tick()
        {
            ticks += 2;
            if (target == null)
            {
                return;
            }
            if (drone.distanceToSqr(net.minecraft.world.phys.Vec3.atCenterOf(target)) > 3.0 * 3.0)
            {
                if (ticks % 20 == 0)
                {
                    drone.navigateTo(target.getX() + 0.5, target.getY(), target.getZ() + 0.5, 0.9);
                }
                return;
            }
            ServerLevel level = (ServerLevel) drone.level();
            // spray everything exposed within reach
            int coated = 0;
            int limit = Math.max(2, (int) Math.round(8 * AvpConfig.server(AvpConfig.SERVER.resinBuildSpeed)));
            BlockPos at = drone.blockPosition();
            for (BlockPos p : BlockPos.betweenClosed(at.offset(-3, -2, -3), at.offset(3, 4, 3)))
            {
                if (coated >= limit)
                {
                    break;
                }
                if (drone.getRandom().nextInt(3) == 0 && exposedNotFloor(level, p) && NaturalResinBlock.cover(level, p))
                {
                    coated++;
                }
            }
            if (coated > 0)
            {
                drone.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
                level.playSound(null, at, SoundEvents.SLIME_BLOCK_PLACE, SoundSource.HOSTILE, 0.7F, 0.5F);
                Hive hive = drone.hive();
                if (hive != null)
                {
                    hive.addResin(coated);
                    HiveData.get(level).changed();
                }
            }
            // and on across the wall
            target = ++sprays < 8 ? findSurface(level, at, 5, 5) : null;
            if (target != null)
            {
                ticks = 0;
                drone.navigateTo(target.getX() + 0.5, target.getY(), target.getZ() + 0.5, 0.9);
            }
        }

        private static boolean exposedNotFloor(ServerLevel level, BlockPos pos)
        {
            for (Direction d : Direction.values())
            {
                if (d != Direction.UP && level.getBlockState(pos.relative(d)).isAir())
                {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Worker Drones take the eggs from the rear of the Queen's egg sac (she lays no more than 3 there)
     * and carry them to the cocooned hosts - or set them out around her chamber.
     */
    public static class CarryEggGoal extends Goal
    {
        private final DroneEntity drone;
        @Nullable
        private OvamorphEntity egg;
        @Nullable
        private BlockPos destination;
        private int ticks;

        public CarryEggGoal(DroneEntity drone)
        {
            this.drone = drone;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            if (!enabled() || drone.getClass() != DroneEntity.class || drone.getTarget() != null || drone.isVehicle()
                    || drone.tickCount % 20 >= 2 || !(drone.level() instanceof ServerLevel level))
            {
                return false;
            }
            Hive hive = drone.hive();
            if (hive == null || hive.queen() == null || !hive.hasQueen(level.getGameTime())
                    || !(level.getEntity(hive.queen()) instanceof AdultXenomorphEntity queen) || drone.distanceTo(queen) > 32)
            {
                return false;
            }
            Vec3 rear = queen.eggSacRear();
            egg = level.getEntitiesOfClass(OvamorphEntity.class, new net.minecraft.world.phys.AABB(rear, rear).inflate(2.5),
                    e -> e.isFull() && !e.isPassenger() && e.openProgress() <= -20).stream().findFirst().orElse(null);
            if (egg == null)
            {
                return false;
            }
            destination = destination(level, hive, rear);
            return destination != null;
        }

        /** Next to a cocooned host with no egg yet; else somewhere in the Queen's chamber, away from the sac. */
        @Nullable
        private BlockPos destination(ServerLevel level, Hive hive, Vec3 rear)
        {
            net.minecraft.world.phys.AABB area = new net.minecraft.world.phys.AABB(hive.core())
                    .inflate(hive.buildRadius(level.getGameTime(), HiveData.maxRadius()) + 2);
            for (net.minecraft.world.entity.LivingEntity host : level.getEntitiesOfClass(net.minecraft.world.entity.LivingEntity.class, area,
                    e -> e.hasData(ModAttachments.COCOONED) && org.avp.entity.parasite.FacehuggerEntity.isValidHost(e)
                            && level.getEntitiesOfClass(OvamorphEntity.class, e.getBoundingBox().inflate(2.5)).isEmpty()))
            {
                for (Direction d : Direction.Plane.HORIZONTAL)
                {
                    BlockPos p = host.blockPosition().relative(d);
                    if (level.getBlockState(p).isAir() && level.getBlockState(p.below()).isSolid())
                    {
                        return p;
                    }
                }
            }
            org.avp.world.hive.Room room = hive.rooms(org.avp.world.hive.Room.Type.QUEEN).stream().findFirst()
                    .or(() -> hive.rooms(org.avp.world.hive.Room.Type.HATCHERY).stream().filter(org.avp.world.hive.Room::done).findFirst()).orElse(null);
            for (int tries = 0; room != null && tries < 6; tries++)
            {
                BlockPos p = org.avp.world.hive.HivePlanner.floorSpot(level, room, false);
                if (p != null && p.distToCenterSqr(rear) > 25 && level.getEntitiesOfClass(OvamorphEntity.class, new net.minecraft.world.phys.AABB(p).inflate(1.0)).isEmpty())
                {
                    return p;
                }
            }
            return null;
        }

        @Override
        public boolean canContinueToUse()
        {
            return egg != null && egg.isAlive() && destination != null && ticks < 600 && drone.getTarget() == null;
        }

        @Override
        public void start()
        {
            ticks = 0;
            drone.setState("CARRYING EGG");
            drone.navigateTo(egg, 0.9);
        }

        @Override
        public void tick()
        {
            ticks += 2;
            if (egg == null || destination == null)
            {
                return;
            }
            if (!egg.isPassenger())
            {
                // go and pick it up
                if (drone.distanceToSqr(egg) < 2.5 * 2.5)
                {
                    egg.startRiding(drone, true);
                    drone.navigateTo(destination.getX() + 0.5, destination.getY(), destination.getZ() + 0.5, 0.8);
                }
                else if (ticks % 20 == 0)
                {
                    drone.navigateTo(egg, 0.9);
                }
                return;
            }
            if (drone.blockPosition().distSqr(destination) > 4)
            {
                if (ticks % 20 == 0)
                {
                    drone.navigateTo(destination.getX() + 0.5, destination.getY(), destination.getZ() + 0.5, 0.8);
                }
                return;
            }
            drone.releasing = true;
            egg.stopRiding();
            drone.releasing = false;
            egg.moveTo(destination.getX() + 0.5, destination.getY(), destination.getZ() + 0.5, egg.getYRot(), 0);
            drone.level().playSound(null, destination, SoundEvents.SLIME_SQUISH, SoundSource.HOSTILE, 0.6F, 0.6F);
            egg = null;
        }

        @Override
        public void stop()
        {
            if (egg != null && egg.isPassenger())
            {
                // interrupted: put it down where it is
                drone.releasing = true;
                egg.stopRiding();
                drone.releasing = false;
            }
            egg = null;
            destination = null;
        }
    }

    /**
     * Water or lava getting into the hive: the nearest free Drone drops what it's doing and walls it
     * off with hivestone, from a safe distance, until the flow is stopped.
     */
    public static class SealLeakGoal extends Goal
    {
        private final DroneEntity drone;
        @Nullable
        private BlockPos leak;
        private int ticks;

        public SealLeakGoal(DroneEntity drone)
        {
            this.drone = drone;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse()
        {
            if (!enabled() || drone.getTarget() != null || drone.isVehicle() || !(drone.level() instanceof ServerLevel level))
            {
                return false;
            }
            Hive hive = drone.hive();
            if (hive == null || hive.leaks.isEmpty())
            {
                return false;
            }
            hive.leaks.removeIf(p -> !Hive.isLeak(level, p));
            leak = hive.leaks.stream().filter(p -> p.distSqr(drone.blockPosition()) < 48 * 48)
                    .min(java.util.Comparator.comparingDouble(p -> p.distSqr(drone.blockPosition()))).orElse(null);
            return leak != null;
        }

        @Override
        public boolean canContinueToUse()
        {
            return leak != null && ticks < 400 && drone.getTarget() == null;
        }

        @Override
        public void start()
        {
            ticks = 0;
            drone.setState("SEALING LEAK");
            approach();
        }

        private void approach()
        {
            BlockPos stand = org.avp.world.hive.HivePlanner.openSide((ServerLevel) drone.level(), leak);
            BlockPos to = stand != null ? stand : leak;
            drone.navigateTo(to.getX() + 0.5, to.getY(), to.getZ() + 0.5, 1.1);
        }

        @Override
        public void tick()
        {
            ticks += 2;
            if (leak == null)
            {
                return;
            }
            ServerLevel level = (ServerLevel) drone.level();
            Hive hive = drone.hive();
            if (hive == null || !Hive.isLeak(level, leak))
            {
                if (hive != null)
                {
                    hive.leaks.remove(leak);
                }
                leak = null;
                return;
            }
            if (drone.distanceToSqr(net.minecraft.world.phys.Vec3.atCenterOf(leak)) > 4.5 * 4.5)
            {
                if (ticks % 20 == 0)
                {
                    approach();
                }
                return;
            }
            drone.getLookControl().setLookAt(net.minecraft.world.phys.Vec3.atCenterOf(leak));
            drone.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
            for (BlockPos p : sealPlan(level, hive, leak))
            {
                level.setBlock(p, org.avp.registry.ModBlocks.HIVESTONE.get().defaultBlockState(), 3);
                hive.leaks.remove(p);
            }
            level.playSound(null, leak, SoundEvents.MUD_PLACE, SoundSource.HOSTILE, 1.0F, 0.6F);
            level.sendParticles(net.minecraft.core.particles.ParticleTypes.CLOUD, leak.getX() + 0.5, leak.getY() + 0.5, leak.getZ() + 0.5, 6, 0.4, 0.4, 0.4, 0.02);
            hive.checkForLeaks(level, leak, 2); // still coming through somewhere next to it?
            leak = null;
        }
    }

    /**
     * Where to put hivestone to stop a leak for good. It follows the liquid upstream: a waterfall or
     * small spring (up to 160 blocks of liquid, with its source) is filled in completely - source
     * and all - leaving a hivestone pillar; anything bigger (a lake, an aquifer) gets a skin of
     * hivestone over every bit of its surface that touches open space, so it can't flow round the patch.
     */
    static java.util.List<BlockPos> sealPlan(ServerLevel level, Hive hive, BlockPos leak)
    {
        net.minecraft.world.level.material.Fluid fluid = level.getFluidState(leak).getType();
        java.util.ArrayDeque<BlockPos> queue = new java.util.ArrayDeque<>();
        java.util.LinkedHashSet<BlockPos> body = new java.util.LinkedHashSet<>();
        queue.add(leak.immutable());
        body.add(leak.immutable());
        boolean source = false, tooBig = false;
        while (!queue.isEmpty())
        {
            BlockPos p = queue.poll();
            source |= level.getFluidState(p).isSource();
            // upwards first: that's where it's coming from
            for (Direction d : new Direction[] { Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.DOWN })
            {
                BlockPos n = p.relative(d);
                if (!body.contains(n) && level.getBlockState(n).getBlock() instanceof net.minecraft.world.level.block.LiquidBlock
                        && level.getFluidState(n).getType().isSame(fluid) && n.distManhattan(leak) <= 40)
                {
                    if (body.size() >= 160)
                    {
                        tooBig = true;
                        queue.clear();
                        break;
                    }
                    body.add(n.immutable());
                    queue.add(n.immutable());
                }
            }
        }
        if (source && !tooBig)
        {
            return new java.util.ArrayList<>(body); // fill the whole flow, source and all
        }
        // too big to fill: seal its surface where it meets open space
        java.util.List<BlockPos> skin = new java.util.ArrayList<>();
        for (BlockPos p : body)
        {
            if (Hive.isLeak(level, p))
            {
                skin.add(p);
            }
        }
        return skin;
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
                    xeno.navigateTo(food, 0.8);
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
