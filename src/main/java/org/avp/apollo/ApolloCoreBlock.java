package org.avp.apollo;

import javax.annotation.Nullable;

import org.avp.registry.ModBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
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

/** The APOLLO core (see ApolloCoreBlockEntity). Breaking it spills its manifest (up to a point). */
public class ApolloCoreBlock extends BaseEntityBlock
{
    public static final MapCodec<ApolloCoreBlock> CODEC = simpleCodec(ApolloCoreBlock::new);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public ApolloCoreBlock(Properties properties)
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
        return new ApolloCoreBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.APOLLO_CORE.get(), ApolloCoreBlockEntity::serverTick);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if (placer instanceof Player player && level.getBlockEntity(pos) instanceof ApolloCoreBlockEntity core)
        {
            core.setOwner(player.getUUID());
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof ApolloCoreBlockEntity core)
        {
            player.displayClientMessage(Component.literal("APOLLO: " + (core.online() ? "ONLINE" : "NO POWER") + " | " + String.format("%,d", core.stored()) + " / "
                    + String.format("%,d", core.capacity()) + " items, " + core.manifest().size() + "/" + core.typeLimit() + " types, " + core.banks()
                    + " data banks | use a Seegson Terminal").withStyle(net.minecraft.ChatFormatting.AQUA), true);
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
        if (!state.is(next.getBlock()) && level.getBlockEntity(pos) instanceof ApolloCoreBlockEntity core)
        {
            // spill up to 54 stacks; the rest is lost with the core
            int stacks = 0;
            for (ApolloCoreBlockEntity.Entry e : core.manifest())
            {
                while (e.count > 0 && stacks < 54)
                {
                    int n = (int) Math.min(e.count, e.proto.getMaxStackSize());
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), e.proto.copyWithCount(n));
                    e.count -= n;
                    stacks++;
                }
            }
        }
        super.onRemove(state, level, pos, next, moving);
        org.avp.power.PowerNetwork.invalidate(level);
    }
}
