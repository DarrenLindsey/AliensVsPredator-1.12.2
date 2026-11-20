package org.avp.block.conveyor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

import org.avp.entity.joe.JoeExpr;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

/**
 * BeltScript: what a Logic Belt runs for every item that reaches its middle. Lines run top to bottom;
 * the first route reached decides where the item goes.
 *
 * <pre>
 * # comments
 * set n = n + 1                       variables keep their values between items
 * if is("iron_ore"): left             item id (minecraft: optional)
 * if tag("c:ores"): right             item tag
 * if count >= 32: back
 * if contains(name, "Diamond"): store into the belt's buffer (hoppers can take it out)
 * if n % 3 == 0: left                 every third item
 * signal n % 16                       redstone output strength (0-15)
 * straight                            default route
 * </pre>
 * Routes: left, right, straight, back, store. Values: item, name, count, n (items seen),
 * redstone (powered), time. Functions: is(id), tag(t), plus JOE-SCRIPT's maths.
 */
public final class BeltScript
{
    public enum Route
    {
        LEFT, RIGHT, STRAIGHT, BACK, STORE
    }

    private record Line(int number, @Nullable JoeExpr cond, @Nullable Route route, @Nullable String setVar, @Nullable JoeExpr value, boolean signal)
    {
    }

    public record Result(Route route, int signal, @Nullable String error)
    {
    }

    private final List<Line> lines = new ArrayList<>();
    public final List<String> errors = new ArrayList<>();

    public static BeltScript parse(String source)
    {
        BeltScript script = new BeltScript();
        String[] lines = source.replace("\r", "").split("\n");
        for (int i = 0; i < lines.length; i++)
        {
            String line = lines[i];
            int hash = line.indexOf('#');
            if (hash >= 0 && line.lastIndexOf('"', hash) < 0)
            {
                line = line.substring(0, hash);
            }
            line = line.trim();
            if (line.isEmpty())
            {
                continue;
            }
            try
            {
                script.lines.add(script.statement(line, i + 1, null));
            }
            catch (JoeExpr.ExprError e)
            {
                script.errors.add("Line " + (i + 1) + ": " + e.getMessage());
            }
        }
        return script;
    }

    private Line statement(String line, int number, @Nullable JoeExpr cond) throws JoeExpr.ExprError
    {
        String lower = line.toLowerCase(Locale.ROOT);
        if (lower.startsWith("if ") || lower.startsWith("unless "))
        {
            int colon = separator(line);
            if (colon < 0)
            {
                throw new JoeExpr.ExprError("'if' needs ':' before what to do");
            }
            boolean unless = lower.startsWith("unless ");
            JoeExpr c = JoeExpr.parse(line.substring(unless ? 7 : 3, colon).trim());
            JoeExpr test = unless ? new JoeExpr()
            {
                @Override
                public Object eval(Context ctx) throws ExprError
                {
                    return !truthy(c.eval(ctx));
                }
            } : c;
            return statement(line.substring(colon + 1).trim(), number, test);
        }
        if (lower.startsWith("set "))
        {
            int eq = line.indexOf('=');
            if (eq < 0)
            {
                throw new JoeExpr.ExprError("set needs '='");
            }
            String name = line.substring(4, eq).trim().toLowerCase(Locale.ROOT);
            if (!name.matches("[a-z_][a-z0-9_]*"))
            {
                throw new JoeExpr.ExprError("bad variable name '" + name + "'");
            }
            return new Line(number, cond, null, name, JoeExpr.parse(line.substring(eq + 1).trim()), false);
        }
        if (lower.startsWith("signal "))
        {
            return new Line(number, cond, null, null, JoeExpr.parse(line.substring(7).trim()), true);
        }
        String word = lower.replace("default", "").replace(":", "").trim();
        try
        {
            return new Line(number, cond, Route.valueOf(word.toUpperCase(Locale.ROOT)), null, null, false);
        }
        catch (IllegalArgumentException e)
        {
            throw new JoeExpr.ExprError("unknown statement '" + line + "' (routes: left right straight back store)");
        }
    }

    private static int separator(String line)
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

    /** Runs the script for one item. */
    public Result run(ItemStack stack, Map<String, Object> vars, boolean redstone, long time, RandomSource random, int signalBefore)
    {
        JoeExpr.Context ctx = new JoeExpr.Context()
        {
            @Override
            public Object variable(String name)
            {
                return switch (name)
                {
                    case "item" -> BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
                    case "name" -> stack.getHoverName().getString();
                    case "count" -> (double) stack.getCount();
                    case "redstone" -> redstone;
                    case "time" -> (double) time;
                    default -> vars.containsKey(name) ? vars.get(name) : name.contains(":") ? name : 0.0;
                };
            }

            @Override
            public Object call(String f, List<Object> a) throws JoeExpr.ExprError
            {
                switch (f)
                {
                    case "is" ->
                    {
                        String id = JoeExpr.text(a.get(0)).toLowerCase(Locale.ROOT);
                        return BuiltInRegistries.ITEM.getKey(stack.getItem()).toString().equals(id.contains(":") ? id : "minecraft:" + id);
                    }
                    case "tag" ->
                    {
                        ResourceLocation id = ResourceLocation.tryParse(JoeExpr.text(a.get(0)).replace("#", ""));
                        return id != null && stack.is(TagKey.create(Registries.ITEM, id));
                    }
                    case "contains" -> { return JoeExpr.text(a.get(0)).toLowerCase(Locale.ROOT).contains(JoeExpr.text(a.get(1)).toLowerCase(Locale.ROOT)); }
                    case "random" -> { return (double) random.nextInt(Math.max(1, (int) JoeExpr.number(a.get(0)))); }
                    case "chance" -> { return random.nextInt(100) < JoeExpr.number(a.get(0)); }
                    case "min" -> { return Math.min(JoeExpr.number(a.get(0)), JoeExpr.number(a.get(1))); }
                    case "max" -> { return Math.max(JoeExpr.number(a.get(0)), JoeExpr.number(a.get(1))); }
                    case "abs" -> { return Math.abs(JoeExpr.number(a.get(0))); }
                    case "floor" -> { return Math.floor(JoeExpr.number(a.get(0))); }
                    default -> throw new JoeExpr.ExprError("unknown function '" + f + "'");
                }
            }
        };
        vars.merge("n", 1.0, (x, y) -> (x instanceof Double d ? d : 0.0) + 1.0);
        int signal = signalBefore;
        try
        {
            for (Line line : lines)
            {
                if (line.cond() != null && !JoeExpr.truthy(line.cond().eval(ctx)))
                {
                    continue;
                }
                if (line.setVar() != null)
                {
                    if (vars.size() < 64)
                    {
                        vars.put(line.setVar(), line.value().eval(ctx));
                    }
                }
                else if (line.signal())
                {
                    signal = Math.max(0, Math.min(15, (int) JoeExpr.number(line.value().eval(ctx))));
                }
                else if (line.route() != null)
                {
                    return new Result(line.route(), signal, null);
                }
            }
        }
        catch (JoeExpr.ExprError e)
        {
            return new Result(Route.STRAIGHT, signal, e.getMessage());
        }
        return new Result(Route.STRAIGHT, signal, null);
    }
}
