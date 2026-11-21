package org.avp.world.hive;

import java.util.UUID;

import javax.annotation.Nullable;

import org.avp.block.entity.ResinBlockEntity;
import org.avp.registry.ModBlocks;
import org.avp.registry.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * One Xenomorph hive (1.12.2 XenomorphHive): where its core is, how it got there and how big it
 * has grown. Kept in HiveData, saved with the world.
 */
public class Hive
{
    public enum State
    {
        /** A Drone is still digging down to where the hive will be. */
        FOUNDING,
        /** Built and growing. */
        ACTIVE
    }

    private final UUID id;
    private BlockPos core;
    /** Top of the tunnel down to the hive (where it was founded). */
    private BlockPos entrance;
    private State state;
    private final HiveWork work = new HiveWork();
    private int resin;
    private boolean coreBlockPlaced;
    private long nextCorePlacement;
    @Nullable
    private UUID queen;
    /** Game time a Queen was last seen alive at this hive. */
    private long queenSeen = -100_000;
    /** Game time anything in the hive was last alive (empty hives are forgotten after a while). */
    private long lastActive;
    /** Where the founding Drone is digging, and which way. */
    @Nullable
    private BlockPos digPos;
    private Direction digDir = Direction.NORTH;
    private boolean verticalShaft;
    /** The rooms: the first is the Queen's chamber (the founding dome). */
    private final java.util.List<Room> rooms = new java.util.ArrayList<>();
    /** Blocks dug out so far (capped by "Hive Max Dug Blocks"). */
    private int dug;
    /** The Drone digging the founding tunnel. */
    @Nullable
    private UUID digger;
    /** Special jobs held by Drones: Drone -> (job, game time last seen). */
    private final java.util.Map<UUID, String> jobs = new java.util.HashMap<>();
    private final java.util.Map<UUID, Long> jobSeen = new java.util.HashMap<>();

    /**
     * The job of this Drone ("BUILDER", "COATER" or "WORKER"), handing out the special jobs until
     * each has its quota; a job whose holder hasn't been seen for 20 seconds (dead, or far away)
     * goes to the next Drone that asks.
     */
    public String job(UUID drone, long now, int builders, int coaters)
    {
        java.util.Iterator<java.util.Map.Entry<UUID, Long>> it = jobSeen.entrySet().iterator();
        while (it.hasNext())
        {
            java.util.Map.Entry<UUID, Long> e = it.next();
            if (now - e.getValue() > 400 && !e.getKey().equals(drone))
            {
                jobs.remove(e.getKey()); // gone: its job is free
                it.remove();
            }
        }
        jobSeen.put(drone, now);
        String job = jobs.get(drone);
        if (job == null)
        {
            long b = jobs.values().stream().filter("BUILDER"::equals).count();
            long c = jobs.values().stream().filter("COATER"::equals).count();
            job = b < builders ? "BUILDER" : c < coaters ? "COATER" : "WORKER";
            jobs.put(drone, job);
        }
        return job;
    }

    @Nullable
    public String jobOf(UUID drone)
    {
        return jobs.get(drone);
    }

    /** Water or lava getting into the hive, waiting to be sealed (not saved: they're found again). */
    public final java.util.Set<BlockPos> leaks = new java.util.LinkedHashSet<>();

    /** Is this a leak: liquid (not a waterlogged block) next to open space? */
    public static boolean isLeak(net.minecraft.world.level.Level level, BlockPos pos)
    {
        if (!(level.getBlockState(pos).getBlock() instanceof net.minecraft.world.level.block.LiquidBlock))
        {
            return false;
        }
        for (net.minecraft.core.Direction d : net.minecraft.core.Direction.values())
        {
            if (level.getBlockState(pos.relative(d)).isAir())
            {
                return true;
            }
        }
        return false;
    }

    /** Note any leaks next to this spot. */
    public void checkForLeaks(net.minecraft.world.level.Level level, BlockPos around, int radius)
    {
        for (BlockPos p : BlockPos.betweenClosed(around.offset(-radius, -radius, -radius), around.offset(radius, radius, radius)))
        {
            if (leaks.size() < 64 && !level.getFluidState(p).isEmpty() && isLeak(level, p))
            {
                leaks.add(p.immutable());
            }
        }
    }

