package org.avp.menu;

import org.avp.block.entity.TurretBlockEntity;
import org.avp.registry.ModItems;
import org.avp.registry.ModMenus;

import net.minecraft.core.BlockPos;
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
 * The sentry turret's menu: a row of 9 ammo slots, 3 upgrade slots, and the player's
 * inventory. The target buttons live on the screen and talk to the server with
 * TurretSettingPayload.
 */
public class TurretMenu extends AbstractContainerMenu
{
    public static final int AMMO_Y = 28;
    public static final int UPGRADE_Y = 66;
    public static final int INVENTORY_Y = 124;
    private static final int TURRET_SLOTS = TurretBlockEntity.AMMO_SLOTS + TurretBlockEntity.UPGRADE_SLOTS;

    private final ContainerLevelAccess access;
    private final BlockPos pos;

    /** Client side. */
    public TurretMenu(int id, Inventory playerInventory, RegistryFriendlyByteBuf extraData)
    {
        this(id, playerInventory, new ItemStackHandler(TurretBlockEntity.AMMO_SLOTS), new ItemStackHandler(TurretBlockEntity.UPGRADE_SLOTS),
                extraData.readBlockPos());
    }

    /** Server side. */
    public TurretMenu(int id, Inventory playerInventory, TurretBlockEntity turret)
    {
        this(id, playerInventory, turret.getAmmo(), turret.getUpgrades(), turret.getBlockPos());
    }

    private TurretMenu(int id, Inventory playerInventory, IItemHandler ammo, IItemHandler upgrades, BlockPos pos)
    {
        super(ModMenus.TURRET.get(), id);
        this.pos = pos;
        this.access = ContainerLevelAccess.create(playerInventory.player.level(), pos);
        for (int i = 0; i < TurretBlockEntity.AMMO_SLOTS; i++)
        {
            addSlot(new SlotItemHandler(ammo, i, 8 + 18 * i, AMMO_Y)
            {
                @Override
                public boolean mayPlace(ItemStack stack)
                {
                    return stack.is(ModItems.AMMO_SENTRY.get());
                }
            });
        }
        for (int i = 0; i < TurretBlockEntity.UPGRADE_SLOTS; i++)
        {
            addSlot(new SlotItemHandler(upgrades, i, 8 + 18 * i, UPGRADE_Y)
            {
                @Override
                public boolean mayPlace(ItemStack stack)
                {
                    return TurretBlockEntity.isUpgrade(stack);
                }
            });
        }
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(playerInventory, 9 + row * 9 + col, 8 + 18 * col, INVENTORY_Y + 18 * row));
            }
        }
        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(playerInventory, col, 8 + 18 * col, INVENTORY_Y + 58));
        }
    }

    public BlockPos pos()
    {
        return pos;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return access.evaluate((level, p) -> level.getBlockEntity(p) instanceof TurretBlockEntity
                && player.distanceToSqr(p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5) <= 64, true);
    }

    /** Shift-click: ammo and upgrades go to their slots; anything in the turret goes back to the player. */
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
        if (index < TURRET_SLOTS)
        {
            if (!moveItemStackTo(stack, TURRET_SLOTS, slots.size(), true))
            {
                return ItemStack.EMPTY;
            }
        }
        else if (stack.is(ModItems.AMMO_SENTRY.get()))
        {
            if (!moveItemStackTo(stack, 0, TurretBlockEntity.AMMO_SLOTS, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else if (TurretBlockEntity.isUpgrade(stack))
        {
            if (!moveItemStackTo(stack, TurretBlockEntity.AMMO_SLOTS, TURRET_SLOTS, false))
            {
                return ItemStack.EMPTY;
            }
        }
        else
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
