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
 * Geometry converted automatically from the 1.12.2 ModelDNASynthesizer by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class DNASynthesizerModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "d_n_a_synthesizer"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart base;
    public final ModelPart tankLid;
    public final ModelPart tankFloor;
    public final ModelPart vial1;
    public final ModelPart tube3;
    public final ModelPart vial1Fluid;
    public final ModelPart rFrameSupport;
    public final ModelPart lFrameSupport;
    public final ModelPart vial3;
    public final ModelPart tube1;
    public final ModelPart injectorNeedle;
    public final ModelPart supportBar;
    public final ModelPart glassN;
    public final ModelPart glassNE;
    public final ModelPart glassE;
    public final ModelPart glassSE;
    public final ModelPart glassNW;
    public final ModelPart glassSW;
    public final ModelPart glassW;
    public final ModelPart tube4;
    public final ModelPart injector;
    public final ModelPart tube5;
    public final ModelPart vial3Fluid;
    public final ModelPart vial2;
    public final ModelPart injectorFluid;
    public final ModelPart capN;
    public final ModelPart capNE;
    public final ModelPart capNW;
    public final ModelPart capE;
    public final ModelPart capSE;
    public final ModelPart capW;
    public final ModelPart capSW;
    public final ModelPart tube2;
    public final ModelPart vial2Fluid;
    public final ModelPart baseN;
    public final ModelPart baseE;
    public final ModelPart baseNW;
    public final ModelPart baseSW;
    public final ModelPart baseNE;
    public final ModelPart baseW;
    public final ModelPart baseSE;
    public final ModelPart frame;

    public DNASynthesizerModel(ModelPart root)
    {
        this.root = root;
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.tankLid = root.getChild("tankLid");
        this.parts.put("tankLid", this.tankLid);
        this.tankFloor = root.getChild("tankFloor");
        this.parts.put("tankFloor", this.tankFloor);
        this.vial1 = root.getChild("vial1");
        this.parts.put("vial1", this.vial1);
        this.tube3 = root.getChild("tube3");
        this.parts.put("tube3", this.tube3);
        this.vial1Fluid = root.getChild("vial1Fluid");
        this.parts.put("vial1Fluid", this.vial1Fluid);
        this.rFrameSupport = root.getChild("rFrameSupport");
        this.parts.put("rFrameSupport", this.rFrameSupport);
        this.lFrameSupport = root.getChild("lFrameSupport");
        this.parts.put("lFrameSupport", this.lFrameSupport);
        this.vial3 = root.getChild("vial3");
        this.parts.put("vial3", this.vial3);
        this.tube1 = root.getChild("tube1");
        this.parts.put("tube1", this.tube1);
        this.injectorNeedle = root.getChild("injectorNeedle");
        this.parts.put("injectorNeedle", this.injectorNeedle);
        this.supportBar = root.getChild("supportBar");
        this.parts.put("supportBar", this.supportBar);
        this.glassN = root.getChild("glassN");
        this.parts.put("glassN", this.glassN);
        this.glassNE = this.glassN.getChild("glassNE");
        this.parts.put("glassNE", this.glassNE);
        this.glassE = this.glassN.getChild("glassE");
        this.parts.put("glassE", this.glassE);
        this.glassSE = this.glassN.getChild("glassSE");
        this.parts.put("glassSE", this.glassSE);
        this.glassNW = this.glassN.getChild("glassNW");
        this.parts.put("glassNW", this.glassNW);
        this.glassSW = this.glassN.getChild("glassSW");
        this.parts.put("glassSW", this.glassSW);
        this.glassW = this.glassN.getChild("glassW");
        this.parts.put("glassW", this.glassW);
        this.tube4 = root.getChild("tube4");
        this.parts.put("tube4", this.tube4);
        this.injector = root.getChild("injector");
        this.parts.put("injector", this.injector);
        this.tube5 = root.getChild("tube5");
        this.parts.put("tube5", this.tube5);
        this.vial3Fluid = root.getChild("vial3Fluid");
        this.parts.put("vial3Fluid", this.vial3Fluid);
        this.vial2 = root.getChild("vial2");
        this.parts.put("vial2", this.vial2);
        this.injectorFluid = root.getChild("injectorFluid");
        this.parts.put("injectorFluid", this.injectorFluid);
        this.capN = root.getChild("capN");
        this.parts.put("capN", this.capN);
        this.capNE = this.capN.getChild("capNE");
        this.parts.put("capNE", this.capNE);
        this.capNW = this.capN.getChild("capNW");
        this.parts.put("capNW", this.capNW);
        this.capE = this.capN.getChild("capE");
        this.parts.put("capE", this.capE);
        this.capSE = this.capN.getChild("capSE");
        this.parts.put("capSE", this.capSE);
        this.capW = this.capN.getChild("capW");
        this.parts.put("capW", this.capW);
        this.capSW = this.capN.getChild("capSW");
        this.parts.put("capSW", this.capSW);
        this.tube2 = root.getChild("tube2");
        this.parts.put("tube2", this.tube2);
        this.vial2Fluid = root.getChild("vial2Fluid");
        this.parts.put("vial2Fluid", this.vial2Fluid);
        this.baseN = root.getChild("baseN");
        this.parts.put("baseN", this.baseN);
        this.baseE = this.baseN.getChild("baseE");
        this.parts.put("baseE", this.baseE);
        this.baseNW = this.baseN.getChild("baseNW");
        this.parts.put("baseNW", this.baseNW);
        this.baseSW = this.baseN.getChild("baseSW");
        this.parts.put("baseSW", this.baseSW);
        this.baseNE = this.baseN.getChild("baseNE");
        this.parts.put("baseNE", this.baseNE);
        this.baseW = this.baseN.getChild("baseW");
        this.parts.put("baseW", this.baseW);
        this.baseSE = this.baseN.getChild("baseSE");
        this.parts.put("baseSE", this.baseSE);
        this.frame = root.getChild("frame");
        this.parts.put("frame", this.frame);
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
        PartDefinition p_base = root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, 16.0F, -3.0F, 15.0F, 8.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tankLid = root.addOrReplaceChild("tankLid",
                CubeListBuilder.create().texOffs(52, 21).addBox(7.5F, 5.3F, -8.5F, 17.0F, 0.0F, 17.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tankFloor = root.addOrReplaceChild("tankFloor",
                CubeListBuilder.create().texOffs(52, 21).addBox(7.5F, 24.0F, -8.5F, 17.0F, 0.0F, 17.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_vial1 = root.addOrReplaceChild("vial1",
                CubeListBuilder.create().texOffs(15, 29).addBox(-5.0F, 9.5F, 2.5F, 2.0F, 5.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tube3 = root.addOrReplaceChild("tube3",
                CubeListBuilder.create().texOffs(92, 0).addBox(1.1F, 8.5F, -0.2F, 4.0F, 8.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_vial1Fluid = root.addOrReplaceChild("vial1Fluid",
                CubeListBuilder.create().texOffs(17, 37).addBox(-4.5F, 11.0F, 3.0F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rFrameSupport = root.addOrReplaceChild("rFrameSupport",
                CubeListBuilder.create().texOffs(0, 32).addBox(5.9F, 10.7F, 1.5F, 1.0F, 6.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lFrameSupport = root.addOrReplaceChild("lFrameSupport",
                CubeListBuilder.create().texOffs(0, 32).addBox(-7.1F, 10.7F, 1.5F, 1.0F, 6.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_vial3 = root.addOrReplaceChild("vial3",
                CubeListBuilder.create().texOffs(37, 29).addBox(3.0F, 9.5F, 2.5F, 2.0F, 5.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tube1 = root.addOrReplaceChild("tube1",
                CubeListBuilder.create().texOffs(74, 0).addBox(-4.4F, 8.5F, 5.3F, 4.0F, 8.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_injectorNeedle = root.addOrReplaceChild("injectorNeedle",
                CubeListBuilder.create().texOffs(62, 22).addBox(15.5F, 9.1F, 1.0F, 1.0F, 2.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_supportBar = root.addOrReplaceChild("supportBar",
                CubeListBuilder.create().texOffs(52, 0).addBox(12.5F, 6.0F, 6.3F, 8.0F, 18.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_glassN = root.addOrReplaceChild("glassN",
                CubeListBuilder.create().texOffs(65, 47).addBox(-3.0F, -14.8F, -7.2F, 6.0F, 15.0F, 0.0F),
                PartPose.offset(16.0F, 22.0F, 0.0F));
        PartDefinition p_glassNE = p_glassN.addOrReplaceChild("glassNE",
                CubeListBuilder.create().texOffs(107, 47).addBox(-3.0F, -14.8F, 7.2F, 6.0F, 15.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_glassE = p_glassN.addOrReplaceChild("glassE",
                CubeListBuilder.create().texOffs(93, 47).addBox(-3.0F, -14.8F, 7.2F, 6.0F, 15.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F, 0.0F));
        PartDefinition p_glassSE = p_glassN.addOrReplaceChild("glassSE",
                CubeListBuilder.create().texOffs(79, 47).addBox(-3.0F, -14.8F, 7.2F, 6.0F, 15.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.356194490192345F, 0.0F));
        PartDefinition p_glassNW = p_glassN.addOrReplaceChild("glassNW",
                CubeListBuilder.create().texOffs(23, 47).addBox(-3.0F, -14.8F, 7.2F, 6.0F, 15.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_glassSW = p_glassN.addOrReplaceChild("glassSW",
                CubeListBuilder.create().texOffs(51, 47).addBox(-3.0F, -14.8F, 7.2F, 6.0F, 15.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.356194490192345F, 0.0F));
        PartDefinition p_glassW = p_glassN.addOrReplaceChild("glassW",
                CubeListBuilder.create().texOffs(37, 47).addBox(-3.0F, -14.8F, 7.2F, 6.0F, 15.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));
        PartDefinition p_tube4 = root.addOrReplaceChild("tube4",
                CubeListBuilder.create().texOffs(102, 0).addBox(3.1F, 3.8F, 4.2F, 4.0F, 12.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F));
        PartDefinition p_injector = root.addOrReplaceChild("injector",
                CubeListBuilder.create().texOffs(51, 22).addBox(15.0F, 5.2F, 0.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tube5 = root.addOrReplaceChild("tube5",
                CubeListBuilder.create().texOffs(74, 12).addBox(7.0F, 3.8F, 4.2F, 8.0F, 2.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F));
        PartDefinition p_vial3Fluid = root.addOrReplaceChild("vial3Fluid",
                CubeListBuilder.create().texOffs(40, 37).addBox(3.5F, 11.0F, 3.0F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_vial2 = root.addOrReplaceChild("vial2",
                CubeListBuilder.create().texOffs(26, 29).addBox(-2.0F, 9.5F, 2.5F, 2.0F, 5.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_injectorFluid = root.addOrReplaceChild("injectorFluid",
                CubeListBuilder.create().texOffs(53, 31).addBox(15.5F, 5.8F, 0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_capN = root.addOrReplaceChild("capN",
                CubeListBuilder.create().texOffs(0, 50).addBox(-3.5F, -18.0F, -8.5F, 7.0F, 2.0F, 3.0F),
                PartPose.offset(16.0F, 23.3F, 0.0F));
        PartDefinition p_capNE = p_capN.addOrReplaceChild("capNE",
                CubeListBuilder.create().texOffs(0, 50).addBox(-3.5F, -18.0F, 5.5F, 7.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_capNW = p_capN.addOrReplaceChild("capNW",
                CubeListBuilder.create().texOffs(0, 50).addBox(-3.5F, -18.0F, 5.5F, 7.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_capE = p_capN.addOrReplaceChild("capE",
                CubeListBuilder.create().texOffs(0, 50).addBox(-3.5F, -18.0F, 5.5F, 7.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F, 0.0F));
        PartDefinition p_capSE = p_capN.addOrReplaceChild("capSE",
                CubeListBuilder.create().texOffs(0, 50).addBox(-3.5F, -18.0F, 5.5F, 7.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.356194490192345F, 0.0F));
        PartDefinition p_capW = p_capN.addOrReplaceChild("capW",
                CubeListBuilder.create().texOffs(0, 50).addBox(-3.5F, -18.0F, 5.5F, 7.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));
        PartDefinition p_capSW = p_capN.addOrReplaceChild("capSW",
                CubeListBuilder.create().texOffs(0, 50).addBox(-3.5F, -18.0F, 5.5F, 7.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.356194490192345F, 0.0F));
        PartDefinition p_tube2 = root.addOrReplaceChild("tube2",
                CubeListBuilder.create().texOffs(83, 0).addBox(-3.4F, 8.5F, 1.7F, 4.0F, 8.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_vial2Fluid = root.addOrReplaceChild("vial2Fluid",
                CubeListBuilder.create().texOffs(28, 37).addBox(-1.5F, 11.0F, 3.0F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_baseN = root.addOrReplaceChild("baseN",
                CubeListBuilder.create().texOffs(0, 50).addBox(-3.5F, 10.0F, -8.5F, 7.0F, 2.0F, 3.0F),
                PartPose.offset(16.0F, 12.0F, 0.0F));
        PartDefinition p_baseE = p_baseN.addOrReplaceChild("baseE",
                CubeListBuilder.create().texOffs(0, 50).addBox(-3.5F, 10.0F, 5.5F, 7.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F, 0.0F));
        PartDefinition p_baseNW = p_baseN.addOrReplaceChild("baseNW",
                CubeListBuilder.create().texOffs(0, 50).addBox(-3.5F, 10.0F, 5.5F, 7.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_baseSW = p_baseN.addOrReplaceChild("baseSW",
                CubeListBuilder.create().texOffs(0, 50).addBox(-3.5F, 10.0F, 5.5F, 7.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.356194490192345F, 0.0F));
        PartDefinition p_baseNE = p_baseN.addOrReplaceChild("baseNE",
                CubeListBuilder.create().texOffs(0, 50).addBox(-3.5F, 10.0F, 5.5F, 7.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_baseW = p_baseN.addOrReplaceChild("baseW",
                CubeListBuilder.create().texOffs(0, 50).addBox(-3.5F, 10.0F, 5.5F, 7.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));
        PartDefinition p_baseSE = p_baseN.addOrReplaceChild("baseSE",
                CubeListBuilder.create().texOffs(0, 50).addBox(-3.5F, 10.0F, 5.5F, 7.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.356194490192345F, 0.0F));
        PartDefinition p_frame = root.addOrReplaceChild("frame",
                CubeListBuilder.create().texOffs(0, 22).addBox(-7.1F, 10.7F, 1.5F, 14.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
