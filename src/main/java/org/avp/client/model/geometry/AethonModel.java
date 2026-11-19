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
 * Geometry converted automatically from the 1.12.2 ModelAethon by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class AethonModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "aethon"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart rWingArm;
    public final ModelPart rWingArm2;
    public final ModelPart rWingArm3;
    public final ModelPart rWingMembrane2;
    public final ModelPart rWingArm4;
    public final ModelPart rWingArm5;
    public final ModelPart rWingArm6;
    public final ModelPart rWingMembrane3;
    public final ModelPart rWingMembrane1;
    public final ModelPart rArm;
    public final ModelPart rForearm;
    public final ModelPart rHand;
    public final ModelPart rThumb2;
    public final ModelPart rFinger1;
    public final ModelPart rFinger2;
    public final ModelPart rFinger4;
    public final ModelPart rFinger3;
    public final ModelPart rThumb1;
    public final ModelPart lWingArm;
    public final ModelPart lWingArm2;
    public final ModelPart lWingArm3;
    public final ModelPart lWingMembrane2;
    public final ModelPart lWingArm4;
    public final ModelPart lWingMembrane3;
    public final ModelPart lWingArm5;
    public final ModelPart lWingArm6;
    public final ModelPart lWingMembrane1;
    public final ModelPart lArm;
    public final ModelPart lForearm;
    public final ModelPart lHand;
    public final ModelPart lFinger4;
    public final ModelPart lFinger3;
    public final ModelPart lThumb1;
    public final ModelPart lFinger2;
    public final ModelPart lFinger1;
    public final ModelPart lThumb2;
    public final ModelPart chest;
    public final ModelPart rCollar;
    public final ModelPart rThigh;
    public final ModelPart rShin;
    public final ModelPart rshin2;
    public final ModelPart rFoot;
    public final ModelPart rToe3;
    public final ModelPart rToe2;
    public final ModelPart rToe1;
    public final ModelPart stomach;
    public final ModelPart tail1;
    public final ModelPart tail2;
    public final ModelPart tail3;
    public final ModelPart tail4;
    public final ModelPart tail5;
    public final ModelPart neck1;
    public final ModelPart neck2;
    public final ModelPart neck3;
    public final ModelPart neck4;
    public final ModelPart head1;
    public final ModelPart beakFang2;
    public final ModelPart beakFang4;
    public final ModelPart beakFang1;
    public final ModelPart headSlope;
    public final ModelPart beakFang3;
    public final ModelPart beakLower;
    public final ModelPart beakFangLower2;
    public final ModelPart beakFangLower1;
    public final ModelPart beakUpper;
    public final ModelPart lThigh;
    public final ModelPart lShin;
    public final ModelPart lshin2;
    public final ModelPart lFoot;
    public final ModelPart lToe2;
    public final ModelPart lToe3;
    public final ModelPart lToe1;
    public final ModelPart lCollar;

    public AethonModel(ModelPart root)
    {
        this.root = root;
        this.rWingArm = root.getChild("rWingArm");
        this.parts.put("rWingArm", this.rWingArm);
        this.rWingArm2 = this.rWingArm.getChild("rWingArm2");
        this.parts.put("rWingArm2", this.rWingArm2);
        this.rWingArm3 = this.rWingArm2.getChild("rWingArm3");
        this.parts.put("rWingArm3", this.rWingArm3);
        this.rWingMembrane2 = this.rWingArm3.getChild("rWingMembrane2");
        this.parts.put("rWingMembrane2", this.rWingMembrane2);
        this.rWingArm4 = this.rWingArm3.getChild("rWingArm4");
        this.parts.put("rWingArm4", this.rWingArm4);
        this.rWingArm5 = this.rWingArm4.getChild("rWingArm5");
        this.parts.put("rWingArm5", this.rWingArm5);
        this.rWingArm6 = this.rWingArm5.getChild("rWingArm6");
        this.parts.put("rWingArm6", this.rWingArm6);
        this.rWingMembrane3 = this.rWingArm4.getChild("rWingMembrane3");
        this.parts.put("rWingMembrane3", this.rWingMembrane3);
        this.rWingMembrane1 = this.rWingArm2.getChild("rWingMembrane1");
        this.parts.put("rWingMembrane1", this.rWingMembrane1);
        this.rArm = root.getChild("rArm");
        this.parts.put("rArm", this.rArm);
        this.rForearm = this.rArm.getChild("rForearm");
        this.parts.put("rForearm", this.rForearm);
        this.rHand = this.rForearm.getChild("rHand");
        this.parts.put("rHand", this.rHand);
        this.rThumb2 = this.rHand.getChild("rThumb2");
        this.parts.put("rThumb2", this.rThumb2);
        this.rFinger1 = this.rHand.getChild("rFinger1");
        this.parts.put("rFinger1", this.rFinger1);
        this.rFinger2 = this.rHand.getChild("rFinger2");
        this.parts.put("rFinger2", this.rFinger2);
        this.rFinger4 = this.rHand.getChild("rFinger4");
        this.parts.put("rFinger4", this.rFinger4);
        this.rFinger3 = this.rHand.getChild("rFinger3");
        this.parts.put("rFinger3", this.rFinger3);
        this.rThumb1 = this.rHand.getChild("rThumb1");
        this.parts.put("rThumb1", this.rThumb1);
        this.lWingArm = root.getChild("lWingArm");
        this.parts.put("lWingArm", this.lWingArm);
        this.lWingArm2 = this.lWingArm.getChild("lWingArm2");
        this.parts.put("lWingArm2", this.lWingArm2);
        this.lWingArm3 = this.lWingArm2.getChild("lWingArm3");
        this.parts.put("lWingArm3", this.lWingArm3);
        this.lWingMembrane2 = this.lWingArm3.getChild("lWingMembrane2");
        this.parts.put("lWingMembrane2", this.lWingMembrane2);
        this.lWingArm4 = this.lWingArm3.getChild("lWingArm4");
        this.parts.put("lWingArm4", this.lWingArm4);
        this.lWingMembrane3 = this.lWingArm4.getChild("lWingMembrane3");
        this.parts.put("lWingMembrane3", this.lWingMembrane3);
        this.lWingArm5 = this.lWingArm4.getChild("lWingArm5");
        this.parts.put("lWingArm5", this.lWingArm5);
        this.lWingArm6 = this.lWingArm5.getChild("lWingArm6");
        this.parts.put("lWingArm6", this.lWingArm6);
        this.lWingMembrane1 = this.lWingArm2.getChild("lWingMembrane1");
        this.parts.put("lWingMembrane1", this.lWingMembrane1);
        this.lArm = root.getChild("lArm");
        this.parts.put("lArm", this.lArm);
        this.lForearm = this.lArm.getChild("lForearm");
        this.parts.put("lForearm", this.lForearm);
        this.lHand = this.lForearm.getChild("lHand");
        this.parts.put("lHand", this.lHand);
        this.lFinger4 = this.lHand.getChild("lFinger4");
        this.parts.put("lFinger4", this.lFinger4);
        this.lFinger3 = this.lHand.getChild("lFinger3");
        this.parts.put("lFinger3", this.lFinger3);
        this.lThumb1 = this.lHand.getChild("lThumb1");
        this.parts.put("lThumb1", this.lThumb1);
        this.lFinger2 = this.lHand.getChild("lFinger2");
        this.parts.put("lFinger2", this.lFinger2);
        this.lFinger1 = this.lHand.getChild("lFinger1");
        this.parts.put("lFinger1", this.lFinger1);
        this.lThumb2 = this.lHand.getChild("lThumb2");
        this.parts.put("lThumb2", this.lThumb2);
        this.chest = root.getChild("chest");
        this.parts.put("chest", this.chest);
        this.rCollar = this.chest.getChild("rCollar");
        this.parts.put("rCollar", this.rCollar);
        this.rThigh = this.chest.getChild("rThigh");
        this.parts.put("rThigh", this.rThigh);
        this.rShin = this.rThigh.getChild("rShin");
        this.parts.put("rShin", this.rShin);
        this.rshin2 = this.rShin.getChild("rshin2");
        this.parts.put("rshin2", this.rshin2);
        this.rFoot = this.rshin2.getChild("rFoot");
        this.parts.put("rFoot", this.rFoot);
        this.rToe3 = this.rFoot.getChild("rToe3");
        this.parts.put("rToe3", this.rToe3);
        this.rToe2 = this.rFoot.getChild("rToe2");
        this.parts.put("rToe2", this.rToe2);
        this.rToe1 = this.rFoot.getChild("rToe1");
        this.parts.put("rToe1", this.rToe1);
        this.stomach = this.chest.getChild("stomach");
        this.parts.put("stomach", this.stomach);
        this.tail1 = this.chest.getChild("tail1");
        this.parts.put("tail1", this.tail1);
        this.tail2 = this.tail1.getChild("tail2");
        this.parts.put("tail2", this.tail2);
        this.tail3 = this.tail2.getChild("tail3");
        this.parts.put("tail3", this.tail3);
        this.tail4 = this.tail3.getChild("tail4");
        this.parts.put("tail4", this.tail4);
        this.tail5 = this.tail4.getChild("tail5");
        this.parts.put("tail5", this.tail5);
        this.neck1 = this.chest.getChild("neck1");
        this.parts.put("neck1", this.neck1);
        this.neck2 = this.neck1.getChild("neck2");
        this.parts.put("neck2", this.neck2);
        this.neck3 = this.neck2.getChild("neck3");
        this.parts.put("neck3", this.neck3);
        this.neck4 = this.neck3.getChild("neck4");
        this.parts.put("neck4", this.neck4);
        this.head1 = this.neck4.getChild("head1");
        this.parts.put("head1", this.head1);
        this.beakFang2 = this.head1.getChild("beakFang2");
        this.parts.put("beakFang2", this.beakFang2);
        this.beakFang4 = this.head1.getChild("beakFang4");
        this.parts.put("beakFang4", this.beakFang4);
        this.beakFang1 = this.head1.getChild("beakFang1");
        this.parts.put("beakFang1", this.beakFang1);
        this.headSlope = this.head1.getChild("headSlope");
        this.parts.put("headSlope", this.headSlope);
        this.beakFang3 = this.head1.getChild("beakFang3");
        this.parts.put("beakFang3", this.beakFang3);
        this.beakLower = this.head1.getChild("beakLower");
        this.parts.put("beakLower", this.beakLower);
        this.beakFangLower2 = this.beakLower.getChild("beakFangLower2");
        this.parts.put("beakFangLower2", this.beakFangLower2);
        this.beakFangLower1 = this.beakLower.getChild("beakFangLower1");
        this.parts.put("beakFangLower1", this.beakFangLower1);
        this.beakUpper = this.head1.getChild("beakUpper");
        this.parts.put("beakUpper", this.beakUpper);
        this.lThigh = this.chest.getChild("lThigh");
        this.parts.put("lThigh", this.lThigh);
        this.lShin = this.lThigh.getChild("lShin");
        this.parts.put("lShin", this.lShin);
        this.lshin2 = this.lShin.getChild("lshin2");
        this.parts.put("lshin2", this.lshin2);
        this.lFoot = this.lshin2.getChild("lFoot");
        this.parts.put("lFoot", this.lFoot);
        this.lToe2 = this.lFoot.getChild("lToe2");
        this.parts.put("lToe2", this.lToe2);
        this.lToe3 = this.lFoot.getChild("lToe3");
        this.parts.put("lToe3", this.lToe3);
        this.lToe1 = this.lFoot.getChild("lToe1");
        this.parts.put("lToe1", this.lToe1);
        this.lCollar = this.chest.getChild("lCollar");
        this.parts.put("lCollar", this.lCollar);
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
        PartDefinition p_rWingArm = root.addOrReplaceChild("rWingArm",
                CubeListBuilder.create().texOffs(0, 87).mirror().addBox(-2.9F, -4.3F, -10.5F, 3.0F, 5.0F, 13.0F),
                PartPose.offsetAndRotation(-3.5F, -21.3F, 5.7F, -0.7285004297824331F, 0.0F, -0.40980330836826856F));
        PartDefinition p_rWingArm2 = p_rWingArm.addOrReplaceChild("rWingArm2",
                CubeListBuilder.create().texOffs(34, 85).mirror().addBox(-1.0F, -1.5F, 0.0F, 2.0F, 4.0F, 16.0F),
                PartPose.offsetAndRotation(-1.4F, -2.7F, -9.3F, 0.40980330836826856F, -0.1795943800302165F, 0.22444934180647075F));
        PartDefinition p_rWingArm3 = p_rWingArm2.addOrReplaceChild("rWingArm3",
                CubeListBuilder.create().texOffs(74, 87).mirror().addBox(-1.0F, -1.5F, 0.0F, 2.0F, 4.0F, 13.0F),
                PartPose.offsetAndRotation(0.0F, 0.2F, 15.6F, -0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_rWingMembrane2 = p_rWingArm3.addOrReplaceChild("rWingMembrane2",
                CubeListBuilder.create().texOffs(206, 23).addBox(0.0F, 0.9F, 0.0F, 0.0F, 14.0F, 15.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rWingArm4 = p_rWingArm3.addOrReplaceChild("rWingArm4",
                CubeListBuilder.create().texOffs(107, 87).mirror().addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 14.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 12.0F, -0.22444934180647075F, 0.0F, 0.0F));
        PartDefinition p_rWingArm5 = p_rWingArm4.addOrReplaceChild("rWingArm5",
                CubeListBuilder.create().texOffs(115, 95).addBox(-0.5F, -0.9F, 0.0F, 1.0F, 2.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -0.2F, 13.6F, -0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rWingArm6 = p_rWingArm5.addOrReplaceChild("rWingArm6",
                CubeListBuilder.create().texOffs(114, 94).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 5.4F));
        PartDefinition p_rWingMembrane3 = p_rWingArm4.addOrReplaceChild("rWingMembrane3",
                CubeListBuilder.create().texOffs(206, 40).addBox(0.0F, 1.1F, 3.0F, 0.0F, 11.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rWingMembrane1 = p_rWingArm2.addOrReplaceChild("rWingMembrane1",
                CubeListBuilder.create().texOffs(206, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 14.0F, 18.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_rArm = root.addOrReplaceChild("rArm",
                CubeListBuilder.create().texOffs(89, 0).mirror().addBox(-1.9F, -0.3F, -2.0F, 3.0F, 13.0F, 4.0F),
                PartPose.offsetAndRotation(-4.1F, -18.0F, 2.0F, 0.5009094953223726F, 0.0F, 0.22759093446006054F));
        PartDefinition p_rForearm = p_rArm.addOrReplaceChild("rForearm",
                CubeListBuilder.create().texOffs(89, 19).mirror().addBox(-1.0F, -1.7F, -1.7F, 2.0F, 14.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 12.4F, 0.0F, -0.8196066167365371F, 0.0F, 0.0F));
        PartDefinition p_rHand = p_rForearm.addOrReplaceChild("rHand",
                CubeListBuilder.create().texOffs(89, 37).mirror().addBox(-2.0F, -0.4F, -0.6F, 4.0F, 5.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 12.2F, -1.1F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rThumb2 = p_rHand.addOrReplaceChild("rThumb2",
                CubeListBuilder.create().texOffs(106, 9).addBox(1.8F, 1.5F, -1.8F, 0.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, -0.136659280431156F));
        PartDefinition p_rFinger1 = p_rHand.addOrReplaceChild("rFinger1",
                CubeListBuilder.create().texOffs(106, 0).addBox(-1.4F, 3.2F, -2.8F, 0.0F, 6.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_rFinger2 = p_rHand.addOrReplaceChild("rFinger2",
                CubeListBuilder.create().texOffs(106, 0).addBox(-0.5F, 3.6F, -3.3F, 0.0F, 6.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_rFinger4 = p_rHand.addOrReplaceChild("rFinger4",
                CubeListBuilder.create().texOffs(106, 0).addBox(1.5F, 3.2F, -2.8F, 0.0F, 6.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_rFinger3 = p_rHand.addOrReplaceChild("rFinger3",
                CubeListBuilder.create().texOffs(106, 0).addBox(0.6F, 3.6F, -3.2F, 0.0F, 6.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_rThumb1 = p_rHand.addOrReplaceChild("rThumb1",
                CubeListBuilder.create().texOffs(106, 9).addBox(-1.8F, 1.5F, -1.8F, 0.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, 0.136659280431156F));
        PartDefinition p_lWingArm = root.addOrReplaceChild("lWingArm",
                CubeListBuilder.create().texOffs(0, 87).addBox(0.0F, -4.3F, -10.5F, 3.0F, 5.0F, 13.0F),
                PartPose.offsetAndRotation(3.5F, -21.3F, 5.7F, -0.7285004297824331F, 0.0F, 0.40980330836826856F));
        PartDefinition p_lWingArm2 = p_lWingArm.addOrReplaceChild("lWingArm2",
                CubeListBuilder.create().texOffs(34, 85).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 4.0F, 16.0F),
                PartPose.offsetAndRotation(1.4F, -2.7F, -9.3F, 0.40980330836826856F, 0.1795943800302165F, -0.22444934180647075F));
        PartDefinition p_lWingArm3 = p_lWingArm2.addOrReplaceChild("lWingArm3",
                CubeListBuilder.create().texOffs(74, 87).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 4.0F, 13.0F),
                PartPose.offsetAndRotation(0.0F, 0.2F, 15.6F, -0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_lWingMembrane2 = p_lWingArm3.addOrReplaceChild("lWingMembrane2",
                CubeListBuilder.create().texOffs(206, 23).addBox(0.0F, 0.9F, 0.0F, 0.0F, 14.0F, 15.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lWingArm4 = p_lWingArm3.addOrReplaceChild("lWingArm4",
                CubeListBuilder.create().texOffs(107, 87).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 14.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 12.0F, -0.22444934180647075F, 0.0F, 0.0F));
        PartDefinition p_lWingMembrane3 = p_lWingArm4.addOrReplaceChild("lWingMembrane3",
                CubeListBuilder.create().texOffs(206, 40).addBox(0.0F, 1.1F, 3.0F, 0.0F, 11.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lWingArm5 = p_lWingArm4.addOrReplaceChild("lWingArm5",
                CubeListBuilder.create().texOffs(115, 95).addBox(-0.5F, -0.9F, 0.0F, 1.0F, 2.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -0.2F, 13.6F, -0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lWingArm6 = p_lWingArm5.addOrReplaceChild("lWingArm6",
                CubeListBuilder.create().texOffs(114, 94).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 5.4F));
        PartDefinition p_lWingMembrane1 = p_lWingArm2.addOrReplaceChild("lWingMembrane1",
                CubeListBuilder.create().texOffs(206, 0).addBox(0.0F, -0.4F, -0.9F, 0.0F, 14.0F, 18.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_lArm = root.addOrReplaceChild("lArm",
                CubeListBuilder.create().texOffs(89, 0).addBox(-0.9F, -0.3F, -2.0F, 3.0F, 13.0F, 4.0F),
                PartPose.offsetAndRotation(4.1F, -18.0F, 2.0F, 0.5009094953223726F, 0.0F, -0.22759093446006054F));
        PartDefinition p_lForearm = p_lArm.addOrReplaceChild("lForearm",
                CubeListBuilder.create().texOffs(89, 19).addBox(-1.0F, -1.7F, -1.7F, 2.0F, 14.0F, 3.0F),
                PartPose.offsetAndRotation(0.8F, 12.4F, 0.0F, -0.8196066167365371F, 0.0F, 0.0F));
        PartDefinition p_lHand = p_lForearm.addOrReplaceChild("lHand",
                CubeListBuilder.create().texOffs(89, 37).addBox(-2.0F, -0.4F, -0.6F, 4.0F, 5.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 12.2F, -1.1F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lFinger4 = p_lHand.addOrReplaceChild("lFinger4",
                CubeListBuilder.create().texOffs(106, 0).addBox(-1.5F, 3.2F, -2.8F, 0.0F, 6.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_lFinger3 = p_lHand.addOrReplaceChild("lFinger3",
                CubeListBuilder.create().texOffs(106, 0).addBox(-0.6F, 3.6F, -3.2F, 0.0F, 6.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_lThumb1 = p_lHand.addOrReplaceChild("lThumb1",
                CubeListBuilder.create().texOffs(106, 9).addBox(1.8F, 1.5F, -1.8F, 0.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, -0.136659280431156F));
        PartDefinition p_lFinger2 = p_lHand.addOrReplaceChild("lFinger2",
                CubeListBuilder.create().texOffs(106, 0).addBox(0.5F, 3.6F, -3.3F, 0.0F, 6.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_lFinger1 = p_lHand.addOrReplaceChild("lFinger1",
                CubeListBuilder.create().texOffs(106, 0).addBox(1.4F, 3.2F, -2.8F, 0.0F, 6.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_lThumb2 = p_lHand.addOrReplaceChild("lThumb2",
                CubeListBuilder.create().texOffs(106, 9).addBox(-1.8F, 1.5F, -1.8F, 0.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, 0.136659280431156F));
        PartDefinition p_chest = root.addOrReplaceChild("chest",
                CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, 0.0F, -3.6F, 11.0F, 11.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -19.0F, 0.0F, 0.4553564018453205F, 0.0F, 0.0F));
        PartDefinition p_rCollar = p_chest.addOrReplaceChild("rCollar",
                CubeListBuilder.create().texOffs(0, 45).addBox(-6.1F, -3.5F, -3.6F, 2.0F, 5.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, -0.4553564018453205F));
        PartDefinition p_rThigh = p_chest.addOrReplaceChild("rThigh",
                CubeListBuilder.create().texOffs(116, 0).mirror().addBox(-3.3F, -0.5F, -3.3F, 5.0F, 14.0F, 6.0F),
                PartPose.offsetAndRotation(-1.9F, 19.6F, 0.5F, -0.7740535232594852F, 0.0F, 0.136659280431156F));
        PartDefinition p_rShin = p_rThigh.addOrReplaceChild("rShin",
                CubeListBuilder.create().texOffs(116, 24).mirror().addBox(-2.7F, 0.1F, -3.6F, 4.0F, 3.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 11.8F, 0.0F, 0.40980330836826856F, 0.0F, 0.0F));
        PartDefinition p_rshin2 = p_rShin.addOrReplaceChild("rshin2",
                CubeListBuilder.create().texOffs(122, 40).mirror().addBox(-2.2F, -0.4F, -1.3F, 3.0F, 11.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 2.9F, 3.5F, -0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_rFoot = p_rshin2.addOrReplaceChild("rFoot",
                CubeListBuilder.create().texOffs(116, 63).mirror().addBox(-2.8F, 0.5F, -5.8F, 4.0F, 2.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 9.6F, 0.0F, 0.091106186954104F, 0.136659280431156F, -0.091106186954104F));
        PartDefinition p_rToe3 = p_rFoot.addOrReplaceChild("rToe3",
                CubeListBuilder.create().texOffs(102, 16).addBox(0.0F, 0.0F, -10.5F, 0.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.045553093477052F, -0.18203784098300857F, 0.0F));
        PartDefinition p_rToe2 = p_rFoot.addOrReplaceChild("rToe2",
                CubeListBuilder.create().texOffs(102, 16).addBox(-0.7F, 0.0F, -10.8F, 0.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_rToe1 = p_rFoot.addOrReplaceChild("rToe1",
                CubeListBuilder.create().texOffs(102, 16).addBox(-1.4F, 0.0F, -10.5F, 0.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.045553093477052F, 0.18203784098300857F, 0.0F));
        PartDefinition p_stomach = p_chest.addOrReplaceChild("stomach",
                CubeListBuilder.create().texOffs(0, 23).addBox(-4.5F, 9.7F, 0.0F, 9.0F, 11.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_tail1 = p_chest.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(160, 0).addBox(-3.0F, -2.5F, 0.0F, 6.0F, 6.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, 19.8F, 1.3F, -1.2566370614359172F, 0.0F, 0.0F));
        PartDefinition p_tail2 = p_tail1.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(160, 20).addBox(-3.0F, -2.2F, 0.0F, 6.0F, 5.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, 0.3F, 10.6F, 0.22444934180647075F, 0.0F, 0.0F));
        PartDefinition p_tail3 = p_tail2.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(160, 40).addBox(-2.5F, -1.7F, 0.0F, 5.0F, 4.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 11.4F, 0.22444934180647075F, 0.0F, 0.0F));
        PartDefinition p_tail4 = p_tail3.addOrReplaceChild("tail4",
                CubeListBuilder.create().texOffs(160, 60).addBox(-2.5F, -1.9F, -0.6F, 5.0F, 4.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 9.2F, 0.22444934180647075F, 0.0F, 0.0F));
        PartDefinition p_tail5 = p_tail4.addOrReplaceChild("tail5",
                CubeListBuilder.create().texOffs(160, 78).addBox(-3.0F, -2.8F, 0.0F, 6.0F, 5.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 6.8F, 0.22444934180647075F, 0.0F, 0.0F));
        PartDefinition p_neck1 = p_chest.addOrReplaceChild("neck1",
                CubeListBuilder.create().texOffs(47, 0).addBox(-4.5F, -7.8F, -2.8F, 9.0F, 10.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.8F, 1.5F, 0.6373942428283291F, 0.0F, 0.0F));
        PartDefinition p_neck2 = p_neck1.addOrReplaceChild("neck2",
                CubeListBuilder.create().texOffs(47, 19).addBox(-4.0F, -7.6F, -2.9F, 8.0F, 9.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -7.5F, 0.8F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_neck3 = p_neck2.addOrReplaceChild("neck3",
                CubeListBuilder.create().texOffs(47, 38).addBox(-3.5F, -7.3F, -2.5F, 7.0F, 6.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -5.5F, 0.6F, 0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_neck4 = p_neck3.addOrReplaceChild("neck4",
                CubeListBuilder.create().texOffs(47, 52).addBox(-3.0F, -4.2F, -1.6F, 6.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -6.4F, 0.0F, 0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_head1 = p_neck4.addOrReplaceChild("head1",
                CubeListBuilder.create().texOffs(0, 65).addBox(-3.5F, -2.5F, -7.0F, 7.0F, 5.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, -3.5F, 0.4F, -1.3658946726107624F, 0.0F, 0.0F));
        PartDefinition p_beakFang2 = p_head1.addOrReplaceChild("beakFang2",
                CubeListBuilder.create().texOffs(31, 45).addBox(1.5F, -5.1F, -13.2F, 0.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4038691889114879F, 0.08970992355250852F, 0.0F));
        PartDefinition p_beakFang4 = p_head1.addOrReplaceChild("beakFang4",
                CubeListBuilder.create().texOffs(31, 41).addBox(-2.8F, -4.9F, -12.2F, 0.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4038691889114879F, -0.13456488532876282F, 0.0F));
        PartDefinition p_beakFang1 = p_head1.addOrReplaceChild("beakFang1",
                CubeListBuilder.create().texOffs(31, 41).addBox(2.8F, -4.9F, -12.2F, 0.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4038691889114879F, 0.13456488532876282F, 0.0F));
        PartDefinition p_headSlope = p_head1.addOrReplaceChild("headSlope",
                CubeListBuilder.create().texOffs(32, 75).addBox(-2.5F, -5.3F, -9.4F, 5.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.44872415068774213F, 0.0F, 0.0F));
        PartDefinition p_beakFang3 = p_head1.addOrReplaceChild("beakFang3",
                CubeListBuilder.create().texOffs(31, 45).addBox(-1.5F, -5.1F, -13.2F, 0.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4038691889114879F, -0.08970992355250852F, 0.0F));
        PartDefinition p_beakLower = p_head1.addOrReplaceChild("beakLower",
                CubeListBuilder.create().texOffs(51, 63).addBox(-1.5F, 0.0F, -3.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 1.2F, -6.5F, 0.22444934180647075F, 0.0F, 0.0F));
        PartDefinition p_beakFangLower2 = p_beakLower.addOrReplaceChild("beakFangLower2",
                CubeListBuilder.create().texOffs(31, 51).addBox(-0.8F, -0.3F, -6.7F, 0.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.08970992355250852F, 0.0F));
        PartDefinition p_beakFangLower1 = p_beakLower.addOrReplaceChild("beakFangLower1",
                CubeListBuilder.create().texOffs(31, 51).addBox(0.7F, -0.3F, -6.7F, 0.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.08970992355250852F, 0.0F));
        PartDefinition p_beakUpper = p_head1.addOrReplaceChild("beakUpper",
                CubeListBuilder.create().texOffs(32, 63).addBox(-2.0F, -2.3F, -10.6F, 4.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.22444934180647075F, 0.0F, 0.0F));
        PartDefinition p_lThigh = p_chest.addOrReplaceChild("lThigh",
                CubeListBuilder.create().texOffs(116, 0).addBox(-1.3F, -0.5F, -3.3F, 5.0F, 14.0F, 6.0F),
                PartPose.offsetAndRotation(1.7F, 19.6F, 0.5F, -0.7740535232594852F, 0.0F, -0.136659280431156F));
        PartDefinition p_lShin = p_lThigh.addOrReplaceChild("lShin",
                CubeListBuilder.create().texOffs(116, 24).addBox(-0.7F, 0.1F, -3.6F, 4.0F, 3.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 11.8F, 0.0F, 0.40980330836826856F, 0.0F, 0.0F));
        PartDefinition p_lshin2 = p_lShin.addOrReplaceChild("lshin2",
                CubeListBuilder.create().texOffs(122, 40).addBox(-1.0F, -0.4F, -1.3F, 3.0F, 11.0F, 3.0F),
                PartPose.offsetAndRotation(1.0F, 2.9F, 3.5F, -0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_lFoot = p_lshin2.addOrReplaceChild("lFoot",
                CubeListBuilder.create().texOffs(116, 63).addBox(-1.4F, 0.5F, -5.8F, 4.0F, 2.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 9.6F, 0.0F, 0.091106186954104F, -0.136659280431156F, 0.136659280431156F));
        PartDefinition p_lToe2 = p_lFoot.addOrReplaceChild("lToe2",
                CubeListBuilder.create().texOffs(102, 16).addBox(0.7F, 0.0F, -10.8F, 0.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_lToe3 = p_lFoot.addOrReplaceChild("lToe3",
                CubeListBuilder.create().texOffs(102, 16).addBox(0.0F, 0.0F, -10.5F, 0.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.045553093477052F, 0.18203784098300857F, 0.0F));
        PartDefinition p_lToe1 = p_lFoot.addOrReplaceChild("lToe1",
                CubeListBuilder.create().texOffs(102, 16).addBox(1.4F, 0.0F, -10.5F, 0.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.045553093477052F, -0.18203784098300857F, 0.0F));
        PartDefinition p_lCollar = p_chest.addOrReplaceChild("lCollar",
                CubeListBuilder.create().texOffs(0, 45).mirror().addBox(4.1F, -3.5F, -3.6F, 2.0F, 5.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F, 0.4553564018453205F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
