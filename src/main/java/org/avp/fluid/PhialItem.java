package org.avp.fluid;

import org.avp.config.AvpConfig;
import org.avp.registry.ModBlocks;
import org.avp.registry.ModFluids;
import org.avp.registry.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * Phials: an empty one is filled from black goo (a pool of it, or an Engineer ampule - which may
 * crack and leak); a full one is poured out as black goo.
 */
public class PhialItem extends Item
{
    private final boolean full;

    public PhialItem(boolean full, Properties properties)
    {
        super(properties.stacksTo(full ? 1 : 16));
        this.full = full;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        if (full)
        {
            BlockPos at = level.getBlockState(pos).canBeReplaced() ? pos : pos.relative(context.getClickedFace());
            if (!level.getBlockState(at).canBeReplaced())
            {
                return InteractionResult.FAIL;
            }
            if (!level.isClientSide)
            {
                level.setBlock(at, ModFluids.BLACK_GOO.get().defaultFluidState().createLegacyBlock(), 11);
                level.playSound(null, at, SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS, 1.0F, 0.6F);
                if (player != null && !player.getAbilities().instabuild)
                {
                    player.setItemInHand(context.getHand(), new ItemStack(ModItems.PHIAL_EMPTY.get()));
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (player != null && level.getBlockState(pos).is(ModBlocks.ENGINEERSHIP_AMPULE.get()))
        {
            if (!level.isClientSide)
            {
                fill(level, player, context.getHand(), pos);
                if (level.random.nextDouble() < AvpConfig.server(AvpConfig.SERVER.ampuleLeakChance))
                {
                    // it cracks: black goo seeps out
                    BlockPos leak = pos.relative(context.getClickedFace());
                    if (level.getBlockState(leak).canBeReplaced())
                    {
                        level.setBlock(leak, ModFluids.BLACK_GOO.get().defaultFluidState().createLegacyBlock(), 11);
                    }
                    level.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 0.7F, 0.6F);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (full)
        {
            return InteractionResultHolder.pass(stack);
        }
        BlockHitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (hit.getType() == HitResult.Type.BLOCK && level.getFluidState(hit.getBlockPos()).is(ModFluids.BLACK_GOO.get()))
        {
            if (!level.isClientSide)
            {
                fill(level, player, hand, hit.getBlockPos());
            }
            return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide);
        }
        return InteractionResultHolder.pass(stack);
    }

    private static void fill(Level level, Player player, InteractionHand hand, BlockPos at)
    {
        level.playSound(null, at, SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0F, 0.6F);
        player.setItemInHand(hand, ItemUtils.createFilledResult(player.getItemInHand(hand), player, new ItemStack(ModItems.PHIAL_GOO.get())));
    }
}
