package org.avp.entity.joe;

import javax.annotation.Nullable;

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

/**
 * The Seegson Terminal (computer) and the Seegson Network Server share this block entity: both draw
 * power. A server also keeps its chunk loaded and tells the network it's online.
 */
public class SeegsonTerminalBlockEntity extends BlockEntity
{
    private final MachineEnergy energy = new MachineEnergy(50_000);
    @Nullable
    private java.util.UUID owner;
    private boolean on, forced;

    public SeegsonTerminalBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.SEEGSON_TERMINAL.get(), pos, state);
    }

    public IEnergyStorage energyFor()
    {
        return energy.view(true, false, Integer.MAX_VALUE);
    }

    public boolean isServer()
    {
        return getBlockState().getBlock() instanceof SeegsonTerminalBlock block && block.server;
    }

    public void setOwner(java.util.UUID owner)
    {
        this.owner = owner;
        setChanged();
    }

    public boolean powered()
    {
        return on || !AvpConfig.server(AvpConfig.SERVER.joeTerminalNeedsPower);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SeegsonTerminalBlockEntity be)
    {
        int cost = be.isServer() ? AvpConfig.server(AvpConfig.SERVER.joeServerPower) : 4;
        boolean on = !AvpConfig.server(AvpConfig.SERVER.joeTerminalNeedsPower) || be.energy.consume(cost);
        if (on != be.on)
        {
            be.on = on;
            level.setBlock(pos, state.setValue(SeegsonTerminalBlock.LIT, on), 3);
        }
        if (be.isServer() && level instanceof net.minecraft.server.level.ServerLevel server)
        {
            if (!be.forced)
            {
                be.forced = true;
                JoeNetwork.TICKETS.forceChunk(server, pos, pos.getX() >> 4, pos.getZ() >> 4, true, true);
            }
            if (on && be.owner != null)
            {
                JoeLibrary.markOnline(be.owner, server.getServer().overworld().getGameTime());
            }
        }
    }

    @Override
    public void setRemoved()
    {
        super.setRemoved();
    }

    /** The block was broken: let go of the chunk. */
    public void release()
    {
        if (forced && level instanceof net.minecraft.server.level.ServerLevel server)
        {
            JoeNetwork.TICKETS.forceChunk(server, worldPosition, worldPosition.getX() >> 4, worldPosition.getZ() >> 4, false, true);
            forced = false;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.putInt("Energy", energy.getEnergyStored());
        if (owner != null)
        {
            tag.putUUID("Owner", owner);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        energy.setStored(tag.getInt("Energy"));
        owner = tag.hasUUID("Owner") ? tag.getUUID("Owner") : null;
    }
}
