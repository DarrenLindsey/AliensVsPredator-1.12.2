package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.mining.PowerLoaderEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/** The Power Loader: yellow cage frame on two legs, a power pack behind, two claw arms. */
public class PowerLoaderRenderer extends MobRenderer<PowerLoaderEntity, PowerLoaderRenderer.Model>
{
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "power_loader"), "main");
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/power_loader.png");

    public PowerLoaderRenderer(EntityRendererProvider.Context context)
    {
        super(context, new Model(context.bakeLayer(LAYER)), 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(PowerLoaderEntity entity)
    {
        return TEXTURE;
    }

    @Override
    public void render(PowerLoaderEntity loader, float yaw, float pt, PoseStack pose, MultiBufferSource buffers, int light)
    {
        super.render(loader, yaw, pt, pose, buffers, light);
        loader.carried().ifPresent(state ->
        {
            // the lifted block, held out between the claws
            pose.pushPose();
            float body = Mth.rotLerp(pt, loader.yBodyRotO, loader.yBodyRot);
            pose.mulPose(Axis.YP.rotationDegrees(-body));
            pose.translate(-0.45, 1.05, 1.15);
            pose.scale(0.9F, 0.9F, 0.9F);
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, pose, buffers, light, OverlayTexture.NO_OVERLAY);
            pose.popPose();
        });
    }

    public static LayerDefinition createLayer()
    {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        // y is down; the ground is at 24
        root.addOrReplaceChild("pelvis", CubeListBuilder.create().texOffs(0, 0).addBox(-10, -3, -5, 20, 6, 10), PartPose.offset(0, 0, 0));
        root.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(0, 16).addBox(-3, 0, -3.5F, 6, 21, 7)
                .texOffs(0, 44).addBox(-4, 21, -7, 8, 3, 12), PartPose.offset(6.5F, 0, 0));
        root.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-3, 0, -3.5F, 6, 21, 7)
                .texOffs(0, 44).addBox(-4, 21, -7, 8, 3, 12), PartPose.offset(-6.5F, 0, 0));
        PartDefinition cage = root.addOrReplaceChild("cage", CubeListBuilder.create()
                .texOffs(60, 0).addBox(-10, -26, -6, 2, 23, 2)
                .texOffs(60, 0).addBox(8, -26, -6, 2, 23, 2)
                .texOffs(60, 0).addBox(-10, -26, 5, 2, 23, 2)
                .texOffs(60, 0).addBox(8, -26, 5, 2, 23, 2)
                .texOffs(60, 26).addBox(-10, -28, -6, 20, 2, 13)
                .texOffs(28, 20).addBox(-6, -10, 3, 12, 10, 2), PartPose.ZERO);
        cage.addOrReplaceChild("pack", CubeListBuilder.create().texOffs(68, 42).addBox(-8, -24, 7, 16, 16, 6), PartPose.ZERO);
        root.addOrReplaceChild("leftArm", CubeListBuilder.create()
                .texOffs(44, 0).addBox(-3, -3, -3, 6, 6, 6)
                .texOffs(28, 34).addBox(-2, -2, -18, 4, 4, 16)
                .texOffs(100, 0).addBox(-3, -4, -27, 2, 2, 10)
                .texOffs(100, 0).addBox(1, -4, -27, 2, 2, 10)
                .texOffs(100, 14).addBox(-3, 2, -27, 2, 2, 10)
                .texOffs(100, 14).addBox(1, 2, -27, 2, 2, 10), PartPose.offset(13, -16, -2));
        root.addOrReplaceChild("rightArm", CubeListBuilder.create()
                .texOffs(44, 0).mirror().addBox(-3, -3, -3, 6, 6, 6)
                .texOffs(28, 34).addBox(-2, -2, -18, 4, 4, 16)
                .texOffs(100, 0).addBox(-3, -4, -27, 2, 2, 10)
                .texOffs(100, 0).addBox(1, -4, -27, 2, 2, 10)
                .texOffs(100, 14).addBox(-3, 2, -27, 2, 2, 10)
                .texOffs(100, 14).addBox(1, 2, -27, 2, 2, 10), PartPose.offset(-13, -16, -2));
        return LayerDefinition.create(mesh, 128, 64);
    }

    public static class Model extends EntityModel<PowerLoaderEntity>
    {
        private final ModelPart root, leftLeg, rightLeg, leftArm, rightArm;

        public Model(ModelPart root)
        {
            this.root = root;
            this.leftLeg = root.getChild("leftLeg");
            this.rightLeg = root.getChild("rightLeg");
            this.leftArm = root.getChild("leftArm");
            this.rightArm = root.getChild("rightArm");
        }

        @Override
        public void setupAnim(PowerLoaderEntity loader, float limbSwing, float limbAmount, float age, float headYaw, float headPitch)
        {
            float walk = Mth.cos(limbSwing * 0.5F) * 0.6F * limbAmount;
            leftLeg.xRot = walk;
            rightLeg.xRot = -walk;
            float lift = loader.carried().isPresent() ? -0.35F : 0.15F;
            leftArm.xRot = lift - walk * 0.3F;
            rightArm.xRot = lift + walk * 0.3F;
            if (attackTime > 0)
            {
                float swing = Mth.sin(attackTime * (float) Math.PI);
                rightArm.xRot = lift - swing * 1.1F;
                rightArm.z = -2 - swing * 6;
            }
            else
            {
                rightArm.z = -2;
            }
        }

        @Override
        public void renderToBuffer(PoseStack pose, VertexConsumer buffer, int light, int overlay, int colour)
        {
            root.render(pose, buffer, light, overlay, colour); // the hips are 1.5 blocks up
        }
    }
}
