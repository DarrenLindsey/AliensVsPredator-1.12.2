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
 * Geometry converted automatically from the 1.12.2 ModelLightPanel by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class LightPanelModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "light_panel"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart side0;
    public final ModelPart side1;
    public final ModelPart side2;
    public final ModelPart side3;
    public final ModelPart side4;
    public final ModelPart side5;
    public final ModelPart side6;
    public final ModelPart side7;
    public final ModelPart side8;
    public final ModelPart side9;
    public final ModelPart side10;
    public final ModelPart side11;

    public LightPanelModel(ModelPart root)
    {
        this.root = root;
        this.side0 = root.getChild("side0");
        this.parts.put("side0", this.side0);
        this.side1 = root.getChild("side1");
        this.parts.put("side1", this.side1);
        this.side2 = root.getChild("side2");
        this.parts.put("side2", this.side2);
        this.side3 = root.getChild("side3");
        this.parts.put("side3", this.side3);
        this.side4 = root.getChild("side4");
        this.parts.put("side4", this.side4);
        this.side5 = root.getChild("side5");
        this.parts.put("side5", this.side5);
        this.side6 = root.getChild("side6");
        this.parts.put("side6", this.side6);
        this.side7 = root.getChild("side7");
        this.parts.put("side7", this.side7);
        this.side8 = root.getChild("side8");
        this.parts.put("side8", this.side8);
        this.side9 = root.getChild("side9");
        this.parts.put("side9", this.side9);
        this.side10 = root.getChild("side10");
        this.parts.put("side10", this.side10);
        this.side11 = root.getChild("side11");
        this.parts.put("side11", this.side11);
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
        PartDefinition p_side0 = root.addOrReplaceChild("side0",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, 6.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offset(0.0F, 8.0F, 0.0F));
        PartDefinition p_side1 = root.addOrReplaceChild("side1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, -2.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offset(0.0F, 8.0F, 0.0F));
        PartDefinition p_side2 = root.addOrReplaceChild("side2",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, 2.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offset(0.0F, 8.0F, 0.0F));
        PartDefinition p_side3 = root.addOrReplaceChild("side3",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, -6.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offset(0.0F, 8.0F, 0.0F));
        PartDefinition p_side4 = root.addOrReplaceChild("side4",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, -2.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 1.570796F, 0.0F));
        PartDefinition p_side5 = root.addOrReplaceChild("side5",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, 2.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 1.570796F, 0.0F));
        PartDefinition p_side6 = root.addOrReplaceChild("side6",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, 6.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 1.570796F, 0.0F));
        PartDefinition p_side7 = root.addOrReplaceChild("side7",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, -6.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.0F, 1.570796F, 0.0F));
        PartDefinition p_side8 = root.addOrReplaceChild("side8",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 1.570796F, 0.0F, 0.0F));
        PartDefinition p_side9 = root.addOrReplaceChild("side9",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -2.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 1.570796F, 0.0F, 0.0F));
        PartDefinition p_side10 = root.addOrReplaceChild("side10",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -10.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 1.570796F, 0.0F, 0.0F));
        PartDefinition p_side11 = root.addOrReplaceChild("side11",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -14.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 1.570796F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
