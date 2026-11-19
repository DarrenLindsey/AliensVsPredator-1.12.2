package org.avp.block.entity;

import org.avp.block.PoweredBlock;
import org.avp.config.AvpConfig;
import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;

/**
 * A block that runs on power: light panels, CCFL tubes, the cryostasis tube (and the
 * machines in batch D).
 *
 * Power is FE ("Forge Energy"), the energy unit almost every tech mod uses. The buffer
 * is exposed as an energy capability (ModCapabilities), so cables and generators from
 * other mods - and later this mod's own generators - can fill it.
 *
 * Each tick the block is "on" when:
 *  - the "Machines Need Power" setting is off, or
 *  - it has enough FE stored (it uses some every tick), or
 *  - it gets a redstone signal and "Redstone Powers Machines" is on.
 * The on/off state is the block's LIT property, which also sets its light level.
 */
public class PoweredBlockEntity extends BlockEntity
{
    public static final int CAPACITY = 10_000;

    /** EnergyStorage with a way for the block itself to use energy (outside machines can't pull it out). */
    private static class Buffer extends EnergyStorage
    {
        Buffer()
        {
            super(CAPACITY, CAPACITY, 0);
        }

        boolean consume(int amount)
        {
            if (energy < amount)
            {
                return false;
            }
            energy -= amount;
            return true;
        }
    }

    private final Buffer energy = new Buffer();

    /** Who last got out and when (game time), so the medpod doesn't pull them straight back in. Not saved. */
    private final java.util.Map<java.util.UUID, Long> recentExits = new java.util.HashMap<>();

    public void markExited(java.util.UUID entity, long gameTime)
    {
        recentExits.put(entity, gameTime);
    }

    public boolean exitedRecently(java.util.UUID entity, long gameTime, int cooldownTicks)
    {
        Long when = recentExits.get(entity);
        if (when == null)
        {
            return false;
        }
        if (gameTime - when >= cooldownTicks)
        {
            recentExits.remove(entity);
            return false;
        }
        return true;
    }

    /** Client-only animation progress (0 = closed, 1 = open), e.g. the medpod lid. */
    private float animation;
    private float animationPrev;

    public PoweredBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.POWERED.get(), pos, state);
    }

    public EnergyStorage getEnergy()
    {
        return energy;
    }

    /** Animation value between the last two ticks, for smooth drawing. */
    public float animation(float partialTick)
    {
        return animationPrev + (animation - animationPrev) * partialTick;
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, PoweredBlockEntity be)
    {
        be.animationPrev = be.animation;
        if (state.getBlock() instanceof PoweredBlock block)
        {
            float target = block.animationTarget(state);
            float step = 0.025F / 3.125F; // 1.12.2 medpod speed: 0.025 per tick up to 3.125
            be.animation = target > be.animation ? Math.min(target, be.animation + step) : Math.max(target, be.animation - step);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, PoweredBlockEntity be)
    {
        if (!(state.getBlock() instanceof PoweredBlock block))
        {
            return;
        }
        boolean on;
        if (!AvpConfig.server(AvpConfig.SERVER.machinesNeedPower))
        {
            on = true;
        }
        else if (be.energy.consume(block.energyPerTick()))
        {
            on = true;
            be.setChanged();
        }
        else
        {
            on = AvpConfig.server(AvpConfig.SERVER.redstonePowersMachines) && level.hasNeighborSignal(pos);
        }
        if (state.getValue(PoweredBlock.LIT) != on)
        {
            state = state.setValue(PoweredBlock.LIT, on);
            level.setBlock(pos, state, Block.UPDATE_ALL);
        }
        block.poweredTick(level, pos, state, be);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Energy", energy.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        if (tag.contains("Energy"))
        {
            energy.deserializeNBT(registries, tag.get("Energy"));
        }
    }
}
