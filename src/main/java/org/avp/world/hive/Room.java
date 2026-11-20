package org.avp.world.hive;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;

/**
 * One room of a hive. Each is dug (by the hive's Drones) as a tunnel from another room followed
 * by the chamber itself, then coated in resin.
 */
public class Room
{
    public enum Type
    {
        /** The founding dome; enlarged once a Queen arrives. */
        QUEEN(7, 8, 3, 3),
        /** Cocooned hosts and eggs (the hatchery of Aliens). */
        HATCHERY(6, 5, 2, 3),
        /** Cocooned prey kept as food. */
        LARDER(5, 4, 2, 3),
        /** The remains of what was eaten. */
        MIDDEN(4, 4, 2, 3),
        /** Near the entrance, where the Warriors and Praetorians gather. */
        GUARD_POST(5, 5, 2, 3),
        /** A narrow ambush tunnel (1 high: they crawl). */
        CRAWLWAY(0, 0, 1, 1),
        /** A natural cave the tunnels broke into, taken over by the hive. */
        CAVE(7, 7, 2, 3);

        public final int radius, height, tunnelWidth, tunnelHeight;

        Type(int radius, int height, int tunnelWidth, int tunnelHeight)
        {
            this.radius = radius;
            this.height = height;
            this.tunnelWidth = tunnelWidth;
            this.tunnelHeight = tunnelHeight;
        }

        public boolean isChamber()
        {
            return this != CRAWLWAY;
        }
    }

    private final Type type;
    /** Middle of the floor. */
    private final BlockPos centre;
    /** Where its tunnel starts (the floor of the room it branches from). */
    private final BlockPos from;
    private int radius, height;
    private boolean done;
    /** How far through its dig list the Drones are. */
    private int cursor;
    private int pillars;

    public Room(Type type, BlockPos centre, BlockPos from, int radius, int height, boolean done)
    {
        this.type = type;
        this.centre = centre;
        this.from = from;
        this.radius = radius;
        this.height = height;
        this.done = done;
    }

    public Type type()
    {
        return type;
    }

    public BlockPos centre()
    {
        return centre;
    }

    public BlockPos from()
    {
        return from;
    }

    public int radius()
    {
        return radius;
    }

    public int height()
    {
        return height;
    }

    /** Make the room bigger (the Queen's chamber, once she's there): dug again with the new size. */
    public void enlarge(int radius, int height)
    {
        this.radius = radius;
        this.height = height;
        this.done = false;
        this.cursor = 0;
    }

    public boolean done()
    {
        return done;
    }

    public void setDone(boolean done)
    {
        this.done = done;
    }

    public int cursor()
    {
        return cursor;
    }

    public void setCursor(int cursor)
    {
        this.cursor = cursor;
    }

    public int pillars()
    {
        return pillars;
    }

    public void addPillar()
    {
        pillars++;
    }

    /** Is this point inside the room (or its doorway)? */
    public boolean contains(BlockPos pos)
    {
        double dx = pos.getX() - centre.getX(), dz = pos.getZ() - centre.getZ();
        int dy = pos.getY() - centre.getY();
        double r = Math.max(1.5, radius + 0.5);
        return dx * dx + dz * dz <= r * r && dy >= -1 && dy <= Math.max(2, height);
    }

    public CompoundTag save()
    {
        CompoundTag tag = new CompoundTag();
        tag.putString("Type", type.name());
        tag.put("Centre", NbtUtils.writeBlockPos(centre));
        tag.put("From", NbtUtils.writeBlockPos(from));
        tag.putInt("Radius", radius);
        tag.putInt("Height", height);
        tag.putBoolean("Done", done);
        tag.putInt("Cursor", cursor);
        tag.putInt("Pillars", pillars);
        return tag;
    }

    public static Room load(CompoundTag tag)
    {
        Room room = new Room(Type.valueOf(tag.getString("Type")), NbtUtils.readBlockPos(tag, "Centre").orElse(BlockPos.ZERO),
                NbtUtils.readBlockPos(tag, "From").orElse(BlockPos.ZERO), tag.getInt("Radius"), tag.getInt("Height"), tag.getBoolean("Done"));
        room.cursor = tag.getInt("Cursor");
        room.pillars = tag.getInt("Pillars");
        return room;
    }
}
