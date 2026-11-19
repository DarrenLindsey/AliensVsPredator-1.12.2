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
 * Geometry converted automatically from the 1.12.2 ModelOctohugger by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class OctohuggerModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "octohugger"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart rFlap1;
    public final ModelPart rFlap2;
    public final ModelPart bFlap1;
    public final ModelPart bFlap2;
    public final ModelPart head2;
    public final ModelPart body2;
    public final ModelPart body;
    public final ModelPart head3;
    public final ModelPart head1;
    public final ModelPart head4;
    public final ModelPart fFlap1;
    public final ModelPart fFlap2;
    public final ModelPart lFlap1;
    public final ModelPart lFlap2;

    public OctohuggerModel(ModelPart root)
    {
        this.root = root;
        this.rFlap1 = root.getChild("rFlap1");
        this.parts.put("rFlap1", this.rFlap1);
        this.rFlap2 = this.rFlap1.getChild("rFlap2");
        this.parts.put("rFlap2", this.rFlap2);
        this.bFlap1 = root.getChild("bFlap1");
        this.parts.put("bFlap1", this.bFlap1);
        this.bFlap2 = this.bFlap1.getChild("bFlap2");
        this.parts.put("bFlap2", this.bFlap2);
        this.head2 = root.getChild("head2");
        this.parts.put("head2", this.head2);
        this.body2 = root.getChild("body2");
        this.parts.put("body2", this.body2);
        this.body = root.getChild("body");
        this.parts.put("body", this.body);
        this.head3 = this.body.getChild("head3");
        this.parts.put("head3", this.head3);
        this.head1 = this.body.getChild("head1");
        this.parts.put("head1", this.head1);
        this.head4 = this.body.getChild("head4");
        this.parts.put("head4", this.head4);
        this.fFlap1 = root.getChild("fFlap1");
        this.parts.put("fFlap1", this.fFlap1);
        this.fFlap2 = this.fFlap1.getChild("fFlap2");
        this.parts.put("fFlap2", this.fFlap2);
        this.lFlap1 = root.getChild("lFlap1");
        this.parts.put("lFlap1", this.lFlap1);
        this.lFlap2 = this.lFlap1.getChild("lFlap2");
        this.parts.put("lFlap2", this.lFlap2);
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
        PartDefinition p_rFlap1 = root.addOrReplaceChild("rFlap1",
                CubeListBuilder.create().texOffs(27, 11).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 3.0F, 2.0F),
                PartPose.offset(-1.0F, 2.1F, 0.0F));
        PartDefinition p_rFlap2 = p_rFlap1.addOrReplaceChild("rFlap2",
                CubeListBuilder.create().texOffs(27, 17).addBox(-0.4F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 2.5F, 0.0F));
        PartDefinition p_bFlap1 = root.addOrReplaceChild("bFlap1",
                CubeListBuilder.create().texOffs(17, 12).mirror().addBox(-1.0F, 0.0F, -0.5F, 2.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 2.1F, 1.0F));
        PartDefinition p_bFlap2 = p_bFlap1.addOrReplaceChild("bFlap2",
                CubeListBuilder.create().texOffs(17, 18).addBox(-0.5F, 0.0F, -0.6F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 2.5F, 0.0F));
        PartDefinition p_head2 = root.addOrReplaceChild("head2",
                CubeListBuilder.create().texOffs(0, 7).addBox(-1.5F, -5.4F, -1.5F, 3.0F, 2.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_body2 = root.addOrReplaceChild("body2",
                CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, -1.7F, -1.5F, 3.0F, 3.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 17).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 5.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_head3 = p_body.addOrReplaceChild("head3",
                CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_head1 = p_body.addOrReplaceChild("head1",
                CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_head4 = p_body.addOrReplaceChild("head4",
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -5.6F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_fFlap1 = root.addOrReplaceChild("fFlap1",
                CubeListBuilder.create().texOffs(17, 0).addBox(-1.0F, -0.1F, -0.5F, 2.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 2.1F, -1.0F));
        PartDefinition p_fFlap2 = p_fFlap1.addOrReplaceChild("fFlap2",
                CubeListBuilder.create().texOffs(17, 5).addBox(-0.5F, 0.0F, -0.4F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 2.5F, 0.0F));
        PartDefinition p_lFlap1 = root.addOrReplaceChild("lFlap1",
                CubeListBuilder.create().texOffs(27, 0).addBox(-0.5F, -0.1F, -1.0F, 1.0F, 3.0F, 2.0F),
                PartPose.offset(1.0F, 2.1F, 0.0F));
        PartDefinition p_lFlap2 = p_lFlap1.addOrReplaceChild("lFlap2",
                CubeListBuilder.create().texOffs(27, 6).addBox(-0.6F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 2.5F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
