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

    public PoweredBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.POWERED.get(), pos, state);
    }

    public EnergyStorage getEnergy()
    {
        return energy;
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
            level.setBlock(pos, state.setValue(PoweredBlock.LIT, on), Block.UPDATE_ALL);
        }
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
