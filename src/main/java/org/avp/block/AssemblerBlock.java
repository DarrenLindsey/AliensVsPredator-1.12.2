package org.avp.block;

import javax.annotation.Nullable;

import org.avp.block.entity.AssemblerBlockEntity;
import org.avp.registry.ModBlockEntities;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * The Assembler: right-click to open the list of schematics and build parts, machines and
 * gear straight from the materials in your inventory. It runs on FE (see the settings).
 */
public class AssemblerBlock extends BaseEntityBlock
{
    public static final MapCodec<AssemblerBlock> CODEC = simpleCodec(AssemblerBlock::new);

    public AssemblerBlock(Properties properties)
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
        return RenderShape.MODEL; // a normal cube; the hologram on top is drawn by AssemblerRenderer
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (level.isClientSide)
        {
            org.avp.client.ClientHooks.openAssembler(pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new AssemblerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return level.isClientSide ? null : createTickerHelper(type, ModBlockEntities.ASSEMBLER.get(), AssemblerBlockEntity::serverTick);
    }
}
