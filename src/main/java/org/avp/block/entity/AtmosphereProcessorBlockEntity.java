package org.avp.block.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.avp.config.AvpConfig;
import org.avp.power.MachineEnergy;
import org.avp.registry.ModBlockEntities;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

/**
 * A Weyland-Yutani atmosphere processor, the terraforming plant at Hadley's Hope. While powered it
 * makes the air breathable for a wide area around it (on Acheron, LV-426, the air isn't otherwise).
 * It draws a lot of power - it's one of the few devices that can sit on a high-voltage line - and
 * it needs cooling: water touching it. Without coolant its core heats up, and at 100% it goes up
 * like the one at Hadley's Hope did. A redstone signal shuts it down.
 */
public class AtmosphereProcessorBlockEntity extends BlockEntity
{
    /** Running processors per world, with the game time they last ran. */
    private static final Map<Level, Map<BlockPos, Long>> RUNNING = new WeakHashMap<>();

    private final MachineEnergy energy = new MachineEnergy(200_000);
    private float heat;
    private boolean running;
    private int coolant;

    public AtmosphereProcessorBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.ATMOSPHERE_PROCESSOR.get(), pos, state);
    }

    public IEnergyStorage energyFor()
    {
        return energy.view(true, false, Integer.MAX_VALUE);
    }

    /** True if a running processor keeps the air at this spot breathable. */
    public static boolean breathable(Level level, BlockPos at)
    {
        Map<BlockPos, Long> running;
        synchronized (RUNNING)
        {
            running = RUNNING.get(level);
        }
        if (running == null)
        {
            return false;
        }
        int radius = AvpConfig.server(AvpConfig.SERVER.processorRadius);
        long now = level.getGameTime();
        for (Map.Entry<BlockPos, Long> entry : running.entrySet())
        {
            BlockPos pos = entry.getKey();
            if (now - entry.getValue() < 40 && Math.abs(pos.getX() - at.getX()) <= radius && Math.abs(pos.getZ() - at.getZ()) <= radius)
            {
                return true;
            }
        }
        return false;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AtmosphereProcessorBlockEntity processor)
    {
        int cost = AvpConfig.server(AvpConfig.SERVER.processorPower);
        processor.running = !level.hasNeighborSignal(pos) && processor.energy.consume(cost);
        if (level.getGameTime() % 20 == 0)
        {
            processor.coolant = 0;
            for (Direction dir : Direction.values())
            {
                if (level.getFluidState(pos.relative(dir)).is(FluidTags.WATER))
                {
                    processor.coolant++;
                }
            }
        }
        synchronized (RUNNING)
        {
            Map<BlockPos, Long> running = RUNNING.computeIfAbsent(level, l -> new HashMap<>());
            if (processor.running)
            {
                running.put(pos.immutable(), level.getGameTime());
            }
            else
            {
                running.remove(pos);
            }
        }
        if (processor.running && processor.coolant == 0)
        {
            processor.heat += 0.02F; // about 80 seconds from cold to critical
        }
        else
        {
            processor.heat = Math.max(0F, processor.heat - 0.05F * Math.max(1, processor.coolant));
        }
        if (processor.running && level instanceof ServerLevel server && level.getGameTime() % 10 == 0)
        {
            server.sendParticles(processor.heat > 75 ? ParticleTypes.LARGE_SMOKE : ParticleTypes.CLOUD,
                    pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, 2, 0.2, 0.1, 0.2, 0.02);
            if (processor.heat > 75 && level.getGameTime() % 40 == 0)
            {
                server.playSound(null, pos, SoundEvents.NOTE_BLOCK_BASS.value(), SoundSource.BLOCKS, 3.0F, 0.5F);
            }
        }
        if (processor.heat >= 100F)
        {
            synchronized (RUNNING)
            {
                RUNNING.getOrDefault(level, Map.of()).remove(pos);
            }
            if (AvpConfig.server(AvpConfig.SERVER.processorMeltdown))
            {
                level.removeBlock(pos, false);
                level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16F, true, Level.ExplosionInteraction.BLOCK);
                return;
            }
            processor.heat = 60F;
            processor.energy.setStored(0);
            level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 1.5F, 0.6F);
        }
        if (level.getGameTime() % 20 == 0)
        {
            processor.setChanged();
        }
    }

    @Override
    public void setRemoved()
    {
        super.setRemoved();
        if (level != null)
        {
            synchronized (RUNNING)
            {
                Map<BlockPos, Long> running = RUNNING.get(level);
                if (running != null)
                {
                    running.remove(worldPosition);
                }
            }
        }
    }

    public Component status()
    {
        ChatFormatting colour = heat > 75 ? ChatFormatting.RED : heat > 40 ? ChatFormatting.GOLD : ChatFormatting.GREEN;
        return Component.translatable(running ? "message.avp.processor_running" : "message.avp.processor_idle",
                String.format("%,d", energy.getEnergyStored()), coolant, String.format("%.0f", heat)).withStyle(colour);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.putInt("Energy", energy.getEnergyStored());
        tag.putFloat("Heat", heat);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        energy.setStored(tag.getInt("Energy"));
        heat = tag.getFloat("Heat");
    }
}
