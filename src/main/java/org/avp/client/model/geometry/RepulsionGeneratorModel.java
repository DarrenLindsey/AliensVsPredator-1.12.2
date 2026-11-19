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
 * Geometry converted automatically from the 1.12.2 ModelRepulsionGenerator by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class RepulsionGeneratorModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "repulsion_generator"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart wireframe2;
    public final ModelPart side7;
    public final ModelPart base;
    public final ModelPart side6;
    public final ModelPart wireframe3;
    public final ModelPart top;
    public final ModelPart side5;
    public final ModelPart wireframe1;
    public final ModelPart side8;
    public final ModelPart rotor;
    public final ModelPart magnet8;
    public final ModelPart magnet7;
    public final ModelPart magnet4;
    public final ModelPart magnet1;
    public final ModelPart magnet3;
    public final ModelPart magnet5;
    public final ModelPart magnet6;
    public final ModelPart magnet2;
    public final ModelPart side1;
    public final ModelPart side3_1;
    public final ModelPart side2;
    public final ModelPart magnetbase;
    public final ModelPart side3;

    public RepulsionGeneratorModel(ModelPart root)
    {
        this.root = root;
        this.wireframe2 = root.getChild("wireframe2");
        this.parts.put("wireframe2", this.wireframe2);
        this.side7 = root.getChild("side7");
        this.parts.put("side7", this.side7);
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.side6 = root.getChild("side6");
        this.parts.put("side6", this.side6);
        this.wireframe3 = root.getChild("wireframe3");
        this.parts.put("wireframe3", this.wireframe3);
        this.top = root.getChild("top");
        this.parts.put("top", this.top);
        this.side5 = root.getChild("side5");
        this.parts.put("side5", this.side5);
        this.wireframe1 = root.getChild("wireframe1");
        this.parts.put("wireframe1", this.wireframe1);
        this.side8 = root.getChild("side8");
        this.parts.put("side8", this.side8);
        this.rotor = root.getChild("rotor");
        this.parts.put("rotor", this.rotor);
        this.magnet8 = this.rotor.getChild("magnet8");
        this.parts.put("magnet8", this.magnet8);
        this.magnet7 = this.rotor.getChild("magnet7");
        this.parts.put("magnet7", this.magnet7);
        this.magnet4 = this.rotor.getChild("magnet4");
        this.parts.put("magnet4", this.magnet4);
        this.magnet1 = this.rotor.getChild("magnet1");
        this.parts.put("magnet1", this.magnet1);
        this.magnet3 = this.rotor.getChild("magnet3");
        this.parts.put("magnet3", this.magnet3);
        this.magnet5 = this.rotor.getChild("magnet5");
        this.parts.put("magnet5", this.magnet5);
        this.magnet6 = this.rotor.getChild("magnet6");
        this.parts.put("magnet6", this.magnet6);
        this.magnet2 = this.rotor.getChild("magnet2");
        this.parts.put("magnet2", this.magnet2);
        this.side1 = root.getChild("side1");
        this.parts.put("side1", this.side1);
        this.side3_1 = root.getChild("side3_1");
        this.parts.put("side3_1", this.side3_1);
        this.side2 = root.getChild("side2");
        this.parts.put("side2", this.side2);
        this.magnetbase = root.getChild("magnetbase");
        this.parts.put("magnetbase", this.magnetbase);
        this.side3 = root.getChild("side3");
        this.parts.put("side3", this.side3);
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
        PartDefinition p_wireframe2 = root.addOrReplaceChild("wireframe2",
                CubeListBuilder.create().texOffs(27, 17).addBox(-9.0F, 16.5F, -9.0F, 18.0F, 0.0F, 18.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_side7 = root.addOrReplaceChild("side7",
                CubeListBuilder.create().texOffs(42, 0).addBox(-1.5F, 9.0F, 4.0F, 3.0F, 12.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_base = root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(64, 45).addBox(-8.0F, 21.0F, -8.0F, 16.0F, 3.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_side6 = root.addOrReplaceChild("side6",
                CubeListBuilder.create().texOffs(56, 0).addBox(-1.5F, 12.0F, 4.0F, 3.0F, 9.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_wireframe3 = root.addOrReplaceChild("wireframe3",
                CubeListBuilder.create().texOffs(27, 17).addBox(-9.0F, 13.0F, -9.0F, 18.0F, 0.0F, 18.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_top = root.addOrReplaceChild("top",
                CubeListBuilder.create().texOffs(0, 47).addBox(-8.0F, 8.0F, -8.0F, 16.0F, 1.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_side5 = root.addOrReplaceChild("side5",
                CubeListBuilder.create().texOffs(28, 0).addBox(-1.5F, 9.0F, 4.0F, 3.0F, 12.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));
        PartDefinition p_wireframe1 = root.addOrReplaceChild("wireframe1",
                CubeListBuilder.create().texOffs(27, 17).addBox(-9.0F, 20.0F, -9.0F, 18.0F, 0.0F, 18.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_side8 = root.addOrReplaceChild("side8",
                CubeListBuilder.create().texOffs(72, 0).addBox(-1.5F, 12.0F, 4.0F, 3.0F, 9.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_rotor = root.addOrReplaceChild("rotor",
                CubeListBuilder.create().texOffs(4, 29).addBox(-2.5F, 12.0F, -2.5F, 5.0F, 8.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_magnet8 = p_rotor.addOrReplaceChild("magnet8",
                CubeListBuilder.create().texOffs(0, 29).addBox(-1.5F, 12.5F, -3.5F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));
        PartDefinition p_magnet7 = p_rotor.addOrReplaceChild("magnet7",
                CubeListBuilder.create().texOffs(0, 29).addBox(-1.5F, 16.5F, -3.5F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));
        PartDefinition p_magnet4 = p_rotor.addOrReplaceChild("magnet4",
                CubeListBuilder.create().texOffs(0, 29).addBox(-1.5F, 12.5F, -3.5F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F, 0.0F));
        PartDefinition p_magnet1 = p_rotor.addOrReplaceChild("magnet1",
                CubeListBuilder.create().texOffs(0, 29).addBox(-1.5F, 12.5F, -3.5F, 3.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_magnet3 = p_rotor.addOrReplaceChild("magnet3",
                CubeListBuilder.create().texOffs(0, 29).addBox(-1.5F, 16.5F, -3.5F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F, 0.0F));
        PartDefinition p_magnet5 = p_rotor.addOrReplaceChild("magnet5",
                CubeListBuilder.create().texOffs(0, 29).addBox(-1.5F, 16.5F, -3.5F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.141592653589793F, 0.0F));
        PartDefinition p_magnet6 = p_rotor.addOrReplaceChild("magnet6",
                CubeListBuilder.create().texOffs(0, 29).addBox(-1.5F, 12.5F, -3.5F, 3.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.141592653589793F, 0.0F));
        PartDefinition p_magnet2 = p_rotor.addOrReplaceChild("magnet2",
                CubeListBuilder.create().texOffs(0, 29).addBox(-1.5F, 16.5F, -3.5F, 3.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_side1 = root.addOrReplaceChild("side1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, 9.0F, 4.0F, 3.0F, 12.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.141592653589793F, 1.5707963267948966F, 3.141592653589793F));
        PartDefinition p_side3_1 = root.addOrReplaceChild("side3_1",
                CubeListBuilder.create().texOffs(104, 0).addBox(-1.5F, 12.0F, 4.0F, 3.0F, 9.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.141592653589793F, -0.7853981633974483F, 3.141592653589793F));
        PartDefinition p_side2 = root.addOrReplaceChild("side2",
                CubeListBuilder.create().texOffs(88, 0).addBox(-1.5F, 12.0F, 4.0F, 3.0F, 9.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.141592653589793F, 0.7853981633974483F, 3.141592653589793F));
        PartDefinition p_magnetbase = root.addOrReplaceChild("magnetbase",
                CubeListBuilder.create().texOffs(0, 16).addBox(-5.5F, 20.0F, -5.5F, 11.0F, 1.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_side3 = root.addOrReplaceChild("side3",
                CubeListBuilder.create().texOffs(14, 0).addBox(-1.5F, 9.0F, 4.0F, 3.0F, 12.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.141592653589793F, 0.0F, 3.141592653589793F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
