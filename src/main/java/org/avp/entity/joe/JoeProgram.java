package org.avp.entity.joe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

/**
 * JOE-SCRIPT, the Seegson Working Joe programming language. A program is plain text, one statement per
 * line (written in a book and quill, or a data pack file in data/&lt;namespace&gt;/joe_programs/). The Joe
 * runs the statements top to bottom and starts over at the end (unless it hits {@code stop}).
 *
 * <pre>
 * # comments start with # or //
 * name Joe-Lab                       the Joe's name
 * home ~ ~ ~                         where it returns to (~ = where the program was loaded; ~3 = 3 from there)
 * goto X Y Z                         walk there ("goto home", "goto alarm", "goto owner")
 * patrol X Y Z ; X Y Z ; ...         walk a route
 * wait SECONDS
 * say "text"                         speak to people nearby
 * report "text"                      send a message to the owner over the Seegson network
 * haul X Y Z -> X Y Z [ITEM] [COUNT] carry items between two containers
 * farm X Y Z X Y Z [-> X Y Z]        harvest and replant ripe crops in a box, store the harvest
 * maintain X Y Z X Y Z [<- X Y Z]    remember the powerlines and lights in a box and put back any that go missing
 * lever X Y Z on|off   press X Y Z   open X Y Z   close X Y Z
 * guard X Y Z [RADIUS] [SECONDS]     stand guard, fight hostiles that come close
 * follow [SECONDS]                   follow the owner
 * charge                             go recharge at the nearest Joe charger
 * security on|off                    attack intruders (hostile mobs, strangers) on sight
 * alarm                              raise the alarm for every Joe on the network
 * label NAME   jump NAME   stop
 * if COND: STATEMENT  /  unless COND: STATEMENT
 *     COND = night | day | lowpower | full | empty | alarm | hurt | raining | player | hostile
 *            | powered X Y Z | has ITEM [COUNT] | chance PERCENT
 * when EVENT: STATEMENT              runs whenever EVENT happens, then the program carries on
 *     EVENT = night | day | alarm | hurt | lowpower | hostile | player | redstone X Y Z
 * load NAMESPACE:PATH                (first line) run a data pack program instead
 * </pre>
 */
public final class JoeProgram
{
    public enum Op
    {
        GOTO, PATROL, WAIT, SAY, REPORT, HAUL, FARM, MAINTAIN, LEVER, PRESS, OPEN, CLOSE, GUARD, FOLLOW, CHARGE, SECURITY,
        ALARM, LABEL, JUMP, STOP, IF, UNLESS
    }

    /** Special places "goto" understands. */
    public enum Place
    {
        POINT, HOME, ALARM, OWNER
    }

    public enum Cond
    {
        NIGHT, DAY, LOWPOWER, FULL, EMPTY, ALARM, HURT, RAINING, PLAYER, HOSTILE, POWERED, HAS, CHANCE, REDSTONE
    }

    /** One statement. Only the fields its op uses are set. */
    public static final class Instr
    {
        public final Op op;
        public final int line;
        public List<BlockPos> points = List.of();
        public Place place = Place.POINT;
        @Nullable
        public BlockPos a, b, store;
        public String text = "";
        public int n, m;
        @Nullable
        public Item item;
        public boolean flag;
        @Nullable
        public Cond cond;
        @Nullable
        public Instr inner;

        Instr(Op op, int line)
        {
            this.op = op;
            this.line = line;
        }

        @Override
        public String toString()
        {
            return op.name().toLowerCase(Locale.ROOT) + (text.isEmpty() ? "" : " " + text);
        }
    }

    /** A "when" handler. */
    public record Handler(Cond event, @Nullable BlockPos pos, Instr action)
    {
    }

    public record Error(int line, String message)
    {
    }

    public final List<Instr> body = new ArrayList<>();
    public final List<Handler> handlers = new ArrayList<>();
    public final Map<String, Integer> labels = new HashMap<>();
    public final List<Error> errors = new ArrayList<>();
    @Nullable
    public String name;
    @Nullable
    public BlockPos home;
    @Nullable
    public ResourceLocation load;

