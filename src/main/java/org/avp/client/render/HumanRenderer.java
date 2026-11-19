package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.entity.creature.Creatures;
import org.avp.entity.human.HumanEntity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

/** Marines and synthetics: a player-shaped model (64x32 layout, like the 1.12.2 ModelMarine / ModelBiped). */
public class HumanRenderer extends HumanoidMobRenderer<HumanEntity, HumanoidModel<HumanEntity>>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "human"), "main");
    private final ResourceLocation texture;

    public HumanRenderer(EntityRendererProvider.Context context, String id)
    {
        super(context, new HumanoidModel<>(context.bakeLayer(LAYER)), 0.5F);
        this.texture = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/" + Creatures.ALL.get(id).texture());
    }

    public static LayerDefinition createLayer()
    {
        return LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0F), 64, 32);
    }

    @Override
    public ResourceLocation getTextureLocation(HumanEntity entity)
    {
        return texture;
    }
}
