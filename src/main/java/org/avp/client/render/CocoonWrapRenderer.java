package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.registry.ModAttachments;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

/** Ribbed hive membrane over a captive's body, leaving the head exposed. */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
public final class CocoonWrapRenderer
{
    private static final ResourceLocation MEMBRANE =
            ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/ovamorph.png");
    private static final int SIDES = 12;
    private static final float[] RIBS = { 0.05F, 0.60F, 0.82F, 0.72F, 0.96F, 0.84F, 1.0F, 0.88F, 0.94F, 0.74F, 0.80F, 0.44F, 0.04F };

    @SubscribeEvent
    public static void render(RenderLivingEvent.Post<?, ?> event)
    {
        LivingEntity host = event.getEntity();
        if (!host.hasData(ModAttachments.COCOONED) || host.isInvisible())
        {
            return;
        }
        boolean animal = host instanceof Animal;
        PoseStack stack = event.getPoseStack();
        stack.pushPose();
        stack.mulPose(Axis.YP.rotationDegrees(-Mth.rotLerp(event.getPartialTick(), host.yBodyRotO, host.yBodyRot)));
        if (animal)
        {
            stack.translate(0, 0, -host.getBbWidth() * 0.31F);
        }
        stack.scale(host.getBbWidth() * (animal ? 0.57F : 0.77F), host.getBbHeight() * (animal ? 0.76F : 0.79F),
                host.getBbWidth() * (animal ? 0.87F : 0.56F));
        VertexConsumer out = event.getMultiBufferSource().getBuffer(RenderType.entityCutoutNoCull(MEMBRANE));
        PoseStack.Pose pose = stack.last();
        for (int ring = 0; ring < RIBS.length - 1; ring++)
        {
            for (int side = 0; side < SIDES; side++)
            {
                int tileSide = side % (SIDES / 3);
                vertex(out, pose, ring, side, tileSide, event.getPackedLight());
                vertex(out, pose, ring + 1, side, tileSide, event.getPackedLight());
                vertex(out, pose, ring + 1, side + 1, tileSide + 1, event.getPackedLight());
                vertex(out, pose, ring, side + 1, tileSide + 1, event.getPackedLight());
            }
        }
        stack.popPose();
    }

    private static void vertex(VertexConsumer out, PoseStack.Pose pose, int ring, int side, int tileSide, int light)
    {
        float angle = side * Mth.TWO_PI / SIDES;
        float x = Mth.cos(angle);
        float z = Mth.sin(angle);
        float t = ring / (float) (RIBS.length - 1);
        // A strip from the egg's ribbed olive membrane, inset from its transparent atlas edges.
        float u = (2F + tileSide * 7F) / 256F;
        float v = (46F - t * 17F) / 128F;
        out.addVertex(pose, x * RIBS[ring], 0.02F + t, z * RIBS[ring])
                .setColor(255, 236, 215, 255).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(light).setNormal(pose, x, t - 0.5F, z);
    }

    private CocoonWrapRenderer()
    {
    }
}
