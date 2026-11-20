package org.avp.block.conveyor;

import javax.annotation.Nullable;

import org.avp.config.AvpConfig;
import org.avp.registry.ModBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

/**
 * A conveyor belt: carries items, mobs and players along; at the end it feeds items into a container.
 * Kinds: NORMAL and EXPRESS carry; SORTING sends its filter item left; LOGIC runs a BeltScript for each
 * item; PROCESSING smelts / blasts / crushes / washes items in passing; CRAFTING collects a 3x3 recipe's
 * ingredients and crafts; STORAGE keeps what arrives (redstone releases it); EXTRACTOR pulls items out
 * of the container behind it. A redstone signal stops a belt (except STORAGE, where it means release).
 */
public class ConveyorBlock extends BaseEntityBlock
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 4, 16);

    public enum Kind
    {
        NORMAL(1.0), EXPRESS(2.2), SORTING(1.0), LOGIC(1.0), PROCESSING(1.0), CRAFTING(1.0), STORAGE(1.0), EXTRACTOR(1.0);

        final double speed;

        Kind(double speed)
        {
            this.speed = speed;
        }

        boolean handlesItems()
        {
            return this == SORTING || this == LOGIC || this == CRAFTING || this == STORAGE;
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

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return level.isClientSide || kind != Kind.STORAGE && kind != Kind.EXTRACTOR ? null
                : createTickerHelper(type, ModBlockEntities.CONVEYOR.get(), ConveyorBlockEntity::serverTick);
    }

    // ------------------------------------------------------------------ redstone

    @Override
    protected boolean isSignalSource(BlockState state)
    {
        return kind == Kind.LOGIC;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction dir)
    {
        return kind == Kind.LOGIC && level.getBlockEntity(pos) instanceof ConveyorBlockEntity be ? be.signal() : 0;
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

    // ------------------------------------------------------------------ using it

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
            BlockHitResult hit)
    {
        if (kind != Kind.SORTING && kind != Kind.EXTRACTOR || !(level.getBlockEntity(pos) instanceof ConveyorBlockEntity be))
        {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!level.isClientSide)
        {
            be.setFilter(stack);
            player.displayClientMessage(Component.translatable(kind == Kind.SORTING ? "message.avp.conveyor_filter" : "message.avp.extractor_filter",
                    stack.getHoverName()), true);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (level.isClientSide || !(level.getBlockEntity(pos) instanceof ConveyorBlockEntity be))
        {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        switch (kind)
        {
            case SORTING, EXTRACTOR ->
            {
                if (player.isShiftKeyDown())
                {
                    be.setFilter(ItemStack.EMPTY);
                }
                player.displayClientMessage(be.filter().isEmpty() ? Component.translatable("message.avp.conveyor_no_filter")
                        : Component.translatable(kind == Kind.SORTING ? "message.avp.conveyor_filter" : "message.avp.extractor_filter", be.filter().getHoverName()), true);
            }
            case PROCESSING ->
            {
                be.cycleProcess();
                player.displayClientMessage(Component.literal("Processing belt: " + be.process()).withStyle(ChatFormatting.GOLD), true);
            }
            case LOGIC ->
            {
                if (player instanceof ServerPlayer sp)
                {
                    ConveyorPayloads.open(sp, be);
                }
            }
            case CRAFTING ->
            {
                player.openMenu(new net.minecraft.world.SimpleMenuProvider((id, inv, p) -> new CraftingBeltMenu(id, inv, be),
                        Component.translatable("block.avp.conveyor_crafting")), buf -> buf.writeBlockPos(pos));
            }
            case STORAGE ->
            {
                int total = 0, kinds = 0;
                for (int i = 0; i < be.buffer.getSlots(); i++)
                {
                    if (!be.buffer.getStackInSlot(i).isEmpty())
                    {
                        total += be.buffer.getStackInSlot(i).getCount();
                        kinds++;
                    }
                }
                player.displayClientMessage(Component.literal("Storage belt: " + total + " items in " + kinds + "/27 slots"
                        + (state.getValue(POWERED) ? " - RELEASING" : " - storing (redstone to release)")).withStyle(ChatFormatting.GOLD), true);
            }
            default ->
            {
            }
        }
        return InteractionResult.CONSUME;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState next, boolean moving)
    {
        if (!state.is(next.getBlock()) && level.getBlockEntity(pos) instanceof ConveyorBlockEntity be)
        {
            for (int i = 0; i < be.buffer.getSlots(); i++)
            {
                ItemStack s = be.buffer.getStackInSlot(i);
                while (!s.isEmpty())
                {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), s.split(64));
                }
            }
        }
        super.onRemove(state, level, pos, next, moving);
    }

    // ------------------------------------------------------------------ moving things

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity)
    {
        boolean stopped = state.getValue(POWERED) && kind != Kind.STORAGE;
        if (stopped || entity.getY() > pos.getY() + 0.5 || entity.isShiftKeyDown() || entity instanceof Player p && p.getAbilities().flying)
        {
            return;
        }
        Direction facing = state.getValue(FACING);
        Direction dir = facing;
        Vec3 centre = Vec3.atCenterOf(pos);
        if (entity instanceof ItemEntity item && level.getBlockEntity(pos) instanceof ConveyorBlockEntity be)
        {
            var data = item.getPersistentData();
            boolean handledHere = data.getLong("avpBeltAt") == pos.asLong() && level.getGameTime() - data.getLong("avpBeltTime") < 200;
            double off = Math.abs(item.getX() - centre.x) + Math.abs(item.getZ() - centre.z);
            if (!level.isClientSide && kind == Kind.PROCESSING && off < 0.3 && be.process(item))
            {
                item.setPos(centre.x, item.getY(), centre.z);
                item.setDeltaMovement(0, item.getDeltaMovement().y, 0);
                return;
            }
            if (!level.isClientSide && kind.handlesItems() && !handledHere && off < 0.3)
            {
                Direction to = be.onItem(item, facing);
                data.putLong("avpBeltAt", pos.asLong());
                data.putLong("avpBeltTime", level.getGameTime());
                data.putInt("avpBeltDir", to == null ? -1 : to.get3DDataValue());
                if (to == null || !item.isAlive())
                {
                    return;
                }
                handledHere = true;
            }
            if (handledHere && data.getInt("avpBeltDir") >= 2)
            {
                dir = Direction.from3DDataValue(data.getInt("avpBeltDir"));
            }
        }
        double speed = 0.08 * kind.speed * AvpConfig.server(AvpConfig.SERVER.conveyorSpeed);
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
