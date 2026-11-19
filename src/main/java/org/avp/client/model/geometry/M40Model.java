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
 * Geometry converted automatically from the 1.12.2 ModelM40 by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class M40Model implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "m40"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart cap;
    public final ModelPart core;
    public final ModelPart ridge1;
    public final ModelPart ridge2;
    public final ModelPart grip;
    public final ModelPart bottom;

    public M40Model(ModelPart root)
    {
        this.root = root;
        this.cap = root.getChild("cap");
        this.parts.put("cap", this.cap);
        this.core = root.getChild("core");
        this.parts.put("core", this.core);
        this.ridge1 = root.getChild("ridge1");
        this.parts.put("ridge1", this.ridge1);
        this.ridge2 = root.getChild("ridge2");
        this.parts.put("ridge2", this.ridge2);
        this.grip = root.getChild("grip");
        this.parts.put("grip", this.grip);
        this.bottom = root.getChild("bottom");
        this.parts.put("bottom", this.bottom);
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
        PartDefinition p_cap = root.addOrReplaceChild("cap",
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -3.2F, -0.5F, 3.0F, 3.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_core = root.addOrReplaceChild("core",
                CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, -1.0F, 0.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_ridge1 = root.addOrReplaceChild("ridge1",
                CubeListBuilder.create().texOffs(14, 0).addBox(-0.5F, 0.0F, -0.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_ridge2 = root.addOrReplaceChild("ridge2",
                CubeListBuilder.create().texOffs(28, 0).addBox(-0.5F, 1.3F, -0.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_grip = root.addOrReplaceChild("grip",
                CubeListBuilder.create().texOffs(14, 8).addBox(-0.5F, 2.5F, -0.5F, 3.0F, 4.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_bottom = root.addOrReplaceChild("bottom",
                CubeListBuilder.create().texOffs(28, 8).addBox(-0.5F, 7.0F, -0.5F, 3.0F, 2.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
