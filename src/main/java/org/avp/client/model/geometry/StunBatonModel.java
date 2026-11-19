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
 * Geometry converted automatically from the 1.12.2 ModelStunBaton by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class StunBatonModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "stun_baton"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart grip;
    public final ModelPart rodCone02;
    public final ModelPart rodCone01;
    public final ModelPart lateralRod020;
    public final ModelPart lateralRod02A;
    public final ModelPart lateralRod02B;
    public final ModelPart lateralRod02C;
    public final ModelPart centralRod;
    public final ModelPart lateralRod010;
    public final ModelPart lateralRod01A;
    public final ModelPart lateralRod01B;
    public final ModelPart lateralRod01C;
    public final ModelPart handGuard;
    public final ModelPart handGuardLower;
    public final ModelPart handGuardU;
    public final ModelPart handGuardLowerU;
    public final ModelPart coilBase;
    public final ModelPart rodTip;
    public final ModelPart coil06;
    public final ModelPart coil04;
    public final ModelPart coil02;
    public final ModelPart coil01;
    public final ModelPart coil03;
    public final ModelPart coil05;
    public final ModelPart coil07;
    public final ModelPart coil08;
    public final ModelPart rodCone03;

    public StunBatonModel(ModelPart root)
    {
        this.root = root;
        this.grip = root.getChild("grip");
        this.parts.put("grip", this.grip);
        this.rodCone02 = root.getChild("rodCone02");
        this.parts.put("rodCone02", this.rodCone02);
        this.rodCone01 = root.getChild("rodCone01");
        this.parts.put("rodCone01", this.rodCone01);
        this.lateralRod020 = root.getChild("lateralRod020");
        this.parts.put("lateralRod020", this.lateralRod020);
        this.lateralRod02A = this.lateralRod020.getChild("lateralRod02A");
        this.parts.put("lateralRod02A", this.lateralRod02A);
        this.lateralRod02B = this.lateralRod02A.getChild("lateralRod02B");
        this.parts.put("lateralRod02B", this.lateralRod02B);
        this.lateralRod02C = this.lateralRod02B.getChild("lateralRod02C");
        this.parts.put("lateralRod02C", this.lateralRod02C);
        this.centralRod = root.getChild("centralRod");
        this.parts.put("centralRod", this.centralRod);
        this.lateralRod010 = root.getChild("lateralRod010");
        this.parts.put("lateralRod010", this.lateralRod010);
        this.lateralRod01A = this.lateralRod010.getChild("lateralRod01A");
        this.parts.put("lateralRod01A", this.lateralRod01A);
        this.lateralRod01B = this.lateralRod01A.getChild("lateralRod01B");
        this.parts.put("lateralRod01B", this.lateralRod01B);
        this.lateralRod01C = this.lateralRod01B.getChild("lateralRod01C");
        this.parts.put("lateralRod01C", this.lateralRod01C);
        this.handGuard = root.getChild("handGuard");
        this.parts.put("handGuard", this.handGuard);
        this.handGuardLower = this.handGuard.getChild("handGuardLower");
        this.parts.put("handGuardLower", this.handGuardLower);
        this.handGuardU = root.getChild("handGuardU");
        this.parts.put("handGuardU", this.handGuardU);
        this.handGuardLowerU = this.handGuardU.getChild("handGuardLowerU");
        this.parts.put("handGuardLowerU", this.handGuardLowerU);
        this.coilBase = root.getChild("coilBase");
        this.parts.put("coilBase", this.coilBase);
        this.rodTip = root.getChild("rodTip");
        this.parts.put("rodTip", this.rodTip);
        this.coil06 = this.rodTip.getChild("coil06");
        this.parts.put("coil06", this.coil06);
        this.coil04 = this.rodTip.getChild("coil04");
        this.parts.put("coil04", this.coil04);
        this.coil02 = this.rodTip.getChild("coil02");
        this.parts.put("coil02", this.coil02);
        this.coil01 = this.rodTip.getChild("coil01");
        this.parts.put("coil01", this.coil01);
        this.coil03 = this.rodTip.getChild("coil03");
        this.parts.put("coil03", this.coil03);
        this.coil05 = this.rodTip.getChild("coil05");
        this.parts.put("coil05", this.coil05);
        this.coil07 = this.rodTip.getChild("coil07");
        this.parts.put("coil07", this.coil07);
        this.coil08 = this.rodTip.getChild("coil08");
        this.parts.put("coil08", this.coil08);
        this.rodCone03 = root.getChild("rodCone03");
        this.parts.put("rodCone03", this.rodCone03);
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
        PartDefinition p_grip = root.addOrReplaceChild("grip",
                CubeListBuilder.create().texOffs(7, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 5.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rodCone02 = root.addOrReplaceChild("rodCone02",
                CubeListBuilder.create().texOffs(28, 12).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, -10.85F, 0.0F));
        PartDefinition p_rodCone01 = root.addOrReplaceChild("rodCone01",
                CubeListBuilder.create().texOffs(28, 9).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, -10.65F, 0.0F));
        PartDefinition p_lateralRod020 = root.addOrReplaceChild("lateralRod020",
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -7.9F, -0.1F, 0.0F, -1.5707963267948966F, 0.0F));
        PartDefinition p_lateralRod02A = p_lateralRod020.addOrReplaceChild("lateralRod02A",
                CubeListBuilder.create().texOffs(15, 0).addBox(-1.9F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-0.1F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5462880558742251F));
        PartDefinition p_lateralRod02B = p_lateralRod02A.addOrReplaceChild("lateralRod02B",
                CubeListBuilder.create().texOffs(16, 3).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 12.0F, 1.0F),
                PartPose.offsetAndRotation(-1.8F, 0.2F, 0.0F, 0.0F, 0.0F, 0.5462880558742251F));
        PartDefinition p_lateralRod02C = p_lateralRod02B.addOrReplaceChild("lateralRod02C",
                CubeListBuilder.create().texOffs(15, 0).addBox(0.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-0.2F, 11.1F, 0.0F, 0.0F, 0.0F, 0.5462880558742251F));
        PartDefinition p_centralRod = root.addOrReplaceChild("centralRod",
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -9.0F, -0.5F, 1.0F, 9.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lateralRod010 = root.addOrReplaceChild("lateralRod010",
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F),
                PartPose.offset(-0.1F, -6.9F, 0.0F));
        PartDefinition p_lateralRod01A = p_lateralRod010.addOrReplaceChild("lateralRod01A",
                CubeListBuilder.create().texOffs(15, 0).addBox(-1.9F, -0.5F, -0.49F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-0.1F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5462880558742251F));
        PartDefinition p_lateralRod01B = p_lateralRod01A.addOrReplaceChild("lateralRod01B",
                CubeListBuilder.create().texOffs(22, 3).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 12.0F, 1.0F),
                PartPose.offsetAndRotation(-1.8F, 0.2F, 0.0F, 0.0F, 0.0F, 0.5462880558742251F));
        PartDefinition p_lateralRod01C = p_lateralRod01B.addOrReplaceChild("lateralRod01C",
                CubeListBuilder.create().texOffs(15, 0).addBox(0.0F, -0.5F, -0.49F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-0.2F, 11.1F, 0.0F, 0.0F, 0.0F, 0.5462880558742251F));
        PartDefinition p_handGuard = root.addOrReplaceChild("handGuard",
                CubeListBuilder.create().texOffs(7, 7).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_handGuardLower = p_handGuard.addOrReplaceChild("handGuardLower",
                CubeListBuilder.create().texOffs(7, 7).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 10.5F, 0.0F));
        PartDefinition p_handGuardU = root.addOrReplaceChild("handGuardU",
                CubeListBuilder.create().texOffs(7, 10).addBox(-0.5F, -0.5F, -0.6F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, -0.4F));
        PartDefinition p_handGuardLowerU = p_handGuardU.addOrReplaceChild("handGuardLowerU",
                CubeListBuilder.create().texOffs(7, 13).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 13.1F, 0.1F));
        PartDefinition p_coilBase = root.addOrReplaceChild("coilBase",
                CubeListBuilder.create().texOffs(28, 9).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, -9.65F, 0.0F));
        PartDefinition p_rodTip = root.addOrReplaceChild("rodTip",
                CubeListBuilder.create().texOffs(28, 0).addBox(-0.5F, -2.6F, -0.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offset(0.0F, -9.1F, 0.0F));
        PartDefinition p_coil06 = p_rodTip.addOrReplaceChild("coil06",
                CubeListBuilder.create().texOffs(25, 5).addBox(-1.5F, 0.0F, -1.4F, 3.0F, 0.0F, 3.0F),
                PartPose.offset(0.0F, -0.9F, 0.0F));
        PartDefinition p_coil04 = p_rodTip.addOrReplaceChild("coil04",
                CubeListBuilder.create().texOffs(25, 5).addBox(-1.5F, 0.0F, -1.4F, 3.0F, 0.0F, 3.0F),
                PartPose.offset(0.0F, -1.1F, 0.0F));
        PartDefinition p_coil02 = p_rodTip.addOrReplaceChild("coil02",
                CubeListBuilder.create().texOffs(25, 5).addBox(-1.5F, 0.0F, -1.4F, 3.0F, 0.0F, 3.0F),
                PartPose.offset(0.0F, -1.3F, 0.0F));
        PartDefinition p_coil01 = p_rodTip.addOrReplaceChild("coil01",
                CubeListBuilder.create().texOffs(25, 5).addBox(-1.5F, 0.0F, -1.4F, 3.0F, 0.0F, 3.0F),
                PartPose.offset(0.0F, -1.4F, 0.0F));
        PartDefinition p_coil03 = p_rodTip.addOrReplaceChild("coil03",
                CubeListBuilder.create().texOffs(25, 5).addBox(-1.5F, 0.0F, -1.4F, 3.0F, 0.0F, 3.0F),
                PartPose.offset(0.0F, -1.2F, 0.0F));
        PartDefinition p_coil05 = p_rodTip.addOrReplaceChild("coil05",
                CubeListBuilder.create().texOffs(25, 5).addBox(-1.5F, 0.0F, -1.4F, 3.0F, 0.0F, 3.0F),
                PartPose.offset(0.0F, -1.0F, 0.0F));
        PartDefinition p_coil07 = p_rodTip.addOrReplaceChild("coil07",
                CubeListBuilder.create().texOffs(25, 5).addBox(-1.5F, 0.0F, -1.4F, 3.0F, 0.0F, 3.0F),
                PartPose.offset(0.0F, -0.8F, 0.0F));
        PartDefinition p_coil08 = p_rodTip.addOrReplaceChild("coil08",
                CubeListBuilder.create().texOffs(25, 5).addBox(-1.5F, 0.0F, -1.4F, 3.0F, 0.0F, 3.0F),
                PartPose.offset(0.0F, -0.7F, 0.0F));
        PartDefinition p_rodCone03 = root.addOrReplaceChild("rodCone03",
                CubeListBuilder.create().texOffs(28, 15).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, -11.05F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
