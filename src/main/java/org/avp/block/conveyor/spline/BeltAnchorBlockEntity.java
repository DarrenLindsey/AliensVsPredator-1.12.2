package org.avp.block.conveyor.spline;

import java.util.ArrayList;
import java.util.List;

import org.avp.block.conveyor.ConveyorBlockEntity;
import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

/**
 * A belt anchor: items handed to it ride its spline (bunching up if the far end is full). With no
 * spline of its own it's an end: it hands items to the block it faces (a belt, a container), or drops
 * them there.
 */
public class BeltAnchorBlockEntity extends SplineAnchorBlockEntity
{
    public static final double SPACING = 0.5;

    public static final class Moving
    {
        public final ItemStack stack;
        public double at;

        Moving(ItemStack stack, double at)
        {
            this.stack = stack;
            this.at = at;
        }
    }

    public final List<Moving> items = new ArrayList<>();
    private double speed = 0.12;

    public BeltAnchorBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.BELT_ANCHOR.get(), pos, state);
    }

    public double speed()
    {
        return speed;
    }

    public void setSpeed(double speed)
    {
        this.speed = speed;
        sync();
    }

    @Override
    protected void onUnlinked()
    {
        if (level != null && !level.isClientSide)
        {
            for (Moving m : items)
            {
                Containers.dropItemStack(level, worldPosition.getX() + 0.5, worldPosition.getY() + 1, worldPosition.getZ() + 0.5, m.stack);
            }
        }
        items.clear();
    }

    /** Hands it an item; returns what it couldn't take. */
    public ItemStack accept(ItemStack stack)
    {
        if (stack.isEmpty() || level == null)
        {
            return stack;
        }
        if (target == null)
        {
            return emit(stack);
        }
        if (!items.isEmpty() && items.get(items.size() - 1).at < SPACING)
        {
            return stack; // the start of the belt is busy
        }
        items.add(new Moving(stack.copy(), 0));
        sync();
        return ItemStack.EMPTY;
    }

    /** An end anchor passes things on to whatever it faces. */
    private ItemStack emit(ItemStack stack)
    {
        Direction facing = getBlockState().getValue(SplineAnchorBlock.FACING);
        BlockPos next = worldPosition.relative(facing);
        if (level.getBlockEntity(next) instanceof ConveyorBlockEntity belt)
        {
            return belt.input.insertItem(0, stack, false);
        }
        IItemHandler into = level.getCapability(Capabilities.ItemHandler.BLOCK, next, facing.getOpposite());
        if (into != null)
        {
            return ItemHandlerHelper.insertItemStacked(into, stack, false);
        }
        if (level.getBlockState(next).isAir())
        {
            ItemEntity drop = new ItemEntity(level, next.getX() + 0.5, next.getY() + 0.3, next.getZ() + 0.5, stack.copy());
            drop.setDeltaMovement(facing.getStepX() * 0.1, 0, facing.getStepZ() * 0.1);
            level.addFreshEntity(drop);
            return ItemStack.EMPTY;
        }
        return stack;
    }

    /** What hoppers, pipes and belts put into it. */
    public final IItemHandler input = new IItemHandler()
    {
        @Override
        public int getSlots()
        {
            return 1;
        }

        @Override
        public ItemStack getStackInSlot(int slot)
        {
            return ItemStack.EMPTY;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
        {
            if (simulate)
            {
                return target == null || items.isEmpty() || items.get(items.size() - 1).at >= SPACING ? ItemStack.EMPTY : stack;
            }
            return accept(stack);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot)
        {
            return 64;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return true;
        }
    };

    public static void tick(Level level, BlockPos pos, BlockState state, BeltAnchorBlockEntity be)
    {
        Spline spline = be.spline();
        if (spline == null || be.items.isEmpty())
        {
            return;
        }
        double end = spline.length;
        double limit = end;
        for (Moving m : be.items)
        {
            m.at = Math.min(m.at + be.speed, limit);
            limit = m.at - SPACING;
        }
        if (level.isClientSide)
        {
            return;
        }
        Moving front = be.items.get(0);
        if (front.at >= end)
        {
            ItemStack left = be.deliver(front.stack);
            if (left.isEmpty())
            {
                be.items.remove(0);
                be.sync();
            }
            else if (left.getCount() != front.stack.getCount())
            {
                front.stack.setCount(left.getCount());
                be.sync();
            }
        }
    }

    private ItemStack deliver(ItemStack stack)
    {
        if (target == null || !level.isLoaded(target))
        {
            return stack;
        }
        if (level.getBlockEntity(target) instanceof BeltAnchorBlockEntity anchor)
        {
            return anchor.accept(stack.copy());
        }
        if (level.getBlockEntity(target) instanceof ConveyorBlockEntity belt)
        {
            return belt.input.insertItem(0, stack.copy(), false);
        }
        IItemHandler into = level.getCapability(Capabilities.ItemHandler.BLOCK, target, null);
        return into != null ? ItemHandlerHelper.insertItemStacked(into, stack.copy(), false) : stack;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        ListTag list = new ListTag();
        for (Moving m : items)
        {
            CompoundTag t = new CompoundTag();
            t.put("Item", m.stack.save(registries));
            t.putDouble("At", m.at);
            list.add(t);
        }
        tag.put("Items", list);
        tag.putDouble("Speed", speed);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        items.clear();
        for (Tag t : tag.getList("Items", Tag.TAG_COMPOUND))
        {
            CompoundTag c = (CompoundTag) t;
            ItemStack.parse(registries, c.getCompound("Item")).ifPresent(s -> items.add(new Moving(s, c.getDouble("At"))));
        }
        speed = tag.contains("Speed") ? tag.getDouble("Speed") : 0.12;
    }

    public List<ItemStack> contents()
    {
        List<ItemStack> out = new ArrayList<>();
        items.forEach(m -> out.add(m.stack));
        return out;
    }
}
