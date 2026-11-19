package org.avp.block;

import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.avp.block.entity.StorageBlockEntity;
import org.avp.config.AvpConfig;
import org.avp.entity.LockerSeatEntity;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Lockers, the gun locker and supply crates.
 *
 * - Right-click: open the 64-slot inventory (the door opens while someone is looking inside).
 * - Lockers: sneak + right-click with an empty hand to hide inside (LockerSeatEntity);
 *   sneak again to get out. Supply crates: sneak + right-click props the lid open/shut.
 * - Lockers are two blocks tall: the bottom half holds the inventory, the top half is just
 *   there to take up space (the same "half" system vanilla doors use).
 */
public class StorageBlock extends BaseEntityBlock
{
    public static final MapCodec<StorageBlock> CODEC = simpleCodec(p -> new StorageBlock(p, false, stack -> true));
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    /** Someone has the inventory open. */
    public static final BooleanProperty VIEWED = BooleanProperty.create("viewed");
    /** The door was propped open with sneak + right-click. */
    public static final BooleanProperty PROPPED = BooleanProperty.create("propped");

    private final boolean tall;
    private final Predicate<ItemStack> filter;

    public StorageBlock(Properties properties, boolean tall, Predicate<ItemStack> filter)
    {
        super(properties);
        this.tall = tall;
        this.filter = filter;
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(VIEWED, false).setValue(PROPPED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

    public Predicate<ItemStack> filter()
    {
        return filter;
    }

    public boolean isTall()
    {
        return tall;
    }

    public static boolean isOpen(BlockState state)
    {
        return state.getValue(VIEWED) || state.getValue(PROPPED);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, HALF, VIEWED, PROPPED);
    }

    // ---- placing a two-block-tall locker ----

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos pos = context.getClickedPos();
        if (tall && (pos.getY() >= context.getLevel().getMaxBuildHeight() - 1 || !context.getLevel().getBlockState(pos.above()).canBeReplaced(context)))
        {
            return null; // no room for the top half
        }
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if (tall)
        {
            level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), Block.UPDATE_ALL);
        }
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        if (tall && state.getValue(HALF) == DoubleBlockHalf.UPPER)
        {
            BlockState below = level.getBlockState(pos.below());
            return below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
        return true;
    }

    /** Keeps both halves together: if one half disappears, so does the other. */
    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighbor, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        if (tall)
        {
            DoubleBlockHalf half = state.getValue(HALF);
            boolean otherHalfSide = (half == DoubleBlockHalf.LOWER) == (direction == Direction.UP);
            if (direction.getAxis() == Direction.Axis.Y && otherHalfSide)
            {
                if (!neighbor.is(this) || neighbor.getValue(HALF) == half)
                {
                    return Blocks.AIR.defaultBlockState();
                }
                // copy the open/closed state so both halves agree
                return state.setValue(VIEWED, neighbor.getValue(VIEWED)).setValue(PROPPED, neighbor.getValue(PROPPED))
                        .setValue(FACING, neighbor.getValue(FACING));
            }
        }
        return super.updateShape(state, direction, neighbor, level, pos, neighborPos);
    }

    /** Breaking the top half in creative shouldn't drop the locker; let the bottom half handle drops. */
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        if (tall && !level.isClientSide && player.isCreative() && state.getValue(HALF) == DoubleBlockHalf.UPPER)
        {
            BlockPos below = pos.below();
            if (level.getBlockState(below).is(this))
            {
                level.setBlock(below, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_SUPPRESS_DROPS);
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    // ---- using it ----

    /** The bottom half (where the inventory is). */
    private BlockPos inventoryPos(BlockState state, BlockPos pos)
    {
        return tall && state.getValue(HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        BlockPos invPos = inventoryPos(state, pos);
        if (level.isClientSide)
        {
            return InteractionResult.SUCCESS;
        }
        if (player.isShiftKeyDown())
        {
            if (tall)
            {
                if (AvpConfig.server(AvpConfig.SERVER.lockersCanHide) && !LockerSeatEntity.isOccupied(level, invPos))
                {
                    LockerSeatEntity.hide(level, invPos, player);
                }
            }
            else
            {
                BlockState lower = level.getBlockState(invPos);
                setOpenState(level, invPos, lower.setValue(PROPPED, !lower.getValue(PROPPED)));
            }
        }
        else if (level.getBlockEntity(invPos) instanceof StorageBlockEntity storage)
        {
            player.openMenu(storage, invPos);
        }
        return InteractionResult.CONSUME;
    }

    /** Called by the block entity when the first player opens / last player closes the menu. */
    public void setViewed(Level level, BlockPos pos, BlockState state, boolean viewed)
    {
        if (state.getValue(VIEWED) != viewed)
        {
            setOpenState(level, pos, state.setValue(VIEWED, viewed));
        }
    }

    /** Opens/closes the door of the storage block at lowerPos (used when hiding in a locker). */
    public static void setDoorOpen(Level level, BlockPos lowerPos, boolean open)
    {
        BlockState state = level.getBlockState(lowerPos);
        if (state.getBlock() instanceof StorageBlock storage && state.getValue(PROPPED) != open)
        {
            storage.setOpenState(level, lowerPos, state.setValue(PROPPED, open));
        }
    }

    /** Opens the door and closes it again after half a second (someone stepping out). */
    public static void openDoorBriefly(Level level, BlockPos lowerPos)
    {
        setDoorOpen(level, lowerPos, true);
        BlockState state = level.getBlockState(lowerPos);
        level.scheduleTick(lowerPos, state.getBlock(), 10);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        // the scheduled tick from openDoorBriefly: close the door again unless someone is still hiding
        if (state.getValue(PROPPED) && !LockerSeatEntity.isOccupied(level, pos))
        {
            setOpenState(level, pos, state.setValue(PROPPED, false));
        }
    }

    private void setOpenState(Level level, BlockPos lowerPos, BlockState newLower)
    {
        level.setBlock(lowerPos, newLower, Block.UPDATE_ALL);
        if (tall)
        {
            BlockPos upper = lowerPos.above();
            BlockState up = level.getBlockState(upper);
            if (up.is(this))
            {
                level.setBlock(upper, up.setValue(VIEWED, newLower.getValue(VIEWED)).setValue(PROPPED, newLower.getValue(PROPPED)), Block.UPDATE_ALL);
            }
        }
    }

    // ---- removing ----

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston)
    {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof StorageBlockEntity storage)
        {
            storage.dropContents();
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    // ---- rendering / block entity ----

    @Override
    protected RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED; // drawn by ModelBlockRenderer
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? new StorageBlockEntity(pos, state) : null;
    }
}
