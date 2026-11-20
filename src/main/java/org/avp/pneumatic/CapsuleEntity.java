package org.avp.pneumatic;

import java.util.ArrayList;
import java.util.List;

import org.avp.registry.ModEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * A PneuPost canister on its way through the tubes. It follows its route block to block; if a tube
 * on the way has gone (cut, blown up, melted by acid) it bursts out there and spills its load.
 */
public class CapsuleEntity extends Entity
{
    private List<BlockPos> route = new ArrayList<>();
    private int at;
    private double speed = 0.3;
    private final List<ItemStack> cargo = new ArrayList<>();

    public CapsuleEntity(EntityType<? extends CapsuleEntity> type, Level level)
    {
        super(type, level);
        noPhysics = true;
        setNoGravity(true);
    }

    public static CapsuleEntity launch(Level level, BlockPos from, List<BlockPos> route, List<ItemStack> cargo, double speed)
    {
        CapsuleEntity capsule = new CapsuleEntity(ModEntities.CAPSULE.get(), level);
        capsule.route = new ArrayList<>(route);
        capsule.cargo.addAll(cargo);
        capsule.speed = speed;
        Vec3 start = Vec3.atCenterOf(from);
        capsule.setPos(start.x, start.y - 0.15, start.z);
        level.addFreshEntity(capsule);
        level.playSound(null, from, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.6F, 1.8F);
        return capsule;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
    }

    @Override
    public void tick()
    {
        super.tick();
        if (level().isClientSide)
        {
            return;
        }
        if (at >= route.size())
        {
            discard();
            return;
        }
        BlockPos next = route.get(at);
        boolean last = at == route.size() - 1;
        if (!level().isLoaded(next))
        {
            return; // wait for the chunk
        }
        if (!last && !(level().getBlockState(next).getBlock() instanceof TubeBlock) || last && !(level().getBlockEntity(next) instanceof PneumaticStationBlockEntity)
                && !(level().getBlockEntity(next) instanceof org.avp.apollo.ApolloCoreBlockEntity))
        {
            burst();
            return;
        }
        Vec3 target = Vec3.atCenterOf(next).subtract(0, 0.15, 0);
        Vec3 here = position();
        Vec3 step = target.subtract(here);
        double dist = step.length();
        double move = speed;
        while (move > 0 && at < route.size())
        {
            if (dist <= move)
            {
                setPos(target.x, target.y, target.z);
                move -= dist;
                at++;
                if (last)
                {
                    deliver(next);
                    return;
                }
                if (at < route.size())
                {
                    next = route.get(at);
                    last = at == route.size() - 1;
                    target = Vec3.atCenterOf(next).subtract(0, 0.15, 0);
                    dist = target.subtract(position()).length();
                }
            }
            else
            {
                Vec3 p = position().add(step.normalize().scale(move));
                setPos(p.x, p.y, p.z);
                move = 0;
            }
            step = target.subtract(position());
        }
    }

    private void deliver(BlockPos station)
    {
        if (level().getBlockEntity(station) instanceof org.avp.apollo.ApolloCoreBlockEntity core)
        {
            for (ItemStack stack : cargo)
            {
                ItemStack left = core.store(stack, false);
                if (!left.isEmpty())
                {
                    Containers.dropItemStack(level(), station.getX() + 0.5, station.getY() + 1.1, station.getZ() + 0.5, left);
                }
            }
            level().playSound(null, station, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 0.6F, 1.6F);
        }
        else if (level().getBlockEntity(station) instanceof PneumaticStationBlockEntity st)
        {
            for (ItemStack stack : cargo)
            {
                ItemStack left = st.receive(stack);
                if (!left.isEmpty())
                {
                    Containers.dropItemStack(level(), station.getX() + 0.5, station.getY() + 1.1, station.getZ() + 0.5, left);
                }
            }
            level().playSound(null, station, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 0.6F, 1.6F);
        }
        cargo.clear();
        discard();
    }

    /** The tube is gone: the canister bursts out here. */
    private void burst()
    {
        for (ItemStack stack : cargo)
        {
            Containers.dropItemStack(level(), getX(), getY(), getZ(), stack);
        }
        cargo.clear();
        if (level() instanceof ServerLevel server)
        {
            server.sendParticles(ParticleTypes.CLOUD, getX(), getY(), getZ(), 12, 0.2, 0.2, 0.2, 0.1);
            server.playSound(null, blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 0.8F, 1.4F);
        }
        discard();
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag)
    {
        route.clear();
        for (long l : tag.getLongArray("Route"))
        {
            route.add(BlockPos.of(l));
        }
        at = tag.getInt("At");
        speed = tag.getDouble("Speed");
        cargo.clear();
        for (Tag t : tag.getList("Cargo", Tag.TAG_COMPOUND))
        {
            ItemStack.parse(registryAccess(), t).ifPresent(cargo::add);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag)
    {
        tag.putLongArray("Route", route.stream().mapToLong(BlockPos::asLong).toArray());
        tag.putInt("At", at);
        tag.putDouble("Speed", speed);
        ListTag list = new ListTag();
        cargo.forEach(s -> list.add(s.save(registryAccess())));
        tag.put("Cargo", list);
    }

    @Override
    public boolean isPickable()
    {
        return false;
    }
}
