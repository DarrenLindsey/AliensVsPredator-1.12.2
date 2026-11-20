package org.avp.entity.creature;

import javax.annotation.Nullable;

import org.avp.registry.ModSounds;
import org.avp.registry.ModTags;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Wildlife and mutants: black goo mutants, Varda's creatures, the Deacon shark, the Hammerpede,
 * the Trilobite, the Aethon, the Yautja hound. Hostile ones hunt players and villagers; neutral
 * ones only fight back (a small one runs). The Predator hound hunts Xenomorphs and monsters.
 */
public class BeastEntity extends Monster
{
    private static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(BeastEntity.class, EntityDataSerializers.BOOLEAN);
    private CreatureSpec spec;
    @Nullable
    private ServerBossEvent bossBar;

    public BeastEntity(EntityType<? extends BeastEntity> type, Level level)
    {
        super(type, level);
        this.xpReward = spec().xp();
        if (has(Ability.FLY))
        {
            this.moveControl = new FlyingMoveControl(this, 20, true);
        }
        if (has(Ability.BOSS) && !level.isClientSide)
        {
            bossBar = new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS);
        }
    }

    public CreatureSpec spec()
    {
        if (spec == null)
        {
            spec = Creatures.get(getType());
        }
        return spec;
    }

    public boolean has(Ability ability)
    {
        return spec() != null && spec().has(ability);
    }

    public boolean isPredatorHound()
    {
        return is("predator_hound");
    }

    @Override
    public boolean isAlliedTo(Entity other)
    {
        if (isPredatorHound() && org.avp.entity.yautja.YautjaEntity.isHuntingPack(other))
        {
            return true;
        }
        return super.isAlliedTo(other);
    }

    private boolean is(String id)
    {
        return spec().id().equals(id);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(CLIMBING, false);
    }

    @Override
    protected void registerGoals()
    {
        if (!has(Ability.AQUATIC) && !has(Ability.FLY))
        {
            goalSelector.addGoal(0, new FloatGoal(this));
        }
        if (!spec().hostile() && spec().health() < 20)
        {
            goalSelector.addGoal(1, new PanicGoal(this, 1.4)); // small neutral things run
        }
        goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
        goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, true));
        goalSelector.addGoal(6, has(Ability.FLY) ? new WaterAvoidingRandomFlyingGoal(this, 0.8)
                : has(Ability.AQUATIC) ? new RandomSwimmingGoal(this, 1.0, 20) : new WaterAvoidingRandomStrollGoal(this, 0.8));
        goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 10F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        if (is("predator_hound"))
        {
            targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false,
                    e -> !org.avp.entity.yautja.YautjaEntity.isHuntingPack(e)
                            && (e.getType().is(ModTags.XENOMORPHS) || e instanceof Enemy && !(e instanceof Creeper))));
            goalSelector.addGoal(5, new HeelGoal(this));
        }
        else if (spec().hostile())
        {
            targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
            targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
            targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, org.avp.entity.human.HumanEntity.class, true));
        }
    }

    @Override
    protected PathNavigation createNavigation(Level level)
    {
        if (has(Ability.FLY))
        {
            FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
            nav.setCanFloat(true);
            return nav;
        }
        if (has(Ability.AQUATIC))
        {
            return new AmphibiousPathNavigation(this, level);
        }
        return has(Ability.CLIMB) ? new WallClimberNavigation(this, level) : super.createNavigation(level);
    }

    @Override
    public boolean onClimbable()
    {
        return entityData.get(CLIMBING);
    }

    @Override
    public boolean canDrownInFluidType(net.neoforged.neoforge.fluids.FluidType type)
    {
        return !has(Ability.AQUATIC) && super.canDrownInFluidType(type);
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source)
    {
        return !has(Ability.FLY) && !has(Ability.CLIMB) && super.causeFallDamage(distance, multiplier, source);
    }

    @Override
    public void aiStep()
    {
        super.aiStep();
        LivingEntity target = getTarget();
        if (has(Ability.AQUATIC) && isInWater() && target != null)
        {
            Vec3 to = target.position().add(0, target.getBbHeight() * 0.5, 0).subtract(position()).normalize();
            setDeltaMovement(getDeltaMovement().scale(0.9).add(to.scale(0.05)));
        }
        if (has(Ability.FLY) && target != null)
        {
            getMoveControl().setWantedPosition(target.getX(), target.getY() + 1.0, target.getZ(), 1.2);
        }
    }

    @Override
    public void tick()
    {
        super.tick();
        if (!level().isClientSide)
        {
            if (isPredatorHound() && tickCount % 10 == 0)
            {
                assistMaster();
            }
            entityData.set(CLIMBING, has(Ability.CLIMB) && horizontalCollision && getTarget() != null && getTarget().getY() > getY() + 0.5);
            if (bossBar != null)
            {
                bossBar.setProgress(getHealth() / getMaxHealth());
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity target)
    {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity victim)
        {
            if (is("hammerpede"))
            {
                victim.addEffect(new MobEffectInstance(MobEffects.POISON, 120, 1), this); // venom
            }
            if (is("goo_mutant") || is("babyhead") || is("yautja_mutant"))
            {
                victim.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 0), this); // the black goo
            }
            if (has(Ability.IMPLANT_ON_BITE) && spec().embryo() != null)
            {
                CreatureAbilities.implantOnBite(this, victim, spec().embryo(), 0.35F);
            }
        }
        return hit;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player)
    {
        super.startSeenByPlayer(player);
        if (bossBar != null)
        {
            bossBar.addPlayer(player);
        }
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player)
    {
        super.stopSeenByPlayer(player);
        if (bossBar != null)
        {
            bossBar.removePlayer(player);
        }
    }

    @Override
    public boolean removeWhenFarAway(double distance)
    {
        return !has(Ability.BOSS) && super.removeWhenFarAway(distance);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return ModSounds.creature(spec().sounds(), "living");
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        SoundEvent s = ModSounds.creature(spec().sounds(), "hurt");
        return s != null ? s : super.getHurtSound(source);
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        SoundEvent s = ModSounds.creature(spec().sounds(), "death");
        return s != null ? s : super.getDeathSound();
    }

    /** The nearest Yautja within 32 blocks: a hound's master. */
    @Nullable
    private org.avp.entity.yautja.YautjaEntity master()
    {
        return level().getEntitiesOfClass(org.avp.entity.yautja.YautjaEntity.class, getBoundingBox().inflate(32), LivingEntity::isAlive)
                .stream().min(java.util.Comparator.comparingDouble(this::distanceToSqr)).orElse(null);
    }

    /** A hound goes for whatever its master is hunting. */
    private void assistMaster()
    {
        org.avp.entity.yautja.YautjaEntity master = master();
        if (master != null && master.getTarget() != null && master.getTarget() != getTarget()
                && !org.avp.entity.yautja.YautjaEntity.isHuntingPack(master.getTarget()))
        {
            setTarget(master.getTarget());
        }
    }

    /** Hounds stay at their master's heel when there's nothing to hunt. */
    static class HeelGoal extends net.minecraft.world.entity.ai.goal.Goal
    {
        private final BeastEntity hound;
        @Nullable
        private org.avp.entity.yautja.YautjaEntity master;

        HeelGoal(BeastEntity hound)
        {
            this.hound = hound;
            setFlags(java.util.EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            if (hound.getTarget() != null || hound.tickCount % 20 >= 2)
            {
                return false;
            }
            master = hound.master();
            return master != null && hound.distanceTo(master) > 8;
        }

        @Override
        public boolean canContinueToUse()
        {
            return master != null && master.isAlive() && hound.getTarget() == null && hound.distanceTo(master) > 4;
        }

        @Override
        public void tick()
        {
            if (hound.tickCount % 10 < 2)
            {
                hound.getNavigation().moveTo(master, 1.2);
            }
        }
    }
}
