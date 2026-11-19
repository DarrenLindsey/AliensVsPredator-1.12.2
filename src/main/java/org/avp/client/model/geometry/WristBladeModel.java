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
 * Geometry converted automatically from the 1.12.2 ModelWristBlade by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class WristBladeModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "wrist_blade"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart bBase;
    public final ModelPart b1;
    public final ModelPart b2;
    public final ModelPart b3;
    public final ModelPart b4;
    public final ModelPart b5;
    public final ModelPart b6;
    public final ModelPart bladeLeft;
    public final ModelPart b7;
    public final ModelPart b8;
    public final ModelPart b9;
    public final ModelPart b10;
    public final ModelPart b11;
    public final ModelPart bladeRight;

    public WristBladeModel(ModelPart root)
    {
        this.root = root;
        this.bBase = root.getChild("bBase");
        this.parts.put("bBase", this.bBase);
        this.b1 = root.getChild("b1");
        this.parts.put("b1", this.b1);
        this.b2 = root.getChild("b2");
        this.parts.put("b2", this.b2);
        this.b3 = root.getChild("b3");
        this.parts.put("b3", this.b3);
        this.b4 = root.getChild("b4");
        this.parts.put("b4", this.b4);
        this.b5 = root.getChild("b5");
        this.parts.put("b5", this.b5);
        this.b6 = root.getChild("b6");
        this.parts.put("b6", this.b6);
        this.bladeLeft = root.getChild("bladeLeft");
        this.parts.put("bladeLeft", this.bladeLeft);
        this.b7 = root.getChild("b7");
        this.parts.put("b7", this.b7);
        this.b8 = root.getChild("b8");
        this.parts.put("b8", this.b8);
        this.b9 = root.getChild("b9");
        this.parts.put("b9", this.b9);
        this.b10 = root.getChild("b10");
        this.parts.put("b10", this.b10);
        this.b11 = root.getChild("b11");
        this.parts.put("b11", this.b11);
        this.bladeRight = root.getChild("bladeRight");
        this.parts.put("bladeRight", this.bladeRight);
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
        PartDefinition p_bBase = root.addOrReplaceChild("bBase",
                CubeListBuilder.create().texOffs(29, 0).addBox(0.0F, 0.0F, 0.0F, 4.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_b1 = root.addOrReplaceChild("b1",
                CubeListBuilder.create().texOffs(7, 5).addBox(0.0F, 0.0F, 0.0F, 4.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F, 0.0F));
        PartDefinition p_b2 = root.addOrReplaceChild("b2",
                CubeListBuilder.create().texOffs(7, 5).addBox(0.0F, 0.0F, 0.0F, 4.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.7853981633974483F, 0.0F, 0.0F));
        PartDefinition p_b3 = root.addOrReplaceChild("b3",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F),
                PartPose.offset(0.5F, -0.2F, 0.5F));
        PartDefinition p_b4 = root.addOrReplaceChild("b4",
                CubeListBuilder.create().texOffs(8, 14).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(2.7F, -0.2F, 0.5F));
        PartDefinition p_b5 = root.addOrReplaceChild("b5",
                CubeListBuilder.create().texOffs(7, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(3.7F, 0.0F, 0.5F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_b6 = root.addOrReplaceChild("b6",
                CubeListBuilder.create().texOffs(15, 0).addBox(0.0F, 0.0F, 0.0F, 8.0F, 2.0F, 0.0F),
                PartPose.offset(3.0F, 0.0F, 0.5F));
        PartDefinition p_bladeLeft = root.addOrReplaceChild("bladeLeft",
                CubeListBuilder.create().texOffs(15, 0).addBox(0.0F, 0.0F, 0.0F, 8.0F, 2.0F, 0.0F),
                PartPose.offset(3.0F, 0.0F, 1.9F));
        PartDefinition p_b7 = root.addOrReplaceChild("b7",
                CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(3.0F, 0.2F, 1.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_b8 = root.addOrReplaceChild("b8",
                CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(2.0F, 0.2F, 1.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_b9 = root.addOrReplaceChild("b9",
                CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(1.0F, 0.2F, 1.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_b10 = root.addOrReplaceChild("b10",
                CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(3.0F, 0.2F, -0.4F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_b11 = root.addOrReplaceChild("b11",
                CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(2.0F, 0.2F, -0.4F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_bladeRight = root.addOrReplaceChild("bladeRight",
                CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(1.0F, 0.2F, -0.4F, 0.0F, -0.7853981633974483F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
