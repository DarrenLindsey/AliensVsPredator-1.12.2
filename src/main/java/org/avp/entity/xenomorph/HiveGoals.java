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
            hive = data.create(new BlockPos(start.getX(), deepslateLevel(level, start), start.getZ()), start, Hive.State.FOUNDING, level.getGameTime());
            hive.setDig(start, drone.getDirection());
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
                    float speed = (float) (AvpConfig.server(AvpConfig.SERVER.xenoDigSpeed) * AvpConfig.server(AvpConfig.SERVER.foundingDigSpeed));
                    if (++digTicks % 3 == 0)
                    {
                        level.destroyBlockProgress(drone.getId(), p, (int) Math.min(9, digTicks * speed / (hardness * 4)));
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
            hive.setDigger(null);
            if (hive.rooms(org.avp.world.hive.Room.Type.QUEEN).isEmpty())
            {
                hive.rooms().add(0, new org.avp.world.hive.Room(org.avp.world.hive.Room.Type.QUEEN, centre, centre, r, height, true));
            }
            hive.setState(Hive.State.ACTIVE);
            HiveData.get(level).changed();
            level.playSound(null, centre, SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.HOSTILE, 1.0F, 0.6F);
            drone.setState("HIVE FOUNDED");
        }

        @Override
        public void stop()
        {
            drone.setFounding(false);
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
                    drone.getNavigation().moveTo(core.getX() + 0.5, core.getY(), core.getZ() + 0.5, 0.9);
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

        @Override
        public void start()
        {
            progress = 0;
            ticks = 0;
            drone.setState("DIGGING THE HIVE");
            drone.getNavigation().moveTo(block.getX() + 0.5, block.getY(), block.getZ() + 0.5, 0.9);
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
            if (drone.distanceToSqr(net.minecraft.world.phys.Vec3.atCenterOf(block)) > 3.2 * 3.2)
            {
                if (ticks % 20 == 0)
                {
                    drone.getNavigation().moveTo(block.getX() + 0.5, block.getY(), block.getZ() + 0.5, 0.9);
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
     * Builder Drones: they secrete hivestone to make the hive whole - floors over holes and drops,
     * floors under galleries that cross open space, seals over water and lava leaking in, a roof
     * over any room open to the sky - and pave the room floors in it.
     */
    public static class BuildHiveGoal extends Goal
    {
        private final DroneEntity drone;
        @Nullable
        private BlockPos job;
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
            drone.getNavigation().moveTo(job.getX() + 0.5, job.getY() + 1, job.getZ() + 0.5, 0.9);
        }

        @Override
        public void tick()
        {
            ticks += 2; // ticked every other tick
            if (job == null || drone.distanceToSqr(net.minecraft.world.phys.Vec3.atCenterOf(job)) > 4.5 * 4.5)
            {
                if (job != null && ticks % 20 == 0)
                {
                    drone.getNavigation().moveTo(job.getX() + 0.5, job.getY() + 1, job.getZ() + 0.5, 0.9);
                }
                return;
            }
            ServerLevel level = (ServerLevel) drone.level();
            drone.getLookControl().setLookAt(net.minecraft.world.phys.Vec3.atCenterOf(job));
            drone.swing(net.minecraft.world.InteractionHand.MAIN_HAND);
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
            drone.getNavigation().moveTo(target.getX() + 0.5, target.getY(), target.getZ() + 0.5, 0.9);
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
                    drone.getNavigation().moveTo(target.getX() + 0.5, target.getY(), target.getZ() + 0.5, 0.9);
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
                drone.getNavigation().moveTo(target.getX() + 0.5, target.getY(), target.getZ() + 0.5, 0.9);
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
