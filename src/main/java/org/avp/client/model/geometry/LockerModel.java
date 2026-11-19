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
 * Geometry converted automatically from the 1.12.2 ModelLocker by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class LockerModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "locker"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart top;
    public final ModelPart back;
    public final ModelPart floor;
    public final ModelPart wallLeft;
    public final ModelPart wallRight;
    public final ModelPart door;

    public LockerModel(ModelPart root)
    {
        this.root = root;
        this.top = root.getChild("top");
        this.parts.put("top", this.top);
        this.back = root.getChild("back");
        this.parts.put("back", this.back);
        this.floor = root.getChild("floor");
        this.parts.put("floor", this.floor);
        this.wallLeft = root.getChild("wallLeft");
        this.parts.put("wallLeft", this.wallLeft);
        this.wallRight = root.getChild("wallRight");
        this.parts.put("wallRight", this.wallRight);
        this.door = root.getChild("door");
        this.parts.put("door", this.door);
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
        PartDefinition p_top = root.addOrReplaceChild("top",
                CubeListBuilder.create().texOffs(0, 49).addBox(-8.0F, -10.0F, -8.0F, 16.0F, 1.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_back = root.addOrReplaceChild("back",
                CubeListBuilder.create().texOffs(100, 14).addBox(-8.0F, -9.0F, 7.0F, 16.0F, 32.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_floor = root.addOrReplaceChild("floor",
                CubeListBuilder.create().texOffs(0, 69).addBox(-8.0F, 23.0F, -8.0F, 16.0F, 1.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_wallLeft = root.addOrReplaceChild("wallLeft",
                CubeListBuilder.create().texOffs(37, 0).addBox(-8.0F, -9.0F, -7.0F, 1.0F, 32.0F, 14.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_wallRight = root.addOrReplaceChild("wallRight",
                CubeListBuilder.create().texOffs(68, 0).addBox(7.0F, -9.0F, -7.0F, 1.0F, 32.0F, 14.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_door = root.addOrReplaceChild("door",
                CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -9.0F, -1.0F, 16.0F, 32.0F, 1.0F),
                PartPose.offset(8.0F, 0.0F, -7.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
