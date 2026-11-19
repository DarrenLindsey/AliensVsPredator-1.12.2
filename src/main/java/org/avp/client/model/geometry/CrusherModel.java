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
 * Geometry converted automatically from the 1.12.2 ModelCrusher by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class CrusherModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "crusher"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart sTorso;
    public final ModelPart sTorso2;
    public final ModelPart sRightThigh;
    public final ModelPart sLeftThigh;
    public final ModelPart sLeftShin;
    public final ModelPart sRightShin;
    public final ModelPart sLeftShin2;
    public final ModelPart sRightShin2;
    public final ModelPart sLeftFoot;
    public final ModelPart sRightFoot;
    public final ModelPart sLeftArm;
    public final ModelPart sRightArm;
    public final ModelPart sLeftClaw;
    public final ModelPart sRightArm2;
    public final ModelPart sNeck;
    public final ModelPart sHead;
    public final ModelPart sHead2;
    public final ModelPart sJaw1;
    public final ModelPart sLeftArm2;
    public final ModelPart sRightClaw;
    public final ModelPart sLeftClaw2;
    public final ModelPart sRightClaw2;
    public final ModelPart sVertibrae1;
    public final ModelPart sTail1;
    public final ModelPart sTail2;
    public final ModelPart sTail3;
    public final ModelPart sStabber;
    public final ModelPart sTail4;
    public final ModelPart sTail5;
    public final ModelPart sVertibrae2;
    public final ModelPart sSpike3;
    public final ModelPart sSpike4;
    public final ModelPart sSpike2;
    public final ModelPart sSpike5;
    public final ModelPart sHead4;
    public final ModelPart crest0;
    public final ModelPart crest1;
    public final ModelPart crest2;
    public final ModelPart crest3;
    public final ModelPart crest4;
    public final ModelPart crest5;
    public final ModelPart sArmCrusher1;
    public final ModelPart sArmCrusher;

    public CrusherModel(ModelPart root)
    {
        this.root = root;
        this.sTorso = root.getChild("sTorso");
        this.parts.put("sTorso", this.sTorso);
        this.sTorso2 = root.getChild("sTorso2");
        this.parts.put("sTorso2", this.sTorso2);
        this.sRightThigh = root.getChild("sRightThigh");
        this.parts.put("sRightThigh", this.sRightThigh);
        this.sLeftThigh = root.getChild("sLeftThigh");
        this.parts.put("sLeftThigh", this.sLeftThigh);
        this.sLeftShin = root.getChild("sLeftShin");
        this.parts.put("sLeftShin", this.sLeftShin);
        this.sRightShin = root.getChild("sRightShin");
        this.parts.put("sRightShin", this.sRightShin);
        this.sLeftShin2 = root.getChild("sLeftShin2");
        this.parts.put("sLeftShin2", this.sLeftShin2);
        this.sRightShin2 = root.getChild("sRightShin2");
        this.parts.put("sRightShin2", this.sRightShin2);
        this.sLeftFoot = root.getChild("sLeftFoot");
        this.parts.put("sLeftFoot", this.sLeftFoot);
        this.sRightFoot = root.getChild("sRightFoot");
        this.parts.put("sRightFoot", this.sRightFoot);
        this.sLeftArm = root.getChild("sLeftArm");
        this.parts.put("sLeftArm", this.sLeftArm);
        this.sRightArm = root.getChild("sRightArm");
        this.parts.put("sRightArm", this.sRightArm);
        this.sLeftClaw = root.getChild("sLeftClaw");
        this.parts.put("sLeftClaw", this.sLeftClaw);
        this.sRightArm2 = root.getChild("sRightArm2");
        this.parts.put("sRightArm2", this.sRightArm2);
        this.sNeck = root.getChild("sNeck");
        this.parts.put("sNeck", this.sNeck);
        this.sHead = root.getChild("sHead");
        this.parts.put("sHead", this.sHead);
        this.sHead2 = root.getChild("sHead2");
        this.parts.put("sHead2", this.sHead2);
        this.sJaw1 = root.getChild("sJaw1");
        this.parts.put("sJaw1", this.sJaw1);
        this.sLeftArm2 = root.getChild("sLeftArm2");
        this.parts.put("sLeftArm2", this.sLeftArm2);
        this.sRightClaw = root.getChild("sRightClaw");
        this.parts.put("sRightClaw", this.sRightClaw);
        this.sLeftClaw2 = root.getChild("sLeftClaw2");
        this.parts.put("sLeftClaw2", this.sLeftClaw2);
        this.sRightClaw2 = root.getChild("sRightClaw2");
        this.parts.put("sRightClaw2", this.sRightClaw2);
        this.sVertibrae1 = root.getChild("sVertibrae1");
        this.parts.put("sVertibrae1", this.sVertibrae1);
        this.sTail1 = root.getChild("sTail1");
        this.parts.put("sTail1", this.sTail1);
        this.sTail2 = root.getChild("sTail2");
        this.parts.put("sTail2", this.sTail2);
        this.sTail3 = root.getChild("sTail3");
        this.parts.put("sTail3", this.sTail3);
        this.sStabber = root.getChild("sStabber");
        this.parts.put("sStabber", this.sStabber);
        this.sTail4 = root.getChild("sTail4");
        this.parts.put("sTail4", this.sTail4);
        this.sTail5 = root.getChild("sTail5");
        this.parts.put("sTail5", this.sTail5);
        this.sVertibrae2 = root.getChild("sVertibrae2");
        this.parts.put("sVertibrae2", this.sVertibrae2);
        this.sSpike3 = root.getChild("sSpike3");
        this.parts.put("sSpike3", this.sSpike3);
        this.sSpike4 = root.getChild("sSpike4");
        this.parts.put("sSpike4", this.sSpike4);
        this.sSpike2 = root.getChild("sSpike2");
        this.parts.put("sSpike2", this.sSpike2);
        this.sSpike5 = root.getChild("sSpike5");
        this.parts.put("sSpike5", this.sSpike5);
        this.sHead4 = root.getChild("sHead4");
        this.parts.put("sHead4", this.sHead4);
        this.crest0 = root.getChild("crest0");
        this.parts.put("crest0", this.crest0);
        this.crest1 = root.getChild("crest1");
        this.parts.put("crest1", this.crest1);
        this.crest2 = root.getChild("crest2");
        this.parts.put("crest2", this.crest2);
        this.crest3 = root.getChild("crest3");
        this.parts.put("crest3", this.crest3);
        this.crest4 = root.getChild("crest4");
        this.parts.put("crest4", this.crest4);
        this.crest5 = root.getChild("crest5");
        this.parts.put("crest5", this.crest5);
        this.sArmCrusher1 = root.getChild("sArmCrusher1");
        this.parts.put("sArmCrusher1", this.sArmCrusher1);
        this.sArmCrusher = root.getChild("sArmCrusher");
        this.parts.put("sArmCrusher", this.sArmCrusher);
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
        PartDefinition p_sTorso = root.addOrReplaceChild("sTorso",
                CubeListBuilder.create().texOffs(50, 0).addBox(-4.5F, -2.0F, 0.0F, 10.0F, 9.0F, 11.0F),
                PartPose.offsetAndRotation(-1.0F, -7.5F, 0.0F, -0.117629F, 0.0F, 0.0F));
        PartDefinition p_sTorso2 = root.addOrReplaceChild("sTorso2",
                CubeListBuilder.create().texOffs(93, 0).addBox(-3.5F, -9.0F, 6.0F, 7.0F, 6.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, -0.5585054F, 0.0F, 0.0F));
        PartDefinition p_sRightThigh = root.addOrReplaceChild("sRightThigh",
                CubeListBuilder.create().texOffs(31, 0).addBox(-1.5F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(-4.5F, 2.0F, 16.0F, -0.8028515F, 0.2443461F, 0.418879F));
        PartDefinition p_sLeftThigh = root.addOrReplaceChild("sLeftThigh",
                CubeListBuilder.create().texOffs(132, 0).addBox(-2.5F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(4.5F, 2.0F, 16.0F, -0.8028515F, -0.2443461F, -0.418879F));
        PartDefinition p_sLeftShin = root.addOrReplaceChild("sLeftShin",
                CubeListBuilder.create().texOffs(155, 0).addBox(-2.0F, 8.0F, -5.5F, 3.0F, 3.0F, 12.0F),
                PartPose.offsetAndRotation(4.5F, 2.0F, 16.0F, -0.4014257F, -0.2443461F, -0.418879F));
        PartDefinition p_sRightShin = root.addOrReplaceChild("sRightShin",
                CubeListBuilder.create().texOffs(186, 0).addBox(-1.0F, 8.0F, -5.5F, 3.0F, 3.0F, 12.0F),
                PartPose.offsetAndRotation(-4.5F, 2.0F, 16.0F, -0.4014257F, 0.2443461F, 0.418879F));
        PartDefinition p_sLeftShin2 = root.addOrReplaceChild("sLeftShin2",
                CubeListBuilder.create().texOffs(217, 0).addBox(-1.5F, 5.5F, 9.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(4.5F, 2.0F, 16.0F, -0.8028515F, -0.2443461F, -0.418879F));
        PartDefinition p_sRightShin2 = root.addOrReplaceChild("sRightShin2",
                CubeListBuilder.create().texOffs(226, 0).addBox(-0.5F, 5.5F, 9.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(-4.5F, 2.0F, 16.0F, -0.8028515F, 0.2443461F, 0.418879F));
        PartDefinition p_sLeftFoot = root.addOrReplaceChild("sLeftFoot",
                CubeListBuilder.create().texOffs(235, 0).addBox(5.0F, 15.0F, -7.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(4.5F, 2.0F, 16.0F, 0.0F, -0.2443461F, 0.0F));
        PartDefinition p_sRightFoot = root.addOrReplaceChild("sRightFoot",
                CubeListBuilder.create().texOffs(242, 8).addBox(-7.0F, 15.0F, -7.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(-4.5F, 2.0F, 16.0F, 0.0F, 0.2443461F, 0.0F));
        PartDefinition p_sLeftArm = root.addOrReplaceChild("sLeftArm",
                CubeListBuilder.create().texOffs(243, 16).addBox(-1.0F, -1.0F, 1.0F, 3.0F, 13.0F, 3.0F),
                PartPose.offsetAndRotation(5.5F, -5.0F, 1.0F, 0.1806261F, 0.0F, -0.2443461F));
        PartDefinition p_sRightArm = root.addOrReplaceChild("sRightArm",
                CubeListBuilder.create().texOffs(229, 12).addBox(-1.0F, -1.0F, 1.0F, 3.0F, 13.0F, 3.0F),
                PartPose.offsetAndRotation(-5.5F, -5.0F, 1.0F, 0.2792527F, -0.1092638F, 0.2246208F));
        PartDefinition p_sLeftClaw = root.addOrReplaceChild("sLeftClaw",
                CubeListBuilder.create().texOffs(217, 12).addBox(-1.0F, 11.0F, -13.5F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(5.5F, -5.0F, 1.0F, 0.3665191F, 0.0F, -0.2443461F));
        PartDefinition p_sRightArm2 = root.addOrReplaceChild("sRightArm2",
                CubeListBuilder.create().texOffs(198, 17).addBox(-1.0F, 10.5F, -9.5F, 3.0F, 3.0F, 12.0F),
                PartPose.offsetAndRotation(-5.5F, -5.0F, 1.0F, 0.3665191F, 0.0F, 0.2443461F));
        PartDefinition p_sNeck = root.addOrReplaceChild("sNeck",
                CubeListBuilder.create().texOffs(179, 16).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 6.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -2.2F, -0.1919862F, 0.0F, 0.0F));
        PartDefinition p_sHead = root.addOrReplaceChild("sHead",
                CubeListBuilder.create().texOffs(152, 21).addBox(-3.0F, -4.0F, -4.5F, 6.0F, 10.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -2.2F, -0.6283185307179586F, 0.0F, 0.0F));
        PartDefinition p_sHead2 = root.addOrReplaceChild("sHead2",
                CubeListBuilder.create().texOffs(131, 21).addBox(-2.5F, 3.0F, -7.0F, 5.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -2.2F, -0.122173F, 0.0F, 0.0F));
        PartDefinition p_sJaw1 = root.addOrReplaceChild("sJaw1",
                CubeListBuilder.create().texOffs(95, 19).addBox(-1.5F, 4.1F, -8.1F, 3.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -2.2F, 0.122173F, 0.0F, 0.0F));
        PartDefinition p_sLeftArm2 = root.addOrReplaceChild("sLeftArm2",
                CubeListBuilder.create().texOffs(64, 21).addBox(-1.0F, 10.5F, -10.5F, 3.0F, 3.0F, 12.0F),
                PartPose.offsetAndRotation(5.5F, -5.0F, 1.0F, 0.3665191F, 0.0F, -0.2443461F));
        PartDefinition p_sRightClaw = root.addOrReplaceChild("sRightClaw",
                CubeListBuilder.create().texOffs(158, 16).addBox(-1.0F, 11.0F, -12.5F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-5.5F, -5.0F, 1.0F, 0.3665191F, 0.0F, 0.2443461F));
        PartDefinition p_sLeftClaw2 = root.addOrReplaceChild("sLeftClaw2",
                CubeListBuilder.create().texOffs(45, 21).addBox(-2.0F, 11.5F, -16.5F, 3.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(5.5F, -5.0F, 1.0F, 0.3665191F, 0.0F, -0.2443461F));
        PartDefinition p_sRightClaw2 = root.addOrReplaceChild("sRightClaw2",
                CubeListBuilder.create().texOffs(26, 20).addBox(-1.0F, 11.5F, -15.5F, 3.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(-5.5F, -5.0F, 1.0F, 0.3665191F, 0.0F, 0.2443461F));
        PartDefinition p_sVertibrae1 = root.addOrReplaceChild("sVertibrae1",
                CubeListBuilder.create().texOffs(43, 28).addBox(0.0F, -10.0F, 0.0F, 0.0F, 8.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, 0.0F, -0.1919862F, 0.0F, 0.0F));
        PartDefinition p_sTail1 = root.addOrReplaceChild("sTail1",
                CubeListBuilder.create().texOffs(21, 70).addBox(-2.5F, -2.5F, 0.0F, 5.0F, 5.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, 18.5F, -0.3665191F, 0.0F, 0.0F));
        PartDefinition p_sTail2 = root.addOrReplaceChild("sTail2",
                CubeListBuilder.create().texOffs(5, 27).addBox(-2.0F, -1.5F, 11.0F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, 18.5F, -0.3141592653589793F, 0.0F, 0.0F));
        PartDefinition p_sTail3 = root.addOrReplaceChild("sTail3",
                CubeListBuilder.create().texOffs(7, 43).addBox(-1.5F, 1.5F, 21.0F, 3.0F, 3.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, 18.5F, -0.2094395F, 0.0F, 0.0F));
        PartDefinition p_sStabber = root.addOrReplaceChild("sStabber",
                CubeListBuilder.create().texOffs(95, 27).addBox(-1.5F, 9.0F, 52.0F, 3.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, 18.5F, -0.0523599F, 0.0F, 0.0F));
        PartDefinition p_sTail4 = root.addOrReplaceChild("sTail4",
                CubeListBuilder.create().texOffs(64, 37).addBox(-1.0F, 4.0F, 31.0F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, 18.5F, -0.1396263F, 0.0F, 0.0F));
        PartDefinition p_sTail5 = root.addOrReplaceChild("sTail5",
                CubeListBuilder.create().texOffs(124, 30).addBox(-0.5F, 8.5F, 41.0F, 1.0F, 1.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, 18.5F, -0.0523599F, 0.0F, 0.0F));
        PartDefinition p_sVertibrae2 = root.addOrReplaceChild("sVertibrae2",
                CubeListBuilder.create().texOffs(101, 39).addBox(0.0F, 6.8F, 40.8F, 0.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, 18.5F, -0.0523599F, 0.0F, 0.0F));
        PartDefinition p_sSpike3 = root.addOrReplaceChild("sSpike3",
                CubeListBuilder.create().texOffs(0, 43).addBox(0.0F, -15.0F, 0.0F, 2.0F, 19.0F, 1.0F),
                PartPose.offsetAndRotation(4.0F, -10.33333F, 8.0F, 0.3490659F, -1.5707963267948966F, 0.1745329F));
        PartDefinition p_sSpike4 = root.addOrReplaceChild("sSpike4",
                CubeListBuilder.create().texOffs(0, 64).addBox(0.0F, -15.0F, 0.0F, 2.0F, 19.0F, 1.0F),
                PartPose.offsetAndRotation(-4.0F, -10.33333F, 8.0F, -0.3490659F, -1.5707963267948966F, 0.1745329F));
        PartDefinition p_sSpike2 = root.addOrReplaceChild("sSpike2",
                CubeListBuilder.create().texOffs(0, 85).addBox(0.0F, -15.0F, 0.0F, 2.0F, 16.0F, 1.0F),
                PartPose.offsetAndRotation(3.0F, -6.333333F, 10.0F, 0.4014257F, -1.902409F, 0.4537856F));
        PartDefinition p_sSpike5 = root.addOrReplaceChild("sSpike5",
                CubeListBuilder.create().texOffs(7, 58).addBox(0.0F, -15.0F, 0.0F, 2.0F, 16.0F, 1.0F),
                PartPose.offsetAndRotation(-3.0F, -6.333333F, 10.0F, -0.4014257F, -1.239184F, 0.4537856F));
        PartDefinition p_sHead4 = root.addOrReplaceChild("sHead4",
                CubeListBuilder.create().texOffs(36, 47).addBox(-2.5F, -19.0F, -5.2F, 5.0F, 16.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -2.2F, -0.7960354F, 0.0F, 0.0F));
        PartDefinition p_crest0 = root.addOrReplaceChild("crest0",
                CubeListBuilder.create().texOffs(5, 20).addBox(-4.0F, -3.0F, -5.0F, 8.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -2.2F, -0.4029748F, 0.0F, 0.0F));
        PartDefinition p_crest1 = root.addOrReplaceChild("crest1",
                CubeListBuilder.create().texOffs(80, 53).addBox(-5.0F, -6.0F, -5.2F, 10.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -2.2F, -0.5516893F, 0.0F, 0.0F));
        PartDefinition p_crest2 = root.addOrReplaceChild("crest2",
                CubeListBuilder.create().texOffs(80, 60).addBox(-5.0F, -10.0F, -6.0F, 10.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -2.2F, -0.7004037F, 0.0F, 0.0F));
        PartDefinition p_crest3 = root.addOrReplaceChild("crest3",
                CubeListBuilder.create().texOffs(105, 57).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -9.02F, -2.2F, -0.8119396F, 0.0F, 0.0F));
        PartDefinition p_crest4 = root.addOrReplaceChild("crest4",
                CubeListBuilder.create().texOffs(134, 57).addBox(-7.0F, -17.0F, -5.0F, 14.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -9.1F, -2.2F, -0.7375823F, 0.0F, 0.0F));
        PartDefinition p_crest5 = root.addOrReplaceChild("crest5",
                CubeListBuilder.create().texOffs(167, 57).addBox(-6.0F, -19.5F, -9.0F, 12.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, -2.2F, -0.9234754F, 0.0F, 0.0F));
        PartDefinition p_sArmCrusher1 = root.addOrReplaceChild("sArmCrusher1",
                CubeListBuilder.create().texOffs(212, 36).addBox(0.0F, 0.0F, 0.0F, 3.0F, 11.0F, 4.0F),
                PartPose.offsetAndRotation(6.066667F, -11.0F, -1.733333F, 0.1318525F, -0.7304083F, 0.1336492F));
        PartDefinition p_sArmCrusher = root.addOrReplaceChild("sArmCrusher",
                CubeListBuilder.create().texOffs(195, 36).addBox(0.0F, 0.0F, 0.0F, 3.0F, 11.0F, 4.0F),
                PartPose.offsetAndRotation(-8.866667F, -10.5F, 0.3333333F, 0.1213327F, 0.7502525F, -0.1919862F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
