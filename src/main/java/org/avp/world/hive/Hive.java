package org.avp.world.hive;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

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
    private int resin;
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

    public void setState(State state)
    {
        this.state = state;
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
        return tag;
    }

    public static Hive load(CompoundTag tag)
    {
        Hive hive = new Hive(tag.getUUID("Id"), NbtUtils.readBlockPos(tag, "Core").orElse(BlockPos.ZERO),
                NbtUtils.readBlockPos(tag, "Entrance").orElse(BlockPos.ZERO), State.valueOf(tag.getString("State")), tag.getLong("LastActive"));
        hive.resin = tag.getInt("Resin");
        hive.queen = tag.hasUUID("Queen") ? tag.getUUID("Queen") : null;
        hive.digPos = NbtUtils.readBlockPos(tag, "Dig").orElse(null);
        hive.digDir = Direction.from2DDataValue(tag.getInt("DigDir"));
        hive.heir = tag.hasUUID("Heir") ? tag.getUUID("Heir") : null;
        hive.digger = tag.hasUUID("Digger") ? tag.getUUID("Digger") : null;
        hive.dug = tag.getInt("Dug");
        for (net.minecraft.nbt.Tag t : tag.getList("Rooms", net.minecraft.nbt.Tag.TAG_COMPOUND))
        {
            hive.rooms.add(Room.load((CompoundTag) t));
        }
        return hive;
    }
}
