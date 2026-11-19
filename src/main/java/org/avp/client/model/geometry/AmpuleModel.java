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
 * Geometry converted automatically from the 1.12.2 ModelAmpule by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class AmpuleModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "ampule"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart phialStem3;
    public final ModelPart phialTip4;
    public final ModelPart phialStem2;
    public final ModelPart phialTop3;
    public final ModelPart phialTip2;
    public final ModelPart canisterBase2;
    public final ModelPart phailBase3;
    public final ModelPart phialStem4;
    public final ModelPart phialTip1;
    public final ModelPart canisterLid2;
    public final ModelPart phailBase4;
    public final ModelPart phailBase;
    public final ModelPart canisterLid1;
    public final ModelPart canisterMiddle1;
    public final ModelPart phialTop4;
    public final ModelPart phialStem1;
    public final ModelPart canisterBase1;
    public final ModelPart phialTip3;
    public final ModelPart phialTop2;
    public final ModelPart phailBase2;
    public final ModelPart phialTop1;

    public AmpuleModel(ModelPart root)
    {
        this.root = root;
        this.phialStem3 = root.getChild("phialStem3");
        this.parts.put("phialStem3", this.phialStem3);
        this.phialTip4 = root.getChild("phialTip4");
        this.parts.put("phialTip4", this.phialTip4);
        this.phialStem2 = root.getChild("phialStem2");
        this.parts.put("phialStem2", this.phialStem2);
        this.phialTop3 = root.getChild("phialTop3");
        this.parts.put("phialTop3", this.phialTop3);
        this.phialTip2 = root.getChild("phialTip2");
        this.parts.put("phialTip2", this.phialTip2);
        this.canisterBase2 = root.getChild("canisterBase2");
        this.parts.put("canisterBase2", this.canisterBase2);
        this.phailBase3 = root.getChild("phailBase3");
        this.parts.put("phailBase3", this.phailBase3);
        this.phialStem4 = root.getChild("phialStem4");
        this.parts.put("phialStem4", this.phialStem4);
        this.phialTip1 = root.getChild("phialTip1");
        this.parts.put("phialTip1", this.phialTip1);
        this.canisterLid2 = root.getChild("canisterLid2");
        this.parts.put("canisterLid2", this.canisterLid2);
        this.phailBase4 = root.getChild("phailBase4");
        this.parts.put("phailBase4", this.phailBase4);
        this.phailBase = root.getChild("phailBase");
        this.parts.put("phailBase", this.phailBase);
        this.canisterLid1 = root.getChild("canisterLid1");
        this.parts.put("canisterLid1", this.canisterLid1);
        this.canisterMiddle1 = root.getChild("canisterMiddle1");
        this.parts.put("canisterMiddle1", this.canisterMiddle1);
        this.phialTop4 = root.getChild("phialTop4");
        this.parts.put("phialTop4", this.phialTop4);
        this.phialStem1 = root.getChild("phialStem1");
        this.parts.put("phialStem1", this.phialStem1);
        this.canisterBase1 = root.getChild("canisterBase1");
        this.parts.put("canisterBase1", this.canisterBase1);
        this.phialTip3 = root.getChild("phialTip3");
        this.parts.put("phialTip3", this.phialTip3);
        this.phialTop2 = root.getChild("phialTop2");
        this.parts.put("phialTop2", this.phialTop2);
        this.phailBase2 = root.getChild("phailBase2");
        this.parts.put("phailBase2", this.phailBase2);
        this.phialTop1 = root.getChild("phialTop1");
        this.parts.put("phialTop1", this.phialTop1);
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
        PartDefinition p_phialStem3 = root.addOrReplaceChild("phialStem3",
                CubeListBuilder.create().texOffs(32, 45).addBox(-2.3F, 10.1F, -2.3F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phialTip4 = root.addOrReplaceChild("phialTip4",
                CubeListBuilder.create().texOffs(46, 33).addBox(1.5F, 4.7F, -2.3F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phialStem2 = root.addOrReplaceChild("phialStem2",
                CubeListBuilder.create().texOffs(18, 45).addBox(1.7F, 10.1F, 1.8F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phialTop3 = root.addOrReplaceChild("phialTop3",
                CubeListBuilder.create().texOffs(30, 39).addBox(-2.8F, 7.7F, -2.8F, 2.0F, 3.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phialTip2 = root.addOrReplaceChild("phialTip2",
                CubeListBuilder.create().texOffs(18, 33).addBox(1.7F, 4.7F, 1.8F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_canisterBase2 = root.addOrReplaceChild("canisterBase2",
                CubeListBuilder.create().texOffs(81, 0).addBox(-4.5F, 13.0F, -4.5F, 9.0F, 8.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phailBase3 = root.addOrReplaceChild("phailBase3",
                CubeListBuilder.create().texOffs(29, 50).addBox(-3.3F, 11.7F, -3.3F, 3.0F, 8.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phialStem4 = root.addOrReplaceChild("phialStem4",
                CubeListBuilder.create().texOffs(46, 45).addBox(1.5F, 10.1F, -2.3F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phialTip1 = root.addOrReplaceChild("phialTip1",
                CubeListBuilder.create().texOffs(5, 33).addBox(-2.3F, 4.7F, 1.8F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_canisterLid2 = root.addOrReplaceChild("canisterLid2",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -1.0F, -4.5F, 9.0F, 5.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phailBase4 = root.addOrReplaceChild("phailBase4",
                CubeListBuilder.create().texOffs(43, 50).addBox(0.5F, 11.7F, -3.3F, 3.0F, 8.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phailBase = root.addOrReplaceChild("phailBase",
                CubeListBuilder.create().texOffs(1, 50).addBox(-3.3F, 11.7F, 0.8F, 3.0F, 8.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_canisterLid1 = root.addOrReplaceChild("canisterLid1",
                CubeListBuilder.create().texOffs(37, 0).addBox(-5.0F, 4.0F, -5.0F, 10.0F, 5.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_canisterMiddle1 = root.addOrReplaceChild("canisterMiddle1",
                CubeListBuilder.create().texOffs(37, 16).addBox(-5.0F, 9.0F, -5.0F, 10.0F, 4.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phialTop4 = root.addOrReplaceChild("phialTop4",
                CubeListBuilder.create().texOffs(44, 39).addBox(1.0F, 7.7F, -2.8F, 2.0F, 3.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phialStem1 = root.addOrReplaceChild("phialStem1",
                CubeListBuilder.create().texOffs(5, 45).addBox(-2.3F, 10.1F, 1.8F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_canisterBase1 = root.addOrReplaceChild("canisterBase1",
                CubeListBuilder.create().texOffs(81, 22).addBox(-4.0F, 18.0F, -4.0F, 8.0F, 6.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phialTip3 = root.addOrReplaceChild("phialTip3",
                CubeListBuilder.create().texOffs(32, 33).addBox(-2.3F, 4.7F, -2.3F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phialTop2 = root.addOrReplaceChild("phialTop2",
                CubeListBuilder.create().texOffs(16, 39).addBox(1.2F, 7.7F, 1.3F, 2.0F, 3.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phailBase2 = root.addOrReplaceChild("phailBase2",
                CubeListBuilder.create().texOffs(15, 50).addBox(0.7F, 11.7F, 0.8F, 3.0F, 8.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_phialTop1 = root.addOrReplaceChild("phialTop1",
                CubeListBuilder.create().texOffs(3, 39).addBox(-2.8F, 7.7F, 1.3F, 2.0F, 3.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
