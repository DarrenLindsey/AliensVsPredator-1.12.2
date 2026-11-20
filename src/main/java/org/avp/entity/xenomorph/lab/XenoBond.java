package org.avp.entity.xenomorph.lab;

import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

import org.avp.entity.xenomorph.XenomorphEntity;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

/**
 * A lab-grown Xenomorph's bond to the person it imprinted on. A bonded Xeno forgets the hive: it
 * follows, stays or guards, fights for its owner, and - with the neural lace (OBEDIENCE) - runs
 * JOE-SCRIPT work programs like a Working Joe. Too many splices make it unstable: now and then an
 * unstable one reverts to the wild.
 */
public final class XenoBond
{
    public enum Mode
    {
        FOLLOW, STAY, GUARD, WORK
    }

    private final XenomorphEntity xeno;
    public UUID owner;
    public Mode mode = Mode.FOLLOW;
    public final Set<XenoTrait> traits = new LinkedHashSet<>();
    public int instability;
    @Nullable
    public BlockPos post;
    public final XenoWorker worker;

    public XenoBond(XenomorphEntity xeno, UUID owner)
    {
        this.xeno = xeno;
        this.owner = owner;
        this.worker = new XenoWorker(xeno, this);
    }

    public boolean has(XenoTrait t)
    {
        return traits.contains(t);
    }

    @Nullable
    public Player ownerPlayer()
    {
        return xeno.level().getPlayerByUUID(owner);
    }

    // ------------------------------------------------------------------ setting up

