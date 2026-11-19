package org.avp.block;

import java.util.List;
import java.util.function.IntSupplier;

import org.avp.block.entity.PoweredBlockEntity;
import org.avp.config.AvpConfig;
import org.avp.entity.SeatEntity;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

/**
 * The medpod.
 *
 * - Right-click to open/close the lid (needs power, like 1.12.2).
 * - When it's open, walk in to lie down in it (you ride a SeatEntity).
 * - Close the lid while you're inside: with power on it slowly heals you and can cure
 *   harmful effects (new - the 1.12.2 medpod had no healing). Later it will also remove
 *   Xenomorph embryos.
 * - Sneak to get out; the lid opens. After a full treatment (closed + powered, a few seconds)
 *   you also get a short golden-apple-style boost: Regeneration II and Absorption.
 */
public class MedpodBlock extends PoweredBlock implements SeatBlock
{
    public static final MapCodec<MedpodBlock> CODEC = simpleCodec(p -> new MedpodBlock(p, () -> 1));
    public static final BooleanProperty OPEN = BooleanProperty.create("open");

    public MedpodBlock(Properties properties, IntSupplier energyPerTick)
    {
        super(properties, energyPerTick);
        registerDefaultState(defaultBlockState().setValue(OPEN, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(OPEN);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (level.isClientSide)
        {
            return InteractionResult.SUCCESS;
        }
        if (!state.getValue(LIT))
        {
            player.displayClientMessage(Component.translatable("message.avp.medpod.no_power"), true);
            return InteractionResult.CONSUME;
        }
        level.setBlock(pos, state.setValue(OPEN, !state.getValue(OPEN)), Block.UPDATE_ALL);
        return InteractionResult.CONSUME;
    }

    @Override
    public float animationTarget(BlockState state)
    {
        return state.getValue(OPEN) ? 1 : 0;
    }

    @Override
    public void poweredTick(Level level, BlockPos pos, BlockState state, PoweredBlockEntity be)
    {
        SeatEntity seat = SeatEntity.find(level, pos);
        if (state.getValue(OPEN))
        {
            // walk in: anyone standing in the open pod (and not sneaking) lies down in it
            if (seat == null)
            {
                List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(pos),
                        p -> !p.isPassenger() && !p.isShiftKeyDown() && !p.isSpectator());
                if (!players.isEmpty())
                {
                    SeatEntity.seatNow(level, pos, players.get(0));
                }
            }
            return;
        }
        // closed with someone inside and power on: treat them
        if (seat != null && state.getValue(LIT) && level.getGameTime() % AvpConfig.server(AvpConfig.SERVER.medpodHealInterval) == 0)
        {
            for (Entity passenger : seat.getPassengers())
            {
                if (passenger instanceof net.minecraft.world.entity.LivingEntity living)
                {
                    living.heal(AvpConfig.server(AvpConfig.SERVER.medpodHealAmount));
                    if (AvpConfig.server(AvpConfig.SERVER.medpodCuresEffects))
                    {
                        // remove harmful effects only (poison, wither, slowness...)
                        living.getActiveEffects().stream()
                                .map(MobEffectInstance::getEffect)
                                .filter(effect -> !effect.value().isBeneficial())
                                .toList()
                                .forEach(living::removeEffect);
                    }
                }
            }
        }
    }

    // ---- being inside (SeatBlock) ----

    @Override
    public void onSeatLeft(Level level, BlockPos pos, Entity passenger, int ticksInside)
    {
        BlockState state = level.getBlockState(pos);
        if (!state.is(this) || state.getValue(OPEN))
        {
            return;
        }
        // getting out of a closed, powered pod after a full treatment: a short "golden apple" boost
        if (state.getValue(LIT) && passenger instanceof net.minecraft.world.entity.LivingEntity living
                && AvpConfig.server(AvpConfig.SERVER.medpodBoost)
                && ticksInside >= AvpConfig.server(AvpConfig.SERVER.medpodBoostMinTicks))
        {
            living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, AvpConfig.server(AvpConfig.SERVER.medpodRegenerationTicks), 1));
            living.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, AvpConfig.server(AvpConfig.SERVER.medpodAbsorptionTicks), 0));
        }
        level.setBlock(pos, state.setValue(OPEN, true), Block.UPDATE_ALL); // open up to let them out
    }

    @Override
    public BlockPos exitPos(Level level, BlockPos pos)
    {
        return pos.relative(level.getBlockState(pos).getValue(FACING));
    }
}
