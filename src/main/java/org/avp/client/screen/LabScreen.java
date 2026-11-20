package org.avp.client.screen;

import org.avp.entity.xenomorph.lab.LabKind;
import org.avp.entity.xenomorph.lab.LabMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

/** Weyland-Yutani genetics: clinical white panel, biohazard trim, green readouts. */
public class LabScreen extends AbstractContainerScreen<LabMenu>
{
    private static final int WHITE = 0xFFD8DCD8, WHITE_HI = 0xFFF0F2EE, WHITE_LO = 0xFF8A908C, WELL = 0xFF1A1E1C, BIO = 0xFF5AD13A, LCD = 0xFF0A1A0C;

    public LabScreen(LabMenu menu, Inventory inv, Component title)
    {
        super(menu, inv, title);
    }

    @Override
    protected void renderBg(GuiGraphics g, float pt, int mx, int my)
    {
        int x = leftPos, y = topPos;
        g.fill(x, y, x + imageWidth, y + imageHeight, WHITE_LO);
        g.fill(x + 1, y + 1, x + imageWidth - 1, y + imageHeight - 1, WHITE);
        g.fill(x + 1, y + 1, x + imageWidth - 1, y + 2, WHITE_HI);
        for (int i = 0; i < imageWidth - 4; i += 8)
        {
            g.fill(x + 2 + i, y + 2, x + 2 + Math.min(i + 4, imageWidth - 4), y + 4, BIO);
        }
        for (Slot slot : menu.slots)
        {
            g.fill(x + slot.x - 1, y + slot.y - 1, x + slot.x + 17, y + slot.y + 17, WHITE_HI);
            g.fill(x + slot.x - 1, y + slot.y - 1, x + slot.x + 16, y + slot.y + 16, WHITE_LO);
            g.fill(x + slot.x, y + slot.y, x + slot.x + 16, y + slot.y + 16, WELL);
        }
        int progress = menu.data.get(0), total = Math.max(1, menu.data.get(1));
        // progress tube
        int tx = menu.kind == LabKind.GENE_SEQUENCER ? 62 : 104, ty = menu.kind == LabKind.GENE_SEQUENCER ? 46 : 36;
        int tw = menu.kind == LabKind.GENE_SEQUENCER ? 64 : 60;
        g.fill(x + tx, y + ty, x + tx + tw, y + ty + 10, LCD);
        g.fill(x + tx + 1, y + ty + 1, x + tx + 1 + (tw - 2) * progress / total, y + ty + 9, BIO);
        // power
        g.fill(x + 8, y + 16, x + 14, y + 66, WELL);
        int e = 48 * menu.data.get(2) / 1000;
        g.fill(x + 9, y + 65 - e, x + 13, y + 65, 0xFFF2B705);
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mx, int my)
    {
        g.drawString(font, title, 18, 6, 0xFF2A3A2A, false);
        if (menu.kind == LabKind.GENE_SEQUENCER)
        {
            g.drawString(font, "DNA", 28, 11 + 0, 0xFF4A5A4A, false);
            g.drawString(font, "SPLICES", 64, 11, 0xFF4A5A4A, false);
            g.drawString(font, "->", 128, 47, 0xFF2A3A2A, false);
        }
        else
        {
            g.drawString(font, "EMBRYO", 36, 24, 0xFF4A5A4A, false);
            g.drawString(font, "JELLY", 76, 24, 0xFF4A5A4A, false);
        }
        int progress = menu.data.get(0), total = Math.max(1, menu.data.get(1));
        g.drawString(font, (100 * progress / total) + "%", menu.kind == LabKind.GENE_SEQUENCER ? 136 : 140, 60, 0xFF2A6A20, false);
        g.drawString(font, "WEYLAND-YUTANI GENETICS", 8, 73, 0xFF6A706C, false);
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float pt)
    {
        super.render(g, mx, my, pt);
        renderTooltip(g, mx, my);
    }
}
