package org.avp.pneumatic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Finds stations, compressors and routes on a pneumatic tube network (breadth-first, up to 4096 tubes). */
public final class TubeNetwork
{
    public static final int MAX = 4096;

    public record Survey(Map<String, BlockPos> stations, int compressors, int pressurised)
    {
    }

    private TubeNetwork()
    {
    }

    /** Everything reachable from a station through tubes. */
    public static Survey survey(Level level, BlockPos from)
    {
        Map<String, BlockPos> stations = new java.util.TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        int compressors = 0, pressurised = 0;
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        java.util.Set<BlockPos> seen = new java.util.HashSet<>();
        queue.add(from);
        seen.add(from);
        while (!queue.isEmpty() && seen.size() < MAX)
        {
            BlockPos at = queue.poll();
            for (Direction d : Direction.values())
            {
                BlockPos next = at.relative(d);
                if (!seen.add(next) || !level.isLoaded(next))
                {
                    continue;
                }
                BlockState s = level.getBlockState(next);
                if (s.getBlock() instanceof TubeBlock)
                {
                    queue.add(next);
                }
                else if (s.getBlock() instanceof PneumaticStationBlock && level.getBlockEntity(next) instanceof PneumaticStationBlockEntity st)
                {
                    stations.putIfAbsent(st.name(), next);
                }
                else if (s.getBlock() instanceof CompressorBlock && level.getBlockEntity(next) instanceof CompressorBlockEntity c)
                {
                    compressors++;
                    if (c.pressurised())
                    {
                        pressurised++;
                    }
                }
            }
            if (at != from && !(level.getBlockState(at).getBlock() instanceof TubeBlock))
            {
                continue;
            }
        }
        return new Survey(stations, compressors, pressurised);
    }

    /** Tube route from one station to another (the positions between them, then the destination), or null. */
    @Nullable
    public static List<BlockPos> route(Level level, BlockPos from, BlockPos to)
    {
        Map<BlockPos, BlockPos> parent = new HashMap<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        queue.add(from);
        parent.put(from, from);
        while (!queue.isEmpty() && parent.size() < MAX)
        {
            BlockPos at = queue.poll();
            for (Direction d : Direction.values())
            {
                BlockPos next = at.relative(d);
                if (parent.containsKey(next) || !level.isLoaded(next))
                {
                    continue;
                }
                if (next.equals(to))
                {
                    parent.put(next, at);
                    List<BlockPos> path = new ArrayList<>();
                    for (BlockPos p = next; !p.equals(from); p = parent.get(p))
                    {
                        path.add(p);
                    }
                    Collections.reverse(path);
                    return path;
                }
                if (level.getBlockState(next).getBlock() instanceof TubeBlock)
                {
                    parent.put(next, at);
                    queue.add(next);
                }
            }
        }
        return null;
    }
}
