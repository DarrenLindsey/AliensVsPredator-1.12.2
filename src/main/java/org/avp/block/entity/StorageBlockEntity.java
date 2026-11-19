package org.avp.block.entity;

import java.util.function.Predicate;

import org.avp.block.StorageBlock;
import org.avp.menu.StorageMenu;
import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * Lockers, gun lockers and supply crates: 64 slots of storage.
 *
 * The items live in an ItemStackHandler, NeoForge's standard inventory. It's
 * also exposed as an "item handler capability" (see ModCapabilities), which is
 * how hoppers, pipes, Applied Energistics 2 buses and Create funnels/arms move
 * items in and out without needing any special code for this mod.
 */
public class StorageBlockEntity extends BlockEntity implements MenuProvider
{
    public static final int SLOTS = 64;

    private final ItemStackHandler items = new ItemStackHandler(SLOTS)
    {
        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return filter().test(stack);
        }

        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }
    };

    /** How many players have the menu open right now (the door stays open while > 0). */
    private int viewers;

    public StorageBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.STORAGE.get(), pos, state);
    }

    public ItemStackHandler getItems()
    {
        return items;
    }

    /** Which items are allowed in (e.g. the gun locker only takes weapons). */
    public Predicate<ItemStack> filter()
    {
        return getBlockState().getBlock() instanceof StorageBlock storage ? storage.filter() : stack -> true;
    }

    // ---- menu ----

    @Override
    public Component getDisplayName()
    {
        return getBlockState().getBlock().getName();
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player)
    {
        return new StorageMenu(id, playerInventory, this);
    }

    public void startOpen()
    {
        viewers++;
        updateOpenState();
    }

    public void stopOpen()
    {
        viewers = Math.max(0, viewers - 1);
        updateOpenState();
    }

    private void updateOpenState()
    {
        if (level != null && !level.isClientSide && getBlockState().getBlock() instanceof StorageBlock storage)
        {
            storage.setViewed(level, worldPosition, getBlockState(), viewers > 0);
        }
    }

    /** Spills the contents on the ground (when the block is broken). */
    public void dropContents()
    {
        if (level == null)
        {
            return;
        }
        for (int i = 0; i < items.getSlots(); i++)
        {
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), items.getStackInSlot(i));
            items.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    // ---- saving ----

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Items", items.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        if (tag.contains("Items"))
        {
            items.deserializeNBT(registries, tag.getCompound("Items"));
        }
    }

    /** Re-draw when the open/closed state changes on the client. */
    public void markForRender()
    {
        if (level != null)
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }
}
