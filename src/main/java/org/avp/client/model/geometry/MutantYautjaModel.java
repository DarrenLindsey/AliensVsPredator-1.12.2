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
 * Geometry converted automatically from the 1.12.2 ModelMutantYautja by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class MutantYautjaModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "mutant_yautja"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart lThigh;
    public final ModelPart lShin;
    public final ModelPart lFootSole;
    public final ModelPart rArmUpper;
    public final ModelPart rArmLower;
    public final ModelPart rHand;
    public final ModelPart rShoulderGuard;
    public final ModelPart lArmUpper;
    public final ModelPart lShoulderGuard;
    public final ModelPart lArmLower;
    public final ModelPart lHand;
    public final ModelPart rThigh;
    public final ModelPart rShin;
    public final ModelPart rFootSole;
    public final ModelPart body;
    public final ModelPart face;
    public final ModelPart lDred3;
    public final ModelPart rDred5;
    public final ModelPart rDred4;
    public final ModelPart rDred2;
    public final ModelPart lMandibleLower1;
    public final ModelPart lMandibleFangLower;
    public final ModelPart lMandibleLower2;
    public final ModelPart lMandibleLower1b;
    public final ModelPart lMandibleFangLowerb;
    public final ModelPart lMandibleLower2b;
    public final ModelPart lMandibleLower1c;
    public final ModelPart lMandibleFangLowerc;
    public final ModelPart lMandibleLower2c;
    public final ModelPart bDred2;
    public final ModelPart lDred1;
    public final ModelPart lInnerMouth;
    public final ModelPart rDred7;
    public final ModelPart rInnerMouth;
    public final ModelPart bDred3;
    public final ModelPart rCheek;
    public final ModelPart headTop;
    public final ModelPart headTop3;
    public final ModelPart bDred4;
    public final ModelPart rDred3;
    public final ModelPart neck2;
    public final ModelPart nose;
    public final ModelPart teethUpper;
    public final ModelPart jawLower;
    public final ModelPart teethLower;
    public final ModelPart lJaw;
    public final ModelPart rJaw;
    public final ModelPart lCheek;
    public final ModelPart headUpperLower;
    public final ModelPart rDred6;
    public final ModelPart lDred2;
    public final ModelPart rMandibleLower1;
    public final ModelPart rMandibleLower1b;
    public final ModelPart rMandibleLower2b;
    public final ModelPart rMandibleFangLowerb;
    public final ModelPart rMandibleLower1c;
    public final ModelPart rMandibleLower2c;
    public final ModelPart rMandibleFangLowerc;
    public final ModelPart rMandibleLower2;
    public final ModelPart rMandibleFangLower;
    public final ModelPart rDred1;
    public final ModelPart rMandibleUpper;
    public final ModelPart rMandibleUpperb;
    public final ModelPart rMandibleFangUpperb;
    public final ModelPart rMandibleUpperc;
    public final ModelPart rMandibleFangUpperc;
    public final ModelPart rMandibleFangUpper;
    public final ModelPart headTop2;
    public final ModelPart headBack2;
    public final ModelPart lMandibleUpper;
    public final ModelPart lMandibleFangUpper;
    public final ModelPart lMandibleUpperb;
    public final ModelPart lMandibleUpperc;
    public final ModelPart lMandibleFangUpperc;
    public final ModelPart lMandibleFangUpperb;
    public final ModelPart lDred7;
    public final ModelPart lDred6;
    public final ModelPart lDred4;
    public final ModelPart lDred5;
    public final ModelPart bDred6;
    public final ModelPart bDred5;
    public final ModelPart lPec;
    public final ModelPart lBackSpike;
    public final ModelPart spines;
    public final ModelPart rPec;
    public final ModelPart rBackSpike;
    public final ModelPart neck;

    public MutantYautjaModel(ModelPart root)
    {
        this.root = root;
        this.lThigh = root.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lShin = this.lThigh.getChild("lShin");
        this.parts.put("lShin", this.lShin);
        this.lFootSole = this.lShin.getChild("lFootSole");
        this.parts.put("lFootSole", this.lFootSole);
        this.rArmUpper = root.getChild("rArmUpper");
        this.parts.put("rArmUpper", this.rArmUpper);
        this.rArmLower = this.rArmUpper.getChild("rArmLower");
        this.parts.put("rArmLower", this.rArmLower);
        this.rHand = this.rArmUpper.getChild("rHand");
        this.parts.put("rHand", this.rHand);
        this.rShoulderGuard = this.rArmUpper.getChild("rShoulderGuard");
        this.parts.put("rShoulderGuard", this.rShoulderGuard);
        this.lArmUpper = root.getChild("lArmUpper");
        this.parts.put("lArmUpper", this.lArmUpper);
        this.lShoulderGuard = this.lArmUpper.getChild("lShoulderGuard");
        this.parts.put("lShoulderGuard", this.lShoulderGuard);
        this.lArmLower = this.lArmUpper.getChild("lArmLower");
        this.parts.put("lArmLower", this.lArmLower);
        this.lHand = this.lArmUpper.getChild("lHand");
        this.parts.put("lHand", this.lHand);
        this.rThigh = root.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.rShin = this.rThigh.getChild("rShin");
        this.parts.put("rShin", this.rShin);
        this.rFootSole = this.rShin.getChild("rFootSole");
        this.parts.put("rFootSole", this.rFootSole);
        this.body = root.getChild("body");
        this.parts.put("body", this.body);
        this.face = this.body.getChild("face");
        this.parts.put("face", this.face);
        this.lDred3 = this.face.getChild("lDred3");
        this.parts.put("lDred3", this.lDred3);
        this.rDred5 = this.face.getChild("rDred5");
        this.parts.put("rDred5", this.rDred5);
        this.rDred4 = this.face.getChild("rDred4");
        this.parts.put("rDred4", this.rDred4);
        this.rDred2 = this.face.getChild("rDred2");
        this.parts.put("rDred2", this.rDred2);
        this.lMandibleLower1 = this.face.getChild("lMandibleLower1");
        this.parts.put("lMandibleLower1", this.lMandibleLower1);
        this.lMandibleFangLower = this.lMandibleLower1.getChild("lMandibleFangLower");
        this.parts.put("lMandibleFangLower", this.lMandibleFangLower);
        this.lMandibleLower2 = this.lMandibleLower1.getChild("lMandibleLower2");
        this.parts.put("lMandibleLower2", this.lMandibleLower2);
        this.lMandibleLower1b = this.lMandibleLower1.getChild("lMandibleLower1b");
        this.parts.put("lMandibleLower1b", this.lMandibleLower1b);
        this.lMandibleFangLowerb = this.lMandibleLower1b.getChild("lMandibleFangLowerb");
        this.parts.put("lMandibleFangLowerb", this.lMandibleFangLowerb);
        this.lMandibleLower2b = this.lMandibleLower1b.getChild("lMandibleLower2b");
        this.parts.put("lMandibleLower2b", this.lMandibleLower2b);
        this.lMandibleLower1c = this.lMandibleLower1b.getChild("lMandibleLower1c");
        this.parts.put("lMandibleLower1c", this.lMandibleLower1c);
        this.lMandibleFangLowerc = this.lMandibleLower1c.getChild("lMandibleFangLowerc");
        this.parts.put("lMandibleFangLowerc", this.lMandibleFangLowerc);
        this.lMandibleLower2c = this.lMandibleLower1c.getChild("lMandibleLower2c");
        this.parts.put("lMandibleLower2c", this.lMandibleLower2c);
        this.bDred2 = this.face.getChild("bDred2");
        this.parts.put("bDred2", this.bDred2);
        this.lDred1 = this.face.getChild("lDred1");
        this.parts.put("lDred1", this.lDred1);
        this.lInnerMouth = this.face.getChild("lInnerMouth");
        this.parts.put("lInnerMouth", this.lInnerMouth);
        this.rDred7 = this.face.getChild("rDred7");
        this.parts.put("rDred7", this.rDred7);
        this.rInnerMouth = this.face.getChild("rInnerMouth");
        this.parts.put("rInnerMouth", this.rInnerMouth);
        this.bDred3 = this.face.getChild("bDred3");
        this.parts.put("bDred3", this.bDred3);
        this.rCheek = this.face.getChild("rCheek");
        this.parts.put("rCheek", this.rCheek);
        this.headTop = this.face.getChild("headTop");
        this.parts.put("headTop", this.headTop);
        this.headTop3 = this.headTop.getChild("headTop3");
        this.parts.put("headTop3", this.headTop3);
        this.bDred4 = this.face.getChild("bDred4");
        this.parts.put("bDred4", this.bDred4);
        this.rDred3 = this.face.getChild("rDred3");
        this.parts.put("rDred3", this.rDred3);
        this.neck2 = this.face.getChild("neck2");
        this.parts.put("neck2", this.neck2);
        this.nose = this.face.getChild("nose");
        this.parts.put("nose", this.nose);
        this.teethUpper = this.nose.getChild("teethUpper");
        this.parts.put("teethUpper", this.teethUpper);
        this.jawLower = this.face.getChild("jawLower");
        this.parts.put("jawLower", this.jawLower);
        this.teethLower = this.jawLower.getChild("teethLower");
        this.parts.put("teethLower", this.teethLower);
        this.lJaw = this.jawLower.getChild("lJaw");
        this.parts.put("lJaw", this.lJaw);
        this.rJaw = this.jawLower.getChild("rJaw");
        this.parts.put("rJaw", this.rJaw);
        this.lCheek = this.face.getChild("lCheek");
        this.parts.put("lCheek", this.lCheek);
        this.headUpperLower = this.face.getChild("headUpperLower");
        this.parts.put("headUpperLower", this.headUpperLower);
        this.rDred6 = this.face.getChild("rDred6");
        this.parts.put("rDred6", this.rDred6);
        this.lDred2 = this.face.getChild("lDred2");
        this.parts.put("lDred2", this.lDred2);
        this.rMandibleLower1 = this.face.getChild("rMandibleLower1");
        this.parts.put("rMandibleLower1", this.rMandibleLower1);
        this.rMandibleLower1b = this.rMandibleLower1.getChild("rMandibleLower1b");
        this.parts.put("rMandibleLower1b", this.rMandibleLower1b);
        this.rMandibleLower2b = this.rMandibleLower1b.getChild("rMandibleLower2b");
        this.parts.put("rMandibleLower2b", this.rMandibleLower2b);
        this.rMandibleFangLowerb = this.rMandibleLower1b.getChild("rMandibleFangLowerb");
        this.parts.put("rMandibleFangLowerb", this.rMandibleFangLowerb);
        this.rMandibleLower1c = this.rMandibleLower1b.getChild("rMandibleLower1c");
        this.parts.put("rMandibleLower1c", this.rMandibleLower1c);
        this.rMandibleLower2c = this.rMandibleLower1c.getChild("rMandibleLower2c");
        this.parts.put("rMandibleLower2c", this.rMandibleLower2c);
        this.rMandibleFangLowerc = this.rMandibleLower1c.getChild("rMandibleFangLowerc");
        this.parts.put("rMandibleFangLowerc", this.rMandibleFangLowerc);
        this.rMandibleLower2 = this.rMandibleLower1.getChild("rMandibleLower2");
        this.parts.put("rMandibleLower2", this.rMandibleLower2);
        this.rMandibleFangLower = this.rMandibleLower1.getChild("rMandibleFangLower");
        this.parts.put("rMandibleFangLower", this.rMandibleFangLower);
        this.rDred1 = this.face.getChild("rDred1");
        this.parts.put("rDred1", this.rDred1);
        this.rMandibleUpper = this.face.getChild("rMandibleUpper");
        this.parts.put("rMandibleUpper", this.rMandibleUpper);
        this.rMandibleUpperb = this.rMandibleUpper.getChild("rMandibleUpperb");
        this.parts.put("rMandibleUpperb", this.rMandibleUpperb);
        this.rMandibleFangUpperb = this.rMandibleUpperb.getChild("rMandibleFangUpperb");
        this.parts.put("rMandibleFangUpperb", this.rMandibleFangUpperb);
        this.rMandibleUpperc = this.rMandibleUpperb.getChild("rMandibleUpperc");
        this.parts.put("rMandibleUpperc", this.rMandibleUpperc);
        this.rMandibleFangUpperc = this.rMandibleUpperc.getChild("rMandibleFangUpperc");
        this.parts.put("rMandibleFangUpperc", this.rMandibleFangUpperc);
        this.rMandibleFangUpper = this.rMandibleUpper.getChild("rMandibleFangUpper");
        this.parts.put("rMandibleFangUpper", this.rMandibleFangUpper);
        this.headTop2 = this.face.getChild("headTop2");
        this.parts.put("headTop2", this.headTop2);
        this.headBack2 = this.face.getChild("headBack2");
        this.parts.put("headBack2", this.headBack2);
        this.lMandibleUpper = this.face.getChild("lMandibleUpper");
        this.parts.put("lMandibleUpper", this.lMandibleUpper);
        this.lMandibleFangUpper = this.lMandibleUpper.getChild("lMandibleFangUpper");
        this.parts.put("lMandibleFangUpper", this.lMandibleFangUpper);
        this.lMandibleUpperb = this.lMandibleUpper.getChild("lMandibleUpperb");
        this.parts.put("lMandibleUpperb", this.lMandibleUpperb);
        this.lMandibleUpperc = this.lMandibleUpperb.getChild("lMandibleUpperc");
        this.parts.put("lMandibleUpperc", this.lMandibleUpperc);
        this.lMandibleFangUpperc = this.lMandibleUpperc.getChild("lMandibleFangUpperc");
        this.parts.put("lMandibleFangUpperc", this.lMandibleFangUpperc);
        this.lMandibleFangUpperb = this.lMandibleUpperb.getChild("lMandibleFangUpperb");
        this.parts.put("lMandibleFangUpperb", this.lMandibleFangUpperb);
        this.lDred7 = this.face.getChild("lDred7");
        this.parts.put("lDred7", this.lDred7);
        this.lDred6 = this.face.getChild("lDred6");
        this.parts.put("lDred6", this.lDred6);
        this.lDred4 = this.face.getChild("lDred4");
        this.parts.put("lDred4", this.lDred4);
        this.lDred5 = this.face.getChild("lDred5");
        this.parts.put("lDred5", this.lDred5);
        this.bDred6 = this.face.getChild("bDred6");
        this.parts.put("bDred6", this.bDred6);
        this.bDred5 = this.face.getChild("bDred5");
        this.parts.put("bDred5", this.bDred5);
        this.lPec = this.body.getChild("lPec");
        this.parts.put("lPec", this.lPec);
        this.lBackSpike = this.body.getChild("lBackSpike");
        this.parts.put("lBackSpike", this.lBackSpike);
        this.spines = this.body.getChild("spines");
        this.parts.put("spines", this.spines);
        this.rPec = this.body.getChild("rPec");
        this.parts.put("rPec", this.rPec);
        this.rBackSpike = this.body.getChild("rBackSpike");
        this.parts.put("rBackSpike", this.rBackSpike);
        this.neck = this.body.getChild("neck");
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
        PartDefinition p_lThigh = root.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(134, 0).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 9.0F, 5.0F),
                PartPose.offsetAndRotation(2.0F, 9.5F, 4.3F, -0.40980330836826856F, 0.0F, -0.06981317007977318F));
        PartDefinition p_lShin = p_lThigh.addOrReplaceChild("lShin",
                CubeListBuilder.create().texOffs(134, 7).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 7.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.40980330836826856F, 0.0F, 0.06981317007977318F));
        PartDefinition p_lFootSole = p_lShin.addOrReplaceChild("lFootSole",
                CubeListBuilder.create().texOffs(194, 0).addBox(-2.0F, 6.2F, -2.5F, 4.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rArmUpper = root.addOrReplaceChild("rArmUpper",
                CubeListBuilder.create().texOffs(119, 35).addBox(-3.0F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(-4.4F, 1.0F, 0.0F, -0.136659280431156F, 0.0F, 0.17453292519943295F));
        PartDefinition p_rArmLower = p_rArmUpper.addOrReplaceChild("rArmLower",
                CubeListBuilder.create().texOffs(119, 42).addBox(-4.0F, 5.0F, -0.7F, 3.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.13962634015954636F, 0.0F, -0.17453292519943295F));
        PartDefinition p_rHand = p_rArmUpper.addOrReplaceChild("rHand",
                CubeListBuilder.create().texOffs(120, 57).mirror().addBox(-3.9F, 11.1F, -0.7F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.13962634015954636F, 0.0F, -0.17453292519943295F));
        PartDefinition p_rShoulderGuard = p_rArmUpper.addOrReplaceChild("rShoulderGuard",
                CubeListBuilder.create().texOffs(103, 55).addBox(-3.5F, -1.5F, -2.0F, 3.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.10471975511965977F));
        PartDefinition p_lArmUpper = root.addOrReplaceChild("lArmUpper",
                CubeListBuilder.create().texOffs(103, 35).addBox(0.0F, -1.0F, -2.0F, 3.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(4.4F, 1.0F, 0.0F, -0.136659280431156F, 0.0F, -0.17453292519943295F));
        PartDefinition p_lShoulderGuard = p_lArmUpper.addOrReplaceChild("lShoulderGuard",
                CubeListBuilder.create().texOffs(86, 52).addBox(0.5F, -1.5F, -2.5F, 3.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.10471975511965977F));
        PartDefinition p_lArmLower = p_lArmUpper.addOrReplaceChild("lArmLower",
                CubeListBuilder.create().texOffs(103, 42).addBox(1.0F, 5.0F, -1.0F, 3.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.13962634015954636F, 0.0F, 0.17453292519943295F));
        PartDefinition p_lHand = p_lArmUpper.addOrReplaceChild("lHand",
                CubeListBuilder.create().texOffs(120, 57).addBox(0.9F, 11.1F, -1.0F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.13962634015954636F, 0.0F, 0.17453292519943295F));
        PartDefinition p_rThigh = root.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(155, 0).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 9.0F, 5.0F),
                PartPose.offsetAndRotation(-2.0F, 9.5F, 4.3F, -0.40980330836826856F, 0.0F, 0.06981317007977318F));
        PartDefinition p_rShin = p_rThigh.addOrReplaceChild("rShin",
                CubeListBuilder.create().texOffs(155, 7).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 7.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.40980330836826856F, 0.0F, -0.06981317007977318F));
        PartDefinition p_rFootSole = p_rShin.addOrReplaceChild("rFootSole",
                CubeListBuilder.create().texOffs(194, 0).mirror().addBox(-2.0F, 6.2F, -2.5F, 4.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(103, 13).addBox(-4.5F, -4.0F, -2.5F, 9.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 2.5F, 0.0F, 0.4553564018453205F, 0.0F, 0.0F));
        PartDefinition p_face = p_body.addOrReplaceChild("face",
                CubeListBuilder.create().texOffs(103, 0).addBox(-3.5F, -7.5F, -4.6F, 7.0F, 4.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -4.1F, 0.0F, -0.5462880558742251F, 0.0F, 0.0F));
        PartDefinition p_lDred3 = p_face.addOrReplaceChild("lDred3",
                CubeListBuilder.create().texOffs(60, 110).addBox(3.0F, -8.5F, 0.5F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, -0.15707963267948966F, 0.0F));
        PartDefinition p_rDred5 = p_face.addOrReplaceChild("rDred5",
                CubeListBuilder.create().texOffs(43, 77).addBox(-10.0F, -9.4F, 3.5F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, 0.19198621771937624F, 0.0F));
        PartDefinition p_rDred4 = p_face.addOrReplaceChild("rDred4",
                CubeListBuilder.create().texOffs(28, 77).addBox(-10.0F, -9.0F, 2.0F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, 0.19198621771937624F, 0.0F));
        PartDefinition p_rDred2 = p_face.addOrReplaceChild("rDred2",
                CubeListBuilder.create().texOffs(0, 77).addBox(-8.0F, -8.0F, -0.5F, 5.0F, 8.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, 0.15707963267948966F, 0.0F));
        PartDefinition p_lMandibleLower1 = p_face.addOrReplaceChild("lMandibleLower1",
                CubeListBuilder.create().texOffs(59, 22).addBox(0.5F, 0.0F, -7.0F, 1.0F, 1.0F, 6.0F),
                PartPose.offsetAndRotation(0.6F, -1.7F, 1.9F, 0.31869712141416456F, -0.27314402793711257F, -0.2537708732399755F));
        PartDefinition p_lMandibleFangLower = p_lMandibleLower1.addOrReplaceChild("lMandibleFangLower",
                CubeListBuilder.create().texOffs(85, 25).addBox(1.0F, -4.5F, -7.1F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lMandibleLower2 = p_lMandibleLower1.addOrReplaceChild("lMandibleLower2",
                CubeListBuilder.create().texOffs(80, 30).addBox(0.5F, -2.6F, -7.1F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lMandibleLower1b = p_lMandibleLower1.addOrReplaceChild("lMandibleLower1b",
                CubeListBuilder.create().texOffs(59, 22).addBox(0.5F, 0.0F, -7.0F, 1.0F, 1.0F, 7.0F),
                PartPose.offsetAndRotation(0.6F, 0.0F, -0.5F, 0.0F, 0.27314402793711257F, 0.0F));
        PartDefinition p_lMandibleFangLowerb = p_lMandibleLower1b.addOrReplaceChild("lMandibleFangLowerb",
                CubeListBuilder.create().texOffs(85, 25).addBox(1.0F, -4.5F, -7.1F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lMandibleLower2b = p_lMandibleLower1b.addOrReplaceChild("lMandibleLower2b",
                CubeListBuilder.create().texOffs(80, 30).addBox(0.5F, -2.6F, -7.1F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lMandibleLower1c = p_lMandibleLower1b.addOrReplaceChild("lMandibleLower1c",
                CubeListBuilder.create().texOffs(59, 20).addBox(0.5F, 0.0F, -7.0F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(-0.1F, 0.0F, -0.5F, 0.0F, 0.18203784098300857F, 0.0F));
        PartDefinition p_lMandibleFangLowerc = p_lMandibleLower1c.addOrReplaceChild("lMandibleFangLowerc",
                CubeListBuilder.create().texOffs(85, 25).addBox(1.0F, -4.5F, -7.1F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lMandibleLower2c = p_lMandibleLower1c.addOrReplaceChild("lMandibleLower2c",
                CubeListBuilder.create().texOffs(80, 30).addBox(0.5F, -2.6F, -7.1F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_bDred2 = p_face.addOrReplaceChild("bDred2",
                CubeListBuilder.create().texOffs(38, 89).addBox(2.0F, -9.5F, 6.5F, 0.0F, 10.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.10471975511965977F, 0.0F));
        PartDefinition p_lDred1 = p_face.addOrReplaceChild("lDred1",
                CubeListBuilder.create().texOffs(88, 110).addBox(3.0F, -8.0F, -1.5F, 5.0F, 8.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, -0.06981317007977318F, 0.0F));
        PartDefinition p_lInnerMouth = p_face.addOrReplaceChild("lInnerMouth",
                CubeListBuilder.create().texOffs(56, 49).addBox(-1.0F, -5.0F, 0.8F, 3.0F, 4.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.5410520681182421F, 0.0F));
        PartDefinition p_rDred7 = p_face.addOrReplaceChild("rDred7",
                CubeListBuilder.create().texOffs(73, 77).addBox(-11.0F, -10.0F, 5.2F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.5235987755982988F, 0.0F));
        PartDefinition p_rInnerMouth = p_face.addOrReplaceChild("rInnerMouth",
                CubeListBuilder.create().texOffs(47, 49).addBox(-2.0F, -5.0F, 0.8F, 3.0F, 4.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, -0.5410520681182421F, 0.0F));
        PartDefinition p_bDred3 = p_face.addOrReplaceChild("bDred3",
                CubeListBuilder.create().texOffs(57, 89).addBox(1.0F, -9.7F, 6.7F, 0.0F, 10.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.06981317007977318F, 0.0F));
        PartDefinition p_rCheek = p_face.addOrReplaceChild("rCheek",
                CubeListBuilder.create().texOffs(7, 37).addBox(-2.4F, -4.0F, -2.8F, 0.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.0F, 0.0F));
        PartDefinition p_headTop = p_face.addOrReplaceChild("headTop",
                CubeListBuilder.create().texOffs(19, 43).addBox(-3.0F, -6.2F, -9.5F, 6.0F, 7.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.1838568316277536F, 0.0F, 0.0F));
        PartDefinition p_headTop3 = p_headTop.addOrReplaceChild("headTop3",
                CubeListBuilder.create().texOffs(194, 38).addBox(-3.0F, -8.4F, -11.0F, 6.0F, 6.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_bDred4 = p_face.addOrReplaceChild("bDred4",
                CubeListBuilder.create().texOffs(0, 89).addBox(0.0F, -9.9F, 7.0F, 0.0F, 10.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.0F, 0.0F));
        PartDefinition p_rDred3 = p_face.addOrReplaceChild("rDred3",
                CubeListBuilder.create().texOffs(13, 77).addBox(-9.0F, -8.5F, 0.5F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, 0.15707963267948966F, 0.0F));
        PartDefinition p_neck2 = p_face.addOrReplaceChild("neck2",
                CubeListBuilder.create().texOffs(69, 59).addBox(-2.5F, -4.0F, 1.0F, 5.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.0F, 0.0F));
        PartDefinition p_nose = p_face.addOrReplaceChild("nose",
                CubeListBuilder.create().texOffs(48, 1).addBox(-1.0F, -4.5F, -5.0F, 2.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_teethUpper = p_nose.addOrReplaceChild("teethUpper",
                CubeListBuilder.create().texOffs(80, 34).addBox(-1.0F, -3.5F, -5.0F, 2.0F, 1.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_jawLower = p_face.addOrReplaceChild("jawLower",
                CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, -0.5F, -2.7F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -1.6F, -2.0F, 0.5462880558742251F, 0.0F, 0.0F));
        PartDefinition p_teethLower = p_jawLower.addOrReplaceChild("teethLower",
                CubeListBuilder.create().texOffs(80, 36).addBox(-1.0F, -1.5F, -2.5F, 2.0F, 2.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lJaw = p_jawLower.addOrReplaceChild("lJaw",
                CubeListBuilder.create().texOffs(65, 0).addBox(0.3F, -0.5F, -2.5F, 1.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.12217304763960307F, 0.0F));
        PartDefinition p_rJaw = p_jawLower.addOrReplaceChild("rJaw",
                CubeListBuilder.create().texOffs(33, 0).addBox(-1.3F, -0.5F, -2.5F, 1.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.12217304763960307F, 0.0F));
        PartDefinition p_lCheek = p_face.addOrReplaceChild("lCheek",
                CubeListBuilder.create().texOffs(7, 29).addBox(2.4F, -4.0F, -2.8F, 0.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.0F, 0.0F));
        PartDefinition p_headUpperLower = p_face.addOrReplaceChild("headUpperLower",
                CubeListBuilder.create().texOffs(0, 45).addBox(-3.5F, -7.2F, -7.8F, 7.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.1838568316277536F, 0.0F, 0.0F));
        PartDefinition p_rDred6 = p_face.addOrReplaceChild("rDred6",
                CubeListBuilder.create().texOffs(58, 77).addBox(-10.0F, -9.8F, 5.0F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.08726646259971647F, 0.20943951023931953F, 0.0F));
        PartDefinition p_lDred2 = p_face.addOrReplaceChild("lDred2",
                CubeListBuilder.create().texOffs(75, 110).addBox(3.0F, -8.0F, -0.5F, 5.0F, 8.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, -0.15707963267948966F, 0.0F));
        PartDefinition p_rMandibleLower1 = p_face.addOrReplaceChild("rMandibleLower1",
                CubeListBuilder.create().texOffs(59, 10).addBox(-0.5F, 0.0F, -7.0F, 1.0F, 1.0F, 6.0F),
                PartPose.offsetAndRotation(-1.6F, -1.9F, 2.0F, 0.31869712141416456F, 0.27314402793711257F, 0.2537708732399755F));
        PartDefinition p_rMandibleLower1b = p_rMandibleLower1.addOrReplaceChild("rMandibleLower1b",
                CubeListBuilder.create().texOffs(59, 10).addBox(-0.5F, 0.0F, -7.0F, 1.0F, 1.0F, 7.0F),
                PartPose.offsetAndRotation(-0.5F, 0.0F, -0.7F, 0.0F, -0.27314402793711257F, 0.0F));
        PartDefinition p_rMandibleLower2b = p_rMandibleLower1b.addOrReplaceChild("rMandibleLower2b",
                CubeListBuilder.create().texOffs(80, 25).addBox(-0.5F, -2.6F, -7.1F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rMandibleFangLowerb = p_rMandibleLower1b.addOrReplaceChild("rMandibleFangLowerb",
                CubeListBuilder.create().texOffs(85, 30).addBox(0.0F, -4.5F, -7.1F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rMandibleLower1c = p_rMandibleLower1b.addOrReplaceChild("rMandibleLower1c",
                CubeListBuilder.create().texOffs(59, 10).addBox(-0.5F, 0.0F, -7.0F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.1F, 0.0F, -0.5F, 0.0F, -0.18203784098300857F, 0.0F));
        PartDefinition p_rMandibleLower2c = p_rMandibleLower1c.addOrReplaceChild("rMandibleLower2c",
                CubeListBuilder.create().texOffs(80, 25).addBox(-0.5F, -2.6F, -7.1F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rMandibleFangLowerc = p_rMandibleLower1c.addOrReplaceChild("rMandibleFangLowerc",
                CubeListBuilder.create().texOffs(85, 30).addBox(0.0F, -4.5F, -7.1F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rMandibleLower2 = p_rMandibleLower1.addOrReplaceChild("rMandibleLower2",
                CubeListBuilder.create().texOffs(80, 25).addBox(-0.5F, -2.6F, -7.1F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rMandibleFangLower = p_rMandibleLower1.addOrReplaceChild("rMandibleFangLower",
                CubeListBuilder.create().texOffs(85, 30).addBox(0.0F, -4.5F, -7.1F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rDred1 = p_face.addOrReplaceChild("rDred1",
                CubeListBuilder.create().texOffs(25, 19).addBox(-8.0F, -8.0F, -1.5F, 5.0F, 8.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, 0.06981317007977318F, 0.0F));
        PartDefinition p_rMandibleUpper = p_face.addOrReplaceChild("rMandibleUpper",
                CubeListBuilder.create().texOffs(38, 22).addBox(-1.5F, -0.5F, -7.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.2F, -4.3F, -0.8F, 0.18203784098300857F, 0.4553564018453205F, 0.05777039824101231F));
        PartDefinition p_rMandibleUpperb = p_rMandibleUpper.addOrReplaceChild("rMandibleUpperb",
                CubeListBuilder.create().texOffs(38, 22).addBox(-1.5F, -0.5F, -7.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(-0.7F, -0.2F, 0.9F, 0.0F, -0.27314402793711257F, 0.0F));
        PartDefinition p_rMandibleFangUpperb = p_rMandibleUpperb.addOrReplaceChild("rMandibleFangUpperb",
                CubeListBuilder.create().texOffs(90, 37).addBox(0.2F, 0.5F, -6.7F, 2.0F, 1.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F));
        PartDefinition p_rMandibleUpperc = p_rMandibleUpperb.addOrReplaceChild("rMandibleUpperc",
                CubeListBuilder.create().texOffs(38, 22).addBox(-1.5F, -0.5F, -7.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(-0.7F, -0.2F, 0.3F, 0.0F, -0.27314402793711257F, 0.0F));
        PartDefinition p_rMandibleFangUpperc = p_rMandibleUpperc.addOrReplaceChild("rMandibleFangUpperc",
                CubeListBuilder.create().texOffs(90, 37).addBox(0.2F, 0.5F, -6.7F, 2.0F, 1.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F));
        PartDefinition p_rMandibleFangUpper = p_rMandibleUpper.addOrReplaceChild("rMandibleFangUpper",
                CubeListBuilder.create().texOffs(90, 37).addBox(0.2F, 0.5F, -6.7F, 2.0F, 1.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F));
        PartDefinition p_headTop2 = p_face.addOrReplaceChild("headTop2",
                CubeListBuilder.create().texOffs(17, 30).mirror().addBox(-4.0F, -7.7F, -8.7F, 8.0F, 9.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.1838568316277536F, 0.0F, 0.0F));
        PartDefinition p_headBack2 = p_face.addOrReplaceChild("headBack2",
                CubeListBuilder.create().texOffs(40, 56).addBox(-3.0F, -10.0F, -6.1F, 6.0F, 7.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2292353921796064F, 0.0F, 0.0F));
        PartDefinition p_lMandibleUpper = p_face.addOrReplaceChild("lMandibleUpper",
                CubeListBuilder.create().texOffs(38, 10).addBox(-0.5F, -0.5F, -7.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.7F, -4.3F, -0.4F, 0.18203784098300857F, -0.4553564018453205F, -0.05777039824101231F));
        PartDefinition p_lMandibleFangUpper = p_lMandibleUpper.addOrReplaceChild("lMandibleFangUpper",
                CubeListBuilder.create().texOffs(85, 37).addBox(-2.2F, -0.5F, -6.7F, 2.0F, 1.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F));
        PartDefinition p_lMandibleUpperb = p_lMandibleUpper.addOrReplaceChild("lMandibleUpperb",
                CubeListBuilder.create().texOffs(38, 10).addBox(-0.5F, -0.5F, -7.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.7F, -0.2F, 0.9F, 0.0F, 0.27314402793711257F, 0.0F));
        PartDefinition p_lMandibleUpperc = p_lMandibleUpperb.addOrReplaceChild("lMandibleUpperc",
                CubeListBuilder.create().texOffs(38, 10).addBox(-0.5F, -0.5F, -7.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.7F, -0.2F, 0.3F, 0.0F, 0.27314402793711257F, 0.0F));
        PartDefinition p_lMandibleFangUpperc = p_lMandibleUpperc.addOrReplaceChild("lMandibleFangUpperc",
                CubeListBuilder.create().texOffs(85, 37).addBox(-2.2F, -0.5F, -6.7F, 2.0F, 1.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F));
        PartDefinition p_lMandibleFangUpperb = p_lMandibleUpperb.addOrReplaceChild("lMandibleFangUpperb",
                CubeListBuilder.create().texOffs(85, 37).addBox(-2.2F, -0.5F, -6.7F, 2.0F, 1.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F));
        PartDefinition p_lDred7 = p_face.addOrReplaceChild("lDred7",
                CubeListBuilder.create().texOffs(0, 110).addBox(5.0F, -10.0F, 5.2F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, -0.5235987755982988F, 0.0F));
        PartDefinition p_lDred6 = p_face.addOrReplaceChild("lDred6",
                CubeListBuilder.create().texOffs(15, 110).addBox(4.0F, -9.8F, 5.0F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.08726646259971647F, -0.20943951023931953F, 0.0F));
        PartDefinition p_lDred4 = p_face.addOrReplaceChild("lDred4",
                CubeListBuilder.create().texOffs(45, 110).addBox(4.0F, -9.0F, 2.0F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, -0.19198621771937624F, 0.0F));
        PartDefinition p_lDred5 = p_face.addOrReplaceChild("lDred5",
                CubeListBuilder.create().texOffs(30, 110).addBox(4.0F, -9.4F, 3.5F, 6.0F, 9.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, -0.19198621771937624F, 0.0F));
        PartDefinition p_bDred6 = p_face.addOrReplaceChild("bDred6",
                CubeListBuilder.create().texOffs(95, 89).addBox(-2.0F, -9.5F, 6.5F, 0.0F, 10.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, -0.10471975511965977F, 0.0F));
        PartDefinition p_bDred5 = p_face.addOrReplaceChild("bDred5",
                CubeListBuilder.create().texOffs(114, 89).addBox(-1.0F, -9.7F, 6.7F, 0.0F, 10.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.17453292519943295F, -0.06981317007977318F, 0.0F));
        PartDefinition p_lPec = p_body.addOrReplaceChild("lPec",
                CubeListBuilder.create().texOffs(80, 13).addBox(0.0F, -4.0F, -3.0F, 5.0F, 5.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.08726646259971647F));
        PartDefinition p_lBackSpike = p_body.addOrReplaceChild("lBackSpike",
                CubeListBuilder.create().texOffs(193, 0).addBox(0.0F, -1.0F, 2.4F, 0.0F, 5.0F, 9.0F),
                PartPose.offsetAndRotation(2.5F, -1.3F, 0.0F, -0.091106186954104F, 0.136659280431156F, 0.0F));
        PartDefinition p_spines = p_body.addOrReplaceChild("spines",
                CubeListBuilder.create().texOffs(218, 0).addBox(0.0F, -3.4F, 2.4F, 0.0F, 11.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rPec = p_body.addOrReplaceChild("rPec",
                CubeListBuilder.create().texOffs(80, 0).addBox(-5.0F, -4.0F, -3.0F, 5.0F, 5.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.08726646259971647F));
        PartDefinition p_rBackSpike = p_body.addOrReplaceChild("rBackSpike",
                CubeListBuilder.create().texOffs(193, 0).addBox(0.0F, -1.0F, 2.4F, 0.0F, 5.0F, 9.0F),
                PartPose.offsetAndRotation(-2.5F, -1.3F, 0.0F, -0.091106186954104F, -0.136659280431156F, 0.0F));
        PartDefinition p_neck = p_body.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(18, 10).addBox(-2.5F, -6.0F, -2.0F, 5.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
