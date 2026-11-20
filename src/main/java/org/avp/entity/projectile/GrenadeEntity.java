package org.avp.entity.projectile;

import org.avp.config.AvpConfig;
import org.avp.registry.ModEntities;
import org.avp.registry.ModGear;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * An M40 grenade (thrown, or fired from the M41A's launcher). It bounces, and goes off after its
 * fuse (2 s) or when it strikes someone. The incendiary one sets the area alight.
 */
public class GrenadeEntity extends ThrowableItemProjectile
{
    private static final EntityDataAccessor<Boolean> INCENDIARY = SynchedEntityData.defineId(GrenadeEntity.class, EntityDataSerializers.BOOLEAN);
    private int fuse = 40;

    public GrenadeEntity(EntityType<? extends GrenadeEntity> type, Level level)
    {
        super(type, level);
    }

    public static GrenadeEntity throwFrom(LivingEntity thrower, boolean incendiary, float speed, int fuse)
    {
        GrenadeEntity grenade = new GrenadeEntity(ModEntities.GRENADE.get(), thrower.level());
        grenade.setOwner(thrower);
        grenade.setPos(thrower.getX(), thrower.getEyeY() - 0.1, thrower.getZ());
        grenade.entityData.set(INCENDIARY, incendiary);
        grenade.setItem(new net.minecraft.world.item.ItemStack(incendiary ? ModGear.GRENADE_INCENDIARY.get() : ModGear.GRENADE_M40.get()));
        grenade.fuse = fuse;
        grenade.shootFromRotation(thrower, thrower.getXRot(), thrower.getYRot(), 0F, speed, 1.0F);
        thrower.level().addFreshEntity(grenade);
        return grenade;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(INCENDIARY, false);
    }

    @Override
    protected Item getDefaultItem()
    {
        return ModGear.GRENADE_M40.get();
    }

    @Override
    public void tick()
    {
        super.tick();
        if (level().isClientSide)
        {
            level().addParticle(ParticleTypes.SMOKE, getX(), getY() + 0.1, getZ(), 0, 0.02, 0);
        }
        else if (--fuse <= 0)
        {
            detonate();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hit)
    {
        super.onHitEntity(hit);
        if (!level().isClientSide && hit.getEntity() != getOwner())
        {
            detonate();
        }
    }

    /** It bounces off walls and floors (losing most of its speed). */
    @Override
    protected void onHitBlock(BlockHitResult hit)
    {
        Vec3 v = getDeltaMovement();
        switch (hit.getDirection().getAxis())
        {
            case X -> setDeltaMovement(-v.x * 0.4, v.y * 0.6, v.z * 0.6);
            case Y -> setDeltaMovement(v.x * 0.5, -v.y * 0.35, v.z * 0.5);
            case Z -> setDeltaMovement(v.x * 0.6, v.y * 0.6, -v.z * 0.4);
        }
        if (getDeltaMovement().lengthSqr() < 0.01)
        {
            setDeltaMovement(Vec3.ZERO);
        }
    }

    private void detonate()
    {
        if (!(level() instanceof ServerLevel level))
        {
            return;
        }
        boolean incendiary = entityData.get(INCENDIARY);
        Level.ExplosionInteraction blocks = AvpConfig.server(AvpConfig.SERVER.grenadesBreakBlocks) ? Level.ExplosionInteraction.TNT
                : Level.ExplosionInteraction.NONE;
        level.explode(this, getX(), getY(), getZ(), incendiary ? 1.5F : 3.0F, incendiary, blocks);
        if (incendiary)
        {
            for (BlockPos p : BlockPos.betweenClosed(blockPosition().offset(-3, -1, -3), blockPosition().offset(3, 2, 3)))
            {
                if (random.nextInt(3) == 0 && p.distSqr(blockPosition()) <= 10 && level.getBlockState(p).isAir()
                        && BaseFireBlock.canBePlacedAt(level, p, net.minecraft.core.Direction.UP))
                {
                    level.setBlock(p, BaseFireBlock.getState(level, p), 3);
                }
            }
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(4)))
            {
                e.igniteForSeconds(8);
            }
        }
        discard();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Incendiary", entityData.get(INCENDIARY));
        tag.putInt("Fuse", fuse);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        entityData.set(INCENDIARY, tag.getBoolean("Incendiary"));
        fuse = tag.getInt("Fuse");
    }
}
