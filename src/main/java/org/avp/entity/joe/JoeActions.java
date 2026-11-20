package org.avp.entity.joe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import org.avp.entity.joe.JoeProgram.Instr;
import org.avp.entity.joe.WorkingJoeEntity.Task;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

/**
 * The "turtle" statements: dig, place, use, attack, pickup, drop, take, put, craft, equip, scan,
 * find, inspect, face. They act through a fake player where Minecraft needs one, so protection
 * mods and block behaviour work as if a person did it.
 */
final class JoeActions
{
    private JoeActions()
    {
    }

    @Nullable
    static IItemHandler container(WorkingJoeEntity joe, BlockPos pos)
    {
        IItemHandler handler = joe.level().getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
        return handler != null ? handler : joe.level().getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.UP);
    }

    /** A fake player standing where the Joe is, looking where it looks. */
    static FakePlayer hands(WorkingJoeEntity joe, ItemStack holding)
    {
        FakePlayer player = FakePlayerFactory.getMinecraft((ServerLevel) joe.level());
        player.moveTo(joe.getX(), joe.getY(), joe.getZ(), joe.getYRot(), joe.getXRot());
        player.setItemInHand(InteractionHand.MAIN_HAND, holding);
        return player;
    }

    static int sensorRange(WorkingJoeEntity joe, int wanted)
    {
        return Math.min(wanted, joe.hasUpgrade("sensor") ? 48 : 12);
    }

    @Nullable
    static Task task(WorkingJoeEntity joe, Instr instr)
    {
        switch (instr.op)
        {
            case DIG ->
            {
                return new Dig(joe, instr.a);
            }
            case PLACE ->
            {
                return new AtBlock(joe, instr.a, 4.0, "placing at ", () -> place(joe, instr));
            }
            case USE ->
            {
                return new AtBlock(joe, instr.a, 4.0, "using ", () -> use(joe, instr));
            }
            case FACE ->
            {
                joe.getLookControl().setLookAt(Vec3.atCenterOf(instr.a));
                joe.setYRot((float) (Math.toDegrees(Math.atan2(instr.a.getZ() + 0.5 - joe.getZ(), instr.a.getX() + 0.5 - joe.getX())) - 90));
                return null;
            }
            case TAKE ->
            {
                return new AtBlock(joe, instr.a, 2.4, "taking from ", () -> take(joe, instr));
            }
            case PUT ->
            {
                return new AtBlock(joe, instr.a, 2.4, "putting into ", () -> put(joe, instr));
            }
            case DROP ->
            {
                ItemStack out = joe.removeItems(instr.item, instr.n);
                if (!out.isEmpty())
                {
                    Vec3 look = joe.getLookAngle();
                    ItemEntity item = new ItemEntity(joe.level(), joe.getX() + look.x * 0.6, joe.getY() + 0.6, joe.getZ() + look.z * 0.6, out);
                    item.setDeltaMovement(look.x * 0.15, 0.1, look.z * 0.15);
                    item.setPickUpDelay(40);
                    joe.level().addFreshEntity(item);
                    joe.swing(InteractionHand.MAIN_HAND);
                }
                return null;
            }
            case PICKUP ->
            {
                return new Pickup(joe, sensorRange(joe, instr.n));
            }
            case ATTACK ->
            {
                LivingEntity target = nearest(joe, "hostile", sensorRange(joe, instr.n));
                if (target != null)
                {
                    joe.setTarget(target);
                }
                return null;
            }
            case EQUIP ->
            {
                if (!joe.tool.isEmpty())
                {
                    ItemStack left = joe.store(joe.tool);
                    if (!left.isEmpty())
                    {
                        joe.spawnAtLocation(left);
                    }
                    joe.tool = ItemStack.EMPTY;
                }
                if (instr.item != Items.AIR)
                {
                    ItemStack got = joe.removeItems(instr.item, 1);
                    if (got.isEmpty())
                    {
                        joe.fault(instr.line, "it has no " + name(instr.item.getDefaultInstance()) + " to equip");
                    }
                    joe.tool = got;
                }
                return null;
            }
            case CRAFT ->
            {
                craft(joe, instr);
                return null;
            }
            case INSPECT ->
            {
                joe.vars.put("block", joe.context.blockId(instr.a));
                BlockState s = joe.level().getBlockState(instr.a);
                joe.vars.put("solid", !s.getCollisionShape(joe.level(), instr.a).isEmpty());
                return null;
            }
            case SCAN ->
            {
                return new Scan(joe, instr.text, sensorRange(joe, instr.n));
            }
            case FIND ->
            {
                LivingEntity found = nearest(joe, instr.text, sensorRange(joe, instr.n));
                Entity any = found != null ? found : nearestItem(joe, instr.text, sensorRange(joe, instr.n));
                setFound(joe, any == null ? null : any.blockPosition());
                joe.vars.put("target", any == null ? "" : any.getName().getString());
                return null;
            }
            default ->
            {
                return null;
            }
        }
    }

    static String name(ItemStack stack)
    {
        return stack.getHoverName().getString();
    }

    static void setFound(WorkingJoeEntity joe, @Nullable BlockPos pos)
    {
        joe.vars.put("found", pos != null);
        if (pos != null)
        {
            joe.vars.put("fx", (double) pos.getX());
            joe.vars.put("fy", (double) pos.getY());
            joe.vars.put("fz", (double) pos.getZ());
        }
    }

    /** The nearest living thing matching: "hostile", "player", "animal", "joe", or an entity id. */
    @Nullable
    static LivingEntity nearest(WorkingJoeEntity joe, String what, int range)
    {
        return joe.level().getEntitiesOfClass(LivingEntity.class, joe.getBoundingBox().inflate(range), e -> e != joe && e.isAlive() && matches(joe, e, what))
                .stream().min(Comparator.comparingDouble(joe::distanceToSqr)).orElse(null);
    }

    @Nullable
    static ItemEntity nearestItem(WorkingJoeEntity joe, String what, int range)
    {
        if (!what.equals("item") && !what.contains(":"))
        {
            return null;
        }
        return joe.level().getEntitiesOfClass(ItemEntity.class, joe.getBoundingBox().inflate(range),
                e -> what.equals("item") || BuiltInRegistries.ITEM.getKey(e.getItem().getItem()).toString().equals(what))
                .stream().min(Comparator.comparingDouble(joe::distanceToSqr)).orElse(null);
    }

    private static boolean matches(WorkingJoeEntity joe, LivingEntity e, String what)
    {
        return switch (what)
        {
            case "hostile", "enemy" -> e instanceof Enemy || e.getType().is(org.avp.registry.ModTags.XENOMORPHS)
                    || e instanceof WorkingJoeEntity other && other.isRogue();
            case "player" -> e instanceof Player p && !p.isSpectator();
            case "animal" -> e instanceof net.minecraft.world.entity.animal.Animal;
            case "joe" -> e instanceof WorkingJoeEntity;
            case "xeno", "xenomorph" -> e.getType().is(org.avp.registry.ModTags.XENOMORPHS);
            default -> BuiltInRegistries.ENTITY_TYPE.getKey(e.getType()).toString().equals(what.contains(":") ? what : "minecraft:" + what);
        };
    }

    // ------------------------------------------------------------------ instant actions at a block

    private static void place(WorkingJoeEntity joe, Instr instr)
    {
        Level level = joe.level();
        if (!level.getBlockState(instr.a).canBeReplaced())
        {
            joe.fault(instr.line, "can't place at " + WorkingJoeEntity.where(instr.a) + ": something is there");
            return;
        }
        if (!(instr.item instanceof BlockItem blockItem))
        {
            joe.fault(instr.line, name(instr.item.getDefaultInstance()) + " isn't a block");
            return;
        }
        ItemStack one = joe.removeItems(instr.item, 1);
        if (one.isEmpty())
        {
            joe.fault(instr.line, "it has no " + name(instr.item.getDefaultInstance()));
            return;
        }
        FakePlayer player = hands(joe, one);
        BlockHitResult hit = new BlockHitResult(Vec3.atCenterOf(instr.a), Direction.UP, instr.a, false);
        var result = blockItem.place(new BlockPlaceContext(player, InteractionHand.MAIN_HAND, one, hit));
        if (!result.consumesAction())
        {
            joe.store(one);
            joe.fault(instr.line, "couldn't place " + name(one) + " at " + WorkingJoeEntity.where(instr.a));
        }
        player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        joe.swing(InteractionHand.MAIN_HAND);
    }

    private static void use(WorkingJoeEntity joe, Instr instr)
    {
        ItemStack held = instr.item == null ? ItemStack.EMPTY : joe.removeItems(instr.item, 64);
        if (instr.item != null && held.isEmpty())
        {
            joe.fault(instr.line, "it has no " + name(instr.item.getDefaultInstance()) + " to use");
            return;
        }
        FakePlayer player = hands(joe, held);
        BlockHitResult hit = new BlockHitResult(Vec3.atCenterOf(instr.a), Direction.UP, instr.a, false);
        player.gameMode.useItemOn(player, joe.level(), held, InteractionHand.MAIN_HAND, hit);
        // whatever it's left holding (and anything the action handed it) goes back in its inventory
        for (ItemStack back : List.of(player.getMainHandItem(), player.getOffhandItem()))
        {
            ItemStack left = joe.store(back.copy());
            if (!left.isEmpty())
            {
                joe.spawnAtLocation(left);
            }
        }
        for (int i = 0; i < player.getInventory().getContainerSize(); i++)
        {
            ItemStack extra = player.getInventory().removeItemNoUpdate(i);
            if (!extra.isEmpty() && !joe.store(extra).isEmpty())
            {
                joe.spawnAtLocation(extra);
            }
        }
        player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        player.closeContainer();
        joe.swing(InteractionHand.MAIN_HAND);
    }

    private static void take(WorkingJoeEntity joe, Instr instr)
    {
        IItemHandler from = container(joe, instr.a);
        if (from == null)
        {
            joe.fault(instr.line, "there is no container at " + WorkingJoeEntity.where(instr.a));
            return;
        }
        int wanted = instr.n, got = 0;
        for (int slot = 0; slot < from.getSlots() && wanted > 0; slot++)
        {
            ItemStack in = from.getStackInSlot(slot);
            if (in.isEmpty() || instr.item != null && !in.is(instr.item))
            {
                continue;
            }
            ItemStack sim = from.extractItem(slot, Math.min(wanted, in.getMaxStackSize()), true);
            ItemStack left = joe.store(sim.copy());
            int moved = sim.getCount() - left.getCount();
            if (moved > 0)
            {
                from.extractItem(slot, moved, false);
                wanted -= moved;
                got += moved;
            }
            if (!left.isEmpty())
            {
                break; // it's full
            }
        }
        joe.vars.put("moved", (double) got);
        joe.swing(InteractionHand.MAIN_HAND);
    }

    private static void put(WorkingJoeEntity joe, Instr instr)
    {
        IItemHandler into = container(joe, instr.a);
        if (into == null)
        {
            joe.fault(instr.line, "there is no container at " + WorkingJoeEntity.where(instr.a));
            return;
        }
        ItemStack out = joe.removeItems(instr.item, instr.n);
        int count = out.getCount();
        ItemStack left = ItemHandlerHelper.insertItemStacked(into, out, false);
        while (!left.isEmpty() && count > 0)
        {
            // more than one kind (no item named): keep going until nothing more fits
            ItemStack back = joe.store(left);
            if (!back.isEmpty())
            {
                joe.spawnAtLocation(back);
            }
            break;
        }
        int moved = count - left.getCount();
        if (instr.item == null && left.isEmpty())
        {
            // put everything: repeat for the other kinds it carries
            for (int guard = 0; guard < 27 && joe.count(null) > 0; guard++)
            {
                ItemStack next = joe.removeItems(null, 64);
                ItemStack rest = ItemHandlerHelper.insertItemStacked(into, next, false);
                moved += next.getCount() - rest.getCount();
                if (!rest.isEmpty())
                {
                    joe.store(rest);
                    break;
                }
            }
        }
        joe.vars.put("moved", (double) moved);
        joe.swing(InteractionHand.MAIN_HAND);
    }

    /** Crafts from its own inventory (needs a fabricator module). */
    private static void craft(WorkingJoeEntity joe, Instr instr)
    {
        if (!joe.hasUpgrade("fabricator"))
        {
            joe.fault(instr.line, "crafting needs a fabricator module");
            return;
        }
        ServerLevel level = (ServerLevel) joe.level();
        List<RecipeHolder<CraftingRecipe>> recipes = new ArrayList<>();
        for (RecipeHolder<CraftingRecipe> r : level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING))
        {
            if (r.value().getResultItem(level.registryAccess()).is(instr.item) && !r.value().getIngredients().isEmpty() && !r.value().isSpecial())
            {
                recipes.add(r);
            }
        }
        if (recipes.isEmpty())
        {
            joe.fault(instr.line, "no crafting recipe makes " + name(instr.item.getDefaultInstance()));
            return;
        }
        int made = 0;
        String missing = "";
        for (int round = 0; round < instr.n; round++)
        {
            boolean crafted = false;
            for (RecipeHolder<CraftingRecipe> r : recipes)
            {
                List<int[]> plan = plan(joe, r.value().getIngredients());
                if (plan == null)
                {
                    Ingredient need = firstMissing(joe, r.value().getIngredients());
                    missing = need == null || need.getItems().length == 0 ? "ingredients" : name(need.getItems()[0]);
                    continue;
                }
                for (int[] use : plan)
                {
                    joe.inventory.getItem(use[0]).shrink(use[1]);
                }
                ItemStack result = r.value().getResultItem(level.registryAccess()).copy();
                ItemStack left = joe.store(result);
                if (!left.isEmpty())
                {
                    joe.spawnAtLocation(left);
                }
                made += result.getCount();
                crafted = true;
                break;
            }
            if (!crafted)
            {
                break;
            }
        }
        joe.vars.put("crafted", (double) made);
        if (made == 0)
        {
            joe.fault(instr.line, "can't craft " + name(instr.item.getDefaultInstance()) + ": missing " + missing);
        }
        else
        {
            joe.playSound(SoundEvents.UI_STONECUTTER_TAKE_RESULT, 0.6F, 1.2F);
            joe.swing(InteractionHand.MAIN_HAND);
        }
    }

    /** Which slots to take each ingredient from, or null if it doesn't have them. */
    @Nullable
    private static List<int[]> plan(WorkingJoeEntity joe, List<Ingredient> ingredients)
    {
        int[] used = new int[joe.inventory.getContainerSize()];
        List<int[]> plan = new ArrayList<>();
        for (Ingredient ing : ingredients)
        {
            if (ing.isEmpty())
            {
                continue;
            }
            boolean found = false;
            for (int i = 0; i < joe.inventory.getContainerSize(); i++)
            {
                ItemStack s = joe.inventory.getItem(i);
                if (!s.isEmpty() && s.getCount() - used[i] > 0 && ing.test(s))
                {
                    used[i]++;
                    plan.add(new int[] { i, 1 });
                    found = true;
                    break;
                }
            }
            if (!found)
            {
                return null;
            }
        }
        return plan;
    }

    @Nullable
    private static Ingredient firstMissing(WorkingJoeEntity joe, List<Ingredient> ingredients)
    {
        for (Ingredient ing : ingredients)
        {
            if (!ing.isEmpty() && plan(joe, List.of(ing)) == null)
            {
                return ing;
            }
        }
        return null;
    }

    // ------------------------------------------------------------------ tasks

    /** Walk within reach of a block, then do something once. */
    private static final class AtBlock implements Task
    {
        private final WorkingJoeEntity joe;
        private final BlockPos at;
        private final double reach;
        private final String what;
        private final Runnable action;
        private final WorkingJoeEntity.Walker walker;

        AtBlock(WorkingJoeEntity joe, BlockPos at, double reach, String what, Runnable action)
        {
            this.joe = joe;
            this.at = at;
            this.reach = reach;
            this.what = what;
            this.action = action;
            this.walker = joe.new Walker();
        }

        @Override
        public boolean tick()
        {
            if (!walker.reached(at, reach))
            {
                return false;
            }
            if (!walker.failed)
            {
                joe.getLookControl().setLookAt(Vec3.atCenterOf(at));
                action.run();
            }
            return true;
        }

        @Override
        public String describe()
        {
            return what + WorkingJoeEntity.where(at);
        }
    }

    /** Breaks a block, taking as long as a person would (much faster with a drill), and keeps the drops. */
    private static final class Dig implements Task
    {
        private final WorkingJoeEntity joe;
        private final BlockPos at;
        private final WorkingJoeEntity.Walker walker;
        private int progress, needed = -1;

        Dig(WorkingJoeEntity joe, BlockPos at)
        {
            this.joe = joe;
            this.at = at;
            this.walker = joe.new Walker();
        }

        @Override
        public boolean tick()
        {
            if (!walker.reached(at, 4.0))
            {
                return false;
            }
            if (walker.failed)
            {
                return true;
            }
            ServerLevel level = (ServerLevel) joe.level();
            BlockState state = level.getBlockState(at);
            if (state.isAir() || !state.getFluidState().isEmpty() && state.getCollisionShape(level, at).isEmpty())
            {
                return true;
            }
            float hardness = state.getDestroySpeed(level, at);
            if (hardness < 0)
            {
                joe.log("Can't dig " + WorkingJoeEntity.where(at) + ": unbreakable");
                return true;
            }
            boolean drill = joe.hasUpgrade("drill");
            if (needed < 0)
            {
                needed = Math.max(2, (int) (hardness * (drill ? 6 : 30)));
            }
            joe.getLookControl().setLookAt(Vec3.atCenterOf(at));
            if (++progress % 5 == 0)
            {
                joe.swing(InteractionHand.MAIN_HAND);
            }
            level.destroyBlockProgress(joe.getId(), at, (int) (10F * progress / needed) - 1);
            if (progress < needed)
            {
                return false;
            }
            level.destroyBlockProgress(joe.getId(), at, -1);
            ItemStack tool = drill ? new ItemStack(Items.NETHERITE_PICKAXE) : joe.tool.copy();
            FakePlayer player = hands(joe, tool);
            if (NeoForge.EVENT_BUS.post(new BlockEvent.BreakEvent(level, at, state, player)).isCanceled())
            {
                joe.log("Not allowed to dig at " + WorkingJoeEntity.where(at));
                player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                return true;
            }
            boolean drops = !state.requiresCorrectToolForDrops() || tool.isCorrectToolForDrops(state);
            List<ItemStack> loot = drops ? Block.getDrops(state, level, at, level.getBlockEntity(at), joe, tool) : List.of();
            level.destroyBlock(at, false, joe);
            for (ItemStack stack : loot)
            {
                ItemStack left = joe.store(stack.copy());
                if (!left.isEmpty())
                {
                    joe.spawnAtLocation(left);
                }
            }
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            if (!drill && !joe.tool.isEmpty() && joe.tool.isDamageableItem())
            {
                joe.tool.hurtAndBreak(1, level, null, item -> joe.tool = ItemStack.EMPTY);
            }
            return true;
        }

        @Override
        public String describe()
        {
            return "digging at " + WorkingJoeEntity.where(at) + (needed > 0 ? " (" + 100 * progress / needed + "%)" : "");
        }
    }

    /** Collects loose items nearby. */
    private static final class Pickup implements Task
    {
        private final WorkingJoeEntity joe;
        private final int range;
        private int timeout = 600;

        Pickup(WorkingJoeEntity joe, int range)
        {
            this.joe = joe;
            this.range = range;
        }

        @Override
        public boolean tick()
        {
            if (--timeout <= 0 || !joe.hasRoom())
            {
                return true;
            }
            List<ItemEntity> items = joe.level().getEntitiesOfClass(ItemEntity.class, joe.getBoundingBox().inflate(range), e -> e.isAlive() && !e.hasPickUpDelay());
            if (items.isEmpty())
            {
                return true;
            }
            for (ItemEntity item : joe.level().getEntitiesOfClass(ItemEntity.class, joe.getBoundingBox().inflate(1.5)))
            {
                ItemStack left = joe.store(item.getItem().copy());
                if (left.getCount() != item.getItem().getCount())
                {
                    joe.take(item, item.getItem().getCount() - left.getCount());
                    joe.playSound(SoundEvents.ITEM_PICKUP, 0.3F, 1.2F);
                }
                if (left.isEmpty())
                {
                    item.discard();
                }
                else
                {
                    item.setItem(left);
                }
            }
            ItemEntity nearest = items.stream().min(Comparator.comparingDouble(joe::distanceToSqr)).get();
            if (joe.getNavigation().isDone() || joe.tickCount % 20 == 0)
            {
                joe.getNavigation().moveTo(nearest, 1.0);
            }
            return false;
        }

        @Override
        public String describe()
        {
            return "collecting items";
        }
    }

    /** Looks for the nearest block of a kind, a slice at a time (so big scans don't stall the server). */
    private static final class Scan implements Task
    {
        private final WorkingJoeEntity joe;
        private final String block;
        private final int range;
        private final BlockPos origin;
        private int dy;
        private BlockPos best;
        private double bestDist = Double.MAX_VALUE;

        Scan(WorkingJoeEntity joe, String block, int range)
        {
            this.joe = joe;
            this.block = block.contains(":") || block.startsWith("#") ? block : "minecraft:" + block;
            this.range = range;
            this.origin = joe.blockPosition();
            this.dy = -range;
        }

        @Override
        public boolean tick()
        {
            for (int layers = 0; layers < 3 && dy <= range; layers++, dy++)
            {
                for (int dx = -range; dx <= range; dx++)
                {
                    for (int dz = -range; dz <= range; dz++)
                    {
                        BlockPos p = origin.offset(dx, dy, dz);
                        if (!joe.level().isLoaded(p))
                        {
                            continue;
                        }
                        BlockState s = joe.level().getBlockState(p);
                        boolean hit = block.startsWith("#")
                                ? s.getTags().anyMatch(t -> ("#" + t.location()).equals(block))
                                : BuiltInRegistries.BLOCK.getKey(s.getBlock()).toString().equals(block);
                        if (hit)
                        {
                            double d = p.distSqr(origin);
                            if (d < bestDist)
                            {
                                bestDist = d;
                                best = p.immutable();
                            }
                        }
                    }
                }
            }
            if (dy > range)
            {
                setFound(joe, best);
                return true;
            }
            return false;
        }

        @Override
        public String describe()
        {
            return "scanning for " + block.toLowerCase(Locale.ROOT);
        }
    }

    static AABB box(BlockPos a, BlockPos b)
    {
        return new AABB(Vec3.atLowerCornerOf(a), Vec3.atLowerCornerOf(b)).expandTowards(1, 1, 1);
    }
}
