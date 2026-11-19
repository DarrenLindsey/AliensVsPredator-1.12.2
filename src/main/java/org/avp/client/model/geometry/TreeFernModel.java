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
 * Geometry converted automatically from the 1.12.2 ModelTreeFern by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class TreeFernModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "tree_fern"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart trunk04;
    public final ModelPart trunk05;
    public final ModelPart trunk03;
    public final ModelPart crown01;
    public final ModelPart lowerLeaf01a;
    public final ModelPart lowerLeaf01b;
    public final ModelPart lowerLeaf07a;
    public final ModelPart lowerLeaf07b;
    public final ModelPart lowerLeaf04a;
    public final ModelPart lowerLeaf04b;
    public final ModelPart lowerLeaf05a;
    public final ModelPart lowerLeaf05b;
    public final ModelPart lowerLeaf02a;
    public final ModelPart lowerLeaf02b;
    public final ModelPart lowerLeaf06a;
    public final ModelPart lowerLeaf06b;
    public final ModelPart lowerLeaf08a;
    public final ModelPart lowerLeaf08b;
    public final ModelPart lowerLeaf03a;
    public final ModelPart lowerLeaf03b;
    public final ModelPart crown02;
    public final ModelPart upperLeaf03a;
    public final ModelPart upperLeaf03b;
    public final ModelPart upperLeaf04a;
    public final ModelPart upperLeaf04b;
    public final ModelPart upperLeaf06a;
    public final ModelPart upperLeaf06b;
    public final ModelPart upperLeaf02a;
    public final ModelPart upperLeaf02b;
    public final ModelPart upperLeaf01a;
    public final ModelPart upperLeaf01b;
    public final ModelPart upperLeaf05a;
    public final ModelPart upperLeaf05b;
    public final ModelPart trunk01;
    public final ModelPart trunk02;

    public TreeFernModel(ModelPart root)
    {
        this.root = root;
        this.trunk04 = root.getChild("trunk04");
        this.parts.put("trunk04", this.trunk04);
        this.trunk05 = this.trunk04.getChild("trunk05");
        this.parts.put("trunk05", this.trunk05);
        this.trunk03 = root.getChild("trunk03");
        this.parts.put("trunk03", this.trunk03);
        this.crown01 = root.getChild("crown01");
        this.parts.put("crown01", this.crown01);
        this.lowerLeaf01a = this.crown01.getChild("lowerLeaf01a");
        this.parts.put("lowerLeaf01a", this.lowerLeaf01a);
        this.lowerLeaf01b = this.lowerLeaf01a.getChild("lowerLeaf01b");
        this.parts.put("lowerLeaf01b", this.lowerLeaf01b);
        this.lowerLeaf07a = this.crown01.getChild("lowerLeaf07a");
        this.parts.put("lowerLeaf07a", this.lowerLeaf07a);
        this.lowerLeaf07b = this.lowerLeaf07a.getChild("lowerLeaf07b");
        this.parts.put("lowerLeaf07b", this.lowerLeaf07b);
        this.lowerLeaf04a = this.crown01.getChild("lowerLeaf04a");
        this.parts.put("lowerLeaf04a", this.lowerLeaf04a);
        this.lowerLeaf04b = this.lowerLeaf04a.getChild("lowerLeaf04b");
        this.parts.put("lowerLeaf04b", this.lowerLeaf04b);
        this.lowerLeaf05a = this.crown01.getChild("lowerLeaf05a");
        this.parts.put("lowerLeaf05a", this.lowerLeaf05a);
        this.lowerLeaf05b = this.lowerLeaf05a.getChild("lowerLeaf05b");
        this.parts.put("lowerLeaf05b", this.lowerLeaf05b);
        this.lowerLeaf02a = this.crown01.getChild("lowerLeaf02a");
        this.parts.put("lowerLeaf02a", this.lowerLeaf02a);
        this.lowerLeaf02b = this.lowerLeaf02a.getChild("lowerLeaf02b");
        this.parts.put("lowerLeaf02b", this.lowerLeaf02b);
        this.lowerLeaf06a = this.crown01.getChild("lowerLeaf06a");
        this.parts.put("lowerLeaf06a", this.lowerLeaf06a);
        this.lowerLeaf06b = this.lowerLeaf06a.getChild("lowerLeaf06b");
        this.parts.put("lowerLeaf06b", this.lowerLeaf06b);
        this.lowerLeaf08a = this.crown01.getChild("lowerLeaf08a");
        this.parts.put("lowerLeaf08a", this.lowerLeaf08a);
        this.lowerLeaf08b = this.lowerLeaf08a.getChild("lowerLeaf08b");
        this.parts.put("lowerLeaf08b", this.lowerLeaf08b);
        this.lowerLeaf03a = this.crown01.getChild("lowerLeaf03a");
        this.parts.put("lowerLeaf03a", this.lowerLeaf03a);
        this.lowerLeaf03b = this.lowerLeaf03a.getChild("lowerLeaf03b");
        this.parts.put("lowerLeaf03b", this.lowerLeaf03b);
        this.crown02 = root.getChild("crown02");
        this.parts.put("crown02", this.crown02);
        this.upperLeaf03a = this.crown02.getChild("upperLeaf03a");
        this.parts.put("upperLeaf03a", this.upperLeaf03a);
        this.upperLeaf03b = this.upperLeaf03a.getChild("upperLeaf03b");
        this.parts.put("upperLeaf03b", this.upperLeaf03b);
        this.upperLeaf04a = this.crown02.getChild("upperLeaf04a");
        this.parts.put("upperLeaf04a", this.upperLeaf04a);
        this.upperLeaf04b = this.upperLeaf04a.getChild("upperLeaf04b");
        this.parts.put("upperLeaf04b", this.upperLeaf04b);
        this.upperLeaf06a = this.crown02.getChild("upperLeaf06a");
        this.parts.put("upperLeaf06a", this.upperLeaf06a);
        this.upperLeaf06b = this.upperLeaf06a.getChild("upperLeaf06b");
        this.parts.put("upperLeaf06b", this.upperLeaf06b);
        this.upperLeaf02a = this.crown02.getChild("upperLeaf02a");
        this.parts.put("upperLeaf02a", this.upperLeaf02a);
        this.upperLeaf02b = this.upperLeaf02a.getChild("upperLeaf02b");
        this.parts.put("upperLeaf02b", this.upperLeaf02b);
        this.upperLeaf01a = this.crown02.getChild("upperLeaf01a");
        this.parts.put("upperLeaf01a", this.upperLeaf01a);
        this.upperLeaf01b = this.upperLeaf01a.getChild("upperLeaf01b");
        this.parts.put("upperLeaf01b", this.upperLeaf01b);
        this.upperLeaf05a = this.crown02.getChild("upperLeaf05a");
        this.parts.put("upperLeaf05a", this.upperLeaf05a);
        this.upperLeaf05b = this.upperLeaf05a.getChild("upperLeaf05b");
        this.parts.put("upperLeaf05b", this.upperLeaf05b);
        this.trunk01 = root.getChild("trunk01");
        this.parts.put("trunk01", this.trunk01);
        this.trunk02 = this.trunk01.getChild("trunk02");
        this.parts.put("trunk02", this.trunk02);
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
        PartDefinition p_trunk04 = root.addOrReplaceChild("trunk04",
                CubeListBuilder.create().texOffs(0, 20).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 1.1F, 0.0F));
        PartDefinition p_trunk05 = p_trunk04.addOrReplaceChild("trunk05",
                CubeListBuilder.create().texOffs(0, 25).addBox(-2.0F, -2.1F, -2.0F, 4.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, -0.9F, 0.0F));
        PartDefinition p_trunk03 = root.addOrReplaceChild("trunk03",
                CubeListBuilder.create().texOffs(19, 0).addBox(-1.0F, -14.0F, -1.0F, 2.0F, 14.0F, 2.0F),
                PartPose.offset(0.0F, 15.0F, 0.0F));
        PartDefinition p_crown01 = root.addOrReplaceChild("crown01",
                CubeListBuilder.create().texOffs(28, 0).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, -1.9F, 0.0F));
        PartDefinition p_lowerLeaf01a = p_crown01.addOrReplaceChild("lowerLeaf01a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf01b = p_lowerLeaf01a.addOrReplaceChild("lowerLeaf01b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf07a = p_crown01.addOrReplaceChild("lowerLeaf07a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.18203784098300857F, 2.356194490192345F, 0.0F));
        PartDefinition p_lowerLeaf07b = p_lowerLeaf07a.addOrReplaceChild("lowerLeaf07b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf04a = p_crown01.addOrReplaceChild("lowerLeaf04a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.136659280431156F, 3.141592653589793F, 0.0F));
        PartDefinition p_lowerLeaf04b = p_lowerLeaf04a.addOrReplaceChild("lowerLeaf04b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf05a = p_crown01.addOrReplaceChild("lowerLeaf05a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.18203784098300857F, 0.7853981633974483F, 0.0F));
        PartDefinition p_lowerLeaf05b = p_lowerLeaf05a.addOrReplaceChild("lowerLeaf05b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf02a = p_crown01.addOrReplaceChild("lowerLeaf02a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.27314402793711257F, 1.5707963267948966F, 0.0F));
        PartDefinition p_lowerLeaf02b = p_lowerLeaf02a.addOrReplaceChild("lowerLeaf02b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf06a = p_crown01.addOrReplaceChild("lowerLeaf06a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.18203784098300857F, -0.7853981633974483F, 0.0F));
        PartDefinition p_lowerLeaf06b = p_lowerLeaf06a.addOrReplaceChild("lowerLeaf06b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf08a = p_crown01.addOrReplaceChild("lowerLeaf08a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.18203784098300857F, -2.356194490192345F, 0.0F));
        PartDefinition p_lowerLeaf08b = p_lowerLeaf08a.addOrReplaceChild("lowerLeaf08b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf03a = p_crown01.addOrReplaceChild("lowerLeaf03a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.27314402793711257F, -1.5707963267948966F, 0.0F));
        PartDefinition p_lowerLeaf03b = p_lowerLeaf03a.addOrReplaceChild("lowerLeaf03b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_crown02 = root.addOrReplaceChild("crown02",
                CubeListBuilder.create().texOffs(31, 6).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, -2.9F, 0.0F));
        PartDefinition p_upperLeaf03a = p_crown02.addOrReplaceChild("upperLeaf03a",
                CubeListBuilder.create().texOffs(18, 15).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.8F, 0.0F, -0.36425021489121656F, -2.0943951023931953F, 0.0F));
        PartDefinition p_upperLeaf03b = p_upperLeaf03a.addOrReplaceChild("upperLeaf03b",
                CubeListBuilder.create().texOffs(22, 25).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_upperLeaf04a = p_crown02.addOrReplaceChild("upperLeaf04a",
                CubeListBuilder.create().texOffs(18, 15).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.36425021489121656F, 1.0471975511965976F, 0.0F));
        PartDefinition p_upperLeaf04b = p_upperLeaf04a.addOrReplaceChild("upperLeaf04b",
                CubeListBuilder.create().texOffs(22, 25).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_upperLeaf06a = p_crown02.addOrReplaceChild("upperLeaf06a",
                CubeListBuilder.create().texOffs(18, 15).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.36425021489121656F, 3.141592653589793F, 0.0F));
        PartDefinition p_upperLeaf06b = p_upperLeaf06a.addOrReplaceChild("upperLeaf06b",
                CubeListBuilder.create().texOffs(22, 25).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_upperLeaf02a = p_crown02.addOrReplaceChild("upperLeaf02a",
                CubeListBuilder.create().texOffs(18, 15).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.8F, 0.0F, -0.36425021489121656F, 2.0943951023931953F, 0.0F));
        PartDefinition p_upperLeaf02b = p_upperLeaf02a.addOrReplaceChild("upperLeaf02b",
                CubeListBuilder.create().texOffs(22, 25).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_upperLeaf01a = p_crown02.addOrReplaceChild("upperLeaf01a",
                CubeListBuilder.create().texOffs(18, 15).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.8F, 0.0F, -0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_upperLeaf01b = p_upperLeaf01a.addOrReplaceChild("upperLeaf01b",
                CubeListBuilder.create().texOffs(22, 25).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_upperLeaf05a = p_crown02.addOrReplaceChild("upperLeaf05a",
                CubeListBuilder.create().texOffs(18, 15).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.36425021489121656F, -1.0471975511965976F, 0.0F));
        PartDefinition p_upperLeaf05b = p_upperLeaf05a.addOrReplaceChild("upperLeaf05b",
                CubeListBuilder.create().texOffs(22, 25).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_trunk01 = root.addOrReplaceChild("trunk01",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition p_trunk02 = p_trunk01.addOrReplaceChild("trunk02",
                CubeListBuilder.create().texOffs(0, 11).addBox(-1.5F, -5.0F, -1.5F, 3.0F, 5.0F, 3.0F),
                PartPose.offset(0.0F, -4.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
