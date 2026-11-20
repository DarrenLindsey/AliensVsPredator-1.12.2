package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.entity.joe.WorkingJoeEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

/** A Working Joe: the biped model, rubbery grey skin and Seegson overalls, with glowing eyes (red when it malfunctions, dark when it's out of power). */
public class WorkingJoeRenderer extends HumanoidMobRenderer<WorkingJoeEntity, HumanoidModel<WorkingJoeEntity>>
{
    private static final ResourceLocation SKIN = tex("working_joe.png");
    private static final ResourceLocation EYES = tex("working_joe_eyes.png");
    private static final ResourceLocation EYES_ROGUE = tex("working_joe_eyes_rogue.png");

    public WorkingJoeRenderer(EntityRendererProvider.Context context)
    {
        super(context, new HumanoidModel<>(context.bakeLayer(HumanRenderer.LAYER)), 0.5F);
        addLayer(new Eyes(this));
    }

    private static ResourceLocation tex(String name)
    {
        return ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/" + name);
    }

    @Override
    public ResourceLocation getTextureLocation(WorkingJoeEntity entity)
    {
        return SKIN;
    }

    private static class Eyes extends RenderLayer<WorkingJoeEntity, HumanoidModel<WorkingJoeEntity>>
    {
        Eyes(RenderLayerParent<WorkingJoeEntity, HumanoidModel<WorkingJoeEntity>> parent)
        {
            super(parent);
        }

        @Override
        public void render(PoseStack pose, MultiBufferSource buffers, int light, WorkingJoeEntity joe, float limbSwing, float limbSwingAmount,
                float partialTick, float age, float headYaw, float headPitch)
        {
            if (!joe.isActive() || joe.isInvisible())
            {
                return;
            }
            // malfunctioning eyes flicker
            if (joe.isRogue() && (joe.tickCount / 3) % 7 == 0)
            {
                return;
            }
            getParentModel().renderToBuffer(pose, buffers.getBuffer(RenderType.eyes(joe.isRogue() ? EYES_ROGUE : EYES)), 0xF000F0,
                    OverlayTexture.NO_OVERLAY);
        }
    }
}
