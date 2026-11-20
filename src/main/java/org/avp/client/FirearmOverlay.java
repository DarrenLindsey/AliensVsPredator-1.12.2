package org.avp.client;

import org.avp.AliensVsPredator;
import org.avp.item.firearm.FirearmItem;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

/** The ammo counter (red digits, like the M41A's own display) next to the hotbar while holding a gun. */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class FirearmOverlay
{
    @SubscribeEvent
    public static void register(RegisterGuiLayersEvent event)
    {
        event.registerAbove(VanillaGuiLayers.HOTBAR, ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "ammo"), (graphics, delta) ->
        {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.options.hideGui)
            {
                return;
            }
            ItemStack stack = mc.player.getMainHandItem();
            if (!(stack.getItem() instanceof FirearmItem gun))
            {
                return;
            }
            int ammo = FirearmItem.ammo(stack);
            int max = gun.firearm().magazine();
            float reload = mc.player.getCooldowns().getCooldownPercent(gun, delta.getGameTimeDeltaPartialTick(true));
            Component text = reload > 0 ? Component.translatable("hud.avp.reloading", Math.round((1 - reload) * 100))
                    : Component.literal(String.format(max >= 100 ? "%03d" : "%02d", ammo) + " / " + max);
            int x = graphics.guiWidth() / 2 + 98;
            int y = graphics.guiHeight() - 19;
            graphics.fill(x - 2, y - 2, x + mc.font.width(text) + 2, y + 9, 0xA0000000);
            graphics.drawString(mc.font, text, x, y, reload > 0 ? 0xFFFFAA00 : ammo == 0 ? 0xFF880000 : 0xFFFF2020, false);
        });
    }

    private FirearmOverlay()
    {
    }
}
