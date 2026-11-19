package org.avp.registry;

import org.avp.AliensVsPredator;
import org.avp.block.StorageBlock;
import org.avp.block.entity.StorageBlockEntity;

import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

/**
 * "Capabilities" are NeoForge's shared way for blocks to say "I have an
 * inventory / energy / fluid tank". Hoppers, pipes, Applied Energistics 2 and
 * Create all look for these, so registering them here is all the compatibility
 * those mods need for basic item transfer.
 */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class ModCapabilities
{
    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event)
    {
        // Lockers and crates: any side gives access to the 64 slots.
        // Registered on the block (not just the block entity) so the top half of a locker works too.
        event.registerBlock(Capabilities.ItemHandler.BLOCK, (level, pos, state, be, side) ->
        {
            var invPos = state.getBlock() instanceof StorageBlock storage && storage.isTall()
                    && state.getValue(StorageBlock.HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos;
            return level.getBlockEntity(invPos) instanceof StorageBlockEntity storageBe ? storageBe.getItems() : null;
        }, ModBlocks.storageBlocks());
    }

    /** Energy (FE): cables and generators from any mod can power lights and machines. */
    @SubscribeEvent
    public static void registerEnergy(RegisterCapabilitiesEvent event)
    {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.POWERED.get(), (be, side) -> be.getEnergy());
        // blast doors: any piece accepts power for the whole door
        event.registerBlock(Capabilities.EnergyStorage.BLOCK, (level, pos, state, be, side) ->
                state.getBlock() instanceof org.avp.block.BlastDoorBlock door && door.controller(level, state, pos) != null
                        ? door.controller(level, state, pos).getEnergy() : null,
                ModBlocks.BLASTDOOR.get(), ModBlocks.BLASTDOOR_SEVASTOPOL.get());
    }

    private ModCapabilities()
    {
    }
}
