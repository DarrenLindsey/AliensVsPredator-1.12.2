package org.avp.block.conveyor;

import java.util.List;

import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.items.IItemHandler;

/** A conveyor's filter (sorting belts) and its input: items handed to it are set down on the belt. */
public class ConveyorBlockEntity extends BlockEntity
{
    private ItemStack filter = ItemStack.EMPTY;

    public ConveyorBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.CONVEYOR.get(), pos, state);
    }

    public ItemStack filter()
    {
        return filter;
    }

    public void setFilter(ItemStack stack)
    {
        filter = stack.isEmpty() ? ItemStack.EMPTY : stack.copyWithCount(1);
        setChanged();
        if (level != null)
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public boolean matches(ItemStack stack)
    {
        return !filter.isEmpty() && ItemStack.isSameItem(filter, stack);
    }

    /** Hoppers, pipes and Joes can put items onto the belt (a few at a time). */
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
            if (level == null || stack.isEmpty())
            {
                return stack;
            }
            List<ItemEntity> on = level.getEntitiesOfClass(ItemEntity.class, new AABB(worldPosition).deflate(0.05));
            if (on.size() >= 3)
            {
                return stack; // the belt is crowded
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

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        if (!filter.isEmpty())
        {
            tag.put("Filter", filter.save(registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        filter = tag.contains("Filter") ? ItemStack.parseOptional(registries, tag.getCompound("Filter")) : ItemStack.EMPTY;
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
