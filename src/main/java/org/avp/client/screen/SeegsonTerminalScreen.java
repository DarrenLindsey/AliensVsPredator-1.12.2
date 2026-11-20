package org.avp.client.screen;

import java.util.ArrayList;
import java.util.List;

import org.avp.entity.joe.net.JoeTerminalActionPayload;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineEditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * The Seegson Terminal: an amber CRT console (Alien: Isolation style) for a player's Working Joe
 * network. Tabs: STATUS, PROGRAM (editor), LIBRARY, DRIVE (radar and remote control) and LOG.
 */
public class SeegsonTerminalScreen extends Screen
{
    static final int AMBER = 0xFFFFB000, AMBER_HI = 0xFFFFD27A, DIM = 0xFF7A5200, FAINT = 0xFF3A2800, BG = 0xFF0A0C07, RED = 0xFFFF4A2A,
            GREEN = 0xFF7CFF6A;
    private static final String[] TABS = { "STATUS", "PROGRAM", "LIBRARY", "DRIVE", "LOG", "APOLLO" };

    private CompoundTag data;
    private final BlockPos pos;
    private final boolean tablet;
    private String selected = "";
    private int tab;
    private String editorText = "# New JOE-SCRIPT program\nname Joe-01\nhome ~ ~ ~\nwhen lowpower: charge\nsay \"Ready.\"\nwait 10\n";
    private String fileName = "";
    private String libraryPick = "";
    private int unitScroll, fileScroll;
    private final List<String> messages = new ArrayList<>();
    private int ticks;

    private int left, top, w, h, rightX, contentY;
    private MultiLineEditBox editor;
    private EditBox nameBox, placeBox, sendBox, renameBox, searchBox, countBox, stationBox;
    private String apolloPick = "", search = "";
    private int apolloScroll;

    public SeegsonTerminalScreen(CompoundTag data)
    {
        super(Component.literal("Seegson Terminal"));
        this.data = data;
        this.tablet = data.getBoolean("Tablet");
        this.pos = data.contains("Pos") ? BlockPos.of(data.getLong("Pos")) : BlockPos.ZERO;
        ListTag units = data.getList("Units", Tag.TAG_COMPOUND);
        if (!units.isEmpty())
        {
            selected = units.getCompound(0).getString("Id");
        }
    }

    /** New data from the server. */
    public void update(CompoundTag fresh)
    {
        this.data = fresh;
        if (fresh.contains("Message"))
        {
            for (String line : fresh.getString("Message").split("\n"))
            {
                messages.add(line);
            }
            while (messages.size() > 4)
            {
                messages.remove(0);
            }
        }
        if (fresh.contains("Editor"))
        {
            editorText = fresh.getString("Editor");
            if (editor != null)
            {
                editor.setValue(editorText);
            }
            if (tab != 1)
            {
                tab = 1;
                rebuild();
            }
        }
    }

    private void send(int action, String text)
    {
        send(action, selected, text);
    }

