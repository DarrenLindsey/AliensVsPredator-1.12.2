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
 * Geometry converted automatically from the 1.12.2 ModelRackModule3 by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class RackModule3Model implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "rack_module3"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart faceplate;
    public final ModelPart lhandle;
    public final ModelPart rhandle;
    public final ModelPart light02;
    public final ModelPart light03;
    public final ModelPart dial1;
    public final ModelPart lfaceplate;
    public final ModelPart light01;
    public final ModelPart dial2;

    public RackModule3Model(ModelPart root)
    {
        this.root = root;
        this.faceplate = root.getChild("faceplate");
        this.parts.put("faceplate", this.faceplate);
        this.lhandle = root.getChild("lhandle");
        this.parts.put("lhandle", this.lhandle);
        this.rhandle = root.getChild("rhandle");
        this.parts.put("rhandle", this.rhandle);
        this.light02 = root.getChild("light02");
        this.parts.put("light02", this.light02);
        this.light03 = root.getChild("light03");
        this.parts.put("light03", this.light03);
        this.dial1 = root.getChild("dial1");
        this.parts.put("dial1", this.dial1);
        this.lfaceplate = root.getChild("lfaceplate");
        this.parts.put("lfaceplate", this.lfaceplate);
        this.light01 = root.getChild("light01");
        this.parts.put("light01", this.light01);
        this.dial2 = root.getChild("dial2");
        this.parts.put("dial2", this.dial2);
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
                CubeListBuilder.create().texOffs(0, 7).addBox(-8.0F, 0.0F, 0.0F, 16.0F, 8.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lhandle = root.addOrReplaceChild("lhandle",
                CubeListBuilder.create().texOffs(71, 0).addBox(-10.8F, 0.0F, -2.5F, 1.0F, 8.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rhandle = root.addOrReplaceChild("rhandle",
                CubeListBuilder.create().texOffs(71, 0).addBox(9.8F, 0.0F, -2.5F, 1.0F, 8.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light02 = root.addOrReplaceChild("light02",
                CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, 1.2F, -0.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light03 = root.addOrReplaceChild("light03",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.1F, 1.2F, -0.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_dial1 = root.addOrReplaceChild("dial1",
                CubeListBuilder.create().texOffs(8, 0).addBox(3.5F, 2.7F, -0.9F, 2.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lfaceplate = root.addOrReplaceChild("lfaceplate",
                CubeListBuilder.create().texOffs(0, 33).addBox(-12.5F, -4.5F, 0.1F, 25.0F, 9.0F, 1.0F),
                PartPose.offset(0.0F, 4.0F, 0.0F));
        PartDefinition p_light01 = root.addOrReplaceChild("light01",
                CubeListBuilder.create().texOffs(0, 0).addBox(-6.9F, 1.2F, -0.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_dial2 = root.addOrReplaceChild("dial2",
                CubeListBuilder.create().texOffs(18, 0).addBox(3.1F, 2.1F, -1.0F, 3.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
