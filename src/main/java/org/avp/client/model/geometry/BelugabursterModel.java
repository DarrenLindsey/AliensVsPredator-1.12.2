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
 * Geometry converted automatically from the 1.12.2 ModelBelugaburster by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class BelugabursterModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "belugaburster"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart bodyMid;
    public final ModelPart neck;
    public final ModelPart neck2;
    public final ModelPart head1;
    public final ModelPart head2;
    public final ModelPart jawLower;
    public final ModelPart headBubbleSlope3;
    public final ModelPart jawUpper;
    public final ModelPart headBubbleSlope;
    public final ModelPart headBubbleSlope2;
    public final ModelPart headBubble;
    public final ModelPart body2;
    public final ModelPart lArm1;
    public final ModelPart lArm2;
    public final ModelPart lArm3;
    public final ModelPart lArm4;
    public final ModelPart lArm5;
    public final ModelPart lArm6;
    public final ModelPart rArm1;
    public final ModelPart rArm2;
    public final ModelPart rArm3;
    public final ModelPart rArm4;
    public final ModelPart rArm5;
    public final ModelPart rArm6;
    public final ModelPart body3;
    public final ModelPart lTail1;
    public final ModelPart lTail2;
    public final ModelPart lTail3;
    public final ModelPart lTail4;
    public final ModelPart rTail1;
    public final ModelPart rTail2;
    public final ModelPart rTail3;
    public final ModelPart rTail4;

    public BelugabursterModel(ModelPart root)
    {
        this.root = root;
        this.bodyMid = root.getChild("bodyMid");
        this.parts.put("bodyMid", this.bodyMid);
        this.neck = this.bodyMid.getChild("neck");
        this.parts.put("neck", this.neck);
        this.neck2 = this.neck.getChild("neck2");
        this.parts.put("neck2", this.neck2);
        this.head1 = this.neck2.getChild("head1");
        this.parts.put("head1", this.head1);
        this.head2 = this.head1.getChild("head2");
        this.parts.put("head2", this.head2);
        this.jawLower = this.head2.getChild("jawLower");
        this.parts.put("jawLower", this.jawLower);
        this.headBubbleSlope3 = this.head2.getChild("headBubbleSlope3");
        this.parts.put("headBubbleSlope3", this.headBubbleSlope3);
        this.jawUpper = this.head2.getChild("jawUpper");
        this.parts.put("jawUpper", this.jawUpper);
        this.headBubbleSlope = this.head2.getChild("headBubbleSlope");
        this.parts.put("headBubbleSlope", this.headBubbleSlope);
        this.headBubbleSlope2 = this.head2.getChild("headBubbleSlope2");
        this.parts.put("headBubbleSlope2", this.headBubbleSlope2);
        this.headBubble = this.head2.getChild("headBubble");
        this.parts.put("headBubble", this.headBubble);
        this.body2 = this.bodyMid.getChild("body2");
        this.parts.put("body2", this.body2);
        this.lArm1 = this.body2.getChild("lArm1");
        this.parts.put("lArm1", this.lArm1);
        this.lArm2 = this.lArm1.getChild("lArm2");
        this.parts.put("lArm2", this.lArm2);
        this.lArm3 = this.lArm2.getChild("lArm3");
        this.parts.put("lArm3", this.lArm3);
        this.lArm4 = this.lArm3.getChild("lArm4");
        this.parts.put("lArm4", this.lArm4);
        this.lArm5 = this.lArm4.getChild("lArm5");
        this.parts.put("lArm5", this.lArm5);
        this.lArm6 = this.lArm5.getChild("lArm6");
        this.parts.put("lArm6", this.lArm6);
        this.rArm1 = this.body2.getChild("rArm1");
        this.parts.put("rArm1", this.rArm1);
        this.rArm2 = this.rArm1.getChild("rArm2");
        this.parts.put("rArm2", this.rArm2);
        this.rArm3 = this.rArm2.getChild("rArm3");
        this.parts.put("rArm3", this.rArm3);
        this.rArm4 = this.rArm3.getChild("rArm4");
        this.parts.put("rArm4", this.rArm4);
        this.rArm5 = this.rArm4.getChild("rArm5");
        this.parts.put("rArm5", this.rArm5);
        this.rArm6 = this.rArm5.getChild("rArm6");
        this.parts.put("rArm6", this.rArm6);
        this.body3 = this.body2.getChild("body3");
        this.parts.put("body3", this.body3);
        this.lTail1 = this.body3.getChild("lTail1");
        this.parts.put("lTail1", this.lTail1);
        this.lTail2 = this.lTail1.getChild("lTail2");
        this.parts.put("lTail2", this.lTail2);
        this.lTail3 = this.lTail2.getChild("lTail3");
        this.parts.put("lTail3", this.lTail3);
        this.lTail4 = this.lTail3.getChild("lTail4");
        this.parts.put("lTail4", this.lTail4);
        this.rTail1 = this.body3.getChild("rTail1");
        this.parts.put("rTail1", this.rTail1);
        this.rTail2 = this.rTail1.getChild("rTail2");
        this.parts.put("rTail2", this.rTail2);
        this.rTail3 = this.rTail2.getChild("rTail3");
        this.parts.put("rTail3", this.rTail3);
        this.rTail4 = this.rTail3.getChild("rTail4");
        this.parts.put("rTail4", this.rTail4);
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
        PartDefinition p_bodyMid = root.addOrReplaceChild("bodyMid",
                CubeListBuilder.create().texOffs(0, 49).addBox(-1.5F, -0.3F, -1.5F, 3.0F, 5.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 16.6F, 0.0F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_neck = p_bodyMid.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(0, 31).addBox(-1.5F, -3.3F, -1.6F, 3.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -3.2F, 1.3F, -0.8651597102135892F, 0.0F, 0.0F));
        PartDefinition p_neck2 = p_neck.addOrReplaceChild("neck2",
                CubeListBuilder.create().texOffs(0, 22).addBox(-1.5F, -2.9F, -2.1F, 3.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -3.1F, 0.0F, 1.0471975511965976F, 0.0F, 0.0F));
        PartDefinition p_head1 = p_neck2.addOrReplaceChild("head1",
                CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -0.8F, -5.4F, 3.0F, 3.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -2.6F, 0.0F, -0.5462880558742251F, 0.0F, 0.0F));
        PartDefinition p_head2 = p_head1.addOrReplaceChild("head2",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -6.8F, 3.0F, 3.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.1F, -5.0F, 0.5918411493512771F, 0.0F, 0.0F));
        PartDefinition p_jawLower = p_head2.addOrReplaceChild("jawLower",
                CubeListBuilder.create().texOffs(25, 55).addBox(-1.0F, -0.4F, -1.3F, 2.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, -6.3F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_headBubbleSlope3 = p_head2.addOrReplaceChild("headBubbleSlope3",
                CubeListBuilder.create().texOffs(24, 42).addBox(-1.0F, -1.9F, 2.1F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7285004297824331F, 0.0F, 0.0F));
        PartDefinition p_jawUpper = p_head2.addOrReplaceChild("jawUpper",
                CubeListBuilder.create().texOffs(25, 49).addBox(-1.0F, 0.1F, -1.4F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.6F, -6.3F));
        PartDefinition p_headBubbleSlope = p_head2.addOrReplaceChild("headBubbleSlope",
                CubeListBuilder.create().texOffs(22, 25).addBox(-1.5F, -2.1F, -5.9F, 3.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_headBubbleSlope2 = p_head2.addOrReplaceChild("headBubbleSlope2",
                CubeListBuilder.create().texOffs(24, 34).addBox(-1.0F, -1.6F, -1.6F, 2.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5462880558742251F, 0.0F, 0.0F));
        PartDefinition p_headBubble = p_head2.addOrReplaceChild("headBubble",
                CubeListBuilder.create().texOffs(22, 15).addBox(-2.0F, -6.9F, -6.4F, 4.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.6F, 0.0F, 0.5918411493512771F, 0.0F, 0.0F));
        PartDefinition p_body2 = p_bodyMid.addOrReplaceChild("body2",
                CubeListBuilder.create().texOffs(0, 40).addBox(-1.5F, -3.7F, -1.5F, 3.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_lArm1 = p_body2.addOrReplaceChild("lArm1",
                CubeListBuilder.create().texOffs(70, 0).addBox(0.0F, -1.7F, -1.0F, 1.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(1.0F, -1.1F, 0.0F, 0.0F, 0.0F, -0.18203784098300857F));
        PartDefinition p_lArm2 = p_lArm1.addOrReplaceChild("lArm2",
                CubeListBuilder.create().texOffs(71, 7).addBox(-0.5F, -0.5F, -0.9F, 1.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.5F, 3.5F, -0.2F, -0.22759093446006054F, 0.0F, 0.136659280431156F));
        PartDefinition p_lArm3 = p_lArm2.addOrReplaceChild("lArm3",
                CubeListBuilder.create().texOffs(70, 14).addBox(-0.3F, -0.9F, -0.6F, 1.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-0.2F, 2.9F, 0.1F, -0.5009094953223726F, -0.045553093477052F, 0.0F));
        PartDefinition p_lArm4 = p_lArm3.addOrReplaceChild("lArm4",
                CubeListBuilder.create().texOffs(71, 21).addBox(-0.5F, -3.7F, -0.3F, 1.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.2F, 0.4F, 1.6F, -0.36425021489121656F, 0.045553093477052F, 0.091106186954104F));
        PartDefinition p_lArm5 = p_lArm4.addOrReplaceChild("lArm5",
                CubeListBuilder.create().texOffs(71, 29).addBox(-0.5F, -4.9F, -0.3F, 1.0F, 5.0F, 1.0F),
                PartPose.offset(0.0F, -3.4F, 1.0F));
        PartDefinition p_lArm6 = p_lArm5.addOrReplaceChild("lArm6",
                CubeListBuilder.create().texOffs(71, 29).addBox(-0.5F, -5.2F, -0.3F, 1.0F, 5.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -4.5F, 0.0F, -0.0041887902047863905F, 0.0F, -0.091106186954104F));
        PartDefinition p_rArm1 = p_body2.addOrReplaceChild("rArm1",
                CubeListBuilder.create().texOffs(70, 0).mirror().addBox(-1.0F, -1.7F, -1.0F, 1.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(-1.0F, -1.1F, 0.0F, 0.0F, 0.0F, 0.18203784098300857F));
        PartDefinition p_rArm2 = p_rArm1.addOrReplaceChild("rArm2",
                CubeListBuilder.create().texOffs(71, 7).mirror().addBox(-0.5F, -0.5F, -0.9F, 1.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(-0.5F, 3.5F, -0.2F, -0.22759093446006054F, 0.0F, -0.136659280431156F));
        PartDefinition p_rArm3 = p_rArm2.addOrReplaceChild("rArm3",
                CubeListBuilder.create().texOffs(70, 14).mirror().addBox(-0.3F, -0.9F, -0.6F, 1.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-0.2F, 2.9F, 0.1F, -0.5009094953223726F, 0.045553093477052F, 0.0F));
        PartDefinition p_rArm4 = p_rArm3.addOrReplaceChild("rArm4",
                CubeListBuilder.create().texOffs(71, 21).mirror().addBox(-0.5F, -3.7F, -0.3F, 1.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.2F, 0.4F, 1.5F, -0.36425021489121656F, -0.045553093477052F, -0.091106186954104F));
        PartDefinition p_rArm5 = p_rArm4.addOrReplaceChild("rArm5",
                CubeListBuilder.create().texOffs(71, 29).mirror().addBox(-0.5F, -4.9F, -0.3F, 1.0F, 5.0F, 1.0F),
                PartPose.offset(0.0F, -3.4F, 1.0F));
        PartDefinition p_rArm6 = p_rArm5.addOrReplaceChild("rArm6",
                CubeListBuilder.create().texOffs(71, 29).mirror().addBox(-0.5F, -5.2F, -0.3F, 1.0F, 5.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -4.5F, 0.0F, -0.0041887902047863905F, 0.0F, 0.091106186954104F));
        PartDefinition p_body3 = p_body2.addOrReplaceChild("body3",
                CubeListBuilder.create().texOffs(22, 0).addBox(-1.5F, 0.2F, -0.8F, 3.0F, 3.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 4.3F, 0.6F, 0.4553564018453205F, 0.0F, 0.0F));
        PartDefinition p_lTail1 = p_body3.addOrReplaceChild("lTail1",
                CubeListBuilder.create().texOffs(47, 2).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 7.0F),
                PartPose.offset(0.8F, 2.3F, 6.0F));
        PartDefinition p_lTail2 = p_lTail1.addOrReplaceChild("lTail2",
                CubeListBuilder.create().texOffs(47, 12).addBox(-0.6F, -1.0F, 0.0F, 1.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.1F, 0.0F, 6.5F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_lTail3 = p_lTail2.addOrReplaceChild("lTail3",
                CubeListBuilder.create().texOffs(47, 22).addBox(-0.6F, -0.4F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 4.6F, -0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lTail4 = p_lTail3.addOrReplaceChild("lTail4",
                CubeListBuilder.create().texOffs(47, 29).addBox(-0.6F, -0.5F, -0.1F, 1.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.2F, 4.0F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_rTail1 = p_body3.addOrReplaceChild("rTail1",
                CubeListBuilder.create().texOffs(47, 2).mirror().addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 7.0F),
                PartPose.offset(-0.8F, 2.3F, 6.0F));
        PartDefinition p_rTail2 = p_rTail1.addOrReplaceChild("rTail2",
                CubeListBuilder.create().texOffs(47, 12).mirror().addBox(-0.6F, -1.0F, 0.0F, 1.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.1F, 0.0F, 6.5F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_rTail3 = p_rTail2.addOrReplaceChild("rTail3",
                CubeListBuilder.create().texOffs(47, 22).mirror().addBox(-0.6F, -0.4F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 4.6F, -0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_rTail4 = p_rTail3.addOrReplaceChild("rTail4",
                CubeListBuilder.create().texOffs(47, 29).mirror().addBox(-0.6F, -0.5F, -0.1F, 1.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.2F, 4.0F, 0.136659280431156F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
