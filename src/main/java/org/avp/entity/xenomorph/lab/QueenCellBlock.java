package org.avp.entity.xenomorph.lab;

import javax.annotation.Nullable;

import org.avp.registry.ModBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/** The Queen Containment Cell (see QueenCellBlockEntity). Use it: status, or capture a weakened Queen nearby. */
public class QueenCellBlock extends BaseEntityBlock
{
    public static final MapCodec<QueenCellBlock> CODEC = simpleCodec(QueenCellBlock::new);

    public QueenCellBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
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
        return new QueenCellBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.QUEEN_CELL.get(), QueenCellBlockEntity::serverTick);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if (placer instanceof Player p && level.getBlockEntity(pos) instanceof QueenCellBlockEntity be)
        {
            be.setOwner(p.getUUID());
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (!level.isClientSide && level.getBlockEntity(pos) instanceof QueenCellBlockEntity cell)
        {
            if (!cell.occupied() && cell.capture(player))
            {
                return InteractionResult.CONSUME;
            }
            player.displayClientMessage(Component.literal(cell.status()).withStyle(ChatFormatting.DARK_GREEN), true);
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
        if (!state.is(next.getBlock()) && level.getBlockEntity(pos) instanceof QueenCellBlockEntity cell)
        {
            for (int i = 0; i < cell.output.getSlots(); i++)
            {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), cell.output.getStackInSlot(i));
            }
            if (level instanceof net.minecraft.server.level.ServerLevel server)
            {
                // breaking the cell frees whatever was in it
                server.getEntitiesOfClass(org.avp.entity.xenomorph.AdultXenomorphEntity.class, new net.minecraft.world.phys.AABB(pos).inflate(3))
                        .forEach(q -> q.setNoAi(false));
            }
        }
        super.onRemove(state, level, pos, next, moving);
        org.avp.power.PowerNetwork.invalidate(level);
    }
}
