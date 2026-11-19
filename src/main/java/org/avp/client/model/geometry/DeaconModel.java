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
 * Geometry converted automatically from the 1.12.2 ModelDeacon by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class DeaconModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "deacon"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart chest;
    public final ModelPart lShoulder;
    public final ModelPart lUpperArm;
    public final ModelPart lLowerArm;
    public final ModelPart lHand;
    public final ModelPart lFinger02;
    public final ModelPart lFinger03;
    public final ModelPart lFinger01;
    public final ModelPart lFinger05;
    public final ModelPart lFinger04;
    public final ModelPart stomach;
    public final ModelPart rThigh;
    public final ModelPart rHipBone;
    public final ModelPart rShin;
    public final ModelPart rFoot;
    public final ModelPart rToes;
    public final ModelPart lThigh;
    public final ModelPart lShin;
    public final ModelPart lFoot;
    public final ModelPart lToes;
    public final ModelPart lHipBone;
    public final ModelPart neck;
    public final ModelPart head;
    public final ModelPart head02;
    public final ModelPart head03;
    public final ModelPart head04;
    public final ModelPart head05;
    public final ModelPart muzzle;
    public final ModelPart upperJaw;
    public final ModelPart lowerJaw;
    public final ModelPart lowerJaw02;
    public final ModelPart innerJaw;
    public final ModelPart innerJaw02;
    public final ModelPart headSlope01;
    public final ModelPart headSlope02;
    public final ModelPart headSlope03;
    public final ModelPart headSlope04;
    public final ModelPart rShoulder;
    public final ModelPart rUpperArm;
    public final ModelPart rLowerArm;
    public final ModelPart rHand;
    public final ModelPart rFinger01;
    public final ModelPart rFinger03;
    public final ModelPart rFinger04;
    public final ModelPart rFinger05;
    public final ModelPart rFinger02;

    public DeaconModel(ModelPart root)
    {
        this.root = root;
        this.chest = root.getChild("chest");
        this.parts.put("chest", this.chest);
        this.lShoulder = this.chest.getChild("lShoulder");
        this.parts.put("lShoulder", this.lShoulder);
        this.lUpperArm = this.lShoulder.getChild("lUpperArm");
        this.parts.put("lUpperArm", this.lUpperArm);
        this.lLowerArm = this.lUpperArm.getChild("lLowerArm");
        this.parts.put("lLowerArm", this.lLowerArm);
        this.lHand = this.lLowerArm.getChild("lHand");
        this.parts.put("lHand", this.lHand);
        this.lFinger02 = this.lHand.getChild("lFinger02");
        this.parts.put("lFinger02", this.lFinger02);
        this.lFinger03 = this.lHand.getChild("lFinger03");
        this.parts.put("lFinger03", this.lFinger03);
        this.lFinger01 = this.lHand.getChild("lFinger01");
        this.parts.put("lFinger01", this.lFinger01);
        this.lFinger05 = this.lHand.getChild("lFinger05");
        this.parts.put("lFinger05", this.lFinger05);
        this.lFinger04 = this.lHand.getChild("lFinger04");
        this.parts.put("lFinger04", this.lFinger04);
        this.stomach = this.chest.getChild("stomach");
        this.parts.put("stomach", this.stomach);
        this.rThigh = this.stomach.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.rHipBone = this.rThigh.getChild("rHipBone");
        this.parts.put("rHipBone", this.rHipBone);
        this.rShin = this.rThigh.getChild("rShin");
        this.parts.put("rShin", this.rShin);
        this.rFoot = this.rShin.getChild("rFoot");
        this.parts.put("rFoot", this.rFoot);
        this.rToes = this.rFoot.getChild("rToes");
        this.parts.put("rToes", this.rToes);
        this.lThigh = this.stomach.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lShin = this.lThigh.getChild("lShin");
        this.parts.put("lShin", this.lShin);
        this.lFoot = this.lShin.getChild("lFoot");
        this.parts.put("lFoot", this.lFoot);
        this.lToes = this.lFoot.getChild("lToes");
        this.parts.put("lToes", this.lToes);
        this.lHipBone = this.lThigh.getChild("lHipBone");
        this.parts.put("lHipBone", this.lHipBone);
        this.neck = this.chest.getChild("neck");
        this.parts.put("neck", this.neck);
        this.head = this.neck.getChild("head");
        this.parts.put("head", this.head);
        this.head02 = this.head.getChild("head02");
        this.parts.put("head02", this.head02);
        this.head03 = this.head02.getChild("head03");
        this.parts.put("head03", this.head03);
        this.head04 = this.head03.getChild("head04");
        this.parts.put("head04", this.head04);
        this.head05 = this.head04.getChild("head05");
        this.parts.put("head05", this.head05);
        this.muzzle = this.head.getChild("muzzle");
        this.parts.put("muzzle", this.muzzle);
        this.upperJaw = this.muzzle.getChild("upperJaw");
        this.parts.put("upperJaw", this.upperJaw);
        this.lowerJaw = this.head.getChild("lowerJaw");
        this.parts.put("lowerJaw", this.lowerJaw);
        this.lowerJaw02 = this.lowerJaw.getChild("lowerJaw02");
        this.parts.put("lowerJaw02", this.lowerJaw02);
        this.innerJaw = this.head.getChild("innerJaw");
        this.parts.put("innerJaw", this.innerJaw);
        this.innerJaw02 = this.innerJaw.getChild("innerJaw02");
        this.parts.put("innerJaw02", this.innerJaw02);
        this.headSlope01 = this.head.getChild("headSlope01");
        this.parts.put("headSlope01", this.headSlope01);
        this.headSlope02 = this.headSlope01.getChild("headSlope02");
        this.parts.put("headSlope02", this.headSlope02);
        this.headSlope03 = this.headSlope02.getChild("headSlope03");
        this.parts.put("headSlope03", this.headSlope03);
        this.headSlope04 = this.headSlope03.getChild("headSlope04");
        this.parts.put("headSlope04", this.headSlope04);
        this.rShoulder = this.chest.getChild("rShoulder");
        this.parts.put("rShoulder", this.rShoulder);
        this.rUpperArm = this.rShoulder.getChild("rUpperArm");
        this.parts.put("rUpperArm", this.rUpperArm);
        this.rLowerArm = this.rUpperArm.getChild("rLowerArm");
        this.parts.put("rLowerArm", this.rLowerArm);
        this.rHand = this.rLowerArm.getChild("rHand");
        this.parts.put("rHand", this.rHand);
        this.rFinger01 = this.rHand.getChild("rFinger01");
        this.parts.put("rFinger01", this.rFinger01);
        this.rFinger03 = this.rHand.getChild("rFinger03");
        this.parts.put("rFinger03", this.rFinger03);
        this.rFinger04 = this.rHand.getChild("rFinger04");
        this.parts.put("rFinger04", this.rFinger04);
        this.rFinger05 = this.rHand.getChild("rFinger05");
        this.parts.put("rFinger05", this.rFinger05);
        this.rFinger02 = this.rHand.getChild("rFinger02");
        this.parts.put("rFinger02", this.rFinger02);
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
        PartDefinition p_chest = root.addOrReplaceChild("chest",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -4.0F, 6.0F, 7.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, -11.8F, 0.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_lShoulder = p_chest.addOrReplaceChild("lShoulder",
                CubeListBuilder.create().texOffs(59, 0).addBox(-1.8F, -1.0F, -1.3F, 3.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(3.4F, 1.0F, -0.6F, 0.091106186954104F, 0.0F, -0.31869712141416456F));
        PartDefinition p_lUpperArm = p_lShoulder.addOrReplaceChild("lUpperArm",
                CubeListBuilder.create().texOffs(62, 10).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 2.4F, 0.2F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_lLowerArm = p_lUpperArm.addOrReplaceChild("lLowerArm",
                CubeListBuilder.create().texOffs(62, 21).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 6.6F, 0.0F, -0.40980330836826856F, 0.0F, 0.091106186954104F));
        PartDefinition p_lHand = p_lLowerArm.addOrReplaceChild("lHand",
                CubeListBuilder.create().texOffs(63, 33).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 6.8F, -0.4F, 0.0F, -0.27314402793711257F, 0.0F));
        PartDefinition p_lFinger02 = p_lHand.addOrReplaceChild("lFinger02",
                CubeListBuilder.create().texOffs(65, 39).addBox(0.0F, 0.0F, -1.0F, 0.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.7F, 3.0F, -0.9F, 0.27314402793711257F, 0.0F, -0.045553093477052F));
        PartDefinition p_lFinger03 = p_lHand.addOrReplaceChild("lFinger03",
                CubeListBuilder.create().texOffs(65, 39).addBox(0.0F, 0.0F, -1.0F, 0.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(-0.3F, 3.0F, -0.9F, 0.27314402793711257F, 0.0F, 0.045553093477052F));
        PartDefinition p_lFinger01 = p_lHand.addOrReplaceChild("lFinger01",
                CubeListBuilder.create().texOffs(65, 39).addBox(0.0F, 0.0F, -1.0F, 0.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(1.3F, 2.7F, -0.9F, 0.27314402793711257F, 0.0F, -0.136659280431156F));
        PartDefinition p_lFinger05 = p_lHand.addOrReplaceChild("lFinger05",
                CubeListBuilder.create().texOffs(71, 39).addBox(0.0F, 0.0F, -1.0F, 0.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(-1.4F, 2.0F, 0.0F, 0.27314402793711257F, 0.0F, 0.18203784098300857F));
        PartDefinition p_lFinger04 = p_lHand.addOrReplaceChild("lFinger04",
                CubeListBuilder.create().texOffs(65, 39).addBox(0.0F, 0.0F, -1.0F, 0.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(-1.2F, 2.7F, -0.9F, 0.27314402793711257F, 0.0F, 0.136659280431156F));
        PartDefinition p_stomach = p_chest.addOrReplaceChild("stomach",
                CubeListBuilder.create().texOffs(0, 18).addBox(-2.5F, 0.0F, -3.5F, 5.0F, 8.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 6.6F, 0.8F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_rThigh = p_stomach.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(35, 0).mirror().addBox(-1.5F, -1.0F, -2.0F, 3.0F, 13.0F, 4.0F),
                PartPose.offsetAndRotation(-2.5F, 6.5F, -0.3F, -0.40980330836826856F, 0.045553093477052F, 0.18203784098300857F));
        PartDefinition p_rHipBone = p_rThigh.addOrReplaceChild("rHipBone",
                CubeListBuilder.create().texOffs(69, 52).mirror().addBox(-0.9F, -3.3F, -3.0F, 2.0F, 4.0F, 6.0F),
                PartPose.offsetAndRotation(0.5F, 0.6F, 0.0F, 0.6373942428283291F, 0.22759093446006054F, -0.4553564018453205F));
        PartDefinition p_rShin = p_rThigh.addOrReplaceChild("rShin",
                CubeListBuilder.create().texOffs(31, 19).mirror().addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(-0.2F, 10.4F, 0.0F, -0.6829473363053812F, 0.045553093477052F, -0.091106186954104F));
        PartDefinition p_rFoot = p_rShin.addOrReplaceChild("rFoot",
                CubeListBuilder.create().texOffs(38, 33).mirror().addBox(-1.5F, -0.7F, -1.0F, 3.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 1.0F, 10.2F, 0.5009094953223726F, 0.136659280431156F, 0.045553093477052F));
        PartDefinition p_rToes = p_rFoot.addOrReplaceChild("rToes",
                CubeListBuilder.create().texOffs(36, 42).mirror().addBox(-1.6F, -1.0F, -2.6F, 3.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 3.7F, 0.1F, 0.5462880558742251F, 0.0F, 0.0F));
        PartDefinition p_lThigh = p_stomach.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(35, 0).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 13.0F, 4.0F),
                PartPose.offsetAndRotation(2.5F, 6.5F, -0.3F, -0.40980330836826856F, -0.045553093477052F, -0.18203784098300857F));
        PartDefinition p_lShin = p_lThigh.addOrReplaceChild("lShin",
                CubeListBuilder.create().texOffs(31, 19).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(0.2F, 10.4F, 0.0F, -0.6829473363053812F, -0.045553093477052F, 0.091106186954104F));
        PartDefinition p_lFoot = p_lShin.addOrReplaceChild("lFoot",
                CubeListBuilder.create().texOffs(38, 33).addBox(-1.5F, -0.7F, -1.0F, 3.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 1.0F, 10.2F, 0.5009094953223726F, -0.136659280431156F, -0.045553093477052F));
        PartDefinition p_lToes = p_lFoot.addOrReplaceChild("lToes",
                CubeListBuilder.create().texOffs(36, 42).addBox(-1.4F, -1.0F, -2.6F, 3.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 3.7F, 0.1F, 0.5462880558742251F, 0.0F, 0.0F));
        PartDefinition p_lHipBone = p_lThigh.addOrReplaceChild("lHipBone",
                CubeListBuilder.create().texOffs(69, 52).addBox(-2.1F, -2.9F, -3.0F, 2.0F, 4.0F, 6.0F),
                PartPose.offsetAndRotation(0.5F, 0.7F, 0.0F, 0.6373942428283291F, -0.22759093446006054F, 0.4553564018453205F));
        PartDefinition p_neck = p_chest.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(0, 38).addBox(-2.0F, -3.1F, -2.5F, 4.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.5F, -0.4F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_head = p_neck.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(85, 0).addBox(-2.5F, -5.0F, -3.0F, 5.0F, 5.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -2.6F, 0.0F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_head02 = p_head.addOrReplaceChild("head02",
                CubeListBuilder.create().texOffs(89, 14).addBox(-2.5F, -2.0F, 0.0F, 5.0F, 3.0F, 3.0F),
                PartPose.offset(0.0F, -2.9F, 2.6F));
        PartDefinition p_head03 = p_head02.addOrReplaceChild("head03",
                CubeListBuilder.create().texOffs(90, 24).addBox(-2.0F, -1.5F, -0.3F, 4.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -0.3F, 2.5F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_head04 = p_head03.addOrReplaceChild("head04",
                CubeListBuilder.create().texOffs(91, 31).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -0.4F, 2.5F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_head05 = p_head04.addOrReplaceChild("head05",
                CubeListBuilder.create().texOffs(92, 38).addBox(-1.0F, -0.8F, 0.0F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -0.1F, 1.8F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_muzzle = p_head.addOrReplaceChild("muzzle",
                CubeListBuilder.create().texOffs(0, 49).addBox(-2.0F, -1.1F, -3.1F, 4.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -3.9F, -2.4F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_upperJaw = p_muzzle.addOrReplaceChild("upperJaw",
                CubeListBuilder.create().texOffs(0, 55).addBox(-2.0F, -1.0F, -2.97F, 4.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 1.2F, -1.0F, -0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_lowerJaw = p_head.addOrReplaceChild("lowerJaw",
                CubeListBuilder.create().texOffs(19, 48).addBox(-1.83F, 0.0F, -2.6F, 3.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, -1.4F, -2.9F));
        PartDefinition p_lowerJaw02 = p_lowerJaw.addOrReplaceChild("lowerJaw02",
                CubeListBuilder.create().texOffs(19, 56).addBox(0.83F, 0.0F, -2.6F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_innerJaw = p_head.addOrReplaceChild("innerJaw",
                CubeListBuilder.create().texOffs(39, 48).addBox(-1.82F, -1.0F, -4.0F, 3.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, -1.8F, -1.3F));
        PartDefinition p_innerJaw02 = p_innerJaw.addOrReplaceChild("innerJaw02",
                CubeListBuilder.create().texOffs(41, 56).addBox(0.82F, -1.0F, -4.0F, 1.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_headSlope01 = p_head.addOrReplaceChild("headSlope01",
                CubeListBuilder.create().texOffs(85, 45).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -1.4F, 0.9F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_headSlope02 = p_headSlope01.addOrReplaceChild("headSlope02",
                CubeListBuilder.create().texOffs(103, 45).addBox(-1.5F, -1.0F, -0.7F, 3.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -0.1F, 4.6F, -0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_headSlope03 = p_headSlope02.addOrReplaceChild("headSlope03",
                CubeListBuilder.create().texOffs(118, 45).addBox(-1.0F, -0.5F, -0.7F, 2.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.4F, 2.3F));
        PartDefinition p_headSlope04 = p_headSlope03.addOrReplaceChild("headSlope04",
                CubeListBuilder.create().texOffs(118, 52).addBox(-0.5F, -0.5F, -0.3F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 1.6F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_rShoulder = p_chest.addOrReplaceChild("rShoulder",
                CubeListBuilder.create().texOffs(59, 0).mirror().addBox(-1.2F, -1.0F, -1.3F, 3.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(-3.4F, 1.0F, -0.6F, 0.091106186954104F, 0.0F, 0.31869712141416456F));
        PartDefinition p_rUpperArm = p_rShoulder.addOrReplaceChild("rUpperArm",
                CubeListBuilder.create().texOffs(62, 10).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 2.4F, 0.2F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_rLowerArm = p_rUpperArm.addOrReplaceChild("rLowerArm",
                CubeListBuilder.create().texOffs(62, 21).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 6.6F, 0.0F, -0.40980330836826856F, 0.0F, -0.091106186954104F));
        PartDefinition p_rHand = p_rLowerArm.addOrReplaceChild("rHand",
                CubeListBuilder.create().texOffs(63, 33).mirror().addBox(-1.5F, 0.0F, -0.5F, 3.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 6.8F, -0.4F, 0.0F, 0.27314402793711257F, 0.0F));
        PartDefinition p_rFinger01 = p_rHand.addOrReplaceChild("rFinger01",
                CubeListBuilder.create().texOffs(65, 39).addBox(0.0F, 0.0F, -1.0F, 0.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(-1.3F, 2.7F, -0.9F, 0.27314402793711257F, 0.0F, 0.136659280431156F));
        PartDefinition p_rFinger03 = p_rHand.addOrReplaceChild("rFinger03",
                CubeListBuilder.create().texOffs(65, 39).addBox(0.0F, 0.0F, -1.0F, 0.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.3F, 3.0F, -0.9F, 0.27314402793711257F, 0.0F, -0.045553093477052F));
        PartDefinition p_rFinger04 = p_rHand.addOrReplaceChild("rFinger04",
                CubeListBuilder.create().texOffs(65, 39).addBox(0.0F, 0.0F, -1.0F, 0.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(1.2F, 2.7F, -0.9F, 0.27314402793711257F, 0.0F, -0.136659280431156F));
        PartDefinition p_rFinger05 = p_rHand.addOrReplaceChild("rFinger05",
                CubeListBuilder.create().texOffs(71, 39).addBox(0.0F, 0.0F, -1.0F, 0.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(1.4F, 2.0F, 0.0F, 0.27314402793711257F, 0.0F, -0.18203784098300857F));
        PartDefinition p_rFinger02 = p_rHand.addOrReplaceChild("rFinger02",
                CubeListBuilder.create().texOffs(65, 39).addBox(0.0F, 0.0F, -1.0F, 0.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(-0.7F, 3.0F, -0.9F, 0.27314402793711257F, 0.0F, 0.045553093477052F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
