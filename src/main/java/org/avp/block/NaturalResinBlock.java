package org.avp.block;

import javax.annotation.Nullable;

import org.avp.block.entity.ResinBlockEntity;
import org.avp.config.AvpConfig;
import org.avp.registry.ModBlocks;
import org.avp.registry.ModTags;
import org.avp.world.hive.HiveData;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

/**
 * Hive resin the Drones spread over the walls, floor and ceiling of a hive (1.12.2 naturalResin).
 * The block it covers shows through and comes back when the resin is scraped off; resin burns.
 * Scraping it off brings the hive down on you.
 */
public class NaturalResinBlock extends BaseEntityBlock
{
    public static final MapCodec<NaturalResinBlock> CODEC = simpleCodec(NaturalResinBlock::new);

    public NaturalResinBlock(Properties properties)
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
        return new ResinBlockEntity(pos, state);
    }

    /** Can a hive coat this block? (natural stone, dirt, deepslate... - or any plain solid block with the config option) */
    public static boolean canCover(Level level, BlockPos pos, BlockState state)
    {
        if (state.isAir() || state.is(ModBlocks.NATURAL_RESIN.get()) || state.hasBlockEntity() || !state.getFluidState().isEmpty()
                || state.is(ModTags.XENO_UNBREAKABLE) || !state.isCollisionShapeFullBlock(level, pos) || !state.canOcclude())
        {
            return false;
        }
        if (state.is(ModTags.RESIN_COVERABLE))
        {
            return true;
        }
        float hardness = state.getDestroySpeed(level, pos);
        return AvpConfig.server(AvpConfig.SERVER.resinCoversBuiltBlocks) && hardness >= 0
                && hardness <= AvpConfig.server(AvpConfig.SERVER.xenoDigMaxHardness);
    }

    /** Coats a block in resin. */
    public static boolean cover(Level level, BlockPos pos)
    {
        BlockState under = level.getBlockState(pos);
        if (!canCover(level, pos, under))
        {
            return false;
        }
        level.setBlock(pos, ModBlocks.NATURAL_RESIN.get().defaultBlockState(), Block.UPDATE_ALL);
        if (level.getBlockEntity(pos) instanceof ResinBlockEntity resin)
        {
            resin.setCovered(under);
        }
        return true;
    }

    /** Scraping the resin off leaves the block that was underneath (and angers the hive). */
    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid)
    {
        BlockState under = level.getBlockEntity(pos) instanceof ResinBlockEntity resin ? resin.covered() : null;
        playerWillDestroy(level, pos, state, player);
        if (level instanceof ServerLevel server)
        {
            HiveData.alert(server, pos, player);
        }
        return level.setBlock(pos, under != null ? under : fluid.createLegacyBlock(), level.isClientSide ? Block.UPDATE_ALL_IMMEDIATE : Block.UPDATE_ALL);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        return 30;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
    {
        return 15;
    }
}
