package org.avp.block.conveyor.spline;

import javax.annotation.Nullable;

import org.avp.registry.ModBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Spline anchors. BELT: a belt post (faces the way items go). CABLE: a Weyland-Yutani cable pylon
 * (part of the power network). HOSE: a Seegson hose anchor (faces away from the tank it pumps from).
 * String them together with the matching spool.
 */
public class SplineAnchorBlock extends BaseEntityBlock
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public enum Kind
    {
        BELT, CABLE, HOSE
    }

    private static final VoxelShape BELT_SHAPE = Shapes.or(Block.box(6, 0, 6, 10, 10, 10), Block.box(3, 10, 3, 13, 13, 13));
    private static final VoxelShape CABLE_SHAPE = Shapes.or(Block.box(6, 0, 6, 10, 16, 10), Block.box(2, 13, 7, 14, 15, 9));
    private static final VoxelShape HOSE_SHAPE = Block.box(4, 4, 4, 12, 12, 12);

    public final Kind kind;

    public SplineAnchorBlock(Kind kind, Properties properties)
    {
        super(properties);
        this.kind = kind;
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return simpleCodec(p -> new SplineAnchorBlock(kind, p));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        // a hose anchor faces away from the block it's stuck on; the others face the way you look
        Direction facing = kind == Kind.HOSE && context.getClickedFace().getAxis().isHorizontal() ? context.getClickedFace()
                : context.getHorizontalDirection();
        return defaultBlockState().setValue(FACING, facing);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return switch (kind)
        {
            case BELT -> BELT_SHAPE;
            case CABLE -> CABLE_SHAPE;
            case HOSE -> HOSE_SHAPE;
        };
    }

    @Override
    protected RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return switch (kind)
        {
            case BELT -> new BeltAnchorBlockEntity(pos, state);
            case CABLE -> new CablePylonBlockEntity(pos, state);
            case HOSE -> new HoseAnchorBlockEntity(pos, state);
        };
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return switch (kind)
        {
            case BELT -> createTickerHelper(type, ModBlockEntities.BELT_ANCHOR.get(), BeltAnchorBlockEntity::tick);
            case HOSE -> createTickerHelper(type, ModBlockEntities.HOSE_ANCHOR.get(), HoseAnchorBlockEntity::tick);
            default -> null;
        };
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState old, boolean moving)
    {
        if (kind == Kind.CABLE)
        {
            org.avp.power.PowerNetwork.invalidate(level);
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState next, boolean moving)
    {
        if (!state.is(next.getBlock()) && level.getBlockEntity(pos) instanceof SplineAnchorBlockEntity be)
        {
            be.detachAll();
        }
        super.onRemove(state, level, pos, next, moving);
        if (kind == Kind.CABLE)
        {
            org.avp.power.PowerNetwork.invalidate(level);
        }
    }
}
