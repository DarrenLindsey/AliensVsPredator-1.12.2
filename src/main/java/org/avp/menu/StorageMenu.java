package org.avp.menu;

import org.avp.block.entity.StorageBlockEntity;
import org.avp.registry.ModMenus;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

/**
 * The menu (inventory screen logic) for lockers and supply crates:
 * an 8x8 storage grid on top, the player's inventory and hotbar below.
 * Slot positions must match the background image (tools/make_gui_textures.py).
 *
 * A menu exists twice: on the server (with the real block entity) and on the
 * client (with an empty stand-in inventory that the server keeps in sync).
 */
public class StorageMenu extends AbstractContainerMenu
{
    private static final int COLUMNS = 8;
    private static final int STORAGE_SLOTS = StorageBlockEntity.SLOTS;

    private final ContainerLevelAccess access;
    private final StorageBlockEntity blockEntity; // null on the client

    /** Client side: called when the server tells the client to open the menu. */
    public StorageMenu(int id, Inventory playerInventory, RegistryFriendlyByteBuf extraData)
    {
        this(id, playerInventory, new ItemStackHandler(STORAGE_SLOTS), null,
                ContainerLevelAccess.create(playerInventory.player.level(), extraData.readBlockPos()));
    }

    /** Server side. */
    public StorageMenu(int id, Inventory playerInventory, StorageBlockEntity blockEntity)
    {
        this(id, playerInventory, blockEntity.getItems(), blockEntity,
                ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()));
    }

    private StorageMenu(int id, Inventory playerInventory, IItemHandler storage, StorageBlockEntity blockEntity, ContainerLevelAccess access)
    {
        super(ModMenus.STORAGE.get(), id);
        this.access = access;
        this.blockEntity = blockEntity;
        if (blockEntity != null)
        {
            blockEntity.startOpen();
        }

        for (int row = 0; row < STORAGE_SLOTS / COLUMNS; row++)
        {
            for (int col = 0; col < COLUMNS; col++)
            {
                addSlot(new SlotItemHandler(storage, row * COLUMNS + col, 14 + 18 * col, 26 + 18 * row));
            }
        }
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(playerInventory, 9 + row * 9 + col, 8 + 18 * col, 184 + 18 * row));
            }
        }
        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(playerInventory, col, 8 + 18 * col, 242));
        }
    }

    @Override
    public void removed(Player player)
    {
        super.removed(player);
        if (blockEntity != null)
        {
            blockEntity.stopOpen();
        }
    }

    @Override
    public boolean stillValid(Player player)
    {
        return access.evaluate((level, pos) -> level.getBlockEntity(pos) instanceof StorageBlockEntity
                && player.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64, true);
    }

    /** Shift-click: storage <-> player inventory. */
    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        Slot slot = slots.get(index);
        if (!slot.hasItem())
        {
            return ItemStack.EMPTY;
        }
        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();
        if (index < STORAGE_SLOTS)
        {
            if (!moveItemStackTo(stack, STORAGE_SLOTS, slots.size(), true))
            {
                return ItemStack.EMPTY;
            }
        }
        else if (!moveItemStackTo(stack, 0, STORAGE_SLOTS, false))
        {
            return ItemStack.EMPTY;
        }
        if (stack.isEmpty())
        {
            slot.setByPlayer(ItemStack.EMPTY);
        }
        else
        {
            slot.setChanged();
        }
        return original;
    }
}
