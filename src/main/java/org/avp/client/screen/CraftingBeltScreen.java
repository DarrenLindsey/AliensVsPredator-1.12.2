package org.avp.client.screen;

import org.avp.block.conveyor.CraftingBeltMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/** Crafting Belt recipe screen: Weyland-Yutani panel, 3x3 pattern, result preview. */
public class CraftingBeltScreen extends AbstractContainerScreen<CraftingBeltMenu>
{
    public CraftingBeltScreen(CraftingBeltMenu menu, Inventory inv, Component title)
    {
        super(menu, inv, title);
    }

    @Override
    protected void renderBg(GuiGraphics g, float pt, int mx, int my)
    {
        int x = leftPos, y = topPos;
        g.fill(x, y, x + imageWidth, y + imageHeight, 0xFF22262A);
        g.fill(x + 1, y + 1, x + imageWidth - 1, y + imageHeight - 1, 0xFF3B3F42);
        for (int i = 0; i < imageWidth - 4; i += 6)
        {
            g.fill(x + 2 + i, y + 2, x + 2 + Math.min(i + 3, imageWidth - 4), y + 5, 0xFFF2B705);
        }
        for (Slot slot : menu.slots)
        {
            g.fill(x + slot.x - 1, y + slot.y - 1, x + slot.x + 17, y + slot.y + 17, 0xFF5A5F63);
            g.fill(x + slot.x, y + slot.y, x + slot.x + 16, y + slot.y + 16, 0xFF16191B);
        }
        g.fill(x + 115, y + 30, x + 141, y + 56, 0xFFF2B705);
        g.fill(x + 116, y + 31, x + 140, y + 55, 0xFF16191B);
        g.drawString(font, "->", x + 96, y + 39, 0xFF6CFF6A, false);
        ItemStack out = menu.preview(minecraft.level);
        if (!out.isEmpty())
        {
            g.renderItem(out, x + 120, y + 35);
            g.renderItemDecorations(font, out, x + 120, y + 35);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mx, int my)
    {
        g.drawString(font, title, 8, 6, 0xFFE8E8E0, false);
        g.drawString(font, "click: set   empty hand: clear", 8, 72, 0xFF9AA0A4, false);
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float pt)
    {
        super.render(g, mx, my, pt);
        renderTooltip(g, mx, my);
    }
}
