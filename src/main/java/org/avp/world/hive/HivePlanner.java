package org.avp.world.hive;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import javax.annotation.Nullable;

import org.avp.config.AvpConfig;
import org.avp.entity.xenomorph.AdultXenomorphEntity;
import org.avp.entity.xenomorph.OvamorphEntity;
import org.avp.entity.xenomorph.XenomorphEntity;
import org.avp.registry.ModAttachments;
import org.avp.registry.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * Plans how a hive grows, the way an ant colony does: when the hive needs a room (the hatchery
 * is full of cocoons, there's food but no larder, warriors with nowhere to gather...) it picks a
 * spot within its reach and the Drones dig a gallery to it and hollow it out. Rooms are dug one
 * at a time, in order; the work is shared by every Drone in the hive.
 */
public final class HivePlanner
{
    /** Each room's dig list, worked out once (keyed by room; rebuilt if the room grows). */
    private static final Map<Room, List<BlockPos>> DIG_LISTS = new WeakHashMap<>();
    private static final Map<Room, Integer> DIG_LIST_SIZE = new WeakHashMap<>();

    public static int maxDug()
    {
        return AvpConfig.server(AvpConfig.SERVER.hiveMaxDugBlocks);
    }

    /** The blocks to clear for a room: its tunnel (from the room it branches off) then the chamber, from the middle out. */
    public static List<BlockPos> digList(Room room)
    {
        List<BlockPos> cached = DIG_LISTS.get(room);
        if (cached != null && DIG_LIST_SIZE.get(room) == room.radius() * 100 + room.height())
        {
            return cached;
        }
        List<BlockPos> list = new ArrayList<>();
        BlockPos a = room.from(), b = room.centre();
        int dx = b.getX() - a.getX(), dy = b.getY() - a.getY(), dz = b.getZ() - a.getZ();
        int steps = Math.max(Math.max(Math.abs(dx), Math.abs(dz)), Math.abs(dy));
        Direction across = Math.abs(dx) > Math.abs(dz) ? Direction.SOUTH : Direction.EAST;
        java.util.Set<BlockPos> seen = new java.util.HashSet<>();
        for (int i = 0; i <= steps; i++)
        {
            double t = steps == 0 ? 1 : (double) i / steps;
            BlockPos p = new BlockPos(a.getX() + (int) Math.round(dx * t), a.getY() + (int) Math.round(dy * t), a.getZ() + (int) Math.round(dz * t));
            for (int w = 0; w < room.type().tunnelWidth; w++)
            {
                for (int h = 0; h < room.type().tunnelHeight; h++)
                {
                    BlockPos q = p.relative(across, w).above(h);
                    if (seen.add(q))
                    {
                        list.add(q);
                    }
                }
            }
        }
        if (room.type().isChamber() && room.radius() > 0)
        {
            List<BlockPos> chamber = new ArrayList<>();
            int r = room.radius(), height = room.height();
            BlockPos c = room.centre();
            for (BlockPos p : BlockPos.betweenClosed(c.offset(-r, 0, -r), c.offset(r, height - 1, r)))
            {
                double ex = (p.getX() - c.getX()) / (double) r, ez = (p.getZ() - c.getZ()) / (double) r;
                double ey = (p.getY() - c.getY()) / (double) height;
                if (ex * ex + ez * ez + ey * ey <= 1.0 && seen.add(p.immutable()))
                {
                    chamber.add(p.immutable());
                }
            }
            chamber.sort(Comparator.comparingDouble(p -> p.distSqr(c)));
            list.addAll(chamber);
        }
        DIG_LISTS.put(room, list);
        DIG_LIST_SIZE.put(room, room.radius() * 100 + room.height());
        return list;
    }

    private static final Map<Room, java.util.Set<BlockPos>> DIG_SETS = new WeakHashMap<>();

    /** The room's dig list as a set (for "is this part of the hive's open space?"). */
    public static java.util.Set<BlockPos> digSet(Room room)
    {
        List<BlockPos> list = digList(room);
        java.util.Set<BlockPos> set = DIG_SETS.get(room);
        if (set == null || set.size() != list.size())
        {
            set = new java.util.HashSet<>(list);
            DIG_SETS.put(room, set);
        }
        return set;
    }

    /** Would digging this block out be safe and allowed? (never next to water or lava, never unbreakable) */
    public static boolean diggable(ServerLevel level, BlockPos pos)
    {
        BlockState state = level.getBlockState(pos);
        if (state.isAir() || !state.getFluidState().isEmpty() || state.is(ModTags.XENO_UNBREAKABLE) || state.hasBlockEntity()
                || state.getDestroySpeed(level, pos) < 0 || state.getDestroySpeed(level, pos) > 50)
        {
            return false;
        }
        for (Direction d : Direction.values())
        {
            if (!level.getFluidState(pos.relative(d)).isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * The next block of the current room for a worker to dig ("slot" spreads several Drones over
     * the next few blocks). Skips what's already clear; finishes the room when its list runs out;
     * takes over natural caves the tunnel breaks into.
     */
    @Nullable
    public static BlockPos nextBlock(ServerLevel level, Hive hive, int slot)
    {
        Room room = hive.project();
        if (room == null || hive.dug() >= maxDug())
        {
            return null;
        }
        List<BlockPos> list = digList(room);
        int found = 0;
        for (int i = room.cursor(); i < list.size(); i++)
        {
            BlockPos p = list.get(i);
            if (diggable(level, p))
            {
                if (found++ == slot)
                {
                    return p;
                }
            }
            else if (found == 0)
            {
                if (level.getBlockState(p).isAir())
                {
                    claimCave(level, hive, p);
                }
                room.setCursor(i + 1); // nothing to do here (already open, or not safe): move on
            }
        }
        if (found == 0)
        {
            room.setDone(true);
            HiveData.get(level).changed();
        }
        return null;
    }

    /** A worker dug (or gave up on) this block. */
    public static void advance(ServerLevel level, Hive hive, BlockPos pos, boolean dug)
    {
        Room room = hive.project();
        if (room == null)
        {
            return;
        }
        List<BlockPos> list = digList(room);
        if (room.cursor() < list.size() && list.get(room.cursor()).equals(pos))
        {
            room.setCursor(room.cursor() + 1);
        }
        if (dug)
        {
            hive.addDug(1);
        }
        HiveData.get(level).changed();
    }

    /** The tunnel opened into a big natural space: the hive takes it over. */
    private static void claimCave(ServerLevel level, Hive hive, BlockPos at)
    {
        if (hive.roomAt(at).isPresent() || hive.rooms(Room.Type.CAVE).size() >= 3)
        {
            return;
        }
        int air = 0;
        for (BlockPos p : BlockPos.betweenClosed(at.offset(-2, -2, -2), at.offset(2, 2, 2)))
        {
            if (level.getBlockState(p).isAir())
            {
                air++;
            }
        }
        if (air >= 70 && Math.sqrt(at.distSqr(hive.core())) < hive.maxReach(level.getGameTime(), HiveData.maxRadius()) + 8)
        {
            // measure the cave (a bounded flood fill of its air) so a big one is claimed whole, up to 16 blocks out
            java.util.ArrayDeque<BlockPos> queue = new java.util.ArrayDeque<>();
            java.util.Set<BlockPos> seen = new java.util.HashSet<>();
            queue.add(at.immutable());
            seen.add(at.immutable());
            int minY = at.getY(), maxY = at.getY();
            double sumX = 0, sumZ = 0;
            while (!queue.isEmpty() && seen.size() < 6000)
            {
                BlockPos p = queue.poll();
                sumX += p.getX();
                sumZ += p.getZ();
                minY = Math.min(minY, p.getY());
                maxY = Math.max(maxY, p.getY());
                for (Direction d : Direction.values())
                {
                    BlockPos n = p.relative(d);
                    if (Math.abs(n.getX() - at.getX()) <= 16 && Math.abs(n.getZ() - at.getZ()) <= 16 && Math.abs(n.getY() - at.getY()) <= 12
                            && !seen.contains(n) && level.getBlockState(n).isAir())
                    {
                        seen.add(n);
                        queue.add(n);
                    }
                }
            }
            BlockPos centre = new BlockPos((int) Math.round(sumX / seen.size()), minY, (int) Math.round(sumZ / seen.size()));
            int radius = 0;
            for (BlockPos p : seen)
            {
                radius = (int) Math.max(radius, Math.sqrt(Math.pow(p.getX() - centre.getX(), 2) + Math.pow(p.getZ() - centre.getZ(), 2)));
            }
            radius = Math.max(Room.Type.CAVE.radius, Math.min(16, radius));
            int height = Math.max(Room.Type.CAVE.height, Math.min(24, maxY - minY + 1));
            hive.rooms().add(new Room(Room.Type.CAVE, centre, at, radius, height, true));
            HiveData.get(level).changed();
        }
    }

    /**
     * Decides whether the hive needs a new room, and plans it. Called now and then by its members.
     */
    public static void plan(ServerLevel level, Hive hive)
    {
        if (hive.state() != Hive.State.ACTIVE || hive.project() != null || hive.dug() >= maxDug())
        {
            return;
        }
        long now = level.getGameTime();
        if (hive.rooms().isEmpty())
        {
            // a hive from before rooms existed (or founded by a Queen): its core chamber becomes the Queen's
            hive.rooms().add(new Room(Room.Type.QUEEN, hive.core().below(), hive.core().below(), 4, 4, true));
            HiveData.get(level).changed();
            return;
        }
        // the Queen's chamber grows once she's there
        Room queenRoom = hive.rooms(Room.Type.QUEEN).stream().findFirst().orElse(null);
        if (queenRoom != null && hive.hasQueen(now) && queenRoom.radius() < 9)
        {
            queenRoom.enlarge(9, 10);
            HiveData.get(level).changed();
            return;
        }
        int reach = hive.maxReach(now, HiveData.maxRadius());
        List<XenomorphEntity> members = level.getEntitiesOfClass(XenomorphEntity.class, new AABB(hive.core()).inflate(reach + 24),
                x -> hive.id().equals(x.hiveId()));
        long chambers = hive.rooms().stream().filter(r -> r.type() != Room.Type.QUEEN && r.type() != Room.Type.CAVE && r.type().isChamber()).count();
        int maxChambers = hive.hasQueen(now) ? 6 : 2;
        Room.Type next = null;
        if (hive.rooms(Room.Type.HATCHERY).isEmpty())
        {
            next = Room.Type.HATCHERY;
        }
        else if (hive.rooms(Room.Type.LARDER).isEmpty() && members.size() >= 3 && AvpConfig.server(AvpConfig.SERVER.hiveLarder))
        {
            next = Room.Type.LARDER;
        }
        else if (hive.rooms(Room.Type.MIDDEN).isEmpty() && !hive.rooms(Room.Type.LARDER).isEmpty())
        {
            next = Room.Type.MIDDEN;
        }
        else if (hive.rooms(Room.Type.GUARD_POST).isEmpty() && (members.size() >= 6
                || members.stream().anyMatch(m -> m instanceof AdultXenomorphEntity a && a.spec().health() >= 50 && !a.isQueen())))
        {
            next = Room.Type.GUARD_POST;
        }
        else if (full(level, hive, Room.Type.HATCHERY))
        {
            next = Room.Type.HATCHERY;
        }
        else if (full(level, hive, Room.Type.LARDER))
        {
            next = Room.Type.LARDER;
        }
        else if (hive.rooms(Room.Type.CRAWLWAY).size() < Math.min(3, chambers) && members.size() >= 4)
        {
            next = Room.Type.CRAWLWAY;
        }
        if (next == null || next.isChamber() && chambers >= maxChambers)
        {
            return;
        }
        Room room = site(level, hive, next, reach);
        if (room != null)
        {
            hive.rooms().add(room);
            HiveData.get(level).changed();
        }
    }

    /** Are all rooms of this kind full? (hatcheries: cocoons and eggs; larders: cocooned food) */
    private static boolean full(ServerLevel level, Hive hive, Room.Type type)
    {
        List<Room> rooms = hive.rooms(type);
        if (rooms.isEmpty() || rooms.stream().anyMatch(r -> !r.done()))
        {
            return false;
        }
        int capacity = 0, used = 0;
        for (Room room : rooms)
        {
            capacity += room.radius() * (type == Room.Type.HATCHERY ? 3 : 2);
            used += level.getEntitiesOfClass(LivingEntity.class, new AABB(room.centre()).inflate(room.radius() + 1, room.height(), room.radius() + 1),
                    e -> e.hasData(ModAttachments.COCOONED) || type == Room.Type.HATCHERY && e instanceof OvamorphEntity).size();
        }
        return used >= capacity;
    }

    /** Somewhere for a new room: within reach, clear of the other rooms, away from water and lava. */
    @Nullable
    private static Room site(ServerLevel level, Hive hive, Room.Type type, int reach)
    {
        RandomSource random = level.random;
        BlockPos core = hive.core();
        List<Room> chambers = hive.rooms().stream().filter(r -> r.type().isChamber()).toList();
        for (int tries = 0; tries < 24; tries++)
        {
            float angle = random.nextFloat() * Mth.TWO_PI;
            if (type == Room.Type.GUARD_POST)
            {
                // between the core and the way in
                BlockPos in = hive.entrance();
                angle = (float) Math.atan2(in.getZ() - core.getZ(), in.getX() - core.getX()) + (random.nextFloat() - 0.5F) * 0.8F;
            }
            Room start;
            BlockPos centre;
            int radius = type.radius, height = type.height;
            if (type == Room.Type.CRAWLWAY)
            {
                start = chambers.get(random.nextInt(chambers.size()));
                int length = 8 + random.nextInt(6);
                centre = start.centre().offset((int) (Mth.cos(angle) * (start.radius() + length)), random.nextInt(3) - 1,
                        (int) (Mth.sin(angle) * (start.radius() + length)));
            }
            else
            {
                int min = Math.min(reach - radius - 1, 6 + radius + random.nextInt(4));
                int max = reach - radius - 1;
                if (max < min || max < 6)
                {
                    return null;
                }
                int distance = min + random.nextInt(max - min + 1);
                centre = core.offset((int) (Mth.cos(angle) * distance), random.nextInt(5) - 2, (int) (Mth.sin(angle) * distance));
                BlockPos c = centre;
                start = chambers.stream().filter(Room::done).min(Comparator.comparingDouble(r -> r.centre().distSqr(c))).orElse(null);
                if (start == null)
                {
                    continue;
                }
            }
            BlockPos c = centre;
            int r = radius;
            if (Math.sqrt(centre.distSqr(core)) > reach + 2
                    || hive.rooms().stream().anyMatch(o -> o != start && o.type() != Room.Type.CAVE && Math.sqrt(o.centre().distSqr(c)) < o.radius() + r + 3)
                    || !safe(level, centre, Math.max(2, radius - 1), Math.max(2, height - 1)))
            {
                continue;
            }
            return new Room(type, centre, start.centre(), radius, height, false);
        }
        return null;
    }

    /** No water or lava (or unbreakable blocks) in the space a room would take. */
    private static boolean safe(ServerLevel level, BlockPos centre, int radius, int height)
    {
        // the room's own space (blocks next to water or lava are skipped while digging anyway)
        for (BlockPos p : BlockPos.betweenClosed(centre.offset(-radius, 0, -radius), centre.offset(radius, height, radius)))
        {
            if ((p.getX() + p.getY() + p.getZ()) % 2 != 0)
            {
                continue; // a checkerboard sample is plenty
            }
            BlockState s = level.getBlockState(p);
            if (!s.getFluidState().isEmpty() || s.is(ModTags.XENO_UNBREAKABLE) || s.getDestroySpeed(level, p) < 0)
            {
                return false;
            }
        }
        return level.isInWorldBounds(centre.above(height + 1)) && centre.getY() > level.getMinBuildHeight() + 3;
    }

    /** A random open floor spot in a room (for a cocoon, an egg, remains...), next to a wall if it can. */
    @Nullable
    public static BlockPos floorSpot(ServerLevel level, Room room, boolean againstWall)
    {
        RandomSource random = level.random;
        int r = Math.max(1, room.radius());
        for (int tries = 0; tries < 40; tries++)
        {
            BlockPos p = room.centre().offset(random.nextInt(r * 2 + 1) - r, 0, random.nextInt(r * 2 + 1) - r);
            for (int dy = -1; dy <= 2; dy++)
            {
                BlockPos q = p.above(dy);
                if (level.getBlockState(q).isAir() && level.getBlockState(q.above()).isAir() && level.getBlockState(q.below()).isSolid())
                {
                    boolean wall = false;
                    for (Direction d : Direction.Plane.HORIZONTAL)
                    {
                        wall |= level.getBlockState(q.relative(d)).isSolid();
                    }
                    if (wall || !againstWall || tries > 30)
                    {
                        return q;
                    }
                }
            }
        }
        return null;
    }

    private HivePlanner()
    {
    }
}
