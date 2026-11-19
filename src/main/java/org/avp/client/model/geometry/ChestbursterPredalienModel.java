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
 * Geometry converted automatically from the 1.12.2 ModelChestbursterPredalien by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class ChestbursterPredalienModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "chestburster_predalien"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart body7;
    public final ModelPart tail2;
    public final ModelPart tail1;
    public final ModelPart tail3;
    public final ModelPart tail4;
    public final ModelPart tail5;
    public final ModelPart tail6;
    public final ModelPart tail7;
    public final ModelPart body14;
    public final ModelPart lFangTop;
    public final ModelPart rtendon;
    public final ModelPart body8;
    public final ModelPart lMandible1;
    public final ModelPart rMandibleTop;
    public final ModelPart body1;
    public final ModelPart body17;
    public final ModelPart body15;
    public final ModelPart rFangLower;
    public final ModelPart body5;
    public final ModelPart body13;
    public final ModelPart body3;
    public final ModelPart body16;
    public final ModelPart body11;
    public final ModelPart rFangTop;
    public final ModelPart body12;
    public final ModelPart mouth;
    public final ModelPart body9;
    public final ModelPart lMandibleTop;
    public final ModelPart ltendon;
    public final ModelPart body2;
    public final ModelPart body4;
    public final ModelPart lFangLower;
    public final ModelPart body10;
    public final ModelPart rMandible1;

    public ChestbursterPredalienModel(ModelPart root)
    {
        this.root = root;
        this.body7 = root.getChild("body7");
        this.parts.put("body7", this.body7);
        this.tail2 = root.getChild("tail2");
        this.parts.put("tail2", this.tail2);
        this.tail1 = this.tail2.getChild("tail1");
        this.parts.put("tail1", this.tail1);
        this.tail3 = this.tail1.getChild("tail3");
        this.parts.put("tail3", this.tail3);
        this.tail4 = this.tail3.getChild("tail4");
        this.parts.put("tail4", this.tail4);
        this.tail5 = this.tail4.getChild("tail5");
        this.parts.put("tail5", this.tail5);
        this.tail6 = this.tail5.getChild("tail6");
        this.parts.put("tail6", this.tail6);
        this.tail7 = this.tail6.getChild("tail7");
        this.parts.put("tail7", this.tail7);
        this.body14 = root.getChild("body14");
        this.parts.put("body14", this.body14);
        this.lFangTop = root.getChild("lFangTop");
        this.parts.put("lFangTop", this.lFangTop);
        this.rtendon = root.getChild("rtendon");
        this.parts.put("rtendon", this.rtendon);
        this.body8 = root.getChild("body8");
        this.parts.put("body8", this.body8);
        this.lMandible1 = root.getChild("lMandible1");
        this.parts.put("lMandible1", this.lMandible1);
        this.rMandibleTop = root.getChild("rMandibleTop");
        this.parts.put("rMandibleTop", this.rMandibleTop);
        this.body1 = root.getChild("body1");
        this.parts.put("body1", this.body1);
        this.body17 = root.getChild("body17");
        this.parts.put("body17", this.body17);
        this.body15 = root.getChild("body15");
        this.parts.put("body15", this.body15);
        this.rFangLower = root.getChild("rFangLower");
        this.parts.put("rFangLower", this.rFangLower);
        this.body5 = root.getChild("body5");
        this.parts.put("body5", this.body5);
        this.body13 = root.getChild("body13");
        this.parts.put("body13", this.body13);
        this.body3 = root.getChild("body3");
        this.parts.put("body3", this.body3);
        this.body16 = root.getChild("body16");
        this.parts.put("body16", this.body16);
        this.body11 = root.getChild("body11");
        this.parts.put("body11", this.body11);
        this.rFangTop = root.getChild("rFangTop");
        this.parts.put("rFangTop", this.rFangTop);
        this.body12 = root.getChild("body12");
        this.parts.put("body12", this.body12);
        this.mouth = root.getChild("mouth");
        this.parts.put("mouth", this.mouth);
        this.body9 = root.getChild("body9");
        this.parts.put("body9", this.body9);
        this.lMandibleTop = root.getChild("lMandibleTop");
        this.parts.put("lMandibleTop", this.lMandibleTop);
        this.ltendon = root.getChild("ltendon");
        this.parts.put("ltendon", this.ltendon);
        this.body2 = root.getChild("body2");
        this.parts.put("body2", this.body2);
        this.body4 = root.getChild("body4");
        this.parts.put("body4", this.body4);
        this.lFangLower = root.getChild("lFangLower");
        this.parts.put("lFangLower", this.lFangLower);
        this.body10 = root.getChild("body10");
        this.parts.put("body10", this.body10);
        this.rMandible1 = root.getChild("rMandible1");
        this.parts.put("rMandible1", this.rMandible1);
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
        PartDefinition p_body7 = root.addOrReplaceChild("body7",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -10.0F, 0.8999999761581421F, 4.0F, 9.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 1.3089970350265503F, 0.0F, 0.0F));
        PartDefinition p_tail2 = root.addOrReplaceChild("tail2",
                CubeListBuilder.create().texOffs(12, 13).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 3.0F, 4.0F),
                PartPose.offset(0.0F, 22.5F, 5.0F));
        PartDefinition p_tail1 = p_tail2.addOrReplaceChild("tail1",
                CubeListBuilder.create().texOffs(12, 13).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 3.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 4.0F));
        PartDefinition p_tail3 = p_tail1.addOrReplaceChild("tail3",
                CubeListBuilder.create().texOffs(28, 12).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, 0.5F, 4.1F));
        PartDefinition p_tail4 = p_tail3.addOrReplaceChild("tail4",
                CubeListBuilder.create().texOffs(28, 12).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 4.0F));
        PartDefinition p_tail5 = p_tail4.addOrReplaceChild("tail5",
                CubeListBuilder.create().texOffs(28, 12).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 3.7F));
        PartDefinition p_tail6 = p_tail5.addOrReplaceChild("tail6",
                CubeListBuilder.create().texOffs(42, 12).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.4F, 4.1F));
        PartDefinition p_tail7 = p_tail6.addOrReplaceChild("tail7",
                CubeListBuilder.create().texOffs(54, 10).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 4.1F));
        PartDefinition p_body14 = root.addOrReplaceChild("body14",
                CubeListBuilder.create().texOffs(48, 10).addBox(-1.5F, -1.2000000476837158F, -2.700000047683716F, 1.0F, 0.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.7853981852531433F, 0.0F, 0.13962629437446594F));
        PartDefinition p_lFangTop = root.addOrReplaceChild("lFangTop",
                CubeListBuilder.create().texOffs(5, 26).addBox(-0.9F, -2.7F, -10.8F, 2.0F, 1.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, -0.136659280431156F, -0.12217304763960307F, 0.0F));
        PartDefinition p_rtendon = root.addOrReplaceChild("rtendon",
                CubeListBuilder.create().texOffs(32, 21).addBox(-0.7F, -7.2F, -7.2F, 0.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.36425021489121656F, 0.12217304763960307F, 0.0F));
        PartDefinition p_body8 = root.addOrReplaceChild("body8",
                CubeListBuilder.create().texOffs(42, 6).addBox(-1.5F, -0.5F, -1.0F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.2967059910297394F, 0.0F, 0.0F));
        PartDefinition p_lMandible1 = root.addOrReplaceChild("lMandible1",
                CubeListBuilder.create().texOffs(15, 24).addBox(0.0F, -4.5F, -8.4F, 1.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.36425021489121656F, -0.12217304763960307F, 0.0F));
        PartDefinition p_rMandibleTop = root.addOrReplaceChild("rMandibleTop",
                CubeListBuilder.create().texOffs(15, 24).addBox(-1.4F, -2.7F, -11.1F, 1.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, -0.136659280431156F, 0.12217304763960307F, 0.0F));
        PartDefinition p_body1 = root.addOrReplaceChild("body1",
                CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, 0.0F, 1.0F, 4.0F, 3.0F, 4.0F),
                PartPose.offset(0.0F, 21.0F, 0.0F));
        PartDefinition p_body17 = root.addOrReplaceChild("body17",
                CubeListBuilder.create().texOffs(28, 18).addBox(-2.0F, -2.0999999046325684F, 0.4000000059604645F, 4.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 1.0122909545898438F, 0.0F, 0.0F));
        PartDefinition p_body15 = root.addOrReplaceChild("body15",
                CubeListBuilder.create().texOffs(58, 6).addBox(0.5F, -1.2000000476837158F, -1.7000000476837158F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.7853981852531433F, 0.0F, -0.13962629437446594F));
        PartDefinition p_rFangLower = root.addOrReplaceChild("rFangLower",
                CubeListBuilder.create().texOffs(0, 27).addBox(-0.5F, -6.0F, -8.2F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.36425021489121656F, 0.12217304763960307F, 0.0F));
        PartDefinition p_body5 = root.addOrReplaceChild("body5",
                CubeListBuilder.create().texOffs(30, 4).addBox(-1.5F, -2.799999952316284F, -0.800000011920929F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.8377580046653748F, 0.0F, 0.0F));
        PartDefinition p_body13 = root.addOrReplaceChild("body13",
                CubeListBuilder.create().texOffs(42, 10).addBox(0.5F, -1.2000000476837158F, -2.700000047683716F, 1.0F, 0.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.7853981852531433F, 0.0F, -0.13962629437446594F));
        PartDefinition p_body3 = root.addOrReplaceChild("body3",
                CubeListBuilder.create().texOffs(42, 0).addBox(-1.5F, -5.0F, -0.10000000149011612F, 3.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 1.1344640254974365F, 0.0F, 0.0F));
        PartDefinition p_body16 = root.addOrReplaceChild("body16",
                CubeListBuilder.create().texOffs(58, 6).addBox(-1.5F, -1.2000000476837158F, -1.7000000476837158F, 1.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.7853981852531433F, 0.0F, 0.13962629437446594F));
        PartDefinition p_body11 = root.addOrReplaceChild("body11",
                CubeListBuilder.create().texOffs(54, 6).addBox(1.0F, -1.7999999523162842F, -1.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.5585054159164429F, 0.0F, -0.13962629437446594F));
        PartDefinition p_rFangTop = root.addOrReplaceChild("rFangTop",
                CubeListBuilder.create().texOffs(0, 26).addBox(-1.0F, -2.7F, -10.8F, 2.0F, 1.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, -0.136659280431156F, 0.12217304763960307F, 0.0F));
        PartDefinition p_body12 = root.addOrReplaceChild("body12",
                CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -10.5F, 1.5F, 3.0F, 9.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 1.3089970350265503F, 0.0F, 0.0F));
        PartDefinition p_mouth = root.addOrReplaceChild("mouth",
                CubeListBuilder.create().texOffs(54, 0).addBox(-1.5F, -9.0F, 1.0F, 3.0F, 5.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 1.4137170314788818F, 0.0F, 0.0F));
        PartDefinition p_body9 = root.addOrReplaceChild("body9",
                CubeListBuilder.create().texOffs(30, 10).addBox(-2.5F, -1.7999999523162842F, -2.5F, 5.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 21.0F, 0.0F));
        PartDefinition p_lMandibleTop = root.addOrReplaceChild("lMandibleTop",
                CubeListBuilder.create().texOffs(15, 24).addBox(0.4F, -2.7F, -11.1F, 1.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, -0.136659280431156F, -0.12217304763960307F, 0.0F));
        PartDefinition p_ltendon = root.addOrReplaceChild("ltendon",
                CubeListBuilder.create().texOffs(32, 21).addBox(0.6F, -7.2F, -7.2F, 0.0F, 3.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.36425021489121656F, -0.12217304763960307F, 0.0F));
        PartDefinition p_body2 = root.addOrReplaceChild("body2",
                CubeListBuilder.create().texOffs(30, 0).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.2967059910297394F, 0.0F, 0.0F));
        PartDefinition p_body4 = root.addOrReplaceChild("body4",
                CubeListBuilder.create().texOffs(14, 7).addBox(-2.0F, -1.7999999523162842F, 0.0F, 4.0F, 3.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, -0.8552113175392152F, 0.0F, 0.0F));
        PartDefinition p_lFangLower = root.addOrReplaceChild("lFangLower",
                CubeListBuilder.create().texOffs(0, 27).addBox(0.5F, -6.0F, -8.2F, 0.0F, 2.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.36425021489121656F, -0.12217304763960307F, 0.0F));
        PartDefinition p_body10 = root.addOrReplaceChild("body10",
                CubeListBuilder.create().texOffs(54, 6).addBox(-2.0F, -1.7999999523162842F, -1.5F, 1.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.5585054159164429F, 0.0F, 0.13962629437446594F));
        PartDefinition p_rMandible1 = root.addOrReplaceChild("rMandible1",
                CubeListBuilder.create().texOffs(15, 24).addBox(-1.0F, -4.5F, -8.4F, 1.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 21.0F, 0.0F, 0.36425021489121656F, 0.12217304763960307F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
