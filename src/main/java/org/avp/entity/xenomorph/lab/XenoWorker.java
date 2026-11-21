package org.avp.entity.xenomorph.lab;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.avp.entity.joe.JoeExpr;
import org.avp.entity.joe.JoeProgram;
import org.avp.entity.joe.JoeProgram.Instr;
import org.avp.entity.xenomorph.XenomorphEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

/**
 * Runs a JOE-SCRIPT program on an obedient (neural-laced) Xenomorph. A Xeno can do the physical work -
 * goto, patrol, wait, guard, attack, dig, pickup, take, put, drop, scan, find - with all the flow
 * (set, if/while/repeat/for, functions, labels); it can't talk (say just makes it hiss) or use machines.
 */
public final class XenoWorker
{
    private final XenomorphEntity xeno;
    private final XenoBond bond;
    final SimpleContainer inventory = new SimpleContainer(9);
    private String source = "";
    private BlockPos anchor = BlockPos.ZERO;
    @Nullable
    private JoeProgram program;
    private int pc;
    private boolean halted;
    private final Map<String, Object> vars = new HashMap<>();
    private final Deque<Integer> stack = new ArrayDeque<>();
    @Nullable
    private Task task;
    private String doing = "idle";

    interface Task
    {
        boolean tick();
    }

    XenoWorker(XenomorphEntity xeno, XenoBond bond)
    {
        this.xeno = xeno;
        this.bond = bond;
    }

    public boolean hasProgram()
    {
        return program != null && !halted;
    }

    public String describe()
    {
        return program == null ? "no program" : halted ? "finished" : doing;
    }

    public List<String> load(String text)
    {
        List<String> problems = new ArrayList<>();
        JoeProgram parsed = JoeProgram.parse(text, xeno.blockPosition());
        if (!parsed.ok())
        {
            parsed.errors.forEach(e -> problems.add("Line " + e.line() + ": " + e.message()));
            return problems;
        }
        source = text;
        anchor = xeno.blockPosition();
        program = parsed;
        pc = 0;
        halted = false;
        vars.clear();
        stack.clear();
        task = null;
        return problems;
    }

    void tick()
    {
        // nothing outside the work goal for now
    }

    void run()
    {
        if (program == null || halted)
        {
            return;
        }
        for (int steps = 0; task == null && steps < 200 && !halted; steps++)
        {
            if (pc >= program.body.size())
            {
                pc = stack.isEmpty() ? 0 : stack.pop();
                if (program.body.isEmpty())
                {
                    return;
                }
                continue;
            }
            task = execute(program.body.get(pc++));
        }
        if (task != null && task.tick())
        {
            task = null;
        }
    }

