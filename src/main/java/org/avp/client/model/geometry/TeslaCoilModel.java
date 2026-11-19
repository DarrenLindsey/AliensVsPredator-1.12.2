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
 * Geometry converted automatically from the 1.12.2 ModelTeslaCoil by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class TeslaCoilModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "tesla_coil"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart base;
    public final ModelPart lbSupport;
    public final ModelPart rfSupport;
    public final ModelPart sparkGapFrame;
    public final ModelPart sparkGap01;
    public final ModelPart sparkGap03;
    public final ModelPart sparkGap02;
    public final ModelPart platform;
    public final ModelPart nOutlet;
    public final ModelPart eOutlet;
    public final ModelPart wOutlet;
    public final ModelPart sOutlet;
    public final ModelPart rbSupport;
    public final ModelPart lfSupport;
    public final ModelPart sPrimaryCoila;
    public final ModelPart panelNNE;
    public final ModelPart seSecondaryCoil;
    public final ModelPart spokeSE;
    public final ModelPart panelENE;
    public final ModelPart panelESE;
    public final ModelPart panelWNW;
    public final ModelPart swSecondaryCoil;
    public final ModelPart spokeSW;
    public final ModelPart neSecondaryCoil;
    public final ModelPart spokeNE;
    public final ModelPart nwSecondaryCoil;
    public final ModelPart spokeNW;
    public final ModelPart panelSSE;
    public final ModelPart panelSSW;
    public final ModelPart nPrimaryCoila;
    public final ModelPart nPrimaryCoilb;
    public final ModelPart panelNNW;
    public final ModelPart sPrimaryCoilb;
    public final ModelPart wPrimaryCoila;
    public final ModelPart wPrimaryCoilb;
    public final ModelPart ePrimaryCoila;
    public final ModelPart ePrimaryCoilb;
    public final ModelPart panelWSW;
    public final ModelPart sSecondaryCoila;
    public final ModelPart nSecondaryCoila;
    public final ModelPart swSecondaryCoil_1;
    public final ModelPart wSecondaryCoila;
    public final ModelPart secondaryCoilTop;
    public final ModelPart nwSecondaryCoil_1;
    public final ModelPart seSecondaryCoil_1;
    public final ModelPart neSecondaryCoil_1;
    public final ModelPart eSecondaryCoila;
    public final ModelPart nToroid;
    public final ModelPart nwToroid;
    public final ModelPart nwToroidRod;
    public final ModelPart neToroid;
    public final ModelPart neToroidRod;
    public final ModelPart wToroid;
    public final ModelPart wToroidRod;
    public final ModelPart seToroid;
    public final ModelPart seToroidRod;
    public final ModelPart toroidSupport;
    public final ModelPart sToroid;
    public final ModelPart sToroidRod;
    public final ModelPart swToroid;
    public final ModelPart swToroidRod;
    public final ModelPart eToroid;
    public final ModelPart eToroidRod;
    public final ModelPart nToroidRod;

    public TeslaCoilModel(ModelPart root)
    {
        this.root = root;
        this.base = root.getChild("base");
        this.parts.put("base", this.base);
        this.lbSupport = this.base.getChild("lbSupport");
        this.parts.put("lbSupport", this.lbSupport);
        this.rfSupport = this.base.getChild("rfSupport");
        this.parts.put("rfSupport", this.rfSupport);
        this.sparkGapFrame = this.base.getChild("sparkGapFrame");
        this.parts.put("sparkGapFrame", this.sparkGapFrame);
        this.sparkGap01 = this.sparkGapFrame.getChild("sparkGap01");
        this.parts.put("sparkGap01", this.sparkGap01);
        this.sparkGap03 = this.sparkGapFrame.getChild("sparkGap03");
        this.parts.put("sparkGap03", this.sparkGap03);
        this.sparkGap02 = this.sparkGapFrame.getChild("sparkGap02");
        this.parts.put("sparkGap02", this.sparkGap02);
        this.platform = this.base.getChild("platform");
        this.parts.put("platform", this.platform);
        this.nOutlet = this.base.getChild("nOutlet");
        this.parts.put("nOutlet", this.nOutlet);
        this.eOutlet = this.nOutlet.getChild("eOutlet");
        this.parts.put("eOutlet", this.eOutlet);
        this.wOutlet = this.nOutlet.getChild("wOutlet");
        this.parts.put("wOutlet", this.wOutlet);
        this.sOutlet = this.nOutlet.getChild("sOutlet");
        this.parts.put("sOutlet", this.sOutlet);
        this.rbSupport = this.base.getChild("rbSupport");
        this.parts.put("rbSupport", this.rbSupport);
        this.lfSupport = this.base.getChild("lfSupport");
        this.parts.put("lfSupport", this.lfSupport);
        this.sPrimaryCoila = root.getChild("sPrimaryCoila");
        this.parts.put("sPrimaryCoila", this.sPrimaryCoila);
        this.panelNNE = this.sPrimaryCoila.getChild("panelNNE");
        this.parts.put("panelNNE", this.panelNNE);
        this.seSecondaryCoil = this.sPrimaryCoila.getChild("seSecondaryCoil");
        this.parts.put("seSecondaryCoil", this.seSecondaryCoil);
        this.spokeSE = this.seSecondaryCoil.getChild("spokeSE");
        this.parts.put("spokeSE", this.spokeSE);
        this.panelENE = this.sPrimaryCoila.getChild("panelENE");
        this.parts.put("panelENE", this.panelENE);
        this.panelESE = this.sPrimaryCoila.getChild("panelESE");
        this.parts.put("panelESE", this.panelESE);
        this.panelWNW = this.sPrimaryCoila.getChild("panelWNW");
        this.parts.put("panelWNW", this.panelWNW);
        this.swSecondaryCoil = this.sPrimaryCoila.getChild("swSecondaryCoil");
        this.parts.put("swSecondaryCoil", this.swSecondaryCoil);
        this.spokeSW = this.swSecondaryCoil.getChild("spokeSW");
        this.parts.put("spokeSW", this.spokeSW);
        this.neSecondaryCoil = this.sPrimaryCoila.getChild("neSecondaryCoil");
        this.parts.put("neSecondaryCoil", this.neSecondaryCoil);
        this.spokeNE = this.neSecondaryCoil.getChild("spokeNE");
        this.parts.put("spokeNE", this.spokeNE);
        this.nwSecondaryCoil = this.sPrimaryCoila.getChild("nwSecondaryCoil");
        this.parts.put("nwSecondaryCoil", this.nwSecondaryCoil);
        this.spokeNW = this.nwSecondaryCoil.getChild("spokeNW");
        this.parts.put("spokeNW", this.spokeNW);
        this.panelSSE = this.sPrimaryCoila.getChild("panelSSE");
        this.parts.put("panelSSE", this.panelSSE);
        this.panelSSW = this.sPrimaryCoila.getChild("panelSSW");
        this.parts.put("panelSSW", this.panelSSW);
        this.nPrimaryCoila = this.sPrimaryCoila.getChild("nPrimaryCoila");
        this.parts.put("nPrimaryCoila", this.nPrimaryCoila);
        this.nPrimaryCoilb = this.nPrimaryCoila.getChild("nPrimaryCoilb");
        this.parts.put("nPrimaryCoilb", this.nPrimaryCoilb);
        this.panelNNW = this.sPrimaryCoila.getChild("panelNNW");
        this.parts.put("panelNNW", this.panelNNW);
        this.sPrimaryCoilb = this.sPrimaryCoila.getChild("sPrimaryCoilb");
        this.parts.put("sPrimaryCoilb", this.sPrimaryCoilb);
        this.wPrimaryCoila = this.sPrimaryCoila.getChild("wPrimaryCoila");
        this.parts.put("wPrimaryCoila", this.wPrimaryCoila);
        this.wPrimaryCoilb = this.wPrimaryCoila.getChild("wPrimaryCoilb");
        this.parts.put("wPrimaryCoilb", this.wPrimaryCoilb);
        this.ePrimaryCoila = this.sPrimaryCoila.getChild("ePrimaryCoila");
        this.parts.put("ePrimaryCoila", this.ePrimaryCoila);
        this.ePrimaryCoilb = this.ePrimaryCoila.getChild("ePrimaryCoilb");
        this.parts.put("ePrimaryCoilb", this.ePrimaryCoilb);
        this.panelWSW = this.sPrimaryCoila.getChild("panelWSW");
        this.parts.put("panelWSW", this.panelWSW);
        this.sSecondaryCoila = root.getChild("sSecondaryCoila");
        this.parts.put("sSecondaryCoila", this.sSecondaryCoila);
        this.nSecondaryCoila = this.sSecondaryCoila.getChild("nSecondaryCoila");
        this.parts.put("nSecondaryCoila", this.nSecondaryCoila);
        this.swSecondaryCoil_1 = this.sSecondaryCoila.getChild("swSecondaryCoil_1");
        this.parts.put("swSecondaryCoil_1", this.swSecondaryCoil_1);
        this.wSecondaryCoila = this.sSecondaryCoila.getChild("wSecondaryCoila");
        this.parts.put("wSecondaryCoila", this.wSecondaryCoila);
        this.secondaryCoilTop = this.sSecondaryCoila.getChild("secondaryCoilTop");
        this.parts.put("secondaryCoilTop", this.secondaryCoilTop);
        this.nwSecondaryCoil_1 = this.sSecondaryCoila.getChild("nwSecondaryCoil_1");
        this.parts.put("nwSecondaryCoil_1", this.nwSecondaryCoil_1);
        this.seSecondaryCoil_1 = this.sSecondaryCoila.getChild("seSecondaryCoil_1");
        this.parts.put("seSecondaryCoil_1", this.seSecondaryCoil_1);
        this.neSecondaryCoil_1 = this.sSecondaryCoila.getChild("neSecondaryCoil_1");
        this.parts.put("neSecondaryCoil_1", this.neSecondaryCoil_1);
        this.eSecondaryCoila = this.sSecondaryCoila.getChild("eSecondaryCoila");
        this.parts.put("eSecondaryCoila", this.eSecondaryCoila);
        this.nToroid = root.getChild("nToroid");
        this.parts.put("nToroid", this.nToroid);
        this.nwToroid = this.nToroid.getChild("nwToroid");
        this.parts.put("nwToroid", this.nwToroid);
        this.nwToroidRod = this.nwToroid.getChild("nwToroidRod");
        this.parts.put("nwToroidRod", this.nwToroidRod);
        this.neToroid = this.nToroid.getChild("neToroid");
        this.parts.put("neToroid", this.neToroid);
        this.neToroidRod = this.neToroid.getChild("neToroidRod");
        this.parts.put("neToroidRod", this.neToroidRod);
        this.wToroid = this.nToroid.getChild("wToroid");
        this.parts.put("wToroid", this.wToroid);
        this.wToroidRod = this.wToroid.getChild("wToroidRod");
        this.parts.put("wToroidRod", this.wToroidRod);
        this.seToroid = this.nToroid.getChild("seToroid");
        this.parts.put("seToroid", this.seToroid);
        this.seToroidRod = this.seToroid.getChild("seToroidRod");
        this.parts.put("seToroidRod", this.seToroidRod);
        this.toroidSupport = this.nToroid.getChild("toroidSupport");
        this.parts.put("toroidSupport", this.toroidSupport);
        this.sToroid = this.nToroid.getChild("sToroid");
        this.parts.put("sToroid", this.sToroid);
        this.sToroidRod = this.sToroid.getChild("sToroidRod");
        this.parts.put("sToroidRod", this.sToroidRod);
        this.swToroid = this.nToroid.getChild("swToroid");
        this.parts.put("swToroid", this.swToroid);
        this.swToroidRod = this.swToroid.getChild("swToroidRod");
        this.parts.put("swToroidRod", this.swToroidRod);
        this.eToroid = this.nToroid.getChild("eToroid");
        this.parts.put("eToroid", this.eToroid);
        this.eToroidRod = this.eToroid.getChild("eToroidRod");
        this.parts.put("eToroidRod", this.eToroidRod);
        this.nToroidRod = this.nToroid.getChild("nToroidRod");
        this.parts.put("nToroidRod", this.nToroidRod);
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
        PartDefinition p_base = root.addOrReplaceChild("base",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -1.0F, -8.0F, 16.0F, 3.0F, 16.0F),
                PartPose.offset(0.0F, 22.0F, 0.0F));
        PartDefinition p_lbSupport = p_base.addOrReplaceChild("lbSupport",
                CubeListBuilder.create().texOffs(72, 30).addBox(-1.5F, -10.5F, -1.5F, 3.0F, 11.0F, 3.0F),
                PartPose.offsetAndRotation(6.3F, -1.0F, 6.3F, 0.136659280431156F, 0.0F, -0.136659280431156F));
        PartDefinition p_rfSupport = p_base.addOrReplaceChild("rfSupport",
                CubeListBuilder.create().texOffs(87, 15).mirror().addBox(-1.5F, -10.5F, -1.5F, 3.0F, 11.0F, 3.0F),
                PartPose.offsetAndRotation(-6.3F, -1.0F, -6.3F, -0.136659280431156F, 0.0F, 0.136659280431156F));
        PartDefinition p_sparkGapFrame = p_base.addOrReplaceChild("sparkGapFrame",
                CubeListBuilder.create().texOffs(0, 32).addBox(-4.0F, -3.5F, -0.5F, 8.0F, 4.0F, 1.0F),
                PartPose.offset(0.0F, -1.4F, 0.0F));
        PartDefinition p_sparkGap01 = p_sparkGapFrame.addOrReplaceChild("sparkGap01",
                CubeListBuilder.create().texOffs(0, 41).addBox(-3.5F, -2.6F, -1.0F, 2.0F, 3.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_sparkGap03 = p_sparkGapFrame.addOrReplaceChild("sparkGap03",
                CubeListBuilder.create().texOffs(0, 41).addBox(1.6F, -2.6F, -1.0F, 2.0F, 3.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_sparkGap02 = p_sparkGapFrame.addOrReplaceChild("sparkGap02",
                CubeListBuilder.create().texOffs(0, 41).addBox(-1.0F, -2.6F, -1.0F, 2.0F, 3.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_platform = p_base.addOrReplaceChild("platform",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -12.0F, -8.0F, 16.0F, 1.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_nOutlet = p_base.addOrReplaceChild("nOutlet",
                CubeListBuilder.create().texOffs(71, 0).addBox(-1.5F, -6.0F, -8.0F, 3.0F, 6.0F, 2.0F),
                PartPose.offset(0.0F, -1.0F, 0.0F));
        PartDefinition p_eOutlet = p_nOutlet.addOrReplaceChild("eOutlet",
                CubeListBuilder.create().texOffs(71, 0).addBox(-1.5F, -6.0F, -8.0F, 3.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F, 0.0F));
        PartDefinition p_wOutlet = p_nOutlet.addOrReplaceChild("wOutlet",
                CubeListBuilder.create().texOffs(71, 0).addBox(-1.5F, -6.0F, -8.0F, 3.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));
        PartDefinition p_sOutlet = p_nOutlet.addOrReplaceChild("sOutlet",
                CubeListBuilder.create().texOffs(71, 0).addBox(-1.5F, -6.0F, -8.0F, 3.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.141592653589793F, 0.0F));
        PartDefinition p_rbSupport = p_base.addOrReplaceChild("rbSupport",
                CubeListBuilder.create().texOffs(72, 30).mirror().addBox(-1.5F, -10.5F, -1.5F, 3.0F, 11.0F, 3.0F),
                PartPose.offsetAndRotation(-6.3F, -1.0F, 6.3F, 0.136659280431156F, 0.0F, 0.136659280431156F));
        PartDefinition p_lfSupport = p_base.addOrReplaceChild("lfSupport",
                CubeListBuilder.create().texOffs(87, 15).addBox(-1.5F, -10.5F, -1.5F, 3.0F, 11.0F, 3.0F),
                PartPose.offsetAndRotation(6.3F, -1.0F, -6.3F, -0.136659280431156F, 0.0F, -0.136659280431156F));
        PartDefinition p_sPrimaryCoila = root.addOrReplaceChild("sPrimaryCoila",
                CubeListBuilder.create().texOffs(10, 40).addBox(-2.0F, 0.0F, 3.9F, 4.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 8.0F, 0.0F));
        PartDefinition p_panelNNE = p_sPrimaryCoila.addOrReplaceChild("panelNNE",
                CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -8.5F, 3.7F, 6.0F, 5.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, 0.4363323129985824F, 0.0F));
        PartDefinition p_seSecondaryCoil = p_sPrimaryCoila.addOrReplaceChild("seSecondaryCoil",
                CubeListBuilder.create().texOffs(10, 40).addBox(-2.0F, 0.0F, 3.9F, 4.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.356194490192345F, 0.0F));
        PartDefinition p_spokeSE = p_seSecondaryCoil.addOrReplaceChild("spokeSE",
                CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, 3.1F, 3.5F, 2.0F, 1.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 0.0F, 0.0F));
        PartDefinition p_panelENE = p_sPrimaryCoila.addOrReplaceChild("panelENE",
                CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -8.5F, 3.7F, 6.0F, 5.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, 1.1693705988362009F, 0.0F));
        PartDefinition p_panelESE = p_sPrimaryCoila.addOrReplaceChild("panelESE",
                CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -8.5F, 3.7F, 6.0F, 5.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, 1.9722220547535922F, 0.0F));
        PartDefinition p_panelWNW = p_sPrimaryCoila.addOrReplaceChild("panelWNW",
                CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -8.5F, 3.7F, 6.0F, 5.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, -1.1838568316277536F, 0.0F));
        PartDefinition p_swSecondaryCoil = p_sPrimaryCoila.addOrReplaceChild("swSecondaryCoil",
                CubeListBuilder.create().texOffs(10, 40).addBox(-2.0F, 0.0F, -4.9F, 4.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_spokeSW = p_swSecondaryCoil.addOrReplaceChild("spokeSW",
                CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, 3.1F, 3.5F, 2.0F, 1.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 3.141592653589793F, 0.0F));
        PartDefinition p_neSecondaryCoil = p_sPrimaryCoila.addOrReplaceChild("neSecondaryCoil",
                CubeListBuilder.create().texOffs(10, 40).addBox(-2.0F, 0.0F, 3.9F, 4.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_spokeNE = p_neSecondaryCoil.addOrReplaceChild("spokeNE",
                CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, 3.1F, 3.2F, 2.0F, 1.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 0.0F, 0.0F));
        PartDefinition p_nwSecondaryCoil = p_sPrimaryCoila.addOrReplaceChild("nwSecondaryCoil",
                CubeListBuilder.create().texOffs(10, 40).addBox(-2.0F, 0.0F, -4.9F, 4.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.356194490192345F, 0.0F));
        PartDefinition p_spokeNW = p_nwSecondaryCoil.addOrReplaceChild("spokeNW",
                CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, 3.1F, 3.5F, 2.0F, 1.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 3.141592653589793F, 0.0F));
        PartDefinition p_panelSSE = p_sPrimaryCoila.addOrReplaceChild("panelSSE",
                CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -8.5F, 3.7F, 6.0F, 5.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, 2.7227136331111543F, 0.0F));
        PartDefinition p_panelSSW = p_sPrimaryCoila.addOrReplaceChild("panelSSW",
                CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -8.5F, 3.7F, 6.0F, 5.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, 3.5255650890285457F, 0.0F));
        PartDefinition p_nPrimaryCoila = p_sPrimaryCoila.addOrReplaceChild("nPrimaryCoila",
                CubeListBuilder.create().texOffs(10, 40).addBox(-2.0F, 0.0F, 3.9F, 4.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.141592653589793F, 0.0F));
        PartDefinition p_nPrimaryCoilb = p_nPrimaryCoila.addOrReplaceChild("nPrimaryCoilb",
                CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, 3.1F, 3.5F, 2.0F, 1.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 0.0F, 0.0F));
        PartDefinition p_panelNNW = p_sPrimaryCoila.addOrReplaceChild("panelNNW",
                CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -8.5F, 3.7F, 6.0F, 5.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, -0.41887902047863906F, 0.0F));
        PartDefinition p_sPrimaryCoilb = p_sPrimaryCoila.addOrReplaceChild("sPrimaryCoilb",
                CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, 3.1F, 3.5F, 2.0F, 1.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 0.0F, 0.0F));
        PartDefinition p_wPrimaryCoila = p_sPrimaryCoila.addOrReplaceChild("wPrimaryCoila",
                CubeListBuilder.create().texOffs(10, 40).addBox(-2.0F, 0.0F, 3.9F, 4.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F, 0.0F));
        PartDefinition p_wPrimaryCoilb = p_wPrimaryCoila.addOrReplaceChild("wPrimaryCoilb",
                CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, 3.1F, 3.5F, 2.0F, 1.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 0.0F, 0.0F));
        PartDefinition p_ePrimaryCoila = p_sPrimaryCoila.addOrReplaceChild("ePrimaryCoila",
                CubeListBuilder.create().texOffs(10, 40).addBox(-2.0F, 0.0F, 3.9F, 4.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));
        PartDefinition p_ePrimaryCoilb = p_ePrimaryCoila.addOrReplaceChild("ePrimaryCoilb",
                CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, 3.1F, 3.5F, 2.0F, 1.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7285004297824331F, 0.0F, 0.0F));
        PartDefinition p_panelWSW = p_sPrimaryCoila.addOrReplaceChild("panelWSW",
                CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -8.5F, 3.7F, 6.0F, 5.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.8196066167365371F, -1.9722220547535922F, 0.0F));
        PartDefinition p_sSecondaryCoila = root.addOrReplaceChild("sSecondaryCoila",
                CubeListBuilder.create().texOffs(102, 0).mirror().addBox(-2.0F, 0.0F, 3.9F, 4.0F, 26.0F, 1.0F),
                PartPose.offset(0.0F, -18.0F, 0.0F));
        PartDefinition p_nSecondaryCoila = p_sSecondaryCoila.addOrReplaceChild("nSecondaryCoila",
                CubeListBuilder.create().texOffs(102, 0).addBox(-2.0F, 0.0F, 3.9F, 4.0F, 26.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.141592653589793F, 0.0F));
        PartDefinition p_swSecondaryCoil_1 = p_sSecondaryCoila.addOrReplaceChild("swSecondaryCoil_1",
                CubeListBuilder.create().texOffs(102, 0).addBox(-2.0F, 0.0F, -4.9F, 4.0F, 26.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_wSecondaryCoila = p_sSecondaryCoila.addOrReplaceChild("wSecondaryCoila",
                CubeListBuilder.create().texOffs(102, 0).mirror().addBox(-2.0F, 0.0F, 3.9F, 4.0F, 26.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F, 0.0F));
        PartDefinition p_secondaryCoilTop = p_sSecondaryCoila.addOrReplaceChild("secondaryCoilTop",
                CubeListBuilder.create().texOffs(85, 30).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 0.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_nwSecondaryCoil_1 = p_sSecondaryCoila.addOrReplaceChild("nwSecondaryCoil_1",
                CubeListBuilder.create().texOffs(102, 0).mirror().addBox(-2.0F, 0.0F, -4.9F, 4.0F, 26.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.356194490192345F, 0.0F));
        PartDefinition p_seSecondaryCoil_1 = p_sSecondaryCoila.addOrReplaceChild("seSecondaryCoil_1",
                CubeListBuilder.create().texOffs(102, 0).mirror().addBox(-2.0F, 0.0F, 3.9F, 4.0F, 26.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.356194490192345F, 0.0F));
        PartDefinition p_neSecondaryCoil_1 = p_sSecondaryCoila.addOrReplaceChild("neSecondaryCoil_1",
                CubeListBuilder.create().texOffs(102, 0).mirror().addBox(-2.0F, 0.0F, 3.9F, 4.0F, 26.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_eSecondaryCoila = p_sSecondaryCoila.addOrReplaceChild("eSecondaryCoila",
                CubeListBuilder.create().texOffs(102, 0).addBox(-2.0F, 0.0F, 3.9F, 4.0F, 26.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));
        PartDefinition p_nToroid = root.addOrReplaceChild("nToroid",
                CubeListBuilder.create().texOffs(24, 24).addBox(-5.0F, -1.9F, -12.0F, 10.0F, 5.0F, 4.0F),
                PartPose.offset(0.0F, -22.9F, 0.0F));
        PartDefinition p_nwToroid = p_nToroid.addOrReplaceChild("nwToroid",
                CubeListBuilder.create().texOffs(24, 24).addBox(-5.0F, -1.9F, -12.0F, 10.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_nwToroidRod = p_nwToroid.addOrReplaceChild("nwToroidRod",
                CubeListBuilder.create().texOffs(18, 46).addBox(-0.5F, 2.5F, -8.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_neToroid = p_nToroid.addOrReplaceChild("neToroid",
                CubeListBuilder.create().texOffs(24, 24).addBox(-5.0F, -1.9F, -12.0F, 10.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_neToroidRod = p_neToroid.addOrReplaceChild("neToroidRod",
                CubeListBuilder.create().texOffs(18, 46).addBox(-0.5F, 2.5F, -8.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_wToroid = p_nToroid.addOrReplaceChild("wToroid",
                CubeListBuilder.create().texOffs(24, 24).addBox(-5.0F, -1.9F, -12.0F, 10.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));
        PartDefinition p_wToroidRod = p_wToroid.addOrReplaceChild("wToroidRod",
                CubeListBuilder.create().texOffs(18, 46).addBox(-0.5F, 2.5F, -8.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_seToroid = p_nToroid.addOrReplaceChild("seToroid",
                CubeListBuilder.create().texOffs(24, 24).addBox(-5.0F, -1.9F, -12.0F, 10.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.356194490192345F, 0.0F));
        PartDefinition p_seToroidRod = p_seToroid.addOrReplaceChild("seToroidRod",
                CubeListBuilder.create().texOffs(18, 46).addBox(-0.5F, 2.5F, -8.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_toroidSupport = p_nToroid.addOrReplaceChild("toroidSupport",
                CubeListBuilder.create().texOffs(36, 39).addBox(-1.5F, 1.8F, -1.5F, 3.0F, 4.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_sToroid = p_nToroid.addOrReplaceChild("sToroid",
                CubeListBuilder.create().texOffs(24, 24).addBox(-5.0F, -1.9F, -12.0F, 10.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.141592653589793F, 0.0F));
        PartDefinition p_sToroidRod = p_sToroid.addOrReplaceChild("sToroidRod",
                CubeListBuilder.create().texOffs(18, 46).addBox(-0.5F, 2.5F, -8.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_swToroid = p_nToroid.addOrReplaceChild("swToroid",
                CubeListBuilder.create().texOffs(24, 24).addBox(-5.0F, -1.9F, -12.0F, 10.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.356194490192345F, 0.0F));
        PartDefinition p_swToroidRod = p_swToroid.addOrReplaceChild("swToroidRod",
                CubeListBuilder.create().texOffs(18, 46).addBox(-0.5F, 2.5F, -8.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_eToroid = p_nToroid.addOrReplaceChild("eToroid",
                CubeListBuilder.create().texOffs(24, 24).addBox(-5.0F, -1.9F, -12.0F, 10.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F, 0.0F));
        PartDefinition p_eToroidRod = p_eToroid.addOrReplaceChild("eToroidRod",
                CubeListBuilder.create().texOffs(18, 46).addBox(-0.5F, 2.5F, -8.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.0F, 0.0F));
        PartDefinition p_nToroidRod = p_nToroid.addOrReplaceChild("nToroidRod",
                CubeListBuilder.create().texOffs(18, 46).addBox(-0.5F, 2.5F, -8.2F, 1.0F, 1.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
