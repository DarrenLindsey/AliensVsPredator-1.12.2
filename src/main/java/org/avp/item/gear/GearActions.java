package org.avp.item.gear;

import org.avp.entity.projectile.GrenadeEntity;
import org.avp.item.firearm.FirearmItem;
import org.avp.item.firearm.Firearms;
import org.avp.network.GearActionPayload;
import org.avp.registry.ModGear;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;

/** What the gear keys do (server side). */
public final class GearActions
{
    public static void handle(ServerPlayer player, int action)
    {
        switch (action)
        {
            case GearActionPayload.GRENADE_LAUNCHER -> grenadeLauncher(player);
            default -> YautjaGearActions.handle(player, action);
        }
    }

    /** The M41A's underslung pump-action grenade launcher: fires an M40 grenade from your inventory. */
    private static void grenadeLauncher(ServerPlayer player)
    {
        ItemStack gun = player.getMainHandItem();
        if (!(gun.getItem() instanceof FirearmItem item) || item.firearm() != Firearms.M41A
                || player.getCooldowns().isOnCooldown(ModGear.GRENADE_M40.get()))
        {
            return;
        }
        ItemStack grenade = ItemStack.EMPTY;
        for (ItemStack s : player.getInventory().items)
        {
            if (s.getItem() instanceof GrenadeItem)
            {
                grenade = s;
                break;
            }
        }
        if (grenade.isEmpty() && !player.getAbilities().instabuild)
        {
            player.displayClientMessage(Component.translatable("message.avp.no_grenades"), true);
            return;
        }
        boolean incendiary = !grenade.isEmpty() && ((GrenadeItem) grenade.getItem()).incendiary();
        GrenadeEntity.throwFrom(player, incendiary, 2.4F, 60);
        if (!player.getAbilities().instabuild)
        {
            grenade.shrink(1);
        }
        player.getCooldowns().addCooldown(ModGear.GRENADE_M40.get(), 30);
        player.level().playSound(null, player.blockPosition(), SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.PLAYERS, 1.0F, 0.6F);
    }

    private GearActions()
    {
    }
}
