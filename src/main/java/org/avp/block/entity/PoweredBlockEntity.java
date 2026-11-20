package org.avp.block.entity;

import org.avp.block.PoweredBlock;
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

    private final EnergyBuffer energy = new EnergyBuffer(CAPACITY);

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

    // ---- cryostasis tube contents (see CryostasisTubeBlock) ----
    private net.minecraft.world.item.ItemStack contents = net.minecraft.world.item.ItemStack.EMPTY;
    private int jelly;
    private int harvestTimer;
    private int thawTicks;

    public net.minecraft.world.item.ItemStack contents()
    {
        return contents;
    }

    public void setContents(net.minecraft.world.item.ItemStack contents)
    {
        this.contents = contents;
        this.harvestTimer = 0;
        this.thawTicks = 0;
        setChanged();
        sync();
    }

    public int jelly()
    {
        return jelly;
    }

    public void setJelly(int jelly)
    {
        this.jelly = jelly;
        setChanged();
    }

    /** Counts up while frozen; returns true when it's time to harvest. */
    public boolean harvestTick(int interval)
    {
        if (++harvestTimer >= interval)
        {
            harvestTimer = 0;
            return true;
        }
        return false;
    }

    public int thawTicks()
    {
        return thawTicks;
    }

    public void setThawTicks(int ticks)
    {
        this.thawTicks = ticks;
    }

    public void sync()
    {
        if (level != null && !level.isClientSide)
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), net.minecraft.world.level.block.Block.UPDATE_CLIENTS);
        }
    }

    /** Hoppers can take the harvested royal jelly out of a cryostasis tube. */
    public net.neoforged.neoforge.items.IItemHandler jellyOutput()
    {
        PoweredBlockEntity be = this;
        return new net.neoforged.neoforge.items.IItemHandler()
        {
            @Override
            public int getSlots()
            {
                return 1;
            }

            @Override
            public net.minecraft.world.item.ItemStack getStackInSlot(int slot)
            {
                return be.jelly > 0 ? new net.minecraft.world.item.ItemStack(org.avp.registry.ModItems.ROYALJELLY.get(), be.jelly) : net.minecraft.world.item.ItemStack.EMPTY;
            }

            @Override
            public net.minecraft.world.item.ItemStack insertItem(int slot, net.minecraft.world.item.ItemStack stack, boolean simulate)
            {
                return stack;
            }

            @Override
            public net.minecraft.world.item.ItemStack extractItem(int slot, int amount, boolean simulate)
            {
                int taken = Math.min(amount, be.jelly);
                if (taken <= 0)
                {
                    return net.minecraft.world.item.ItemStack.EMPTY;
                }
                if (!simulate)
                {
                    be.setJelly(be.jelly - taken);
                }
                return new net.minecraft.world.item.ItemStack(org.avp.registry.ModItems.ROYALJELLY.get(), taken);
            }

            @Override
            public int getSlotLimit(int slot)
            {
                return 64;
            }

            @Override
            public boolean isItemValid(int slot, net.minecraft.world.item.ItemStack stack)
            {
                return false;
            }
        };
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
        boolean on = be.energy.tickPower(level, pos, block.energyPerTick());
        if (on)
        {
            be.setChanged();
        }
        // brownout: a light running on its last dregs of power flickers
        if (on && block.energyPerTick() > 0 && be.energy.getEnergyStored() < block.energyPerTick() * 60
                && state.setValue(PoweredBlock.LIT, true).getLightEmission() > 0 && level.random.nextInt(5) == 0)
        {
            on = false;
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
        if (!contents.isEmpty())
        {
            tag.put("Contents", contents.save(registries));
        }
        tag.putInt("Jelly", jelly);
        tag.putInt("HarvestTimer", harvestTimer);
        tag.putInt("ThawTicks", thawTicks);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        if (tag.contains("Energy"))
        {
            energy.deserializeNBT(registries, tag.get("Energy"));
        }
        contents = tag.contains("Contents") ? net.minecraft.world.item.ItemStack.parseOptional(registries, tag.getCompound("Contents"))
                : net.minecraft.world.item.ItemStack.EMPTY;
        jelly = tag.getInt("Jelly");
        harvestTimer = tag.getInt("HarvestTimer");
        thawTicks = tag.getInt("ThawTicks");
    }

    /** The client needs to know what's in a cryostasis tube to draw it. */
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        CompoundTag tag = super.getUpdateTag(registries);
        if (!contents.isEmpty())
        {
            tag.put("Contents", contents.save(registries));
        }
        return tag;
    }

    @Override
    public net.minecraft.network.protocol.Packet<net.minecraft.network.protocol.game.ClientGamePacketListener> getUpdatePacket()
    {
        return net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket.create(this);
    }
}
