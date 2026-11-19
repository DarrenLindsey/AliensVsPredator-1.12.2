package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.client.model.DroneEntityModel;
import org.avp.client.model.geometry.DroneModel;
import org.avp.entity.xenomorph.DroneEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Drone (1.12.2 RenderDrone: the "advanced" texture, 0.9 scale). */
public class DroneRenderer extends MobRenderer<DroneEntity, DroneEntityModel>
{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/drone.png");

    public DroneRenderer(EntityRendererProvider.Context context)
    {
        super(context, new DroneEntityModel(context.bakeLayer(DroneModel.LAYER)), 0.6F);
    }

    @Override
    public ResourceLocation getTextureLocation(DroneEntity drone)
    {
        return TEXTURE;
    }

    @Override
    protected void scale(DroneEntity drone, PoseStack poseStack, float partialTick)
    {
        poseStack.scale(0.9F, 0.9F, 0.9F);
    }
}