    /** What the hive is short of (worked out now and then by its members; not saved). */
    public boolean needsHosts, needsFood;

    /** Royal succession: the member being fed royal jelly to become the next Queen. */
    @Nullable
    private UUID heir;

    public Hive(UUID id, BlockPos core, BlockPos entrance, State state, long now)
    {
        this.id = id;
        this.core = core;
        this.entrance = entrance;
        this.state = state;
        this.lastActive = now;
    }

    public UUID id()
    {
        return id;
    }

    public BlockPos core()
    {
        return core;
    }

    public void setCore(BlockPos core)
    {
        this.core = core;
    }

    public BlockPos entrance()
    {
        return entrance;
    }

    public State state()
    {
        return state;
    }

    public HiveWork work()
    {
        return work;
    }

    @Nullable
    private HiveBlueprint blueprint;
    private long nextForage;

    @Nullable
    public HiveBlueprint blueprint()
    {
        return blueprint;
    }

    public void setBlueprint(HiveBlueprint blueprint)
    {
        this.blueprint = blueprint;
    }

    public boolean canSendForager(long now)
    {
        return now >= nextForage;
    }

    public void sentForager(long now)
    {
        nextForage = now + 140;
    }

    public void setState(State state)
    {
        this.state = state;
    }

    /** Installs the visible core in the floor once, including for hives loaded from older saves. */
    public void placeCoreBlock(ServerLevel level)
    {
        long now = level.getGameTime();
        if (coreBlockPlaced || state != State.ACTIVE || now < nextCorePlacement)
        {
            return;
        }
        nextCorePlacement = now + 200;

        BlockPos floor = rooms.stream().filter(room -> room.type() == Room.Type.QUEEN && room.done())
                .findFirst().map(room -> room.centre().below()).orElseGet(core::below);
        if (!level.isInWorldBounds(floor) || !level.isLoaded(floor) || !level.getBlockState(floor.above()).isAir())
        {
            return;
        }
        BlockState marker = ModBlocks.HIVE_CORE.get().defaultBlockState();
        BlockState current = level.getBlockState(floor);
        if (!current.is(marker.getBlock()) && (!isCoreFloor(level, floor, current) || !level.setBlock(floor, marker, Block.UPDATE_ALL)))
        {
            return;
        }
        coreBlockPlaced = true;
        HiveData.get(level).changed();
    }

    static boolean isCoreFloor(ServerLevel level, BlockPos pos, BlockState state)
    {
        // A natural resin coating is safe to replace only when the block it remembers was natural terrain.
        if (state.is(ModBlocks.NATURAL_RESIN.get()) && level.getBlockEntity(pos) instanceof ResinBlockEntity resin)
        {
            state = resin.covered();
        }
        return state != null && !state.hasBlockEntity() && state.getFluidState().isEmpty() && !state.is(ModTags.XENO_UNBREAKABLE)
                && (state.is(ModTags.RESIN_COVERABLE) || state.is(ModBlocks.HIVERESIN_STANDARD.get()));
    }

    public int resin()
    {
        return resin;
    }

    public void addResin(int amount)
    {
        resin = Math.max(0, resin + amount);
    }

    @Nullable
    public UUID queen()
    {
        return queen;
    }

    public void setQueen(@Nullable UUID queen, long now)
    {
        this.queen = queen;
        if (queen != null)
        {
            queenSeen = now;
        }
    }

    /** Has a living Queen (seen in the last 10 seconds)? */
    public boolean hasQueen(long now)
    {
        return queen != null && now - queenSeen < 200;
    }

    public void touch(long now)
    {
        lastActive = now;
    }

    public long lastActive()
    {
        return lastActive;
    }

    @Nullable
    public BlockPos digPos()
    {
        return digPos;
    }

    public Direction digDir()
    {
        return digDir;
    }

    public void setDig(@Nullable BlockPos pos, Direction dir)
    {
        this.digPos = pos;
        this.digDir = dir;
    }

    public boolean hasVerticalShaft()
    {
        return verticalShaft;
    }

    public void beginShaft()
    {
        verticalShaft = true;
        digPos = entrance;
    }

    /** How far its rooms may reach from the core: the full radius with a Queen, a small nest without one. */
    public int maxReach(long now, int maxRadius)
    {
        return hasQueen(now) ? maxRadius : Math.min(maxRadius, 16);
    }

