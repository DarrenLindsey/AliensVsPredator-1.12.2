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
 * Geometry converted automatically from the 1.12.2 ModelMotionTracker by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class MotionTrackerModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "motion_tracker"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart handleBottom;
    public final ModelPart handle;
    public final ModelPart display;
    public final ModelPart keypad;
    public final ModelPart handleTop;
    public final ModelPart base;
    public final ModelPart sensorSupport;
    public final ModelPart sensor;

    public MotionTrackerModel(ModelPart root)
    {
        this.root = root;
        this.handleBottom = root.getChild("handleBottom");
        this.parts.put("handleBottom", this.handleBottom);
        this.handle = root.getChild("handle");
        this.parts.put("handle", this.handle);
        this.display = root.getChild("display");
        this.parts.put("display", this.display);
        this.keypad = root.getChild("keypad");
        this.parts.put("keypad", this.keypad);
        this.handleTop = root.getChild("handleTop");
        this.parts.put("handleTop", this.handleTop);
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.sensorSupport = root.getChild("sensorSupport");
        this.parts.put("sensorSupport", this.sensorSupport);
        this.sensor = root.getChild("sensor");
        this.parts.put("sensor", this.sensor);
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
        PartDefinition p_handleBottom = root.addOrReplaceChild("handleBottom",
                CubeListBuilder.create().texOffs(20, 6).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(-0.5F, 2.0F, -0.5F));
        PartDefinition p_handle = root.addOrReplaceChild("handle",
                CubeListBuilder.create().texOffs(20, 12).addBox(0.0F, 0.0F, 0.0F, 1.0F, 5.0F, 2.0F),
                PartPose.offset(-0.5F, -3.0F, -0.5F));
        PartDefinition p_display = root.addOrReplaceChild("display",
                CubeListBuilder.create().texOffs(22, 0).addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 2.0F),
                PartPose.offset(1.0F, -2.5F, 3.4F));
        PartDefinition p_keypad = root.addOrReplaceChild("keypad",
                CubeListBuilder.create().texOffs(11, 6).addBox(0.0F, 0.0F, 3.0F, 1.0F, 2.0F, 3.0F),
                PartPose.offset(-1.3F, -1.0F, 0.5F));
        PartDefinition p_handleTop = root.addOrReplaceChild("handleTop",
                CubeListBuilder.create().texOffs(9, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 4.0F),
                PartPose.offset(-1.0F, -4.0F, -1.0F));
        PartDefinition p_base = root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 3.0F),
                PartPose.offset(-1.0F, -4.0F, 3.0F));
        PartDefinition p_sensorSupport = root.addOrReplaceChild("sensorSupport",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(-1.0F, -2.1F, 6.3F, -0.5759587F, 0.0F, 0.0F));
        PartDefinition p_sensor = root.addOrReplaceChild("sensor",
                CubeListBuilder.create().texOffs(11, 12).addBox(0.0F, 0.0F, 0.0F, 2.0F, 3.0F, 2.0F),
                PartPose.offset(-1.0F, -4.0F, 6.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
