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
 * Geometry converted automatically from the 1.12.2 ModelYautja by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class YautjaModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "yautja"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart rArmUpper;
    public final ModelPart rShoulderGuard;
    public final ModelPart rArmLower;
    public final ModelPart rWristbracer;
    public final ModelPart blade2;
    public final ModelPart blade1;
    public final ModelPart rHand;
    public final ModelPart lThigh;
    public final ModelPart lLegguard;
    public final ModelPart lShin;
    public final ModelPart lFootSole;
    public final ModelPart lFootguard;
    public final ModelPart lKneepad;
    public final ModelPart body;
    public final ModelPart rPec;
    public final ModelPart lPec;
    public final ModelPart neck;
    public final ModelPart face;
    public final ModelPart lDred7;
    public final ModelPart lDred6;
    public final ModelPart lMandibleUpper;
    public final ModelPart lMandibleFangUpper;
    public final ModelPart headTop;
    public final ModelPart rDred6;
    public final ModelPart rInnerMouth;
    public final ModelPart nose;
    public final ModelPart rTendonUpper;
    public final ModelPart lTendonUpper;
    public final ModelPart teethUpper;
    public final ModelPart lDred2;
    public final ModelPart rMandibleUpper;
    public final ModelPart rMandibleFangUpper;
    public final ModelPart rDred3;
    public final ModelPart lInnerMouth;
    public final ModelPart bDred1;
    public final ModelPart rDred5;
    public final ModelPart lDred3;
    public final ModelPart headUpperLower;
    public final ModelPart neck2;
    public final ModelPart bDred7;
    public final ModelPart rCheek;
    public final ModelPart rDred1;
    public final ModelPart bDred2;
    public final ModelPart lDred1;
    public final ModelPart rDred2;
    public final ModelPart biomaskMouth;
    public final ModelPart biomaskSlope;
    public final ModelPart biomaskSlope3;
    public final ModelPart lBiomaskRidge;
    public final ModelPart rBiomaskSide4;
    public final ModelPart lBiomaskSide1;
    public final ModelPart mBiomaskJaw;
    public final ModelPart lBiomaskSide2;
    public final ModelPart biomaskHeadPlate;
    public final ModelPart rBiomaskRidge;
    public final ModelPart lBiomaskJaw;
    public final ModelPart biomaskVisor;
    public final ModelPart biomaskSlope2;
    public final ModelPart rBiomaskSide2;
    public final ModelPart lBiomaskSide3;
    public final ModelPart lBiomaskSide4;
    public final ModelPart rBiomaskSide1;
    public final ModelPart rBiomaskSide3;
    public final ModelPart lBiomaskEye;
    public final ModelPart rBiomaskEye;
    public final ModelPart sights;
    public final ModelPart jawLower;
    public final ModelPart rJaw;
    public final ModelPart lJaw;
    public final ModelPart teethLower;
    public final ModelPart lCheek;
    public final ModelPart rDred7;
    public final ModelPart lMandibleLower1;
    public final ModelPart lMandibleFangLower;
    public final ModelPart lTendon;
    public final ModelPart lMandibleLower2;
    public final ModelPart lDred5;
    public final ModelPart headBack;
    public final ModelPart bDred3;
    public final ModelPart rDred4;
    public final ModelPart lDred4;
    public final ModelPart bDred5;
    public final ModelPart headTop2;
    public final ModelPart bDred6;
    public final ModelPart headBack2;
    public final ModelPart bDred4;
    public final ModelPart rMandibleLower1;
    public final ModelPart rTendon;
    public final ModelPart rMandibleLower2;
    public final ModelPart rMandibleFangLower;
    public final ModelPart rThigh;
    public final ModelPart rKneepad;
    public final ModelPart rLegguard;
    public final ModelPart rShin;
    public final ModelPart rFootSole;
    public final ModelPart rFootguard;
    public final ModelPart lArmUpper;
    public final ModelPart lShoulderGuard;
    public final ModelPart lArmLower;
    public final ModelPart lHand;
    public final ModelPart lWristbracer;

    public YautjaModel(ModelPart root)
    {
        this.root = root;
        this.rArmUpper = root.getChild("rArmUpper");
        this.parts.put("rArmUpper", this.rArmUpper);
        this.rShoulderGuard = this.rArmUpper.getChild("rShoulderGuard");
        this.parts.put("rShoulderGuard", this.rShoulderGuard);
        this.rArmLower = this.rArmUpper.getChild("rArmLower");
        this.parts.put("rArmLower", this.rArmLower);
        this.rWristbracer = this.rArmLower.getChild("rWristbracer");
        this.parts.put("rWristbracer", this.rWristbracer);
        this.blade2 = this.rWristbracer.getChild("blade2");
        this.parts.put("blade2", this.blade2);
        this.blade1 = this.rWristbracer.getChild("blade1");
        this.parts.put("blade1", this.blade1);
        this.rHand = this.rArmLower.getChild("rHand");
        this.parts.put("rHand", this.rHand);
        this.lThigh = root.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lLegguard = this.lThigh.getChild("lLegguard");
        this.parts.put("lLegguard", this.lLegguard);
        this.lShin = this.lThigh.getChild("lShin");
        this.parts.put("lShin", this.lShin);
        this.lFootSole = this.lShin.getChild("lFootSole");
        this.parts.put("lFootSole", this.lFootSole);
        this.lFootguard = this.lShin.getChild("lFootguard");
        this.parts.put("lFootguard", this.lFootguard);
        this.lKneepad = this.lThigh.getChild("lKneepad");
        this.parts.put("lKneepad", this.lKneepad);
        this.body = root.getChild("body");
        this.parts.put("body", this.body);
        this.rPec = this.body.getChild("rPec");
        this.parts.put("rPec", this.rPec);
        this.lPec = this.body.getChild("lPec");
        this.parts.put("lPec", this.lPec);
        this.neck = this.body.getChild("neck");
        this.parts.put("neck", this.neck);
        this.face = root.getChild("face");
        this.parts.put("face", this.face);
        this.lDred7 = this.face.getChild("lDred7");
        this.parts.put("lDred7", this.lDred7);
        this.lDred6 = this.face.getChild("lDred6");
        this.parts.put("lDred6", this.lDred6);
        this.lMandibleUpper = this.face.getChild("lMandibleUpper");
        this.parts.put("lMandibleUpper", this.lMandibleUpper);
        this.lMandibleFangUpper = this.lMandibleUpper.getChild("lMandibleFangUpper");
        this.parts.put("lMandibleFangUpper", this.lMandibleFangUpper);
        this.headTop = this.face.getChild("headTop");
        this.parts.put("headTop", this.headTop);
        this.rDred6 = this.face.getChild("rDred6");
        this.parts.put("rDred6", this.rDred6);
        this.rInnerMouth = this.face.getChild("rInnerMouth");
        this.parts.put("rInnerMouth", this.rInnerMouth);
        this.nose = this.face.getChild("nose");
        this.parts.put("nose", this.nose);
        this.rTendonUpper = this.nose.getChild("rTendonUpper");
        this.parts.put("rTendonUpper", this.rTendonUpper);
        this.lTendonUpper = this.nose.getChild("lTendonUpper");
        this.parts.put("lTendonUpper", this.lTendonUpper);
        this.teethUpper = this.nose.getChild("teethUpper");
        this.parts.put("teethUpper", this.teethUpper);
        this.lDred2 = this.face.getChild("lDred2");
        this.parts.put("lDred2", this.lDred2);
        this.rMandibleUpper = this.face.getChild("rMandibleUpper");
        this.parts.put("rMandibleUpper", this.rMandibleUpper);
        this.rMandibleFangUpper = this.rMandibleUpper.getChild("rMandibleFangUpper");
        this.parts.put("rMandibleFangUpper", this.rMandibleFangUpper);
        this.rDred3 = this.face.getChild("rDred3");
        this.parts.put("rDred3", this.rDred3);
        this.lInnerMouth = this.face.getChild("lInnerMouth");
        this.parts.put("lInnerMouth", this.lInnerMouth);
        this.bDred1 = this.face.getChild("bDred1");
        this.parts.put("bDred1", this.bDred1);
        this.rDred5 = this.face.getChild("rDred5");
        this.parts.put("rDred5", this.rDred5);
        this.lDred3 = this.face.getChild("lDred3");
        this.parts.put("lDred3", this.lDred3);
        this.headUpperLower = this.face.getChild("headUpperLower");
        this.parts.put("headUpperLower", this.headUpperLower);
        this.neck2 = this.face.getChild("neck2");
        this.parts.put("neck2", this.neck2);
        this.bDred7 = this.face.getChild("bDred7");
        this.parts.put("bDred7", this.bDred7);
        this.rCheek = this.face.getChild("rCheek");
        this.parts.put("rCheek", this.rCheek);
        this.rDred1 = this.face.getChild("rDred1");
        this.parts.put("rDred1", this.rDred1);
        this.bDred2 = this.face.getChild("bDred2");
        this.parts.put("bDred2", this.bDred2);
        this.lDred1 = this.face.getChild("lDred1");
        this.parts.put("lDred1", this.lDred1);
        this.rDred2 = this.face.getChild("rDred2");
        this.parts.put("rDred2", this.rDred2);
        this.biomaskMouth = this.face.getChild("biomaskMouth");
        this.parts.put("biomaskMouth", this.biomaskMouth);
        this.biomaskSlope = this.biomaskMouth.getChild("biomaskSlope");
        this.parts.put("biomaskSlope", this.biomaskSlope);
        this.biomaskSlope3 = this.biomaskMouth.getChild("biomaskSlope3");
        this.parts.put("biomaskSlope3", this.biomaskSlope3);
        this.lBiomaskRidge = this.biomaskMouth.getChild("lBiomaskRidge");
        this.parts.put("lBiomaskRidge", this.lBiomaskRidge);
        this.rBiomaskSide4 = this.biomaskMouth.getChild("rBiomaskSide4");
        this.parts.put("rBiomaskSide4", this.rBiomaskSide4);
        this.lBiomaskSide1 = this.biomaskMouth.getChild("lBiomaskSide1");
        this.parts.put("lBiomaskSide1", this.lBiomaskSide1);
        this.mBiomaskJaw = this.biomaskMouth.getChild("mBiomaskJaw");
        this.parts.put("mBiomaskJaw", this.mBiomaskJaw);
        this.lBiomaskSide2 = this.biomaskMouth.getChild("lBiomaskSide2");
        this.parts.put("lBiomaskSide2", this.lBiomaskSide2);
        this.biomaskHeadPlate = this.biomaskMouth.getChild("biomaskHeadPlate");
        this.parts.put("biomaskHeadPlate", this.biomaskHeadPlate);
        this.rBiomaskRidge = this.biomaskMouth.getChild("rBiomaskRidge");
        this.parts.put("rBiomaskRidge", this.rBiomaskRidge);
        this.lBiomaskJaw = this.biomaskMouth.getChild("lBiomaskJaw");
        this.parts.put("lBiomaskJaw", this.lBiomaskJaw);
        this.biomaskVisor = this.biomaskMouth.getChild("biomaskVisor");
        this.parts.put("biomaskVisor", this.biomaskVisor);
        this.biomaskSlope2 = this.biomaskMouth.getChild("biomaskSlope2");
        this.parts.put("biomaskSlope2", this.biomaskSlope2);
        this.rBiomaskSide2 = this.biomaskMouth.getChild("rBiomaskSide2");
        this.parts.put("rBiomaskSide2", this.rBiomaskSide2);
        this.lBiomaskSide3 = this.biomaskMouth.getChild("lBiomaskSide3");
        this.parts.put("lBiomaskSide3", this.lBiomaskSide3);
        this.lBiomaskSide4 = this.biomaskMouth.getChild("lBiomaskSide4");
        this.parts.put("lBiomaskSide4", this.lBiomaskSide4);
        this.rBiomaskSide1 = this.biomaskMouth.getChild("rBiomaskSide1");
        this.parts.put("rBiomaskSide1", this.rBiomaskSide1);
        this.rBiomaskSide3 = this.biomaskMouth.getChild("rBiomaskSide3");
        this.parts.put("rBiomaskSide3", this.rBiomaskSide3);
        this.lBiomaskEye = this.biomaskMouth.getChild("lBiomaskEye");
        this.parts.put("lBiomaskEye", this.lBiomaskEye);
        this.rBiomaskEye = this.biomaskMouth.getChild("rBiomaskEye");
        this.parts.put("rBiomaskEye", this.rBiomaskEye);
        this.sights = this.biomaskMouth.getChild("sights");
        this.parts.put("sights", this.sights);
        this.jawLower = this.face.getChild("jawLower");
        this.parts.put("jawLower", this.jawLower);
        this.rJaw = this.jawLower.getChild("rJaw");
        this.parts.put("rJaw", this.rJaw);
        this.lJaw = this.jawLower.getChild("lJaw");
        this.parts.put("lJaw", this.lJaw);
        this.teethLower = this.jawLower.getChild("teethLower");
        this.parts.put("teethLower", this.teethLower);
        this.lCheek = this.face.getChild("lCheek");
        this.parts.put("lCheek", this.lCheek);
        this.rDred7 = this.face.getChild("rDred7");
        this.parts.put("rDred7", this.rDred7);
        this.lMandibleLower1 = this.face.getChild("lMandibleLower1");
        this.parts.put("lMandibleLower1", this.lMandibleLower1);
        this.lMandibleFangLower = this.lMandibleLower1.getChild("lMandibleFangLower");
        this.parts.put("lMandibleFangLower", this.lMandibleFangLower);
        this.lTendon = this.lMandibleLower1.getChild("lTendon");
        this.parts.put("lTendon", this.lTendon);
        this.lMandibleLower2 = this.lMandibleLower1.getChild("lMandibleLower2");
        this.parts.put("lMandibleLower2", this.lMandibleLower2);
        this.lDred5 = this.face.getChild("lDred5");
        this.parts.put("lDred5", this.lDred5);
        this.headBack = this.face.getChild("headBack");
        this.parts.put("headBack", this.headBack);
        this.bDred3 = this.face.getChild("bDred3");
        this.parts.put("bDred3", this.bDred3);
        this.rDred4 = this.face.getChild("rDred4");
        this.parts.put("rDred4", this.rDred4);
        this.lDred4 = this.face.getChild("lDred4");
        this.parts.put("lDred4", this.lDred4);
        this.bDred5 = this.face.getChild("bDred5");
        this.parts.put("bDred5", this.bDred5);
        this.headTop2 = this.face.getChild("headTop2");
        this.parts.put("headTop2", this.headTop2);
        this.bDred6 = this.face.getChild("bDred6");
        this.parts.put("bDred6", this.bDred6);
        this.headBack2 = this.face.getChild("headBack2");
        this.parts.put("headBack2", this.headBack2);
        this.bDred4 = this.face.getChild("bDred4");
        this.parts.put("bDred4", this.bDred4);
        this.rMandibleLower1 = this.face.getChild("rMandibleLower1");
        this.parts.put("rMandibleLower1", this.rMandibleLower1);
        this.rTendon = this.rMandibleLower1.getChild("rTendon");
        this.parts.put("rTendon", this.rTendon);
        this.rMandibleLower2 = this.rMandibleLower1.getChild("rMandibleLower2");
        this.parts.put("rMandibleLower2", this.rMandibleLower2);
        this.rMandibleFangLower = this.rMandibleLower1.getChild("rMandibleFangLower");
        this.parts.put("rMandibleFangLower", this.rMandibleFangLower);
        this.rThigh = root.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.rKneepad = this.rThigh.getChild("rKneepad");
        this.parts.put("rKneepad", this.rKneepad);
        this.rLegguard = this.rThigh.getChild("rLegguard");
        this.parts.put("rLegguard", this.rLegguard);
        this.rShin = this.rThigh.getChild("rShin");
        this.parts.put("rShin", this.rShin);
        this.rFootSole = this.rShin.getChild("rFootSole");
        this.parts.put("rFootSole", this.rFootSole);
        this.rFootguard = this.rShin.getChild("rFootguard");
        this.parts.put("rFootguard", this.rFootguard);
        this.lArmUpper = root.getChild("lArmUpper");
        this.parts.put("lArmUpper", this.lArmUpper);
        this.lShoulderGuard = this.lArmUpper.getChild("lShoulderGuard");
        this.parts.put("lShoulderGuard", this.lShoulderGuard);
        this.lArmLower = this.lArmUpper.getChild("lArmLower");
        this.parts.put("lArmLower", this.lArmLower);
        this.lHand = this.lArmLower.getChild("lHand");
        this.parts.put("lHand", this.lHand);
        this.lWristbracer = this.lArmLower.getChild("lWristbracer");
        this.parts.put("lWristbracer", this.lWristbracer);
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
                CubeListBuilder.create().texOffs(119, 35).addBox(-3.0F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(-4.9F, -2.5F, 0.0F, 0.06981317007977318F, 0.0F, 0.17453292519943295F));
        PartDefinition p_rShoulderGuard = p_rArmUpper.addOrReplaceChild("rShoulderGuard",
                CubeListBuilder.create().texOffs(103, 55).addBox(-3.5F, -1.5F, -2.0F, 3.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.10471975511965977F));
        PartDefinition p_rArmLower = p_rArmUpper.addOrReplaceChild("rArmLower",
                CubeListBuilder.create().texOffs(119, 42).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(-1.6F, 5.3F, 0.0F, -0.41887902047863906F, 0.0F, -0.17453292519943295F));
        PartDefinition p_rWristbracer = p_rArmLower.addOrReplaceChild("rWristbracer",
                CubeListBuilder.create().texOffs(134, 44).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0017453292519943296F));
        PartDefinition p_blade2 = p_rWristbracer.addOrReplaceChild("blade2",
                CubeListBuilder.create().texOffs(89, 26).addBox(-3.1F, 2.0F, 0.7F, 2.0F, 10.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_blade1 = p_rWristbracer.addOrReplaceChild("blade1",
                CubeListBuilder.create().texOffs(96, 26).addBox(-3.1F, 2.0F, -0.8F, 2.0F, 10.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rHand = p_rArmLower.addOrReplaceChild("rHand",
                CubeListBuilder.create().texOffs(120, 57).mirror().addBox(-1.3F, 6.4F, -1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lThigh = root.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(134, 0).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 8.0F, 5.0F),
                PartPose.offsetAndRotation(2.0F, 9.0F, 0.0F, -0.13962634015954636F, 0.0F, -0.06981317007977318F));
        PartDefinition p_lLegguard = p_lThigh.addOrReplaceChild("lLegguard",
                CubeListBuilder.create().texOffs(176, 0).addBox(1.8F, 1.0F, -2.6F, 1.0F, 7.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lShin = p_lThigh.addOrReplaceChild("lShin",
                CubeListBuilder.create().texOffs(134, 7).addBox(-1.9F, 0.0F, -2.3F, 4.0F, 7.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.13962634015954636F, 0.0F, 0.06981317007977318F));
        PartDefinition p_lFootSole = p_lShin.addOrReplaceChild("lFootSole",
                CubeListBuilder.create().texOffs(194, 0).addBox(-1.9F, 6.1F, -2.2F, 4.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lFootguard = p_lShin.addOrReplaceChild("lFootguard",
                CubeListBuilder.create().texOffs(217, 113).addBox(-2.0F, 5.1F, -3.3F, 4.0F, 2.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lKneepad = p_lThigh.addOrReplaceChild("lKneepad",
                CubeListBuilder.create().texOffs(176, 30).addBox(-1.5F, 4.8F, -4.0F, 4.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.13962634015954636F, 0.0F, 0.06981317007977318F));
        PartDefinition p_body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(103, 13).addBox(-4.5F, -4.0F, -2.5F, 9.0F, 14.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rPec = p_body.addOrReplaceChild("rPec",
                CubeListBuilder.create().texOffs(80, 0).addBox(-5.0F, -4.0F, -3.0F, 5.0F, 5.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.08726646259971647F));
        PartDefinition p_lPec = p_body.addOrReplaceChild("lPec",
                CubeListBuilder.create().texOffs(80, 13).addBox(0.0F, -4.0F, -3.0F, 5.0F, 5.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.08726646259971647F));
        PartDefinition p_neck = p_body.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(18, 10).addBox(-2.5F, -6.0F, -2.0F, 5.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_face = root.addOrReplaceChild("face",
                CubeListBuilder.create().texOffs(103, 0).addBox(-3.5F, -7.5F, -4.6F, 7.0F, 4.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -4.1F, 0.0F, -0.15707963267948966F, 0.0F, 0.0F));
        PartDefinition p_lDred7 = p_face.addOrReplaceChild("lDred7",
                CubeListBuilder.create().texOffs(0, 110).addBox(5.0F, -10.0F, 2.5F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, -0.5235987755982988F, 0.0F));
        PartDefinition p_lDred6 = p_face.addOrReplaceChild("lDred6",
                CubeListBuilder.create().texOffs(15, 110).addBox(4.0F, -9.5F, 3.5F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.08726646259971647F, -0.20943951023931953F, 0.0F));
        PartDefinition p_lMandibleUpper = p_face.addOrReplaceChild("lMandibleUpper",
                CubeListBuilder.create().texOffs(38, 10).addBox(-0.5F, -0.5F, -7.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.7F, -4.5F, -0.3F, 0.18203784098300857F, -0.36425021489121656F, -0.05777039824101231F));
        PartDefinition p_lMandibleFangUpper = p_lMandibleUpper.addOrReplaceChild("lMandibleFangUpper",
                CubeListBuilder.create().texOffs(85, 37).addBox(-2.2F, -0.5F, -6.7F, 2.0F, 1.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_headTop = p_face.addOrReplaceChild("headTop",
                CubeListBuilder.create().texOffs(19, 43).addBox(-3.0F, -8.2F, -9.8F, 6.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.064650843716541F, 0.0F, 0.0F));
        PartDefinition p_rDred6 = p_face.addOrReplaceChild("rDred6",
                CubeListBuilder.create().texOffs(58, 77).addBox(-10.0F, -9.5F, 3.5F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.08726646259971647F, 0.20943951023931953F, 0.0F));
        PartDefinition p_rInnerMouth = p_face.addOrReplaceChild("rInnerMouth",
                CubeListBuilder.create().texOffs(47, 49).addBox(-2.0F, -5.0F, 0.8F, 3.0F, 4.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, -0.5410520681182421F, 0.0F));
        PartDefinition p_nose = p_face.addOrReplaceChild("nose",
                CubeListBuilder.create().texOffs(48, 1).addBox(-1.0F, -4.5F, -5.0F, 2.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rTendonUpper = p_nose.addOrReplaceChild("rTendonUpper",
                CubeListBuilder.create().texOffs(65, 49).addBox(-4.5F, -4.6F, -4.2F, 6.0F, 0.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.136659280431156F, -0.3490658503988659F, -0.045553093477052F));
        PartDefinition p_lTendonUpper = p_nose.addOrReplaceChild("lTendonUpper",
                CubeListBuilder.create().texOffs(65, 54).addBox(-1.5F, -4.6F, -4.2F, 6.0F, 0.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.136659280431156F, 0.36425021489121656F, 0.045553093477052F));
        PartDefinition p_teethUpper = p_nose.addOrReplaceChild("teethUpper",
                CubeListBuilder.create().texOffs(80, 34).addBox(-1.0F, -3.5F, -5.0F, 2.0F, 1.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_lDred2 = p_face.addOrReplaceChild("lDred2",
                CubeListBuilder.create().texOffs(75, 110).addBox(3.0F, -8.0F, -0.5F, 5.0F, 8.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, -0.15707963267948966F, 0.0F));
        PartDefinition p_rMandibleUpper = p_face.addOrReplaceChild("rMandibleUpper",
                CubeListBuilder.create().texOffs(38, 22).addBox(-1.5F, -0.5F, -7.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.3F, -4.5F, -0.7F, 0.18203784098300857F, 0.36425021489121656F, 0.05777039824101231F));
        PartDefinition p_rMandibleFangUpper = p_rMandibleUpper.addOrReplaceChild("rMandibleFangUpper",
                CubeListBuilder.create().texOffs(90, 37).addBox(-0.8F, -0.5F, -6.7F, 2.0F, 1.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rDred3 = p_face.addOrReplaceChild("rDred3",
                CubeListBuilder.create().texOffs(13, 77).addBox(-9.0F, -8.5F, 0.5F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, 0.15707963267948966F, 0.0F));
        PartDefinition p_lInnerMouth = p_face.addOrReplaceChild("lInnerMouth",
                CubeListBuilder.create().texOffs(56, 49).addBox(-1.0F, -5.0F, 0.8F, 3.0F, 4.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.5410520681182421F, 0.0F));
        PartDefinition p_bDred1 = p_face.addOrReplaceChild("bDred1",
                CubeListBuilder.create().texOffs(19, 89).addBox(2.0F, -10.0F, 2.0F, 0.0F, 10.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.24434609527920614F, 0.0F));
        PartDefinition p_rDred5 = p_face.addOrReplaceChild("rDred5",
                CubeListBuilder.create().texOffs(43, 77).addBox(-10.0F, -9.0F, 2.5F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, 0.19198621771937624F, 0.0F));
        PartDefinition p_lDred3 = p_face.addOrReplaceChild("lDred3",
                CubeListBuilder.create().texOffs(60, 110).addBox(3.0F, -8.5F, 0.5F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, -0.15707963267948966F, 0.0F));
        PartDefinition p_headUpperLower = p_face.addOrReplaceChild("headUpperLower",
                CubeListBuilder.create().texOffs(0, 45).addBox(-3.5F, -7.2F, -7.8F, 7.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.064650843716541F, 0.0F, 0.0F));
        PartDefinition p_neck2 = p_face.addOrReplaceChild("neck2",
                CubeListBuilder.create().texOffs(69, 59).addBox(-2.5F, -4.0F, 1.0F, 5.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.0F, 0.0F));
        PartDefinition p_bDred7 = p_face.addOrReplaceChild("bDred7",
                CubeListBuilder.create().texOffs(76, 89).addBox(-2.0F, -10.0F, 2.0F, 0.0F, 10.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, -0.24434609527920614F, 0.0F));
        PartDefinition p_rCheek = p_face.addOrReplaceChild("rCheek",
                CubeListBuilder.create().texOffs(7, 37).addBox(-2.5F, -4.0F, -2.0F, 0.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.0F, 0.0F));
        PartDefinition p_rDred1 = p_face.addOrReplaceChild("rDred1",
                CubeListBuilder.create().texOffs(25, 19).addBox(-8.0F, -8.0F, -1.5F, 5.0F, 8.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, 0.06981317007977318F, 0.0F));
        PartDefinition p_bDred2 = p_face.addOrReplaceChild("bDred2",
                CubeListBuilder.create().texOffs(38, 89).addBox(2.0F, -10.0F, 2.0F, 0.0F, 10.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.10471975511965977F, 0.0F));
        PartDefinition p_lDred1 = p_face.addOrReplaceChild("lDred1",
                CubeListBuilder.create().texOffs(88, 110).addBox(3.0F, -8.0F, -1.5F, 5.0F, 8.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, -0.06981317007977318F, 0.0F));
        PartDefinition p_rDred2 = p_face.addOrReplaceChild("rDred2",
                CubeListBuilder.create().texOffs(0, 77).addBox(-8.0F, -8.0F, -0.5F, 5.0F, 8.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, 0.15707963267948966F, 0.0F));
        PartDefinition p_biomaskMouth = p_face.addOrReplaceChild("biomaskMouth",
                CubeListBuilder.create().texOffs(180, 87).addBox(-1.5F, -4.9F, -5.5F, 3.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -0.1F, 0.0F, 0.15707963267948966F, 0.0F, 0.0F));
        PartDefinition p_biomaskSlope = p_biomaskMouth.addOrReplaceChild("biomaskSlope",
                CubeListBuilder.create().texOffs(174, 69).addBox(-4.0F, 0.0F, -8.9F, 8.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217304763960306F, 0.0F, 0.0F));
        PartDefinition p_biomaskSlope3 = p_biomaskMouth.addOrReplaceChild("biomaskSlope3",
                CubeListBuilder.create().texOffs(178, 77).addBox(-1.5F, -8.0F, -3.1F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.19198621771937624F, 0.0F, 0.0F));
        PartDefinition p_lBiomaskRidge = p_biomaskMouth.addOrReplaceChild("lBiomaskRidge",
                CubeListBuilder.create().texOffs(180, 115).addBox(1.5F, -10.5F, -8.7F, 3.0F, 10.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.1344640137963142F, 0.12217304763960307F, 0.0F));
        PartDefinition p_rBiomaskSide4 = p_biomaskMouth.addOrReplaceChild("rBiomaskSide4",
                CubeListBuilder.create().texOffs(207, 88).addBox(-1.0F, -5.0F, -5.3F, 3.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6457718232379019F, 0.0F));
        PartDefinition p_lBiomaskSide1 = p_biomaskMouth.addOrReplaceChild("lBiomaskSide1",
                CubeListBuilder.create().texOffs(221, 104).addBox(-3.0F, -5.0F, -4.3F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.48352986419518F, 0.0F));
        PartDefinition p_mBiomaskJaw = p_biomaskMouth.addOrReplaceChild("mBiomaskJaw",
                CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, -2.5F, -4.3F, 2.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lBiomaskSide2 = p_biomaskMouth.addOrReplaceChild("lBiomaskSide2",
                CubeListBuilder.create().texOffs(221, 98).addBox(-3.0F, -4.0F, -4.0F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.413716694115407F, 0.0F));
        PartDefinition p_biomaskHeadPlate = p_biomaskMouth.addOrReplaceChild("biomaskHeadPlate",
                CubeListBuilder.create().texOffs(173, 57).addBox(-3.5F, -8.0F, -10.0F, 7.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217304763960306F, 0.0F, 0.0F));
        PartDefinition p_rBiomaskRidge = p_biomaskMouth.addOrReplaceChild("rBiomaskRidge",
                CubeListBuilder.create().texOffs(180, 101).addBox(-4.5F, -10.5F, -8.7F, 3.0F, 10.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.1344640137963142F, -0.12217304763960307F, 0.0F));
        PartDefinition p_lBiomaskJaw = p_biomaskMouth.addOrReplaceChild("lBiomaskJaw",
                CubeListBuilder.create().texOffs(65, 0).addBox(0.5F, -2.5F, -4.0F, 1.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.12217304763960307F, 0.0F));
        PartDefinition p_biomaskVisor = p_biomaskMouth.addOrReplaceChild("biomaskVisor",
                CubeListBuilder.create().texOffs(176, 82).addBox(-3.5F, -6.5F, -5.3F, 7.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.22689280275926282F, 0.0F, 0.0F));
        PartDefinition p_biomaskSlope2 = p_biomaskMouth.addOrReplaceChild("biomaskSlope2",
                CubeListBuilder.create().texOffs(178, 73).addBox(-1.5F, -4.5F, -7.9F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.593411945678072F, 0.0F, 0.0F));
        PartDefinition p_rBiomaskSide2 = p_biomaskMouth.addOrReplaceChild("rBiomaskSide2",
                CubeListBuilder.create().texOffs(207, 98).addBox(0.0F, -4.0F, -4.0F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.413716694115407F, 0.0F));
        PartDefinition p_lBiomaskSide3 = p_biomaskMouth.addOrReplaceChild("lBiomaskSide3",
                CubeListBuilder.create().texOffs(221, 93).addBox(-2.0F, -4.0F, -5.0F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6457718232379019F, 0.0F));
        PartDefinition p_lBiomaskSide4 = p_biomaskMouth.addOrReplaceChild("lBiomaskSide4",
                CubeListBuilder.create().texOffs(219, 88).addBox(-2.0F, -5.0F, -5.3F, 3.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6457718232379019F, 0.0F));
        PartDefinition p_rBiomaskSide1 = p_biomaskMouth.addOrReplaceChild("rBiomaskSide1",
                CubeListBuilder.create().texOffs(207, 104).addBox(0.3F, -5.0F, -4.3F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.48352986419518F, 0.0F));
        PartDefinition p_rBiomaskSide3 = p_biomaskMouth.addOrReplaceChild("rBiomaskSide3",
                CubeListBuilder.create().texOffs(207, 93).addBox(-1.0F, -4.0F, -5.0F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6457718232379019F, 0.0F));
        PartDefinition p_lBiomaskEye = p_biomaskMouth.addOrReplaceChild("lBiomaskEye",
                CubeListBuilder.create().texOffs(219, 78).addBox(3.2F, -8.0F, -3.2F, 1.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.17453292519943295F, 0.0F));
        PartDefinition p_rBiomaskEye = p_biomaskMouth.addOrReplaceChild("rBiomaskEye",
                CubeListBuilder.create().texOffs(207, 78).addBox(-4.2F, -8.0F, -3.2F, 1.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.17453292519943295F, 0.0F));
        PartDefinition p_sights = p_biomaskMouth.addOrReplaceChild("sights",
                CubeListBuilder.create().texOffs(214, 55).addBox(-3.5F, -8.9F, -2.5F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2241002759560719F, -0.10367255756846318F, -0.14032447186034408F));
        PartDefinition p_jawLower = p_face.addOrReplaceChild("jawLower",
                CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, -0.5F, -2.7F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -1.6F, -2.0F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_rJaw = p_jawLower.addOrReplaceChild("rJaw",
                CubeListBuilder.create().texOffs(33, 0).addBox(-1.3F, -0.5F, -2.5F, 1.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.12217304763960307F, 0.0F));
        PartDefinition p_lJaw = p_jawLower.addOrReplaceChild("lJaw",
                CubeListBuilder.create().texOffs(65, 0).addBox(0.3F, -0.5F, -2.5F, 1.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.12217304763960307F, 0.0F));
        PartDefinition p_teethLower = p_jawLower.addOrReplaceChild("teethLower",
                CubeListBuilder.create().texOffs(80, 36).addBox(-1.0F, -1.5F, -2.5F, 2.0F, 2.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lCheek = p_face.addOrReplaceChild("lCheek",
                CubeListBuilder.create().texOffs(7, 29).addBox(2.5F, -4.0F, -2.0F, 0.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.0F, 0.0F));
        PartDefinition p_rDred7 = p_face.addOrReplaceChild("rDred7",
                CubeListBuilder.create().texOffs(73, 77).addBox(-11.0F, -10.0F, 2.5F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.5235987755982988F, 0.0F));
        PartDefinition p_lMandibleLower1 = p_face.addOrReplaceChild("lMandibleLower1",
                CubeListBuilder.create().texOffs(59, 22).addBox(0.5F, 0.0F, -7.0F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.6F, -1.7F, 0.0F, 0.31869712141416456F, -0.18203784098300857F, -0.2537708732399755F));
        PartDefinition p_lMandibleFangLower = p_lMandibleLower1.addOrReplaceChild("lMandibleFangLower",
                CubeListBuilder.create().texOffs(85, 25).addBox(1.0F, -4.5F, -7.1F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lTendon = p_lMandibleLower1.addOrReplaceChild("lTendon",
                CubeListBuilder.create().texOffs(61, 34).addBox(1.0F, -3.0F, -6.1F, 0.0F, 4.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.045553093477052F, 0.0F, 0.18203784098300857F));
        PartDefinition p_lMandibleLower2 = p_lMandibleLower1.addOrReplaceChild("lMandibleLower2",
                CubeListBuilder.create().texOffs(80, 30).addBox(0.5F, -2.6F, -7.1F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lDred5 = p_face.addOrReplaceChild("lDred5",
                CubeListBuilder.create().texOffs(30, 110).addBox(4.0F, -9.0F, 2.5F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, -0.19198621771937624F, 0.0F));
        PartDefinition p_headBack = p_face.addOrReplaceChild("headBack",
                CubeListBuilder.create().texOffs(0, 18).addBox(-3.5F, -7.0F, -9.5F, 7.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.239183768915974F, 0.0F, 0.0F));
        PartDefinition p_bDred3 = p_face.addOrReplaceChild("bDred3",
                CubeListBuilder.create().texOffs(57, 89).addBox(1.0F, -10.0F, 2.0F, 0.0F, 10.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.06981317007977318F, 0.0F));
        PartDefinition p_rDred4 = p_face.addOrReplaceChild("rDred4",
                CubeListBuilder.create().texOffs(28, 77).addBox(-10.0F, -9.0F, 1.5F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, 0.19198621771937624F, 0.0F));
        PartDefinition p_lDred4 = p_face.addOrReplaceChild("lDred4",
                CubeListBuilder.create().texOffs(45, 110).addBox(4.0F, -9.0F, 1.5F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, -0.19198621771937624F, 0.0F));
        PartDefinition p_bDred5 = p_face.addOrReplaceChild("bDred5",
                CubeListBuilder.create().texOffs(114, 89).addBox(-1.0F, -10.0F, 2.0F, 0.0F, 10.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.17453292519943295F, -0.06981317007977318F, 0.0F));
        PartDefinition p_headTop2 = p_face.addOrReplaceChild("headTop2",
                CubeListBuilder.create().texOffs(17, 30).mirror().addBox(-4.0F, -8.7F, -8.8F, 8.0F, 9.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.064650843716541F, 0.0F, 0.0F));
        PartDefinition p_bDred6 = p_face.addOrReplaceChild("bDred6",
                CubeListBuilder.create().texOffs(95, 89).addBox(-2.0F, -10.0F, 2.0F, 0.0F, 10.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, -0.10471975511965977F, 0.0F));
        PartDefinition p_headBack2 = p_face.addOrReplaceChild("headBack2",
                CubeListBuilder.create().texOffs(13, 56).addBox(-3.5F, -8.9F, -4.4F, 7.0F, 6.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5759586531581287F, 0.0F, 0.0F));
        PartDefinition p_bDred4 = p_face.addOrReplaceChild("bDred4",
                CubeListBuilder.create().texOffs(0, 89).addBox(0.0F, -10.0F, 2.0F, 0.0F, 10.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.0F, 0.0F));
        PartDefinition p_rMandibleLower1 = p_face.addOrReplaceChild("rMandibleLower1",
                CubeListBuilder.create().texOffs(59, 10).addBox(-0.5F, 0.0F, -7.0F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(-1.6F, -1.9F, 0.0F, 0.31869712141416456F, 0.18203784098300857F, 0.2537708732399755F));
        PartDefinition p_rTendon = p_rMandibleLower1.addOrReplaceChild("rTendon",
                CubeListBuilder.create().texOffs(42, 34).addBox(0.0F, -2.9F, -6.1F, 0.0F, 4.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.045553093477052F, 0.0F, -0.18203784098300857F));
        PartDefinition p_rMandibleLower2 = p_rMandibleLower1.addOrReplaceChild("rMandibleLower2",
                CubeListBuilder.create().texOffs(80, 25).addBox(-0.5F, -2.6F, -7.1F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rMandibleFangLower = p_rMandibleLower1.addOrReplaceChild("rMandibleFangLower",
                CubeListBuilder.create().texOffs(85, 30).addBox(0.0F, -4.5F, -7.1F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rThigh = root.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(155, 0).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 8.0F, 5.0F),
                PartPose.offsetAndRotation(-2.0F, 9.0F, 0.0F, -0.13962634015954636F, 0.0F, 0.06981317007977318F));
        PartDefinition p_rKneepad = p_rThigh.addOrReplaceChild("rKneepad",
                CubeListBuilder.create().texOffs(176, 38).addBox(-2.5F, 4.8F, -4.0F, 4.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.13962634015954636F, 0.0F, -0.06981317007977318F));
        PartDefinition p_rLegguard = p_rThigh.addOrReplaceChild("rLegguard",
                CubeListBuilder.create().texOffs(176, 15).addBox(-2.8F, 1.0F, -2.6F, 1.0F, 7.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rShin = p_rThigh.addOrReplaceChild("rShin",
                CubeListBuilder.create().texOffs(155, 7).addBox(-1.9F, 0.0F, -2.3F, 4.0F, 7.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.13962634015954636F, 0.0F, -0.06981317007977318F));
        PartDefinition p_rFootSole = p_rShin.addOrReplaceChild("rFootSole",
                CubeListBuilder.create().texOffs(194, 0).addBox(-2.0F, 6.1F, -2.2F, 4.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rFootguard = p_rShin.addOrReplaceChild("rFootguard",
                CubeListBuilder.create().texOffs(196, 113).addBox(-2.0F, 5.1F, -3.3F, 4.0F, 2.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lArmUpper = root.addOrReplaceChild("lArmUpper",
                CubeListBuilder.create().texOffs(103, 35).addBox(0.0F, -1.0F, -2.0F, 3.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(4.9F, -2.5F, 0.0F, 0.06981317007977318F, 0.0F, -0.17453292519943295F));
        PartDefinition p_lShoulderGuard = p_lArmUpper.addOrReplaceChild("lShoulderGuard",
                CubeListBuilder.create().texOffs(86, 52).addBox(0.5F, -1.5F, -2.5F, 3.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.10471975511965977F));
        PartDefinition p_lArmLower = p_lArmUpper.addOrReplaceChild("lArmLower",
                CubeListBuilder.create().texOffs(103, 42).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(1.6F, 5.3F, -0.4F, -0.13962634015954636F, 0.0F, 0.17453292519943295F));
        PartDefinition p_lHand = p_lArmLower.addOrReplaceChild("lHand",
                CubeListBuilder.create().texOffs(120, 57).addBox(-1.7F, 6.4F, -1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lWristbracer = p_lArmLower.addOrReplaceChild("lWristbracer",
                CubeListBuilder.create().texOffs(84, 39).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
