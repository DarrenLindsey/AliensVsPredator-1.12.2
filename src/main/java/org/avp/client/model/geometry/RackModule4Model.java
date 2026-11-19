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
 * Geometry converted automatically from the 1.12.2 ModelRackModule4 by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class RackModule4Model implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "rack_module4"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart faceplate;
    public final ModelPart light04;
    public final ModelPart light05;
    public final ModelPart chassis;
    public final ModelPart light07;
    public final ModelPart light01;
    public final ModelPart light06;
    public final ModelPart light08;
    public final ModelPart light03;
    public final ModelPart plate;
    public final ModelPart screen;
    public final ModelPart hinge;
    public final ModelPart light02;

    public RackModule4Model(ModelPart root)
    {
        this.root = root;
        this.faceplate = root.getChild("faceplate");
        this.parts.put("faceplate", this.faceplate);
        this.light04 = root.getChild("light04");
        this.parts.put("light04", this.light04);
        this.light05 = root.getChild("light05");
        this.parts.put("light05", this.light05);
        this.chassis = root.getChild("chassis");
        this.parts.put("chassis", this.chassis);
        this.light07 = root.getChild("light07");
        this.parts.put("light07", this.light07);
        this.light01 = root.getChild("light01");
        this.parts.put("light01", this.light01);
        this.light06 = root.getChild("light06");
        this.parts.put("light06", this.light06);
        this.light08 = root.getChild("light08");
        this.parts.put("light08", this.light08);
        this.light03 = root.getChild("light03");
        this.parts.put("light03", this.light03);
        this.plate = root.getChild("plate");
        this.parts.put("plate", this.plate);
        this.screen = root.getChild("screen");
        this.parts.put("screen", this.screen);
        this.hinge = root.getChild("hinge");
        this.parts.put("hinge", this.hinge);
        this.light02 = root.getChild("light02");
        this.parts.put("light02", this.light02);
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
        PartDefinition p_faceplate = root.addOrReplaceChild("faceplate",
                CubeListBuilder.create().texOffs(71, 0).addBox(-6.8F, 0.5F, -0.2F, 7.0F, 4.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light04 = root.addOrReplaceChild("light04",
                CubeListBuilder.create().texOffs(6, 6).addBox(-3.3F, 1.2F, -0.4F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light05 = root.addOrReplaceChild("light05",
                CubeListBuilder.create().texOffs(6, 6).addBox(-1.8F, 1.2F, -0.4F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_chassis = root.addOrReplaceChild("chassis",
                CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, 0.0F, 0.0F, 22.0F, 8.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light07 = root.addOrReplaceChild("light07",
                CubeListBuilder.create().texOffs(6, 6).addBox(-3.3F, 2.5F, -0.4F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light01 = root.addOrReplaceChild("light01",
                CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 3.1F, -0.4F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light06 = root.addOrReplaceChild("light06",
                CubeListBuilder.create().texOffs(6, 6).addBox(-4.6F, 2.5F, -0.4F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light08 = root.addOrReplaceChild("light08",
                CubeListBuilder.create().texOffs(6, 6).addBox(-1.8F, 2.5F, -0.4F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light03 = root.addOrReplaceChild("light03",
                CubeListBuilder.create().texOffs(6, 6).addBox(-4.6F, 1.2F, -0.4F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_plate = root.addOrReplaceChild("plate",
                CubeListBuilder.create().texOffs(96, 37).addBox(-8.9F, 4.8F, -0.8F, 2.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_screen = root.addOrReplaceChild("screen",
                CubeListBuilder.create().texOffs(8, 22).addBox(1.2F, -0.3F, -0.9F, 9.0F, 9.0F, 13.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_hinge = root.addOrReplaceChild("hinge",
                CubeListBuilder.create().texOffs(58, 24).addBox(-11.5F, 0.4F, -0.6F, 4.0F, 3.0F, 13.0F),
                PartPose.offset(0.0F, 4.0F, 0.0F));
        PartDefinition p_light02 = root.addOrReplaceChild("light02",
                CubeListBuilder.create().texOffs(0, 6).addBox(-6.0F, 1.2F, -0.4F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
