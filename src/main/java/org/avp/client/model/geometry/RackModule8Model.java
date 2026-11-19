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
 * Geometry converted automatically from the 1.12.2 ModelRackModule8 by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class RackModule8Model implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "rack_module8"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart lhandle;
    public final ModelPart light04;
    public final ModelPart light05;
    public final ModelPart light06;
    public final ModelPart light01;
    public final ModelPart rhandle;
    public final ModelPart light08;
    public final ModelPart body;
    public final ModelPart light02;
    public final ModelPart faceplate;
    public final ModelPart light07;
    public final ModelPart light03;

    public RackModule8Model(ModelPart root)
    {
        this.root = root;
        this.lhandle = root.getChild("lhandle");
        this.parts.put("lhandle", this.lhandle);
        this.light04 = root.getChild("light04");
        this.parts.put("light04", this.light04);
        this.light05 = root.getChild("light05");
        this.parts.put("light05", this.light05);
        this.light06 = root.getChild("light06");
        this.parts.put("light06", this.light06);
        this.light01 = root.getChild("light01");
        this.parts.put("light01", this.light01);
        this.rhandle = root.getChild("rhandle");
        this.parts.put("rhandle", this.rhandle);
        this.light08 = root.getChild("light08");
        this.parts.put("light08", this.light08);
        this.body = root.getChild("body");
        this.parts.put("body", this.body);
        this.light02 = root.getChild("light02");
        this.parts.put("light02", this.light02);
        this.faceplate = root.getChild("faceplate");
        this.parts.put("faceplate", this.faceplate);
        this.light07 = root.getChild("light07");
        this.parts.put("light07", this.light07);
        this.light03 = root.getChild("light03");
        this.parts.put("light03", this.light03);
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
        PartDefinition p_lhandle = root.addOrReplaceChild("lhandle",
                CubeListBuilder.create().texOffs(54, 0).addBox(10.0F, 0.4F, -2.1F, 1.0F, 6.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light04 = root.addOrReplaceChild("light04",
                CubeListBuilder.create().texOffs(0, 8).addBox(-4.3F, 4.4F, -0.3F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light05 = root.addOrReplaceChild("light05",
                CubeListBuilder.create().texOffs(0, 4).addBox(-2.9F, 0.5F, -0.3F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light06 = root.addOrReplaceChild("light06",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.9F, 1.8F, -0.3F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light01 = root.addOrReplaceChild("light01",
                CubeListBuilder.create().texOffs(0, 4).addBox(-4.3F, 0.5F, -0.3F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rhandle = root.addOrReplaceChild("rhandle",
                CubeListBuilder.create().texOffs(54, 0).addBox(-11.0F, 0.4F, -2.1F, 1.0F, 6.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light08 = root.addOrReplaceChild("light08",
                CubeListBuilder.create().texOffs(0, 8).addBox(-2.9F, 4.4F, -0.3F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, 0.5F, 0.3F, 14.0F, 6.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light02 = root.addOrReplaceChild("light02",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.3F, 1.8F, -0.3F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_faceplate = root.addOrReplaceChild("faceplate",
                CubeListBuilder.create().texOffs(0, 20).addBox(-12.5F, 0.0F, 0.0F, 25.0F, 7.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.8F));
        PartDefinition p_light07 = root.addOrReplaceChild("light07",
                CubeListBuilder.create().texOffs(0, 8).addBox(-2.9F, 3.1F, -0.3F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light03 = root.addOrReplaceChild("light03",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.3F, 3.1F, -0.3F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
