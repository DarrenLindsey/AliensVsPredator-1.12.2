package org.avp.entity.joe;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import javax.annotation.Nullable;

import org.avp.config.AvpConfig;
import org.avp.power.MachineEnergy;
import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.energy.IEnergyStorage;

/** A Joe charging pad: takes FE from the power network and tops up any Working Joe standing on or next to it. */
public class JoeChargerBlockEntity extends BlockEntity
{
    private static final Map<Level, Set<BlockPos>> CHARGERS = new WeakHashMap<>();
    private final MachineEnergy energy = new MachineEnergy(200_000);

    public JoeChargerBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.JOE_CHARGER.get(), pos, state);
    }

    public IEnergyStorage energyFor()
    {
        return energy.view(true, false, Integer.MAX_VALUE);
    }

    public static boolean isCharger(Level level, BlockPos pos)
    {
        return level.getBlockEntity(pos) instanceof JoeChargerBlockEntity;
    }

    /** The nearest charger with power in it (or any charger, if none has power). */
    @Nullable
    public static BlockPos nearest(Level level, BlockPos from, int range)
    {
        Set<BlockPos> known;
        synchronized (CHARGERS)
        {
            known = new HashSet<>(CHARGERS.getOrDefault(level, Set.of()));
        }
        BlockPos best = null, bestEmpty = null;
        double bestDist = (double) range * range, bestEmptyDist = bestDist;
        for (BlockPos pos : known)
        {
            if (!(level.getBlockEntity(pos) instanceof JoeChargerBlockEntity charger))
            {
                continue;
            }
            double d = pos.distSqr(from);
            if (charger.energy.getEnergyStored() > 0 && d < bestDist)
            {
                best = pos;
                bestDist = d;
            }
            else if (d < bestEmptyDist)
            {
                bestEmpty = pos;
                bestEmptyDist = d;
            }
        }
        return best != null ? best : bestEmpty;
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        if (level != null && !level.isClientSide)
        {
            synchronized (CHARGERS)
            {
                CHARGERS.computeIfAbsent(level, l -> new HashSet<>()).add(worldPosition.immutable());
            }
        }
    }

    @Override
    public void setRemoved()
    {
        super.setRemoved();
        if (level != null)
        {
            synchronized (CHARGERS)
            {
                CHARGERS.getOrDefault(level, new HashSet<>()).remove(worldPosition);
            }
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, JoeChargerBlockEntity charger)
    {
        if (level.getGameTime() % 5 != 0 || charger.energy.getEnergyStored() <= 0)
        {
            return;
        }
        int rate = AvpConfig.server(AvpConfig.SERVER.joeChargeRate) * 5;
        for (WorkingJoeEntity joe : level.getEntitiesOfClass(WorkingJoeEntity.class, new AABB(pos).inflate(1.5, 2, 1.5)))
        {
            if (!joe.needsCharge())
            {
                continue;
            }
            int give = joe.addCharge(Math.min(rate, charger.energy.getEnergyStored()));
            charger.energy.drain(give);
            if (give > 0 && level instanceof ServerLevel server && level.getGameTime() % 20 == 0)
            {
                server.sendParticles(ParticleTypes.ELECTRIC_SPARK, joe.getX(), joe.getY() + 0.3, joe.getZ(), 3, 0.3, 0.2, 0.3, 0.05);
            }
        }
        for (org.avp.mining.PowerLoaderEntity loader : level.getEntitiesOfClass(org.avp.mining.PowerLoaderEntity.class, new AABB(pos).inflate(1.5, 2, 1.5)))
        {
            if (loader.needsCharge())
            {
                charger.energy.drain(loader.addCharge(Math.min(rate, charger.energy.getEnergyStored())));
            }
        }
        charger.setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.putInt("Energy", energy.getEnergyStored());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        energy.setStored(tag.getInt("Energy"));
    }

    public int stored()
    {
        return energy.getEnergyStored();
    }
}
