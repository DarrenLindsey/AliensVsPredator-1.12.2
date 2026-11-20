package org.avp.item;

import org.avp.AliensVsPredator;
import org.avp.config.AvpConfig;
import org.avp.registry.ModBlocks;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * A Yautja biomask worn on the head: the wearer sees in the dark (the biomask's vision modes).
 * Topped up quietly - no potion swirls and no flicker when it would run out.
 */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class BiomaskEvents
{
    public static boolean isBiomask(ItemStack stack)
    {
        return stack.is(ModBlocks.BIOMASK_CLASSIC.asItem()) || stack.is(ModBlocks.BIOMASK_BERSERKER.asItem())
                || stack.is(ModBlocks.BIOMASK_FALCONER.asItem()) || stack.is(ModBlocks.BIOMASK_TRACKER.asItem())
                || stack.is(org.avp.registry.ModArmor.SETS.get("celtic").get(0).get()); // the Celtic helmet is a biomask
    }

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event)
    {
        Player player = event.getEntity();
        if (player.level().isClientSide || player.tickCount % 20 != 0)
        {
            return;
        }
        MobEffectInstance current = player.getEffect(MobEffects.NIGHT_VISION);
        if (!AvpConfig.server(AvpConfig.SERVER.biomaskNightVision) || !isBiomask(player.getItemBySlot(EquipmentSlot.HEAD)))
        {
            // mask off: the mask's vision goes with it (a night vision potion's doesn't - it shows its icon)
            if (current != null && current.isAmbient() && !current.showIcon())
            {
                player.removeEffect(MobEffects.NIGHT_VISION);
            }
            return;
        }
        if (current == null || current.getDuration() < 300)
        {
            // over 200 ticks so it never starts to flash; ambient, no particles, no icon
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, true, false, false));
        }
    }

    private BiomaskEvents()
    {
    }
}
