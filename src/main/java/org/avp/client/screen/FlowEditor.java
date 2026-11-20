package org.avp.client.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

/**
 * Seegson Flow: JOE-SCRIPT as blocks. Every block is one line of JOE-SCRIPT, made from a template
 * like "dig {xyz}" or "haul {xyz} -> {xyz} {item?} {n?}", so programs convert both ways without
 * loss (anything it doesn't recognise becomes a RAW block holding the line as written).
 */
public class FlowEditor
{
    /** A kind of block. Template placeholders: {xyz} 3 words, {word}/{item}/{n} 1 word, {rest} the rest; '?' = optional. */
    public record Spec(String cat, String label, String template, String[] defaults, int shape)
    {
        static final int PLAIN = 0, OPEN = 1, MIDDLE = 2, CLOSE = 3;
    }

    public static final String[] CATS = { "MOVE", "WORK", "SENSE", "TALK", "LOGIC", "EVENT" };
    static final int[] CAT_COLOURS = { 0xFF3D7BD8, 0xFFE07A20, 0xFF3FA84A, 0xFF9A5AD0, 0xFFD8A020, 0xFFD04040, 0xFF707070 };

    static final List<Spec> SPECS = List.of(
            new Spec("MOVE", "GO TO", "goto {xyz}", new String[] { "~ ~ ~5" }, Spec.PLAIN),
            new Spec("MOVE", "GO HOME", "goto home", new String[0], Spec.PLAIN),
            new Spec("MOVE", "GO TO ALARM", "goto alarm", new String[0], Spec.PLAIN),
            new Spec("MOVE", "PATROL", "patrol {rest}", new String[] { "~5 ~ ~ ; ~5 ~ ~5 ; ~ ~ ~5 ; ~ ~ ~" }, Spec.PLAIN),
            new Spec("MOVE", "FOLLOW ME", "follow {n?}", new String[] { "" }, Spec.PLAIN),
            new Spec("MOVE", "GUARD", "guard {xyz} {n?} {n?}", new String[] { "~ ~ ~", "8", "" }, Spec.PLAIN),
            new Spec("MOVE", "FACE", "face {xyz}", new String[] { "~ ~ ~1" }, Spec.PLAIN),
            new Spec("MOVE", "RECHARGE", "charge", new String[0], Spec.PLAIN),
            new Spec("WORK", "DIG", "dig {xyz}", new String[] { "~ ~-1 ~1" }, Spec.PLAIN),
            new Spec("WORK", "PLACE", "place {item} {xyz}", new String[] { "cobblestone", "~ ~ ~1" }, Spec.PLAIN),
            new Spec("WORK", "USE", "use {xyz} {item?}", new String[] { "~ ~ ~1", "" }, Spec.PLAIN),
            new Spec("WORK", "TAKE", "take {xyz} {item?} {n?}", new String[] { "~2 ~ ~", "", "" }, Spec.PLAIN),
            new Spec("WORK", "PUT", "put {xyz} {item?} {n?}", new String[] { "~2 ~ ~", "", "" }, Spec.PLAIN),
            new Spec("WORK", "DROP", "drop {item?} {n?}", new String[] { "", "" }, Spec.PLAIN),
            new Spec("WORK", "PICK UP", "pickup {n?}", new String[] { "6" }, Spec.PLAIN),
            new Spec("WORK", "CRAFT", "craft {item} {n?}", new String[] { "stick", "4" }, Spec.PLAIN),
            new Spec("WORK", "EQUIP", "equip {item}", new String[] { "iron_pickaxe" }, Spec.PLAIN),
            new Spec("WORK", "HAUL", "haul {xyz} -> {xyz} {item?} {n?}", new String[] { "~-2 ~ ~", "~2 ~ ~", "", "" }, Spec.PLAIN),
            new Spec("WORK", "FARM", "farm {xyz} {xyz} -> {xyz}", new String[] { "~-4 ~-1 ~-4", "~4 ~ ~4", "~ ~ ~-6" }, Spec.PLAIN),
            new Spec("WORK", "LEVER", "lever {xyz} {word}", new String[] { "~ ~ ~1", "on" }, Spec.PLAIN),
            new Spec("WORK", "MAINTAIN", "maintain {xyz} {xyz} <- {xyz}", new String[] { "~-16 ~-4 ~-16", "~16 ~8 ~16", "~2 ~ ~" }, Spec.PLAIN),
            new Spec("WORK", "MAINTAIN", "maintain {xyz} {xyz}", new String[] { "~-16 ~-4 ~-16", "~16 ~8 ~16" }, Spec.PLAIN),
            new Spec("WORK", "COURIER", "courier", new String[0], Spec.PLAIN),
            new Spec("WORK", "STORE (APOLLO)", "store", new String[0], Spec.PLAIN),
            new Spec("SENSE", "SCAN BLOCKS", "scan {word} {n?}", new String[] { "iron_ore", "16" }, Spec.PLAIN),
            new Spec("SENSE", "FIND", "find {word} {n?}", new String[] { "hostile", "12" }, Spec.PLAIN),
            new Spec("SENSE", "INSPECT", "inspect {xyz}", new String[] { "~ ~-1 ~" }, Spec.PLAIN),
            new Spec("TALK", "SAY", "say {rest}", new String[] { "\"Hello.\"" }, Spec.PLAIN),
            new Spec("TALK", "REPORT", "report {rest}", new String[] { "\"Job done.\"" }, Spec.PLAIN),
            new Spec("TALK", "LOG", "print {rest}", new String[] { "\"x = $x\"" }, Spec.PLAIN),
            new Spec("TALK", "SEND", "send {word} {rest}", new String[] { "ops", "\"ready\"" }, Spec.PLAIN),
            new Spec("TALK", "ALARM", "alarm", new String[0], Spec.PLAIN),
            new Spec("TALK", "SECURITY", "security {word}", new String[] { "on" }, Spec.PLAIN),
            new Spec("LOGIC", "IF", "if {rest}", new String[] { "night" }, Spec.OPEN),
            new Spec("LOGIC", "UNLESS", "unless {rest}", new String[] { "full" }, Spec.OPEN),
            new Spec("LOGIC", "ELSE IF", "elif {rest}", new String[] { "day" }, Spec.MIDDLE),
            new Spec("LOGIC", "ELSE", "else", new String[0], Spec.MIDDLE),
            new Spec("LOGIC", "WHILE", "while {rest}", new String[] { "not full" }, Spec.OPEN),
            new Spec("LOGIC", "REPEAT", "repeat {rest}", new String[] { "4" }, Spec.OPEN),
            new Spec("LOGIC", "FOREVER", "forever", new String[0], Spec.OPEN),
            new Spec("LOGIC", "FOR", "for {word} = {word} to {rest}", new String[] { "i", "1", "10" }, Spec.OPEN),
            new Spec("LOGIC", "END", "end", new String[0], Spec.CLOSE),
            new Spec("LOGIC", "BREAK", "break", new String[0], Spec.PLAIN),
            new Spec("LOGIC", "SET", "set {word} = {rest}", new String[] { "n", "n + 1" }, Spec.PLAIN),
            new Spec("LOGIC", "WAIT", "wait {n}", new String[] { "5" }, Spec.PLAIN),
            new Spec("LOGIC", "STOP", "stop", new String[0], Spec.PLAIN),
            new Spec("LOGIC", "LABEL", "label {word}", new String[] { "top" }, Spec.PLAIN),
            new Spec("LOGIC", "JUMP", "jump {word}", new String[] { "top" }, Spec.PLAIN),
            new Spec("LOGIC", "IF ... DO", "if {rest}", new String[] { "night: say \"Lights on.\"" }, Spec.PLAIN),
            new Spec("EVENT", "WHEN", "when {word}: {rest}", new String[] { "lowpower", "charge" }, Spec.PLAIN),
            new Spec("EVENT", "FUNCTION", "func {word}", new String[] { "job" }, Spec.OPEN),
            new Spec("EVENT", "CALL", "call {word}", new String[] { "job" }, Spec.PLAIN),
            new Spec("EVENT", "RETURN", "return", new String[0], Spec.PLAIN),
            new Spec("EVENT", "NAME", "name {rest}", new String[] { "Joe-01" }, Spec.PLAIN),
            new Spec("EVENT", "HOME", "home {xyz}", new String[] { "~ ~ ~" }, Spec.PLAIN));
    static final Spec RAW = new Spec("RAW", "CODE", "{rest}", new String[] { "# comment" }, Spec.PLAIN);

