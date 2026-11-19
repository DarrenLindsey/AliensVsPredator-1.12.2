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
 * Geometry converted automatically from the 1.12.2 ModelDeaconShark by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class DeaconSharkModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "deacon_shark"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart body1;
    public final ModelPart body2;
    public final ModelPart rHindFin;
    public final ModelPart rHindFlipper;
    public final ModelPart body3;
    public final ModelPart tail1;
    public final ModelPart rTailFin;
    public final ModelPart tail2;
    public final ModelPart tail3;
    public final ModelPart tail4;
    public final ModelPart tailFin;
    public final ModelPart tailSpines;
    public final ModelPart tailSpinesLower;
    public final ModelPart lTailFin;
    public final ModelPart lHindFin;
    public final ModelPart lHindFlipper;
    public final ModelPart muzzle1;
    public final ModelPart jawUpper2;
    public final ModelPart jawUpper;
    public final ModelPart muzzleSlope;
    public final ModelPart jawInnerUpper;
    public final ModelPart jawInnerLower;
    public final ModelPart jawLower2;
    public final ModelPart jawLower;
    public final ModelPart rFrontFin;
    public final ModelPart rFrontFlipper;
    public final ModelPart lFrontFin;
    public final ModelPart lFrontFlipper;
    public final ModelPart dorsalFin;

    public DeaconSharkModel(ModelPart root)
    {
        this.root = root;
        this.body1 = root.getChild("body1");
        this.parts.put("body1", this.body1);
        this.body2 = this.body1.getChild("body2");
        this.parts.put("body2", this.body2);
        this.rHindFin = this.body2.getChild("rHindFin");
        this.parts.put("rHindFin", this.rHindFin);
        this.rHindFlipper = this.rHindFin.getChild("rHindFlipper");
        this.parts.put("rHindFlipper", this.rHindFlipper);
        this.body3 = this.body2.getChild("body3");
        this.parts.put("body3", this.body3);
        this.tail1 = this.body3.getChild("tail1");
        this.parts.put("tail1", this.tail1);
        this.rTailFin = this.tail1.getChild("rTailFin");
        this.parts.put("rTailFin", this.rTailFin);
        this.tail2 = this.tail1.getChild("tail2");
        this.parts.put("tail2", this.tail2);
        this.tail3 = this.tail2.getChild("tail3");
        this.parts.put("tail3", this.tail3);
        this.tail4 = this.tail3.getChild("tail4");
        this.parts.put("tail4", this.tail4);
        this.tailFin = this.tail4.getChild("tailFin");
        this.parts.put("tailFin", this.tailFin);
        this.tailSpines = this.tail3.getChild("tailSpines");
        this.parts.put("tailSpines", this.tailSpines);
        this.tailSpinesLower = this.tail3.getChild("tailSpinesLower");
        this.parts.put("tailSpinesLower", this.tailSpinesLower);
        this.lTailFin = this.tail1.getChild("lTailFin");
        this.parts.put("lTailFin", this.lTailFin);
        this.lHindFin = this.body2.getChild("lHindFin");
        this.parts.put("lHindFin", this.lHindFin);
        this.lHindFlipper = this.lHindFin.getChild("lHindFlipper");
        this.parts.put("lHindFlipper", this.lHindFlipper);
        this.muzzle1 = this.body1.getChild("muzzle1");
        this.parts.put("muzzle1", this.muzzle1);
        this.jawUpper2 = this.muzzle1.getChild("jawUpper2");
        this.parts.put("jawUpper2", this.jawUpper2);
        this.jawUpper = this.jawUpper2.getChild("jawUpper");
        this.parts.put("jawUpper", this.jawUpper);
        this.muzzleSlope = this.muzzle1.getChild("muzzleSlope");
        this.parts.put("muzzleSlope", this.muzzleSlope);
        this.jawInnerUpper = this.muzzle1.getChild("jawInnerUpper");
        this.parts.put("jawInnerUpper", this.jawInnerUpper);
        this.jawInnerLower = this.muzzle1.getChild("jawInnerLower");
        this.parts.put("jawInnerLower", this.jawInnerLower);
        this.jawLower2 = this.muzzle1.getChild("jawLower2");
        this.parts.put("jawLower2", this.jawLower2);
        this.jawLower = this.jawLower2.getChild("jawLower");
        this.parts.put("jawLower", this.jawLower);
        this.rFrontFin = this.body1.getChild("rFrontFin");
        this.parts.put("rFrontFin", this.rFrontFin);
        this.rFrontFlipper = this.rFrontFin.getChild("rFrontFlipper");
        this.parts.put("rFrontFlipper", this.rFrontFlipper);
        this.lFrontFin = this.body1.getChild("lFrontFin");
        this.parts.put("lFrontFin", this.lFrontFin);
        this.lFrontFlipper = this.lFrontFin.getChild("lFrontFlipper");
        this.parts.put("lFrontFlipper", this.lFrontFlipper);
        this.dorsalFin = this.body1.getChild("dorsalFin");
        this.parts.put("dorsalFin", this.dorsalFin);
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
        PartDefinition p_body1 = root.addOrReplaceChild("body1",
                CubeListBuilder.create().texOffs(47, 0).addBox(-3.5F, -5.0F, 0.0F, 8.0F, 10.0F, 12.0F),
                PartPose.offset(-1.0F, 4.0F, -14.0F));
        PartDefinition p_body2 = p_body1.addOrReplaceChild("body2",
                CubeListBuilder.create().texOffs(88, 0).addBox(-2.5F, -4.0F, 0.0F, 6.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 12.0F));
        PartDefinition p_rHindFin = p_body2.addOrReplaceChild("rHindFin",
                CubeListBuilder.create().texOffs(128, 21).addBox(-1.0F, -0.5F, -1.0F, 1.0F, 12.0F, 2.0F),
                PartPose.offsetAndRotation(-2.0F, 0.0F, 7.0F, 0.0F, 0.0F, 0.593411945678072F));
        PartDefinition p_rHindFlipper = p_rHindFin.addOrReplaceChild("rHindFlipper",
                CubeListBuilder.create().texOffs(15, 48).addBox(-0.5F, 5.0F, -3.0F, 0.0F, 9.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_body3 = p_body2.addOrReplaceChild("body3",
                CubeListBuilder.create().texOffs(0, 25).addBox(-2.0F, -3.0F, 0.0F, 5.0F, 6.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 11.0F));
        PartDefinition p_tail1 = p_body3.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(29, 25).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 8.0F),
                PartPose.offset(0.5F, 0.5F, 9.0F));
        PartDefinition p_rTailFin = p_tail1.addOrReplaceChild("rTailFin",
                CubeListBuilder.create().texOffs(54, 90).mirror().addBox(-5.0F, 0.0F, -2.7F, 5.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, 0.0F, 0.6806784082777886F, 0.0F));
        PartDefinition p_tail2 = p_tail1.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(54, 25).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 3.0F, 8.0F),
                PartPose.offset(0.0F, 0.5F, 8.0F));
        PartDefinition p_tail3 = p_tail2.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(77, 25).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 8.0F),
                PartPose.offset(0.0F, -0.5F, 8.0F));
        PartDefinition p_tail4 = p_tail3.addOrReplaceChild("tail4",
                CubeListBuilder.create().texOffs(98, 25).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 8.0F));
        PartDefinition p_tailFin = p_tail4.addOrReplaceChild("tailFin",
                CubeListBuilder.create().texOffs(30, 44).addBox(0.0F, -3.5F, -2.0F, 0.0F, 7.0F, 13.0F),
                PartPose.offset(0.0F, -0.1F, 2.0F));
        PartDefinition p_tailSpines = p_tail3.addOrReplaceChild("tailSpines",
                CubeListBuilder.create().texOffs(30, 66).addBox(0.0F, -3.0F, -3.9F, 0.0F, 4.0F, 10.0F),
                PartPose.offset(0.0F, -0.5F, 1.9F));
        PartDefinition p_tailSpinesLower = p_tail3.addOrReplaceChild("tailSpinesLower",
                CubeListBuilder.create().texOffs(32, 93).addBox(0.0F, 1.1F, -3.9F, 0.0F, 4.0F, 10.0F),
                PartPose.offset(0.0F, -0.2F, 1.9F));
        PartDefinition p_lTailFin = p_tail1.addOrReplaceChild("lTailFin",
                CubeListBuilder.create().texOffs(54, 90).addBox(0.0F, 0.0F, -2.7F, 5.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 4.0F, 0.0F, -0.6806784082777886F, 0.0F));
        PartDefinition p_lHindFin = p_body2.addOrReplaceChild("lHindFin",
                CubeListBuilder.create().texOffs(128, 21).mirror().addBox(-1.0F, -0.5F, -1.0F, 1.0F, 12.0F, 2.0F),
                PartPose.offsetAndRotation(4.0F, 0.0F, 7.0F, 0.0F, 0.0F, -0.593411945678072F));
        PartDefinition p_lHindFlipper = p_lHindFin.addOrReplaceChild("lHindFlipper",
                CubeListBuilder.create().texOffs(15, 48).addBox(-0.5F, 5.0F, -3.0F, 0.0F, 9.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_muzzle1 = p_body1.addOrReplaceChild("muzzle1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -5.5F, -13.0F, 10.0F, 11.0F, 13.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_jawUpper2 = p_muzzle1.addOrReplaceChild("jawUpper2",
                CubeListBuilder.create().texOffs(74, 99).addBox(-3.5F, -1.0F, -7.0F, 8.0F, 4.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, -1.6F, -2.0F, -0.08726646259971647F, 0.0F, 0.0F));
        PartDefinition p_jawUpper = p_jawUpper2.addOrReplaceChild("jawUpper",
                CubeListBuilder.create().texOffs(72, 45).addBox(-3.5F, -1.0F, -21.0F, 8.0F, 4.0F, 14.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_muzzleSlope = p_muzzle1.addOrReplaceChild("muzzleSlope",
                CubeListBuilder.create().texOffs(0, 84).addBox(-3.0F, -1.1F, -8.4F, 7.0F, 2.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, -4.5F, -12.5F, 0.10471975511965977F, 0.0F, 0.0F));
        PartDefinition p_jawInnerUpper = p_muzzle1.addOrReplaceChild("jawInnerUpper",
                CubeListBuilder.create().texOffs(118, 52).addBox(-2.5F, -1.6F, -21.0F, 5.0F, 2.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.03490658503988659F, 0.0F, 0.0F));
        PartDefinition p_jawInnerLower = p_muzzle1.addOrReplaceChild("jawInnerLower",
                CubeListBuilder.create().texOffs(118, 64).addBox(-2.5F, 0.0F, -21.0F, 5.0F, 2.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.05235987755982988F, 0.0F, 0.0F));
        PartDefinition p_jawLower2 = p_muzzle1.addOrReplaceChild("jawLower2",
                CubeListBuilder.create().texOffs(115, 99).addBox(-3.5F, 1.2F, -7.5F, 8.0F, 4.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, -1.6F, -2.0F, 0.20943951023931953F, 0.0F, 0.0F));
        PartDefinition p_jawLower = p_jawLower2.addOrReplaceChild("jawLower",
                CubeListBuilder.create().texOffs(72, 67).addBox(-3.5F, 1.2F, -21.5F, 8.0F, 4.0F, 14.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rFrontFin = p_body1.addOrReplaceChild("rFrontFin",
                CubeListBuilder.create().texOffs(128, 0).addBox(0.0F, 0.0F, -1.0F, 1.0F, 16.0F, 2.0F),
                PartPose.offsetAndRotation(-3.7F, 0.2F, 4.0F, 0.0F, 0.0F, 0.593411945678072F));
        PartDefinition p_rFrontFlipper = p_rFrontFin.addOrReplaceChild("rFrontFlipper",
                CubeListBuilder.create().texOffs(0, 45).addBox(0.5F, 7.0F, -3.0F, 0.0F, 12.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lFrontFin = p_body1.addOrReplaceChild("lFrontFin",
                CubeListBuilder.create().texOffs(128, 0).mirror().addBox(0.0F, 0.0F, -1.0F, 1.0F, 16.0F, 2.0F),
                PartPose.offsetAndRotation(3.7F, 0.2F, 4.0F, 0.0F, 0.0F, -0.593411945678072F));
        PartDefinition p_lFrontFlipper = p_lFrontFin.addOrReplaceChild("lFrontFlipper",
                CubeListBuilder.create().texOffs(0, 45).addBox(0.5F, 7.0F, -3.0F, 0.0F, 12.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_dorsalFin = p_body1.addOrReplaceChild("dorsalFin",
                CubeListBuilder.create().texOffs(54, 75).addBox(0.0F, -11.0F, -4.0F, 2.0F, 7.0F, 5.0F),
                PartPose.offsetAndRotation(-0.5F, 0.0F, 0.0F, -0.6283185307179586F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
