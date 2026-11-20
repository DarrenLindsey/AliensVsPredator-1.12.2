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

/**
 * Hive resin over a block (1.12.2 TileEntityHiveResin): remembers the block underneath, which
 * still shows through (ResinBakedModel) and comes back when the resin is scraped off.
 */
public class ResinBlockEntity extends BlockEntity
{
    @Nullable
    private BlockState covered;

    public ResinBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.RESIN.get(), pos, state);
    }

    @Nullable
    public BlockState covered()
    {
        return covered;
    }

    public void setCovered(@Nullable BlockState covered)
    {
        this.covered = covered;
        setChanged();
        if (level != null)
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    public ModelData getModelData()
    {
        return covered == null ? ModelData.EMPTY : ModelData.builder().with(ShapeBlockEntity.MIMIC, covered).build();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        if (covered != null)
        {
            tag.put("Covered", NbtUtils.writeBlockState(covered));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        BlockState old = covered;
        covered = tag.contains("Covered") ? NbtUtils.readBlockState(registries.lookupOrThrow(Registries.BLOCK), tag.getCompound("Covered")) : null;
        if (level != null && level.isClientSide && old != covered)
        {
            requestModelDataUpdate();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

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
