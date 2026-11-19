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
 * Geometry converted automatically from the 1.12.2 ModelTurret by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class TurretModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "turret"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart barrel;
    public final ModelPart supportLeft;
    public final ModelPart headBase2;
    public final ModelPart neckBase;
    public final ModelPart headBase3;
    public final ModelPart headBase;
    public final ModelPart barrelGuard;
    public final ModelPart sightBase3;
    public final ModelPart rightFoot;
    public final ModelPart supportRight;
    public final ModelPart supportCenter;
    public final ModelPart centerFoot;
    public final ModelPart legHub;
    public final ModelPart LeftFoot;
    public final ModelPart neck;
    public final ModelPart leftLeg;
    public final ModelPart rightLeg;
    public final ModelPart centerLeg;

    public TurretModel(ModelPart root)
    {
        this.root = root;
        this.barrel = root.getChild("barrel");
        this.parts.put("barrel", this.barrel);
        this.supportLeft = root.getChild("supportLeft");
        this.parts.put("supportLeft", this.supportLeft);
        this.headBase2 = root.getChild("headBase2");
        this.parts.put("headBase2", this.headBase2);
        this.neckBase = root.getChild("neckBase");
        this.parts.put("neckBase", this.neckBase);
        this.headBase3 = root.getChild("headBase3");
        this.parts.put("headBase3", this.headBase3);
        this.headBase = root.getChild("headBase");
        this.parts.put("headBase", this.headBase);
        this.barrelGuard = root.getChild("barrelGuard");
        this.parts.put("barrelGuard", this.barrelGuard);
        this.sightBase3 = root.getChild("sightBase3");
        this.parts.put("sightBase3", this.sightBase3);
        this.rightFoot = root.getChild("rightFoot");
        this.parts.put("rightFoot", this.rightFoot);
        this.supportRight = root.getChild("supportRight");
        this.parts.put("supportRight", this.supportRight);
        this.supportCenter = root.getChild("supportCenter");
        this.parts.put("supportCenter", this.supportCenter);
        this.centerFoot = root.getChild("centerFoot");
        this.parts.put("centerFoot", this.centerFoot);
        this.legHub = root.getChild("legHub");
        this.parts.put("legHub", this.legHub);
        this.LeftFoot = root.getChild("LeftFoot");
        this.parts.put("LeftFoot", this.LeftFoot);
        this.neck = root.getChild("neck");
        this.parts.put("neck", this.neck);
        this.leftLeg = root.getChild("leftLeg");
        this.parts.put("leftLeg", this.leftLeg);
        this.rightLeg = root.getChild("rightLeg");
        this.parts.put("rightLeg", this.rightLeg);
        this.centerLeg = root.getChild("centerLeg");
        this.parts.put("centerLeg", this.centerLeg);
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
                CubeListBuilder.create().texOffs(19, 22).addBox(-0.5F, -1.2F, 10.0F, 1.0F, 1.0F, 6.0F),
                PartPose.offset(0.0F, 11.0F, 0.0F));
        PartDefinition p_supportLeft = root.addOrReplaceChild("supportLeft",
                CubeListBuilder.create().texOffs(43, 22).addBox(-0.5F, -0.8F, -3.0F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.6108652F, -2.617994F, 0.0F));
        PartDefinition p_headBase2 = root.addOrReplaceChild("headBase2",
                CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, -3.0F, -3.5F, 4.0F, 4.0F, 7.0F),
                PartPose.offset(0.0F, 11.0F, 0.0F));
        PartDefinition p_neckBase = root.addOrReplaceChild("neckBase",
                CubeListBuilder.create().texOffs(42, 0).addBox(-1.5F, 3.6F, -1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.0F, 0.7853982F, 0.0F));
        PartDefinition p_headBase3 = root.addOrReplaceChild("headBase3",
                CubeListBuilder.create().texOffs(19, 12).addBox(-2.5F, -1.0F, -4.0F, 5.0F, 1.0F, 8.0F),
                PartPose.offset(0.0F, 11.0F, 0.0F));
        PartDefinition p_headBase = root.addOrReplaceChild("headBase",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 3.0F, 3.0F),
                PartPose.offset(0.0F, 11.0F, 0.0F));
        PartDefinition p_barrelGuard = root.addOrReplaceChild("barrelGuard",
                CubeListBuilder.create().texOffs(0, 9).addBox(-1.0F, -1.5F, 3.5F, 2.0F, 2.0F, 7.0F),
                PartPose.offset(0.0F, 11.0F, 0.0F));
        PartDefinition p_sightBase3 = root.addOrReplaceChild("sightBase3",
                CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, -2.2F, 3.5F, 1.0F, 2.0F, 8.0F),
                PartPose.offset(0.0F, 11.0F, 0.0F));
        PartDefinition p_rightFoot = root.addOrReplaceChild("rightFoot",
                CubeListBuilder.create().texOffs(53, 5).addBox(2.1F, 12.0F, 0.0F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.0F, -2.495821F, 0.0F));
        PartDefinition p_supportRight = root.addOrReplaceChild("supportRight",
                CubeListBuilder.create().texOffs(43, 22).addBox(-0.5F, -0.8F, -3.0F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.6108652F, 2.617994F, 0.0F));
        PartDefinition p_supportCenter = root.addOrReplaceChild("supportCenter",
                CubeListBuilder.create().texOffs(34, 22).addBox(-0.5F, -0.8F, -3.0F, 1.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.6108652F, 0.0F, 0.0F));
        PartDefinition p_centerFoot = root.addOrReplaceChild("centerFoot",
                CubeListBuilder.create().texOffs(53, 5).addBox(-1.0F, 12.0F, 6.1F, 2.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 11.0F, 0.0F));
        PartDefinition p_legHub = root.addOrReplaceChild("legHub",
                CubeListBuilder.create().texOffs(42, 5).addBox(-1.5F, 8.2F, -4.5F, 3.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.8726646F, 0.0F, 0.0F));
        PartDefinition p_LeftFoot = root.addOrReplaceChild("LeftFoot",
                CubeListBuilder.create().texOffs(53, 5).addBox(-3.9F, 12.0F, 0.0F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.0F, 2.530727F, 0.0F));
        PartDefinition p_neck = root.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(46, 12).addBox(-1.0F, 2.2F, -4.0F, 2.0F, 8.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.8726646F, 0.0F, 0.0F));
        PartDefinition p_leftLeg = root.addOrReplaceChild("leftLeg",
                CubeListBuilder.create().texOffs(53, 12).addBox(-3.4F, 2.8F, -10.0F, 1.0F, 7.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.8726646F, 2.530727F, 0.0F));
        PartDefinition p_rightLeg = root.addOrReplaceChild("rightLeg",
                CubeListBuilder.create().texOffs(53, 12).addBox(2.6F, 2.8F, -10.0F, 1.0F, 7.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.8726646F, -2.495821F, 0.0F));
        PartDefinition p_centerLeg = root.addOrReplaceChild("centerLeg",
                CubeListBuilder.create().texOffs(48, 22).addBox(-1.0F, 10.4F, 0.1F, 2.0F, 5.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.4712389F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
