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
 * Geometry converted automatically from the 1.12.2 ModelDracoOvamorph by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class DracoOvamorphModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "draco_ovamorph"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart lFrontLobe2;
    public final ModelPart lFrontLobe2Child;
    public final ModelPart lBackLobe2;
    public final ModelPart lBackLobe2Child;
    public final ModelPart rFrontLobe2;
    public final ModelPart rFrontLobe2Child;
    public final ModelPart rBackLobe2;
    public final ModelPart rBackLobe2Child;
    public final ModelPart base;
    public final ModelPart center;
    public final ModelPart webbing3;
    public final ModelPart webbing3b;
    public final ModelPart sideSpikes1;
    public final ModelPart sideSpikes2;
    public final ModelPart webbing1;
    public final ModelPart webbing1b;
    public final ModelPart sideSpikes3;
    public final ModelPart webbing4;
    public final ModelPart webbing4b;
    public final ModelPart sideSpikes4;
    public final ModelPart webbing2;
    public final ModelPart webbing2b;

    public DracoOvamorphModel(ModelPart root)
    {
        this.root = root;
        this.lFrontLobe2 = root.getChild("lFrontLobe2");
        this.parts.put("lFrontLobe2", this.lFrontLobe2);
        this.lFrontLobe2Child = this.lFrontLobe2.getChild("lFrontLobe2Child");
        this.parts.put("lFrontLobe2Child", this.lFrontLobe2Child);
        this.lBackLobe2 = root.getChild("lBackLobe2");
        this.parts.put("lBackLobe2", this.lBackLobe2);
        this.lBackLobe2Child = this.lBackLobe2.getChild("lBackLobe2Child");
        this.parts.put("lBackLobe2Child", this.lBackLobe2Child);
        this.rFrontLobe2 = root.getChild("rFrontLobe2");
        this.parts.put("rFrontLobe2", this.rFrontLobe2);
        this.rFrontLobe2Child = this.rFrontLobe2.getChild("rFrontLobe2Child");
        this.parts.put("rFrontLobe2Child", this.rFrontLobe2Child);
        this.rBackLobe2 = root.getChild("rBackLobe2");
        this.parts.put("rBackLobe2", this.rBackLobe2);
        this.rBackLobe2Child = this.rBackLobe2.getChild("rBackLobe2Child");
        this.parts.put("rBackLobe2Child", this.rBackLobe2Child);
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.center = root.getChild("center");
        this.parts.put("center", this.center);
        this.webbing3 = this.center.getChild("webbing3");
        this.parts.put("webbing3", this.webbing3);
        this.webbing3b = this.webbing3.getChild("webbing3b");
        this.parts.put("webbing3b", this.webbing3b);
        this.sideSpikes1 = this.center.getChild("sideSpikes1");
        this.parts.put("sideSpikes1", this.sideSpikes1);
        this.sideSpikes2 = this.center.getChild("sideSpikes2");
        this.parts.put("sideSpikes2", this.sideSpikes2);
        this.webbing1 = this.center.getChild("webbing1");
        this.parts.put("webbing1", this.webbing1);
        this.webbing1b = this.webbing1.getChild("webbing1b");
        this.parts.put("webbing1b", this.webbing1b);
        this.sideSpikes3 = this.center.getChild("sideSpikes3");
        this.parts.put("sideSpikes3", this.sideSpikes3);
        this.webbing4 = this.center.getChild("webbing4");
        this.parts.put("webbing4", this.webbing4);
        this.webbing4b = this.webbing4.getChild("webbing4b");
        this.parts.put("webbing4b", this.webbing4b);
        this.sideSpikes4 = this.center.getChild("sideSpikes4");
        this.parts.put("sideSpikes4", this.sideSpikes4);
        this.webbing2 = this.center.getChild("webbing2");
        this.parts.put("webbing2", this.webbing2);
        this.webbing2b = this.webbing2.getChild("webbing2b");
        this.parts.put("webbing2b", this.webbing2b);
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
        PartDefinition p_lFrontLobe2 = root.addOrReplaceChild("lFrontLobe2",
                CubeListBuilder.create().texOffs(22, 12).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(1.5F, 18.0F, -1.5F, 1.1318434189888649E-06F, 0.0F, 1.1318434189888649E-06F));
        PartDefinition p_lFrontLobe2Child = p_lFrontLobe2.addOrReplaceChild("lFrontLobe2Child",
                CubeListBuilder.create().texOffs(28, 0).addBox(-1.5F, -3.0F, -0.5F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 5.093295385449892E-07F, 0.0F, 5.093295385449892E-07F));
        PartDefinition p_lBackLobe2 = root.addOrReplaceChild("lBackLobe2",
                CubeListBuilder.create().texOffs(22, 18).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(1.5F, 18.0F, 1.5F, -1.1318434189888649E-06F, 0.0F, 1.1318434189888649E-06F));
        PartDefinition p_lBackLobe2Child = p_lBackLobe2.addOrReplaceChild("lBackLobe2Child",
                CubeListBuilder.create().texOffs(28, 6).addBox(-1.5F, -3.0F, -1.5F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -5.093295385449892E-07F, 0.0F, 5.093295385449892E-07F));
        PartDefinition p_rFrontLobe2 = root.addOrReplaceChild("rFrontLobe2",
                CubeListBuilder.create().texOffs(35, 12).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-1.5F, 18.0F, -1.5F, 1.1318434189888649E-06F, 0.0F, -1.1318434189888649E-06F));
        PartDefinition p_rFrontLobe2Child = p_rFrontLobe2.addOrReplaceChild("rFrontLobe2Child",
                CubeListBuilder.create().texOffs(37, 0).addBox(-0.5F, -3.0F, -0.5F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 5.093295385449892E-07F, 0.0F, -5.093295385449892E-07F));
        PartDefinition p_rBackLobe2 = root.addOrReplaceChild("rBackLobe2",
                CubeListBuilder.create().texOffs(35, 18).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-1.5F, 18.0F, 1.5F, -1.1318434189888649E-06F, 0.0F, -1.1318434189888649E-06F));
        PartDefinition p_rBackLobe2Child = p_rBackLobe2.addOrReplaceChild("rBackLobe2Child",
                CubeListBuilder.create().texOffs(37, 6).addBox(-0.5F, -3.0F, -1.5F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -5.093295385449892E-07F, 0.0F, -5.093295385449892E-07F));
        PartDefinition p_base = root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(0, 15).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition p_center = root.addOrReplaceChild("center",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -6.0F, -3.5F, 7.0F, 5.0F, 7.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition p_webbing3 = p_center.addOrReplaceChild("webbing3",
                CubeListBuilder.create().texOffs(47, 0).addBox(0.0F, -0.1F, -3.5F, 0.0F, 2.0F, 7.0F),
                PartPose.offsetAndRotation(3.5F, -1.2F, 0.0F, 0.0F, 0.0F, -0.9560913642424937F));
        PartDefinition p_webbing3b = p_webbing3.addOrReplaceChild("webbing3b",
                CubeListBuilder.create().texOffs(44, 11).addBox(1.4F, 1.0F, -3.5F, 3.0F, 0.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.9841911651996024F));
        PartDefinition p_sideSpikes1 = p_center.addOrReplaceChild("sideSpikes1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-5.7F, -6.0F, 0.1F, 1.0F, 6.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.8196066167365371F, 0.0F));
        PartDefinition p_sideSpikes2 = p_center.addOrReplaceChild("sideSpikes2",
                CubeListBuilder.create().texOffs(0, 0).addBox(-5.7F, -6.0F, -0.2F, 1.0F, 6.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.8196066167365371F, 0.0F));
        PartDefinition p_webbing1 = p_center.addOrReplaceChild("webbing1",
                CubeListBuilder.create().texOffs(26, 25).addBox(-3.5F, 0.0F, -0.1F, 7.0F, 2.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, -1.2F, -3.4F, -0.9560913642424937F, 0.0F, 0.0F));
        PartDefinition p_webbing1b = p_webbing1.addOrReplaceChild("webbing1b",
                CubeListBuilder.create().texOffs(39, 25).addBox(-3.5F, 1.0F, -4.6F, 7.0F, 0.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.9841911651996024F, 0.0F, 0.0F));
        PartDefinition p_sideSpikes3 = p_center.addOrReplaceChild("sideSpikes3",
                CubeListBuilder.create().texOffs(0, 0).addBox(-5.7F, -6.0F, 0.1F, 1.0F, 6.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.367539130330308F, 0.0F));
        PartDefinition p_webbing4 = p_center.addOrReplaceChild("webbing4",
                CubeListBuilder.create().texOffs(47, 0).addBox(0.0F, -0.2F, -3.5F, 0.0F, 2.0F, 7.0F),
                PartPose.offsetAndRotation(-3.5F, -1.2F, 0.0F, 0.0F, 0.0F, 0.9560913642424937F));
        PartDefinition p_webbing4b = p_webbing4.addOrReplaceChild("webbing4b",
                CubeListBuilder.create().texOffs(51, 18).addBox(-4.4F, 1.0F, -3.5F, 3.0F, 0.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.9841911651996024F));
        PartDefinition p_sideSpikes4 = p_center.addOrReplaceChild("sideSpikes4",
                CubeListBuilder.create().texOffs(0, 0).addBox(-5.7F, -6.0F, -0.1F, 1.0F, 6.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.367539130330308F, 0.0F));
        PartDefinition p_webbing2 = p_center.addOrReplaceChild("webbing2",
                CubeListBuilder.create().texOffs(26, 25).addBox(-3.5F, 0.0F, 0.1F, 7.0F, 2.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, -1.2F, 3.4F, 0.9560913642424937F, 0.0F, 0.0F));
        PartDefinition p_webbing2b = p_webbing2.addOrReplaceChild("webbing2b",
                CubeListBuilder.create().texOffs(39, 29).addBox(-3.5F, 1.0F, 1.6F, 7.0F, 0.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.9841911651996024F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
