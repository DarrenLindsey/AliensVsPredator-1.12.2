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
 * Geometry converted automatically from the 1.12.2 ModelRackModule1 by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class RackModule1Model implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "rack_module1"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart light2;
    public final ModelPart light7;
    public final ModelPart base;
    public final ModelPart light4;
    public final ModelPart light1;
    public final ModelPart lTape;
    public final ModelPart rTape;
    public final ModelPart rCanisterF;
    public final ModelPart tRTape2;
    public final ModelPart tRTape;
    public final ModelPart rCanisterB;
    public final ModelPart bRTape;
    public final ModelPart rRTape2;
    public final ModelPart rRTape2_1;
    public final ModelPart bRTape2;
    public final ModelPart lRTape2;
    public final ModelPart lRTape2_1;
    public final ModelPart tapeL;
    public final ModelPart lLTape;
    public final ModelPart tLTape;
    public final ModelPart tLTape2;
    public final ModelPart lCanisterB;
    public final ModelPart rLTape2;
    public final ModelPart bLTape2;
    public final ModelPart rLTape;
    public final ModelPart lLTape2;
    public final ModelPart lCanisterF;
    public final ModelPart bLTape;
    public final ModelPart light3;
    public final ModelPart rTape_1;
    public final ModelPart light5;
    public final ModelPart roller;
    public final ModelPart light6;
    public final ModelPart light8;

    public RackModule1Model(ModelPart root)
    {
        this.root = root;
        this.light2 = root.getChild("light2");
        this.parts.put("light2", this.light2);
        this.light7 = root.getChild("light7");
        this.parts.put("light7", this.light7);
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.light4 = root.getChild("light4");
        this.parts.put("light4", this.light4);
        this.light1 = root.getChild("light1");
        this.parts.put("light1", this.light1);
        this.lTape = root.getChild("lTape");
        this.parts.put("lTape", this.lTape);
        this.rTape = root.getChild("rTape");
        this.parts.put("rTape", this.rTape);
        this.rCanisterF = this.rTape.getChild("rCanisterF");
        this.parts.put("rCanisterF", this.rCanisterF);
        this.tRTape2 = this.rTape.getChild("tRTape2");
        this.parts.put("tRTape2", this.tRTape2);
        this.tRTape = this.rTape.getChild("tRTape");
        this.parts.put("tRTape", this.tRTape);
        this.rCanisterB = this.rTape.getChild("rCanisterB");
        this.parts.put("rCanisterB", this.rCanisterB);
        this.bRTape = this.rTape.getChild("bRTape");
        this.parts.put("bRTape", this.bRTape);
        this.rRTape2 = this.rTape.getChild("rRTape2");
        this.parts.put("rRTape2", this.rRTape2);
        this.rRTape2_1 = this.rTape.getChild("rRTape2_1");
        this.parts.put("rRTape2_1", this.rRTape2_1);
        this.bRTape2 = this.rTape.getChild("bRTape2");
        this.parts.put("bRTape2", this.bRTape2);
        this.lRTape2 = this.rTape.getChild("lRTape2");
        this.parts.put("lRTape2", this.lRTape2);
        this.lRTape2_1 = this.rTape.getChild("lRTape2_1");
        this.parts.put("lRTape2_1", this.lRTape2_1);
        this.tapeL = root.getChild("tapeL");
        this.parts.put("tapeL", this.tapeL);
        this.lLTape = this.tapeL.getChild("lLTape");
        this.parts.put("lLTape", this.lLTape);
        this.tLTape = this.tapeL.getChild("tLTape");
        this.parts.put("tLTape", this.tLTape);
        this.tLTape2 = this.tapeL.getChild("tLTape2");
        this.parts.put("tLTape2", this.tLTape2);
        this.lCanisterB = this.tapeL.getChild("lCanisterB");
        this.parts.put("lCanisterB", this.lCanisterB);
        this.rLTape2 = this.tapeL.getChild("rLTape2");
        this.parts.put("rLTape2", this.rLTape2);
        this.bLTape2 = this.tapeL.getChild("bLTape2");
        this.parts.put("bLTape2", this.bLTape2);
        this.rLTape = this.tapeL.getChild("rLTape");
        this.parts.put("rLTape", this.rLTape);
        this.lLTape2 = this.tapeL.getChild("lLTape2");
        this.parts.put("lLTape2", this.lLTape2);
        this.lCanisterF = this.tapeL.getChild("lCanisterF");
        this.parts.put("lCanisterF", this.lCanisterF);
        this.bLTape = this.tapeL.getChild("bLTape");
        this.parts.put("bLTape", this.bLTape);
        this.light3 = root.getChild("light3");
        this.parts.put("light3", this.light3);
        this.rTape_1 = root.getChild("rTape_1");
        this.parts.put("rTape_1", this.rTape_1);
        this.light5 = root.getChild("light5");
        this.parts.put("light5", this.light5);
        this.roller = root.getChild("roller");
        this.parts.put("roller", this.roller);
        this.light6 = root.getChild("light6");
        this.parts.put("light6", this.light6);
        this.light8 = root.getChild("light8");
        this.parts.put("light8", this.light8);
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
        PartDefinition p_light2 = root.addOrReplaceChild("light2",
                CubeListBuilder.create().texOffs(0, 15).addBox(0.5F, 1.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light7 = root.addOrReplaceChild("light7",
                CubeListBuilder.create().texOffs(0, 15).addBox(-1.5F, 5.5F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_base = root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(0, 18).addBox(-12.5F, 0.0F, 0.0F, 25.0F, 12.0F, 10.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light4 = root.addOrReplaceChild("light4",
                CubeListBuilder.create().texOffs(0, 15).addBox(0.5F, 2.5F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light1 = root.addOrReplaceChild("light1",
                CubeListBuilder.create().texOffs(0, 15).addBox(-1.5F, 1.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lTape = root.addOrReplaceChild("lTape",
                CubeListBuilder.create().texOffs(13, 0).addBox(-5.2F, 10.3F, -1.2F, 6.0F, 0.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.22759093446006054F));
        PartDefinition p_rTape = root.addOrReplaceChild("rTape",
                CubeListBuilder.create().texOffs(31, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 1.0F),
                PartPose.offset(7.0F, 5.5F, -1.2F));
        PartDefinition p_rCanisterF = p_rTape.addOrReplaceChild("rCanisterF",
                CubeListBuilder.create().texOffs(65, 0).addBox(-4.5F, -4.9F, -0.2F, 9.0F, 9.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tRTape2 = p_rTape.addOrReplaceChild("tRTape2",
                CubeListBuilder.create().texOffs(47, 10).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tRTape = p_rTape.addOrReplaceChild("tRTape",
                CubeListBuilder.create().texOffs(47, 10).addBox(-2.5F, -3.5F, 0.0F, 5.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rCanisterB = p_rTape.addOrReplaceChild("rCanisterB",
                CubeListBuilder.create().texOffs(65, 0).addBox(-4.5F, -4.9F, 1.11F, 9.0F, 9.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_bRTape = p_rTape.addOrReplaceChild("bRTape",
                CubeListBuilder.create().texOffs(47, 13).addBox(-2.5F, 2.5F, 0.0F, 5.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rRTape2 = p_rTape.addOrReplaceChild("rRTape2",
                CubeListBuilder.create().texOffs(36, 8).addBox(2.6F, -2.5F, 0.0F, 1.0F, 5.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rRTape2_1 = p_rTape.addOrReplaceChild("rRTape2_1",
                CubeListBuilder.create().texOffs(36, 9).addBox(3.2F, -2.0F, 0.0F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_bRTape2 = p_rTape.addOrReplaceChild("bRTape2",
                CubeListBuilder.create().texOffs(47, 13).addBox(-2.0F, 3.0F, 0.0F, 4.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lRTape2 = p_rTape.addOrReplaceChild("lRTape2",
                CubeListBuilder.create().texOffs(31, 8).addBox(-3.6F, -2.5F, 0.0F, 1.0F, 5.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lRTape2_1 = p_rTape.addOrReplaceChild("lRTape2_1",
                CubeListBuilder.create().texOffs(31, 9).addBox(-4.2F, -2.0F, 0.0F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tapeL = root.addOrReplaceChild("tapeL",
                CubeListBuilder.create().texOffs(31, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 1.0F),
                PartPose.offset(-7.0F, 5.5F, -1.2F));
        PartDefinition p_lLTape = p_tapeL.addOrReplaceChild("lLTape",
                CubeListBuilder.create().texOffs(31, 8).addBox(-3.6F, -2.5F, 0.0F, 1.0F, 5.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tLTape = p_tapeL.addOrReplaceChild("tLTape",
                CubeListBuilder.create().texOffs(47, 10).addBox(-2.5F, -3.5F, 0.0F, 5.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tLTape2 = p_tapeL.addOrReplaceChild("tLTape2",
                CubeListBuilder.create().texOffs(47, 10).addBox(-2.0F, -4.0F, 0.0F, 4.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lCanisterB = p_tapeL.addOrReplaceChild("lCanisterB",
                CubeListBuilder.create().texOffs(46, 0).addBox(-4.5F, -4.9F, 1.11F, 9.0F, 9.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rLTape2 = p_tapeL.addOrReplaceChild("rLTape2",
                CubeListBuilder.create().texOffs(36, 9).addBox(3.2F, -2.0F, 0.0F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_bLTape2 = p_tapeL.addOrReplaceChild("bLTape2",
                CubeListBuilder.create().texOffs(47, 13).addBox(-2.0F, 3.0F, 0.0F, 4.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rLTape = p_tapeL.addOrReplaceChild("rLTape",
                CubeListBuilder.create().texOffs(36, 8).addBox(2.6F, -2.5F, 0.0F, 1.0F, 5.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lLTape2 = p_tapeL.addOrReplaceChild("lLTape2",
                CubeListBuilder.create().texOffs(31, 9).addBox(-4.2F, -2.0F, 0.0F, 1.0F, 4.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lCanisterF = p_tapeL.addOrReplaceChild("lCanisterF",
                CubeListBuilder.create().texOffs(46, 0).addBox(-4.5F, -4.9F, -0.2F, 9.0F, 9.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_bLTape = p_tapeL.addOrReplaceChild("bLTape",
                CubeListBuilder.create().texOffs(47, 13).addBox(-2.5F, 2.5F, 0.0F, 5.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light3 = root.addOrReplaceChild("light3",
                CubeListBuilder.create().texOffs(0, 15).addBox(-1.5F, 2.5F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rTape_1 = root.addOrReplaceChild("rTape_1",
                CubeListBuilder.create().texOffs(13, 0).addBox(-1.0F, 10.3F, -1.2F, 6.0F, 0.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.22759093446006054F));
        PartDefinition p_light5 = root.addOrReplaceChild("light5",
                CubeListBuilder.create().texOffs(0, 15).addBox(-1.5F, 4.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_roller = root.addOrReplaceChild("roller",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, 7.6F, -1.2F, 3.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light6 = root.addOrReplaceChild("light6",
                CubeListBuilder.create().texOffs(0, 15).addBox(0.5F, 4.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_light8 = root.addOrReplaceChild("light8",
                CubeListBuilder.create().texOffs(0, 15).addBox(0.5F, 5.5F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
