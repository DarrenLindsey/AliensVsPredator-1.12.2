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
    private static final ResourceLocation BUILDER = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/builder_drone.png");
    private static final ResourceLocation COATER = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/coater_drone.png");

    public DroneRenderer(EntityRendererProvider.Context context)
    {
        super(context, new DroneEntityModel(context.bakeLayer(DroneModel.LAYER)), 0.6F);
    }

    @Override
    public ResourceLocation getTextureLocation(DroneEntity drone)
    {
        return drone instanceof org.avp.entity.xenomorph.BuilderDroneEntity ? BUILDER
                : drone instanceof org.avp.entity.xenomorph.CoaterDroneEntity ? COATER : TEXTURE;
    }

    /**
     * The 1.12.2 model's head sits 1.3 blocks in front of its centre, so it poked through walls.
     * Moving the model back half a block centres the body on the hitbox.
     */
    @Override
    protected void scale(DroneEntity drone, PoseStack poseStack, float partialTick)
    {
        poseStack.translate(0F, 0F, 0.5F);
        poseStack.scale(0.9F, 0.9F, 0.9F);
        if (drone instanceof org.avp.entity.xenomorph.BuilderDroneEntity)
        {
            poseStack.scale(1.1F, 1.0F, 1.1F); // stockier
        }
        else if (drone instanceof org.avp.entity.xenomorph.CoaterDroneEntity)
        {
            poseStack.scale(0.92F, 0.95F, 0.92F); // leaner
        }
        float crawl = drone.crawlBlend(partialTick);
        if (crawl > 0)
        {
            // flattened and stretched out, belly to the floor (fits a 1-block gap); the feet stay on the ground
            poseStack.scale(1.0F, 1.0F - 0.58F * crawl, 1.0F + 0.15F * crawl);
        }
    }
}
