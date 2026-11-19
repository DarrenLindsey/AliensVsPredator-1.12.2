package org.avp.power;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import javax.annotation.Nullable;

import org.avp.block.PowerMachineBlock;
import org.avp.block.PowerlineBlock;
import org.avp.block.RedstoneSensorBlock;
import org.avp.block.entity.PowerMachineBlockEntity;
import org.avp.config.AvpConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

/**
 * How FE moves through powerlines.
 *
 * Powerlines (and switched-on redstone sensors) are "conductors": they store nothing.
 * When anything pushes energy into a powerline - one of our generators, or a generator
 * or cable from another mod - the whole connected run of powerlines is looked up and the
 * energy is shared out evenly between every block touching it that accepts FE (lights,
 * machines, power cells, other mods' machines...). So power arrives instantly and nothing
 * is lost along the way.
 *
 * The list of connected blocks is cached and thrown away whenever a powerline or a block
 * next to one changes, so big networks don't have to be searched every tick.
 *
 * Power cells never feed other power cells (otherwise two cells on one network would pass
 * the same energy back and forth forever).
 */
public final class PowerNetwork
{
    /** One connected run of powerlines and the blocks it can feed. */
    public record Network(Set<BlockPos> conductors, List<Target> targets, boolean tooBig)
    {
    }

    /** A block next to the network and the side of it that touches the powerline. */
    public record Target(BlockPos pos, Direction side)
    {
    }

    private static final class LevelCache
    {
        final Map<BlockPos, Network> byConductor = new HashMap<>();
    }

    private static final Map<Level, LevelCache> CACHE = new WeakHashMap<>();

    /** True while a power cell is pushing, so the network skips other power cells. */
    private static boolean pushingFromStorage;

    private PowerNetwork()
    {
    }

    public static boolean isConductor(BlockState state)
    {
        return state.getBlock() instanceof PowerlineBlock
                || state.getBlock() instanceof RedstoneSensorBlock && state.getValue(RedstoneSensorBlock.POWERED);
    }

    /** Something next to a powerline changed: forget the cached networks of this world. */
    public static void invalidate(Level level)
    {
        synchronized (CACHE)
        {
            CACHE.remove(level);
        }
    }

    /** Finds (or reuses) the network a conductor belongs to. */
    public static Network network(Level level, BlockPos start)
    {
        LevelCache cache;
        synchronized (CACHE)
        {
            cache = CACHE.computeIfAbsent(level, l -> new LevelCache());
        }
        Network known = cache.byConductor.get(start);
        if (known != null)
        {
            return known;
        }
        int limit = AvpConfig.server(AvpConfig.SERVER.maxCableNetworkSize);
        Set<BlockPos> conductors = new HashSet<>();
        Map<BlockPos, Target> targets = new LinkedHashMap<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        queue.add(start.immutable());
        conductors.add(start.immutable());
        boolean tooBig = false;
        while (!queue.isEmpty())
        {
            BlockPos pos = queue.poll();
            for (Direction dir : Direction.values())
            {
                BlockPos next = pos.relative(dir);
                if (conductors.contains(next) || !level.isLoaded(next))
                {
                    continue;
                }
                if (isConductor(level.getBlockState(next)))
                {
                    if (conductors.size() >= limit)
                    {
                        tooBig = true;
                        continue;
                    }
                    conductors.add(next);
                    queue.add(next);
                }
                else if (!targets.containsKey(next)
                        && level.getCapability(Capabilities.EnergyStorage.BLOCK, next, dir.getOpposite()) != null)
                {
                    targets.put(next, new Target(next, dir.getOpposite()));
                }
            }
        }
        Network network = new Network(conductors, new ArrayList<>(targets.values()), tooBig);
        for (BlockPos pos : conductors)
        {
            cache.byConductor.put(pos, network);
        }
        return network;
    }

    /**
     * Energy entering a powerline.
     *
     * @param from the block pushing the energy in (gets none of it back), or null
     * @return how much was accepted
     */
    public static int insert(Level level, BlockPos conductor, @Nullable BlockPos from, int amount, boolean simulate)
    {
        if (amount <= 0)
        {
            return 0;
        }
        Network network = network(level, conductor);
        if (network.tooBig())
        {
            return 0;
        }
        List<IEnergyStorage> receivers = new ArrayList<>();
        for (Target target : network.targets())
        {
            if (target.pos().equals(from) || pushingFromStorage && isPowercell(level, target.pos()))
            {
                continue;
            }
            IEnergyStorage storage = level.getCapability(Capabilities.EnergyStorage.BLOCK, target.pos(), target.side());
            if (storage != null && storage.canReceive())
            {
                receivers.add(storage);
            }
        }
        // share evenly; whatever one block can't take is offered to the ones after it
        int remaining = amount;
        for (int i = 0; i < receivers.size() && remaining > 0; i++)
        {
            int share = (remaining + (receivers.size() - i) - 1) / (receivers.size() - i);
            remaining -= receivers.get(i).receiveEnergy(share, simulate);
        }
        return amount - remaining;
    }

    private static boolean isPowercell(Level level, BlockPos pos)
    {
        return level.getBlockEntity(pos) instanceof PowerMachineBlockEntity machine && machine.kind() == PowerMachineBlock.Kind.POWERCELL;
    }

    /**
     * Pushes energy from a machine's buffer into its neighbours (powerlines, machines,
     * other mods' blocks).
     *
     * @param sides       which sides may give power (null = all)
     * @param fromStorage true for power cells (they never feed other power cells)
     * @return how much was given away
     */
    public static int pushOut(Level level, BlockPos pos, EnergyStorage source, int max, @Nullable Direction[] sides, boolean fromStorage)
    {
        int budget = Math.min(max, source.getEnergyStored());
        if (budget <= 0)
        {
            return 0;
        }
        boolean wasPushing = pushingFromStorage;
        pushingFromStorage = fromStorage;
        int given = 0;
        try
        {
            for (Direction dir : sides == null ? Direction.values() : sides)
            {
                if (given >= budget)
                {
                    break;
                }
                BlockPos next = pos.relative(dir);
                if (fromStorage && isPowercell(level, next))
                {
                    continue;
                }
                IEnergyStorage target = level.getCapability(Capabilities.EnergyStorage.BLOCK, next, dir.getOpposite());
                if (target != null && target.canReceive())
                {
                    given += target.receiveEnergy(budget - given, false);
                }
            }
        }
        finally
        {
            pushingFromStorage = wasPushing;
        }
        if (given > 0 && source instanceof MachineEnergy machine)
        {
            machine.drain(given);
        }
        return given;
    }

    /** The FE capability of a powerline: takes energy in and hands it straight to the network. */
    public static final class ConductorEnergy implements IEnergyStorage
    {
        private final Level level;
        private final BlockPos pos;
        @Nullable
        private final Direction side;

        public ConductorEnergy(Level level, BlockPos pos, @Nullable Direction side)
        {
            this.level = level;
            this.pos = pos;
            this.side = side;
        }

        @Override
        public int receiveEnergy(int toReceive, boolean simulate)
        {
            return insert(level, pos, side == null ? null : pos.relative(side), toReceive, simulate);
        }

        @Override
        public int extractEnergy(int toExtract, boolean simulate)
        {
            return 0;
        }

        @Override
        public int getEnergyStored()
        {
            return 0;
        }

        @Override
        public int getMaxEnergyStored()
        {
            return 1000; // some mods only push into blocks that report room for energy
        }

        @Override
        public boolean canExtract()
        {
            return false;
        }

        @Override
        public boolean canReceive()
        {
            return true;
        }
    }
}
