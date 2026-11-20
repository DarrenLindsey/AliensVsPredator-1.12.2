package org.avp.block.conveyor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nullable;

import org.avp.power.MachineEnergy;
import org.avp.refining.Refining;
import org.avp.refining.RefineryKind;
import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * Everything a belt can know: the sorting filter, the Logic Belt's script and variables, a buffer
 * (Storage Belt: 27 x 1,024; Logic Belt's "store"; Crafting Belt's ingredients), the Crafting Belt's
 * 3x3 pattern, the Processing Belt's mode and power.
 */
public class ConveyorBlockEntity extends BlockEntity
{
    public enum Process
    {
        SMELT, BLAST, CRUSH, WASH
    }

    private ItemStack filter = ItemStack.EMPTY;
    private String script = "# BeltScript: runs for every item.\nif tag(\"c:ores\"): left\nstraight\n";
    @Nullable
    private BeltScript compiled;
    final Map<String, Object> vars = new HashMap<>();
    private int signal;
    private String lastError = "";
    private Process process = Process.SMELT;
    final NonNullList<ItemStack> pattern = NonNullList.withSize(9, ItemStack.EMPTY);
    @Nullable
    private RecipeHolder<CraftingRecipe> recipe;
    private boolean recipeChecked;
    private final MachineEnergy energy = new MachineEnergy(40_000);

