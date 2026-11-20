package org.avp.mining;

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
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class SeismicDrillMenu extends AbstractContainerMenu
{
    public final ContainerData data;
    private final ContainerLevelAccess access;
    private final SeismicDrillBlockEntity drill;

    public SeismicDrillMenu(int id, Inventory inv, RegistryFriendlyByteBuf buf)
    {
        this(id, inv, new ItemStackHandler(27), new SimpleContainerData(18), buf.readBlockPos(), null);
    }

    public SeismicDrillMenu(int id, Inventory inv, SeismicDrillBlockEntity be)
    {
        this(id, inv, be.buffer, be.data, be.getBlockPos(), be);
    }

    private SeismicDrillMenu(int id, Inventory inv, IItemHandler buffer, ContainerData data, BlockPos pos, SeismicDrillBlockEntity be)
    {
        super(ModMenus.SEISMIC_DRILL.get(), id);
        this.data = data;
        this.drill = be;
        this.access = ContainerLevelAccess.create(inv.player.level(), pos);
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new SlotItemHandler(buffer, row * 9 + col, 8 + col * 18, 84 + row * 18)
                {
                    @Override
                    public boolean mayPlace(ItemStack stack)
                    {
                        return false;
                    }
                });
            }
        }
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 9; col++)
            {
                addSlot(new Slot(inv, 9 + row * 9 + col, 8 + col * 18, 148 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(inv, col, 8 + col * 18, 206));
        }
        addDataSlots(data);
    }

    @Override
    public boolean clickMenuButton(Player player, int id)
    {
        if (drill != null)
        {
            drill.button(id);
        }
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        Slot slot = slots.get(index);
        if (!slot.hasItem() || index >= 27)
        {
            return ItemStack.EMPTY;
        }
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();
        if (!moveItemStackTo(stack, 27, slots.size(), true))
        {
            return ItemStack.EMPTY;
        }
        slot.setChanged();
        return copy;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return access.evaluate((level, p) -> level.getBlockEntity(p) instanceof SeismicDrillBlockEntity && player.distanceToSqr(p.getCenter()) < 64, true);
    }
}
