package org.avp.pneumatic;

import java.util.EnumMap;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** A glass pneumatic tube. Connects to other tubes, stations and compressors. */
public class TubeBlock extends Block
{
    public static final Map<Direction, BooleanProperty> SIDES = new EnumMap<>(Direction.class);
    static
    {
        SIDES.put(Direction.NORTH, BlockStateProperties.NORTH);
        SIDES.put(Direction.SOUTH, BlockStateProperties.SOUTH);
        SIDES.put(Direction.EAST, BlockStateProperties.EAST);
        SIDES.put(Direction.WEST, BlockStateProperties.WEST);
        SIDES.put(Direction.UP, BlockStateProperties.UP);
        SIDES.put(Direction.DOWN, BlockStateProperties.DOWN);
    }
    private static final VoxelShape CORE = Block.box(5, 5, 5, 11, 11, 11);
    private static final Map<Direction, VoxelShape> ARMS = new EnumMap<>(Direction.class);
    static
    {
        ARMS.put(Direction.NORTH, Block.box(5, 5, 0, 11, 11, 5));
        ARMS.put(Direction.SOUTH, Block.box(5, 5, 11, 11, 11, 16));
        ARMS.put(Direction.WEST, Block.box(0, 5, 5, 5, 11, 11));
        ARMS.put(Direction.EAST, Block.box(11, 5, 5, 16, 11, 11));
        ARMS.put(Direction.DOWN, Block.box(5, 0, 5, 11, 5, 11));
        ARMS.put(Direction.UP, Block.box(5, 11, 5, 11, 16, 11));
    }

    public TubeBlock(Properties properties)
    {
        super(properties);
        BlockState state = stateDefinition.any();
        for (BooleanProperty p : SIDES.values())
        {
            state = state.setValue(p, false);
        }
        registerDefaultState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        SIDES.values().forEach(builder::add);
    }

    public static boolean connects(BlockState other)
    {
        return other.getBlock() instanceof TubeBlock || other.getBlock() instanceof PneumaticStationBlock || other.getBlock() instanceof CompressorBlock
                || other.getBlock() instanceof org.avp.apollo.ApolloCoreBlock;
    }

    private BlockState connected(BlockGetter level, BlockPos pos, BlockState state)
    {
        for (Direction d : Direction.values())
        {
            state = state.setValue(SIDES.get(d), connects(level.getBlockState(pos.relative(d))));
        }
        return state;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return connected(context.getLevel(), context.getClickedPos(), defaultBlockState());
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction dir, BlockState neighbour, LevelAccessor level, BlockPos pos, BlockPos from)
    {
        return state.setValue(SIDES.get(dir), connects(neighbour));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        VoxelShape shape = CORE;
        for (Direction d : Direction.values())
        {
            if (state.getValue(SIDES.get(d)))
            {
                shape = Shapes.or(shape, ARMS.get(d));
            }
        }
        return shape;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos)
    {
        return true;
    }
}
