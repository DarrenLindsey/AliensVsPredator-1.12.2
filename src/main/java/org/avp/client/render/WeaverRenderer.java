package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.client.model.WeaverModel;
import org.avp.entity.xenomorph.DroneEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public final class WeaverRenderer extends MobRenderer<DroneEntity, WeaverModel>
{
    private final ResourceLocation texture;

    public WeaverRenderer(EntityRendererProvider.Context context, boolean builder)
    {
        super(context, new WeaverModel(context.bakeLayer(builder ? WeaverModel.BUILDER : WeaverModel.COATER)),
                builder ? 0.44F : 0.39F);
        texture = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID,
                builder ? "textures/entity/builder_weaver.png" : "textures/entity/coater_weaver.png");
    }

    @Override
    public ResourceLocation getTextureLocation(DroneEntity drone)
    {
        return texture;
    }

    @Override
    protected void scale(DroneEntity drone, PoseStack poses, float partialTick)
    {
        float crawl = drone.crawlBlend(partialTick);
        poses.scale(1F, 1F - 0.45F * crawl, 1F + 0.12F * crawl);
    }
}
