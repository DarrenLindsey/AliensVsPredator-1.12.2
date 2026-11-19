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
 * Geometry converted automatically from the 1.12.2 ModelQueenSkull by tools/convert_models.py.
 * Re-running the script overwrites this file - put custom code in another class.
 */
@SuppressWarnings("unused")
public class QueenSkullModel implements GeneratedModel
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "queen_skull"), "main");

    private final ModelPart root;
    private final Map<String, ModelPart> parts = new LinkedHashMap<>();
    public final ModelPart head0;
    public final ModelPart headWebR;
    public final ModelPart headSpinesM;
    public final ModelPart headWebL;
    public final ModelPart Jaw1;
    public final ModelPart headFinL0;
    public final ModelPart headSpinesL;
    public final ModelPart headFinR0;
    public final ModelPart headFinR1;
    public final ModelPart headFinR2;
    public final ModelPart head1;
    public final ModelPart headFinL2;
    public final ModelPart headSpinesR;
    public final ModelPart headBase0;
    public final ModelPart headFinL1;

    public QueenSkullModel(ModelPart root)
    {
        this.root = root;
        this.head0 = root.getChild("head0");
        this.parts.put("head0", this.head0);
        this.headWebR = this.head0.getChild("headWebR");
        this.parts.put("headWebR", this.headWebR);
        this.headSpinesM = this.head0.getChild("headSpinesM");
        this.parts.put("headSpinesM", this.headSpinesM);
        this.headWebL = this.head0.getChild("headWebL");
        this.parts.put("headWebL", this.headWebL);
        this.Jaw1 = this.head0.getChild("Jaw1");
        this.parts.put("Jaw1", this.Jaw1);
        this.headFinL0 = this.head0.getChild("headFinL0");
        this.parts.put("headFinL0", this.headFinL0);
        this.headSpinesL = this.head0.getChild("headSpinesL");
        this.parts.put("headSpinesL", this.headSpinesL);
        this.headFinR0 = this.head0.getChild("headFinR0");
        this.parts.put("headFinR0", this.headFinR0);
        this.headFinR1 = this.head0.getChild("headFinR1");
        this.parts.put("headFinR1", this.headFinR1);
        this.headFinR2 = this.head0.getChild("headFinR2");
        this.parts.put("headFinR2", this.headFinR2);
        this.head1 = this.head0.getChild("head1");
        this.parts.put("head1", this.head1);
        this.headFinL2 = this.head0.getChild("headFinL2");
        this.parts.put("headFinL2", this.headFinL2);
        this.headSpinesR = this.head0.getChild("headSpinesR");
        this.parts.put("headSpinesR", this.headSpinesR);
        this.headBase0 = this.head0.getChild("headBase0");
        this.parts.put("headBase0", this.headBase0);
        this.headFinL1 = this.head0.getChild("headFinL1");
        this.parts.put("headFinL1", this.headFinL1);
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
        PartDefinition p_head0 = root.addOrReplaceChild("head0",
                CubeListBuilder.create().texOffs(19, 19).addBox(-3.5F, -5.0F, -3.0F, 7.0F, 11.0F, 5.0F),
                PartPose.offset(0.0F, 11.3F, 4.0F));
        PartDefinition p_headWebR = p_head0.addOrReplaceChild("headWebR",
                CubeListBuilder.create().texOffs(22, 70).addBox(-4.8F, -5.0F, -1.2F, 3.0F, 6.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.12217304763960307F, -0.091106186954104F));
        PartDefinition p_headSpinesM = p_head0.addOrReplaceChild("headSpinesM",
                CubeListBuilder.create().texOffs(112, 18).addBox(0.0F, -6.0F, -1.0F, 0.0F, 4.0F, 24.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.3264502315156905F, 0.0F, 0.0F));
        PartDefinition p_headWebL = p_head0.addOrReplaceChild("headWebL",
                CubeListBuilder.create().texOffs(15, 70).addBox(1.8F, -5.0F, -1.2F, 3.0F, 6.0F, 0.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.12217304763960307F, 0.091106186954104F));
        PartDefinition p_Jaw1 = p_head0.addOrReplaceChild("Jaw1",
                CubeListBuilder.create().texOffs(19, 77).addBox(-1.5F, 5.6F, -8.5F, 3.0F, 4.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.9773843811168246F, 0.0F, 0.0F));
        PartDefinition p_headFinL0 = p_head0.addOrReplaceChild("headFinL0",
                CubeListBuilder.create().texOffs(0, 59).addBox(0.0F, -4.0F, -2.0F, 5.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.15707963267948966F, -0.091106186954104F, 1.0016444577195458F));
        PartDefinition p_headSpinesL = p_head0.addOrReplaceChild("headSpinesL",
                CubeListBuilder.create().texOffs(63, 18).addBox(1.0F, -6.0F, -1.0F, 0.0F, 4.0F, 24.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.3264502315156905F, 0.0F, 0.296705972839036F));
        PartDefinition p_headFinR0 = p_head0.addOrReplaceChild("headFinR0",
                CubeListBuilder.create().texOffs(0, 67).addBox(-5.0F, -4.0F, -2.0F, 5.0F, 5.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.15707963267948966F, 0.091106186954104F, -1.0016444577195458F));
        PartDefinition p_headFinR1 = p_head0.addOrReplaceChild("headFinR1",
                CubeListBuilder.create().texOffs(28, 61).addBox(-6.0F, -9.6F, -3.1F, 4.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.27314402793711257F, 0.18203784098300857F, -0.091106186954104F));
        PartDefinition p_headFinR2 = p_head0.addOrReplaceChild("headFinR2",
                CubeListBuilder.create().texOffs(44, 47).addBox(-3.6F, -23.0F, -3.0F, 7.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.31869712141416456F, 0.18203784098300857F, -0.31869712141416456F));
        PartDefinition p_head1 = p_head0.addOrReplaceChild("head1",
                CubeListBuilder.create().texOffs(44, 19).addBox(-2.5F, 3.8F, -5.2F, 5.0F, 5.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5009094953223726F, 0.0F, 0.0F));
        PartDefinition p_headFinL2 = p_head0.addOrReplaceChild("headFinL2",
                CubeListBuilder.create().texOffs(44, 31).addBox(-4.3F, -23.0F, -3.0F, 7.0F, 13.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.31869712141416456F, -0.18203784098300857F, 0.36425021489121656F));
        PartDefinition p_headSpinesR = p_head0.addOrReplaceChild("headSpinesR",
                CubeListBuilder.create().texOffs(161, 18).addBox(-1.0F, -6.0F, -1.0F, 0.0F, 4.0F, 24.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.3264502315156905F, 0.0F, -0.296705972839036F));
        PartDefinition p_headBase0 = p_head0.addOrReplaceChild("headBase0",
                CubeListBuilder.create().texOffs(19, 36).addBox(-3.5F, -23.0F, -4.2F, 7.0F, 19.0F, 5.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2792526803190927F, 0.0F, 0.0F));
        PartDefinition p_headFinL1 = p_head0.addOrReplaceChild("headFinL1",
                CubeListBuilder.create().texOffs(15, 61).addBox(2.5F, -9.6F, -3.1F, 4.0F, 6.0F, 2.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.27314402793711257F, -0.18203784098300857F, 0.0F));
        return LayerDefinition.create(mesh, 256, 128);
    }
}
