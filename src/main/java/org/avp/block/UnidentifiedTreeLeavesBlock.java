package org.avp.block;

import org.avp.registry.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The hanging leaves of the Unidentified (Giger) tree. They come in three
 * pieces that must hang under each other: TOP, then MIDDLE(s), then BOTTOM.
 * A middle or bottom piece with the wrong block above it breaks.
 *
 * 1.12.2 did this in neighborChanged()/checkAndDropBlock(). In 1.21 the
 * vanilla way is canSurvive() + updateShape() returning air.
 */
public class UnidentifiedTreeLeavesBlock extends LeavesBlock
{
    public enum Part
    {
        TOP, MIDDLE, BOTTOM
    }

    private final Part part;

    public UnidentifiedTreeLeavesBlock(Part part, Properties properties)
    {
        super(properties);
        this.part = part;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockState above = level.getBlockState(pos.above());
        return switch (part)
        {
            case TOP -> true;
            case MIDDLE -> above.is(ModBlocks.UNITREE_LEAVES.get()) || above.is(ModBlocks.UNITREE_LEAVES_M.get());
            case BOTTOM -> above.is(ModBlocks.UNITREE_LEAVES_M.get());
        };
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighbor, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        if (direction == Direction.UP && !state.canSurvive(level, pos))
        {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighbor, level, pos, neighborPos);
    }
}
