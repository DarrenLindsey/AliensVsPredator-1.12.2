package org.avp.entity.joe;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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
 * JOE-SCRIPT v2, the Seegson Working Joe programming language. Plain text, one statement per line.
 * The full reference is the Working Joe manual (/joes manual) and docs/JOE-SCRIPT.md.
 *
 * Statements are compiled to a flat list: blocks (if/elif/else, while, repeat, for, func) become
 * conditional jumps, so a Joe can run a program a step at a time and save its place.
 *
 * Any statement can use $name to put a variable's value into it (worked out when it runs), e.g.
 * {@code dig $fx $fy $fz} or {@code say "I have $n wheat"}.
 */
public final class JoeProgram
{
    public enum Op
    {
        // movement and timing
        GOTO, PATROL, WAIT, FOLLOW, GUARD, CHARGE, FACE,
        // talking
        SAY, REPORT, PRINT, SEND, ALARM,
        // world
        HAUL, FARM, MAINTAIN, LEVER, PRESS, OPEN, CLOSE, DIG, PLACE, USE, ATTACK, PICKUP, DROP, TAKE, PUT, CRAFT, EQUIP,
        SCAN, FIND, INSPECT, SECURITY, COURIER, STORE,
        // flow
        SET, LABEL, JUMP, STOP, IF, UNLESS, BRANCH, GOTOPC, CALL, RETURN, NOP
    }

    public enum Place
    {
        POINT, HOME, ALARM, OWNER
    }

    public enum Cond
    {
        NIGHT, DAY, LOWPOWER, FULL, EMPTY, ALARM, HURT, RAINING, PLAYER, HOSTILE, POWERED, HAS, CHANCE, REDSTONE, MESSAGE, EXPR
    }

    public static final class Instr
    {
        public final Op op;
        public final int line;
        /** The statement as written, re-read at run time when it uses $variables. */
        public String raw = "";
        public boolean dynamic;
        public List<BlockPos> points = List.of();
        public Place place = Place.POINT;
        @Nullable
        public BlockPos a, b, store;
        public String text = "";
        /** A second text (the message of "send"). */
        public String extra = "";
        public int n, m;
        @Nullable
        public Item item;
        public boolean flag;
        @Nullable
        public Cond cond;
        @Nullable
        public Instr inner;
        @Nullable
        public JoeExpr expr;
        /** Jump target (BRANCH, GOTOPC, CALL). */
        public int target = -1;

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

    public record Handler(Cond event, @Nullable BlockPos pos, String channel, Instr action)
    {
    }

    public record Error(int line, String message)
    {
    }

    public final List<Instr> body = new ArrayList<>();
    public final List<Handler> handlers = new ArrayList<>();
    public final Map<String, Integer> labels = new HashMap<>();
    public final Map<String, Integer> functions = new HashMap<>();
    public final List<Error> errors = new ArrayList<>();
    @Nullable
    public String name;
    @Nullable
    public BlockPos home;
    @Nullable
    public ResourceLocation load;

    private final BlockPos anchor;
    private final Deque<Frame> frames = new ArrayDeque<>();
    private final List<Instr> calls = new ArrayList<>();
    private int hidden;

    /** An open block. */
    private static final class Frame
    {
        final String kind;
        final int line;
        int start = -1, branch = -1;
        final List<Integer> exits = new ArrayList<>(), continues = new ArrayList<>();
        String counter;
        JoeExpr step;
        boolean sawElse;

        Frame(String kind, int line)
        {
            this.kind = kind;
            this.line = line;
        }

        boolean loop()
        {
            return kind.equals("while") || kind.equals("repeat") || kind.equals("for");
        }
    }

    private JoeProgram(BlockPos anchor)
    {
        this.anchor = anchor;
    }

