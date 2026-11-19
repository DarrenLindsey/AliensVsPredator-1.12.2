package org.avp.block;

import java.util.Map;

import javax.annotation.Nullable;

import org.avp.block.entity.PowerlineBlockEntity;
import org.avp.power.PowerNetwork;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;

/**
 * Powerline (cable). Joins up with other powerlines and with any block that uses or makes
 * FE - from this mod or any other. See {@link PowerNetwork} for how the power is shared.
 */
public class PowerlineBlock extends BaseEntityBlock
{
    public static final MapCodec<PowerlineBlock> CODEC = simpleCodec(PowerlineBlock::new);
    public static final Map<Direction, BooleanProperty> CONNECTED = PipeBlock.PROPERTY_BY_DIRECTION;

    private static final VoxelShape CORE = Block.box(6, 6, 6, 10, 10, 10);
    private static final Map<Direction, VoxelShape> ARMS = Map.of(
            Direction.DOWN, Block.box(6, 0, 6, 10, 6, 10),
            Direction.UP, Block.box(6, 10, 6, 10, 16, 10),
            Direction.NORTH, Block.box(6, 6, 0, 10, 10, 6),
            Direction.SOUTH, Block.box(6, 6, 10, 10, 10, 16),
            Direction.WEST, Block.box(0, 6, 6, 6, 10, 10),
            Direction.EAST, Block.box(10, 6, 6, 16, 10, 10));

    public PowerlineBlock(Properties properties)
    {
        super(properties);
        BlockState state = stateDefinition.any();
        for (BooleanProperty property : CONNECTED.values())
        {
            state = state.setValue(property, false);
        }
        registerDefaultState(state);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        CONNECTED.values().forEach(builder::add);
    }

    /** Does a powerline at pos join up with whatever is on that side? */
    public static boolean connectsTo(BlockGetter level, BlockPos pos, Direction dir)
    {
        BlockPos next = pos.relative(dir);
        BlockState state = level.getBlockState(next);
        if (state.getBlock() instanceof PowerlineBlock || state.getBlock() instanceof RedstoneSensorBlock
                || state.getBlock() instanceof PowerMachineBlock || state.getBlock() instanceof PoweredBlock
                || state.getBlock() instanceof BlastDoorBlock)
        {
            return true;
        }
        return level instanceof Level real && real.getCapability(Capabilities.EnergyStorage.BLOCK, next, dir.getOpposite()) != null;
    }

    private BlockState withConnections(BlockGetter level, BlockPos pos, BlockState state)
    {
        for (Direction dir : Direction.values())
        {
            state = state.setValue(CONNECTED.get(dir), connectsTo(level, pos, dir));
        }
        return state;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return withConnections(context.getLevel(), context.getClickedPos(), defaultBlockState());
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction dir, BlockState neighbor, LevelAccessor level, BlockPos pos, BlockPos neighborPos)
    {
        boolean connected = connectsTo(level, pos, dir);
        if (connected != state.getValue(CONNECTED.get(dir)) || isConductorChange(neighbor))
        {
            // something joined or left the network (a lamp turning on next door doesn't count)
            if (level instanceof Level real)
            {
                PowerNetwork.invalidate(real);
            }
        }
        return state.setValue(CONNECTED.get(dir), connected);
    }

    /** Redstone sensors switching on/off change the network too. */
    private static boolean isConductorChange(BlockState neighbor)
    {
        return neighbor.getBlock() instanceof RedstoneSensorBlock;
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighbor, BlockPos neighborPos, boolean moving)
    {
        super.neighborChanged(state, level, pos, neighbor, neighborPos, moving);
        // a block entity next door may have just appeared (its FE capability shows up a moment later)
        BlockState updated = withConnections(level, pos, state);
        if (updated != state)
        {
            PowerNetwork.invalidate(level);
            level.setBlock(pos, updated, Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving)
    {
        super.onPlace(state, level, pos, oldState, moving);
        PowerNetwork.invalidate(level);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving)
    {
        super.onRemove(state, level, pos, newState, moving);
        PowerNetwork.invalidate(level);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        VoxelShape shape = CORE;
        for (Direction dir : Direction.values())
        {
            if (state.getValue(CONNECTED.get(dir)))
            {
                shape = Shapes.or(shape, ARMS.get(dir));
            }
        }
        return shape;
    }

    /** Right-click with an empty hand: how big the network is. */
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (!level.isClientSide)
        {
            PowerNetwork.Network network = PowerNetwork.network(level, pos);
            player.displayClientMessage(network.tooBig()
                    ? Component.translatable("message.avp.powerline.too_big", network.conductors().size())
                    : Component.translatable("message.avp.powerline.info", network.conductors().size(), network.targets().size()), true);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new PowerlineBlockEntity(pos, state);
    }
}
