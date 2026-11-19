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
 * Geometry converted automatically from the 1.12.2 ModelSporePod by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class SporePodModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "spore_pod"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart podBase02;
    public final ModelPart pod02a;
    public final ModelPart pod02c;
    public final ModelPart pod02d;
    public final ModelPart pod02e;
    public final ModelPart pod02f;
    public final ModelPart pod02b;
    public final ModelPart podBase03;
    public final ModelPart pod03f;
    public final ModelPart pod03b;
    public final ModelPart pod03d;
    public final ModelPart pod03a;
    public final ModelPart pod03c;
    public final ModelPart pod03e;
    public final ModelPart podBase04;
    public final ModelPart pod04d;
    public final ModelPart pod04e;
    public final ModelPart pod04a;
    public final ModelPart pod04f;
    public final ModelPart pod04c;
    public final ModelPart pod04b;
    public final ModelPart podBase05;
    public final ModelPart pod05e;
    public final ModelPart pod05b;
    public final ModelPart pod05f;
    public final ModelPart pod05c;
    public final ModelPart pod05d;
    public final ModelPart pod05a;
    public final ModelPart podBase06;
    public final ModelPart pod06e;
    public final ModelPart pod06c;
    public final ModelPart pod06d;
    public final ModelPart pod06a;
    public final ModelPart pod06b;
    public final ModelPart pod06f;
    public final ModelPart podBase01;
    public final ModelPart pod01c;
    public final ModelPart pod01f;
    public final ModelPart pod01e;
    public final ModelPart pod01d;
    public final ModelPart pod01b;
    public final ModelPart pod01a;

    public SporePodModel(ModelPart root)
    {
        this.root = root;
        this.podBase02 = root.getChild("podBase02");
        this.parts.put("podBase02", this.podBase02);
        this.pod02a = this.podBase02.getChild("pod02a");
        this.parts.put("pod02a", this.pod02a);
        this.pod02c = this.podBase02.getChild("pod02c");
        this.parts.put("pod02c", this.pod02c);
        this.pod02d = this.podBase02.getChild("pod02d");
        this.parts.put("pod02d", this.pod02d);
        this.pod02e = this.podBase02.getChild("pod02e");
        this.parts.put("pod02e", this.pod02e);
        this.pod02f = this.podBase02.getChild("pod02f");
        this.parts.put("pod02f", this.pod02f);
        this.pod02b = this.podBase02.getChild("pod02b");
        this.parts.put("pod02b", this.pod02b);
        this.podBase03 = root.getChild("podBase03");
        this.parts.put("podBase03", this.podBase03);
        this.pod03f = this.podBase03.getChild("pod03f");
        this.parts.put("pod03f", this.pod03f);
        this.pod03b = this.podBase03.getChild("pod03b");
        this.parts.put("pod03b", this.pod03b);
        this.pod03d = this.podBase03.getChild("pod03d");
        this.parts.put("pod03d", this.pod03d);
        this.pod03a = this.podBase03.getChild("pod03a");
        this.parts.put("pod03a", this.pod03a);
        this.pod03c = this.podBase03.getChild("pod03c");
        this.parts.put("pod03c", this.pod03c);
        this.pod03e = this.podBase03.getChild("pod03e");
        this.parts.put("pod03e", this.pod03e);
        this.podBase04 = root.getChild("podBase04");
        this.parts.put("podBase04", this.podBase04);
        this.pod04d = this.podBase04.getChild("pod04d");
        this.parts.put("pod04d", this.pod04d);
        this.pod04e = this.podBase04.getChild("pod04e");
        this.parts.put("pod04e", this.pod04e);
        this.pod04a = this.podBase04.getChild("pod04a");
        this.parts.put("pod04a", this.pod04a);
        this.pod04f = this.podBase04.getChild("pod04f");
        this.parts.put("pod04f", this.pod04f);
        this.pod04c = this.podBase04.getChild("pod04c");
        this.parts.put("pod04c", this.pod04c);
        this.pod04b = this.podBase04.getChild("pod04b");
        this.parts.put("pod04b", this.pod04b);
        this.podBase05 = root.getChild("podBase05");
        this.parts.put("podBase05", this.podBase05);
        this.pod05e = this.podBase05.getChild("pod05e");
        this.parts.put("pod05e", this.pod05e);
        this.pod05b = this.podBase05.getChild("pod05b");
        this.parts.put("pod05b", this.pod05b);
        this.pod05f = this.podBase05.getChild("pod05f");
        this.parts.put("pod05f", this.pod05f);
        this.pod05c = this.podBase05.getChild("pod05c");
        this.parts.put("pod05c", this.pod05c);
        this.pod05d = this.podBase05.getChild("pod05d");
        this.parts.put("pod05d", this.pod05d);
        this.pod05a = this.podBase05.getChild("pod05a");
        this.parts.put("pod05a", this.pod05a);
        this.podBase06 = root.getChild("podBase06");
        this.parts.put("podBase06", this.podBase06);
        this.pod06e = this.podBase06.getChild("pod06e");
        this.parts.put("pod06e", this.pod06e);
        this.pod06c = this.podBase06.getChild("pod06c");
        this.parts.put("pod06c", this.pod06c);
        this.pod06d = this.podBase06.getChild("pod06d");
        this.parts.put("pod06d", this.pod06d);
        this.pod06a = this.podBase06.getChild("pod06a");
        this.parts.put("pod06a", this.pod06a);
        this.pod06b = this.podBase06.getChild("pod06b");
        this.parts.put("pod06b", this.pod06b);
        this.pod06f = this.podBase06.getChild("pod06f");
        this.parts.put("pod06f", this.pod06f);
        this.podBase01 = root.getChild("podBase01");
        this.parts.put("podBase01", this.podBase01);
        this.pod01c = this.podBase01.getChild("pod01c");
        this.parts.put("pod01c", this.pod01c);
        this.pod01f = this.podBase01.getChild("pod01f");
        this.parts.put("pod01f", this.pod01f);
        this.pod01e = this.podBase01.getChild("pod01e");
        this.parts.put("pod01e", this.pod01e);
        this.pod01d = this.podBase01.getChild("pod01d");
        this.parts.put("pod01d", this.pod01d);
        this.pod01b = this.podBase01.getChild("pod01b");
        this.parts.put("pod01b", this.pod01b);
        this.pod01a = this.podBase01.getChild("pod01a");
        this.parts.put("pod01a", this.pod01a);
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
        PartDefinition p_podBase02 = root.addOrReplaceChild("podBase02",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(4.8F, 23.5F, 4.3F, 0.0F, 0.0F, 0.18203784098300857F));
        PartDefinition p_pod02a = p_podBase02.addOrReplaceChild("pod02a",
                CubeListBuilder.create().texOffs(0, 5).addBox(-2.0F, -0.7F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod02c = p_podBase02.addOrReplaceChild("pod02c",
                CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, -3.9F, -3.0F, 6.0F, 3.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod02d = p_podBase02.addOrReplaceChild("pod02d",
                CubeListBuilder.create().texOffs(19, 0).addBox(-2.5F, -4.8F, -2.5F, 5.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod02e = p_podBase02.addOrReplaceChild("pod02e",
                CubeListBuilder.create().texOffs(19, 8).addBox(-2.0F, -5.6F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod02f = p_podBase02.addOrReplaceChild("pod02f",
                CubeListBuilder.create().texOffs(25, 15).addBox(-1.0F, -6.5F, -1.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod02b = p_podBase02.addOrReplaceChild("pod02b",
                CubeListBuilder.create().texOffs(0, 11).addBox(-2.5F, -1.1F, -2.5F, 5.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, -0.1F, 0.0F));
        PartDefinition p_podBase03 = root.addOrReplaceChild("podBase03",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-5.4F, 24.0F, 3.7F, -0.045553093477052F, -0.091106186954104F, -0.27314402793711257F));
        PartDefinition p_pod03f = p_podBase03.addOrReplaceChild("pod03f",
                CubeListBuilder.create().texOffs(25, 15).addBox(-1.0F, -6.5F, -1.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod03b = p_podBase03.addOrReplaceChild("pod03b",
                CubeListBuilder.create().texOffs(0, 11).addBox(-2.5F, -1.1F, -2.5F, 5.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, -0.1F, 0.0F));
        PartDefinition p_pod03d = p_podBase03.addOrReplaceChild("pod03d",
                CubeListBuilder.create().texOffs(19, 0).addBox(-2.5F, -4.8F, -2.5F, 5.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod03a = p_podBase03.addOrReplaceChild("pod03a",
                CubeListBuilder.create().texOffs(0, 5).addBox(-2.0F, -0.7F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod03c = p_podBase03.addOrReplaceChild("pod03c",
                CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, -3.9F, -3.0F, 6.0F, 3.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod03e = p_podBase03.addOrReplaceChild("pod03e",
                CubeListBuilder.create().texOffs(19, 8).addBox(-2.0F, -5.6F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_podBase04 = root.addOrReplaceChild("podBase04",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 24.1F, -3.6F, 0.36425021489121656F, 0.0F, -0.18203784098300857F));
        PartDefinition p_pod04d = p_podBase04.addOrReplaceChild("pod04d",
                CubeListBuilder.create().texOffs(19, 0).addBox(-2.5F, -4.8F, -2.5F, 5.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod04e = p_podBase04.addOrReplaceChild("pod04e",
                CubeListBuilder.create().texOffs(19, 8).addBox(-2.0F, -5.6F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod04a = p_podBase04.addOrReplaceChild("pod04a",
                CubeListBuilder.create().texOffs(0, 5).addBox(-2.0F, -0.7F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod04f = p_podBase04.addOrReplaceChild("pod04f",
                CubeListBuilder.create().texOffs(25, 15).addBox(-1.0F, -6.5F, -1.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod04c = p_podBase04.addOrReplaceChild("pod04c",
                CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, -3.9F, -3.0F, 6.0F, 3.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod04b = p_podBase04.addOrReplaceChild("pod04b",
                CubeListBuilder.create().texOffs(0, 11).addBox(-2.5F, -1.1F, -2.5F, 5.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, -0.1F, 0.0F));
        PartDefinition p_podBase05 = root.addOrReplaceChild("podBase05",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(-3.4F, 23.5F, -1.3F, 0.27314402793711257F, 0.0F, -0.40980330836826856F));
        PartDefinition p_pod05e = p_podBase05.addOrReplaceChild("pod05e",
                CubeListBuilder.create().texOffs(19, 8).addBox(-2.0F, -5.6F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod05b = p_podBase05.addOrReplaceChild("pod05b",
                CubeListBuilder.create().texOffs(0, 11).addBox(-2.5F, -1.1F, -2.5F, 5.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, -0.1F, 0.0F));
        PartDefinition p_pod05f = p_podBase05.addOrReplaceChild("pod05f",
                CubeListBuilder.create().texOffs(25, 15).addBox(-1.0F, -6.5F, -1.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod05c = p_podBase05.addOrReplaceChild("pod05c",
                CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, -3.9F, -3.0F, 6.0F, 3.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod05d = p_podBase05.addOrReplaceChild("pod05d",
                CubeListBuilder.create().texOffs(19, 0).addBox(-2.5F, -4.8F, -2.5F, 5.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod05a = p_podBase05.addOrReplaceChild("pod05a",
                CubeListBuilder.create().texOffs(0, 5).addBox(-2.0F, -0.7F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_podBase06 = root.addOrReplaceChild("podBase06",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(2.9F, 23.5F, -2.0F, 0.18203784098300857F, 0.0F, 0.22759093446006054F));
        PartDefinition p_pod06e = p_podBase06.addOrReplaceChild("pod06e",
                CubeListBuilder.create().texOffs(19, 8).addBox(-2.0F, -5.6F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod06c = p_podBase06.addOrReplaceChild("pod06c",
                CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, -3.9F, -3.0F, 6.0F, 3.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod06d = p_podBase06.addOrReplaceChild("pod06d",
                CubeListBuilder.create().texOffs(19, 0).addBox(-2.5F, -4.8F, -2.5F, 5.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod06a = p_podBase06.addOrReplaceChild("pod06a",
                CubeListBuilder.create().texOffs(0, 5).addBox(-2.0F, -0.7F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod06b = p_podBase06.addOrReplaceChild("pod06b",
                CubeListBuilder.create().texOffs(0, 11).addBox(-2.5F, -1.1F, -2.5F, 5.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, -0.1F, 0.0F));
        PartDefinition p_pod06f = p_podBase06.addOrReplaceChild("pod06f",
                CubeListBuilder.create().texOffs(25, 15).addBox(-1.0F, -6.5F, -1.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_podBase01 = root.addOrReplaceChild("podBase01",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.2F, 23.6F, 1.5F, 0.136659280431156F, 0.0F, -0.136659280431156F));
        PartDefinition p_pod01c = p_podBase01.addOrReplaceChild("pod01c",
                CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, -3.9F, -3.0F, 6.0F, 3.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod01f = p_podBase01.addOrReplaceChild("pod01f",
                CubeListBuilder.create().texOffs(25, 15).addBox(-1.0F, -6.5F, -1.0F, 2.0F, 1.0F, 2.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod01e = p_podBase01.addOrReplaceChild("pod01e",
                CubeListBuilder.create().texOffs(19, 8).addBox(-2.0F, -5.6F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod01d = p_podBase01.addOrReplaceChild("pod01d",
                CubeListBuilder.create().texOffs(19, 0).addBox(-2.5F, -4.8F, -2.5F, 5.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_pod01b = p_podBase01.addOrReplaceChild("pod01b",
                CubeListBuilder.create().texOffs(0, 11).addBox(-2.5F, -1.1F, -2.5F, 5.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, -0.1F, 0.0F));
        PartDefinition p_pod01a = p_podBase01.addOrReplaceChild("pod01a",
                CubeListBuilder.create().texOffs(0, 5).addBox(-2.0F, -0.7F, -2.0F, 4.0F, 1.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
