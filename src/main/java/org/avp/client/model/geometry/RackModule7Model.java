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
 * Geometry converted automatically from the 1.12.2 ModelRackModule7 by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class RackModule7Model implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "rack_module7"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart rack01;
    public final ModelPart back;
    public final ModelPart lightBank;
    public final ModelPart rack00;
    public final ModelPart rack05;
    public final ModelPart cassette01;
    public final ModelPart rack07;
    public final ModelPart light02;
    public final ModelPart rack03;
    public final ModelPart rack04;
    public final ModelPart rSide;
    public final ModelPart light01;
    public final ModelPart cassette02;
    public final ModelPart lhandle;
    public final ModelPart faceplate;
    public final ModelPart light03;
    public final ModelPart rack02;
    public final ModelPart rhandle;
    public final ModelPart rack06;
    public final ModelPart lSide;

    public RackModule7Model(ModelPart root)
    {
        this.root = root;
        this.rack01 = root.getChild("rack01");
        this.parts.put("rack01", this.rack01);
        this.back = root.getChild("back");
        this.parts.put("back", this.back);
        this.lightBank = root.getChild("lightBank");
        this.parts.put("lightBank", this.lightBank);
        this.rack00 = root.getChild("rack00");
        this.parts.put("rack00", this.rack00);
        this.rack05 = root.getChild("rack05");
        this.parts.put("rack05", this.rack05);
        this.cassette01 = root.getChild("cassette01");
        this.parts.put("cassette01", this.cassette01);
        this.rack07 = root.getChild("rack07");
        this.parts.put("rack07", this.rack07);
        this.light02 = root.getChild("light02");
        this.parts.put("light02", this.light02);
        this.rack03 = root.getChild("rack03");
        this.parts.put("rack03", this.rack03);
        this.rack04 = root.getChild("rack04");
        this.parts.put("rack04", this.rack04);
        this.rSide = root.getChild("rSide");
        this.parts.put("rSide", this.rSide);
        this.light01 = root.getChild("light01");
        this.parts.put("light01", this.light01);
        this.cassette02 = root.getChild("cassette02");
        this.parts.put("cassette02", this.cassette02);
        this.lhandle = root.getChild("lhandle");
        this.parts.put("lhandle", this.lhandle);
        this.faceplate = root.getChild("faceplate");
        this.parts.put("faceplate", this.faceplate);
        this.light03 = root.getChild("light03");
        this.parts.put("light03", this.light03);
        this.rack02 = root.getChild("rack02");
        this.parts.put("rack02", this.rack02);
        this.rhandle = root.getChild("rhandle");
        this.parts.put("rhandle", this.rhandle);
        this.rack06 = root.getChild("rack06");
        this.parts.put("rack06", this.rack06);
        this.lSide = root.getChild("lSide");
        this.parts.put("lSide", this.lSide);
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
        PartDefinition p_rack01 = root.addOrReplaceChild("rack01",
                CubeListBuilder.create().texOffs(88, 0).addBox(-1.0F, -0.5F, 0.1F, 2.0F, 1.0F, 12.0F),
                PartPose.offset(-7.0F, 1.2F, 0.0F));
        PartDefinition p_back = root.addOrReplaceChild("back",
                CubeListBuilder.create().texOffs(38, 24).addBox(-12.0F, -7.2F, 0.1F, 24.0F, 8.0F, 2.0F),
                PartPose.offset(0.0F, 7.8F, 10.9F));
        PartDefinition p_lightBank = root.addOrReplaceChild("lightBank",
                CubeListBuilder.create().texOffs(12, 0).addBox(-1.0F, 0.0F, -0.6F, 2.0F, 5.0F, 1.0F),
                PartPose.offset(-8.9F, 1.3F, 0.4F));
        PartDefinition p_rack00 = root.addOrReplaceChild("rack00",
                CubeListBuilder.create().texOffs(95, 20).addBox(-1.6F, -0.5F, 0.2F, 1.0F, 6.0F, 12.0F),
                PartPose.offset(-7.0F, 1.8F, 0.0F));
        PartDefinition p_rack05 = root.addOrReplaceChild("rack05",
                CubeListBuilder.create().texOffs(88, 0).addBox(-1.0F, -0.5F, 0.1F, 2.0F, 1.0F, 12.0F),
                PartPose.offset(7.0F, 3.8F, 0.0F));
        PartDefinition p_cassette01 = root.addOrReplaceChild("cassette01",
                CubeListBuilder.create().texOffs(34, 37).addBox(-7.5F, -1.0F, -0.1F, 15.0F, 2.0F, 11.0F),
                PartPose.offset(0.0F, 2.4F, 0.0F));
        PartDefinition p_rack07 = root.addOrReplaceChild("rack07",
                CubeListBuilder.create().texOffs(95, 20).addBox(1.6F, -0.5F, 0.2F, 1.0F, 6.0F, 12.0F),
                PartPose.offset(6.0F, 1.8F, 0.0F));
        PartDefinition p_light02 = root.addOrReplaceChild("light02",
                CubeListBuilder.create().texOffs(0, 0).addBox(-9.4F, 2.5F, -0.35F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.3F, 0.0F));
        PartDefinition p_rack03 = root.addOrReplaceChild("rack03",
                CubeListBuilder.create().texOffs(88, 0).addBox(-1.0F, -0.5F, 0.1F, 2.0F, 1.0F, 12.0F),
                PartPose.offset(-7.0F, 6.6F, 0.0F));
        PartDefinition p_rack04 = root.addOrReplaceChild("rack04",
                CubeListBuilder.create().texOffs(88, 0).addBox(-1.0F, -0.5F, 0.1F, 2.0F, 1.0F, 12.0F),
                PartPose.offset(7.0F, 1.2F, 0.0F));
        PartDefinition p_rSide = root.addOrReplaceChild("rSide",
                CubeListBuilder.create().texOffs(0, 23).mirror().addBox(-12.0F, -3.4F, 0.0F, 4.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 4.0F, 0.0F));
        PartDefinition p_light01 = root.addOrReplaceChild("light01",
                CubeListBuilder.create().texOffs(0, 0).addBox(-9.4F, 1.3F, -0.35F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.2F, 0.0F));
        PartDefinition p_cassette02 = root.addOrReplaceChild("cassette02",
                CubeListBuilder.create().texOffs(34, 37).addBox(-7.5F, -1.0F, -0.1F, 15.0F, 2.0F, 11.0F),
                PartPose.offset(0.0F, 5.0F, 0.0F));
        PartDefinition p_lhandle = root.addOrReplaceChild("lhandle",
                CubeListBuilder.create().texOffs(71, 0).addBox(9.8F, 1.5F, -1.8F, 1.0F, 6.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_faceplate = root.addOrReplaceChild("faceplate",
                CubeListBuilder.create().texOffs(0, 7).addBox(-8.0F, -1.0F, 0.0F, 16.0F, 2.0F, 11.0F),
                PartPose.offset(0.0F, 7.6F, 0.0F));
        PartDefinition p_light03 = root.addOrReplaceChild("light03",
                CubeListBuilder.create().texOffs(0, 5).addBox(-9.4F, 3.8F, -0.35F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.2F, 0.0F));
        PartDefinition p_rack02 = root.addOrReplaceChild("rack02",
                CubeListBuilder.create().texOffs(88, 0).addBox(-1.0F, -0.5F, 0.1F, 2.0F, 1.0F, 12.0F),
                PartPose.offset(-7.0F, 3.8F, 0.0F));
        PartDefinition p_rhandle = root.addOrReplaceChild("rhandle",
                CubeListBuilder.create().texOffs(71, 0).mirror().addBox(-10.8F, 1.5F, -1.8F, 1.0F, 6.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rack06 = root.addOrReplaceChild("rack06",
                CubeListBuilder.create().texOffs(88, 0).addBox(-1.0F, -0.5F, 0.1F, 2.0F, 1.0F, 12.0F),
                PartPose.offset(7.0F, 6.6F, 0.0F));
        PartDefinition p_lSide = root.addOrReplaceChild("lSide",
                CubeListBuilder.create().texOffs(0, 45).addBox(8.0F, -3.4F, 0.0F, 4.0F, 8.0F, 11.0F),
                PartPose.offset(0.0F, 4.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
