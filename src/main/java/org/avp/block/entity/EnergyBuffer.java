package org.avp.block.entity;

import org.avp.config.AvpConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.energy.EnergyStorage;

/**
 * An FE energy buffer that other mods can fill (exposed as an energy capability) but not
 * drain; the block itself uses the energy. Shared by all powered blocks.
 */
public class EnergyBuffer extends EnergyStorage
{
    public EnergyBuffer(int capacity)
    {
        super(capacity, capacity, 0);
    }

    public boolean consume(int amount)
    {
        if (energy < amount)
        {
            return false;
        }
        energy -= amount;
        return true;
    }

    /**
     * The power rules every powered block uses. A block is "on" this tick when:
     *  - the "Machines Need Power" setting is off, or
     *  - it has enough FE stored (it uses some every tick), or
     *  - it gets a redstone signal and "Redstone Powers Machines" is on.
     *
     * @return true if the block is powered this tick (and uses energy if that's how)
     */
    public boolean tickPower(Level level, BlockPos pos, int usePerTick)
    {
        if (!AvpConfig.server(AvpConfig.SERVER.machinesNeedPower))
        {
            return true;
        }
        if (consume(usePerTick))
        {
            return true;
        }
        return AvpConfig.server(AvpConfig.SERVER.redstonePowersMachines) && level.hasNeighborSignal(pos);
    }
}
