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
 * Geometry converted automatically from the 1.12.2 ModelMedpod by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class MedpodModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "medpod"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart baseRod1;
    public final ModelPart base;
    public final ModelPart baseRod2;
    public final ModelPart bedBack;
    public final ModelPart orb;
    public final ModelPart lGlassBack;
    public final ModelPart rGlassBack;
    public final ModelPart bedHead;
    public final ModelPart lLoop1;
    public final ModelPart rLoop1;
    public final ModelPart loopTop;
    public final ModelPart lLoop2;
    public final ModelPart rLoop2;
    public final ModelPart footrest;
    public final ModelPart glassTop;
    public final ModelPart headrest;

    public MedpodModel(ModelPart root)
    {
        this.root = root;
        this.baseRod1 = root.getChild("baseRod1");
        this.parts.put("baseRod1", this.baseRod1);
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.baseRod2 = root.getChild("baseRod2");
        this.parts.put("baseRod2", this.baseRod2);
        this.bedBack = root.getChild("bedBack");
        this.parts.put("bedBack", this.bedBack);
        this.orb = this.bedBack.getChild("orb");
        this.parts.put("orb", this.orb);
        this.lGlassBack = this.bedBack.getChild("lGlassBack");
        this.parts.put("lGlassBack", this.lGlassBack);
        this.rGlassBack = this.bedBack.getChild("rGlassBack");
        this.parts.put("rGlassBack", this.rGlassBack);
        this.bedHead = this.bedBack.getChild("bedHead");
        this.parts.put("bedHead", this.bedHead);
        this.lLoop1 = this.bedBack.getChild("lLoop1");
        this.parts.put("lLoop1", this.lLoop1);
        this.rLoop1 = this.lLoop1.getChild("rLoop1");
        this.parts.put("rLoop1", this.rLoop1);
        this.loopTop = this.lLoop1.getChild("loopTop");
        this.parts.put("loopTop", this.loopTop);
        this.lLoop2 = this.lLoop1.getChild("lLoop2");
        this.parts.put("lLoop2", this.lLoop2);
        this.rLoop2 = this.lLoop1.getChild("rLoop2");
        this.parts.put("rLoop2", this.rLoop2);
        this.footrest = this.bedBack.getChild("footrest");
        this.parts.put("footrest", this.footrest);
        this.glassTop = this.bedBack.getChild("glassTop");
        this.parts.put("glassTop", this.glassTop);
        this.headrest = this.bedBack.getChild("headrest");
        this.parts.put("headrest", this.headrest);
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
        PartDefinition p_baseRod1 = root.addOrReplaceChild("baseRod1",
                CubeListBuilder.create().texOffs(17, 35).addBox(-1.5F, 19.0F, -1.5F, 3.0F, 4.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_base = root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(0, 44).addBox(-5.0F, 23.0F, -5.0F, 10.0F, 1.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_baseRod2 = root.addOrReplaceChild("baseRod2",
                CubeListBuilder.create().texOffs(0, 35).addBox(-2.0F, 16.0F, -2.0F, 4.0F, 4.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_bedBack = root.addOrReplaceChild("bedBack",
                CubeListBuilder.create().texOffs(0, 14).addBox(-6.0F, -3.0F, -3.0F, 12.0F, 3.0F, 17.0F),
                PartPose.offset(0.0F, 16.0F, 0.0F));
        PartDefinition p_orb = p_bedBack.addOrReplaceChild("orb",
                CubeListBuilder.create().texOffs(76, 42).addBox(-3.0F, 0.0F, -12.0F, 6.0F, 5.0F, 7.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lGlassBack = p_bedBack.addOrReplaceChild("lGlassBack",
                CubeListBuilder.create().texOffs(59, 0).mirror().addBox(-3.5F, -7.3F, -9.0F, 6.0F, 7.0F, 12.0F),
                PartPose.offset(3.0F, -2.7F, 9.0F));
        PartDefinition p_rGlassBack = p_bedBack.addOrReplaceChild("rGlassBack",
                CubeListBuilder.create().texOffs(59, 0).addBox(-2.5F, -7.3F, -9.0F, 6.0F, 7.0F, 12.0F),
                PartPose.offset(-3.0F, -2.7F, 9.0F));
        PartDefinition p_bedHead = p_bedBack.addOrReplaceChild("bedHead",
                CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -3.0F, -13.0F, 12.0F, 4.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lLoop1 = p_bedBack.addOrReplaceChild("lLoop1",
                CubeListBuilder.create().texOffs(96, 0).mirror().addBox(4.0F, -7.0F, 8.0F, 1.0F, 4.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rLoop1 = p_lLoop1.addOrReplaceChild("rLoop1",
                CubeListBuilder.create().texOffs(96, 0).addBox(-5.0F, -7.0F, 8.0F, 1.0F, 4.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_loopTop = p_lLoop1.addOrReplaceChild("loopTop",
                CubeListBuilder.create().texOffs(105, 6).addBox(-2.5F, -9.8F, 8.0F, 5.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lLoop2 = p_lLoop1.addOrReplaceChild("lLoop2",
                CubeListBuilder.create().texOffs(105, 0).mirror().addBox(-5.6F, -8.4F, 8.0F, 4.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.8203047484373349F));
        PartDefinition p_rLoop2 = p_lLoop1.addOrReplaceChild("rLoop2",
                CubeListBuilder.create().texOffs(105, 0).addBox(1.6F, -8.4F, 8.0F, 4.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.8203047484373349F));
        PartDefinition p_footrest = p_bedBack.addOrReplaceChild("footrest",
                CubeListBuilder.create().texOffs(41, 54).addBox(-5.5F, -14.0F, 2.4F, 11.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.9424777960769379F, 0.0F, 0.0F));
        PartDefinition p_glassTop = p_bedBack.addOrReplaceChild("glassTop",
                CubeListBuilder.create().texOffs(59, 20).addBox(-5.5F, -10.0F, -1.9F, 11.0F, 7.0F, 12.0F),
                PartPose.offset(0.0F, 0.0F, -10.0F));
        PartDefinition p_headrest = p_bedBack.addOrReplaceChild("headrest",
                CubeListBuilder.create().texOffs(41, 45).addBox(-5.5F, -12.9F, -8.6F, 11.0F, 5.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7504915783575618F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
