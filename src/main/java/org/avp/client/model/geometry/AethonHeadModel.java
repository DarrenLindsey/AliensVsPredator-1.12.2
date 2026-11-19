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
 * Geometry converted automatically from the 1.12.2 ModelAethonHead by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class AethonHeadModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "aethon_head"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart head;
    public final ModelPart headSlope;
    public final ModelPart beakMouthSlope;
    public final ModelPart beak01;
    public final ModelPart beak02;
    public final ModelPart beak03;
    public final ModelPart beak04;
    public final ModelPart beakLower;
    public final ModelPart rbeakLower;
    public final ModelPart rbeakLower02;
    public final ModelPart beakLower02;

    public AethonHeadModel(ModelPart root)
    {
        this.root = root;
        this.head = root.getChild("head");
        this.parts.put("head", this.head);
        this.headSlope = this.head.getChild("headSlope");
        this.parts.put("headSlope", this.headSlope);
        this.beakMouthSlope = this.head.getChild("beakMouthSlope");
        this.parts.put("beakMouthSlope", this.beakMouthSlope);
        this.beak01 = this.beakMouthSlope.getChild("beak01");
        this.parts.put("beak01", this.beak01);
        this.beak02 = this.beak01.getChild("beak02");
        this.parts.put("beak02", this.beak02);
        this.beak03 = this.beak02.getChild("beak03");
        this.parts.put("beak03", this.beak03);
        this.beak04 = this.beak03.getChild("beak04");
        this.parts.put("beak04", this.beak04);
        this.beakLower = this.head.getChild("beakLower");
        this.parts.put("beakLower", this.beakLower);
        this.rbeakLower = this.beakLower.getChild("rbeakLower");
        this.parts.put("rbeakLower", this.rbeakLower);
        this.rbeakLower02 = this.rbeakLower.getChild("rbeakLower02");
        this.parts.put("rbeakLower02", this.rbeakLower02);
        this.beakLower02 = this.beakLower.getChild("beakLower02");
        this.parts.put("beakLower02", this.beakLower02);
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
        PartDefinition p_head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 65).addBox(-3.5F, -2.5F, -7.0F, 7.0F, 5.0F, 7.0F),
                PartPose.offset(0.0F, 21.5F, 4.5F));
        PartDefinition p_headSlope = p_head.addOrReplaceChild("headSlope",
                CubeListBuilder.create().texOffs(3, 79).addBox(-2.5F, -5.3F, -9.4F, 5.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 1.1F, 0.44872415068774213F, 0.0F, 0.0F));
        PartDefinition p_beakMouthSlope = p_head.addOrReplaceChild("beakMouthSlope",
                CubeListBuilder.create().texOffs(4, 87).addBox(-1.5F, -2.7F, -12.1F, 3.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -1.1F, 1.4F, 0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_beak01 = p_beakMouthSlope.addOrReplaceChild("beak01",
                CubeListBuilder.create().texOffs(26, 87).addBox(-2.0F, -1.0F, -1.7F, 4.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -2.6F, -10.2F, 0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_beak02 = p_beak01.addOrReplaceChild("beak02",
                CubeListBuilder.create().texOffs(26, 94).addBox(-1.5F, -0.5F, -0.6F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -0.4F, -1.9F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_beak03 = p_beak02.addOrReplaceChild("beak03",
                CubeListBuilder.create().texOffs(27, 99).addBox(-1.0F, -0.5F, -1.6F, 2.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.1F, -0.2F, 0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_beak04 = p_beak03.addOrReplaceChild("beak04",
                CubeListBuilder.create().texOffs(28, 104).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.1F, -1.4F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_beakLower = p_head.addOrReplaceChild("beakLower",
                CubeListBuilder.create().texOffs(6, 98).addBox(-1.7F, -0.9F, -5.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(1.6F, 0.6F, -5.3F, 0.22689280275926282F, 0.0F, 0.0F));
        PartDefinition p_rbeakLower = p_beakLower.addOrReplaceChild("rbeakLower",
                CubeListBuilder.create().texOffs(6, 98).mirror().addBox(-0.7F, -0.9F, -5.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offset(-2.5F, 0.0F, 0.0F));
        PartDefinition p_rbeakLower02 = p_rbeakLower.addOrReplaceChild("rbeakLower02",
                CubeListBuilder.create().texOffs(31, 80).mirror().addBox(-0.5F, -0.5F, -1.8F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.5F, -5.0F, -0.22689280275926282F, 0.0F, 0.0F));
        PartDefinition p_beakLower02 = p_beakLower.addOrReplaceChild("beakLower02",
                CubeListBuilder.create().texOffs(31, 80).addBox(-0.7F, -0.5F, -1.8F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-0.5F, 0.5F, -5.0F, -0.22689280275926282F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 256);
    }
}
