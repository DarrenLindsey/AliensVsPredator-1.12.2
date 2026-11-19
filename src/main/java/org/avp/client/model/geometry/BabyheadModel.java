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
 * Geometry converted automatically from the 1.12.2 ModelBabyhead by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class BabyheadModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "babyhead"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart chest;
    public final ModelPart uTube1;
    public final ModelPart uTube2;
    public final ModelPart lowerbody;
    public final ModelPart head1;
    public final ModelPart lheadFront;
    public final ModelPart rheadFront;
    public final ModelPart head2;
    public final ModelPart upperJaw;
    public final ModelPart upperJawBone;
    public final ModelPart lowerJaw;
    public final ModelPart lowerJawBone;
    public final ModelPart lBackSpike;
    public final ModelPart spines;
    public final ModelPart lShoulder;
    public final ModelPart lArmUpper;
    public final ModelPart lArmLower;
    public final ModelPart lHand;
    public final ModelPart lFinger2;
    public final ModelPart lFinger1;
    public final ModelPart lMuscle;
    public final ModelPart rBackSpike;
    public final ModelPart lLeg1;
    public final ModelPart lLeg2;
    public final ModelPart lLeg3;
    public final ModelPart lLeg4;
    public final ModelPart lfoot;
    public final ModelPart lToe2;
    public final ModelPart lToe1;
    public final ModelPart lTube1;
    public final ModelPart rTube1;
    public final ModelPart rShoulder;
    public final ModelPart rArmUpper;
    public final ModelPart rArmLower;
    public final ModelPart rMuscle;
    public final ModelPart rHand;
    public final ModelPart rFinger1;
    public final ModelPart rFinger2;
    public final ModelPart rLeg1;
    public final ModelPart rLeg2;
    public final ModelPart rLeg3;
    public final ModelPart rLeg4;
    public final ModelPart rfoot;
    public final ModelPart rToe1;
    public final ModelPart rToe2;
    public final ModelPart bTube1;
    public final ModelPart bTube2;
    public final ModelPart neck;

    public BabyheadModel(ModelPart root)
    {
        this.root = root;
        this.chest = root.getChild("chest");
        this.parts.put("chest", this.chest);
        this.uTube1 = this.chest.getChild("uTube1");
        this.parts.put("uTube1", this.uTube1);
        this.uTube2 = this.uTube1.getChild("uTube2");
        this.parts.put("uTube2", this.uTube2);
        this.lowerbody = this.chest.getChild("lowerbody");
        this.parts.put("lowerbody", this.lowerbody);
        this.head1 = this.chest.getChild("head1");
        this.parts.put("head1", this.head1);
        this.lheadFront = this.head1.getChild("lheadFront");
        this.parts.put("lheadFront", this.lheadFront);
        this.rheadFront = this.head1.getChild("rheadFront");
        this.parts.put("rheadFront", this.rheadFront);
        this.head2 = this.head1.getChild("head2");
        this.parts.put("head2", this.head2);
        this.upperJaw = this.head1.getChild("upperJaw");
        this.parts.put("upperJaw", this.upperJaw);
        this.upperJawBone = this.head1.getChild("upperJawBone");
        this.parts.put("upperJawBone", this.upperJawBone);
        this.lowerJaw = this.head1.getChild("lowerJaw");
        this.parts.put("lowerJaw", this.lowerJaw);
        this.lowerJawBone = this.lowerJaw.getChild("lowerJawBone");
        this.parts.put("lowerJawBone", this.lowerJawBone);
        this.lBackSpike = this.chest.getChild("lBackSpike");
        this.parts.put("lBackSpike", this.lBackSpike);
        this.spines = this.chest.getChild("spines");
        this.parts.put("spines", this.spines);
        this.lShoulder = this.chest.getChild("lShoulder");
        this.parts.put("lShoulder", this.lShoulder);
        this.lArmUpper = this.lShoulder.getChild("lArmUpper");
        this.parts.put("lArmUpper", this.lArmUpper);
        this.lArmLower = this.lArmUpper.getChild("lArmLower");
        this.parts.put("lArmLower", this.lArmLower);
        this.lHand = this.lArmLower.getChild("lHand");
        this.parts.put("lHand", this.lHand);
        this.lFinger2 = this.lHand.getChild("lFinger2");
        this.parts.put("lFinger2", this.lFinger2);
        this.lFinger1 = this.lHand.getChild("lFinger1");
        this.parts.put("lFinger1", this.lFinger1);
        this.lMuscle = this.lArmLower.getChild("lMuscle");
        this.parts.put("lMuscle", this.lMuscle);
        this.rBackSpike = this.chest.getChild("rBackSpike");
        this.parts.put("rBackSpike", this.rBackSpike);
        this.lLeg1 = this.chest.getChild("lLeg1");
        this.parts.put("lLeg1", this.lLeg1);
        this.lLeg2 = this.lLeg1.getChild("lLeg2");
        this.parts.put("lLeg2", this.lLeg2);
        this.lLeg3 = this.lLeg2.getChild("lLeg3");
        this.parts.put("lLeg3", this.lLeg3);
        this.lLeg4 = this.lLeg3.getChild("lLeg4");
        this.parts.put("lLeg4", this.lLeg4);
        this.lfoot = this.lLeg4.getChild("lfoot");
        this.parts.put("lfoot", this.lfoot);
        this.lToe2 = this.lfoot.getChild("lToe2");
        this.parts.put("lToe2", this.lToe2);
        this.lToe1 = this.lfoot.getChild("lToe1");
        this.parts.put("lToe1", this.lToe1);
        this.lTube1 = this.chest.getChild("lTube1");
        this.parts.put("lTube1", this.lTube1);
        this.rTube1 = this.chest.getChild("rTube1");
        this.parts.put("rTube1", this.rTube1);
        this.rShoulder = this.chest.getChild("rShoulder");
        this.parts.put("rShoulder", this.rShoulder);
        this.rArmUpper = this.rShoulder.getChild("rArmUpper");
        this.parts.put("rArmUpper", this.rArmUpper);
        this.rArmLower = this.rArmUpper.getChild("rArmLower");
        this.parts.put("rArmLower", this.rArmLower);
        this.rMuscle = this.rArmLower.getChild("rMuscle");
        this.parts.put("rMuscle", this.rMuscle);
        this.rHand = this.rArmLower.getChild("rHand");
        this.parts.put("rHand", this.rHand);
        this.rFinger1 = this.rHand.getChild("rFinger1");
        this.parts.put("rFinger1", this.rFinger1);
        this.rFinger2 = this.rHand.getChild("rFinger2");
        this.parts.put("rFinger2", this.rFinger2);
        this.rLeg1 = this.chest.getChild("rLeg1");
        this.parts.put("rLeg1", this.rLeg1);
        this.rLeg2 = this.rLeg1.getChild("rLeg2");
        this.parts.put("rLeg2", this.rLeg2);
        this.rLeg3 = this.rLeg2.getChild("rLeg3");
        this.parts.put("rLeg3", this.rLeg3);
        this.rLeg4 = this.rLeg3.getChild("rLeg4");
        this.parts.put("rLeg4", this.rLeg4);
        this.rfoot = this.rLeg4.getChild("rfoot");
        this.parts.put("rfoot", this.rfoot);
        this.rToe1 = this.rfoot.getChild("rToe1");
        this.parts.put("rToe1", this.rToe1);
        this.rToe2 = this.rfoot.getChild("rToe2");
        this.parts.put("rToe2", this.rToe2);
        this.bTube1 = this.chest.getChild("bTube1");
        this.parts.put("bTube1", this.bTube1);
        this.bTube2 = this.bTube1.getChild("bTube2");
        this.parts.put("bTube2", this.bTube2);
        this.neck = this.chest.getChild("neck");
        this.parts.put("neck", this.neck);
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
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.2F, -3.0F, 6.0F, 5.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.40980330836826856F, 0.0F, 0.0F));
        PartDefinition p_uTube1 = p_chest.addOrReplaceChild("uTube1",
                CubeListBuilder.create().texOffs(5, 45).addBox(-1.0F, -4.3F, 2.4F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_uTube2 = p_uTube1.addOrReplaceChild("uTube2",
                CubeListBuilder.create().texOffs(26, 45).addBox(-0.5F, -4.8F, 3.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_lowerbody = p_chest.addOrReplaceChild("lowerbody",
                CubeListBuilder.create().texOffs(0, 14).addBox(-2.5F, 0.3F, -2.0F, 5.0F, 4.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_head1 = p_chest.addOrReplaceChild("head1",
                CubeListBuilder.create().texOffs(87, 0).addBox(-2.5F, -4.9F, -2.3F, 5.0F, 4.0F, 5.0F),
                PartPose.offset(0.0F, -4.4F, 0.0F));
        PartDefinition p_lheadFront = p_head1.addOrReplaceChild("lheadFront",
                CubeListBuilder.create().texOffs(88, 22).addBox(-0.6F, -5.4F, -2.6F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_rheadFront = p_head1.addOrReplaceChild("rheadFront",
                CubeListBuilder.create().texOffs(102, 22).addBox(-2.4F, -5.4F, -2.6F, 2.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_head2 = p_head1.addOrReplaceChild("head2",
                CubeListBuilder.create().texOffs(88, 10).addBox(-2.5F, -5.5F, 0.2F, 5.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4553564018453205F, 0.0F, 0.0F));
        PartDefinition p_upperJaw = p_head1.addOrReplaceChild("upperJaw",
                CubeListBuilder.create().texOffs(88, 29).addBox(-1.5F, -2.1F, -4.1F, 3.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_upperJawBone = p_head1.addOrReplaceChild("upperJawBone",
                CubeListBuilder.create().texOffs(87, 40).addBox(-1.0F, -1.9F, -3.7F, 2.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lowerJaw = p_head1.addOrReplaceChild("lowerJaw",
                CubeListBuilder.create().texOffs(88, 33).addBox(-1.5F, -0.8F, -1.5F, 3.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, -0.6F, -2.4F));
        PartDefinition p_lowerJawBone = p_lowerJaw.addOrReplaceChild("lowerJawBone",
                CubeListBuilder.create().texOffs(88, 48).addBox(-1.0F, -1.0F, -1.4F, 2.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lBackSpike = p_chest.addOrReplaceChild("lBackSpike",
                CubeListBuilder.create().texOffs(5, 45).addBox(1.5F, -4.0F, 3.2F, 0.0F, 3.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.18203784098300857F, 0.0F));
        PartDefinition p_spines = p_chest.addOrReplaceChild("spines",
                CubeListBuilder.create().texOffs(25, 50).addBox(0.0F, 0.5F, 3.0F, 0.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -0.2F, 0.0F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_lShoulder = p_chest.addOrReplaceChild("lShoulder",
                CubeListBuilder.create().texOffs(60, 0).addBox(-0.5F, -0.3F, -1.5F, 2.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(2.2F, -3.0F, 0.1F, 0.18203784098300857F, 0.0F, -0.31869712141416456F));
        PartDefinition p_lArmUpper = p_lShoulder.addOrReplaceChild("lArmUpper",
                CubeListBuilder.create().texOffs(60, 9).addBox(-0.5F, -0.4F, -1.3F, 2.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 3.6F, 0.5F, -0.4553564018453205F, 0.0F, 0.0F));
        PartDefinition p_lArmLower = p_lArmUpper.addOrReplaceChild("lArmLower",
                CubeListBuilder.create().texOffs(60, 17).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 15.0F, 1.0F),
                PartPose.offsetAndRotation(0.6F, 4.3F, -0.3F, -0.31869712141416456F, 0.0F, 0.27314402793711257F));
        PartDefinition p_lHand = p_lArmLower.addOrReplaceChild("lHand",
                CubeListBuilder.create().texOffs(60, 35).addBox(-1.1F, 0.0F, -0.7F, 2.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 14.9F, 0.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_lFinger2 = p_lHand.addOrReplaceChild("lFinger2",
                CubeListBuilder.create().texOffs(60, 40).addBox(-0.6F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(-0.6F, 2.6F, -0.3F));
        PartDefinition p_lFinger1 = p_lHand.addOrReplaceChild("lFinger1",
                CubeListBuilder.create().texOffs(60, 40).addBox(-0.6F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.6F, 2.6F, -0.3F));
        PartDefinition p_lMuscle = p_lArmLower.addOrReplaceChild("lMuscle",
                CubeListBuilder.create().texOffs(75, 0).addBox(-0.6F, 4.2F, 1.6F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_rBackSpike = p_chest.addOrReplaceChild("rBackSpike",
                CubeListBuilder.create().texOffs(5, 45).addBox(-1.5F, -4.0F, 3.2F, 0.0F, 3.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.18203784098300857F, 0.0F));
        PartDefinition p_lLeg1 = p_chest.addOrReplaceChild("lLeg1",
                CubeListBuilder.create().texOffs(37, 0).addBox(0.0F, -1.1F, -1.5F, 2.0F, 5.0F, 3.0F),
                PartPose.offsetAndRotation(1.6F, 4.0F, 0.3F, -0.6829473363053812F, 0.0F, 0.0F));
        PartDefinition p_lLeg2 = p_lLeg1.addOrReplaceChild("lLeg2",
                CubeListBuilder.create().texOffs(37, 10).addBox(-1.1F, -1.2F, -4.4F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(1.0F, 3.6F, 0.0F, 0.5918411493512771F, 0.0F, 0.0F));
        PartDefinition p_lLeg3 = p_lLeg2.addOrReplaceChild("lLeg3",
                CubeListBuilder.create().texOffs(39, 19).addBox(-1.1F, 0.0F, -0.7F, 2.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.4F, -3.7F, 0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_lLeg4 = p_lLeg3.addOrReplaceChild("lLeg4",
                CubeListBuilder.create().texOffs(41, 28).addBox(-0.5F, -0.1F, -0.5F, 1.0F, 8.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 4.4F, 0.4F, -0.6373942428283291F, 0.0F, 0.0F));
        PartDefinition p_lfoot = p_lLeg4.addOrReplaceChild("lfoot",
                CubeListBuilder.create().texOffs(41, 39).addBox(-1.0F, -0.1F, -0.5F, 2.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 7.6F, 0.0F, -0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_lToe2 = p_lfoot.addOrReplaceChild("lToe2",
                CubeListBuilder.create().texOffs(42, 44).addBox(-0.7F, 0.2F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(-0.5F, 1.6F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lToe1 = p_lfoot.addOrReplaceChild("lToe1",
                CubeListBuilder.create().texOffs(42, 44).mirror().addBox(-0.4F, 0.2F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.5F, 1.6F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lTube1 = p_chest.addOrReplaceChild("lTube1",
                CubeListBuilder.create().texOffs(15, 45).addBox(1.0F, -1.4F, 2.7F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.27314402793711257F, 0.18203784098300857F, 0.0F));
        PartDefinition p_rTube1 = p_chest.addOrReplaceChild("rTube1",
                CubeListBuilder.create().texOffs(15, 45).mirror().addBox(-2.0F, -1.4F, 2.7F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.27314402793711257F, -0.18203784098300857F, 0.0F));
        PartDefinition p_rShoulder = p_chest.addOrReplaceChild("rShoulder",
                CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-1.5F, -0.3F, -1.5F, 2.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(-2.2F, -3.0F, 0.1F, 0.18203784098300857F, 0.0F, 0.31869712141416456F));
        PartDefinition p_rArmUpper = p_rShoulder.addOrReplaceChild("rArmUpper",
                CubeListBuilder.create().texOffs(60, 9).mirror().addBox(-1.5F, -0.4F, -1.3F, 2.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 3.6F, 0.5F, -0.4553564018453205F, 0.0F, 0.0F));
        PartDefinition p_rArmLower = p_rArmUpper.addOrReplaceChild("rArmLower",
                CubeListBuilder.create().texOffs(60, 17).mirror().addBox(-0.5F, 0.0F, -0.5F, 1.0F, 15.0F, 1.0F),
                PartPose.offsetAndRotation(-0.6F, 4.3F, -0.3F, -0.31869712141416456F, 0.0F, -0.27314402793711257F));
        PartDefinition p_rMuscle = p_rArmLower.addOrReplaceChild("rMuscle",
                CubeListBuilder.create().texOffs(75, 0).mirror().addBox(-0.4F, 4.2F, 1.6F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_rHand = p_rArmLower.addOrReplaceChild("rHand",
                CubeListBuilder.create().texOffs(60, 35).addBox(-1.1F, 0.0F, -0.7F, 2.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 14.9F, 0.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_rFinger1 = p_rHand.addOrReplaceChild("rFinger1",
                CubeListBuilder.create().texOffs(60, 40).mirror().addBox(-0.6F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(-0.6F, 2.6F, -0.3F));
        PartDefinition p_rFinger2 = p_rHand.addOrReplaceChild("rFinger2",
                CubeListBuilder.create().texOffs(60, 40).mirror().addBox(-0.6F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.6F, 2.6F, -0.3F));
        PartDefinition p_rLeg1 = p_chest.addOrReplaceChild("rLeg1",
                CubeListBuilder.create().texOffs(37, 0).mirror().addBox(-2.0F, -1.1F, -1.5F, 2.0F, 5.0F, 3.0F),
                PartPose.offsetAndRotation(-1.6F, 4.0F, 0.3F, -0.6829473363053812F, 0.0F, 0.0F));
        PartDefinition p_rLeg2 = p_rLeg1.addOrReplaceChild("rLeg2",
                CubeListBuilder.create().texOffs(37, 10).mirror().addBox(-0.9F, -1.2F, -4.4F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(-1.0F, 3.6F, 0.0F, 0.5918411493512771F, 0.0F, 0.0F));
        PartDefinition p_rLeg3 = p_rLeg2.addOrReplaceChild("rLeg3",
                CubeListBuilder.create().texOffs(39, 19).mirror().addBox(-0.9F, 0.0F, -0.7F, 2.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.4F, -3.7F, 0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_rLeg4 = p_rLeg3.addOrReplaceChild("rLeg4",
                CubeListBuilder.create().texOffs(41, 28).mirror().addBox(-0.5F, -0.1F, -0.5F, 1.0F, 8.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 4.4F, 0.4F, -0.6373942428283291F, 0.0F, 0.0F));
        PartDefinition p_rfoot = p_rLeg4.addOrReplaceChild("rfoot",
                CubeListBuilder.create().texOffs(41, 39).mirror().addBox(-1.0F, -0.1F, -0.5F, 2.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 7.6F, 0.0F, -0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_rToe1 = p_rfoot.addOrReplaceChild("rToe1",
                CubeListBuilder.create().texOffs(42, 44).addBox(-0.7F, 0.2F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(-0.5F, 1.6F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rToe2 = p_rfoot.addOrReplaceChild("rToe2",
                CubeListBuilder.create().texOffs(42, 44).mirror().addBox(-0.4F, 0.2F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.5F, 1.6F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_bTube1 = p_chest.addOrReplaceChild("bTube1",
                CubeListBuilder.create().texOffs(5, 45).addBox(-1.0F, -2.3F, 2.4F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_bTube2 = p_bTube1.addOrReplaceChild("bTube2",
                CubeListBuilder.create().texOffs(26, 45).addBox(-0.5F, -2.5F, 3.6F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_neck = p_chest.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(0, 27).addBox(-2.0F, -1.7F, -2.0F, 4.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -4.1F, 0.0F, -0.136659280431156F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
