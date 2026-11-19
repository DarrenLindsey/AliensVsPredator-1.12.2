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
 * Geometry converted automatically from the 1.12.2 ModelGroundFern by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class GroundFernModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "ground_fern"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart crown02;
    public final ModelPart fiddlehead02;
    public final ModelPart upperLeaf01a;
    public final ModelPart upperLeaf01b;
    public final ModelPart upperLeaf03a;
    public final ModelPart upperLeaf03b;
    public final ModelPart upperLeaf04a;
    public final ModelPart upperLeaf04b;
    public final ModelPart fiddlehead01;
    public final ModelPart upperLeaf05a;
    public final ModelPart upperLeaf05b;
    public final ModelPart fiddlehead03;
    public final ModelPart upperLeaf06a;
    public final ModelPart upperLeaf06b;
    public final ModelPart upperLeaf02a;
    public final ModelPart upperLeaf02b;
    public final ModelPart crown01;
    public final ModelPart lowerLeaf04a;
    public final ModelPart lowerLeaf04b;
    public final ModelPart lowerLeaf06a;
    public final ModelPart lowerLeaf06b;
    public final ModelPart lowerLeaf07a;
    public final ModelPart lowerLeaf07b;
    public final ModelPart lowerLeaf08a;
    public final ModelPart lowerLeaf08b;
    public final ModelPart lowerLeaf02a;
    public final ModelPart lowerLeaf02b;
    public final ModelPart lowerLeaf01a;
    public final ModelPart lowerLeaf01b;
    public final ModelPart lowerLeaf03a;
    public final ModelPart lowerLeaf03b;
    public final ModelPart lowerLeaf05a;
    public final ModelPart lowerLeaf05b;

    public GroundFernModel(ModelPart root)
    {
        this.root = root;
        this.crown02 = root.getChild("crown02");
        this.parts.put("crown02", this.crown02);
        this.fiddlehead02 = this.crown02.getChild("fiddlehead02");
        this.parts.put("fiddlehead02", this.fiddlehead02);
        this.upperLeaf01a = this.crown02.getChild("upperLeaf01a");
        this.parts.put("upperLeaf01a", this.upperLeaf01a);
        this.upperLeaf01b = this.upperLeaf01a.getChild("upperLeaf01b");
        this.parts.put("upperLeaf01b", this.upperLeaf01b);
        this.upperLeaf03a = this.crown02.getChild("upperLeaf03a");
        this.parts.put("upperLeaf03a", this.upperLeaf03a);
        this.upperLeaf03b = this.upperLeaf03a.getChild("upperLeaf03b");
        this.parts.put("upperLeaf03b", this.upperLeaf03b);
        this.upperLeaf04a = this.crown02.getChild("upperLeaf04a");
        this.parts.put("upperLeaf04a", this.upperLeaf04a);
        this.upperLeaf04b = this.upperLeaf04a.getChild("upperLeaf04b");
        this.parts.put("upperLeaf04b", this.upperLeaf04b);
        this.fiddlehead01 = this.crown02.getChild("fiddlehead01");
        this.parts.put("fiddlehead01", this.fiddlehead01);
        this.upperLeaf05a = this.crown02.getChild("upperLeaf05a");
        this.parts.put("upperLeaf05a", this.upperLeaf05a);
        this.upperLeaf05b = this.upperLeaf05a.getChild("upperLeaf05b");
        this.parts.put("upperLeaf05b", this.upperLeaf05b);
        this.fiddlehead03 = this.crown02.getChild("fiddlehead03");
        this.parts.put("fiddlehead03", this.fiddlehead03);
        this.upperLeaf06a = this.crown02.getChild("upperLeaf06a");
        this.parts.put("upperLeaf06a", this.upperLeaf06a);
        this.upperLeaf06b = this.upperLeaf06a.getChild("upperLeaf06b");
        this.parts.put("upperLeaf06b", this.upperLeaf06b);
        this.upperLeaf02a = this.crown02.getChild("upperLeaf02a");
        this.parts.put("upperLeaf02a", this.upperLeaf02a);
        this.upperLeaf02b = this.upperLeaf02a.getChild("upperLeaf02b");
        this.parts.put("upperLeaf02b", this.upperLeaf02b);
        this.crown01 = root.getChild("crown01");
        this.parts.put("crown01", this.crown01);
        this.lowerLeaf04a = this.crown01.getChild("lowerLeaf04a");
        this.parts.put("lowerLeaf04a", this.lowerLeaf04a);
        this.lowerLeaf04b = this.lowerLeaf04a.getChild("lowerLeaf04b");
        this.parts.put("lowerLeaf04b", this.lowerLeaf04b);
        this.lowerLeaf06a = this.crown01.getChild("lowerLeaf06a");
        this.parts.put("lowerLeaf06a", this.lowerLeaf06a);
        this.lowerLeaf06b = this.lowerLeaf06a.getChild("lowerLeaf06b");
        this.parts.put("lowerLeaf06b", this.lowerLeaf06b);
        this.lowerLeaf07a = this.crown01.getChild("lowerLeaf07a");
        this.parts.put("lowerLeaf07a", this.lowerLeaf07a);
        this.lowerLeaf07b = this.lowerLeaf07a.getChild("lowerLeaf07b");
        this.parts.put("lowerLeaf07b", this.lowerLeaf07b);
        this.lowerLeaf08a = this.crown01.getChild("lowerLeaf08a");
        this.parts.put("lowerLeaf08a", this.lowerLeaf08a);
        this.lowerLeaf08b = this.lowerLeaf08a.getChild("lowerLeaf08b");
        this.parts.put("lowerLeaf08b", this.lowerLeaf08b);
        this.lowerLeaf02a = this.crown01.getChild("lowerLeaf02a");
        this.parts.put("lowerLeaf02a", this.lowerLeaf02a);
        this.lowerLeaf02b = this.lowerLeaf02a.getChild("lowerLeaf02b");
        this.parts.put("lowerLeaf02b", this.lowerLeaf02b);
        this.lowerLeaf01a = this.crown01.getChild("lowerLeaf01a");
        this.parts.put("lowerLeaf01a", this.lowerLeaf01a);
        this.lowerLeaf01b = this.lowerLeaf01a.getChild("lowerLeaf01b");
        this.parts.put("lowerLeaf01b", this.lowerLeaf01b);
        this.lowerLeaf03a = this.crown01.getChild("lowerLeaf03a");
        this.parts.put("lowerLeaf03a", this.lowerLeaf03a);
        this.lowerLeaf03b = this.lowerLeaf03a.getChild("lowerLeaf03b");
        this.parts.put("lowerLeaf03b", this.lowerLeaf03b);
        this.lowerLeaf05a = this.crown01.getChild("lowerLeaf05a");
        this.parts.put("lowerLeaf05a", this.lowerLeaf05a);
        this.lowerLeaf05b = this.lowerLeaf05a.getChild("lowerLeaf05b");
        this.parts.put("lowerLeaf05b", this.lowerLeaf05b);
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
        PartDefinition p_crown02 = root.addOrReplaceChild("crown02",
                CubeListBuilder.create().texOffs(31, 6).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 23.1F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_fiddlehead02 = p_crown02.addOrReplaceChild("fiddlehead02",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -9.0F, -1.5F, 0.0F, 9.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.5009094953223726F, 2.0943951023931953F, 0.0F));
        PartDefinition p_upperLeaf01a = p_crown02.addOrReplaceChild("upperLeaf01a",
                CubeListBuilder.create().texOffs(18, 15).addBox(-2.5F, 0.0F, -10.0F, 5.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.8F, 0.0F, -0.6373942428283291F, 0.0F, 0.0F));
        PartDefinition p_upperLeaf01b = p_upperLeaf01a.addOrReplaceChild("upperLeaf01b",
                CubeListBuilder.create().texOffs(22, 25).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_upperLeaf03a = p_crown02.addOrReplaceChild("upperLeaf03a",
                CubeListBuilder.create().texOffs(18, 15).addBox(-2.5F, 0.0F, -10.0F, 5.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.8F, 0.0F, -0.6373942428283291F, -2.0943951023931953F, 0.0F));
        PartDefinition p_upperLeaf03b = p_upperLeaf03a.addOrReplaceChild("upperLeaf03b",
                CubeListBuilder.create().texOffs(22, 25).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_upperLeaf04a = p_crown02.addOrReplaceChild("upperLeaf04a",
                CubeListBuilder.create().texOffs(18, 15).addBox(-2.5F, 0.0F, -10.0F, 5.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4553564018453205F, 1.0471975511965976F, 0.0F));
        PartDefinition p_upperLeaf04b = p_upperLeaf04a.addOrReplaceChild("upperLeaf04b",
                CubeListBuilder.create().texOffs(22, 25).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_fiddlehead01 = p_crown02.addOrReplaceChild("fiddlehead01",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -9.0F, -1.5F, 0.0F, 9.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_upperLeaf05a = p_crown02.addOrReplaceChild("upperLeaf05a",
                CubeListBuilder.create().texOffs(18, 15).addBox(-2.5F, 0.0F, -10.0F, 5.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4553564018453205F, -1.0471975511965976F, 0.0F));
        PartDefinition p_upperLeaf05b = p_upperLeaf05a.addOrReplaceChild("upperLeaf05b",
                CubeListBuilder.create().texOffs(22, 25).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_fiddlehead03 = p_crown02.addOrReplaceChild("fiddlehead03",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -9.0F, -1.5F, 0.0F, 9.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.5009094953223726F, -2.0943951023931953F, 0.0F));
        PartDefinition p_upperLeaf06a = p_crown02.addOrReplaceChild("upperLeaf06a",
                CubeListBuilder.create().texOffs(18, 15).addBox(-2.5F, 0.0F, -10.0F, 5.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4553564018453205F, 3.141592653589793F, 0.0F));
        PartDefinition p_upperLeaf06b = p_upperLeaf06a.addOrReplaceChild("upperLeaf06b",
                CubeListBuilder.create().texOffs(22, 25).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_upperLeaf02a = p_crown02.addOrReplaceChild("upperLeaf02a",
                CubeListBuilder.create().texOffs(18, 15).addBox(-2.5F, 0.0F, -10.0F, 5.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.8F, 0.0F, -0.6373942428283291F, 2.0943951023931953F, 0.0F));
        PartDefinition p_upperLeaf02b = p_upperLeaf02a.addOrReplaceChild("upperLeaf02b",
                CubeListBuilder.create().texOffs(22, 25).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_crown01 = root.addOrReplaceChild("crown01",
                CubeListBuilder.create().texOffs(28, 0).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition p_lowerLeaf04a = p_crown01.addOrReplaceChild("lowerLeaf04a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.4553564018453205F, 3.141592653589793F, 0.0F));
        PartDefinition p_lowerLeaf04b = p_lowerLeaf04a.addOrReplaceChild("lowerLeaf04b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf06a = p_crown01.addOrReplaceChild("lowerLeaf06a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -0.22759093446006054F, -0.7853981633974483F, 0.0F));
        PartDefinition p_lowerLeaf06b = p_lowerLeaf06a.addOrReplaceChild("lowerLeaf06b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf07a = p_crown01.addOrReplaceChild("lowerLeaf07a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -0.22759093446006054F, 2.356194490192345F, 0.0F));
        PartDefinition p_lowerLeaf07b = p_lowerLeaf07a.addOrReplaceChild("lowerLeaf07b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf08a = p_crown01.addOrReplaceChild("lowerLeaf08a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -0.22759093446006054F, -2.356194490192345F, 0.0F));
        PartDefinition p_lowerLeaf08b = p_lowerLeaf08a.addOrReplaceChild("lowerLeaf08b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf02a = p_crown01.addOrReplaceChild("lowerLeaf02a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.36425021489121656F, 1.5707963267948966F, 0.0F));
        PartDefinition p_lowerLeaf02b = p_lowerLeaf02a.addOrReplaceChild("lowerLeaf02b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf01a = p_crown01.addOrReplaceChild("lowerLeaf01a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, -0.4553564018453205F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf01b = p_lowerLeaf01a.addOrReplaceChild("lowerLeaf01b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf03a = p_crown01.addOrReplaceChild("lowerLeaf03a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.36425021489121656F, -1.5707963267948966F, 0.0F));
        PartDefinition p_lowerLeaf03b = p_lowerLeaf03a.addOrReplaceChild("lowerLeaf03b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_lowerLeaf05a = p_crown01.addOrReplaceChild("lowerLeaf05a",
                CubeListBuilder.create().texOffs(34, 0).addBox(-3.5F, 0.0F, -10.0F, 7.0F, 0.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -0.22759093446006054F, 0.7853981633974483F, 0.0F));
        PartDefinition p_lowerLeaf05b = p_lowerLeaf05a.addOrReplaceChild("lowerLeaf05b",
                CubeListBuilder.create().texOffs(38, 10).addBox(-3.5F, 0.0F, -6.0F, 7.0F, 0.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.136659280431156F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
