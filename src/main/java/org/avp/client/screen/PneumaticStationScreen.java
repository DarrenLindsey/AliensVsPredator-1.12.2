package org.avp.client.screen;

import java.util.ArrayList;
import java.util.List;

import org.avp.pneumatic.PneumaticStationMenu;
import org.avp.pneumatic.StationPayloads;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.network.PacketDistributor;

/** Seegson PneuPost station: beige Seegson plastic with an amber CRT address panel. */
public class PneumaticStationScreen extends AbstractContainerScreen<PneumaticStationMenu>
{
    private static final int BEIGE = 0xFFC9BFA8, BEIGE_HI = 0xFFE3DAC4, BEIGE_LO = 0xFF8C8370, WELL = 0xFF2A2822, ORANGE = 0xFFE07020;
    private static CompoundTag latest = new CompoundTag();
    private EditBox nameBox;
    private final List<String> stations = new ArrayList<>();
    private String message = "";
    private int ticks, scroll;

    public PneumaticStationScreen(PneumaticStationMenu menu, Inventory inv, Component title)
    {
        super(menu, inv, title);
        imageWidth = 256;
        imageHeight = 186;
        inventoryLabelY = 93;
        titleLabelY = 5;
    }

    public static void info(CompoundTag data)
    {
        latest = data;
        if (net.minecraft.client.Minecraft.getInstance().screen instanceof PneumaticStationScreen screen)
        {
            screen.apply(data);
        }
    }

    private void apply(CompoundTag data)
    {
        stations.clear();
        ListTag list = data.getList("Stations", Tag.TAG_STRING);
        for (int i = 0; i < list.size(); i++)
        {
            stations.add(list.getString(i));
        }
        if (data.contains("Message"))
        {
            message = data.getString("Message");
        }
        if (nameBox != null && !nameBox.isFocused())
        {
            nameBox.setValue(data.getString("Name"));
        }
    }

    private void send(int action, String text)
    {
        PacketDistributor.sendToServer(new StationPayloads.Action(menu.pos, action, text));
    }

    @Override
    protected void init()
    {
        super.init();
        nameBox = new EditBox(font, leftPos + 172, topPos + 16, 78, 12, Component.literal("name"));
        nameBox.setMaxLength(20);
        nameBox.setTextColor(0xFFFFB000);
        addRenderableWidget(nameBox);
        addRenderableWidget(new SeegsonTerminalScreen.TermButton(leftPos + 172, topPos + 30, 78, 12, "RENAME", () -> send(StationPayloads.RENAME, nameBox.getValue()), false));
        send(StationPayloads.REFRESH, "");
    }

    @Override
    public void containerTick()
    {
        super.containerTick();
        if (++ticks % 20 == 0)
        {
            send(StationPayloads.REFRESH, "");
        }
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button)
    {
        int x = leftPos + 172, y = topPos + 58;
        for (int i = scroll; i < stations.size() && i < scroll + 7; i++, y += 12)
        {
            if (mx >= x && mx < x + 56 && my >= y && my < y + 11)
            {
                send(StationPayloads.SEND, stations.get(i));
                return true;
            }
            if (mx >= x + 58 && mx < x + 78 && my >= y && my < y + 11)
            {
                boolean already = stations.get(i).equals(latest.getString("Auto"));
                send(StationPayloads.AUTO, already ? "" : stations.get(i));
                return true;
            }
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double sx, double sy)
    {
        scroll = Math.max(0, Math.min(Math.max(0, stations.size() - 7), scroll - (int) Math.signum(sy)));
        return true;
    }

    @Override
    public boolean keyPressed(int key, int scan, int mods)
    {
        if (nameBox.isFocused() && key != 256)
        {
            return nameBox.keyPressed(key, scan, mods) || true;
        }
        return super.keyPressed(key, scan, mods);
    }

    @Override
    protected void renderBg(GuiGraphics g, float pt, int mx, int my)
    {
        int x = leftPos, y = topPos;
        g.fill(x, y, x + imageWidth, y + imageHeight, BEIGE_LO);
        g.fill(x + 1, y + 1, x + imageWidth - 1, y + imageHeight - 1, BEIGE);
        g.fill(x + 1, y + 1, x + imageWidth - 1, y + 2, BEIGE_HI);
        g.fill(x + 3, y + 14, x + 166, y + 15, ORANGE);
        for (Slot slot : menu.slots)
        {
            g.fill(x + slot.x - 1, y + slot.y - 1, x + slot.x + 17, y + slot.y + 17, BEIGE_HI);
            g.fill(x + slot.x - 1, y + slot.y - 1, x + slot.x + 16, y + slot.y + 16, BEIGE_LO);
            g.fill(x + slot.x, y + slot.y, x + slot.x + 16, y + slot.y + 16, WELL);
        }
        // the CRT address panel
        g.fill(x + 168, y + 4, x + 252, y + 180, 0xFF1A1A16);
        g.fill(x + 169, y + 5, x + 251, y + 179, SeegsonTerminalScreen.BG);
        for (int sy = y + 5; sy < y + 179; sy += 2)
        {
            g.fill(x + 169, sy, x + 251, sy + 1, 0x18000000);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mx, int my)
    {
        g.drawString(font, "SEEGSON PNEUPOST", 8, 5, 0xFF3A342A, false);
        g.drawString(font, "OUTGOING", 8, 20, 0xFF5A5242, false);
        g.drawString(font, "INCOMING", 8, 56, 0xFF5A5242, false);
        g.drawString(font, "STATION", 172, 7, SeegsonTerminalScreen.DIM, false);
        int pressure = latest.getInt("Pressurised");
        g.drawString(font, pressure > 0 ? "PRESSURE x" + Math.min(4, pressure) : "NO PRESSURE", 172, 46,
                pressure > 0 ? SeegsonTerminalScreen.GREEN : SeegsonTerminalScreen.RED, false);
        int y = 58;
        String auto = latest.getString("Auto");
        for (int i = scroll; i < stations.size() && i < scroll + 7; i++, y += 12)
        {
            String s = stations.get(i);
            g.fill(172, y, 228, y + 11, 0xFF241A06);
            g.drawString(font, font.plainSubstrByWidth("> " + s, 54), 174, y + 2, SeegsonTerminalScreen.AMBER, false);
            boolean on = s.equals(auto);
            g.fill(230, y, 250, y + 11, on ? 0xFF3A2800 : 0xFF14100A);
            g.drawString(font, "A", 237, y + 2, on ? SeegsonTerminalScreen.AMBER_HI : SeegsonTerminalScreen.DIM, false);
        }
        if (stations.isEmpty())
        {
            g.drawString(font, "NO STATIONS", 174, 60, SeegsonTerminalScreen.DIM, false);
        }
        g.drawString(font, "click: send", 172, 146, SeegsonTerminalScreen.DIM, false);
        g.drawString(font, "A: auto-send", 172, 155, SeegsonTerminalScreen.DIM, false);
        if (!message.isEmpty())
        {
            boolean bad = message.startsWith("!");
            List<net.minecraft.util.FormattedCharSequence> lines = font.split(Component.literal(bad ? message.substring(1) : message), 160);
            for (int i = 0; i < Math.min(2, lines.size()); i++)
            {
                g.drawString(font, lines.get(i), 8, 84 + i * 9 - 0, bad ? 0xFFB02010 : 0xFF2A6A20, false);
            }
        }
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float pt)
    {
        super.render(g, mx, my, pt);
        renderTooltip(g, mx, my);
    }
}
