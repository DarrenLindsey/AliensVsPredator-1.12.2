package org.avp.client;

import org.avp.AliensVsPredator;
import org.avp.network.GearActionPayload;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/** Keys for gear: the M41A grenade launcher (G), Yautja vision mode (V), cloak (C), self-destruct (K). */
public final class GearKeys
{
    public static final String CATEGORY = "key.categories.avp";
    public static final KeyMapping GRENADE_LAUNCHER = new KeyMapping("key.avp.grenade_launcher", InputConstants.KEY_G, CATEGORY);
    public static final KeyMapping VISION_MODE = new KeyMapping("key.avp.vision_mode", InputConstants.KEY_V, CATEGORY);
    public static final KeyMapping CLOAK = new KeyMapping("key.avp.cloak", InputConstants.KEY_C, CATEGORY);
    public static final KeyMapping SELF_DESTRUCT = new KeyMapping("key.avp.self_destruct", InputConstants.KEY_K, CATEGORY);

    @EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static final class Registration
    {
        @SubscribeEvent
        public static void register(RegisterKeyMappingsEvent event)
        {
            event.register(GRENADE_LAUNCHER);
            event.register(VISION_MODE);
            event.register(CLOAK);
            event.register(SELF_DESTRUCT);
        }
    }

    @EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
    public static final class Ticks
    {
        @SubscribeEvent
        public static void tick(ClientTickEvent.Post event)
        {
            if (Minecraft.getInstance().player == null)
            {
                return;
            }
            while (GRENADE_LAUNCHER.consumeClick())
            {
                PacketDistributor.sendToServer(new GearActionPayload(GearActionPayload.GRENADE_LAUNCHER));
            }
            while (VISION_MODE.consumeClick())
            {
                PacketDistributor.sendToServer(new GearActionPayload(GearActionPayload.VISION_MODE));
            }
            while (CLOAK.consumeClick())
            {
                PacketDistributor.sendToServer(new GearActionPayload(GearActionPayload.CLOAK));
            }
            while (SELF_DESTRUCT.consumeClick())
            {
                PacketDistributor.sendToServer(new GearActionPayload(GearActionPayload.SELF_DESTRUCT));
            }
        }
    }

    private GearKeys()
    {
    }
}
