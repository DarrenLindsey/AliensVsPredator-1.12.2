package org.avp.block;

import java.util.function.IntSupplier;

import org.avp.block.entity.PoweredBlockEntity;
import org.avp.config.AvpConfig;
import org.avp.entity.xenomorph.OvamorphEntity;
import org.avp.registry.ModBlocks;
import org.avp.registry.ModEntities;
import org.avp.registry.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Cryostasis tube with a specimen inside (roadmap: "stasis harvesting").
 *
 *  - Use a Contained Ovamorph on it: the egg goes in, frozen and visible through the glass.
 *  - While powered it stays dormant and slowly gives off royal jelly (right-click with an
 *    empty hand to collect it, or pull it out with a hopper).
 *  - Sneak + right-click with an empty hand: take the egg back out (contained again).
 *  - Lose power and it starts to thaw: the glass cracks, then shatters, and the egg gets
 *    out - and it's awake. A shattered tube is repaired with Industrial Glass.
 */
public class CryostasisTubeBlock extends PoweredBlock
{
    public CryostasisTubeBlock(Properties properties, IntSupplier energyPerTick)
    {
        super(properties, energyPerTick);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (!(level.getBlockEntity(pos) instanceof PoweredBlockEntity tube))
        {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (stack.is(ModItems.CONTAINED_OVAMORPH.get()))
        {
            if (state.getValue(DAMAGE) == 2)
            {
                message(level, player, "message.avp.cryotube.broken");
                return ItemInteractionResult.CONSUME;
            }
            if (!tube.contents().isEmpty())
            {
                message(level, player, "message.avp.cryotube.occupied");
                return ItemInteractionResult.CONSUME;
            }
            if (!level.isClientSide)
            {
                tube.setContents(stack.copyWithCount(1));
                stack.consume(1, player);
                player.getInventory().placeItemBackInInventory(new ItemStack(ModItems.SPECIMEN_CONTAINER.get()));
                level.playSound(null, pos, SoundEvents.GLASS_PLACE, SoundSource.BLOCKS, 1.0F, 0.6F);
                message(level, player, "message.avp.cryotube.stored");
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        if (stack.is(ModBlocks.INDUSTRIALGLASS.get().asItem()) && state.getValue(DAMAGE) > 0)
        {
            if (!level.isClientSide)
            {
                level.setBlock(pos, state.setValue(DAMAGE, 0), Block.UPDATE_ALL);
                stack.consume(1, player);
                level.playSound(null, pos, SoundEvents.GLASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit)
    {
        if (!(level.getBlockEntity(pos) instanceof PoweredBlockEntity tube))
        {
            return InteractionResult.PASS;
        }
        if (level.isClientSide)
        {
            return InteractionResult.SUCCESS;
        }
        if (player.isShiftKeyDown() && !tube.contents().isEmpty())
        {
            player.getInventory().placeItemBackInInventory(tube.contents().copy());
            tube.setContents(ItemStack.EMPTY);
            level.playSound(null, pos, SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS, 0.8F, 0.7F);
            return InteractionResult.SUCCESS;
        }
        if (tube.jelly() > 0)
        {
            player.getInventory().placeItemBackInInventory(new ItemStack(ModItems.ROYALJELLY.get(), tube.jelly()));
            tube.setJelly(0);
            level.playSound(null, pos, SoundEvents.HONEY_BLOCK_BREAK, SoundSource.BLOCKS, 0.8F, 1.2F);
            return InteractionResult.SUCCESS;
        }
        player.displayClientMessage(tube.contents().isEmpty() ? Component.translatable("message.avp.cryotube.empty")
                : Component.translatable(state.getValue(LIT) ? "message.avp.cryotube.frozen" : "message.avp.cryotube.thawing"), true);
        return InteractionResult.SUCCESS;
    }

    /** Freezing, harvesting and thawing (runs every server tick after the power check). */
    @Override
    public void poweredTick(Level level, BlockPos pos, BlockState state, PoweredBlockEntity tube)
    {
        if (tube.contents().isEmpty())
        {
            return;
        }
        if (state.getValue(LIT))
        {
            if (tube.thawTicks() > 0)
            {
                tube.setThawTicks(0);
            }
            if (tube.harvestTick(AvpConfig.server(AvpConfig.SERVER.cryotubeHarvestTicks)) && tube.jelly() < 64)
            {
                tube.setJelly(tube.jelly() + 1);
            }
            return;
        }
        // no power: thawing
        int thaw = AvpConfig.server(AvpConfig.SERVER.cryotubeThawTicks);
        tube.setThawTicks(tube.thawTicks() + 1);
        if (tube.thawTicks() == thaw / 2 && state.getValue(DAMAGE) == 0)
        {
            level.setBlock(pos, state.setValue(DAMAGE, 1), Block.UPDATE_ALL);
            level.playSound(null, pos, SoundEvents.GLASS_HIT, SoundSource.BLOCKS, 1.0F, 0.6F);
        }
        if (tube.thawTicks() >= thaw && level instanceof ServerLevel server)
        {
            level.setBlock(pos, state.setValue(DAMAGE, 2), Block.UPDATE_ALL);
            level.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0F, 0.8F);
            OvamorphEntity egg = ModEntities.OVAMORPH.get().create(server, null, pos, MobSpawnType.TRIGGERED, false, false);
            if (egg != null)
            {
                egg.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, server.random.nextFloat() * 360F, 0);
                egg.trigger();
                server.addFreshEntity(egg);
            }
            tube.setContents(ItemStack.EMPTY);
        }
    }

    private static void message(Level level, Player player, String key)
    {
        if (!level.isClientSide)
        {
            player.displayClientMessage(Component.translatable(key), true);
        }
    }
}
