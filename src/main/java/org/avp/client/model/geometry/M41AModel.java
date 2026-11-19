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
 * Geometry converted automatically from the 1.12.2 ModelM41A by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class M41AModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "m41_a"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart stockEnd;
    public final ModelPart stock1;
    public final ModelPart sight1;
    public final ModelPart sight2;
    public final ModelPart underrail;
    public final ModelPart underrail2;
    public final ModelPart sight3;
    public final ModelPart gunBase1;
    public final ModelPart gunBase2;
    public final ModelPart barrel;
    public final ModelPart gunBase3;
    public final ModelPart mechanism;
    public final ModelPart grip;
    public final ModelPart grip2;
    public final ModelPart handle;
    public final ModelPart gunFrame;

    public M41AModel(ModelPart root)
    {
        this.root = root;
        this.stockEnd = root.getChild("stockEnd");
        this.parts.put("stockEnd", this.stockEnd);
        this.stock1 = root.getChild("stock1");
        this.parts.put("stock1", this.stock1);
        this.sight1 = root.getChild("sight1");
        this.parts.put("sight1", this.sight1);
        this.sight2 = root.getChild("sight2");
        this.parts.put("sight2", this.sight2);
        this.underrail = root.getChild("underrail");
        this.parts.put("underrail", this.underrail);
        this.underrail2 = root.getChild("underrail2");
        this.parts.put("underrail2", this.underrail2);
        this.sight3 = root.getChild("sight3");
        this.parts.put("sight3", this.sight3);
        this.gunBase1 = root.getChild("gunBase1");
        this.parts.put("gunBase1", this.gunBase1);
        this.gunBase2 = root.getChild("gunBase2");
        this.parts.put("gunBase2", this.gunBase2);
        this.barrel = root.getChild("barrel");
        this.parts.put("barrel", this.barrel);
        this.gunBase3 = root.getChild("gunBase3");
        this.parts.put("gunBase3", this.gunBase3);
        this.mechanism = root.getChild("mechanism");
        this.parts.put("mechanism", this.mechanism);
        this.grip = root.getChild("grip");
        this.parts.put("grip", this.grip);
        this.grip2 = root.getChild("grip2");
        this.parts.put("grip2", this.grip2);
        this.handle = root.getChild("handle");
        this.parts.put("handle", this.handle);
        this.gunFrame = root.getChild("gunFrame");
        this.parts.put("gunFrame", this.gunFrame);
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
        PartDefinition p_stockEnd = root.addOrReplaceChild("stockEnd",
                CubeListBuilder.create().texOffs(13, 26).addBox(-2.0F, 18.0F, -2.0F, 2.0F, 4.0F, 1.0F),
                PartPose.offset(-3.0F, -10.0F, -5.0F));
        PartDefinition p_stock1 = root.addOrReplaceChild("stock1",
                CubeListBuilder.create().texOffs(37, 12).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(-5.0F, 8.033334F, -6.0F));
        PartDefinition p_sight1 = root.addOrReplaceChild("sight1",
                CubeListBuilder.create().texOffs(12, 11).addBox(0.0F, 0.0F, 0.0F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(-4.5F, 6.7F, -3.0F, -0.9687629F, 0.0F, 0.0F));
        PartDefinition p_sight2 = root.addOrReplaceChild("sight2",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 7.0F, 1.0F),
                PartPose.offsetAndRotation(-4.5F, 7.65F, -3.0F, 1.5707963267948966F, 0.0F, 0.0F));
        PartDefinition p_underrail = root.addOrReplaceChild("underrail",
                CubeListBuilder.create().texOffs(17, 9).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(-5.0F, 9.0F, -4.0F));
        PartDefinition p_underrail2 = root.addOrReplaceChild("underrail2",
                CubeListBuilder.create().texOffs(16, 0).addBox(0.0F, 0.0F, 0.0F, 3.0F, 1.0F, 7.0F),
                PartPose.offset(-5.5F, 9.0F, -2.0F));
        PartDefinition p_sight3 = root.addOrReplaceChild("sight3",
                CubeListBuilder.create().texOffs(0, 8).addBox(0.0F, 0.0F, 0.0F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(-4.5F, 7.35F, 3.3F, 0.7326949F, 0.0F, 0.0F));
        PartDefinition p_gunBase1 = root.addOrReplaceChild("gunBase1",
                CubeListBuilder.create().texOffs(38, 1).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 8.0F),
                PartPose.offset(-5.0F, 9.0F, 1.0F));
        PartDefinition p_gunBase2 = root.addOrReplaceChild("gunBase2",
                CubeListBuilder.create().texOffs(5, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 5.0F),
                PartPose.offset(-5.0F, 8.0F, 1.3F));
        PartDefinition p_barrel = root.addOrReplaceChild("barrel",
                CubeListBuilder.create().texOffs(5, 11).addBox(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(-4.5F, 9.0F, 9.0F));
        PartDefinition p_gunBase3 = root.addOrReplaceChild("gunBase3",
                CubeListBuilder.create().texOffs(37, 17).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 12.0F),
                PartPose.offset(-4.5F, 10.0F, -2.0F));
        PartDefinition p_mechanism = root.addOrReplaceChild("mechanism",
                CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(-4.5F, 11.0F, 10.0F));
        PartDefinition p_grip = root.addOrReplaceChild("grip",
                CubeListBuilder.create().texOffs(0, 19).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(-4.5F, 13.8F, 1.3F, 0.7570103F, 0.0F, 0.0F));
        PartDefinition p_grip2 = root.addOrReplaceChild("grip2",
                CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offset(-4.966667F, 10.53333F, 0.0F));
        PartDefinition p_handle = root.addOrReplaceChild("handle",
                CubeListBuilder.create().texOffs(26, 9).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(-4.5F, 13.0F, -3.9F, 0.9462628F, 0.0F, 0.0F));
        PartDefinition p_gunFrame = root.addOrReplaceChild("gunFrame",
                CubeListBuilder.create().texOffs(18, 15).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 4.0F),
                PartPose.offset(-5.0F, 10.0F, -3.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
