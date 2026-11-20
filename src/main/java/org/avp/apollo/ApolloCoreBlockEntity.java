package org.avp.apollo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

import javax.annotation.Nullable;

import org.avp.config.AvpConfig;
import org.avp.power.MachineEnergy;
import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;

/**
 * APOLLO, the station AI's core: keeps a manifest of everything stored in it (as counts, not slots),
 * capacity from the Data Banks joined to it. It's a PneuPost station called APOLLO, so canisters can
 * deliver to it and it can dispatch requests down the tubes; Working Joes running "courier" carry
 * requests by hand. It's one per player: the newest core you place is the one in charge.
 */
public class ApolloCoreBlockEntity extends BlockEntity
{
    public static final int ITEMS_PER_BANK = 16_384, TYPES_PER_BANK = 64;
    private static final Map<Level, Set<BlockPos>> CORES = new WeakHashMap<>();

    /** One stored kind of item. */
    public static final class Entry
    {
        public final ItemStack proto;
        public long count;

        Entry(ItemStack proto, long count)
        {
            this.proto = proto.copyWithCount(1);
            this.count = count;
        }
    }

    /** A delivery waiting for a courier. */
    public record Job(ItemStack item, int count, UUID player, long made)
    {
    }

    private final List<Entry> manifest = new ArrayList<>();
    private final Deque<Job> jobs = new ArrayDeque<>();
    private final MachineEnergy energy = new MachineEnergy(100_000);
    @Nullable
    private UUID owner;
    private int banks;
    private boolean online;
    private final Deque<String> log = new ArrayDeque<>();

