package org.avp.entity.creature;

import org.avp.registry.ModTags;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Engineers and Space Jockeys. Vast and unhurried: they ignore you until you strike one (then
 * every Engineer nearby turns on you), crush anything they hit, and destroy any Xenomorph that
 * comes close - they know what those are.
 */
public class EngineerEntity extends Monster
{
    private CreatureSpec spec;

    public EngineerEntity(EntityType<? extends EngineerEntity> type, Level level)
    {
        super(type, level);
        this.xpReward = spec().xp();
    }

    public CreatureSpec spec()
    {
        if (spec == null)
        {
            spec = Creatures.get(getType());
        }
        return spec;
    }

    @Override
    protected void registerGoals()
    {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.1, true));
        goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6));
        goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 12F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this, EngineerEntity.class).setAlertOthers());
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false,
                e -> e.getType().is(ModTags.XENOMORPHS) && e.distanceTo(this) < 12));
    }

    @Override
    public boolean doHurtTarget(Entity target)
    {
        boolean hit = super.doHurtTarget(target);
        if (hit)
        {
            target.push(getLookAngle().x * 1.2, 0.5, getLookAngle().z * 1.2);
            target.hurtMarked = true;
        }
        return hit;
    }

    @Override
    public boolean removeWhenFarAway(double distance)
    {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return SoundEvents.RAVAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.RAVAGER_DEATH;
    }

    @Override
    public float getVoicePitch()
    {
        return 0.6F;
    }
}
