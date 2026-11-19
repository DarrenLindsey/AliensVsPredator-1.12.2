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
 * Geometry converted automatically from the 1.12.2 ModelBiomaskClassic by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class BiomaskClassicModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "biomask_classic"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart biomaskMouth;
    public final ModelPart rBiomaskSide2;
    public final ModelPart biomaskSlope;
    public final ModelPart lBiomaskSide2;
    public final ModelPart rBiomaskSide4;
    public final ModelPart lBiomaskSide3;
    public final ModelPart rBiomaskSide1;
    public final ModelPart biomaskVisor;
    public final ModelPart lBiomaskSide1;
    public final ModelPart rBiomaskRidge;
    public final ModelPart lBiomaskJaw;
    public final ModelPart biomaskHeadPlate;
    public final ModelPart lBiomaskSide4;
    public final ModelPart rBiomaskEye;
    public final ModelPart lBiomaskRidge;
    public final ModelPart rBiomaskSide3;
    public final ModelPart sights;
    public final ModelPart lBiomaskEye;
    public final ModelPart mBiomaskJaw;

    public BiomaskClassicModel(ModelPart root)
    {
        this.root = root;
        this.biomaskMouth = root.getChild("biomaskMouth");
        this.parts.put("biomaskMouth", this.biomaskMouth);
        this.rBiomaskSide2 = this.biomaskMouth.getChild("rBiomaskSide2");
        this.parts.put("rBiomaskSide2", this.rBiomaskSide2);
        this.biomaskSlope = this.biomaskMouth.getChild("biomaskSlope");
        this.parts.put("biomaskSlope", this.biomaskSlope);
        this.lBiomaskSide2 = this.biomaskMouth.getChild("lBiomaskSide2");
        this.parts.put("lBiomaskSide2", this.lBiomaskSide2);
        this.rBiomaskSide4 = this.biomaskMouth.getChild("rBiomaskSide4");
        this.parts.put("rBiomaskSide4", this.rBiomaskSide4);
        this.lBiomaskSide3 = this.biomaskMouth.getChild("lBiomaskSide3");
        this.parts.put("lBiomaskSide3", this.lBiomaskSide3);
        this.rBiomaskSide1 = this.biomaskMouth.getChild("rBiomaskSide1");
        this.parts.put("rBiomaskSide1", this.rBiomaskSide1);
        this.biomaskVisor = this.biomaskMouth.getChild("biomaskVisor");
        this.parts.put("biomaskVisor", this.biomaskVisor);
        this.lBiomaskSide1 = this.biomaskMouth.getChild("lBiomaskSide1");
        this.parts.put("lBiomaskSide1", this.lBiomaskSide1);
        this.rBiomaskRidge = this.biomaskMouth.getChild("rBiomaskRidge");
        this.parts.put("rBiomaskRidge", this.rBiomaskRidge);
        this.lBiomaskJaw = this.biomaskMouth.getChild("lBiomaskJaw");
        this.parts.put("lBiomaskJaw", this.lBiomaskJaw);
        this.biomaskHeadPlate = this.biomaskMouth.getChild("biomaskHeadPlate");
        this.parts.put("biomaskHeadPlate", this.biomaskHeadPlate);
        this.lBiomaskSide4 = this.biomaskMouth.getChild("lBiomaskSide4");
        this.parts.put("lBiomaskSide4", this.lBiomaskSide4);
        this.rBiomaskEye = this.biomaskMouth.getChild("rBiomaskEye");
        this.parts.put("rBiomaskEye", this.rBiomaskEye);
        this.lBiomaskRidge = this.biomaskMouth.getChild("lBiomaskRidge");
        this.parts.put("lBiomaskRidge", this.lBiomaskRidge);
        this.rBiomaskSide3 = this.biomaskMouth.getChild("rBiomaskSide3");
        this.parts.put("rBiomaskSide3", this.rBiomaskSide3);
        this.sights = this.biomaskMouth.getChild("sights");
        this.parts.put("sights", this.sights);
        this.lBiomaskEye = this.biomaskMouth.getChild("lBiomaskEye");
        this.parts.put("lBiomaskEye", this.lBiomaskEye);
        this.mBiomaskJaw = this.biomaskMouth.getChild("mBiomaskJaw");
        this.parts.put("mBiomaskJaw", this.mBiomaskJaw);
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
        PartDefinition p_rBiomaskSide2 = p_biomaskMouth.addOrReplaceChild("rBiomaskSide2",
                CubeListBuilder.create().texOffs(207, 98).addBox(0.0F, -4.0F, -4.0F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.413716694115407F, 0.0F));
        PartDefinition p_biomaskSlope = p_biomaskMouth.addOrReplaceChild("biomaskSlope",
                CubeListBuilder.create().texOffs(174, 69).addBox(-4.0F, 0.0F, -8.9F, 8.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217304763960306F, 0.0F, 0.0F));
        PartDefinition p_lBiomaskSide2 = p_biomaskMouth.addOrReplaceChild("lBiomaskSide2",
                CubeListBuilder.create().texOffs(221, 98).addBox(-3.0F, -4.0F, -4.0F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.413716694115407F, 0.0F));
        PartDefinition p_rBiomaskSide4 = p_biomaskMouth.addOrReplaceChild("rBiomaskSide4",
                CubeListBuilder.create().texOffs(207, 88).addBox(-1.0F, -5.0F, -5.3F, 3.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6457718232379019F, 0.0F));
        PartDefinition p_lBiomaskSide3 = p_biomaskMouth.addOrReplaceChild("lBiomaskSide3",
                CubeListBuilder.create().texOffs(221, 93).addBox(-2.0F, -4.0F, -5.0F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6457718232379019F, 0.0F));
        PartDefinition p_rBiomaskSide1 = p_biomaskMouth.addOrReplaceChild("rBiomaskSide1",
                CubeListBuilder.create().texOffs(207, 104).addBox(0.3F, -5.0F, -4.3F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.48352986419518F, 0.0F));
        PartDefinition p_biomaskVisor = p_biomaskMouth.addOrReplaceChild("biomaskVisor",
                CubeListBuilder.create().texOffs(176, 82).addBox(-3.5F, -6.5F, -5.3F, 7.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.22689280275926282F, 0.0F, 0.0F));
        PartDefinition p_lBiomaskSide1 = p_biomaskMouth.addOrReplaceChild("lBiomaskSide1",
                CubeListBuilder.create().texOffs(221, 104).addBox(-3.0F, -5.0F, -4.3F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.48352986419518F, 0.0F));
        PartDefinition p_rBiomaskRidge = p_biomaskMouth.addOrReplaceChild("rBiomaskRidge",
                CubeListBuilder.create().texOffs(180, 101).addBox(-4.5F, -10.5F, -8.7F, 3.0F, 10.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.1344640137963142F, -0.12217304763960307F, 0.0F));
        PartDefinition p_lBiomaskJaw = p_biomaskMouth.addOrReplaceChild("lBiomaskJaw",
                CubeListBuilder.create().texOffs(65, 0).addBox(0.5F, -2.5F, -4.0F, 1.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.12217304763960307F, 0.0F));
        PartDefinition p_biomaskHeadPlate = p_biomaskMouth.addOrReplaceChild("biomaskHeadPlate",
                CubeListBuilder.create().texOffs(173, 57).addBox(-3.5F, -8.0F, -10.0F, 7.0F, 8.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.2217304763960306F, 0.0F, 0.0F));
        PartDefinition p_lBiomaskSide4 = p_biomaskMouth.addOrReplaceChild("lBiomaskSide4",
                CubeListBuilder.create().texOffs(219, 88).addBox(-2.0F, -5.0F, -5.3F, 3.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6457718232379019F, 0.0F));
        PartDefinition p_rBiomaskEye = p_biomaskMouth.addOrReplaceChild("rBiomaskEye",
                CubeListBuilder.create().texOffs(207, 78).addBox(-4.2F, -8.0F, -3.2F, 1.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.17453292519943295F, 0.0F));
        PartDefinition p_lBiomaskRidge = p_biomaskMouth.addOrReplaceChild("lBiomaskRidge",
                CubeListBuilder.create().texOffs(180, 115).addBox(1.5F, -10.5F, -8.7F, 3.0F, 10.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.1344640137963142F, 0.12217304763960307F, 0.0F));
        PartDefinition p_rBiomaskSide3 = p_biomaskMouth.addOrReplaceChild("rBiomaskSide3",
                CubeListBuilder.create().texOffs(207, 93).addBox(-1.0F, -4.0F, -5.0F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6457718232379019F, 0.0F));
        PartDefinition p_sights = p_biomaskMouth.addOrReplaceChild("sights",
                CubeListBuilder.create().texOffs(214, 55).addBox(-3.5F, -8.9F, -2.5F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2241002759560719F, -0.10367255756846318F, -0.14032447186034408F));
        PartDefinition p_lBiomaskEye = p_biomaskMouth.addOrReplaceChild("lBiomaskEye",
                CubeListBuilder.create().texOffs(219, 78).addBox(3.2F, -8.0F, -3.2F, 1.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.17453292519943295F, 0.0F));
        PartDefinition p_mBiomaskJaw = p_biomaskMouth.addOrReplaceChild("mBiomaskJaw",
                CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, -2.5F, -4.3F, 2.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