    public boolean ok()
    {
        return errors.isEmpty();
    }

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
        for (Frame open : program.frames)
        {
            program.errors.add(new Error(open.line, "'" + open.kind + "' has no matching 'end'"));
        }
        for (Instr instr : program.body)
        {
            Instr jump = instr.op == Op.JUMP ? instr : instr.inner != null && instr.inner.op == Op.JUMP ? instr.inner : null;
            if (jump != null && !program.labels.containsKey(jump.text))
            {
                program.errors.add(new Error(jump.line, "no label called '" + jump.text + "'"));
            }
        }
        for (Instr call : program.calls)
        {
            Integer at = program.functions.get(call.text);
            if (at == null)
            {
                program.errors.add(new Error(call.line, "no function called '" + call.text + "'"));
            }
            else
            {
                call.target = at;
            }
        }
        return program;
    }

    /** Parses one statement on its own, at run time (for statements with $variables in them). */
    @Nullable
    public static Instr reparse(String line, int number, BlockPos anchor, List<String> problems)
    {
        JoeProgram p = new JoeProgram(anchor);
        try
        {
            return p.instruction(line, number);
        }
        catch (ParseError e)
        {
            problems.add(e.getMessage());
            return null;
        }
    }

    static String stripComment(String line)
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

    /** The ':' that ends a condition (outside quotes, followed by a space or the end of the line). */
    static int separator(String line)
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
                return i;
            }
        }
        return -1;
    }

    static final class ParseError extends Exception
    {
        ParseError(String message)
        {
            super(message);
        }
    }

    private Instr add(Op op, int line)
    {
        Instr i = new Instr(op, line);
        body.add(i);
        return i;
    }

    private static JoeExpr expr(String text) throws ParseError
    {
        try
        {
            return JoeExpr.parse(text);
        }
        catch (JoeExpr.ExprError e)
        {
            throw new ParseError(e.getMessage());
        }
    }

    private void statement(String line, int number) throws ParseError
    {
        String lower = line.toLowerCase(Locale.ROOT);
        String first = lower.split("\\s+")[0];
        int sep = separator(line);
        // ---- "when EVENT: statement" responses
        if (first.equals("when"))
        {
            if (sep < 0)
            {
                throw new ParseError("'when' needs a ':' before what to do");
            }
            Tokens event = new Tokens(line.substring(5, sep));
            Cond cond = cond(event.next("an event"));
            BlockPos pos = null;
            String channel = "";
            switch (cond)
            {
                case NIGHT, DAY, LOWPOWER, HURT, ALARM, HOSTILE, PLAYER ->
                {
                }
                case REDSTONE -> pos = pos(event);
                case MESSAGE -> channel = event.has() ? event.next("").toLowerCase(Locale.ROOT) : "";
                default -> throw new ParseError("'" + cond.name().toLowerCase(Locale.ROOT) + "' can't be used with 'when' (try 'if')");
            }
            Instr action = instruction(line.substring(sep + 1).trim(), number);
            if (action.op == Op.LABEL || action.op == Op.JUMP)
            {
                throw new ParseError("a 'when' response can't jump or define a label (use 'call' for several steps)");
            }
            if (action.op == Op.CALL)
            {
                calls.add(action);
            }
            handlers.add(new Handler(cond, pos, channel, action));
            return;
        }
        // ---- blocks
        if ((first.equals("if") || first.equals("unless") || first.equals("while")) && sep < 0)
        {
            String cond = line.substring(first.length()).trim();
            if (cond.isEmpty())
            {
                throw new ParseError("'" + first + "' needs a condition");
            }
            Frame f = new Frame(first.equals("while") ? "while" : "if", number);
            f.start = body.size();
            Instr branch = add(Op.BRANCH, number);
            branch.cond = Cond.EXPR;
            branch.expr = condition(cond, first.equals("unless"));
            f.branch = body.size() - 1;
            frames.push(f);
            return;
        }
        switch (first)
        {
            case "elif", "else" ->
            {
                Frame f = frames.peek();
                if (f == null || !f.kind.equals("if") || f.sawElse)
                {
                    throw new ParseError("'" + first + "' without an open 'if'");
                }
                f.exits.add(body.size());
                add(Op.GOTOPC, number);
                body.get(f.branch).target = body.size();
                if (first.equals("elif"))
                {
                    String cond = line.substring(4).trim();
                    if (cond.isEmpty())
                    {
                        throw new ParseError("'elif' needs a condition");
                    }
                    Instr branch = add(Op.BRANCH, number);
                    branch.cond = Cond.EXPR;
                    branch.expr = condition(cond, false);
                    f.branch = body.size() - 1;
                }
                else
                {
                    f.branch = -1;
                    f.sawElse = true;
                }
                return;
            }
            case "repeat" ->
            {
                String count = line.substring(6).trim();
                Frame f = new Frame("repeat", number);
                f.counter = "__r" + (hidden++);
                Instr set = add(Op.SET, number);
                set.text = f.counter;
                set.expr = expr(count.isEmpty() ? "0" : count);
                f.start = body.size();
                Instr branch = add(Op.BRANCH, number);
                branch.expr = expr(f.counter + " > 0");
                f.branch = body.size() - 1;
                f.step = expr(f.counter + " - 1");
                frames.push(f);
                return;
            }
            case "forever", "loop" ->
            {
                Frame f = new Frame("while", number);
                f.start = body.size();
                Instr branch = add(Op.BRANCH, number);
                branch.expr = expr("true");
                f.branch = body.size() - 1;
                frames.push(f);
                return;
            }
            case "for" ->
            {
                Tokens t = new Tokens(line.substring(3));
                String var = t.next("a variable name").toLowerCase(Locale.ROOT);
                t.expect("=");
                String rest = t.rest();
                String lowerRest = rest.toLowerCase(Locale.ROOT);
                int to = lowerRest.indexOf(" to ");
                if (to < 0)
                {
                    throw new ParseError("for needs 'to': for i = 1 to 10");
                }
                int stepAt = lowerRest.indexOf(" step ");
                String from = rest.substring(0, to), until = stepAt > 0 ? rest.substring(to + 4, stepAt) : rest.substring(to + 4);
                String step = stepAt > 0 ? rest.substring(stepAt + 6) : "1";
                Frame f = new Frame("for", number);
                f.counter = var;
                Instr set = add(Op.SET, number);
                set.text = var;
                set.expr = expr(from);
                f.start = body.size();
                Instr branch = add(Op.BRANCH, number);
                branch.expr = expr("(" + step + ") >= 0 and " + var + " <= (" + until + ") or (" + step + ") < 0 and " + var + " >= (" + until + ")");
                f.branch = body.size() - 1;
                f.step = expr(var + " + (" + step + ")");
                frames.push(f);
                return;
            }
            case "func", "function" ->
            {
                if (!frames.isEmpty())
                {
                    throw new ParseError("functions can't be inside another block");
                }
                Tokens t = new Tokens(line.substring(first.length()));
                String fname = t.next("a function name").toLowerCase(Locale.ROOT);
                if (functions.containsKey(fname))
                {
                    throw new ParseError("function '" + fname + "' is defined twice");
                }
                Frame f = new Frame("func", number);
                f.branch = body.size();
                add(Op.GOTOPC, number); // skip over the body when running past it
                functions.put(fname, body.size());
                frames.push(f);
                return;
            }
            case "end" ->
            {
                Frame f = frames.poll();
                if (f == null)
                {
                    throw new ParseError("'end' without a block to end");
                }
                switch (f.kind)
                {
                    case "if" ->
                    {
                        if (f.branch >= 0)
                        {
                            body.get(f.branch).target = body.size();
                        }
                        for (int exit : f.exits)
                        {
                            body.get(exit).target = body.size();
                        }
                    }
                    case "func" ->
                    {
                        add(Op.RETURN, number);
                        body.get(f.branch).target = body.size();
                    }
                    default ->
                    {
                        int cont = body.size();
                        if (f.step != null)
                        {
                            Instr step = add(Op.SET, number);
                            step.text = f.counter;
                            step.expr = f.step;
                        }
                        Instr back = add(Op.GOTOPC, number);
                        back.target = f.start;
                        body.get(f.branch).target = body.size();
                        for (int exit : f.exits)
                        {
                            body.get(exit).target = body.size();
                        }
                        for (int c : f.continues)
                        {
                            body.get(c).target = cont;
                        }
                    }
                }
                return;
            }
            case "break", "continue" ->
            {
                Frame loop = null;
                for (Frame f : frames)
                {
                    if (f.loop())
                    {
                        loop = f;
                        break;
                    }
                }
                if (loop == null)
                {
                    throw new ParseError("'" + first + "' outside a loop");
                }
                (first.equals("break") ? loop.exits : loop.continues).add(body.size());
                add(Op.GOTOPC, number);
                return;
            }
            case "name" ->
            {
                name = line.substring(4).trim();
                if (name.isEmpty() || name.length() > 24)
                {
                    throw new ParseError("a name must be 1-24 characters");
                }
                return;
            }
            case "home" ->
            {
                home = pos(new Tokens(line.substring(4)));
                return;
            }
            case "load" ->
            {
                load = ResourceLocation.tryParse(line.substring(4).trim());
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
        if (instr.op == Op.CALL || instr.inner != null && instr.inner.op == Op.CALL)
        {
            calls.add(instr.op == Op.CALL ? instr : instr.inner);
        }
        if (instr.op == Op.RETURN && frames.stream().noneMatch(f -> f.kind.equals("func")))
        {
            throw new ParseError("'return' outside a function");
        }
        body.add(instr);
    }

    /** A condition: the old keyword forms ("night", "powered X Y Z", "has wheat 10", "chance 20") or any expression. */
    private JoeExpr condition(String text, boolean negate) throws ParseError
    {
        Tokens t = new Tokens(text);
        String word = t.peek().toLowerCase(Locale.ROOT);
        String converted = null;
        switch (word)
        {
            case "powered" ->
            {
                if (!text.contains("("))
                {
                    t.next("");
                    BlockPos p = pos(t);
                    converted = "powered(" + p.getX() + "," + p.getY() + "," + p.getZ() + ")";
                }
            }
            case "has" ->
            {
                if (!text.contains("("))
                {
                    t.next("");
                    String item = t.next("an item");
                    String n = t.has() ? t.next("") : "1";
                    converted = "count(\"" + item.replace("\"", "") + "\") >= " + n;
                }
            }
            case "chance" ->
            {
                if (!text.contains("("))
                {
                    t.next("");
                    converted = "chance(" + t.next("a percent") + ")";
                }
            }
            default ->
            {
            }
        }
        JoeExpr e = expr(converted != null ? converted : text);
        if (!negate)
        {
            return e;
        }
        return new JoeExpr()
        {
            @Override
            public Object eval(Context c) throws ExprError
            {
                return !truthy(e.eval(c));
            }
        };
    }

    private Instr instruction(String line, int number) throws ParseError
    {
        Tokens t = new Tokens(line);
        String word = t.next("a statement").toLowerCase(Locale.ROOT);
        Op op = opOf(word);
        if (line.contains("$") && op != Op.SET && op != Op.IF && op != Op.UNLESS)
        {
            // worked out when it runs; check the shape now with placeholder numbers
            Instr dyn = new Instr(op, number);
            dyn.raw = line;
            dyn.dynamic = true;
            Instr shape = instruction(line.replaceAll("\\$[A-Za-z_][A-Za-z0-9_]*", "0"), number);
            dyn.text = shape.text;
            return dyn;
        }
        Instr instr = new Instr(op, number);
        instr.raw = line;
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
            case SAY, REPORT, PRINT ->
            {
                instr.text = quoted(t.rest());
                if (instr.text.isEmpty())
                {
                    throw new ParseError(word + " needs some text");
                }
                return instr;
            }
            case SEND ->
            {
                instr.text = t.next("a channel").toLowerCase(Locale.ROOT);
                instr.extra = quoted(t.rest());
                return instr;
            }
            case HAUL ->
            {
                instr.a = pos(t);
                t.expect("->");
                instr.b = pos(t);
                if (t.has() && !isNumber(t.peek()))
                {
                    instr.item = item(t.next(""));
                }
                instr.n = t.has() ? (int) number(t, "a count") : 64;
            }
            case TAKE, PUT ->
            {
                instr.a = pos(t);
                if (t.has() && !isNumber(t.peek()))
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
                checkBox(instr);
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
            case PRESS, OPEN, CLOSE, DIG, INSPECT, FACE -> instr.a = pos(t);
            case PLACE ->
            {
                instr.item = item(t.next("a block"));
                instr.a = pos(t);
            }
            case USE ->
            {
                instr.a = pos(t);
                if (t.has())
                {
                    instr.item = item(t.next(""));
                }
            }
            case ATTACK, PICKUP -> instr.n = t.has() ? (int) number(t, "a radius") : 8;
            case DROP ->
            {
                if (t.has() && !isNumber(t.peek()))
                {
                    instr.item = item(t.next(""));
                }
                instr.n = t.has() ? (int) number(t, "a count") : 64;
            }
            case CRAFT ->
            {
                instr.item = item(t.next("an item to craft"));
                instr.n = t.has() ? (int) number(t, "a count") : 1;
            }
            case EQUIP ->
            {
                String what = t.next("an item or 'nothing'");
                instr.item = what.equalsIgnoreCase("nothing") ? Items.AIR : item(what);
            }
            case SCAN, FIND ->
            {
                instr.text = t.next(op == Op.SCAN ? "a block id" : "an entity id or 'hostile'").toLowerCase(Locale.ROOT);
                instr.n = t.has() ? (int) number(t, "a radius") : 16;
            }
            case GUARD ->
            {
                instr.a = pos(t);
                instr.n = t.has() ? (int) number(t, "a radius") : 8;
                instr.m = t.has() ? (int) (number(t, "seconds") * 20) : 0;
            }
            case FOLLOW -> instr.m = t.has() ? (int) (number(t, "seconds") * 20) : 0;
            case SECURITY -> instr.flag = t.next("on or off").equalsIgnoreCase("on");
            case LABEL, JUMP -> instr.text = t.next("a label name").toLowerCase(Locale.ROOT);
            case CALL -> instr.text = t.next("a function name").toLowerCase(Locale.ROOT);
            case SET ->
            {
                instr.text = t.next("a variable name").toLowerCase(Locale.ROOT);
                if (!instr.text.matches("[a-z_][a-z0-9_]*"))
                {
                    throw new ParseError("bad variable name '" + instr.text + "'");
                }
                t.expect("=");
                int eq = line.indexOf('=');
                String rest = line.substring(eq + 1).trim();
                if (rest.isEmpty())
                {
                    throw new ParseError("set needs a value after '='");
                }
                instr.expr = expr(rest.replace("$", ""));
                return instr;
            }
            case IF, UNLESS ->
            {
                int colon = separator(line);
                if (colon < 0)
                {
                    throw new ParseError("'" + word + "' needs a ':' before what to do");
                }
                instr.cond = Cond.EXPR;
                instr.expr = condition(line.substring(word.length(), colon).trim().replace("$", ""), op == Op.UNLESS);
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

    private static Op opOf(String word) throws ParseError
    {
        String w = word.toLowerCase(Locale.ROOT);
        switch (w)
        {
            case "sleep" -> w = "wait";
            case "log", "echo" -> w = "print";
            case "broadcast" -> w = "send";
            case "grab", "suck" -> w = "take";
            case "deposit" -> w = "put";
            case "mine", "break" -> w = "dig";
            case "let", "var" -> w = "set";
            default ->
            {
            }
        }
        try
        {
            Op op = Op.valueOf(w.toUpperCase(Locale.ROOT));
            if (op == Op.BRANCH || op == Op.GOTOPC || op == Op.NOP)
            {
                throw new IllegalArgumentException();
            }
            return op;
        }
        catch (IllegalArgumentException e)
        {
            throw new ParseError("unknown statement '" + word + "'");
        }
    }

    private static void checkBox(Instr instr) throws ParseError
    {
        long size = (long) (Math.abs(instr.a.getX() - instr.b.getX()) + 1) * (Math.abs(instr.a.getY() - instr.b.getY()) + 1)
                * (Math.abs(instr.a.getZ() - instr.b.getZ()) + 1);
        if (size > 32 * 32 * 16)
        {
            throw new ParseError("that area is too big (at most 32 x 32 x 16)");
        }
    }

    private static boolean isNumber(String s)
    {
        return s.matches("-?\\d+(\\.\\d+)?");
    }

    private static Cond cond(String word) throws ParseError
    {
        try
        {
            Cond c = Cond.valueOf(word.toUpperCase(Locale.ROOT));
            if (c == Cond.EXPR)
            {
                throw new IllegalArgumentException();
            }
            return c;
        }
        catch (IllegalArgumentException e)
        {
            throw new ParseError("unknown event '" + word + "'");
        }
    }

    static Item item(String id) throws ParseError
    {
        if (id.startsWith("\"") && id.endsWith("\"") && id.length() > 1)
        {
            id = id.substring(1, id.length() - 1);
        }
        String lower = id.toLowerCase(Locale.ROOT);
        ResourceLocation key = ResourceLocation.tryParse(lower.contains(":") ? lower : "minecraft:" + lower);
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
        return text.length() > 200 ? text.substring(0, 200) : text;
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
                return base + (word.length() == 1 ? 0 : (int) Math.round(Double.parseDouble(word.substring(1))));
            }
            return (int) Math.floor(Double.parseDouble(word));
        }
        catch (NumberFormatException e)
        {
            throw new ParseError("expected a coordinate, got '" + word + "'");
        }
    }

    /** Splits a line into words (a quoted string is one word; "->", "<-" and a lone "=" stand alone). */
    static final class Tokens
    {
        private final List<String> words = new ArrayList<>();
        private final String source;
        private int at;

        Tokens(String line)
        {
            source = line;
            StringBuilder cur = new StringBuilder();
            boolean quoted = false;
            String spaced = line.replace("->", " -> ").replace("<-", " <- ");
            for (int i = 0; i < spaced.length(); i++)
            {
                char c = spaced.charAt(i);
                char prev = i > 0 ? spaced.charAt(i - 1) : ' ';
                char next = i + 1 < spaced.length() ? spaced.charAt(i + 1) : ' ';
                if (c == '"')
                {
                    quoted = !quoted;
                    cur.append(c);
                }
                else if (!quoted && Character.isWhitespace(c))
                {
                    flush(cur);
                }
                else if (!quoted && c == '=' && next != '=' && "=!<>".indexOf(prev) < 0)
                {
                    flush(cur);
                    words.add("=");
                }
                else
                {
                    cur.append(c);
                }
            }
            flush(cur);
        }

        private void flush(StringBuilder cur)
        {
            if (!cur.isEmpty())
            {
                words.add(cur.toString());
                cur.setLength(0);
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
            int quote = source.indexOf('"');
            return quote >= 0 && out.indexOf("\"") >= 0 ? source.substring(quote).trim() : out.toString();
        }
    }
}
