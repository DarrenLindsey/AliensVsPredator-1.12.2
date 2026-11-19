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
 * Geometry converted automatically from the 1.12.2 ModelWarrior by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class WarriorModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "warrior"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart chest;
    public final ModelPart abdomen;
    public final ModelPart rThigh;
    public final ModelPart lThigh;
    public final ModelPart lShin1;
    public final ModelPart rShin1;
    public final ModelPart lShin2;
    public final ModelPart rShin2;
    public final ModelPart lFoot;
    public final ModelPart rFoot;
    public final ModelPart lArm1;
    public final ModelPart rArm1;
    public final ModelPart lClaw1;
    public final ModelPart rArm2;
    public final ModelPart neck;
    public final ModelPart headSpine1;
    public final ModelPart rHead;
    public final ModelPart jaw;
    public final ModelPart jaw2;
    public final ModelPart headSpine2;
    public final ModelPart lArm2;
    public final ModelPart rClaw1;
    public final ModelPart lClaw2;
    public final ModelPart rClaw2;
    public final ModelPart lHead;
    public final ModelPart part1;
    public final ModelPart part2;
    public final ModelPart part3;
    public final ModelPart part4;
    public final ModelPart headBase;
    public final ModelPart headTop;
    public final ModelPart back1;
    public final ModelPart back2;
    public final ModelPart tail1;
    public final ModelPart tail2;
    public final ModelPart tail3;
    public final ModelPart tail4;
    public final ModelPart tail5;
    public final ModelPart tailSpikes5;
    public final ModelPart tailSpikes4;
    public final ModelPart tailSpikes3;
    public final ModelPart tailSpikes2;
    public final ModelPart tailSpikes1;
    public final ModelPart stabber;

    public WarriorModel(ModelPart root)
    {
        this.root = root;
        this.chest = root.getChild("chest");
        this.parts.put("chest", this.chest);
        this.abdomen = root.getChild("abdomen");
        this.parts.put("abdomen", this.abdomen);
        this.rThigh = root.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.lThigh = root.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lShin1 = root.getChild("lShin1");
        this.parts.put("lShin1", this.lShin1);
        this.rShin1 = root.getChild("rShin1");
        this.parts.put("rShin1", this.rShin1);
        this.lShin2 = root.getChild("lShin2");
        this.parts.put("lShin2", this.lShin2);
        this.rShin2 = root.getChild("rShin2");
        this.parts.put("rShin2", this.rShin2);
        this.lFoot = root.getChild("lFoot");
        this.parts.put("lFoot", this.lFoot);
        this.rFoot = root.getChild("rFoot");
        this.parts.put("rFoot", this.rFoot);
        this.lArm1 = root.getChild("lArm1");
        this.parts.put("lArm1", this.lArm1);
        this.rArm1 = root.getChild("rArm1");
        this.parts.put("rArm1", this.rArm1);
        this.lClaw1 = root.getChild("lClaw1");
        this.parts.put("lClaw1", this.lClaw1);
        this.rArm2 = root.getChild("rArm2");
        this.parts.put("rArm2", this.rArm2);
        this.neck = root.getChild("neck");
        this.parts.put("neck", this.neck);
        this.headSpine1 = root.getChild("headSpine1");
        this.parts.put("headSpine1", this.headSpine1);
        this.rHead = root.getChild("rHead");
        this.parts.put("rHead", this.rHead);
        this.jaw = root.getChild("jaw");
        this.parts.put("jaw", this.jaw);
        this.jaw2 = root.getChild("jaw2");
        this.parts.put("jaw2", this.jaw2);
        this.headSpine2 = root.getChild("headSpine2");
        this.parts.put("headSpine2", this.headSpine2);
        this.lArm2 = root.getChild("lArm2");
        this.parts.put("lArm2", this.lArm2);
        this.rClaw1 = root.getChild("rClaw1");
        this.parts.put("rClaw1", this.rClaw1);
        this.lClaw2 = root.getChild("lClaw2");
        this.parts.put("lClaw2", this.lClaw2);
        this.rClaw2 = root.getChild("rClaw2");
        this.parts.put("rClaw2", this.rClaw2);
        this.lHead = root.getChild("lHead");
        this.parts.put("lHead", this.lHead);
        this.part1 = root.getChild("part1");
        this.parts.put("part1", this.part1);
        this.part2 = root.getChild("part2");
        this.parts.put("part2", this.part2);
        this.part3 = root.getChild("part3");
        this.parts.put("part3", this.part3);
        this.part4 = root.getChild("part4");
        this.parts.put("part4", this.part4);
        this.headBase = root.getChild("headBase");
        this.parts.put("headBase", this.headBase);
        this.headTop = root.getChild("headTop");
        this.parts.put("headTop", this.headTop);
        this.back1 = root.getChild("back1");
        this.parts.put("back1", this.back1);
        this.back2 = root.getChild("back2");
        this.parts.put("back2", this.back2);
        this.tail1 = root.getChild("tail1");
        this.parts.put("tail1", this.tail1);
        this.tail2 = root.getChild("tail2");
        this.parts.put("tail2", this.tail2);
        this.tail3 = root.getChild("tail3");
        this.parts.put("tail3", this.tail3);
        this.tail4 = root.getChild("tail4");
        this.parts.put("tail4", this.tail4);
        this.tail5 = root.getChild("tail5");
        this.parts.put("tail5", this.tail5);
        this.tailSpikes5 = root.getChild("tailSpikes5");
        this.parts.put("tailSpikes5", this.tailSpikes5);
        this.tailSpikes4 = root.getChild("tailSpikes4");
        this.parts.put("tailSpikes4", this.tailSpikes4);
        this.tailSpikes3 = root.getChild("tailSpikes3");
        this.parts.put("tailSpikes3", this.tailSpikes3);
        this.tailSpikes2 = root.getChild("tailSpikes2");
        this.parts.put("tailSpikes2", this.tailSpikes2);
        this.tailSpikes1 = root.getChild("tailSpikes1");
        this.parts.put("tailSpikes1", this.tailSpikes1);
        this.stabber = root.getChild("stabber");
        this.parts.put("stabber", this.stabber);
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
                PartPose.offsetAndRotation(0.0F, -7.5F, 0.0F, -0.1919862F, 0.0F, 0.0F));
        PartDefinition p_abdomen = root.addOrReplaceChild("abdomen",
                CubeListBuilder.create().texOffs(0, 27).addBox(-3.5F, -5.0F, 8.0F, 7.0F, 6.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, 0.0F, -0.5585054F, 0.0F, 0.0F));
        PartDefinition p_rThigh = root.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(59, 45).addBox(-1.5F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(-4.5F, 2.0F, 16.0F, -0.8028515F, 0.2443461F, 0.418879F));
        PartDefinition p_lThigh = root.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(40, 45).addBox(-2.5F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(4.5F, 2.0F, 16.0F, -0.8028515F, -0.2443461F, -0.418879F));
        PartDefinition p_lShin1 = root.addOrReplaceChild("lShin1",
                CubeListBuilder.create().texOffs(79, 49).addBox(-2.0F, 8.0F, -5.5F, 3.0F, 3.0F, 12.0F),
                PartPose.offsetAndRotation(4.5F, 2.0F, 16.0F, -0.4014257F, -0.2443461F, -0.418879F));
        PartDefinition p_rShin1 = root.addOrReplaceChild("rShin1",
                CubeListBuilder.create().texOffs(79, 33).addBox(-1.0F, 8.0F, -5.5F, 3.0F, 3.0F, 12.0F),
                PartPose.offsetAndRotation(-4.5F, 2.0F, 16.0F, -0.4014257F, 0.2443461F, 0.418879F));
        PartDefinition p_lShin2 = root.addOrReplaceChild("lShin2",
                CubeListBuilder.create().texOffs(113, 40).addBox(-1.5F, 5.5F, 9.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(4.5F, 2.0F, 16.0F, -0.8028515F, -0.2443461F, -0.418879F));
        PartDefinition p_rShin2 = root.addOrReplaceChild("rShin2",
                CubeListBuilder.create().texOffs(113, 53).addBox(-0.5F, 5.5F, 9.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(-4.5F, 2.0F, 16.0F, -0.8028515F, 0.2443461F, 0.418879F));
        PartDefinition p_lFoot = root.addOrReplaceChild("lFoot",
                CubeListBuilder.create().texOffs(110, 24).addBox(5.0F, 15.0F, -8.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(4.5F, 2.0F, 17.0F, 0.0F, -0.2443461F, 0.0F));
        PartDefinition p_rFoot = root.addOrReplaceChild("rFoot",
                CubeListBuilder.create().texOffs(95, 24).addBox(-7.0F, 15.0F, -8.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(-4.5F, 2.0F, 17.0F, 0.0F, 0.2443461F, 0.0F));
        PartDefinition p_lArm1 = root.addOrReplaceChild("lArm1",
                CubeListBuilder.create().texOffs(40, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(4.5F, -5.0F, 3.0F, 0.3665191F, 0.0F, -0.5934119F));
        PartDefinition p_rArm1 = root.addOrReplaceChild("rArm1",
                CubeListBuilder.create().texOffs(50, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(-4.5F, -5.0F, 3.0F, 0.3665191F, 0.0F, 0.5934119F));
        PartDefinition p_lClaw1 = root.addOrReplaceChild("lClaw1",
                CubeListBuilder.create().texOffs(83, 24).addBox(-1.0F, 11.0F, -13.5F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(4.5F, -5.0F, 3.0F, 0.3665191F, 0.0F, -0.5934119F));
        PartDefinition p_rArm2 = root.addOrReplaceChild("rArm2",
                CubeListBuilder.create().texOffs(98, 9).addBox(-1.0F, 10.5F, -10.5F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(-4.5F, -5.0F, 3.0F, 0.3665191F, 0.0F, 0.5934119F));
        PartDefinition p_neck = root.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(23, 86).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 6.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, 0.0F, -0.1919862F, 0.0F, 0.0F));
        PartDefinition p_headSpine1 = root.addOrReplaceChild("headSpine1",
                CubeListBuilder.create().texOffs(0, 100).addBox(0.0F, -5.0F, -6.0F, 0.0F, 10.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -4.0F, -0.6283185307179586F, 0.0F, 0.0F));
        PartDefinition p_rHead = root.addOrReplaceChild("rHead",
                CubeListBuilder.create().texOffs(34, 0).addBox(-2.51F, 3.0F, -5.0F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -4.0F, -0.122173F, 0.0F, 0.0F));
        PartDefinition p_jaw = root.addOrReplaceChild("jaw",
                CubeListBuilder.create().texOffs(51, 10).addBox(-2.5F, 2.8F, -5.0F, 5.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -4.0F, 0.122173F, 0.0F, 0.0F));
        PartDefinition p_jaw2 = root.addOrReplaceChild("jaw2",
                CubeListBuilder.create().texOffs(52, 20).addBox(-1.5F, 4.1F, -6.1F, 3.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -4.0F, 0.122173F, 0.0F, 0.0F));
        PartDefinition p_headSpine2 = root.addOrReplaceChild("headSpine2",
                CubeListBuilder.create().texOffs(7, 100).addBox(0.0F, -14.0F, -7.2F, 0.0F, 10.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -4.0F, -0.9075712F, 0.0F, 0.0F));
        PartDefinition p_lArm2 = root.addOrReplaceChild("lArm2",
                CubeListBuilder.create().texOffs(71, 9).addBox(-1.0F, 10.5F, -10.5F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(4.5F, -5.0F, 3.0F, 0.3665191F, 0.0F, -0.5934119F));
        PartDefinition p_rClaw1 = root.addOrReplaceChild("rClaw1",
                CubeListBuilder.create().texOffs(72, 24).addBox(-1.0F, 11.0F, -13.5F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-4.5F, -5.0F, 3.0F, 0.3665191F, 0.0F, 0.5934119F));
        PartDefinition p_lClaw2 = root.addOrReplaceChild("lClaw2",
                CubeListBuilder.create().texOffs(60, 30).addBox(-2.0F, 11.5F, -16.5F, 3.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(4.5F, -5.0F, 3.0F, 0.3665191F, 0.0F, -0.5934119F));
        PartDefinition p_rClaw2 = root.addOrReplaceChild("rClaw2",
                CubeListBuilder.create().texOffs(60, 38).addBox(-1.0F, 11.5F, -16.5F, 3.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(-4.5F, -5.0F, 3.0F, 0.3665191F, 0.0F, 0.5934119F));
        PartDefinition p_lHead = root.addOrReplaceChild("lHead",
                CubeListBuilder.create().texOffs(53, 0).addBox(-0.499F, 3.0F, -5.0F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -4.0F, -0.122173F, 0.0F, 0.0F));
        PartDefinition p_part1 = root.addOrReplaceChild("part1",
                CubeListBuilder.create().texOffs(10, 86).addBox(-4.5F, -11.0F, 7.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, 0.0F, -0.1919862F, 0.0F, -0.1745329F));
        PartDefinition p_part2 = root.addOrReplaceChild("part2",
                CubeListBuilder.create().texOffs(0, 86).addBox(2.5F, -11.0F, 7.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, 0.0F, -0.1919862F, 0.0F, 0.1745329F));
        PartDefinition p_part3 = root.addOrReplaceChild("part3",
                CubeListBuilder.create().texOffs(9, 86).addBox(-3.5F, -14.0F, 10.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, 0.0F, -0.5585054F, 0.0F, -0.1745329F));
        PartDefinition p_part4 = root.addOrReplaceChild("part4",
                CubeListBuilder.create().texOffs(0, 86).addBox(1.5F, -14.0F, 10.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, 0.0F, -0.5585054F, 0.0F, 0.1745329F));
        PartDefinition p_headBase = root.addOrReplaceChild("headBase",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -3.0F, 5.0F, 10.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -4.0F, -0.6283185307179586F, 0.0F, 0.0F));
        PartDefinition p_headTop = root.addOrReplaceChild("headTop",
                CubeListBuilder.create().texOffs(28, 10).addBox(-2.5F, -14.0F, -4.2F, 5.0F, 10.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -4.0F, -0.9075712F, 0.0F, 0.0F));
        PartDefinition p_back1 = root.addOrReplaceChild("back1",
                CubeListBuilder.create().texOffs(0, 65).addBox(0.0F, -10.0F, 0.0F, 0.0F, 8.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, 0.0F, -0.1919862F, 0.0F, 0.0F));
        PartDefinition p_back2 = root.addOrReplaceChild("back2",
                CubeListBuilder.create().texOffs(23, 65).addBox(0.0F, -11.0F, 8.0F, 0.0F, 6.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, 0.0F, -0.5585054F, 0.0F, 0.0F));
        PartDefinition p_tail1 = root.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(50, 66).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 6.5F, 5.5F, -0.4014257F, 0.0F, 0.0F));
        PartDefinition p_tail2 = root.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(85, 66).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 9.5F, 29.5F, -0.3141592653589793F, 0.0F, 0.0F));
        PartDefinition p_tail3 = root.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(118, 66).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 13.5F, 30.5F, -0.2094395F, 0.0F, 0.0F));
        PartDefinition p_tail4 = root.addOrReplaceChild("tail4",
                CubeListBuilder.create().texOffs(149, 66).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 15.5F, 40.5F, -0.1396263F, 0.0F, 0.0F));
        PartDefinition p_tail5 = root.addOrReplaceChild("tail5",
                CubeListBuilder.create().texOffs(178, 66).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 17.5F, 50.5F, -0.0523599F, 0.0F, 0.0F));
        PartDefinition p_tailSpikes5 = root.addOrReplaceChild("tailSpikes5",
                CubeListBuilder.create().texOffs(178, 96).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 17.5F, 51.5F, -0.0523599F, 0.0F, 0.0F));
        PartDefinition p_tailSpikes4 = root.addOrReplaceChild("tailSpikes4",
                CubeListBuilder.create().texOffs(149, 96).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 15.5F, 41.5F, -0.1396263F, 0.0F, 0.0F));
        PartDefinition p_tailSpikes3 = root.addOrReplaceChild("tailSpikes3",
                CubeListBuilder.create().texOffs(118, 94).addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 13.5F, 31.5F, -0.2094395F, 0.0F, 0.0F));
        PartDefinition p_tailSpikes2 = root.addOrReplaceChild("tailSpikes2",
                CubeListBuilder.create().texOffs(90, 93).addBox(0.0F, -3.5F, 0.0F, 0.0F, 8.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 9.5F, 20.5F, -0.3141592653589793F, 0.0F, 0.0F));
        PartDefinition p_tailSpikes1 = root.addOrReplaceChild("tailSpikes1",
                CubeListBuilder.create().texOffs(58, 93).addBox(0.0F, -4.5F, 0.0F, 0.0F, 8.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 6.5F, 5.5F, -0.4014257F, 0.0F, 0.0F));
        PartDefinition p_stabber = root.addOrReplaceChild("stabber",
                CubeListBuilder.create().texOffs(205, 66).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 18.5F, 61.5F, -0.0523599F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
