package org.avp.entity.creature;

import java.util.EnumSet;

import org.avp.config.AvpConfig;
import org.avp.entity.parasite.Embryo;
import org.avp.entity.parasite.EmbryoKinds;
import org.avp.entity.parasite.FacehuggerEntity;
import org.avp.registry.ModAttachments;
import org.avp.registry.ModDamageTypes;
import org.avp.registry.ModSounds;
import org.avp.registry.ModTags;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** Abilities shared by several creature families (see {@link Ability}). */
public final class CreatureAbilities
{
    /**
     * A bite that plants an embryo (Predalien, Trilobite). Only into a creature that could carry
     * one, and only sometimes.
     */
    public static boolean implantOnBite(LivingEntity biter, LivingEntity victim, String parasiteKind, float chance)
    {
        if (!FacehuggerEntity.isValidHost(victim) || biter.getRandom().nextFloat() >= chance)
        {
            return false;
        }
        victim.setData(ModAttachments.EMBRYO, new Embryo(0, AvpConfig.server(AvpConfig.SERVER.embryoGestationTicks),
                EmbryoKinds.forHost(victim, parasiteKind)));
        biter.playSound(ModSounds.FACEHUGGER_IMPLANT.get(), 0.8F, 0.7F);
        if (victim instanceof Player player)
        {
            player.displayClientMessage(net.minecraft.network.chat.Component.translatable("message.avp.implanted"), true);
        }
        return true;
    }

