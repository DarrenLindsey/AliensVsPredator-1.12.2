package org.avp.entity.joe;

import java.util.List;
import java.util.Locale;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandler;

/**
 * What a JOE-SCRIPT expression can read: the program's own variables, plus built-in values and
 * functions (see the manual). Unknown names are 0, and a name with a ':' in it is just that text
 * (so count(minecraft:wheat) works without quotes).
 */
final class JoeContext implements JoeExpr.Context
{
    private final WorkingJoeEntity joe;

    JoeContext(WorkingJoeEntity joe)
    {
        this.joe = joe;
    }

    @Override
    public Object variable(String name)
    {
        Object own = joe.vars.get(name);
        if (own != null)
        {
            return own;
        }
        BlockPos at = joe.blockPosition();
        return switch (name)
        {
            case "x" -> (double) at.getX();
            case "y" -> (double) at.getY();
            case "z" -> (double) at.getZ();
            case "charge", "battery" -> (double) joe.chargePercent();
            case "health" -> (double) joe.getHealth();
            case "time" -> (double) (joe.level().getDayTime() % 24000);
            case "night" -> joe.level().isNight();
            case "day" -> !joe.level().isNight();
            case "raining" -> joe.level().isRaining();
            case "lowpower" -> joe.isLowPower();
            case "full" -> !joe.hasRoom();
            case "empty" -> joe.count(null) == 0;
            case "alarm" -> JoeNetwork.alarmPos(joe.owner()) != null;
            case "hurt" -> joe.getHealth() < joe.getMaxHealth() * 0.5F;
            case "hostile" -> joe.hasHostileNear();
            case "player" -> joe.hasStrangerNear();
            case "security" -> joe.security();
            case "name" -> joe.joeName();
            case "items" -> (double) joe.count(null);
            case "slots" -> (double) joe.usableSlots();
            case "found" -> false;
            case "homex" -> (double) joe.homeOrAnchor().getX();
            case "homey" -> (double) joe.homeOrAnchor().getY();
            case "homez" -> (double) joe.homeOrAnchor().getZ();
            case "ownerx", "ownery", "ownerz" ->
            {
                var p = joe.ownerPlayer();
                yield p == null ? 0.0 : (double) (name.equals("ownerx") ? p.getBlockX() : name.equals("ownery") ? p.getBlockY() : p.getBlockZ());
            }
            case "pi" -> Math.PI;
            case "msg", "sender", "channel", "block", "target" -> "";
            default -> name.contains(":") ? name : 0.0;
        };
    }

    private static double num(List<Object> args, int i) throws JoeExpr.ExprError
    {
        if (i >= args.size())
        {
            throw new JoeExpr.ExprError("missing argument " + (i + 1));
        }
        return JoeExpr.number(args.get(i));
    }

    private static String str(List<Object> args, int i) throws JoeExpr.ExprError
    {
        if (i >= args.size())
        {
            throw new JoeExpr.ExprError("missing argument " + (i + 1));
        }
        return JoeExpr.text(args.get(i));
    }

    private static BlockPos pos(List<Object> args, int from) throws JoeExpr.ExprError
    {
        return BlockPos.containing(num(args, from), num(args, from + 1), num(args, from + 2));
    }

    static Item item(String id) throws JoeExpr.ExprError
    {
        String lower = id.toLowerCase(Locale.ROOT);
        ResourceLocation key = ResourceLocation.tryParse(lower.contains(":") ? lower : "minecraft:" + lower);
        Item item = key == null ? Items.AIR : BuiltInRegistries.ITEM.get(key);
        if (item == Items.AIR)
        {
            throw new JoeExpr.ExprError("unknown item '" + id + "'");
        }
        return item;
    }

    @Override
    public Object call(String f, List<Object> a) throws JoeExpr.ExprError
    {
        switch (f)
        {
            // ---- maths
            case "abs" -> { return Math.abs(num(a, 0)); }
            case "floor" -> { return Math.floor(num(a, 0)); }
            case "ceil" -> { return Math.ceil(num(a, 0)); }
            case "round" -> { return (double) Math.round(num(a, 0)); }
            case "sqrt" -> { return Math.sqrt(Math.max(0, num(a, 0))); }
            case "min" -> { return Math.min(num(a, 0), num(a, 1)); }
            case "max" -> { return Math.max(num(a, 0), num(a, 1)); }
            case "random" -> { return (double) joe.getRandom().nextInt(Math.max(1, (int) num(a, 0))); }
            case "chance" -> { return joe.getRandom().nextInt(100) < num(a, 0); }
            case "sin" -> { return Math.sin(Math.toRadians(num(a, 0))); }
            case "cos" -> { return Math.cos(Math.toRadians(num(a, 0))); }
            // ---- text
            case "str", "text" -> { return str(a, 0); }
            case "num" -> { return JoeExpr.number(a.get(0)); }
            case "len" -> { return (double) str(a, 0).length(); }
            case "upper" -> { return str(a, 0).toUpperCase(Locale.ROOT); }
            case "lower" -> { return str(a, 0).toLowerCase(Locale.ROOT); }
            case "contains" -> { return str(a, 0).toLowerCase(Locale.ROOT).contains(str(a, 1).toLowerCase(Locale.ROOT)); }
            case "word" ->
            {
                String[] words = str(a, 0).trim().split("\\s+");
                int i = (int) num(a, 1);
                return i >= 0 && i < words.length ? words[i] : "";
            }
            // ---- itself
            case "count" -> { return (double) (a.isEmpty() ? joe.count(null) : joe.count(item(str(a, 0)))); }
            case "upgrade", "hasupgrade" -> { return joe.hasUpgrade(str(a, 0).toLowerCase(Locale.ROOT)); }
            case "var" -> { return variable(str(a, 0).toLowerCase(Locale.ROOT)); }
            // ---- the world
            case "dist" -> { return Math.sqrt(joe.distanceToSqr(Vec3.atBottomCenterOf(pos(a, 0)))); }
            case "block" -> { return blockId(pos(a, 0)); }
            case "isair" -> { return joe.level().getBlockState(pos(a, 0)).isAir(); }
            case "powered" -> { return joe.powered(pos(a, 0)); }
            case "ripe" ->
            {
                BlockState s = joe.level().getBlockState(pos(a, 0));
                return s.getBlock() instanceof CropBlock crop && crop.isMaxAge(s);
            }
            case "items", "stock" ->
            {
                IItemHandler h = JoeActions.container(joe, pos(a, 0));
                if (h == null)
                {
                    return -1.0;
                }
                Item only = a.size() > 3 ? item(str(a, 3)) : null;
                int n = 0;
                for (int i = 0; i < h.getSlots(); i++)
                {
                    ItemStack s = h.getStackInSlot(i);
                    if (!s.isEmpty() && (only == null || s.is(only)))
                    {
                        n += s.getCount();
                    }
                }
                return (double) n;
            }
            case "joes" -> { return (double) (joe.owner() == null ? 1 : JoeNetwork.of(joe.owner()).size()); }
            case "light" -> { return (double) joe.level().getMaxLocalRawBrightness(pos(a, 0)); }
            default -> throw new JoeExpr.ExprError("unknown function '" + f + "'");
        }
    }

    String blockId(BlockPos pos)
    {
        if (!joe.level().isLoaded(pos))
        {
            return "unloaded";
        }
        return BuiltInRegistries.BLOCK.getKey(joe.level().getBlockState(pos).getBlock()).toString();
    }
}
