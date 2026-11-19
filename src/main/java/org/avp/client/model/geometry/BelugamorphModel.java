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
 * Geometry converted automatically from the 1.12.2 ModelBelugamorph by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class BelugamorphModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "belugamorph"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart chest;
    public final ModelPart lQuillA;
    public final ModelPart lQuillB;
    public final ModelPart lQuillC;
    public final ModelPart lQuillD;
    public final ModelPart lArmUpper;
    public final ModelPart lUpperArm;
    public final ModelPart lForearm;
    public final ModelPart lArmjoint;
    public final ModelPart lHand;
    public final ModelPart lFinger04;
    public final ModelPart lFinger05;
    public final ModelPart lFinger03;
    public final ModelPart lFinger01;
    public final ModelPart lFinger02;
    public final ModelPart ribs;
    public final ModelPart rHip;
    public final ModelPart lHip;
    public final ModelPart stomach;
    public final ModelPart lThigh;
    public final ModelPart lThigh2;
    public final ModelPart lShin;
    public final ModelPart lFoot;
    public final ModelPart lToes01;
    public final ModelPart lToes02;
    public final ModelPart rThigh;
    public final ModelPart rThigh2;
    public final ModelPart rShin;
    public final ModelPart rFoot;
    public final ModelPart rToes;
    public final ModelPart rToes02;
    public final ModelPart quill1A;
    public final ModelPart quill1B;
    public final ModelPart quill1D;
    public final ModelPart quill1C;
    public final ModelPart spines;
    public final ModelPart neck;
    public final ModelPart headBase;
    public final ModelPart headLower1;
    public final ModelPart head2;
    public final ModelPart head3;
    public final ModelPart head4;
    public final ModelPart headLower3;
    public final ModelPart headLower2;
    public final ModelPart lFaceBubble;
    public final ModelPart mFaceBubble;
    public final ModelPart mFaceBubble2a;
    public final ModelPart mFaceBubble2b;
    public final ModelPart headSlope;
    public final ModelPart rFaceBubble;
    public final ModelPart upperJaw0;
    public final ModelPart upperJaw;
    public final ModelPart innerJaw02;
    public final ModelPart lowerJaw;
    public final ModelPart lowerJaw2;
    public final ModelPart lowerTeeth;
    public final ModelPart rQuillA;
    public final ModelPart rQuillB;
    public final ModelPart rQuillC;
    public final ModelPart rQuillD;
    public final ModelPart rArmUpper;
    public final ModelPart rUpperArm;
    public final ModelPart rForearm;
    public final ModelPart rHand;
    public final ModelPart rFinger04;
    public final ModelPart rFinger05;
    public final ModelPart rFinger01;
    public final ModelPart rFinger02;
    public final ModelPart rFinger03;
    public final ModelPart rArmjoint;
    public final ModelPart quill2C;
    public final ModelPart quill2A;
    public final ModelPart quill2B;

    public BelugamorphModel(ModelPart root)
    {
        this.root = root;
        this.chest = root.getChild("chest");
        this.parts.put("chest", this.chest);
        this.lQuillA = this.chest.getChild("lQuillA");
        this.parts.put("lQuillA", this.lQuillA);
        this.lQuillB = this.lQuillA.getChild("lQuillB");
        this.parts.put("lQuillB", this.lQuillB);
        this.lQuillC = this.lQuillB.getChild("lQuillC");
        this.parts.put("lQuillC", this.lQuillC);
        this.lQuillD = this.lQuillC.getChild("lQuillD");
        this.parts.put("lQuillD", this.lQuillD);
        this.lArmUpper = this.chest.getChild("lArmUpper");
        this.parts.put("lArmUpper", this.lArmUpper);
        this.lUpperArm = this.lArmUpper.getChild("lUpperArm");
        this.parts.put("lUpperArm", this.lUpperArm);
        this.lForearm = this.lUpperArm.getChild("lForearm");
        this.parts.put("lForearm", this.lForearm);
        this.lArmjoint = this.lForearm.getChild("lArmjoint");
        this.parts.put("lArmjoint", this.lArmjoint);
        this.lHand = this.lForearm.getChild("lHand");
        this.parts.put("lHand", this.lHand);
        this.lFinger04 = this.lHand.getChild("lFinger04");
        this.parts.put("lFinger04", this.lFinger04);
        this.lFinger05 = this.lHand.getChild("lFinger05");
        this.parts.put("lFinger05", this.lFinger05);
        this.lFinger03 = this.lHand.getChild("lFinger03");
        this.parts.put("lFinger03", this.lFinger03);
        this.lFinger01 = this.lHand.getChild("lFinger01");
        this.parts.put("lFinger01", this.lFinger01);
        this.lFinger02 = this.lHand.getChild("lFinger02");
        this.parts.put("lFinger02", this.lFinger02);
        this.ribs = this.chest.getChild("ribs");
        this.parts.put("ribs", this.ribs);
        this.rHip = this.ribs.getChild("rHip");
        this.parts.put("rHip", this.rHip);
        this.lHip = this.ribs.getChild("lHip");
        this.parts.put("lHip", this.lHip);
        this.stomach = this.ribs.getChild("stomach");
        this.parts.put("stomach", this.stomach);
        this.lThigh = this.stomach.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lThigh2 = this.lThigh.getChild("lThigh2");
        this.parts.put("lThigh2", this.lThigh2);
        this.lShin = this.lThigh.getChild("lShin");
        this.parts.put("lShin", this.lShin);
        this.lFoot = this.lShin.getChild("lFoot");
        this.parts.put("lFoot", this.lFoot);
        this.lToes01 = this.lFoot.getChild("lToes01");
        this.parts.put("lToes01", this.lToes01);
        this.lToes02 = this.lToes01.getChild("lToes02");
        this.parts.put("lToes02", this.lToes02);
        this.rThigh = this.stomach.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.rThigh2 = this.rThigh.getChild("rThigh2");
        this.parts.put("rThigh2", this.rThigh2);
        this.rShin = this.rThigh.getChild("rShin");
        this.parts.put("rShin", this.rShin);
        this.rFoot = this.rShin.getChild("rFoot");
        this.parts.put("rFoot", this.rFoot);
        this.rToes = this.rFoot.getChild("rToes");
        this.parts.put("rToes", this.rToes);
        this.rToes02 = this.rToes.getChild("rToes02");
        this.parts.put("rToes02", this.rToes02);
        this.quill1A = this.chest.getChild("quill1A");
        this.parts.put("quill1A", this.quill1A);
        this.quill1B = this.quill1A.getChild("quill1B");
        this.parts.put("quill1B", this.quill1B);
        this.quill1D = this.quill1B.getChild("quill1D");
        this.parts.put("quill1D", this.quill1D);
        this.quill1C = this.quill1B.getChild("quill1C");
        this.parts.put("quill1C", this.quill1C);
        this.spines = this.chest.getChild("spines");
        this.parts.put("spines", this.spines);
        this.neck = this.chest.getChild("neck");
        this.parts.put("neck", this.neck);
        this.headBase = this.neck.getChild("headBase");
        this.parts.put("headBase", this.headBase);
        this.headLower1 = this.headBase.getChild("headLower1");
        this.parts.put("headLower1", this.headLower1);
        this.head2 = this.headBase.getChild("head2");
        this.parts.put("head2", this.head2);
        this.head3 = this.head2.getChild("head3");
        this.parts.put("head3", this.head3);
        this.head4 = this.head3.getChild("head4");
        this.parts.put("head4", this.head4);
        this.headLower3 = this.head4.getChild("headLower3");
        this.parts.put("headLower3", this.headLower3);
        this.headLower2 = this.head3.getChild("headLower2");
        this.parts.put("headLower2", this.headLower2);
        this.lFaceBubble = this.headBase.getChild("lFaceBubble");
        this.parts.put("lFaceBubble", this.lFaceBubble);
        this.mFaceBubble = this.lFaceBubble.getChild("mFaceBubble");
        this.parts.put("mFaceBubble", this.mFaceBubble);
        this.mFaceBubble2a = this.mFaceBubble.getChild("mFaceBubble2a");
        this.parts.put("mFaceBubble2a", this.mFaceBubble2a);
        this.mFaceBubble2b = this.mFaceBubble2a.getChild("mFaceBubble2b");
        this.parts.put("mFaceBubble2b", this.mFaceBubble2b);
        this.headSlope = this.lFaceBubble.getChild("headSlope");
        this.parts.put("headSlope", this.headSlope);
        this.rFaceBubble = this.lFaceBubble.getChild("rFaceBubble");
        this.parts.put("rFaceBubble", this.rFaceBubble);
        this.upperJaw0 = this.lFaceBubble.getChild("upperJaw0");
        this.parts.put("upperJaw0", this.upperJaw0);
        this.upperJaw = this.upperJaw0.getChild("upperJaw");
        this.parts.put("upperJaw", this.upperJaw);
        this.innerJaw02 = this.upperJaw.getChild("innerJaw02");
        this.parts.put("innerJaw02", this.innerJaw02);
        this.lowerJaw = this.lFaceBubble.getChild("lowerJaw");
        this.parts.put("lowerJaw", this.lowerJaw);
        this.lowerJaw2 = this.lowerJaw.getChild("lowerJaw2");
        this.parts.put("lowerJaw2", this.lowerJaw2);
        this.lowerTeeth = this.lowerJaw.getChild("lowerTeeth");
        this.parts.put("lowerTeeth", this.lowerTeeth);
        this.rQuillA = this.chest.getChild("rQuillA");
        this.parts.put("rQuillA", this.rQuillA);
        this.rQuillB = this.rQuillA.getChild("rQuillB");
        this.parts.put("rQuillB", this.rQuillB);
        this.rQuillC = this.rQuillB.getChild("rQuillC");
        this.parts.put("rQuillC", this.rQuillC);
        this.rQuillD = this.rQuillC.getChild("rQuillD");
        this.parts.put("rQuillD", this.rQuillD);
        this.rArmUpper = this.chest.getChild("rArmUpper");
        this.parts.put("rArmUpper", this.rArmUpper);
        this.rUpperArm = this.rArmUpper.getChild("rUpperArm");
        this.parts.put("rUpperArm", this.rUpperArm);
        this.rForearm = this.rUpperArm.getChild("rForearm");
        this.parts.put("rForearm", this.rForearm);
        this.rHand = this.rForearm.getChild("rHand");
        this.parts.put("rHand", this.rHand);
        this.rFinger04 = this.rHand.getChild("rFinger04");
        this.parts.put("rFinger04", this.rFinger04);
        this.rFinger05 = this.rHand.getChild("rFinger05");
        this.parts.put("rFinger05", this.rFinger05);
        this.rFinger01 = this.rHand.getChild("rFinger01");
        this.parts.put("rFinger01", this.rFinger01);
        this.rFinger02 = this.rHand.getChild("rFinger02");
        this.parts.put("rFinger02", this.rFinger02);
        this.rFinger03 = this.rHand.getChild("rFinger03");
        this.parts.put("rFinger03", this.rFinger03);
        this.rArmjoint = this.rForearm.getChild("rArmjoint");
        this.parts.put("rArmjoint", this.rArmjoint);
        this.quill2C = root.getChild("quill2C");
        this.parts.put("quill2C", this.quill2C);
        this.quill2A = root.getChild("quill2A");
        this.parts.put("quill2A", this.quill2A);
        this.quill2B = this.quill2A.getChild("quill2B");
        this.parts.put("quill2B", this.quill2B);
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
                CubeListBuilder.create().texOffs(0, 10).addBox(-3.0F, -9.0F, -3.5F, 7.0F, 8.0F, 7.0F),
                PartPose.offsetAndRotation(-0.5F, -8.2F, 0.0F, -0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_lQuillA = p_chest.addOrReplaceChild("lQuillA",
                CubeListBuilder.create().texOffs(0, 50).addBox(-1.0F, -1.0F, -0.4F, 2.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(2.0F, -5.2F, 3.1F, -0.136659280431156F, 0.136659280431156F, 0.0F));
        PartDefinition p_lQuillB = p_lQuillA.addOrReplaceChild("lQuillB",
                CubeListBuilder.create().texOffs(13, 50).addBox(-0.5F, -1.0F, -0.4F, 1.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.1F, 3.4F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_lQuillC = p_lQuillB.addOrReplaceChild("lQuillC",
                CubeListBuilder.create().texOffs(24, 50).addBox(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.01F, 0.2F, 2.5F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_lQuillD = p_lQuillC.addOrReplaceChild("lQuillD",
                CubeListBuilder.create().texOffs(24, 57).addBox(-0.49F, -0.5F, -0.4F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.2F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_lArmUpper = p_chest.addOrReplaceChild("lArmUpper",
                CubeListBuilder.create().texOffs(87, 0).addBox(0.0F, -0.5F, -1.5F, 3.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(2.1F, -7.9F, 0.0F, 0.27314402793711257F, 0.0F, -0.36425021489121656F));
        PartDefinition p_lUpperArm = p_lArmUpper.addOrReplaceChild("lUpperArm",
                CubeListBuilder.create().texOffs(90, 10).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(1.8F, 3.1F, 0.0F, -0.136659280431156F, 0.0F, 0.136659280431156F));
        PartDefinition p_lForearm = p_lUpperArm.addOrReplaceChild("lForearm",
                CubeListBuilder.create().texOffs(85, 35).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 7.7F, 0.0F, -0.27314402793711257F, 0.0F, 0.091106186954104F));
        PartDefinition p_lArmjoint = p_lForearm.addOrReplaceChild("lArmjoint",
                CubeListBuilder.create().texOffs(102, 0).addBox(-0.9F, -0.5F, -0.4F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_lHand = p_lForearm.addOrReplaceChild("lHand",
                CubeListBuilder.create().texOffs(90, 23).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.3F, 7.6F, -0.5F, 0.27314402793711257F, -0.4553564018453205F, 0.0F));
        PartDefinition p_lFinger04 = p_lHand.addOrReplaceChild("lFinger04",
                CubeListBuilder.create().texOffs(92, 28).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 2.0F),
                PartPose.offset(-0.7F, 3.5F, 0.0F));
        PartDefinition p_lFinger05 = p_lHand.addOrReplaceChild("lFinger05",
                CubeListBuilder.create().texOffs(92, 28).addBox(0.0F, 0.0F, -0.8F, 0.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(-1.4F, 2.4F, -0.1F, 0.091106186954104F, 0.0F, 0.136659280431156F));
        PartDefinition p_lFinger03 = p_lHand.addOrReplaceChild("lFinger03",
                CubeListBuilder.create().texOffs(92, 28).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 3.5F, 0.0F));
        PartDefinition p_lFinger01 = p_lHand.addOrReplaceChild("lFinger01",
                CubeListBuilder.create().texOffs(92, 28).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 2.0F),
                PartPose.offset(1.3F, 3.5F, 0.0F));
        PartDefinition p_lFinger02 = p_lHand.addOrReplaceChild("lFinger02",
                CubeListBuilder.create().texOffs(92, 28).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 2.0F),
                PartPose.offset(0.7F, 3.5F, 0.0F));
        PartDefinition p_ribs = p_chest.addOrReplaceChild("ribs",
                CubeListBuilder.create().texOffs(21, 27).addBox(-2.6F, 0.0F, -3.0F, 6.0F, 2.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -1.4F, 0.2F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_rHip = p_ribs.addOrReplaceChild("rHip",
                CubeListBuilder.create().texOffs(68, 0).mirror().addBox(-1.0F, -1.5F, -2.5F, 1.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(-1.8F, 6.3F, -0.1F, 0.091106186954104F, 0.0F, -0.31869712141416456F));
        PartDefinition p_lHip = p_ribs.addOrReplaceChild("lHip",
                CubeListBuilder.create().texOffs(68, 0).addBox(0.0F, -1.5F, -2.5F, 1.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(2.8F, 6.3F, -0.1F, 0.091106186954104F, 0.0F, 0.31869712141416456F));
        PartDefinition p_stomach = p_ribs.addOrReplaceChild("stomach",
                CubeListBuilder.create().texOffs(0, 27).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 6.0F, 5.0F),
                PartPose.offsetAndRotation(0.5F, 1.8F, 0.0F, -0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_lThigh = p_stomach.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(37, 0).addBox(-2.1F, -0.5F, -2.0F, 3.0F, 14.0F, 4.0F),
                PartPose.offsetAndRotation(2.5F, 4.9F, 0.0F, -0.045553093477052F, 0.0F, -0.18203784098300857F));
        PartDefinition p_lThigh2 = p_lThigh.addOrReplaceChild("lThigh2",
                CubeListBuilder.create().texOffs(55, 0).addBox(-2.75F, -0.5F, -2.0F, 1.0F, 14.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lShin = p_lThigh.addOrReplaceChild("lShin",
                CubeListBuilder.create().texOffs(47, 19).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 11.0F, 3.0F),
                PartPose.offsetAndRotation(-0.8F, 13.2F, -0.1F, 0.31869712141416456F, 0.0F, 0.18203784098300857F));
        PartDefinition p_lFoot = p_lShin.addOrReplaceChild("lFoot",
                CubeListBuilder.create().texOffs(41, 36).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 9.7F, 0.0F, -0.6829473363053812F, -0.136659280431156F, 0.0F));
        PartDefinition p_lToes01 = p_lFoot.addOrReplaceChild("lToes01",
                CubeListBuilder.create().texOffs(38, 45).addBox(-1.5F, -1.0F, -2.8F, 3.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.1F, 4.3F, 0.5F, 0.40980330836826856F, 0.0F, 0.045553093477052F));
        PartDefinition p_lToes02 = p_lToes01.addOrReplaceChild("lToes02",
                CubeListBuilder.create().texOffs(45, 53).addBox(-1.5F, -0.4F, -2.0F, 3.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, -0.2F, -2.7F));
        PartDefinition p_rThigh = p_stomach.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(37, 0).mirror().addBox(-0.9F, -0.5F, -2.0F, 3.0F, 14.0F, 4.0F),
                PartPose.offsetAndRotation(-2.5F, 4.9F, 0.0F, -0.045553093477052F, 0.0F, 0.18203784098300857F));
        PartDefinition p_rThigh2 = p_rThigh.addOrReplaceChild("rThigh2",
                CubeListBuilder.create().texOffs(55, 0).mirror().addBox(1.75F, -0.5F, -2.0F, 1.0F, 14.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rShin = p_rThigh.addOrReplaceChild("rShin",
                CubeListBuilder.create().texOffs(47, 19).mirror().addBox(-1.5F, -0.5F, -1.5F, 3.0F, 11.0F, 3.0F),
                PartPose.offsetAndRotation(0.8F, 13.2F, -0.1F, 0.31869712141416456F, 0.0F, -0.18203784098300857F));
        PartDefinition p_rFoot = p_rShin.addOrReplaceChild("rFoot",
                CubeListBuilder.create().texOffs(41, 36).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 9.7F, 0.0F, -0.6829473363053812F, 0.136659280431156F, 0.0F));
        PartDefinition p_rToes = p_rFoot.addOrReplaceChild("rToes",
                CubeListBuilder.create().texOffs(38, 45).mirror().addBox(-1.5F, -1.0F, -2.8F, 3.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-0.1F, 4.3F, 0.5F, 0.40980330836826856F, 0.0F, -0.045553093477052F));
        PartDefinition p_rToes02 = p_rToes.addOrReplaceChild("rToes02",
                CubeListBuilder.create().texOffs(45, 53).mirror().addBox(-1.5F, -0.4F, -2.0F, 3.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, -0.2F, -2.7F));
        PartDefinition p_quill1A = p_chest.addOrReplaceChild("quill1A",
                CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, -1.5F, -0.4F, 2.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.5F, -7.7F, 3.0F, 0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_quill1B = p_quill1A.addOrReplaceChild("quill1B",
                CubeListBuilder.create().texOffs(13, 43).addBox(-0.5F, -1.0F, -0.4F, 1.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -0.2F, 3.4F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_quill1D = p_quill1B.addOrReplaceChild("quill1D",
                CubeListBuilder.create().texOffs(26, 37).addBox(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -0.3F, 3.4F, -0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_quill1C = p_quill1B.addOrReplaceChild("quill1C",
                CubeListBuilder.create().texOffs(24, 43).addBox(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.2F, 3.3F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_spines = p_chest.addOrReplaceChild("spines",
                CubeListBuilder.create().texOffs(37, 50).addBox(0.5F, 0.0F, 0.0F, 0.0F, 7.0F, 3.0F),
                PartPose.offset(0.0F, -4.9F, 2.3F));
        PartDefinition p_neck = p_chest.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.2F, -2.0F, 4.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.5F, -8.7F, 0.0F, 0.4553564018453205F, 0.0F, 0.0F));
        PartDefinition p_headBase = p_neck.addOrReplaceChild("headBase",
                CubeListBuilder.create().texOffs(60, 20).addBox(-2.5F, -4.6F, -4.5F, 5.0F, 4.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, -2.2F, 1.1F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_headLower1 = p_headBase.addOrReplaceChild("headLower1",
                CubeListBuilder.create().texOffs(20, 0).addBox(-1.5F, -1.1F, -0.7F, 3.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, -1.6F, 2.5F));
        PartDefinition p_head2 = p_headBase.addOrReplaceChild("head2",
                CubeListBuilder.create().texOffs(58, 34).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -2.9F, 2.2F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_head3 = p_head2.addOrReplaceChild("head3",
                CubeListBuilder.create().texOffs(59, 44).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -0.2F, 3.7F, -0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_head4 = p_head3.addOrReplaceChild("head4",
                CubeListBuilder.create().texOffs(59, 52).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 2.5F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_headLower3 = p_head4.addOrReplaceChild("headLower3",
                CubeListBuilder.create().texOffs(28, 14).addBox(-0.5F, -0.41F, -0.5F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 1.0F, 1.1F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_headLower2 = p_head3.addOrReplaceChild("headLower2",
                CubeListBuilder.create().texOffs(24, 7).addBox(-1.0F, -1.01F, -0.7F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.9F, 0.7F, 0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_lFaceBubble = p_headBase.addOrReplaceChild("lFaceBubble",
                CubeListBuilder.create().texOffs(112, 9).addBox(-2.85F, -2.1F, -3.0F, 4.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(1.5F, -3.4F, -3.2F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_mFaceBubble = p_lFaceBubble.addOrReplaceChild("mFaceBubble",
                CubeListBuilder.create().texOffs(106, 19).addBox(-4.0F, 0.3F, -6.05F, 5.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 1.9F, 1.5F, -0.7740535232594852F, 0.0F, 0.0F));
        PartDefinition p_mFaceBubble2a = p_mFaceBubble.addOrReplaceChild("mFaceBubble2a",
                CubeListBuilder.create().texOffs(101, 27).addBox(-3.8F, -1.1F, -6.5F, 4.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -1.2F, -0.45F, 0.7285004297824331F, 0.0F, 0.0F));
        PartDefinition p_mFaceBubble2b = p_mFaceBubble2a.addOrReplaceChild("mFaceBubble2b",
                CubeListBuilder.create().texOffs(117, 27).addBox(-0.2F, -1.1F, -6.5F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_headSlope = p_lFaceBubble.addOrReplaceChild("headSlope",
                CubeListBuilder.create().texOffs(104, 53).addBox(-2.5F, -0.8F, 0.0F, 5.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-1.5F, -1.4F, -1.6F, -0.6373942428283291F, 0.0F, 0.0F));
        PartDefinition p_rFaceBubble = p_lFaceBubble.addOrReplaceChild("rFaceBubble",
                CubeListBuilder.create().texOffs(101, 9).addBox(-4.15F, -2.1F, -3.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_upperJaw0 = p_lFaceBubble.addOrReplaceChild("upperJaw0",
                CubeListBuilder.create().texOffs(104, 36).addBox(-2.0F, -2.1F, -5.0F, 4.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(-1.5F, 3.6F, -0.4F, -0.5462880558742251F, 0.0F, 0.0F));
        PartDefinition p_upperJaw = p_upperJaw0.addOrReplaceChild("upperJaw",
                CubeListBuilder.create().texOffs(73, 50).addBox(-1.95F, -0.5F, -2.91F, 3.0F, 1.0F, 3.0F),
                PartPose.offset(0.3F, -0.3F, -1.9F));
        PartDefinition p_innerJaw02 = p_upperJaw.addOrReplaceChild("innerJaw02",
                CubeListBuilder.create().texOffs(87, 50).addBox(0.35F, -0.5F, -2.91F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lowerJaw = p_lFaceBubble.addOrReplaceChild("lowerJaw",
                CubeListBuilder.create().texOffs(94, 45).addBox(-1.4F, -0.1F, -3.0F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-1.9F, 2.3F, -1.8F, -0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lowerJaw2 = p_lowerJaw.addOrReplaceChild("lowerJaw2",
                CubeListBuilder.create().texOffs(110, 45).addBox(1.2F, -0.1F, -3.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lowerTeeth = p_lowerJaw.addOrReplaceChild("lowerTeeth",
                CubeListBuilder.create().texOffs(81, 57).addBox(-1.5F, -0.8F, -0.8F, 3.0F, 1.0F, 2.0F),
                PartPose.offset(0.4F, -0.3F, -2.1F));
        PartDefinition p_rQuillA = p_chest.addOrReplaceChild("rQuillA",
                CubeListBuilder.create().texOffs(0, 50).mirror().addBox(-1.0F, -1.0F, -0.4F, 2.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(-1.2F, -5.2F, 3.1F, -0.136659280431156F, -0.136659280431156F, 0.0F));
        PartDefinition p_rQuillB = p_rQuillA.addOrReplaceChild("rQuillB",
                CubeListBuilder.create().texOffs(13, 50).mirror().addBox(-0.5F, -1.0F, -0.4F, 1.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.1F, 3.4F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_rQuillC = p_rQuillB.addOrReplaceChild("rQuillC",
                CubeListBuilder.create().texOffs(24, 50).mirror().addBox(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-0.01F, 0.2F, 2.5F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_rQuillD = p_rQuillC.addOrReplaceChild("rQuillD",
                CubeListBuilder.create().texOffs(24, 57).mirror().addBox(-0.51F, -0.5F, -0.4F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.2F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_rArmUpper = p_chest.addOrReplaceChild("rArmUpper",
                CubeListBuilder.create().texOffs(87, 0).mirror().addBox(-3.0F, -0.5F, -1.5F, 3.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(-1.1F, -7.9F, 0.0F, 0.27314402793711257F, 0.0F, 0.36425021489121656F));
        PartDefinition p_rUpperArm = p_rArmUpper.addOrReplaceChild("rUpperArm",
                CubeListBuilder.create().texOffs(90, 10).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(-1.8F, 3.1F, 0.0F, -0.136659280431156F, 0.0F, -0.136659280431156F));
        PartDefinition p_rForearm = p_rUpperArm.addOrReplaceChild("rForearm",
                CubeListBuilder.create().texOffs(85, 35).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 7.7F, 0.0F, -0.27314402793711257F, 0.0F, -0.091106186954104F));
        PartDefinition p_rHand = p_rForearm.addOrReplaceChild("rHand",
                CubeListBuilder.create().texOffs(90, 23).mirror().addBox(-1.5F, 0.0F, -0.5F, 3.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(-0.3F, 7.6F, -0.5F, 0.27314402793711257F, 0.4553564018453205F, 0.0F));
        PartDefinition p_rFinger04 = p_rHand.addOrReplaceChild("rFinger04",
                CubeListBuilder.create().texOffs(92, 28).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 2.0F),
                PartPose.offset(0.7F, 3.5F, 0.0F));
        PartDefinition p_rFinger05 = p_rHand.addOrReplaceChild("rFinger05",
                CubeListBuilder.create().texOffs(92, 28).addBox(0.0F, 0.0F, -0.8F, 0.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(1.4F, 2.4F, -0.1F, 0.091106186954104F, 0.0F, -0.136659280431156F));
        PartDefinition p_rFinger01 = p_rHand.addOrReplaceChild("rFinger01",
                CubeListBuilder.create().texOffs(92, 28).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 2.0F),
                PartPose.offset(-1.3F, 3.5F, 0.0F));
        PartDefinition p_rFinger02 = p_rHand.addOrReplaceChild("rFinger02",
                CubeListBuilder.create().texOffs(92, 28).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 2.0F),
                PartPose.offset(-0.7F, 3.5F, 0.0F));
        PartDefinition p_rFinger03 = p_rHand.addOrReplaceChild("rFinger03",
                CubeListBuilder.create().texOffs(92, 28).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 3.5F, 0.0F));
        PartDefinition p_rArmjoint = p_rForearm.addOrReplaceChild("rArmjoint",
                CubeListBuilder.create().texOffs(102, 0).mirror().addBox(-1.1F, -0.5F, -0.4F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_quill2C = root.addOrReplaceChild("quill2C",
                CubeListBuilder.create().texOffs(24, 43).addBox(-0.5F, -0.5F, -0.4F, 1.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -17.8F, 3.2F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_quill2A = root.addOrReplaceChild("quill2A",
                CubeListBuilder.create().texOffs(1, 51).addBox(-1.0F, -1.0F, -0.4F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -17.5F, 2.2F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_quill2B = p_quill2A.addOrReplaceChild("quill2B",
                CubeListBuilder.create().texOffs(13, 50).addBox(-0.5F, -1.0F, -0.4F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.2F, 2.5F, -0.091106186954104F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
