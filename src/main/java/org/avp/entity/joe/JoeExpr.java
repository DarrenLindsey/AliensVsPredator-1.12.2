package org.avp.entity.joe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Expressions for JOE-SCRIPT: numbers, "strings", true/false, variables, + - * / %, comparisons
 * (== != &lt; &gt; &lt;= &gt;=), and/or/not, parentheses and built-in functions like count("wheat") or
 * dist(x, y, z). Values are Double, String or Boolean.
 */
public abstract class JoeExpr
{
    /** What an expression can ask the Joe running it. */
    public interface Context
    {
        Object variable(String name);

        Object call(String function, List<Object> args) throws ExprError;
    }

    public static final class ExprError extends Exception
    {
        public ExprError(String message)
        {
            super(message);
        }
    }

    public abstract Object eval(Context ctx) throws ExprError;

    // ------------------------------------------------------------------ values

    public static boolean truthy(Object v)
    {
        if (v instanceof Boolean b)
        {
            return b;
        }
        if (v instanceof Double d)
        {
            return d != 0;
        }
        return v instanceof String s && !s.isEmpty();
    }

    public static double number(Object v) throws ExprError
    {
        if (v instanceof Double d)
        {
            return d;
        }
        if (v instanceof Boolean b)
        {
            return b ? 1 : 0;
        }
        try
        {
            return Double.parseDouble(String.valueOf(v).trim());
        }
        catch (NumberFormatException e)
        {
            throw new ExprError("'" + v + "' is not a number");
        }
    }

    public static String text(Object v)
    {
        if (v instanceof Double d)
        {
            return d == Math.rint(d) && Math.abs(d) < 1e15 ? String.valueOf(d.longValue()) : String.format(Locale.ROOT, "%.3f", d);
        }
        return String.valueOf(v);
    }

    // ------------------------------------------------------------------ parsing

    public static JoeExpr parse(String source) throws ExprError
    {
        Parser p = new Parser(source);
        JoeExpr e = p.or();
        if (p.pos < p.tokens.size())
        {
            throw new ExprError("unexpected '" + p.tokens.get(p.pos) + "'");
        }
        return e;
    }

    private static final class Parser
    {
        final List<String> tokens = new ArrayList<>();
        int pos;

        Parser(String s) throws ExprError
        {
            int i = 0;
            while (i < s.length())
            {
                char c = s.charAt(i);
                if (Character.isWhitespace(c))
                {
                    i++;
                }
                else if (c == '"')
                {
                    int end = s.indexOf('"', i + 1);
                    if (end < 0)
                    {
                        throw new ExprError("missing closing \"");
                    }
                    tokens.add(s.substring(i, end + 1));
                    i = end + 1;
                }
                else if (Character.isDigit(c) || c == '.' && i + 1 < s.length() && Character.isDigit(s.charAt(i + 1)))
                {
                    int j = i;
                    while (j < s.length() && (Character.isDigit(s.charAt(j)) || s.charAt(j) == '.'))
                    {
                        j++;
                    }
                    tokens.add(s.substring(i, j));
                    i = j;
                }
                else if (Character.isLetter(c) || c == '_')
                {
                    int j = i;
                    while (j < s.length() && (Character.isLetterOrDigit(s.charAt(j)) || s.charAt(j) == '_' || s.charAt(j) == ':' && j + 1 < s.length()
                            && Character.isLetter(s.charAt(j + 1))))
                    {
                        j++;
                    }
                    tokens.add(s.substring(i, j));
                    i = j;
                }
                else
                {
                    String two = i + 1 < s.length() ? s.substring(i, i + 2) : "";
                    if (two.equals("==") || two.equals("!=") || two.equals("<=") || two.equals(">=") || two.equals("&&") || two.equals("||"))
                    {
                        tokens.add(two);
                        i += 2;
                    }
                    else if ("+-*/%()<>,!".indexOf(c) >= 0)
                    {
                        tokens.add(String.valueOf(c));
                        i++;
                    }
                    else
                    {
                        throw new ExprError("unexpected character '" + c + "'");
                    }
                }
            }
        }

        String peek()
        {
            return pos < tokens.size() ? tokens.get(pos) : "";
        }

        boolean eat(String... any)
        {
            for (String t : any)
            {
                if (peek().equalsIgnoreCase(t))
                {
                    pos++;
                    return true;
                }
            }
            return false;
        }

        JoeExpr or() throws ExprError
        {
            JoeExpr left = and();
            while (eat("or", "||"))
            {
                JoeExpr l = left, r = and();
                left = new JoeExpr()
                {
                    @Override
                    public Object eval(Context c) throws ExprError
                    {
                        return truthy(l.eval(c)) || truthy(r.eval(c));
                    }
                };
            }
            return left;
        }

        JoeExpr and() throws ExprError
        {
            JoeExpr left = not();
            while (eat("and", "&&"))
            {
                JoeExpr l = left, r = not();
                left = new JoeExpr()
                {
                    @Override
                    public Object eval(Context c) throws ExprError
                    {
                        return truthy(l.eval(c)) && truthy(r.eval(c));
                    }
                };
            }
            return left;
        }

