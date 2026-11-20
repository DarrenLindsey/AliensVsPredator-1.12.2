package org.avp.refining;

import org.avp.registry.ModMenus;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class RefineryMenu extends AbstractContainerMenu
{
    public final RefineryKind kind;
    public final ContainerData data;
    private final ContainerLevelAccess access;

    /** Client. */
    public RefineryMenu(int id, Inventory inv, RegistryFriendlyByteBuf buf)
    {
        this(id, inv, kindAt(inv, buf.readBlockPos()), new ItemStackHandler(3), new SimpleContainerData(5), BlockPos.ZERO);
    }

    /** Server. */
    public RefineryMenu(int id, Inventory inv, RefineryBlockEntity be)
    {
        this(id, inv, be.kind, be.items(), be.data, be.getBlockPos());
    }

    private static RefineryKind kindAt(Inventory inv, BlockPos pos)
    {
        BlockEntity be = inv.player.level().getBlockEntity(pos);
        return be instanceof RefineryBlockEntity r ? r.kind : RefineryKind.CRUSHER;
    }

    private RefineryMenu(int id, Inventory inv, RefineryKind kind, IItemHandler items, ContainerData data, BlockPos pos)
    {
        super(ModMenus.REFINERY.get(), id);
        this.kind = kind;
        this.data = data;
        this.access = ContainerLevelAccess.create(inv.player.level(), pos);
        addSlot(new SlotItemHandler(items, RefineryBlockEntity.INPUT, 56, 35));
        addSlot(new SlotItemHandler(items, RefineryBlockEntity.OUTPUT, 116, 35)
        {
            @Override
            public boolean mayPlace(ItemStack stack)
            {
                return false;
            }
        });
        if (kind.fluid != RefineryKind.Fluid.NONE)
        {
            addSlot(new SlotItemHandler(items, RefineryBlockEntity.FLUID_SLOT, 26, 53));
        }
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(inv, 9 + row * 9 + col, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(inv, col, 8 + col * 18, 142));
        }
        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        Slot slot = slots.get(index);
        if (!slot.hasItem())
        {
            return ItemStack.EMPTY;
        }
        int machineSlots = kind.fluid != RefineryKind.Fluid.NONE ? 3 : 2;
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();
        if (index < machineSlots)
        {
            if (!moveItemStackTo(stack, machineSlots, slots.size(), true))
            {
                return ItemStack.EMPTY;
            }
        }
        else if (!moveItemStackTo(stack, 0, 1, false) && (machineSlots < 3 || !moveItemStackTo(stack, 2, 3, false)))
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
        return access.evaluate((level, pos) -> level.getBlockEntity(pos) instanceof RefineryBlockEntity
                && player.distanceToSqr(pos.getCenter()) < 64, true);
    }
}
