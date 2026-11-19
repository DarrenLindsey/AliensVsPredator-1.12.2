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
 * Geometry converted automatically from the 1.12.2 ModelSevastopolBlastdoor by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class SevastopolBlastdoorModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "sevastopol_blastdoor"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart frameL;
    public final ModelPart frameB;
    public final ModelPart frameBL;
    public final ModelPart frameBL2;
    public final ModelPart frameBR2;
    public final ModelPart frameBR;
    public final ModelPart frameT;
    public final ModelPart frameTR;
    public final ModelPart frameTL;
    public final ModelPart frameTR2;
    public final ModelPart frameTL2;
    public final ModelPart frameR;
    public final ModelPart doorMain;
    public final ModelPart doorTop;
    public final ModelPart doorTopL;
    public final ModelPart doorTopR;
    public final ModelPart keypadF;
    public final ModelPart indicatorLightF;
    public final ModelPart keypadB;
    public final ModelPart indicatorLightB;
    public final ModelPart doorBotton;
    public final ModelPart doorBottomL;
    public final ModelPart doorBottomR;

    public SevastopolBlastdoorModel(ModelPart root)
    {
        this.root = root;
        this.frameL = root.getChild("frameL");
        this.parts.put("frameL", this.frameL);
        this.frameB = this.frameL.getChild("frameB");
        this.parts.put("frameB", this.frameB);
        this.frameBL = this.frameB.getChild("frameBL");
        this.parts.put("frameBL", this.frameBL);
        this.frameBL2 = this.frameB.getChild("frameBL2");
        this.parts.put("frameBL2", this.frameBL2);
        this.frameBR2 = this.frameB.getChild("frameBR2");
        this.parts.put("frameBR2", this.frameBR2);
        this.frameBR = this.frameB.getChild("frameBR");
        this.parts.put("frameBR", this.frameBR);
        this.frameT = this.frameL.getChild("frameT");
        this.parts.put("frameT", this.frameT);
        this.frameTR = this.frameT.getChild("frameTR");
        this.parts.put("frameTR", this.frameTR);
        this.frameTL = this.frameT.getChild("frameTL");
        this.parts.put("frameTL", this.frameTL);
        this.frameTR2 = this.frameT.getChild("frameTR2");
        this.parts.put("frameTR2", this.frameTR2);
        this.frameTL2 = this.frameT.getChild("frameTL2");
        this.parts.put("frameTL2", this.frameTL2);
        this.frameR = this.frameL.getChild("frameR");
        this.parts.put("frameR", this.frameR);
        this.doorMain = root.getChild("doorMain");
        this.parts.put("doorMain", this.doorMain);
        this.doorTop = this.doorMain.getChild("doorTop");
        this.parts.put("doorTop", this.doorTop);
        this.doorTopL = this.doorTop.getChild("doorTopL");
        this.parts.put("doorTopL", this.doorTopL);
        this.doorTopR = this.doorTop.getChild("doorTopR");
        this.parts.put("doorTopR", this.doorTopR);
        this.keypadF = this.doorMain.getChild("keypadF");
        this.parts.put("keypadF", this.keypadF);
        this.indicatorLightF = this.keypadF.getChild("indicatorLightF");
        this.parts.put("indicatorLightF", this.indicatorLightF);
        this.keypadB = this.keypadF.getChild("keypadB");
        this.parts.put("keypadB", this.keypadB);
        this.indicatorLightB = this.keypadB.getChild("indicatorLightB");
        this.parts.put("indicatorLightB", this.indicatorLightB);
        this.doorBotton = this.doorMain.getChild("doorBotton");
        this.parts.put("doorBotton", this.doorBotton);
        this.doorBottomL = this.doorBotton.getChild("doorBottomL");
        this.parts.put("doorBottomL", this.doorBottomL);
        this.doorBottomR = this.doorBotton.getChild("doorBottomR");
        this.parts.put("doorBottomR", this.doorBottomR);
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
        PartDefinition p_frameL = root.addOrReplaceChild("frameL",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -13.0F, -8.0F, 4.0F, 26.0F, 16.0F),
                PartPose.offset(-6.0F, 0.0F, 0.0F));
        PartDefinition p_frameB = p_frameL.addOrReplaceChild("frameB",
                CubeListBuilder.create().texOffs(112, 1).addBox(-8.0F, -2.5F, -8.0F, 16.0F, 4.0F, 16.0F),
                PartPose.offset(14.0F, 22.5F, 0.0F));
        PartDefinition p_frameBL = p_frameB.addOrReplaceChild("frameBL",
                CubeListBuilder.create().texOffs(111, 67).addBox(-16.0F, -9.5F, -8.0F, 8.0F, 11.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_frameBL2 = p_frameB.addOrReplaceChild("frameBL2",
                CubeListBuilder.create().texOffs(166, 67).addBox(-1.83F, -9.7F, -8.0F, 4.0F, 9.0F, 16.0F),
                PartPose.offsetAndRotation(-4.4F, 0.3F, 0.0F, 0.0F, 0.0F, -0.5918411493512771F));
        PartDefinition p_frameBR2 = p_frameB.addOrReplaceChild("frameBR2",
                CubeListBuilder.create().texOffs(166, 67).mirror().addBox(-2.18F, -9.7F, -8.0F, 4.0F, 9.0F, 16.0F),
                PartPose.offsetAndRotation(4.4F, 0.3F, 0.0F, 0.0F, 0.0F, 0.5918411493512771F));
        PartDefinition p_frameBR = p_frameB.addOrReplaceChild("frameBR",
                CubeListBuilder.create().texOffs(111, 67).mirror().addBox(8.0F, -9.5F, -8.0F, 8.0F, 11.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_frameT = p_frameL.addOrReplaceChild("frameT",
                CubeListBuilder.create().texOffs(44, 1).addBox(-8.0F, -2.5F, -8.0F, 16.0F, 4.0F, 16.0F),
                PartPose.offset(14.0F, -21.5F, 0.0F));
        PartDefinition p_frameTR = p_frameT.addOrReplaceChild("frameTR",
                CubeListBuilder.create().texOffs(111, 33).mirror().addBox(8.0F, -2.5F, -8.0F, 8.0F, 11.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_frameTL = p_frameT.addOrReplaceChild("frameTL",
                CubeListBuilder.create().texOffs(111, 33).addBox(-16.0F, -2.5F, -8.0F, 8.0F, 11.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_frameTR2 = p_frameT.addOrReplaceChild("frameTR2",
                CubeListBuilder.create().texOffs(166, 31).mirror().addBox(-1.23F, 0.8F, -8.0F, 4.0F, 8.0F, 16.0F),
                PartPose.offsetAndRotation(4.4F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5918411493512771F));
        PartDefinition p_frameTL2 = p_frameT.addOrReplaceChild("frameTL2",
                CubeListBuilder.create().texOffs(166, 31).addBox(-1.23F, -0.1F, -8.0F, 4.0F, 8.0F, 16.0F),
                PartPose.offsetAndRotation(-6.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5918411493512771F));
        PartDefinition p_frameR = p_frameL.addOrReplaceChild("frameR",
                CubeListBuilder.create().texOffs(0, 0).mirror().addBox(6.0F, -13.0F, -8.0F, 4.0F, 26.0F, 16.0F),
                PartPose.offset(20.0F, 0.0F, 0.0F));
        PartDefinition p_doorMain = root.addOrReplaceChild("doorMain",
                CubeListBuilder.create().texOffs(0, 48).addBox(-4.0F, -2.0F, -4.0F, 24.0F, 26.0F, 8.0F),
                PartPose.offset(0.0F, -11.0F, 0.0F));
        PartDefinition p_doorTop = p_doorMain.addOrReplaceChild("doorTop",
                CubeListBuilder.create().texOffs(73, 46).addBox(4.0F, -8.3F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.offset(0.0F, -1.1F, 0.0F));
        PartDefinition p_doorTopL = p_doorTop.addOrReplaceChild("doorTopL",
                CubeListBuilder.create().texOffs(73, 85).addBox(0.5F, 1.1F, -4.0F, 4.0F, 9.0F, 8.0F),
                PartPose.offsetAndRotation(4.5F, -9.0F, 0.0F, 0.0F, 0.0F, 0.5918411493512771F));
        PartDefinition p_doorTopR = p_doorTop.addOrReplaceChild("doorTopR",
                CubeListBuilder.create().texOffs(73, 85).mirror().addBox(-4.0F, 1.4F, -4.0F, 4.0F, 9.0F, 8.0F),
                PartPose.offsetAndRotation(11.0F, -9.0F, 0.0F, 0.0F, 0.0F, -0.5918411493512771F));
        PartDefinition p_keypadF = p_doorMain.addOrReplaceChild("keypadF",
                CubeListBuilder.create().texOffs(0, 91).addBox(-3.0F, -4.0F, -1.0F, 6.0F, 8.0F, 1.0F),
                PartPose.offset(8.0F, 9.9F, -3.5F));
        PartDefinition p_indicatorLightF = p_keypadF.addOrReplaceChild("indicatorLightF",
                CubeListBuilder.create().texOffs(0, 106).addBox(-3.0F, 4.6F, -1.0F, 6.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_keypadB = p_keypadF.addOrReplaceChild("keypadB",
                CubeListBuilder.create().texOffs(19, 91).addBox(-3.0F, -4.0F, 1.0F, 6.0F, 8.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 6.0F));
        PartDefinition p_indicatorLightB = p_keypadB.addOrReplaceChild("indicatorLightB",
                CubeListBuilder.create().texOffs(19, 106).addBox(-3.0F, 4.6F, 1.0F, 6.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_doorBotton = p_doorMain.addOrReplaceChild("doorBotton",
                CubeListBuilder.create().texOffs(73, 65).addBox(4.0F, 22.8F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.offset(0.0F, 0.2F, 0.0F));
        PartDefinition p_doorBottomL = p_doorBotton.addOrReplaceChild("doorBottomL",
                CubeListBuilder.create().texOffs(73, 106).addBox(-0.1F, -9.6F, -4.0F, 4.0F, 9.0F, 8.0F),
                PartPose.offsetAndRotation(4.5F, 30.9F, 0.0F, 0.0F, 0.0F, -0.5918411493512771F));
        PartDefinition p_doorBottomR = p_doorBotton.addOrReplaceChild("doorBottomR",
                CubeListBuilder.create().texOffs(73, 106).mirror().addBox(-3.4F, -9.7F, -4.0F, 4.0F, 9.0F, 8.0F),
                PartPose.offsetAndRotation(11.0F, 30.7F, 0.0F, 0.0F, 0.0F, 0.5918411493512771F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
