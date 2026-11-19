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
 * Geometry converted automatically from the 1.12.2 ModelProtomorphSkull by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class ProtomorphSkullModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "protomorph_skull"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart head1;
    public final ModelPart face1;
    public final ModelPart face2;
    public final ModelPart jawUpper;
    public final ModelPart jawLower;
    public final ModelPart innerJaw;
    public final ModelPart jawLower2;
    public final ModelPart head2;

    public ProtomorphSkullModel(ModelPart root)
    {
        this.root = root;
        this.head1 = root.getChild("head1");
        this.parts.put("head1", this.head1);
        this.face1 = this.head1.getChild("face1");
        this.parts.put("face1", this.face1);
        this.face2 = this.face1.getChild("face2");
        this.parts.put("face2", this.face2);
        this.jawUpper = this.head1.getChild("jawUpper");
        this.parts.put("jawUpper", this.jawUpper);
        this.jawLower = this.head1.getChild("jawLower");
        this.parts.put("jawLower", this.jawLower);
        this.innerJaw = this.jawLower.getChild("innerJaw");
        this.parts.put("innerJaw", this.innerJaw);
        this.jawLower2 = this.jawLower.getChild("jawLower2");
        this.parts.put("jawLower2", this.jawLower2);
        this.head2 = this.head1.getChild("head2");
        this.parts.put("head2", this.head2);
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
        PartDefinition p_head1 = root.addOrReplaceChild("head1",
                CubeListBuilder.create().texOffs(83, 47).addBox(-2.0F, -4.1F, -3.3F, 4.0F, 4.0F, 8.0F),
                PartPose.offsetAndRotation(0.0F, 23.4F, -2.5F, 0.06981317007977318F, 0.0F, 0.0F));
        PartDefinition p_face1 = p_head1.addOrReplaceChild("face1",
                CubeListBuilder.create().texOffs(144, 50).addBox(-2.25F, -1.9F, -3.0F, 3.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.1F, -2.8F, -2.0F, 0.7740535232594852F, 0.0F, 0.0F));
        PartDefinition p_face2 = p_face1.addOrReplaceChild("face2",
                CubeListBuilder.create().texOffs(161, 50).addBox(0.05F, -1.9F, -3.0F, 2.0F, 2.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_jawUpper = p_head1.addOrReplaceChild("jawUpper",
                CubeListBuilder.create().texOffs(184, 51).addBox(-2.0F, -1.5F, -3.1F, 4.0F, 2.0F, 3.0F),
                PartPose.offset(0.0F, -0.6F, -2.4F));
        PartDefinition p_jawLower = p_head1.addOrReplaceChild("jawLower",
                CubeListBuilder.create().texOffs(184, 61).addBox(-1.8F, 0.3F, -3.0F, 3.0F, 1.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, -1.1F, -2.4F, 0.045553093477052F, 0.0F, 0.0F));
        PartDefinition p_innerJaw = p_jawLower.addOrReplaceChild("innerJaw",
                CubeListBuilder.create().texOffs(155, 60).addBox(-1.0F, -1.0F, -2.8F, 2.0F, 2.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, -0.4F, 0.0F, 0.22759093446006054F, 0.0F, 0.0F));
        PartDefinition p_jawLower2 = p_jawLower.addOrReplaceChild("jawLower2",
                CubeListBuilder.create().texOffs(203, 61).addBox(0.86F, 0.3F, -3.0F, 1.0F, 1.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition p_head2 = p_head1.addOrReplaceChild("head2",
                CubeListBuilder.create().texOffs(112, 47).addBox(-2.0F, -1.88F, -0.63F, 4.0F, 4.0F, 7.0F),
                PartPose.offsetAndRotation(0.0F, -2.13F, 4.7F, -0.27314402793711257F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
