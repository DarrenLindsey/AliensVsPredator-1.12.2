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
 * Geometry converted automatically from the 1.12.2 ModelBatXenomorph by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class BatXenomorphModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "bat_xenomorph"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart rArmUpper;
    public final ModelPart rWing01;
    public final ModelPart rArmLower;
    public final ModelPart rWing02;
    public final ModelPart chest;
    public final ModelPart stomach;
    public final ModelPart tail1;
    public final ModelPart tail2;
    public final ModelPart tailSpines2;
    public final ModelPart tail3;
    public final ModelPart tailSpines3;
    public final ModelPart tail4;
    public final ModelPart tailSpines4;
    public final ModelPart tail5;
    public final ModelPart tailSpines5;
    public final ModelPart stabber;
    public final ModelPart tailSpines1;
    public final ModelPart backSpine;
    public final ModelPart rThigh;
    public final ModelPart rShin;
    public final ModelPart rLowerLeg;
    public final ModelPart rFoot;
    public final ModelPart lThigh;
    public final ModelPart lShin;
    public final ModelPart lLowerLeg;
    public final ModelPart lFoot;
    public final ModelPart dorsalSpike;
    public final ModelPart lShoulderRidge;
    public final ModelPart head1;
    public final ModelPart innerJaw;
    public final ModelPart lHeadFin;
    public final ModelPart rHeadFin;
    public final ModelPart head2;
    public final ModelPart head6;
    public final ModelPart jawLower;
    public final ModelPart head3;
    public final ModelPart head4;
    public final ModelPart head5;
    public final ModelPart rShoulderRidge;
    public final ModelPart neck;
    public final ModelPart lArmUpper;
    public final ModelPart lArmLower;
    public final ModelPart lWing02;
    public final ModelPart lWing01;

    public BatXenomorphModel(ModelPart root)
    {
        this.root = root;
        this.rArmUpper = root.getChild("rArmUpper");
        this.parts.put("rArmUpper", this.rArmUpper);
        this.rWing01 = this.rArmUpper.getChild("rWing01");
        this.parts.put("rWing01", this.rWing01);
        this.rArmLower = this.rArmUpper.getChild("rArmLower");
        this.parts.put("rArmLower", this.rArmLower);
        this.rWing02 = this.rArmLower.getChild("rWing02");
        this.parts.put("rWing02", this.rWing02);
        this.chest = root.getChild("chest");
        this.parts.put("chest", this.chest);
        this.stomach = this.chest.getChild("stomach");
        this.parts.put("stomach", this.stomach);
        this.tail1 = this.stomach.getChild("tail1");
        this.parts.put("tail1", this.tail1);
        this.tail2 = this.tail1.getChild("tail2");
        this.parts.put("tail2", this.tail2);
        this.tailSpines2 = this.tail2.getChild("tailSpines2");
        this.parts.put("tailSpines2", this.tailSpines2);
        this.tail3 = this.tail2.getChild("tail3");
        this.parts.put("tail3", this.tail3);
        this.tailSpines3 = this.tail3.getChild("tailSpines3");
        this.parts.put("tailSpines3", this.tailSpines3);
        this.tail4 = this.tail3.getChild("tail4");
        this.parts.put("tail4", this.tail4);
        this.tailSpines4 = this.tail4.getChild("tailSpines4");
        this.parts.put("tailSpines4", this.tailSpines4);
        this.tail5 = this.tail4.getChild("tail5");
        this.parts.put("tail5", this.tail5);
        this.tailSpines5 = this.tail5.getChild("tailSpines5");
        this.parts.put("tailSpines5", this.tailSpines5);
        this.stabber = this.tail5.getChild("stabber");
        this.parts.put("stabber", this.stabber);
        this.tailSpines1 = this.tail1.getChild("tailSpines1");
        this.parts.put("tailSpines1", this.tailSpines1);
        this.backSpine = this.stomach.getChild("backSpine");
        this.parts.put("backSpine", this.backSpine);
        this.rThigh = this.stomach.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.rShin = this.rThigh.getChild("rShin");
        this.parts.put("rShin", this.rShin);
        this.rLowerLeg = this.rShin.getChild("rLowerLeg");
        this.parts.put("rLowerLeg", this.rLowerLeg);
        this.rFoot = this.rLowerLeg.getChild("rFoot");
        this.parts.put("rFoot", this.rFoot);
        this.lThigh = this.stomach.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lShin = this.lThigh.getChild("lShin");
        this.parts.put("lShin", this.lShin);
        this.lLowerLeg = this.lShin.getChild("lLowerLeg");
        this.parts.put("lLowerLeg", this.lLowerLeg);
        this.lFoot = this.lLowerLeg.getChild("lFoot");
        this.parts.put("lFoot", this.lFoot);
        this.dorsalSpike = this.chest.getChild("dorsalSpike");
        this.parts.put("dorsalSpike", this.dorsalSpike);
        this.lShoulderRidge = this.chest.getChild("lShoulderRidge");
        this.parts.put("lShoulderRidge", this.lShoulderRidge);
        this.head1 = this.chest.getChild("head1");
        this.parts.put("head1", this.head1);
        this.innerJaw = this.head1.getChild("innerJaw");
        this.parts.put("innerJaw", this.innerJaw);
        this.lHeadFin = this.head1.getChild("lHeadFin");
        this.parts.put("lHeadFin", this.lHeadFin);
        this.rHeadFin = this.head1.getChild("rHeadFin");
        this.parts.put("rHeadFin", this.rHeadFin);
        this.head2 = this.head1.getChild("head2");
        this.parts.put("head2", this.head2);
        this.head6 = this.head1.getChild("head6");
        this.parts.put("head6", this.head6);
        this.jawLower = this.head1.getChild("jawLower");
        this.parts.put("jawLower", this.jawLower);
        this.head3 = this.head1.getChild("head3");
        this.parts.put("head3", this.head3);
        this.head4 = this.head1.getChild("head4");
        this.parts.put("head4", this.head4);
        this.head5 = this.head1.getChild("head5");
        this.parts.put("head5", this.head5);
        this.rShoulderRidge = this.chest.getChild("rShoulderRidge");
        this.parts.put("rShoulderRidge", this.rShoulderRidge);
        this.neck = this.chest.getChild("neck");
        this.parts.put("neck", this.neck);
        this.lArmUpper = root.getChild("lArmUpper");
        this.parts.put("lArmUpper", this.lArmUpper);
        this.lArmLower = this.lArmUpper.getChild("lArmLower");
        this.parts.put("lArmLower", this.lArmLower);
        this.lWing02 = this.lArmLower.getChild("lWing02");
        this.parts.put("lWing02", this.lWing02);
        this.lWing01 = this.lArmUpper.getChild("lWing01");
        this.parts.put("lWing01", this.lWing01);
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
        PartDefinition p_rArmUpper = root.addOrReplaceChild("rArmUpper",
                CubeListBuilder.create().texOffs(50, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(-4.3F, -0.2F, -10.9F, 0.0F, 0.0F, 0.593411945678072F));
        PartDefinition p_rWing01 = p_rArmUpper.addOrReplaceChild("rWing01",
                CubeListBuilder.create().texOffs(188, 0).addBox(0.0F, -1.7F, 0.0F, 0.0F, 13.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rArmLower = p_rArmUpper.addOrReplaceChild("rArmLower",
                CubeListBuilder.create().texOffs(98, 9).addBox(-1.0F, 0.0F, -10.5F, 2.0F, 2.0F, 11.0F),
                PartPose.offset(0.0F, 10.5F, 0.0F));
        PartDefinition p_rWing02 = p_rArmLower.addOrReplaceChild("rWing02",
                CubeListBuilder.create().texOffs(186, 14).addBox(0.0F, 0.3F, -16.3F, 0.0F, 13.0F, 28.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_chest = root.addOrReplaceChild("chest",
                CubeListBuilder.create().texOffs(0, 46).addBox(-4.5F, -2.0F, 0.0F, 9.0F, 8.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -13.0F, -0.19198621771937624F, 0.0F, 0.0F));
        PartDefinition p_stomach = p_chest.addOrReplaceChild("stomach",
                CubeListBuilder.create().texOffs(0, 27).addBox(-3.5F, -3.0F, 0.0F, 7.0F, 6.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, 0.9F, 8.0F, -0.3665191429188092F, 0.0F, 0.0F));
        PartDefinition p_tail1 = p_stomach.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(50, 66).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.6F, 11.7F, 0.15707963267948966F, 0.0F, 0.0F));
        PartDefinition p_tail2 = p_tail1.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(85, 66).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 10.36F, 0.08726646259971647F, 0.0F, 0.0F));
        PartDefinition p_tailSpines2 = p_tail2.addOrReplaceChild("tailSpines2",
                CubeListBuilder.create().texOffs(90, 93).addBox(0.0F, -3.5F, 0.6F, 0.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail3 = p_tail2.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(118, 66).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.1F, 10.7F, 0.10471975511965977F, 0.0F, 0.0F));
        PartDefinition p_tailSpines3 = p_tail3.addOrReplaceChild("tailSpines3",
                CubeListBuilder.create().texOffs(117, 94).addBox(0.0F, -2.5F, 1.1F, 0.0F, 6.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail4 = p_tail3.addOrReplaceChild("tail4",
                CubeListBuilder.create().texOffs(149, 66).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 10.7F, 0.06981317007977318F, 0.0F, 0.0F));
        PartDefinition p_tailSpines4 = p_tail4.addOrReplaceChild("tailSpines4",
                CubeListBuilder.create().texOffs(148, 96).addBox(0.0F, -5.2F, 0.8F, 0.0F, 8.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail5 = p_tail4.addOrReplaceChild("tail5",
                CubeListBuilder.create().texOffs(178, 66).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.1F, 0.08726646259971647F, 0.0F, 0.0F));
        PartDefinition p_tailSpines5 = p_tail5.addOrReplaceChild("tailSpines5",
                CubeListBuilder.create().texOffs(178, 96).addBox(0.0F, -5.2F, -0.4F, 0.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_stabber = p_tail5.addOrReplaceChild("stabber",
                CubeListBuilder.create().texOffs(205, 66).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 9.6F));
        PartDefinition p_tailSpines1 = p_tail1.addOrReplaceChild("tailSpines1",
                CubeListBuilder.create().texOffs(58, 93).addBox(0.0F, -4.5F, 0.0F, 0.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_backSpine = p_stomach.addOrReplaceChild("backSpine",
                CubeListBuilder.create().texOffs(23, 65).addBox(0.0F, -8.9F, 1.0F, 0.0F, 6.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rThigh = p_stomach.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(59, 45).addBox(-1.5F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(-4.0F, 1.5F, 11.1F, -0.31869712141416456F, 0.06981317007977318F, 0.41887902047863906F));
        PartDefinition p_rShin = p_rThigh.addOrReplaceChild("rShin",
                CubeListBuilder.create().texOffs(79, 33).addBox(-1.0F, 0.7F, -2.1F, 3.0F, 3.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.40142572795869574F, 0.0F, 0.0F));
        PartDefinition p_rLowerLeg = p_rShin.addOrReplaceChild("rLowerLeg",
                CubeListBuilder.create().texOffs(113, 53).addBox(-0.5F, 0.4F, -1.5F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, 9.3F, -0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rFoot = p_rLowerLeg.addOrReplaceChild("rFoot",
                CubeListBuilder.create().texOffs(95, 24).addBox(0.0F, -0.6F, -3.5F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(-0.5F, 8.9F, -0.4F, 0.6829473363053812F, 0.136659280431156F, -0.31869712141416456F));
        PartDefinition p_lThigh = p_stomach.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(40, 45).addBox(-2.5F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(4.0F, 1.5F, 11.1F, -0.31869712141416456F, 0.06981317007977318F, -0.41887902047863906F));
        PartDefinition p_lShin = p_lThigh.addOrReplaceChild("lShin",
                CubeListBuilder.create().texOffs(79, 49).addBox(-2.0F, 0.7F, -2.1F, 3.0F, 3.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.40142572795869574F, 0.0F, 0.0F));
        PartDefinition p_lLowerLeg = p_lShin.addOrReplaceChild("lLowerLeg",
                CubeListBuilder.create().texOffs(113, 40).addBox(-1.5F, 0.4F, -1.5F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, 9.3F, -0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lFoot = p_lLowerLeg.addOrReplaceChild("lFoot",
                CubeListBuilder.create().texOffs(110, 24).addBox(-1.0F, -0.9F, -3.5F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(-0.5F, 8.9F, -0.4F, 0.6829473363053812F, -0.136659280431156F, 0.31869712141416456F));
        PartDefinition p_dorsalSpike = p_chest.addOrReplaceChild("dorsalSpike",
                CubeListBuilder.create().texOffs(0, 65).addBox(0.0F, -10.0F, 0.0F, 0.0F, 8.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lShoulderRidge = p_chest.addOrReplaceChild("lShoulderRidge",
                CubeListBuilder.create().texOffs(155, 25).addBox(3.5F, -2.8F, -2.3F, 2.0F, 9.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, -0.18203784098300857F, 0.13962634015954636F));
        PartDefinition p_head1 = p_chest.addOrReplaceChild("head1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -3.0F, 5.0F, 10.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, -3.7F, -0.5462880558742251F, 0.0F, 0.0F));
        PartDefinition p_innerJaw = p_head1.addOrReplaceChild("innerJaw",
                CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.7F, -4.6F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 4.3F, 2.2F, 0.7504915783575618F, 0.0F, 0.0F));
        PartDefinition p_lHeadFin = p_head1.addOrReplaceChild("lHeadFin",
                CubeListBuilder.create().texOffs(144, 0).mirror().addBox(0.8F, -12.3F, -3.4F, 5.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2792526803190927F, -0.4553564018453205F, 0.136659280431156F));
        PartDefinition p_rHeadFin = p_head1.addOrReplaceChild("rHeadFin",
                CubeListBuilder.create().texOffs(144, 0).addBox(-5.7F, -12.3F, -3.4F, 5.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2792526803190927F, 0.4553564018453205F, -0.136659280431156F));
        PartDefinition p_head2 = p_head1.addOrReplaceChild("head2",
                CubeListBuilder.create().texOffs(34, 0).addBox(-2.51F, 3.0F, -5.0F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5061454830783556F, 0.0F, 0.0F));
        PartDefinition p_head6 = p_head1.addOrReplaceChild("head6",
                CubeListBuilder.create().texOffs(53, 0).addBox(-0.5F, 3.0F, -5.0F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5061454830783556F, 0.0F, 0.0F));
        PartDefinition p_jawLower = p_head1.addOrReplaceChild("jawLower",
                CubeListBuilder.create().texOffs(88, 1).addBox(-1.5F, 0.3F, -5.0F, 3.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 4.2F, 2.5F, 0.7504915783575618F, 0.0F, 0.0F));
        PartDefinition p_head3 = p_head1.addOrReplaceChild("head3",
                CubeListBuilder.create().texOffs(51, 10).addBox(-2.5F, 2.8F, -5.0F, 5.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6283185307179586F, 0.0F, 0.0F));
        PartDefinition p_head4 = p_head1.addOrReplaceChild("head4",
                CubeListBuilder.create().texOffs(52, 20).addBox(-1.5F, 4.1F, -6.1F, 3.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7504915783575618F, 0.0F, 0.0F));
        PartDefinition p_head5 = p_head1.addOrReplaceChild("head5",
                CubeListBuilder.create().texOffs(28, 10).addBox(-2.5F, -14.0F, -4.2F, 5.0F, 10.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2792526803190927F, 0.0F, 0.0F));
        PartDefinition p_rShoulderRidge = p_chest.addOrReplaceChild("rShoulderRidge",
                CubeListBuilder.create().texOffs(172, 25).addBox(-5.5F, -2.8F, -2.3F, 2.0F, 9.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.18203784098300857F, -0.13962634015954636F));
        PartDefinition p_neck = p_chest.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(23, 86).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 6.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lArmUpper = root.addOrReplaceChild("lArmUpper",
                CubeListBuilder.create().texOffs(40, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(4.3F, -0.2F, -10.9F, 0.0F, 0.0F, -0.593411945678072F));
        PartDefinition p_lArmLower = p_lArmUpper.addOrReplaceChild("lArmLower",
                CubeListBuilder.create().texOffs(71, 9).addBox(-1.0F, 0.0F, -10.5F, 2.0F, 2.0F, 11.0F),
                PartPose.offset(0.0F, 10.5F, 0.0F));
        PartDefinition p_lWing02 = p_lArmLower.addOrReplaceChild("lWing02",
                CubeListBuilder.create().texOffs(186, 14).mirror().addBox(0.0F, 0.3F, -16.3F, 0.0F, 13.0F, 28.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lWing01 = p_lArmUpper.addOrReplaceChild("lWing01",
                CubeListBuilder.create().texOffs(188, 0).mirror().addBox(0.0F, -1.7F, 0.0F, 0.0F, 13.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
