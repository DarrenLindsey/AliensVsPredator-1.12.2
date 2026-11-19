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
 * Geometry converted automatically from the 1.12.2 ModelWorkstation by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class WorkstationModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "workstation"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart stand;
    public final ModelPart standBase;
    public final ModelPart desk;
    public final ModelPart mainArm;
    public final ModelPart leftArm;
    public final ModelPart supportLeft;
    public final ModelPart supportCenter;
    public final ModelPart supportRight;
    public final ModelPart rightArm;
    public final ModelPart screenLeft;
    public final ModelPart screenCenter;
    public final ModelPart screenRight;

    public WorkstationModel(ModelPart root)
    {
        this.root = root;
        this.stand = root.getChild("stand");
        this.parts.put("stand", this.stand);
        this.standBase = root.getChild("standBase");
        this.parts.put("standBase", this.standBase);
        this.desk = root.getChild("desk");
        this.parts.put("desk", this.desk);
        this.mainArm = root.getChild("mainArm");
        this.parts.put("mainArm", this.mainArm);
        this.leftArm = root.getChild("leftArm");
        this.parts.put("leftArm", this.leftArm);
        this.supportLeft = root.getChild("supportLeft");
        this.parts.put("supportLeft", this.supportLeft);
        this.supportCenter = root.getChild("supportCenter");
        this.parts.put("supportCenter", this.supportCenter);
        this.supportRight = root.getChild("supportRight");
        this.parts.put("supportRight", this.supportRight);
        this.rightArm = root.getChild("rightArm");
        this.parts.put("rightArm", this.rightArm);
        this.screenLeft = root.getChild("screenLeft");
        this.parts.put("screenLeft", this.screenLeft);
        this.screenCenter = root.getChild("screenCenter");
        this.parts.put("screenCenter", this.screenCenter);
        this.screenRight = root.getChild("screenRight");
        this.parts.put("screenRight", this.screenRight);
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
        PartDefinition p_stand = root.addOrReplaceChild("stand",
                CubeListBuilder.create().texOffs(42, 21).addBox(0.0F, 0.0F, 0.0F, 10.0F, 18.0F, 4.0F),
                PartPose.offset(-5.0F, 4.0F, 2.0F));
        PartDefinition p_standBase = root.addOrReplaceChild("standBase",
                CubeListBuilder.create().texOffs(1, 17).addBox(0.0F, 0.0F, 0.0F, 16.0F, 2.0F, 4.0F),
                PartPose.offset(-8.0F, 22.0F, 2.0F));
        PartDefinition p_desk = root.addOrReplaceChild("desk",
                CubeListBuilder.create().texOffs(42, 1).addBox(0.0F, 0.0F, 0.0F, 16.0F, 2.0F, 16.0F),
                PartPose.offsetAndRotation(-8.0F, 6.0F, -10.6F, 0.1115358F, 0.0F, 0.0F));
        PartDefinition p_mainArm = root.addOrReplaceChild("mainArm",
                CubeListBuilder.create().texOffs(1, 7).addBox(0.0F, 0.0F, 0.0F, 4.0F, 8.0F, 1.0F),
                PartPose.offset(-2.0F, -4.0F, 5.0F));
        PartDefinition p_leftArm = root.addOrReplaceChild("leftArm",
                CubeListBuilder.create().texOffs(1, 1).addBox(-6.0F, 0.0F, 0.0F, 6.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(-8.0F, -4.0F, 6.0F, 3.141593F, -0.3346145F, 0.0F));
        PartDefinition p_supportLeft = root.addOrReplaceChild("supportLeft",
                CubeListBuilder.create().texOffs(1, 1).addBox(0.0F, 0.0F, 0.0F, 6.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(-8.0F, -4.0F, 6.0F, 3.141593F, 0.0F, 0.0F));
        PartDefinition p_supportCenter = root.addOrReplaceChild("supportCenter",
                CubeListBuilder.create().texOffs(16, 1).addBox(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(-2.0F, -4.0F, 6.0F, 3.141593F, 0.0F, 0.0F));
        PartDefinition p_supportRight = root.addOrReplaceChild("supportRight",
                CubeListBuilder.create().texOffs(1, 1).addBox(0.0F, 0.0F, 0.0F, 6.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(2.0F, -4.0F, 6.0F, 3.141593F, 0.0F, 0.0F));
        PartDefinition p_rightArm = root.addOrReplaceChild("rightArm",
                CubeListBuilder.create().texOffs(1, 1).addBox(0.0F, 0.0F, 0.0F, 6.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(8.0F, -4.0F, 6.0F, 3.141593F, 0.3346075F, 0.0F));
        PartDefinition p_screenLeft = root.addOrReplaceChild("screenLeft",
                CubeListBuilder.create().texOffs(1, 24).addBox(-16.0F, 0.0F, 0.0F, 16.0F, 9.0F, 1.0F),
                PartPose.offsetAndRotation(-8.0F, -10.0F, 4.0F, 0.1115358F, -0.3346075F, 0.0F));
        PartDefinition p_screenCenter = root.addOrReplaceChild("screenCenter",
                CubeListBuilder.create().texOffs(1, 24).addBox(0.0F, 0.0F, 0.0F, 16.0F, 9.0F, 1.0F),
                PartPose.offsetAndRotation(-8.0F, -10.0F, 4.0F, 0.1115358F, 0.0F, 0.0F));
        PartDefinition p_screenRight = root.addOrReplaceChild("screenRight",
                CubeListBuilder.create().texOffs(1, 24).addBox(0.0F, 0.0F, 0.0F, 16.0F, 9.0F, 1.0F),
                PartPose.offsetAndRotation(8.0F, -10.0F, 4.0F, 0.111544F, 0.3346145F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
