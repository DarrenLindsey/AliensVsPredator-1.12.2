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
 * Geometry converted automatically from the 1.12.2 ModelPlasmaCannon by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class PlasmaCannonModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "plasma_cannon"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart supportBar;
    public final ModelPart swivelHinge;
    public final ModelPart lTop;
    public final ModelPart bottom;
    public final ModelPart rBottom;
    public final ModelPart top;
    public final ModelPart rTop;
    public final ModelPart lBottom;
    public final ModelPart supportBarTop;
    public final ModelPart barrel;

    public PlasmaCannonModel(ModelPart root)
    {
        this.root = root;
        this.supportBar = root.getChild("supportBar");
        this.parts.put("supportBar", this.supportBar);
        this.swivelHinge = this.supportBar.getChild("swivelHinge");
        this.parts.put("swivelHinge", this.swivelHinge);
        this.lTop = this.swivelHinge.getChild("lTop");
        this.parts.put("lTop", this.lTop);
        this.bottom = this.swivelHinge.getChild("bottom");
        this.parts.put("bottom", this.bottom);
        this.rBottom = this.swivelHinge.getChild("rBottom");
        this.parts.put("rBottom", this.rBottom);
        this.top = this.swivelHinge.getChild("top");
        this.parts.put("top", this.top);
        this.rTop = this.swivelHinge.getChild("rTop");
        this.parts.put("rTop", this.rTop);
        this.lBottom = this.swivelHinge.getChild("lBottom");
        this.parts.put("lBottom", this.lBottom);
        this.supportBarTop = this.swivelHinge.getChild("supportBarTop");
        this.parts.put("supportBarTop", this.supportBarTop);
        this.barrel = this.supportBarTop.getChild("barrel");
        this.parts.put("barrel", this.barrel);
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
        PartDefinition p_supportBar = root.addOrReplaceChild("supportBar",
                CubeListBuilder.create().texOffs(38, 20).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 10.8F, 10.0F, 1.3089969389957472F, 0.0F, 0.0F));
        PartDefinition p_swivelHinge = p_supportBar.addOrReplaceChild("swivelHinge",
                CubeListBuilder.create().texOffs(16, 0).addBox(-2.5F, -2.0F, -2.0F, 5.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.3F, 11.0F, -0.5235987755982988F, 0.0F, 0.0F));
        PartDefinition p_lTop = p_swivelHinge.addOrReplaceChild("lTop",
                CubeListBuilder.create().texOffs(0, 8).mirror().addBox(1.0F, -1.2F, -9.2F, 1.0F, 2.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7546803685623481F, -0.22060961745208327F, -0.22759093446006054F));
        PartDefinition p_bottom = p_swivelHinge.addOrReplaceChild("bottom",
                CubeListBuilder.create().texOffs(23, 9).addBox(-1.0F, 0.9F, -9.0F, 2.0F, 1.0F, 9.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F, 0.0F));
        PartDefinition p_rBottom = p_swivelHinge.addOrReplaceChild("rBottom",
                CubeListBuilder.create().texOffs(0, 20).addBox(-2.1F, -0.5F, -9.2F, 1.0F, 2.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7546803685623481F, -0.22060961745208327F, -0.22759093446006054F));
        PartDefinition p_top = p_swivelHinge.addOrReplaceChild("top",
                CubeListBuilder.create().texOffs(35, 0).addBox(-1.0F, -1.4F, -9.2F, 2.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F, 0.0F));
        PartDefinition p_rTop = p_swivelHinge.addOrReplaceChild("rTop",
                CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, -1.2F, -9.2F, 1.0F, 2.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7546803685623481F, 0.22060961745208327F, 0.22759093446006054F));
        PartDefinition p_lBottom = p_swivelHinge.addOrReplaceChild("lBottom",
                CubeListBuilder.create().texOffs(0, 20).mirror().addBox(1.1F, -0.5F, -9.2F, 1.0F, 2.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7546803685623481F, 0.22060961745208327F, 0.22759093446006054F));
        PartDefinition p_supportBarTop = p_swivelHinge.addOrReplaceChild("supportBarTop",
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -2.7F, -6.9F, 1.0F, 1.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5759586531581287F, 0.0F, 0.0F));
        PartDefinition p_barrel = p_supportBarTop.addOrReplaceChild("barrel",
                CubeListBuilder.create().texOffs(23, 20).addBox(-1.0F, -1.0F, -4.2F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -1.9F, -9.0F, -0.15009831567151233F, 0.143116998663535F, 0.7853981633974483F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
