package org.avp.entity.creature;

import org.avp.registry.ModDamageTypes;
import org.avp.registry.ModEntities;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

/**
 * What creatures shoot: a Spitter's acid, a Yautja plasma caster bolt, a Marine's pulse rifle round.
 * Drawn as particles only (no model).
 */
public class CreatureProjectile extends ThrowableProjectile
{
    public enum Kind
    {
        ACID, PLASMA, BULLET
    }

    private static final EntityDataAccessor<Integer> KIND = SynchedEntityData.defineId(CreatureProjectile.class, EntityDataSerializers.INT);
    private float damage = 4;

    public CreatureProjectile(EntityType<? extends CreatureProjectile> type, Level level)
    {
        super(type, level);
    }

    /** Fires from the shooter's eyes towards the target. */
    public static void shoot(LivingEntity shooter, LivingEntity target, Kind kind, float damage, float speed, float spread)
    {
        CreatureProjectile shot = new CreatureProjectile(ModEntities.CREATURE_PROJECTILE.get(), shooter.level());
        shot.setOwner(shooter);
        shot.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        shot.entityData.set(KIND, kind.ordinal());
        shot.damage = damage;
        Vec3 to = target.getEyePosition().subtract(0, target.getBbHeight() * 0.25, 0).subtract(shot.position());
        // acid arcs, so aim a little higher the further it goes
        double lift = kind == Kind.ACID ? to.horizontalDistance() * 0.2 : 0;
        shot.shoot(to.x, to.y + lift, to.z, speed, spread);
        shooter.level().addFreshEntity(shot);
    }

    /** Fires straight along a direction (a player's plasma caster). */
    public static void shootAlong(LivingEntity shooter, Vec3 direction, Kind kind, float damage, float speed)
    {
        CreatureProjectile shot = new CreatureProjectile(ModEntities.CREATURE_PROJECTILE.get(), shooter.level());
        shot.setOwner(shooter);
        shot.setPos(shooter.getX(), shooter.getEyeY() - 0.15, shooter.getZ());
        shot.entityData.set(KIND, kind.ordinal());
        shot.damage = damage;
        shot.shoot(direction.x, direction.y, direction.z, speed, 0.5F);
        shooter.level().addFreshEntity(shot);
    }

    public Kind kind()
    {
        return Kind.values()[Math.floorMod(entityData.get(KIND), Kind.values().length)];
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        builder.define(KIND, 0);
    }

    @Override
    protected double getDefaultGravity()
    {
        return kind() == Kind.ACID ? 0.03 : 0.0;
    }

    @Override
    public void tick()
    {
        super.tick();
        if (level().isClientSide)
        {
            ParticleOptions trail = switch (kind())
            {
                case ACID -> new DustParticleOptions(new Vector3f(0.55F, 0.85F, 0.15F), 1.2F);
                case PLASMA -> new DustParticleOptions(new Vector3f(0.3F, 0.6F, 1.0F), 1.5F);
                case BULLET -> ParticleTypes.CRIT;
            };
            for (int i = 0; i < 3; i++)
            {
                Vec3 v = getDeltaMovement().scale(-i / 3.0);
                level().addParticle(trail, getX() + v.x, getY() + v.y, getZ() + v.z, 0, 0, 0);
            }
        }
        else if (tickCount > 100)
        {
            discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hit)
    {
        super.onHitEntity(hit);
        Entity victim = hit.getEntity();
        Entity owner = getOwner();
        if (victim == owner || owner != null && (victim.isAlliedTo(owner) || owner.isAlliedTo(victim)))
        {
            return;
        }
        DamageSource source = switch (kind())
        {
            case ACID -> owner == null ? damageSources().source(ModDamageTypes.ACID)
                    : damageSources().source(ModDamageTypes.ACID, this, owner);
            case PLASMA -> damageSources().indirectMagic(this, owner);
            case BULLET -> damageSources().mobProjectile(this, owner instanceof LivingEntity l ? l : null);
        };
        victim.hurt(source, damage);
        if (kind() == Kind.ACID && victim instanceof LivingEntity living)
        {
            living.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0), owner);
        }
    }

    @Override
    protected void onHit(HitResult hit)
    {
        super.onHit(hit);
        if (level() instanceof ServerLevel server)
        {
            switch (kind())
            {
                case ACID -> server.sendParticles(ParticleTypes.ITEM_SLIME, getX(), getY(), getZ(), 8, 0.2, 0.2, 0.2, 0.05);
                case PLASMA ->
                {
                    // a plasma bolt bursts (sparing the shooter's own side), but it doesn't wreck the terrain
                    server.sendParticles(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 1, 0, 0, 0, 0);
                    server.playSound(null, blockPosition(), net.minecraft.sounds.SoundEvents.GENERIC_EXPLODE.value(),
                            net.minecraft.sounds.SoundSource.HOSTILE, 0.7F, 1.4F);
                    Entity owner = getOwner();
                    for (LivingEntity near : server.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(1.8),
                            e -> e != owner && (owner == null || !owner.isAlliedTo(e) && !e.isAlliedTo(owner))))
                    {
                        near.hurt(damageSources().explosion(this, owner), damage * 0.5F);
                    }
                }
                case BULLET -> server.sendParticles(ParticleTypes.SMOKE, getX(), getY(), getZ(), 2, 0.05, 0.05, 0.05, 0.01);
            }
            discard();
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.putInt("Kind", entityData.get(KIND));
        tag.putFloat("Damage", damage);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        entityData.set(KIND, tag.getInt("Kind"));
        damage = tag.getFloat("Damage");
    }
}
