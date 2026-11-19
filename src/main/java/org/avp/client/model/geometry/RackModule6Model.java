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
 * Geometry converted automatically from the 1.12.2 ModelRackModule6 by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class RackModule6Model implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "rack_module6"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart light02;
    public final ModelPart light01;
    public final ModelPart plate;
    public final ModelPart light04;
    public final ModelPart base;
    public final ModelPart light06;
    public final ModelPart frame;
    public final ModelPart light05;
    public final ModelPart toggle;
    public final ModelPart light03;

    public RackModule6Model(ModelPart root)
    {
        this.root = root;
        this.light02 = root.getChild("light02");
        this.parts.put("light02", this.light02);
        this.light01 = root.getChild("light01");
        this.parts.put("light01", this.light01);
        this.plate = root.getChild("plate");
        this.parts.put("plate", this.plate);
        this.light04 = root.getChild("light04");
        this.parts.put("light04", this.light04);
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.light06 = root.getChild("light06");
        this.parts.put("light06", this.light06);
        this.frame = root.getChild("frame");
        this.parts.put("frame", this.frame);
        this.light05 = root.getChild("light05");
        this.parts.put("light05", this.light05);
        this.toggle = root.getChild("toggle");
        this.parts.put("toggle", this.toggle);
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
        PartDefinition p_light02 = root.addOrReplaceChild("light02",
                CubeListBuilder.create().texOffs(0, 3).addBox(2.7F, 1.4F, 3.7F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5759586531581287F, 0.0F, 0.0F));
        PartDefinition p_light01 = root.addOrReplaceChild("light01",
                CubeListBuilder.create().texOffs(0, 0).addBox(1.2F, 1.4F, 3.7F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5759586531581287F, 0.0F, 0.0F));
        PartDefinition p_plate = root.addOrReplaceChild("plate",
                CubeListBuilder.create().texOffs(53, 0).addBox(-2.1F, 0.2F, 3.7F, 3.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5759586531581287F, 0.0F, 0.0F));
        PartDefinition p_light04 = root.addOrReplaceChild("light04",
                CubeListBuilder.create().texOffs(5, 0).addBox(5.9F, 1.4F, 3.7F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5759586531581287F, 0.0F, 0.0F));
        PartDefinition p_base = root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(0, 18).addBox(-9.8F, -0.5F, 4.3F, 19.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5759586531581287F, 0.0F, 0.0F));
        PartDefinition p_light06 = root.addOrReplaceChild("light06",
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.3F, 1.1F, 3.6F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5759586531581287F, 0.0F, 0.0F));
        PartDefinition p_frame = root.addOrReplaceChild("frame",
                CubeListBuilder.create().texOffs(2, 0).addBox(-10.3F, 1.2F, 0.0F, 20.0F, 8.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light05 = root.addOrReplaceChild("light05",
                CubeListBuilder.create().texOffs(0, 0).addBox(7.4F, 1.4F, 3.7F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5759586531581287F, 0.0F, 0.0F));
        PartDefinition p_toggle = root.addOrReplaceChild("toggle",
                CubeListBuilder.create().texOffs(6, 5).addBox(-1.7F, 2.8F, 3.0F, 1.0F, 0.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5759586531581287F, 0.0F, 0.0F));
        PartDefinition p_light03 = root.addOrReplaceChild("light03",
                CubeListBuilder.create().texOffs(0, 3).addBox(4.3F, 1.4F, 3.7F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5759586531581287F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
