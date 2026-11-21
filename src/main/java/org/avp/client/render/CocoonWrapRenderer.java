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
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Cow;
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
    private static final float[] TORSO_RIBS = { 0.0F, 0.88F, 1.0F, 0.94F, 1.0F, 0.94F, 1.0F, 0.94F, 1.0F, 0.94F, 1.0F, 0.88F, 0.0F };

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
        fitWrap(stack, host, animal);
        float[] profile = animal ? TORSO_RIBS : RIBS;
        VertexConsumer out = event.getMultiBufferSource().getBuffer(RenderType.entityCutoutNoCull(MEMBRANE));
        PoseStack.Pose pose = stack.last();
        for (int ring = 0; ring < profile.length - 1; ring++)
        {
            for (int side = 0; side < SIDES; side++)
            {
                int tileSide = side % (SIDES / 3);
                vertex(out, pose, profile, animal, ring, side, tileSide, event.getPackedLight());
                vertex(out, pose, profile, animal, ring + 1, side, tileSide, event.getPackedLight());
                vertex(out, pose, profile, animal, ring + 1, side + 1, tileSide + 1, event.getPackedLight());
                vertex(out, pose, profile, animal, ring, side + 1, tileSide + 1, event.getPackedLight());
            }
        }
        stack.popPose();
    }

    private static void fitWrap(PoseStack stack, LivingEntity host, boolean animal)
    {
        float width = host.getBbWidth();
        float height = host.getBbHeight();
        if (host instanceof Bee)
        {
            // Bees have a low, long abdomen and their eyes are part of the same vanilla model cube.
            stack.translate(0, height * 0.57F, width * 0.44F);
            stack.scale(width * 0.44F, height * 0.48F, width * 1.10F);
        }
        else if (host instanceof Cow)
        {
            // The cow's torso sits above its long legs, and its muzzle projects beyond the front collar.
            stack.translate(0, height * 0.75F, width * 0.72F);
            stack.scale(width * 0.62F, height * 0.34F, width * 1.61F);
        }
        else if (animal)
        {
            stack.translate(0, height * 0.55F, width * 0.47F);
            stack.scale(width * 0.60F, height * 0.40F, width * 1.40F);
        }
        else
        {
            stack.scale(width * 0.77F, height * 0.79F, width * 0.56F);
        }
    }

    private static void vertex(VertexConsumer out, PoseStack.Pose pose, float[] profile, boolean animal,
            int ring, int side, int tileSide, int light)
    {
        float angle = side * Mth.TWO_PI / SIDES;
        float x = Mth.cos(angle);
        float z = Mth.sin(angle);
        float t = ring / (float) (profile.length - 1);
        // A strip from the egg's ribbed olive membrane, inset from its transparent atlas edges.
        float u = (2F + tileSide * 7F) / 256F;
        float v = (46F - t * 17F) / 128F;
        float radius = profile[ring];
        if (animal)
        {
            // A rounded rectangular cross-section follows vanilla mobs' blocky bodies.
            float across = Math.copySign(Mth.sqrt(Math.abs(x)), x) * radius;
            float up = Math.copySign(Mth.sqrt(Math.abs(z)), z) * radius;
            out.addVertex(pose, across, up, -t)
                    .setColor(255, 236, 215, 255).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY)
                    .setLight(light).setNormal(pose, x, z, 0.5F - t);
        }
        else
        {
            out.addVertex(pose, x * radius, 0.02F + t, z * radius)
                    .setColor(255, 236, 215, 255).setUv(u, v).setOverlay(OverlayTexture.NO_OVERLAY)
                    .setLight(light).setNormal(pose, x, t - 0.5F, z);
        }
    }

    private CocoonWrapRenderer()
    {
    }
}
