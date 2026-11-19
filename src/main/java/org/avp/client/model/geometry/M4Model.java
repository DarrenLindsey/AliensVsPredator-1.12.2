package org.avp.client.model.geometry;

import java.util.LinkedHashMap;
import java.util.Map;

import org.avp.AliensVsPredator;
import org.avp.client.model.GeneratedModel;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

/**
 * Geometry converted automatically from the 1.12.2 ModelM4 by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class M4Model implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "m4"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart barrel;
    public final ModelPart clip;
    public final ModelPart stock1;
    public final ModelPart stock2;
    public final ModelPart stock3;
    public final ModelPart handle;
    public final ModelPart mainBody1;
    public final ModelPart mainBody2;
    public final ModelPart mainBody3;
    public final ModelPart triggerGuard;
    public final ModelPart barrelGuard;
    public final ModelPart sightBase1;
    public final ModelPart sightBase2;
    public final ModelPart sightBase3;
    public final ModelPart sight1;
    public final ModelPart sight2;

    public M4Model(ModelPart root)
    {
        this.root = root;
        this.barrel = root.getChild("barrel");
        this.parts.put("barrel", this.barrel);
        this.clip = root.getChild("clip");
        this.parts.put("clip", this.clip);
        this.stock1 = root.getChild("stock1");
        this.parts.put("stock1", this.stock1);
        this.stock2 = root.getChild("stock2");
        this.parts.put("stock2", this.stock2);
        this.stock3 = root.getChild("stock3");
        this.parts.put("stock3", this.stock3);
        this.handle = root.getChild("handle");
        this.parts.put("handle", this.handle);
        this.mainBody1 = root.getChild("mainBody1");
        this.parts.put("mainBody1", this.mainBody1);
        this.mainBody2 = root.getChild("mainBody2");
        this.parts.put("mainBody2", this.mainBody2);
        this.mainBody3 = root.getChild("mainBody3");
        this.parts.put("mainBody3", this.mainBody3);
        this.triggerGuard = root.getChild("triggerGuard");
        this.parts.put("triggerGuard", this.triggerGuard);
        this.barrelGuard = root.getChild("barrelGuard");
        this.parts.put("barrelGuard", this.barrelGuard);
        this.sightBase1 = root.getChild("sightBase1");
        this.parts.put("sightBase1", this.sightBase1);
        this.sightBase2 = root.getChild("sightBase2");
        this.parts.put("sightBase2", this.sightBase2);
        this.sightBase3 = root.getChild("sightBase3");
        this.parts.put("sightBase3", this.sightBase3);
        this.sight1 = root.getChild("sight1");
        this.parts.put("sight1", this.sight1);
        this.sight2 = root.getChild("sight2");
        this.parts.put("sight2", this.sight2);
    }

    @Override
    public ModelPart root()
    {
        return root;
    }

    @Override
    public Map<String, ModelPart> parts()
    {
        return parts;
    }

    public static LayerDefinition createLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition p_barrel = root.addOrReplaceChild("barrel",
                CubeListBuilder.create().texOffs(13, 12).addBox(0.0F, -1.7F, -6.0F, 1.0F, 1.0F, 9.0F),
                PartPose.offset(-4.5F, 10.7F, 9.0F));
        PartDefinition p_clip = root.addOrReplaceChild("clip",
                CubeListBuilder.create().texOffs(27, 0).addBox(0.5F, 0.5F, 0.3F, 1.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(-5.0F, 11.0F, 0.0F, 0.2617994F, 0.0F, 0.0F));
        PartDefinition p_stock1 = root.addOrReplaceChild("stock1",
                CubeListBuilder.create().texOffs(16, 0).addBox(0.5F, 0.0F, -7.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(-5.0F, 9.0F, 0.0F));
        PartDefinition p_stock2 = root.addOrReplaceChild("stock2",
                CubeListBuilder.create().texOffs(9, 0).addBox(0.0F, -0.4F, -8.0F, 2.0F, 3.0F, 1.0F),
                PartPose.offset(-5.0F, 9.0F, 0.0F));
        PartDefinition p_stock3 = root.addOrReplaceChild("stock3",
                CubeListBuilder.create().texOffs(4, 11).addBox(-4.5F, 6.5F, -11.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3839724F, 0.0F, 0.0F));
        PartDefinition p_handle = root.addOrReplaceChild("handle",
                CubeListBuilder.create().texOffs(5, 5).addBox(0.0F, -0.4F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(-4.5F, 14.0F, -3.9F, 1.186824F, 0.0F, 0.0F));
        PartDefinition p_mainBody1 = root.addOrReplaceChild("mainBody1",
                CubeListBuilder.create().texOffs(34, 14).addBox(0.0F, -2.0F, -3.0F, 2.0F, 2.0F, 6.0F),
                PartPose.offset(-5.0F, 10.7F, 0.0F));
        PartDefinition p_mainBody2 = root.addOrReplaceChild("mainBody2",
                CubeListBuilder.create().texOffs(0, 5).addBox(1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 2.0F),
                PartPose.offset(-5.0F, 10.7F, -3.0F));
        PartDefinition p_mainBody3 = root.addOrReplaceChild("mainBody3",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.5F, 0.0F, 3.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(-5.0F, 10.7F, -3.0F));
        PartDefinition p_triggerGuard = root.addOrReplaceChild("triggerGuard",
                CubeListBuilder.create().texOffs(27, 7).addBox(0.5F, 1.0F, 0.0F, 1.0F, 0.0F, 3.0F),
                PartPose.offset(-5.0F, 10.7F, -3.0F));
        PartDefinition p_barrelGuard = root.addOrReplaceChild("barrelGuard",
                CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, -2.0F, -5.5F, 2.0F, 2.0F, 4.0F),
                PartPose.offset(-4.5F, 10.7F, 9.0F));
        PartDefinition p_sightBase1 = root.addOrReplaceChild("sightBase1",
                CubeListBuilder.create().texOffs(1, 9).addBox(0.0F, -3.0F, -11.8F, 1.0F, 1.0F, 0.0F),
                PartPose.offset(-4.5F, 10.7F, 9.0F));
        PartDefinition p_sightBase2 = root.addOrReplaceChild("sightBase2",
                CubeListBuilder.create().texOffs(36, 6).addBox(-4.5F, 8.4F, -3.0F, 1.0F, 1.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_sightBase3 = root.addOrReplaceChild("sightBase3",
                CubeListBuilder.create().texOffs(16, 6).addBox(-4.5F, 8.4F, 3.5F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_sight1 = root.addOrReplaceChild("sight1",
                CubeListBuilder.create().texOffs(1, 11).addBox(0.0F, -3.0F, -1.8F, 1.0F, 1.0F, 0.0F),
                PartPose.offset(-4.5F, 10.7F, 9.0F));
        PartDefinition p_sight2 = root.addOrReplaceChild("sight2",
                CubeListBuilder.create().texOffs(1, 13).addBox(0.5F, -3.0F, -2.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(-4.5F, 10.7F, 9.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
