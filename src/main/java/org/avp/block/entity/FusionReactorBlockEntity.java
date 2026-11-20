package org.avp.block.entity;

import org.avp.config.AvpConfig;
import org.avp.power.MachineEnergy;
import org.avp.power.PowerNetwork;
import org.avp.registry.ModBlockEntities;
import org.avp.registry.ModItems;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;

/**
 * A Weyland-Yutani compact fusion reactor (the kind a starship like the Nostromo runs on). Each
 * lithium ingot keeps it fusing for 2 minutes at a high, steady output. Its power has to go
 * somewhere: with nowhere to put it the core heats up, and at 100% it melts down. A redstone
 * signal scrams it (shuts it down).
 */
public class FusionReactorBlockEntity extends BlockEntity
{
    public static final int FUEL_TICKS = 2400;
    private final MachineEnergy energy = new MachineEnergy(400_000);
    private int fuel, burn;
    private float heat;
    private int alarm;

    public FusionReactorBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.FUSION_REACTOR.get(), pos, state);
    }

    public IEnergyStorage energyFor()
    {
        return energy.view(false, true, 0);
    }

    /** Hoppers and pipes may load lithium. */
    public final IItemHandler fuelInput = new IItemHandler()
    {
        @Override
        public int getSlots()
        {
            return 1;
        }

        @Override
        public ItemStack getStackInSlot(int slot)
        {
            return ItemStack.EMPTY;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
        {
            if (!stack.is(ModItems.INGOT_LITHIUM.get()))
            {
                return stack;
            }
            int take = Math.min(stack.getCount(), 64 - fuel);
            if (!simulate && take > 0)
            {
                fuel += take;
                setChanged();
            }
            return stack.copyWithCount(stack.getCount() - take);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot)
        {
            return 64;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return stack.is(ModItems.INGOT_LITHIUM.get());
        }
    };

    public static void serverTick(Level level, BlockPos pos, BlockState state, FusionReactorBlockEntity reactor)
    {
        boolean scram = level.hasNeighborSignal(pos);
        int output = AvpConfig.server(AvpConfig.SERVER.reactorOutput);
        boolean running = false;
        if (!scram && (reactor.burn > 0 || reactor.fuel > 0))
        {
            if (reactor.burn <= 0)
            {
                reactor.fuel--;
                reactor.burn = FUEL_TICKS;
            }
            reactor.burn--;
            running = true;
            int made = reactor.energy.fill(output);
            if (made < output)
            {
                reactor.heat += 0.05F * (1F - made / (float) output); // nowhere for the power to go: the core heats up
            }
        }
        if (!running || reactor.energy.getEnergyStored() < reactor.energy.getMaxEnergyStored() * 0.9F)
        {
            reactor.heat = Math.max(0F, reactor.heat - (running ? 0.03F : 0.1F));
        }
        PowerNetwork.pushOut(level, pos, reactor.energy, output * 2, null, false);
        if (reactor.heat > 75F && level instanceof ServerLevel server)
        {
            if (++reactor.alarm % 20 == 0)
            {
                server.playSound(null, pos, SoundEvents.NOTE_BLOCK_BASS.value(), SoundSource.BLOCKS, 2.0F, 0.5F);
            }
            server.sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 2, 0.3, 0.1, 0.3, 0.02);
        }
        if (reactor.heat >= 100F)
        {
            if (AvpConfig.server(AvpConfig.SERVER.reactorMeltdown))
            {
                level.removeBlock(pos, false);
                level.explode(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10F, true, Level.ExplosionInteraction.BLOCK);
                return;
            }
            // safety interlock: it scrams itself
            reactor.burn = 0;
            reactor.heat = 60F;
            level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 1.5F, 0.6F);
        }
        if (level.getGameTime() % 20 == 0)
        {
            reactor.setChanged();
        }
    }

    public Component status()
    {
        ChatFormatting colour = heat > 75 ? ChatFormatting.RED : heat > 40 ? ChatFormatting.GOLD : ChatFormatting.GREEN;
        return Component.translatable("message.avp.reactor_status", fuel, burn / 20, String.format("%,d", energy.getEnergyStored()),
                String.format("%.0f", heat)).withStyle(colour);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.putInt("Energy", energy.getEnergyStored());
        tag.putInt("Fuel", fuel);
        tag.putInt("Burn", burn);
        tag.putFloat("Heat", heat);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        energy.setStored(tag.getInt("Energy"));
        fuel = tag.getInt("Fuel");
        burn = tag.getInt("Burn");
        heat = tag.getFloat("Heat");
    }
}
