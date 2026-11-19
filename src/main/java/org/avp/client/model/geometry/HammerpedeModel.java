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
 * Geometry converted automatically from the 1.12.2 ModelHammerpede by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class HammerpedeModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "hammerpede"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart body2;
    public final ModelPart body1;
    public final ModelPart body3;
    public final ModelPart head;
    public final ModelPart body5;
    public final ModelPart body4;
    public final ModelPart body6;
    public final ModelPart body7;
    public final ModelPart body8;
    public final ModelPart lCrest;
    public final ModelPart rCrest;
    public final ModelPart mouthLower;
    public final ModelPart rMouth;
    public final ModelPart mouthUpper;
    public final ModelPart lMouth;
    public final ModelPart fangUpper;
    public final ModelPart rFangLower;
    public final ModelPart lFangLower;
    public final ModelPart rFang;
    public final ModelPart lFang;
    public final ModelPart tail;

    public HammerpedeModel(ModelPart root)
    {
        this.root = root;
        this.body2 = root.getChild("body2");
        this.parts.put("body2", this.body2);
        this.body1 = root.getChild("body1");
        this.parts.put("body1", this.body1);
        this.body3 = root.getChild("body3");
        this.parts.put("body3", this.body3);
        this.head = root.getChild("head");
        this.parts.put("head", this.head);
        this.body5 = root.getChild("body5");
        this.parts.put("body5", this.body5);
        this.body4 = root.getChild("body4");
        this.parts.put("body4", this.body4);
        this.body6 = root.getChild("body6");
        this.parts.put("body6", this.body6);
        this.body7 = root.getChild("body7");
        this.parts.put("body7", this.body7);
        this.body8 = root.getChild("body8");
        this.parts.put("body8", this.body8);
        this.lCrest = root.getChild("lCrest");
        this.parts.put("lCrest", this.lCrest);
        this.rCrest = root.getChild("rCrest");
        this.parts.put("rCrest", this.rCrest);
        this.mouthLower = root.getChild("mouthLower");
        this.parts.put("mouthLower", this.mouthLower);
        this.rMouth = root.getChild("rMouth");
        this.parts.put("rMouth", this.rMouth);
        this.mouthUpper = root.getChild("mouthUpper");
        this.parts.put("mouthUpper", this.mouthUpper);
        this.lMouth = root.getChild("lMouth");
        this.parts.put("lMouth", this.lMouth);
        this.fangUpper = root.getChild("fangUpper");
        this.parts.put("fangUpper", this.fangUpper);
        this.rFangLower = root.getChild("rFangLower");
        this.parts.put("rFangLower", this.rFangLower);
        this.lFangLower = root.getChild("lFangLower");
        this.parts.put("lFangLower", this.lFangLower);
        this.rFang = root.getChild("rFang");
        this.parts.put("rFang", this.rFang);
        this.lFang = root.getChild("lFang");
        this.parts.put("lFang", this.lFang);
        this.tail = root.getChild("tail");
        this.parts.put("tail", this.tail);
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
        PartDefinition p_body2 = root.addOrReplaceChild("body2",
                CubeListBuilder.create().texOffs(9, 15).addBox(-1.0F, 0.0F, -1.2F, 2.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 13.0F, -1.0F, -0.2443461F, 0.0F, 0.0F));
        PartDefinition p_body1 = root.addOrReplaceChild("body1",
                CubeListBuilder.create().texOffs(0, 15).addBox(-1.0F, 0.3F, -1.7F, 2.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 8.0F, -0.3F, -0.0523599F, 0.0F, 0.0F));
        PartDefinition p_body3 = root.addOrReplaceChild("body3",
                CubeListBuilder.create().texOffs(18, 15).addBox(-1.0F, -0.1F, -1.5F, 2.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 18.0F, -2.0F, -0.3490659F, 0.0F, 0.0F));
        PartDefinition p_head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, 6.0F, -2.0F, 3.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, -1.0F, 0.0F));
        PartDefinition p_body5 = root.addOrReplaceChild("body5",
                CubeListBuilder.create().texOffs(27, 15).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 22.0F, -4.0F));
        PartDefinition p_body4 = root.addOrReplaceChild("body4",
                CubeListBuilder.create().texOffs(0, 9).addBox(-1.0F, 0.2F, 0.1F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, -3.3F, -1.012291F, 0.0F, 0.0F));
        PartDefinition p_body6 = root.addOrReplaceChild("body6",
                CubeListBuilder.create().texOffs(42, 15).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 8.0F),
                PartPose.offset(0.0F, 22.0F, 0.0F));
        PartDefinition p_body7 = root.addOrReplaceChild("body7",
                CubeListBuilder.create().texOffs(0, 23).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 2.0F, 6.0F),
                PartPose.offset(0.0F, 22.0F, 8.0F));
        PartDefinition p_body8 = root.addOrReplaceChild("body8",
                CubeListBuilder.create().texOffs(18, 23).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 6.0F),
                PartPose.offset(0.0F, 22.0F, 14.0F));
        PartDefinition p_lCrest = root.addOrReplaceChild("lCrest",
                CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, 0.0F, -1.0F, 5.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, 0.2268928F, -0.8203047F));
        PartDefinition p_rCrest = root.addOrReplaceChild("rCrest",
                CubeListBuilder.create().texOffs(11, 0).addBox(-4.0F, 0.0F, -1.0F, 5.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.0F, -0.2268928F, 0.8203047F));
        PartDefinition p_mouthLower = root.addOrReplaceChild("mouthLower",
                CubeListBuilder.create().texOffs(51, 8).addBox(-0.5F, 8.0F, 1.7F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.418879F, 0.0F, 0.0F));
        PartDefinition p_rMouth = root.addOrReplaceChild("rMouth",
                CubeListBuilder.create().texOffs(46, 5).addBox(-2.3F, 7.7F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, -0.5585054F, 0.0F));
        PartDefinition p_mouthUpper = root.addOrReplaceChild("mouthUpper",
                CubeListBuilder.create().texOffs(51, 1).addBox(-0.5F, 5.0F, -5.0F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.4014257F, 0.0F, 0.0F));
        PartDefinition p_lMouth = root.addOrReplaceChild("lMouth",
                CubeListBuilder.create().texOffs(56, 5).addBox(1.3F, 7.7F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, 0.5585054F, 0.0F));
        PartDefinition p_fangUpper = root.addOrReplaceChild("fangUpper",
                CubeListBuilder.create().texOffs(16, 5).addBox(0.0F, 5.0F, -6.0F, 0.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.4014257F, 0.0F, 0.0F));
        PartDefinition p_rFangLower = root.addOrReplaceChild("rFangLower",
                CubeListBuilder.create().texOffs(12, 10).addBox(-0.2F, 10.0F, 0.5F, 0.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.418879F, 0.0F, 0.0F));
        PartDefinition p_lFangLower = root.addOrReplaceChild("lFangLower",
                CubeListBuilder.create().texOffs(18, 10).addBox(0.2F, 10.0F, 0.5F, 0.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.418879F, 0.0F, 0.0F));
        PartDefinition p_rFang = root.addOrReplaceChild("rFang",
                CubeListBuilder.create().texOffs(11, 8).addBox(-1.5F, 8.2F, -3.2F, 1.0F, 0.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, -0.1047198F, 0.0F));
        PartDefinition p_lFang = root.addOrReplaceChild("lFang",
                CubeListBuilder.create().texOffs(19, 8).addBox(0.5F, 8.2F, -3.2F, 1.0F, 0.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, 0.1047198F, 0.0F));
        PartDefinition p_tail = root.addOrReplaceChild("tail",
                CubeListBuilder.create().texOffs(27, 6).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 23.0F, 20.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
