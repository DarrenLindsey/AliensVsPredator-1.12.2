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
 * Geometry converted automatically from the 1.12.2 ModelVardaMonkey by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class VardaMonkeyModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "varda_monkey"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart tail3;
    public final ModelPart chest;
    public final ModelPart neck;
    public final ModelPart head;
    public final ModelPart mane1;
    public final ModelPart muzzle;
    public final ModelPart rHeadTube;
    public final ModelPart mane2;
    public final ModelPart mane5;
    public final ModelPart mane3;
    public final ModelPart lHeadTube;
    public final ModelPart mane4;
    public final ModelPart jawUpper;
    public final ModelPart jawLower;
    public final ModelPart stomach;
    public final ModelPart lLeg1;
    public final ModelPart lLeg2;
    public final ModelPart lLeg3;
    public final ModelPart lLegSpikes;
    public final ModelPart rLeg1;
    public final ModelPart rLeg2;
    public final ModelPart rLeg3;
    public final ModelPart rLegSpikes;
    public final ModelPart rArm1;
    public final ModelPart rArm2;
    public final ModelPart rHand;
    public final ModelPart rFingerThumb;
    public final ModelPart rFinger3;
    public final ModelPart rFinger2;
    public final ModelPart rFinger4;
    public final ModelPart rFinger1;
    public final ModelPart tail1;
    public final ModelPart lArm1;
    public final ModelPart lArm2;
    public final ModelPart lHand;
    public final ModelPart lFinger1;
    public final ModelPart lFinger3;
    public final ModelPart lFinger4;
    public final ModelPart lFinger2;
    public final ModelPart lFingerThumb;
    public final ModelPart tail2;

    public VardaMonkeyModel(ModelPart root)
    {
        this.root = root;
        this.tail3 = root.getChild("tail3");
        this.parts.put("tail3", this.tail3);
        this.chest = root.getChild("chest");
        this.parts.put("chest", this.chest);
        this.neck = this.chest.getChild("neck");
        this.parts.put("neck", this.neck);
        this.head = this.neck.getChild("head");
        this.parts.put("head", this.head);
        this.mane1 = this.head.getChild("mane1");
        this.parts.put("mane1", this.mane1);
        this.muzzle = this.head.getChild("muzzle");
        this.parts.put("muzzle", this.muzzle);
        this.rHeadTube = this.head.getChild("rHeadTube");
        this.parts.put("rHeadTube", this.rHeadTube);
        this.mane2 = this.head.getChild("mane2");
        this.parts.put("mane2", this.mane2);
        this.mane5 = this.head.getChild("mane5");
        this.parts.put("mane5", this.mane5);
        this.mane3 = this.head.getChild("mane3");
        this.parts.put("mane3", this.mane3);
        this.lHeadTube = this.head.getChild("lHeadTube");
        this.parts.put("lHeadTube", this.lHeadTube);
        this.mane4 = this.head.getChild("mane4");
        this.parts.put("mane4", this.mane4);
        this.jawUpper = this.head.getChild("jawUpper");
        this.parts.put("jawUpper", this.jawUpper);
        this.jawLower = this.jawUpper.getChild("jawLower");
        this.parts.put("jawLower", this.jawLower);
        this.stomach = this.chest.getChild("stomach");
        this.parts.put("stomach", this.stomach);
        this.lLeg1 = this.stomach.getChild("lLeg1");
        this.parts.put("lLeg1", this.lLeg1);
        this.lLeg2 = this.lLeg1.getChild("lLeg2");
        this.parts.put("lLeg2", this.lLeg2);
        this.lLeg3 = this.lLeg2.getChild("lLeg3");
        this.parts.put("lLeg3", this.lLeg3);
        this.lLegSpikes = this.lLeg3.getChild("lLegSpikes");
        this.parts.put("lLegSpikes", this.lLegSpikes);
        this.rLeg1 = this.stomach.getChild("rLeg1");
        this.parts.put("rLeg1", this.rLeg1);
        this.rLeg2 = this.rLeg1.getChild("rLeg2");
        this.parts.put("rLeg2", this.rLeg2);
        this.rLeg3 = this.rLeg2.getChild("rLeg3");
        this.parts.put("rLeg3", this.rLeg3);
        this.rLegSpikes = this.rLeg3.getChild("rLegSpikes");
        this.parts.put("rLegSpikes", this.rLegSpikes);
        this.rArm1 = this.chest.getChild("rArm1");
        this.parts.put("rArm1", this.rArm1);
        this.rArm2 = this.rArm1.getChild("rArm2");
        this.parts.put("rArm2", this.rArm2);
        this.rHand = this.rArm2.getChild("rHand");
        this.parts.put("rHand", this.rHand);
        this.rFingerThumb = this.rHand.getChild("rFingerThumb");
        this.parts.put("rFingerThumb", this.rFingerThumb);
        this.rFinger3 = this.rHand.getChild("rFinger3");
        this.parts.put("rFinger3", this.rFinger3);
        this.rFinger2 = this.rHand.getChild("rFinger2");
        this.parts.put("rFinger2", this.rFinger2);
        this.rFinger4 = this.rHand.getChild("rFinger4");
        this.parts.put("rFinger4", this.rFinger4);
        this.rFinger1 = this.rHand.getChild("rFinger1");
        this.parts.put("rFinger1", this.rFinger1);
        this.tail1 = this.chest.getChild("tail1");
        this.parts.put("tail1", this.tail1);
        this.lArm1 = this.chest.getChild("lArm1");
        this.parts.put("lArm1", this.lArm1);
        this.lArm2 = this.lArm1.getChild("lArm2");
        this.parts.put("lArm2", this.lArm2);
        this.lHand = this.lArm2.getChild("lHand");
        this.parts.put("lHand", this.lHand);
        this.lFinger1 = this.lHand.getChild("lFinger1");
        this.parts.put("lFinger1", this.lFinger1);
        this.lFinger3 = this.lHand.getChild("lFinger3");
        this.parts.put("lFinger3", this.lFinger3);
        this.lFinger4 = this.lHand.getChild("lFinger4");
        this.parts.put("lFinger4", this.lFinger4);
        this.lFinger2 = this.lHand.getChild("lFinger2");
        this.parts.put("lFinger2", this.lFinger2);
        this.lFingerThumb = this.lHand.getChild("lFingerThumb");
        this.parts.put("lFingerThumb", this.lFingerThumb);
        this.tail2 = this.chest.getChild("tail2");
        this.parts.put("tail2", this.tail2);
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
        PartDefinition p_tail3 = root.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(47, 46).addBox(-0.5F, -0.5F, 0.1F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 15.6F, 7.5F, 0.4886921905584123F, 0.0F, 0.0F));
        PartDefinition p_chest = root.addOrReplaceChild("chest",
                CubeListBuilder.create().texOffs(0, 39).addBox(-2.5F, 0.0F, -1.5F, 5.0F, 5.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 12.0F, -3.0F, 0.7504915783575618F, 0.0F, 0.0F));
        PartDefinition p_neck = p_chest.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(94, 53).addBox(-1.5F, -2.2F, 0.0F, 3.0F, 4.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_head = p_neck.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.5F, -1.8F, 4.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -3.0F, 1.4F, -0.6829473363053812F, 0.0F, 0.0F));
        PartDefinition p_mane1 = p_head.addOrReplaceChild("mane1",
                CubeListBuilder.create().texOffs(29, 55).addBox(1.3F, -2.5F, 0.0F, 0.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.05235987755982988F, 0.08726646259971647F, 0.0F));
        PartDefinition p_muzzle = p_head.addOrReplaceChild("muzzle",
                CubeListBuilder.create().texOffs(0, 11).addBox(-1.5F, -1.3F, -4.2F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.03490658503988659F, 0.0F, 0.0F));
        PartDefinition p_rHeadTube = p_head.addOrReplaceChild("rHeadTube",
                CubeListBuilder.create().texOffs(93, 42).addBox(-2.0F, -1.3F, -0.1F, 0.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.148352986419518F, 0.12217304763960307F, 0.0F));
        PartDefinition p_mane2 = p_head.addOrReplaceChild("mane2",
                CubeListBuilder.create().texOffs(29, 45).addBox(0.5F, -2.5F, 0.0F, 0.0F, 3.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.12217304763960307F, 0.03490658503988659F, 0.0F));
        PartDefinition p_mane5 = p_head.addOrReplaceChild("mane5",
                CubeListBuilder.create().texOffs(29, 55).addBox(-1.0F, -2.5F, 0.0F, 0.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.05235987755982988F, -0.08726646259971647F, 0.0F));
        PartDefinition p_mane3 = p_head.addOrReplaceChild("mane3",
                CubeListBuilder.create().texOffs(29, 34).addBox(0.0F, -2.5F, 0.0F, 0.0F, 3.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.20943951023931953F, 0.0F, 0.0F));
        PartDefinition p_lHeadTube = p_head.addOrReplaceChild("lHeadTube",
                CubeListBuilder.create().texOffs(93, 42).mirror().addBox(2.0F, -1.3F, -0.1F, 0.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.148352986419518F, -0.12217304763960307F, 0.0F));
        PartDefinition p_mane4 = p_head.addOrReplaceChild("mane4",
                CubeListBuilder.create().texOffs(29, 45).addBox(-0.5F, -2.5F, 0.0F, 0.0F, 3.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.12217304763960307F, -0.03490658503988659F, 0.0F));
        PartDefinition p_jawUpper = p_head.addOrReplaceChild("jawUpper",
                CubeListBuilder.create().texOffs(0, 20).addBox(-1.0F, -1.3F, -5.6F, 2.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.06981317007977318F, 0.0F, 0.0F));
        PartDefinition p_jawLower = p_jawUpper.addOrReplaceChild("jawLower",
                CubeListBuilder.create().texOffs(0, 29).addBox(-1.0F, -1.0F, -4.5F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -0.1F, -0.7F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_stomach = p_chest.addOrReplaceChild("stomach",
                CubeListBuilder.create().texOffs(0, 50).addBox(-2.0F, 3.8F, -1.2F, 4.0F, 6.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.12217304763960307F, 0.0F, 0.0F));
        PartDefinition p_lLeg1 = p_stomach.addOrReplaceChild("lLeg1",
                CubeListBuilder.create().texOffs(101, 0).addBox(0.0F, -1.0F, -2.0F, 1.0F, 7.0F, 2.0F),
                PartPose.offsetAndRotation(1.5F, 9.2F, 0.0F, -1.4570008595648662F, 0.0F, -0.05235987755982988F));
        PartDefinition p_lLeg2 = p_lLeg1.addOrReplaceChild("lLeg2",
                CubeListBuilder.create().texOffs(101, 10).addBox(-1.5F, -1.1F, -0.2F, 1.0F, 2.0F, 6.0F),
                PartPose.offsetAndRotation(1.5F, 5.0F, -1.1F, 0.18203784098300857F, 0.0F, -0.05235987755982988F));
        PartDefinition p_lLeg3 = p_lLeg2.addOrReplaceChild("lLeg3",
                CubeListBuilder.create().texOffs(101, 19).addBox(-0.6F, -0.5F, -7.2F, 1.0F, 1.0F, 7.0F),
                PartPose.offsetAndRotation(-0.9F, 0.1F, 5.2F, 0.40980330836826856F, 0.0F, 0.091106186954104F));
        PartDefinition p_lLegSpikes = p_lLeg3.addOrReplaceChild("lLegSpikes",
                CubeListBuilder.create().texOffs(101, 28).addBox(-0.1F, -0.5F, -10.0F, 0.0F, 2.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rLeg1 = p_stomach.addOrReplaceChild("rLeg1",
                CubeListBuilder.create().texOffs(74, 0).addBox(-1.0F, -1.0F, -2.0F, 1.0F, 7.0F, 2.0F),
                PartPose.offsetAndRotation(-1.5F, 9.2F, 0.0F, -1.4570008595648662F, 0.0F, 0.05235987755982988F));
        PartDefinition p_rLeg2 = p_rLeg1.addOrReplaceChild("rLeg2",
                CubeListBuilder.create().texOffs(74, 10).addBox(-0.1F, -1.1F, -0.2F, 1.0F, 2.0F, 6.0F),
                PartPose.offsetAndRotation(-0.9F, 5.0F, -1.1F, 0.18203784098300857F, 0.0F, 0.05235987755982988F));
        PartDefinition p_rLeg3 = p_rLeg2.addOrReplaceChild("rLeg3",
                CubeListBuilder.create().texOffs(74, 19).addBox(-0.4F, -0.5F, -7.2F, 1.0F, 1.0F, 7.0F),
                PartPose.offsetAndRotation(0.4F, 0.1F, 5.2F, 0.40980330836826856F, 0.0F, -0.091106186954104F));
        PartDefinition p_rLegSpikes = p_rLeg3.addOrReplaceChild("rLegSpikes",
                CubeListBuilder.create().texOffs(74, 28).addBox(0.1F, -0.5F, -10.0F, 0.0F, 2.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rArm1 = p_chest.addOrReplaceChild("rArm1",
                CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(-2.0F, 3.2F, 1.2F, -0.40980330836826856F, 0.136659280431156F, 0.136659280431156F));
        PartDefinition p_rArm2 = p_rArm1.addOrReplaceChild("rArm2",
                CubeListBuilder.create().texOffs(26, 11).addBox(-0.5F, -0.5F, -6.6F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(-0.5F, 6.4F, -1.0F, 0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_rHand = p_rArm2.addOrReplaceChild("rHand",
                CubeListBuilder.create().texOffs(26, 21).addBox(-1.0F, -0.6F, -3.3F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -6.2F, -0.6829473363053812F, -0.12217304763960307F, -0.13962634015954636F));
        PartDefinition p_rFingerThumb = p_rHand.addOrReplaceChild("rFingerThumb",
                CubeListBuilder.create().texOffs(77, 52).addBox(0.2F, -0.7F, -5.3F, 0.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.15707963267948966F, -0.3490658503988659F, -0.05235987755982988F));
        PartDefinition p_rFinger3 = p_rHand.addOrReplaceChild("rFinger3",
                CubeListBuilder.create().texOffs(77, 43).addBox(-0.3F, -0.7F, -6.3F, 0.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.15707963267948966F, 0.05235987755982988F, -0.05235987755982988F));
        PartDefinition p_rFinger2 = p_rHand.addOrReplaceChild("rFinger2",
                CubeListBuilder.create().texOffs(77, 43).addBox(-0.1F, -0.7F, -6.3F, 0.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.15707963267948966F, -0.08726646259971647F, -0.05235987755982988F));
        PartDefinition p_rFinger4 = p_rHand.addOrReplaceChild("rFinger4",
                CubeListBuilder.create().texOffs(77, 43).addBox(-0.1F, -0.7F, -6.3F, 0.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.15707963267948966F, 0.05235987755982988F, -0.05235987755982988F));
        PartDefinition p_rFinger1 = p_rHand.addOrReplaceChild("rFinger1",
                CubeListBuilder.create().texOffs(77, 43).addBox(-0.1F, -0.7F, -6.3F, 0.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.15707963267948966F, -0.22689280275926282F, -0.05235987755982988F));
        PartDefinition p_tail1 = p_chest.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(47, 54).addBox(-1.0F, 5.2F, 6.2F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_lArm1 = p_chest.addOrReplaceChild("lArm1",
                CubeListBuilder.create().texOffs(46, 0).addBox(0.0F, -1.0F, -1.0F, 1.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(2.0F, 3.2F, 1.2F, -0.40980330836826856F, -0.136659280431156F, -0.136659280431156F));
        PartDefinition p_lArm2 = p_lArm1.addOrReplaceChild("lArm2",
                CubeListBuilder.create().texOffs(46, 11).addBox(-0.5F, -0.5F, -6.6F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.5F, 6.4F, -1.0F, 0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_lHand = p_lArm2.addOrReplaceChild("lHand",
                CubeListBuilder.create().texOffs(46, 21).addBox(-1.0F, -0.6F, -3.3F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -6.2F, -0.6829473363053812F, 0.12217304763960307F, 0.13962634015954636F));
        PartDefinition p_lFinger1 = p_lHand.addOrReplaceChild("lFinger1",
                CubeListBuilder.create().texOffs(63, 43).mirror().addBox(0.1F, -0.7F, -6.3F, 0.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.15707963267948966F, 0.22689280275926282F, 0.05235987755982988F));
        PartDefinition p_lFinger3 = p_lHand.addOrReplaceChild("lFinger3",
                CubeListBuilder.create().texOffs(63, 43).mirror().addBox(0.3F, -0.7F, -6.3F, 0.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.15707963267948966F, -0.05235987755982988F, 0.05235987755982988F));
        PartDefinition p_lFinger4 = p_lHand.addOrReplaceChild("lFinger4",
                CubeListBuilder.create().texOffs(63, 43).mirror().addBox(0.1F, -0.7F, -6.3F, 0.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.15707963267948966F, -0.22689280275926282F, 0.05235987755982988F));
        PartDefinition p_lFinger2 = p_lHand.addOrReplaceChild("lFinger2",
                CubeListBuilder.create().texOffs(63, 43).mirror().addBox(0.1F, -0.7F, -6.3F, 0.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.15707963267948966F, 0.05235987755982988F, 0.05235987755982988F));
        PartDefinition p_lFingerThumb = p_lHand.addOrReplaceChild("lFingerThumb",
                CubeListBuilder.create().texOffs(63, 51).addBox(-0.2F, -0.7F, -5.3F, 0.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.15707963267948966F, 0.3490658503988659F, 0.05235987755982988F));
        PartDefinition p_tail2 = p_chest.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(47, 50).addBox(-0.5F, 6.9F, 6.4F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3490658503988659F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
