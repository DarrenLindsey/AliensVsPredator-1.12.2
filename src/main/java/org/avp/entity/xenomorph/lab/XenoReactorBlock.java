package org.avp.entity.xenomorph.lab;

import javax.annotation.Nullable;

import org.avp.registry.ModBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.phys.BlockHitResult;

/** The Xeno Bio-Reactor (see XenoReactorBlockEntity). Use eggs on it to fuel it; use it empty-handed for its status. */
public class XenoReactorBlock extends BaseEntityBlock
{
    public static final MapCodec<XenoReactorBlock> CODEC = simpleCodec(XenoReactorBlock::new);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public XenoReactorBlock(Properties properties)
    {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(LIT, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(LIT);
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
        return new XenoReactorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.XENO_REACTOR.get(), XenoReactorBlockEntity::serverTick);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
            BlockHitResult hit)
    {
        if (!(level.getBlockEntity(pos) instanceof XenoReactorBlockEntity be) || !be.fuel.isItemValid(0, stack))
        {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!level.isClientSide)
        {
            player.setItemInHand(hand, be.fuel.insertItem(0, stack.copy(), false));
            player.displayClientMessage(Component.literal(be.status()), true);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof XenoReactorBlockEntity be)
        {
            player.displayClientMessage(Component.literal(be.status()), true);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState old, boolean moving)
    {
        org.avp.power.PowerNetwork.invalidate(level);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState next, boolean moving)
    {
        if (!state.is(next.getBlock()) && level.getBlockEntity(pos) instanceof XenoReactorBlockEntity be)
        {
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), be.fuel.getStackInSlot(0));
        }
        super.onRemove(state, level, pos, next, moving);
        org.avp.power.PowerNetwork.invalidate(level);
    }
}
