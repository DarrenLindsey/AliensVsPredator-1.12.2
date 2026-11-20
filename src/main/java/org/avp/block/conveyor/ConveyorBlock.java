package org.avp.block.conveyor;

import javax.annotation.Nullable;

import org.avp.config.AvpConfig;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

/**
 * A conveyor belt: carries items, mobs and players along. At the end it feeds items into a container;
 * hoppers and pipes (and Joes' "put") can load it from the side or top. A redstone signal stops it.
 * The sorting kind sends items matching its filter off to its left.
 */
public class ConveyorBlock extends BaseEntityBlock
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 4, 16);

    public enum Kind
    {
        NORMAL(1.0), EXPRESS(2.2), SORTING(1.0);

        final double speed;

        Kind(double speed)
        {
            this.speed = speed;
        }
    }

    public final Kind kind;

    public ConveyorBlock(Kind kind, Properties properties)
    {
        super(properties);
        this.kind = kind;
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return simpleCodec(p -> new ConveyorBlock(kind, p));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, POWERED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection())
                .setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
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
        return new ConveyorBlockEntity(pos, state);
    }

    @Override
    protected net.minecraft.world.ItemInteractionResult useItemOn(net.minecraft.world.item.ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, net.minecraft.world.InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit)
    {
        if (kind != Kind.SORTING || !(level.getBlockEntity(pos) instanceof ConveyorBlockEntity be))
        {
            return net.minecraft.world.ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!level.isClientSide)
        {
            be.setFilter(stack);
            player.displayClientMessage(net.minecraft.network.chat.Component.translatable("message.avp.conveyor_filter", stack.getHoverName()), true);
        }
        return net.minecraft.world.ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected net.minecraft.world.InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
            net.minecraft.world.phys.BlockHitResult hit)
    {
        if (kind == Kind.SORTING && level.getBlockEntity(pos) instanceof ConveyorBlockEntity be && !level.isClientSide)
        {
            if (player.isShiftKeyDown())
            {
                be.setFilter(net.minecraft.world.item.ItemStack.EMPTY);
            }
            player.displayClientMessage(be.filter().isEmpty() ? net.minecraft.network.chat.Component.translatable("message.avp.conveyor_no_filter")
                    : net.minecraft.network.chat.Component.translatable("message.avp.conveyor_filter", be.filter().getHoverName()), true);
        }
        return net.minecraft.world.InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos from, boolean moving)
    {
        boolean powered = level.hasNeighborSignal(pos);
        if (powered != state.getValue(POWERED))
        {
            level.setBlock(pos, state.setValue(POWERED, powered), 3);
        }
    }

    /** Where an item on this belt goes next: straight on, or (sorting) off to the left when it matches. */
    Direction exitFor(Level level, BlockPos pos, BlockState state, Entity entity)
    {
        Direction facing = state.getValue(FACING);
        if (kind == Kind.SORTING && entity instanceof ItemEntity item && level.getBlockEntity(pos) instanceof ConveyorBlockEntity be
                && be.matches(item.getItem()))
        {
            return facing.getCounterClockWise();
        }
        return facing;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        if (state.getValue(POWERED) || entity.getY() > pos.getY() + 0.5 || entity.isShiftKeyDown() || entity instanceof Player p && p.getAbilities().flying)
        {
            return;
        }
        Direction dir = exitFor(level, pos, state, entity);
        double speed = 0.08 * kind.speed * AvpConfig.server(AvpConfig.SERVER.conveyorSpeed);
        Vec3 centre = Vec3.atCenterOf(pos);
        // push along, and pull towards the middle of the belt
        double along = dir.getAxis() == Direction.Axis.X ? entity.getX() - centre.x : entity.getZ() - centre.z;
        double side = dir.getAxis() == Direction.Axis.X ? centre.z - entity.getZ() : centre.x - entity.getX();
        Vec3 push = new Vec3(dir.getStepX() * speed, 0, dir.getStepZ() * speed);
        Vec3 pull = dir.getAxis() == Direction.Axis.X ? new Vec3(0, 0, side * 0.1) : new Vec3(side * 0.1, 0, 0);
        Vec3 motion = entity.getDeltaMovement();
        if (entity instanceof ItemEntity)
        {
            entity.setDeltaMovement(push.x + pull.x, motion.y, push.z + pull.z);
        }
        else
        {
            entity.setDeltaMovement(motion.x * 0.5 + push.x + pull.x, motion.y, motion.z * 0.5 + push.z + pull.z);
        }
        entity.hurtMarked = true;
        // feed a container at the end of the belt
        if (!level.isClientSide && entity instanceof ItemEntity item && item.isAlive()
                && along * (dir.getAxis() == Direction.Axis.X ? dir.getStepX() : dir.getStepZ()) > 0.3)
        {
            BlockPos next = pos.relative(dir);
            if (!(level.getBlockState(next).getBlock() instanceof ConveyorBlock))
            {
                IItemHandler into = level.getCapability(Capabilities.ItemHandler.BLOCK, next, dir.getOpposite());
                if (into != null)
                {
                    var left = ItemHandlerHelper.insertItemStacked(into, item.getItem().copy(), false);
                    if (left.isEmpty())
                    {
                        item.discard();
                    }
                    else
                    {
                        item.setItem(left);
                    }
                }
            }
        }
    }
}
