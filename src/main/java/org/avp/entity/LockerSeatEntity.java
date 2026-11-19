package org.avp.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import org.avp.block.StorageBlock;
import org.avp.registry.ModEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * An invisible "seat" inside a locker. Hiding in a locker means riding this
 * entity (the same trick minecarts and Create's seats use), which keeps you
 * snapped in place and gives "sneak to get out" for free.
 *
 * Holding sneak while riding makes Minecraft dismount you straight away, so the
 * seat waits until the player lets go of sneak before seating them.
 */
public class LockerSeatEntity extends Entity
{
    /** Give up if the player doesn't let go of sneak within this many ticks (3 seconds). */
    private static final int PENDING_TIMEOUT = 60;

    @Nullable
    private UUID pendingPlayer;
    private int age;

    public LockerSeatEntity(EntityType<? extends LockerSeatEntity> type, Level level)
    {
        super(type, level);
        this.noPhysics = true;
        setNoGravity(true);
    }

    /** Creates a seat in the locker at lockerPos for the given player. */
    public static void hide(Level level, BlockPos lockerPos, Player player)
    {
        LockerSeatEntity seat = new LockerSeatEntity(ModEntities.LOCKER_SEAT.get(), level);
        seat.moveTo(lockerPos.getX() + 0.5, lockerPos.getY(), lockerPos.getZ() + 0.5, facingYaw(level.getBlockState(lockerPos)), 0);
        seat.pendingPlayer = player.getUUID();
        level.addFreshEntity(seat);
    }

    /** True if someone is already hiding in (or getting into) the locker at pos. */
    public static boolean isOccupied(Level level, BlockPos lockerPos)
    {
        return !level.getEntitiesOfClass(LockerSeatEntity.class, new net.minecraft.world.phys.AABB(lockerPos)).isEmpty();
    }

    private static float facingYaw(BlockState state)
    {
        return state.hasProperty(StorageBlock.FACING) ? state.getValue(StorageBlock.FACING).toYRot() : 0;
    }

    private BlockPos lockerPos()
    {
        return blockPosition();
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
        BlockState state = level().getBlockState(lockerPos());
        if (!(state.getBlock() instanceof StorageBlock))
        {
            discard(); // locker was broken
            return;
        }
        if (pendingPlayer != null)
        {
            Player player = level().getPlayerByUUID(pendingPlayer);
            if (player == null || age > PENDING_TIMEOUT || player.distanceToSqr(this) > 9)
            {
                discard();
                return;
            }
            if (!player.isShiftKeyDown())
            {
                pendingPlayer = null;
                player.startRiding(this, true);
                player.setYRot(getYRot());
                player.setYHeadRot(getYRot());
                StorageBlock.setDoorOpen(level(), lockerPos(), false); // close the door behind them
            }
            return;
        }
        if (getPassengers().isEmpty())
        {
            discard();
        }
    }

    /** Getting out: open the door for a moment. */
    @Override
    protected void removePassenger(Entity passenger)
    {
        super.removePassenger(passenger);
        if (!level().isClientSide && level().getBlockState(lockerPos()).getBlock() instanceof StorageBlock)
        {
            StorageBlock.openDoorBriefly(level(), lockerPos());
        }
    }

    /** Step out in front of the locker door. */
    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger)
    {
        BlockState state = level().getBlockState(lockerPos());
        if (state.hasProperty(StorageBlock.FACING))
        {
            Direction front = state.getValue(StorageBlock.FACING);
            BlockPos out = lockerPos().relative(front);
            if (level().getBlockState(out).getCollisionShape(level(), out).isEmpty()
                    && level().getBlockState(out.above()).getCollisionShape(level(), out.above()).isEmpty())
            {
                return Vec3.atBottomCenterOf(out);
            }
        }
        return super.getDismountLocationForPassenger(passenger);
    }

    /** The player stands upright with their feet on the locker floor. */
    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scale)
    {
        return Vec3.ZERO;
    }

    @Override
    public boolean shouldRiderSit()
    {
        return false;
    }

    /** Never saved: if the game closes while you're hiding, you simply aren't hiding when you come back. */
    @Override
    public boolean shouldBeSaved()
    {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag)
    {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag)
    {
    }
}
