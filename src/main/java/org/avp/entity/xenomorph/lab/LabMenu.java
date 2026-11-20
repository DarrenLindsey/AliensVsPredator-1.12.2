package org.avp.entity.xenomorph.lab;

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

public class LabMenu extends AbstractContainerMenu
{
    public static final int[][] SEQUENCER = { { 26, 20 }, { 26, 46 }, { 62, 20 }, { 80, 20 }, { 98, 20 }, { 134, 33 } };
    public static final int[][] INCUBATOR = { { 44, 33 }, { 80, 33 } };

    public final LabKind kind;
    public final ContainerData data;
    public final BlockPos pos;
    private final ContainerLevelAccess access;

    public LabMenu(int id, Inventory inv, RegistryFriendlyByteBuf buf)
    {
        this(id, inv, buf.readBlockPos());
    }

    private LabMenu(int id, Inventory inv, BlockPos pos)
    {
        this(id, inv, kindAt(inv, pos), new ItemStackHandler(kindAt(inv, pos).slots), new SimpleContainerData(3), pos);
    }

    public LabMenu(int id, Inventory inv, LabBlockEntity be)
    {
        this(id, inv, be.kind, be.items, be.data, be.getBlockPos());
    }

    private static LabKind kindAt(Inventory inv, BlockPos pos)
    {
        BlockEntity be = inv.player.level().getBlockEntity(pos);
        return be instanceof LabBlockEntity l ? l.kind : LabKind.GENE_SEQUENCER;
    }

    private LabMenu(int id, Inventory inv, LabKind kind, IItemHandler items, ContainerData data, BlockPos pos)
    {
        super(ModMenus.XENO_LAB.get(), id);
        this.kind = kind;
        this.data = data;
        this.pos = pos;
        this.access = ContainerLevelAccess.create(inv.player.level(), pos);
        int[][] layout = kind == LabKind.GENE_SEQUENCER ? SEQUENCER : INCUBATOR;
        for (int i = 0; i < layout.length; i++)
        {
            boolean output = kind == LabKind.GENE_SEQUENCER && i == 5;
            addSlot(new SlotItemHandler(items, i, layout[i][0], layout[i][1])
            {
                @Override
                public boolean mayPlace(ItemStack stack)
                {
                    return !output && super.mayPlace(stack);
                }
            });
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
        int machine = kind.slots;
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();
        if (index < machine)
        {
            if (!moveItemStackTo(stack, machine, slots.size(), true))
            {
                return ItemStack.EMPTY;
            }
        }
        else if (!moveItemStackTo(stack, 0, kind == LabKind.GENE_SEQUENCER ? 5 : machine, false))
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
        return access.evaluate((level, p) -> level.getBlockEntity(p) instanceof LabBlockEntity && player.distanceToSqr(p.getCenter()) < 64, true);
    }
}
