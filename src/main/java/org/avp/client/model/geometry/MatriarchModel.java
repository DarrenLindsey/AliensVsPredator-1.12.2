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
 * Geometry converted automatically from the 1.12.2 ModelMatriarch by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class MatriarchModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "matriarch"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart torso0;
    public final ModelPart torso1;
    public final ModelPart rThigh;
    public final ModelPart lThigh;
    public final ModelPart lShin0;
    public final ModelPart lShin1;
    public final ModelPart lFoot;
    public final ModelPart rShin0;
    public final ModelPart rShin1;
    public final ModelPart rFoot;
    public final ModelPart lArm0;
    public final ModelPart lArm1;
    public final ModelPart lHand;
    public final ModelPart lClaw;
    public final ModelPart rArm0;
    public final ModelPart rArm1;
    public final ModelPart rHand;
    public final ModelPart rClaw;
    public final ModelPart tail0;
    public final ModelPart tail1;
    public final ModelPart tail2;
    public final ModelPart tail3;
    public final ModelPart tail4;
    public final ModelPart tailStabber;
    public final ModelPart bStabber0;
    public final ModelPart bStabber1;
    public final ModelPart bStabber2;
    public final ModelPart bStabber3;
    public final ModelPart bStabber4;
    public final ModelPart bStabber5;
    public final ModelPart bSpines0;
    public final ModelPart bSpines1;
    public final ModelPart bSpines2;
    public final ModelPart bSpines3;
    public final ModelPart bSpines4;
    public final ModelPart bSpines5;
    public final ModelPart bSpines6;
    public final ModelPart lArmMini0;
    public final ModelPart lArmMini1;
    public final ModelPart rArmMini0;
    public final ModelPart rArmMini1;
    public final ModelPart lHandMini;
    public final ModelPart rHandMini;
    public final ModelPart lClawMini;
    public final ModelPart rClawMini;
    public final ModelPart neck;
    public final ModelPart Jaw1;
    public final ModelPart head0;
    public final ModelPart head1;
    public final ModelPart headBase0;
    public final ModelPart headFinL0;
    public final ModelPart headFinL1;
    public final ModelPart headFinL2;
    public final ModelPart headFinR0;
    public final ModelPart headFinR1;
    public final ModelPart headFinR2;
    public final ModelPart headWebL;
    public final ModelPart headSpinesL;
    public final ModelPart headSpinesM;
    public final ModelPart headSpinesR;
    public final ModelPart headWebR;
    public final ModelPart rHip;
    public final ModelPart lHip;
    public final ModelPart lShinSpike;
    public final ModelPart rShinSpike;
    public final ModelPart sack0;
    public final ModelPart sack7;
    public final ModelPart sack1;
    public final ModelPart sack2;
    public final ModelPart sack3;
    public final ModelPart sack4;
    public final ModelPart sack5;
    public final ModelPart sack6;

    public MatriarchModel(ModelPart root)
    {
        this.root = root;
        this.torso0 = root.getChild("torso0");
        this.parts.put("torso0", this.torso0);
        this.torso1 = root.getChild("torso1");
        this.parts.put("torso1", this.torso1);
        this.rThigh = root.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.lThigh = root.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lShin0 = root.getChild("lShin0");
        this.parts.put("lShin0", this.lShin0);
        this.lShin1 = root.getChild("lShin1");
        this.parts.put("lShin1", this.lShin1);
        this.lFoot = root.getChild("lFoot");
        this.parts.put("lFoot", this.lFoot);
        this.rShin0 = root.getChild("rShin0");
        this.parts.put("rShin0", this.rShin0);
        this.rShin1 = root.getChild("rShin1");
        this.parts.put("rShin1", this.rShin1);
        this.rFoot = root.getChild("rFoot");
        this.parts.put("rFoot", this.rFoot);
        this.lArm0 = root.getChild("lArm0");
        this.parts.put("lArm0", this.lArm0);
        this.lArm1 = root.getChild("lArm1");
        this.parts.put("lArm1", this.lArm1);
        this.lHand = root.getChild("lHand");
        this.parts.put("lHand", this.lHand);
        this.lClaw = root.getChild("lClaw");
        this.parts.put("lClaw", this.lClaw);
        this.rArm0 = root.getChild("rArm0");
        this.parts.put("rArm0", this.rArm0);
        this.rArm1 = root.getChild("rArm1");
        this.parts.put("rArm1", this.rArm1);
        this.rHand = root.getChild("rHand");
        this.parts.put("rHand", this.rHand);
        this.rClaw = root.getChild("rClaw");
        this.parts.put("rClaw", this.rClaw);
        this.tail0 = root.getChild("tail0");
        this.parts.put("tail0", this.tail0);
        this.tail1 = root.getChild("tail1");
        this.parts.put("tail1", this.tail1);
        this.tail2 = root.getChild("tail2");
        this.parts.put("tail2", this.tail2);
        this.tail3 = root.getChild("tail3");
        this.parts.put("tail3", this.tail3);
        this.tail4 = root.getChild("tail4");
        this.parts.put("tail4", this.tail4);
        this.tailStabber = root.getChild("tailStabber");
        this.parts.put("tailStabber", this.tailStabber);
        this.bStabber0 = root.getChild("bStabber0");
        this.parts.put("bStabber0", this.bStabber0);
        this.bStabber1 = root.getChild("bStabber1");
        this.parts.put("bStabber1", this.bStabber1);
        this.bStabber2 = root.getChild("bStabber2");
        this.parts.put("bStabber2", this.bStabber2);
        this.bStabber3 = root.getChild("bStabber3");
        this.parts.put("bStabber3", this.bStabber3);
        this.bStabber4 = root.getChild("bStabber4");
        this.parts.put("bStabber4", this.bStabber4);
        this.bStabber5 = root.getChild("bStabber5");
        this.parts.put("bStabber5", this.bStabber5);
        this.bSpines0 = root.getChild("bSpines0");
        this.parts.put("bSpines0", this.bSpines0);
        this.bSpines1 = root.getChild("bSpines1");
        this.parts.put("bSpines1", this.bSpines1);
        this.bSpines2 = root.getChild("bSpines2");
        this.parts.put("bSpines2", this.bSpines2);
        this.bSpines3 = root.getChild("bSpines3");
        this.parts.put("bSpines3", this.bSpines3);
        this.bSpines4 = root.getChild("bSpines4");
        this.parts.put("bSpines4", this.bSpines4);
        this.bSpines5 = root.getChild("bSpines5");
        this.parts.put("bSpines5", this.bSpines5);
        this.bSpines6 = root.getChild("bSpines6");
        this.parts.put("bSpines6", this.bSpines6);
        this.lArmMini0 = root.getChild("lArmMini0");
        this.parts.put("lArmMini0", this.lArmMini0);
        this.lArmMini1 = root.getChild("lArmMini1");
        this.parts.put("lArmMini1", this.lArmMini1);
        this.rArmMini0 = root.getChild("rArmMini0");
        this.parts.put("rArmMini0", this.rArmMini0);
        this.rArmMini1 = root.getChild("rArmMini1");
        this.parts.put("rArmMini1", this.rArmMini1);
        this.lHandMini = root.getChild("lHandMini");
        this.parts.put("lHandMini", this.lHandMini);
        this.rHandMini = root.getChild("rHandMini");
        this.parts.put("rHandMini", this.rHandMini);
        this.lClawMini = root.getChild("lClawMini");
        this.parts.put("lClawMini", this.lClawMini);
        this.rClawMini = root.getChild("rClawMini");
        this.parts.put("rClawMini", this.rClawMini);
        this.neck = root.getChild("neck");
        this.parts.put("neck", this.neck);
        this.Jaw1 = root.getChild("Jaw1");
        this.parts.put("Jaw1", this.Jaw1);
        this.head0 = root.getChild("head0");
        this.parts.put("head0", this.head0);
        this.head1 = root.getChild("head1");
        this.parts.put("head1", this.head1);
        this.headBase0 = root.getChild("headBase0");
        this.parts.put("headBase0", this.headBase0);
        this.headFinL0 = root.getChild("headFinL0");
        this.parts.put("headFinL0", this.headFinL0);
        this.headFinL1 = root.getChild("headFinL1");
        this.parts.put("headFinL1", this.headFinL1);
        this.headFinL2 = root.getChild("headFinL2");
        this.parts.put("headFinL2", this.headFinL2);
        this.headFinR0 = root.getChild("headFinR0");
        this.parts.put("headFinR0", this.headFinR0);
        this.headFinR1 = root.getChild("headFinR1");
        this.parts.put("headFinR1", this.headFinR1);
        this.headFinR2 = root.getChild("headFinR2");
        this.parts.put("headFinR2", this.headFinR2);
        this.headWebL = root.getChild("headWebL");
        this.parts.put("headWebL", this.headWebL);
        this.headSpinesL = root.getChild("headSpinesL");
        this.parts.put("headSpinesL", this.headSpinesL);
        this.headSpinesM = root.getChild("headSpinesM");
        this.parts.put("headSpinesM", this.headSpinesM);
        this.headSpinesR = root.getChild("headSpinesR");
        this.parts.put("headSpinesR", this.headSpinesR);
        this.headWebR = root.getChild("headWebR");
        this.parts.put("headWebR", this.headWebR);
        this.rHip = root.getChild("rHip");
        this.parts.put("rHip", this.rHip);
        this.lHip = root.getChild("lHip");
        this.parts.put("lHip", this.lHip);
        this.lShinSpike = root.getChild("lShinSpike");
        this.parts.put("lShinSpike", this.lShinSpike);
        this.rShinSpike = root.getChild("rShinSpike");
        this.parts.put("rShinSpike", this.rShinSpike);
        this.sack0 = root.getChild("sack0");
        this.parts.put("sack0", this.sack0);
        this.sack7 = root.getChild("sack7");
        this.parts.put("sack7", this.sack7);
        this.sack1 = root.getChild("sack1");
        this.parts.put("sack1", this.sack1);
        this.sack2 = root.getChild("sack2");
        this.parts.put("sack2", this.sack2);
        this.sack3 = root.getChild("sack3");
        this.parts.put("sack3", this.sack3);
        this.sack4 = root.getChild("sack4");
        this.parts.put("sack4", this.sack4);
        this.sack5 = root.getChild("sack5");
        this.parts.put("sack5", this.sack5);
        this.sack6 = root.getChild("sack6");
        this.parts.put("sack6", this.sack6);
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
        PartDefinition p_torso0 = root.addOrReplaceChild("torso0",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -2.0F, 0.0F, 9.0F, 8.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -1.0F, -0.4363323F, 0.0F, 0.0F));
        PartDefinition p_torso1 = root.addOrReplaceChild("torso1",
                CubeListBuilder.create().texOffs(39, 0).addBox(-3.5F, -7.0F, 8.0F, 7.0F, 6.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, -6.0F, -3.0F, -0.2792527F, 0.0F, 0.0F));
        PartDefinition p_rThigh = root.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(185, 0).addBox(-2.5F, -4.0F, -4.5F, 4.0F, 17.0F, 5.0F),
                PartPose.offsetAndRotation(-4.5F, 0.0F, 16.0F, -0.7330383F, 0.2443461F, 0.418879F));
        PartDefinition p_lThigh = root.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(204, 0).addBox(-1.5F, -4.0F, -4.5F, 4.0F, 17.0F, 5.0F),
                PartPose.offsetAndRotation(4.5F, 0.0F, 16.0F, -0.7330383F, -0.2443461F, -0.418879F));
        PartDefinition p_lShin0 = root.addOrReplaceChild("lShin0",
                CubeListBuilder.create().texOffs(78, 0).addBox(-1.0F, 12.0F, -4.5F, 3.0F, 3.0F, 14.0F),
                PartPose.offsetAndRotation(4.5F, 0.0F, 16.0F, -0.6981317F, -0.2443461F, -0.418879F));
        PartDefinition p_lShin1 = root.addOrReplaceChild("lShin1",
                CubeListBuilder.create().texOffs(148, 0).addBox(-0.5F, 10.0F, 11.4F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(4.5F, 0.0F, 16.0F, -0.9599311F, -0.2443461F, -0.418879F));
        PartDefinition p_lFoot = root.addOrReplaceChild("lFoot",
                CubeListBuilder.create().texOffs(166, 0).addBox(10.5F, 20.1F, -13.3F, 3.0F, 2.0F, 6.0F),
                PartPose.offset(4.5F, 0.0F, 16.0F));
        PartDefinition p_rShin0 = root.addOrReplaceChild("rShin0",
                CubeListBuilder.create().texOffs(113, 0).addBox(-2.0F, 12.0F, -4.5F, 3.0F, 3.0F, 14.0F),
                PartPose.offsetAndRotation(-4.5F, 0.0F, 16.0F, -0.6981317F, 0.2443461F, 0.418879F));
        PartDefinition p_rShin1 = root.addOrReplaceChild("rShin1",
                CubeListBuilder.create().texOffs(157, 0).addBox(-1.5F, 10.0F, 11.4F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(-4.5F, 0.0F, 16.0F, -0.9599311F, 0.2443461F, 0.418879F));
        PartDefinition p_rFoot = root.addOrReplaceChild("rFoot",
                CubeListBuilder.create().texOffs(166, 9).addBox(-13.5F, 20.1F, -13.3F, 3.0F, 2.0F, 6.0F),
                PartPose.offset(-4.5F, 0.0F, 16.0F));
        PartDefinition p_lArm0 = root.addOrReplaceChild("lArm0",
                CubeListBuilder.create().texOffs(67, 19).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(4.5F, -8.0F, 1.0F, 0.3665191F, 0.0F, -0.2443461F));
        PartDefinition p_lArm1 = root.addOrReplaceChild("lArm1",
                CubeListBuilder.create().texOffs(123, 19).addBox(-1.0F, 9.5F, -10.5F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(4.5F, -8.0F, 1.0F, 0.3665191F, 0.0F, -0.2443461F));
        PartDefinition p_lHand = root.addOrReplaceChild("lHand",
                CubeListBuilder.create().texOffs(85, 19).addBox(-1.0F, 10.0F, -13.5F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(4.5F, -8.0F, 1.0F, 0.3665191F, 0.0F, -0.2443461F));
        PartDefinition p_lClaw = root.addOrReplaceChild("lClaw",
                CubeListBuilder.create().texOffs(185, 23).addBox(-2.0F, 10.5F, -16.5F, 3.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(4.5F, -8.0F, 1.0F, 0.3665191F, 0.0F, -0.2443461F));
        PartDefinition p_rArm0 = root.addOrReplaceChild("rArm0",
                CubeListBuilder.create().texOffs(76, 19).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(-4.5F, -8.0F, 1.0F, 0.3665191F, 0.0F, 0.2443461F));
        PartDefinition p_rArm1 = root.addOrReplaceChild("rArm1",
                CubeListBuilder.create().texOffs(96, 19).addBox(-1.0F, 9.5F, -10.5F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(-4.5F, -8.0F, 1.0F, 0.3665191F, 0.0F, 0.2443461F));
        PartDefinition p_rHand = root.addOrReplaceChild("rHand",
                CubeListBuilder.create().texOffs(85, 24).addBox(-1.0F, 10.0F, -13.5F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-4.5F, -8.0F, 1.0F, 0.3665191F, 0.0F, 0.2443461F));
        PartDefinition p_rClaw = root.addOrReplaceChild("rClaw",
                CubeListBuilder.create().texOffs(204, 23).addBox(-1.0F, 10.5F, -16.5F, 3.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(-4.5F, -8.0F, 1.0F, 0.3665191F, 0.0F, 0.2443461F));
        PartDefinition p_tail0 = root.addOrReplaceChild("tail0",
                CubeListBuilder.create().texOffs(175, 47).addBox(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, -3.5F, 16.5F, -0.2792527F, 0.0F, 0.0F));
        PartDefinition p_tail1 = root.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(144, 47).addBox(-2.0F, -3.5F, 8.0F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 18.5F, -0.2268928F, 0.0F, 0.0F));
        PartDefinition p_tail2 = root.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(115, 47).addBox(-1.5F, 1.5F, 18.0F, 3.0F, 3.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, 19.5F, -0.122173F, 0.0F, 0.0F));
        PartDefinition p_tail3 = root.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(88, 47).addBox(-1.0F, 2.0F, 28.0F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 18.5F, -0.0523599F, 0.0F, 0.0F));
        PartDefinition p_tail4 = root.addOrReplaceChild("tail4",
                CubeListBuilder.create().texOffs(63, 47).addBox(-0.5F, 5.5F, 38.0F, 1.0F, 1.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 18.5F, 0.0349066F, 0.0F, 0.0F));
        PartDefinition p_tailStabber = root.addOrReplaceChild("tailStabber",
                CubeListBuilder.create().texOffs(211, 0).addBox(-2.0F, 6.0F, 48.0F, 4.0F, 0.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 18.5F, 0.0349066F, 0.0F, 0.0F));
        PartDefinition p_bStabber0 = root.addOrReplaceChild("bStabber0",
                CubeListBuilder.create().texOffs(249, 22).addBox(0.0F, -14.0F, 0.0F, 3.0F, 14.0F, 0.0F),
                PartPose.offsetAndRotation(2.0F, -8.3F, 14.0F, 0.3665191F, -2.007129F, 0.7504916F));
        PartDefinition p_bStabber1 = root.addOrReplaceChild("bStabber1",
                CubeListBuilder.create().texOffs(235, 13).addBox(0.0F, -17.0F, 0.0F, 3.0F, 18.0F, 0.0F),
                PartPose.offsetAndRotation(2.0F, -9.3F, 10.0F, 0.3316126F, -1.902409F, 0.4537856F));
        PartDefinition p_bStabber2 = root.addOrReplaceChild("bStabber2",
                CubeListBuilder.create().texOffs(249, 0).addBox(0.0F, -19.0F, 0.0F, 3.0F, 21.0F, 0.0F),
                PartPose.offsetAndRotation(3.0F, -12.3F, 6.0F, 0.2792527F, -1.570796F, 0.3490659F));
        PartDefinition p_bStabber3 = root.addOrReplaceChild("bStabber3",
                CubeListBuilder.create().texOffs(242, 22).addBox(0.0F, -14.0F, 0.0F, 3.0F, 14.0F, 0.0F),
                PartPose.offsetAndRotation(-2.0F, -8.3F, 14.0F, -0.3665191F, -1.134464F, 0.7504916F));
        PartDefinition p_bStabber4 = root.addOrReplaceChild("bStabber4",
                CubeListBuilder.create().texOffs(228, 13).addBox(0.0F, -17.0F, 0.0F, 3.0F, 18.0F, 0.0F),
                PartPose.offsetAndRotation(-2.0F, -9.3F, 10.0F, -0.3316126F, -1.239184F, 0.4537856F));
        PartDefinition p_bStabber5 = root.addOrReplaceChild("bStabber5",
                CubeListBuilder.create().texOffs(242, 0).addBox(0.0F, -20.0F, 0.0F, 3.0F, 21.0F, 0.0F),
                PartPose.offsetAndRotation(-3.0F, -12.3F, 6.0F, -0.2792527F, -1.570796F, 0.3490659F));
        PartDefinition p_bSpines0 = root.addOrReplaceChild("bSpines0",
                CubeListBuilder.create().texOffs(210, 25).addBox(0.0F, -10.0F, -2.0F, 0.0F, 8.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -9.5F, 0.0F, -0.4712389F, 0.0F, 0.0F));
        PartDefinition p_bSpines1 = root.addOrReplaceChild("bSpines1",
                CubeListBuilder.create().texOffs(185, 23).addBox(0.0F, -13.0F, 6.0F, 0.0F, 6.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -0.3141593F, 0.0F, 0.0F));
        PartDefinition p_bSpines2 = root.addOrReplaceChild("bSpines2",
                CubeListBuilder.create().texOffs(162, 24).addBox(-1.0F, -21.0F, 12.0F, 0.0F, 5.0F, 11.0F),
                PartPose.offsetAndRotation(1.0F, 7.0F, 0.0F, -0.3141593F, 0.0F, 0.0F));
        PartDefinition p_bSpines3 = root.addOrReplaceChild("bSpines3",
                CubeListBuilder.create().texOffs(139, 24).addBox(-1.0F, -17.8F, 25.0F, 0.0F, 5.0F, 11.0F),
                PartPose.offsetAndRotation(1.0F, 7.0F, 0.0F, -0.2268928F, 0.0F, 0.0F));
        PartDefinition p_bSpines4 = root.addOrReplaceChild("bSpines4",
                CubeListBuilder.create().texOffs(118, 25).addBox(-1.0F, -13.8F, 37.4F, 0.0F, 4.0F, 10.0F),
                PartPose.offsetAndRotation(1.0F, 7.0F, 0.0F, -0.1396263F, 0.0F, 0.0F));
        PartDefinition p_bSpines5 = root.addOrReplaceChild("bSpines5",
                CubeListBuilder.create().texOffs(97, 25).addBox(-1.0F, -12.2F, 47.8F, 0.0F, 3.0F, 10.0F),
                PartPose.offsetAndRotation(1.0F, 7.0F, 0.0F, -0.1047198F, 0.0F, 0.0F));
        PartDefinition p_bSpines6 = root.addOrReplaceChild("bSpines6",
                CubeListBuilder.create().texOffs(74, 24).addBox(-1.0F, -3.2F, 57.0F, 0.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(1.0F, 6.0F, 0.0F, 0.0174533F, 0.0F, 0.0F));
        PartDefinition p_lArmMini0 = root.addOrReplaceChild("lArmMini0",
                CubeListBuilder.create().texOffs(0, 40).addBox(0.0F, 0.0F, 0.0F, 2.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(1.0F, -6.0F, 0.0F, -0.0523599F, 0.0F, -0.2443461F));
        PartDefinition p_lArmMini1 = root.addOrReplaceChild("lArmMini1",
                CubeListBuilder.create().texOffs(0, 33).addBox(0.0F, 3.5F, 3.2F, 2.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(1.0F, -6.0F, 0.0F, -0.715585F, 0.0F, -0.2443461F));
        PartDefinition p_rArmMini0 = root.addOrReplaceChild("rArmMini0",
                CubeListBuilder.create().texOffs(9, 40).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(-1.0F, -6.0F, 0.0F, -0.0523599F, 0.0F, 0.2443461F));
        PartDefinition p_rArmMini1 = root.addOrReplaceChild("rArmMini1",
                CubeListBuilder.create().texOffs(9, 33).addBox(-2.0F, 3.5F, 3.2F, 2.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(-1.0F, -6.0F, 0.0F, -0.715585F, 0.0F, 0.2443461F));
        PartDefinition p_lHandMini = root.addOrReplaceChild("lHandMini",
                CubeListBuilder.create().texOffs(0, 49).addBox(0.0F, 6.5F, -7.0F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(1.0F, -6.0F, 0.0F, 0.3665191F, 0.0F, -0.2443461F));
        PartDefinition p_rHandMini = root.addOrReplaceChild("rHandMini",
                CubeListBuilder.create().texOffs(0, 54).addBox(-2.0F, 6.5F, -7.0F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-1.0F, -6.0F, 0.0F, 0.3665191F, 0.0F, 0.2443461F));
        PartDefinition p_lClawMini = root.addOrReplaceChild("lClawMini",
                CubeListBuilder.create().texOffs(0, 19).addBox(-1.0F, 7.0F, -10.0F, 3.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(1.0F, -6.0F, 0.0F, 0.3665191F, 0.0F, -0.2443461F));
        PartDefinition p_rClawMini = root.addOrReplaceChild("rClawMini",
                CubeListBuilder.create().texOffs(0, 26).addBox(-2.0F, 7.0F, -10.0F, 3.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(-1.0F, -6.0F, 0.0F, 0.3665191F, 0.0F, 0.2443461F));
        PartDefinition p_neck = root.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(0, 77).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 5.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -11.5F, -2.0F, -0.1919862F, 0.0F, 0.0F));
        PartDefinition p_Jaw1 = root.addOrReplaceChild("Jaw1",
                CubeListBuilder.create().texOffs(19, 77).addBox(-1.5F, 5.6F, -8.5F, 3.0F, 4.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -6.0F, 0.3490659F, 0.0F, 0.0F));
        PartDefinition p_head0 = root.addOrReplaceChild("head0",
                CubeListBuilder.create().texOffs(19, 19).addBox(-3.5F, -5.0F, -3.0F, 7.0F, 11.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -6.0F, -0.6283185F, 0.0F, 0.0F));
        PartDefinition p_head1 = root.addOrReplaceChild("head1",
                CubeListBuilder.create().texOffs(44, 19).addBox(-2.5F, 4.0F, -6.0F, 5.0F, 5.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -5.0F, -0.122173F, 0.0F, 0.0F));
        PartDefinition p_headBase0 = root.addOrReplaceChild("headBase0",
                CubeListBuilder.create().texOffs(19, 36).addBox(-3.5F, -23.0F, -4.2F, 7.0F, 19.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -6.0F, -0.9075712F, 0.0F, 0.0F));
        PartDefinition p_headFinL0 = root.addOrReplaceChild("headFinL0",
                CubeListBuilder.create().texOffs(0, 59).addBox(0.0F, -3.0F, -2.5F, 5.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -6.0F, -0.7853982F, 0.2617994F, 0.8552113F));
        PartDefinition p_headFinL1 = root.addOrReplaceChild("headFinL1",
                CubeListBuilder.create().texOffs(15, 61).addBox(2.0F, -10.0F, -3.6F, 4.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -6.0F, -0.9075712F, -0.1570796F, 0.2268928F));
        PartDefinition p_headFinL2 = root.addOrReplaceChild("headFinL2",
                CubeListBuilder.create().texOffs(44, 31).addBox(-3.5F, -23.0F, -3.0F, 7.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -6.0F, -0.9250245F, 0.0698132F, 0.3490659F));
        PartDefinition p_headFinR0 = root.addOrReplaceChild("headFinR0",
                CubeListBuilder.create().texOffs(0, 67).addBox(-5.0F, -3.0F, -2.5F, 5.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -6.0F, -0.7853982F, -0.2617994F, -0.8552113F));
        PartDefinition p_headFinR1 = root.addOrReplaceChild("headFinR1",
                CubeListBuilder.create().texOffs(28, 61).addBox(-5.8F, -10.0F, -3.6F, 4.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -6.0F, -0.9075712F, 0.1570796F, -0.2268928F));
        PartDefinition p_headFinR2 = root.addOrReplaceChild("headFinR2",
                CubeListBuilder.create().texOffs(44, 47).addBox(-3.5F, -23.0F, -3.0F, 7.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -6.0F, -0.9250245F, -0.0698132F, -0.3490659F));
        PartDefinition p_headWebL = root.addOrReplaceChild("headWebL",
                CubeListBuilder.create().texOffs(15, 70).addBox(1.8F, -5.0F, -1.2F, 3.0F, 6.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -6.0F, -0.6283185F, -0.122173F, 0.2268928F));
        PartDefinition p_headSpinesL = root.addOrReplaceChild("headSpinesL",
                CubeListBuilder.create().texOffs(63, 18).addBox(1.0F, -6.0F, -1.0F, 0.0F, 4.0F, 24.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -6.0F, 0.6981317F, 0.0F, 0.296706F));
        PartDefinition p_headSpinesM = root.addOrReplaceChild("headSpinesM",
                CubeListBuilder.create().texOffs(112, 18).addBox(0.0F, -6.0F, -1.0F, 0.0F, 4.0F, 24.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -6.0F, 0.6981317F, 0.0F, 0.0F));
        PartDefinition p_headSpinesR = root.addOrReplaceChild("headSpinesR",
                CubeListBuilder.create().texOffs(161, 18).addBox(-1.0F, -6.0F, -1.0F, 0.0F, 4.0F, 24.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -6.0F, 0.6981317F, 0.0F, -0.296706F));
        PartDefinition p_headWebR = root.addOrReplaceChild("headWebR",
                CubeListBuilder.create().texOffs(22, 70).addBox(-4.8F, -5.0F, -1.2F, 3.0F, 6.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, -13.5F, -6.0F, -0.6283185F, 0.122173F, -0.2268928F));
        PartDefinition p_rHip = root.addOrReplaceChild("rHip",
                CubeListBuilder.create().texOffs(167, 18).addBox(0.0F, -9.0F, -2.5F, 0.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(-4.5F, 0.0F, 16.0F, -0.296706F, 0.0F, 0.0F));
        PartDefinition p_lHip = root.addOrReplaceChild("lHip",
                CubeListBuilder.create().texOffs(176, 18).addBox(0.0F, -9.0F, -2.5F, 0.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(4.5F, 0.0F, 16.0F, -0.296706F, 0.0F, 0.0F));
        PartDefinition p_lShinSpike = root.addOrReplaceChild("lShinSpike",
                CubeListBuilder.create().texOffs(150, 17).addBox(0.5F, 8.0F, -12.0F, 0.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(4.5F, 0.0F, 16.0F, 0.8028515F, -0.2443461F, -0.418879F));
        PartDefinition p_rShinSpike = root.addOrReplaceChild("rShinSpike",
                CubeListBuilder.create().texOffs(150, 26).addBox(-0.5F, 8.0F, -12.0F, 0.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(-4.5F, 0.0F, 16.0F, 0.8028515F, 0.2443461F, 0.418879F));
        PartDefinition p_sack0 = root.addOrReplaceChild("sack0",
                CubeListBuilder.create().texOffs(229, 64).addBox(-3.0F, -10.0F, 2.0F, 6.0F, 6.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, -0.9599311F, 0.0F, 0.0F));
        PartDefinition p_sack7 = root.addOrReplaceChild("sack7",
                CubeListBuilder.create().texOffs(42, 64).addBox(-1.5F, -52.0F, 35.0F, 3.0F, 3.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, -1.151917F, 0.0F, 0.0F));
        PartDefinition p_sack1 = root.addOrReplaceChild("sack1",
                CubeListBuilder.create().texOffs(192, 64).addBox(-4.0F, -5.0F, 9.0F, 8.0F, 8.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, -0.3665191F, 0.0F, 0.0F));
        PartDefinition p_sack2 = root.addOrReplaceChild("sack2",
                CubeListBuilder.create().texOffs(149, 64).addBox(-5.0F, -2.5F, 18.0F, 10.0F, 10.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, -0.1919862F, 0.0F, 0.0F));
        PartDefinition p_sack3 = root.addOrReplaceChild("sack3",
                CubeListBuilder.create().texOffs(104, 64).addBox(-5.5F, -1.5F, 28.0F, 11.0F, 11.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, -0.1396263F, 0.0F, 0.0F));
        PartDefinition p_sack4 = root.addOrReplaceChild("sack4",
                CubeListBuilder.create().texOffs(63, 64).addBox(-4.5F, 2.5F, 37.0F, 9.0F, 9.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, -0.0349066F, 0.0F, 0.0F));
        PartDefinition p_sack5 = root.addOrReplaceChild("sack5",
                CubeListBuilder.create().texOffs(223, 47).addBox(-3.5F, 6.0F, 47.2F, 7.0F, 7.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, 0.0349066F, 0.0F, 0.0F));
        PartDefinition p_sack6 = root.addOrReplaceChild("sack6",
                CubeListBuilder.create().texOffs(38, 75).addBox(-2.5F, -17.0F, 54.0F, 5.0F, 6.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, -0.4014257F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
