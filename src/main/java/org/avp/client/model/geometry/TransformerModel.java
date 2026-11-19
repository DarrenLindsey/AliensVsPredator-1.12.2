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
 * Geometry converted automatically from the 1.12.2 ModelTransformer by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class TransformerModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "transformer"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart core;
    public final ModelPart base;
    public final ModelPart powerInput;
    public final ModelPart powerOutput;
    public final ModelPart rightSupport;
    public final ModelPart leftSupport;
    public final ModelPart topSupport;

    public TransformerModel(ModelPart root)
    {
        this.root = root;
        this.core = root.getChild("core");
        this.parts.put("core", this.core);
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.powerInput = root.getChild("powerInput");
        this.parts.put("powerInput", this.powerInput);
        this.powerOutput = root.getChild("powerOutput");
        this.parts.put("powerOutput", this.powerOutput);
        this.rightSupport = root.getChild("rightSupport");
        this.parts.put("rightSupport", this.rightSupport);
        this.leftSupport = root.getChild("leftSupport");
        this.parts.put("leftSupport", this.leftSupport);
        this.topSupport = root.getChild("topSupport");
        this.parts.put("topSupport", this.topSupport);
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
        PartDefinition p_core = root.addOrReplaceChild("core",
                CubeListBuilder.create().texOffs(36, 17).addBox(-4.0F, -13.0F, -4.0F, 8.0F, 13.0F, 8.0F),
                PartPose.offset(0.0F, 23.0F, 0.0F));
        PartDefinition p_base = root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(44, 0).addBox(-8.0F, 0.0F, -8.0F, 16.0F, 1.0F, 16.0F),
                PartPose.offset(0.0F, 23.0F, 0.0F));
        PartDefinition p_powerInput = root.addOrReplaceChild("powerInput",
                CubeListBuilder.create().texOffs(44, 0).addBox(-2.0F, -9.0F, -8.0F, 4.0F, 9.0F, 2.0F),
                PartPose.offset(0.0F, 23.0F, 0.0F));
        PartDefinition p_powerOutput = root.addOrReplaceChild("powerOutput",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -9.0F, 6.0F, 4.0F, 9.0F, 2.0F),
                PartPose.offset(0.0F, 23.0F, 0.0F));
        PartDefinition p_rightSupport = root.addOrReplaceChild("rightSupport",
                CubeListBuilder.create().texOffs(12, 0).addBox(4.0F, -15.0F, -2.0F, 4.0F, 16.0F, 4.0F),
                PartPose.offset(0.0F, 23.0F, 0.0F));
        PartDefinition p_leftSupport = root.addOrReplaceChild("leftSupport",
                CubeListBuilder.create().texOffs(28, 0).addBox(-8.0F, -15.0F, -2.0F, 4.0F, 16.0F, 4.0F),
                PartPose.offset(0.0F, 23.0F, 0.0F));
        PartDefinition p_topSupport = root.addOrReplaceChild("topSupport",
                CubeListBuilder.create().texOffs(92, 0).addBox(-4.0F, -15.0F, -2.0F, 8.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, 23.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
