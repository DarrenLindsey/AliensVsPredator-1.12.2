package org.avp.entity.human;

import java.util.EnumSet;

import org.avp.config.AvpConfig;
import org.avp.entity.creature.CreatureAbilities;
import org.avp.entity.creature.CreatureProjectile;
import org.avp.entity.creature.CreatureSpec;
import org.avp.entity.creature.Creatures;
import org.avp.registry.ModSounds;
import org.avp.registry.ModTags;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Colonial Marines and Weyland-Yutani combat synthetics. On the players' side (config): they
 * fall in with a nearby player, open fire with pulse rifles in 3-round bursts on Xenomorphs,
 * visible Yautja and hostile monsters, and back off from anything that gets too close.
 * A synthetic is tougher, fights hand to hand as well, never backs off and can't be a host.
 */
public class HumanEntity extends PathfinderMob
{
    private CreatureSpec spec;

    public HumanEntity(EntityType<? extends HumanEntity> type, Level level)
    {
        super(type, level);
        this.xpReward = spec().xp();
        if (getNavigation() instanceof GroundPathNavigation ground)
        {
            ground.setCanOpenDoors(true);
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

    public boolean isSynthetic()
    {
        return spec().id().contains("synthetic");
    }

    private static boolean allied()
    {
        return AvpConfig.server(AvpConfig.SERVER.marinesAlliedToPlayers);
    }

    @Override
    protected void registerGoals()
    {
        goalSelector.addGoal(0, new FloatGoal(this));
        if (!isSynthetic())
        {
            goalSelector.addGoal(1, new AvoidEntityGoal<>(this, LivingEntity.class, 4.0F, 1.0, 1.25, e -> e == getTarget()));
        }
        goalSelector.addGoal(2, new CreatureAbilities.ShootGoal(this, CreatureProjectile.Kind.BULLET,
                (float) (double) AvpConfig.server(AvpConfig.SERVER.marineRifleDamage), 2, 28, 25, 45, 3));
        if (isSynthetic())
        {
            goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.1, true));
        }
        goalSelector.addGoal(4, new OpenDoorGoal(this, true));
        goalSelector.addGoal(5, new FallInGoal(this));
        goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7));
        goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this, HumanEntity.class).setAlertOthers());
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 5, true, false, this::isHostile));
    }

    /** Who they open fire on. */
    public boolean isHostile(LivingEntity e)
    {
        if (e instanceof HumanEntity || e.getType().is(ModTags.XENOMORPHS) && e instanceof org.avp.entity.xenomorph.OvamorphEntity)
        {
            return false;
        }
        if (e instanceof Player player)
        {
            return !allied() && !player.isCreative() && !player.isSpectator();
        }
        return e.getType().is(ModTags.XENOMORPHS) || e.getType().is(ModTags.PREDATORS)
                || e instanceof Enemy && !(e instanceof net.minecraft.world.entity.monster.Creeper && e.distanceTo(this) < 6);
    }

    @Override
    public boolean isAlliedTo(Entity other)
    {
        if (other instanceof HumanEntity || other instanceof Player && allied())
        {
            return true;
        }
        return super.isAlliedTo(other);
    }

    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        boolean hurt = super.hurt(source, amount);
        if (hurt && isSynthetic() && level() instanceof ServerLevel server)
        {
            // white synthetic "blood"
            server.sendParticles(ParticleTypes.WHITE_ASH, getX(), getY() + 1.2, getZ(), 12, 0.2, 0.3, 0.2, 0.02);
        }
        return hurt;
    }

    @Override
    public boolean removeWhenFarAway(double distance)
    {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return ModSounds.creature("marine", "hurt");
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return ModSounds.creature("marine", "death");
    }

    /** When there's nothing to shoot, they stick with the nearest player. */
    static class FallInGoal extends Goal
    {
        private final HumanEntity human;
        private Player leader;

        FallInGoal(HumanEntity human)
        {
            this.human = human;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            if (!allied() || human.getTarget() != null || human.tickCount % 20 != 0)
            {
                return false;
            }
            leader = human.level().getNearestPlayer(human, 24);
            return leader != null && !leader.isSpectator() && human.distanceTo(leader) > 6;
        }

        @Override
        public boolean canContinueToUse()
        {
            return leader != null && leader.isAlive() && human.getTarget() == null && human.distanceTo(leader) > 3;
        }

        @Override
        public void tick()
        {
            if (human.tickCount % 10 == 0)
            {
                human.getNavigation().moveTo(leader, 1.1);
            }
        }

        @Override
        public void stop()
        {
            human.getNavigation().stop();
        }
    }
}
