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
 * Geometry converted automatically from the 1.12.2 ModelRackModule5 by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class RackModule5Model implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "rack_module5"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart light02;
    public final ModelPart light03;
    public final ModelPart lhandle;
    public final ModelPart light01;
    public final ModelPart dial1;
    public final ModelPart dial2;
    public final ModelPart chassis;
    public final ModelPart rhandle;
    public final ModelPart faceplate;

    public RackModule5Model(ModelPart root)
    {
        this.root = root;
        this.light02 = root.getChild("light02");
        this.parts.put("light02", this.light02);
        this.light03 = root.getChild("light03");
        this.parts.put("light03", this.light03);
        this.lhandle = root.getChild("lhandle");
        this.parts.put("lhandle", this.lhandle);
        this.light01 = root.getChild("light01");
        this.parts.put("light01", this.light01);
        this.dial1 = root.getChild("dial1");
        this.parts.put("dial1", this.dial1);
        this.dial2 = root.getChild("dial2");
        this.parts.put("dial2", this.dial2);
        this.chassis = root.getChild("chassis");
        this.parts.put("chassis", this.chassis);
        this.rhandle = root.getChild("rhandle");
        this.parts.put("rhandle", this.rhandle);
        this.faceplate = root.getChild("faceplate");
        this.parts.put("faceplate", this.faceplate);
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
                CubeListBuilder.create().texOffs(0, 0).addBox(2.2F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light03 = root.addOrReplaceChild("light03",
                CubeListBuilder.create().texOffs(0, 0).addBox(3.4F, 1.0F, -1.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lhandle = root.addOrReplaceChild("lhandle",
                CubeListBuilder.create().texOffs(36, 1).addBox(-11.0F, 1.0F, -2.1F, 1.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light01 = root.addOrReplaceChild("light01",
                CubeListBuilder.create().texOffs(0, 4).addBox(0.0F, 1.0F, -1.0F, 2.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_dial1 = root.addOrReplaceChild("dial1",
                CubeListBuilder.create().texOffs(12, 0).addBox(-4.7F, 1.4F, -1.0F, 3.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_dial2 = root.addOrReplaceChild("dial2",
                CubeListBuilder.create().texOffs(22, 0).addBox(-4.2F, 1.9F, -1.6F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_chassis = root.addOrReplaceChild("chassis",
                CubeListBuilder.create().texOffs(0, 18).addBox(-6.5F, 0.9F, -0.3F, 13.0F, 4.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rhandle = root.addOrReplaceChild("rhandle",
                CubeListBuilder.create().texOffs(36, 1).addBox(10.0F, 1.0F, -2.1F, 1.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_faceplate = root.addOrReplaceChild("faceplate",
                CubeListBuilder.create().texOffs(0, 10).addBox(-12.5F, 0.1F, -0.1F, 25.0F, 6.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
