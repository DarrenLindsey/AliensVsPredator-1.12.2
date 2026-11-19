package org.avp.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Paradisian wheat grass. A BushBlock already handles "must stand on dirt/grass
 * and breaks otherwise", so we only set its size. Its look is the old OBJ model
 * (see models/block/paradisian_wheat_grass.json, which uses NeoForge's OBJ loader).
 */
public class WheatGrassBlock extends BushBlock
{
    public static final MapCodec<WheatGrassBlock> CODEC = simpleCodec(WheatGrassBlock::new);
    private static final VoxelShape SHAPE = Block.box(1.6, 0, 1.6, 14.4, 12.8, 14.4);

    public WheatGrassBlock(Properties properties)
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
}
