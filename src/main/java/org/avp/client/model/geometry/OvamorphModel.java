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
 * Geometry converted automatically from the 1.12.2 ModelOvamorph by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class OvamorphModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "ovamorph"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart rFrontLobe2;
    public final ModelPart rFrontLobe;
    public final ModelPart lBackLobe2;
    public final ModelPart lBackLobe;
    public final ModelPart base;
    public final ModelPart lFrontLobe2;
    public final ModelPart lFrontLobe;
    public final ModelPart center;
    public final ModelPart rBackLobe2;
    public final ModelPart rBackLobe;

    public OvamorphModel(ModelPart root)
    {
        this.root = root;
        this.rFrontLobe2 = root.getChild("rFrontLobe2");
        this.parts.put("rFrontLobe2", this.rFrontLobe2);
        this.rFrontLobe = this.rFrontLobe2.getChild("rFrontLobe");
        this.parts.put("rFrontLobe", this.rFrontLobe);
        this.lBackLobe2 = root.getChild("lBackLobe2");
        this.parts.put("lBackLobe2", this.lBackLobe2);
        this.lBackLobe = this.lBackLobe2.getChild("lBackLobe");
        this.parts.put("lBackLobe", this.lBackLobe);
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.lFrontLobe2 = root.getChild("lFrontLobe2");
        this.parts.put("lFrontLobe2", this.lFrontLobe2);
        this.lFrontLobe = this.lFrontLobe2.getChild("lFrontLobe");
        this.parts.put("lFrontLobe", this.lFrontLobe);
        this.center = root.getChild("center");
        this.parts.put("center", this.center);
        this.rBackLobe2 = root.getChild("rBackLobe2");
        this.parts.put("rBackLobe2", this.rBackLobe2);
        this.rBackLobe = this.rBackLobe2.getChild("rBackLobe");
        this.parts.put("rBackLobe", this.rBackLobe);
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
        PartDefinition p_rFrontLobe2 = root.addOrReplaceChild("rFrontLobe2",
                CubeListBuilder.create().texOffs(35, 12).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F),
                PartPose.offset(-1.5F, 18.0F, -1.5F));
        PartDefinition p_rFrontLobe = p_rFrontLobe2.addOrReplaceChild("rFrontLobe",
                CubeListBuilder.create().texOffs(37, 0).addBox(-0.5F, -3.0F, -0.5F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lBackLobe2 = root.addOrReplaceChild("lBackLobe2",
                CubeListBuilder.create().texOffs(22, 18).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F),
                PartPose.offset(1.5F, 18.0F, 1.5F));
        PartDefinition p_lBackLobe = p_lBackLobe2.addOrReplaceChild("lBackLobe",
                CubeListBuilder.create().texOffs(28, 6).addBox(-1.5F, -3.0F, -1.5F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_base = root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(0, 15).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition p_lFrontLobe2 = root.addOrReplaceChild("lFrontLobe2",
                CubeListBuilder.create().texOffs(22, 12).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F),
                PartPose.offset(1.5F, 18.0F, -1.5F));
        PartDefinition p_lFrontLobe = p_lFrontLobe2.addOrReplaceChild("lFrontLobe",
                CubeListBuilder.create().texOffs(28, 0).addBox(-1.5F, -3.0F, -0.5F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_center = root.addOrReplaceChild("center",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -6.0F, -3.5F, 7.0F, 5.0F, 7.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition p_rBackLobe2 = root.addOrReplaceChild("rBackLobe2",
                CubeListBuilder.create().texOffs(35, 18).addBox(-1.5F, -2.0F, -1.5F, 3.0F, 2.0F, 3.0F),
                PartPose.offset(-1.5F, 18.0F, 1.5F));
        PartDefinition p_rBackLobe = p_rBackLobe2.addOrReplaceChild("rBackLobe",
                CubeListBuilder.create().texOffs(37, 6).addBox(-0.5F, -3.0F, -1.5F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
