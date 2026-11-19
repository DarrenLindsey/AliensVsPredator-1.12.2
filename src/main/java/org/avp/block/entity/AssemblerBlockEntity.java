package org.avp.block.entity;

import org.avp.config.AvpConfig;
import org.avp.crafting.AssemblyRecipe;
import org.avp.power.MachineEnergy;
import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;

/** The Assembler's power and the item it last made (shown as a hologram on top). */
public class AssemblerBlockEntity extends BlockEntity
{
    public static final int CAPACITY = 20_000;

    private final MachineEnergy energy = new MachineEnergy(CAPACITY);
    private ItemStack lastMade = ItemStack.EMPTY;
    private int syncedEnergy = -1;

    public AssemblerBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.ASSEMBLER.get(), pos, state);
    }

    public IEnergyStorage energyFor()
    {
        return energy.view(true, false, Integer.MAX_VALUE);
    }

    public MachineEnergy getEnergy()
    {
        return energy;
    }

    public ItemStack lastMade()
    {
        return lastMade;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AssemblerBlockEntity be)
    {
        if (level.getGameTime() % 10 == 0 && be.syncedEnergy != be.energy.getEnergyStored())
        {
            be.setChanged();
            be.sync();
        }
    }

    /**
     * Builds the schematic up to count times from the player's inventory.
     * Stops when materials or power run out.
     */
    public void assemble(Player player, RecipeHolder<AssemblyRecipe> holder, int count)
    {
        AssemblyRecipe recipe = holder.value();
        int cost = AvpConfig.server(AvpConfig.SERVER.assemblerEnergyPerItem);
        boolean free = cost <= 0 || !AvpConfig.server(AvpConfig.SERVER.machinesNeedPower)
                || AvpConfig.server(AvpConfig.SERVER.redstonePowersMachines) && level.hasNeighborSignal(worldPosition);
        int made = 0;
        Component problem = null;
        while (made < count)
        {
            if (!recipe.canAssemble(player.getInventory()))
            {
                problem = made == 0 ? Component.translatable("message.avp.assembler.missing") : null;
                break;
            }
            if (!free && !energy.consume(cost))
            {
                problem = Component.translatable("message.avp.assembler.no_power", cost);
                break;
            }
            recipe.consume(player.getInventory());
            player.getInventory().placeItemBackInInventory(recipe.result().copy());
            made++;
        }
        if (made > 0)
        {
            lastMade = recipe.result().copyWithCount(1);
            level.playSound(null, worldPosition, SoundEvents.SMITHING_TABLE_USE, SoundSource.BLOCKS, 0.6F, 1.3F);
            player.displayClientMessage(Component.translatable("message.avp.assembler.made",
                    made * recipe.result().getCount(), recipe.result().getHoverName()), true);
            setChanged();
            sync();
        }
        if (problem != null)
        {
            player.displayClientMessage(problem, true);
        }
    }

    private void sync()
    {
        if (level != null && !level.isClientSide)
        {
            syncedEnergy = energy.getEnergyStored();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.putInt("Energy", energy.getEnergyStored());
        if (!lastMade.isEmpty())
        {
            tag.put("LastMade", lastMade.save(registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        energy.setStored(tag.getInt("Energy"));
        lastMade = tag.contains("LastMade") ? ItemStack.parseOptional(registries, tag.getCompound("LastMade")) : ItemStack.EMPTY;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