    private void send(int action, String joe, String text)
    {
        PacketDistributor.sendToServer(new JoeTerminalActionPayload(action, joe, text, pos, tablet));
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    @Override
    public void onClose()
    {
        send(JoeTerminalActionPayload.CLOSE, "");
        super.onClose();
    }

    @Override
    protected void init()
    {
        w = Math.min(440, width - 8);
        h = Math.min(256, height - 8);
        left = (width - w) / 2;
        top = (height - h) / 2;
        rightX = left + 112;
        contentY = top + 34;
        rebuild();
        send(JoeTerminalActionPayload.REFRESH, "");
    }

    private void rebuild()
    {
        if (editor != null)
        {
            editorText = editor.getValue();
        }
        if (nameBox != null)
        {
            fileName = nameBox.getValue();
        }
        clearWidgets();
        editor = null;
        if (searchBox != null)
        {
            search = searchBox.getValue();
        }
        nameBox = placeBox = sendBox = renameBox = searchBox = countBox = stationBox = null;
        int rw = left + w - 6 - rightX;
        // tabs
        int tw = rw / TABS.length;
        for (int i = 0; i < TABS.length; i++)
        {
            int index = i;
            addRenderableWidget(new TermButton(rightX + i * tw, top + 18, tw - 2, 13, TABS[i], () ->
            {
                tab = index;
                rebuild();
            }, i == tab));
        }
        int by = top + h - 42;
        switch (tab)
        {
            case 0 ->
            {
                String[] names = { "RECALL", "STOP", "RESUME", "SECURITY", "CLR ALARM" };
                int[] acts = { JoeTerminalActionPayload.RECALL, JoeTerminalActionPayload.STOP, JoeTerminalActionPayload.RESUME,
                        JoeTerminalActionPayload.SECURITY, JoeTerminalActionPayload.CLEAR_ALARM };
                int bw = rw / names.length;
                for (int i = 0; i < names.length; i++)
                {
                    int act = acts[i];
                    addRenderableWidget(new TermButton(rightX + i * bw, by, bw - 2, 14, names[i], () -> send(act, ""), false));
                }
                renameBox = term(new EditBox(font, rightX, by - 18, rw - 70, 14, Component.literal("name")));
                renameBox.setMaxLength(24);
                renameBox.setHint(Component.literal("new name").withColor(DIM));
                addRenderableWidget(renameBox);
                addRenderableWidget(new TermButton(rightX + rw - 66, by - 18, 66, 14, "RENAME", () -> send(JoeTerminalActionPayload.RENAME, renameBox.getValue()), false));
            }
            case 1 ->
            {
                editor = new TermEditor(font, rightX, contentY, rw, by - contentY - 20);
                editor.setCharacterLimit(16_000);
                editor.setValue(editorText);
                addRenderableWidget(editor);
                int bw = rw / 5;
                addRenderableWidget(new TermButton(rightX, by - 16, bw - 2, 14, "FETCH", () -> send(JoeTerminalActionPayload.FETCH_SOURCE, ""), false));
                addRenderableWidget(new TermButton(rightX + bw, by - 16, bw - 2, 14, "CHECK", () -> send(JoeTerminalActionPayload.CHECK, editor.getValue()), false));
                addRenderableWidget(new TermButton(rightX + bw * 2, by - 16, bw - 2, 14, "UPLOAD", () -> send(JoeTerminalActionPayload.UPLOAD, editor.getValue()), false));
                addRenderableWidget(new TermButton(rightX + bw * 3, by - 16, bw - 2, 14, "TO ALL", () -> send(JoeTerminalActionPayload.UPLOAD_ALL, "", editor.getValue()), false));
                addRenderableWidget(new TermButton(rightX + bw * 4, by - 16, bw - 2, 14, "CLEAR", () -> editor.setValue(""), false));
                nameBox = term(new EditBox(font, rightX, by + 1, rw - 70, 14, Component.literal("file")));
                nameBox.setMaxLength(32);
                nameBox.setValue(fileName);
                nameBox.setHint(Component.literal("file name").withColor(DIM));
                addRenderableWidget(nameBox);
                addRenderableWidget(new TermButton(rightX + rw - 66, by + 1, 66, 14, "SAVE", () ->
                        send(JoeTerminalActionPayload.SAVE_FILE, nameBox.getValue().trim() + "\n" + editor.getValue()), false));
            }
            case 2 ->
            {
                int bw = rw / 4;
                addRenderableWidget(new TermButton(rightX, by, bw - 2, 14, "OPEN", () -> send(JoeTerminalActionPayload.LOAD_FILE, libraryPick), false));
                addRenderableWidget(new TermButton(rightX + bw, by, bw - 2, 14, "RUN", () -> runFile(false), false));
                addRenderableWidget(new TermButton(rightX + bw * 2, by, bw - 2, 14, "RUN ALL", () -> runFile(true), false));
                addRenderableWidget(new TermButton(rightX + bw * 3, by, bw - 2, 14, "DELETE", () ->
                {
                    if (!libraryPick.contains(":"))
                    {
                        send(JoeTerminalActionPayload.DELETE_FILE, libraryPick);
                    }
                }, false));
            }
            case 3 ->
            {
                int gx = rightX + 128, gy = contentY + 4, s = 22;
                addRenderableWidget(new TermButton(gx + s, gy, s - 2, s - 2, "^", () -> drive("fwd"), false));
                addRenderableWidget(new TermButton(gx, gy + s, s - 2, s - 2, "<", () -> drive("left"), false));
                addRenderableWidget(new TermButton(gx + s, gy + s, s - 2, s - 2, "o", () -> drive("stop"), false));
                addRenderableWidget(new TermButton(gx + s * 2, gy + s, s - 2, s - 2, ">", () -> drive("right"), false));
                addRenderableWidget(new TermButton(gx + s, gy + s * 2, s - 2, s - 2, "v", () -> drive("back"), false));
                addRenderableWidget(new TermButton(gx + s * 3 + 4, gy, 44, s - 2, "JUMP", () -> drive("jump"), false));
                String[] acts = { "DIG", "DIG UP", "DIG DN", "USE", "ATTACK", "PICKUP", "DROP", "RELEASE" };
                String[] cmds = { "dig", "digup", "digdown", "use", "attack", "pickup", "drop", "release" };
                for (int i = 0; i < acts.length; i++)
                {
                    String cmd = cmds[i];
                    addRenderableWidget(new TermButton(gx + (i % 4) * 48, gy + s * 3 + 6 + (i / 4) * 16, 46, 14, acts[i], () -> drive(cmd), false));
                }
                placeBox = term(new EditBox(font, gx, gy + s * 3 + 40, 140, 14, Component.literal("item")));
                placeBox.setHint(Component.literal("block to place").withColor(DIM));
                addRenderableWidget(placeBox);
                addRenderableWidget(new TermButton(gx + 144, gy + s * 3 + 40, 46, 14, "PLACE", () -> drive("place " + placeBox.getValue().trim()), false));
            }
            case 4 ->
            {
                sendBox = term(new EditBox(font, rightX, by, rw - 140, 14, Component.literal("message")));
                sendBox.setMaxLength(200);
                sendBox.setHint(Component.literal("channel message").withColor(DIM));
                addRenderableWidget(sendBox);
                addRenderableWidget(new TermButton(rightX + rw - 136, by, 66, 14, "SEND", () -> send(JoeTerminalActionPayload.SEND, sendBox.getValue()), false));
                addRenderableWidget(new TermButton(rightX + rw - 68, by, 68, 14, "SEND ALL", () -> send(JoeTerminalActionPayload.SEND, "", sendBox.getValue()), false));
            }
            case 5 ->
            {
                searchBox = term(new EditBox(font, rightX, contentY, rw, 12, Component.literal("search")));
                searchBox.setHint(Component.literal("search manifest").withColor(DIM));
                searchBox.setValue(search);
                addRenderableWidget(searchBox);
                countBox = term(new EditBox(font, rightX, by - 18, 40, 14, Component.literal("count")));
                countBox.setValue("64");
                countBox.setMaxLength(3);
                addRenderableWidget(countBox);
                addRenderableWidget(new TermButton(rightX + 44, by - 18, 70, 14, "TO ME", () ->
                        send(JoeTerminalActionPayload.APOLLO_REQUEST, apolloPick + " " + countBox.getValue().trim()), false));
                stationBox = term(new EditBox(font, rightX, by, rw - 74, 14, Component.literal("station")));
                stationBox.setHint(Component.literal("PneuPost station").withColor(DIM));
                stationBox.setMaxLength(20);
                addRenderableWidget(stationBox);
                addRenderableWidget(new TermButton(rightX + rw - 70, by, 70, 14, "DISPATCH", () ->
                        send(JoeTerminalActionPayload.APOLLO_DISPATCH, apolloPick + " " + countBox.getValue().trim() + " "
                                + stationBox.getValue().trim().replace(' ', '_')), false));
            }
            default ->
            {
            }
        }
    }

    private List<CompoundTag> apolloItems()
    {
        List<CompoundTag> out = new ArrayList<>();
        ListTag items = data.getCompound("Apollo").getList("Items", Tag.TAG_COMPOUND);
        String q = searchBox != null ? searchBox.getValue().toLowerCase(java.util.Locale.ROOT) : search.toLowerCase(java.util.Locale.ROOT);
        for (int i = 0; i < items.size(); i++)
        {
            CompoundTag t = items.getCompound(i);
            if (q.isEmpty() || t.getString("Name").toLowerCase(java.util.Locale.ROOT).contains(q) || t.getString("Id").contains(q))
            {
                out.add(t);
            }
        }
        return out;
    }

    private void drawApollo(GuiGraphics g, Font f, int rw)
    {
        CompoundTag a = data.getCompound("Apollo");
        if (a.isEmpty())
        {
            g.drawString(f, "NO APOLLO CORE ON THIS NETWORK", rightX, contentY + 20, DIM, false);
            return;
        }
        long stored = a.getLong("Stored"), cap = Math.max(1, a.getLong("Capacity"));
        String head = (a.getBoolean("Online") ? "APOLLO ONLINE" : "APOLLO OFFLINE") + "  " + String.format("%,d/%,d", stored, cap) + "  "
                + a.getInt("Types") + "/" + a.getInt("TypeLimit") + " types  " + a.getInt("Jobs") + " jobs";
        g.drawString(f, f.plainSubstrByWidth(head, rw), rightX, contentY + 15, a.getBoolean("Online") ? GREEN : RED, false);
        g.fill(rightX, contentY + 25, rightX + rw, contentY + 27, FAINT);
        g.fill(rightX, contentY + 25, rightX + (int) (rw * stored / cap), contentY + 27, AMBER);
        List<CompoundTag> items = apolloItems();
        int y = contentY + 30, rows = (top + h - 66 - y) / 10;
        apolloScroll = Math.max(0, Math.min(apolloScroll, Math.max(0, items.size() - rows)));
        for (int i = apolloScroll; i < items.size() && i < apolloScroll + rows; i++, y += 10)
        {
            CompoundTag t = items.get(i);
            boolean pick = t.getString("Id").equals(apolloPick);
            if (pick)
            {
                g.fill(rightX, y - 1, rightX + rw, y + 9, 0xFF241A06);
            }
            String count = String.format("%,d", t.getLong("Count"));
            g.drawString(f, f.plainSubstrByWidth(t.getString("Name"), rw - f.width(count) - 6), rightX + 2, y, pick ? AMBER_HI : AMBER, false);
            g.drawString(f, count, rightX + rw - f.width(count), y, DIM, false);
        }
        ListTag log = a.getList("Log", Tag.TAG_STRING);
        if (!log.isEmpty())
        {
            g.drawString(f, f.plainSubstrByWidth("MU/TH/UR: " + log.getString(log.size() - 1), rw), rightX, top + h - 64, GREEN, false);
        }
    }

    private void runFile(boolean all)
    {
        if (libraryPick.isEmpty())
        {
            return;
        }
        // load it into the editor first, then upload what comes back
        pendingRun = all ? 2 : 1;
        send(JoeTerminalActionPayload.LOAD_FILE, libraryPick);
    }

    private int pendingRun;

    private void drive(String command)
    {
        send(JoeTerminalActionPayload.DRIVE, command);
    }

    private EditBox term(EditBox box)
    {
        box.setTextColor(AMBER_HI);
        box.setTextColorUneditable(DIM);
        return box;
    }

    @Override
    public void tick()
    {
        super.tick();
        if (++ticks % 10 == 0)
        {
            send(JoeTerminalActionPayload.REFRESH, "");
        }
        if (pendingRun > 0 && data.contains("Editor") && editor != null)
        {
            send(pendingRun == 2 ? JoeTerminalActionPayload.UPLOAD_ALL : JoeTerminalActionPayload.UPLOAD, pendingRun == 2 ? "" : selected, editor.getValue());
            pendingRun = 0;
            data.remove("Editor");
        }
    }

    // ------------------------------------------------------------------ input

    @Override
    public boolean mouseClicked(double mx, double my, int button)
    {
        // unit list
        ListTag units = data.getList("Units", Tag.TAG_COMPOUND);
        int listTop = top + 30;
        if (mx >= left + 4 && mx < left + 106 && my >= listTop && my < top + h - 26)
        {
            int row = (int) (my - listTop) / 22 + unitScroll;
            if (row >= 0 && row < units.size())
            {
                selected = units.getCompound(row).getString("Id");
                send(JoeTerminalActionPayload.REFRESH, "");
                return true;
            }
        }
        if (tab == 5 && mx >= rightX && mx < left + w - 6 && my >= contentY + 30 && my < top + h - 66)
        {
            int row = (int) (my - contentY - 30) / 10 + apolloScroll;
            List<CompoundTag> items = apolloItems();
            if (row >= 0 && row < items.size())
            {
                apolloPick = items.get(row).getString("Id");
                return true;
            }
        }
        if (tab == 2)
        {
            ListTag files = data.getList("Files", Tag.TAG_STRING);
            if (mx >= rightX && mx < left + w - 6 && my >= contentY && my < top + h - 46)
            {
                int row = (int) (my - contentY) / 11 + fileScroll;
                if (row >= 0 && row < files.size())
                {
                    libraryPick = files.getString(row);
                    return true;
                }
            }
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double sx, double sy)
    {
        if (mx < left + 106)
        {
            unitScroll = Math.max(0, unitScroll - (int) Math.signum(sy));
            return true;
        }
        if (tab == 2)
        {
            fileScroll = Math.max(0, fileScroll - (int) Math.signum(sy));
            return true;
        }
        if (tab == 5)
        {
            apolloScroll = Math.max(0, apolloScroll - (int) Math.signum(sy));
            return true;
        }
        return super.mouseScrolled(mx, my, sx, sy);
    }

    @Override
    public boolean keyPressed(int key, int scan, int mods)
    {
        boolean typing = getFocused() instanceof EditBox || getFocused() instanceof MultiLineEditBox;
        if (tab == 3 && !typing)
        {
            switch (key)
            {
                case 87 /* W */, 265 /* UP */ -> drive("fwd");
                case 83 /* S */, 264 /* DOWN */ -> drive("back");
                case 65 /* A */, 263 /* LEFT */ -> drive("left");
                case 68 /* D */, 262 /* RIGHT */ -> drive("right");
                case 32 /* SPACE */ -> drive("jump");
                case 81 /* Q */ -> drive("dig");
                case 69 /* E */ -> drive("use");
                case 70 /* F */ -> drive("attack");
                case 88 /* X */ -> drive("stop");
                default ->
                {
                    return super.keyPressed(key, scan, mods);
                }
            }
            return true;
        }
        return super.keyPressed(key, scan, mods);
    }

    // ------------------------------------------------------------------ drawing

    @Override
    public void renderBackground(GuiGraphics g, int mx, int my, float pt)
    {
        g.fill(0, 0, width, height, 0xC0000000);
        g.fill(left - 3, top - 3, left + w + 3, top + h + 3, 0xFF1A1A16); // bezel
        g.fill(left - 1, top - 1, left + w + 1, top + h + 1, DIM);
        g.fill(left, top, left + w, top + h, BG);
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float pt)
    {
        super.render(g, mx, my, pt);
        Font f = font;
        boolean server = data.getBoolean("Server"), power = data.getBoolean("Power");
        // header
        g.drawString(f, "SEEGSON SYNTHETICS", left + 6, top + 5, AMBER_HI, false);
        g.drawString(f, "// WORKING JOE NETWORK", left + 6 + f.width("SEEGSON SYNTHETICS "), top + 5, DIM, false);
        String link = !power ? "NO POWER" : server ? "APOLLO LINK: ONLINE" : tablet ? "APOLLO LINK: OFFLINE" : "LOCAL MODE (64m)";
        int linkColour = !power || tablet && !server ? RED : server ? GREEN : AMBER;
        g.drawString(f, link, left + w - 6 - f.width(link), top + 5, linkColour, false);
        g.fill(left + 4, top + 15, left + w - 4, top + 16, FAINT);
        if (data.contains("Alarm") && (ticks / 8) % 2 == 0)
        {
            String alarm = "!! ALERT @ " + data.getString("Alarm") + " !!";
            g.drawString(f, alarm, left + (w - f.width(alarm)) / 2, top + h - 11, RED, false);
        }
        drawUnits(g, f, mx, my);
        CompoundTag sel = data.getCompound("Selected");
        int rw = left + w - 6 - rightX;
        switch (tab)
        {
            case 0 -> drawStatus(g, f, sel, rw);
            case 2 -> drawLibrary(g, f, rw, mx, my);
            case 3 -> drawRadar(g, f, sel);
            case 4 -> drawLog(g, f, sel, rw);
            case 5 -> drawApollo(g, f, rw);
            default ->
            {
            }
        }
        // messages
        int my2 = top + h - 24;
        for (int i = 0; i < Math.min(2, messages.size()); i++)
        {
            String m = messages.get(messages.size() - 1 - i);
            boolean bad = m.startsWith("!");
            g.drawString(f, f.plainSubstrByWidth("> " + (bad ? m.substring(1) : m), w - 120), rightX, my2 - i * 9 + (tab == 1 ? 18 : 0) - (tab == 1 ? 0 : 0),
                    bad ? RED : GREEN, false);
            if (tab == 1)
            {
                break;
            }
        }
        // CRT: scanlines and a soft flicker
        int flicker = (ticks % 47 == 0) ? 0x30 : 0x18;
        for (int y = top; y < top + h; y += 2)
        {
            g.fill(left, y, left + w, y + 1, flicker << 24);
        }
        g.fill(left, top, left + w, top + 2, 0x40FFB000 & 0x22FFFFFF);
    }

    private void drawUnits(GuiGraphics g, Font f, int mx, int my)
    {
        ListTag units = data.getList("Units", Tag.TAG_COMPOUND);
        g.drawString(f, "UNITS: " + units.size(), left + 6, top + 20, DIM, false);
        g.fill(left + 108, top + 18, left + 109, top + h - 6, FAINT);
        int y = top + 30;
        for (int i = unitScroll; i < units.size() && y < top + h - 30; i++, y += 22)
        {
            CompoundTag u = units.getCompound(i);
            boolean sel = u.getString("Id").equals(selected);
            byte mode = u.getByte("Mode");
            int colour = mode == 2 ? RED : mode == 1 ? DIM : u.getInt("Charge") < 20 ? 0xFFFF7A30 : AMBER;
            if (sel)
            {
                g.fill(left + 4, y - 1, left + 106, y + 20, 0xFF241A06);
                g.fill(left + 4, y - 1, left + 5, y + 20, AMBER);
            }
            g.drawString(f, f.plainSubstrByWidth(u.getString("Name"), 70), left + 8, y, colour, false);
            g.drawString(f, u.getInt("Charge") + "%", left + 104 - f.width(u.getInt("Charge") + "%"), y, colour, false);
            String status = mode == 2 ? "FAULT" : u.getBoolean("Remote") ? "REMOTE" : u.getString("Status");
            g.drawString(f, f.plainSubstrByWidth(status, 96), left + 8, y + 10, DIM, false);
        }
        if (units.isEmpty())
        {
            g.drawString(f, "NO UNITS", left + 8, top + 34, DIM, false);
            g.drawString(f, "IN RANGE", left + 8, top + 44, DIM, false);
        }
    }

    private void drawStatus(GuiGraphics g, Font f, CompoundTag sel, int rw)
    {
        ListTag lines = sel.getList("Status", Tag.TAG_STRING);
        int y = contentY;
        if (lines.isEmpty())
        {
            g.drawString(f, "SELECT A UNIT", rightX, y, DIM, false);
            return;
        }
        for (int i = 0; i < lines.size() && y < top + h - 80; i++, y += 10)
        {
            g.drawString(f, f.plainSubstrByWidth(lines.getString(i), rw), rightX, y, i == 0 ? AMBER_HI : AMBER, false);
        }
        // battery bar
        CompoundTag unit = unit(sel.getString("Id"));
        if (unit != null)
        {
            int charge = unit.getInt("Charge");
            y += 2;
            g.drawString(f, "PWR", rightX, y, DIM, false);
            g.fill(rightX + 22, y, rightX + 22 + 102, y + 7, FAINT);
            g.fill(rightX + 23, y + 1, rightX + 23 + charge, y + 6, charge < 20 ? RED : AMBER);
            g.drawString(f, unit.getString("Dim").toUpperCase(), rightX + 130, y, DIM, false);
            y += 12;
        }
        ListTag vars = sel.getList("Vars", Tag.TAG_STRING);
        if (!vars.isEmpty())
        {
            g.drawString(f, "VARIABLES", rightX, y, DIM, false);
            y += 10;
            int col = 0;
            for (int i = 0; i < vars.size() && y < top + h - 64; i++)
            {
                g.drawString(f, f.plainSubstrByWidth(vars.getString(i), rw / 2 - 4), rightX + col * (rw / 2), y, AMBER, false);
                if (++col == 2)
                {
                    col = 0;
                    y += 9;
                }
            }
        }
    }

    private CompoundTag unit(String id)
    {
        ListTag units = data.getList("Units", Tag.TAG_COMPOUND);
        for (int i = 0; i < units.size(); i++)
        {
            if (units.getCompound(i).getString("Id").equals(id))
            {
                return units.getCompound(i);
            }
        }
        return null;
    }

    private void drawLibrary(GuiGraphics g, Font f, int rw, int mx, int my)
    {
        ListTag files = data.getList("Files", Tag.TAG_STRING);
        if (!data.getBoolean("Server"))
        {
            g.drawString(f, "NETWORK SERVER OFFLINE - only built-in programs", rightX, top + h - 58, DIM, false);
        }
        int y = contentY;
        for (int i = fileScroll; i < files.size() && y < top + h - 62; i++, y += 11)
        {
            String name = files.getString(i);
            boolean pick = name.equals(libraryPick);
            if (pick)
            {
                g.fill(rightX, y - 1, rightX + rw, y + 9, 0xFF241A06);
            }
            g.drawString(f, (name.contains(":") ? "[ROM] " : "      ") + name, rightX + 2, y, pick ? AMBER_HI : name.contains(":") ? DIM : AMBER, false);
        }
        if (files.isEmpty())
        {
            g.drawString(f, "NO PROGRAMS", rightX, y, DIM, false);
        }
    }

    private void drawRadar(GuiGraphics g, Font f, CompoundTag sel)
    {
        int[] cells = sel.getIntArray("Radar");
        int size = 17, cell = 7, x0 = rightX, y0 = contentY + 2;
        g.fill(x0 - 2, y0 - 2, x0 + size * cell + 2, y0 + size * cell + 2, DIM);
        g.fill(x0 - 1, y0 - 1, x0 + size * cell + 1, y0 + size * cell + 1, 0xFF000000);
        if (cells.length == size * size)
        {
            for (int i = 0; i < cells.length; i++)
            {
                int c = cells[i];
                // tint everything amber, like a monochrome CRT
                int lum = ((c >> 16 & 255) * 3 + (c >> 8 & 255) * 6 + (c & 255)) / 10;
                int tinted = 0xFF000000 | (lum * 255 / 255) << 16 | (lum * 176 / 255) << 8;
                g.fill(x0 + i % size * cell, y0 + i / size * cell, x0 + i % size * cell + cell - 1, y0 + i / size * cell + cell - 1, tinted);
            }
            ListTag blips = sel.getList("Blips", Tag.TAG_COMPOUND);
            for (int i = 0; i < blips.size(); i++)
            {
                CompoundTag b = blips.getCompound(i);
                int bx = x0 + (int) ((b.getFloat("X") + 8) * cell), bz = y0 + (int) ((b.getFloat("Z") + 8) * cell);
                int colour = switch (b.getString("K"))
                {
                    case "hostile" -> RED;
                    case "player" -> 0xFFFFFFFF;
                    case "joe" -> AMBER_HI;
                    case "item" -> 0xFFFFFF60;
                    default -> GREEN;
                };
                if ((ticks / 4) % 2 == 0 || !b.getString("K").equals("hostile"))
                {
                    g.fill(bx - 1, bz - 1, bx + 2, bz + 2, colour);
                }
            }
            // the Joe itself, with a heading tick
            int cx = x0 + 8 * cell + cell / 2, cz = y0 + 8 * cell + cell / 2;
            g.fill(cx - 2, cz - 2, cx + 3, cz + 3, 0xFFFFFFFF);
            double yaw = Math.toRadians(sel.getFloat("Yaw"));
            int hx = cx + (int) Math.round(-Math.sin(yaw) * 6), hz = cz + (int) Math.round(Math.cos(yaw) * 6);
            g.fill(hx - 1, hz - 1, hx + 2, hz + 2, 0xFFFFFFFF);
        }
        else
        {
            g.drawString(f, "NO SIGNAL", x0 + 34, y0 + 56, DIM, false);
        }
        g.drawString(f, "WASD drive  Q dig  E use  F attack  X stop", rightX, y0 + size * cell + 6, DIM, false);
    }

    private void drawLog(GuiGraphics g, Font f, CompoundTag sel, int rw)
    {
        ListTag log = sel.getList("Log", Tag.TAG_STRING);
        int lines = (top + h - 48 - contentY) / 9;
        int start = Math.max(0, log.size() - lines);
        int y = contentY;
        for (int i = start; i < log.size(); i++, y += 9)
        {
            String line = log.getString(i);
            g.drawString(f, f.plainSubstrByWidth(line, rw), rightX, y, line.contains("ERROR") ? RED : line.contains("REPORT") ? AMBER_HI : AMBER, false);
        }
        if (log.isEmpty())
        {
            g.drawString(f, "LOG EMPTY - use print \"...\" in a program", rightX, y, DIM, false);
        }
    }

    // ------------------------------------------------------------------ widgets

    /** An amber outline button. */
    static class TermButton extends AbstractButton
    {
        private final Runnable action;
        private final boolean active;

        TermButton(int x, int y, int w, int h, String label, Runnable action, boolean active)
        {
            super(x, y, w, h, Component.literal(label));
            this.action = action;
            this.active = active;
        }

        @Override
        public void onPress()
        {
            action.run();
        }

        @Override
        protected void renderWidget(GuiGraphics g, int mx, int my, float pt)
        {
            boolean hot = isHoveredOrFocused();
            int border = active || hot ? AMBER : DIM;
            g.fill(getX(), getY(), getX() + width, getY() + height, border);
            g.fill(getX() + 1, getY() + 1, getX() + width - 1, getY() + height - 1, active ? 0xFF3A2800 : hot ? 0xFF241A06 : BG);
            var font = net.minecraft.client.Minecraft.getInstance().font;
            String text = getMessage().getString();
            g.drawString(font, text, getX() + (width - font.width(text)) / 2, getY() + (height - 8) / 2, active ? AMBER_HI : AMBER, false);
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput out)
        {
            defaultButtonNarrationText(out);
        }
    }

    /** The program editor, on a black screen with an amber frame. */
    static class TermEditor extends MultiLineEditBox
    {
        TermEditor(Font font, int x, int y, int w, int h)
        {
            super(font, x, y, w, h, Component.literal("# write your program here"), Component.literal("program"));
        }

        @Override
        protected void renderBackground(GuiGraphics g)
        {
            g.fill(getX(), getY(), getX() + width, getY() + height, isFocused() ? AMBER : DIM);
            g.fill(getX() + 1, getY() + 1, getX() + width - 1, getY() + height - 1, 0xFF050603);
        }
    }
}
