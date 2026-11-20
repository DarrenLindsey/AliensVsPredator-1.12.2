package org.avp.block.conveyor.spline;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import org.avp.block.conveyor.ConveyorBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * An anchor that a spline leaves from (belt, cable or hose). It keeps its one outgoing link and the
 * anchors whose links end at it.
 */
public abstract class SplineAnchorBlockEntity extends BlockEntity
{
    @Nullable
    protected BlockPos target;
    protected final Set<BlockPos> incoming = new HashSet<>();
    @Nullable
    private Spline spline;

    protected SplineAnchorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    public SplineAnchorBlock.Kind kind()
    {
        return getBlockState().getBlock() instanceof SplineAnchorBlock b ? b.kind : SplineAnchorBlock.Kind.BELT;
    }

    @Nullable
    public BlockPos target()
    {
        return target;
    }

    public Set<BlockPos> incoming()
    {
        return incoming;
    }

    public void link(BlockPos to)
    {
        unlink();
        target = to.immutable();
        spline = null;
        if (level != null && level.getBlockEntity(to) instanceof SplineAnchorBlockEntity other)
        {
            other.incoming.add(worldPosition.immutable());
            other.sync();
        }
        onLinked();
        sync();
    }

    public void unlink()
    {
        if (target != null && level != null && level.getBlockEntity(target) instanceof SplineAnchorBlockEntity other)
        {
            other.incoming.remove(worldPosition);
            other.sync();
        }
        onUnlinked();
        target = null;
        spline = null;
        sync();
    }

    protected void onLinked()
    {
    }

    protected void onUnlinked()
    {
    }

    /** Before the block goes: undo every link to and from it. */
    public void detachAll()
    {
        unlink();
        if (level != null)
        {
            for (BlockPos from : Set.copyOf(incoming))
            {
                if (level.getBlockEntity(from) instanceof SplineAnchorBlockEntity other && worldPosition.equals(other.target))
                {
                    other.unlink();
                }
            }
        }
    }

    // ------------------------------------------------------------------ geometry

    /** Where a spline leaves or reaches a block, in world coordinates, with its direction. */
    public static Vec3[] endpoint(net.minecraft.world.level.Level level, BlockPos pos, Vec3 towards)
    {
        BlockState s = level.getBlockState(pos);
        if (s.getBlock() instanceof SplineAnchorBlock anchor)
        {
            Direction facing = s.getValue(SplineAnchorBlock.FACING);
            Vec3 dir = anchor.kind == SplineAnchorBlock.Kind.BELT ? Vec3.atLowerCornerOf(facing.getNormal())
                    : towards.subtract(Vec3.atCenterOf(pos)).multiply(1, 0, 1).normalize();
            double y = switch (anchor.kind)
            {
                case BELT -> 0.8;
                case CABLE -> 1.85;
                case HOSE -> 0.55;
            };
            return new Vec3[] { new Vec3(pos.getX() + 0.5, pos.getY() + y, pos.getZ() + 0.5), dir };
        }
        if (s.getBlock() instanceof ConveyorBlock)
        {
            Direction facing = s.getValue(ConveyorBlock.FACING);
            return new Vec3[] { new Vec3(pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5), Vec3.atLowerCornerOf(facing.getNormal()) };
        }
        return new Vec3[] { Vec3.atCenterOf(pos), towards.subtract(Vec3.atCenterOf(pos)).normalize() };
    }

    /** The spline from here to the target (null if unlinked). */
    @Nullable
    public Spline spline()
    {
        if (target == null || level == null)
        {
            return null;
        }
        if (spline == null)
        {
            Vec3[] a = endpoint(level, worldPosition, Vec3.atCenterOf(target));
            Vec3[] b = endpoint(level, target, Vec3.atCenterOf(worldPosition));
            if (kind() == SplineAnchorBlock.Kind.BELT)
            {
                spline = new Spline(a[0], a[1], b[0], b[1]);
            }
            else
            {
                // cables and hoses hang: control points a third of the way along, sagging below the line
                double span = a[0].distanceTo(b[0]);
                double sag = span * (kind() == SplineAnchorBlock.Kind.CABLE ? 0.12 : 0.2);
                Vec3 third = b[0].subtract(a[0]).scale(1 / 3.0);
                spline = new Spline(a[0], third.add(0, -sag, 0), b[0], third.add(0, sag, 0));
            }
        }
        return spline;
    }

    public AABB renderBox()
    {
        AABB box = new AABB(worldPosition);
        if (target != null)
        {
            box = box.minmax(new AABB(target));
        }
        return box.inflate(2);
    }

    // ------------------------------------------------------------------ saving and syncing

    protected void sync()
    {
        setChanged();
        if (level != null)
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        if (target != null)
        {
            tag.putLong("Target", target.asLong());
        }
        tag.putLongArray("Incoming", incoming.stream().mapToLong(BlockPos::asLong).toArray());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        target = tag.contains("Target") ? BlockPos.of(tag.getLong("Target")) : null;
        incoming.clear();
        for (long l : tag.getLongArray("Incoming"))
        {
            incoming.add(BlockPos.of(l));
        }
        spline = null;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
