package org.avp.item.gear;

import org.avp.entity.projectile.ThrownWeaponEntity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Shuriken and smart disc: use to throw (the disc comes back). */
public class ThrowableWeaponItem extends Item
{
    private final float damage, speed;
    private final boolean returns;

    public ThrowableWeaponItem(float damage, float speed, boolean returns, Properties properties)
    {
        super(properties);
        this.damage = damage;
        this.speed = speed;
        this.returns = returns;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        level.playSound(null, player.blockPosition(), SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 0.8F, returns ? 1.4F : 1.8F);
        if (!level.isClientSide)
        {
            ThrownWeaponEntity.throwFrom(player, stack, damage, speed, returns);
        }
        player.getCooldowns().addCooldown(this, returns ? 30 : 8);
        if (!player.getAbilities().instabuild)
        {
            stack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
