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
 * Geometry converted automatically from the 1.12.2 ModelM240ICU by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class M240ICUModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "m240_i_c_u"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart canister;
    public final ModelPart canisterSealTop;
    public final ModelPart canisterSealTopLeft;
    public final ModelPart canisterSealTopRight;
    public final ModelPart canisterSealBottom;
    public final ModelPart canisterSealBottomLeft;
    public final ModelPart canisterSealBottomRight;
    public final ModelPart base;
    public final ModelPart handleSupport;
    public final ModelPart handle;
    public final ModelPart nozzle;
    public final ModelPart ignitor;
    public final ModelPart barrel;
    public final ModelPart stock;
    public final ModelPart stockArch;
    public final ModelPart butt;
    public final ModelPart buttBar;
    public final ModelPart sight;
    public final ModelPart barrelArch;
    public final ModelPart underGrip;
    public final ModelPart underGripArchBack;
    public final ModelPart underGripArchFront;

    public M240ICUModel(ModelPart root)
    {
        this.root = root;
        this.canister = root.getChild("canister");
        this.parts.put("canister", this.canister);
        this.canisterSealTop = root.getChild("canisterSealTop");
        this.parts.put("canisterSealTop", this.canisterSealTop);
        this.canisterSealTopLeft = root.getChild("canisterSealTopLeft");
        this.parts.put("canisterSealTopLeft", this.canisterSealTopLeft);
        this.canisterSealTopRight = root.getChild("canisterSealTopRight");
        this.parts.put("canisterSealTopRight", this.canisterSealTopRight);
        this.canisterSealBottom = root.getChild("canisterSealBottom");
        this.parts.put("canisterSealBottom", this.canisterSealBottom);
        this.canisterSealBottomLeft = root.getChild("canisterSealBottomLeft");
        this.parts.put("canisterSealBottomLeft", this.canisterSealBottomLeft);
        this.canisterSealBottomRight = root.getChild("canisterSealBottomRight");
        this.parts.put("canisterSealBottomRight", this.canisterSealBottomRight);
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.handleSupport = root.getChild("handleSupport");
        this.parts.put("handleSupport", this.handleSupport);
        this.handle = root.getChild("handle");
        this.parts.put("handle", this.handle);
        this.nozzle = root.getChild("nozzle");
        this.parts.put("nozzle", this.nozzle);
        this.ignitor = root.getChild("ignitor");
        this.parts.put("ignitor", this.ignitor);
        this.barrel = root.getChild("barrel");
        this.parts.put("barrel", this.barrel);
        this.stock = root.getChild("stock");
        this.parts.put("stock", this.stock);
        this.stockArch = root.getChild("stockArch");
        this.parts.put("stockArch", this.stockArch);
        this.butt = root.getChild("butt");
        this.parts.put("butt", this.butt);
        this.buttBar = root.getChild("buttBar");
        this.parts.put("buttBar", this.buttBar);
        this.sight = root.getChild("sight");
        this.parts.put("sight", this.sight);
        this.barrelArch = root.getChild("barrelArch");
        this.parts.put("barrelArch", this.barrelArch);
        this.underGrip = root.getChild("underGrip");
        this.parts.put("underGrip", this.underGrip);
        this.underGripArchBack = root.getChild("underGripArchBack");
        this.parts.put("underGripArchBack", this.underGripArchBack);
        this.underGripArchFront = root.getChild("underGripArchFront");
        this.parts.put("underGripArchFront", this.underGripArchFront);
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
        PartDefinition p_canister = root.addOrReplaceChild("canister",
                CubeListBuilder.create().texOffs(31, 20).addBox(0.0F, 0.0F, 2.0F, 2.0F, 8.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_canisterSealTop = root.addOrReplaceChild("canisterSealTop",
                CubeListBuilder.create().texOffs(13, 15).addBox(-0.5F, 2.0F, 1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_canisterSealTopLeft = root.addOrReplaceChild("canisterSealTopLeft",
                CubeListBuilder.create().texOffs(22, 30).addBox(1.5F, -1.0F, 1.5F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.7679449F));
        PartDefinition p_canisterSealTopRight = root.addOrReplaceChild("canisterSealTopRight",
                CubeListBuilder.create().texOffs(18, 40).addBox(-1.7F, 1.1F, 1.5F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7679449F));
        PartDefinition p_canisterSealBottom = root.addOrReplaceChild("canisterSealBottom",
                CubeListBuilder.create().texOffs(0, 29).addBox(-0.5F, 6.0F, 1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_canisterSealBottomLeft = root.addOrReplaceChild("canisterSealBottomLeft",
                CubeListBuilder.create().texOffs(7, 46).addBox(5.6F, 3.3F, 1.5F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7679449F));
        PartDefinition p_canisterSealBottomRight = root.addOrReplaceChild("canisterSealBottomRight",
                CubeListBuilder.create().texOffs(22, 35).addBox(4.5F, 4.4F, 1.5F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7679449F));
        PartDefinition p_base = root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -2.0F, -6.0F, 3.0F, 3.0F, 11.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_handleSupport = root.addOrReplaceChild("handleSupport",
                CubeListBuilder.create().texOffs(11, 34).addBox(0.5F, 1.0F, 6.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_handle = root.addOrReplaceChild("handle",
                CubeListBuilder.create().texOffs(0, 39).addBox(0.5F, 3.0F, 6.0F, 1.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2094395F, 0.0F, 0.0F));
        PartDefinition p_nozzle = root.addOrReplaceChild("nozzle",
                CubeListBuilder.create().texOffs(31, 31).addBox(0.5F, -1.0F, -15.0F, 1.0F, 1.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_ignitor = root.addOrReplaceChild("ignitor",
                CubeListBuilder.create().texOffs(7, 39).addBox(0.5F, 3.5F, -14.4F, 1.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.296706F, 0.0F, 0.0F));
        PartDefinition p_barrel = root.addOrReplaceChild("barrel",
                CubeListBuilder.create().texOffs(29, 9).addBox(0.0F, -1.5F, -13.0F, 2.0F, 2.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_stock = root.addOrReplaceChild("stock",
                CubeListBuilder.create().texOffs(7, 21).addBox(0.0F, -1.0F, 5.0F, 2.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_stockArch = root.addOrReplaceChild("stockArch",
                CubeListBuilder.create().texOffs(0, 34).addBox(0.0F, -4.2F, 3.4F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5061455F, 0.0F, 0.0F));
        PartDefinition p_butt = root.addOrReplaceChild("butt",
                CubeListBuilder.create().texOffs(0, 49).addBox(0.0F, -1.0F, 11.0F, 2.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_buttBar = root.addOrReplaceChild("buttBar",
                CubeListBuilder.create().texOffs(0, 46).addBox(0.5F, -0.5F, 10.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_sight = root.addOrReplaceChild("sight",
                CubeListBuilder.create().texOffs(29, 0).addBox(0.4F, -3.5F, -2.5F, 1.0F, 2.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_barrelArch = root.addOrReplaceChild("barrelArch",
                CubeListBuilder.create().texOffs(22, 26).addBox(0.0F, -4.6F, -6.3F, 2.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5061455F, 0.0F, 0.0F));
        PartDefinition p_underGrip = root.addOrReplaceChild("underGrip",
                CubeListBuilder.create().texOffs(0, 15).addBox(0.0F, 1.0F, -4.5F, 2.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_underGripArchBack = root.addOrReplaceChild("underGripArchBack",
                CubeListBuilder.create().texOffs(0, 25).addBox(0.0F, -0.2F, 0.0F, 2.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7504916F, 0.0F, 0.0F));
        PartDefinition p_underGripArchFront = root.addOrReplaceChild("underGripArchFront",
                CubeListBuilder.create().texOffs(0, 21).addBox(0.0F, -3.8F, -4.6F, 2.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7853982F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
