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
 * Geometry converted automatically from the 1.12.2 ModelNauticomorph by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class NauticomorphModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "nauticomorph"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart chest;
    public final ModelPart spine1;
    public final ModelPart rArm1;
    public final ModelPart rArm2;
    public final ModelPart rClaw1;
    public final ModelPart rClaw2;
    public final ModelPart lTube01;
    public final ModelPart neck;
    public final ModelPart head1;
    public final ModelPart head5;
    public final ModelPart lHeadSpines;
    public final ModelPart headSideHorns;
    public final ModelPart jawLower;
    public final ModelPart head4;
    public final ModelPart rHeadSpines;
    public final ModelPart innerJaw;
    public final ModelPart head6;
    public final ModelPart head3;
    public final ModelPart mHeadSpines;
    public final ModelPart head2;
    public final ModelPart abdomen;
    public final ModelPart rTube02;
    public final ModelPart rThigh;
    public final ModelPart rShin1;
    public final ModelPart rShin2;
    public final ModelPart rFoot;
    public final ModelPart rFootFlipper;
    public final ModelPart tail0;
    public final ModelPart tail1;
    public final ModelPart tail2;
    public final ModelPart tail3;
    public final ModelPart tail4;
    public final ModelPart tailFin;
    public final ModelPart tail4Spines;
    public final ModelPart tail3Spines;
    public final ModelPart tail2Spines;
    public final ModelPart tail1Spines;
    public final ModelPart tail0Spines;
    public final ModelPart lThigh;
    public final ModelPart lShin1;
    public final ModelPart lShin2;
    public final ModelPart lFoot;
    public final ModelPart lFootFlipper;
    public final ModelPart lTube02;
    public final ModelPart spine2;
    public final ModelPart rTube01;
    public final ModelPart lArm1;
    public final ModelPart lArm2;
    public final ModelPart lClaw1;
    public final ModelPart lClaw2;

    public NauticomorphModel(ModelPart root)
    {
        this.root = root;
        this.chest = root.getChild("chest");
        this.parts.put("chest", this.chest);
        this.spine1 = this.chest.getChild("spine1");
        this.parts.put("spine1", this.spine1);
        this.rArm1 = this.chest.getChild("rArm1");
        this.parts.put("rArm1", this.rArm1);
        this.rArm2 = this.rArm1.getChild("rArm2");
        this.parts.put("rArm2", this.rArm2);
        this.rClaw1 = this.rArm2.getChild("rClaw1");
        this.parts.put("rClaw1", this.rClaw1);
        this.rClaw2 = this.rClaw1.getChild("rClaw2");
        this.parts.put("rClaw2", this.rClaw2);
        this.lTube01 = this.chest.getChild("lTube01");
        this.parts.put("lTube01", this.lTube01);
        this.neck = this.chest.getChild("neck");
        this.parts.put("neck", this.neck);
        this.head1 = this.neck.getChild("head1");
        this.parts.put("head1", this.head1);
        this.head5 = this.head1.getChild("head5");
        this.parts.put("head5", this.head5);
        this.lHeadSpines = this.head1.getChild("lHeadSpines");
        this.parts.put("lHeadSpines", this.lHeadSpines);
        this.headSideHorns = this.head1.getChild("headSideHorns");
        this.parts.put("headSideHorns", this.headSideHorns);
        this.jawLower = this.head1.getChild("jawLower");
        this.parts.put("jawLower", this.jawLower);
        this.head4 = this.head1.getChild("head4");
        this.parts.put("head4", this.head4);
        this.rHeadSpines = this.head1.getChild("rHeadSpines");
        this.parts.put("rHeadSpines", this.rHeadSpines);
        this.innerJaw = this.head1.getChild("innerJaw");
        this.parts.put("innerJaw", this.innerJaw);
        this.head6 = this.head1.getChild("head6");
        this.parts.put("head6", this.head6);
        this.head3 = this.head1.getChild("head3");
        this.parts.put("head3", this.head3);
        this.mHeadSpines = this.head1.getChild("mHeadSpines");
        this.parts.put("mHeadSpines", this.mHeadSpines);
        this.head2 = this.head1.getChild("head2");
        this.parts.put("head2", this.head2);
        this.abdomen = this.chest.getChild("abdomen");
        this.parts.put("abdomen", this.abdomen);
        this.rTube02 = this.abdomen.getChild("rTube02");
        this.parts.put("rTube02", this.rTube02);
        this.rThigh = this.abdomen.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.rShin1 = this.rThigh.getChild("rShin1");
        this.parts.put("rShin1", this.rShin1);
        this.rShin2 = this.rShin1.getChild("rShin2");
        this.parts.put("rShin2", this.rShin2);
        this.rFoot = this.rShin2.getChild("rFoot");
        this.parts.put("rFoot", this.rFoot);
        this.rFootFlipper = this.rFoot.getChild("rFootFlipper");
        this.parts.put("rFootFlipper", this.rFootFlipper);
        this.tail0 = this.abdomen.getChild("tail0");
        this.parts.put("tail0", this.tail0);
        this.tail1 = this.tail0.getChild("tail1");
        this.parts.put("tail1", this.tail1);
        this.tail2 = this.tail1.getChild("tail2");
        this.parts.put("tail2", this.tail2);
        this.tail3 = this.tail2.getChild("tail3");
        this.parts.put("tail3", this.tail3);
        this.tail4 = this.tail3.getChild("tail4");
        this.parts.put("tail4", this.tail4);
        this.tailFin = this.tail4.getChild("tailFin");
        this.parts.put("tailFin", this.tailFin);
        this.tail4Spines = this.tail4.getChild("tail4Spines");
        this.parts.put("tail4Spines", this.tail4Spines);
        this.tail3Spines = this.tail3.getChild("tail3Spines");
        this.parts.put("tail3Spines", this.tail3Spines);
        this.tail2Spines = this.tail2.getChild("tail2Spines");
        this.parts.put("tail2Spines", this.tail2Spines);
        this.tail1Spines = this.tail1.getChild("tail1Spines");
        this.parts.put("tail1Spines", this.tail1Spines);
        this.tail0Spines = this.tail0.getChild("tail0Spines");
        this.parts.put("tail0Spines", this.tail0Spines);
        this.lThigh = this.abdomen.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lShin1 = this.lThigh.getChild("lShin1");
        this.parts.put("lShin1", this.lShin1);
        this.lShin2 = this.lShin1.getChild("lShin2");
        this.parts.put("lShin2", this.lShin2);
        this.lFoot = this.lShin2.getChild("lFoot");
        this.parts.put("lFoot", this.lFoot);
        this.lFootFlipper = this.lFoot.getChild("lFootFlipper");
        this.parts.put("lFootFlipper", this.lFootFlipper);
        this.lTube02 = this.abdomen.getChild("lTube02");
        this.parts.put("lTube02", this.lTube02);
        this.spine2 = this.abdomen.getChild("spine2");
        this.parts.put("spine2", this.spine2);
        this.rTube01 = this.chest.getChild("rTube01");
        this.parts.put("rTube01", this.rTube01);
        this.lArm1 = this.chest.getChild("lArm1");
        this.parts.put("lArm1", this.lArm1);
        this.lArm2 = this.lArm1.getChild("lArm2");
        this.parts.put("lArm2", this.lArm2);
        this.lClaw1 = this.lArm2.getChild("lClaw1");
        this.parts.put("lClaw1", this.lClaw1);
        this.lClaw2 = this.lClaw1.getChild("lClaw2");
        this.parts.put("lClaw2", this.lClaw2);
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
                CubeListBuilder.create().texOffs(0, 46).addBox(-4.5F, -2.0F, 0.0F, 9.0F, 8.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -2.1F, -9.0F, -0.19198621771937624F, 0.0F, 0.0F));
        PartDefinition p_spine1 = p_chest.addOrReplaceChild("spine1",
                CubeListBuilder.create().texOffs(0, 65).addBox(0.0F, -9.7F, 0.0F, 0.0F, 8.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rArm1 = p_chest.addOrReplaceChild("rArm1",
                CubeListBuilder.create().texOffs(50, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(-4.5F, 1.8F, 3.0F, 0.5585053606381855F, 0.0F, 0.593411945678072F));
        PartDefinition p_rArm2 = p_rArm1.addOrReplaceChild("rArm2",
                CubeListBuilder.create().texOffs(98, 9).addBox(-1.01F, -0.5F, -10.5F, 2.0F, 2.0F, 11.0F),
                PartPose.offset(0.0F, 11.0F, 0.0F));
        PartDefinition p_rClaw1 = p_rArm2.addOrReplaceChild("rClaw1",
                CubeListBuilder.create().texOffs(72, 24).addBox(-1.0F, -0.5F, -3.0F, 2.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.5F, -10.5F));
        PartDefinition p_rClaw2 = p_rClaw1.addOrReplaceChild("rClaw2",
                CubeListBuilder.create().texOffs(60, 37).addBox(-3.0F, 0.0F, -7.0F, 6.0F, 0.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lTube01 = p_chest.addOrReplaceChild("lTube01",
                CubeListBuilder.create().texOffs(0, 86).addBox(2.5F, -11.0F, 0.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, 0.0F, 0.0F, 0.17453292519943295F));
        PartDefinition p_neck = p_chest.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(23, 86).addBox(-2.0F, -3.0F, -4.4F, 4.0F, 6.0F, 5.0F),
                PartPose.offset(0.0F, 1.1F, 0.0F));
        PartDefinition p_head1 = p_neck.addOrReplaceChild("head1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -2.9F, 5.0F, 10.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.4F, -5.4F, -0.5014330940979709F, 0.0F, 0.0F));
        PartDefinition p_head5 = p_head1.addOrReplaceChild("head5",
                CubeListBuilder.create().texOffs(28, 10).addBox(-2.5F, -14.0F, -4.2F, 5.0F, 10.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2792526803190927F, 0.0F, 0.0F));
        PartDefinition p_lHeadSpines = p_head1.addOrReplaceChild("lHeadSpines",
                CubeListBuilder.create().texOffs(123, 35).addBox(0.5F, -14.0F, -10.1F, 0.0F, 20.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.22759093446006054F, -0.18203784098300857F, 0.136659280431156F));
        PartDefinition p_headSideHorns = p_head1.addOrReplaceChild("headSideHorns",
                CubeListBuilder.create().texOffs(66, 0).addBox(-6.5F, 2.9F, -2.3F, 13.0F, 3.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_jawLower = p_head1.addOrReplaceChild("jawLower",
                CubeListBuilder.create().texOffs(88, 1).addBox(-1.5F, 0.3F, -5.0F, 3.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 4.2F, 2.5F, 0.7504915783575618F, 0.0F, 0.0F));
        PartDefinition p_head4 = p_head1.addOrReplaceChild("head4",
                CubeListBuilder.create().texOffs(52, 20).addBox(-1.5F, 4.1F, -6.1F, 3.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7504915783575618F, 0.0F, 0.0F));
        PartDefinition p_rHeadSpines = p_head1.addOrReplaceChild("rHeadSpines",
                CubeListBuilder.create().texOffs(123, 35).addBox(-0.5F, -14.0F, -10.2F, 0.0F, 20.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.22759093446006054F, 0.18203784098300857F, -0.136659280431156F));
        PartDefinition p_innerJaw = p_head1.addOrReplaceChild("innerJaw",
                CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.7F, -4.6F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 4.3F, 2.2F, 0.7504915783575618F, 0.0F, 0.0F));
        PartDefinition p_head6 = p_head1.addOrReplaceChild("head6",
                CubeListBuilder.create().texOffs(53, 0).addBox(-0.5F, 3.0F, -5.0F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5061454830783556F, 0.0F, 0.0F));
        PartDefinition p_head3 = p_head1.addOrReplaceChild("head3",
                CubeListBuilder.create().texOffs(51, 10).addBox(-2.5F, 2.8F, -5.0F, 5.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6283185307179586F, 0.0F, 0.0F));
        PartDefinition p_mHeadSpines = p_head1.addOrReplaceChild("mHeadSpines",
                CubeListBuilder.create().texOffs(123, 35).addBox(0.0F, -14.0F, -10.2F, 0.0F, 20.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_head2 = p_head1.addOrReplaceChild("head2",
                CubeListBuilder.create().texOffs(34, 0).addBox(-2.51F, 3.0F, -5.0F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5061454830783556F, 0.0F, 0.0F));
        PartDefinition p_abdomen = p_chest.addOrReplaceChild("abdomen",
                CubeListBuilder.create().texOffs(0, 27).addBox(-3.5F, -3.0F, -1.3F, 7.0F, 6.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, 10.0F, -0.3665191429188092F, 0.0F, 0.0F));
        PartDefinition p_rTube02 = p_abdomen.addOrReplaceChild("rTube02",
                CubeListBuilder.create().texOffs(9, 86).addBox(-2.7F, -10.5F, 0.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.17453292519943295F));
        PartDefinition p_rThigh = p_abdomen.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(59, 45).addBox(-1.5F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(-4.5F, 2.0F, 8.0F, -0.17453292519943295F, 0.24434609527920614F, 0.41887902047863906F));
        PartDefinition p_rShin1 = p_rThigh.addOrReplaceChild("rShin1",
                CubeListBuilder.create().texOffs(79, 33).addBox(-1.5F, -0.7F, -1.8F, 3.0F, 3.0F, 12.0F),
                PartPose.offsetAndRotation(0.5F, 8.9F, -0.3F, 0.3665191429188092F, 0.0F, 0.0F));
        PartDefinition p_rShin2 = p_rShin1.addOrReplaceChild("rShin2",
                CubeListBuilder.create().texOffs(113, 53).addBox(-1.0F, -0.8F, -1.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 1.0F, 9.9F, -0.3490658503988659F, 0.0F, 0.0F));
        PartDefinition p_rFoot = p_rShin2.addOrReplaceChild("rFoot",
                CubeListBuilder.create().texOffs(95, 24).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(-0.4F, 7.2F, 0.0F, 0.7853981633974483F, 0.0F, -0.22689280275926282F));
        PartDefinition p_rFootFlipper = p_rFoot.addOrReplaceChild("rFootFlipper",
                CubeListBuilder.create().texOffs(143, 53).addBox(-4.0F, 0.9F, -11.0F, 8.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.05235987755982988F));
        PartDefinition p_tail0 = p_abdomen.addOrReplaceChild("tail0",
                CubeListBuilder.create().texOffs(50, 66).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, -0.2F, 9.8F, 0.22689280275926282F, 0.0F, 0.0F));
        PartDefinition p_tail1 = p_tail0.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(85, 66).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 10.7F, 0.08726646259971647F, 0.0F, 0.0F));
        PartDefinition p_tail2 = p_tail1.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(118, 66).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, 0.10471975511965977F, 0.0F, 0.0F));
        PartDefinition p_tail3 = p_tail2.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(149, 66).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, 0.05235987755982988F, 0.0F, 0.0F));
        PartDefinition p_tail4 = p_tail3.addOrReplaceChild("tail4",
                CubeListBuilder.create().texOffs(178, 66).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, 0.08726646259971647F, 0.0F, 0.0F));
        PartDefinition p_tailFin = p_tail4.addOrReplaceChild("tailFin",
                CubeListBuilder.create().texOffs(202, 96).addBox(0.0F, -6.5F, 0.0F, 0.0F, 12.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, 0.05235987755982988F, 0.0F, 0.0F));
        PartDefinition p_tail4Spines = p_tail4.addOrReplaceChild("tail4Spines",
                CubeListBuilder.create().texOffs(178, 96).addBox(0.0F, -5.5F, 0.0F, 0.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail3Spines = p_tail3.addOrReplaceChild("tail3Spines",
                CubeListBuilder.create().texOffs(148, 96).addBox(0.0F, -5.5F, 0.9F, 0.0F, 8.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail2Spines = p_tail2.addOrReplaceChild("tail2Spines",
                CubeListBuilder.create().texOffs(117, 94).addBox(0.0F, -3.5F, 1.0F, 0.0F, 7.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail1Spines = p_tail1.addOrReplaceChild("tail1Spines",
                CubeListBuilder.create().texOffs(90, 93).addBox(0.0F, -4.0F, 0.7F, 0.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail0Spines = p_tail0.addOrReplaceChild("tail0Spines",
                CubeListBuilder.create().texOffs(58, 93).addBox(0.0F, -4.5F, 0.0F, 0.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lThigh = p_abdomen.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(40, 45).addBox(-2.5F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(4.5F, 2.0F, 8.0F, -0.17453292519943295F, -0.24434609527920614F, -0.41887902047863906F));
        PartDefinition p_lShin1 = p_lThigh.addOrReplaceChild("lShin1",
                CubeListBuilder.create().texOffs(79, 49).addBox(-1.5F, -0.7F, -1.8F, 3.0F, 3.0F, 12.0F),
                PartPose.offsetAndRotation(-0.5F, 8.9F, -0.3F, 0.3665191429188092F, 0.0F, 0.0F));
        PartDefinition p_lShin2 = p_lShin1.addOrReplaceChild("lShin2",
                CubeListBuilder.create().texOffs(113, 40).addBox(-1.0F, -0.8F, -1.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 1.0F, 9.9F, -0.3490658503988659F, 0.0F, 0.0F));
        PartDefinition p_lFoot = p_lShin2.addOrReplaceChild("lFoot",
                CubeListBuilder.create().texOffs(110, 24).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.4F, 7.2F, 0.0F, 0.7853981633974483F, 0.0F, 0.22689280275926282F));
        PartDefinition p_lFootFlipper = p_lFoot.addOrReplaceChild("lFootFlipper",
                CubeListBuilder.create().texOffs(143, 40).addBox(-4.0F, 0.9F, -11.0F, 8.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.05235987755982988F));
        PartDefinition p_lTube02 = p_abdomen.addOrReplaceChild("lTube02",
                CubeListBuilder.create().texOffs(0, 86).addBox(0.7F, -10.5F, 0.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.17453292519943295F));
        PartDefinition p_spine2 = p_abdomen.addOrReplaceChild("spine2",
                CubeListBuilder.create().texOffs(23, 65).addBox(0.0F, -8.9F, -2.7F, 0.0F, 6.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rTube01 = p_chest.addOrReplaceChild("rTube01",
                CubeListBuilder.create().texOffs(10, 86).addBox(-4.5F, -11.0F, 0.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, 0.0F, 0.0F, -0.17453292519943295F));
        PartDefinition p_lArm1 = p_chest.addOrReplaceChild("lArm1",
                CubeListBuilder.create().texOffs(40, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(4.5F, 1.8F, 3.0F, 0.5585053606381855F, 0.0F, -0.593411945678072F));
        PartDefinition p_lArm2 = p_lArm1.addOrReplaceChild("lArm2",
                CubeListBuilder.create().texOffs(71, 9).addBox(-0.99F, -0.5F, -10.5F, 2.0F, 2.0F, 11.0F),
                PartPose.offset(0.0F, 11.0F, 0.0F));
        PartDefinition p_lClaw1 = p_lArm2.addOrReplaceChild("lClaw1",
                CubeListBuilder.create().texOffs(83, 24).addBox(-1.0F, -0.5F, -3.0F, 2.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.5F, -10.5F));
        PartDefinition p_lClaw2 = p_lClaw1.addOrReplaceChild("lClaw2",
                CubeListBuilder.create().texOffs(60, 37).mirror().addBox(-3.0F, 0.0F, -7.0F, 6.0F, 0.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
