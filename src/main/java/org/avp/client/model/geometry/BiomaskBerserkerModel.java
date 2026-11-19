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
 * Geometry converted automatically from the 1.12.2 ModelBiomaskBerserker by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class BiomaskBerserkerModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "biomask_berserker"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart biomaskMouth;
    public final ModelPart mBiomaskJaw;
    public final ModelPart biomaskHeadPlate;
    public final ModelPart rBiomaskRidge;
    public final ModelPart lBiomaskRidge;
    public final ModelPart lBiomaskSide2;
    public final ModelPart rBiomaskSide1;
    public final ModelPart lBiomaskSide1;
    public final ModelPart biomaskVisor;
    public final ModelPart rBiomaskSide2;
    public final ModelPart biomaskSlope;
    public final ModelPart sights;
    public final ModelPart lBiomaskEye;
    public final ModelPart lBiomaskJaw;
    public final ModelPart rBiomaskSide4;
    public final ModelPart lBiomaskSide4;
    public final ModelPart biomaskJawBone;
    public final ModelPart biomaskFangsFront;
    public final ModelPart biomaskJawBone2;
    public final ModelPart lBiomaskJawBone;
    public final ModelPart rBiomaskJawBone;
    public final ModelPart rBiomaskJawBone2;
    public final ModelPart rBiomaskJawBone3;
    public final ModelPart rBiomaskJawBone4;
    public final ModelPart rBiomaskTeeth;
    public final ModelPart rBiomaskJawBone2b;
    public final ModelPart lBiomaskJawBone2;
    public final ModelPart lBiomaskJawBone3;
    public final ModelPart lBiomaskJawBone4;
    public final ModelPart lBiomaskTeeth;
    public final ModelPart lBiomaskJawBone2b;
    public final ModelPart lBiomaskSide3;
    public final ModelPart rBiomaskSide3;
    public final ModelPart rBiomaskEye;

    public BiomaskBerserkerModel(ModelPart root)
    {
        this.root = root;
        this.biomaskMouth = root.getChild("biomaskMouth");
        this.parts.put("biomaskMouth", this.biomaskMouth);
        this.mBiomaskJaw = this.biomaskMouth.getChild("mBiomaskJaw");
        this.parts.put("mBiomaskJaw", this.mBiomaskJaw);
        this.biomaskHeadPlate = this.biomaskMouth.getChild("biomaskHeadPlate");
        this.parts.put("biomaskHeadPlate", this.biomaskHeadPlate);
        this.rBiomaskRidge = this.biomaskMouth.getChild("rBiomaskRidge");
        this.parts.put("rBiomaskRidge", this.rBiomaskRidge);
        this.lBiomaskRidge = this.biomaskMouth.getChild("lBiomaskRidge");
        this.parts.put("lBiomaskRidge", this.lBiomaskRidge);
        this.lBiomaskSide2 = this.biomaskMouth.getChild("lBiomaskSide2");
        this.parts.put("lBiomaskSide2", this.lBiomaskSide2);
        this.rBiomaskSide1 = this.biomaskMouth.getChild("rBiomaskSide1");
        this.parts.put("rBiomaskSide1", this.rBiomaskSide1);
        this.lBiomaskSide1 = this.biomaskMouth.getChild("lBiomaskSide1");
        this.parts.put("lBiomaskSide1", this.lBiomaskSide1);
        this.biomaskVisor = this.biomaskMouth.getChild("biomaskVisor");
        this.parts.put("biomaskVisor", this.biomaskVisor);
        this.rBiomaskSide2 = this.biomaskMouth.getChild("rBiomaskSide2");
        this.parts.put("rBiomaskSide2", this.rBiomaskSide2);
        this.biomaskSlope = this.biomaskMouth.getChild("biomaskSlope");
        this.parts.put("biomaskSlope", this.biomaskSlope);
        this.sights = this.biomaskMouth.getChild("sights");
        this.parts.put("sights", this.sights);
        this.lBiomaskEye = this.biomaskMouth.getChild("lBiomaskEye");
        this.parts.put("lBiomaskEye", this.lBiomaskEye);
        this.lBiomaskJaw = this.biomaskMouth.getChild("lBiomaskJaw");
        this.parts.put("lBiomaskJaw", this.lBiomaskJaw);
        this.rBiomaskSide4 = this.biomaskMouth.getChild("rBiomaskSide4");
        this.parts.put("rBiomaskSide4", this.rBiomaskSide4);
        this.lBiomaskSide4 = this.biomaskMouth.getChild("lBiomaskSide4");
        this.parts.put("lBiomaskSide4", this.lBiomaskSide4);
        this.biomaskJawBone = this.biomaskMouth.getChild("biomaskJawBone");
        this.parts.put("biomaskJawBone", this.biomaskJawBone);
        this.biomaskFangsFront = this.biomaskJawBone.getChild("biomaskFangsFront");
        this.parts.put("biomaskFangsFront", this.biomaskFangsFront);
        this.biomaskJawBone2 = this.biomaskJawBone.getChild("biomaskJawBone2");
        this.parts.put("biomaskJawBone2", this.biomaskJawBone2);
        this.lBiomaskJawBone = this.biomaskJawBone.getChild("lBiomaskJawBone");
        this.parts.put("lBiomaskJawBone", this.lBiomaskJawBone);
        this.rBiomaskJawBone = this.lBiomaskJawBone.getChild("rBiomaskJawBone");
        this.parts.put("rBiomaskJawBone", this.rBiomaskJawBone);
        this.rBiomaskJawBone2 = this.rBiomaskJawBone.getChild("rBiomaskJawBone2");
        this.parts.put("rBiomaskJawBone2", this.rBiomaskJawBone2);
        this.rBiomaskJawBone3 = this.rBiomaskJawBone2.getChild("rBiomaskJawBone3");
        this.parts.put("rBiomaskJawBone3", this.rBiomaskJawBone3);
        this.rBiomaskJawBone4 = this.rBiomaskJawBone3.getChild("rBiomaskJawBone4");
        this.parts.put("rBiomaskJawBone4", this.rBiomaskJawBone4);
        this.rBiomaskTeeth = this.rBiomaskJawBone2.getChild("rBiomaskTeeth");
        this.parts.put("rBiomaskTeeth", this.rBiomaskTeeth);
        this.rBiomaskJawBone2b = this.rBiomaskJawBone2.getChild("rBiomaskJawBone2b");
        this.parts.put("rBiomaskJawBone2b", this.rBiomaskJawBone2b);
        this.lBiomaskJawBone2 = this.lBiomaskJawBone.getChild("lBiomaskJawBone2");
        this.parts.put("lBiomaskJawBone2", this.lBiomaskJawBone2);
        this.lBiomaskJawBone3 = this.lBiomaskJawBone2.getChild("lBiomaskJawBone3");
        this.parts.put("lBiomaskJawBone3", this.lBiomaskJawBone3);
        this.lBiomaskJawBone4 = this.lBiomaskJawBone3.getChild("lBiomaskJawBone4");
        this.parts.put("lBiomaskJawBone4", this.lBiomaskJawBone4);
        this.lBiomaskTeeth = this.lBiomaskJawBone2.getChild("lBiomaskTeeth");
        this.parts.put("lBiomaskTeeth", this.lBiomaskTeeth);
        this.lBiomaskJawBone2b = this.lBiomaskJawBone2.getChild("lBiomaskJawBone2b");
        this.parts.put("lBiomaskJawBone2b", this.lBiomaskJawBone2b);
        this.lBiomaskSide3 = this.biomaskMouth.getChild("lBiomaskSide3");
        this.parts.put("lBiomaskSide3", this.lBiomaskSide3);
        this.rBiomaskSide3 = this.biomaskMouth.getChild("rBiomaskSide3");
        this.parts.put("rBiomaskSide3", this.rBiomaskSide3);
        this.rBiomaskEye = this.biomaskMouth.getChild("rBiomaskEye");
        this.parts.put("rBiomaskEye", this.rBiomaskEye);
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
        PartDefinition p_biomaskMouth = root.addOrReplaceChild("biomaskMouth",
                CubeListBuilder.create().texOffs(180, 87).addBox(-1.5F, -4.9F, -5.5F, 3.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 20.9F, 9.1F, 0.5235987755982988F, 0.0F, 0.0F));
        PartDefinition p_mBiomaskJaw = p_biomaskMouth.addOrReplaceChild("mBiomaskJaw",
                CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, -2.5F, -4.3F, 2.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_biomaskHeadPlate = p_biomaskMouth.addOrReplaceChild("biomaskHeadPlate",
                CubeListBuilder.create().texOffs(173, 57).addBox(-3.5F, -8.0F, -10.0F, 7.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217304763960306F, 0.0F, 0.0F));
        PartDefinition p_rBiomaskRidge = p_biomaskMouth.addOrReplaceChild("rBiomaskRidge",
                CubeListBuilder.create().texOffs(180, 101).addBox(-4.5F, -10.5F, -8.7F, 3.0F, 10.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.1344640137963142F, -0.12217304763960307F, 0.0F));
        PartDefinition p_lBiomaskRidge = p_biomaskMouth.addOrReplaceChild("lBiomaskRidge",
                CubeListBuilder.create().texOffs(180, 115).addBox(1.5F, -10.5F, -8.7F, 3.0F, 10.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.1344640137963142F, 0.12217304763960307F, 0.0F));
        PartDefinition p_lBiomaskSide2 = p_biomaskMouth.addOrReplaceChild("lBiomaskSide2",
                CubeListBuilder.create().texOffs(221, 98).addBox(-3.0F, -4.0F, -4.0F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.413716694115407F, 0.0F));
        PartDefinition p_rBiomaskSide1 = p_biomaskMouth.addOrReplaceChild("rBiomaskSide1",
                CubeListBuilder.create().texOffs(207, 104).addBox(0.3F, -5.0F, -4.3F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.48352986419518F, 0.0F));
        PartDefinition p_lBiomaskSide1 = p_biomaskMouth.addOrReplaceChild("lBiomaskSide1",
                CubeListBuilder.create().texOffs(221, 104).addBox(-3.0F, -5.0F, -4.3F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.48352986419518F, 0.0F));
        PartDefinition p_biomaskVisor = p_biomaskMouth.addOrReplaceChild("biomaskVisor",
                CubeListBuilder.create().texOffs(176, 82).addBox(-3.5F, -6.5F, -5.3F, 7.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.22689280275926282F, 0.0F, 0.0F));
        PartDefinition p_rBiomaskSide2 = p_biomaskMouth.addOrReplaceChild("rBiomaskSide2",
                CubeListBuilder.create().texOffs(207, 98).addBox(0.0F, -4.0F, -4.0F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.413716694115407F, 0.0F));
        PartDefinition p_biomaskSlope = p_biomaskMouth.addOrReplaceChild("biomaskSlope",
                CubeListBuilder.create().texOffs(174, 69).addBox(-4.0F, 0.0F, -8.9F, 8.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217304763960306F, 0.0F, 0.0F));
        PartDefinition p_sights = p_biomaskMouth.addOrReplaceChild("sights",
                CubeListBuilder.create().texOffs(214, 55).addBox(-3.5F, -8.9F, -2.5F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2241002759560719F, -0.10367255756846318F, -0.14032447186034408F));
        PartDefinition p_lBiomaskEye = p_biomaskMouth.addOrReplaceChild("lBiomaskEye",
                CubeListBuilder.create().texOffs(219, 78).addBox(3.2F, -8.0F, -3.2F, 1.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.17453292519943295F, 0.0F));
        PartDefinition p_lBiomaskJaw = p_biomaskMouth.addOrReplaceChild("lBiomaskJaw",
                CubeListBuilder.create().texOffs(65, 0).addBox(0.5F, -2.5F, -4.0F, 1.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.12217304763960307F, 0.0F));
        PartDefinition p_rBiomaskSide4 = p_biomaskMouth.addOrReplaceChild("rBiomaskSide4",
                CubeListBuilder.create().texOffs(207, 88).addBox(-1.0F, -5.0F, -5.3F, 3.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6457718232379019F, 0.0F));
        PartDefinition p_lBiomaskSide4 = p_biomaskMouth.addOrReplaceChild("lBiomaskSide4",
                CubeListBuilder.create().texOffs(219, 88).addBox(-2.0F, -5.0F, -5.3F, 3.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6457718232379019F, 0.0F));
        PartDefinition p_biomaskJawBone = p_biomaskMouth.addOrReplaceChild("biomaskJawBone",
                CubeListBuilder.create().texOffs(200, 69).addBox(-2.0F, -0.7F, -0.5F, 4.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -0.9F, -5.4F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_biomaskFangsFront = p_biomaskJawBone.addOrReplaceChild("biomaskFangsFront",
                CubeListBuilder.create().texOffs(222, 63).addBox(-2.0F, -1.6F, -0.3F, 4.0F, 1.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_biomaskJawBone2 = p_biomaskJawBone.addOrReplaceChild("biomaskJawBone2",
                CubeListBuilder.create().texOffs(200, 75).addBox(-2.0F, 0.1F, -0.4F, 4.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lBiomaskJawBone = p_biomaskJawBone.addOrReplaceChild("lBiomaskJawBone",
                CubeListBuilder.create().texOffs(216, 69).addBox(1.2F, 0.0F, 0.6F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.6F, 0.0F, 0.7285004297824331F, 0.0F));
        PartDefinition p_rBiomaskJawBone = p_lBiomaskJawBone.addOrReplaceChild("rBiomaskJawBone",
                CubeListBuilder.create().texOffs(216, 69).mirror().addBox(-2.2F, 0.0F, 0.6F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.4570008595648662F, 0.0F));
        PartDefinition p_rBiomaskJawBone2 = p_rBiomaskJawBone.addOrReplaceChild("rBiomaskJawBone2",
                CubeListBuilder.create().texOffs(227, 69).mirror().addBox(-0.7F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-1.5F, 0.5F, 2.4F, 0.27314402793711257F, 0.18203784098300857F, 0.0F));
        PartDefinition p_rBiomaskJawBone3 = p_rBiomaskJawBone2.addOrReplaceChild("rBiomaskJawBone3",
                CubeListBuilder.create().texOffs(238, 69).mirror().addBox(-1.5F, -0.9F, -0.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.9F, 0.3F, 1.7F, 0.18203784098300857F, 0.27314402793711257F, 0.0F));
        PartDefinition p_rBiomaskJawBone4 = p_rBiomaskJawBone3.addOrReplaceChild("rBiomaskJawBone4",
                CubeListBuilder.create().texOffs(238, 76).mirror().addBox(-1.4F, -1.6F, 0.9F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rBiomaskTeeth = p_rBiomaskJawBone2.addOrReplaceChild("rBiomaskTeeth",
                CubeListBuilder.create().texOffs(222, 74).addBox(-0.55F, -1.5F, -0.1F, 0.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rBiomaskJawBone2b = p_rBiomaskJawBone2.addOrReplaceChild("rBiomaskJawBone2b",
                CubeListBuilder.create().texOffs(227, 69).addBox(-0.6F, -0.8F, 0.3F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lBiomaskJawBone2 = p_lBiomaskJawBone.addOrReplaceChild("lBiomaskJawBone2",
                CubeListBuilder.create().texOffs(227, 69).addBox(-0.7F, -0.5F, 0.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(1.9F, 0.5F, 2.4F, 0.27314402793711257F, -0.18203784098300857F, 0.0F));
        PartDefinition p_lBiomaskJawBone3 = p_lBiomaskJawBone2.addOrReplaceChild("lBiomaskJawBone3",
                CubeListBuilder.create().texOffs(238, 69).addBox(-0.4F, -0.9F, -0.1F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-0.4F, 0.3F, 2.0F, 0.18203784098300857F, -0.27314402793711257F, 0.0F));
        PartDefinition p_lBiomaskJawBone4 = p_lBiomaskJawBone3.addOrReplaceChild("lBiomaskJawBone4",
                CubeListBuilder.create().texOffs(238, 76).addBox(-0.5F, -1.6F, 0.9F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lBiomaskTeeth = p_lBiomaskJawBone2.addOrReplaceChild("lBiomaskTeeth",
                CubeListBuilder.create().texOffs(222, 74).addBox(0.16F, -1.5F, -0.1F, 0.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lBiomaskJawBone2b = p_lBiomaskJawBone2.addOrReplaceChild("lBiomaskJawBone2b",
                CubeListBuilder.create().texOffs(227, 69).mirror().addBox(-0.8F, -0.8F, 0.3F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lBiomaskSide3 = p_biomaskMouth.addOrReplaceChild("lBiomaskSide3",
                CubeListBuilder.create().texOffs(221, 93).addBox(-2.0F, -4.0F, -5.0F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6457718232379019F, 0.0F));
        PartDefinition p_rBiomaskSide3 = p_biomaskMouth.addOrReplaceChild("rBiomaskSide3",
                CubeListBuilder.create().texOffs(207, 93).addBox(-1.0F, -4.0F, -5.0F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6457718232379019F, 0.0F));
        PartDefinition p_rBiomaskEye = p_biomaskMouth.addOrReplaceChild("rBiomaskEye",
                CubeListBuilder.create().texOffs(207, 78).addBox(-4.2F, -8.0F, -3.2F, 1.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.17453292519943295F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