    @Nullable
    private Task execute(Instr instr)
    {
        try
        {
            if (instr.dynamic)
            {
                List<String> problems = new ArrayList<>();
                Instr real = JoeProgram.reparse(substitute(instr.raw), instr.line, anchor, problems);
                if (real == null)
                {
                    return null;
                }
                instr = real;
            }
            switch (instr.op)
            {
                case SET -> vars.put(instr.text, instr.expr.eval(ctx));
                case BRANCH ->
                {
                    if (!JoeExpr.truthy(instr.expr.eval(ctx)))
                    {
                        pc = instr.target;
                    }
                }
                case GOTOPC -> pc = instr.target;
                case CALL ->
                {
                    if (stack.size() < 32)
                    {
                        stack.push(pc);
                        pc = instr.target;
                    }
                }
                case RETURN -> pc = stack.isEmpty() ? program.body.size() : stack.pop();
                case IF, UNLESS ->
                {
                    return JoeExpr.truthy(instr.expr.eval(ctx)) ? execute(instr.inner) : null;
                }
                case JUMP ->
                {
                    pc = program.labels.getOrDefault(instr.text, pc);
                    return wait(1);
                }
                case STOP -> halted = true;
                case WAIT ->
                {
                    doing = "waiting";
                    return wait(instr.n);
                }
                case SAY ->
                {
                    xeno.playSound(SoundEvents.CAT_HISS, 1F, 0.5F);
                }
                case GOTO ->
                {
                    BlockPos to = switch (instr.place)
                    {
                        case HOME, ALARM -> anchor;
                        case OWNER -> bond.ownerPlayer() != null ? bond.ownerPlayer().blockPosition() : null;
                        case POINT -> instr.points.get(0);
                    };
                    return to == null ? null : walk(to, 1.5);
                }
                case PATROL ->
                {
                    List<BlockPos> route = instr.points;
                    int[] at = { 0 };
                    Task[] leg = { walk(route.get(0), 1.5) };
                    doing = "patrolling";
                    return () ->
                    {
                        if (leg[0].tick() && ++at[0] < route.size())
                        {
                            leg[0] = walk(route.get(at[0]), 1.5);
                        }
                        return at[0] >= route.size();
                    };
                }
                case FOLLOW ->
                {
                    int[] left = { instr.m > 0 ? instr.m : 200 };
                    doing = "following";
                    return () ->
                    {
                        var p = bond.ownerPlayer();
                        if (p != null && xeno.distanceToSqr(p) > 9 && xeno.tickCount % 10 == 0)
                        {
                            xeno.getNavigation().moveTo(p, 1.1);
                        }
                        return --left[0] <= 0 || p == null;
                    };
                }
                case GUARD ->
                {
                    bond.post = instr.a;
                    return walk(instr.a, 2);
                }
                case ATTACK ->
                {
                    LivingEntity target = nearest(instr.n);
                    if (target != null)
                    {
                        xeno.setTarget(target);
                    }
                }
                case DIG ->
                {
                    return dig(instr.a);
                }
                case PICKUP ->
                {
                    return pickup(Math.min(16, instr.n));
                }
                case TAKE, PUT ->
                {
                    Instr in = instr;
                    Task go = walk(instr.a, 2.4);
                    return () ->
                    {
                        if (!go.tick())
                        {
                            return false;
                        }
                        transfer(in);
                        return true;
                    };
                }
                case DROP ->
                {
                    for (int i = 0; i < inventory.getContainerSize(); i++)
                    {
                        ItemStack s = inventory.getItem(i);
                        if (!s.isEmpty() && (instr.item == null || s.is(instr.item)))
                        {
                            xeno.spawnAtLocation(inventory.removeItemNoUpdate(i));
                        }
                    }
                }
                case SCAN ->
                {
                    scan(instr.text, Math.min(16, instr.n));
                }
                case FIND ->
                {
                    LivingEntity e = nearest(Math.min(24, instr.n));
                    found(e == null ? null : e.blockPosition());
                }
                case LABEL, NOP ->
                {
                }
                default ->
                {
                    // things only a Joe can do (machines, talking, crafting...): skipped
                }
            }
        }
        catch (JoeExpr.ExprError e)
        {
            // a bad expression: skip the line
        }
        return null;
    }

