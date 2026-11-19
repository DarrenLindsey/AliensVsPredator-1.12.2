package org.avp.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import org.avp.block.SeatBlock;
import org.avp.registry.ModEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * An invisible seat inside a block (locker, medpod). Being "inside" means riding
 * this entity - the same trick minecarts and Create's seats use - which keeps you
 * snapped in place and gives "sneak to get out" for free. The block (a SeatBlock)
 * decides what happens when someone gets in or out.
 *
 * Holding sneak while riding makes Minecraft dismount you straight away, so for
 * the locker (entered with sneak + right-click) the seat waits until the player
 * lets go of sneak before seating them.
 */
public class SeatEntity extends Entity
{
    /** Give up if the player doesn't let go of sneak within this many ticks (3 seconds). */
    private static final int PENDING_TIMEOUT = 60;

    @Nullable
    private UUID pendingPlayer;
    private int age;
    /** Ticks since someone was seated. */
    private int seatedTicks;

    public SeatEntity(EntityType<? extends SeatEntity> type, Level level)
    {
        super(type, level);
        this.noPhysics = true;
        setNoGravity(true);
    }

    private static SeatEntity create(Level level, BlockPos pos)
    {
        SeatEntity seat = new SeatEntity(ModEntities.SEAT.get(), level);
        BlockState state = level.getBlockState(pos);
        float yaw = state.hasProperty(BlockStateProperties.HORIZONTAL_FACING) ? state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() : 0;
        seat.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, yaw, 0);
        return seat;
    }

    /** Seats the player once they let go of sneak (used by the locker: sneak + right-click to get in). */
    public static void seatAfterSneak(Level level, BlockPos pos, Player player)
    {
        SeatEntity seat = create(level, pos);
        seat.pendingPlayer = player.getUUID();
        level.addFreshEntity(seat);
    }

    /** Seats an entity right away (used by the medpod: walk in). */
    public static void seatNow(Level level, BlockPos pos, LivingEntity entity)
    {
        SeatEntity seat = create(level, pos);
        level.addFreshEntity(seat);
        seat.seat(entity);
    }

    /** True if someone is already in (or getting into) the block at pos. */
    public static boolean isOccupied(Level level, BlockPos pos)
    {
        return find(level, pos) != null;
    }

    /** The seat in the block at pos, if any. */
    @Nullable
    public static SeatEntity find(Level level, BlockPos pos)
    {
        var seats = level.getEntitiesOfClass(SeatEntity.class, new AABB(pos));
        return seats.isEmpty() ? null : seats.get(0);
    }

    private void seat(Entity entity)
    {
        seatedTicks = 0;
        entity.startRiding(this, true);
        entity.setYRot(getYRot());
        entity.setYHeadRot(getYRot());
        if (level().getBlockState(blockPosition()).getBlock() instanceof SeatBlock block)
        {
            block.onSeated(level(), blockPosition(), entity);
        }
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
        if (!(level().getBlockState(blockPosition()).getBlock() instanceof SeatBlock))
        {
            discard(); // the block was broken
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
                seat(player);
            }
            return;
        }
        if (getPassengers().isEmpty())
        {
            discard();
        }
        else
        {
            seatedTicks++;
        }
    }

    @Override
    protected void removePassenger(Entity passenger)
    {
        super.removePassenger(passenger);
        if (!level().isClientSide && level().getBlockState(blockPosition()).getBlock() instanceof SeatBlock block)
        {
            block.onSeatLeft(level(), blockPosition(), passenger, seatedTicks);
        }
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger)
    {
        if (level().getBlockState(blockPosition()).getBlock() instanceof SeatBlock block)
        {
            BlockPos out = block.exitPos(level(), blockPosition());
            if (out != null && level().getBlockState(out).getCollisionShape(level(), out).isEmpty()
                    && level().getBlockState(out.above()).getCollisionShape(level(), out.above()).isEmpty())
            {
                return Vec3.atBottomCenterOf(out);
            }
        }
        return super.getDismountLocationForPassenger(passenger);
    }

    /**
     * The passenger stands upright with their feet on the block's floor. Minecraft lowers every
     * rider by their "vehicle attachment" (0.6 blocks for players - the seated height in a
     * minecart), so we lift them by the same amount to cancel it out.
     */
    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scale)
    {
        return new Vec3(0, passenger.getVehicleAttachmentPoint(this).y, 0);
    }

    @Override
    public boolean shouldRiderSit()
    {
        return false;
    }

    /** Never saved: if the game closes while you're inside, you simply aren't when you come back. */
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
