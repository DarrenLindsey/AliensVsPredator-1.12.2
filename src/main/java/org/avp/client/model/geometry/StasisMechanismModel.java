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
 * Geometry converted automatically from the 1.12.2 ModelStasisMechanism by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class StasisMechanismModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "stasis_mechanism"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart core;
    public final ModelPart west;
    public final ModelPart north;
    public final ModelPart northwest;
    public final ModelPart northeast;
    public final ModelPart east;
    public final ModelPart southeast;
    public final ModelPart southwest;
    public final ModelPart south;

    public StasisMechanismModel(ModelPart root)
    {
        this.root = root;
        this.core = root.getChild("core");
        this.parts.put("core", this.core);
        this.west = root.getChild("west");
        this.parts.put("west", this.west);
        this.north = root.getChild("north");
        this.parts.put("north", this.north);
        this.northwest = root.getChild("northwest");
        this.parts.put("northwest", this.northwest);
        this.northeast = root.getChild("northeast");
        this.parts.put("northeast", this.northeast);
        this.east = root.getChild("east");
        this.parts.put("east", this.east);
        this.southeast = root.getChild("southeast");
        this.parts.put("southeast", this.southeast);
        this.southwest = root.getChild("southwest");
        this.parts.put("southwest", this.southwest);
        this.south = root.getChild("south");
        this.parts.put("south", this.south);
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
        PartDefinition p_core = root.addOrReplaceChild("core",
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_west = root.addOrReplaceChild("west",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 5.0F, 5.5F, 7.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7853982F, -1.570796F, 0.0F));
        PartDefinition p_north = root.addOrReplaceChild("north",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 5.0F, 5.5F, 7.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7853982F, 0.0F, 0.0F));
        PartDefinition p_northwest = root.addOrReplaceChild("northwest",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 5.0F, 5.5F, 7.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7853982F, -0.7853982F, 0.0F));
        PartDefinition p_northeast = root.addOrReplaceChild("northeast",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 5.0F, 5.5F, 7.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7853982F, 0.7853982F, 0.0F));
        PartDefinition p_east = root.addOrReplaceChild("east",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 5.0F, 5.5F, 7.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7853982F, 1.570796F, 0.0F));
        PartDefinition p_southeast = root.addOrReplaceChild("southeast",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 5.0F, 5.5F, 7.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7853982F, 2.356194F, 0.0F));
        PartDefinition p_southwest = root.addOrReplaceChild("southwest",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 5.0F, 5.5F, 7.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7853982F, -2.356194F, 0.0F));
        PartDefinition p_south = root.addOrReplaceChild("south",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 5.0F, 5.5F, 7.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7853982F, 3.141593F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
