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
 * Geometry converted automatically from the 1.12.2 ModelCCFL by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class CCFLModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "c_c_f_l"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart middleRod;
    public final ModelPart lightTube;
    public final ModelPart lCap;
    public final ModelPart lightAura;
    public final ModelPart rCap;

    public CCFLModel(ModelPart root)
    {
        this.root = root;
        this.middleRod = root.getChild("middleRod");
        this.parts.put("middleRod", this.middleRod);
        this.lightTube = root.getChild("lightTube");
        this.parts.put("lightTube", this.lightTube);
        this.lCap = root.getChild("lCap");
        this.parts.put("lCap", this.lCap);
        this.lightAura = root.getChild("lightAura");
        this.parts.put("lightAura", this.lightAura);
        this.rCap = root.getChild("rCap");
        this.parts.put("rCap", this.rCap);
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
        PartDefinition p_middleRod = root.addOrReplaceChild("middleRod",
                CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, -0.5F, -0.5F, 15.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 9.0F, 0.0F));
        PartDefinition p_lightTube = root.addOrReplaceChild("lightTube",
                CubeListBuilder.create().texOffs(0, 5).addBox(-6.0F, -0.5F, -0.5F, 12.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 9.0F, 0.0F));
        PartDefinition p_lCap = root.addOrReplaceChild("lCap",
                CubeListBuilder.create().texOffs(0, 9).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F),
                PartPose.offset(-7.3F, 9.0F, 0.0F));
        PartDefinition p_lightAura = root.addOrReplaceChild("lightAura",
                CubeListBuilder.create().texOffs(0, 18).addBox(-5.5F, -0.5F, -0.5F, 11.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 9.0F, 0.0F));
        PartDefinition p_rCap = root.addOrReplaceChild("rCap",
                CubeListBuilder.create().texOffs(0, 9).mirror().addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F),
                PartPose.offset(7.3F, 9.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
