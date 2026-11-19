package org.avp.block;

import javax.annotation.Nullable;

import org.avp.block.entity.PowerMachineBlockEntity;
import org.avp.registry.ModBlockEntities;
import org.avp.registry.ModItems;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
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
 * Generators, transformers, the power cell and the machines that only use power
 * (redstone emitter, satellite dish, tesla coil). What each one does is in
 * {@link PowerMachineBlockEntity}; this class handles placing, clicking and redstone.
 *
 * Right-click with an empty hand shows the stored energy. Sneak + right-click:
 *  - transformers turn to face the next way,
 *  - the Universal Generator gives back its fuel,
 *  - the Repulsion Generator gives back a magnet.
 */
public class PowerMachineBlock extends BaseEntityBlock
{
    public enum Kind
    {
        /** Makes FE from daylight. */
        SOLAR_PANEL(false),
        /** Burns furnace fuel (the 1.12.2 "Redstone Flux Generator"). */
        GENERATOR(false),
        /** Spins neodymium magnets: steady FE, no fuel. */
        REPULSION_GENERATOR(false),
        /** Passes power from its back to its front. */
        TRANSFORMER(true),
        /** Like the transformer but lets less through. */
        STEPDOWN_TRANSFORMER(true),
        /** Stores a lot of FE. */
        POWERCELL(false),
        /** Gives a redstone signal while powered. */
        REDSTONE_EMITTER(false),
        SATELLITE_DISH(false),
        /** Strikes hostile creatures with lightning arcs. */
        TESLA_COIL(false);

        /** Transformers point away from the player (power flows the way you face). */
        public final boolean facesAway;

        Kind(boolean facesAway)
        {
            this.facesAway = facesAway;
        }

        public boolean isTransformer()
        {
            return this == TRANSFORMER || this == STEPDOWN_TRANSFORMER;
        }

        public boolean isGenerator()
        {
            return this == SOLAR_PANEL || this == GENERATOR || this == REPULSION_GENERATOR;
        }
    }

    public static final MapCodec<PowerMachineBlock> CODEC = simpleCodec(p -> new PowerMachineBlock(p, Kind.POWERCELL));
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final int MAX_MAGNETS = 8;

    private static final VoxelShape SOLAR_SHAPE = Block.box(0, 4, 0, 16, 12, 16);
    private static final VoxelShape TRANSFORMER_SHAPE = Block.box(0, 0, 0, 16, 15, 16);

    private final Kind kind;

    public PowerMachineBlock(Properties properties, Kind kind)
    {
        super(properties);
        this.kind = kind;
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    public Kind kind()
    {
        return kind;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, LIT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Direction facing = context.getHorizontalDirection();
        return defaultBlockState().setValue(FACING, kind.facesAway ? facing : facing.getOpposite());
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return switch (kind)
        {
            case SOLAR_PANEL -> SOLAR_SHAPE;
            case TRANSFORMER, STEPDOWN_TRANSFORMER, GENERATOR -> TRANSFORMER_SHAPE;
            default -> super.getShape(state, level, pos, context);
        };
    }

    @Override
    protected RenderShape getRenderShape(BlockState state)
    {
        // the redstone emitter is a plain cube; the rest are drawn with their 1.12.2 models
        return kind == Kind.REDSTONE_EMITTER ? RenderShape.MODEL : RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (!(level.getBlockEntity(pos) instanceof PowerMachineBlockEntity machine))
        {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (kind == Kind.GENERATOR && PowerMachineBlockEntity.isFuel(stack))
        {
            if (!level.isClientSide)
            {
                ItemStack left = machine.getFuel().insertItem(0, stack.copy(), false);
                if (left.getCount() == stack.getCount())
                {
                    return ItemInteractionResult.CONSUME; // slot full of another fuel
                }
                player.setItemInHand(hand, player.getAbilities().instabuild ? stack : left);
                level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 0.3F, 1.4F);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        if (kind == Kind.REPULSION_GENERATOR && stack.is(ModItems.NEODYMIUM_MAGNET.get()))
        {
            if (machine.magnets() >= MAX_MAGNETS)
            {
                return ItemInteractionResult.CONSUME;
            }
            if (!level.isClientSide)
            {
                machine.setMagnets(machine.magnets() + 1);
                stack.consume(1, player);
                level.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.BLOCKS, 0.6F, 1.6F);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (!(level.getBlockEntity(pos) instanceof PowerMachineBlockEntity machine))
        {
            return InteractionResult.PASS;
        }
        if (level.isClientSide)
        {
            return InteractionResult.SUCCESS;
        }
        if (player.isShiftKeyDown())
        {
            if (kind.isTransformer())
            {
                level.setBlock(pos, state.setValue(FACING, state.getValue(FACING).getClockWise()), Block.UPDATE_ALL);
                level.invalidateCapabilities(pos);
                level.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS, 0.5F, 1.5F);
                return InteractionResult.SUCCESS;
            }
            if (kind == Kind.GENERATOR && !machine.getFuel().getStackInSlot(0).isEmpty())
            {
                player.getInventory().placeItemBackInInventory(machine.getFuel().extractItem(0, 64, false));
                return InteractionResult.SUCCESS;
            }
            if (kind == Kind.REPULSION_GENERATOR && machine.magnets() > 0)
            {
                machine.setMagnets(machine.magnets() - 1);
                player.getInventory().placeItemBackInInventory(new ItemStack(ModItems.NEODYMIUM_MAGNET.get()));
                return InteractionResult.SUCCESS;
            }
        }
        player.displayClientMessage(machine.statusMessage(), true);
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving)
    {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof PowerMachineBlockEntity machine)
        {
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), machine.getFuel().getStackInSlot(0));
            if (machine.magnets() > 0)
            {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.NEODYMIUM_MAGNET.get(), machine.magnets()));
            }
        }
        super.onRemove(state, level, pos, newState, moving);
    }

    // ---- redstone: the emitter gives a signal while powered; the power cell works with comparators

    @Override
    protected boolean isSignalSource(BlockState state)
    {
        return kind == Kind.REDSTONE_EMITTER;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        return kind == Kind.REDSTONE_EMITTER && state.getValue(LIT) ? 15 : 0;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state)
    {
        return kind == Kind.POWERCELL;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
    {
        return level.getBlockEntity(pos) instanceof PowerMachineBlockEntity machine ? machine.comparatorLevel() : 0;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new PowerMachineBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return level.isClientSide
                ? createTickerHelper(type, ModBlockEntities.POWER_MACHINE.get(), PowerMachineBlockEntity::clientTick)
                : createTickerHelper(type, ModBlockEntities.POWER_MACHINE.get(), PowerMachineBlockEntity::serverTick);
    }
}
