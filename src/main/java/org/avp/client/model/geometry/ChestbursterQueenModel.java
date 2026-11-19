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
 * Geometry converted automatically from the 1.12.2 ModelChestbursterQueen by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class ChestbursterQueenModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "chestburster_queen"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart chest;
    public final ModelPart lThigh;
    public final ModelPart lShin1;
    public final ModelPart lShin2;
    public final ModelPart lFoot;
    public final ModelPart tail1;
    public final ModelPart tailSpines1;
    public final ModelPart tail2;
    public final ModelPart tailSpines2;
    public final ModelPart tail3;
    public final ModelPart tailSpines3;
    public final ModelPart tail4;
    public final ModelPart tail5;
    public final ModelPart tailSpines5;
    public final ModelPart tailStabber;
    public final ModelPart tailSpines4;
    public final ModelPart lArm1;
    public final ModelPart lArm2;
    public final ModelPart lClaw1;
    public final ModelPart lClaw2;
    public final ModelPart backSpines1;
    public final ModelPart backSpines2;
    public final ModelPart abdomen;
    public final ModelPart rThigh;
    public final ModelPart rShin1;
    public final ModelPart rShin2;
    public final ModelPart rFoot;
    public final ModelPart lMiniArm;
    public final ModelPart lMiniArm2;
    public final ModelPart lMiniClaw;
    public final ModelPart rArm1;
    public final ModelPart rArm2;
    public final ModelPart rClaw1;
    public final ModelPart rClaw2;
    public final ModelPart rMiniArm;
    public final ModelPart rMiniArm2;
    public final ModelPart rMiniClaw;
    public final ModelPart headBase;
    public final ModelPart lCrest1;
    public final ModelPart headTop;
    public final ModelPart jaw2;
    public final ModelPart rCrest2;
    public final ModelPart lCrestWeb;
    public final ModelPart lCrest2;
    public final ModelPart jawLower;
    public final ModelPart rCrest1;
    public final ModelPart rHead;
    public final ModelPart rCrestWeb;
    public final ModelPart innerJaw;
    public final ModelPart lHead;
    public final ModelPart neck;

    public ChestbursterQueenModel(ModelPart root)
    {
        this.root = root;
        this.chest = root.getChild("chest");
        this.parts.put("chest", this.chest);
        this.lThigh = this.chest.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lShin1 = this.lThigh.getChild("lShin1");
        this.parts.put("lShin1", this.lShin1);
        this.lShin2 = this.lShin1.getChild("lShin2");
        this.parts.put("lShin2", this.lShin2);
        this.lFoot = this.lShin2.getChild("lFoot");
        this.parts.put("lFoot", this.lFoot);
        this.tail1 = this.chest.getChild("tail1");
        this.parts.put("tail1", this.tail1);
        this.tailSpines1 = this.tail1.getChild("tailSpines1");
        this.parts.put("tailSpines1", this.tailSpines1);
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
        this.tail5 = this.tail4.getChild("tail5");
        this.parts.put("tail5", this.tail5);
        this.tailSpines5 = this.tail5.getChild("tailSpines5");
        this.parts.put("tailSpines5", this.tailSpines5);
        this.tailStabber = this.tail5.getChild("tailStabber");
        this.parts.put("tailStabber", this.tailStabber);
        this.tailSpines4 = this.tail4.getChild("tailSpines4");
        this.parts.put("tailSpines4", this.tailSpines4);
        this.lArm1 = this.chest.getChild("lArm1");
        this.parts.put("lArm1", this.lArm1);
        this.lArm2 = this.lArm1.getChild("lArm2");
        this.parts.put("lArm2", this.lArm2);
        this.lClaw1 = this.lArm2.getChild("lClaw1");
        this.parts.put("lClaw1", this.lClaw1);
        this.lClaw2 = this.lClaw1.getChild("lClaw2");
        this.parts.put("lClaw2", this.lClaw2);
        this.backSpines1 = this.chest.getChild("backSpines1");
        this.parts.put("backSpines1", this.backSpines1);
        this.backSpines2 = this.chest.getChild("backSpines2");
        this.parts.put("backSpines2", this.backSpines2);
        this.abdomen = this.chest.getChild("abdomen");
        this.parts.put("abdomen", this.abdomen);
        this.rThigh = this.chest.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.rShin1 = this.rThigh.getChild("rShin1");
        this.parts.put("rShin1", this.rShin1);
        this.rShin2 = this.rShin1.getChild("rShin2");
        this.parts.put("rShin2", this.rShin2);
        this.rFoot = this.rShin2.getChild("rFoot");
        this.parts.put("rFoot", this.rFoot);
        this.lMiniArm = this.chest.getChild("lMiniArm");
        this.parts.put("lMiniArm", this.lMiniArm);
        this.lMiniArm2 = this.lMiniArm.getChild("lMiniArm2");
        this.parts.put("lMiniArm2", this.lMiniArm2);
        this.lMiniClaw = this.lMiniArm.getChild("lMiniClaw");
        this.parts.put("lMiniClaw", this.lMiniClaw);
        this.rArm1 = this.chest.getChild("rArm1");
        this.parts.put("rArm1", this.rArm1);
        this.rArm2 = this.rArm1.getChild("rArm2");
        this.parts.put("rArm2", this.rArm2);
        this.rClaw1 = this.rArm2.getChild("rClaw1");
        this.parts.put("rClaw1", this.rClaw1);
        this.rClaw2 = this.rClaw1.getChild("rClaw2");
        this.parts.put("rClaw2", this.rClaw2);
        this.rMiniArm = this.chest.getChild("rMiniArm");
        this.parts.put("rMiniArm", this.rMiniArm);
        this.rMiniArm2 = this.rMiniArm.getChild("rMiniArm2");
        this.parts.put("rMiniArm2", this.rMiniArm2);
        this.rMiniClaw = this.rMiniArm.getChild("rMiniClaw");
        this.parts.put("rMiniClaw", this.rMiniClaw);
        this.headBase = this.chest.getChild("headBase");
        this.parts.put("headBase", this.headBase);
        this.lCrest1 = this.headBase.getChild("lCrest1");
        this.parts.put("lCrest1", this.lCrest1);
        this.headTop = this.headBase.getChild("headTop");
        this.parts.put("headTop", this.headTop);
        this.jaw2 = this.headBase.getChild("jaw2");
        this.parts.put("jaw2", this.jaw2);
        this.rCrest2 = this.headBase.getChild("rCrest2");
        this.parts.put("rCrest2", this.rCrest2);
        this.lCrestWeb = this.headBase.getChild("lCrestWeb");
        this.parts.put("lCrestWeb", this.lCrestWeb);
        this.lCrest2 = this.headBase.getChild("lCrest2");
        this.parts.put("lCrest2", this.lCrest2);
        this.jawLower = this.headBase.getChild("jawLower");
        this.parts.put("jawLower", this.jawLower);
        this.rCrest1 = this.headBase.getChild("rCrest1");
        this.parts.put("rCrest1", this.rCrest1);
        this.rHead = this.headBase.getChild("rHead");
        this.parts.put("rHead", this.rHead);
        this.rCrestWeb = this.headBase.getChild("rCrestWeb");
        this.parts.put("rCrestWeb", this.rCrestWeb);
        this.innerJaw = this.headBase.getChild("innerJaw");
        this.parts.put("innerJaw", this.innerJaw);
        this.lHead = this.headBase.getChild("lHead");
        this.parts.put("lHead", this.lHead);
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
                CubeListBuilder.create().texOffs(0, 46).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 8.0F, 10.0F),
                PartPose.offset(0.0F, -1.5F, 0.0F));
        PartDefinition p_lThigh = p_chest.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(41, 47).addBox(-2.5F, -2.0F, -1.0F, 2.0F, 14.0F, 3.0F),
                PartPose.offsetAndRotation(4.5F, 3.5F, 17.8F, -0.4923573819876004F, -0.2228785454796759F, -0.43022366061660217F));
        PartDefinition p_lShin1 = p_lThigh.addOrReplaceChild("lShin1",
                CubeListBuilder.create().texOffs(79, 49).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 2.0F, 12.0F),
                PartPose.offset(-1.5F, 10.0F, 0.0F));
        PartDefinition p_lShin2 = p_lShin1.addOrReplaceChild("lShin2",
                CubeListBuilder.create().texOffs(113, 40).addBox(0.0F, -0.2F, -1.1F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(-1.0F, 1.5F, 10.4F, 0.0F, 0.0F, 0.36425021489121656F));
        PartDefinition p_lFoot = p_lShin2.addOrReplaceChild("lFoot",
                CubeListBuilder.create().texOffs(110, 24).addBox(-1.0F, -1.0F, -4.4F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(1.0F, 8.2F, 0.0F, 0.5462880558742251F, 0.0F, 0.136659280431156F));
        PartDefinition p_tail1 = p_chest.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(50, 66).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 3.5F, 18.5F, -0.40142572795869574F, 0.0F, 0.0F));
        PartDefinition p_tailSpines1 = p_tail1.addOrReplaceChild("tailSpines1",
                CubeListBuilder.create().texOffs(58, 93).addBox(0.0F, -4.7F, 0.0F, 0.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail2 = p_tail1.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(85, 66).addBox(-2.0F, -2.5F, -0.1F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_tailSpines2 = p_tail2.addOrReplaceChild("tailSpines2",
                CubeListBuilder.create().texOffs(90, 93).addBox(0.0F, -4.5F, 0.2F, 0.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail3 = p_tail2.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(118, 66).addBox(-1.5F, -1.8F, 0.0F, 3.0F, 3.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 10.4F, 0.10175269539126941F, 0.0F, 0.0F));
        PartDefinition p_tailSpines3 = p_tail3.addOrReplaceChild("tailSpines3",
                CubeListBuilder.create().texOffs(118, 94).addBox(0.0F, -2.9F, 1.3F, 0.0F, 6.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail4 = p_tail3.addOrReplaceChild("tail4",
                CubeListBuilder.create().texOffs(149, 66).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_tail5 = p_tail4.addOrReplaceChild("tail5",
                CubeListBuilder.create().texOffs(178, 66).addBox(-0.5F, -0.2F, 0.0F, 1.0F, 1.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_tailSpines5 = p_tail5.addOrReplaceChild("tailSpines5",
                CubeListBuilder.create().texOffs(177, 96).addBox(0.0F, -0.9F, 0.4F, 0.0F, 3.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailStabber = p_tail5.addOrReplaceChild("tailStabber",
                CubeListBuilder.create().texOffs(205, 66).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 11.0F),
                PartPose.offset(0.0F, 0.3F, 10.2F));
        PartDefinition p_tailSpines4 = p_tail4.addOrReplaceChild("tailSpines4",
                CubeListBuilder.create().texOffs(149, 96).addBox(0.0F, -2.1F, 1.1F, 0.0F, 4.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lArm1 = p_chest.addOrReplaceChild("lArm1",
                CubeListBuilder.create().texOffs(40, 29).addBox(0.0F, 0.0F, -0.5F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(3.5F, 3.5F, 3.5F, 0.12217304763960307F, 0.0F, -0.24434609527920614F));
        PartDefinition p_lArm2 = p_lArm1.addOrReplaceChild("lArm2",
                CubeListBuilder.create().texOffs(71, 9).addBox(-0.9F, -1.1F, -10.6F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(0.9F, 12.9F, 0.6F, 0.4553564018453205F, 0.0F, -0.045553093477052F));
        PartDefinition p_lClaw1 = p_lArm2.addOrReplaceChild("lClaw1",
                CubeListBuilder.create().texOffs(83, 24).addBox(-0.9F, 0.0F, -3.1F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -0.1F, -10.3F, 0.0F, 0.18203784098300857F, 0.22759093446006054F));
        PartDefinition p_lClaw2 = p_lClaw1.addOrReplaceChild("lClaw2",
                CubeListBuilder.create().texOffs(60, 30).addBox(-1.9F, 1.0F, -5.8F, 3.0F, 0.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_backSpines1 = p_chest.addOrReplaceChild("backSpines1",
                CubeListBuilder.create().texOffs(0, 67).addBox(0.0F, -5.8F, 0.0F, 0.0F, 6.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_backSpines2 = p_chest.addOrReplaceChild("backSpines2",
                CubeListBuilder.create().texOffs(23, 65).addBox(0.0F, -5.5F, 8.5F, 0.0F, 6.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_abdomen = p_chest.addOrReplaceChild("abdomen",
                CubeListBuilder.create().texOffs(0, 27).addBox(-3.5F, 0.5F, 8.0F, 7.0F, 6.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rThigh = p_chest.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(61, 47).addBox(-1.5F, -2.0F, -1.0F, 2.0F, 14.0F, 3.0F),
                PartPose.offsetAndRotation(-3.0F, 4.5F, 17.3F, -0.4923573819876004F, 0.2228785454796759F, 0.43022366061660217F));
        PartDefinition p_rShin1 = p_rThigh.addOrReplaceChild("rShin1",
                CubeListBuilder.create().texOffs(79, 33).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 2.0F, 12.0F),
                PartPose.offset(-0.5F, 10.0F, 0.0F));
        PartDefinition p_rShin2 = p_rShin1.addOrReplaceChild("rShin2",
                CubeListBuilder.create().texOffs(113, 53).addBox(-1.0F, -0.2F, -1.1F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, 10.4F, 0.0F, 0.0F, -0.36425021489121656F));
        PartDefinition p_rFoot = p_rShin2.addOrReplaceChild("rFoot",
                CubeListBuilder.create().texOffs(95, 24).addBox(-1.0F, -1.0F, -4.4F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 8.2F, 0.0F, 0.5462880558742251F, 0.0F, -0.136659280431156F));
        PartDefinition p_lMiniArm = p_chest.addOrReplaceChild("lMiniArm",
                CubeListBuilder.create().texOffs(134, 29).addBox(0.0F, 0.0F, 0.0F, 1.0F, 5.0F, 1.0F),
                PartPose.offsetAndRotation(1.7F, 7.6F, 4.2F, 0.17453292519943295F, 0.0F, -0.08726646259971647F));
        PartDefinition p_lMiniArm2 = p_lMiniArm.addOrReplaceChild("lMiniArm2",
                CubeListBuilder.create().texOffs(140, 37).addBox(0.0F, 3.3F, 3.0F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6829473363053812F, 0.0F, 0.0F));
        PartDefinition p_lMiniClaw = p_lMiniArm.addOrReplaceChild("lMiniClaw",
                CubeListBuilder.create().texOffs(134, 49).addBox(0.0F, 7.5F, -2.2F, 1.0F, 2.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rArm1 = p_chest.addOrReplaceChild("rArm1",
                CubeListBuilder.create().texOffs(50, 29).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(-3.5F, 3.5F, 3.5F, 0.12217304763960307F, 0.0F, 0.24434609527920614F));
        PartDefinition p_rArm2 = p_rArm1.addOrReplaceChild("rArm2",
                CubeListBuilder.create().texOffs(98, 9).addBox(-1.2F, -1.1F, -10.6F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(-0.9F, 12.9F, 0.6F, 0.4553564018453205F, 0.0F, -0.045553093477052F));
        PartDefinition p_rClaw1 = p_rArm2.addOrReplaceChild("rClaw1",
                CubeListBuilder.create().texOffs(72, 24).addBox(-1.1F, 0.0F, -3.1F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -0.1F, -10.3F, 0.0F, -0.091106186954104F, -0.22759093446006054F));
        PartDefinition p_rClaw2 = p_rClaw1.addOrReplaceChild("rClaw2",
                CubeListBuilder.create().texOffs(60, 38).addBox(-1.1F, 1.0F, -5.8F, 3.0F, 0.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rMiniArm = p_chest.addOrReplaceChild("rMiniArm",
                CubeListBuilder.create().texOffs(140, 29).addBox(0.0F, 0.0F, 0.0F, 1.0F, 5.0F, 1.0F),
                PartPose.offsetAndRotation(-2.5F, 7.6F, 4.2F, 0.17453292519943295F, 0.0F, 0.08726646259971647F));
        PartDefinition p_rMiniArm2 = p_rMiniArm.addOrReplaceChild("rMiniArm2",
                CubeListBuilder.create().texOffs(140, 37).addBox(0.0F, 3.3F, 3.0F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6829473363053812F, 0.0F, 0.0F));
        PartDefinition p_rMiniClaw = p_rMiniArm.addOrReplaceChild("rMiniClaw",
                CubeListBuilder.create().texOffs(140, 49).addBox(0.0F, 7.5F, -2.2F, 1.0F, 2.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_headBase = p_chest.addOrReplaceChild("headBase",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.2F, -3.9F, 5.0F, 10.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.4F, -1.4F, -0.6017895260876448F, 0.0F, 0.0F));
        PartDefinition p_lCrest1 = p_headBase.addOrReplaceChild("lCrest1",
                CubeListBuilder.create().texOffs(151, 55).addBox(-3.4F, -10.9F, -3.6F, 6.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.40980330836826856F, 0.0F, 0.4886921905584123F));
        PartDefinition p_headTop = p_headBase.addOrReplaceChild("headTop",
                CubeListBuilder.create().texOffs(28, 10).addBox(-2.5F, -11.8F, -4.7F, 5.0F, 10.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_jaw2 = p_headBase.addOrReplaceChild("jaw2",
                CubeListBuilder.create().texOffs(52, 20).addBox(-1.5F, 5.0F, -8.4F, 3.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7740535232594852F, 0.0F, 0.0F));
        PartDefinition p_rCrest2 = p_headBase.addOrReplaceChild("rCrest2",
                CubeListBuilder.create().texOffs(151, 35).addBox(-3.5F, -2.6F, -2.4F, 2.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.091106186954104F, 0.0F, -0.4886921905584123F));
        PartDefinition p_lCrestWeb = p_headBase.addOrReplaceChild("lCrestWeb",
                CubeListBuilder.create().texOffs(169, 46).addBox(-1.5F, -1.8F, 0.5F, 4.0F, 0.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2747884856566583F, 0.0F, 0.4363323129985824F));
        PartDefinition p_lCrest2 = p_headBase.addOrReplaceChild("lCrest2",
                CubeListBuilder.create().texOffs(165, 35).addBox(1.3F, -2.6F, -2.4F, 2.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.091106186954104F, 0.0F, 0.4886921905584123F));
        PartDefinition p_jawLower = p_headBase.addOrReplaceChild("jawLower",
                CubeListBuilder.create().texOffs(88, 1).addBox(-1.5F, -0.5F, -5.2F, 3.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 6.7F, 2.3F, 0.7740535232594852F, 0.0F, 0.0F));
        PartDefinition p_rCrest1 = p_headBase.addOrReplaceChild("rCrest1",
                CubeListBuilder.create().texOffs(134, 55).addBox(-2.6F, -10.9F, -3.6F, 6.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.40980330836826856F, 0.0F, -0.4886921905584123F));
        PartDefinition p_rHead = p_headBase.addOrReplaceChild("rHead",
                CubeListBuilder.create().texOffs(34, 0).addBox(-2.51F, 4.1F, -6.7F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_rCrestWeb = p_headBase.addOrReplaceChild("rCrestWeb",
                CubeListBuilder.create().texOffs(169, 54).addBox(-2.5F, -1.8F, 0.5F, 4.0F, 0.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2747884856566583F, 0.0F, -0.4363323129985824F));
        PartDefinition p_innerJaw = p_headBase.addOrReplaceChild("innerJaw",
                CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.7F, -4.6F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 6.4F, 1.2F, 0.7740535232594852F, 0.0F, 0.0F));
        PartDefinition p_lHead = p_headBase.addOrReplaceChild("lHead",
                CubeListBuilder.create().texOffs(53, 0).addBox(-0.5F, 4.1F, -6.7F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_neck = p_chest.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(23, 86).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 6.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.19198621771937624F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
