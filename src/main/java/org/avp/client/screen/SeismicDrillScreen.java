package org.avp.client.screen;

import org.avp.mining.SeismicDrillBlockEntity;
import org.avp.mining.SeismicDrillMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

/** The seismic drill's control panel: Weyland-Yutani gunmetal and green LCD. */
public class SeismicDrillScreen extends AbstractContainerScreen<SeismicDrillMenu>
{
    private static final int PLATE = 0xFF3B3F42, PLATE_HI = 0xFF5A5F63, PLATE_LO = 0xFF22262A, WELL = 0xFF16191B, LCD = 0xFF0A1A0C,
            LCD_TEXT = 0xFF6CFF6A, LCD_DIM = 0xFF2E7A30, HAZARD = 0xFFF2B705;
    private static final String[] STATES = { "STANDBY", "DRILLING", "COMPLETE", "NO POWER", "CHECK CASING", "BUFFER FULL" };

    public SeismicDrillScreen(SeismicDrillMenu menu, Inventory inv, Component title)
    {
        super(menu, inv, title);
        imageWidth = 176;
        imageHeight = 230;
        inventoryLabelY = 137;
    }

    @Override
    protected void init()
    {
        super.init();
        int x = leftPos, y = topPos;
        addRenderableWidget(new SeegsonTerminalScreen.TermButton(x + 8, y + 62, 40, 14, "START", () -> press(0), false)
        {
            @Override
            public net.minecraft.network.chat.Component getMessage()
            {
                return Component.literal(menu.data.get(0) == SeismicDrillBlockEntity.RUNNING ? "STOP" : "START");
            }
        });
        addRenderableWidget(new SeegsonTerminalScreen.TermButton(x + 50, y + 62, 14, 14, "-", () -> press(1), false));
        addRenderableWidget(new SeegsonTerminalScreen.TermButton(x + 66, y + 62, 14, 14, "+", () -> press(2), false));
        addRenderableWidget(new SeegsonTerminalScreen.TermButton(x + 82, y + 62, 30, 14, "MODE", () -> press(3), false));
        addRenderableWidget(new SeegsonTerminalScreen.TermButton(x + 114, y + 62, 34, 14, "SURVEY", () -> press(4), false));
        addRenderableWidget(new SeegsonTerminalScreen.TermButton(x + 150, y + 62, 18, 14, "R", () -> press(5), false));
    }

    private void press(int id)
    {
        minecraft.gameMode.handleInventoryButtonClick(menu.containerId, id);
    }

    @Override
    protected void renderBg(GuiGraphics g, float pt, int mx, int my)
    {
        int x = leftPos, y = topPos;
        g.fill(x, y, x + imageWidth, y + imageHeight, PLATE_LO);
        g.fill(x + 1, y + 1, x + imageWidth - 1, y + imageHeight - 1, PLATE);
        g.fill(x + 1, y + 1, x + imageWidth - 1, y + 2, PLATE_HI);
        for (int i = 0; i < imageWidth - 4; i += 6)
        {
            g.fill(x + 2 + i, y + 2, x + 2 + Math.min(i + 3, imageWidth - 4), y + 5, HAZARD);
        }
        for (Slot slot : menu.slots)
        {
            g.fill(x + slot.x - 1, y + slot.y - 1, x + slot.x + 17, y + slot.y + 17, PLATE_HI);
            g.fill(x + slot.x - 1, y + slot.y - 1, x + slot.x + 16, y + slot.y + 16, PLATE_LO);
            g.fill(x + slot.x, y + slot.y, x + slot.x + 16, y + slot.y + 16, WELL);
        }
        // LCD status panel and survey panel
        g.fill(x + 8, y + 16, x + 92, y + 60, LCD);
        g.fill(x + 94, y + 16, x + 168, y + 60, LCD);
        // power
        g.fill(x + 8, y + 78, x + 168, y + 81, WELL);
        g.fill(x + 8, y + 78, x + 8 + 160 * menu.data.get(5) / 1000, y + 81, HAZARD);
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mx, int my)
    {
        g.drawString(font, title, 8, 7, 0xFFE8E8E0, false);
        int state = menu.data.get(0);
        g.drawString(font, STATES[Math.max(0, Math.min(STATES.length - 1, state))], 11, 19, state == 1 ? LCD_TEXT : 0xFFFFB000, false);
        int r = menu.data.get(1);
        g.drawString(font, "AREA " + (r * 2 + 1) + "x" + (r * 2 + 1), 11, 29, LCD_TEXT, false);
        g.drawString(font, menu.data.get(2) == 0 ? "MODE QUARRY" : "MODE ORE-BORE", 11, 38, LCD_TEXT, false);
        g.drawString(font, "Y " + menu.data.get(3) + "  #" + menu.data.get(4), 11, 47, LCD_DIM, false);
        g.drawString(font, "SEISMIC SURVEY", 97, 19, LCD_DIM, false);
        int y = 28;
        boolean any = false;
        for (int i = 0; i < 6; i++)
        {
            int id = menu.data.get(6 + i * 2), count = menu.data.get(7 + i * 2);
            if (count <= 0)
            {
                continue;
            }
            any = true;
            String name = BuiltInRegistries.BLOCK.byId(id).getName().getString().replace(" Ore", "").replace("Deepslate ", "D.");
            g.drawString(font, font.plainSubstrByWidth(name, 46), 97, y, LCD_TEXT, false);
            g.drawString(font, String.valueOf(count), 165 - font.width(String.valueOf(count)), y, LCD_TEXT, false);
            y += 5;
            if (y > 55)
            {
                break;
            }
        }
        if (!any)
        {
            g.drawString(font, "press SURVEY", 97, 30, LCD_DIM, false);
        }
        g.drawString(font, "HAUL", 8, 73 + 0, 0xFF9AA0A4, false);
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float pt)
    {
        super.render(g, mx, my, pt);
        renderTooltip(g, mx, my);
    }
}
