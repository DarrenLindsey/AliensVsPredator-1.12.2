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
 * Geometry converted automatically from the 1.12.2 ModelNeomorphSkull by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class NeomorphSkullModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "neomorph_skull"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart head;
    public final ModelPart head2;
    public final ModelPart head5;
    public final ModelPart head6;
    public final ModelPart head3;
    public final ModelPart head4;
    public final ModelPart headLower01;
    public final ModelPart headLower02;
    public final ModelPart jawLower;
    public final ModelPart jawLowerInner;
    public final ModelPart jawLowerInner02;
    public final ModelPart muzzle01;
    public final ModelPart jawUpper01;
    public final ModelPart jawUpper02;
    public final ModelPart jawUpperInner1;
    public final ModelPart JawUpperInner02;
    public final ModelPart face01;
    public final ModelPart face02;
    public final ModelPart muzzle03;
    public final ModelPart muzzle04;

    public NeomorphSkullModel(ModelPart root)
    {
        this.root = root;
        this.head = root.getChild("head");
        this.parts.put("head", this.head);
        this.head2 = this.head.getChild("head2");
        this.parts.put("head2", this.head2);
        this.head5 = this.head2.getChild("head5");
        this.parts.put("head5", this.head5);
        this.head6 = this.head5.getChild("head6");
        this.parts.put("head6", this.head6);
        this.head3 = this.head2.getChild("head3");
        this.parts.put("head3", this.head3);
        this.head4 = this.head3.getChild("head4");
        this.parts.put("head4", this.head4);
        this.headLower01 = this.head.getChild("headLower01");
        this.parts.put("headLower01", this.headLower01);
        this.headLower02 = this.headLower01.getChild("headLower02");
        this.parts.put("headLower02", this.headLower02);
        this.jawLower = this.head.getChild("jawLower");
        this.parts.put("jawLower", this.jawLower);
        this.jawLowerInner = this.jawLower.getChild("jawLowerInner");
        this.parts.put("jawLowerInner", this.jawLowerInner);
        this.jawLowerInner02 = this.jawLowerInner.getChild("jawLowerInner02");
        this.parts.put("jawLowerInner02", this.jawLowerInner02);
        this.muzzle01 = this.head.getChild("muzzle01");
        this.parts.put("muzzle01", this.muzzle01);
        this.jawUpper01 = this.muzzle01.getChild("jawUpper01");
        this.parts.put("jawUpper01", this.jawUpper01);
        this.jawUpper02 = this.jawUpper01.getChild("jawUpper02");
        this.parts.put("jawUpper02", this.jawUpper02);
        this.jawUpperInner1 = this.jawUpper01.getChild("jawUpperInner1");
        this.parts.put("jawUpperInner1", this.jawUpperInner1);
        this.JawUpperInner02 = this.jawUpperInner1.getChild("JawUpperInner02");
        this.parts.put("JawUpperInner02", this.JawUpperInner02);
        this.face01 = this.muzzle01.getChild("face01");
        this.parts.put("face01", this.face01);
        this.face02 = this.face01.getChild("face02");
        this.parts.put("face02", this.face02);
        this.muzzle03 = this.muzzle01.getChild("muzzle03");
        this.parts.put("muzzle03", this.muzzle03);
        this.muzzle04 = this.muzzle03.getChild("muzzle04");
        this.parts.put("muzzle04", this.muzzle04);
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
        PartDefinition p_head = root.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(124, 0).addBox(-3.0F, -5.0F, -3.1F, 6.0F, 3.0F, 6.0F),
                PartPose.offset(0.0F, 24.0F, -3.0F));
        PartDefinition p_head2 = p_head.addOrReplaceChild("head2",
                CubeListBuilder.create().texOffs(157, 0).addBox(-2.5F, -1.0F, -0.8F, 5.0F, 2.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, -3.6F, 2.4F, 0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_head5 = p_head2.addOrReplaceChild("head5",
                CubeListBuilder.create().texOffs(157, 12).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 0.1F, 4.1F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_head6 = p_head5.addOrReplaceChild("head6",
                CubeListBuilder.create().texOffs(157, 25).addBox(-1.5F, -0.9F, 0.2F, 3.0F, 2.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -0.1F, 3.4F, -0.091106186954104F, 0.0F, 0.0F));
        PartDefinition p_head3 = p_head2.addOrReplaceChild("head3",
                CubeListBuilder.create().texOffs(184, 0).addBox(-2.0F, -1.2F, 0.0F, 4.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 2.1F, 0.2F, 0.18203784098300857F, 0.0F, 0.0F));
        PartDefinition p_head4 = p_head3.addOrReplaceChild("head4",
                CubeListBuilder.create().texOffs(185, 12).addBox(-1.5F, -0.4F, 0.0F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.1F, 4.7F, -0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_headLower01 = p_head.addOrReplaceChild("headLower01",
                CubeListBuilder.create().texOffs(118, 29).addBox(-2.83F, 0.0F, -3.1F, 5.0F, 2.0F, 6.0F),
                PartPose.offset(0.0F, -2.2F, 0.0F));
        PartDefinition p_headLower02 = p_headLower01.addOrReplaceChild("headLower02",
                CubeListBuilder.create().texOffs(131, 40).addBox(1.83F, 0.0F, -3.1F, 1.0F, 2.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_jawLower = p_head.addOrReplaceChild("jawLower",
                CubeListBuilder.create().texOffs(181, 58).addBox(-1.5F, -0.3F, -2.2F, 3.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, -0.4F, -2.8F));
        PartDefinition p_jawLowerInner = p_jawLower.addOrReplaceChild("jawLowerInner",
                CubeListBuilder.create().texOffs(183, 51).addBox(-1.5F, -0.9F, -1.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(0.1F, -0.2F, -1.0F));
        PartDefinition p_jawLowerInner02 = p_jawLowerInner.addOrReplaceChild("jawLowerInner02",
                CubeListBuilder.create().texOffs(195, 51).addBox(0.35F, -0.9F, -1.0F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_muzzle01 = p_head.addOrReplaceChild("muzzle01",
                CubeListBuilder.create().texOffs(175, 25).addBox(-2.5F, -1.6F, -2.0F, 5.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -1.8F, -3.5F, -0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_jawUpper01 = p_muzzle01.addOrReplaceChild("jawUpper01",
                CubeListBuilder.create().texOffs(151, 37).addBox(-2.83F, -0.7F, -3.9F, 3.0F, 1.0F, 4.0F),
                PartPose.offsetAndRotation(1.0F, -0.1F, 2.4F, 0.40980330836826856F, 0.0F, 0.0F));
        PartDefinition p_jawUpper02 = p_jawUpper01.addOrReplaceChild("jawUpper02",
                CubeListBuilder.create().texOffs(168, 37).addBox(-0.15F, -0.7F, -3.9F, 1.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_jawUpperInner1 = p_jawUpper01.addOrReplaceChild("jawUpperInner1",
                CubeListBuilder.create().texOffs(179, 44).addBox(-2.49F, -0.5F, -2.1F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.6F, -1.6F));
        PartDefinition p_JawUpperInner02 = p_jawUpperInner1.addOrReplaceChild("JawUpperInner02",
                CubeListBuilder.create().texOffs(189, 44).addBox(-0.51F, -0.5F, -2.1F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_face01 = p_muzzle01.addOrReplaceChild("face01",
                CubeListBuilder.create().texOffs(124, 16).addBox(-2.65F, -2.5F, 0.1F, 5.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, -0.1F, -0.1F, 0.9105382707654417F, 0.0F, 0.0F));
        PartDefinition p_face02 = p_face01.addOrReplaceChild("face02",
                CubeListBuilder.create().texOffs(139, 16).addBox(1.65F, -2.5F, 0.1F, 1.0F, 3.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_muzzle03 = p_muzzle01.addOrReplaceChild("muzzle03",
                CubeListBuilder.create().texOffs(179, 38).addBox(-2.45F, -1.2F, -1.6F, 4.0F, 1.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.5918411493512771F, 0.0F, 0.0F));
        PartDefinition p_muzzle04 = p_muzzle03.addOrReplaceChild("muzzle04",
                CubeListBuilder.create().texOffs(193, 38).addBox(1.44F, -1.2F, -1.6F, 1.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
