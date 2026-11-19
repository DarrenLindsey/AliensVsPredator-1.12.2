package org.avp.block;

import javax.annotation.Nullable;

import org.avp.block.entity.TurretBlockEntity;
import org.avp.registry.ModBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * UA 571-C style sentry gun. Runs on FE, loads Sentry Ammo Boxes, and shoots whatever its
 * target settings allow - never its owner or the owner's team (IFF). Right-click to open it.
 */
public class TurretBlock extends BaseEntityBlock
{
    public static final MapCodec<TurretBlock> CODEC = simpleCodec(TurretBlock::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    /** Mounted upside down under a ceiling. */
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    private static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 15, 13);
    private static final VoxelShape HANGING_SHAPE = Block.box(3, 1, 3, 13, 16, 13);

    public TurretBlock(Properties properties)
    {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false).setValue(HANGING, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, LIT, HANGING);
    }

    /**
     * It watches the way the player was facing when it was put down.
     * Placed against the underside of a block, it hangs from the ceiling.
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection())
                .setValue(HANGING, context.getClickedFace() == Direction.DOWN);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (placer instanceof Player player && level.getBlockEntity(pos) instanceof TurretBlockEntity turret)
        {
            turret.setOwner(player);
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(HANGING) ? HANGING_SHAPE : SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer && level.getBlockEntity(pos) instanceof TurretBlockEntity turret)
        {
            serverPlayer.openMenu(turret, pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving)
    {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof TurretBlockEntity turret)
        {
            for (int i = 0; i < turret.getAmmo().getSlots(); i++)
            {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), turret.getAmmo().getStackInSlot(i));
            }
            for (int i = 0; i < turret.getUpgrades().getSlots(); i++)
            {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), turret.getUpgrades().getStackInSlot(i));
            }
        }
        super.onRemove(state, level, pos, newState, moving);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state)
    {
        return true;
    }

    /** Comparators read how much ammo is left (loaded rounds + boxes in the bay). */
    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
    {
        return level.getBlockEntity(pos) instanceof TurretBlockEntity turret ? turret.comparatorLevel() : 0;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new TurretBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return createTickerHelper(type, ModBlockEntities.TURRET.get(),
                level.isClientSide ? TurretBlockEntity::clientTick : TurretBlockEntity::serverTick);
    }
}
