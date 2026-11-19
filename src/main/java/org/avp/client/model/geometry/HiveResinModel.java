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
 * Geometry converted automatically from the 1.12.2 ModelHiveResin by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class HiveResinModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "hive_resin"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart webbing05;
    public final ModelPart webbing09;
    public final ModelPart webbing03;
    public final ModelPart webbing13;
    public final ModelPart nResinRipCenter;
    public final ModelPart nResinRibBottom01;
    public final ModelPart nTopRoot01;
    public final ModelPart nTopRoot03;
    public final ModelPart nTopRoot02;
    public final ModelPart nResinRibTop02;
    public final ModelPart nResinRibBottom02;
    public final ModelPart nResinRibTop01;
    public final ModelPart nBottomRoot01;
    public final ModelPart nBottomRoot02;
    public final ModelPart webbing10;
    public final ModelPart webbing06;
    public final ModelPart webbing07;
    public final ModelPart bResinRibCenter;
    public final ModelPart bResinRib02_2;
    public final ModelPart bResinRib02_1;
    public final ModelPart bResinRib02;
    public final ModelPart bResinRib03;
    public final ModelPart wResinRipCenter;
    public final ModelPart wResinRibBottom02;
    public final ModelPart wResinRibTop02;
    public final ModelPart wResinRibBottom01;
    public final ModelPart wTopRoot01;
    public final ModelPart wTopRoot03;
    public final ModelPart wTopRoot04;
    public final ModelPart wTopRoot02;
    public final ModelPart wResinRibTop01;
    public final ModelPart wBottomRoot01;
    public final ModelPart wBottomRoot02;
    public final ModelPart wBottomRoot03;
    public final ModelPart webbing15;
    public final ModelPart webbing02;
    public final ModelPart webbing01;
    public final ModelPart webbing08;
    public final ModelPart webbing04;
    public final ModelPart webbing14;
    public final ModelPart sResinRipCenter;
    public final ModelPart sTopRoot01;
    public final ModelPart sTopRoot02;
    public final ModelPart sTopRoot03;
    public final ModelPart sResinRibBottom01;
    public final ModelPart sResinRibTop02;
    public final ModelPart sResinRibTop01;
    public final ModelPart sBottomRoot01;
    public final ModelPart sBottomRoot03;
    public final ModelPart sBottomRoot02;
    public final ModelPart sResinRibBottom02;
    public final ModelPart webbing11;
    public final ModelPart webbing12;
    public final ModelPart eResinRipCenter;
    public final ModelPart eResinRibBottom01;
    public final ModelPart eResinRibTop01;
    public final ModelPart eBottomRoot01;
    public final ModelPart eBottomRoot02;
    public final ModelPart eResinRibBottom02;
    public final ModelPart eResinRibTop02;
    public final ModelPart eTopRoot01;
    public final ModelPart eTopRoot02;
    public final ModelPart eTopRoot03;
    public final ModelPart blockFaceTop;
    public final ModelPart blockFaceSouth;
    public final ModelPart blockFaceBottom;
    public final ModelPart blockFaceNorth;
    public final ModelPart blockFaceEast;
    public final ModelPart blockFaceWest;

    public HiveResinModel(ModelPart root)
    {
        this.root = root;
        this.webbing05 = root.getChild("webbing05");
        this.parts.put("webbing05", this.webbing05);
        this.webbing09 = root.getChild("webbing09");
        this.parts.put("webbing09", this.webbing09);
        this.webbing03 = root.getChild("webbing03");
        this.parts.put("webbing03", this.webbing03);
        this.webbing13 = root.getChild("webbing13");
        this.parts.put("webbing13", this.webbing13);
        this.nResinRipCenter = root.getChild("nResinRipCenter");
        this.parts.put("nResinRipCenter", this.nResinRipCenter);
        this.nResinRibBottom01 = this.nResinRipCenter.getChild("nResinRibBottom01");
        this.parts.put("nResinRibBottom01", this.nResinRibBottom01);
        this.nTopRoot01 = this.nResinRipCenter.getChild("nTopRoot01");
        this.parts.put("nTopRoot01", this.nTopRoot01);
        this.nTopRoot03 = this.nTopRoot01.getChild("nTopRoot03");
        this.parts.put("nTopRoot03", this.nTopRoot03);
        this.nTopRoot02 = this.nTopRoot01.getChild("nTopRoot02");
        this.parts.put("nTopRoot02", this.nTopRoot02);
        this.nResinRibTop02 = this.nResinRipCenter.getChild("nResinRibTop02");
        this.parts.put("nResinRibTop02", this.nResinRibTop02);
        this.nResinRibBottom02 = this.nResinRipCenter.getChild("nResinRibBottom02");
        this.parts.put("nResinRibBottom02", this.nResinRibBottom02);
        this.nResinRibTop01 = this.nResinRipCenter.getChild("nResinRibTop01");
        this.parts.put("nResinRibTop01", this.nResinRibTop01);
        this.nBottomRoot01 = this.nResinRipCenter.getChild("nBottomRoot01");
        this.parts.put("nBottomRoot01", this.nBottomRoot01);
        this.nBottomRoot02 = this.nBottomRoot01.getChild("nBottomRoot02");
        this.parts.put("nBottomRoot02", this.nBottomRoot02);
        this.webbing10 = root.getChild("webbing10");
        this.parts.put("webbing10", this.webbing10);
        this.webbing06 = root.getChild("webbing06");
        this.parts.put("webbing06", this.webbing06);
        this.webbing07 = root.getChild("webbing07");
        this.parts.put("webbing07", this.webbing07);
        this.bResinRibCenter = root.getChild("bResinRibCenter");
        this.parts.put("bResinRibCenter", this.bResinRibCenter);
        this.bResinRib02_2 = this.bResinRibCenter.getChild("bResinRib02_2");
        this.parts.put("bResinRib02_2", this.bResinRib02_2);
        this.bResinRib02_1 = this.bResinRibCenter.getChild("bResinRib02_1");
        this.parts.put("bResinRib02_1", this.bResinRib02_1);
        this.bResinRib02 = this.bResinRibCenter.getChild("bResinRib02");
        this.parts.put("bResinRib02", this.bResinRib02);
        this.bResinRib03 = this.bResinRibCenter.getChild("bResinRib03");
        this.parts.put("bResinRib03", this.bResinRib03);
        this.wResinRipCenter = root.getChild("wResinRipCenter");
        this.parts.put("wResinRipCenter", this.wResinRipCenter);
        this.wResinRibBottom02 = this.wResinRipCenter.getChild("wResinRibBottom02");
        this.parts.put("wResinRibBottom02", this.wResinRibBottom02);
        this.wResinRibTop02 = this.wResinRipCenter.getChild("wResinRibTop02");
        this.parts.put("wResinRibTop02", this.wResinRibTop02);
        this.wResinRibBottom01 = this.wResinRipCenter.getChild("wResinRibBottom01");
        this.parts.put("wResinRibBottom01", this.wResinRibBottom01);
        this.wTopRoot01 = this.wResinRipCenter.getChild("wTopRoot01");
        this.parts.put("wTopRoot01", this.wTopRoot01);
        this.wTopRoot03 = this.wTopRoot01.getChild("wTopRoot03");
        this.parts.put("wTopRoot03", this.wTopRoot03);
        this.wTopRoot04 = this.wTopRoot01.getChild("wTopRoot04");
        this.parts.put("wTopRoot04", this.wTopRoot04);
        this.wTopRoot02 = this.wTopRoot01.getChild("wTopRoot02");
        this.parts.put("wTopRoot02", this.wTopRoot02);
        this.wResinRibTop01 = this.wResinRipCenter.getChild("wResinRibTop01");
        this.parts.put("wResinRibTop01", this.wResinRibTop01);
        this.wBottomRoot01 = this.wResinRipCenter.getChild("wBottomRoot01");
        this.parts.put("wBottomRoot01", this.wBottomRoot01);
        this.wBottomRoot02 = this.wBottomRoot01.getChild("wBottomRoot02");
        this.parts.put("wBottomRoot02", this.wBottomRoot02);
        this.wBottomRoot03 = this.wBottomRoot01.getChild("wBottomRoot03");
        this.parts.put("wBottomRoot03", this.wBottomRoot03);
        this.webbing15 = root.getChild("webbing15");
        this.parts.put("webbing15", this.webbing15);
        this.webbing02 = root.getChild("webbing02");
        this.parts.put("webbing02", this.webbing02);
        this.webbing01 = root.getChild("webbing01");
        this.parts.put("webbing01", this.webbing01);
        this.webbing08 = root.getChild("webbing08");
        this.parts.put("webbing08", this.webbing08);
        this.webbing04 = root.getChild("webbing04");
        this.parts.put("webbing04", this.webbing04);
        this.webbing14 = root.getChild("webbing14");
        this.parts.put("webbing14", this.webbing14);
        this.sResinRipCenter = root.getChild("sResinRipCenter");
        this.parts.put("sResinRipCenter", this.sResinRipCenter);
        this.sTopRoot01 = this.sResinRipCenter.getChild("sTopRoot01");
        this.parts.put("sTopRoot01", this.sTopRoot01);
        this.sTopRoot02 = this.sTopRoot01.getChild("sTopRoot02");
        this.parts.put("sTopRoot02", this.sTopRoot02);
        this.sTopRoot03 = this.sTopRoot01.getChild("sTopRoot03");
        this.parts.put("sTopRoot03", this.sTopRoot03);
        this.sResinRibBottom01 = this.sResinRipCenter.getChild("sResinRibBottom01");
        this.parts.put("sResinRibBottom01", this.sResinRibBottom01);
        this.sResinRibTop02 = this.sResinRipCenter.getChild("sResinRibTop02");
        this.parts.put("sResinRibTop02", this.sResinRibTop02);
        this.sResinRibTop01 = this.sResinRipCenter.getChild("sResinRibTop01");
        this.parts.put("sResinRibTop01", this.sResinRibTop01);
        this.sBottomRoot01 = this.sResinRipCenter.getChild("sBottomRoot01");
        this.parts.put("sBottomRoot01", this.sBottomRoot01);
        this.sBottomRoot03 = this.sBottomRoot01.getChild("sBottomRoot03");
        this.parts.put("sBottomRoot03", this.sBottomRoot03);
        this.sBottomRoot02 = this.sBottomRoot01.getChild("sBottomRoot02");
        this.parts.put("sBottomRoot02", this.sBottomRoot02);
        this.sResinRibBottom02 = this.sResinRipCenter.getChild("sResinRibBottom02");
        this.parts.put("sResinRibBottom02", this.sResinRibBottom02);
        this.webbing11 = root.getChild("webbing11");
        this.parts.put("webbing11", this.webbing11);
        this.webbing12 = root.getChild("webbing12");
        this.parts.put("webbing12", this.webbing12);
        this.eResinRipCenter = root.getChild("eResinRipCenter");
        this.parts.put("eResinRipCenter", this.eResinRipCenter);
        this.eResinRibBottom01 = this.eResinRipCenter.getChild("eResinRibBottom01");
        this.parts.put("eResinRibBottom01", this.eResinRibBottom01);
        this.eResinRibTop01 = this.eResinRipCenter.getChild("eResinRibTop01");
        this.parts.put("eResinRibTop01", this.eResinRibTop01);
        this.eBottomRoot01 = this.eResinRipCenter.getChild("eBottomRoot01");
        this.parts.put("eBottomRoot01", this.eBottomRoot01);
        this.eBottomRoot02 = this.eBottomRoot01.getChild("eBottomRoot02");
        this.parts.put("eBottomRoot02", this.eBottomRoot02);
        this.eResinRibBottom02 = this.eResinRipCenter.getChild("eResinRibBottom02");
        this.parts.put("eResinRibBottom02", this.eResinRibBottom02);
        this.eResinRibTop02 = this.eResinRipCenter.getChild("eResinRibTop02");
        this.parts.put("eResinRibTop02", this.eResinRibTop02);
        this.eTopRoot01 = this.eResinRipCenter.getChild("eTopRoot01");
        this.parts.put("eTopRoot01", this.eTopRoot01);
        this.eTopRoot02 = this.eTopRoot01.getChild("eTopRoot02");
        this.parts.put("eTopRoot02", this.eTopRoot02);
        this.eTopRoot03 = this.eTopRoot01.getChild("eTopRoot03");
        this.parts.put("eTopRoot03", this.eTopRoot03);
        this.blockFaceTop = root.getChild("blockFaceTop");
        this.parts.put("blockFaceTop", this.blockFaceTop);
        this.blockFaceSouth = root.getChild("blockFaceSouth");
        this.parts.put("blockFaceSouth", this.blockFaceSouth);
        this.blockFaceBottom = root.getChild("blockFaceBottom");
        this.parts.put("blockFaceBottom", this.blockFaceBottom);
        this.blockFaceNorth = root.getChild("blockFaceNorth");
        this.parts.put("blockFaceNorth", this.blockFaceNorth);
        this.blockFaceEast = root.getChild("blockFaceEast");
        this.parts.put("blockFaceEast", this.blockFaceEast);
        this.blockFaceWest = root.getChild("blockFaceWest");
        this.parts.put("blockFaceWest", this.blockFaceWest);
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
        PartDefinition p_webbing05 = root.addOrReplaceChild("webbing05",
                CubeListBuilder.create().texOffs(102, 45).addBox(-10.4F, 11.0F, -3.3F, 0.0F, 12.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.4553564018453205F, 0.0F));
        PartDefinition p_webbing09 = root.addOrReplaceChild("webbing09",
                CubeListBuilder.create().texOffs(88, 48).mirror().addBox(10.3F, 14.5F, -1.2F, 0.0F, 9.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.2292353921796064F, 0.0F));
        PartDefinition p_webbing03 = root.addOrReplaceChild("webbing03",
                CubeListBuilder.create().texOffs(0, 7).mirror().addBox(-1.5F, 16.9F, 10.6F, 5.0F, 7.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.40980330836826856F, 0.0F));
        PartDefinition p_webbing13 = root.addOrReplaceChild("webbing13",
                CubeListBuilder.create().texOffs(88, 51).addBox(-10.1F, 8.3F, -1.9F, 0.0F, 7.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.1838568316277536F, 0.0F));
        PartDefinition p_nResinRipCenter = root.addOrReplaceChild("nResinRipCenter",
                CubeListBuilder.create().texOffs(0, 36).addBox(-2.0F, -2.5F, -2.3F, 4.0F, 5.0F, 3.0F),
                PartPose.offset(0.0F, 16.0F, -7.8F));
        PartDefinition p_nResinRibBottom01 = p_nResinRipCenter.addOrReplaceChild("nResinRibBottom01",
                CubeListBuilder.create().texOffs(0, 48).addBox(-2.5F, 2.3F, -2.3F, 5.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_nTopRoot01 = p_nResinRipCenter.addOrReplaceChild("nTopRoot01",
                CubeListBuilder.create().texOffs(55, 0).addBox(-2.0F, -0.4F, -5.1F, 4.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -7.2F, 0.0F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_nTopRoot03 = p_nTopRoot01.addOrReplaceChild("nTopRoot03",
                CubeListBuilder.create().texOffs(80, 0).addBox(1.2F, -0.4F, -9.4F, 1.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6829473363053812F, 0.0F));
        PartDefinition p_nTopRoot02 = p_nTopRoot01.addOrReplaceChild("nTopRoot02",
                CubeListBuilder.create().texOffs(55, 4).addBox(-1.2F, -0.4F, -8.0F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.22759093446006054F, 0.0F));
        PartDefinition p_nResinRibTop02 = p_nResinRipCenter.addOrReplaceChild("nResinRibTop02",
                CubeListBuilder.create().texOffs(0, 55).addBox(-3.0F, -8.1F, -3.0F, 6.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_nResinRibBottom02 = p_nResinRipCenter.addOrReplaceChild("nResinRibBottom02",
                CubeListBuilder.create().texOffs(0, 55).addBox(-3.0F, 5.1F, -3.2F, 6.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_nResinRibTop01 = p_nResinRipCenter.addOrReplaceChild("nResinRibTop01",
                CubeListBuilder.create().texOffs(0, 48).addBox(-2.5F, -5.2F, -2.3F, 5.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_nBottomRoot01 = p_nResinRipCenter.addOrReplaceChild("nBottomRoot01",
                CubeListBuilder.create().texOffs(55, 0).addBox(-2.0F, -0.4F, -5.3F, 4.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 6.1F, 0.0F, 0.091106186954104F, -0.136659280431156F, 0.0F));
        PartDefinition p_nBottomRoot02 = p_nBottomRoot01.addOrReplaceChild("nBottomRoot02",
                CubeListBuilder.create().texOffs(55, 4).addBox(-1.9F, 0.6F, -7.8F, 2.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.22759093446006054F, 0.0F));
        PartDefinition p_webbing10 = root.addOrReplaceChild("webbing10",
                CubeListBuilder.create().texOffs(100, 30).addBox(10.6F, 7.9F, -4.3F, 0.0F, 9.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.1383037381507017F, 0.0F));
        PartDefinition p_webbing06 = root.addOrReplaceChild("webbing06",
                CubeListBuilder.create().texOffs(102, 45).addBox(-10.5F, 12.0F, -3.6F, 0.0F, 12.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.1383037381507017F, 0.0F));
        PartDefinition p_webbing07 = root.addOrReplaceChild("webbing07",
                CubeListBuilder.create().texOffs(100, 30).addBox(10.2F, 13.1F, -6.1F, 0.0F, 10.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.9123572614101867F, 0.0F));
        PartDefinition p_bResinRibCenter = root.addOrReplaceChild("bResinRibCenter",
                CubeListBuilder.create().texOffs(66, 17).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 5.0F),
                PartPose.offset(0.0F, 23.0F, 0.0F));
        PartDefinition p_bResinRib02_2 = p_bResinRibCenter.addOrReplaceChild("bResinRib02_2",
                CubeListBuilder.create().texOffs(83, 26).addBox(-3.0F, -0.3F, 5.5F, 6.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_bResinRib02_1 = p_bResinRibCenter.addOrReplaceChild("bResinRib02_1",
                CubeListBuilder.create().texOffs(83, 26).addBox(-3.0F, -0.3F, -7.8F, 6.0F, 4.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_bResinRib02 = p_bResinRibCenter.addOrReplaceChild("bResinRib02",
                CubeListBuilder.create().texOffs(66, 26).addBox(-2.5F, 0.0F, -4.9F, 5.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_bResinRib03 = p_bResinRibCenter.addOrReplaceChild("bResinRib03",
                CubeListBuilder.create().texOffs(66, 26).addBox(-2.5F, 0.0F, 2.7F, 5.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_wResinRipCenter = root.addOrReplaceChild("wResinRipCenter",
                CubeListBuilder.create().texOffs(66, 33).addBox(-3.0F, -3.0F, -1.8F, 3.0F, 5.0F, 4.0F),
                PartPose.offset(-7.2F, 16.8F, 0.0F));
        PartDefinition p_wResinRibBottom02 = p_wResinRipCenter.addOrReplaceChild("wResinRibBottom02",
                CubeListBuilder.create().texOffs(66, 55).addBox(-3.8F, 4.3F, -3.0F, 4.0F, 3.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.045553093477052F));
        PartDefinition p_wResinRibTop02 = p_wResinRipCenter.addOrReplaceChild("wResinRibTop02",
                CubeListBuilder.create().texOffs(66, 55).addBox(-3.8F, -8.9F, -3.0F, 4.0F, 3.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.045553093477052F));
        PartDefinition p_wResinRibBottom01 = p_wResinRipCenter.addOrReplaceChild("wResinRibBottom01",
                CubeListBuilder.create().texOffs(66, 44).addBox(-2.9F, 2.0F, -2.5F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.091106186954104F));
        PartDefinition p_wTopRoot01 = p_wResinRipCenter.addOrReplaceChild("wTopRoot01",
                CubeListBuilder.create().texOffs(100, 7).addBox(-6.0F, -0.7F, -2.0F, 2.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, -8.0F, 0.0F));
        PartDefinition p_wTopRoot03 = p_wTopRoot01.addOrReplaceChild("wTopRoot03",
                CubeListBuilder.create().texOffs(99, 0).addBox(-10.5F, -0.7F, 4.2F, 4.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.8196066167365371F, 0.0F));
        PartDefinition p_wTopRoot04 = p_wTopRoot01.addOrReplaceChild("wTopRoot04",
                CubeListBuilder.create().texOffs(80, 0).addBox(-10.0F, -0.8F, -10.0F, 1.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.27314402793711257F, 0.0F));
        PartDefinition p_wTopRoot02 = p_wTopRoot01.addOrReplaceChild("wTopRoot02",
                CubeListBuilder.create().texOffs(99, 0).addBox(-9.0F, -0.7F, 0.6F, 4.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.36425021489121656F, 0.0F));
        PartDefinition p_wResinRibTop01 = p_wResinRipCenter.addOrReplaceChild("wResinRibTop01",
                CubeListBuilder.create().texOffs(66, 44).addBox(-2.9F, -6.1F, -2.5F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.091106186954104F));
        PartDefinition p_wBottomRoot01 = p_wResinRipCenter.addOrReplaceChild("wBottomRoot01",
                CubeListBuilder.create().texOffs(100, 7).addBox(-6.0F, 0.2F, -2.0F, 2.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, 5.0F, 0.0F));
        PartDefinition p_wBottomRoot02 = p_wBottomRoot01.addOrReplaceChild("wBottomRoot02",
                CubeListBuilder.create().texOffs(80, 0).addBox(-0.7F, 1.2F, -10.1F, 1.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.2292353921796064F, 0.0F));
        PartDefinition p_wBottomRoot03 = p_wBottomRoot01.addOrReplaceChild("wBottomRoot03",
                CubeListBuilder.create().texOffs(80, 0).addBox(-3.4F, 1.2F, -10.3F, 1.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.3203415791337103F, 0.0F));
        PartDefinition p_webbing15 = root.addOrReplaceChild("webbing15",
                CubeListBuilder.create().texOffs(0, 7).mirror().addBox(-2.9F, 14.5F, -10.9F, 5.0F, 7.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_webbing02 = root.addOrReplaceChild("webbing02",
                CubeListBuilder.create().texOffs(99, 30).addBox(7.9F, 11.0F, -5.0F, 3.0F, 10.0F, 9.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_webbing01 = root.addOrReplaceChild("webbing01",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 10.0F, -10.1F, 6.0F, 6.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.0F));
        PartDefinition p_webbing08 = root.addOrReplaceChild("webbing08",
                CubeListBuilder.create().texOffs(96, 45).addBox(10.4F, 11.5F, -1.3F, 0.0F, 12.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.36425021489121656F, 0.0F));
        PartDefinition p_webbing04 = root.addOrReplaceChild("webbing04",
                CubeListBuilder.create().texOffs(88, 48).addBox(-10.9F, 8.6F, -2.8F, 0.0F, 9.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.31869712141416456F, 0.0F));
        PartDefinition p_webbing14 = root.addOrReplaceChild("webbing14",
                CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.1F, 9.2F, 10.9F, 6.0F, 6.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_sResinRipCenter = root.addOrReplaceChild("sResinRipCenter",
                CubeListBuilder.create().texOffs(20, 36).addBox(-2.0F, -2.5F, -0.3F, 4.0F, 5.0F, 3.0F),
                PartPose.offset(0.0F, 16.0F, 7.5F));
        PartDefinition p_sTopRoot01 = p_sResinRipCenter.addOrReplaceChild("sTopRoot01",
                CubeListBuilder.create().texOffs(55, 0).addBox(-2.0F, -0.4F, 3.8F, 4.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -7.2F, 0.0F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_sTopRoot02 = p_sTopRoot01.addOrReplaceChild("sTopRoot02",
                CubeListBuilder.create().texOffs(82, 7).addBox(-1.5F, -0.4F, 5.0F, 3.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_sTopRoot03 = p_sTopRoot01.addOrReplaceChild("sTopRoot03",
                CubeListBuilder.create().texOffs(55, 4).addBox(-1.0F, -0.2F, 9.0F, 2.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_sResinRibBottom01 = p_sResinRipCenter.addOrReplaceChild("sResinRibBottom01",
                CubeListBuilder.create().texOffs(20, 48).addBox(-2.5F, 2.3F, -0.4F, 5.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_sResinRibTop02 = p_sResinRipCenter.addOrReplaceChild("sResinRibTop02",
                CubeListBuilder.create().texOffs(22, 55).addBox(-3.0F, -8.1F, -0.4F, 6.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_sResinRibTop01 = p_sResinRipCenter.addOrReplaceChild("sResinRibTop01",
                CubeListBuilder.create().texOffs(20, 48).addBox(-2.5F, -5.3F, -0.3F, 5.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_sBottomRoot01 = p_sResinRipCenter.addOrReplaceChild("sBottomRoot01",
                CubeListBuilder.create().texOffs(55, 0).addBox(-2.0F, -0.4F, 3.8F, 4.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 6.1F, 0.0F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_sBottomRoot03 = p_sBottomRoot01.addOrReplaceChild("sBottomRoot03",
                CubeListBuilder.create().texOffs(80, 0).addBox(-9.0F, 0.6F, 4.8F, 1.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.2292353921796064F, 0.0F));
        PartDefinition p_sBottomRoot02 = p_sBottomRoot01.addOrReplaceChild("sBottomRoot02",
                CubeListBuilder.create().texOffs(55, 9).addBox(-3.0F, 0.6F, 4.9F, 2.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.40980330836826856F, 0.0F));
        PartDefinition p_sResinRibBottom02 = p_sResinRipCenter.addOrReplaceChild("sResinRibBottom02",
                CubeListBuilder.create().texOffs(22, 55).addBox(-3.0F, 5.1F, -0.5F, 6.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_webbing11 = root.addOrReplaceChild("webbing11",
                CubeListBuilder.create().texOffs(0, 7).mirror().addBox(-0.4F, 17.0F, 10.3F, 5.0F, 7.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.2292353921796064F, 0.0F));
        PartDefinition p_webbing12 = root.addOrReplaceChild("webbing12",
                CubeListBuilder.create().texOffs(88, 48).addBox(9.9F, 15.1F, -5.7F, 0.0F, 9.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.27314402793711257F, 0.0F));
        PartDefinition p_eResinRipCenter = root.addOrReplaceChild("eResinRipCenter",
                CubeListBuilder.create().texOffs(50, 33).addBox(0.0F, -3.0F, -2.0F, 3.0F, 5.0F, 4.0F),
                PartPose.offset(7.2F, 16.8F, 0.0F));
        PartDefinition p_eResinRibBottom01 = p_eResinRipCenter.addOrReplaceChild("eResinRibBottom01",
                CubeListBuilder.create().texOffs(48, 44).addBox(-0.1F, 2.0F, -2.5F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.091106186954104F));
        PartDefinition p_eResinRibTop01 = p_eResinRipCenter.addOrReplaceChild("eResinRibTop01",
                CubeListBuilder.create().texOffs(48, 44).addBox(-0.1F, -6.1F, -2.5F, 3.0F, 3.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.091106186954104F));
        PartDefinition p_eBottomRoot01 = p_eResinRipCenter.addOrReplaceChild("eBottomRoot01",
                CubeListBuilder.create().texOffs(100, 7).addBox(4.0F, 0.2F, -2.0F, 2.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, 5.0F, 0.0F));
        PartDefinition p_eBottomRoot02 = p_eBottomRoot01.addOrReplaceChild("eBottomRoot02",
                CubeListBuilder.create().texOffs(99, 0).addBox(5.8F, 1.2F, -1.5F, 4.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_eResinRibBottom02 = p_eResinRipCenter.addOrReplaceChild("eResinRibBottom02",
                CubeListBuilder.create().texOffs(45, 54).addBox(-0.2F, 4.3F, -3.0F, 4.0F, 3.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.045553093477052F));
        PartDefinition p_eResinRibTop02 = p_eResinRipCenter.addOrReplaceChild("eResinRibTop02",
                CubeListBuilder.create().texOffs(45, 54).addBox(-0.2F, -8.9F, -3.0F, 4.0F, 3.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.045553093477052F));
        PartDefinition p_eTopRoot01 = p_eResinRipCenter.addOrReplaceChild("eTopRoot01",
                CubeListBuilder.create().texOffs(100, 7).addBox(4.0F, -0.7F, -2.0F, 2.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, -8.0F, 0.0F));
        PartDefinition p_eTopRoot02 = p_eTopRoot01.addOrReplaceChild("eTopRoot02",
                CubeListBuilder.create().texOffs(99, 0).addBox(5.3F, -0.8F, -3.1F, 4.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.0F));
        PartDefinition p_eTopRoot03 = p_eTopRoot01.addOrReplaceChild("eTopRoot03",
                CubeListBuilder.create().texOffs(80, 0).addBox(7.6F, -0.8F, 3.3F, 1.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.36425021489121656F, 0.0F));
        PartDefinition p_blockFaceTop = root.addOrReplaceChild("blockFaceTop",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 8.0F, -8.0F, 16.0F, 0.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_blockFaceSouth = root.addOrReplaceChild("blockFaceSouth",
                CubeListBuilder.create().texOffs(0, 17).addBox(-8.0F, 8.0F, 8.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_blockFaceBottom = root.addOrReplaceChild("blockFaceBottom",
                CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 24.0F, -8.0F, 16.0F, 0.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_blockFaceNorth = root.addOrReplaceChild("blockFaceNorth",
                CubeListBuilder.create().texOffs(0, 17).addBox(-8.0F, 8.0F, -8.0F, 16.0F, 16.0F, 0.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_blockFaceEast = root.addOrReplaceChild("blockFaceEast",
                CubeListBuilder.create().texOffs(0, 1).addBox(8.0F, 8.0F, -8.0F, 0.0F, 16.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_blockFaceWest = root.addOrReplaceChild("blockFaceWest",
                CubeListBuilder.create().texOffs(0, 1).addBox(-8.0F, 8.0F, -8.0F, 0.0F, 16.0F, 16.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
