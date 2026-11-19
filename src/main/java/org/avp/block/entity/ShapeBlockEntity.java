package org.avp.block.entity;

import javax.annotation.Nullable;

import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;

/**
 * Remembers which block a slope/corner/ridge/pyramid is copying the look of.
 * The client-side model (org.avp.client.model.MimicBakedModel) reads it through
 * ModelData and swaps its texture for that block's texture.
 */
public class ShapeBlockEntity extends BlockEntity
{
    /** How the copied block is handed to the model on the client. */
    public static final ModelProperty<BlockState> MIMIC = new ModelProperty<>();

    @Nullable
    private BlockState mimic;

    public ShapeBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.SHAPE.get(), pos, state);
    }

    @Nullable
    public BlockState getMimic()
    {
        return mimic;
    }

    /** Called on the server; saves the block and sends it to every client that can see it. */
    public void setMimic(@Nullable BlockState mimic)
    {
        this.mimic = mimic;
        setChanged();
        if (level != null)
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    public ModelData getModelData()
    {
        return mimic == null ? ModelData.EMPTY : ModelData.builder().with(MIMIC, mimic).build();
    }

    // ---- saving ----

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        if (mimic != null)
        {
            tag.put("Mimic", NbtUtils.writeBlockState(mimic));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        BlockState old = mimic;
        mimic = tag.contains("Mimic") ? NbtUtils.readBlockState(registries.lookupOrThrow(Registries.BLOCK), tag.getCompound("Mimic")) : null;
        if (level != null && level.isClientSide && old != mimic)
        {
            // the texture changed: rebuild the model data and re-draw the block
            requestModelDataUpdate();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    // ---- syncing to clients ----

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
