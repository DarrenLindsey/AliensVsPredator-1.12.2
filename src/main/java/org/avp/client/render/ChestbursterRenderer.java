package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.client.model.ChestbursterEntityModel;
import org.avp.client.model.geometry.ChestbursterModel;
import org.avp.entity.xenomorph.ChestbursterEntity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Chestburster (1.12.2 RenderChestburster: the model at 0.55 scale). */
public class ChestbursterRenderer extends MobRenderer<ChestbursterEntity, ChestbursterEntityModel>
{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/chestburster.png");

    public ChestbursterRenderer(EntityRendererProvider.Context context)
    {
        super(context, new ChestbursterEntityModel(context.bakeLayer(ChestbursterModel.LAYER)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(ChestbursterEntity burster)
    {
        return TEXTURE;
    }

    @Override
    protected void scale(ChestbursterEntity burster, PoseStack poseStack, float partialTick)
    {
        poseStack.scale(0.55F, 0.55F, 0.55F);
    }
}
