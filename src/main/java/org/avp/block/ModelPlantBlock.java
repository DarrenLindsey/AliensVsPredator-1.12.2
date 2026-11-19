package org.avp.block;

import org.avp.block.entity.ModelBlockEntity;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Paradisian ferns: plants drawn with a 3D box model. Each fern gets its own
 * size, colour and turn, worked out from its position (see ModelBlockRenderer),
 * so no data has to be saved - in 1.12.2 these were random values stored in
 * the TileEntity.
 */
public class ModelPlantBlock extends BushBlock implements EntityBlock
{
    public static final MapCodec<ModelPlantBlock> CODEC = simpleCodec(ModelPlantBlock::new);
    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);

    public ModelPlantBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ModelBlockEntity(pos, state);
    }
}
