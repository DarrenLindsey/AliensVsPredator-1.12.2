package org.avp.pneumatic;

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

public class PneumaticStationMenu extends AbstractContainerMenu
{
    public final BlockPos pos;
    private final ContainerLevelAccess access;

    public PneumaticStationMenu(int id, Inventory inv, RegistryFriendlyByteBuf buf)
    {
        this(id, inv, new ItemStackHandler(9), new ItemStackHandler(9), buf.readBlockPos());
    }

    public PneumaticStationMenu(int id, Inventory inv, PneumaticStationBlockEntity be)
    {
        this(id, inv, be.outbox, be.inbox, be.getBlockPos());
    }

    private PneumaticStationMenu(int id, Inventory inv, IItemHandler out, IItemHandler in, BlockPos pos)
    {
        super(ModMenus.PNEUMATIC_STATION.get(), id);
        this.pos = pos;
        this.access = ContainerLevelAccess.create(inv.player.level(), pos);
        for (int i = 0; i < 9; i++)
        {
            addSlot(new SlotItemHandler(out, i, 8 + i * 18, 30));
        }
        for (int i = 0; i < 9; i++)
        {
            addSlot(new SlotItemHandler(in, i, 8 + i * 18, 66)
            {
                @Override
                public boolean mayPlace(ItemStack stack)
                {
                    return false;
                }
            });
        }
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(inv, 9 + row * 9 + col, 8 + col * 18, 104 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(inv, col, 8 + col * 18, 162));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        Slot slot = slots.get(index);
        if (!slot.hasItem())
        {
            return ItemStack.EMPTY;
        }
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();
        if (index < 18)
        {
            if (!moveItemStackTo(stack, 18, slots.size(), true))
            {
                return ItemStack.EMPTY;
            }
        }
        else if (!moveItemStackTo(stack, 0, 9, false))
        {
            return ItemStack.EMPTY;
        }
        if (stack.isEmpty())
        {
            slot.set(ItemStack.EMPTY);
        }
        else
        {
            slot.setChanged();
        }
        return copy;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return access.evaluate((level, p) -> level.getBlockEntity(p) instanceof PneumaticStationBlockEntity && player.distanceToSqr(p.getCenter()) < 64, true);
    }
}