    /** How far from the core the hive reaches now (its rooms, at least 8). */
    public int buildRadius(long now, int maxRadius)
    {
        double reach = 8;
        for (Room room : rooms)
        {
            reach = Math.max(reach, Math.sqrt(room.centre().distSqr(core)) + room.radius() + 1);
        }
        return (int) Math.min(reach, Math.max(8, maxReach(now, maxRadius)));
    }

    @Nullable
    public UUID heir()
    {
        return heir;
    }

    public void setHeir(@Nullable UUID heir)
    {
        this.heir = heir;
    }

    public java.util.List<Room> rooms()
    {
        return rooms;
    }

    /** The first room (in order) that still needs digging, if any. */
    @Nullable
    public Room project()
    {
        return rooms.stream().filter(r -> !r.done()).findFirst().orElse(null);
    }

    public java.util.Optional<Room> roomAt(BlockPos pos)
    {
        return rooms.stream().filter(r -> r.type().isChamber() && r.contains(pos)).findFirst();
    }

    public java.util.List<Room> rooms(Room.Type type)
    {
        return rooms.stream().filter(r -> r.type() == type).toList();
    }

    public int dug()
    {
        return dug;
    }

    public void addDug(int amount)
    {
        dug += amount;
    }

    @Nullable
    public UUID digger()
    {
        return digger;
    }

    public void setDigger(@Nullable UUID digger)
    {
        this.digger = digger;
    }

    public CompoundTag save()
    {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("Id", id);
        tag.put("Core", NbtUtils.writeBlockPos(core));
        tag.put("Entrance", NbtUtils.writeBlockPos(entrance));
        tag.putString("State", state.name());
        tag.putInt("Resin", resin);
        tag.putBoolean("VerticalShaft", verticalShaft);
        tag.putBoolean("CoreBlockPlaced", coreBlockPlaced);
        if (queen != null)
        {
            tag.putUUID("Queen", queen);
        }
        tag.putLong("LastActive", lastActive);
        if (digPos != null)
        {
            tag.put("Dig", NbtUtils.writeBlockPos(digPos));
            tag.putInt("DigDir", digDir.get2DDataValue());
        }
        if (heir != null)
        {
            tag.putUUID("Heir", heir);
        }
        if (digger != null)
        {
            tag.putUUID("Digger", digger);
        }
        tag.putInt("Dug", dug);
        net.minecraft.nbt.ListTag list = new net.minecraft.nbt.ListTag();
        rooms.forEach(r -> list.add(r.save()));
        tag.put("Rooms", list);
        if (blueprint != null)
        {
            tag.put("Blueprint", blueprint.save());
        }
        return tag;
    }

    public static Hive load(CompoundTag tag)
    {
        Hive hive = new Hive(tag.getUUID("Id"), NbtUtils.readBlockPos(tag, "Core").orElse(BlockPos.ZERO),
                NbtUtils.readBlockPos(tag, "Entrance").orElse(BlockPos.ZERO), State.valueOf(tag.getString("State")), tag.getLong("LastActive"));
        hive.resin = tag.getInt("Resin");
        hive.verticalShaft = tag.getBoolean("VerticalShaft");
        hive.coreBlockPlaced = tag.getBoolean("CoreBlockPlaced");
        hive.queen = tag.hasUUID("Queen") ? tag.getUUID("Queen") : null;
        hive.digPos = NbtUtils.readBlockPos(tag, "Dig").orElse(null);
        hive.digDir = Direction.from2DDataValue(tag.getInt("DigDir"));
        hive.heir = tag.hasUUID("Heir") ? tag.getUUID("Heir") : null;
        hive.digger = tag.hasUUID("Digger") ? tag.getUUID("Digger") : null;
        hive.dug = tag.getInt("Dug");
        if (tag.contains("Blueprint"))
        {
            hive.blueprint = HiveBlueprint.load(tag.getCompound("Blueprint"));
        }
        for (net.minecraft.nbt.Tag t : tag.getList("Rooms", net.minecraft.nbt.Tag.TAG_COMPOUND))
        {
            hive.rooms.add(Room.load((CompoundTag) t));
        }
        return hive;
    }
}
