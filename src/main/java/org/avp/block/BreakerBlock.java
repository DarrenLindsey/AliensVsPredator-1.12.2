package org.avp.block;

import org.avp.power.PowerNetwork;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

/**
 * A circuit breaker: part of a powerline while closed. An overload trips it open (cutting the line
 * there) instead of burning the line out. Use it to reset or open it by hand; a redstone signal
 * holds it open.
 */
public class BreakerBlock extends Block
{
    public static final BooleanProperty CLOSED = BooleanProperty.create("closed");

    public BreakerBlock(Properties properties)
    {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(CLOSED, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(CLOSED);
    }

    public static void trip(Level level, BlockPos pos, BlockState state)
    {
        level.setBlock(pos, state.setValue(CLOSED, false), 3);
        level.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.BLOCKS, 1.0F, 1.6F);
        if (level instanceof ServerLevel server)
        {
            server.sendParticles(ParticleTypes.ELECTRIC_SPARK, pos.getX() + 0.5, pos.getY() + 0.7, pos.getZ() + 0.5, 12, 0.2, 0.2, 0.2, 0.1);
        }
        PowerNetwork.invalidate(level);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (!level.isClientSide)
        {
            boolean close = !state.getValue(CLOSED) && !level.hasNeighborSignal(pos);
            level.setBlock(pos, state.setValue(CLOSED, close), 3);
            level.playSound(null, pos, close ? SoundEvents.IRON_TRAPDOOR_OPEN : SoundEvents.IRON_TRAPDOOR_CLOSE, SoundSource.BLOCKS, 1.0F, 1.4F);
            player.displayClientMessage(Component.translatable(close ? "message.avp.breaker_closed" : "message.avp.breaker_open"), true);
            PowerNetwork.invalidate(level);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos from, boolean moving)
    {
        if (!level.isClientSide && level.hasNeighborSignal(pos) && state.getValue(CLOSED))
        {
            level.setBlock(pos, state.setValue(CLOSED, false), 3);
            PowerNetwork.invalidate(level);
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState old, boolean moving)
    {
        PowerNetwork.invalidate(level);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState next, boolean moving)
    {
        super.onRemove(state, level, pos, next, moving);
        PowerNetwork.invalidate(level);
    }
}
