package org.avp.item.gear;

import org.avp.entity.projectile.GrenadeEntity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** M40 grenades (1.12.2 ItemGrenade): throw them, or load them in the M41A's launcher. */
public class GrenadeItem extends Item
{
    private final boolean incendiary;

    public GrenadeItem(boolean incendiary, Properties properties)
    {
        super(properties.stacksTo(16));
        this.incendiary = incendiary;
    }

    public boolean incendiary()
    {
        return incendiary;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        level.playSound(null, player.blockPosition(), SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.6F, 0.6F);
        if (!level.isClientSide)
        {
            GrenadeEntity.throwFrom(player, incendiary, 1.1F, 50);
        }
        player.getCooldowns().addCooldown(this, 20);
        stack.consume(1, player);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
