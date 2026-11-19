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
 * Geometry converted automatically from the 1.12.2 ModelSupplyChute by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class SupplyChuteModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "supply_chute"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart casing;
    public final ModelPart lHinge;
    public final ModelPart wire1;
    public final ModelPart rHinge;
    public final ModelPart lid;
    public final ModelPart chute;
    public final ModelPart wire4;
    public final ModelPart wire2;
    public final ModelPart wire3;
    public final ModelPart chuteTop;

    public SupplyChuteModel(ModelPart root)
    {
        this.root = root;
        this.casing = root.getChild("casing");
        this.parts.put("casing", this.casing);
        this.lHinge = root.getChild("lHinge");
        this.parts.put("lHinge", this.lHinge);
        this.wire1 = root.getChild("wire1");
        this.parts.put("wire1", this.wire1);
        this.rHinge = root.getChild("rHinge");
        this.parts.put("rHinge", this.rHinge);
        this.lid = root.getChild("lid");
        this.parts.put("lid", this.lid);
        this.chute = root.getChild("chute");
        this.parts.put("chute", this.chute);
        this.wire4 = root.getChild("wire4");
        this.parts.put("wire4", this.wire4);
        this.wire2 = root.getChild("wire2");
        this.parts.put("wire2", this.wire2);
        this.wire3 = root.getChild("wire3");
        this.parts.put("wire3", this.wire3);
        this.chuteTop = root.getChild("chuteTop");
        this.parts.put("chuteTop", this.chuteTop);
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
        PartDefinition p_casing = root.addOrReplaceChild("casing",
                CubeListBuilder.create().texOffs(74, 0).addBox(-8.0F, 14.0F, -5.0F, 16.0F, 10.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lHinge = root.addOrReplaceChild("lHinge",
                CubeListBuilder.create().texOffs(94, 44).addBox(3.0F, 12.5F, 4.5F, 4.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_wire1 = root.addOrReplaceChild("wire1",
                CubeListBuilder.create().texOffs(85, 37).addBox(11.0F, -5.0F, -0.5F, 0.0F, 17.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.08674675716837597F, 0.5524356441140822F, 0.16423009791780357F));
        PartDefinition p_rHinge = root.addOrReplaceChild("rHinge",
                CubeListBuilder.create().texOffs(94, 44).addBox(-7.0F, 12.5F, 4.5F, 4.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lid = root.addOrReplaceChild("lid",
                CubeListBuilder.create().texOffs(74, 22).addBox(-8.0F, 0.0F, -10.5F, 16.0F, 1.0F, 11.0F),
                PartPose.offset(0.0F, 13.0F, 5.5F));
        PartDefinition p_chute = root.addOrReplaceChild("chute",
                CubeListBuilder.create().texOffs(0, 34).addBox(-11.0F, -14.0F, -9.0F, 22.0F, 11.0F, 19.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_wire4 = root.addOrReplaceChild("wire4",
                CubeListBuilder.create().texOffs(85, 37).addBox(-11.0F, -5.0F, -0.5F, 0.0F, 17.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.08674675716837597F, -0.5524356441140822F, -0.16423009791780357F));
        PartDefinition p_wire2 = root.addOrReplaceChild("wire2",
                CubeListBuilder.create().texOffs(85, 37).addBox(11.5F, -5.0F, 0.30000001192092896F, 0.0F, 17.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.08674675716837597F, -0.5524356441140822F, 0.16423009791780357F));
        PartDefinition p_wire3 = root.addOrReplaceChild("wire3",
                CubeListBuilder.create().texOffs(85, 37).addBox(-11.5F, -5.0F, 0.30000001192092896F, 0.0F, 17.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.08674675716837597F, 0.5524356441140822F, -0.16423009791780357F));
        PartDefinition p_chuteTop = root.addOrReplaceChild("chuteTop",
                CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -20.0F, -7.0F, 18.0F, 6.0F, 15.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
