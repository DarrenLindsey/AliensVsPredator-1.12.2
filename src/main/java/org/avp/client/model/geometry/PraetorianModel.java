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
 * Geometry converted automatically from the 1.12.2 ModelPraetorian by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class PraetorianModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "praetorian"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart torso;
    public final ModelPart stomache;
    public final ModelPart rThigh;
    public final ModelPart lThigh;
    public final ModelPart lShin;
    public final ModelPart rShin;
    public final ModelPart lShin1;
    public final ModelPart rShin1;
    public final ModelPart lFoot;
    public final ModelPart rFoot;
    public final ModelPart neck;
    public final ModelPart headBase;
    public final ModelPart face1;
    public final ModelPart jaw1;
    public final ModelPart jaw2;
    public final ModelPart head2;
    public final ModelPart vertibrae1;
    public final ModelPart vertibrae2;
    public final ModelPart face2;
    public final ModelPart headSide1;
    public final ModelPart headSide2;
    public final ModelPart backStabber1;
    public final ModelPart backStabber2;
    public final ModelPart backStabber3;
    public final ModelPart backStabber4;
    public final ModelPart backStabber5;
    public final ModelPart backStabber6;
    public final ModelPart tailspines1;
    public final ModelPart tail2;
    public final ModelPart tail3;
    public final ModelPart stabber;
    public final ModelPart tail4;
    public final ModelPart tail5;
    public final ModelPart tailspines5;
    public final ModelPart tailspikes5;
    public final ModelPart tailspikes4;
    public final ModelPart tailspines4;
    public final ModelPart tailspines3;
    public final ModelPart tailspikes3;
    public final ModelPart tailspines2;
    public final ModelPart tailspikes2;
    public final ModelPart tail1;
    public final ModelPart tailspikes1;
    public final ModelPart lArm;
    public final ModelPart rArm;
    public final ModelPart lArm2;
    public final ModelPart rArm2;
    public final ModelPart lClaw;
    public final ModelPart rClaw;
    public final ModelPart lClaw2;
    public final ModelPart rClaw2;

    public PraetorianModel(ModelPart root)
    {
        this.root = root;
        this.torso = root.getChild("torso");
        this.parts.put("torso", this.torso);
        this.stomache = root.getChild("stomache");
        this.parts.put("stomache", this.stomache);
        this.rThigh = root.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.lThigh = root.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lShin = root.getChild("lShin");
        this.parts.put("lShin", this.lShin);
        this.rShin = root.getChild("rShin");
        this.parts.put("rShin", this.rShin);
        this.lShin1 = root.getChild("lShin1");
        this.parts.put("lShin1", this.lShin1);
        this.rShin1 = root.getChild("rShin1");
        this.parts.put("rShin1", this.rShin1);
        this.lFoot = root.getChild("lFoot");
        this.parts.put("lFoot", this.lFoot);
        this.rFoot = root.getChild("rFoot");
        this.parts.put("rFoot", this.rFoot);
        this.neck = root.getChild("neck");
        this.parts.put("neck", this.neck);
        this.headBase = root.getChild("headBase");
        this.parts.put("headBase", this.headBase);
        this.face1 = root.getChild("face1");
        this.parts.put("face1", this.face1);
        this.jaw1 = root.getChild("jaw1");
        this.parts.put("jaw1", this.jaw1);
        this.jaw2 = root.getChild("jaw2");
        this.parts.put("jaw2", this.jaw2);
        this.head2 = root.getChild("head2");
        this.parts.put("head2", this.head2);
        this.vertibrae1 = root.getChild("vertibrae1");
        this.parts.put("vertibrae1", this.vertibrae1);
        this.vertibrae2 = root.getChild("vertibrae2");
        this.parts.put("vertibrae2", this.vertibrae2);
        this.face2 = root.getChild("face2");
        this.parts.put("face2", this.face2);
        this.headSide1 = root.getChild("headSide1");
        this.parts.put("headSide1", this.headSide1);
        this.headSide2 = root.getChild("headSide2");
        this.parts.put("headSide2", this.headSide2);
        this.backStabber1 = root.getChild("backStabber1");
        this.parts.put("backStabber1", this.backStabber1);
        this.backStabber2 = root.getChild("backStabber2");
        this.parts.put("backStabber2", this.backStabber2);
        this.backStabber3 = root.getChild("backStabber3");
        this.parts.put("backStabber3", this.backStabber3);
        this.backStabber4 = root.getChild("backStabber4");
        this.parts.put("backStabber4", this.backStabber4);
        this.backStabber5 = root.getChild("backStabber5");
        this.parts.put("backStabber5", this.backStabber5);
        this.backStabber6 = root.getChild("backStabber6");
        this.parts.put("backStabber6", this.backStabber6);
        this.tailspines1 = root.getChild("tailspines1");
        this.parts.put("tailspines1", this.tailspines1);
        this.tail2 = root.getChild("tail2");
        this.parts.put("tail2", this.tail2);
        this.tail3 = root.getChild("tail3");
        this.parts.put("tail3", this.tail3);
        this.stabber = root.getChild("stabber");
        this.parts.put("stabber", this.stabber);
        this.tail4 = root.getChild("tail4");
        this.parts.put("tail4", this.tail4);
        this.tail5 = root.getChild("tail5");
        this.parts.put("tail5", this.tail5);
        this.tailspines5 = root.getChild("tailspines5");
        this.parts.put("tailspines5", this.tailspines5);
        this.tailspikes5 = root.getChild("tailspikes5");
        this.parts.put("tailspikes5", this.tailspikes5);
        this.tailspikes4 = root.getChild("tailspikes4");
        this.parts.put("tailspikes4", this.tailspikes4);
        this.tailspines4 = root.getChild("tailspines4");
        this.parts.put("tailspines4", this.tailspines4);
        this.tailspines3 = root.getChild("tailspines3");
        this.parts.put("tailspines3", this.tailspines3);
        this.tailspikes3 = root.getChild("tailspikes3");
        this.parts.put("tailspikes3", this.tailspikes3);
        this.tailspines2 = root.getChild("tailspines2");
        this.parts.put("tailspines2", this.tailspines2);
        this.tailspikes2 = root.getChild("tailspikes2");
        this.parts.put("tailspikes2", this.tailspikes2);
        this.tail1 = root.getChild("tail1");
        this.parts.put("tail1", this.tail1);
        this.tailspikes1 = root.getChild("tailspikes1");
        this.parts.put("tailspikes1", this.tailspikes1);
        this.lArm = root.getChild("lArm");
        this.parts.put("lArm", this.lArm);
        this.rArm = root.getChild("rArm");
        this.parts.put("rArm", this.rArm);
        this.lArm2 = root.getChild("lArm2");
        this.parts.put("lArm2", this.lArm2);
        this.rArm2 = root.getChild("rArm2");
        this.parts.put("rArm2", this.rArm2);
        this.lClaw = root.getChild("lClaw");
        this.parts.put("lClaw", this.lClaw);
        this.rClaw = root.getChild("rClaw");
        this.parts.put("rClaw", this.rClaw);
        this.lClaw2 = root.getChild("lClaw2");
        this.parts.put("lClaw2", this.lClaw2);
        this.rClaw2 = root.getChild("rClaw2");
        this.parts.put("rClaw2", this.rClaw2);
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
        PartDefinition p_torso = root.addOrReplaceChild("torso",
                CubeListBuilder.create().texOffs(0, 46).addBox(-4.5F, -2.0F, 0.0F, 9.0F, 8.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -8.0F, -0.1919862F, 0.0F, 0.0F));
        PartDefinition p_stomache = root.addOrReplaceChild("stomache",
                CubeListBuilder.create().texOffs(0, 27).addBox(-3.5F, -5.0F, 8.0F, 7.0F, 6.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -8.0F, -0.5585054F, 0.0F, 0.0F));
        PartDefinition p_rThigh = root.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(59, 45).addBox(-1.5F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(-4.5F, 7.0F, 8.0F, -0.8028515F, 0.2443461F, 0.418879F));
        PartDefinition p_lThigh = root.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(40, 45).addBox(-2.5F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(4.5F, 7.0F, 8.0F, -0.8028515F, -0.2443461F, -0.418879F));
        PartDefinition p_lShin = root.addOrReplaceChild("lShin",
                CubeListBuilder.create().texOffs(79, 49).addBox(-2.0F, 8.0F, -5.5F, 3.0F, 3.0F, 12.0F),
                PartPose.offsetAndRotation(4.5F, 7.0F, 8.0F, -0.4014257F, -0.2443461F, -0.418879F));
        PartDefinition p_rShin = root.addOrReplaceChild("rShin",
                CubeListBuilder.create().texOffs(79, 33).addBox(-1.0F, 8.0F, -5.5F, 3.0F, 3.0F, 12.0F),
                PartPose.offsetAndRotation(-4.5F, 7.0F, 8.0F, -0.4014257F, 0.2443461F, 0.418879F));
        PartDefinition p_lShin1 = root.addOrReplaceChild("lShin1",
                CubeListBuilder.create().texOffs(113, 40).addBox(-1.5F, 5.5F, 9.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(4.5F, 7.0F, 8.0F, -0.8028515F, -0.2443461F, -0.418879F));
        PartDefinition p_rShin1 = root.addOrReplaceChild("rShin1",
                CubeListBuilder.create().texOffs(113, 53).addBox(-0.5F, 5.5F, 9.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(-4.5F, 7.0F, 8.0F, -0.8028515F, 0.2443461F, 0.418879F));
        PartDefinition p_lFoot = root.addOrReplaceChild("lFoot",
                CubeListBuilder.create().texOffs(110, 24).addBox(5.0F, 15.0F, -8.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(4.5F, 7.0F, 9.0F, 0.0F, -0.2443461F, 0.0F));
        PartDefinition p_rFoot = root.addOrReplaceChild("rFoot",
                CubeListBuilder.create().texOffs(95, 24).addBox(-7.0F, 15.0F, -8.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(-4.5F, 7.0F, 9.0F, 0.0F, 0.2443461F, 0.0F));
        PartDefinition p_neck = root.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(23, 86).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 6.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -8.0F, -0.1919862F, 0.0F, 0.0F));
        PartDefinition p_headBase = root.addOrReplaceChild("headBase",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -3.0F, 5.0F, 10.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -12.0F, -0.6283185307179586F, 0.0F, 0.0F));
        PartDefinition p_face1 = root.addOrReplaceChild("face1",
                CubeListBuilder.create().texOffs(34, 0).addBox(-2.51F, 3.0F, -5.0F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -12.0F, -0.122173F, 0.0F, 0.0F));
        PartDefinition p_jaw1 = root.addOrReplaceChild("jaw1",
                CubeListBuilder.create().texOffs(51, 10).addBox(-2.5F, 2.8F, -5.0F, 5.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -12.0F, 0.122173F, 0.0F, 0.0F));
        PartDefinition p_jaw2 = root.addOrReplaceChild("jaw2",
                CubeListBuilder.create().texOffs(52, 20).addBox(-1.5F, 4.1F, -6.1F, 3.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -12.0F, 0.122173F, 0.0F, 0.0F));
        PartDefinition p_head2 = root.addOrReplaceChild("head2",
                CubeListBuilder.create().texOffs(127, 10).addBox(-2.5F, -21.0F, -4.2F, 5.0F, 17.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -12.0F, -0.9075712F, 0.0F, 0.0F));
        PartDefinition p_vertibrae1 = root.addOrReplaceChild("vertibrae1",
                CubeListBuilder.create().texOffs(0, 65).addBox(0.0F, -10.0F, 0.0F, 0.0F, 8.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -8.0F, -0.1919862F, 0.0F, 0.0F));
        PartDefinition p_vertibrae2 = root.addOrReplaceChild("vertibrae2",
                CubeListBuilder.create().texOffs(23, 65).addBox(0.0F, -11.0F, 8.0F, 0.0F, 6.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -8.0F, -0.5585054F, 0.0F, 0.0F));
        PartDefinition p_face2 = root.addOrReplaceChild("face2",
                CubeListBuilder.create().texOffs(53, 0).addBox(-0.499F, 3.0F, -5.0F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -12.0F, -0.122173F, 0.0F, 0.0F));
        PartDefinition p_headSide1 = root.addOrReplaceChild("headSide1",
                CubeListBuilder.create().texOffs(151, 10).addBox(-2.8F, -21.0F, -3.2F, 5.0F, 17.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -12.0F, -0.8552113F, 0.3665191F, 0.0F));
        PartDefinition p_headSide2 = root.addOrReplaceChild("headSide2",
                CubeListBuilder.create().texOffs(169, 10).addBox(-1.8F, -21.0F, -3.2F, 5.0F, 17.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -12.0F, -0.8552113F, -0.3665191F, 0.0F));
        PartDefinition p_backStabber1 = root.addOrReplaceChild("backStabber1",
                CubeListBuilder.create().texOffs(0, 86).addBox(0.0F, -18.0F, 0.0F, 2.0F, 19.0F, 0.0F),
                PartPose.offsetAndRotation(-3.0F, -2.3F, 1.0F, -0.5235988F, -1.134464F, 0.2617994F));
        PartDefinition p_backStabber2 = root.addOrReplaceChild("backStabber2",
                CubeListBuilder.create().texOffs(0, 86).addBox(0.0F, -18.0F, 0.0F, 2.0F, 19.0F, 0.0F),
                PartPose.offsetAndRotation(3.0F, -3.3F, -2.0F, 0.6981317007977318F, -1.919862F, 0.0F));
        PartDefinition p_backStabber3 = root.addOrReplaceChild("backStabber3",
                CubeListBuilder.create().texOffs(8, 86).addBox(0.0F, -15.0F, 0.0F, 2.0F, 16.0F, 0.0F),
                PartPose.offsetAndRotation(-3.0F, -1.3F, 3.0F, -0.3490659F, -1.134464F, 0.6108652F));
        PartDefinition p_backStabber4 = root.addOrReplaceChild("backStabber4",
                CubeListBuilder.create().texOffs(8, 86).addBox(0.0F, -16.0F, 0.0F, 2.0F, 16.0F, 0.0F),
                PartPose.offsetAndRotation(3.0F, -2.3F, 1.0F, 0.5235988F, -1.919862F, 0.2617994F));
        PartDefinition p_backStabber5 = root.addOrReplaceChild("backStabber5",
                CubeListBuilder.create().texOffs(8, 86).addBox(0.0F, -15.0F, 0.0F, 2.0F, 16.0F, 0.0F),
                PartPose.offsetAndRotation(-3.0F, -3.3F, -2.0F, -0.6981317007977318F, -1.134464F, 0.0F));
        PartDefinition p_backStabber6 = root.addOrReplaceChild("backStabber6",
                CubeListBuilder.create().texOffs(8, 86).addBox(0.0F, -15.0F, 0.0F, 2.0F, 16.0F, 0.0F),
                PartPose.offsetAndRotation(3.0F, -1.3F, 3.0F, 0.3665191F, -1.919862F, 0.6108652F));
        PartDefinition p_tailspines1 = root.addOrReplaceChild("tailspines1",
                CubeListBuilder.create().texOffs(50, 83).addBox(-4.0F, -0.5F, 0.0F, 8.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 6.5F, 10.5F, -0.4014257F, 0.0F, 0.0F));
        PartDefinition p_tail2 = root.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(85, 66).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 9.5F, 20.5F, -0.3141592653589793F, 0.0F, 0.0F));
        PartDefinition p_tail3 = root.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(118, 66).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 13.5F, 30.5F, -0.2094395F, 0.0F, 0.0F));
        PartDefinition p_stabber = root.addOrReplaceChild("stabber",
                CubeListBuilder.create().texOffs(205, 66).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 18.5F, 61.5F, -0.0523599F, 0.0F, 0.0F));
        PartDefinition p_tail4 = root.addOrReplaceChild("tail4",
                CubeListBuilder.create().texOffs(149, 66).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 15.5F, 40.5F, -0.1396263F, 0.0F, 0.0F));
        PartDefinition p_tail5 = root.addOrReplaceChild("tail5",
                CubeListBuilder.create().texOffs(178, 66).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 17.5F, 50.5F, -0.0523599F, 0.0F, 0.0F));
        PartDefinition p_tailspines5 = root.addOrReplaceChild("tailspines5",
                CubeListBuilder.create().texOffs(178, 80).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 17.5F, 51.5F, -0.0523599F, 0.0F, 0.0F));
        PartDefinition p_tailspikes5 = root.addOrReplaceChild("tailspikes5",
                CubeListBuilder.create().texOffs(178, 96).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 17.5F, 51.5F, -0.0523599F, 0.0F, 0.0F));
        PartDefinition p_tailspikes4 = root.addOrReplaceChild("tailspikes4",
                CubeListBuilder.create().texOffs(149, 96).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 15.5F, 41.5F, -0.1396263F, 0.0F, 0.0F));
        PartDefinition p_tailspines4 = root.addOrReplaceChild("tailspines4",
                CubeListBuilder.create().texOffs(149, 81).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 15.5F, 41.5F, -0.1396263F, 0.0F, 0.0F));
        PartDefinition p_tailspines3 = root.addOrReplaceChild("tailspines3",
                CubeListBuilder.create().texOffs(118, 82).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 13.5F, 31.5F, -0.2094395F, 0.0F, 0.0F));
        PartDefinition p_tailspikes3 = root.addOrReplaceChild("tailspikes3",
                CubeListBuilder.create().texOffs(118, 94).addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 13.5F, 31.5F, -0.2094395F, 0.0F, 0.0F));
        PartDefinition p_tailspines2 = root.addOrReplaceChild("tailspines2",
                CubeListBuilder.create().texOffs(85, 85).addBox(-4.0F, 0.5F, 1.0F, 8.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 9.5F, 19.5F, -0.3141592653589793F, 0.0F, 0.0F));
        PartDefinition p_tailspikes2 = root.addOrReplaceChild("tailspikes2",
                CubeListBuilder.create().texOffs(90, 93).addBox(0.0F, -3.5F, 0.0F, 0.0F, 8.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 9.5F, 20.5F, -0.3141592653589793F, 0.0F, 0.0F));
        PartDefinition p_tail1 = root.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(50, 66).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 6.5F, 10.5F, -0.4014257F, 0.0F, 0.0F));
        PartDefinition p_tailspikes1 = root.addOrReplaceChild("tailspikes1",
                CubeListBuilder.create().texOffs(58, 93).addBox(0.0F, -4.5F, 0.0F, 0.0F, 8.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 6.5F, 10.5F, -0.4014257F, 0.0F, 0.0F));
        PartDefinition p_lArm = root.addOrReplaceChild("lArm",
                CubeListBuilder.create().texOffs(40, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, -0.5934119F));
        PartDefinition p_rArm = root.addOrReplaceChild("rArm",
                CubeListBuilder.create().texOffs(50, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(-4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, 0.5934119F));
        PartDefinition p_lArm2 = root.addOrReplaceChild("lArm2",
                CubeListBuilder.create().texOffs(83, 24).addBox(-1.0F, 11.0F, -13.5F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, -0.5934119F));
        PartDefinition p_rArm2 = root.addOrReplaceChild("rArm2",
                CubeListBuilder.create().texOffs(98, 9).addBox(-1.0F, 10.5F, -10.5F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(-4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, 0.5934119F));
        PartDefinition p_lClaw = root.addOrReplaceChild("lClaw",
                CubeListBuilder.create().texOffs(71, 9).addBox(-1.0F, 10.5F, -10.5F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, -0.5934119F));
        PartDefinition p_rClaw = root.addOrReplaceChild("rClaw",
                CubeListBuilder.create().texOffs(72, 24).addBox(-1.0F, 11.0F, -13.5F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, 0.5934119F));
        PartDefinition p_lClaw2 = root.addOrReplaceChild("lClaw2",
                CubeListBuilder.create().texOffs(60, 30).addBox(-2.0F, 11.5F, -16.5F, 3.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, -0.5934119F));
        PartDefinition p_rClaw2 = root.addOrReplaceChild("rClaw2",
                CubeListBuilder.create().texOffs(60, 38).addBox(-1.0F, 11.5F, -16.5F, 3.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(-4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, 0.5934119F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
