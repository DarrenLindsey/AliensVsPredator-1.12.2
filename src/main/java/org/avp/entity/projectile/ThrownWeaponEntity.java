package org.avp.entity.projectile;

import org.avp.registry.ModEntities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * A thrown Yautja weapon: shuriken, combi-stick spear or smart disc. Shuriken and spear stick
 * where they land (pick them up again); the smart disc flies back to its thrower and is caught.
 */
public class ThrownWeaponEntity extends ThrowableItemProjectile
{
    private static final EntityDataAccessor<Boolean> RETURNING = SynchedEntityData.defineId(ThrownWeaponEntity.class, EntityDataSerializers.BOOLEAN);
    private float damage = 6;
    private boolean returns;
    private int flight;

    public ThrownWeaponEntity(EntityType<? extends ThrownWeaponEntity> type, Level level)
    {
        super(type, level);
    }

    public static void throwFrom(LivingEntity thrower, ItemStack stack, float damage, float speed, boolean returns)
    {
        ThrownWeaponEntity weapon = new ThrownWeaponEntity(ModEntities.THROWN_WEAPON.get(), thrower.level());
        weapon.setOwner(thrower);
        weapon.setPos(thrower.getX(), thrower.getEyeY() - 0.1, thrower.getZ());
        weapon.setItem(stack.copyWithCount(1));
        weapon.damage = damage;
        weapon.returns = returns;
        weapon.shootFromRotation(thrower, thrower.getXRot(), thrower.getYRot(), 0F, speed, 0.5F);
        thrower.level().addFreshEntity(weapon);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(RETURNING, false);
    }

    @Override
    protected Item getDefaultItem()
    {
        return Items.IRON_NUGGET;
    }

    @Override
    protected double getDefaultGravity()
    {
        return returns ? 0.0 : 0.03;
    }

    @Override
    public void tick()
    {
        flight++;
        if (returns && !level().isClientSide && (flight > 25 || entityData.get(RETURNING)))
        {
            entityData.set(RETURNING, true);
            Entity owner = getOwner();
            if (owner == null || !owner.isAlive() || owner.level() != level())
            {
                drop();
                return;
            }
            // homing back to the thrower's hand
            Vec3 to = owner.getEyePosition().subtract(0, 0.4, 0).subtract(position());
            setDeltaMovement(to.normalize().scale(Math.min(1.6, 0.5 + flight * 0.02)));
            noPhysics = true;
            if (to.lengthSqr() < 2.0)
            {
                catchIt(owner);
                return;
            }
        }
        super.tick();
        if (flight > 400 && !level().isClientSide)
        {
            drop();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult hit)
    {
        Entity target = hit.getEntity();
        if (target == getOwner() || level().isClientSide)
        {
            return;
        }
        target.hurt(damageSources().thrown(this, getOwner()), damage);
        level().playSound(null, blockPosition(), SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 1.0F, 1.2F);
        if (returns)
        {
            entityData.set(RETURNING, true); // the disc slices through and comes back
        }
        else
        {
            setDeltaMovement(getDeltaMovement().scale(-0.1));
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult hit)
    {
        super.onHitBlock(hit);
        if (level().isClientSide)
        {
            return;
        }
        if (returns)
        {
            entityData.set(RETURNING, true);
        }
        else
        {
            level().playSound(null, blockPosition(), SoundEvents.TRIDENT_HIT_GROUND, SoundSource.PLAYERS, 1.0F, 1.2F);
            drop();
        }
    }

    private void catchIt(Entity owner)
    {
        if (owner instanceof Player player && !player.getAbilities().instabuild && !player.getInventory().add(getItem()))
        {
            drop();
            return;
        }
        discard();
    }

    private void drop()
    {
        if (level() instanceof ServerLevel level && !(getOwner() instanceof Player p && p.getAbilities().instabuild))
        {
            ItemEntity item = new ItemEntity(level, getX(), getY(), getZ(), getItem());
            item.setDefaultPickUpDelay();
            level.addFreshEntity(item);
        }
        discard();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Damage", damage);
        tag.putBoolean("Returns", returns);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        damage = tag.getFloat("Damage");
        returns = tag.getBoolean("Returns");
    }
}
