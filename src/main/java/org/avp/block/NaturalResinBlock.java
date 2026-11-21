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
import net.minecraft.util.RandomSource;
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
 * Fresh resin shows the block it covers. It can be scraped off before it hardens into solid Hive Resin.
 * Scraping it off brings the hive down on you.
 */
public class NaturalResinBlock extends BaseEntityBlock
{
    public static final MapCodec<NaturalResinBlock> CODEC = simpleCodec(NaturalResinBlock::new);
    private static final int MIN_CURE_AGE = 2 * 60 * 20;
    private static final int CURE_CHANCE = 8; // One in eight random ticks after the minimum age.

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
        if (state.isAir() || state.is(ModBlocks.NATURAL_RESIN.get()) || state.is(ModBlocks.HIVERESIN_STANDARD.get())
                || state.hasBlockEntity() || !state.getFluidState().isEmpty()
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
        if (!level.setBlock(pos, ModBlocks.NATURAL_RESIN.get().defaultBlockState(), Block.UPDATE_ALL))
        {
            return false;
        }
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

    /**
     * Resin creep: in a living hive, resin slowly spreads onto the blocks next to it (only within
     * the hive's reach, only onto bare surfaces), so even a huge cave ends up coated in time.
     */
    @Override
    protected boolean isRandomlyTicking(BlockState state)
    {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random)
    {
        if (!AvpConfig.server(AvpConfig.SERVER.hives))
        {
            return;
        }
        if (random.nextInt(CURE_CHANCE) == 0 && cure(level, pos))
        {
            return;
        }

        double rate = AvpConfig.server(AvpConfig.SERVER.resinCreepRate);
        if (rate <= 0 || random.nextDouble() >= rate * 0.25)
        {
            return;
        }
        HiveData data = HiveData.get(level);
        long now = level.getGameTime();
        data.containing(pos, now).filter(h -> h.state() == org.avp.world.hive.Hive.State.ACTIVE && now - h.lastActive() < 6000).ifPresent(hive ->
        {
            BlockPos next = pos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
            if (next.distSqr(hive.core()) <= sq(hive.buildRadius(now, HiveData.maxRadius()) + 4) && exposed(level, next) && cover(level, next))
            {
                hive.addResin(1);
                data.changed();
            }
        });
    }

    private static boolean cure(ServerLevel level, BlockPos pos)
    {
        if (!(level.getBlockEntity(pos) instanceof ResinBlockEntity resin)
                || !resin.hasBeenCoveredFor(level.getGameTime(), MIN_CURE_AGE))
        {
            return false;
        }
        BlockState under = resin.covered();
        Block solidResin = ModBlocks.HIVERESIN_STANDARD.get();
        // Coating intentionally placed over existing Hive Stone remains a separate layer.
        if (under == null || under.is(solidResin) || under.is(ModBlocks.HIVESTONE.get())
                || under.is(ModTags.XENO_UNBREAKABLE) || !under.is(ModTags.RESIN_COVERABLE))
        {
            return false;
        }
        return level.setBlock(pos, solidResin.defaultBlockState(), Block.UPDATE_ALL);
    }

    private static double sq(double d)
    {
        return d * d;
    }

    private static boolean exposed(Level level, BlockPos pos)
    {
        for (Direction d : Direction.values())
        {
            if (level.getBlockState(pos.relative(d)).isAir())
            {
                return true;
            }
        }
        return false;
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