    public final ItemStackHandler buffer = new ItemStackHandler(27)
    {
        @Override
        public int getSlotLimit(int slot)
        {
            return kind() == ConveyorBlock.Kind.STORAGE ? 1024 : 64;
        }

        @Override
        protected int getStackLimit(int slot, ItemStack stack)
        {
            return getSlotLimit(slot);
        }

        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }
    };

    public ConveyorBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.CONVEYOR.get(), pos, state);
    }

    ConveyorBlock.Kind kind()
    {
        return getBlockState().getBlock() instanceof ConveyorBlock b ? b.kind : ConveyorBlock.Kind.NORMAL;
    }

    // ------------------------------------------------------------------ settings

    public ItemStack filter()
    {
        return filter;
    }

    public void setFilter(ItemStack stack)
    {
        filter = stack.isEmpty() ? ItemStack.EMPTY : stack.copyWithCount(1);
        sync();
    }

    public boolean matches(ItemStack stack)
    {
        return !filter.isEmpty() && ItemStack.isSameItem(filter, stack);
    }

    public String script()
    {
        return script;
    }

    /** Sets the Logic Belt's script; returns its errors. */
    public List<String> setScript(String text)
    {
        BeltScript parsed = BeltScript.parse(text);
        if (parsed.errors.isEmpty())
        {
            script = text;
            compiled = parsed;
            vars.clear();
            lastError = "";
            sync();
        }
        return parsed.errors;
    }

    public String lastError()
    {
        return lastError;
    }

    public Map<String, Object> vars()
    {
        return vars;
    }

    public int signal()
    {
        return signal;
    }

    public Process process()
    {
        return process;
    }

    public void cycleProcess()
    {
        process = Process.values()[(process.ordinal() + 1) % Process.values().length];
        sync();
    }

    public void setPattern(int slot, ItemStack stack)
    {
        pattern.set(slot, stack.isEmpty() ? ItemStack.EMPTY : stack.copyWithCount(1));
        recipeChecked = false;
        sync();
    }

    public IEnergyStorage energyFor()
    {
        return energy.view(true, false, Integer.MAX_VALUE);
    }

    private void sync()
    {
        setChanged();
        if (level != null)
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    // ------------------------------------------------------------------ items arriving at the middle of the belt

    /**
     * Something special happens to an item reaching the middle. Returns the way it should go on, or null
     * if the belt took it.
     */
    @Nullable
    Direction onItem(ItemEntity item, Direction facing)
    {
        switch (kind())
        {
            case LOGIC ->
            {
                if (compiled == null)
                {
                    compiled = BeltScript.parse(script);
                }
                BeltScript.Result r = compiled.run(item.getItem(), vars, level.hasNeighborSignal(worldPosition), level.getGameTime(), level.random, signal);
                if (r.error() != null)
                {
                    lastError = r.error();
                }
                if (r.signal() != signal)
                {
                    signal = r.signal();
                    level.updateNeighborsAt(worldPosition, getBlockState().getBlock());
                }
                return switch (r.route())
                {
                    case LEFT -> facing.getCounterClockWise();
                    case RIGHT -> facing.getClockWise();
                    case BACK -> facing.getOpposite();
                    case STORE -> absorb(item) ? null : facing;
                    default -> facing;
                };
            }
            case STORAGE ->
            {
                return absorb(item) ? null : facing;
            }
            case CRAFTING ->
            {
                craftWith(item);
                return item.isAlive() ? facing : null;
            }
            case SORTING ->
            {
                return matches(item.getItem()) ? facing.getCounterClockWise() : facing;
            }
            default ->
            {
                return facing;
            }
        }
    }

    /** Puts the item in the buffer (true if all of it went in). */
    private boolean absorb(ItemEntity item)
    {
        ItemStack left = ItemHandlerHelper.insertItemStacked(buffer, item.getItem().copy(), false);
        if (left.isEmpty())
        {
            item.discard();
            return true;
        }
        item.setItem(left);
        return false;
    }

    // ------------------------------------------------------------------ processing

    /** Processing Belt: holds the item in the middle until it's done. True while it's working on it. */
    boolean process(ItemEntity item)
    {
        CompoundTag data = item.getPersistentData();
        if (data.getLong("avpProcAt") == worldPosition.asLong() && data.getBoolean("avpProcDone"))
        {
            return false;
        }
        ItemStack stack = item.getItem();
        ItemStack result = result(stack.copyWithCount(1));
        if (result.isEmpty())
        {
            return false;
        }
        if (data.getLong("avpProcAt") != worldPosition.asLong())
        {
            data.putLong("avpProcAt", worldPosition.asLong());
            data.putInt("avpProc", 0);
            data.putBoolean("avpProcDone", false);
        }
        if (!energy.consume(16))
        {
            return true; // waits for power
        }
        int progress = data.getInt("avpProc") + 1;
        int needed = Math.min(400, 30 + 8 * stack.getCount()) / (process == Process.BLAST ? 2 : 1);
        if (progress >= needed)
        {
            ItemStack out = result.copyWithCount(Math.min(result.getMaxStackSize(), result.getCount() * stack.getCount()));
            item.setItem(out);
            data.putBoolean("avpProcDone", true);
            level.playSound(null, worldPosition, process == Process.CRUSH ? SoundEvents.GRINDSTONE_USE : SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 0.5F, 1.2F);
            return false;
        }
        data.putInt("avpProc", progress);
        if (level instanceof ServerLevel server && progress % 6 == 0)
        {
            server.sendParticles(process == Process.WASH ? ParticleTypes.SPLASH : process == Process.CRUSH ? ParticleTypes.CRIT : ParticleTypes.FLAME,
                    item.getX(), item.getY() + 0.2, item.getZ(), 2, 0.1, 0.05, 0.1, 0.01);
        }
        return true;
    }

    private ItemStack result(ItemStack one)
    {
        return switch (process)
        {
            case SMELT -> level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(one), level)
                    .map(r -> r.value().getResultItem(level.registryAccess()).copy()).orElse(ItemStack.EMPTY);
            case BLAST -> level.getRecipeManager().getRecipeFor(RecipeType.BLASTING, new SingleRecipeInput(one), level)
                    .map(r -> r.value().getResultItem(level.registryAccess()).copy()).orElse(ItemStack.EMPTY);
            case CRUSH -> Refining.result(RefineryKind.CRUSHER, one);
            case WASH -> Refining.result(RefineryKind.WASHER, one);
        };
    }

    // ------------------------------------------------------------------ crafting

    @Nullable
    private RecipeHolder<CraftingRecipe> recipe()
    {
        if (!recipeChecked && level != null)
        {
            recipeChecked = true;
            CraftingInput input = CraftingInput.of(3, 3, new ArrayList<>(pattern));
            Optional<RecipeHolder<CraftingRecipe>> found = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level);
            recipe = found.orElse(null);
        }
        return recipe;
    }

    public ItemStack craftResult()
    {
        RecipeHolder<CraftingRecipe> r = recipe();
        return r == null ? ItemStack.EMPTY : r.value().getResultItem(level.registryAccess());
    }

    private Map<Item, Integer> needs()
    {
        Map<Item, Integer> need = new HashMap<>();
        for (ItemStack p : pattern)
        {
            if (!p.isEmpty())
            {
                need.merge(p.getItem(), 1, Integer::sum);
            }
        }
        return need;
    }

    private int buffered(Item item)
    {
        int n = 0;
        for (int i = 0; i < buffer.getSlots(); i++)
        {
            if (buffer.getStackInSlot(i).is(item))
            {
                n += buffer.getStackInSlot(i).getCount();
            }
        }
        return n;
    }

    /** Takes what the pattern needs off the belt (up to 4 crafts' worth), then crafts what it can. */
    private void craftWith(ItemEntity item)
    {
        RecipeHolder<CraftingRecipe> r = recipe();
        if (r == null)
        {
            return;
        }
        Map<Item, Integer> need = needs();
        ItemStack stack = item.getItem();
        Integer per = need.get(stack.getItem());
        if (per != null)
        {
            int want = per * 4 - buffered(stack.getItem());
            if (want > 0)
            {
                int take = Math.min(want, stack.getCount());
                ItemStack left = ItemHandlerHelper.insertItemStacked(buffer, stack.copyWithCount(take), false);
                int taken = take - left.getCount();
                if (taken >= stack.getCount())
                {
                    item.discard();
                }
                else
                {
                    item.setItem(stack.copyWithCount(stack.getCount() - taken));
                }
            }
        }
        // craft as many as it can
        for (int round = 0; round < 4; round++)
        {
            boolean ready = need.entrySet().stream().allMatch(e -> buffered(e.getKey()) >= e.getValue());
            if (!ready)
            {
                break;
            }
            need.forEach((it, n) ->
            {
                int left = n;
                for (int i = 0; i < buffer.getSlots() && left > 0; i++)
                {
                    if (buffer.getStackInSlot(i).is(it))
                    {
                        left -= buffer.extractItem(i, left, false).getCount();
                    }
                }
            });
            CraftingInput input = CraftingInput.of(3, 3, new ArrayList<>(pattern));
            ItemStack out = r.value().assemble(input, level.registryAccess());
            spawnAhead(out);
            for (ItemStack rem : r.value().getRemainingItems(input))
            {
                if (!rem.isEmpty())
                {
                    spawnAhead(rem);
                }
            }
            level.playSound(null, worldPosition, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.4F, 1.2F);
        }
    }

    /** Puts an item on the far half of this belt so it carries on. */
    private void spawnAhead(ItemStack stack)
    {
        Direction facing = getBlockState().getValue(ConveyorBlock.FACING);
        ItemEntity e = new ItemEntity(level, worldPosition.getX() + 0.5 + facing.getStepX() * 0.38, worldPosition.getY() + 0.3,
                worldPosition.getZ() + 0.5 + facing.getStepZ() * 0.38, stack);
        e.setDeltaMovement(0, 0, 0);
        e.getPersistentData().putLong("avpBeltAt", worldPosition.asLong());
        e.getPersistentData().putLong("avpBeltTime", level.getGameTime());
        level.addFreshEntity(e);
    }

    // ------------------------------------------------------------------ ticking: storage release, extractor

    public static void serverTick(Level level, BlockPos pos, BlockState state, ConveyorBlockEntity be)
    {
        if (state.getValue(ConveyorBlock.POWERED) && be.kind() != ConveyorBlock.Kind.STORAGE)
        {
            return;
        }
        long t = level.getGameTime();
        switch (be.kind())
        {
            case STORAGE ->
            {
                // a redstone signal releases it, a stack every half second
                if (state.getValue(ConveyorBlock.POWERED) && t % 10 == 0 && be.crowd() < 2)
                {
                    for (int i = 0; i < be.buffer.getSlots(); i++)
                    {
                        ItemStack s = be.buffer.extractItem(i, 64, false);
                        if (!s.isEmpty())
                        {
                            be.spawnAhead(s);
                            break;
                        }
                    }
                }
            }
            case EXTRACTOR ->
            {
                if (t % 20 == 0 && be.crowd() < 2)
                {
                    Direction facing = state.getValue(ConveyorBlock.FACING);
                    IItemHandler from = level.getCapability(Capabilities.ItemHandler.BLOCK, pos.relative(facing.getOpposite()), facing);
                    if (from != null)
                    {
                        for (int i = 0; i < from.getSlots(); i++)
                        {
                            ItemStack s = from.getStackInSlot(i);
                            if (!s.isEmpty() && (be.filter.isEmpty() || ItemStack.isSameItem(be.filter, s)))
                            {
                                ItemStack got = from.extractItem(i, 16, false);
                                if (!got.isEmpty())
                                {
                                    be.spawnAhead(got);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            default ->
            {
            }
        }
    }

    private int crowd()
    {
        return level.getEntitiesOfClass(ItemEntity.class, new AABB(worldPosition).deflate(0.05)).size();
    }

    /** Hoppers, pipes and Joes can put items onto the belt (a few at a time), and take from the buffer. */
    public final IItemHandler input = new IItemHandler()
    {
        @Override
        public int getSlots()
        {
            return 1 + buffer.getSlots();
        }

        @Override
        public ItemStack getStackInSlot(int slot)
        {
            return slot == 0 ? ItemStack.EMPTY : buffer.getStackInSlot(slot - 1);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
        {
            if (level == null || stack.isEmpty() || slot != 0)
            {
                return stack;
            }
            if (kind() == ConveyorBlock.Kind.STORAGE)
            {
                return ItemHandlerHelper.insertItemStacked(buffer, stack, simulate);
            }
            if (crowd() >= 3)
            {
                return stack;
            }
            if (!simulate)
            {
                ItemEntity item = new ItemEntity(level, worldPosition.getX() + 0.5, worldPosition.getY() + 0.3, worldPosition.getZ() + 0.5, stack.copy());
                item.setDeltaMovement(0, 0, 0);
                item.setPickUpDelay(10);
                level.addFreshEntity(item);
            }
            return ItemStack.EMPTY;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return slot == 0 || kind() == ConveyorBlock.Kind.CRAFTING ? ItemStack.EMPTY : buffer.extractItem(slot - 1, amount, simulate);
        }

        @Override
        public int getSlotLimit(int slot)
        {
            return slot == 0 ? 64 : buffer.getSlotLimit(slot - 1);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return slot == 0;
        }
    };

    // ------------------------------------------------------------------ saving

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        if (!filter.isEmpty())
        {
            tag.put("Filter", filter.save(registries));
        }
        tag.putString("Script", script);
        CompoundTag v = new CompoundTag();
        vars.forEach((k, val) ->
        {
            if (val instanceof Double d)
            {
                v.putDouble(k, d);
            }
            else if (val instanceof Boolean b)
            {
                v.putBoolean(k, b);
            }
            else
            {
                v.putString(k, String.valueOf(val));
            }
        });
        tag.put("Vars", v);
        tag.putInt("Signal", signal);
        tag.putInt("Process", process.ordinal());
        tag.put("Buffer", buffer.serializeNBT(registries));
        CompoundTag pat = new CompoundTag();
        for (int i = 0; i < 9; i++)
        {
            if (!pattern.get(i).isEmpty())
            {
                pat.put(String.valueOf(i), pattern.get(i).save(registries));
            }
        }
        tag.put("Pattern", pat);
        tag.putInt("Energy", energy.getEnergyStored());
        tag.putString("Error", lastError);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        filter = tag.contains("Filter") ? ItemStack.parseOptional(registries, tag.getCompound("Filter")) : ItemStack.EMPTY;
        if (tag.contains("Script"))
        {
            script = tag.getString("Script");
            compiled = null;
        }
        vars.clear();
        CompoundTag v = tag.getCompound("Vars");
        for (String k : v.getAllKeys())
        {
            Tag t = v.get(k);
            vars.put(k, t.getId() == Tag.TAG_BYTE ? (Object) (v.getBoolean(k)) : t instanceof net.minecraft.nbt.NumericTag n ? (Object) n.getAsDouble() : t.getAsString());
        }
        signal = tag.getInt("Signal");
        process = Process.values()[Math.max(0, Math.min(Process.values().length - 1, tag.getInt("Process")))];
        buffer.deserializeNBT(registries, tag.getCompound("Buffer"));
        CompoundTag pat = tag.getCompound("Pattern");
        for (int i = 0; i < 9; i++)
        {
            pattern.set(i, pat.contains(String.valueOf(i)) ? ItemStack.parseOptional(registries, pat.getCompound(String.valueOf(i))) : ItemStack.EMPTY);
        }
        recipeChecked = false;
        energy.setStored(tag.getInt("Energy"));
        lastError = tag.getString("Error");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
