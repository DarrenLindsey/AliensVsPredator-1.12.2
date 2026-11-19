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
 * Geometry converted automatically from the 1.12.2 ModelTrilobite by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class TrilobiteModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "trilobite"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart smallToobBase;
    public final ModelPart lFrontSmallTube01;
    public final ModelPart lFrontSmallTube02;
    public final ModelPart lFrontSmallTube03;
    public final ModelPart lFrontSmallTube04;
    public final ModelPart lFrontSmallTube05;
    public final ModelPart lFrontSmallTube06;
    public final ModelPart lFrontSmallTube07;
    public final ModelPart lFrontSmallTube08;
    public final ModelPart lFrontSmallTube09;
    public final ModelPart lFrontSmallTube10;
    public final ModelPart lFrontSmallTube11;
    public final ModelPart lFrontSmallTube12;
    public final ModelPart rFrontSmallTube01;
    public final ModelPart rFrontSmallTube02;
    public final ModelPart rFrontSmallTube03;
    public final ModelPart rFrontSmallTube04;
    public final ModelPart rFrontSmallTube05;
    public final ModelPart rFrontSmallTube06;
    public final ModelPart rFrontSmallTube07;
    public final ModelPart rFrontSmallTube08;
    public final ModelPart rFrontSmallTube09;
    public final ModelPart rFrontSmallTube10;
    public final ModelPart rFrontSmallTube11;
    public final ModelPart rFrontSmallTube12;
    public final ModelPart rMiddleSmallTube01;
    public final ModelPart rMiddleSmallTube02;
    public final ModelPart rMiddleSmallTube03;
    public final ModelPart rMiddleSmallTube04;
    public final ModelPart rMiddleSmallTube05;
    public final ModelPart rMiddleSmallTube06;
    public final ModelPart rMiddleSmallTube07;
    public final ModelPart rMiddleSmallTube08;
    public final ModelPart rMiddleSmallTube09;
    public final ModelPart rMiddleSmallTube10;
    public final ModelPart rMiddleSmallTube11;
    public final ModelPart rMiddleSmallTube12;
    public final ModelPart tailSmallTube01;
    public final ModelPart tailSmallTube02;
    public final ModelPart tailSmallTube03;
    public final ModelPart tailSmallTube04;
    public final ModelPart tailSmallTube05;
    public final ModelPart tailSmallTube06;
    public final ModelPart tailSmallTube07;
    public final ModelPart tailSmallTube08;
    public final ModelPart tailSmallTube09;
    public final ModelPart tailSmallTube10;
    public final ModelPart tailSmallTube11;
    public final ModelPart tailSmallTube12;
    public final ModelPart rBackSmallTube01;
    public final ModelPart rBackSmallTube02;
    public final ModelPart rBackSmallTube03;
    public final ModelPart rBackSmallTube04;
    public final ModelPart rBackSmallTube05;
    public final ModelPart rBackSmallTube06;
    public final ModelPart rBackSmallTube07;
    public final ModelPart rBackSmallTube08;
    public final ModelPart rBackSmallTube09;
    public final ModelPart rBackSmallTube10;
    public final ModelPart rBackSmallTube11;
    public final ModelPart rBackSmallTube12;
    public final ModelPart lBackSmallTube01;
    public final ModelPart lBackSmallTube02;
    public final ModelPart lBackSmallTube03;
    public final ModelPart lBackSmallTube04;
    public final ModelPart lBackSmallTube05;
    public final ModelPart lBackSmallTube06;
    public final ModelPart lBackSmallTube07;
    public final ModelPart lBackSmallTube08;
    public final ModelPart lBackSmallTube09;
    public final ModelPart lBackSmallTube10;
    public final ModelPart lBackSmallTube11;
    public final ModelPart lBackSmallTube12;
    public final ModelPart lMiddleSmallTube01;
    public final ModelPart lMiddleSmallTube02;
    public final ModelPart lMiddleSmallTube03;
    public final ModelPart lMiddleSmallTube04;
    public final ModelPart lMiddleSmallTube05;
    public final ModelPart lMiddleSmallTube06;
    public final ModelPart lMiddleSmallTube07;
    public final ModelPart lMiddleSmallTube08;
    public final ModelPart lMiddleSmallTube09;
    public final ModelPart lMiddleSmallTube10;
    public final ModelPart lMiddleSmallTube11;
    public final ModelPart lMiddleSmallTube12;
    public final ModelPart body;
    public final ModelPart rMiddleTenticle1;
    public final ModelPart rMiddleTenticle2;
    public final ModelPart rMiddleTenticle3;
    public final ModelPart rMiddleTenticle4;
    public final ModelPart rMiddleTenticle5;
    public final ModelPart rMiddleClaw;
    public final ModelPart lBackPetal;
    public final ModelPart lMiddleTenticle1;
    public final ModelPart lMiddleTenticle2;
    public final ModelPart lMiddleTenticle3;
    public final ModelPart lMiddleTenticle4;
    public final ModelPart lMiddleTenticle5;
    public final ModelPart lMiddleClaw;
    public final ModelPart lMiddlePetal;
    public final ModelPart rBackPetal;
    public final ModelPart lJaw;
    public final ModelPart rFrontTenticle1;
    public final ModelPart rFrontTenticle2;
    public final ModelPart rFrontTenticle3;
    public final ModelPart rFrontTenticle4;
    public final ModelPart rFrontTenticle5;
    public final ModelPart rFrontClaw;
    public final ModelPart rMiddlePetal;
    public final ModelPart lowerBody;
    public final ModelPart rBackTenticle1;
    public final ModelPart rBackTenticle2;
    public final ModelPart rBackTenticle3;
    public final ModelPart rBackTenticle4;
    public final ModelPart rBackTenticle5;
    public final ModelPart rBackClaw;
    public final ModelPart lBackTenticle1;
    public final ModelPart lBackTenticle2;
    public final ModelPart lBackTenticle3;
    public final ModelPart lBackTenticle4;
    public final ModelPart lBackTenticle5;
    public final ModelPart lBackClaw;
    public final ModelPart tail;
    public final ModelPart tail2;
    public final ModelPart tail3;
    public final ModelPart tail4;
    public final ModelPart tail5;
    public final ModelPart tailClaw;
    public final ModelPart tailPetal;
    public final ModelPart rFrontPetal;
    public final ModelPart lFrontTenticle1;
    public final ModelPart lFrontTenticle2;
    public final ModelPart lFrontTenticle3;
    public final ModelPart lFrontTenticle4;
    public final ModelPart lFrontTenticle5;
    public final ModelPart lFrontClaw;
    public final ModelPart tube01;
    public final ModelPart tube02;
    public final ModelPart tube03;
    public final ModelPart tube04;
    public final ModelPart tube05;
    public final ModelPart tube06;
    public final ModelPart tube07;
    public final ModelPart tube08;
    public final ModelPart tube09;
    public final ModelPart tube10;
    public final ModelPart tube11;
    public final ModelPart tube12;
    public final ModelPart rJaw;
    public final ModelPart lFrontPetal;

    public TrilobiteModel(ModelPart root)
    {
        this.root = root;
        this.smallToobBase = root.getChild("smallToobBase");
        this.parts.put("smallToobBase", this.smallToobBase);
        this.lFrontSmallTube01 = this.smallToobBase.getChild("lFrontSmallTube01");
        this.parts.put("lFrontSmallTube01", this.lFrontSmallTube01);
        this.lFrontSmallTube02 = this.lFrontSmallTube01.getChild("lFrontSmallTube02");
        this.parts.put("lFrontSmallTube02", this.lFrontSmallTube02);
        this.lFrontSmallTube03 = this.lFrontSmallTube02.getChild("lFrontSmallTube03");
        this.parts.put("lFrontSmallTube03", this.lFrontSmallTube03);
        this.lFrontSmallTube04 = this.lFrontSmallTube03.getChild("lFrontSmallTube04");
        this.parts.put("lFrontSmallTube04", this.lFrontSmallTube04);
        this.lFrontSmallTube05 = this.lFrontSmallTube04.getChild("lFrontSmallTube05");
        this.parts.put("lFrontSmallTube05", this.lFrontSmallTube05);
        this.lFrontSmallTube06 = this.lFrontSmallTube05.getChild("lFrontSmallTube06");
        this.parts.put("lFrontSmallTube06", this.lFrontSmallTube06);
        this.lFrontSmallTube07 = this.lFrontSmallTube06.getChild("lFrontSmallTube07");
        this.parts.put("lFrontSmallTube07", this.lFrontSmallTube07);
        this.lFrontSmallTube08 = this.lFrontSmallTube07.getChild("lFrontSmallTube08");
        this.parts.put("lFrontSmallTube08", this.lFrontSmallTube08);
        this.lFrontSmallTube09 = this.lFrontSmallTube08.getChild("lFrontSmallTube09");
        this.parts.put("lFrontSmallTube09", this.lFrontSmallTube09);
        this.lFrontSmallTube10 = this.lFrontSmallTube09.getChild("lFrontSmallTube10");
        this.parts.put("lFrontSmallTube10", this.lFrontSmallTube10);
        this.lFrontSmallTube11 = this.lFrontSmallTube10.getChild("lFrontSmallTube11");
        this.parts.put("lFrontSmallTube11", this.lFrontSmallTube11);
        this.lFrontSmallTube12 = this.lFrontSmallTube11.getChild("lFrontSmallTube12");
        this.parts.put("lFrontSmallTube12", this.lFrontSmallTube12);
        this.rFrontSmallTube01 = this.smallToobBase.getChild("rFrontSmallTube01");
        this.parts.put("rFrontSmallTube01", this.rFrontSmallTube01);
        this.rFrontSmallTube02 = this.rFrontSmallTube01.getChild("rFrontSmallTube02");
        this.parts.put("rFrontSmallTube02", this.rFrontSmallTube02);
        this.rFrontSmallTube03 = this.rFrontSmallTube02.getChild("rFrontSmallTube03");
        this.parts.put("rFrontSmallTube03", this.rFrontSmallTube03);
        this.rFrontSmallTube04 = this.rFrontSmallTube03.getChild("rFrontSmallTube04");
        this.parts.put("rFrontSmallTube04", this.rFrontSmallTube04);
        this.rFrontSmallTube05 = this.rFrontSmallTube04.getChild("rFrontSmallTube05");
        this.parts.put("rFrontSmallTube05", this.rFrontSmallTube05);
        this.rFrontSmallTube06 = this.rFrontSmallTube05.getChild("rFrontSmallTube06");
        this.parts.put("rFrontSmallTube06", this.rFrontSmallTube06);
        this.rFrontSmallTube07 = this.rFrontSmallTube06.getChild("rFrontSmallTube07");
        this.parts.put("rFrontSmallTube07", this.rFrontSmallTube07);
        this.rFrontSmallTube08 = this.rFrontSmallTube07.getChild("rFrontSmallTube08");
        this.parts.put("rFrontSmallTube08", this.rFrontSmallTube08);
        this.rFrontSmallTube09 = this.rFrontSmallTube08.getChild("rFrontSmallTube09");
        this.parts.put("rFrontSmallTube09", this.rFrontSmallTube09);
        this.rFrontSmallTube10 = this.rFrontSmallTube09.getChild("rFrontSmallTube10");
        this.parts.put("rFrontSmallTube10", this.rFrontSmallTube10);
        this.rFrontSmallTube11 = this.rFrontSmallTube10.getChild("rFrontSmallTube11");
        this.parts.put("rFrontSmallTube11", this.rFrontSmallTube11);
        this.rFrontSmallTube12 = this.rFrontSmallTube11.getChild("rFrontSmallTube12");
        this.parts.put("rFrontSmallTube12", this.rFrontSmallTube12);
        this.rMiddleSmallTube01 = this.smallToobBase.getChild("rMiddleSmallTube01");
        this.parts.put("rMiddleSmallTube01", this.rMiddleSmallTube01);
        this.rMiddleSmallTube02 = this.rMiddleSmallTube01.getChild("rMiddleSmallTube02");
        this.parts.put("rMiddleSmallTube02", this.rMiddleSmallTube02);
        this.rMiddleSmallTube03 = this.rMiddleSmallTube02.getChild("rMiddleSmallTube03");
        this.parts.put("rMiddleSmallTube03", this.rMiddleSmallTube03);
        this.rMiddleSmallTube04 = this.rMiddleSmallTube03.getChild("rMiddleSmallTube04");
        this.parts.put("rMiddleSmallTube04", this.rMiddleSmallTube04);
        this.rMiddleSmallTube05 = this.rMiddleSmallTube04.getChild("rMiddleSmallTube05");
        this.parts.put("rMiddleSmallTube05", this.rMiddleSmallTube05);
        this.rMiddleSmallTube06 = this.rMiddleSmallTube05.getChild("rMiddleSmallTube06");
        this.parts.put("rMiddleSmallTube06", this.rMiddleSmallTube06);
        this.rMiddleSmallTube07 = this.rMiddleSmallTube06.getChild("rMiddleSmallTube07");
        this.parts.put("rMiddleSmallTube07", this.rMiddleSmallTube07);
        this.rMiddleSmallTube08 = this.rMiddleSmallTube07.getChild("rMiddleSmallTube08");
        this.parts.put("rMiddleSmallTube08", this.rMiddleSmallTube08);
        this.rMiddleSmallTube09 = this.rMiddleSmallTube08.getChild("rMiddleSmallTube09");
        this.parts.put("rMiddleSmallTube09", this.rMiddleSmallTube09);
        this.rMiddleSmallTube10 = this.rMiddleSmallTube09.getChild("rMiddleSmallTube10");
        this.parts.put("rMiddleSmallTube10", this.rMiddleSmallTube10);
        this.rMiddleSmallTube11 = this.rMiddleSmallTube10.getChild("rMiddleSmallTube11");
        this.parts.put("rMiddleSmallTube11", this.rMiddleSmallTube11);
        this.rMiddleSmallTube12 = this.rMiddleSmallTube11.getChild("rMiddleSmallTube12");
        this.parts.put("rMiddleSmallTube12", this.rMiddleSmallTube12);
        this.tailSmallTube01 = this.smallToobBase.getChild("tailSmallTube01");
        this.parts.put("tailSmallTube01", this.tailSmallTube01);
        this.tailSmallTube02 = this.tailSmallTube01.getChild("tailSmallTube02");
        this.parts.put("tailSmallTube02", this.tailSmallTube02);
        this.tailSmallTube03 = this.tailSmallTube02.getChild("tailSmallTube03");
        this.parts.put("tailSmallTube03", this.tailSmallTube03);
        this.tailSmallTube04 = this.tailSmallTube03.getChild("tailSmallTube04");
        this.parts.put("tailSmallTube04", this.tailSmallTube04);
        this.tailSmallTube05 = this.tailSmallTube04.getChild("tailSmallTube05");
        this.parts.put("tailSmallTube05", this.tailSmallTube05);
        this.tailSmallTube06 = this.tailSmallTube05.getChild("tailSmallTube06");
        this.parts.put("tailSmallTube06", this.tailSmallTube06);
        this.tailSmallTube07 = this.tailSmallTube06.getChild("tailSmallTube07");
        this.parts.put("tailSmallTube07", this.tailSmallTube07);
        this.tailSmallTube08 = this.tailSmallTube07.getChild("tailSmallTube08");
        this.parts.put("tailSmallTube08", this.tailSmallTube08);
        this.tailSmallTube09 = this.tailSmallTube08.getChild("tailSmallTube09");
        this.parts.put("tailSmallTube09", this.tailSmallTube09);
        this.tailSmallTube10 = this.tailSmallTube09.getChild("tailSmallTube10");
        this.parts.put("tailSmallTube10", this.tailSmallTube10);
        this.tailSmallTube11 = this.tailSmallTube10.getChild("tailSmallTube11");
        this.parts.put("tailSmallTube11", this.tailSmallTube11);
        this.tailSmallTube12 = this.tailSmallTube11.getChild("tailSmallTube12");
        this.parts.put("tailSmallTube12", this.tailSmallTube12);
        this.rBackSmallTube01 = this.smallToobBase.getChild("rBackSmallTube01");
        this.parts.put("rBackSmallTube01", this.rBackSmallTube01);
        this.rBackSmallTube02 = this.rBackSmallTube01.getChild("rBackSmallTube02");
        this.parts.put("rBackSmallTube02", this.rBackSmallTube02);
        this.rBackSmallTube03 = this.rBackSmallTube02.getChild("rBackSmallTube03");
        this.parts.put("rBackSmallTube03", this.rBackSmallTube03);
        this.rBackSmallTube04 = this.rBackSmallTube03.getChild("rBackSmallTube04");
        this.parts.put("rBackSmallTube04", this.rBackSmallTube04);
        this.rBackSmallTube05 = this.rBackSmallTube04.getChild("rBackSmallTube05");
        this.parts.put("rBackSmallTube05", this.rBackSmallTube05);
        this.rBackSmallTube06 = this.rBackSmallTube05.getChild("rBackSmallTube06");
        this.parts.put("rBackSmallTube06", this.rBackSmallTube06);
        this.rBackSmallTube07 = this.rBackSmallTube06.getChild("rBackSmallTube07");
        this.parts.put("rBackSmallTube07", this.rBackSmallTube07);
        this.rBackSmallTube08 = this.rBackSmallTube07.getChild("rBackSmallTube08");
        this.parts.put("rBackSmallTube08", this.rBackSmallTube08);
        this.rBackSmallTube09 = this.rBackSmallTube08.getChild("rBackSmallTube09");
        this.parts.put("rBackSmallTube09", this.rBackSmallTube09);
        this.rBackSmallTube10 = this.rBackSmallTube09.getChild("rBackSmallTube10");
        this.parts.put("rBackSmallTube10", this.rBackSmallTube10);
        this.rBackSmallTube11 = this.rBackSmallTube10.getChild("rBackSmallTube11");
        this.parts.put("rBackSmallTube11", this.rBackSmallTube11);
        this.rBackSmallTube12 = this.rBackSmallTube11.getChild("rBackSmallTube12");
        this.parts.put("rBackSmallTube12", this.rBackSmallTube12);
        this.lBackSmallTube01 = this.smallToobBase.getChild("lBackSmallTube01");
        this.parts.put("lBackSmallTube01", this.lBackSmallTube01);
        this.lBackSmallTube02 = this.lBackSmallTube01.getChild("lBackSmallTube02");
        this.parts.put("lBackSmallTube02", this.lBackSmallTube02);
        this.lBackSmallTube03 = this.lBackSmallTube02.getChild("lBackSmallTube03");
        this.parts.put("lBackSmallTube03", this.lBackSmallTube03);
        this.lBackSmallTube04 = this.lBackSmallTube03.getChild("lBackSmallTube04");
        this.parts.put("lBackSmallTube04", this.lBackSmallTube04);
        this.lBackSmallTube05 = this.lBackSmallTube04.getChild("lBackSmallTube05");
        this.parts.put("lBackSmallTube05", this.lBackSmallTube05);
        this.lBackSmallTube06 = this.lBackSmallTube05.getChild("lBackSmallTube06");
        this.parts.put("lBackSmallTube06", this.lBackSmallTube06);
        this.lBackSmallTube07 = this.lBackSmallTube06.getChild("lBackSmallTube07");
        this.parts.put("lBackSmallTube07", this.lBackSmallTube07);
        this.lBackSmallTube08 = this.lBackSmallTube07.getChild("lBackSmallTube08");
        this.parts.put("lBackSmallTube08", this.lBackSmallTube08);
        this.lBackSmallTube09 = this.lBackSmallTube08.getChild("lBackSmallTube09");
        this.parts.put("lBackSmallTube09", this.lBackSmallTube09);
        this.lBackSmallTube10 = this.lBackSmallTube09.getChild("lBackSmallTube10");
        this.parts.put("lBackSmallTube10", this.lBackSmallTube10);
        this.lBackSmallTube11 = this.lBackSmallTube10.getChild("lBackSmallTube11");
        this.parts.put("lBackSmallTube11", this.lBackSmallTube11);
        this.lBackSmallTube12 = this.lBackSmallTube11.getChild("lBackSmallTube12");
        this.parts.put("lBackSmallTube12", this.lBackSmallTube12);
        this.lMiddleSmallTube01 = this.smallToobBase.getChild("lMiddleSmallTube01");
        this.parts.put("lMiddleSmallTube01", this.lMiddleSmallTube01);
        this.lMiddleSmallTube02 = this.lMiddleSmallTube01.getChild("lMiddleSmallTube02");
        this.parts.put("lMiddleSmallTube02", this.lMiddleSmallTube02);
        this.lMiddleSmallTube03 = this.lMiddleSmallTube02.getChild("lMiddleSmallTube03");
        this.parts.put("lMiddleSmallTube03", this.lMiddleSmallTube03);
        this.lMiddleSmallTube04 = this.lMiddleSmallTube03.getChild("lMiddleSmallTube04");
        this.parts.put("lMiddleSmallTube04", this.lMiddleSmallTube04);
        this.lMiddleSmallTube05 = this.lMiddleSmallTube04.getChild("lMiddleSmallTube05");
        this.parts.put("lMiddleSmallTube05", this.lMiddleSmallTube05);
        this.lMiddleSmallTube06 = this.lMiddleSmallTube05.getChild("lMiddleSmallTube06");
        this.parts.put("lMiddleSmallTube06", this.lMiddleSmallTube06);
        this.lMiddleSmallTube07 = this.lMiddleSmallTube06.getChild("lMiddleSmallTube07");
        this.parts.put("lMiddleSmallTube07", this.lMiddleSmallTube07);
        this.lMiddleSmallTube08 = this.lMiddleSmallTube07.getChild("lMiddleSmallTube08");
        this.parts.put("lMiddleSmallTube08", this.lMiddleSmallTube08);
        this.lMiddleSmallTube09 = this.lMiddleSmallTube08.getChild("lMiddleSmallTube09");
        this.parts.put("lMiddleSmallTube09", this.lMiddleSmallTube09);
        this.lMiddleSmallTube10 = this.lMiddleSmallTube09.getChild("lMiddleSmallTube10");
        this.parts.put("lMiddleSmallTube10", this.lMiddleSmallTube10);
        this.lMiddleSmallTube11 = this.lMiddleSmallTube10.getChild("lMiddleSmallTube11");
        this.parts.put("lMiddleSmallTube11", this.lMiddleSmallTube11);
        this.lMiddleSmallTube12 = this.lMiddleSmallTube11.getChild("lMiddleSmallTube12");
        this.parts.put("lMiddleSmallTube12", this.lMiddleSmallTube12);
        this.body = root.getChild("body");
        this.parts.put("body", this.body);
        this.rMiddleTenticle1 = this.body.getChild("rMiddleTenticle1");
        this.parts.put("rMiddleTenticle1", this.rMiddleTenticle1);
        this.rMiddleTenticle2 = this.rMiddleTenticle1.getChild("rMiddleTenticle2");
        this.parts.put("rMiddleTenticle2", this.rMiddleTenticle2);
        this.rMiddleTenticle3 = this.rMiddleTenticle2.getChild("rMiddleTenticle3");
        this.parts.put("rMiddleTenticle3", this.rMiddleTenticle3);
        this.rMiddleTenticle4 = this.rMiddleTenticle3.getChild("rMiddleTenticle4");
        this.parts.put("rMiddleTenticle4", this.rMiddleTenticle4);
        this.rMiddleTenticle5 = this.rMiddleTenticle4.getChild("rMiddleTenticle5");
        this.parts.put("rMiddleTenticle5", this.rMiddleTenticle5);
        this.rMiddleClaw = this.rMiddleTenticle5.getChild("rMiddleClaw");
        this.parts.put("rMiddleClaw", this.rMiddleClaw);
        this.lBackPetal = this.body.getChild("lBackPetal");
        this.parts.put("lBackPetal", this.lBackPetal);
        this.lMiddleTenticle1 = this.body.getChild("lMiddleTenticle1");
        this.parts.put("lMiddleTenticle1", this.lMiddleTenticle1);
        this.lMiddleTenticle2 = this.lMiddleTenticle1.getChild("lMiddleTenticle2");
        this.parts.put("lMiddleTenticle2", this.lMiddleTenticle2);
        this.lMiddleTenticle3 = this.lMiddleTenticle2.getChild("lMiddleTenticle3");
        this.parts.put("lMiddleTenticle3", this.lMiddleTenticle3);
        this.lMiddleTenticle4 = this.lMiddleTenticle3.getChild("lMiddleTenticle4");
        this.parts.put("lMiddleTenticle4", this.lMiddleTenticle4);
        this.lMiddleTenticle5 = this.lMiddleTenticle4.getChild("lMiddleTenticle5");
        this.parts.put("lMiddleTenticle5", this.lMiddleTenticle5);
        this.lMiddleClaw = this.lMiddleTenticle5.getChild("lMiddleClaw");
        this.parts.put("lMiddleClaw", this.lMiddleClaw);
        this.lMiddlePetal = this.body.getChild("lMiddlePetal");
        this.parts.put("lMiddlePetal", this.lMiddlePetal);
        this.rBackPetal = this.body.getChild("rBackPetal");
        this.parts.put("rBackPetal", this.rBackPetal);
        this.lJaw = this.body.getChild("lJaw");
        this.parts.put("lJaw", this.lJaw);
        this.rFrontTenticle1 = this.body.getChild("rFrontTenticle1");
        this.parts.put("rFrontTenticle1", this.rFrontTenticle1);
        this.rFrontTenticle2 = this.rFrontTenticle1.getChild("rFrontTenticle2");
        this.parts.put("rFrontTenticle2", this.rFrontTenticle2);
        this.rFrontTenticle3 = this.rFrontTenticle2.getChild("rFrontTenticle3");
        this.parts.put("rFrontTenticle3", this.rFrontTenticle3);
        this.rFrontTenticle4 = this.rFrontTenticle3.getChild("rFrontTenticle4");
        this.parts.put("rFrontTenticle4", this.rFrontTenticle4);
        this.rFrontTenticle5 = this.rFrontTenticle4.getChild("rFrontTenticle5");
        this.parts.put("rFrontTenticle5", this.rFrontTenticle5);
        this.rFrontClaw = this.rFrontTenticle5.getChild("rFrontClaw");
        this.parts.put("rFrontClaw", this.rFrontClaw);
        this.rMiddlePetal = this.body.getChild("rMiddlePetal");
        this.parts.put("rMiddlePetal", this.rMiddlePetal);
        this.lowerBody = this.body.getChild("lowerBody");
        this.parts.put("lowerBody", this.lowerBody);
        this.rBackTenticle1 = this.lowerBody.getChild("rBackTenticle1");
        this.parts.put("rBackTenticle1", this.rBackTenticle1);
        this.rBackTenticle2 = this.rBackTenticle1.getChild("rBackTenticle2");
        this.parts.put("rBackTenticle2", this.rBackTenticle2);
        this.rBackTenticle3 = this.rBackTenticle2.getChild("rBackTenticle3");
        this.parts.put("rBackTenticle3", this.rBackTenticle3);
        this.rBackTenticle4 = this.rBackTenticle3.getChild("rBackTenticle4");
        this.parts.put("rBackTenticle4", this.rBackTenticle4);
        this.rBackTenticle5 = this.rBackTenticle4.getChild("rBackTenticle5");
        this.parts.put("rBackTenticle5", this.rBackTenticle5);
        this.rBackClaw = this.rBackTenticle5.getChild("rBackClaw");
        this.parts.put("rBackClaw", this.rBackClaw);
        this.lBackTenticle1 = this.lowerBody.getChild("lBackTenticle1");
        this.parts.put("lBackTenticle1", this.lBackTenticle1);
        this.lBackTenticle2 = this.lBackTenticle1.getChild("lBackTenticle2");
        this.parts.put("lBackTenticle2", this.lBackTenticle2);
        this.lBackTenticle3 = this.lBackTenticle2.getChild("lBackTenticle3");
        this.parts.put("lBackTenticle3", this.lBackTenticle3);
        this.lBackTenticle4 = this.lBackTenticle3.getChild("lBackTenticle4");
        this.parts.put("lBackTenticle4", this.lBackTenticle4);
        this.lBackTenticle5 = this.lBackTenticle4.getChild("lBackTenticle5");
        this.parts.put("lBackTenticle5", this.lBackTenticle5);
        this.lBackClaw = this.lBackTenticle5.getChild("lBackClaw");
        this.parts.put("lBackClaw", this.lBackClaw);
        this.tail = this.lowerBody.getChild("tail");
        this.parts.put("tail", this.tail);
        this.tail2 = this.tail.getChild("tail2");
        this.parts.put("tail2", this.tail2);
        this.tail3 = this.tail2.getChild("tail3");
        this.parts.put("tail3", this.tail3);
        this.tail4 = this.tail3.getChild("tail4");
        this.parts.put("tail4", this.tail4);
        this.tail5 = this.tail4.getChild("tail5");
        this.parts.put("tail5", this.tail5);
        this.tailClaw = this.tail5.getChild("tailClaw");
        this.parts.put("tailClaw", this.tailClaw);
        this.tailPetal = this.lowerBody.getChild("tailPetal");
        this.parts.put("tailPetal", this.tailPetal);
        this.rFrontPetal = this.body.getChild("rFrontPetal");
        this.parts.put("rFrontPetal", this.rFrontPetal);
        this.lFrontTenticle1 = this.body.getChild("lFrontTenticle1");
        this.parts.put("lFrontTenticle1", this.lFrontTenticle1);
        this.lFrontTenticle2 = this.lFrontTenticle1.getChild("lFrontTenticle2");
        this.parts.put("lFrontTenticle2", this.lFrontTenticle2);
        this.lFrontTenticle3 = this.lFrontTenticle2.getChild("lFrontTenticle3");
        this.parts.put("lFrontTenticle3", this.lFrontTenticle3);
        this.lFrontTenticle4 = this.lFrontTenticle3.getChild("lFrontTenticle4");
        this.parts.put("lFrontTenticle4", this.lFrontTenticle4);
        this.lFrontTenticle5 = this.lFrontTenticle4.getChild("lFrontTenticle5");
        this.parts.put("lFrontTenticle5", this.lFrontTenticle5);
        this.lFrontClaw = this.lFrontTenticle5.getChild("lFrontClaw");
        this.parts.put("lFrontClaw", this.lFrontClaw);
        this.tube01 = this.body.getChild("tube01");
        this.parts.put("tube01", this.tube01);
        this.tube02 = this.tube01.getChild("tube02");
        this.parts.put("tube02", this.tube02);
        this.tube03 = this.tube02.getChild("tube03");
        this.parts.put("tube03", this.tube03);
        this.tube04 = this.tube03.getChild("tube04");
        this.parts.put("tube04", this.tube04);
        this.tube05 = this.tube04.getChild("tube05");
        this.parts.put("tube05", this.tube05);
        this.tube06 = this.tube05.getChild("tube06");
        this.parts.put("tube06", this.tube06);
        this.tube07 = this.tube06.getChild("tube07");
        this.parts.put("tube07", this.tube07);
        this.tube08 = this.tube07.getChild("tube08");
        this.parts.put("tube08", this.tube08);
        this.tube09 = this.tube08.getChild("tube09");
        this.parts.put("tube09", this.tube09);
        this.tube10 = this.tube09.getChild("tube10");
        this.parts.put("tube10", this.tube10);
        this.tube11 = this.tube10.getChild("tube11");
        this.parts.put("tube11", this.tube11);
        this.tube12 = this.tube11.getChild("tube12");
        this.parts.put("tube12", this.tube12);
        this.rJaw = this.body.getChild("rJaw");
        this.parts.put("rJaw", this.rJaw);
        this.lFrontPetal = this.body.getChild("lFrontPetal");
        this.parts.put("lFrontPetal", this.lFrontPetal);
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
        PartDefinition p_smallToobBase = root.addOrReplaceChild("smallToobBase",
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 11.4F, 0.0F));
        PartDefinition p_lFrontSmallTube01 = p_smallToobBase.addOrReplaceChild("lFrontSmallTube01",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(-3.0F, 1.5F, -2.4F));
        PartDefinition p_lFrontSmallTube02 = p_lFrontSmallTube01.addOrReplaceChild("lFrontSmallTube02",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lFrontSmallTube03 = p_lFrontSmallTube02.addOrReplaceChild("lFrontSmallTube03",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lFrontSmallTube04 = p_lFrontSmallTube03.addOrReplaceChild("lFrontSmallTube04",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lFrontSmallTube05 = p_lFrontSmallTube04.addOrReplaceChild("lFrontSmallTube05",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lFrontSmallTube06 = p_lFrontSmallTube05.addOrReplaceChild("lFrontSmallTube06",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lFrontSmallTube07 = p_lFrontSmallTube06.addOrReplaceChild("lFrontSmallTube07",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -7.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lFrontSmallTube08 = p_lFrontSmallTube07.addOrReplaceChild("lFrontSmallTube08",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lFrontSmallTube09 = p_lFrontSmallTube08.addOrReplaceChild("lFrontSmallTube09",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -9.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lFrontSmallTube10 = p_lFrontSmallTube09.addOrReplaceChild("lFrontSmallTube10",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lFrontSmallTube11 = p_lFrontSmallTube10.addOrReplaceChild("lFrontSmallTube11",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -11.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lFrontSmallTube12 = p_lFrontSmallTube11.addOrReplaceChild("lFrontSmallTube12",
                CubeListBuilder.create().texOffs(99, 6).addBox(-0.5F, -12.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rFrontSmallTube01 = p_smallToobBase.addOrReplaceChild("rFrontSmallTube01",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(3.0F, 1.5F, -2.4F));
        PartDefinition p_rFrontSmallTube02 = p_rFrontSmallTube01.addOrReplaceChild("rFrontSmallTube02",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rFrontSmallTube03 = p_rFrontSmallTube02.addOrReplaceChild("rFrontSmallTube03",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rFrontSmallTube04 = p_rFrontSmallTube03.addOrReplaceChild("rFrontSmallTube04",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rFrontSmallTube05 = p_rFrontSmallTube04.addOrReplaceChild("rFrontSmallTube05",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rFrontSmallTube06 = p_rFrontSmallTube05.addOrReplaceChild("rFrontSmallTube06",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rFrontSmallTube07 = p_rFrontSmallTube06.addOrReplaceChild("rFrontSmallTube07",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -7.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rFrontSmallTube08 = p_rFrontSmallTube07.addOrReplaceChild("rFrontSmallTube08",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rFrontSmallTube09 = p_rFrontSmallTube08.addOrReplaceChild("rFrontSmallTube09",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -9.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rFrontSmallTube10 = p_rFrontSmallTube09.addOrReplaceChild("rFrontSmallTube10",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rFrontSmallTube11 = p_rFrontSmallTube10.addOrReplaceChild("rFrontSmallTube11",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -11.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rFrontSmallTube12 = p_rFrontSmallTube11.addOrReplaceChild("rFrontSmallTube12",
                CubeListBuilder.create().texOffs(99, 6).addBox(-0.5F, -12.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rMiddleSmallTube01 = p_smallToobBase.addOrReplaceChild("rMiddleSmallTube01",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(3.6F, 1.5F, 1.2F));
        PartDefinition p_rMiddleSmallTube02 = p_rMiddleSmallTube01.addOrReplaceChild("rMiddleSmallTube02",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rMiddleSmallTube03 = p_rMiddleSmallTube02.addOrReplaceChild("rMiddleSmallTube03",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rMiddleSmallTube04 = p_rMiddleSmallTube03.addOrReplaceChild("rMiddleSmallTube04",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rMiddleSmallTube05 = p_rMiddleSmallTube04.addOrReplaceChild("rMiddleSmallTube05",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rMiddleSmallTube06 = p_rMiddleSmallTube05.addOrReplaceChild("rMiddleSmallTube06",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rMiddleSmallTube07 = p_rMiddleSmallTube06.addOrReplaceChild("rMiddleSmallTube07",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -7.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rMiddleSmallTube08 = p_rMiddleSmallTube07.addOrReplaceChild("rMiddleSmallTube08",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rMiddleSmallTube09 = p_rMiddleSmallTube08.addOrReplaceChild("rMiddleSmallTube09",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -9.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rMiddleSmallTube10 = p_rMiddleSmallTube09.addOrReplaceChild("rMiddleSmallTube10",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rMiddleSmallTube11 = p_rMiddleSmallTube10.addOrReplaceChild("rMiddleSmallTube11",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -11.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rMiddleSmallTube12 = p_rMiddleSmallTube11.addOrReplaceChild("rMiddleSmallTube12",
                CubeListBuilder.create().texOffs(99, 6).addBox(-0.5F, -12.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tailSmallTube01 = p_smallToobBase.addOrReplaceChild("tailSmallTube01",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.2F, 6.2F));
        PartDefinition p_tailSmallTube02 = p_tailSmallTube01.addOrReplaceChild("tailSmallTube02",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tailSmallTube03 = p_tailSmallTube02.addOrReplaceChild("tailSmallTube03",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tailSmallTube04 = p_tailSmallTube03.addOrReplaceChild("tailSmallTube04",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tailSmallTube05 = p_tailSmallTube04.addOrReplaceChild("tailSmallTube05",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tailSmallTube06 = p_tailSmallTube05.addOrReplaceChild("tailSmallTube06",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tailSmallTube07 = p_tailSmallTube06.addOrReplaceChild("tailSmallTube07",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -7.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tailSmallTube08 = p_tailSmallTube07.addOrReplaceChild("tailSmallTube08",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tailSmallTube09 = p_tailSmallTube08.addOrReplaceChild("tailSmallTube09",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -9.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tailSmallTube10 = p_tailSmallTube09.addOrReplaceChild("tailSmallTube10",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tailSmallTube11 = p_tailSmallTube10.addOrReplaceChild("tailSmallTube11",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -11.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tailSmallTube12 = p_tailSmallTube11.addOrReplaceChild("tailSmallTube12",
                CubeListBuilder.create().texOffs(92, 0).addBox(-0.5F, -12.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rBackSmallTube01 = p_smallToobBase.addOrReplaceChild("rBackSmallTube01",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(2.6F, 1.0F, 4.6F));
        PartDefinition p_rBackSmallTube02 = p_rBackSmallTube01.addOrReplaceChild("rBackSmallTube02",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rBackSmallTube03 = p_rBackSmallTube02.addOrReplaceChild("rBackSmallTube03",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rBackSmallTube04 = p_rBackSmallTube03.addOrReplaceChild("rBackSmallTube04",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rBackSmallTube05 = p_rBackSmallTube04.addOrReplaceChild("rBackSmallTube05",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rBackSmallTube06 = p_rBackSmallTube05.addOrReplaceChild("rBackSmallTube06",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rBackSmallTube07 = p_rBackSmallTube06.addOrReplaceChild("rBackSmallTube07",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -7.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rBackSmallTube08 = p_rBackSmallTube07.addOrReplaceChild("rBackSmallTube08",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rBackSmallTube09 = p_rBackSmallTube08.addOrReplaceChild("rBackSmallTube09",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -9.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rBackSmallTube10 = p_rBackSmallTube09.addOrReplaceChild("rBackSmallTube10",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rBackSmallTube11 = p_rBackSmallTube10.addOrReplaceChild("rBackSmallTube11",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -11.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rBackSmallTube12 = p_rBackSmallTube11.addOrReplaceChild("rBackSmallTube12",
                CubeListBuilder.create().texOffs(99, 6).addBox(-0.5F, -12.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lBackSmallTube01 = p_smallToobBase.addOrReplaceChild("lBackSmallTube01",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(-2.6F, 1.0F, 4.6F));
        PartDefinition p_lBackSmallTube02 = p_lBackSmallTube01.addOrReplaceChild("lBackSmallTube02",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lBackSmallTube03 = p_lBackSmallTube02.addOrReplaceChild("lBackSmallTube03",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lBackSmallTube04 = p_lBackSmallTube03.addOrReplaceChild("lBackSmallTube04",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lBackSmallTube05 = p_lBackSmallTube04.addOrReplaceChild("lBackSmallTube05",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lBackSmallTube06 = p_lBackSmallTube05.addOrReplaceChild("lBackSmallTube06",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lBackSmallTube07 = p_lBackSmallTube06.addOrReplaceChild("lBackSmallTube07",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -7.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lBackSmallTube08 = p_lBackSmallTube07.addOrReplaceChild("lBackSmallTube08",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lBackSmallTube09 = p_lBackSmallTube08.addOrReplaceChild("lBackSmallTube09",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -9.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lBackSmallTube10 = p_lBackSmallTube09.addOrReplaceChild("lBackSmallTube10",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lBackSmallTube11 = p_lBackSmallTube10.addOrReplaceChild("lBackSmallTube11",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -11.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lBackSmallTube12 = p_lBackSmallTube11.addOrReplaceChild("lBackSmallTube12",
                CubeListBuilder.create().texOffs(99, 6).addBox(-0.5F, -12.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lMiddleSmallTube01 = p_smallToobBase.addOrReplaceChild("lMiddleSmallTube01",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(-3.6F, 1.5F, 1.2F));
        PartDefinition p_lMiddleSmallTube02 = p_lMiddleSmallTube01.addOrReplaceChild("lMiddleSmallTube02",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lMiddleSmallTube03 = p_lMiddleSmallTube02.addOrReplaceChild("lMiddleSmallTube03",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lMiddleSmallTube04 = p_lMiddleSmallTube03.addOrReplaceChild("lMiddleSmallTube04",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lMiddleSmallTube05 = p_lMiddleSmallTube04.addOrReplaceChild("lMiddleSmallTube05",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lMiddleSmallTube06 = p_lMiddleSmallTube05.addOrReplaceChild("lMiddleSmallTube06",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lMiddleSmallTube07 = p_lMiddleSmallTube06.addOrReplaceChild("lMiddleSmallTube07",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -7.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lMiddleSmallTube08 = p_lMiddleSmallTube07.addOrReplaceChild("lMiddleSmallTube08",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lMiddleSmallTube09 = p_lMiddleSmallTube08.addOrReplaceChild("lMiddleSmallTube09",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -9.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lMiddleSmallTube10 = p_lMiddleSmallTube09.addOrReplaceChild("lMiddleSmallTube10",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lMiddleSmallTube11 = p_lMiddleSmallTube10.addOrReplaceChild("lMiddleSmallTube11",
                CubeListBuilder.create().texOffs(92, 6).addBox(-0.5F, -11.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_lMiddleSmallTube12 = p_lMiddleSmallTube11.addOrReplaceChild("lMiddleSmallTube12",
                CubeListBuilder.create().texOffs(99, 6).addBox(-0.5F, -12.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_body = root.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -1.5F, -3.0F, 6.0F, 3.0F, 6.0F),
                PartPose.offset(0.0F, 11.4F, 0.0F));
        PartDefinition p_rMiddleTenticle1 = p_body.addOrReplaceChild("rMiddleTenticle1",
                CubeListBuilder.create().texOffs(30, 8).mirror().addBox(-4.0F, -1.0F, -1.5F, 4.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-2.5F, 0.0F, 1.0F, 0.0F, 0.0F, -0.3141592653589793F));
        PartDefinition p_rMiddleTenticle2 = p_rMiddleTenticle1.addOrReplaceChild("rMiddleTenticle2",
                CubeListBuilder.create().texOffs(25, 14).mirror().addBox(-7.0F, -1.0F, -2.0F, 7.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(-3.7F, 0.0F, 0.0F, 0.0F, 0.0F, -0.22689280275926282F));
        PartDefinition p_rMiddleTenticle3 = p_rMiddleTenticle2.addOrReplaceChild("rMiddleTenticle3",
                CubeListBuilder.create().texOffs(27, 21).mirror().addBox(-6.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-6.8F, 0.0F, 0.0F, 0.0F, 0.0F, -0.40142572795869574F));
        PartDefinition p_rMiddleTenticle4 = p_rMiddleTenticle3.addOrReplaceChild("rMiddleTenticle4",
                CubeListBuilder.create().texOffs(27, 28).mirror().addBox(-6.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-5.6F, 0.0F, 0.0F, 0.0F, 0.0F, 0.45378560551852565F));
        PartDefinition p_rMiddleTenticle5 = p_rMiddleTenticle4.addOrReplaceChild("rMiddleTenticle5",
                CubeListBuilder.create().texOffs(27, 36).mirror().addBox(-7.0F, -0.5F, -1.0F, 7.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-5.8F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4886921905584123F));
        PartDefinition p_rMiddleClaw = p_rMiddleTenticle5.addOrReplaceChild("rMiddleClaw",
                CubeListBuilder.create().texOffs(31, 41).mirror().addBox(-5.0F, 0.0F, -1.5F, 5.0F, 0.0F, 3.0F),
                PartPose.offset(-7.0F, 0.0F, 0.0F));
        PartDefinition p_lBackPetal = p_body.addOrReplaceChild("lBackPetal",
                CubeListBuilder.create().texOffs(81, 45).addBox(0.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(1.4F, 0.9F, 3.9F, -0.5759586531581287F, 0.45378560551852565F, 2.0943951023931953F));
        PartDefinition p_lMiddleTenticle1 = p_body.addOrReplaceChild("lMiddleTenticle1",
                CubeListBuilder.create().texOffs(30, 8).addBox(0.0F, -1.0F, -1.5F, 4.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(2.5F, 0.0F, 1.0F, 0.0F, 0.0F, 0.3141592653589793F));
        PartDefinition p_lMiddleTenticle2 = p_lMiddleTenticle1.addOrReplaceChild("lMiddleTenticle2",
                CubeListBuilder.create().texOffs(25, 14).addBox(0.0F, -1.0F, -2.0F, 7.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(3.7F, 0.0F, 0.0F, 0.0F, 0.0F, 0.22689280275926282F));
        PartDefinition p_lMiddleTenticle3 = p_lMiddleTenticle2.addOrReplaceChild("lMiddleTenticle3",
                CubeListBuilder.create().texOffs(27, 21).addBox(0.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(6.8F, 0.0F, 0.0F, 0.0F, 0.0F, 0.40142572795869574F));
        PartDefinition p_lMiddleTenticle4 = p_lMiddleTenticle3.addOrReplaceChild("lMiddleTenticle4",
                CubeListBuilder.create().texOffs(27, 28).addBox(0.0F, -1.5F, -1.5F, 6.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(5.6F, 0.5F, 0.0F, 0.0F, 0.0F, -0.45378560551852565F));
        PartDefinition p_lMiddleTenticle5 = p_lMiddleTenticle4.addOrReplaceChild("lMiddleTenticle5",
                CubeListBuilder.create().texOffs(27, 36).addBox(0.0F, -0.5F, -1.0F, 7.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(5.8F, -0.5F, 0.0F, 0.0F, 0.0F, -0.4886921905584123F));
        PartDefinition p_lMiddleClaw = p_lMiddleTenticle5.addOrReplaceChild("lMiddleClaw",
                CubeListBuilder.create().texOffs(31, 41).addBox(0.0F, 0.0F, -1.5F, 5.0F, 0.0F, 3.0F),
                PartPose.offset(7.0F, 0.0F, 0.0F));
        PartDefinition p_lMiddlePetal = p_body.addOrReplaceChild("lMiddlePetal",
                CubeListBuilder.create().texOffs(81, 41).addBox(0.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(2.8F, 1.1F, 1.0F, -0.03490658503988659F, 0.0F, 2.443460952792061F));
        PartDefinition p_rBackPetal = p_body.addOrReplaceChild("rBackPetal",
                CubeListBuilder.create().texOffs(81, 45).mirror().addBox(-3.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-1.4F, 0.9F, 4.0F, -0.5759586531581287F, 0.0F, -2.0943951023931953F));
        PartDefinition p_lJaw = p_body.addOrReplaceChild("lJaw",
                CubeListBuilder.create().texOffs(5, 48).addBox(-0.2F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.3F, 1.2F, -0.1F, 0.032812189937493394F, -0.08098327729253688F, -0.3853686988403479F));
        PartDefinition p_rFrontTenticle1 = p_body.addOrReplaceChild("rFrontTenticle1",
                CubeListBuilder.create().texOffs(0, 53).mirror().addBox(-3.2F, -1.0F, -1.5F, 4.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-2.2F, 0.0F, -2.1F, 0.45378560551852565F, -0.7155849933176751F, -0.593411945678072F));
        PartDefinition p_rFrontTenticle2 = p_rFrontTenticle1.addOrReplaceChild("rFrontTenticle2",
                CubeListBuilder.create().texOffs(0, 15).mirror().addBox(-7.0F, -1.0F, -2.0F, 7.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(-2.7F, -0.2F, 0.0F, 0.0F, 0.0F, -0.45378560551852565F));
        PartDefinition p_rFrontTenticle3 = p_rFrontTenticle2.addOrReplaceChild("rFrontTenticle3",
                CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-6.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-6.9F, 0.0F, 0.0F, 0.0F, 0.0F, 0.22689280275926282F));
        PartDefinition p_rFrontTenticle4 = p_rFrontTenticle3.addOrReplaceChild("rFrontTenticle4",
                CubeListBuilder.create().texOffs(0, 28).mirror().addBox(-6.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-6.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2617993877991494F));
        PartDefinition p_rFrontTenticle5 = p_rFrontTenticle4.addOrReplaceChild("rFrontTenticle5",
                CubeListBuilder.create().texOffs(0, 35).mirror().addBox(-7.0F, -0.5F, -1.0F, 7.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-5.9F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3665191429188092F));
        PartDefinition p_rFrontClaw = p_rFrontTenticle5.addOrReplaceChild("rFrontClaw",
                CubeListBuilder.create().texOffs(0, 39).mirror().addBox(-5.0F, 0.0F, -1.5F, 5.0F, 0.0F, 3.0F),
                PartPose.offset(-7.0F, 0.0F, 0.0F));
        PartDefinition p_rMiddlePetal = p_body.addOrReplaceChild("rMiddlePetal",
                CubeListBuilder.create().texOffs(81, 41).mirror().addBox(-3.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-2.8F, 1.1F, 1.0F, -0.03490658503988659F, 0.0F, -2.443460952792061F));
        PartDefinition p_lowerBody = p_body.addOrReplaceChild("lowerBody",
                CubeListBuilder.create().texOffs(25, 0).addBox(-2.5F, -1.0F, 0.0F, 5.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 2.2F, -0.33161255787892263F, 0.0F, 0.0F));
        PartDefinition p_rBackTenticle1 = p_lowerBody.addOrReplaceChild("rBackTenticle1",
                CubeListBuilder.create().texOffs(30, 8).mirror().addBox(-4.0F, -1.0F, -1.5F, 4.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-1.7F, 0.0F, 2.4F, 0.17453292519943295F, 0.5410520681182421F, -0.17453292519943295F));
        PartDefinition p_rBackTenticle2 = p_rBackTenticle1.addOrReplaceChild("rBackTenticle2",
                CubeListBuilder.create().texOffs(25, 14).mirror().addBox(-7.0F, -1.0F, -2.0F, 7.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3141592653589793F));
        PartDefinition p_rBackTenticle3 = p_rBackTenticle2.addOrReplaceChild("rBackTenticle3",
                CubeListBuilder.create().texOffs(27, 21).mirror().addBox(-6.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-6.7F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2617993877991494F));
        PartDefinition p_rBackTenticle4 = p_rBackTenticle3.addOrReplaceChild("rBackTenticle4",
                CubeListBuilder.create().texOffs(27, 28).mirror().addBox(-6.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(-5.8F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4886921905584123F));
        PartDefinition p_rBackTenticle5 = p_rBackTenticle4.addOrReplaceChild("rBackTenticle5",
                CubeListBuilder.create().texOffs(27, 36).mirror().addBox(-7.0F, -0.5F, -1.0F, 7.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-5.7F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4886921905584123F));
        PartDefinition p_rBackClaw = p_rBackTenticle5.addOrReplaceChild("rBackClaw",
                CubeListBuilder.create().texOffs(31, 41).mirror().addBox(-5.0F, 0.0F, -1.5F, 5.0F, 0.0F, 3.0F),
                PartPose.offset(-7.0F, 0.0F, 0.0F));
        PartDefinition p_lBackTenticle1 = p_lowerBody.addOrReplaceChild("lBackTenticle1",
                CubeListBuilder.create().texOffs(30, 8).addBox(0.0F, -1.0F, -1.5F, 4.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(1.7F, 0.0F, 2.4F, 0.17453292519943295F, -0.5410520681182421F, 0.17453292519943295F));
        PartDefinition p_lBackTenticle2 = p_lBackTenticle1.addOrReplaceChild("lBackTenticle2",
                CubeListBuilder.create().texOffs(25, 14).addBox(0.0F, -1.0F, -2.0F, 7.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3141592653589793F));
        PartDefinition p_lBackTenticle3 = p_lBackTenticle2.addOrReplaceChild("lBackTenticle3",
                CubeListBuilder.create().texOffs(27, 21).addBox(0.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(7.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2617993877991494F));
        PartDefinition p_lBackTenticle4 = p_lBackTenticle3.addOrReplaceChild("lBackTenticle4",
                CubeListBuilder.create().texOffs(27, 28).addBox(0.0F, -1.5F, -1.5F, 6.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(5.8F, 0.5F, 0.0F, 0.0F, 0.0F, -0.4886921905584123F));
        PartDefinition p_lBackTenticle5 = p_lBackTenticle4.addOrReplaceChild("lBackTenticle5",
                CubeListBuilder.create().texOffs(27, 36).addBox(0.0F, -0.5F, -1.0F, 7.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(5.7F, -0.5F, 0.0F, 0.0F, 0.0F, -0.41887902047863906F));
        PartDefinition p_lBackClaw = p_lBackTenticle5.addOrReplaceChild("lBackClaw",
                CubeListBuilder.create().texOffs(31, 41).addBox(0.0F, 0.0F, -1.5F, 5.0F, 0.0F, 3.0F),
                PartPose.offset(7.0F, 0.0F, 0.0F));
        PartDefinition p_tail = p_lowerBody.addOrReplaceChild("tail",
                CubeListBuilder.create().texOffs(60, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 4.0F));
        PartDefinition p_tail2 = p_tail.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(54, 7).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 2.7F, -0.45378560551852565F, 0.0F, 0.0F));
        PartDefinition p_tail3 = p_tail2.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(56, 18).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 7.0F, 0.13962634015954636F, 0.0F, 0.0F));
        PartDefinition p_tail4 = p_tail3.addOrReplaceChild("tail4",
                CubeListBuilder.create().texOffs(56, 28).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 5.6F, 0.3665191429188092F, 0.0F, 0.0F));
        PartDefinition p_tail5 = p_tail4.addOrReplaceChild("tail5",
                CubeListBuilder.create().texOffs(56, 38).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 5.7F, 0.3141592653589793F, 0.0F, 0.0F));
        PartDefinition p_tailClaw = p_tail5.addOrReplaceChild("tailClaw",
                CubeListBuilder.create().texOffs(57, 49).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 7.0F));
        PartDefinition p_tailPetal = p_lowerBody.addOrReplaceChild("tailPetal",
                CubeListBuilder.create().texOffs(89, 50).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.6F, 4.0F, -2.443460952792061F, 0.0F, 0.0F));
        PartDefinition p_rFrontPetal = p_body.addOrReplaceChild("rFrontPetal",
                CubeListBuilder.create().texOffs(81, 37).mirror().addBox(-4.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-2.6F, 1.0F, -1.5F, 0.17453292519943295F, -0.3490658503988659F, -2.6179938779914944F));
        PartDefinition p_lFrontTenticle1 = p_body.addOrReplaceChild("lFrontTenticle1",
                CubeListBuilder.create().texOffs(0, 53).addBox(-0.8F, -1.0F, -1.5F, 4.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(2.2F, 0.0F, -2.1F, 0.45378560551852565F, 0.7155849933176751F, 0.593411945678072F));
        PartDefinition p_lFrontTenticle2 = p_lFrontTenticle1.addOrReplaceChild("lFrontTenticle2",
                CubeListBuilder.create().texOffs(0, 15).addBox(0.0F, -1.0F, -2.0F, 7.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(2.7F, -0.2F, 0.0F, 0.0F, 0.0F, 0.45378560551852565F));
        PartDefinition p_lFrontTenticle3 = p_lFrontTenticle2.addOrReplaceChild("lFrontTenticle3",
                CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(6.9F, 0.0F, 0.0F, 0.0F, 0.0F, -0.22689280275926282F));
        PartDefinition p_lFrontTenticle4 = p_lFrontTenticle3.addOrReplaceChild("lFrontTenticle4",
                CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(6.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2617993877991494F));
        PartDefinition p_lFrontTenticle5 = p_lFrontTenticle4.addOrReplaceChild("lFrontTenticle5",
                CubeListBuilder.create().texOffs(0, 35).addBox(0.0F, -0.5F, -1.0F, 7.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(5.9F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3665191429188092F));
        PartDefinition p_lFrontClaw = p_lFrontTenticle5.addOrReplaceChild("lFrontClaw",
                CubeListBuilder.create().texOffs(0, 39).addBox(0.0F, 0.0F, -1.5F, 5.0F, 0.0F, 3.0F),
                PartPose.offset(7.0F, 0.1F, 0.0F));
        PartDefinition p_tube01 = p_body.addOrReplaceChild("tube01",
                CubeListBuilder.create().texOffs(92, 0).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.2F, 0.4F));
        PartDefinition p_tube02 = p_tube01.addOrReplaceChild("tube02",
                CubeListBuilder.create().texOffs(92, 0).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tube03 = p_tube02.addOrReplaceChild("tube03",
                CubeListBuilder.create().texOffs(92, 0).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_tube04 = p_tube03.addOrReplaceChild("tube04",
                CubeListBuilder.create().texOffs(92, 0).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tube05 = p_tube04.addOrReplaceChild("tube05",
                CubeListBuilder.create().texOffs(92, 0).addBox(-0.5F, -5.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tube06 = p_tube05.addOrReplaceChild("tube06",
                CubeListBuilder.create().texOffs(92, 0).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tube07 = p_tube06.addOrReplaceChild("tube07",
                CubeListBuilder.create().texOffs(92, 0).addBox(-0.5F, -7.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tube08 = p_tube07.addOrReplaceChild("tube08",
                CubeListBuilder.create().texOffs(92, 0).addBox(-0.5F, -8.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tube09 = p_tube08.addOrReplaceChild("tube09",
                CubeListBuilder.create().texOffs(92, 0).addBox(-0.5F, -9.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tube10 = p_tube09.addOrReplaceChild("tube10",
                CubeListBuilder.create().texOffs(92, 0).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tube11 = p_tube10.addOrReplaceChild("tube11",
                CubeListBuilder.create().texOffs(92, 0).addBox(-0.5F, -11.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_tube12 = p_tube11.addOrReplaceChild("tube12",
                CubeListBuilder.create().texOffs(92, 0).addBox(-0.5F, -12.0F, -0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 1.0F, 0.0F));
        PartDefinition p_rJaw = p_body.addOrReplaceChild("rJaw",
                CubeListBuilder.create().texOffs(5, 48).mirror().addBox(-0.8F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-0.3F, 1.2F, -0.1F, 0.032812189937493394F, 0.08098327729253688F, 0.3853686988403479F));
        PartDefinition p_lFrontPetal = p_body.addOrReplaceChild("lFrontPetal",
                CubeListBuilder.create().texOffs(81, 37).addBox(0.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(2.6F, 1.0F, -1.5F, 0.17453292519943295F, 0.3490658503988659F, 2.6179938779914944F));
        return LayerDefinition.create(mesh, 128, 64);
    }
}
