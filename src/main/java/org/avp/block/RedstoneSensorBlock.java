package org.avp.block;

import javax.annotation.Nullable;

import org.avp.power.PowerNetwork;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/**
 * Redstone sensor: a switch for powerlines. While it gets a redstone signal it joins the
 * powerlines on either side together; without one, power stops here.
 * (In 1.12.2 it put out a small voltage instead, which FE has no use for.)
 */
public class RedstoneSensorBlock extends Block
{
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public RedstoneSensorBlock(Properties properties)
    {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(POWERED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return defaultBlockState().setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighbor, BlockPos neighborPos, boolean moving)
    {
        super.neighborChanged(state, level, pos, neighbor, neighborPos, moving);
        boolean powered = level.hasNeighborSignal(pos);
        if (powered != state.getValue(POWERED))
        {
            level.setBlock(pos, state.setValue(POWERED, powered), Block.UPDATE_ALL);
        }
        PowerNetwork.invalidate(level);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving)
    {
        super.onRemove(state, level, pos, newState, moving);
        PowerNetwork.invalidate(level);
    }
}
