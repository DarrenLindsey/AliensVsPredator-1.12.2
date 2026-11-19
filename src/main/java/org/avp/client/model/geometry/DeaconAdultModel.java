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
 * Geometry converted automatically from the 1.12.2 ModelDeaconAdult by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class DeaconAdultModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "deacon_adult"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart chest01;
    public final ModelPart neck;
    public final ModelPart head;
    public final ModelPart headLower01;
    public final ModelPart headLower02;
    public final ModelPart headLower03;
    public final ModelPart innerJaw01;
    public final ModelPart innerJaw02;
    public final ModelPart lowerJaw;
    public final ModelPart lowerTeeth01;
    public final ModelPart lowerJaw02;
    public final ModelPart lowerTeeth02;
    public final ModelPart head01;
    public final ModelPart head02;
    public final ModelPart head03;
    public final ModelPart head04;
    public final ModelPart jawTendon01;
    public final ModelPart jawTendon02;
    public final ModelPart muzzle;
    public final ModelPart upperJaw;
    public final ModelPart lShoulderRidge01;
    public final ModelPart lShoulderRidge02;
    public final ModelPart lShoulderRidge03;
    public final ModelPart lShoulder;
    public final ModelPart lUpperArm;
    public final ModelPart lLowerArm;
    public final ModelPart lHand;
    public final ModelPart lFinger04;
    public final ModelPart lFinger01;
    public final ModelPart lFinger02;
    public final ModelPart lFinger03;
    public final ModelPart lFinger05;
    public final ModelPart lArmSpike;
    public final ModelPart rShoulderRidge01;
    public final ModelPart rShoulderRidge02;
    public final ModelPart rShoulderRidge03;
    public final ModelPart chest02;
    public final ModelPart stomach;
    public final ModelPart hips;
    public final ModelPart tail01a;
    public final ModelPart tail01c;
    public final ModelPart tail02a;
    public final ModelPart tail02b;
    public final ModelPart tail02c;
    public final ModelPart tail02d;
    public final ModelPart tail03;
    public final ModelPart tailBlade01;
    public final ModelPart tailBlade02;
    public final ModelPart tail01b;
    public final ModelPart tail01d;
    public final ModelPart rThigh;
    public final ModelPart rShin;
    public final ModelPart rFoot01;
    public final ModelPart rFoot02;
    public final ModelPart lThigh;
    public final ModelPart lShin;
    public final ModelPart lFoot01;
    public final ModelPart lFoot02;
    public final ModelPart rHipBone;
    public final ModelPart lHipBone;
    public final ModelPart rShoulder;
    public final ModelPart rUpperArm;
    public final ModelPart rLowerArm;
    public final ModelPart lArmSpike_1;
    public final ModelPart rHand;
    public final ModelPart rFinger01;
    public final ModelPart rFinger02;
    public final ModelPart rFinger05;
    public final ModelPart rFinger04;
    public final ModelPart rFinger03;

    public DeaconAdultModel(ModelPart root)
    {
        this.root = root;
        this.chest01 = root.getChild("chest01");
        this.parts.put("chest01", this.chest01);
        this.neck = this.chest01.getChild("neck");
        this.parts.put("neck", this.neck);
        this.head = this.neck.getChild("head");
        this.parts.put("head", this.head);
        this.headLower01 = this.head.getChild("headLower01");
        this.parts.put("headLower01", this.headLower01);
        this.headLower02 = this.headLower01.getChild("headLower02");
        this.parts.put("headLower02", this.headLower02);
        this.headLower03 = this.headLower02.getChild("headLower03");
        this.parts.put("headLower03", this.headLower03);
        this.innerJaw01 = this.head.getChild("innerJaw01");
        this.parts.put("innerJaw01", this.innerJaw01);
        this.innerJaw02 = this.innerJaw01.getChild("innerJaw02");
        this.parts.put("innerJaw02", this.innerJaw02);
        this.lowerJaw = this.head.getChild("lowerJaw");
        this.parts.put("lowerJaw", this.lowerJaw);
        this.lowerTeeth01 = this.lowerJaw.getChild("lowerTeeth01");
        this.parts.put("lowerTeeth01", this.lowerTeeth01);
        this.lowerJaw02 = this.lowerJaw.getChild("lowerJaw02");
        this.parts.put("lowerJaw02", this.lowerJaw02);
        this.lowerTeeth02 = this.lowerJaw.getChild("lowerTeeth02");
        this.parts.put("lowerTeeth02", this.lowerTeeth02);
        this.head01 = this.head.getChild("head01");
        this.parts.put("head01", this.head01);
        this.head02 = this.head01.getChild("head02");
        this.parts.put("head02", this.head02);
        this.head03 = this.head02.getChild("head03");
        this.parts.put("head03", this.head03);
        this.head04 = this.head03.getChild("head04");
        this.parts.put("head04", this.head04);
        this.jawTendon01 = this.head.getChild("jawTendon01");
        this.parts.put("jawTendon01", this.jawTendon01);
        this.jawTendon02 = this.jawTendon01.getChild("jawTendon02");
        this.parts.put("jawTendon02", this.jawTendon02);
        this.muzzle = this.head.getChild("muzzle");
        this.parts.put("muzzle", this.muzzle);
        this.upperJaw = this.head.getChild("upperJaw");
        this.parts.put("upperJaw", this.upperJaw);
        this.lShoulderRidge01 = this.chest01.getChild("lShoulderRidge01");
        this.parts.put("lShoulderRidge01", this.lShoulderRidge01);
        this.lShoulderRidge02 = this.lShoulderRidge01.getChild("lShoulderRidge02");
        this.parts.put("lShoulderRidge02", this.lShoulderRidge02);
        this.lShoulderRidge03 = this.lShoulderRidge02.getChild("lShoulderRidge03");
        this.parts.put("lShoulderRidge03", this.lShoulderRidge03);
        this.lShoulder = this.chest01.getChild("lShoulder");
        this.parts.put("lShoulder", this.lShoulder);
        this.lUpperArm = this.lShoulder.getChild("lUpperArm");
        this.parts.put("lUpperArm", this.lUpperArm);
        this.lLowerArm = this.lUpperArm.getChild("lLowerArm");
        this.parts.put("lLowerArm", this.lLowerArm);
        this.lHand = this.lLowerArm.getChild("lHand");
        this.parts.put("lHand", this.lHand);
        this.lFinger04 = this.lHand.getChild("lFinger04");
        this.parts.put("lFinger04", this.lFinger04);
        this.lFinger01 = this.lHand.getChild("lFinger01");
        this.parts.put("lFinger01", this.lFinger01);
        this.lFinger02 = this.lHand.getChild("lFinger02");
        this.parts.put("lFinger02", this.lFinger02);
        this.lFinger03 = this.lHand.getChild("lFinger03");
        this.parts.put("lFinger03", this.lFinger03);
        this.lFinger05 = this.lHand.getChild("lFinger05");
        this.parts.put("lFinger05", this.lFinger05);
        this.lArmSpike = this.lLowerArm.getChild("lArmSpike");
        this.parts.put("lArmSpike", this.lArmSpike);
        this.rShoulderRidge01 = this.chest01.getChild("rShoulderRidge01");
        this.parts.put("rShoulderRidge01", this.rShoulderRidge01);
        this.rShoulderRidge02 = this.rShoulderRidge01.getChild("rShoulderRidge02");
        this.parts.put("rShoulderRidge02", this.rShoulderRidge02);
        this.rShoulderRidge03 = this.rShoulderRidge02.getChild("rShoulderRidge03");
        this.parts.put("rShoulderRidge03", this.rShoulderRidge03);
        this.chest02 = this.chest01.getChild("chest02");
        this.parts.put("chest02", this.chest02);
        this.stomach = this.chest02.getChild("stomach");
        this.parts.put("stomach", this.stomach);
        this.hips = this.stomach.getChild("hips");
        this.parts.put("hips", this.hips);
        this.tail01a = this.hips.getChild("tail01a");
        this.parts.put("tail01a", this.tail01a);
        this.tail01c = this.tail01a.getChild("tail01c");
        this.parts.put("tail01c", this.tail01c);
        this.tail02a = this.tail01a.getChild("tail02a");
        this.parts.put("tail02a", this.tail02a);
        this.tail02b = this.tail02a.getChild("tail02b");
        this.parts.put("tail02b", this.tail02b);
        this.tail02c = this.tail02a.getChild("tail02c");
        this.parts.put("tail02c", this.tail02c);
        this.tail02d = this.tail02a.getChild("tail02d");
        this.parts.put("tail02d", this.tail02d);
        this.tail03 = this.tail02a.getChild("tail03");
        this.parts.put("tail03", this.tail03);
        this.tailBlade01 = this.tail03.getChild("tailBlade01");
        this.parts.put("tailBlade01", this.tailBlade01);
        this.tailBlade02 = this.tailBlade01.getChild("tailBlade02");
        this.parts.put("tailBlade02", this.tailBlade02);
        this.tail01b = this.tail01a.getChild("tail01b");
        this.parts.put("tail01b", this.tail01b);
        this.tail01d = this.tail01a.getChild("tail01d");
        this.parts.put("tail01d", this.tail01d);
        this.rThigh = this.hips.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.rShin = this.rThigh.getChild("rShin");
        this.parts.put("rShin", this.rShin);
        this.rFoot01 = this.rShin.getChild("rFoot01");
        this.parts.put("rFoot01", this.rFoot01);
        this.rFoot02 = this.rFoot01.getChild("rFoot02");
        this.parts.put("rFoot02", this.rFoot02);
        this.lThigh = this.hips.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lShin = this.lThigh.getChild("lShin");
        this.parts.put("lShin", this.lShin);
        this.lFoot01 = this.lShin.getChild("lFoot01");
        this.parts.put("lFoot01", this.lFoot01);
        this.lFoot02 = this.lFoot01.getChild("lFoot02");
        this.parts.put("lFoot02", this.lFoot02);
        this.rHipBone = this.stomach.getChild("rHipBone");
        this.parts.put("rHipBone", this.rHipBone);
        this.lHipBone = this.stomach.getChild("lHipBone");
        this.parts.put("lHipBone", this.lHipBone);
        this.rShoulder = this.chest01.getChild("rShoulder");
        this.parts.put("rShoulder", this.rShoulder);
        this.rUpperArm = this.rShoulder.getChild("rUpperArm");
        this.parts.put("rUpperArm", this.rUpperArm);
        this.rLowerArm = this.rUpperArm.getChild("rLowerArm");
        this.parts.put("rLowerArm", this.rLowerArm);
        this.lArmSpike_1 = this.rLowerArm.getChild("lArmSpike_1");
        this.parts.put("lArmSpike_1", this.lArmSpike_1);
        this.rHand = this.rLowerArm.getChild("rHand");
        this.parts.put("rHand", this.rHand);
        this.rFinger01 = this.rHand.getChild("rFinger01");
        this.parts.put("rFinger01", this.rFinger01);
        this.rFinger02 = this.rHand.getChild("rFinger02");
        this.parts.put("rFinger02", this.rFinger02);
        this.rFinger05 = this.rHand.getChild("rFinger05");
        this.parts.put("rFinger05", this.rFinger05);
        this.rFinger04 = this.rHand.getChild("rFinger04");
        this.parts.put("rFinger04", this.rFinger04);
        this.rFinger03 = this.rHand.getChild("rFinger03");
        this.parts.put("rFinger03", this.rFinger03);
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
        PartDefinition p_chest01 = root.addOrReplaceChild("chest01",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -4.3F, 8.0F, 8.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, -11.4F, 1.0F, 0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_neck = p_chest01.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(33, 0).addBox(-2.5F, -4.1F, -3.0F, 5.0F, 4.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -8.6F, 0.0F, 0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_head = p_neck.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(92, 20).addBox(-3.0F, -5.0F, -4.0F, 6.0F, 5.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, -3.1F, 0.0F, -0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_headLower01 = p_head.addOrReplaceChild("headLower01",
                CubeListBuilder.create().texOffs(73, 58).addBox(-2.0F, -0.2F, 0.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, -0.9F, 2.7F));
        PartDefinition p_headLower02 = p_headLower01.addOrReplaceChild("headLower02",
                CubeListBuilder.create().texOffs(91, 59).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.2F, 2.9F));
        PartDefinition p_headLower03 = p_headLower02.addOrReplaceChild("headLower03",
                CubeListBuilder.create().texOffs(109, 59).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -0.1F, 3.8F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_innerJaw01 = p_head.addOrReplaceChild("innerJaw01",
                CubeListBuilder.create().texOffs(106, 36).addBox(-1.78F, -1.8F, -5.1F, 3.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, -1.3F, -1.9F));
        PartDefinition p_innerJaw02 = p_innerJaw01.addOrReplaceChild("innerJaw02",
                CubeListBuilder.create().texOffs(115, 28).addBox(0.88F, -1.8F, -5.1F, 1.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lowerJaw = p_head.addOrReplaceChild("lowerJaw",
                CubeListBuilder.create().texOffs(86, 53).addBox(-1.78F, -0.5F, -4.0F, 3.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, -0.9F, -3.1F));
        PartDefinition p_lowerTeeth01 = p_lowerJaw.addOrReplaceChild("lowerTeeth01",
                CubeListBuilder.create().texOffs(30, 54).addBox(-1.75F, -1.1F, -3.8F, 3.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lowerJaw02 = p_lowerJaw.addOrReplaceChild("lowerJaw02",
                CubeListBuilder.create().texOffs(104, 53).addBox(0.88F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lowerTeeth02 = p_lowerJaw.addOrReplaceChild("lowerTeeth02",
                CubeListBuilder.create().texOffs(30, 59).addBox(0.85F, -1.1F, -3.8F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_head01 = p_head.addOrReplaceChild("head01",
                CubeListBuilder.create().texOffs(73, 21).addBox(-2.5F, -1.7F, 0.0F, 5.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -3.0F, 2.7F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_head02 = p_head01.addOrReplaceChild("head02",
                CubeListBuilder.create().texOffs(66, 31).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 3.9F, -0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_head03 = p_head02.addOrReplaceChild("head03",
                CubeListBuilder.create().texOffs(62, 39).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -0.2F, 2.9F, -0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_head04 = p_head03.addOrReplaceChild("head04",
                CubeListBuilder.create().texOffs(76, 39).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.1F, 2.9F, -0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_jawTendon01 = p_head.addOrReplaceChild("jawTendon01",
                CubeListBuilder.create().texOffs(86, 45).addBox(-1.98F, 0.1F, -2.5F, 3.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, -2.3F, -4.6F));
        PartDefinition p_jawTendon02 = p_jawTendon01.addOrReplaceChild("jawTendon02",
                CubeListBuilder.create().texOffs(104, 45).addBox(0.98F, 0.1F, -2.5F, 1.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_muzzle = p_head.addOrReplaceChild("muzzle",
                CubeListBuilder.create().texOffs(112, 49).addBox(-2.0F, -0.54F, -3.4F, 4.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -4.0F, -3.8F, 0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_upperJaw = p_head.addOrReplaceChild("upperJaw",
                CubeListBuilder.create().texOffs(89, 37).addBox(-2.0F, -1.5F, -2.9F, 4.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, -1.9F, -4.3F));
        PartDefinition p_lShoulderRidge01 = p_chest01.addOrReplaceChild("lShoulderRidge01",
                CubeListBuilder.create().texOffs(31, 25).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(3.5F, -8.1F, -1.9F, 0.36425021489121656F, 0.0F, 0.7740535232594852F));
        PartDefinition p_lShoulderRidge02 = p_lShoulderRidge01.addOrReplaceChild("lShoulderRidge02",
                CubeListBuilder.create().texOffs(31, 33).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -1.1F, 1.6F, -0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_lShoulderRidge03 = p_lShoulderRidge02.addOrReplaceChild("lShoulderRidge03",
                CubeListBuilder.create().texOffs(31, 41).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.1F, 3.7F, -0.18203784098300857F, 0.18203784098300857F, 0.7853981633974483F));
        PartDefinition p_lShoulder = p_chest01.addOrReplaceChild("lShoulder",
                CubeListBuilder.create().texOffs(45, 38).addBox(0.0F, -1.0F, -2.0F, 3.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(3.0F, -6.6F, 0.0F, 0.045553093477052F, 0.0F, -0.36425021489121656F));
        PartDefinition p_lUpperArm = p_lShoulder.addOrReplaceChild("lUpperArm",
                CubeListBuilder.create().texOffs(45, 49).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(1.5F, 3.6F, 0.0F, -0.091106186954104F, 0.0F, 0.136659280431156F));
        PartDefinition p_lLowerArm = p_lUpperArm.addOrReplaceChild("lLowerArm",
                CubeListBuilder.create().texOffs(58, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 6.8F, 0.0F, -0.5009094953223726F, 0.0F, 0.045553093477052F));
        PartDefinition p_lHand = p_lLowerArm.addOrReplaceChild("lHand",
                CubeListBuilder.create().texOffs(67, 49).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 9.5F, 0.0F, 0.27314402793711257F, -0.8196066167365371F, 0.0F));
        PartDefinition p_lFinger04 = p_lHand.addOrReplaceChild("lFinger04",
                CubeListBuilder.create().texOffs(67, 53).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(-1.2F, 3.1F, -1.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lFinger01 = p_lHand.addOrReplaceChild("lFinger01",
                CubeListBuilder.create().texOffs(67, 53).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(1.2F, 3.1F, -1.0F, 0.22759093446006054F, 0.0F, -0.136659280431156F));
        PartDefinition p_lFinger02 = p_lHand.addOrReplaceChild("lFinger02",
                CubeListBuilder.create().texOffs(67, 53).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.5F, 3.4F, -1.0F, 0.22759093446006054F, 0.0F, -0.091106186954104F));
        PartDefinition p_lFinger03 = p_lHand.addOrReplaceChild("lFinger03",
                CubeListBuilder.create().texOffs(67, 53).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(-0.4F, 3.4F, -1.0F, 0.22759093446006054F, 0.0F, -0.045553093477052F));
        PartDefinition p_lFinger05 = p_lHand.addOrReplaceChild("lFinger05",
                CubeListBuilder.create().texOffs(72, 53).addBox(0.0F, 0.0F, -1.0F, 0.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(-1.2F, 1.5F, -0.6F, 0.22759093446006054F, 0.0F, 0.136659280431156F));
        PartDefinition p_lArmSpike = p_lLowerArm.addOrReplaceChild("lArmSpike",
                CubeListBuilder.create().texOffs(32, 47).addBox(-1.0F, -3.1F, -1.0F, 2.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.7F, 0.0F, -0.5009094953223726F, 0.7853981633974483F, -0.31869712141416456F));
        PartDefinition p_rShoulderRidge01 = p_chest01.addOrReplaceChild("rShoulderRidge01",
                CubeListBuilder.create().texOffs(31, 25).mirror().addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(-3.5F, -8.1F, -1.9F, 0.36425021489121656F, 0.0F, -0.7740535232594852F));
        PartDefinition p_rShoulderRidge02 = p_rShoulderRidge01.addOrReplaceChild("rShoulderRidge02",
                CubeListBuilder.create().texOffs(31, 33).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -1.1F, 1.6F, -0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_rShoulderRidge03 = p_rShoulderRidge02.addOrReplaceChild("rShoulderRidge03",
                CubeListBuilder.create().texOffs(31, 41).mirror().addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.1F, 3.7F, -0.18203784098300857F, -0.18203784098300857F, -0.7853981633974483F));
        PartDefinition p_chest02 = p_chest01.addOrReplaceChild("chest02",
                CubeListBuilder.create().texOffs(0, 20).addBox(-3.5F, 0.3F, -3.7F, 7.0F, 2.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, -1.9F, 0.0F, -0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_stomach = p_chest02.addOrReplaceChild("stomach",
                CubeListBuilder.create().texOffs(0, 33).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_hips = p_stomach.addOrReplaceChild("hips",
                CubeListBuilder.create().texOffs(30, 13).addBox(-3.5F, -4.7F, -4.2F, 7.0F, 4.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 10.0F, 1.7F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_tail01a = p_hips.addOrReplaceChild("tail01a",
                CubeListBuilder.create().texOffs(0, 49).addBox(-0.25F, 0.0F, -0.2F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.3F, 1.2F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_tail01c = p_tail01a.addOrReplaceChild("tail01c",
                CubeListBuilder.create().texOffs(0, 54).addBox(-0.25F, 0.0F, -0.8F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail02a = p_tail01a.addOrReplaceChild("tail02a",
                CubeListBuilder.create().texOffs(11, 49).addBox(-0.25F, 0.0F, -0.2F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 2.7F, 0.0F, -0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_tail02b = p_tail02a.addOrReplaceChild("tail02b",
                CubeListBuilder.create().texOffs(16, 49).addBox(-0.75F, 0.0F, -0.2F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail02c = p_tail02a.addOrReplaceChild("tail02c",
                CubeListBuilder.create().texOffs(11, 55).addBox(-0.25F, 0.0F, -0.8F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail02d = p_tail02a.addOrReplaceChild("tail02d",
                CubeListBuilder.create().texOffs(16, 55).addBox(-0.75F, 0.0F, -0.8F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail03 = p_tail02a.addOrReplaceChild("tail03",
                CubeListBuilder.create().texOffs(22, 49).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 3.6F, 0.0F, -0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_tailBlade01 = p_tail03.addOrReplaceChild("tailBlade01",
                CubeListBuilder.create().texOffs(22, 56).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 4.3F, 0.3F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_tailBlade02 = p_tailBlade01.addOrReplaceChild("tailBlade02",
                CubeListBuilder.create().texOffs(21, 60).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.7F, 0.0F, -0.18203784098300857F, 0.7853981633974483F, -0.136659280431156F));
        PartDefinition p_tail01b = p_tail01a.addOrReplaceChild("tail01b",
                CubeListBuilder.create().texOffs(5, 49).addBox(-0.75F, 0.0F, -0.2F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail01d = p_tail01a.addOrReplaceChild("tail01d",
                CubeListBuilder.create().texOffs(5, 54).addBox(-0.75F, 0.0F, -0.8F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rThigh = p_hips.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(57, 0).mirror().addBox(-4.0F, -1.0F, -2.5F, 4.0F, 15.0F, 5.0F),
                PartPose.offsetAndRotation(-0.3F, -1.8F, -0.5F, -0.5462880558742251F, 0.0F, 0.22759093446006054F));
        PartDefinition p_rShin = p_rThigh.addOrReplaceChild("rShin",
                CubeListBuilder.create().texOffs(88, 0).mirror().addBox(-1.5F, -1.0F, -2.0F, 3.0F, 13.0F, 4.0F),
                PartPose.offsetAndRotation(-1.9F, 13.4F, 0.5F, 0.8196066167365371F, 0.0F, -0.27314402793711257F));
        PartDefinition p_rFoot01 = p_rShin.addOrReplaceChild("rFoot01",
                CubeListBuilder.create().texOffs(117, 0).mirror().addBox(-1.0F, 0.0F, -1.5F, 2.0F, 5.0F, 3.0F),
                PartPose.offsetAndRotation(-0.1F, 10.9F, 0.0F, -0.7285004297824331F, 0.045553093477052F, 0.0F));
        PartDefinition p_rFoot02 = p_rFoot01.addOrReplaceChild("rFoot02",
                CubeListBuilder.create().texOffs(107, 10).mirror().addBox(-1.5F, 0.0F, -4.5F, 3.0F, 2.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 4.4F, -0.7F, 0.22759093446006054F, 0.18203784098300857F, 0.045553093477052F));
        PartDefinition p_lThigh = p_hips.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(57, 0).addBox(0.0F, -1.0F, -2.5F, 4.0F, 15.0F, 5.0F),
                PartPose.offsetAndRotation(0.3F, -1.8F, -0.5F, -0.5462880558742251F, 0.0F, -0.22759093446006054F));
        PartDefinition p_lShin = p_lThigh.addOrReplaceChild("lShin",
                CubeListBuilder.create().texOffs(88, 0).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 13.0F, 4.0F),
                PartPose.offsetAndRotation(1.9F, 13.4F, 0.5F, 0.8196066167365371F, 0.0F, 0.27314402793711257F));
        PartDefinition p_lFoot01 = p_lShin.addOrReplaceChild("lFoot01",
                CubeListBuilder.create().texOffs(117, 0).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 5.0F, 3.0F),
                PartPose.offsetAndRotation(0.1F, 10.9F, 0.0F, -0.7285004297824331F, -0.045553093477052F, 0.0F));
        PartDefinition p_lFoot02 = p_lFoot01.addOrReplaceChild("lFoot02",
                CubeListBuilder.create().texOffs(107, 10).addBox(-1.5F, 0.0F, -4.5F, 3.0F, 2.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 4.4F, -0.7F, 0.27314402793711257F, -0.18203784098300857F, -0.045553093477052F));
        PartDefinition p_rHipBone = p_stomach.addOrReplaceChild("rHipBone",
                CubeListBuilder.create().texOffs(46, 26).mirror().addBox(-1.5F, -3.0F, -3.0F, 2.0F, 3.0F, 6.0F),
                PartPose.offsetAndRotation(-2.5F, 7.0F, 0.5F, 0.045553093477052F, 0.0F, -0.7285004297824331F));
        PartDefinition p_lHipBone = p_stomach.addOrReplaceChild("lHipBone",
                CubeListBuilder.create().texOffs(46, 26).addBox(-0.5F, -3.0F, -3.0F, 2.0F, 3.0F, 6.0F),
                PartPose.offsetAndRotation(2.5F, 7.0F, 0.5F, 0.045553093477052F, 0.0F, 0.7285004297824331F));
        PartDefinition p_rShoulder = p_chest01.addOrReplaceChild("rShoulder",
                CubeListBuilder.create().texOffs(45, 38).mirror().addBox(-3.0F, -1.0F, -2.0F, 3.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(-3.0F, -6.6F, 0.0F, 0.045553093477052F, 0.0F, 0.36425021489121656F));
        PartDefinition p_rUpperArm = p_rShoulder.addOrReplaceChild("rUpperArm",
                CubeListBuilder.create().texOffs(45, 49).mirror().addBox(-1.0F, 0.0F, -1.5F, 2.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(-1.5F, 3.6F, 0.0F, -0.091106186954104F, 0.0F, -0.136659280431156F));
        PartDefinition p_rLowerArm = p_rUpperArm.addOrReplaceChild("rLowerArm",
                CubeListBuilder.create().texOffs(58, 49).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 6.8F, 0.0F, -0.5009094953223726F, 0.0F, -0.045553093477052F));
        PartDefinition p_lArmSpike_1 = p_rLowerArm.addOrReplaceChild("lArmSpike_1",
                CubeListBuilder.create().texOffs(32, 47).mirror().addBox(-1.0F, -3.1F, -1.0F, 2.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.7F, 0.0F, -0.5009094953223726F, -0.7853981633974483F, 0.31869712141416456F));
        PartDefinition p_rHand = p_rLowerArm.addOrReplaceChild("rHand",
                CubeListBuilder.create().texOffs(67, 49).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 9.5F, 0.0F, 0.27314402793711257F, 0.8196066167365371F, 0.0F));
        PartDefinition p_rFinger01 = p_rHand.addOrReplaceChild("rFinger01",
                CubeListBuilder.create().texOffs(67, 53).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(-1.2F, 3.1F, -1.0F, 0.22759093446006054F, 0.0F, 0.136659280431156F));
        PartDefinition p_rFinger02 = p_rHand.addOrReplaceChild("rFinger02",
                CubeListBuilder.create().texOffs(67, 53).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(-0.5F, 3.4F, -1.0F, 0.22759093446006054F, 0.0F, 0.091106186954104F));
        PartDefinition p_rFinger05 = p_rHand.addOrReplaceChild("rFinger05",
                CubeListBuilder.create().texOffs(72, 53).addBox(0.0F, 0.0F, -1.0F, 0.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(1.2F, 1.5F, -0.6F, 0.22759093446006054F, 0.0F, -0.136659280431156F));
        PartDefinition p_rFinger04 = p_rHand.addOrReplaceChild("rFinger04",
                CubeListBuilder.create().texOffs(67, 53).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(1.2F, 3.1F, -1.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rFinger03 = p_rHand.addOrReplaceChild("rFinger03",
                CubeListBuilder.create().texOffs(67, 53).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.4F, 3.4F, -1.0F, 0.22759093446006054F, 0.0F, 0.045553093477052F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
