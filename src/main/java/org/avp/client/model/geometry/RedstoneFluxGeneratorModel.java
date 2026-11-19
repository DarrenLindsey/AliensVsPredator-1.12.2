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
 * Geometry converted automatically from the 1.12.2 ModelRedstoneFluxGenerator by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class RedstoneFluxGeneratorModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "redstone_flux_generator"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart base;
    public final ModelPart electicityNode;
    public final ModelPart fluxNode;
    public final ModelPart coil;
    public final ModelPart fluxFaceplate;
    public final ModelPart fluxLightOutput;
    public final ModelPart electricityLightInput;
    public final ModelPart fluxLightInput;
    public final ModelPart electricityLightOutput;

    public RedstoneFluxGeneratorModel(ModelPart root)
    {
        this.root = root;
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.electicityNode = this.base.getChild("electicityNode");
        this.parts.put("electicityNode", this.electicityNode);
        this.fluxNode = this.base.getChild("fluxNode");
        this.parts.put("fluxNode", this.fluxNode);
        this.coil = this.base.getChild("coil");
        this.parts.put("coil", this.coil);
        this.fluxFaceplate = this.base.getChild("fluxFaceplate");
        this.parts.put("fluxFaceplate", this.fluxFaceplate);
        this.fluxLightOutput = root.getChild("fluxLightOutput");
        this.parts.put("fluxLightOutput", this.fluxLightOutput);
        this.electricityLightInput = this.fluxLightOutput.getChild("electricityLightInput");
        this.parts.put("electricityLightInput", this.electricityLightInput);
        this.fluxLightInput = root.getChild("fluxLightInput");
        this.parts.put("fluxLightInput", this.fluxLightInput);
        this.electricityLightOutput = this.fluxLightInput.getChild("electricityLightOutput");
        this.parts.put("electricityLightOutput", this.electricityLightOutput);
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
                CubeListBuilder.create().texOffs(0, 12).addBox(-8.0F, -0.5F, -8.0F, 16.0F, 1.0F, 16.0F),
                PartPose.offset(0.0F, 23.5F, 0.0F));
        PartDefinition p_electicityNode = p_base.addOrReplaceChild("electicityNode",
                CubeListBuilder.create().texOffs(21, 0).addBox(-2.0F, -9.5F, 6.0F, 4.0F, 9.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_fluxNode = p_base.addOrReplaceChild("fluxNode",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -9.5F, -8.0F, 4.0F, 9.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_coil = p_base.addOrReplaceChild("coil",
                CubeListBuilder.create().texOffs(0, 31).addBox(-4.0F, -13.5F, 0.0F, 8.0F, 13.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_fluxFaceplate = p_base.addOrReplaceChild("fluxFaceplate",
                CubeListBuilder.create().texOffs(30, 31).addBox(-4.5F, -14.5F, -3.8F, 9.0F, 14.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_fluxLightOutput = root.addOrReplaceChild("fluxLightOutput",
                CubeListBuilder.create().texOffs(34, 0).addBox(-0.5F, -0.5F, -8.2F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 14.4F, 0.0F));
        PartDefinition p_electricityLightInput = p_fluxLightOutput.addOrReplaceChild("electricityLightInput",
                CubeListBuilder.create().texOffs(13, 0).addBox(-0.5F, -0.5F, 7.2F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_fluxLightInput = root.addOrReplaceChild("fluxLightInput",
                CubeListBuilder.create().texOffs(34, 5).addBox(-0.5F, -0.5F, -8.2F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 14.4F, 0.0F));
        PartDefinition p_electricityLightOutput = p_fluxLightInput.addOrReplaceChild("electricityLightOutput",
                CubeListBuilder.create().texOffs(13, 5).addBox(-0.5F, -0.5F, 7.2F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }
}
