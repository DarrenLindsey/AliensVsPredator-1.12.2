package org.avp.entity.projectile;

import java.util.UUID;

import javax.annotation.Nullable;

import org.avp.registry.ModEntities;
import org.avp.registry.ModGear;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * A Yautja laser (proximity) mine stuck to a wall (1.12.2 EntityLaserMine): it shines a red beam
 * straight out, up to 12 blocks; anything that breaks the beam - except its owner - sets it off.
 * Hit it to take it down again.
 */
public class LaserMineEntity extends Entity
{
    private static final EntityDataAccessor<Integer> FACING = SynchedEntityData.defineId(LaserMineEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> BEAM = SynchedEntityData.defineId(LaserMineEntity.class, EntityDataSerializers.FLOAT);
    public static final double RANGE = 12;
    @Nullable
    private UUID owner;

    public LaserMineEntity(EntityType<? extends LaserMineEntity> type, Level level)
    {
        super(type, level);
    }

    public static LaserMineEntity place(Level level, BlockPos against, Direction face, @Nullable Player owner)
    {
        LaserMineEntity mine = new LaserMineEntity(ModEntities.LASER_MINE.get(), level);
        Vec3 at = Vec3.atCenterOf(against).add(Vec3.atLowerCornerOf(face.getNormal()).scale(0.5 + 0.13));
        mine.setPos(at.x, at.y - 0.15, at.z);
        mine.entityData.set(FACING, face.get3DDataValue());
        mine.owner = owner == null ? null : owner.getUUID();
        return mine;
    }

    public Direction facing()
    {
        return Direction.from3DDataValue(entityData.get(FACING));
    }

    /** How far the beam reaches (to the first block), for drawing it. */
    public float beamLength()
    {
        return entityData.get(BEAM);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        builder.define(FACING, Direction.NORTH.get3DDataValue());
        builder.define(BEAM, (float) RANGE);
    }

    @Override
    public void tick()
    {
        super.tick();
        if (!(level() instanceof ServerLevel level) || tickCount % 4 != 0)
        {
            return;
        }
        Vec3 from = position().add(0, 0.15, 0);
        Vec3 dir = Vec3.atLowerCornerOf(facing().getNormal());
        Vec3 to = from.add(dir.scale(RANGE));
        BlockHitResult block = level.clip(new ClipContext(from.add(dir.scale(0.3)), to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (block.getType() != HitResult.Type.MISS)
        {
            to = block.getLocation();
        }
        entityData.set(BEAM, (float) from.distanceTo(to));
        // still on its wall?
        if (level.getBlockState(BlockPos.containing(position().subtract(dir.scale(0.4)))).isAir())
        {
            breakOff();
            return;
        }
        EntityHitResult hit = ProjectileUtil.getEntityHitResult(level, this, from, to, new AABB(from, to).inflate(0.5),
                e -> e instanceof LivingEntity && e.isAlive() && !e.getUUID().equals(owner) && !(e instanceof Player p && (p.isCreative() || p.isSpectator())),
                0.0F);
        if (hit != null)
        {
            discard();
            level.explode(this, getX(), getY(), getZ(), 3.0F, Level.ExplosionInteraction.NONE);
        }
    }

    @Override
    public boolean isPickable()
    {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        if (!level().isClientSide && !isRemoved())
        {
            breakOff();
        }
        return true;
    }

    private void breakOff()
    {
        spawnAtLocation(ModGear.LASER_MINE.get());
        discard();
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance)
    {
        return distance < 64 * 64;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag)
    {
        entityData.set(FACING, tag.getInt("Facing"));
        owner = tag.hasUUID("Owner") ? tag.getUUID("Owner") : null;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag)
    {
        tag.putInt("Facing", entityData.get(FACING));
        if (owner != null)
        {
            tag.putUUID("Owner", owner);
        }
    }
}
