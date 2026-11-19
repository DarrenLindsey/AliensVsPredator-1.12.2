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
 * Geometry converted automatically from the 1.12.2 ModelUrsuidae by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class UrsuidaeModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "ursuidae"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart chest;
    public final ModelPart rArm1;
    public final ModelPart rArm2;
    public final ModelPart rFoot;
    public final ModelPart rHoofF;
    public final ModelPart rHoofFB;
    public final ModelPart neck;
    public final ModelPart stomach;
    public final ModelPart lLeg1;
    public final ModelPart lLeg2;
    public final ModelPart lLeg3;
    public final ModelPart lBHoofB;
    public final ModelPart lHoof;
    public final ModelPart rLeg1;
    public final ModelPart rLeg2;
    public final ModelPart rLeg3;
    public final ModelPart rBHoofB;
    public final ModelPart rHoof;
    public final ModelPart head1;
    public final ModelPart lWhiskers;
    public final ModelPart quills2;
    public final ModelPart quills1;
    public final ModelPart lLowerJaw2;
    public final ModelPart lowerJaw;
    public final ModelPart teethLower;
    public final ModelPart rTusk;
    public final ModelPart lTusk;
    public final ModelPart rlowerJaw2;
    public final ModelPart lJawTendons;
    public final ModelPart rJawTendons;
    public final ModelPart rWhiskers;
    public final ModelPart muzzle;
    public final ModelPart eyes;
    public final ModelPart teethUpper;
    public final ModelPart lArm1;
    public final ModelPart lArm2;
    public final ModelPart lFoot;
    public final ModelPart lHoofFB;
    public final ModelPart lHoofF;

    public UrsuidaeModel(ModelPart root)
    {
        this.root = root;
        this.chest = root.getChild("chest");
        this.parts.put("chest", this.chest);
        this.rArm1 = this.chest.getChild("rArm1");
        this.parts.put("rArm1", this.rArm1);
        this.rArm2 = this.rArm1.getChild("rArm2");
        this.parts.put("rArm2", this.rArm2);
        this.rFoot = this.rArm2.getChild("rFoot");
        this.parts.put("rFoot", this.rFoot);
        this.rHoofF = this.rFoot.getChild("rHoofF");
        this.parts.put("rHoofF", this.rHoofF);
        this.rHoofFB = this.rFoot.getChild("rHoofFB");
        this.parts.put("rHoofFB", this.rHoofFB);
        this.neck = this.chest.getChild("neck");
        this.parts.put("neck", this.neck);
        this.stomach = this.chest.getChild("stomach");
        this.parts.put("stomach", this.stomach);
        this.lLeg1 = this.stomach.getChild("lLeg1");
        this.parts.put("lLeg1", this.lLeg1);
        this.lLeg2 = this.lLeg1.getChild("lLeg2");
        this.parts.put("lLeg2", this.lLeg2);
        this.lLeg3 = this.lLeg2.getChild("lLeg3");
        this.parts.put("lLeg3", this.lLeg3);
        this.lBHoofB = this.lLeg3.getChild("lBHoofB");
        this.parts.put("lBHoofB", this.lBHoofB);
        this.lHoof = this.lLeg3.getChild("lHoof");
        this.parts.put("lHoof", this.lHoof);
        this.rLeg1 = this.stomach.getChild("rLeg1");
        this.parts.put("rLeg1", this.rLeg1);
        this.rLeg2 = this.rLeg1.getChild("rLeg2");
        this.parts.put("rLeg2", this.rLeg2);
        this.rLeg3 = this.rLeg2.getChild("rLeg3");
        this.parts.put("rLeg3", this.rLeg3);
        this.rBHoofB = this.rLeg3.getChild("rBHoofB");
        this.parts.put("rBHoofB", this.rBHoofB);
        this.rHoof = this.rLeg3.getChild("rHoof");
        this.parts.put("rHoof", this.rHoof);
        this.head1 = this.chest.getChild("head1");
        this.parts.put("head1", this.head1);
        this.lWhiskers = this.head1.getChild("lWhiskers");
        this.parts.put("lWhiskers", this.lWhiskers);
        this.quills2 = this.head1.getChild("quills2");
        this.parts.put("quills2", this.quills2);
        this.quills1 = this.head1.getChild("quills1");
        this.parts.put("quills1", this.quills1);
        this.lLowerJaw2 = this.head1.getChild("lLowerJaw2");
        this.parts.put("lLowerJaw2", this.lLowerJaw2);
        this.lowerJaw = this.lLowerJaw2.getChild("lowerJaw");
        this.parts.put("lowerJaw", this.lowerJaw);
        this.teethLower = this.lowerJaw.getChild("teethLower");
        this.parts.put("teethLower", this.teethLower);
        this.rTusk = this.lowerJaw.getChild("rTusk");
        this.parts.put("rTusk", this.rTusk);
        this.lTusk = this.lowerJaw.getChild("lTusk");
        this.parts.put("lTusk", this.lTusk);
        this.rlowerJaw2 = this.lLowerJaw2.getChild("rlowerJaw2");
        this.parts.put("rlowerJaw2", this.rlowerJaw2);
        this.lJawTendons = this.lLowerJaw2.getChild("lJawTendons");
        this.parts.put("lJawTendons", this.lJawTendons);
        this.rJawTendons = this.lLowerJaw2.getChild("rJawTendons");
        this.parts.put("rJawTendons", this.rJawTendons);
        this.rWhiskers = this.head1.getChild("rWhiskers");
        this.parts.put("rWhiskers", this.rWhiskers);
        this.muzzle = this.head1.getChild("muzzle");
        this.parts.put("muzzle", this.muzzle);
        this.eyes = this.head1.getChild("eyes");
        this.parts.put("eyes", this.eyes);
        this.teethUpper = this.head1.getChild("teethUpper");
        this.parts.put("teethUpper", this.teethUpper);
        this.lArm1 = this.chest.getChild("lArm1");
        this.parts.put("lArm1", this.lArm1);
        this.lArm2 = this.lArm1.getChild("lArm2");
        this.parts.put("lArm2", this.lArm2);
        this.lFoot = this.lArm2.getChild("lFoot");
        this.parts.put("lFoot", this.lFoot);
        this.lHoofFB = this.lFoot.getChild("lHoofFB");
        this.parts.put("lHoofFB", this.lHoofFB);
        this.lHoofF = this.lFoot.getChild("lHoofF");
        this.parts.put("lHoofF", this.lHoofF);
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
                CubeListBuilder.create().texOffs(29, 0).addBox(-3.5F, -2.0F, -5.0F, 7.0F, 6.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 13.5F, -4.0F, -0.03490658503988659F, 0.0F, 0.0F));
        PartDefinition p_rArm1 = p_chest.addOrReplaceChild("rArm1",
                CubeListBuilder.create().texOffs(58, 0).addBox(-2.0F, 0.0F, -1.0F, 3.0F, 6.0F, 3.0F),
                PartPose.offsetAndRotation(-2.5F, -0.4F, -3.0F, 0.2617993877991494F, 0.0F, 0.05235987755982988F));
        PartDefinition p_rArm2 = p_rArm1.addOrReplaceChild("rArm2",
                CubeListBuilder.create().texOffs(58, 10).addBox(-1.5F, 0.1F, -0.6F, 2.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, -0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_rFoot = p_rArm2.addOrReplaceChild("rFoot",
                CubeListBuilder.create().texOffs(58, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(-0.5F, 3.0F, 0.4F, 0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_rHoofF = p_rFoot.addOrReplaceChild("rHoofF",
                CubeListBuilder.create().texOffs(58, 21).addBox(-1.1F, 0.3F, -1.1F, 2.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rHoofFB = p_rFoot.addOrReplaceChild("rHoofFB",
                CubeListBuilder.create().texOffs(58, 26).addBox(-0.5F, 1.4F, -0.2F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_neck = p_chest.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(26, 31).addBox(-2.0F, -1.4F, -7.0F, 4.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.15707963267948966F, 0.0F, 0.0F));
        PartDefinition p_stomach = p_chest.addOrReplaceChild("stomach",
                CubeListBuilder.create().texOffs(31, 14).addBox(-3.0F, -1.5F, 1.8F, 6.0F, 5.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lLeg1 = p_stomach.addOrReplaceChild("lLeg1",
                CubeListBuilder.create().texOffs(106, 0).addBox(-1.0F, -1.5F, -1.5F, 3.0F, 7.0F, 4.0F),
                PartPose.offsetAndRotation(2.0F, 0.3F, 5.9F, -0.3665191429188092F, 0.0F, -0.12217304763960307F));
        PartDefinition p_lLeg2 = p_lLeg1.addOrReplaceChild("lLeg2",
                CubeListBuilder.create().texOffs(106, 13).addBox(-1.4F, 0.3F, 0.8F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.5009094953223726F, 0.0F, -0.045553093477052F));
        PartDefinition p_lLeg3 = p_lLeg2.addOrReplaceChild("lLeg3",
                CubeListBuilder.create().texOffs(106, 22).addBox(-1.0F, 0.3F, -0.9F, 2.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.2F, 1.9F, 4.8F, -0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_lBHoofB = p_lLeg3.addOrReplaceChild("lBHoofB",
                CubeListBuilder.create().texOffs(72, 26).addBox(0.1F, 4.0F, -0.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.18203784098300857F, -0.091106186954104F, 0.091106186954104F));
        PartDefinition p_lHoof = p_lLeg3.addOrReplaceChild("lHoof",
                CubeListBuilder.create().texOffs(72, 21).addBox(-0.5F, 4.1F, -1.9F, 2.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.18203784098300857F, 0.0F, 0.091106186954104F));
        PartDefinition p_rLeg1 = p_stomach.addOrReplaceChild("rLeg1",
                CubeListBuilder.create().texOffs(88, 0).addBox(-2.0F, -1.5F, -1.5F, 3.0F, 7.0F, 4.0F),
                PartPose.offsetAndRotation(-2.0F, 0.3F, 5.9F, -0.3665191429188092F, 0.0F, 0.12217304763960307F));
        PartDefinition p_rLeg2 = p_rLeg1.addOrReplaceChild("rLeg2",
                CubeListBuilder.create().texOffs(88, 13).addBox(-1.8F, 0.3F, 0.8F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.5009094953223726F, 0.0F, 0.045553093477052F));
        PartDefinition p_rLeg3 = p_rLeg2.addOrReplaceChild("rLeg3",
                CubeListBuilder.create().texOffs(88, 22).addBox(-1.3F, 0.3F, -0.9F, 2.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 1.9F, 4.8F, -0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_rBHoofB = p_rLeg3.addOrReplaceChild("rBHoofB",
                CubeListBuilder.create().texOffs(58, 26).addBox(-1.4F, 4.1F, -0.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.18203784098300857F, 0.091106186954104F, -0.091106186954104F));
        PartDefinition p_rHoof = p_rLeg3.addOrReplaceChild("rHoof",
                CubeListBuilder.create().texOffs(58, 22).addBox(-1.8F, 4.2F, -1.9F, 2.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.18203784098300857F, 0.0F, -0.091106186954104F));
        PartDefinition p_head1 = p_chest.addOrReplaceChild("head1",
                CubeListBuilder.create().texOffs(15, 43).addBox(-2.0F, -3.1F, -3.3F, 4.0F, 2.0F, 3.0F),
                PartPose.offset(0.0F, -0.5F, -5.7F));
        PartDefinition p_lWhiskers = p_head1.addOrReplaceChild("lWhiskers",
                CubeListBuilder.create().texOffs(0, 50).addBox(2.2F, -4.4F, 0.2F, 0.0F, 7.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7330382858376184F, 0.0F));
        PartDefinition p_quills2 = p_head1.addOrReplaceChild("quills2",
                CubeListBuilder.create().texOffs(12, 57).addBox(-1.5F, -2.0F, -0.1F, 4.0F, 0.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.0F, 0.0F));
        PartDefinition p_quills1 = p_head1.addOrReplaceChild("quills1",
                CubeListBuilder.create().texOffs(12, 57).addBox(-2.0F, -3.0F, -0.5F, 4.0F, 0.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.15707963267948966F, 0.0F, 0.0F));
        PartDefinition p_lLowerJaw2 = p_head1.addOrReplaceChild("lLowerJaw2",
                CubeListBuilder.create().texOffs(12, 33).addBox(0.4F, 0.2F, -1.7F, 2.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-0.6F, -0.1F, -1.1F, 0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_lowerJaw = p_lLowerJaw2.addOrReplaceChild("lowerJaw",
                CubeListBuilder.create().texOffs(6, 39).addBox(-0.8F, 0.2F, -2.6F, 3.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_teethLower = p_lowerJaw.addOrReplaceChild("teethLower",
                CubeListBuilder.create().texOffs(7, 18).addBox(-0.8F, -0.6F, -2.6F, 3.0F, 1.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rTusk = p_lowerJaw.addOrReplaceChild("rTusk",
                CubeListBuilder.create().texOffs(3, 16).addBox(-1.1F, -0.7F, -2.6F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.2F, 0.0F, 0.05235987755982988F, 0.0F, -0.20943951023931953F));
        PartDefinition p_lTusk = p_lowerJaw.addOrReplaceChild("lTusk",
                CubeListBuilder.create().texOffs(3, 16).addBox(2.4F, -0.7F, -2.6F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.2F, 0.0F, 0.05235987755982988F, 0.0F, 0.20943951023931953F));
        PartDefinition p_rlowerJaw2 = p_lLowerJaw2.addOrReplaceChild("rlowerJaw2",
                CubeListBuilder.create().texOffs(2, 33).addBox(-1.1F, 0.2F, -1.7F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lJawTendons = p_lLowerJaw2.addOrReplaceChild("lJawTendons",
                CubeListBuilder.create().texOffs(13, 24).addBox(2.2F, -1.5F, -1.6F, 0.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.091106186954104F, 0.0F, -0.136659280431156F));
        PartDefinition p_rJawTendons = p_lLowerJaw2.addOrReplaceChild("rJawTendons",
                CubeListBuilder.create().texOffs(5, 24).mirror().addBox(-1.2F, -1.5F, -1.6F, 0.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.091106186954104F, 0.0F, 0.136659280431156F));
        PartDefinition p_rWhiskers = p_head1.addOrReplaceChild("rWhiskers",
                CubeListBuilder.create().texOffs(0, 50).mirror().addBox(-2.2F, -4.4F, -0.4F, 0.0F, 7.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7330382858376184F, 0.0F));
        PartDefinition p_muzzle = p_head1.addOrReplaceChild("muzzle",
                CubeListBuilder.create().texOffs(0, 8).addBox(-1.5F, -2.5F, -3.5F, 3.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2792526803190927F, 0.0F, 0.0F));
        PartDefinition p_eyes = p_head1.addOrReplaceChild("eyes",
                CubeListBuilder.create().texOffs(1, 1).addBox(-2.0F, -2.6F, -3.9F, 4.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.12217304763960307F, 0.0F, 0.0F));
        PartDefinition p_teethUpper = p_head1.addOrReplaceChild("teethUpper",
                CubeListBuilder.create().texOffs(7, 14).addBox(-1.5F, -0.7F, -3.4F, 3.0F, 1.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2792526803190927F, 0.0F, 0.0F));
        PartDefinition p_lArm1 = p_chest.addOrReplaceChild("lArm1",
                CubeListBuilder.create().texOffs(72, 0).addBox(-0.5F, 0.0F, -1.0F, 3.0F, 6.0F, 3.0F),
                PartPose.offsetAndRotation(2.0F, -0.4F, -3.0F, 0.2617993877991494F, 0.0F, -0.05235987755982988F));
        PartDefinition p_lArm2 = p_lArm1.addOrReplaceChild("lArm2",
                CubeListBuilder.create().texOffs(72, 10).addBox(0.0F, 0.1F, -0.6F, 2.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, -0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_lFoot = p_lArm2.addOrReplaceChild("lFoot",
                CubeListBuilder.create().texOffs(72, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(1.0F, 3.0F, 0.4F, 0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_lHoofFB = p_lFoot.addOrReplaceChild("lHoofFB",
                CubeListBuilder.create().texOffs(72, 26).addBox(-0.5F, 1.4F, -0.2F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_lHoofF = p_lFoot.addOrReplaceChild("lHoofF",
                CubeListBuilder.create().texOffs(72, 21).addBox(-0.9F, 0.3F, -1.1F, 2.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