    /** Throws out the hive mind and gives it a pet's goals. */
    public void install()
    {
        xeno.goalSelector.removeAllGoals(g -> true);
        xeno.targetSelector.removeAllGoals(g -> true);
        xeno.setHiveId(null);
        xeno.setPersistenceRequired();
        double speed = 1.1;
        xeno.goalSelector.addGoal(0, new FloatGoal(xeno));
        xeno.goalSelector.addGoal(1, new StayGoal());
        xeno.goalSelector.addGoal(2, new MeleeAttackGoal(xeno, speed * 1.2, true));
        xeno.goalSelector.addGoal(3, new WorkGoal());
        xeno.goalSelector.addGoal(4, new FollowGoal(speed));
        xeno.goalSelector.addGoal(5, new GuardGoal(speed));
        xeno.goalSelector.addGoal(7, new LookAtPlayerGoal(xeno, Player.class, 8F));
        xeno.goalSelector.addGoal(8, new RandomLookAroundGoal(xeno));
        xeno.targetSelector.addGoal(1, new OwnerAttackedGoal(true));
        xeno.targetSelector.addGoal(2, new OwnerAttackedGoal(false));
        xeno.targetSelector.addGoal(3, new HurtByTargetGoal(xeno)
        {
            @Override
            public boolean canUse()
            {
                return super.canUse() && !(xeno.getLastHurtByMob() instanceof Player p && p.getUUID().equals(owner));
            }
        });
        xeno.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(xeno, LivingEntity.class, 10, true, false, this::isThreat));
    }

    /** What it attacks by itself: hostile things near it (not its owner's). */
    private boolean isThreat(LivingEntity e)
    {
        if (mode == Mode.STAY || e instanceof Creeper || allied(e))
        {
            return false;
        }
        return (e instanceof Enemy || e.getType().is(org.avp.registry.ModTags.PREDATORS)) && e.distanceTo(xeno) < 14;
    }

    /** Its owner, its owner's other Xenos and Working Joes. */
    public boolean allied(net.minecraft.world.entity.Entity e)
    {
        if (e instanceof Player p)
        {
            return p.getUUID().equals(owner);
        }
        if (e instanceof XenomorphEntity x && x.bond() != null)
        {
            return owner.equals(x.bond().owner);
        }
        return e instanceof org.avp.entity.joe.WorkingJoeEntity joe && owner.equals(joe.owner());
    }

    private static final ResourceLocation[] IDS = new ResourceLocation[XenoTrait.values().length];

    private static ResourceLocation id(XenoTrait t)
    {
        if (IDS[t.ordinal()] == null)
        {
            IDS[t.ordinal()] = ResourceLocation.fromNamespaceAndPath(org.avp.AliensVsPredator.MOD_ID, "xeno_trait_" + t.id());
        }
        return IDS[t.ordinal()];
    }

    /** On hatching: the traits as attribute bonuses. */
    public void applyTraits()
    {
        for (XenoTrait t : traits)
        {
            switch (t)
            {
                case ARMOUR -> add(Attributes.ARMOR, t, 8, AttributeModifier.Operation.ADD_VALUE);
                case VITALITY -> add(Attributes.MAX_HEALTH, t, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
                case STRENGTH -> add(Attributes.ATTACK_DAMAGE, t, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
                case SPEED -> add(Attributes.MOVEMENT_SPEED, t, 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
                case SIZE ->
                {
                    add(Attributes.SCALE, t, 0.3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
                    if (!has(XenoTrait.VITALITY))
                    {
                        add(Attributes.MAX_HEALTH, t, 0.3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
                    }
                }
                default ->
                {
                }
            }
        }
        xeno.setHealth(xeno.getMaxHealth());
    }

    private void add(net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attr, XenoTrait t, double amount,
            AttributeModifier.Operation op)
    {
        var inst = xeno.getAttribute(attr);
        if (inst != null)
        {
            inst.removeModifier(id(t));
            inst.addPermanentModifier(new AttributeModifier(id(t), amount, op));
        }
    }

    // ------------------------------------------------------------------ ticking

    public void serverTick(ServerLevel level)
    {
        if (has(XenoTrait.REGENERATION) && xeno.tickCount % 40 == 0 && xeno.getHealth() < xeno.getMaxHealth())
        {
            xeno.heal(1F);
        }
        // an unstable one may revert
        if (instability > 0 && xeno.tickCount % 6000 == 0 && level.random.nextInt(100) < instability * 8)
        {
            Player p = ownerPlayer();
            if (p != null)
            {
                p.sendSystemMessage(Component.literal("[W-Y Genetics] Specimen " + xeno.getName().getString() + " has reverted. Containment advised.")
                        .withStyle(ChatFormatting.RED));
            }
            revert(level);
        }
        worker.tick();
    }

    /** Back to the wild: a fresh wild one of the same kind takes its place. */
    private void revert(ServerLevel level)
    {
        var wild = xeno.getType().create(level);
        if (wild != null)
        {
            wild.moveTo(xeno.getX(), xeno.getY(), xeno.getZ(), xeno.getYRot(), 0);
            level.addFreshEntity(wild);
            xeno.playSound(SoundEvents.RAVAGER_ROAR, 1.2F, 1.4F);
            xeno.discard();
        }
    }

    // ------------------------------------------------------------------ the owner

    public InteractionResult interact(Player player, InteractionHand hand)
    {
        if (!player.getUUID().equals(owner))
        {
            return InteractionResult.PASS;
        }
        if (xeno.level().isClientSide)
        {
            return InteractionResult.SUCCESS;
        }
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(Items.WRITABLE_BOOK) || stack.is(Items.WRITTEN_BOOK))
        {
            if (!has(XenoTrait.OBEDIENCE))
            {
                player.displayClientMessage(Component.literal("It needs a neural lace (OBEDIENCE) to follow programs.").withStyle(ChatFormatting.RED), true);
                return InteractionResult.CONSUME;
            }
            List<String> errors = worker.load(org.avp.entity.joe.JoeBooks.text(stack));
            if (errors.isEmpty())
            {
                mode = Mode.WORK;
                player.displayClientMessage(Component.literal(xeno.getName().getString() + ": program accepted. WORK mode.").withStyle(ChatFormatting.GOLD), true);
            }
            else
            {
                errors.stream().limit(5).forEach(e -> player.sendSystemMessage(Component.literal("  " + e).withStyle(ChatFormatting.RED)));
            }
            return InteractionResult.CONSUME;
        }
        if (stack.is(org.avp.registry.ModItems.ROYALJELLY.get()) || stack.is(Items.BEEF) || stack.is(Items.PORKCHOP) || stack.is(Items.MUTTON))
        {
            xeno.heal(stack.is(org.avp.registry.ModItems.ROYALJELLY.get()) ? 20F : 6F);
            stack.consume(1, player);
            xeno.playSound(SoundEvents.GENERIC_EAT, 0.8F, 0.6F);
            return InteractionResult.CONSUME;
        }
        if (stack.isEmpty() && player.isShiftKeyDown())
        {
            player.sendSystemMessage(Component.literal("== " + xeno.getName().getString() + " | bonded specimen ==").withStyle(ChatFormatting.DARK_GREEN));
            player.sendSystemMessage(Component.literal("Mode: " + mode + "   Health: " + Math.round(xeno.getHealth()) + "/" + Math.round(xeno.getMaxHealth())
                    + "   Instability: " + instability).withStyle(ChatFormatting.GRAY));
            if (!traits.isEmpty())
            {
                player.sendSystemMessage(Component.literal("Traits: " + String.join(", ", traits.stream().map(XenoTrait::id).toList()))
                        .withStyle(ChatFormatting.GRAY));
            }
            if (mode == Mode.WORK)
            {
                player.sendSystemMessage(Component.literal("Work: " + worker.describe()).withStyle(ChatFormatting.GRAY));
            }
            return InteractionResult.CONSUME;
        }
        if (stack.isEmpty())
        {
            Mode[] order = has(XenoTrait.OBEDIENCE) && worker.hasProgram() ? Mode.values() : new Mode[] { Mode.FOLLOW, Mode.STAY, Mode.GUARD };
            int i = 0;
            for (; i < order.length && order[i] != mode; i++)
            {
            }
            mode = order[(i + 1) % order.length];
            if (mode == Mode.GUARD)
            {
                post = xeno.blockPosition();
            }
            xeno.getNavigation().stop();
            xeno.setTarget(null);
            xeno.playSound(SoundEvents.CAT_PURREOW, 0.6F, 0.4F);
            player.displayClientMessage(Component.literal(xeno.getName().getString() + ": " + mode).withStyle(ChatFormatting.DARK_GREEN), true);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    // ------------------------------------------------------------------ goals

    private class StayGoal extends Goal
    {
        StayGoal()
        {
            setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
        }

        @Override
        public boolean canUse()
        {
            return mode == Mode.STAY;
        }

        @Override
        public void start()
        {
            xeno.getNavigation().stop();
        }
    }

    private class FollowGoal extends Goal
    {
        private final double speed;

        FollowGoal(double speed)
        {
            this.speed = speed;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse()
        {
            Player p = ownerPlayer();
            return mode == Mode.FOLLOW && p != null && !p.isSpectator() && xeno.distanceToSqr(p) > 16;
        }

        @Override
        public boolean canContinueToUse()
        {
            Player p = ownerPlayer();
            return mode == Mode.FOLLOW && p != null && xeno.distanceToSqr(p) > 6;
        }

        @Override
        public void tick()
        {
            Player p = ownerPlayer();
            if (p == null)
            {
                return;
            }
            xeno.getLookControl().setLookAt(p, 10, xeno.getMaxHeadXRot());
            if (xeno.distanceToSqr(p) > 28 * 28 && p.onGround())
            {
                xeno.teleportTo(p.getX(), p.getY(), p.getZ());
                xeno.getNavigation().stop();
            }
            else if (xeno.tickCount % 10 == 0)
            {
                xeno.getNavigation().moveTo(p, speed);
            }
        }

        @Override
        public void stop()
        {
            xeno.getNavigation().stop();
        }
    }

    private class GuardGoal extends Goal
    {
        private final double speed;

        GuardGoal(double speed)
        {
            this.speed = speed;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            return mode == Mode.GUARD && post != null && (xeno.blockPosition().distSqr(post) > 36 || xeno.getRandom().nextInt(80) == 0);
        }

        @Override
        public void start()
        {
            Vec3 to = Vec3.atBottomCenterOf(post).add(xeno.getRandom().nextInt(7) - 3, 0, xeno.getRandom().nextInt(7) - 3);
            xeno.getNavigation().moveTo(to.x, to.y, to.z, speed * 0.8);
        }

        @Override
        public boolean canContinueToUse()
        {
            return mode == Mode.GUARD && !xeno.getNavigation().isDone();
        }
    }

    private class WorkGoal extends Goal
    {
        WorkGoal()
        {
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse()
        {
            return mode == Mode.WORK && worker.hasProgram() && xeno.getTarget() == null;
        }

        @Override
        public boolean requiresUpdateEveryTick()
        {
            return true;
        }

        @Override
        public void tick()
        {
            worker.run();
        }

        @Override
        public void stop()
        {
            xeno.getNavigation().stop();
        }
    }

    /** Fights whoever hurts its owner (first) or whoever its owner hits (second). */
    private class OwnerAttackedGoal extends TargetGoal
    {
        private final boolean defend;
        private int seen;
        @Nullable
        private LivingEntity other;

        OwnerAttackedGoal(boolean defend)
        {
            super(xeno, false);
            this.defend = defend;
            setFlags(EnumSet.of(Flag.TARGET));
        }

        @Override
        public boolean canUse()
        {
            Player p = ownerPlayer();
            if (p == null || mode == Mode.STAY)
            {
                return false;
            }
            other = defend ? p.getLastHurtByMob() : p.getLastHurtMob();
            int stamp = defend ? p.getLastHurtByMobTimestamp() : p.getLastHurtMobTimestamp();
            return other != null && stamp != seen && !allied(other) && canAttack(other, net.minecraft.world.entity.ai.targeting.TargetingConditions.DEFAULT);
        }

        @Override
        public void start()
        {
            mob.setTarget(other);
            Player p = ownerPlayer();
            if (p != null)
            {
                seen = defend ? p.getLastHurtByMobTimestamp() : p.getLastHurtMobTimestamp();
            }
            super.start();
        }
    }

    // ------------------------------------------------------------------ saving

    public CompoundTag save()
    {
        CompoundTag tag = new CompoundTag();
        tag.putUUID("Owner", owner);
        tag.putString("Mode", mode.name());
        ListTag t = new ListTag();
        traits.forEach(tr -> t.add(StringTag.valueOf(tr.id())));
        tag.put("Traits", t);
        tag.putInt("Instability", instability);
        if (post != null)
        {
            tag.putLong("Post", post.asLong());
        }
        tag.put("Worker", worker.save());
        return tag;
    }

    public static XenoBond load(XenomorphEntity xeno, CompoundTag tag)
    {
        XenoBond bond = new XenoBond(xeno, tag.getUUID("Owner"));
        try
        {
            bond.mode = Mode.valueOf(tag.getString("Mode"));
        }
        catch (IllegalArgumentException ignored)
        {
        }
        for (Tag t : tag.getList("Traits", Tag.TAG_STRING))
        {
            XenoTrait tr = XenoTrait.byId(t.getAsString());
            if (tr != null)
            {
                bond.traits.add(tr);
            }
        }
        bond.instability = tag.getInt("Instability");
        bond.post = tag.contains("Post") ? BlockPos.of(tag.getLong("Post")) : null;
        bond.worker.load(tag.getCompound("Worker"));
        return bond;
    }
}
