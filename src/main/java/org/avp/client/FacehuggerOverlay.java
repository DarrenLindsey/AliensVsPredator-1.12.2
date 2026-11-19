package org.avp.client;

import org.avp.AliensVsPredator;
import org.avp.entity.parasite.FacehuggerEntity;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

/** 1.12.2 FacehuggerRenderEvent: with a facehugger on your face, you see its underside. */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class FacehuggerOverlay
{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/misc/facehugger_overlay.png");

    @SubscribeEvent
    public static void register(RegisterGuiLayersEvent event)
    {
        event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "facehugger"), (graphics, delta) ->
        {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || !mc.options.getCameraType().isFirstPerson()
                    || !FacehuggerEntity.hasFacehugger(mc.player))
            {
                return;
            }
            RenderSystem.enableBlend();
            graphics.blit(TEXTURE, 0, 0, 0, 0, graphics.guiWidth(), graphics.guiHeight(), graphics.guiWidth(), graphics.guiHeight());
            RenderSystem.disableBlend();
        });
    }

    private FacehuggerOverlay()
    {
    }
}
