package org.avp.client.model;

import org.avp.AliensVsPredator;
import org.avp.entity.xenomorph.DroneEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/** An articulated Weaver with a low cowl, digitigrade legs and caste-specific tools. */
public final class WeaverModel extends EntityModel<DroneEntity>
{
    public static final ModelLayerLocation BUILDER = layer("builder_weaver");
    public static final ModelLayerLocation COATER = layer("coater_weaver");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart[] tail;

    public WeaverModel(ModelPart root)
    {
        this.root = root;
        ModelPart body = root.getChild("body");
        head = body.getChild("neck").getChild("cowl");
        leftArm = body.getChild("left_arm");
        rightArm = body.getChild("right_arm");
        leftLeg = body.getChild("left_leg");
        rightLeg = body.getChild("right_leg");
        tail = new ModelPart[5];
        tail[0] = body.getChild("tail");
        for (int i = 1; i < tail.length; i++)
        {
            tail[i] = tail[i - 1].getChild("segment_" + i);
        }
    }

    private static ModelLayerLocation layer(String name)
    {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, name), "main");
    }

    @Override
    public void setupAnim(DroneEntity drone, float swing, float amount, float age, float yaw, float pitch)
    {
        root.getAllParts().forEach(ModelPart::resetPose);
        head.yRot = yaw * Mth.DEG_TO_RAD * 0.62F;
        head.xRot += pitch * Mth.DEG_TO_RAD * 0.55F;
        float walk = Mth.cos(swing * 0.6662F) * amount;
        leftLeg.xRot += walk * 0.60F;
        rightLeg.xRot -= walk * 0.60F;
        leftArm.xRot -= walk * 0.48F;
        rightArm.xRot += walk * 0.48F;
        float curl = drone.updateTailCurl(2.2F * drone.getScale());
        for (int i = 0; i < tail.length; i++)
        {
            tail[i].yRot = Mth.sin(age * 0.095F - i * 0.68F) * (0.13F + amount * 0.19F) * (1F - curl * 0.75F);
            tail[i].xRot += curl * 0.50F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poses, VertexConsumer vertices, int light, int overlay, int color)
    {
        root.render(poses, vertices, light, overlay, color);
    }

    public static LayerDefinition builderLayer()
    {
        return createLayer(true);
    }

    public static LayerDefinition coaterLayer()
    {
        return createLayer(false);
    }

    private static LayerDefinition createLayer(boolean builder)
    {
        MeshDefinition mesh = new MeshDefinition();
        float half = builder ? 4.5F : 3.25F;
        float y = builder ? 10.8F : 12.8F;
        PartDefinition body = mesh.getRoot().addOrReplaceChild("body",
                box(Material.MEMBRANE, -half, -2, -5, 2 * half, 6, 11), PartPose.offset(0, y, 0));
        body.addOrReplaceChild("carapace", box(Material.SHELL, -half - 0.5F, -4, -4.5F, 2 * half + 1, 3, 10),
                PartPose.rotation(0.16F, 0, 0));
        body.addOrReplaceChild("pelvis", box(Material.SHELL, -3, -1, 4, 6, 4, 4), PartPose.ZERO);
        PartDefinition neck = body.addOrReplaceChild("neck", box(Material.MEMBRANE, -2.5F, -2, -4, 5, 4, 5),
                PartPose.offsetAndRotation(0, -0.7F, -5, -0.33F, 0, 0));
        PartDefinition cowl = neck.addOrReplaceChild("cowl",
                box(Material.COWL, -half + 0.65F, -4.3F, -7.3F, 2 * half - 1.3F, 5, 13.7F),
                PartPose.offsetAndRotation(0, -2.0F, -2.5F, builder ? -0.12F : -0.04F, 0, 0));
        cowl.addOrReplaceChild("crest", box(Material.COWL, -half + 1.35F, -5.7F, -6.8F, 2 * half - 2.7F, 2.8F, 12),
                PartPose.rotation(-0.14F, 0, 0));
        cowl.addOrReplaceChild("mantle", box(Material.COWL, -half + 1.1F, -3.6F, 3.3F, 2 * half - 2.2F, 3.8F, 5.6F),
                PartPose.rotation(0.32F, 0, 0));
        cowl.addOrReplaceChild("brow", box(Material.SHELL, -half + 1.3F, -2.5F, -9.0F, 2 * half - 2.6F, 2.5F, 3.1F),
                PartPose.rotation(-0.21F, 0, 0));
        cowl.addOrReplaceChild("mouth", box(Material.MEMBRANE, -1.90F, -0.35F, -8.6F, 3.8F, 2.65F, 4.9F), PartPose.ZERO);
        PartDefinition jaw = cowl.addOrReplaceChild("jaw",
                box(Material.SHELL, -2.35F, 1.65F, -9.0F, 4.7F, 0.95F, 4.5F), PartPose.rotation(0.035F, 0, 0));
        for (int i = 0; i < 5; i++)
        {
            jaw.addOrReplaceChild("tooth_" + i, box(Material.CLAW, -0.19F, 0.85F, -8.95F, 0.38F, 0.9F, 0.46F),
                    PartPose.offset((i - 2) * 0.78F, 0, Math.abs(i - 2) * 0.14F));
        }

        addSide(body, "left", -1, half, builder);
        addSide(body, "right", 1, half, builder);
        addTail(body, builder);
        return LayerDefinition.create(mesh, 256, 256);
    }

    private static void addSide(PartDefinition body, String name, int side, float half, boolean builder)
    {
        for (int rib = 0; rib < 3; rib++)
        {
            body.addOrReplaceChild(name + "_rib_" + rib, box(Material.SPINE, -0.55F, -1.2F, -0.55F, 1.1F, 4.9F, 1.1F),
                    PartPose.offsetAndRotation(side * (half - 0.13F), -0.2F, -0.95F + rib * 1.85F,
                            -0.30F - rib * 0.06F, 0, side * 0.28F));
        }
        float upperWidth = builder ? 3.0F : 2.0F;
        PartDefinition arm = body.addOrReplaceChild(name + "_arm",
                box(Material.SHELL, -upperWidth / 2, -1, -1.6F, upperWidth, 6, 3.2F),
                PartPose.offsetAndRotation(side * half, 0.1F, -4.0F, -0.43F, 0, side * -0.24F));
        arm.addOrReplaceChild("shoulder", box(Material.SHELL, -upperWidth * 0.66F, -2, -2.15F, upperWidth * 1.32F, 3, 4.3F),
                PartPose.ZERO);
        float toolWidth = builder ? 4.8F : 2.1F;
        PartDefinition hand = arm.addOrReplaceChild("forearm",
                box(Material.TOOL, -toolWidth / 2, -1.1F, -1.6F, toolWidth, 6, 3.2F),
                PartPose.offsetAndRotation(0, 4.25F, 0, -0.86F, 0, side * 0.11F));
        if (builder)
        {
            hand.addOrReplaceChild("outer_tooth", box(Material.CLAW, -0.75F, 0, -1.1F, 1.5F, 3.9F, 2.2F),
                    PartPose.offsetAndRotation(side * 1.55F, 4.7F, -0.45F, -0.63F, 0, side * -0.31F));
            hand.addOrReplaceChild("inner_tooth", box(Material.CLAW, -0.7F, 0, -1.0F, 1.4F, 3.2F, 2.0F),
                    PartPose.offsetAndRotation(side * -1.25F, 4.6F, -0.1F, -0.27F, 0, side * 0.14F));
            body.addOrReplaceChild(name + "_ridge", box(Material.SPINE, -0.85F, -4.2F, -3, 1.7F, 5.0F, 7.0F),
                    PartPose.offsetAndRotation(side * 2.3F, -2.7F, 0.9F, -0.27F, 0, side * 0.26F));
            body.addOrReplaceChild(name + "_short_ridge", box(Material.SPINE, -0.53F, -2.0F, -1.5F, 1.06F, 3, 4),
                    PartPose.offsetAndRotation(side * 3.1F, -2.75F, 3.9F, -0.40F, 0, side * 0.41F));
        }
        else
        {
            hand.addOrReplaceChild("resin_pad", box(Material.SAC, -1.3F, 0, -1.0F, 2.6F, 3.5F, 2.0F),
                    PartPose.offsetAndRotation(0, 4.1F, -0.7F, -0.28F, 0, 0));
            for (int i = -1; i <= 1; i++)
            {
                hand.addOrReplaceChild("finger_" + i, box(Material.CLAW, -0.35F, 0, -0.6F, 0.7F, 3.1F, 1.2F),
                        PartPose.offsetAndRotation(i * 0.94F, 6.2F, -0.8F, -0.55F, 0, -i * 0.27F));
            }
            PartDefinition sac = body.addOrReplaceChild(name + "_sac",
                    box(Material.SAC, -1.5F, -1.2F, -3, 3, 4.2F, 9),
                    PartPose.offsetAndRotation(side * (half + 0.45F), -0.7F, 2.0F, 0.25F, side * 0.21F, side * 0.36F));
            sac.addOrReplaceChild("sac_cap", box(Material.SHELL, -1.45F, -1.25F, -3.4F, 2.9F, 2.5F, 2.8F), PartPose.ZERO);
            sac.addOrReplaceChild("sac_rim", box(Material.SPINE, -0.75F, -2.1F, -0.7F, 1.5F, 1.3F, 6),
                    PartPose.rotation(0.09F, 0, 0));
        }

        PartDefinition leg = body.addOrReplaceChild(name + "_leg",
                box(Material.SHELL, -1.6F, -0.8F, -1.8F, 3.2F, 5.7F, 3.6F),
                PartPose.offsetAndRotation(side * (half - 0.55F), 2.5F, 3.2F, -0.59F, 0, -side * 0.25F));
        PartDefinition shin = leg.addOrReplaceChild("shin",
                box(Material.MEMBRANE, -1.1F, -1, -1.2F, 2.2F, 6.3F, 2.1F),
                PartPose.offsetAndRotation(0, builder ? 4.75F : 4.1F, -0.25F, 1.08F, 0, side * 0.10F));
        shin.addOrReplaceChild("foot", box(Material.CLAW, -1.4F, -0.3F, -3.25F, 2.8F, 1.5F, 4.1F),
                PartPose.offsetAndRotation(0, builder ? 5.15F : 4.1F, 0.25F, -0.49F, 0, side * 0.15F));
    }

    private static void addTail(PartDefinition body, boolean builder)
    {
        float length = builder ? 5.8F : 6.2F;
        PartDefinition segment = body;
        for (int i = 0; i < 5; i++)
        {
            float width = 3.2F - i * 0.48F;
            final float pieceLength = i == 4 ? length + 0.8F : length;
            segment = segment.addOrReplaceChild(i == 0 ? "tail" : "segment_" + i,
                    box(Material.TAIL, -width / 2, -width / 2, -0.65F, width, width, pieceLength + 0.7F),
                    PartPose.offsetAndRotation(0, i == 0 ? 1.0F : 0, i == 0 ? 6.8F : length,
                            i == 0 ? -0.06F : 0.13F, 0, 0));
            if (i != 4)
            {
                segment.addOrReplaceChild("scute", box(Material.SPINE, -width * 0.35F, -width * 0.8F - 0.1F, 0.7F,
                        width * 0.7F, 1.5F, 2.7F), PartPose.rotation(-0.16F, 0, 0));
            }
        }
        segment.addOrReplaceChild("tip", box(Material.CLAW, -0.46F, -0.38F, 0, 0.92F, 0.76F, 3),
                PartPose.offsetAndRotation(0, 0, length + 0.5F, builder ? -0.39F : -0.23F, 0, 0));
    }

    private static CubeListBuilder box(Material material, float x, float y, float z, float w, float h, float d)
    {
        return CubeListBuilder.create().texOffs(material.u, material.v).addBox(x, y, z, w, h, d);
    }

    // Each material owns a 64 by 48 pixel cell in the dedicated Weaver atlases.
    private enum Material
    {
        COWL(0, 0), SHELL(64, 0), MEMBRANE(128, 0), TOOL(192, 0),
        SAC(0, 48), SPINE(64, 48), TAIL(128, 48), CLAW(192, 48);

        final int u;
        final int v;

        Material(int u, int v)
        {
            this.u = u;
            this.v = v;
        }
    }
}