    private final BlockPos anchor;

    private JoeProgram(BlockPos anchor)
    {
        this.anchor = anchor;
    }

    public boolean ok()
    {
        return errors.isEmpty();
    }

    /** Parses a program; relative (~) coordinates are measured from the anchor. */
    public static JoeProgram parse(String source, BlockPos anchor)
    {
        JoeProgram program = new JoeProgram(anchor);
        String[] lines = source.replace("\r", "").split("\n");
        for (int i = 0; i < lines.length; i++)
        {
            String line = stripComment(lines[i]).trim();
            if (line.isEmpty())
            {
                continue;
            }
            try
            {
                program.statement(line, i + 1);
            }
            catch (ParseError e)
            {
                program.errors.add(new Error(i + 1, e.getMessage()));
            }
        }
        for (Instr instr : program.body)
        {
            Instr jump = instr.op == Op.JUMP ? instr : instr.inner != null && instr.inner.op == Op.JUMP ? instr.inner : null;
            if (jump != null && !program.labels.containsKey(jump.text))
            {
                program.errors.add(new Error(jump.line, "no label called '" + jump.text + "'"));
            }
        }
        return program;
    }

    private static String stripComment(String line)
    {
        boolean quoted = false;
        for (int i = 0; i < line.length(); i++)
        {
            char c = line.charAt(i);
            if (c == '"')
            {
                quoted = !quoted;
            }
            else if (!quoted && (c == '#' || c == '/' && i + 1 < line.length() && line.charAt(i + 1) == '/'))
            {
                return line.substring(0, i);
            }
        }
        return line;
    }

    private static final class ParseError extends Exception
    {
        ParseError(String message)
        {
            super(message);
        }
    }

    private void statement(String line, int number) throws ParseError
    {
        String lower = line.toLowerCase(Locale.ROOT);
        if (lower.startsWith("when "))
        {
            int colon = line.indexOf(':');
            if (colon < 0)
            {
                throw new ParseError("'when' needs a ':' before what to do");
            }
            Tokens event = new Tokens(line.substring(5, colon));
            Cond cond = cond(event.next("an event"));
            if (cond == Cond.POWERED || cond == Cond.HAS || cond == Cond.CHANCE || cond == Cond.FULL || cond == Cond.EMPTY || cond == Cond.RAINING)
            {
                throw new ParseError("'" + cond.name().toLowerCase(Locale.ROOT) + "' can't be used with 'when' (try 'if')");
            }
            BlockPos pos = cond == Cond.REDSTONE ? pos(event) : null;
            Instr action = instruction(line.substring(colon + 1).trim(), number);
            if (action.op == Op.LABEL || action.op == Op.JUMP)
            {
                throw new ParseError("a 'when' handler can't jump or define a label");
            }
            handlers.add(new Handler(cond, pos, action));
            return;
        }
        Tokens t = new Tokens(line);
        String word = t.peek().toLowerCase(Locale.ROOT);
        switch (word)
        {
            case "name" ->
            {
                t.next("");
                name = t.rest();
                if (name.isEmpty() || name.length() > 24)
                {
                    throw new ParseError("a name must be 1-24 characters");
                }
                return;
            }
            case "home" ->
            {
                t.next("");
                home = pos(t);
                return;
            }
            case "load" ->
            {
                t.next("");
                load = ResourceLocation.tryParse(t.next("a program id like avp:farmer"));
                if (load == null)
                {
                    throw new ParseError("bad program id");
                }
                return;
            }
            default ->
            {
            }
        }
        Instr instr = instruction(line, number);
        if (instr.op == Op.LABEL)
        {
            if (labels.containsKey(instr.text))
            {
                throw new ParseError("label '" + instr.text + "' is defined twice");
            }
            labels.put(instr.text, body.size());
        }
        body.add(instr);
    }

