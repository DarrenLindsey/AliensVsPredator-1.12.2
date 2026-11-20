package org.avp.entity.joe;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

/**
 * The Seegson network: every loaded Working Joe, grouped by owner. Joes on one network share alarms
 * (one sees a threat, the others hear about it) and report to their owner.
 */
public final class JoeNetwork
{
    private static final Map<UUID, Set<WorkingJoeEntity>> BY_OWNER = new HashMap<>();
    private static final Map<UUID, Alarm> ALARMS = new HashMap<>();

    private record Alarm(BlockPos pos, long until, long seq)
    {
    }

    private JoeNetwork()
    {
    }

    static synchronized void join(WorkingJoeEntity joe)
    {
        if (joe.owner() != null && !joe.isRemoved())
        {
            BY_OWNER.computeIfAbsent(joe.owner(), o -> Collections.newSetFromMap(new WeakHashMap<>())).add(joe);
        }
    }

    static synchronized void leave(WorkingJoeEntity joe)
    {
        for (Set<WorkingJoeEntity> joes : BY_OWNER.values())
        {
            joes.remove(joe);
        }
    }

    /** The owner's loaded Joes. */
    public static synchronized List<WorkingJoeEntity> of(UUID owner)
    {
        List<WorkingJoeEntity> list = new ArrayList<>();
        for (WorkingJoeEntity joe : BY_OWNER.getOrDefault(owner, Set.of()))
        {
            if (joe.isAlive() && !joe.isRemoved())
            {
                list.add(joe);
            }
        }
        list.sort((a, b) -> a.joeName().compareToIgnoreCase(b.joeName()));
        return list;
    }

    static synchronized void raiseAlarm(WorkingJoeEntity by, BlockPos at, String what)
    {
        UUID owner = by.owner();
        if (owner == null)
        {
            return;
        }
        long now = by.level().getGameTime();
        Alarm old = ALARMS.get(owner);
        if (old != null && now < old.until() - 400 && old.pos().closerThan(at, 16))
        {
            return; // already raised for this
        }
        ALARMS.put(owner, new Alarm(at.immutable(), now + 600, old == null ? 1 : old.seq() + 1));
        by.report("ALERT: " + what + " detected at " + WorkingJoeEntity.where(at) + ".");
    }

    /** Where the network's current alarm is (null if none). */
    @Nullable
    static synchronized BlockPos alarmPos(@Nullable UUID owner)
    {
        Alarm alarm = owner == null ? null : ALARMS.get(owner);
        if (alarm == null)
        {
            return null;
        }
        for (WorkingJoeEntity joe : BY_OWNER.getOrDefault(owner, Set.of()))
        {
            return joe.level().getGameTime() < alarm.until() ? alarm.pos() : null;
        }
        return null;
    }

    static synchronized long alarmSeq(@Nullable UUID owner)
    {
        Alarm alarm = owner == null ? null : ALARMS.get(owner);
        return alarm == null ? 0 : alarm.seq();
    }

    public static synchronized void clearAlarm(UUID owner)
    {
        ALARMS.remove(owner);
    }

    /** Reads data/NAMESPACE/joe_programs/PATH.joe from the server's data packs. */
    @Nullable
    public static String readDatapackProgram(ServerLevel level, ResourceLocation id)
    {
        ResourceLocation file = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "joe_programs/" + id.getPath() + ".joe");
        var resource = level.getServer().getResourceManager().getResource(file);
        if (resource.isEmpty())
        {
            return null;
        }
        try (BufferedReader reader = resource.get().openAsReader())
        {
            StringBuilder text = new StringBuilder();
            reader.lines().forEach(line -> text.append(line).append('\n'));
            return text.toString();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /** The data pack programs there are (for command suggestions). */
    public static List<ResourceLocation> datapackPrograms(ServerLevel level)
    {
        List<ResourceLocation> ids = new ArrayList<>();
        level.getServer().getResourceManager().listResources("joe_programs", f -> f.getPath().endsWith(".joe")).keySet().forEach(f ->
        {
            String path = f.getPath().substring("joe_programs/".length(), f.getPath().length() - 4);
            ids.add(ResourceLocation.fromNamespaceAndPath(f.getNamespace(), path));
        });
        Collections.sort(ids);
        return ids;
    }
}
