package org.avp.item.gear;

import org.avp.entity.projectile.ThrownWeaponEntity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

/** The combi-stick: a strong melee weapon; hold use and let go to hurl it (like a trident). */
public class SpearItem extends SwordItem
{
    public SpearItem(Tier tier, Properties properties)
    {
        super(tier, properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity)
    {
        return 72_000;
    }

    @Override
    public net.minecraft.world.InteractionResultHolder<ItemStack> use(Level level, Player player, net.minecraft.world.InteractionHand hand)
    {
        player.startUsingItem(hand);
        return net.minecraft.world.InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int remaining)
    {
        if (getUseDuration(stack, user) - remaining < 10 || !(user instanceof Player player))
        {
            return; // not drawn back far enough
        }
        level.playSound(null, player.blockPosition(), SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0F, 0.8F);
        if (!level.isClientSide)
        {
            ThrownWeaponEntity.throwFrom(player, stack, 12F, 2.2F, false);
            if (!player.getAbilities().instabuild)
            {
                player.getInventory().removeItem(stack);
            }
        }
    }
}
