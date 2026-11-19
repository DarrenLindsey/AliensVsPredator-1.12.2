package org.avp.entity.xenomorph;

import javax.annotation.Nullable;

import org.avp.config.AvpConfig;
import org.avp.entity.parasite.FacehuggerEntity;
import org.avp.registry.ModEntities;
import org.avp.registry.ModSounds;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Ovamorph - the Xenomorph egg (1.12.2 EntityOvamorph).
 *
 * It sits still. When a possible host comes within 8 blocks it starts counting down; then its
 * four lobes peel open and a Facehugger springs out at whoever is closest. Touching it or
 * hurting it makes it hatch at once. Empty eggs wither away after a few minutes.
 */
public class OvamorphEntity extends XenomorphEntity
{
    public static final int MAX_OPEN = 21;
    private static final EntityDataAccessor<Integer> OPEN = SynchedEntityData.defineId(OvamorphEntity.class, EntityDataSerializers.INT);

    private boolean full = true;
    private boolean triggered;
    private int hatchTimer;
    private int emptyTicks;

    public OvamorphEntity(EntityType<? extends OvamorphEntity> type, Level level)
    {
        super(type, level);
        this.xpReward = 10;
        this.hatchTimer = 600 + random.nextInt(240); // 1.12.2: 30 s + up to 12 s
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 8.0)
                .add(Attributes.MOVEMENT_SPEED, 0.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(OPEN, -MAX_OPEN);
    }

    @Override
    protected void registerGoals()
    {
        // an egg doesn't do anything on purpose
    }

    /** -21 (shut) .. 21 (wide open), drives the lobe animation. */
    public int openProgress()
    {
        return entityData.get(OPEN);
    }

    public boolean isFull()
    {
        return full;
    }

    @Override
    public void tick()
    {
        super.tick();
        setDeltaMovement(0, Math.min(0, getDeltaMovement().y), 0); // never slides around
        if (level().isClientSide)
        {
            return;
        }
        if (!full)
        {
            entityData.set(OPEN, MAX_OPEN);
            if (++emptyTicks >= AvpConfig.server(AvpConfig.SERVER.emptyOvamorphLifetime))
            {
                discard();
            }
            return;
        }
        boolean hostNearby = !level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(8), FacehuggerEntity::isValidHost).isEmpty();
        if (triggered || hostNearby)
        {
            hatchTimer -= triggered ? 20 : 1;
            if (hatchTimer <= 60)
            {
                if (openProgress() == -MAX_OPEN)
                {
                    playSound(ModSounds.OVAMORPH_OPEN.get(), 1.0F, 1.0F);
                }
                entityData.set(OPEN, Math.min(MAX_OPEN, openProgress() + 1));
            }
            if (hatchTimer <= 0 && openProgress() >= MAX_OPEN)
            {
                hatch();
            }
        }
    }

    private void hatch()
    {
        full = false;
        FacehuggerEntity hugger = ModEntities.FACEHUGGER.get().create(level());
        if (hugger == null)
        {
            return;
        }
        hugger.moveTo(getX(), getY() + 0.9, getZ(), random.nextFloat() * 360F, 0);
        // spring out towards the nearest host
        Player near = level().getNearestPlayer(this, 8);
        Vec3 push = near != null ? near.position().subtract(position()).normalize().scale(0.45) : Vec3.ZERO;
        hugger.setDeltaMovement(push.x, 0.75, push.z);
        hugger.finalizeSpawn((net.minecraft.server.level.ServerLevel) level(), level().getCurrentDifficultyAt(blockPosition()), MobSpawnType.BREEDING, null);
        level().addFreshEntity(hugger);
    }

    /** Bumping into it: it opens straight away (1.12.2 collideWithEntity). */
    @Override
    protected void doPush(Entity entity)
    {
        if (entity instanceof LivingEntity living && FacehuggerEntity.isValidHost(living))
        {
            triggered = true;
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        boolean hurt = super.hurt(source, amount);
        if (hurt)
        {
            triggered = true;
        }
        return hurt;
    }

    @Override
    public boolean isPushable()
    {
        return false;
    }

    @Override
    public boolean canDrownInFluidType(net.neoforged.neoforge.fluids.FluidType type)
    {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distance)
    {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return full ? ModSounds.OVAMORPH_LIVING.get() : null;
    }

    @Override
    public int getAmbientSoundInterval()
    {
        return 240;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("ContainsFacehugger", full);
        tag.putInt("OpenProgress", openProgress());
        tag.putInt("HatchTimer", hatchTimer);
        tag.putInt("EmptyTicks", emptyTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        full = !tag.contains("ContainsFacehugger") || tag.getBoolean("ContainsFacehugger");
        entityData.set(OPEN, tag.contains("OpenProgress") ? tag.getInt("OpenProgress") : -MAX_OPEN);
        if (tag.contains("HatchTimer"))
        {
            hatchTimer = tag.getInt("HatchTimer");
        }
        emptyTicks = tag.getInt("EmptyTicks");
    }
}
