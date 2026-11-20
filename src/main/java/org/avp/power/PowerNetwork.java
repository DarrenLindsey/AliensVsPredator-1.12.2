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
    public record Network(Set<BlockPos> conductors, List<Target> targets, boolean tooBig, boolean highVoltage)
    {
        /** Power lost on the way along a low-voltage line (long low-voltage runs waste power; high voltage doesn't). */
        public float loss()
        {
            if (highVoltage)
            {
                return 0F;
            }
            return Math.min(0.5F, conductors.size() / 16 * AvpConfig.server(AvpConfig.SERVER.lowVoltageLossPer16) / 100F);
        }
    }

    /**
     * Whether a device may draw from a high-voltage line: only transformers and power cells can.
     * Anything else would be fried by it.
     */
    public static boolean takesHighVoltage(Level level, BlockPos pos)
    {
        return level.getBlockEntity(pos) instanceof PowerMachineBlockEntity machine
                && (machine.kind().isTransformer() || machine.kind() == PowerMachineBlock.Kind.POWERCELL);
    }

    /** A step-up transformer whose output (front) faces into the line makes the whole line high voltage. */
    private static boolean feedsHighVoltage(Level level, Target target)
    {
        BlockState state = level.getBlockState(target.pos());
        return state.getBlock() instanceof PowerMachineBlock block && block.kind() == PowerMachineBlock.Kind.TRANSFORMER
                && state.getValue(PowerMachineBlock.FACING) == target.side();
    }

    /** A device on a high-voltage line that can't take it: sparks, refuses the power, and may burn out. */
    private static void fry(Level level, BlockPos pos)
    {
        if (level.random.nextInt(40) != 0 || !(level instanceof net.minecraft.server.level.ServerLevel server))
        {
            return;
        }
        server.sendParticles(net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5,
                8, 0.3, 0.2, 0.3, 0.1);
        server.playSound(null, pos, net.minecraft.sounds.SoundEvents.FIRE_EXTINGUISH, net.minecraft.sounds.SoundSource.BLOCKS, 0.5F, 1.5F);
        if (AvpConfig.server(AvpConfig.SERVER.highVoltageBurnsDevices) && level.random.nextInt(10) == 0)
        {
            level.destroyBlock(pos, true);
            invalidate(level);
        }
    }

    /** A block next to the network and the side of it that touches the powerline. */
    public record Target(BlockPos pos, Direction side)
    {
    }

    /**
     * Solar panels touching each other. Panels with no wire of their own hand their power to
     * the "outlets" - the panels of the array that touch a powerline or a machine.
     */
    public record SolarArray(List<BlockPos> panels, List<BlockPos> outlets, long builtAt)
    {
    }

    private static final class LevelCache
    {
        final Map<BlockPos, Network> byConductor = new HashMap<>();
        final Map<BlockPos, SolarArray> solar = new HashMap<>();
        /** Per network: [game tick, FE carried this tick, overload strain] */
        final Map<Network, long[]> load = new java.util.IdentityHashMap<>();
    }

    /** How much a network can carry per tick (it's limited by its weakest link: a network is one line). */
    public static int capacity()
    {
        return AvpConfig.server(AvpConfig.SERVER.powerlineCapacity);
    }

    /** High-voltage lines carry several times more. */
    public static int capacity(Network network)
    {
        return network.highVoltage() ? capacity() * AvpConfig.server(AvpConfig.SERVER.highVoltageMultiplier) : capacity();
    }

    /** FE carried through this network in the last tick, for terminals. */
    public static int lastLoad(Level level, Network network)
    {
        LevelCache cache;
        synchronized (CACHE)
        {
            cache = CACHE.get(level);
        }
        long[] l = cache == null ? null : cache.load.get(network);
        return l == null || level.getGameTime() - l[0] > 2 ? 0 : (int) l[1];
    }

    public static boolean isOverloaded(Level level, Network network)
    {
        LevelCache cache;
        synchronized (CACHE)
        {
            cache = CACHE.get(level);
        }
        long[] l = cache == null ? null : cache.load.get(network);
        return l != null && l[2] > 20;
    }

    private static long[] loadOf(Level level, Network network)
    {
        LevelCache cache;
        synchronized (CACHE)
        {
            cache = CACHE.computeIfAbsent(level, l -> new LevelCache());
        }
        long[] l = cache.load.computeIfAbsent(network, n -> new long[3]);
        long now = level.getGameTime();
        if (l[0] != now)
        {
            l[0] = now;
            l[1] = 0;
            l[2] = Math.max(0, l[2] - 1); // strain eases off when it's not pushed too hard
        }
        return l;
    }

    /**
     * More power is being forced through the line than it can carry. A breaker on the network trips
     * (cutting it off); without one the line sparks, arcs to anyone close and may burn out.
     */
    private static void overload(Level level, Network network, long[] load)
    {
        load[2] += 3;
        if (load[2] < 60 || !(level instanceof net.minecraft.server.level.ServerLevel server))
        {
            return;
        }
        load[2] = 30;
        List<BlockPos> lines = new ArrayList<>(network.conductors());
        for (BlockPos pos : lines)
        {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof org.avp.block.BreakerBlock && state.getValue(org.avp.block.BreakerBlock.CLOSED))
            {
                org.avp.block.BreakerBlock.trip(level, pos, state);
                return;
            }
        }
        BlockPos spot = lines.get(level.random.nextInt(lines.size()));
        server.sendParticles(net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK, spot.getX() + 0.5, spot.getY() + 0.5, spot.getZ() + 0.5,
                20, 0.3, 0.3, 0.3, 0.2);
        server.playSound(null, spot, net.minecraft.sounds.SoundEvents.LIGHTNING_BOLT_IMPACT, net.minecraft.sounds.SoundSource.BLOCKS, 0.4F, 1.8F);
        for (net.minecraft.world.entity.LivingEntity victim : level.getEntitiesOfClass(net.minecraft.world.entity.LivingEntity.class,
                new net.minecraft.world.phys.AABB(spot).inflate(3)))
        {
            victim.hurt(org.avp.registry.ModDamageTypes.of(level, org.avp.registry.ModDamageTypes.ELECTRICITY), 4.0F);
        }
        if (AvpConfig.server(AvpConfig.SERVER.powerlinesBurnOut) && level.getBlockState(spot).getBlock() instanceof PowerlineBlock
                && level.random.nextInt(3) == 0)
        {
            level.destroyBlock(spot, false);
            server.sendParticles(net.minecraft.core.particles.ParticleTypes.LARGE_SMOKE, spot.getX() + 0.5, spot.getY() + 0.5, spot.getZ() + 0.5,
                    10, 0.2, 0.2, 0.2, 0.02);
            invalidate(level);
        }
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
                || state.getBlock() instanceof RedstoneSensorBlock && state.getValue(RedstoneSensorBlock.POWERED)
                || state.getBlock() instanceof org.avp.block.BreakerBlock && state.getValue(org.avp.block.BreakerBlock.CLOSED);
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
        boolean high = false;
        for (Target target : targets.values())
        {
            if (feedsHighVoltage(level, target))
            {
                high = true;
                break;
            }
        }
        Network network = new Network(conductors, new ArrayList<>(targets.values()), tooBig, high);
        for (BlockPos pos : conductors)
        {
            cache.byConductor.put(pos, network);
        }
        return network;
    }

    private static boolean isSolar(Level level, BlockPos pos)
    {
        return level.getBlockState(pos).getBlock() instanceof PowerMachineBlock block && block.kind() == PowerMachineBlock.Kind.SOLAR_PANEL;
    }

    /** True if something other than a solar panel next to pos takes FE (a powerline, a machine...). */
    private static boolean hasOutput(Level level, BlockPos pos)
    {
        for (Direction dir : Direction.values())
        {
            BlockPos next = pos.relative(dir);
            if (isSolar(level, next))
            {
                continue;
            }
            IEnergyStorage storage = level.getCapability(Capabilities.EnergyStorage.BLOCK, next, dir.getOpposite());
            if (storage != null && storage.canReceive())
            {
                return true;
            }
        }
        return false;
    }

    /** The array of touching solar panels that pos belongs to (rebuilt every 5 seconds or when blocks change). */
    public static SolarArray solarArray(Level level, BlockPos start)
    {
        LevelCache cache;
        synchronized (CACHE)
        {
            cache = CACHE.computeIfAbsent(level, l -> new LevelCache());
        }
        SolarArray known = cache.solar.get(start);
        if (known != null && level.getGameTime() - known.builtAt() < 100)
        {
            return known;
        }
        List<BlockPos> panels = new ArrayList<>();
        List<BlockPos> outlets = new ArrayList<>();
        Set<BlockPos> seen = new HashSet<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        queue.add(start.immutable());
        seen.add(start.immutable());
        while (!queue.isEmpty() && panels.size() < 1024)
        {
            BlockPos pos = queue.poll();
            panels.add(pos);
            if (hasOutput(level, pos))
            {
                outlets.add(pos);
            }
            for (Direction dir : Direction.values())
            {
                BlockPos next = pos.relative(dir);
                if (!seen.contains(next) && level.isLoaded(next) && isSolar(level, next))
                {
                    seen.add(next);
                    queue.add(next);
                }
            }
        }
        SolarArray array = new SolarArray(panels, outlets, level.getGameTime());
        for (BlockPos pos : panels)
        {
            cache.solar.put(pos, array);
        }
        return array;
    }

    /**
     * Power cells linked to this one: cells touching it (a "bank") and cells on any powerline
     * network it is wired to. Linked cells even out their charge.
     */
    public static List<BlockPos> linkedCells(Level level, BlockPos cell)
    {
        Set<BlockPos> found = new HashSet<>();
        // the bank: cells touching each other
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        queue.add(cell.immutable());
        found.add(cell.immutable());
        List<BlockPos> bank = new ArrayList<>();
        while (!queue.isEmpty() && found.size() < 256)
        {
            BlockPos pos = queue.poll();
            bank.add(pos);
            for (Direction dir : Direction.values())
            {
                BlockPos next = pos.relative(dir);
                if (!found.contains(next) && isPowercell(level, next))
                {
                    found.add(next);
                    queue.add(next);
                }
            }
        }
        // anything wired to the bank
        for (BlockPos pos : bank)
        {
            for (Direction dir : Direction.values())
            {
                BlockPos next = pos.relative(dir);
                if (isConductor(level.getBlockState(next)))
                {
                    for (Target target : network(level, next).targets())
                    {
                        if (isPowercell(level, target.pos()))
                        {
                            found.add(target.pos());
                        }
                    }
                }
            }
        }
        found.remove(cell);
        return new ArrayList<>(found);
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
                if (network.highVoltage() && !takesHighVoltage(level, target.pos()))
                {
                    if (!simulate)
                    {
                        fry(level, target.pos());
                    }
                    continue;
                }
                receivers.add(storage);
            }
        }
        // the line carries only so much per tick; trying to force more through strains it
        long[] load = loadOf(level, network);
        int room = (int) Math.max(0, capacity(network) - load[1]);
        // what the far end would actually draw (a generator offers its whole buffer; that alone is no overload)
        long demand = 0;
        for (IEnergyStorage receiver : receivers)
        {
            demand += receiver.receiveEnergy(amount, true);
            if (demand >= amount)
            {
                break;
            }
        }
        amount = (int) Math.min(amount, demand);
        if (amount <= 0)
        {
            return 0;
        }
        if (amount > room)
        {
            if (!simulate)
            {
                overload(level, network, load);
            }
            amount = room;
            if (amount <= 0)
            {
                return 0;
            }
        }
        // share evenly; whatever one block can't take is offered to the ones after it
        // a long low-voltage run loses some on the way: the sender pays for it
        float loss = network.loss();
        int deliverable = (int) (amount * (1F - loss));
        int remaining = deliverable;
        for (int i = 0; i < receivers.size() && remaining > 0; i++)
        {
            int share = (remaining + (receivers.size() - i) - 1) / (receivers.size() - i);
            remaining -= receivers.get(i).receiveEnergy(share, simulate);
        }
        int delivered = deliverable - remaining;
        int paid = loss <= 0F ? delivered : Math.min(amount, (int) Math.ceil(delivered / (1F - loss)));
        if (!simulate)
        {
            load[1] += paid;
        }
        return paid;
    }

    public static boolean isPowercell(Level level, BlockPos pos)
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
