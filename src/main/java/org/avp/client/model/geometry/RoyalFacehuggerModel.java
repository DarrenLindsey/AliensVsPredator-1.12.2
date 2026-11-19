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
 * Geometry converted automatically from the 1.12.2 ModelRoyalFacehugger by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class RoyalFacehuggerModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "royal_facehugger"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart tail01;
    public final ModelPart tailVertebrae01b;
    public final ModelPart tailVertebrae01;
    public final ModelPart tail02;
    public final ModelPart tailVertebrae02b;
    public final ModelPart tailVertebrae02;
    public final ModelPart tail03;
    public final ModelPart tailVertebrae03;
    public final ModelPart tail04;
    public final ModelPart tailVertebrae04b;
    public final ModelPart tail05;
    public final ModelPart tail06;
    public final ModelPart tailVertebrae06b;
    public final ModelPart tail07;
    public final ModelPart tailVertebrae07b;
    public final ModelPart tail08;
    public final ModelPart tailVertebrae08b;
    public final ModelPart tail09;
    public final ModelPart tailVertebrae09b;
    public final ModelPart tailVertebrae09;
    public final ModelPart tail10;
    public final ModelPart tailVertebrae10b;
    public final ModelPart tailVertebrae10;
    public final ModelPart tail11;
    public final ModelPart tailVertebrae11b;
    public final ModelPart tailVertebrae11;
    public final ModelPart tail12;
    public final ModelPart tailVertebrae12;
    public final ModelPart tail13;
    public final ModelPart tailVertebrae13b;
    public final ModelPart tailVertebrae13;
    public final ModelPart tail14;
    public final ModelPart tailVertebrae14;
    public final ModelPart tailVertebrae14b;
    public final ModelPart tail15;
    public final ModelPart tailVertebrae15;
    public final ModelPart tail16;
    public final ModelPart tailVertebrae16b;
    public final ModelPart tailVertebrae16;
    public final ModelPart tailVertebrae15b;
    public final ModelPart tailVertebrae12b;
    public final ModelPart tailVertebrae08;
    public final ModelPart tailVertebrae07;
    public final ModelPart tailVertebrae06;
    public final ModelPart tailVertebrae05b;
    public final ModelPart tailVertebrae05;
    public final ModelPart tailVertebrae04;
    public final ModelPart tailVertebrae03b;
    public final ModelPart rLeg4a;
    public final ModelPart rLeg4d;
    public final ModelPart rLeg4c;
    public final ModelPart rLeg4b;
    public final ModelPart rLeg3a;
    public final ModelPart rLeg3c;
    public final ModelPart rLeg3b;
    public final ModelPart rLeg3d;
    public final ModelPart rAirSac;
    public final ModelPart vertebrae01;
    public final ModelPart lLeg4a;
    public final ModelPart lLeg4c;
    public final ModelPart lLeg4d;
    public final ModelPart lLeg4b;
    public final ModelPart rLeg1a;
    public final ModelPart rLeg1d;
    public final ModelPart rLeg1b;
    public final ModelPart rLeg1c;
    public final ModelPart lLeg2a;
    public final ModelPart lLeg2d;
    public final ModelPart lLeg2b;
    public final ModelPart lLeg2c;
    public final ModelPart spine;
    public final ModelPart vertebrae03;
    public final ModelPart lAirSac;
    public final ModelPart lLeg1a;
    public final ModelPart lLeg1d;
    public final ModelPart lLeg1b;
    public final ModelPart lLeg1c;
    public final ModelPart bodyMain;
    public final ModelPart vertebrae02;
    public final ModelPart lLeg3a;
    public final ModelPart lLeg3c;
    public final ModelPart lLeg3d;
    public final ModelPart lLeg3b;
    public final ModelPart rLeg2a;
    public final ModelPart rLeg2c;
    public final ModelPart rLeg2d;
    public final ModelPart rLeg2b;
    public final ModelPart spine2;

    public RoyalFacehuggerModel(ModelPart root)
    {
        this.root = root;
        this.tail01 = root.getChild("tail01");
        this.parts.put("tail01", this.tail01);
        this.tailVertebrae01b = this.tail01.getChild("tailVertebrae01b");
        this.parts.put("tailVertebrae01b", this.tailVertebrae01b);
        this.tailVertebrae01 = this.tail01.getChild("tailVertebrae01");
        this.parts.put("tailVertebrae01", this.tailVertebrae01);
        this.tail02 = this.tail01.getChild("tail02");
        this.parts.put("tail02", this.tail02);
        this.tailVertebrae02b = this.tail02.getChild("tailVertebrae02b");
        this.parts.put("tailVertebrae02b", this.tailVertebrae02b);
        this.tailVertebrae02 = this.tail02.getChild("tailVertebrae02");
        this.parts.put("tailVertebrae02", this.tailVertebrae02);
        this.tail03 = this.tail02.getChild("tail03");
        this.parts.put("tail03", this.tail03);
        this.tailVertebrae03 = this.tail03.getChild("tailVertebrae03");
        this.parts.put("tailVertebrae03", this.tailVertebrae03);
        this.tail04 = this.tail03.getChild("tail04");
        this.parts.put("tail04", this.tail04);
        this.tailVertebrae04b = this.tail04.getChild("tailVertebrae04b");
        this.parts.put("tailVertebrae04b", this.tailVertebrae04b);
        this.tail05 = this.tail04.getChild("tail05");
        this.parts.put("tail05", this.tail05);
        this.tail06 = this.tail05.getChild("tail06");
        this.parts.put("tail06", this.tail06);
        this.tailVertebrae06b = this.tail06.getChild("tailVertebrae06b");
        this.parts.put("tailVertebrae06b", this.tailVertebrae06b);
        this.tail07 = this.tail06.getChild("tail07");
        this.parts.put("tail07", this.tail07);
        this.tailVertebrae07b = this.tail07.getChild("tailVertebrae07b");
        this.parts.put("tailVertebrae07b", this.tailVertebrae07b);
        this.tail08 = this.tail07.getChild("tail08");
        this.parts.put("tail08", this.tail08);
        this.tailVertebrae08b = this.tail08.getChild("tailVertebrae08b");
        this.parts.put("tailVertebrae08b", this.tailVertebrae08b);
        this.tail09 = this.tail08.getChild("tail09");
        this.parts.put("tail09", this.tail09);
        this.tailVertebrae09b = this.tail09.getChild("tailVertebrae09b");
        this.parts.put("tailVertebrae09b", this.tailVertebrae09b);
        this.tailVertebrae09 = this.tail09.getChild("tailVertebrae09");
        this.parts.put("tailVertebrae09", this.tailVertebrae09);
        this.tail10 = this.tail09.getChild("tail10");
        this.parts.put("tail10", this.tail10);
        this.tailVertebrae10b = this.tail10.getChild("tailVertebrae10b");
        this.parts.put("tailVertebrae10b", this.tailVertebrae10b);
        this.tailVertebrae10 = this.tail10.getChild("tailVertebrae10");
        this.parts.put("tailVertebrae10", this.tailVertebrae10);
        this.tail11 = this.tail10.getChild("tail11");
        this.parts.put("tail11", this.tail11);
        this.tailVertebrae11b = this.tail11.getChild("tailVertebrae11b");
        this.parts.put("tailVertebrae11b", this.tailVertebrae11b);
        this.tailVertebrae11 = this.tail11.getChild("tailVertebrae11");
        this.parts.put("tailVertebrae11", this.tailVertebrae11);
        this.tail12 = this.tail11.getChild("tail12");
        this.parts.put("tail12", this.tail12);
        this.tailVertebrae12 = this.tail12.getChild("tailVertebrae12");
        this.parts.put("tailVertebrae12", this.tailVertebrae12);
        this.tail13 = this.tail12.getChild("tail13");
        this.parts.put("tail13", this.tail13);
        this.tailVertebrae13b = this.tail13.getChild("tailVertebrae13b");
        this.parts.put("tailVertebrae13b", this.tailVertebrae13b);
        this.tailVertebrae13 = this.tail13.getChild("tailVertebrae13");
        this.parts.put("tailVertebrae13", this.tailVertebrae13);
        this.tail14 = this.tail13.getChild("tail14");
        this.parts.put("tail14", this.tail14);
        this.tailVertebrae14 = this.tail14.getChild("tailVertebrae14");
        this.parts.put("tailVertebrae14", this.tailVertebrae14);
        this.tailVertebrae14b = this.tail14.getChild("tailVertebrae14b");
        this.parts.put("tailVertebrae14b", this.tailVertebrae14b);
        this.tail15 = this.tail14.getChild("tail15");
        this.parts.put("tail15", this.tail15);
        this.tailVertebrae15 = this.tail15.getChild("tailVertebrae15");
        this.parts.put("tailVertebrae15", this.tailVertebrae15);
        this.tail16 = this.tail15.getChild("tail16");
        this.parts.put("tail16", this.tail16);
        this.tailVertebrae16b = this.tail16.getChild("tailVertebrae16b");
        this.parts.put("tailVertebrae16b", this.tailVertebrae16b);
        this.tailVertebrae16 = this.tail16.getChild("tailVertebrae16");
        this.parts.put("tailVertebrae16", this.tailVertebrae16);
        this.tailVertebrae15b = this.tail15.getChild("tailVertebrae15b");
        this.parts.put("tailVertebrae15b", this.tailVertebrae15b);
        this.tailVertebrae12b = this.tail12.getChild("tailVertebrae12b");
        this.parts.put("tailVertebrae12b", this.tailVertebrae12b);
        this.tailVertebrae08 = this.tail08.getChild("tailVertebrae08");
        this.parts.put("tailVertebrae08", this.tailVertebrae08);
        this.tailVertebrae07 = this.tail07.getChild("tailVertebrae07");
        this.parts.put("tailVertebrae07", this.tailVertebrae07);
        this.tailVertebrae06 = this.tail06.getChild("tailVertebrae06");
        this.parts.put("tailVertebrae06", this.tailVertebrae06);
        this.tailVertebrae05b = this.tail05.getChild("tailVertebrae05b");
        this.parts.put("tailVertebrae05b", this.tailVertebrae05b);
        this.tailVertebrae05 = this.tail05.getChild("tailVertebrae05");
        this.parts.put("tailVertebrae05", this.tailVertebrae05);
        this.tailVertebrae04 = this.tail04.getChild("tailVertebrae04");
        this.parts.put("tailVertebrae04", this.tailVertebrae04);
        this.tailVertebrae03b = this.tail03.getChild("tailVertebrae03b");
        this.parts.put("tailVertebrae03b", this.tailVertebrae03b);
        this.rLeg4a = root.getChild("rLeg4a");
        this.parts.put("rLeg4a", this.rLeg4a);
        this.rLeg4d = this.rLeg4a.getChild("rLeg4d");
        this.parts.put("rLeg4d", this.rLeg4d);
        this.rLeg4c = this.rLeg4a.getChild("rLeg4c");
        this.parts.put("rLeg4c", this.rLeg4c);
        this.rLeg4b = this.rLeg4a.getChild("rLeg4b");
        this.parts.put("rLeg4b", this.rLeg4b);
        this.rLeg3a = root.getChild("rLeg3a");
        this.parts.put("rLeg3a", this.rLeg3a);
        this.rLeg3c = this.rLeg3a.getChild("rLeg3c");
        this.parts.put("rLeg3c", this.rLeg3c);
        this.rLeg3b = this.rLeg3a.getChild("rLeg3b");
        this.parts.put("rLeg3b", this.rLeg3b);
        this.rLeg3d = this.rLeg3a.getChild("rLeg3d");
        this.parts.put("rLeg3d", this.rLeg3d);
        this.rAirSac = root.getChild("rAirSac");
        this.parts.put("rAirSac", this.rAirSac);
        this.vertebrae01 = root.getChild("vertebrae01");
        this.parts.put("vertebrae01", this.vertebrae01);
        this.lLeg4a = root.getChild("lLeg4a");
        this.parts.put("lLeg4a", this.lLeg4a);
        this.lLeg4c = this.lLeg4a.getChild("lLeg4c");
        this.parts.put("lLeg4c", this.lLeg4c);
        this.lLeg4d = this.lLeg4a.getChild("lLeg4d");
        this.parts.put("lLeg4d", this.lLeg4d);
        this.lLeg4b = this.lLeg4a.getChild("lLeg4b");
        this.parts.put("lLeg4b", this.lLeg4b);
        this.rLeg1a = root.getChild("rLeg1a");
        this.parts.put("rLeg1a", this.rLeg1a);
        this.rLeg1d = this.rLeg1a.getChild("rLeg1d");
        this.parts.put("rLeg1d", this.rLeg1d);
        this.rLeg1b = this.rLeg1a.getChild("rLeg1b");
        this.parts.put("rLeg1b", this.rLeg1b);
        this.rLeg1c = this.rLeg1a.getChild("rLeg1c");
        this.parts.put("rLeg1c", this.rLeg1c);
        this.lLeg2a = root.getChild("lLeg2a");
        this.parts.put("lLeg2a", this.lLeg2a);
        this.lLeg2d = this.lLeg2a.getChild("lLeg2d");
        this.parts.put("lLeg2d", this.lLeg2d);
        this.lLeg2b = this.lLeg2a.getChild("lLeg2b");
        this.parts.put("lLeg2b", this.lLeg2b);
        this.lLeg2c = this.lLeg2a.getChild("lLeg2c");
        this.parts.put("lLeg2c", this.lLeg2c);
        this.spine = root.getChild("spine");
        this.parts.put("spine", this.spine);
        this.vertebrae03 = root.getChild("vertebrae03");
        this.parts.put("vertebrae03", this.vertebrae03);
        this.lAirSac = root.getChild("lAirSac");
        this.parts.put("lAirSac", this.lAirSac);
        this.lLeg1a = root.getChild("lLeg1a");
        this.parts.put("lLeg1a", this.lLeg1a);
        this.lLeg1d = this.lLeg1a.getChild("lLeg1d");
        this.parts.put("lLeg1d", this.lLeg1d);
        this.lLeg1b = this.lLeg1a.getChild("lLeg1b");
        this.parts.put("lLeg1b", this.lLeg1b);
        this.lLeg1c = this.lLeg1a.getChild("lLeg1c");
        this.parts.put("lLeg1c", this.lLeg1c);
        this.bodyMain = root.getChild("bodyMain");
        this.parts.put("bodyMain", this.bodyMain);
        this.vertebrae02 = root.getChild("vertebrae02");
        this.parts.put("vertebrae02", this.vertebrae02);
        this.lLeg3a = root.getChild("lLeg3a");
        this.parts.put("lLeg3a", this.lLeg3a);
        this.lLeg3c = this.lLeg3a.getChild("lLeg3c");
        this.parts.put("lLeg3c", this.lLeg3c);
        this.lLeg3d = this.lLeg3a.getChild("lLeg3d");
        this.parts.put("lLeg3d", this.lLeg3d);
        this.lLeg3b = this.lLeg3a.getChild("lLeg3b");
        this.parts.put("lLeg3b", this.lLeg3b);
        this.rLeg2a = root.getChild("rLeg2a");
        this.parts.put("rLeg2a", this.rLeg2a);
        this.rLeg2c = this.rLeg2a.getChild("rLeg2c");
        this.parts.put("rLeg2c", this.rLeg2c);
        this.rLeg2d = this.rLeg2a.getChild("rLeg2d");
        this.parts.put("rLeg2d", this.rLeg2d);
        this.rLeg2b = this.rLeg2a.getChild("rLeg2b");
        this.parts.put("rLeg2b", this.rLeg2b);
        this.spine2 = root.getChild("spine2");
        this.parts.put("spine2", this.spine2);
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
        PartDefinition p_tail01 = root.addOrReplaceChild("tail01",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(-0.1F, 20.6F, 2.9F));
        PartDefinition p_tailVertebrae01b = p_tail01.addOrReplaceChild("tailVertebrae01b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.0F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae01 = p_tail01.addOrReplaceChild("tailVertebrae01",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail02 = p_tail01.addOrReplaceChild("tail02",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.4F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.7F));
        PartDefinition p_tailVertebrae02b = p_tail02.addOrReplaceChild("tailVertebrae02b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.6F, 0.4F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae02 = p_tail02.addOrReplaceChild("tailVertebrae02",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.4F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail03 = p_tail02.addOrReplaceChild("tail03",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.0F));
        PartDefinition p_tailVertebrae03 = p_tail03.addOrReplaceChild("tailVertebrae03",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.5F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail04 = p_tail03.addOrReplaceChild("tail04",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tailVertebrae04b = p_tail04.addOrReplaceChild("tailVertebrae04b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail05 = p_tail04.addOrReplaceChild("tail05",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tail06 = p_tail05.addOrReplaceChild("tail06",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tailVertebrae06b = p_tail06.addOrReplaceChild("tailVertebrae06b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail07 = p_tail06.addOrReplaceChild("tail07",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tailVertebrae07b = p_tail07.addOrReplaceChild("tailVertebrae07b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail08 = p_tail07.addOrReplaceChild("tail08",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tailVertebrae08b = p_tail08.addOrReplaceChild("tailVertebrae08b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail09 = p_tail08.addOrReplaceChild("tail09",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tailVertebrae09b = p_tail09.addOrReplaceChild("tailVertebrae09b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae09 = p_tail09.addOrReplaceChild("tailVertebrae09",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.5F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail10 = p_tail09.addOrReplaceChild("tail10",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tailVertebrae10b = p_tail10.addOrReplaceChild("tailVertebrae10b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae10 = p_tail10.addOrReplaceChild("tailVertebrae10",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.5F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail11 = p_tail10.addOrReplaceChild("tail11",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tailVertebrae11b = p_tail11.addOrReplaceChild("tailVertebrae11b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae11 = p_tail11.addOrReplaceChild("tailVertebrae11",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.5F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail12 = p_tail11.addOrReplaceChild("tail12",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tailVertebrae12 = p_tail12.addOrReplaceChild("tailVertebrae12",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.5F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail13 = p_tail12.addOrReplaceChild("tail13",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tailVertebrae13b = p_tail13.addOrReplaceChild("tailVertebrae13b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae13 = p_tail13.addOrReplaceChild("tailVertebrae13",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.5F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail14 = p_tail13.addOrReplaceChild("tail14",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tailVertebrae14 = p_tail14.addOrReplaceChild("tailVertebrae14",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.5F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae14b = p_tail14.addOrReplaceChild("tailVertebrae14b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail15 = p_tail14.addOrReplaceChild("tail15",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tailVertebrae15 = p_tail15.addOrReplaceChild("tailVertebrae15",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.5F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tail16 = p_tail15.addOrReplaceChild("tail16",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tailVertebrae16b = p_tail16.addOrReplaceChild("tailVertebrae16b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae16 = p_tail16.addOrReplaceChild("tailVertebrae16",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.5F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae15b = p_tail15.addOrReplaceChild("tailVertebrae15b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae12b = p_tail12.addOrReplaceChild("tailVertebrae12b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae08 = p_tail08.addOrReplaceChild("tailVertebrae08",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.5F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae07 = p_tail07.addOrReplaceChild("tailVertebrae07",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.5F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae06 = p_tail06.addOrReplaceChild("tailVertebrae06",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.5F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae05b = p_tail05.addOrReplaceChild("tailVertebrae05b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae05 = p_tail05.addOrReplaceChild("tailVertebrae05",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.5F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae04 = p_tail04.addOrReplaceChild("tailVertebrae04",
                CubeListBuilder.create().texOffs(21, 24).addBox(-1.5F, 0.0F, 0.5F, 3.0F, 0.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_tailVertebrae03b = p_tail03.addOrReplaceChild("tailVertebrae03b",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.0F, -1.5F, 0.5F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_rLeg4a = root.addOrReplaceChild("rLeg4a",
                CubeListBuilder.create().texOffs(49, 0).addBox(-1.6F, -0.3F, -0.5F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-0.4F, 20.7F, 1.3F, 0.0F, 0.13962634015954636F, 0.0F));
        PartDefinition p_rLeg4d = p_rLeg4a.addOrReplaceChild("rLeg4d",
                CubeListBuilder.create().texOffs(52, 11).addBox(0.0F, 0.3F, -0.5F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(-5.6F, 1.3F, 0.0F, 0.0F, 0.0F, 0.17453292519943295F));
        PartDefinition p_rLeg4c = p_rLeg4a.addOrReplaceChild("rLeg4c",
                CubeListBuilder.create().texOffs(51, 6).addBox(-0.5F, -0.5F, -0.4F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(-3.6F, -0.9F, -0.1F, 0.0F, 0.0F, 0.5585053606381855F));
        PartDefinition p_rLeg4b = p_rLeg4a.addOrReplaceChild("rLeg4b",
                CubeListBuilder.create().texOffs(49, 3).addBox(-2.8F, -0.6F, -0.5F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-1.6F, 0.3F, 0.0F, 0.0F, 0.0F, 0.5462880558742251F));
        PartDefinition p_rLeg3a = root.addOrReplaceChild("rLeg3a",
                CubeListBuilder.create().texOffs(49, 0).addBox(-1.8F, -0.3F, -0.4F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-0.6F, 20.7F, 0.3F, 0.0F, -0.13962634015954636F, 0.0F));
        PartDefinition p_rLeg3c = p_rLeg3a.addOrReplaceChild("rLeg3c",
                CubeListBuilder.create().texOffs(51, 6).addBox(-0.3F, -0.7F, -0.4F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(-4.1F, -0.8F, 0.0F, 0.0F, 0.0F, 0.5585053606381855F));
        PartDefinition p_rLeg3b = p_rLeg3a.addOrReplaceChild("rLeg3b",
                CubeListBuilder.create().texOffs(49, 3).addBox(-3.1F, -0.3F, -0.5F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-1.3F, 0.1F, 0.1F, 0.0F, 0.0F, 0.5462880558742251F));
        PartDefinition p_rLeg3d = p_rLeg3a.addOrReplaceChild("rLeg3d",
                CubeListBuilder.create().texOffs(52, 11).addBox(-0.7F, 0.0F, -0.4F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(-5.3F, 1.8F, 0.0F, 0.0F, 0.0F, 0.17453292519943295F));
        PartDefinition p_rAirSac = root.addOrReplaceChild("rAirSac",
                CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, -0.3F, 0.1F, 1.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(-0.5F, 20.0F, 1.0F, 0.3665191429188092F, -0.33161255787892263F, 0.0F));
        PartDefinition p_vertebrae01 = root.addOrReplaceChild("vertebrae01",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.5F, -0.6F, 0.6F, 0.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-0.5F, 20.0F, -1.0F, 0.08726646259971647F, 0.0F, 0.0F));
        PartDefinition p_lLeg4a = root.addOrReplaceChild("lLeg4a",
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.1F, -0.3F, -0.5F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.4F, 20.7F, 1.3F, 0.0F, -0.13962634015954636F, 0.0F));
        PartDefinition p_lLeg4c = p_lLeg4a.addOrReplaceChild("lLeg4c",
                CubeListBuilder.create().texOffs(29, 6).addBox(-0.3F, -0.5F, -0.4F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(3.6F, -0.9F, -0.1F, 0.0F, 0.0F, -0.5585053606381855F));
        PartDefinition p_lLeg4d = p_lLeg4a.addOrReplaceChild("lLeg4d",
                CubeListBuilder.create().texOffs(30, 11).addBox(0.3F, 0.3F, -0.5F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(5.6F, 1.3F, 0.0F, 0.0F, 0.0F, -0.17453292519943295F));
        PartDefinition p_lLeg4b = p_lLeg4a.addOrReplaceChild("lLeg4b",
                CubeListBuilder.create().texOffs(27, 3).addBox(0.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(1.6F, 0.3F, 0.0F, 0.0F, 0.0F, -0.5585053606381855F));
        PartDefinition p_rLeg1a = root.addOrReplaceChild("rLeg1a",
                CubeListBuilder.create().texOffs(38, 0).addBox(-0.4F, -0.3F, -1.5F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-0.7F, 20.7F, -1.3F, 0.0F, 0.5585053606381855F, 0.0F));
        PartDefinition p_rLeg1d = p_rLeg1a.addOrReplaceChild("rLeg1d",
                CubeListBuilder.create().texOffs(38, 15).addBox(-0.4F, 0.4F, 0.0F, 1.0F, 2.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, -5.6F, -0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition p_rLeg1b = p_rLeg1a.addOrReplaceChild("rLeg1b",
                CubeListBuilder.create().texOffs(38, 4).addBox(-0.4F, -0.4F, -3.2F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.2F, -1.1F, -0.43214352279379603F, 0.0F, 0.0F));
        PartDefinition p_rLeg1c = p_rLeg1a.addOrReplaceChild("rLeg1c",
                CubeListBuilder.create().texOffs(38, 9).addBox(-0.4F, -0.5F, -0.4F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -0.9F, -3.7F, -0.5061454830783556F, 0.0F, 0.0F));
        PartDefinition p_lLeg2a = root.addOrReplaceChild("lLeg2a",
                CubeListBuilder.create().texOffs(27, 0).addBox(0.2F, -0.3F, -0.3F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.1F, 20.7F, -0.5F, 0.0F, 0.40072759625789806F, 0.0F));
        PartDefinition p_lLeg2d = p_lLeg2a.addOrReplaceChild("lLeg2d",
                CubeListBuilder.create().texOffs(30, 11).addBox(0.7F, 0.0F, -0.3F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(5.6F, 1.6F, 0.0F, 0.0F, 0.0F, -0.17453292519943295F));
        PartDefinition p_lLeg2b = p_lLeg2a.addOrReplaceChild("lLeg2b",
                CubeListBuilder.create().texOffs(27, 3).addBox(0.3F, -0.3F, -0.4F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(1.6F, 0.2F, 0.1F, 0.0F, 0.0F, -0.5462880558742251F));
        PartDefinition p_lLeg2c = p_lLeg2a.addOrReplaceChild("lLeg2c",
                CubeListBuilder.create().texOffs(29, 6).addBox(-0.1F, -0.7F, -0.2F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(3.9F, -0.7F, -0.1F, 0.0F, 0.0F, -0.5585053606381855F));
        PartDefinition p_spine = root.addOrReplaceChild("spine",
                CubeListBuilder.create().texOffs(0, 4).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-0.5F, 20.0F, -1.0F, 0.08726646259971647F, 0.0F, 0.0F));
        PartDefinition p_vertebrae03 = root.addOrReplaceChild("vertebrae03",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.5F, -1.2F, 2.9F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(-0.5F, 20.0F, -1.0F));
        PartDefinition p_lAirSac = root.addOrReplaceChild("lAirSac",
                CubeListBuilder.create().texOffs(0, 22).mirror().addBox(1.0F, -0.3F, 0.1F, 1.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(-0.5F, 20.0F, 1.0F, 0.3665191429188092F, 0.33161255787892263F, 0.0F));
        PartDefinition p_lLeg1a = root.addOrReplaceChild("lLeg1a",
                CubeListBuilder.create().texOffs(19, 0).addBox(-0.4F, -0.3F, -1.5F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.6F, 20.7F, -1.5F, 0.0F, -0.5585053606381855F, 0.0F));
        PartDefinition p_lLeg1d = p_lLeg1a.addOrReplaceChild("lLeg1d",
                CubeListBuilder.create().texOffs(21, 15).addBox(-0.4F, 0.4F, 0.0F, 1.0F, 2.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, -5.6F, -0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition p_lLeg1b = p_lLeg1a.addOrReplaceChild("lLeg1b",
                CubeListBuilder.create().texOffs(18, 4).addBox(-0.4F, -0.4F, -3.2F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.2F, -1.1F, -0.43214352279379603F, 0.0F, 0.0F));
        PartDefinition p_lLeg1c = p_lLeg1a.addOrReplaceChild("lLeg1c",
                CubeListBuilder.create().texOffs(20, 9).addBox(-0.4F, -0.5F, -0.4F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -0.9F, -3.7F, -0.5061454830783556F, 0.0F, 0.0F));
        PartDefinition p_bodyMain = root.addOrReplaceChild("bodyMain",
                CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, 0.7F, -0.5F, 3.0F, 1.0F, 5.0F),
                PartPose.offset(-0.5F, 20.0F, -1.0F));
        PartDefinition p_vertebrae02 = root.addOrReplaceChild("vertebrae02",
                CubeListBuilder.create().texOffs(30, 23).addBox(0.5F, -1.1F, 1.8F, 0.0F, 1.0F, 1.0F),
                PartPose.offset(-0.5F, 20.0F, -1.0F));
        PartDefinition p_lLeg3a = root.addOrReplaceChild("lLeg3a",
                CubeListBuilder.create().texOffs(27, 0).addBox(-0.1F, -0.3F, -0.4F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.6F, 20.7F, 0.3F, 0.0F, 0.13962634015954636F, 0.0F));
        PartDefinition p_lLeg3c = p_lLeg3a.addOrReplaceChild("lLeg3c",
                CubeListBuilder.create().texOffs(29, 6).addBox(-0.7F, -0.7F, -0.4F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(4.1F, -0.8F, 0.0F, 0.0F, 0.0F, -0.5585053606381855F));
        PartDefinition p_lLeg3d = p_lLeg3a.addOrReplaceChild("lLeg3d",
                CubeListBuilder.create().texOffs(30, 11).addBox(0.7F, 0.0F, -0.4F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(5.3F, 1.8F, 0.0F, 0.0F, 0.0F, -0.17453292519943295F));
        PartDefinition p_lLeg3b = p_lLeg3a.addOrReplaceChild("lLeg3b",
                CubeListBuilder.create().texOffs(27, 3).addBox(0.1F, -0.3F, -0.5F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(1.3F, 0.1F, 0.1F, 0.0F, 0.0F, -0.5462880558742251F));
        PartDefinition p_rLeg2a = root.addOrReplaceChild("rLeg2a",
                CubeListBuilder.create().texOffs(49, 0).addBox(-1.5F, -0.3F, -0.7F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-0.8F, 20.7F, -0.5F, 0.0F, -0.40072759625789806F, 0.0F));
        PartDefinition p_rLeg2c = p_rLeg2a.addOrReplaceChild("rLeg2c",
                CubeListBuilder.create().texOffs(51, 6).addBox(-0.2F, -1.1F, -0.6F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(-3.9F, -0.7F, -0.1F, 0.0F, 0.0F, 0.5585053606381855F));
        PartDefinition p_rLeg2d = p_rLeg2a.addOrReplaceChild("rLeg2d",
                CubeListBuilder.create().texOffs(52, 11).addBox(0.2F, 0.0F, -0.7F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(-5.6F, 1.6F, 0.0F, 0.0F, 0.0F, 0.17941984710501704F));
        PartDefinition p_rLeg2b = p_rLeg2a.addOrReplaceChild("rLeg2b",
                CubeListBuilder.create().texOffs(49, 3).addBox(-2.5F, -0.7F, -0.8F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-1.6F, 0.2F, 0.1F, 0.0F, 0.0F, 0.5462880558742251F));
        PartDefinition p_spine2 = root.addOrReplaceChild("spine2",
                CubeListBuilder.create().texOffs(0, 10).addBox(0.0F, -0.3F, 1.9F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(-0.5F, 20.0F, -1.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
