package org.avp.entity.xenomorph;

import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import org.avp.registry.ModItems;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

/**
 * Base for every Xenomorph stage (1.12.2 SpeciesAlien + IMaturable + IRoyalOrganism):
 *  - Royal jelly: it seeks out and eats royal jelly lying around; each one adds to its jelly level.
 *  - Maturing: after a set time, or once it has eaten enough jelly, it turns into its next
 *    form (e.g. Chestburster -> Drone). If that form isn't ported yet it simply waits.
 *  - Immune to poison.
 */
public abstract class XenomorphEntity extends Monster
{
    /** Jelly gained per royal jelly item (1.12.2: 100). */
    public static final int JELLY_PER_ITEM = 100;

    private int jelly;
    private int age;
    // film-style behaviour state (see XenoBrain)
    @Nullable
    private net.minecraft.world.phys.Vec3 investigatePos;
    private int investigateTicks;
    private int retreatCooldown;
    @Nullable
    private net.minecraft.core.BlockPos knownTurret;
    private int knownTurretTicks;
    private int calmTicks;

    /** Hidden hunger meter, 0 (starving) .. MAX_HUNGER (full). */
    private int hunger = 60 + new java.util.Random().nextInt(41);
    public static final int MAX_HUNGER = 100;

    protected XenomorphEntity(EntityType<? extends XenomorphEntity> type, Level level)
    {
        super(type, level);
        // they keep away from fire and lava
        setPathfindingMalus(net.minecraft.world.level.pathfinder.PathType.DANGER_FIRE, 16.0F);
        setPathfindingMalus(net.minecraft.world.level.pathfinder.PathType.DAMAGE_FIRE, -1.0F);
        setPathfindingMalus(net.minecraft.world.level.pathfinder.PathType.LAVA, -1.0F);
    }

    // ---- behaviour state used by XenoBrain

    @Nullable
    public net.minecraft.world.phys.Vec3 investigatePos()
    {
        return investigatePos;
    }

    public void investigate(@Nullable net.minecraft.world.phys.Vec3 at)
    {
        investigatePos = at;
        investigateTicks = at == null ? 0 : 400;
    }

    public int retreatCooldown()
    {
        return retreatCooldown;
    }

    public void setRetreatCooldown(int ticks)
    {
        retreatCooldown = ticks;
    }

    @Nullable
    public net.minecraft.core.BlockPos knownTurret()
    {
        return knownTurret;
    }

    public void rememberTurret(net.minecraft.core.BlockPos turret)
    {
        knownTurret = turret;
        knownTurretTicks = 1200; // a minute
    }

    /** What it grows into, or null if this is a final form. */
    @Nullable
    protected ResourceLocation matureInto()
    {
        return null;
    }

    /** Ticks it takes to mature on its own. */
    protected int maturityTime()
    {
        return Integer.MAX_VALUE;
    }

    /** Jelly that makes it mature straight away. */
    protected int maturityJelly()
    {
        return Integer.MAX_VALUE;
    }

    public int jelly()
    {
        return jelly;
    }

    public int hunger()
    {
        return hunger;
    }

    protected void setHunger(int hunger)
    {
        this.hunger = Math.max(0, Math.min(MAX_HUNGER, hunger));
    }

    public void feed(int amount)
    {
        hunger = Math.min(MAX_HUNGER, hunger + amount);
    }

