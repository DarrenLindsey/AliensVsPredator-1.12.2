package org.avp.entity.xenomorph.lab;

import org.avp.config.AvpConfig;
import org.avp.power.MachineEnergy;
import org.avp.power.PowerNetwork;
import org.avp.registry.ModBlockEntities;
import org.avp.registry.ModEntities;
import org.avp.registry.ModItems;

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
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * The Xeno Bio-Reactor: burns contained ovamorphs (and royal jelly, briefly) for the most power in the
 * mod. It runs hot and needs water touching it as coolant. Run it hot and a spent egg may hatch - a
 * facehugger comes out of the exhaust; let it reach 100% and it scrams, spilling several.
 */
public class XenoReactorBlockEntity extends BlockEntity
{
    private final MachineEnergy energy = new MachineEnergy(4_000_000);
    public final ItemStackHandler fuel = new ItemStackHandler(1)
    {
        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return stack.is(ModItems.CONTAINED_OVAMORPH.get()) || stack.is(ModItems.ROYALJELLY.get());
        }

        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }
    };
    private int burn, burnTotal;
    private float heat;

    public XenoReactorBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.XENO_REACTOR.get(), pos, state);
    }

    public IEnergyStorage energyFor()
    {
        return energy.view(false, true, 0);
    }

    public String status()
    {
        ChatFormatting c = heat > 75 ? ChatFormatting.RED : heat > 40 ? ChatFormatting.GOLD : ChatFormatting.GREEN;
        return c + String.format("BIO-REACTOR | fuel %d | burning %ds | %,d FE | heat %.0f%%", fuel.getStackInSlot(0).getCount(), burn / 20,
                energy.getEnergyStored(), heat);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, XenoReactorBlockEntity be)
    {
        int output = AvpConfig.server(AvpConfig.SERVER.xenoReactorOutput);
        int coolant = 0;
        for (Direction d : Direction.values())
        {
            if (level.getFluidState(pos.relative(d)).is(FluidTags.WATER))
            {
                coolant++;
            }
        }
        boolean running = false;
        if (be.burn <= 0 && !level.hasNeighborSignal(pos))
        {
            ItemStack f = be.fuel.getStackInSlot(0);
            if (!f.isEmpty())
            {
                be.burnTotal = be.burn = f.is(ModItems.ROYALJELLY.get()) ? 600 : 6000;
                be.fuel.extractItem(0, 1, false);
            }
        }
        if (be.burn > 0)
        {
            be.burn--;
            running = true;
            be.energy.fill(output);
            be.heat += coolant >= 2 ? -0.01F : coolant == 1 ? 0.01F : 0.05F;
            if (be.burn == 0 && be.burnTotal == 6000 && be.heat > 50 && level.random.nextInt(4) == 0)
            {
                be.hatch((ServerLevel) level, 1); // a spent egg wasn't quite dead
            }
        }
        else
        {
            be.heat -= 0.05F;
        }
        be.heat = Math.max(0, be.heat);
        if (be.heat >= 100)
        {
            be.burn = 0;
            be.heat = 70;
            level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 2F, 0.5F);
            be.hatch((ServerLevel) level, 3);
        }
        PowerNetwork.pushOut(level, pos, be.energy, output * 2, null, false);
        if (running && level instanceof ServerLevel server && level.getGameTime() % 10 == 0)
        {
            server.sendParticles(be.heat > 60 ? ParticleTypes.SNEEZE : ParticleTypes.GLOW, pos.getX() + 0.5, pos.getY() + 1.05, pos.getZ() + 0.5, 2, 0.2, 0.05,
                    0.2, 0.01);
        }
        if (state.getValue(XenoReactorBlock.LIT) != running)
        {
            level.setBlock(pos, state.setValue(XenoReactorBlock.LIT, running), 3);
        }
        if (level.getGameTime() % 20 == 0)
        {
            be.setChanged();
        }
    }

    private void hatch(ServerLevel level, int count)
    {
        for (int i = 0; i < count; i++)
        {
            var hugger = ModEntities.FACEHUGGER.get().create(level);
            if (hugger != null)
            {
                hugger.moveTo(worldPosition.getX() + 0.5, worldPosition.getY() + 1.1, worldPosition.getZ() + 0.5, level.random.nextFloat() * 360, 0);
                hugger.finalizeSpawn(level, level.getCurrentDifficultyAt(worldPosition), MobSpawnType.TRIGGERED, null);
                level.addFreshEntity(hugger);
            }
        }
        level.playSound(null, worldPosition, SoundEvents.SLIME_JUMP, SoundSource.HOSTILE, 1.5F, 0.6F);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Fuel", fuel.serializeNBT(registries));
        tag.putInt("Energy", energy.getEnergyStored());
        tag.putInt("Burn", burn);
        tag.putInt("BurnTotal", burnTotal);
        tag.putFloat("Heat", heat);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        fuel.deserializeNBT(registries, tag.getCompound("Fuel"));
        energy.setStored(tag.getInt("Energy"));
        burn = tag.getInt("Burn");
        burnTotal = tag.getInt("BurnTotal");
        heat = tag.getFloat("Heat");
    }

}
