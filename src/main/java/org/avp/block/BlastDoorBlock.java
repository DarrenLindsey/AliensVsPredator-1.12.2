package org.avp.block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.avp.block.entity.BlastDoorBlockEntity;
import org.avp.registry.ModBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Blast doors: a multi-block door (4x3, or 2x3 for the Sevastopol door).
 *
 * The block you place is the "controller" (PART 0) and holds the BlastDoorBlockEntity
 * with power, password and lock settings. The other pieces are the same block with
 * PART 1..n; they work out where the controller is from their PART and FACING.
 * The door extends to the side and up from the controller, like in 1.12.2.
 *
 * All the interaction logic (power, passwords, Security Tuner, Maintenance Jack)
 * lives in BlastDoorBlockEntity.
 */
public class BlastDoorBlock extends BaseEntityBlock
{
    /** 4 wide, 3 tall (1.12.2 TileEntityBlastdoor.defaultSet, controller first). */
    public static final List<Vec3i> BIG = layout(4, 3);
    /** 2 wide, 3 tall (1.12.2 TileEntitySevastopolBlastDoor.defaultSet). */
    public static final List<Vec3i> SEVASTOPOL = layout(2, 3);

    public static final MapCodec<BlastDoorBlock> CODEC = simpleCodec(p -> new BlastDoorBlock(p, BIG, 0.03F, false));
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 11);
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    /** Threat detected: the door is in lockdown and gives out a redstone signal. */
    public static final BooleanProperty ALARM = BooleanProperty.create("alarm");

    private final List<Vec3i> layout;
    private final float speed;
    private final boolean sevastopol;

    /**
     * @param layout     the pieces (x = sideways, y = up), controller first
     * @param speed      how far the door moves per tick (1.12.2: 0.03 big door, 0.09 Sevastopol)
     * @param sevastopol the Sevastopol style (slides up, indicator lights, own sounds)
     */
    public BlastDoorBlock(Properties properties, List<Vec3i> layout, float speed, boolean sevastopol)
    {
        super(properties);
        this.layout = layout;
        this.speed = speed;
        this.sevastopol = sevastopol;
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PART, 0).setValue(OPEN, false).setValue(ALARM, false));
    }

    private static List<Vec3i> layout(int width, int height)
    {
        List<Vec3i> parts = new ArrayList<>();
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                parts.add(new Vec3i(x, y, 0));
            }
        }
        return parts;
    }

    public float speed()
    {
        return speed;
    }

    public boolean isSevastopol()
    {
        return sevastopol;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, PART, OPEN, ALARM);
    }

    // ---- where the pieces are ----

    /** Offset of a piece from the controller. The door extends to the right of whoever placed it. */
    private BlockPos offset(int part, Direction facing)
    {
        Vec3i v = layout.get(part);
        Direction side = facing.getClockWise();
        return new BlockPos(side.getStepX() * v.getX(), v.getY(), side.getStepZ() * v.getX());
    }

    public BlockPos controllerPos(BlockState state, BlockPos pos)
    {
        return pos.subtract(offset(state.getValue(PART), state.getValue(FACING)));
    }

    /** Every piece's position, controller first. */
    public List<BlockPos> partPositions(BlockPos controller, Direction facing)
    {
        List<BlockPos> list = new ArrayList<>();
        for (int i = 0; i < layout.size(); i++)
        {
            list.add(controller.offset(offset(i, facing)));
        }
        return list;
    }

    @Nullable
    public BlastDoorBlockEntity controller(Level level, BlockState state, BlockPos pos)
    {
        return level.getBlockEntity(controllerPos(state, pos)) instanceof BlastDoorBlockEntity door ? door : null;
    }

    // ---- placing and breaking ----

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Direction facing = context.getHorizontalDirection().getOpposite();
        Level level = context.getLevel();
        for (BlockPos p : partPositions(context.getClickedPos(), facing))
        {
            if (p.getY() >= level.getMaxBuildHeight() || !level.getBlockState(p).canBeReplaced(context))
            {
                return null; // not enough room for the whole door
            }
        }
        return defaultBlockState().setValue(FACING, facing);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        List<BlockPos> positions = partPositions(pos, state.getValue(FACING));
        for (int i = 1; i < positions.size(); i++)
        {
            level.setBlock(positions.get(i), state.setValue(PART, i), Block.UPDATE_ALL);
        }
    }

    /** Breaking any piece in survival breaks the controller with drops (the item comes from the controller). */
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        if (!level.isClientSide && state.getValue(PART) != 0)
        {
            level.destroyBlock(controllerPos(state, pos), !player.isCreative(), player);
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    /** When one piece goes, the whole door goes. */
    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston)
    {
        if (!newState.is(this))
        {
            for (BlockPos p : partPositions(controllerPos(state, pos), state.getValue(FACING)))
            {
                if (!p.equals(pos) && level.getBlockState(p).is(this))
                {
                    level.setBlock(p, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                }
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    // ---- using it ----

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (!BlastDoorBlockEntity.isDoorTool(stack))
        {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!level.isClientSide)
        {
            BlastDoorBlockEntity door = controller(level, state, pos);
            if (door != null)
            {
                door.use(player, stack);
            }
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (!level.isClientSide)
        {
            BlastDoorBlockEntity door = controller(level, state, pos);
            if (door != null)
            {
                door.use(player, ItemStack.EMPTY);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    // ---- redstone output while a threat is detected ----

    @Override
    protected boolean isSignalSource(BlockState state)
    {
        return true;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        return state.getValue(ALARM) ? 15 : 0;
    }

    // ---- shape and rendering ----

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(OPEN) ? Shapes.empty() : Shapes.block();
    }

    @Override
    protected RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED; // drawn by BlastDoorRenderer from the controller
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return state.getValue(PART) == 0 ? new BlastDoorBlockEntity(pos, state) : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return level.isClientSide
                ? createTickerHelper(type, ModBlockEntities.BLAST_DOOR.get(), BlastDoorBlockEntity::clientTick)
                : createTickerHelper(type, ModBlockEntities.BLAST_DOOR.get(), BlastDoorBlockEntity::serverTick);
    }
}
