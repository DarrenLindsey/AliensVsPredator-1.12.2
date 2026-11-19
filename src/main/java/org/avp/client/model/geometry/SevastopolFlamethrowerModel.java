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
 * Geometry converted automatically from the 1.12.2 ModelSevastopolFlamethrower by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class SevastopolFlamethrowerModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "sevastopol_flamethrower"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart body04L;
    public final ModelPart laterialBump;
    public final ModelPart body05;
    public final ModelPart nozzlePipe;
    public final ModelPart body04R;
    public final ModelPart gauge;
    public final ModelPart triggerGuard;
    public final ModelPart hoseValve;
    public final ModelPart rRod;
    public final ModelPart body04B;
    public final ModelPart lowerRidge02;
    public final ModelPart lRod;
    public final ModelPart nozzle01;
    public final ModelPart propaneCanister;
    public final ModelPart propaneCanisterTop;
    public final ModelPart propaneCanisterValve;
    public final ModelPart foregrip;
    public final ModelPart canisterRack;
    public final ModelPart backgrip;
    public final ModelPart nozzle02;
    public final ModelPart body01_1;
    public final ModelPart nozzle03;
    public final ModelPart hose01;
    public final ModelPart hose02;
    public final ModelPart hose03;
    public final ModelPart hose04;
    public final ModelPart hose05;
    public final ModelPart hose06;
    public final ModelPart hose07;
    public final ModelPart hose08;
    public final ModelPart hose09;
    public final ModelPart hose10;
    public final ModelPart hose11;
    public final ModelPart hose12;
    public final ModelPart body03;
    public final ModelPart topBump;
    public final ModelPart hoseConnector;
    public final ModelPart body01;
    public final ModelPart triggerLever;
    public final ModelPart lowerRidge01;
    public final ModelPart backgripGuard;
    public final ModelPart lowerRidge03;
    public final ModelPart toggleBank01;
    public final ModelPart toggleBank02;
    public final ModelPart toggles;
    public final ModelPart cylinderN;
    public final ModelPart cylinderSE;
    public final ModelPart cylinderE;
    public final ModelPart cylinderS;
    public final ModelPart cylinderNE;
    public final ModelPart cylinderSW;
    public final ModelPart cylinderNW;
    public final ModelPart cylinderW;
    public final ModelPart cylinderBack;
    public final ModelPart propellantCanister;
    public final ModelPart propellantCanisterTop;
    public final ModelPart propellantCanisterPin;

    public SevastopolFlamethrowerModel(ModelPart root)
    {
        this.root = root;
        this.body04L = root.getChild("body04L");
        this.parts.put("body04L", this.body04L);
        this.laterialBump = root.getChild("laterialBump");
        this.parts.put("laterialBump", this.laterialBump);
        this.body05 = root.getChild("body05");
        this.parts.put("body05", this.body05);
        this.nozzlePipe = root.getChild("nozzlePipe");
        this.parts.put("nozzlePipe", this.nozzlePipe);
        this.body04R = root.getChild("body04R");
        this.parts.put("body04R", this.body04R);
        this.gauge = root.getChild("gauge");
        this.parts.put("gauge", this.gauge);
        this.triggerGuard = root.getChild("triggerGuard");
        this.parts.put("triggerGuard", this.triggerGuard);
        this.hoseValve = root.getChild("hoseValve");
        this.parts.put("hoseValve", this.hoseValve);
        this.rRod = root.getChild("rRod");
        this.parts.put("rRod", this.rRod);
        this.body04B = root.getChild("body04B");
        this.parts.put("body04B", this.body04B);
        this.lowerRidge02 = root.getChild("lowerRidge02");
        this.parts.put("lowerRidge02", this.lowerRidge02);
        this.lRod = root.getChild("lRod");
        this.parts.put("lRod", this.lRod);
        this.nozzle01 = root.getChild("nozzle01");
        this.parts.put("nozzle01", this.nozzle01);
        this.propaneCanister = root.getChild("propaneCanister");
        this.parts.put("propaneCanister", this.propaneCanister);
        this.propaneCanisterTop = this.propaneCanister.getChild("propaneCanisterTop");
        this.parts.put("propaneCanisterTop", this.propaneCanisterTop);
        this.propaneCanisterValve = this.propaneCanister.getChild("propaneCanisterValve");
        this.parts.put("propaneCanisterValve", this.propaneCanisterValve);
        this.foregrip = root.getChild("foregrip");
        this.parts.put("foregrip", this.foregrip);
        this.canisterRack = root.getChild("canisterRack");
        this.parts.put("canisterRack", this.canisterRack);
        this.backgrip = root.getChild("backgrip");
        this.parts.put("backgrip", this.backgrip);
        this.nozzle02 = root.getChild("nozzle02");
        this.parts.put("nozzle02", this.nozzle02);
        this.body01_1 = root.getChild("body01_1");
        this.parts.put("body01_1", this.body01_1);
        this.nozzle03 = root.getChild("nozzle03");
        this.parts.put("nozzle03", this.nozzle03);
        this.hose01 = root.getChild("hose01");
        this.parts.put("hose01", this.hose01);
        this.hose02 = this.hose01.getChild("hose02");
        this.parts.put("hose02", this.hose02);
        this.hose03 = this.hose02.getChild("hose03");
        this.parts.put("hose03", this.hose03);
        this.hose04 = this.hose03.getChild("hose04");
        this.parts.put("hose04", this.hose04);
        this.hose05 = this.hose04.getChild("hose05");
        this.parts.put("hose05", this.hose05);
        this.hose06 = this.hose05.getChild("hose06");
        this.parts.put("hose06", this.hose06);
        this.hose07 = this.hose06.getChild("hose07");
        this.parts.put("hose07", this.hose07);
        this.hose08 = this.hose07.getChild("hose08");
        this.parts.put("hose08", this.hose08);
        this.hose09 = this.hose08.getChild("hose09");
        this.parts.put("hose09", this.hose09);
        this.hose10 = this.hose09.getChild("hose10");
        this.parts.put("hose10", this.hose10);
        this.hose11 = this.hose10.getChild("hose11");
        this.parts.put("hose11", this.hose11);
        this.hose12 = this.hose11.getChild("hose12");
        this.parts.put("hose12", this.hose12);
        this.body03 = root.getChild("body03");
        this.parts.put("body03", this.body03);
        this.topBump = root.getChild("topBump");
        this.parts.put("topBump", this.topBump);
        this.hoseConnector = root.getChild("hoseConnector");
        this.parts.put("hoseConnector", this.hoseConnector);
        this.body01 = root.getChild("body01");
        this.parts.put("body01", this.body01);
        this.triggerLever = root.getChild("triggerLever");
        this.parts.put("triggerLever", this.triggerLever);
        this.lowerRidge01 = root.getChild("lowerRidge01");
        this.parts.put("lowerRidge01", this.lowerRidge01);
        this.backgripGuard = root.getChild("backgripGuard");
        this.parts.put("backgripGuard", this.backgripGuard);
        this.lowerRidge03 = root.getChild("lowerRidge03");
        this.parts.put("lowerRidge03", this.lowerRidge03);
        this.toggleBank01 = root.getChild("toggleBank01");
        this.parts.put("toggleBank01", this.toggleBank01);
        this.toggleBank02 = this.toggleBank01.getChild("toggleBank02");
        this.parts.put("toggleBank02", this.toggleBank02);
        this.toggles = this.toggleBank02.getChild("toggles");
        this.parts.put("toggles", this.toggles);
        this.cylinderN = root.getChild("cylinderN");
        this.parts.put("cylinderN", this.cylinderN);
        this.cylinderSE = this.cylinderN.getChild("cylinderSE");
        this.parts.put("cylinderSE", this.cylinderSE);
        this.cylinderE = this.cylinderN.getChild("cylinderE");
        this.parts.put("cylinderE", this.cylinderE);
        this.cylinderS = this.cylinderN.getChild("cylinderS");
        this.parts.put("cylinderS", this.cylinderS);
        this.cylinderNE = this.cylinderN.getChild("cylinderNE");
        this.parts.put("cylinderNE", this.cylinderNE);
        this.cylinderSW = this.cylinderN.getChild("cylinderSW");
        this.parts.put("cylinderSW", this.cylinderSW);
        this.cylinderNW = this.cylinderN.getChild("cylinderNW");
        this.parts.put("cylinderNW", this.cylinderNW);
        this.cylinderW = this.cylinderN.getChild("cylinderW");
        this.parts.put("cylinderW", this.cylinderW);
        this.cylinderBack = this.cylinderN.getChild("cylinderBack");
        this.parts.put("cylinderBack", this.cylinderBack);
        this.propellantCanister = root.getChild("propellantCanister");
        this.parts.put("propellantCanister", this.propellantCanister);
        this.propellantCanisterTop = this.propellantCanister.getChild("propellantCanisterTop");
        this.parts.put("propellantCanisterTop", this.propellantCanisterTop);
        this.propellantCanisterPin = this.propellantCanister.getChild("propellantCanisterPin");
        this.parts.put("propellantCanisterPin", this.propellantCanisterPin);
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
        PartDefinition p_body04L = root.addOrReplaceChild("body04L",
                CubeListBuilder.create().texOffs(73, 3).addBox(-0.5F, -0.4F, 0.0F, 1.0F, 2.0F, 26.0F),
                PartPose.offsetAndRotation(1.2F, -0.3F, 3.4F, 0.0F, 0.0F, 0.22759093446006054F));
        PartDefinition p_laterialBump = root.addOrReplaceChild("laterialBump",
                CubeListBuilder.create().texOffs(71, 32).addBox(0.0F, -1.0F, -2.0F, 1.0F, 2.0F, 4.0F),
                PartPose.offset(0.6F, 0.9F, 27.4F));
        PartDefinition p_body05 = root.addOrReplaceChild("body05",
                CubeListBuilder.create().texOffs(19, 0).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 1.0F, 26.0F),
                PartPose.offset(0.0F, -1.9F, 3.5F));
        PartDefinition p_nozzlePipe = root.addOrReplaceChild("nozzlePipe",
                CubeListBuilder.create().texOffs(0, 21).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, -6.3F));
        PartDefinition p_body04R = root.addOrReplaceChild("body04R",
                CubeListBuilder.create().texOffs(73, 3).addBox(-0.5F, -0.4F, 0.0F, 1.0F, 2.0F, 26.0F),
                PartPose.offsetAndRotation(-1.2F, -0.3F, 3.4F, 0.0F, 0.0F, -0.22759093446006054F));
        PartDefinition p_gauge = root.addOrReplaceChild("gauge",
                CubeListBuilder.create().texOffs(53, 3).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -3.0F, 17.8F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_triggerGuard = root.addOrReplaceChild("triggerGuard",
                CubeListBuilder.create().texOffs(24, 34).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 2.0F, 3.0F),
                PartPose.offset(0.0F, 2.4F, 11.5F));
        PartDefinition p_hoseValve = root.addOrReplaceChild("hoseValve",
                CubeListBuilder.create().texOffs(14, 9).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(-1.2F, -0.1F, -10.0F, 0.0F, 0.0F, 0.18203784098300857F));
        PartDefinition p_rRod = root.addOrReplaceChild("rRod",
                CubeListBuilder.create().texOffs(52, 7).mirror().addBox(-1.0F, -0.5F, -7.0F, 1.0F, 1.0F, 16.0F),
                PartPose.offset(-1.8F, -1.1F, 9.9F));
        PartDefinition p_body04B = root.addOrReplaceChild("body04B",
                CubeListBuilder.create().texOffs(69, 34).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 1.0F, 26.0F),
                PartPose.offset(0.0F, 1.1F, 3.4F));
        PartDefinition p_lowerRidge02 = root.addOrReplaceChild("lowerRidge02",
                CubeListBuilder.create().texOffs(57, 45).addBox(-1.0F, -0.7F, -1.9F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-0.05F, 1.2F, 6.3F, -0.4553564018453205F, 0.0F, 0.0F));
        PartDefinition p_lRod = root.addOrReplaceChild("lRod",
                CubeListBuilder.create().texOffs(52, 7).addBox(0.0F, -0.5F, -7.0F, 1.0F, 1.0F, 16.0F),
                PartPose.offset(1.8F, -1.1F, 9.9F));
        PartDefinition p_nozzle01 = root.addOrReplaceChild("nozzle01",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, -8.0F));
        PartDefinition p_propaneCanister = root.addOrReplaceChild("propaneCanister",
                CubeListBuilder.create().texOffs(103, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F),
                PartPose.offsetAndRotation(1.7F, 3.6F, 22.6F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_propaneCanisterTop = p_propaneCanister.addOrReplaceChild("propaneCanisterTop",
                CubeListBuilder.create().texOffs(105, 1).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F),
                PartPose.offset(0.0F, 0.2F, 0.0F));
        PartDefinition p_propaneCanisterValve = p_propaneCanister.addOrReplaceChild("propaneCanisterValve",
                CubeListBuilder.create().texOffs(103, 0).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, -0.5F, 0.0F));
        PartDefinition p_foregrip = root.addOrReplaceChild("foregrip",
                CubeListBuilder.create().texOffs(24, 40).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 4.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 2.5F, 13.6F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_canisterRack = root.addOrReplaceChild("canisterRack",
                CubeListBuilder.create().texOffs(102, 13).addBox(0.0F, -1.0F, -2.8F, 2.0F, 2.0F, 5.0F),
                PartPose.offset(1.1F, 2.0F, 22.4F));
        PartDefinition p_backgrip = root.addOrReplaceChild("backgrip",
                CubeListBuilder.create().texOffs(83, 48).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 1.1F, 26.8F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_nozzle02 = root.addOrReplaceChild("nozzle02",
                CubeListBuilder.create().texOffs(0, 9).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, -8.9F));
        PartDefinition p_body01_1 = root.addOrReplaceChild("body01_1",
                CubeListBuilder.create().texOffs(0, 52).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 2.4F));
        PartDefinition p_nozzle03 = root.addOrReplaceChild("nozzle03",
                CubeListBuilder.create().texOffs(0, 9).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, -8.1F));
        PartDefinition p_hose01 = root.addOrReplaceChild("hose01",
                CubeListBuilder.create().texOffs(24, 10).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, -3.1F, 4.3F));
        PartDefinition p_hose02 = p_hose01.addOrReplaceChild("hose02",
                CubeListBuilder.create().texOffs(25, 11).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.8F, 0.0F, 0.136659280431156F, 0.0F));
        PartDefinition p_hose03 = p_hose02.addOrReplaceChild("hose03",
                CubeListBuilder.create().texOffs(25, 11).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -1.9F, -0.091106186954104F, 0.136659280431156F, 0.0F));
        PartDefinition p_hose04 = p_hose03.addOrReplaceChild("hose04",
                CubeListBuilder.create().texOffs(24, 10).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, -0.045553093477052F, 0.22759093446006054F, 0.0F));
        PartDefinition p_hose05 = p_hose04.addOrReplaceChild("hose05",
                CubeListBuilder.create().texOffs(25, 11).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.9F, 0.045553093477052F, 0.18203784098300857F, 0.0F));
        PartDefinition p_hose06 = p_hose05.addOrReplaceChild("hose06",
                CubeListBuilder.create().texOffs(25, 11).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -0.05F, -1.7F, 0.18203784098300857F, -0.136659280431156F, 0.0F));
        PartDefinition p_hose07 = p_hose06.addOrReplaceChild("hose07",
                CubeListBuilder.create().texOffs(25, 11).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -0.05F, -1.7F, 0.27314402793711257F, -0.136659280431156F, 0.0F));
        PartDefinition p_hose08 = p_hose07.addOrReplaceChild("hose08",
                CubeListBuilder.create().texOffs(26, 12).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-0.05F, -0.05F, -1.7F, 0.18203784098300857F, 0.091106186954104F, 0.0F));
        PartDefinition p_hose09 = p_hose08.addOrReplaceChild("hose09",
                CubeListBuilder.create().texOffs(26, 12).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-0.05F, 0.0F, -0.7F, 0.136659280431156F, 0.22759093446006054F, 0.0F));
        PartDefinition p_hose10 = p_hose09.addOrReplaceChild("hose10",
                CubeListBuilder.create().texOffs(26, 12).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-0.05F, 0.0F, -0.7F, 0.136659280431156F, -0.5009094953223726F, 0.0F));
        PartDefinition p_hose11 = p_hose10.addOrReplaceChild("hose11",
                CubeListBuilder.create().texOffs(25, 11).addBox(-0.5F, -0.5F, -1.9F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, -0.8F, 0.27314402793711257F, -0.31869712141416456F, 0.0F));
        PartDefinition p_hose12 = p_hose11.addOrReplaceChild("hose12",
                CubeListBuilder.create().texOffs(24, 10).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-0.2F, -0.1F, -1.5F, 0.4553564018453205F, -0.8196066167365371F, 0.0F));
        PartDefinition p_body03 = root.addOrReplaceChild("body03",
                CubeListBuilder.create().texOffs(19, 31).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 1.0F, 26.0F),
                PartPose.offset(0.0F, 0.0F, 3.4F));
        PartDefinition p_topBump = root.addOrReplaceChild("topBump",
                CubeListBuilder.create().texOffs(29, 0).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, -2.2F, 26.4F));
        PartDefinition p_hoseConnector = root.addOrReplaceChild("hoseConnector",
                CubeListBuilder.create().texOffs(0, 34).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, -1.1F, 4.0F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_body01 = root.addOrReplaceChild("body01",
                CubeListBuilder.create().texOffs(0, 47).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.6F));
        PartDefinition p_triggerLever = root.addOrReplaceChild("triggerLever",
                CubeListBuilder.create().texOffs(34, 34).addBox(-0.5F, 0.0F, -1.5F, 0.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 2.6F, 12.5F));
        PartDefinition p_lowerRidge01 = root.addOrReplaceChild("lowerRidge01",
                CubeListBuilder.create().texOffs(51, 32).addBox(-1.0F, -0.7F, -1.9F, 2.0F, 2.0F, 9.0F),
                PartPose.offset(-0.05F, 1.6F, 8.6F));
        PartDefinition p_backgripGuard = root.addOrReplaceChild("backgripGuard",
                CubeListBuilder.create().texOffs(80, 38).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 5.0F, 3.0F),
                PartPose.offset(0.0F, 1.2F, 27.4F));
        PartDefinition p_lowerRidge03 = root.addOrReplaceChild("lowerRidge03",
                CubeListBuilder.create().texOffs(57, 45).addBox(-1.0F, -0.7F, -1.9F, 2.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-0.05F, 0.9F, 16.7F, 0.4553564018453205F, 0.0F, 0.0F));
        PartDefinition p_toggleBank01 = root.addOrReplaceChild("toggleBank01",
                CubeListBuilder.create().texOffs(72, 8).addBox(-1.0F, -1.0F, -2.7F, 2.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, -2.1F, 21.8F));
        PartDefinition p_toggleBank02 = p_toggleBank01.addOrReplaceChild("toggleBank02",
                CubeListBuilder.create().texOffs(74, 2).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, -0.6F, 0.8F));
        PartDefinition p_toggles = p_toggleBank02.addOrReplaceChild("toggles",
                CubeListBuilder.create().texOffs(87, 1).addBox(0.0F, -1.3F, -1.0F, 0.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -0.6F, 0.1F, 0.0F, 0.0F, -0.136659280431156F));
        PartDefinition p_cylinderN = root.addOrReplaceChild("cylinderN",
                CubeListBuilder.create().texOffs(107, 39).addBox(-1.0F, 0.0F, -2.45F, 2.0F, 12.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -0.5F, 29.4F, 1.5707963267948966F, 0.0F, 0.0F));
        PartDefinition p_cylinderSE = p_cylinderN.addOrReplaceChild("cylinderSE",
                CubeListBuilder.create().texOffs(107, 39).addBox(-1.0F, 0.0F, -2.45F, 2.0F, 12.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.356194490192345F, 0.0F));
        PartDefinition p_cylinderE = p_cylinderN.addOrReplaceChild("cylinderE",
                CubeListBuilder.create().texOffs(107, 39).addBox(-1.0F, 0.0F, -2.45F, 2.0F, 12.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5707963267948966F, 0.0F));
        PartDefinition p_cylinderS = p_cylinderN.addOrReplaceChild("cylinderS",
                CubeListBuilder.create().texOffs(107, 39).addBox(-1.0F, 0.0F, -2.45F, 2.0F, 12.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.141592653589793F, 0.0F));
        PartDefinition p_cylinderNE = p_cylinderN.addOrReplaceChild("cylinderNE",
                CubeListBuilder.create().texOffs(107, 39).addBox(-1.0F, 0.0F, -2.45F, 2.0F, 12.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_cylinderSW = p_cylinderN.addOrReplaceChild("cylinderSW",
                CubeListBuilder.create().texOffs(107, 39).addBox(-1.0F, 0.0F, -2.45F, 2.0F, 12.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.356194490192345F, 0.0F));
        PartDefinition p_cylinderNW = p_cylinderN.addOrReplaceChild("cylinderNW",
                CubeListBuilder.create().texOffs(107, 39).addBox(-1.0F, 0.0F, -2.45F, 2.0F, 12.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7853981633974483F, 0.0F));
        PartDefinition p_cylinderW = p_cylinderN.addOrReplaceChild("cylinderW",
                CubeListBuilder.create().texOffs(107, 39).addBox(-1.0F, 0.0F, -2.45F, 2.0F, 12.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5707963267948966F, 0.0F));
        PartDefinition p_cylinderBack = p_cylinderN.addOrReplaceChild("cylinderBack",
                CubeListBuilder.create().texOffs(111, 39).addBox(-2.5F, 0.01F, -2.6F, 5.0F, 0.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 12.0F, -0.1F, 0.0F, 3.141592653589793F, 0.0F));
        PartDefinition p_propellantCanister = root.addOrReplaceChild("propellantCanister",
                CubeListBuilder.create().texOffs(116, 0).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 9.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 3.6F, 19.9F, 0.0F, 0.7853981633974483F, 0.0F));
        PartDefinition p_propellantCanisterTop = p_propellantCanister.addOrReplaceChild("propellantCanisterTop",
                CubeListBuilder.create().texOffs(117, 1).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F),
                PartPose.offset(0.0F, 0.2F, 0.0F));
        PartDefinition p_propellantCanisterPin = p_propellantCanister.addOrReplaceChild("propellantCanisterPin",
                CubeListBuilder.create().texOffs(120, 14).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F),
                PartPose.offset(0.0F, 8.6F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
