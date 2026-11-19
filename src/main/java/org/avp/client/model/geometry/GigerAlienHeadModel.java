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
 * Geometry converted automatically from the 1.12.2 ModelGigerAlienHead by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class GigerAlienHeadModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "giger_alien_head"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart head;
    public final ModelPart head2;
    public final ModelPart lowerJaw1;
    public final ModelPart lowerJaw2;
    public final ModelPart lowerTeeth;
    public final ModelPart upperJaw;
    public final ModelPart upperTeeth01;
    public final ModelPart upperTeeth02;
    public final ModelPart innerJaw;
    public final ModelPart face;
    public final ModelPart face2;
    public final ModelPart face3;

    public GigerAlienHeadModel(ModelPart root)
    {
        this.root = root;
        this.head = root.getChild("head");
        this.parts.put("head", this.head);
        this.head2 = this.head.getChild("head2");
        this.parts.put("head2", this.head2);
        this.lowerJaw1 = this.head.getChild("lowerJaw1");
        this.parts.put("lowerJaw1", this.lowerJaw1);
        this.lowerJaw2 = this.lowerJaw1.getChild("lowerJaw2");
        this.parts.put("lowerJaw2", this.lowerJaw2);
        this.lowerTeeth = this.lowerJaw1.getChild("lowerTeeth");
        this.parts.put("lowerTeeth", this.lowerTeeth);
        this.upperJaw = this.head.getChild("upperJaw");
        this.parts.put("upperJaw", this.upperJaw);
        this.upperTeeth01 = this.upperJaw.getChild("upperTeeth01");
        this.parts.put("upperTeeth01", this.upperTeeth01);
        this.upperTeeth02 = this.upperTeeth01.getChild("upperTeeth02");
        this.parts.put("upperTeeth02", this.upperTeeth02);
        this.innerJaw = this.upperJaw.getChild("innerJaw");
        this.parts.put("innerJaw", this.innerJaw);
        this.face = this.head.getChild("face");
        this.parts.put("face", this.face);
        this.face2 = this.face.getChild("face2");
        this.parts.put("face2", this.face2);
        this.face3 = this.face2.getChild("face3");
        this.parts.put("face3", this.face3);
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
                CubeListBuilder.create().texOffs(112, 1).addBox(-3.5F, -7.3F, -6.6F, 7.0F, 6.0F, 13.0F),
                PartPose.offsetAndRotation(0.0F, 12.1F, 4.4F, 1.4660765716752369F, -1.5707963267948966F, -0.5235987755982988F));
        PartDefinition p_head2 = p_head.addOrReplaceChild("head2",
                CubeListBuilder.create().texOffs(155, 1).addBox(-3.5F, -3.04F, 0.0F, 7.0F, 6.0F, 12.0F),
                PartPose.offsetAndRotation(0.0F, -4.4F, 5.4F, -0.27314402793711257F, 0.0F, 0.0F));
        PartDefinition p_lowerJaw1 = p_head.addOrReplaceChild("lowerJaw1",
                CubeListBuilder.create().texOffs(219, 37).addBox(-2.8F, -0.3F, -5.0F, 5.0F, 1.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -1.0F, -4.9F, 0.36425021489121656F, 0.0F, 0.0F));
        PartDefinition p_lowerJaw2 = p_lowerJaw1.addOrReplaceChild("lowerJaw2",
                CubeListBuilder.create().texOffs(241, 36).addBox(1.8F, -0.3F, -5.0F, 1.0F, 1.0F, 5.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_lowerTeeth = p_lowerJaw1.addOrReplaceChild("lowerTeeth",
                CubeListBuilder.create().texOffs(219, 81).addBox(-2.5F, -1.4F, -4.4F, 5.0F, 2.0F, 3.0F),
                PartPose.offset(0.0F, -0.2F, -0.5F));
        PartDefinition p_upperJaw = p_head.addOrReplaceChild("upperJaw",
                CubeListBuilder.create().texOffs(225, 27).addBox(-3.0F, -1.3F, -5.8F, 6.0F, 2.0F, 5.0F),
                PartPose.offset(0.0F, -2.0F, -4.2F));
        PartDefinition p_upperTeeth01 = p_upperJaw.addOrReplaceChild("upperTeeth01",
                CubeListBuilder.create().texOffs(219, 71).addBox(-2.65F, -0.3F, -5.0F, 5.0F, 2.0F, 3.0F),
                PartPose.offset(0.0F, 0.4F, -0.7F));
        PartDefinition p_upperTeeth02 = p_upperTeeth01.addOrReplaceChild("upperTeeth02",
                CubeListBuilder.create().texOffs(241, 71).addBox(1.65F, -0.3F, -5.0F, 1.0F, 2.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_innerJaw = p_upperJaw.addOrReplaceChild("innerJaw",
                CubeListBuilder.create().texOffs(199, 54).addBox(-1.5F, -1.6F, -9.7F, 3.0F, 3.0F, 10.0F),
                PartPose.offsetAndRotation(0.0F, -1.5F, 4.6F, 0.136659280431156F, 0.0F, 0.0F));
        PartDefinition p_face = p_head.addOrReplaceChild("face",
                CubeListBuilder.create().texOffs(184, 20).addBox(-3.65F, -1.6F, -2.2F, 1.0F, 5.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, -5.0F, -7.1F, 0.6373942428283291F, 0.0F, 0.0F));
        PartDefinition p_face2 = p_face.addOrReplaceChild("face2",
                CubeListBuilder.create().texOffs(159, 20).addBox(-3.35F, -1.5F, -2.2F, 7.0F, 5.0F, 4.0F),
                PartPose.offset(0.0F, -0.1F, 0.0F));
        PartDefinition p_face3 = p_face2.addOrReplaceChild("face3",
                CubeListBuilder.create().texOffs(197, 18).addBox(-3.5F, -2.6F, -2.6F, 7.0F, 3.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7740535232594852F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