    private Instr instruction(String line, int number) throws ParseError
    {
        Tokens t = new Tokens(line);
        String word = t.next("a statement").toLowerCase(Locale.ROOT);
        Op op;
        try
        {
            op = Op.valueOf(word.toUpperCase(Locale.ROOT));
        }
        catch (IllegalArgumentException e)
        {
            throw new ParseError("unknown statement '" + word + "'");
        }
        Instr instr = new Instr(op, number);
        switch (op)
        {
            case GOTO ->
            {
                String where = t.peek().toLowerCase(Locale.ROOT);
                switch (where)
                {
                    case "home" -> instr.place = Place.HOME;
                    case "alarm" -> instr.place = Place.ALARM;
                    case "owner" -> instr.place = Place.OWNER;
                    default -> instr.points = List.of(pos(t));
                }
                if (instr.place != Place.POINT)
                {
                    t.next("");
                }
            }
            case PATROL ->
            {
                List<BlockPos> route = new ArrayList<>();
                for (String part : t.rest().split(";"))
                {
                    if (!part.isBlank())
                    {
                        route.add(pos(new Tokens(part)));
                    }
                }
                if (route.isEmpty())
                {
                    throw new ParseError("patrol needs at least one point");
                }
                instr.points = route;
                return instr;
            }
            case WAIT -> instr.n = Math.max(1, (int) (number(t, "seconds") * 20));
            case SAY, REPORT ->
            {
                instr.text = quoted(t.rest());
                if (instr.text.isEmpty())
                {
                    throw new ParseError(word + " needs some text");
                }
                return instr;
            }
            case HAUL ->
            {
                instr.a = pos(t);
                t.expect("->");
                instr.b = pos(t);
                if (t.has() && !t.peek().matches("\\d+"))
                {
                    instr.item = item(t.next(""));
                }
                instr.n = t.has() ? (int) number(t, "a count") : 64;
            }
            case FARM, MAINTAIN ->
            {
                instr.a = pos(t);
                instr.b = pos(t);
                if (t.has())
                {
                    t.expect(op == Op.FARM ? "->" : "<-");
                    instr.store = pos(t);
                }
                int size = (Math.abs(instr.a.getX() - instr.b.getX()) + 1) * (Math.abs(instr.a.getY() - instr.b.getY()) + 1)
                        * (Math.abs(instr.a.getZ() - instr.b.getZ()) + 1);
                if (size > 32 * 32 * 16)
                {
                    throw new ParseError("that area is too big (at most 32 x 32 x 16)");
                }
            }
            case LEVER ->
            {
                instr.a = pos(t);
                String state = t.next("on or off").toLowerCase(Locale.ROOT);
                if (!state.equals("on") && !state.equals("off"))
                {
                    throw new ParseError("lever needs 'on' or 'off'");
                }
                instr.flag = state.equals("on");
            }
            case PRESS, OPEN, CLOSE -> instr.a = pos(t);
            case GUARD ->
            {
                instr.a = pos(t);
                instr.n = t.has() ? (int) number(t, "a radius") : 8;
                instr.m = t.has() ? (int) (number(t, "seconds") * 20) : 0;
            }
            case FOLLOW -> instr.m = t.has() ? (int) (number(t, "seconds") * 20) : 0;
            case SECURITY ->
            {
                String state = t.next("on or off").toLowerCase(Locale.ROOT);
                instr.flag = state.equals("on");
            }
            case LABEL, JUMP -> instr.text = t.next("a label name").toLowerCase(Locale.ROOT);
            case IF, UNLESS ->
            {
                int colon = line.indexOf(':');
                if (colon < 0)
                {
                    throw new ParseError("'" + word + "' needs a ':' before what to do");
                }
                Tokens c = new Tokens(line.substring(word.length(), colon));
                instr.cond = cond(c.next("a condition"));
                switch (instr.cond)
                {
                    case POWERED -> instr.a = pos(c);
                    case HAS ->
                    {
                        instr.item = item(c.next("an item"));
                        instr.n = c.has() ? (int) number(c, "a count") : 1;
                    }
                    case CHANCE -> instr.n = (int) number(c, "a percent");
                    case REDSTONE -> throw new ParseError("use 'powered X Y Z' with if");
                    default ->
                    {
                    }
                }
                instr.inner = instruction(line.substring(colon + 1).trim(), number);
                if (instr.inner.op == Op.LABEL || instr.inner.op == Op.IF || instr.inner.op == Op.UNLESS)
                {
                    throw new ParseError("can't put a label or another if after an if");
                }
                return instr;
            }
            default ->
            {
            }
        }
        if (t.has())
        {
            throw new ParseError("unexpected '" + t.peek() + "'");
        }
        return instr;
    }

