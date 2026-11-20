package org.avp.pneumatic;

import org.avp.config.AvpConfig;
import org.avp.power.MachineEnergy;
import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class CompressorBlockEntity extends BlockEntity
{
    private final MachineEnergy energy = new MachineEnergy(20_000);
    private boolean pressurised;

    public CompressorBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.COMPRESSOR.get(), pos, state);
    }

    public IEnergyStorage energyFor()
    {
        return energy.view(true, false, Integer.MAX_VALUE);
    }

    public boolean pressurised()
    {
        return pressurised;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CompressorBlockEntity be)
    {
        be.pressurised = be.energy.consume(AvpConfig.server(AvpConfig.SERVER.compressorPower));
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
}
