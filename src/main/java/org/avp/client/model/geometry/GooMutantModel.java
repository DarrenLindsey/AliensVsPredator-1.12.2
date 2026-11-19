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
 * Geometry converted automatically from the 1.12.2 ModelGooMutant by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class GooMutantModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "goo_mutant"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart headOverlay;
    public final ModelPart body;
    public final ModelPart spines;
    public final ModelPart lspike;
    public final ModelPart rspike;
    public final ModelPart rLeg;
    public final ModelPart rArm;
    public final ModelPart lLeg;
    public final ModelPart head;
    public final ModelPart head3;
    public final ModelPart head2;
    public final ModelPart lArm;

    public GooMutantModel(ModelPart root)
    {
        this.root = root;
        this.headOverlay = root.getChild("headOverlay");
        this.parts.put("headOverlay", this.headOverlay);
        this.body = root.getChild("body");
        this.parts.put("body", this.body);
        this.spines = this.body.getChild("spines");
        this.parts.put("spines", this.spines);
        this.lspike = this.body.getChild("lspike");
        this.parts.put("lspike", this.lspike);
        this.rspike = this.body.getChild("rspike");
        this.parts.put("rspike", this.rspike);
        this.rLeg = root.getChild("rLeg");
        this.parts.put("rLeg", this.rLeg);
        this.rArm = root.getChild("rArm");
        this.parts.put("rArm", this.rArm);
        this.lLeg = root.getChild("lLeg");
        this.parts.put("lLeg", this.lLeg);
        this.head = root.getChild("head");
        this.parts.put("head", this.head);
        this.head3 = this.head.getChild("head3");
        this.parts.put("head3", this.head3);
        this.head2 = this.head.getChild("head2");
        this.parts.put("head2", this.head2);
        this.lArm = root.getChild("lArm");
        this.parts.put("lArm", this.lArm);
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
        PartDefinition p_headOverlay = root.addOrReplaceChild("headOverlay",
                CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_spines = p_body.addOrReplaceChild("spines",
                CubeListBuilder.create().texOffs(0, 48).addBox(0.0F, 0.7F, 2.0F, 0.0F, 10.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lspike = p_body.addOrReplaceChild("lspike",
                CubeListBuilder.create().texOffs(0, 36).mirror().addBox(2.5F, 0.8F, 1.1F, 0.0F, 5.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.22759093446006054F, 0.0F));
        PartDefinition p_rspike = p_body.addOrReplaceChild("rspike",
                CubeListBuilder.create().texOffs(0, 36).addBox(-2.5F, 0.8F, 1.1F, 0.0F, 5.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.22759093446006054F, 0.0F));
        PartDefinition p_rLeg = root.addOrReplaceChild("rLeg",
                CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-1.9F, 12.0F, 0.0F));
        PartDefinition p_rArm = root.addOrReplaceChild("rArm",
                CubeListBuilder.create().texOffs(40, 16).addBox(-2.9F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-5.0F, 2.0F, 0.0F));
        PartDefinition p_lLeg = root.addOrReplaceChild("lLeg",
                CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(1.9F, 12.0F, 0.0F));
        PartDefinition p_head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_head3 = p_head.addOrReplaceChild("head3",
                CubeListBuilder.create().texOffs(67, 13).addBox(-3.0F, -8.6F, 5.4F, 6.0F, 6.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_head2 = p_head.addOrReplaceChild("head2",
                CubeListBuilder.create().texOffs(67, 0).addBox(-3.5F, -8.2F, 3.2F, 7.0F, 7.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_lArm = root.addOrReplaceChild("lArm",
                CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.1F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(5.0F, 2.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