    /** A Boiler bursting: acid sprays everything around it. */
    public static void acidBurst(Mob mob, float damage, double radius)
    {
        if (!(mob.level() instanceof ServerLevel level))
        {
            return;
        }
        level.playSound(null, mob.blockPosition(), SoundEvents.SLIME_DEATH, mob.getSoundSource(), 1.5F, 0.6F);
        level.sendParticles(ParticleTypes.ITEM_SLIME, mob.getX(), mob.getY() + mob.getBbHeight() * 0.5, mob.getZ(), 80,
                radius * 0.4, mob.getBbHeight() * 0.3, radius * 0.4, 0.15);
        level.sendParticles(ParticleTypes.CLOUD, mob.getX(), mob.getY() + mob.getBbHeight() * 0.5, mob.getZ(), 20, 0.6, 0.6, 0.6, 0.05);
        for (LivingEntity victim : level.getEntitiesOfClass(LivingEntity.class, mob.getBoundingBox().inflate(radius),
                e -> e != mob && !e.getType().is(ModTags.XENOMORPHS)))
        {
            double falloff = 1.0 - Math.min(1.0, victim.distanceTo(mob) / (radius + 1.0));
            victim.hurt(ModDamageTypes.of(level, ModDamageTypes.ACID), (float) (damage * (0.4 + 0.6 * falloff)));
            victim.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1), mob);
            victim.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0), mob);
        }
    }

    /** Ranged attack: spit acid / fire a plasma caster / shoot a rifle, with a cooldown. Doesn't take over movement. */
    public static class ShootGoal extends Goal
    {
        private final Mob mob;
        private final CreatureProjectile.Kind kind;
        private final float damage;
        private final double minRange, maxRange;
        private final int minCooldown, maxCooldown, burst;
        private int cooldown, shotsLeft;

        public ShootGoal(Mob mob, CreatureProjectile.Kind kind, float damage, double minRange, double maxRange, int minCooldown, int maxCooldown, int burst)
        {
            this.mob = mob;
            this.kind = kind;
            this.damage = damage;
            this.minRange = minRange;
            this.maxRange = maxRange;
            this.minCooldown = minCooldown;
            this.maxCooldown = maxCooldown;
            this.burst = burst;
            setFlags(EnumSet.of(Flag.LOOK));
        }

        @Override
        public boolean canUse()
        {
            if (cooldown > 0)
            {
                cooldown--;
                return false;
            }
            LivingEntity target = mob.getTarget();
            if (target == null || !target.isAlive())
            {
                return false;
            }
            double d = mob.distanceTo(target);
            return d >= minRange && d <= maxRange && mob.getSensing().hasLineOfSight(target);
        }

        @Override
        public void start()
        {
            shotsLeft = burst;
        }

        @Override
        public boolean canContinueToUse()
        {
            return shotsLeft > 0 && mob.getTarget() != null && mob.getTarget().isAlive();
        }

        @Override
        public boolean requiresUpdateEveryTick()
        {
            return true;
        }

        @Override
        public void tick()
        {
            LivingEntity target = mob.getTarget();
            if (target == null)
            {
                return;
            }
            mob.getLookControl().setLookAt(target, 30F, 30F);
            if (mob.tickCount % 4 == 0)
            {
                float speed = switch (kind)
                {
                    case ACID -> 1.1F;
                    case PLASMA -> 1.8F;
                    case BULLET -> 3.5F;
                };
                CreatureProjectile.shoot(mob, target, kind, damage, speed, kind == CreatureProjectile.Kind.BULLET ? 3F : 1.5F);
                mob.playSound(switch (kind)
                {
                    case ACID -> SoundEvents.LLAMA_SPIT;
                    case PLASMA -> SoundEvents.BEACON_DEACTIVATE;
                    case BULLET -> ModSounds.PULSE_RIFLE.get();
                }, kind == CreatureProjectile.Kind.BULLET ? 0.6F : 1.0F, 0.9F + mob.getRandom().nextFloat() * 0.2F);
                shotsLeft--;
                if (mob instanceof org.avp.entity.yautja.YautjaEntity yautja)
                {
                    yautja.reveal(40); // the plasma caster gives away where it is
                }
            }
        }

        @Override
        public void stop()
        {
            cooldown = minCooldown + mob.getRandom().nextInt(Math.max(1, maxCooldown - minCooldown));
        }
    }

    /** Crusher / charging beasts: a run-up straight at the prey that bowls it over. */
    public static class ChargeGoal extends Goal
    {
        private final Mob mob;
        private int cooldown, ticks;
        private Vec3 direction = Vec3.ZERO;

        public ChargeGoal(Mob mob)
        {
            this.mob = mob;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
        }

        @Override
        public boolean canUse()
        {
            if (cooldown > 0)
            {
                cooldown--;
                return false;
            }
            LivingEntity target = mob.getTarget();
            if (target == null || !mob.onGround())
            {
                return false;
            }
            double d = mob.distanceTo(target);
            return d > 5 && d < 18 && Math.abs(target.getY() - mob.getY()) < 2 && mob.getSensing().hasLineOfSight(target);
        }

        @Override
        public void start()
        {
            LivingEntity target = mob.getTarget();
            direction = new Vec3(target.getX() - mob.getX(), 0, target.getZ() - mob.getZ()).normalize();
            ticks = 0;
            mob.getNavigation().stop();
            mob.playSound(SoundEvents.RAVAGER_ROAR, 1.2F, 0.8F);
        }

        @Override
        public boolean canContinueToUse()
        {
            return ticks < 40 && mob.getTarget() != null && mob.getTarget().isAlive();
        }

        @Override
        public boolean requiresUpdateEveryTick()
        {
            return true;
        }

        @Override
        public void tick()
        {
            ticks++;
            LivingEntity target = mob.getTarget();
            if (target == null)
            {
                ticks = 40;
                return;
            }
            if (ticks < 10)
            {
                mob.getLookControl().setLookAt(target, 30F, 30F); // winding up
                return;
            }
            mob.setYRot((float) (Math.atan2(direction.z, direction.x) * 180 / Math.PI) - 90F);
            mob.yBodyRot = mob.getYRot();
            Vec3 v = mob.getDeltaMovement();
            mob.setDeltaMovement(direction.x * 0.65, v.y, direction.z * 0.65);
            if (mob.level() instanceof ServerLevel level && ticks % 2 == 0)
            {
                level.sendParticles(ParticleTypes.POOF, mob.getX(), mob.getY() + 0.1, mob.getZ(), 2, 0.3, 0, 0.3, 0.01);
            }
            if (mob.getBoundingBox().inflate(0.6).intersects(target.getBoundingBox()))
            {
                target.hurt(mob.damageSources().mobAttack(mob), (float) mob.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE) * 1.5F);
                target.push(direction.x * 2.0, 0.6, direction.z * 2.0);
                target.hurtMarked = true;
                ticks = 40;
            }
            else if (mob.horizontalCollision)
            {
                // slammed into a wall: dazed for a moment
                mob.playSound(SoundEvents.ANVIL_LAND, 0.8F, 0.6F);
                mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 3));
                ticks = 40;
            }
        }

        @Override
        public void stop()
        {
            cooldown = 100 + mob.getRandom().nextInt(60);
        }
    }

    private CreatureAbilities()
    {
    }
}
