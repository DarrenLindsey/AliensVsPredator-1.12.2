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
 * Geometry converted automatically from the 1.12.2 ModelDrone by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class DroneModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "drone"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart body1;
    public final ModelPart body2;
    public final ModelPart rightleg1;
    public final ModelPart leftleg1;
    public final ModelPart leftleg2;
    public final ModelPart rightleg2;
    public final ModelPart leftleg3;
    public final ModelPart rightleg3;
    public final ModelPart leftleg4;
    public final ModelPart rightleg4;
    public final ModelPart leftarm1;
    public final ModelPart rightarm1;
    public final ModelPart leftarm2;
    public final ModelPart rightarm2;
    public final ModelPart neck;
    public final ModelPart head1;
    public final ModelPart head2;
    public final ModelPart head3;
    public final ModelPart head4;
    public final ModelPart head5;
    public final ModelPart leftarm3;
    public final ModelPart rightarm3;
    public final ModelPart leftarm4;
    public final ModelPart rightarm4;
    public final ModelPart back1;
    public final ModelPart back2;
    public final ModelPart head6;
    public final ModelPart tail1;
    public final ModelPart tail2;
    public final ModelPart tail3;
    public final ModelPart tail4;
    public final ModelPart tail5;
    public final ModelPart tailSpikes5;
    public final ModelPart tailSpikes4;
    public final ModelPart tailSpikes3;
    public final ModelPart tailSpikes2;
    public final ModelPart tailSpikes1;
    public final ModelPart stabber;
    public final ModelPart backhorn1;
    public final ModelPart backhorn2;
    public final ModelPart backhorn3;
    public final ModelPart backhorn4;

    public DroneModel(ModelPart root)
    {
        this.root = root;
        this.body1 = root.getChild("body1");
        this.parts.put("body1", this.body1);
        this.body2 = root.getChild("body2");
        this.parts.put("body2", this.body2);
        this.rightleg1 = root.getChild("rightleg1");
        this.parts.put("rightleg1", this.rightleg1);
        this.leftleg1 = root.getChild("leftleg1");
        this.parts.put("leftleg1", this.leftleg1);
        this.leftleg2 = root.getChild("leftleg2");
        this.parts.put("leftleg2", this.leftleg2);
        this.rightleg2 = root.getChild("rightleg2");
        this.parts.put("rightleg2", this.rightleg2);
        this.leftleg3 = root.getChild("leftleg3");
        this.parts.put("leftleg3", this.leftleg3);
        this.rightleg3 = root.getChild("rightleg3");
        this.parts.put("rightleg3", this.rightleg3);
        this.leftleg4 = root.getChild("leftleg4");
        this.parts.put("leftleg4", this.leftleg4);
        this.rightleg4 = root.getChild("rightleg4");
        this.parts.put("rightleg4", this.rightleg4);
        this.leftarm1 = root.getChild("leftarm1");
        this.parts.put("leftarm1", this.leftarm1);
        this.rightarm1 = root.getChild("rightarm1");
        this.parts.put("rightarm1", this.rightarm1);
        this.leftarm2 = root.getChild("leftarm2");
        this.parts.put("leftarm2", this.leftarm2);
        this.rightarm2 = root.getChild("rightarm2");
        this.parts.put("rightarm2", this.rightarm2);
        this.neck = root.getChild("neck");
        this.parts.put("neck", this.neck);
        this.head1 = root.getChild("head1");
        this.parts.put("head1", this.head1);
        this.head2 = root.getChild("head2");
        this.parts.put("head2", this.head2);
        this.head3 = root.getChild("head3");
        this.parts.put("head3", this.head3);
        this.head4 = root.getChild("head4");
        this.parts.put("head4", this.head4);
        this.head5 = root.getChild("head5");
        this.parts.put("head5", this.head5);
        this.leftarm3 = root.getChild("leftarm3");
        this.parts.put("leftarm3", this.leftarm3);
        this.rightarm3 = root.getChild("rightarm3");
        this.parts.put("rightarm3", this.rightarm3);
        this.leftarm4 = root.getChild("leftarm4");
        this.parts.put("leftarm4", this.leftarm4);
        this.rightarm4 = root.getChild("rightarm4");
        this.parts.put("rightarm4", this.rightarm4);
        this.back1 = root.getChild("back1");
        this.parts.put("back1", this.back1);
        this.back2 = root.getChild("back2");
        this.parts.put("back2", this.back2);
        this.head6 = root.getChild("head6");
        this.parts.put("head6", this.head6);
        this.tail1 = root.getChild("tail1");
        this.parts.put("tail1", this.tail1);
        this.tail2 = root.getChild("tail2");
        this.parts.put("tail2", this.tail2);
        this.tail3 = root.getChild("tail3");
        this.parts.put("tail3", this.tail3);
        this.tail4 = root.getChild("tail4");
        this.parts.put("tail4", this.tail4);
        this.tail5 = root.getChild("tail5");
        this.parts.put("tail5", this.tail5);
        this.tailSpikes5 = root.getChild("tailSpikes5");
        this.parts.put("tailSpikes5", this.tailSpikes5);
        this.tailSpikes4 = root.getChild("tailSpikes4");
        this.parts.put("tailSpikes4", this.tailSpikes4);
        this.tailSpikes3 = root.getChild("tailSpikes3");
        this.parts.put("tailSpikes3", this.tailSpikes3);
        this.tailSpikes2 = root.getChild("tailSpikes2");
        this.parts.put("tailSpikes2", this.tailSpikes2);
        this.tailSpikes1 = root.getChild("tailSpikes1");
        this.parts.put("tailSpikes1", this.tailSpikes1);
        this.stabber = root.getChild("stabber");
        this.parts.put("stabber", this.stabber);
        this.backhorn1 = root.getChild("backhorn1");
        this.parts.put("backhorn1", this.backhorn1);
        this.backhorn2 = root.getChild("backhorn2");
        this.parts.put("backhorn2", this.backhorn2);
        this.backhorn3 = root.getChild("backhorn3");
        this.parts.put("backhorn3", this.backhorn3);
        this.backhorn4 = root.getChild("backhorn4");
        this.parts.put("backhorn4", this.backhorn4);
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
        PartDefinition p_body1 = root.addOrReplaceChild("body1",
                CubeListBuilder.create().texOffs(0, 46).addBox(-4.5F, -2.0F, 0.0F, 9.0F, 8.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -13.0F, -0.1919862F, 0.0F, 0.0F));
        PartDefinition p_body2 = root.addOrReplaceChild("body2",
                CubeListBuilder.create().texOffs(0, 27).addBox(-3.5F, -5.0F, 8.0F, 7.0F, 6.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -13.0F, -0.5585054F, 0.0F, 0.0F));
        PartDefinition p_rightleg1 = root.addOrReplaceChild("rightleg1",
                CubeListBuilder.create().texOffs(59, 45).addBox(-1.5F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(-4.5F, 7.0F, 3.0F, -0.8028515F, 0.2443461F, 0.418879F));
        PartDefinition p_leftleg1 = root.addOrReplaceChild("leftleg1",
                CubeListBuilder.create().texOffs(40, 45).addBox(-2.5F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F),
                PartPose.offsetAndRotation(4.5F, 7.0F, 3.0F, -0.8028515F, -0.2443461F, -0.418879F));
        PartDefinition p_leftleg2 = root.addOrReplaceChild("leftleg2",
                CubeListBuilder.create().texOffs(79, 49).addBox(-2.0F, 8.0F, -5.5F, 3.0F, 3.0F, 12.0F),
                PartPose.offsetAndRotation(4.5F, 7.0F, 3.0F, -0.4014257F, -0.2443461F, -0.418879F));
        PartDefinition p_rightleg2 = root.addOrReplaceChild("rightleg2",
                CubeListBuilder.create().texOffs(79, 33).addBox(-1.0F, 8.0F, -5.5F, 3.0F, 3.0F, 12.0F),
                PartPose.offsetAndRotation(-4.5F, 7.0F, 3.0F, -0.4014257F, 0.2443461F, 0.418879F));
        PartDefinition p_leftleg3 = root.addOrReplaceChild("leftleg3",
                CubeListBuilder.create().texOffs(113, 40).addBox(-1.5F, 5.5F, 9.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(4.5F, 7.0F, 3.0F, -0.8028515F, -0.2443461F, -0.418879F));
        PartDefinition p_rightleg3 = root.addOrReplaceChild("rightleg3",
                CubeListBuilder.create().texOffs(113, 53).addBox(-0.5F, 5.5F, 9.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(-4.5F, 7.0F, 3.0F, -0.8028515F, 0.2443461F, 0.418879F));
        PartDefinition p_leftleg4 = root.addOrReplaceChild("leftleg4",
                CubeListBuilder.create().texOffs(110, 24).addBox(5.0F, 15.0F, -8.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(4.5F, 7.0F, 4.0F, 0.0F, -0.2443461F, 0.0F));
        PartDefinition p_rightleg4 = root.addOrReplaceChild("rightleg4",
                CubeListBuilder.create().texOffs(95, 24).addBox(-7.0F, 15.0F, -8.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(-4.5F, 7.0F, 4.0F, 0.0F, 0.2443461F, 0.0F));
        PartDefinition p_leftarm1 = root.addOrReplaceChild("leftarm1",
                CubeListBuilder.create().texOffs(40, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, -0.5934119F));
        PartDefinition p_rightarm1 = root.addOrReplaceChild("rightarm1",
                CubeListBuilder.create().texOffs(50, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(-4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, 0.5934119F));
        PartDefinition p_leftarm2 = root.addOrReplaceChild("leftarm2",
                CubeListBuilder.create().texOffs(83, 24).addBox(-1.0F, 11.0F, -13.5F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, -0.5934119F));
        PartDefinition p_rightarm2 = root.addOrReplaceChild("rightarm2",
                CubeListBuilder.create().texOffs(98, 9).addBox(-1.0F, 10.5F, -10.5F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(-4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, 0.5934119F));
        PartDefinition p_neck = root.addOrReplaceChild("neck",
                CubeListBuilder.create().texOffs(23, 86).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 6.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -13.0F, -0.1919862F, 0.0F, 0.0F));
        PartDefinition p_head1 = root.addOrReplaceChild("head1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -3.0F, 5.0F, 10.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -17.0F, -0.6283185307179586F, 0.0F, 0.0F));
        PartDefinition p_head2 = root.addOrReplaceChild("head2",
                CubeListBuilder.create().texOffs(34, 0).addBox(-2.51F, 3.0F, -5.0F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -17.0F, -0.122173F, 0.0F, 0.0F));
        PartDefinition p_head3 = root.addOrReplaceChild("head3",
                CubeListBuilder.create().texOffs(51, 10).addBox(-2.5F, 2.8F, -5.0F, 5.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -17.0F, 0.122173F, 0.0F, 0.0F));
        PartDefinition p_head4 = root.addOrReplaceChild("head4",
                CubeListBuilder.create().texOffs(52, 20).addBox(-1.5F, 4.1F, -6.1F, 3.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -17.0F, 0.122173F, 0.0F, 0.0F));
        PartDefinition p_head5 = root.addOrReplaceChild("head5",
                CubeListBuilder.create().texOffs(28, 10).addBox(-2.5F, -14.0F, -4.2F, 5.0F, 10.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -17.0F, -0.9075712F, 0.0F, 0.0F));
        PartDefinition p_leftarm3 = root.addOrReplaceChild("leftarm3",
                CubeListBuilder.create().texOffs(71, 9).addBox(-1.0F, 10.5F, -10.5F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, -0.5934119F));
        PartDefinition p_rightarm3 = root.addOrReplaceChild("rightarm3",
                CubeListBuilder.create().texOffs(72, 24).addBox(-1.0F, 11.0F, -13.5F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, 0.5934119F));
        PartDefinition p_leftarm4 = root.addOrReplaceChild("leftarm4",
                CubeListBuilder.create().texOffs(60, 30).addBox(-2.0F, 11.5F, -16.5F, 3.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, -0.5934119F));
        PartDefinition p_rightarm4 = root.addOrReplaceChild("rightarm4",
                CubeListBuilder.create().texOffs(60, 38).addBox(-1.0F, 11.5F, -16.5F, 3.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(-4.5F, 0.0F, -10.0F, 0.3665191F, 0.0F, 0.5934119F));
        PartDefinition p_back1 = root.addOrReplaceChild("back1",
                CubeListBuilder.create().texOffs(0, 65).addBox(0.0F, -10.0F, 0.0F, 0.0F, 8.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -13.0F, -0.1919862F, 0.0F, 0.0F));
        PartDefinition p_back2 = root.addOrReplaceChild("back2",
                CubeListBuilder.create().texOffs(23, 65).addBox(0.0F, -11.0F, 8.0F, 0.0F, 6.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -13.0F, -0.5585054F, 0.0F, 0.0F));
        PartDefinition p_head6 = root.addOrReplaceChild("head6",
                CubeListBuilder.create().texOffs(53, 0).addBox(-0.499F, 3.0F, -5.0F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -17.0F, -0.122173F, 0.0F, 0.0F));
        PartDefinition p_tail1 = root.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(50, 66).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 6.5F, 5.5F, -0.4014257F, 0.0F, 0.0F));
        PartDefinition p_tail2 = root.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(85, 66).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 4.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 9.5F, 29.5F, -0.3141592653589793F, 0.0F, 0.0F));
        PartDefinition p_tail3 = root.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(118, 66).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 13.5F, 30.5F, -0.2094395F, 0.0F, 0.0F));
        PartDefinition p_tail4 = root.addOrReplaceChild("tail4",
                CubeListBuilder.create().texOffs(149, 66).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 15.5F, 40.5F, -0.1396263F, 0.0F, 0.0F));
        PartDefinition p_tail5 = root.addOrReplaceChild("tail5",
                CubeListBuilder.create().texOffs(178, 66).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 17.5F, 50.5F, -0.0523599F, 0.0F, 0.0F));
        PartDefinition p_tailSpikes5 = root.addOrReplaceChild("tailSpikes5",
                CubeListBuilder.create().texOffs(178, 96).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 17.5F, 51.5F, -0.0523599F, 0.0F, 0.0F));
        PartDefinition p_tailSpikes4 = root.addOrReplaceChild("tailSpikes4",
                CubeListBuilder.create().texOffs(149, 96).addBox(0.0F, -2.0F, 0.0F, 0.0F, 4.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 15.5F, 41.5F, -0.1396263F, 0.0F, 0.0F));
        PartDefinition p_tailSpikes3 = root.addOrReplaceChild("tailSpikes3",
                CubeListBuilder.create().texOffs(118, 94).addBox(0.0F, -3.0F, 0.0F, 0.0F, 6.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 13.5F, 31.5F, -0.2094395F, 0.0F, 0.0F));
        PartDefinition p_tailSpikes2 = root.addOrReplaceChild("tailSpikes2",
                CubeListBuilder.create().texOffs(90, 93).addBox(0.0F, -3.5F, 0.0F, 0.0F, 8.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 9.5F, 20.5F, -0.3141592653589793F, 0.0F, 0.0F));
        PartDefinition p_tailSpikes1 = root.addOrReplaceChild("tailSpikes1",
                CubeListBuilder.create().texOffs(58, 93).addBox(0.0F, -4.5F, 0.0F, 0.0F, 8.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 6.5F, 5.5F, -0.4014257F, 0.0F, 0.0F));
        PartDefinition p_stabber = root.addOrReplaceChild("stabber",
                CubeListBuilder.create().texOffs(205, 66).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 11.0F),
                PartPose.offsetAndRotation(0.0F, 18.5F, 61.5F, -0.0523599F, 0.0F, 0.0F));
        PartDefinition p_backhorn1 = root.addOrReplaceChild("backhorn1",
                CubeListBuilder.create().texOffs(10, 86).addBox(-4.5F, -11.0F, 7.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -13.0F, -0.1919862F, 0.0F, -0.1745329F));
        PartDefinition p_backhorn2 = root.addOrReplaceChild("backhorn2",
                CubeListBuilder.create().texOffs(0, 86).addBox(2.5F, -11.0F, 7.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -13.0F, -0.1919862F, 0.0F, 0.1745329F));
        PartDefinition p_backhorn3 = root.addOrReplaceChild("backhorn3",
                CubeListBuilder.create().texOffs(9, 86).addBox(-3.5F, -14.0F, 10.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -13.0F, -0.5585054F, 0.0F, -0.1745329F));
        PartDefinition p_backhorn4 = root.addOrReplaceChild("backhorn4",
                CubeListBuilder.create().texOffs(0, 86).addBox(1.5F, -14.0F, 10.0F, 2.0F, 9.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -2.5F, -13.0F, -0.5585054F, 0.0F, 0.1745329F));
        return LayerDefinition.create(mesh, 256, 256);
    }
}
