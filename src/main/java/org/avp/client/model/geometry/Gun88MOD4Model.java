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
 * Geometry converted automatically from the 1.12.2 Model88MOD4 by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class Gun88MOD4Model implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "gun88_m_o_d4"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart barrelNose;
    public final ModelPart butt1;
    public final ModelPart barrelLeft;
    public final ModelPart barrelSupport;
    public final ModelPart rack;
    public final ModelPart gripSupport;
    public final ModelPart underGrip;
    public final ModelPart trigger;
    public final ModelPart barrelRight;
    public final ModelPart handleGrip;

    public Gun88MOD4Model(ModelPart root)
    {
        this.root = root;
        this.barrelNose = root.getChild("barrelNose");
        this.parts.put("barrelNose", this.barrelNose);
        this.butt1 = root.getChild("butt1");
        this.parts.put("butt1", this.butt1);
        this.barrelLeft = root.getChild("barrelLeft");
        this.parts.put("barrelLeft", this.barrelLeft);
        this.barrelSupport = root.getChild("barrelSupport");
        this.parts.put("barrelSupport", this.barrelSupport);
        this.rack = root.getChild("rack");
        this.parts.put("rack", this.rack);
        this.gripSupport = root.getChild("gripSupport");
        this.parts.put("gripSupport", this.gripSupport);
        this.underGrip = root.getChild("underGrip");
        this.parts.put("underGrip", this.underGrip);
        this.trigger = root.getChild("trigger");
        this.parts.put("trigger", this.trigger);
        this.barrelRight = root.getChild("barrelRight");
        this.parts.put("barrelRight", this.barrelRight);
        this.handleGrip = root.getChild("handleGrip");
        this.parts.put("handleGrip", this.handleGrip);
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
        PartDefinition p_barrelNose = root.addOrReplaceChild("barrelNose",
                CubeListBuilder.create().texOffs(7, 25).addBox(0.0F, -0.7F, -2.5F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_butt1 = root.addOrReplaceChild("butt1",
                CubeListBuilder.create().texOffs(50, 0).mirror().addBox(0.0F, -7.9F, 3.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.1693705988362009F, 0.0F, 0.0F));
        PartDefinition p_barrelLeft = root.addOrReplaceChild("barrelLeft",
                CubeListBuilder.create().texOffs(25, 0).addBox(0.2F, -1.0F, -2.0F, 1.0F, 2.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_barrelSupport = root.addOrReplaceChild("barrelSupport",
                CubeListBuilder.create().texOffs(14, 25).addBox(0.0F, -1.5F, -2.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.767944870877505F, 0.0F, 0.0F));
        PartDefinition p_rack = root.addOrReplaceChild("rack",
                CubeListBuilder.create().texOffs(0, 25).addBox(0.0F, 1.3F, -2.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_gripSupport = root.addOrReplaceChild("gripSupport",
                CubeListBuilder.create().texOffs(43, 15).addBox(0.0F, 2.0F, 4.3F, 1.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.13962634015954636F, 0.0F, 0.0F));
        PartDefinition p_underGrip = root.addOrReplaceChild("underGrip",
                CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, 0.7F, -1.5F, 1.0F, 1.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_trigger = root.addOrReplaceChild("trigger",
                CubeListBuilder.create().texOffs(21, 14).addBox(0.5F, 1.5F, 0.0F, 0.0F, 3.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_barrelRight = root.addOrReplaceChild("barrelRight",
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.2F, -1.0F, -2.0F, 1.0F, 2.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_handleGrip = root.addOrReplaceChild("handleGrip",
                CubeListBuilder.create().texOffs(36, 15).addBox(0.0F, 0.5F, 4.0F, 1.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.13962634015954636F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
