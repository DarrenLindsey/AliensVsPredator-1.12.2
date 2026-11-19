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
 * Geometry converted automatically from the 1.12.2 ModelDracomorph by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class DracomorphModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "dracomorph"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart sack0;
    public final ModelPart sack1;
    public final ModelPart sack2;
    public final ModelPart sack3;
    public final ModelPart sack4;
    public final ModelPart sack5;
    public final ModelPart sack6;
    public final ModelPart sack7;
    public final ModelPart rWingArm1;
    public final ModelPart rWingMembrane1;
    public final ModelPart rWingArm2;
    public final ModelPart rWingArm3;
    public final ModelPart rWingArm4;
    public final ModelPart lWingMembrane4_1;
    public final ModelPart rWingMembrane3;
    public final ModelPart rWingMembrane2;
    public final ModelPart chest;
    public final ModelPart lArm1;
    public final ModelPart lArm2;
    public final ModelPart lClaw1;
    public final ModelPart lClaw2;
    public final ModelPart rShoulderBlade;
    public final ModelPart abdomen;
    public final ModelPart lThigh;
    public final ModelPart lShin1;
    public final ModelPart lShin2;
    public final ModelPart lFoot;
    public final ModelPart backSpines2;
    public final ModelPart backSpines1;
    public final ModelPart rThigh;
    public final ModelPart rShin1;
    public final ModelPart rShin2;
    public final ModelPart rFoot;
    public final ModelPart rArm1;
    public final ModelPart rArm2;
    public final ModelPart rClaw1;
    public final ModelPart rClaw2;
    public final ModelPart neck;
    public final ModelPart neck1;
    public final ModelPart headBase;
    public final ModelPart rHeadFin;
    public final ModelPart jawLower;
    public final ModelPart innerJaw;
    public final ModelPart jaw2;
    public final ModelPart lHeadFin;
    public final ModelPart lHead;
    public final ModelPart headTop;
    public final ModelPart rHorn1;
    public final ModelPart rHorn2;
    public final ModelPart rHorn3;
    public final ModelPart rHorn4;
    public final ModelPart lHorn1;
    public final ModelPart lHorn2;
    public final ModelPart lHorn3;
    public final ModelPart lHorn4;
    public final ModelPart rHead;
    public final ModelPart lShoulderBlade;
    public final ModelPart tail1;
    public final ModelPart tailSpines1;
    public final ModelPart tail2;
    public final ModelPart tail3;
    public final ModelPart tailSpines3;
    public final ModelPart tail4;
    public final ModelPart tailSpines4;
    public final ModelPart tail5;
    public final ModelPart tailSpines5;
    public final ModelPart tailStabber;
    public final ModelPart tailSpines2;
    public final ModelPart lWingArm1;
    public final ModelPart lWingMembrane1;
    public final ModelPart lWingArm2;
    public final ModelPart lWingArm3;
    public final ModelPart lWingArm4;
    public final ModelPart lWingMembrane4;
    public final ModelPart lWingMembrane3;
    public final ModelPart lWingMembrane2;

    public DracomorphModel(ModelPart root)
    {
        this.root = root;
        this.sack0 = root.getChild("sack0");
        this.parts.put("sack0", this.sack0);
        this.sack1 = this.sack0.getChild("sack1");
        this.parts.put("sack1", this.sack1);
        this.sack2 = this.sack1.getChild("sack2");
        this.parts.put("sack2", this.sack2);
        this.sack3 = this.sack2.getChild("sack3");
        this.parts.put("sack3", this.sack3);
        this.sack4 = this.sack3.getChild("sack4");
        this.parts.put("sack4", this.sack4);
        this.sack5 = this.sack4.getChild("sack5");
        this.parts.put("sack5", this.sack5);
        this.sack6 = this.sack5.getChild("sack6");
        this.parts.put("sack6", this.sack6);
        this.sack7 = this.sack6.getChild("sack7");
        this.parts.put("sack7", this.sack7);
        this.rWingArm1 = root.getChild("rWingArm1");
        this.parts.put("rWingArm1", this.rWingArm1);
        this.rWingMembrane1 = this.rWingArm1.getChild("rWingMembrane1");
        this.parts.put("rWingMembrane1", this.rWingMembrane1);
        this.rWingArm2 = this.rWingArm1.getChild("rWingArm2");
        this.parts.put("rWingArm2", this.rWingArm2);
        this.rWingArm3 = this.rWingArm2.getChild("rWingArm3");
        this.parts.put("rWingArm3", this.rWingArm3);
        this.rWingArm4 = this.rWingArm3.getChild("rWingArm4");
        this.parts.put("rWingArm4", this.rWingArm4);
        this.lWingMembrane4_1 = this.rWingArm4.getChild("lWingMembrane4_1");
        this.parts.put("lWingMembrane4_1", this.lWingMembrane4_1);
        this.rWingMembrane3 = this.rWingArm3.getChild("rWingMembrane3");
        this.parts.put("rWingMembrane3", this.rWingMembrane3);
        this.rWingMembrane2 = this.rWingArm2.getChild("rWingMembrane2");
        this.parts.put("rWingMembrane2", this.rWingMembrane2);
        this.chest = root.getChild("chest");
        this.parts.put("chest", this.chest);
        this.lArm1 = this.chest.getChild("lArm1");
        this.parts.put("lArm1", this.lArm1);
        this.lArm2 = this.lArm1.getChild("lArm2");
        this.parts.put("lArm2", this.lArm2);
        this.lClaw1 = this.lArm2.getChild("lClaw1");
        this.parts.put("lClaw1", this.lClaw1);
        this.lClaw2 = this.lClaw1.getChild("lClaw2");
        this.parts.put("lClaw2", this.lClaw2);
        this.rShoulderBlade = this.chest.getChild("rShoulderBlade");
        this.parts.put("rShoulderBlade", this.rShoulderBlade);
        this.abdomen = this.chest.getChild("abdomen");
        this.parts.put("abdomen", this.abdomen);
        this.lThigh = this.chest.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lShin1 = this.lThigh.getChild("lShin1");
        this.parts.put("lShin1", this.lShin1);
        this.lShin2 = this.lShin1.getChild("lShin2");
        this.parts.put("lShin2", this.lShin2);
        this.lFoot = this.lShin2.getChild("lFoot");
        this.parts.put("lFoot", this.lFoot);
        this.backSpines2 = this.chest.getChild("backSpines2");
        this.parts.put("backSpines2", this.backSpines2);
        this.backSpines1 = this.chest.getChild("backSpines1");
        this.parts.put("backSpines1", this.backSpines1);
        this.rThigh = this.chest.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.rShin1 = this.rThigh.getChild("rShin1");
        this.parts.put("rShin1", this.rShin1);
        this.rShin2 = this.rShin1.getChild("rShin2");
        this.parts.put("rShin2", this.rShin2);
        this.rFoot = this.rShin2.getChild("rFoot");
        this.parts.put("rFoot", this.rFoot);
        this.rArm1 = this.chest.getChild("rArm1");
        this.parts.put("rArm1", this.rArm1);
        this.rArm2 = this.rArm1.getChild("rArm2");
        this.parts.put("rArm2", this.rArm2);
        this.rClaw1 = this.rArm2.getChild("rClaw1");
        this.parts.put("rClaw1", this.rClaw1);
        this.rClaw2 = this.rClaw1.getChild("rClaw2");
        this.parts.put("rClaw2", this.rClaw2);
        this.neck = this.chest.getChild("neck");
        this.parts.put("neck", this.neck);
        this.neck1 = this.neck.getChild("neck1");
        this.parts.put("neck1", this.neck1);
        this.headBase = this.neck1.getChild("headBase");
        this.parts.put("headBase", this.headBase);
        this.rHeadFin = this.headBase.getChild("rHeadFin");
        this.parts.put("rHeadFin", this.rHeadFin);
        this.jawLower = this.headBase.getChild("jawLower");
        this.parts.put("jawLower", this.jawLower);
        this.innerJaw = this.headBase.getChild("innerJaw");
        this.parts.put("innerJaw", this.innerJaw);
        this.jaw2 = this.headBase.getChild("jaw2");
        this.parts.put("jaw2", this.jaw2);
        this.lHeadFin = this.headBase.getChild("lHeadFin");
        this.parts.put("lHeadFin", this.lHeadFin);
        this.lHead = this.headBase.getChild("lHead");
        this.parts.put("lHead", this.lHead);
        this.headTop = this.headBase.getChild("headTop");
        this.parts.put("headTop", this.headTop);
        this.rHorn1 = this.headBase.getChild("rHorn1");
        this.parts.put("rHorn1", this.rHorn1);
        this.rHorn2 = this.rHorn1.getChild("rHorn2");
        this.parts.put("rHorn2", this.rHorn2);
        this.rHorn3 = this.rHorn2.getChild("rHorn3");
        this.parts.put("rHorn3", this.rHorn3);
        this.rHorn4 = this.rHorn3.getChild("rHorn4");
        this.parts.put("rHorn4", this.rHorn4);
        this.lHorn1 = this.headBase.getChild("lHorn1");
        this.parts.put("lHorn1", this.lHorn1);
        this.lHorn2 = this.lHorn1.getChild("lHorn2");
        this.parts.put("lHorn2", this.lHorn2);
        this.lHorn3 = this.lHorn2.getChild("lHorn3");
        this.parts.put("lHorn3", this.lHorn3);
        this.lHorn4 = this.lHorn3.getChild("lHorn4");
        this.parts.put("lHorn4", this.lHorn4);
        this.rHead = this.headBase.getChild("rHead");
        this.parts.put("rHead", this.rHead);
        this.lShoulderBlade = this.chest.getChild("lShoulderBlade");
        this.parts.put("lShoulderBlade", this.lShoulderBlade);
        this.tail1 = this.chest.getChild("tail1");
        this.parts.put("tail1", this.tail1);
        this.tailSpines1 = this.tail1.getChild("tailSpines1");
        this.parts.put("tailSpines1", this.tailSpines1);
        this.tail2 = this.tail1.getChild("tail2");
        this.parts.put("tail2", this.tail2);
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
        this.tailStabber = this.tail5.getChild("tailStabber");
        this.parts.put("tailStabber", this.tailStabber);
        this.tailSpines2 = this.tail2.getChild("tailSpines2");
        this.parts.put("tailSpines2", this.tailSpines2);
        this.lWingArm1 = root.getChild("lWingArm1");
        this.parts.put("lWingArm1", this.lWingArm1);
        this.lWingMembrane1 = this.lWingArm1.getChild("lWingMembrane1");
        this.parts.put("lWingMembrane1", this.lWingMembrane1);
        this.lWingArm2 = this.lWingArm1.getChild("lWingArm2");
        this.parts.put("lWingArm2", this.lWingArm2);
        this.lWingArm3 = this.lWingArm2.getChild("lWingArm3");
        this.parts.put("lWingArm3", this.lWingArm3);
        this.lWingArm4 = this.lWingArm3.getChild("lWingArm4");
        this.parts.put("lWingArm4", this.lWingArm4);
        this.lWingMembrane4 = this.lWingArm4.getChild("lWingMembrane4");
        this.parts.put("lWingMembrane4", this.lWingMembrane4);
        this.lWingMembrane3 = this.lWingArm3.getChild("lWingMembrane3");
        this.parts.put("lWingMembrane3", this.lWingMembrane3);
        this.lWingMembrane2 = this.lWingArm2.getChild("lWingMembrane2");
        this.parts.put("lWingMembrane2", this.lWingMembrane2);
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
        PartDefinition p_sack0 = root.addOrReplaceChild("sack0",
                CubeListBuilder.create().texOffs(0, 87).addBox(-2.0F, -3.8F, 0.0F, 4.0F, 4.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 4.9F, 17.4F, -0.9560913642424937F, 0.0F, 0.0F));
        PartDefinition p_sack1 = p_sack0.addOrReplaceChild("sack1",
                CubeListBuilder.create().texOffs(0, 98).addBox(-3.5F, -1.2F, -1.3F, 7.0F, 6.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -3.4F, 4.2F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_sack2 = p_sack1.addOrReplaceChild("sack2",
                CubeListBuilder.create().texOffs(0, 111).addBox(-5.0F, -2.4F, -1.3F, 10.0F, 9.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.2F, 4.5F, 0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_sack3 = p_sack2.addOrReplaceChild("sack3",
                CubeListBuilder.create().texOffs(84, 82).addBox(-5.5F, -2.4F, -3.1F, 11.0F, 10.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.2F, 7.0F, -0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_sack4 = p_sack3.addOrReplaceChild("sack4",
                CubeListBuilder.create().texOffs(132, 82).addBox(-4.5F, -2.4F, -1.8F, 9.0F, 10.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.4F, 8.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_sack5 = p_sack4.addOrReplaceChild("sack5",
                CubeListBuilder.create().texOffs(177, 83).addBox(-3.5F, -2.4F, -1.2F, 7.0F, 8.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 1.6F, 9.3F, -0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_sack6 = p_sack5.addOrReplaceChild("sack6",
                CubeListBuilder.create().texOffs(217, 90).addBox(-2.3F, -1.7F, 0.0F, 5.0F, 6.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 6.9F, -0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_sack7 = p_sack6.addOrReplaceChild("sack7",
                CubeListBuilder.create().texOffs(215, 108).addBox(-1.5F, -0.9F, -0.7F, 3.0F, 3.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 1.2F, 8.0F, -0.5918411493512771F, 0.0F, 0.0F));
        PartDefinition p_rWingArm1 = root.addOrReplaceChild("rWingArm1",
                CubeListBuilder.create().texOffs(134, 0).mirror().addBox(-1.0F, -9.0F, -1.5F, 2.0F, 9.0F, 3.0F),
                PartPose.offsetAndRotation(-2.3F, -1.3F, 6.3F, 0.26930430358272506F, 0.0F, -0.1795943800302165F));
        PartDefinition p_rWingMembrane1 = p_rWingArm1.addOrReplaceChild("rWingMembrane1",
                CubeListBuilder.create().texOffs(142, 4).addBox(0.0F, -7.9F, -2.1F, 0.0F, 11.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.44872415068774213F, 0.0F, 0.0F));
        PartDefinition p_rWingArm2 = p_rWingArm1.addOrReplaceChild("rWingArm2",
                CubeListBuilder.create().texOffs(149, 0).mirror().addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.13456488532876282F, -0.18203784098300857F, -0.045553093477052F));
        PartDefinition p_rWingArm3 = p_rWingArm2.addOrReplaceChild("rWingArm3",
                CubeListBuilder.create().texOffs(174, 0).mirror().addBox(-0.5F, -1.1F, 0.0F, 1.0F, 2.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 9.4F, -0.4038691889114879F, 0.0F, 0.0F));
        PartDefinition p_rWingArm4 = p_rWingArm3.addOrReplaceChild("rWingArm4",
                CubeListBuilder.create().texOffs(200, 0).mirror().addBox(-0.5F, -0.7F, 0.0F, 1.0F, 1.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 8.3F, -0.3141592653589793F, 0.0F, 0.0F));
        PartDefinition p_lWingMembrane4_1 = p_rWingArm4.addOrReplaceChild("lWingMembrane4_1",
                CubeListBuilder.create().texOffs(208, 3).addBox(0.0F, 0.0F, 1.4F, 0.0F, 17.0F, 14.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rWingMembrane3 = p_rWingArm3.addOrReplaceChild("rWingMembrane3",
                CubeListBuilder.create().texOffs(188, 8).addBox(0.0F, 0.0F, 0.7F, 0.0F, 15.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rWingMembrane2 = p_rWingArm2.addOrReplaceChild("rWingMembrane2",
                CubeListBuilder.create().texOffs(166, 5).addBox(0.0F, 0.0F, 0.0F, 0.0F, 13.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_chest = root.addOrReplaceChild("chest",
                CubeListBuilder.create().texOffs(1, 55).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, -1.5F, 0.0F));
        PartDefinition p_lArm1 = p_chest.addOrReplaceChild("lArm1",
                CubeListBuilder.create().texOffs(40, 27).addBox(0.0F, 0.0F, -0.5F, 3.0F, 14.0F, 3.0F),
                PartPose.offsetAndRotation(3.5F, 3.5F, 3.5F, 0.12217304763960307F, 0.0F, -0.24434609527920614F));
        PartDefinition p_lArm2 = p_lArm1.addOrReplaceChild("lArm2",
                CubeListBuilder.create().texOffs(71, 6).addBox(-1.7F, -1.4F, -10.1F, 3.0F, 3.0F, 11.0F),
                PartPose.offsetAndRotation(1.7F, 13.8F, 0.7F, 0.9560913642424937F, 0.0F, 0.045553093477052F));
        PartDefinition p_lClaw1 = p_lArm2.addOrReplaceChild("lClaw1",
                CubeListBuilder.create().texOffs(83, 24).addBox(-1.0F, -0.2F, -2.6F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-0.2F, 0.1F, -10.0F, -1.0471975511965976F, 0.045553093477052F, 0.22759093446006054F));
        PartDefinition p_lClaw2 = p_lClaw1.addOrReplaceChild("lClaw2",
                CubeListBuilder.create().texOffs(60, 30).addBox(-2.0F, 0.5F, -5.6F, 3.0F, 0.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rShoulderBlade = p_chest.addOrReplaceChild("rShoulderBlade",
                CubeListBuilder.create().texOffs(171, 34).addBox(-6.0F, -1.0F, -7.2F, 2.0F, 3.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.4114477660878142F, 0.18203784098300857F, -0.136659280431156F));
        PartDefinition p_abdomen = p_chest.addOrReplaceChild("abdomen",
                CubeListBuilder.create().texOffs(0, 27).addBox(-3.5F, 0.5F, 8.0F, 7.0F, 6.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lThigh = p_chest.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(40, 45).addBox(-2.5F, -2.0F, -1.0F, 4.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(4.5F, 3.5F, 17.0F, -0.4923573819876004F, -0.2228785454796759F, -0.43022366061660217F));
        PartDefinition p_lShin1 = p_lThigh.addOrReplaceChild("lShin1",
                CubeListBuilder.create().texOffs(79, 49).addBox(-2.0F, 0.0F, -0.5F, 3.0F, 3.0F, 12.0F),
                PartPose.offset(0.0F, 10.0F, 0.0F));
        PartDefinition p_lShin2 = p_lShin1.addOrReplaceChild("lShin2",
                CubeListBuilder.create().texOffs(113, 40).addBox(0.0F, -0.2F, -1.1F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(-1.3F, 1.5F, 10.4F, 0.0F, 0.0F, 0.36425021489121656F));
        PartDefinition p_lFoot = p_lShin2.addOrReplaceChild("lFoot",
                CubeListBuilder.create().texOffs(110, 24).addBox(-1.0F, -1.0F, -4.5F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(1.0F, 8.2F, 0.0F, 0.5462880558742251F, 0.0F, 0.136659280431156F));
        PartDefinition p_backSpines2 = p_chest.addOrReplaceChild("backSpines2",
                CubeListBuilder.create().texOffs(22, 65).addBox(0.0F, -5.5F, 8.5F, 0.0F, 6.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_backSpines1 = p_chest.addOrReplaceChild("backSpines1",
                CubeListBuilder.create().texOffs(0, 67).addBox(0.0F, -5.8F, 0.0F, 0.0F, 6.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rThigh = p_chest.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(59, 45).addBox(-1.5F, -2.0F, -1.0F, 4.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(-4.5F, 3.5F, 16.0F, -0.4923573819876004F, 0.2228785454796759F, 0.43022366061660217F));
        PartDefinition p_rShin1 = p_rThigh.addOrReplaceChild("rShin1",
                CubeListBuilder.create().texOffs(79, 33).addBox(-1.0F, 0.0F, -0.5F, 3.0F, 3.0F, 12.0F),
                PartPose.offset(0.0F, 10.0F, 0.0F));
        PartDefinition p_rShin2 = p_rShin1.addOrReplaceChild("rShin2",
                CubeListBuilder.create().texOffs(113, 53).addBox(-1.0F, -0.2F, -1.1F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.3F, 1.5F, 10.4F, 0.0F, 0.0F, -0.36425021489121656F));
        PartDefinition p_rFoot = p_rShin2.addOrReplaceChild("rFoot",
                CubeListBuilder.create().texOffs(95, 24).addBox(-1.0F, -1.0F, -4.4F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 8.2F, 0.0F, 0.5462880558742251F, 0.0F, -0.136659280431156F));
        PartDefinition p_rArm1 = p_chest.addOrReplaceChild("rArm1",
                CubeListBuilder.create().texOffs(50, 85).addBox(-2.0F, 0.0F, -0.5F, 3.0F, 14.0F, 3.0F),
                PartPose.offsetAndRotation(-3.5F, 3.5F, 3.5F, 0.12217304763960307F, 0.0F, 0.24434609527920614F));
        PartDefinition p_rArm2 = p_rArm1.addOrReplaceChild("rArm2",
                CubeListBuilder.create().texOffs(103, 6).addBox(-1.7F, -1.4F, -10.1F, 3.0F, 3.0F, 11.0F),
                PartPose.offsetAndRotation(-0.2F, 13.8F, 0.7F, 0.9560913642424937F, 0.0F, -0.045553093477052F));
        PartDefinition p_rClaw1 = p_rArm2.addOrReplaceChild("rClaw1",
                CubeListBuilder.create().texOffs(72, 24).addBox(-1.0F, -0.2F, -2.6F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-0.2F, 0.1F, -10.0F, -1.0471975511965976F, -0.045553093477052F, -0.22759093446006054F));
        PartDefinition p_rClaw2 = p_rClaw1.addOrReplaceChild("rClaw2",
                CubeListBuilder.create().texOffs(60, 38).addBox(-1.0F, 0.5F, -5.6F, 3.0F, 0.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_neck = p_chest.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(23, 86).addBox(-3.0F, -3.3F, -3.7F, 6.0F, 7.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 3.2F, -0.8F, -0.19198621771937624F, 0.0F, 0.0F));
        PartDefinition p_neck1 = p_neck.addOrReplaceChild("neck1",
                CubeListBuilder.create().texOffs(65, 86).addBox(-2.5F, -3.0F, -3.6F, 5.0F, 6.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -3.6F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_headBase = p_neck1.addOrReplaceChild("headBase",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -3.2F, -3.9F, 5.0F, 10.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -0.8F, -4.0F, -0.22444934180647075F, 0.0F, 0.0F));
        PartDefinition p_rHeadFin = p_headBase.addOrReplaceChild("rHeadFin",
                CubeListBuilder.create().texOffs(43, 111).mirror().addBox(-7.6F, -16.0F, -4.1F, 7.0F, 15.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3141592653589793F, 0.13456488532876282F, 0.0F));
        PartDefinition p_jawLower = p_headBase.addOrReplaceChild("jawLower",
                CubeListBuilder.create().texOffs(88, 1).addBox(-1.5F, -0.5F, -5.2F, 3.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 6.7F, 2.3F, 0.7740535232594852F, 0.0F, 0.0F));
        PartDefinition p_innerJaw = p_headBase.addOrReplaceChild("innerJaw",
                CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -0.7F, -4.6F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 6.4F, 1.2F, 0.7740535232594852F, 0.0F, 0.0F));
        PartDefinition p_jaw2 = p_headBase.addOrReplaceChild("jaw2",
                CubeListBuilder.create().texOffs(52, 20).addBox(-1.5F, 5.0F, -8.4F, 3.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7740535232594852F, 0.0F, 0.0F));
        PartDefinition p_lHeadFin = p_headBase.addOrReplaceChild("lHeadFin",
                CubeListBuilder.create().texOffs(43, 111).addBox(0.6F, -16.0F, -4.1F, 7.0F, 15.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3141592653589793F, -0.13456488532876282F, 0.0F));
        PartDefinition p_lHead = p_headBase.addOrReplaceChild("lHead",
                CubeListBuilder.create().texOffs(53, 0).addBox(-0.5F, 4.1F, -6.7F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_headTop = p_headBase.addOrReplaceChild("headTop",
                CubeListBuilder.create().texOffs(28, 10).addBox(-2.5F, -11.8F, -4.7F, 5.0F, 10.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_rHorn1 = p_headBase.addOrReplaceChild("rHorn1",
                CubeListBuilder.create().texOffs(207, 41).mirror().addBox(-1.2F, -3.2F, -1.9F, 2.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(-1.5F, -1.6F, -1.6F, 0.36425021489121656F, -0.136659280431156F, -0.7285004297824331F));
        PartDefinition p_rHorn2 = p_rHorn1.addOrReplaceChild("rHorn2",
                CubeListBuilder.create().texOffs(219, 42).mirror().addBox(-1.0F, -3.6F, -1.2F, 1.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -2.9F, -0.7F, -0.18203784098300857F, 0.045553093477052F, 0.4553564018453205F));
        PartDefinition p_rHorn3 = p_rHorn2.addOrReplaceChild("rHorn3",
                CubeListBuilder.create().texOffs(228, 41).mirror().addBox(-0.5F, -2.6F, -1.1F, 1.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(-0.4F, -3.5F, 0.0F, -0.4553564018453205F, 0.045553093477052F, 0.136659280431156F));
        PartDefinition p_rHorn4 = p_rHorn3.addOrReplaceChild("rHorn4",
                CubeListBuilder.create().texOffs(238, 44).mirror().addBox(-0.5F, -4.0F, -0.2F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -2.1F, -0.3F, 0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_lHorn1 = p_headBase.addOrReplaceChild("lHorn1",
                CubeListBuilder.create().texOffs(207, 41).addBox(-1.0F, -3.2F, -1.9F, 2.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(1.5F, -1.6F, -1.6F, 0.36425021489121656F, 0.136659280431156F, 0.7285004297824331F));
        PartDefinition p_lHorn2 = p_lHorn1.addOrReplaceChild("lHorn2",
                CubeListBuilder.create().texOffs(219, 42).addBox(-0.1F, -3.7F, -1.2F, 1.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -2.9F, -0.7F, -0.18203784098300857F, -0.045553093477052F, -0.4553564018453205F));
        PartDefinition p_lHorn3 = p_lHorn2.addOrReplaceChild("lHorn3",
                CubeListBuilder.create().texOffs(228, 41).addBox(-0.4F, -2.6F, -1.2F, 1.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(0.2F, -3.5F, 0.0F, -0.4553564018453205F, -0.045553093477052F, -0.136659280431156F));
        PartDefinition p_lHorn4 = p_lHorn3.addOrReplaceChild("lHorn4",
                CubeListBuilder.create().texOffs(238, 44).addBox(-0.4F, -4.0F, -0.2F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -2.1F, -0.3F, 0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_rHead = p_headBase.addOrReplaceChild("rHead",
                CubeListBuilder.create().texOffs(34, 0).addBox(-2.51F, 4.1F, -6.7F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_lShoulderBlade = p_chest.addOrReplaceChild("lShoulderBlade",
                CubeListBuilder.create().texOffs(146, 34).addBox(4.1F, -1.0F, -7.2F, 2.0F, 3.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.4114477660878142F, -0.18203784098300857F, 0.136659280431156F));
        PartDefinition p_tail1 = p_chest.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(50, 66).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 3.5F, 18.5F, -0.40142572795869574F, 0.0F, 0.0F));
        PartDefinition p_tailSpines1 = p_tail1.addOrReplaceChild("tailSpines1",
                CubeListBuilder.create().texOffs(58, 94).addBox(0.0F, -5.5F, 0.0F, 0.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail2 = p_tail1.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(85, 66).addBox(-2.0F, -2.5F, -0.1F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_tail3 = p_tail2.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(118, 66).addBox(-1.5F, -1.8F, 0.0F, 3.0F, 3.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 10.4F, 0.10175269539126941F, 0.0F, 0.0F));
        PartDefinition p_tailSpines3 = p_tail3.addOrReplaceChild("tailSpines3",
                CubeListBuilder.create().texOffs(117, 94).addBox(0.0F, -4.1F, 1.3F, 0.0F, 6.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail4 = p_tail3.addOrReplaceChild("tail4",
                CubeListBuilder.create().texOffs(149, 66).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_tailSpines4 = p_tail4.addOrReplaceChild("tailSpines4",
                CubeListBuilder.create().texOffs(148, 96).addBox(0.0F, -5.3F, 1.0F, 0.0F, 8.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail5 = p_tail4.addOrReplaceChild("tail5",
                CubeListBuilder.create().texOffs(178, 66).addBox(-0.5F, -0.2F, 0.0F, 1.0F, 1.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.0F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_tailSpines5 = p_tail5.addOrReplaceChild("tailSpines5",
                CubeListBuilder.create().texOffs(178, 96).addBox(0.0F, -5.3F, 0.0F, 0.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailStabber = p_tail5.addOrReplaceChild("tailStabber",
                CubeListBuilder.create().texOffs(205, 66).addBox(0.0F, -3.0F, -0.4F, 0.0F, 6.0F, 15.0F),
                PartPose.offset(0.0F, 0.3F, 10.2F));
        PartDefinition p_tailSpines2 = p_tail2.addOrReplaceChild("tailSpines2",
                CubeListBuilder.create().texOffs(89, 94).addBox(0.0F, -4.9F, 0.2F, 0.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lWingArm1 = root.addOrReplaceChild("lWingArm1",
                CubeListBuilder.create().texOffs(134, 0).addBox(-1.0F, -9.0F, -1.5F, 2.0F, 9.0F, 3.0F),
                PartPose.offsetAndRotation(2.3F, -1.3F, 6.3F, 0.22759093446006054F, 0.0F, 0.1795943800302165F));
        PartDefinition p_lWingMembrane1 = p_lWingArm1.addOrReplaceChild("lWingMembrane1",
                CubeListBuilder.create().texOffs(142, 4).addBox(0.0F, -7.9F, -2.1F, 0.0F, 11.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.44872415068774213F, 0.0F, 0.0F));
        PartDefinition p_lWingArm2 = p_lWingArm1.addOrReplaceChild("lWingArm2",
                CubeListBuilder.create().texOffs(149, 0).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.13456488532876282F, 0.18203784098300857F, -0.045553093477052F));
        PartDefinition p_lWingArm3 = p_lWingArm2.addOrReplaceChild("lWingArm3",
                CubeListBuilder.create().texOffs(174, 0).addBox(-0.5F, -1.1F, 0.0F, 1.0F, 2.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 9.4F, -0.4038691889114879F, 0.0F, 0.0F));
        PartDefinition p_lWingArm4 = p_lWingArm3.addOrReplaceChild("lWingArm4",
                CubeListBuilder.create().texOffs(200, 0).addBox(-0.5F, -0.7F, 0.0F, 1.0F, 1.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 8.3F, -0.3141592653589793F, 0.0F, 0.0F));
        PartDefinition p_lWingMembrane4 = p_lWingArm4.addOrReplaceChild("lWingMembrane4",
                CubeListBuilder.create().texOffs(208, 3).addBox(0.0F, 0.0F, 1.4F, 0.0F, 17.0F, 14.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lWingMembrane3 = p_lWingArm3.addOrReplaceChild("lWingMembrane3",
                CubeListBuilder.create().texOffs(188, 8).addBox(0.0F, 0.0F, 0.7F, 0.0F, 15.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lWingMembrane2 = p_lWingArm2.addOrReplaceChild("lWingMembrane2",
                CubeListBuilder.create().texOffs(166, 5).addBox(0.0F, 0.0F, 0.0F, 0.0F, 13.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
