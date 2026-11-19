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
 * Geometry converted automatically from the 1.12.2 ModelM56SG by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class M56SGModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "m56_s_g"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart handleBase;
    public final ModelPart base;
    public final ModelPart ammoHolder;
    public final ModelPart handleWire;
    public final ModelPart autoTargetEye;
    public final ModelPart baseUnder;
    public final ModelPart topHandle;
    public final ModelPart topHandleBase;
    public final ModelPart barrelHolderBase;
    public final ModelPart barrelSupportLeft;
    public final ModelPart barrelSupportRight;
    public final ModelPart autoTargetControl;
    public final ModelPart barrel;
    public final ModelPart handle;
    public final ModelPart base2;
    public final ModelPart base3;
    public final ModelPart baseUnder2;
    public final ModelPart handleTop;
    public final ModelPart topHandleBase2;

    public M56SGModel(ModelPart root)
    {
        this.root = root;
        this.handleBase = root.getChild("handleBase");
        this.parts.put("handleBase", this.handleBase);
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.ammoHolder = root.getChild("ammoHolder");
        this.parts.put("ammoHolder", this.ammoHolder);
        this.handleWire = root.getChild("handleWire");
        this.parts.put("handleWire", this.handleWire);
        this.autoTargetEye = root.getChild("autoTargetEye");
        this.parts.put("autoTargetEye", this.autoTargetEye);
        this.baseUnder = root.getChild("baseUnder");
        this.parts.put("baseUnder", this.baseUnder);
        this.topHandle = root.getChild("topHandle");
        this.parts.put("topHandle", this.topHandle);
        this.topHandleBase = root.getChild("topHandleBase");
        this.parts.put("topHandleBase", this.topHandleBase);
        this.barrelHolderBase = root.getChild("barrelHolderBase");
        this.parts.put("barrelHolderBase", this.barrelHolderBase);
        this.barrelSupportLeft = root.getChild("barrelSupportLeft");
        this.parts.put("barrelSupportLeft", this.barrelSupportLeft);
        this.barrelSupportRight = root.getChild("barrelSupportRight");
        this.parts.put("barrelSupportRight", this.barrelSupportRight);
        this.autoTargetControl = root.getChild("autoTargetControl");
        this.parts.put("autoTargetControl", this.autoTargetControl);
        this.barrel = root.getChild("barrel");
        this.parts.put("barrel", this.barrel);
        this.handle = root.getChild("handle");
        this.parts.put("handle", this.handle);
        this.base2 = root.getChild("base2");
        this.parts.put("base2", this.base2);
        this.base3 = root.getChild("base3");
        this.parts.put("base3", this.base3);
        this.baseUnder2 = root.getChild("baseUnder2");
        this.parts.put("baseUnder2", this.baseUnder2);
        this.handleTop = root.getChild("handleTop");
        this.parts.put("handleTop", this.handleTop);
        this.topHandleBase2 = root.getChild("topHandleBase2");
        this.parts.put("topHandleBase2", this.topHandleBase2);
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
        PartDefinition p_handleBase = root.addOrReplaceChild("handleBase",
                CubeListBuilder.create().texOffs(30, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 1.0F),
                PartPose.offset(0.5F, 0.1F, -2.0F));
        PartDefinition p_base = root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(37, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 2.0F),
                PartPose.offset(1.5F, 0.2F, 1.0F));
        PartDefinition p_ammoHolder = root.addOrReplaceChild("ammoHolder",
                CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 2.9F));
        PartDefinition p_handleWire = root.addOrReplaceChild("handleWire",
                CubeListBuilder.create().texOffs(44, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 4.0F),
                PartPose.offset(0.5F, 1.7F, -1.0F));
        PartDefinition p_autoTargetEye = root.addOrReplaceChild("autoTargetEye",
                CubeListBuilder.create().texOffs(19, 5).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(1.0F, -0.1F, 10.5F));
        PartDefinition p_baseUnder = root.addOrReplaceChild("baseUnder",
                CubeListBuilder.create().texOffs(19, 0).addBox(0.0F, -1.0F, -0.6F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.5F, 2.0F, 8.5F, 0.3141592653589793F, 0.0F, 0.0F));
        PartDefinition p_topHandle = root.addOrReplaceChild("topHandle",
                CubeListBuilder.create().texOffs(28, 5).addBox(0.0F, 0.0F, 0.0F, 5.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-2.5F, -0.9F, 9.0F, -0.418879F, 0.0F, 0.0F));
        PartDefinition p_topHandleBase = root.addOrReplaceChild("topHandleBase",
                CubeListBuilder.create().texOffs(7, 24).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 2.0F),
                PartPose.offset(0.4F, -0.5F, 9.5F));
        PartDefinition p_barrelHolderBase = root.addOrReplaceChild("barrelHolderBase",
                CubeListBuilder.create().texOffs(28, 8).addBox(0.0F, 1.0F, 5.0F, 2.0F, 1.0F, 1.0F),
                PartPose.offset(0.5F, -0.5F, 8.9F));
        PartDefinition p_barrelSupportLeft = root.addOrReplaceChild("barrelSupportLeft",
                CubeListBuilder.create().texOffs(19, 10).addBox(0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(0.7F, -0.4F, 11.03333F, -0.2094395F, 0.0F, 0.0F));
        PartDefinition p_barrelSupportRight = root.addOrReplaceChild("barrelSupportRight",
                CubeListBuilder.create().texOffs(19, 16).addBox(0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(2.3F, -0.4F, 11.0F, -0.2094395F, 0.0F, 0.0F));
        PartDefinition p_autoTargetControl = root.addOrReplaceChild("autoTargetControl",
                CubeListBuilder.create().texOffs(11, 7).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(1.0F, -0.9F, 9.0F));
        PartDefinition p_barrel = root.addOrReplaceChild("barrel",
                CubeListBuilder.create().texOffs(28, 11).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 9.0F),
                PartPose.offset(1.0F, 0.4F, 10.0F));
        PartDefinition p_handle = root.addOrReplaceChild("handle",
                CubeListBuilder.create().texOffs(19, 23).addBox(-0.5F, 0.0F, 1.0F, 0.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(2.0F, 2.2F, -6.8F, 0.2792527F, 0.0F, 0.0F));
        PartDefinition p_base2 = root.addOrReplaceChild("base2",
                CubeListBuilder.create().texOffs(0, 15).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 7.0F),
                PartPose.offset(1.5F, 0.2F, 3.0F));
        PartDefinition p_base3 = root.addOrReplaceChild("base3",
                CubeListBuilder.create().texOffs(11, 11).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(1.5F, 0.2F, -1.0F));
        PartDefinition p_baseUnder2 = root.addOrReplaceChild("baseUnder2",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 5.0F),
                PartPose.offset(0.5F, 1.2F, 2.9F));
        PartDefinition p_handleTop = root.addOrReplaceChild("handleTop",
                CubeListBuilder.create().texOffs(41, 5).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(1.5F, 0.2F, -6.0F));
        PartDefinition p_topHandleBase2 = root.addOrReplaceChild("topHandleBase2",
                CubeListBuilder.create().texOffs(0, 24).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 2.0F),
                PartPose.offset(1.6F, -0.5F, 9.5F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
