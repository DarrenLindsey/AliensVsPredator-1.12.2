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
 * Geometry converted automatically from the 1.12.2 ModelFacehugger by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class FacehuggerModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "facehugger"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart lLeg1a;
    public final ModelPart lLeg1b;
    public final ModelPart lLeg1c;
    public final ModelPart lLeg1d;
    public final ModelPart rLeg3a;
    public final ModelPart rLeg3c;
    public final ModelPart rLeg3b;
    public final ModelPart rLeg3d;
    public final ModelPart lLeg4a;
    public final ModelPart lLeg4c;
    public final ModelPart lLeg4d;
    public final ModelPart lLeg4b;
    public final ModelPart bodyMain;
    public final ModelPart lAirSac;
    public final ModelPart spine;
    public final ModelPart rAirSac;
    public final ModelPart rLeg1a;
    public final ModelPart rLeg1c;
    public final ModelPart rLeg1b;
    public final ModelPart rLeg1d;
    public final ModelPart lLeg2a;
    public final ModelPart lLeg2c;
    public final ModelPart lLeg2b;
    public final ModelPart lLeg2d;
    public final ModelPart rLeg2a;
    public final ModelPart rLeg2c;
    public final ModelPart rLeg2b;
    public final ModelPart rLeg2d;
    public final ModelPart lLeg3a;
    public final ModelPart lLeg3b;
    public final ModelPart lLeg3d;
    public final ModelPart lLeg3c;
    public final ModelPart rLeg4a;
    public final ModelPart rLeg4b;
    public final ModelPart rLeg4d;
    public final ModelPart rLeg4c;
    public final ModelPart spine2;
    public final ModelPart tail01;
    public final ModelPart tail02;
    public final ModelPart tail03;
    public final ModelPart tail04;
    public final ModelPart tail05;
    public final ModelPart tail06;
    public final ModelPart tail07;
    public final ModelPart tail08;
    public final ModelPart tail09;
    public final ModelPart tail10;
    public final ModelPart tail11;
    public final ModelPart tail12;
    public final ModelPart tail13;
    public final ModelPart tail14;
    public final ModelPart tail15;

    public FacehuggerModel(ModelPart root)
    {
        this.root = root;
        this.lLeg1a = root.getChild("lLeg1a");
        this.parts.put("lLeg1a", this.lLeg1a);
        this.lLeg1b = this.lLeg1a.getChild("lLeg1b");
        this.parts.put("lLeg1b", this.lLeg1b);
        this.lLeg1c = this.lLeg1a.getChild("lLeg1c");
        this.parts.put("lLeg1c", this.lLeg1c);
        this.lLeg1d = this.lLeg1a.getChild("lLeg1d");
        this.parts.put("lLeg1d", this.lLeg1d);
        this.rLeg3a = root.getChild("rLeg3a");
        this.parts.put("rLeg3a", this.rLeg3a);
        this.rLeg3c = this.rLeg3a.getChild("rLeg3c");
        this.parts.put("rLeg3c", this.rLeg3c);
        this.rLeg3b = this.rLeg3a.getChild("rLeg3b");
        this.parts.put("rLeg3b", this.rLeg3b);
        this.rLeg3d = this.rLeg3a.getChild("rLeg3d");
        this.parts.put("rLeg3d", this.rLeg3d);
        this.lLeg4a = root.getChild("lLeg4a");
        this.parts.put("lLeg4a", this.lLeg4a);
        this.lLeg4c = this.lLeg4a.getChild("lLeg4c");
        this.parts.put("lLeg4c", this.lLeg4c);
        this.lLeg4d = this.lLeg4a.getChild("lLeg4d");
        this.parts.put("lLeg4d", this.lLeg4d);
        this.lLeg4b = this.lLeg4a.getChild("lLeg4b");
        this.parts.put("lLeg4b", this.lLeg4b);
        this.bodyMain = root.getChild("bodyMain");
        this.parts.put("bodyMain", this.bodyMain);
        this.lAirSac = root.getChild("lAirSac");
        this.parts.put("lAirSac", this.lAirSac);
        this.spine = root.getChild("spine");
        this.parts.put("spine", this.spine);
        this.rAirSac = root.getChild("rAirSac");
        this.parts.put("rAirSac", this.rAirSac);
        this.rLeg1a = root.getChild("rLeg1a");
        this.parts.put("rLeg1a", this.rLeg1a);
        this.rLeg1c = this.rLeg1a.getChild("rLeg1c");
        this.parts.put("rLeg1c", this.rLeg1c);
        this.rLeg1b = this.rLeg1a.getChild("rLeg1b");
        this.parts.put("rLeg1b", this.rLeg1b);
        this.rLeg1d = this.rLeg1a.getChild("rLeg1d");
        this.parts.put("rLeg1d", this.rLeg1d);
        this.lLeg2a = root.getChild("lLeg2a");
        this.parts.put("lLeg2a", this.lLeg2a);
        this.lLeg2c = this.lLeg2a.getChild("lLeg2c");
        this.parts.put("lLeg2c", this.lLeg2c);
        this.lLeg2b = this.lLeg2a.getChild("lLeg2b");
        this.parts.put("lLeg2b", this.lLeg2b);
        this.lLeg2d = this.lLeg2a.getChild("lLeg2d");
        this.parts.put("lLeg2d", this.lLeg2d);
        this.rLeg2a = root.getChild("rLeg2a");
        this.parts.put("rLeg2a", this.rLeg2a);
        this.rLeg2c = this.rLeg2a.getChild("rLeg2c");
        this.parts.put("rLeg2c", this.rLeg2c);
        this.rLeg2b = this.rLeg2a.getChild("rLeg2b");
        this.parts.put("rLeg2b", this.rLeg2b);
        this.rLeg2d = this.rLeg2a.getChild("rLeg2d");
        this.parts.put("rLeg2d", this.rLeg2d);
        this.lLeg3a = root.getChild("lLeg3a");
        this.parts.put("lLeg3a", this.lLeg3a);
        this.lLeg3b = this.lLeg3a.getChild("lLeg3b");
        this.parts.put("lLeg3b", this.lLeg3b);
        this.lLeg3d = this.lLeg3a.getChild("lLeg3d");
        this.parts.put("lLeg3d", this.lLeg3d);
        this.lLeg3c = this.lLeg3a.getChild("lLeg3c");
        this.parts.put("lLeg3c", this.lLeg3c);
        this.rLeg4a = root.getChild("rLeg4a");
        this.parts.put("rLeg4a", this.rLeg4a);
        this.rLeg4b = this.rLeg4a.getChild("rLeg4b");
        this.parts.put("rLeg4b", this.rLeg4b);
        this.rLeg4d = this.rLeg4a.getChild("rLeg4d");
        this.parts.put("rLeg4d", this.rLeg4d);
        this.rLeg4c = this.rLeg4a.getChild("rLeg4c");
        this.parts.put("rLeg4c", this.rLeg4c);
        this.spine2 = root.getChild("spine2");
        this.parts.put("spine2", this.spine2);
        this.tail01 = root.getChild("tail01");
        this.parts.put("tail01", this.tail01);
        this.tail02 = this.tail01.getChild("tail02");
        this.parts.put("tail02", this.tail02);
        this.tail03 = this.tail02.getChild("tail03");
        this.parts.put("tail03", this.tail03);
        this.tail04 = this.tail03.getChild("tail04");
        this.parts.put("tail04", this.tail04);
        this.tail05 = this.tail04.getChild("tail05");
        this.parts.put("tail05", this.tail05);
        this.tail06 = this.tail05.getChild("tail06");
        this.parts.put("tail06", this.tail06);
        this.tail07 = this.tail06.getChild("tail07");
        this.parts.put("tail07", this.tail07);
        this.tail08 = this.tail07.getChild("tail08");
        this.parts.put("tail08", this.tail08);
        this.tail09 = this.tail08.getChild("tail09");
        this.parts.put("tail09", this.tail09);
        this.tail10 = this.tail09.getChild("tail10");
        this.parts.put("tail10", this.tail10);
        this.tail11 = this.tail10.getChild("tail11");
        this.parts.put("tail11", this.tail11);
        this.tail12 = this.tail11.getChild("tail12");
        this.parts.put("tail12", this.tail12);
        this.tail13 = this.tail12.getChild("tail13");
        this.parts.put("tail13", this.tail13);
        this.tail14 = this.tail13.getChild("tail14");
        this.parts.put("tail14", this.tail14);
        this.tail15 = this.tail14.getChild("tail15");
        this.parts.put("tail15", this.tail15);
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
        PartDefinition p_lLeg1a = root.addOrReplaceChild("lLeg1a",
                CubeListBuilder.create().texOffs(19, 0).addBox(-0.4F, -0.3F, -1.5F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.6F, 20.7F, -1.5F, 0.0F, -0.5585053606381855F, 0.0F));
        PartDefinition p_lLeg1b = p_lLeg1a.addOrReplaceChild("lLeg1b",
                CubeListBuilder.create().texOffs(18, 4).addBox(-0.4F, -0.4F, -3.2F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.2F, -1.1F, -0.43214352279379603F, 0.0F, 0.0F));
        PartDefinition p_lLeg1c = p_lLeg1a.addOrReplaceChild("lLeg1c",
                CubeListBuilder.create().texOffs(20, 9).addBox(-0.4F, -0.5F, -0.4F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -0.9F, -3.7F, -0.5061454830783556F, 0.0F, 0.0F));
        PartDefinition p_lLeg1d = p_lLeg1a.addOrReplaceChild("lLeg1d",
                CubeListBuilder.create().texOffs(21, 15).addBox(-0.4F, 0.4F, 0.0F, 1.0F, 2.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, -5.6F, -0.17453292519943295F, 0.0F, 0.0F));
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
        PartDefinition p_lLeg4a = root.addOrReplaceChild("lLeg4a",
                CubeListBuilder.create().texOffs(27, 0).addBox(-0.1F, -0.3F, -0.5F, 2.0F, 1.0F, 1.0F),
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
        PartDefinition p_bodyMain = root.addOrReplaceChild("bodyMain",
                CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, 0.7F, -1.1F, 3.0F, 1.0F, 5.0F),
                PartPose.offset(-0.5F, 20.0F, -1.0F));
        PartDefinition p_lAirSac = root.addOrReplaceChild("lAirSac",
                CubeListBuilder.create().texOffs(0, 22).mirror().addBox(1.6F, 0.7F, 0.3F, 3.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-0.5F, 20.0F, 1.0F, 0.0F, -0.3490658503988659F, 0.0F));
        PartDefinition p_spine = root.addOrReplaceChild("spine",
                CubeListBuilder.create().texOffs(0, 4).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-0.5F, 20.0F, -1.0F, 0.08726646259971647F, 0.0F, 0.0F));
        PartDefinition p_rAirSac = root.addOrReplaceChild("rAirSac",
                CubeListBuilder.create().texOffs(0, 22).addBox(-3.6F, 0.7F, 0.6F, 3.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-0.5F, 20.0F, 1.0F, 0.0F, 0.3490658503988659F, 0.0F));
        PartDefinition p_rLeg1a = root.addOrReplaceChild("rLeg1a",
                CubeListBuilder.create().texOffs(38, 0).addBox(-0.4F, -0.3F, -1.5F, 1.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(-0.7F, 20.7F, -1.3F, 0.0F, 0.5585053606381855F, 0.0F));
        PartDefinition p_rLeg1c = p_rLeg1a.addOrReplaceChild("rLeg1c",
                CubeListBuilder.create().texOffs(38, 9).addBox(-0.4F, -0.5F, -0.4F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -0.9F, -3.7F, -0.5061454830783556F, 0.0F, 0.0F));
        PartDefinition p_rLeg1b = p_rLeg1a.addOrReplaceChild("rLeg1b",
                CubeListBuilder.create().texOffs(38, 4).addBox(-0.4F, -0.4F, -3.2F, 1.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.2F, -1.1F, -0.43214352279379603F, 0.0F, 0.0F));
        PartDefinition p_rLeg1d = p_rLeg1a.addOrReplaceChild("rLeg1d",
                CubeListBuilder.create().texOffs(38, 15).addBox(-0.4F, 0.4F, 0.0F, 1.0F, 2.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 1.5F, -5.6F, -0.17453292519943295F, 0.0F, 0.0F));
        PartDefinition p_lLeg2a = root.addOrReplaceChild("lLeg2a",
                CubeListBuilder.create().texOffs(27, 0).addBox(0.2F, -0.3F, -0.3F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.1F, 20.7F, -0.5F, 0.0F, 0.40072759625789806F, 0.0F));
        PartDefinition p_lLeg2c = p_lLeg2a.addOrReplaceChild("lLeg2c",
                CubeListBuilder.create().texOffs(29, 6).addBox(-0.1F, -0.7F, -0.2F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(3.9F, -0.7F, -0.1F, 0.0F, 0.0F, -0.5585053606381855F));
        PartDefinition p_lLeg2b = p_lLeg2a.addOrReplaceChild("lLeg2b",
                CubeListBuilder.create().texOffs(27, 3).addBox(0.3F, -0.3F, -0.4F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(1.6F, 0.2F, 0.1F, 0.0F, 0.0F, -0.5462880558742251F));
        PartDefinition p_lLeg2d = p_lLeg2a.addOrReplaceChild("lLeg2d",
                CubeListBuilder.create().texOffs(30, 11).addBox(0.7F, 0.0F, -0.3F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(5.6F, 1.6F, 0.0F, 0.0F, 0.0F, -0.17453292519943295F));
        PartDefinition p_rLeg2a = root.addOrReplaceChild("rLeg2a",
                CubeListBuilder.create().texOffs(49, 0).addBox(-1.5F, -0.3F, -0.7F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-0.8F, 20.7F, -0.5F, 0.0F, -0.40072759625789806F, 0.0F));
        PartDefinition p_rLeg2c = p_rLeg2a.addOrReplaceChild("rLeg2c",
                CubeListBuilder.create().texOffs(51, 6).addBox(-0.2F, -1.1F, -0.6F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(-3.9F, -0.7F, -0.1F, 0.0F, 0.0F, 0.5585053606381855F));
        PartDefinition p_rLeg2b = p_rLeg2a.addOrReplaceChild("rLeg2b",
                CubeListBuilder.create().texOffs(49, 3).addBox(-2.5F, -0.7F, -0.8F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-1.6F, 0.2F, 0.1F, 0.0F, 0.0F, 0.5462880558742251F));
        PartDefinition p_rLeg2d = p_rLeg2a.addOrReplaceChild("rLeg2d",
                CubeListBuilder.create().texOffs(52, 11).addBox(0.2F, 0.0F, -0.7F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(-5.6F, 1.6F, 0.0F, 0.0F, 0.0F, 0.17941984710501704F));
        PartDefinition p_lLeg3a = root.addOrReplaceChild("lLeg3a",
                CubeListBuilder.create().texOffs(27, 0).addBox(-0.1F, -0.3F, -0.4F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(0.6F, 20.7F, 0.3F, 0.0F, 0.13962634015954636F, 0.0F));
        PartDefinition p_lLeg3b = p_lLeg3a.addOrReplaceChild("lLeg3b",
                CubeListBuilder.create().texOffs(27, 3).addBox(0.1F, -0.3F, -0.5F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(1.3F, 0.1F, 0.1F, 0.0F, 0.0F, -0.5462880558742251F));
        PartDefinition p_lLeg3d = p_lLeg3a.addOrReplaceChild("lLeg3d",
                CubeListBuilder.create().texOffs(30, 11).addBox(0.7F, 0.0F, -0.4F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(5.3F, 1.8F, 0.0F, 0.0F, 0.0F, -0.17453292519943295F));
        PartDefinition p_lLeg3c = p_lLeg3a.addOrReplaceChild("lLeg3c",
                CubeListBuilder.create().texOffs(29, 6).addBox(-0.7F, -0.7F, -0.4F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(4.1F, -0.8F, 0.0F, 0.0F, 0.0F, -0.5585053606381855F));
        PartDefinition p_rLeg4a = root.addOrReplaceChild("rLeg4a",
                CubeListBuilder.create().texOffs(49, 0).addBox(-1.6F, -0.3F, -0.5F, 2.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-0.4F, 20.7F, 1.3F, 0.0F, 0.13962634015954636F, 0.0F));
        PartDefinition p_rLeg4b = p_rLeg4a.addOrReplaceChild("rLeg4b",
                CubeListBuilder.create().texOffs(49, 3).addBox(-2.8F, -0.6F, -0.5F, 3.0F, 1.0F, 1.0F),
                PartPose.offsetAndRotation(-1.6F, 0.3F, 0.0F, 0.0F, 0.0F, 0.5462880558742251F));
        PartDefinition p_rLeg4d = p_rLeg4a.addOrReplaceChild("rLeg4d",
                CubeListBuilder.create().texOffs(52, 11).addBox(0.0F, 0.3F, -0.5F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(-5.6F, 1.3F, 0.0F, 0.0F, 0.0F, 0.17453292519943295F));
        PartDefinition p_rLeg4c = p_rLeg4a.addOrReplaceChild("rLeg4c",
                CubeListBuilder.create().texOffs(51, 6).addBox(-0.5F, -0.5F, -0.4F, 1.0F, 4.0F, 1.0F),
                PartPose.offsetAndRotation(-3.6F, -0.9F, -0.1F, 0.0F, 0.0F, 0.5585053606381855F));
        PartDefinition p_spine2 = root.addOrReplaceChild("spine2",
                CubeListBuilder.create().texOffs(0, 10).addBox(0.0F, -0.3F, 1.9F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(-0.5F, 20.0F, -1.0F));
        PartDefinition p_tail01 = root.addOrReplaceChild("tail01",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(-0.1F, 20.6F, 2.9F));
        PartDefinition p_tail02 = p_tail01.addOrReplaceChild("tail02",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.4F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 0.7F));
        PartDefinition p_tail03 = p_tail02.addOrReplaceChild("tail03",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.0F));
        PartDefinition p_tail04 = p_tail03.addOrReplaceChild("tail04",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tail05 = p_tail04.addOrReplaceChild("tail05",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tail06 = p_tail05.addOrReplaceChild("tail06",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tail07 = p_tail06.addOrReplaceChild("tail07",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tail08 = p_tail07.addOrReplaceChild("tail08",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tail09 = p_tail08.addOrReplaceChild("tail09",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tail10 = p_tail09.addOrReplaceChild("tail10",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tail11 = p_tail10.addOrReplaceChild("tail11",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tail12 = p_tail11.addOrReplaceChild("tail12",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tail13 = p_tail12.addOrReplaceChild("tail13",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tail14 = p_tail13.addOrReplaceChild("tail14",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        PartDefinition p_tail15 = p_tail14.addOrReplaceChild("tail15",
                CubeListBuilder.create().texOffs(28, 21).addBox(-0.4F, -0.5F, 0.5F, 1.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 0.0F, 1.1F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
