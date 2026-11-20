package org.avp.block.conveyor;

import org.avp.registry.ModMenus;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * The Crafting Belt's recipe: 9 "ghost" slots (click with an item to set it, click empty-handed to
 * clear; nothing is used up), plus the player's inventory.
 */
public class CraftingBeltMenu extends AbstractContainerMenu
{
    public final BlockPos pos;
    private final Container ghost;
    private final ConveyorBlockEntity belt;
    private final ContainerLevelAccess access;

    public CraftingBeltMenu(int id, Inventory inv, RegistryFriendlyByteBuf buf)
    {
        this(id, inv, buf.readBlockPos(), null);
    }

    public CraftingBeltMenu(int id, Inventory inv, ConveyorBlockEntity be)
    {
        this(id, inv, be.getBlockPos(), be);
    }

    private CraftingBeltMenu(int id, Inventory inv, BlockPos pos, ConveyorBlockEntity be)
    {
        super(ModMenus.CRAFTING_BELT.get(), id);
        this.pos = pos;
        this.belt = be;
        this.access = ContainerLevelAccess.create(inv.player.level(), pos);
        this.ghost = new SimpleContainer(9);
        if (be != null)
        {
            for (int i = 0; i < 9; i++)
            {
                ghost.setItem(i, be.pattern.get(i).copy());
            }
        }
        for (int i = 0; i < 9; i++)
        {
            addSlot(new Slot(ghost, i, 30 + (i % 3) * 18, 17 + (i / 3) * 18)
            {
                @Override
                public boolean mayPickup(Player player)
                {
                    return false;
                }

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
                addSlot(new Slot(inv, 9 + row * 9 + col, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++)
        {
            addSlot(new Slot(inv, col, 8 + col * 18, 142));
        }
    }

    @Override
    public void clicked(int slot, int button, ClickType type, Player player)
    {
        if (slot >= 0 && slot < 9)
        {
            ItemStack carried = getCarried();
            ItemStack set = carried.isEmpty() ? ItemStack.EMPTY : carried.copyWithCount(1);
            ghost.setItem(slot, set);
            if (belt != null)
            {
                belt.setPattern(slot, set);
            }
            broadcastChanges();
            return;
        }
        super.clicked(slot, button, type, player);
    }

    public ItemStack preview(net.minecraft.world.level.Level level)
    {
        java.util.List<ItemStack> items = new java.util.ArrayList<>();
        for (int i = 0; i < 9; i++)
        {
            items.add(ghost.getItem(i));
        }
        var input = net.minecraft.world.item.crafting.CraftingInput.of(3, 3, items);
        return level.getRecipeManager().getRecipeFor(net.minecraft.world.item.crafting.RecipeType.CRAFTING, input, level)
                .map(r -> r.value().assemble(input, level.registryAccess())).orElse(ItemStack.EMPTY);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player)
    {
        return access.evaluate((level, p) -> level.getBlockEntity(p) instanceof ConveyorBlockEntity && player.distanceToSqr(p.getCenter()) < 64, true);
    }
}
