package org.avp.block.entity;

import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * An empty block entity. It holds no data; it only exists so a
 * BlockEntityRenderer can draw a converted 1.12.2 box model for the block
 * (skulls, biomasks, heads, ferns). See org.avp.client.render.ModelBlockRenderer.
 */
public class ModelBlockEntity extends BlockEntity
{
    public ModelBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.MODEL_BLOCK.get(), pos, state);
    }
}
