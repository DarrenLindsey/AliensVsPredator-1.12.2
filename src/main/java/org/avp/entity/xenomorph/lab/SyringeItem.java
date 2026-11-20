package org.avp.entity.xenomorph.lab;

import org.avp.registry.ModTags;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** A bio-sampling syringe: use it on any Xenomorph (careful: it hurts it, and it notices) for a DNA sample. */
public class SyringeItem extends Item
{
    public SyringeItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand)
    {
        if (!target.getType().is(ModTags.XENOMORPHS))
        {
            return InteractionResult.PASS;
        }
        if (!player.level().isClientSide)
        {
            target.hurt(player.damageSources().playerAttack(player), 1.0F);
            ItemStack sample = XenoLab.sample(target.getType());
            stack.consume(1, player);
            if (!player.getInventory().add(sample))
            {
                player.drop(sample, false);
            }
            player.level().playSound(null, target.blockPosition(), SoundEvents.BOTTLE_FILL, target.getSoundSource(), 0.8F, 0.7F);
        }
        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }
}