    /** A block in the program. */
    public static final class Block
    {
        final Spec spec;
        final List<String> params = new ArrayList<>();
        int line;

        Block(Spec spec, List<String> params)
        {
            this.spec = spec;
            this.params.addAll(params);
            while (this.params.size() < placeholders(spec).size())
            {
                this.params.add("");
            }
        }

        String text()
        {
            StringBuilder out = new StringBuilder();
            int p = 0;
            for (String part : spec.template.split(" "))
            {
                String piece;
                if (part.startsWith("{"))
                {
                    piece = p < params.size() ? params.get(p).trim() : "";
                    p++;
                    if (part.endsWith(":"))
                    {
                        piece += ":";
                    }
                }
                else
                {
                    piece = part;
                }
                if (!piece.isEmpty())
                {
                    out.append(out.isEmpty() ? "" : " ").append(piece);
                }
            }
            return out.toString();
        }
    }

    final List<Block> blocks = new ArrayList<>();
    int selected = -1;
    int scroll;
    String category = "MOVE";

    static List<String> placeholders(Spec spec)
    {
        List<String> list = new ArrayList<>();
        for (String part : spec.template.split(" "))
        {
            if (part.startsWith("{"))
            {
                list.add(part.substring(1, part.indexOf('}')));
            }
        }
        return list;
    }

