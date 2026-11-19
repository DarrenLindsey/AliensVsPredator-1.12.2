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
 * Geometry converted automatically from the 1.12.2 ModelLaserMine by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class LaserMineModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "laser_mine"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart lSupport;
    public final ModelPart rSupport;
    public final ModelPart bSupport;
    public final ModelPart core;
    public final ModelPart tPlate;
    public final ModelPart rPlate;
    public final ModelPart lPlate;

    public LaserMineModel(ModelPart root)
    {
        this.root = root;
        this.lSupport = root.getChild("lSupport");
        this.parts.put("lSupport", this.lSupport);
        this.rSupport = root.getChild("rSupport");
        this.parts.put("rSupport", this.rSupport);
        this.bSupport = root.getChild("bSupport");
        this.parts.put("bSupport", this.bSupport);
        this.core = root.getChild("core");
        this.parts.put("core", this.core);
        this.tPlate = root.getChild("tPlate");
        this.parts.put("tPlate", this.tPlate);
        this.rPlate = root.getChild("rPlate");
        this.parts.put("rPlate", this.rPlate);
        this.lPlate = root.getChild("lPlate");
        this.parts.put("lPlate", this.lPlate);
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
        PartDefinition p_lSupport = root.addOrReplaceChild("lSupport",
                CubeListBuilder.create().texOffs(9, 0).addBox(-1.0F, -7.0F, -2.0F, 2.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.296706F, 0.0F, 1.047198F));
        PartDefinition p_rSupport = root.addOrReplaceChild("rSupport",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -7.0F, -2.0F, 2.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.296706F, 0.0F, -1.047198F));
        PartDefinition p_bSupport = root.addOrReplaceChild("bSupport",
                CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -7.0F, -2.0F, 2.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.296706F, 0.0F, -3.141593F));
        PartDefinition p_core = root.addOrReplaceChild("core",
                CubeListBuilder.create().texOffs(0, 9).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tPlate = root.addOrReplaceChild("tPlate",
                CubeListBuilder.create().texOffs(28, 0).addBox(-5.5F, -4.0F, 0.0F, 11.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rPlate = root.addOrReplaceChild("rPlate",
                CubeListBuilder.create().texOffs(0, 15).addBox(-4.2F, -5.5F, 0.0F, 5.0F, 11.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5235988F));
        PartDefinition p_lPlate = root.addOrReplaceChild("lPlate",
                CubeListBuilder.create().texOffs(16, 15).addBox(-0.8F, -5.5F, 0.0F, 5.0F, 11.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5235988F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