    private static Cond cond(String word) throws ParseError
    {
        try
        {
            return Cond.valueOf(word.toUpperCase(Locale.ROOT));
        }
        catch (IllegalArgumentException e)
        {
            throw new ParseError("unknown condition '" + word + "'");
        }
    }

    private static Item item(String id) throws ParseError
    {
        ResourceLocation key = ResourceLocation.tryParse(id.contains(":") ? id : "minecraft:" + id);
        Item item = key == null ? Items.AIR : BuiltInRegistries.ITEM.get(key);
        if (item == Items.AIR)
        {
            throw new ParseError("unknown item '" + id + "'");
        }
        return item;
    }

    private static String quoted(String text)
    {
        text = text.trim();
        if (text.length() >= 2 && text.startsWith("\"") && text.endsWith("\""))
        {
            text = text.substring(1, text.length() - 1);
        }
        return text.length() > 120 ? text.substring(0, 120) : text;
    }

    private static double number(Tokens t, String what) throws ParseError
    {
        String word = t.next(what);
        try
        {
            double value = Double.parseDouble(word);
            if (value < 0 || value > 100_000)
            {
                throw new ParseError(what + " out of range");
            }
            return value;
        }
        catch (NumberFormatException e)
        {
            throw new ParseError("expected " + what + ", got '" + word + "'");
        }
    }

    private BlockPos pos(Tokens t) throws ParseError
    {
        int x = coord(t.next("X"), anchor.getX());
        int y = coord(t.next("Y"), anchor.getY());
        int z = coord(t.next("Z"), anchor.getZ());
        return new BlockPos(x, y, z);
    }

    private static int coord(String word, int base) throws ParseError
    {
        try
        {
            if (word.startsWith("~"))
            {
                return base + (word.length() == 1 ? 0 : Integer.parseInt(word.substring(1)));
            }
            return Integer.parseInt(word);
        }
        catch (NumberFormatException e)
        {
            throw new ParseError("expected a coordinate, got '" + word + "'");
        }
    }

    /** Splits a line into words (a quoted string is one word; "->", "<-" and ";" stand alone). */
    private static final class Tokens
    {
        private final List<String> words = new ArrayList<>();
        private final String source;
        private int at;

        Tokens(String line)
        {
            source = line;
            String spaced = line.replace("->", " -> ").replace("<-", " <- ");
            for (String w : spaced.trim().split("\\s+"))
            {
                if (!w.isEmpty())
                {
                    words.add(w);
                }
            }
        }

        boolean has()
        {
            return at < words.size();
        }

        String peek()
        {
            return has() ? words.get(at) : "";
        }

        String next(String what) throws ParseError
        {
            if (!has())
            {
                throw new ParseError("expected " + what);
            }
            return words.get(at++);
        }

        void expect(String word) throws ParseError
        {
            if (!next("'" + word + "'").equals(word))
            {
                throw new ParseError("expected '" + word + "'");
            }
        }

        /** Everything after the words used so far, as written. */
        String rest()
        {
            if (!has())
            {
                return "";
            }
            StringBuilder out = new StringBuilder();
            for (int i = at; i < words.size(); i++)
            {
                out.append(i > at ? " " : "").append(words.get(i));
            }
            at = words.size();
            // keep the original spacing of quoted text
            int quote = source.indexOf('"');
            return quote >= 0 && out.indexOf("\"") >= 0 ? source.substring(quote).trim() : out.toString();
        }
    }
}
