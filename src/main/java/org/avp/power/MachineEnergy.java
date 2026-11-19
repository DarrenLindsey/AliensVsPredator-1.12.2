package org.avp.power;

import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

/**
 * The FE store inside a generator, transformer, power cell or machine.
 * The block itself adds energy (generators) or drains it (machines, pushing out);
 * what other blocks and mods may do is set by {@link #view}.
 */
public class MachineEnergy extends EnergyStorage
{
    public MachineEnergy(int capacity)
    {
        super(capacity, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /** Adds energy made by the block itself. @return how much fitted */
    public int fill(int amount)
    {
        int added = Math.max(0, Math.min(capacity - energy, amount));
        energy += added;
        return added;
    }

    /** Takes energy out (used, or pushed to a neighbour). @return how much was taken */
    public int drain(int amount)
    {
        int taken = Math.max(0, Math.min(energy, amount));
        energy -= taken;
        return taken;
    }

    /** Uses exactly this much energy, or none if there isn't enough. */
    public boolean consume(int amount)
    {
        if (energy < amount)
        {
            return false;
        }
        energy -= amount;
        return true;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
        this.energy = Math.min(energy, capacity);
    }

    public void setStored(int energy)
    {
        this.energy = Math.max(0, Math.min(capacity, energy));
    }

    /**
     * What another block (a cable, another mod's machine) sees on one side.
     *
     * @param in  may it put energy in
     * @param out may it take energy out
     * @param maxIn most it may put in per call
     */
    public IEnergyStorage view(boolean in, boolean out, int maxIn)
    {
        MachineEnergy self = this;
        return new IEnergyStorage()
        {
            @Override
            public int receiveEnergy(int toReceive, boolean simulate)
            {
                if (!in)
                {
                    return 0;
                }
                int accepted = Math.max(0, Math.min(Math.min(maxIn, toReceive), self.capacity - self.energy));
                if (!simulate)
                {
                    self.energy += accepted;
                }
                return accepted;
            }

            @Override
            public int extractEnergy(int toExtract, boolean simulate)
            {
                if (!out)
                {
                    return 0;
                }
                int taken = Math.max(0, Math.min(toExtract, self.energy));
                if (!simulate)
                {
                    self.energy -= taken;
                }
                return taken;
            }

            @Override
            public int getEnergyStored()
            {
                return self.energy;
            }

            @Override
            public int getMaxEnergyStored()
            {
                return self.capacity;
            }

            @Override
            public boolean canExtract()
            {
                return out;
            }

            @Override
            public boolean canReceive()
            {
                return in;
            }
        };
    }
}
