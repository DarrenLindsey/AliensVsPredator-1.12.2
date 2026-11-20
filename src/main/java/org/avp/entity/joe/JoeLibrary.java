package org.avp.entity.joe;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * The program library each player's Seegson Network Server keeps (saved with the world), and which
 * servers are online. With a server online, a player's terminals and tablet reach every Joe they
 * own, at any distance and in any dimension.
 */
public final class JoeLibrary extends SavedData
{
    public static final int MAX_FILES = 64, MAX_SIZE = 16_000;
    private final Map<UUID, TreeMap<String, String>> files = new HashMap<>();
    /** Owner -> game time a server of theirs last ran with power (not saved: servers tick to stay online). */
    private static final Map<UUID, Long> ONLINE = new HashMap<>();

    public static JoeLibrary get(MinecraftServer server)
    {
        return server.overworld().getDataStorage().computeIfAbsent(new Factory<>(JoeLibrary::new, JoeLibrary::load, null), "avp_joe_library");
    }

    public TreeMap<String, String> files(UUID owner)
    {
        return files.computeIfAbsent(owner, o -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER));
    }

    /** Saves a program; returns an error, or null. */
    public String save(UUID owner, String name, String source)
    {
        name = name.trim();
        if (!name.matches("[A-Za-z0-9_\\-. ]{1,32}"))
        {
            return "File names: 1-32 letters, digits, spaces, - _ .";
        }
        if (source.length() > MAX_SIZE)
        {
            return "Program too long (" + MAX_SIZE + " characters at most).";
        }
        TreeMap<String, String> mine = files(owner);
        if (!mine.containsKey(name) && mine.size() >= MAX_FILES)
        {
            return "Library full (" + MAX_FILES + " files).";
        }
        mine.put(name, source);
        setDirty();
        return null;
    }

    public void delete(UUID owner, String name)
    {
        if (files(owner).remove(name) != null)
        {
            setDirty();
        }
    }

    public static synchronized void markOnline(UUID owner, long time)
    {
        ONLINE.put(owner, time);
    }

    public static synchronized boolean serverOnline(UUID owner, long time)
    {
        Long last = ONLINE.get(owner);
        return last != null && time - last < 40;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries)
    {
        files.forEach((owner, map) ->
        {
            CompoundTag mine = new CompoundTag();
            map.forEach(mine::putString);
            tag.put(owner.toString(), mine);
        });
        return tag;
    }

    private static JoeLibrary load(CompoundTag tag, HolderLookup.Provider registries)
    {
        JoeLibrary lib = new JoeLibrary();
        for (String key : tag.getAllKeys())
        {
            try
            {
                UUID owner = UUID.fromString(key);
                CompoundTag mine = tag.getCompound(key);
                TreeMap<String, String> map = lib.files(owner);
                for (String name : mine.getAllKeys())
                {
                    map.put(name, mine.getString(name));
                }
            }
            catch (IllegalArgumentException ignored)
            {
            }
        }
        return lib;
    }
}
