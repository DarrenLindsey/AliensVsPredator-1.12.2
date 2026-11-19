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
 * Geometry converted automatically from the 1.12.2 ModelPowercell by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class PowercellModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "powercell"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart cell;
    public final ModelPart connector1;
    public final ModelPart connector2;
    public final ModelPart connector3;
    public final ModelPart connector4;

    public PowercellModel(ModelPart root)
    {
        this.root = root;
        this.cell = root.getChild("cell");
        this.parts.put("cell", this.cell);
        this.connector1 = root.getChild("connector1");
        this.parts.put("connector1", this.connector1);
        this.connector2 = root.getChild("connector2");
        this.parts.put("connector2", this.connector2);
        this.connector3 = root.getChild("connector3");
        this.parts.put("connector3", this.connector3);
        this.connector4 = root.getChild("connector4");
        this.parts.put("connector4", this.connector4);
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
        PartDefinition p_cell = root.addOrReplaceChild("cell",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 16.0F, 8.0F),
                PartPose.offset(0.0F, 8.0F, 0.0F));
        PartDefinition p_connector1 = root.addOrReplaceChild("connector1",
                CubeListBuilder.create().texOffs(33, 0).addBox(2.0F, 6.0F, -2.0F, 6.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 1.570796F, 0.0F));
        PartDefinition p_connector2 = root.addOrReplaceChild("connector2",
                CubeListBuilder.create().texOffs(33, 9).addBox(2.0F, 6.0F, -2.0F, 6.0F, 4.0F, 4.0F),
                PartPose.offset(0.0F, 8.0F, 0.0F));
        PartDefinition p_connector3 = root.addOrReplaceChild("connector3",
                CubeListBuilder.create().texOffs(33, 18).addBox(2.0F, 6.0F, -2.0F, 6.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 3.141593F, 0.0F));
        PartDefinition p_connector4 = root.addOrReplaceChild("connector4",
                CubeListBuilder.create().texOffs(54, 0).addBox(2.0F, 6.0F, -2.0F, 6.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 4.712389F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
