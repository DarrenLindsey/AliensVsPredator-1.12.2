package org.avp.client.screen;

import org.avp.refining.RefineryKind;
import org.avp.refining.RefineryMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

/** Weyland-Yutani industrial panel: gunmetal plate, hazard stripes, green LCD readouts. Drawn in code, no texture. */
public class RefineryScreen extends AbstractContainerScreen<RefineryMenu>
{
    private static final int PLATE = 0xFF3B3F42, PLATE_HI = 0xFF5A5F63, PLATE_LO = 0xFF22262A, WELL = 0xFF16191B, LCD = 0xFF0A1A0C,
            LCD_TEXT = 0xFF6CFF6A, LCD_DIM = 0xFF2E7A30, HAZARD = 0xFFF2B705, WATER = 0xFF3A7CFF, ACID = 0xFFB6FF2A;

    public RefineryScreen(RefineryMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
        imageWidth = 176;
        imageHeight = 166;
        inventoryLabelY = 73;
    }

    @Override
    protected void renderBg(GuiGraphics g, float pt, int mx, int my)
    {
        int x = leftPos, y = topPos;
        // plate with bevel
        g.fill(x, y, x + imageWidth, y + imageHeight, PLATE_LO);
        g.fill(x + 1, y + 1, x + imageWidth - 1, y + imageHeight - 1, PLATE);
        g.fill(x + 1, y + 1, x + imageWidth - 1, y + 2, PLATE_HI);
        g.fill(x + 1, y + 1, x + 2, y + imageHeight - 1, PLATE_HI);
        // hazard stripe header
        for (int i = 0; i < imageWidth - 4; i += 6)
        {
            g.fill(x + 2 + i, y + 2, x + 2 + Math.min(i + 3, imageWidth - 4), y + 5, HAZARD);
        }
        // rivets
        for (int[] r : new int[][] { { 4, 8 }, { imageWidth - 6, 8 }, { 4, imageHeight - 6 }, { imageWidth - 6, imageHeight - 6 } })
        {
            g.fill(x + r[0], y + r[1], x + r[0] + 2, y + r[1] + 2, PLATE_HI);
        }
        // slot wells
        for (Slot slot : menu.slots)
        {
            g.fill(x + slot.x - 1, y + slot.y - 1, x + slot.x + 17, y + slot.y + 17, PLATE_HI);
            g.fill(x + slot.x - 1, y + slot.y - 1, x + slot.x + 16, y + slot.y + 16, PLATE_LO);
            g.fill(x + slot.x, y + slot.y, x + slot.x + 16, y + slot.y + 16, WELL);
        }
        // output gets a bigger frame
        g.fill(x + 111, y + 30, x + 137, y + 56, HAZARD);
        g.fill(x + 112, y + 31, x + 136, y + 55, PLATE_LO);
        g.fill(x + 115, y + 34, x + 133, y + 52, WELL);
        // progress arrow (LCD)
        int progress = menu.data.get(0), total = Math.max(1, menu.data.get(1));
        g.fill(x + 78, y + 36, x + 106, y + 50, LCD);
        int filled = 26 * progress / total;
        for (int i = 0; i < 26; i++)
        {
            int h = i < 18 ? 3 : 3 + (i - 18);
            int top = y + 43 - Math.min(h, 6), bottom = y + 43 + Math.min(h, 6);
            if (i >= 18)
            {
                top = y + 37 + (i - 18);
                bottom = y + 49 - (i - 18);
            }
            g.fill(x + 79 + i, top, x + 80 + i, bottom, i < filled ? LCD_TEXT : LCD_DIM);
        }
        // power bar
        int energy = menu.data.get(2);
        g.fill(x + 8, y + 16, x + 18, y + 70, WELL);
        int eh = 52 * energy / 1000;
        g.fill(x + 9, y + 69 - eh, x + 17, y + 69, energy < 100 ? 0xFFFF4040 : HAZARD);
        for (int i = 0; i < 52; i += 4)
        {
            g.fill(x + 9, y + 17 + i, x + 17, y + 18 + i, 0x40000000);
        }
        // tank
        if (menu.kind.fluid != RefineryKind.Fluid.NONE)
        {
            int tank = menu.data.get(4);
            g.fill(x + 26, y + 16, x + 42, y + 50, WELL);
            int th = 32 * tank / 8000;
            g.fill(x + 27, y + 49 - th, x + 41, y + 49, menu.kind.fluid == RefineryKind.Fluid.WATER ? WATER : ACID);
        }
        // readout panel
        g.fill(x + 140, y + 16, x + 170, y + 70, LCD);
        g.drawString(font, "x" + menu.kind.multiplier, x + 144, y + 20, LCD_TEXT, false);
        g.drawString(font, menu.kind.power + "", x + 143, y + 34, LCD_TEXT, false);
        g.drawString(font, "FE/t", x + 143, y + 43, LCD_DIM, false);
        g.drawString(font, (100 * progress / total) + "%", x + 143, y + 57, LCD_TEXT, false);
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mx, int my)
    {
        g.drawString(font, title, 22, 7, 0xFFE8E8E0, false);
        g.drawString(font, "WEYLAND-YUTANI", 22, 73, 0xFF9AA0A4, false);
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float pt)
    {
        super.render(g, mx, my, pt);
        renderTooltip(g, mx, my);
        int x = leftPos, y = topPos;
        if (mx >= x + 8 && mx < x + 18 && my >= y + 16 && my < y + 70)
        {
            g.renderTooltip(font, Component.literal("Power: " + menu.data.get(2) / 10 + "% (" + menu.kind.power + " FE/t while running)"), mx, my);
        }
        if (menu.kind.fluid != RefineryKind.Fluid.NONE && mx >= x + 26 && mx < x + 42 && my >= y + 16 && my < y + 50)
        {
            g.renderTooltip(font, Component.literal((menu.kind.fluid == RefineryKind.Fluid.WATER ? "Water: " : "Xenomorph acid: ") + menu.data.get(4)
                    + " / 8000 mB"), mx, my);
        }
    }
}
