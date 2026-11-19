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

    protected XenomorphEntity(EntityType<? extends XenomorphEntity> type, Level level)
    {
        super(type, level);
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
        // eat royal jelly it walks over
        if (tickCount % 5 == 0)
        {
            for (ItemEntity item : level().getEntitiesOfClass(ItemEntity.class, getBoundingBox().inflate(0.6),
                    e -> e.getItem().is(ModItems.ROYALJELLY.get())))
            {
                jelly += item.getItem().getCount() * JELLY_PER_ITEM;
                playSound(net.minecraft.sounds.SoundEvents.GENERIC_EAT, 0.6F, 1.4F);
                item.discard();
            }
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

    /** The standard adult Xenomorph AI (1.12.2 SpeciesXenomorph.addStandardXenomorphAISet). */
    protected void addHunterGoals(double speed)
    {
        goalSelector.addGoal(0, new net.minecraft.world.entity.ai.goal.FloatGoal(this));
        goalSelector.addGoal(1, new net.minecraft.world.entity.ai.goal.LeapAtTargetGoal(this, 0.5F));
        goalSelector.addGoal(2, new net.minecraft.world.entity.ai.goal.MeleeAttackGoal(this, speed * 1.15, false));
        goalSelector.addGoal(5, new net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal(this, speed * 0.8));
        goalSelector.addGoal(6, new net.minecraft.world.entity.ai.goal.LookAtPlayerGoal(this, net.minecraft.world.entity.player.Player.class, 16F));
        goalSelector.addGoal(7, new net.minecraft.world.entity.ai.goal.RandomLookAroundGoal(this));
        targetSelector.addGoal(0, new net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal(this, XenomorphEntity.class).setAlertOthers());
        targetSelector.addGoal(1, new net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal<>(this,
                net.minecraft.world.entity.player.Player.class, 10, true, false, XenomorphEntity::isPrey));
        targetSelector.addGoal(2, new net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal<>(this,
                net.minecraft.world.entity.LivingEntity.class, 20, true, false,
                e -> isPrey(e) && !(e instanceof net.minecraft.world.entity.monster.Creeper)));
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
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        jelly = tag.getInt("Jelly");
        age = tag.getInt("XenoAge");
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
