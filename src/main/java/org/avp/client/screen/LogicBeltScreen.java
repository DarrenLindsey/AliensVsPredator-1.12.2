package org.avp.client.screen;

import java.util.ArrayList;
import java.util.List;

import org.avp.block.conveyor.ConveyorPayloads;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

/** The Logic Belt's BeltScript editor, in the Seegson CRT style. */
public class LogicBeltScreen extends Screen
{
    private final BlockPos pos;
    private String text;
    private MultiLineEditBox editor;
    private CompoundTag info;
    private String message = "";
    private int ticks;

    public LogicBeltScreen(CompoundTag data)
    {
        super(Component.literal("Logic Belt"));
        this.pos = BlockPos.of(data.getLong("Pos"));
        this.text = data.getString("Script");
        this.info = data;
    }

    public static void info(CompoundTag data)
    {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen instanceof LogicBeltScreen screen)
        {
            screen.info = data;
            if (data.contains("Message"))
            {
                screen.message = data.getString("Message");
            }
        }
        else if (data.getBoolean("Open"))
        {
            mc.setScreen(new LogicBeltScreen(data));
        }
    }

    private int left()
    {
        return (width - 340) / 2;
    }

    private int top()
    {
        return (height - 220) / 2;
    }

    @Override
    protected void init()
    {
        int x = left(), y = top();
        editor = new SeegsonTerminalScreen.TermEditor(font, x + 6, y + 18, 220, 170);
        editor.setCharacterLimit(8000);
        editor.setValue(text);
        addRenderableWidget(editor);
        addRenderableWidget(new SeegsonTerminalScreen.TermButton(x + 6, y + 194, 70, 14, "SAVE", () ->
                PacketDistributor.sendToServer(new ConveyorPayloads.Save(pos, editor.getValue(), false)), false));
        addRenderableWidget(new SeegsonTerminalScreen.TermButton(x + 80, y + 194, 70, 14, "EXAMPLE", () -> editor.setValue(EXAMPLE), false));
        addRenderableWidget(new SeegsonTerminalScreen.TermButton(x + 154, y + 194, 70, 14, "CLOSE", this::onClose, false));
    }

    private static final String EXAMPLE = """
            # BeltScript runs for every item, top to bottom.
            # The first route wins: left right straight back store
            set n = n + 1
            if tag("c:ores"): left
            if is("cobblestone"): store
            if count >= 32: right
            signal n % 16
            straight
            """;

    @Override
    public void tick()
    {
        if (++ticks % 20 == 0)
        {
            PacketDistributor.sendToServer(new ConveyorPayloads.Save(pos, "", true));
        }
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    @Override
    public void renderBackground(GuiGraphics g, int mx, int my, float pt)
    {
        int x = left(), y = top();
        g.fill(0, 0, width, height, 0xC0000000);
        g.fill(x - 2, y - 2, x + 342, y + 222, SeegsonTerminalScreen.DIM);
        g.fill(x, y, x + 340, y + 220, SeegsonTerminalScreen.BG);
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float pt)
    {
        super.render(g, mx, my, pt);
        int x = left(), y = top();
        g.drawString(font, "LOGIC BELT // BELTSCRIPT", x + 6, y + 6, SeegsonTerminalScreen.AMBER_HI, false);
        g.drawString(font, "SIGNAL " + info.getInt("Signal"), x + 340 - 6 - font.width("SIGNAL 15"), y + 6, SeegsonTerminalScreen.GREEN, false);
        int ty = y + 20;
        g.drawString(font, "VARIABLES", x + 232, ty, SeegsonTerminalScreen.DIM, false);
        ty += 10;
        ListTag vars = info.getList("Vars", Tag.TAG_STRING);
        for (int i = 0; i < vars.size() && ty < y + 110; i++, ty += 9)
        {
            g.drawString(font, font.plainSubstrByWidth(vars.getString(i), 100), x + 232, ty, SeegsonTerminalScreen.AMBER, false);
        }
        ty = y + 116;
        g.drawString(font, "ROUTES", x + 232, ty, SeegsonTerminalScreen.DIM, false);
        List<String> help = new ArrayList<>(List.of("left right straight", "back store", "", "is(\"id\") tag(\"c:ores\")", "count name item n",
                "redstone  signal N", "set v = v + 1"));
        for (String h : help)
        {
            ty += 9;
            g.drawString(font, h, x + 232, ty, SeegsonTerminalScreen.AMBER, false);
        }
        ListTag errors = info.getList("Errors", Tag.TAG_STRING);
        int ey = y + 210;
        for (int i = 0; i < Math.min(1, errors.size()); i++)
        {
            g.drawString(font, font.plainSubstrByWidth(errors.getString(i), 330), x + 232 - 226, ey, SeegsonTerminalScreen.RED, false);
        }
        if (errors.isEmpty() && !message.isEmpty())
        {
            g.drawString(font, message, x + 232, y + 196, SeegsonTerminalScreen.GREEN, false);
        }
        for (int sy = y; sy < y + 220; sy += 2)
        {
            g.fill(x, sy, x + 340, sy + 1, 0x18000000);
        }
    }
}