    public boolean isHungry()
    {
        return hunger < org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenoHungryBelow);
    }

    /** Would it go after this creature right now? (Players: see "Xenos Always Hunt Players".) */
    public boolean wantsToHunt(net.minecraft.world.entity.LivingEntity target)
    {
        if (!isPrey(target))
        {
            return false;
        }
        if (target instanceof net.minecraft.world.entity.player.Player)
        {
            return isHungry() || org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenosAlwaysHuntPlayers);
        }
        return isHungry();
    }

    /** Eating what it killed: bigger prey fills it up more. */
    @Override
    public boolean killedEntity(ServerLevel level, net.minecraft.world.entity.LivingEntity victim)
    {
        feed(Math.max(5, (int) (victim.getMaxHealth() * 2)));
        return super.killedEntity(level, victim);
    }

    @Override
    protected void registerGoals()
    {
        goalSelector.addGoal(4, new EatJellyGoal(this));
    }

    @Override
    public void tick()
    {
        super.tick();
        if (level().isClientSide)
        {
            return;
        }
        age++;
        if (investigateTicks > 0 && --investigateTicks == 0)
        {
            investigatePos = null;
        }
        if (retreatCooldown > 0)
        {
            retreatCooldown--;
        }
        if (knownTurretTicks > 0 && --knownTurretTicks == 0)
        {
            knownTurret = null;
        }
        // out of combat they slowly heal
        calmTicks = getTarget() == null && hurtTime == 0 ? calmTicks + 1 : 0;
        if (calmTicks > 200 && tickCount % 40 == 0 && getHealth() < getMaxHealth()
                && org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenosRegenerate))
        {
            heal(1.0F);
        }
        // eat royal jelly it walks over
        if (tickCount % 5 == 0)
        {
            for (ItemEntity item : level().getEntitiesOfClass(ItemEntity.class, getBoundingBox().inflate(0.6),
                    e -> e.getItem().is(ModItems.ROYALJELLY.get())))
            {
                jelly += item.getItem().getCount() * JELLY_PER_ITEM;
                feed(10 * item.getItem().getCount());
                playSound(net.minecraft.sounds.SoundEvents.GENERIC_EAT, 0.6F, 1.4F);
                item.discard();
            }
        }
        // hunger: drains slowly; a full Xenomorph stops chasing animals (but still fights back)
        if (age % org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenoHungerDrainTicks) == 0 && hunger > 0)
        {
            hunger--;
        }
        if (hunger <= 0 && org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenosStarve) && tickCount % 200 == 0)
        {
            hurt(damageSources().starve(), 1.0F);
        }
        if (tickCount % 20 == 0 && getTarget() != null && getTarget() != getLastHurtByMob() && !wantsToHunt(getTarget()))
        {
            setTarget(null);
        }
        if (tickCount % 20 == 0 && matureInto() != null && (age >= maturityTime() || jelly >= maturityJelly()))
        {
            mature();
        }
    }

    /** Turns into the next form, if that creature exists yet. */
    protected void mature()
    {
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getOptional(matureInto()).orElse(null);
        if (type == null || !(level() instanceof ServerLevel level))
        {
            return; // next form not ported yet: keep waiting
        }
        Entity next = type.create(level, null, blockPosition(), MobSpawnType.CONVERSION, false, false);
        if (next == null)
        {
            return;
        }
        next.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
        if (next instanceof XenomorphEntity xeno && jelly >= maturityJelly())
        {
            xeno.jelly = jelly - maturityJelly(); // leftover jelly carries over
        }
        if (next instanceof XenomorphEntity xeno)
        {
            xeno.hunger = hunger;
        }
        if (next instanceof Mob mob && hasCustomName())
        {
            mob.setCustomName(getCustomName());
        }
        level.addFreshEntity(next);
        level.sendParticles(ParticleTypes.ITEM_SLIME, getX(), getY() + 0.3, getZ(), 12, 0.3, 0.3, 0.3, 0.05);
        discard();
    }

    // ---------------------------------------------------------------- shared adult behaviour

    /** Who a Xenomorph hunts: anything living that isn't one of them - but never a host carrying an embryo. */
    public static boolean isPrey(net.minecraft.world.entity.LivingEntity target)
    {
        if (!target.isAlive() || target.getType().is(org.avp.registry.ModTags.XENOMORPHS)
                || target instanceof net.minecraft.world.entity.decoration.ArmorStand
                || target.hasData(org.avp.registry.ModAttachments.EMBRYO))
        {
            return false;
        }
        return !(target instanceof net.minecraft.world.entity.player.Player player) || !player.isCreative() && !player.isSpectator();
    }

    /**
     * The adult Xenomorph AI. With "Smart Xenomorphs" on (default) it's the film-style behaviour in
     * XenoBrain; off, it's the simple 1.12.2 set (SpeciesXenomorph.addStandardXenomorphAISet).
     */
    protected void addHunterGoals(double speed)
    {
        boolean smart = org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.smartXenomorphs);
        goalSelector.addGoal(0, new net.minecraft.world.entity.ai.goal.FloatGoal(this));
        if (smart)
        {
            goalSelector.addGoal(1, new XenoBrain.RetreatGoal(this));
            goalSelector.addGoal(1, new XenoBrain.AvoidTurretGoal(this));
        }
        goalSelector.addGoal(2, new net.minecraft.world.entity.ai.goal.LeapAtTargetGoal(this, 0.5F));
        goalSelector.addGoal(3, smart ? new XenoBrain.StalkAndStrikeGoal(this, speed * 1.2)
                : new net.minecraft.world.entity.ai.goal.MeleeAttackGoal(this, speed * 1.15, false));
        if (org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenosBreakDoors))
        {
            if (getNavigation() instanceof net.minecraft.world.entity.ai.navigation.GroundPathNavigation ground)
            {
                ground.setCanOpenDoors(true); // lets it path to doors (it can't open them - it smashes them)
            }
            goalSelector.addGoal(4, new net.minecraft.world.entity.ai.goal.BreakDoorGoal(this, 120, d -> true));
        }
        if (smart)
        {
            goalSelector.addGoal(4, new XenoBrain.InvestigateGoal(this));
            goalSelector.addGoal(5, new XenoBrain.BreakLightsGoal(this));
            goalSelector.addGoal(6, new XenoBrain.LurkGoal(this, speed * 0.8));
        }
        else
        {
            goalSelector.addGoal(6, new net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal(this, speed * 0.8));
        }
        goalSelector.addGoal(7, new net.minecraft.world.entity.ai.goal.LookAtPlayerGoal(this, net.minecraft.world.entity.player.Player.class, 16F));
        goalSelector.addGoal(8, new net.minecraft.world.entity.ai.goal.RandomLookAroundGoal(this));

        targetSelector.addGoal(0, new net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal(this, XenomorphEntity.class).setAlertOthers());
        if (smart)
        {
            targetSelector.addGoal(1, new XenoBrain.SensePlayersGoal(this));
        }
        else
        {
            targetSelector.addGoal(1, new net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal<>(this,
                    net.minecraft.world.entity.player.Player.class, 10, true, false, this::wantsToHunt));
        }
        // food: only when hungry (see the hunger meter)
        targetSelector.addGoal(2, new net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal<>(this,
                net.minecraft.world.entity.LivingEntity.class, 20, true, false,
                e -> !(e instanceof net.minecraft.world.entity.player.Player) && wantsToHunt(e)
                        && !(e instanceof net.minecraft.world.entity.monster.Creeper)));
    }

    /** Size in the films compared with the classic 1.12.2 size (1 = same). */
    protected float filmScale()
    {
        return 1F;
    }

    /** "Film-Accurate Sizes": newly spawned creatures get their film size (scales the hitbox too). */
    @Override
    public net.minecraft.world.entity.SpawnGroupData finalizeSpawn(net.minecraft.world.level.ServerLevelAccessor level,
            net.minecraft.world.DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable net.minecraft.world.entity.SpawnGroupData data)
    {
        if (org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.filmAccurateSizes) && filmScale() != 1F)
        {
            var scale = getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.SCALE);
            if (scale != null)
            {
                scale.setBaseValue(filmScale());
            }
        }
        return super.finalizeSpawn(level, difficulty, spawnType, data);
    }

    /** Acid blood: hitting a Xenomorph up close splashes the attacker and eats into their weapon. */
    @Override
    public boolean hurt(net.minecraft.world.damagesource.DamageSource source, float amount)
    {
        boolean hurt = super.hurt(source, amount);
        if (hurt && !level().isClientSide && (source.is(org.avp.registry.ModDamageTypes.SENTRY) || source.is(org.avp.registry.ModDamageTypes.SENTRY_AP))
                && knownTurretTicks < 1100)
        {
            XenoBrain.learnTurret(this);
        }
        if (hurt && !level().isClientSide && source.getDirectEntity() instanceof net.minecraft.world.entity.LivingEntity attacker
                && attacker.distanceToSqr(this) < 9 && !attacker.getType().is(org.avp.registry.ModTags.XENOMORPHS))
        {
            double acid = org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenoAcidSplashDamage);
            if (acid > 0)
            {
                attacker.hurt(org.avp.registry.ModDamageTypes.of(level(), org.avp.registry.ModDamageTypes.ACID), (float) acid);
            }
            int eat = org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenoAcidWeaponDamage);
            net.minecraft.world.item.ItemStack weapon = attacker.getMainHandItem();
            if (eat > 0 && weapon.isDamageableItem())
            {
                weapon.hurtAndBreak(eat, attacker, net.minecraft.world.entity.EquipmentSlot.MAINHAND);
            }
            ((ServerLevel) level()).sendParticles(ParticleTypes.ITEM_SLIME, getX(), getY() + getBbHeight() * 0.6, getZ(), 6, 0.2, 0.2, 0.2, 0.05);
        }
        return hurt;
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, net.minecraft.world.damagesource.DamageSource source)
    {
        return false; // 1.12.2: Xenomorphs take no fall damage
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect)
    {
        return effect.getEffect() != MobEffects.POISON && super.canBeAffected(effect);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.putInt("Jelly", jelly);
        tag.putInt("XenoAge", age);
        tag.putInt("Hunger", hunger);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        jelly = tag.getInt("Jelly");
        age = tag.getInt("XenoAge");
        if (tag.contains("Hunger"))
        {
            hunger = tag.getInt("Hunger");
        }
    }

    /** Walks to royal jelly it can see nearby (when not busy fighting). */
    static class EatJellyGoal extends Goal
    {
        private final XenomorphEntity xeno;
        private ItemEntity target;

        EatJellyGoal(XenomorphEntity xeno)
        {
            this.xeno = xeno;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            if (xeno.getTarget() != null || xeno.matureInto() == null || xeno.tickCount % 10 != 0)
            {
                return false;
            }
            List<ItemEntity> jelly = xeno.level().getEntitiesOfClass(ItemEntity.class, xeno.getBoundingBox().inflate(12),
                    e -> e.getItem().is(ModItems.ROYALJELLY.get()) && xeno.hasLineOfSight(e));
            target = jelly.isEmpty() ? null : jelly.get(0);
            return target != null;
        }

        @Override
        public boolean canContinueToUse()
        {
            return target != null && target.isAlive() && !xeno.getNavigation().isDone();
        }

        @Override
        public void start()
        {
            xeno.getNavigation().moveTo(target, 1.1);
        }
    }
}