    public ApolloCoreBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.APOLLO_CORE.get(), pos, state);
    }

    // ------------------------------------------------------------------ registry

    @Nullable
    public static ApolloCoreBlockEntity of(net.minecraft.server.MinecraftServer server, UUID owner)
    {
        for (var level : server.getAllLevels())
        {
            Set<BlockPos> set;
            synchronized (CORES)
            {
                set = new HashSet<>(CORES.getOrDefault(level, Set.of()));
            }
            for (BlockPos pos : set)
            {
                if (level.isLoaded(pos) && level.getBlockEntity(pos) instanceof ApolloCoreBlockEntity core && owner.equals(core.owner))
                {
                    return core;
                }
            }
        }
        return null;
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        if (level != null && !level.isClientSide)
        {
            synchronized (CORES)
            {
                CORES.computeIfAbsent(level, l -> new HashSet<>()).add(worldPosition.immutable());
            }
        }
    }

    @Override
    public void setRemoved()
    {
        super.setRemoved();
        if (level != null)
        {
            synchronized (CORES)
            {
                CORES.getOrDefault(level, new HashSet<>()).remove(worldPosition);
            }
        }
    }

    public void setOwner(UUID owner)
    {
        this.owner = owner;
        say("APOLLO ONLINE. GOOD MORNING.");
        setChanged();
    }

    @Nullable
    public UUID owner()
    {
        return owner;
    }

    // ------------------------------------------------------------------ the manifest

    public long capacity()
    {
        return (long) (1 + banks) * ITEMS_PER_BANK;
    }

    public int typeLimit()
    {
        return (1 + banks) * TYPES_PER_BANK;
    }

    public long stored()
    {
        long n = 0;
        for (Entry e : manifest)
        {
            n += e.count;
        }
        return n;
    }

    public List<Entry> manifest()
    {
        return manifest;
    }

    public int banks()
    {
        return banks;
    }

    public boolean online()
    {
        return online || !AvpConfig.server(AvpConfig.SERVER.joeTerminalNeedsPower);
    }

    @Nullable
    private Entry find(ItemStack stack)
    {
        for (Entry e : manifest)
        {
            if (ItemStack.isSameItemSameComponents(e.proto, stack))
            {
                return e;
            }
        }
        return null;
    }

    /** Stores what it can; returns the rest. */
    public ItemStack store(ItemStack stack, boolean simulate)
    {
        if (stack.isEmpty() || !online())
        {
            return stack;
        }
        long room = capacity() - stored();
        if (room <= 0)
        {
            return stack;
        }
        Entry e = find(stack);
        if (e == null && manifest.size() >= typeLimit())
        {
            return stack;
        }
        int take = (int) Math.min(room, stack.getCount());
        if (!simulate)
        {
            if (e == null)
            {
                e = new Entry(stack, 0);
                manifest.add(e);
                if (isSpecimen(stack))
                {
                    say("SPECIMEN LOGGED. SPECIAL ORDER 937 IN EFFECT. RELEASE DENIED.");
                }
            }
            e.count += take;
            setChanged();
        }
        return stack.copyWithCount(stack.getCount() - take);
    }

    /** Takes items out (fewer if it hasn't got them). Specimens never come out. */
    public ItemStack take(ItemStack like, int count, boolean simulate)
    {
        Entry e = find(like);
        if (e == null || count <= 0 || !online())
        {
            return ItemStack.EMPTY;
        }
        if (isSpecimen(e.proto))
        {
            if (!simulate)
            {
                say("REQUEST DENIED. SPECIMEN IS COMPANY PROPERTY.");
            }
            return ItemStack.EMPTY;
        }
        int n = (int) Math.min(count, Math.min(e.count, like.getMaxStackSize()));
        if (!simulate)
        {
            e.count -= n;
            if (e.count <= 0)
            {
                manifest.remove(e);
            }
            setChanged();
        }
        return e.proto.copyWithCount(n);
    }

    /** Eggs, facehuggers in jars, embryos: the Company wants them. */
    public static boolean isSpecimen(ItemStack stack)
    {
        String id = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
        return id.contains("ovamorph") || id.contains("facehugger") || id.contains("embryo") || id.contains("royal_jelly");
    }

    /** Hoppers, PneuPost, Joes: insert anything; the "slots" are the manifest. */
    public final IItemHandler handler = new IItemHandler()
    {
        @Override
        public int getSlots()
        {
            return manifest.size() + 1;
        }

        @Override
        public ItemStack getStackInSlot(int slot)
        {
            if (slot >= manifest.size())
            {
                return ItemStack.EMPTY;
            }
            Entry e = manifest.get(slot);
            return e.proto.copyWithCount((int) Math.min(e.count, e.proto.getMaxStackSize()));
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
        {
            return store(stack, simulate);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return slot < manifest.size() ? take(manifest.get(slot).proto, amount, simulate) : ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot)
        {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return true;
        }
    };

    public IEnergyStorage energyFor()
    {
        return energy.view(true, false, Integer.MAX_VALUE);
    }

    // ------------------------------------------------------------------ jobs and talk

    public void queue(Job job)
    {
        jobs.addLast(job);
        say("REQUEST LOGGED: " + job.count() + " x " + job.item().getHoverName().getString() + ". AWAITING COURIER.");
        setChanged();
    }

    @Nullable
    public Job nextJob()
    {
        return jobs.pollFirst();
    }

    public List<Job> jobs()
    {
        return new ArrayList<>(jobs);
    }

    public void say(String line)
    {
        log.addLast(line);
        while (log.size() > 20)
        {
            log.removeFirst();
        }
    }

    public List<String> log()
    {
        return new ArrayList<>(log);
    }

    /** Sends items down the PneuPost tubes to a station. Returns a message (! = failed). */
    public String dispatch(String station, ItemStack like, int count)
    {
        if (level == null || !online())
        {
            return "!APOLLO OFFLINE.";
        }
        org.avp.pneumatic.TubeNetwork.Survey survey = org.avp.pneumatic.TubeNetwork.survey(level, worldPosition);
        BlockPos dest = survey.stations().get(station);
        if (dest == null)
        {
            return "!NO STATION " + station + " ON APOLLO'S LINE.";
        }
        if (survey.pressurised() == 0)
        {
            return "!NO LINE PRESSURE.";
        }
        List<BlockPos> route = org.avp.pneumatic.TubeNetwork.route(level, worldPosition, dest);
        if (route == null)
        {
            return "!NO ROUTE TO " + station + ".";
        }
        List<ItemStack> cargo = new ArrayList<>();
        int left = count;
        while (left > 0 && cargo.size() < 9)
        {
            ItemStack got = take(like, left, false);
            if (got.isEmpty())
            {
                break;
            }
            cargo.add(got);
            left -= got.getCount();
        }
        if (cargo.isEmpty())
        {
            return "!NOTHING TO SEND.";
        }
        double speed = AvpConfig.server(AvpConfig.SERVER.capsuleSpeed) * Math.min(4, survey.pressurised());
        org.avp.pneumatic.CapsuleEntity.launch(level, worldPosition, route, cargo, speed);
        say("DISPATCHED " + (count - left) + " x " + like.getHoverName().getString() + " TO " + station + ".");
        return "Dispatched to " + station + ".";
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ApolloCoreBlockEntity core)
    {
        boolean was = core.online;
        core.online = core.energy.consume(AvpConfig.server(AvpConfig.SERVER.apolloPower));
        if (was != core.online)
        {
            core.say(core.online ? "POWER RESTORED. APOLLO ONLINE." : "POWER FAILURE. APOLLO OFFLINE.");
            level.setBlock(pos, state.setValue(ApolloCoreBlock.LIT, core.online), 3);
        }
        if (level.getGameTime() % 40 == 0)
        {
            core.banks = countBanks(level, pos);
        }
    }

    /** Data Banks joined to the core (touching it or another bank), up to 64. */
    private static int countBanks(Level level, BlockPos core)
    {
        Set<BlockPos> seen = new HashSet<>();
        ArrayDeque<BlockPos> queue = new ArrayDeque<>();
        queue.add(core);
        seen.add(core);
        int n = 0;
        while (!queue.isEmpty() && n < 64)
        {
            BlockPos at = queue.poll();
            for (Direction d : Direction.values())
            {
                BlockPos next = at.relative(d);
                if (seen.add(next) && level.getBlockState(next).getBlock() instanceof ApolloBankBlock)
                {
                    n++;
                    queue.add(next);
                }
            }
        }
        return n;
    }

    // ------------------------------------------------------------------ saving

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        ListTag list = new ListTag();
        for (Entry e : manifest)
        {
            CompoundTag t = new CompoundTag();
            t.put("Item", e.proto.save(registries));
            t.putLong("Count", e.count);
            list.add(t);
        }
        tag.put("Manifest", list);
        ListTag jobList = new ListTag();
        for (Job j : jobs)
        {
            CompoundTag t = new CompoundTag();
            t.put("Item", j.item().save(registries));
            t.putInt("Count", j.count());
            t.putUUID("Player", j.player());
            jobList.add(t);
        }
        tag.put("Jobs", jobList);
        tag.putInt("Energy", energy.getEnergyStored());
        if (owner != null)
        {
            tag.putUUID("Owner", owner);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        manifest.clear();
        for (Tag t : tag.getList("Manifest", Tag.TAG_COMPOUND))
        {
            CompoundTag c = (CompoundTag) t;
            ItemStack.parse(registries, c.getCompound("Item")).ifPresent(s -> manifest.add(new Entry(s, c.getLong("Count"))));
        }
        jobs.clear();
        for (Tag t : tag.getList("Jobs", Tag.TAG_COMPOUND))
        {
            CompoundTag c = (CompoundTag) t;
            ItemStack.parse(registries, c.getCompound("Item")).ifPresent(s -> jobs.add(new Job(s, c.getInt("Count"), c.getUUID("Player"), 0)));
        }
        energy.setStored(tag.getInt("Energy"));
        owner = tag.hasUUID("Owner") ? tag.getUUID("Owner") : null;
    }
}
