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
 * Geometry converted automatically from the 1.12.2 ModelAK47 by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class AK47Model implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "a_k47"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart handle;
    public final ModelPart clip;
    public final ModelPart barrel;
    public final ModelPart sight1;
    public final ModelPart sightBase;
    public final ModelPart rbody1;
    public final ModelPart lbody1;
    public final ModelPart stockEnd;
    public final ModelPart barrelBase;
    public final ModelPart rBarrelGrip;
    public final ModelPart lBarrelGrip;
    public final ModelPart stockAngle;
    public final ModelPart trigger;
    public final ModelPart triggerGuard;
    public final ModelPart sightBase1;
    public final ModelPart sight2;
    public final ModelPart stockBase;

    public AK47Model(ModelPart root)
    {
        this.root = root;
        this.handle = root.getChild("handle");
        this.parts.put("handle", this.handle);
        this.clip = root.getChild("clip");
        this.parts.put("clip", this.clip);
        this.barrel = root.getChild("barrel");
        this.parts.put("barrel", this.barrel);
        this.sight1 = root.getChild("sight1");
        this.parts.put("sight1", this.sight1);
        this.sightBase = root.getChild("sightBase");
        this.parts.put("sightBase", this.sightBase);
        this.rbody1 = root.getChild("rbody1");
        this.parts.put("rbody1", this.rbody1);
        this.lbody1 = root.getChild("lbody1");
        this.parts.put("lbody1", this.lbody1);
        this.stockEnd = root.getChild("stockEnd");
        this.parts.put("stockEnd", this.stockEnd);
        this.barrelBase = root.getChild("barrelBase");
        this.parts.put("barrelBase", this.barrelBase);
        this.rBarrelGrip = root.getChild("rBarrelGrip");
        this.parts.put("rBarrelGrip", this.rBarrelGrip);
        this.lBarrelGrip = root.getChild("lBarrelGrip");
        this.parts.put("lBarrelGrip", this.lBarrelGrip);
        this.stockAngle = root.getChild("stockAngle");
        this.parts.put("stockAngle", this.stockAngle);
        this.trigger = root.getChild("trigger");
        this.parts.put("trigger", this.trigger);
        this.triggerGuard = root.getChild("triggerGuard");
        this.parts.put("triggerGuard", this.triggerGuard);
        this.sightBase1 = root.getChild("sightBase1");
        this.parts.put("sightBase1", this.sightBase1);
        this.sight2 = root.getChild("sight2");
        this.parts.put("sight2", this.sight2);
        this.stockBase = root.getChild("stockBase");
        this.parts.put("stockBase", this.stockBase);
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
        PartDefinition p_handle = root.addOrReplaceChild("handle",
                CubeListBuilder.create().texOffs(9, 5).addBox(0.5F, -1.1F, -0.4F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.5F, 2.8F, 4.2F, -0.3665191F, 0.0F, 0.0F));
        PartDefinition p_clip = root.addOrReplaceChild("clip",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.5F, 0.0F, 0.2F, 1.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.5F, 2.0F, 6.5F, 0.3665191F, 0.0F, 0.0F));
        PartDefinition p_barrel = root.addOrReplaceChild("barrel",
                CubeListBuilder.create().texOffs(25, 0).addBox(1.0F, 0.3F, 8.5F, 1.0F, 1.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_sight1 = root.addOrReplaceChild("sight1",
                CubeListBuilder.create().texOffs(44, 6).addBox(1.0F, -0.7F, 16.3F, 1.0F, 1.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_sightBase = root.addOrReplaceChild("sightBase",
                CubeListBuilder.create().texOffs(0, 12).addBox(1.0F, -0.3F, 4.5F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rbody1 = root.addOrReplaceChild("rbody1",
                CubeListBuilder.create().texOffs(0, 18).addBox(1.3F, 0.0F, 0.0F, 1.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 4.0F));
        PartDefinition p_lbody1 = root.addOrReplaceChild("lbody1",
                CubeListBuilder.create().texOffs(13, 18).addBox(0.7F, 0.0F, 0.0F, 1.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 4.0F));
        PartDefinition p_stockEnd = root.addOrReplaceChild("stockEnd",
                CubeListBuilder.create().texOffs(9, 0).addBox(1.0F, -0.2F, 0.0F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_barrelBase = root.addOrReplaceChild("barrelBase",
                CubeListBuilder.create().texOffs(25, 10).addBox(1.0F, 0.1F, 9.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rBarrelGrip = root.addOrReplaceChild("rBarrelGrip",
                CubeListBuilder.create().texOffs(14, 6).addBox(1.2F, 1.0F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, 0.0872665F, 0.0F, 0.0F));
        PartDefinition p_lBarrelGrip = root.addOrReplaceChild("lBarrelGrip",
                CubeListBuilder.create().texOffs(14, 0).addBox(0.8F, 1.0F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, 0.0872665F, 0.0F, 0.0F));
        PartDefinition p_stockAngle = root.addOrReplaceChild("stockAngle",
                CubeListBuilder.create().texOffs(14, 12).addBox(1.0F, 1.2F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.122173F, 0.0F, 0.0F));
        PartDefinition p_trigger = root.addOrReplaceChild("trigger",
                CubeListBuilder.create().texOffs(11, 14).addBox(1.5F, 1.7F, 0.8F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 4.0F));
        PartDefinition p_triggerGuard = root.addOrReplaceChild("triggerGuard",
                CubeListBuilder.create().texOffs(44, 0).addBox(1.0F, 2.7F, 0.0F, 1.0F, 0.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 4.0F));
        PartDefinition p_sightBase1 = root.addOrReplaceChild("sightBase1",
                CubeListBuilder.create().texOffs(44, 4).addBox(1.0F, -1.3F, 5.0F, 1.0F, 1.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_sight2 = root.addOrReplaceChild("sight2",
                CubeListBuilder.create().texOffs(11, 10).addBox(1.5F, -0.7F, 15.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_stockBase = root.addOrReplaceChild("stockBase",
                CubeListBuilder.create().texOffs(0, 7).addBox(1.0F, 0.1F, 1.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
