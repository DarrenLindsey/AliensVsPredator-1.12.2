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
 * Geometry converted automatically from the 1.12.2 ModelRackModule2 by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class RackModule2Model implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "rack_module2"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart glowButton04;
    public final ModelPart borderLower;
    public final ModelPart glowButton03;
    public final ModelPart light07;
    public final ModelPart light08;
    public final ModelPart light12;
    public final ModelPart monitor;
    public final ModelPart light02;
    public final ModelPart glowButton02;
    public final ModelPart frameLower;
    public final ModelPart borderUpper;
    public final ModelPart glowButton06;
    public final ModelPart borderRight;
    public final ModelPart light04;
    public final ModelPart light16;
    public final ModelPart borderLeft;
    public final ModelPart light15;
    public final ModelPart glowButton05;
    public final ModelPart frameUpper;
    public final ModelPart light06;
    public final ModelPart light09;
    public final ModelPart light03;
    public final ModelPart light11;
    public final ModelPart light10;
    public final ModelPart base;
    public final ModelPart light13;
    public final ModelPart glowButton01;
    public final ModelPart light05;
    public final ModelPart light01;
    public final ModelPart light14;

    public RackModule2Model(ModelPart root)
    {
        this.root = root;
        this.glowButton04 = root.getChild("glowButton04");
        this.parts.put("glowButton04", this.glowButton04);
        this.borderLower = root.getChild("borderLower");
        this.parts.put("borderLower", this.borderLower);
        this.glowButton03 = root.getChild("glowButton03");
        this.parts.put("glowButton03", this.glowButton03);
        this.light07 = root.getChild("light07");
        this.parts.put("light07", this.light07);
        this.light08 = root.getChild("light08");
        this.parts.put("light08", this.light08);
        this.light12 = root.getChild("light12");
        this.parts.put("light12", this.light12);
        this.monitor = root.getChild("monitor");
        this.parts.put("monitor", this.monitor);
        this.light02 = root.getChild("light02");
        this.parts.put("light02", this.light02);
        this.glowButton02 = root.getChild("glowButton02");
        this.parts.put("glowButton02", this.glowButton02);
        this.frameLower = root.getChild("frameLower");
        this.parts.put("frameLower", this.frameLower);
        this.borderUpper = root.getChild("borderUpper");
        this.parts.put("borderUpper", this.borderUpper);
        this.glowButton06 = root.getChild("glowButton06");
        this.parts.put("glowButton06", this.glowButton06);
        this.borderRight = root.getChild("borderRight");
        this.parts.put("borderRight", this.borderRight);
        this.light04 = root.getChild("light04");
        this.parts.put("light04", this.light04);
        this.light16 = root.getChild("light16");
        this.parts.put("light16", this.light16);
        this.borderLeft = root.getChild("borderLeft");
        this.parts.put("borderLeft", this.borderLeft);
        this.light15 = root.getChild("light15");
        this.parts.put("light15", this.light15);
        this.glowButton05 = root.getChild("glowButton05");
        this.parts.put("glowButton05", this.glowButton05);
        this.frameUpper = root.getChild("frameUpper");
        this.parts.put("frameUpper", this.frameUpper);
        this.light06 = root.getChild("light06");
        this.parts.put("light06", this.light06);
        this.light09 = root.getChild("light09");
        this.parts.put("light09", this.light09);
        this.light03 = root.getChild("light03");
        this.parts.put("light03", this.light03);
        this.light11 = root.getChild("light11");
        this.parts.put("light11", this.light11);
        this.light10 = root.getChild("light10");
        this.parts.put("light10", this.light10);
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.light13 = root.getChild("light13");
        this.parts.put("light13", this.light13);
        this.glowButton01 = root.getChild("glowButton01");
        this.parts.put("glowButton01", this.glowButton01);
        this.light05 = root.getChild("light05");
        this.parts.put("light05", this.light05);
        this.light01 = root.getChild("light01");
        this.parts.put("light01", this.light01);
        this.light14 = root.getChild("light14");
        this.parts.put("light14", this.light14);
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
        PartDefinition p_glowButton04 = root.addOrReplaceChild("glowButton04",
                CubeListBuilder.create().texOffs(10, 0).addBox(-4.6F, 9.7F, -1.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition p_borderLower = root.addOrReplaceChild("borderLower",
                CubeListBuilder.create().texOffs(40, 25).addBox(-12.0F, 10.5F, -2.0F, 25.0F, 1.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_glowButton03 = root.addOrReplaceChild("glowButton03",
                CubeListBuilder.create().texOffs(10, 0).addBox(-6.0F, 9.7F, -1.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition p_light07 = root.addOrReplaceChild("light07",
                CubeListBuilder.create().texOffs(0, 15).addBox(7.5F, 5.5F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light08 = root.addOrReplaceChild("light08",
                CubeListBuilder.create().texOffs(0, 15).addBox(9.5F, 5.5F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light12 = root.addOrReplaceChild("light12",
                CubeListBuilder.create().texOffs(0, 15).addBox(4.5F, 2.5F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_monitor = root.addOrReplaceChild("monitor",
                CubeListBuilder.create().texOffs(0, 18).addBox(-12.0F, 0.8F, 3.0F, 12.0F, 9.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light02 = root.addOrReplaceChild("light02",
                CubeListBuilder.create().texOffs(0, 15).addBox(9.5F, 1.0F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_glowButton02 = root.addOrReplaceChild("glowButton02",
                CubeListBuilder.create().texOffs(0, 0).addBox(-10.2F, 9.7F, -2.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition p_frameLower = root.addOrReplaceChild("frameLower",
                CubeListBuilder.create().texOffs(0, 37).addBox(-11.99F, 10.0F, -3.0F, 12.0F, 2.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition p_borderUpper = root.addOrReplaceChild("borderUpper",
                CubeListBuilder.create().texOffs(40, 25).addBox(-12.0F, -0.8F, -2.0F, 25.0F, 1.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_glowButton06 = root.addOrReplaceChild("glowButton06",
                CubeListBuilder.create().texOffs(10, 0).addBox(-1.8F, 9.7F, -1.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition p_borderRight = root.addOrReplaceChild("borderRight",
                CubeListBuilder.create().texOffs(45, 40).mirror().addBox(12.0F, 0.0F, -2.0F, 1.0F, 11.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light04 = root.addOrReplaceChild("light04",
                CubeListBuilder.create().texOffs(0, 15).addBox(9.5F, 2.5F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light16 = root.addOrReplaceChild("light16",
                CubeListBuilder.create().texOffs(0, 15).addBox(4.5F, 5.5F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_borderLeft = root.addOrReplaceChild("borderLeft",
                CubeListBuilder.create().texOffs(45, 40).addBox(0.0F, 0.0F, -2.0F, 1.0F, 11.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light15 = root.addOrReplaceChild("light15",
                CubeListBuilder.create().texOffs(0, 15).addBox(2.5F, 5.5F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_glowButton05 = root.addOrReplaceChild("glowButton05",
                CubeListBuilder.create().texOffs(10, 0).addBox(-3.2F, 9.7F, -1.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition p_frameUpper = root.addOrReplaceChild("frameUpper",
                CubeListBuilder.create().texOffs(0, 51).addBox(-11.99F, -1.6F, -0.2F, 12.0F, 2.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition p_light06 = root.addOrReplaceChild("light06",
                CubeListBuilder.create().texOffs(0, 15).addBox(9.5F, 4.0F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light09 = root.addOrReplaceChild("light09",
                CubeListBuilder.create().texOffs(0, 15).addBox(2.5F, 1.0F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light03 = root.addOrReplaceChild("light03",
                CubeListBuilder.create().texOffs(0, 15).addBox(7.5F, 2.5F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light11 = root.addOrReplaceChild("light11",
                CubeListBuilder.create().texOffs(0, 15).addBox(2.5F, 2.5F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light10 = root.addOrReplaceChild("light10",
                CubeListBuilder.create().texOffs(0, 15).addBox(4.5F, 1.0F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_base = root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(40, 0).addBox(1.0F, 0.0F, -1.0F, 11.0F, 11.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light13 = root.addOrReplaceChild("light13",
                CubeListBuilder.create().texOffs(0, 15).addBox(2.5F, 4.0F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_glowButton01 = root.addOrReplaceChild("glowButton01",
                CubeListBuilder.create().texOffs(0, 0).addBox(-11.5F, 9.7F, -2.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2617993877991494F, 0.0F, 0.0F));
        PartDefinition p_light05 = root.addOrReplaceChild("light05",
                CubeListBuilder.create().texOffs(0, 15).addBox(7.5F, 4.0F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light01 = root.addOrReplaceChild("light01",
                CubeListBuilder.create().texOffs(0, 15).addBox(7.5F, 1.0F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light14 = root.addOrReplaceChild("light14",
                CubeListBuilder.create().texOffs(0, 15).addBox(4.5F, 4.0F, -1.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