    // ------------------------------------------------------------------ text <-> blocks

    public String toText()
    {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < blocks.size(); i++)
        {
            Block b = blocks.get(i);
            b.line = i + 1;
            out.append("  ".repeat(depthOf(i))).append(b.text()).append('\n');
        }
        return out.toString();
    }

    public void fromText(String text)
    {
        blocks.clear();
        String[] lines = text.replace("\r", "").split("\n");
        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[i].trim();
            if (line.isEmpty())
            {
                continue;
            }
            Block b = parse(line);
            b.line = i + 1;
            blocks.add(b);
        }
        selected = -1;
        scroll = 0;
    }

    static Block parse(String line)
    {
        String lower = line.toLowerCase(Locale.ROOT);
        boolean oneLiner = (lower.startsWith("if ") || lower.startsWith("unless ") || lower.startsWith("while ")) && hasSeparator(line);
        if (oneLiner && lower.startsWith("if "))
        {
            Spec doSpec = SPECS.stream().filter(sp -> sp.label.equals("IF ... DO")).findFirst().get();
            return new Block(doSpec, List.of(line.substring(3).trim()));
        }
        if (!oneLiner && !line.startsWith("#") && !line.startsWith("//"))
        {
            // specs with more fixed words first ("goto home" before "goto X Y Z")
            List<Spec> candidates = new ArrayList<>(SPECS);
            candidates.sort((a, b) -> Long.compare(literals(b), literals(a)));
            for (Spec spec : candidates)
            {
                List<String> params = match(spec, line);
                if (params != null)
                {
                    return new Block(spec, params);
                }
            }
        }
        return new Block(RAW, List.of(line));
    }

    private static boolean hasSeparator(String line)
    {
        boolean quoted = false;
        for (int i = 0; i < line.length(); i++)
        {
            char c = line.charAt(i);
            if (c == '"')
            {
                quoted = !quoted;
            }
            else if (!quoted && c == ':' && (i + 1 == line.length() || Character.isWhitespace(line.charAt(i + 1))))
            {
                return true;
            }
        }
        return false;
    }

    private static long literals(Spec spec)
    {
        return java.util.Arrays.stream(spec.template.split(" ")).filter(p -> !p.startsWith("{")).count();
    }

    /** Fits a line to a template; returns the parameters, or null if it doesn't fit. */
    static List<String> match(Spec spec, String line)
    {
        String[] parts = spec.template.split(" ");
        String spaced = line.replace("->", " -> ").replace("<-", " <- ").trim();
        List<String> words = new ArrayList<>(List.of(spaced.split("\\s+")));
        if (spec.template.startsWith("when "))
        {
            // when EVENT: ACTION
            int colon = line.indexOf(':');
            if (!line.toLowerCase(Locale.ROOT).startsWith("when ") || colon < 0)
            {
                return null;
            }
            return List.of(line.substring(5, colon).trim(), line.substring(colon + 1).trim());
        }
        List<String> params = new ArrayList<>();
        int w = 0;
        for (int i = 0; i < parts.length; i++)
        {
            String part = parts[i];
            if (!part.startsWith("{"))
            {
                if (w >= words.size() || !words.get(w).equalsIgnoreCase(part))
                {
                    return null;
                }
                w++;
                continue;
            }
            String kind = part.substring(1, part.indexOf('}'));
            boolean optional = kind.endsWith("?");
            kind = kind.replace("?", "");
            if (kind.equals("rest"))
            {
                if (w >= words.size() && !optional)
                {
                    return null;
                }
                // the rest of the line as written (keeps quoted spacing)
                int idx = nthWordStart(spaced, w);
                params.add(idx < 0 ? "" : spaced.substring(idx).trim());
                w = words.size();
                continue;
            }
            int n = kind.equals("xyz") ? 3 : 1;
            if (w + n > words.size() || isLiteralNext(parts, i, words, w))
            {
                if (!optional)
                {
                    return null;
                }
                params.add("");
                continue;
            }
            params.add(String.join(" ", words.subList(w, w + n)));
            w += n;
        }
        return w == words.size() ? params : null;
    }

    private static boolean isLiteralNext(String[] parts, int i, List<String> words, int w)
    {
        // an optional slot shouldn't swallow the next fixed word ("->")
        return w < words.size() && (words.get(w).equals("->") || words.get(w).equals("<-"));
    }

    /** Where the n-th whitespace-separated word starts. */
    private static int nthWordStart(String line, int n)
    {
        int count = 0;
        boolean inWord = false;
        for (int i = 0; i < line.length(); i++)
        {
            boolean ws = Character.isWhitespace(line.charAt(i));
            if (!ws && !inWord)
            {
                if (count == n)
                {
                    return i;
                }
                count++;
            }
            inWord = !ws;
        }
        return -1;
    }

    // ------------------------------------------------------------------ editing

    int depthOf(int index)
    {
        int depth = 0;
        for (int i = 0; i < index; i++)
        {
            int shape = blocks.get(i).spec.shape;
            if (shape == Spec.OPEN)
            {
                depth++;
            }
            else if (shape == Spec.CLOSE)
            {
                depth = Math.max(0, depth - 1);
            }
        }
        int shape = blocks.get(index).spec.shape;
        return shape == Spec.CLOSE || shape == Spec.MIDDLE ? Math.max(0, depth - 1) : depth;
    }

    void insert(Spec spec)
    {
        int at = selected < 0 ? blocks.size() : selected + 1;
        blocks.add(at, new Block(spec, List.of(spec.defaults)));
        if (spec.shape == Spec.OPEN)
        {
            blocks.add(at + 1, new Block(SPECS.stream().filter(s -> s.template.equals("end")).findFirst().get(), List.of()));
        }
        selected = at;
    }

    void delete()
    {
        if (selected >= 0 && selected < blocks.size())
        {
            blocks.remove(selected);
            selected = Math.min(selected, blocks.size() - 1);
        }
    }

    void move(int by)
    {
        int to = selected + by;
        if (selected >= 0 && to >= 0 && to < blocks.size())
        {
            Block b = blocks.remove(selected);
            blocks.add(to, b);
            selected = to;
        }
    }

    void duplicate()
    {
        if (selected >= 0)
        {
            Block b = blocks.get(selected);
            blocks.add(selected + 1, new Block(b.spec, b.params));
            selected++;
        }
    }

    // ------------------------------------------------------------------ drawing

    static int colour(Spec spec)
    {
        for (int i = 0; i < CATS.length; i++)
        {
            if (CATS[i].equals(spec.cat))
            {
                return CAT_COLOURS[i];
            }
        }
        return CAT_COLOURS[6];
    }

    /** Draws the block stack; returns the row height used. */
    void renderStack(GuiGraphics g, Font font, int x, int y, int w, int h, int running, int ticks)
    {
        g.fill(x, y, x + w, y + h, 0xFF050603);
        g.fill(x, y, x + w, y + 1, SeegsonTerminalScreen.DIM);
        int rows = h / 12;
        scroll = Math.max(0, Math.min(scroll, Math.max(0, blocks.size() - rows)));
        for (int i = scroll; i < blocks.size() && i < scroll + rows; i++)
        {
            Block b = blocks.get(i);
            int ry = y + 2 + (i - scroll) * 12;
            int indent = depthOf(i) * 10;
            int col = colour(b.spec);
            boolean sel = i == selected, live = running > 0 && b.line == running;
            int bx = x + 3 + indent;
            int bw = w - 6 - indent;
            // the block: a coloured tab with a notch, like a jigsaw piece
            g.fill(bx, ry, bx + bw, ry + 11, sel ? 0xFF3A2800 : 0xFF181410);
            g.fill(bx, ry, bx + 3, ry + 11, col);
            if (b.spec.shape == Spec.OPEN || b.spec.shape == Spec.MIDDLE)
            {
                g.fill(bx + 3, ry + 10, bx + 14, ry + 11, col);
            }
            if (b.spec.shape == Spec.CLOSE)
            {
                g.fill(bx + 3, ry, bx + 14, ry + 1, col);
            }
            if (live && (ticks / 5) % 2 == 0)
            {
                g.fill(bx - 2, ry, bx - 1, ry + 11, SeegsonTerminalScreen.GREEN);
                g.fill(bx + bw, ry, bx + bw + 1, ry + 11, SeegsonTerminalScreen.GREEN);
            }
            String label = b.spec == RAW ? b.text() : b.spec.label;
            String args = b.spec == RAW ? "" : summary(b);
            g.drawString(font, label, bx + 6, ry + 2, b.spec == RAW ? 0xFF9A9A9A : col | 0xFF000000, false);
            g.drawString(font, font.plainSubstrByWidth(args, bw - font.width(label) - 12), bx + 10 + font.width(label), ry + 2,
                    sel ? SeegsonTerminalScreen.AMBER_HI : SeegsonTerminalScreen.AMBER, false);
        }
        if (blocks.isEmpty())
        {
            g.drawString(font, "EMPTY - click a block in the palette", x + 6, y + 6, SeegsonTerminalScreen.DIM, false);
        }
    }

    private static String summary(Block b)
    {
        StringBuilder s = new StringBuilder();
        for (String p : b.params)
        {
            if (!p.isBlank())
            {
                s.append(s.isEmpty() ? "" : "  ").append(p);
            }
        }
        return s.toString();
    }

    /** Index of the block under a click in the stack, or -1. */
    int blockAt(double mx, double my, int x, int y, int w, int h)
    {
        if (mx < x || mx >= x + w || my < y + 2 || my >= y + h)
        {
            return -1;
        }
        int i = (int) ((my - y - 2) / 12) + scroll;
        return i < blocks.size() ? i : -1;
    }

    List<Spec> palette()
    {
        return SPECS.stream().filter(s -> s.cat.equals(category)).toList();
    }
}