    private String substitute(String raw)
    {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < raw.length(); i++)
        {
            char c = raw.charAt(i);
            if (c == '$' && i + 1 < raw.length() && Character.isLetter(raw.charAt(i + 1)))
            {
                int j = i + 1;
                while (j < raw.length() && (Character.isLetterOrDigit(raw.charAt(j)) || raw.charAt(j) == '_'))
                {
                    j++;
                }
                out.append(JoeExpr.text(ctx.variable(raw.substring(i + 1, j).toLowerCase(java.util.Locale.ROOT))));
                i = j - 1;
            }
            else
            {
                out.append(c);
            }
        }
        return out.toString();
    }

    private Task wait(int ticks)
    {
        int[] left = { ticks };
        return () -> --left[0] <= 0;
    }

    private Task walk(BlockPos to, double range)
    {
        int[] tries = { 0 };
        double[] best = { Double.MAX_VALUE };
        doing = "going to " + to.getX() + " " + to.getY() + " " + to.getZ();
        return () ->
        {
            double d = xeno.distanceToSqr(Vec3.atBottomCenterOf(to));
            if (d <= range * range)
            {
                xeno.getNavigation().stop();
                return true;
            }
            if (xeno.getNavigation().isDone() || xeno.tickCount % 40 == 0)
            {
                if (d < best[0] - 1)
                {
                    best[0] = d;
                    tries[0] = 0;
                }
                else if (++tries[0] > 12)
                {
                    return true; // can't get there
                }
                xeno.getNavigation().moveTo(to.getX() + 0.5, to.getY(), to.getZ() + 0.5, 1.1);
            }
            return false;
        };
    }

    private Task dig(BlockPos at)
    {
        Task go = walk(at, 3.5);
        int[] progress = { 0 };
        doing = "digging";
        return () ->
        {
            if (!go.tick())
            {
                return false;
            }
            ServerLevel level = (ServerLevel) xeno.level();
            BlockState s = level.getBlockState(at);
            float hardness = s.getDestroySpeed(level, at);
            if (s.isAir() || hardness < 0 || level.getBlockEntity(at) != null || !xeno.mayBreakTerrain(at))
            {
                return true;
            }
            xeno.getLookControl().setLookAt(Vec3.atCenterOf(at));
            if (++progress[0] % 6 == 0)
            {
                xeno.swing(InteractionHand.MAIN_HAND);
            }
            if (progress[0] < Math.max(4, hardness * 15))
            {
                return false;
            }
            for (ItemStack drop : Block.getDrops(s, level, at, null, xeno, new ItemStack(Items.IRON_PICKAXE)))
            {
                ItemStack left = inventory.addItem(drop);
                if (!left.isEmpty())
                {
                    xeno.spawnAtLocation(left);
                }
            }
            level.destroyBlock(at, false, xeno);
            return true;
        };
    }

    private Task pickup(int range)
    {
        int[] timeout = { 400 };
        doing = "collecting";
        return () ->
        {
            List<ItemEntity> items = xeno.level().getEntitiesOfClass(ItemEntity.class, xeno.getBoundingBox().inflate(range), e -> !e.hasPickUpDelay());
            if (items.isEmpty() || --timeout[0] <= 0)
            {
                return true;
            }
            for (ItemEntity item : xeno.level().getEntitiesOfClass(ItemEntity.class, xeno.getBoundingBox().inflate(1.5)))
            {
                ItemStack left = inventory.addItem(item.getItem().copy());
                if (left.isEmpty())
                {
                    item.discard();
                }
                else
                {
                    item.setItem(left);
                }
            }
            if (xeno.getNavigation().isDone())
            {
                xeno.getNavigation().moveTo(items.get(0), 1.1);
            }
            return false;
        };
    }

    private void transfer(Instr instr)
    {
        IItemHandler h = xeno.level().getCapability(Capabilities.ItemHandler.BLOCK, instr.a, Direction.UP);
        if (h == null)
        {
            h = xeno.level().getCapability(Capabilities.ItemHandler.BLOCK, instr.a, null);
        }
        if (h == null)
        {
            return;
        }
        if (instr.op == JoeProgram.Op.PUT)
        {
            for (int i = 0; i < inventory.getContainerSize(); i++)
            {
                ItemStack s = inventory.getItem(i);
                if (!s.isEmpty() && (instr.item == null || s.is(instr.item)))
                {
                    inventory.setItem(i, ItemHandlerHelper.insertItemStacked(h, s, false));
                }
            }
        }
        else
        {
            int want = instr.n;
            for (int i = 0; i < h.getSlots() && want > 0; i++)
            {
                ItemStack s = h.getStackInSlot(i);
                if (s.isEmpty() || instr.item != null && !s.is(instr.item))
                {
                    continue;
                }
                ItemStack got = h.extractItem(i, Math.min(want, 64), false);
                ItemStack left = inventory.addItem(got);
                if (!left.isEmpty())
                {
                    h.insertItem(i, left, false);
                    break;
                }
                want -= got.getCount();
            }
        }
        xeno.swing(InteractionHand.MAIN_HAND);
    }

    @Nullable
    private LivingEntity nearest(int range)
    {
        return xeno.level().getEntitiesOfClass(LivingEntity.class, xeno.getBoundingBox().inflate(range),
                e -> e != xeno && e.isAlive() && (e instanceof Enemy || e.getType().is(org.avp.registry.ModTags.PREDATORS)) && !bond.allied(e))
                .stream().min(java.util.Comparator.comparingDouble(xeno::distanceToSqr)).orElse(null);
    }

    private void scan(String block, int range)
    {
        String id = block.contains(":") ? block : "minecraft:" + block;
        BlockPos best = null;
        double bestD = Double.MAX_VALUE;
        BlockPos c = xeno.blockPosition();
        for (BlockPos p : BlockPos.betweenClosed(c.offset(-range, -range, -range), c.offset(range, range, range)))
        {
            if (BuiltInRegistries.BLOCK.getKey(xeno.level().getBlockState(p).getBlock()).toString().equals(id))
            {
                double d = p.distSqr(c);
                if (d < bestD)
                {
                    bestD = d;
                    best = p.immutable();
                }
            }
        }
        found(best);
    }

    private void found(@Nullable BlockPos p)
    {
        vars.put("found", p != null);
        if (p != null)
        {
            vars.put("fx", (double) p.getX());
            vars.put("fy", (double) p.getY());
            vars.put("fz", (double) p.getZ());
        }
    }

    private int count()
    {
        int n = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            n += inventory.getItem(i).getCount();
        }
        return n;
    }

    private boolean full()
    {
        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            if (inventory.getItem(i).isEmpty())
            {
                return false;
            }
        }
        return true;
    }

    private final JoeExpr.Context ctx = new JoeExpr.Context()
    {
        @Override
        public Object variable(String name)
        {
            if (vars.containsKey(name))
            {
                return vars.get(name);
            }
            BlockPos at = xeno.blockPosition();
            return switch (name)
            {
                case "x" -> (double) at.getX();
                case "y" -> (double) at.getY();
                case "z" -> (double) at.getZ();
                case "health" -> (double) xeno.getHealth();
                case "night" -> xeno.level().isNight();
                case "day" -> !xeno.level().isNight();
                case "full" -> full();
                case "empty" -> count() == 0;
                case "items" -> (double) count();
                case "found" -> false;
                case "hostile" -> nearest(12) != null;
                default -> name.contains(":") ? name : 0.0;
            };
        }

        @Override
        public Object call(String f, List<Object> a) throws JoeExpr.ExprError
        {
            switch (f)
            {
                case "count" -> { return (double) count(); }
                case "random" -> { return (double) xeno.getRandom().nextInt(Math.max(1, (int) JoeExpr.number(a.get(0)))); }
                case "chance" -> { return xeno.getRandom().nextInt(100) < JoeExpr.number(a.get(0)); }
                case "abs" -> { return Math.abs(JoeExpr.number(a.get(0))); }
                case "min" -> { return Math.min(JoeExpr.number(a.get(0)), JoeExpr.number(a.get(1))); }
                case "max" -> { return Math.max(JoeExpr.number(a.get(0)), JoeExpr.number(a.get(1))); }
                case "floor" -> { return Math.floor(JoeExpr.number(a.get(0))); }
                case "dist" -> { return Math.sqrt(xeno.distanceToSqr(JoeExpr.number(a.get(0)), JoeExpr.number(a.get(1)), JoeExpr.number(a.get(2)))); }
                case "isair" -> { return xeno.level().getBlockState(BlockPos.containing(JoeExpr.number(a.get(0)), JoeExpr.number(a.get(1)), JoeExpr.number(a.get(2)))).isAir(); }
                default -> throw new JoeExpr.ExprError("a Xeno can't use '" + f + "'");
            }
        }
    };

    CompoundTag save()
    {
        CompoundTag tag = new CompoundTag();
        tag.putString("Program", source);
        tag.put("Anchor", NbtUtils.writeBlockPos(anchor));
        tag.putInt("PC", pc);
        tag.putBoolean("Halted", halted);
        tag.put("Inventory", inventory.createTag(xeno.registryAccess()));
        return tag;
    }

    void load(CompoundTag tag)
    {
        inventory.fromTag(tag.getList("Inventory", 10), xeno.registryAccess());
        String text = tag.getString("Program");
        if (!text.isEmpty())
        {
            JoeProgram parsed = JoeProgram.parse(text, NbtUtils.readBlockPos(tag, "Anchor").orElse(xeno.blockPosition()));
            if (parsed.ok())
            {
                source = text;
                anchor = NbtUtils.readBlockPos(tag, "Anchor").orElse(xeno.blockPosition());
                program = parsed;
                pc = Math.min(tag.getInt("PC"), parsed.body.size());
                halted = tag.getBoolean("Halted");
            }
        }
    }
}
