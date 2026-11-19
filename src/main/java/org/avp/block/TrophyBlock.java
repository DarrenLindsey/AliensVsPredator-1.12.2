package org.avp.block;

import org.avp.block.entity.ModelBlockEntity;
import org.avp.config.AvpConfig;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Skulls, Predator biomasks and trophy heads. They face the player who placed
 * them and are drawn with a converted 1.12.2 model (see ModelBlockRenderer).
 *
 * Some trophies (the Giger alien head) can also be tipped over: right-click the
 * top to lay it flat, sneak + right-click the top to stand it back up ("side",
 * the normal look), and right-click a side to tilt it like it hangs on a wall.
 *
 * In 1.12.2 the facing and orientation were saved in a TileEntity. In 1.21
 * they're block state properties, which is simpler and needs no syncing.
 */
public class TrophyBlock extends HorizontalDirectionalBlock implements EntityBlock
{
    public enum Orientation implements StringRepresentable
    {
        SIDE, FLAT, WALL;

        @Override
        public String getSerializedName()
        {
            return name().toLowerCase();
        }
    }

    public static final MapCodec<TrophyBlock> CODEC = simpleCodec(p -> new TrophyBlock(p, false));
    public static final EnumProperty<Orientation> ORIENTATION = EnumProperty.create("orientation", Orientation.class);
    private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 8, 12);

    private final boolean canTip;

    public TrophyBlock(Properties properties, boolean canTip)
    {
        super(properties);
        this.canTip = canTip;
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(ORIENTATION, Orientation.SIDE));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, ORIENTATION);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (!canTip || !AvpConfig.SERVER.trophiesCanBeTipped.get())
        {
            return InteractionResult.PASS;
        }
        Orientation orientation;
        if (hit.getDirection() == Direction.UP)
        {
            orientation = player.isShiftKeyDown() ? Orientation.SIDE : Orientation.FLAT;
        }
        else
        {
            orientation = Orientation.WALL;
        }
        level.setBlock(pos, state.setValue(ORIENTATION, orientation), Block.UPDATE_ALL);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    /** Invisible as a normal block model - the block entity renderer draws it instead. */
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