        JoeExpr not() throws ExprError
        {
            if (eat("not", "!"))
            {
                JoeExpr inner = not();
                return new JoeExpr()
                {
                    @Override
                    public Object eval(Context c) throws ExprError
                    {
                        return !truthy(inner.eval(c));
                    }
                };
            }
            return compare();
        }

        JoeExpr compare() throws ExprError
        {
            JoeExpr left = sum();
            String op = peek();
            if (op.equals("==") || op.equals("!=") || op.equals("<") || op.equals(">") || op.equals("<=") || op.equals(">="))
            {
                pos++;
                JoeExpr l = left, r = sum();
                return new JoeExpr()
                {
                    @Override
                    public Object eval(Context c) throws ExprError
                    {
                        Object a = l.eval(c), b = r.eval(c);
                        if (op.equals("==") || op.equals("!="))
                        {
                            boolean same = a instanceof String || b instanceof String ? text(a).equalsIgnoreCase(text(b)) : number(a) == number(b);
                            return op.equals("==") == same;
                        }
                        double x = number(a), y = number(b);
                        return switch (op)
                        {
                            case "<" -> x < y;
                            case ">" -> x > y;
                            case "<=" -> x <= y;
                            default -> x >= y;
                        };
                    }
                };
            }
            return left;
        }

        JoeExpr sum() throws ExprError
        {
            JoeExpr left = product();
            while (peek().equals("+") || peek().equals("-"))
            {
                String op = tokens.get(pos++);
                JoeExpr l = left, r = product();
                left = new JoeExpr()
                {
                    @Override
                    public Object eval(Context c) throws ExprError
                    {
                        Object a = l.eval(c), b = r.eval(c);
                        if (op.equals("+") && (a instanceof String || b instanceof String))
                        {
                            return text(a) + text(b);
                        }
                        return op.equals("+") ? number(a) + number(b) : number(a) - number(b);
                    }
                };
            }
            return left;
        }

        JoeExpr product() throws ExprError
        {
            JoeExpr left = unary();
            while (peek().equals("*") || peek().equals("/") || peek().equals("%"))
            {
                String op = tokens.get(pos++);
                JoeExpr l = left, r = unary();
                left = new JoeExpr()
                {
                    @Override
                    public Object eval(Context c) throws ExprError
                    {
                        double a = number(l.eval(c)), b = number(r.eval(c));
                        if (b == 0 && !op.equals("*"))
                        {
                            throw new ExprError("division by zero");
                        }
                        return op.equals("*") ? a * b : op.equals("/") ? a / b : a % b;
                    }
                };
            }
            return left;
        }

        JoeExpr unary() throws ExprError
        {
            if (eat("-"))
            {
                JoeExpr inner = unary();
                return new JoeExpr()
                {
                    @Override
                    public Object eval(Context c) throws ExprError
                    {
                        return -number(inner.eval(c));
                    }
                };
            }
            return atom();
        }

        JoeExpr atom() throws ExprError
        {
            String t = peek();
            if (t.isEmpty())
            {
                throw new ExprError("expression ends too early");
            }
            pos++;
            if (t.equals("("))
            {
                JoeExpr inner = or();
                if (!eat(")"))
                {
                    throw new ExprError("missing )");
                }
                return inner;
            }
            if (t.startsWith("\""))
            {
                String s = t.substring(1, t.length() - 1);
                return constant(s);
            }
            if (Character.isDigit(t.charAt(0)) || t.charAt(0) == '.')
            {
                try
                {
                    return constant(Double.parseDouble(t));
                }
                catch (NumberFormatException e)
                {
                    throw new ExprError("bad number '" + t + "'");
                }
            }
            if (t.equalsIgnoreCase("true") || t.equalsIgnoreCase("false"))
            {
                return constant(t.equalsIgnoreCase("true"));
            }
            if (!Character.isLetter(t.charAt(0)) && t.charAt(0) != '_')
            {
                throw new ExprError("unexpected '" + t + "'");
            }
            String name = t.toLowerCase(Locale.ROOT);
            if (eat("("))
            {
                List<JoeExpr> args = new ArrayList<>();
                if (!eat(")"))
                {
                    do
                    {
                        args.add(or());
                    }
                    while (eat(","));
                    if (!eat(")"))
                    {
                        throw new ExprError("missing ) after arguments to " + name);
                    }
                }
                return new JoeExpr()
                {
                    @Override
                    public Object eval(Context c) throws ExprError
                    {
                        List<Object> values = new ArrayList<>();
                        for (JoeExpr a : args)
                        {
                            values.add(a.eval(c));
                        }
                        return c.call(name, values);
                    }
                };
            }
            return new JoeExpr()
            {
                @Override
                public Object eval(Context c)
                {
                    return c.variable(name);
                }
            };
        }

        static JoeExpr constant(Object value)
        {
            return new JoeExpr()
            {
                @Override
                public Object eval(Context c)
                {
                    return value;
                }
            };
        }
    }
}
