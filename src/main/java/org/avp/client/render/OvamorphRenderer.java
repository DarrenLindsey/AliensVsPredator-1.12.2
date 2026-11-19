package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.client.model.OvamorphEntityModel;
import org.avp.client.model.geometry.OvamorphModel;
import org.avp.entity.xenomorph.OvamorphEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Ovamorph (1.12.2 RenderOvamorph: 1.75 scale). */
public class OvamorphRenderer extends MobRenderer<OvamorphEntity, OvamorphEntityModel>
{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/ovamorph.png");

    public OvamorphRenderer(EntityRendererProvider.Context context)
    {
        super(context, new OvamorphEntityModel(context.bakeLayer(OvamorphModel.LAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(OvamorphEntity egg)
    {
        return TEXTURE;
    }

    @Override
    protected void scale(OvamorphEntity egg, PoseStack poseStack, float partialTick)
    {
        poseStack.scale(1.75F, 1.75F, 1.75F);
    }

    @Override
    protected float getFlipDegrees(OvamorphEntity egg)
    {
        return 0F; // an egg doesn't keel over
    }
}
