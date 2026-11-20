package org.avp.item.gear;

import java.util.List;

import org.avp.entity.creature.CreatureProjectile;
import org.avp.item.ModelledItem;
import org.avp.registry.ModGear;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

/** The Yautja plasma caster (1.12.2 ItemPlasmaCannon): hold use to charge, let go to fire a plasma bolt - stronger the longer it charged. */
public class PlasmaCasterItem extends Item implements ModelledItem
{
    private static final int FULL_CHARGE = 40;

    public PlasmaCasterItem(Properties properties)
    {
        super(properties.stacksTo(1));
    }

    @Override
    public String model()
    {
        return "PlasmaCannonModel";
    }

    @Override
    public String texture()
    {
        return "plasmacannon.png";
    }

    @Override
    public boolean twoHanded()
    {
        return false;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity)
    {
        return 72_000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {
        return UseAnim.NONE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        level.playSound(null, player.blockPosition(), ModGear.PLASMA_CHARGE.get(), SoundSource.PLAYERS, 0.8F, 1.0F);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int remaining)
    {
        float charge = Math.min(FULL_CHARGE, getUseDuration(stack, user) - remaining) / (float) FULL_CHARGE;
        if (charge < 0.15F)
        {
            return;
        }
        level.playSound(null, user.blockPosition(), ModGear.PLASMA_SHOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        if (!level.isClientSide)
        {
            CreatureProjectile.shootAlong(user, user.getViewVector(1F), CreatureProjectile.Kind.PLASMA, 6F + 12F * charge, 2.0F + charge);
        }
        if (user instanceof Player player)
        {
            player.getCooldowns().addCooldown(this, 30);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        tooltip.add(Component.translatable("tooltip.avp.plasmacaster").withStyle(ChatFormatting.GRAY));
    }
}
