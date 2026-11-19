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
 * Geometry converted automatically from the 1.12.2 ModelCable by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class CableModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "cable"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart cableTop;
    public final ModelPart nodeZ;
    public final ModelPart cableEast;
    public final ModelPart cableSouth;
    public final ModelPart nodeX;
    public final ModelPart cableWest;
    public final ModelPart cableBottom;
    public final ModelPart nodeY;
    public final ModelPart cableNorth;

    public CableModel(ModelPart root)
    {
        this.root = root;
        this.cableTop = root.getChild("cableTop");
        this.parts.put("cableTop", this.cableTop);
        this.nodeZ = root.getChild("nodeZ");
        this.parts.put("nodeZ", this.nodeZ);
        this.cableEast = root.getChild("cableEast");
        this.parts.put("cableEast", this.cableEast);
        this.cableSouth = root.getChild("cableSouth");
        this.parts.put("cableSouth", this.cableSouth);
        this.nodeX = root.getChild("nodeX");
        this.parts.put("nodeX", this.nodeX);
        this.cableWest = root.getChild("cableWest");
        this.parts.put("cableWest", this.cableWest);
        this.cableBottom = root.getChild("cableBottom");
        this.parts.put("cableBottom", this.cableBottom);
        this.nodeY = root.getChild("nodeY");
        this.parts.put("nodeY", this.nodeY);
        this.cableNorth = root.getChild("cableNorth");
        this.parts.put("cableNorth", this.cableNorth);
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
        PartDefinition p_cableTop = root.addOrReplaceChild("cableTop",
                CubeListBuilder.create().texOffs(41, 0).addBox(-0.5F, -7.5F, -0.5F, 1.0F, 7.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_nodeZ = root.addOrReplaceChild("nodeZ",
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_cableEast = root.addOrReplaceChild("cableEast",
                CubeListBuilder.create().texOffs(41, 12).addBox(-8.0F, 0.0F, -0.5F, 7.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_cableSouth = root.addOrReplaceChild("cableSouth",
                CubeListBuilder.create().texOffs(20, 0).addBox(-0.5F, 0.0F, 1.0F, 1.0F, 1.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_nodeX = root.addOrReplaceChild("nodeX",
                CubeListBuilder.create().texOffs(0, 4).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_cableWest = root.addOrReplaceChild("cableWest",
                CubeListBuilder.create().texOffs(0, 12).addBox(1.0F, 0.0F, -0.5F, 7.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_cableBottom = root.addOrReplaceChild("cableBottom",
                CubeListBuilder.create().texOffs(48, 0).addBox(-0.5F, 1.5F, -0.5F, 1.0F, 7.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_nodeY = root.addOrReplaceChild("nodeY",
                CubeListBuilder.create().texOffs(0, 4).addBox(-0.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F));
        PartDefinition p_cableNorth = root.addOrReplaceChild("cableNorth",
                CubeListBuilder.create().texOffs(20, 16).addBox(-0.5F, 0.0F, -8.0F, 1.0F, 1.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
