package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.client.model.geometry.LaserMineModel;
import org.avp.client.model.geometry.SupplyChuteModel;
import org.avp.entity.projectile.LaserMineEntity;
import org.avp.entity.projectile.SupplyDropEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/** Renderers for the gear entities: the laser mine (and its beam) and the supply drop under its chute. */
public final class GearEntityRenderers
{
    public static class LaserMine extends EntityRenderer<LaserMineEntity>
    {
        private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/laser_mine.png");
        private final LaserMineModel model;

        public LaserMine(EntityRendererProvider.Context context)
        {
            super(context);
            model = new LaserMineModel(context.bakeLayer(LaserMineModel.LAYER));
        }

        @Override
        public void render(LaserMineEntity mine, float yaw, float partialTick, PoseStack pose, MultiBufferSource buffers, int light)
        {
            Direction facing = mine.facing();
            pose.pushPose();
            pose.translate(0, 0.15, 0);
            // face out of the wall: the model's front is -Z
            if (facing.getAxis().isHorizontal())
            {
                pose.mulPose(Axis.YP.rotationDegrees(180F - facing.toYRot()));
            }
            else
            {
                pose.mulPose(Axis.XP.rotationDegrees(facing == Direction.UP ? -90F : 90F));
            }
            pose.pushPose();
            pose.scale(0.5F, 0.5F, 0.5F);
            pose.scale(-1F, -1F, 1F);
            model.root().render(pose, buffers.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, OverlayTexture.NO_OVERLAY);
            pose.popPose();
            // the red beam, straight out
            float length = mine.beamLength();
            VertexConsumer beam = buffers.getBuffer(RenderType.lightning());
            float w = 0.02F;
            var m = pose.last().pose();
            for (int side = 0; side < 2; side++)
            {
                float dx = side == 0 ? w : 0, dy = side == 0 ? 0 : w;
                beam.addVertex(m, -dx, -dy, 0).setColor(1F, 0.1F, 0.1F, 0.8F);
                beam.addVertex(m, dx, dy, 0).setColor(1F, 0.1F, 0.1F, 0.8F);
                beam.addVertex(m, dx, dy, -length).setColor(1F, 0.1F, 0.1F, 0.6F);
                beam.addVertex(m, -dx, -dy, -length).setColor(1F, 0.1F, 0.1F, 0.6F);
            }
            pose.popPose();
            super.render(mine, yaw, partialTick, pose, buffers, light);
        }

        @Override
        public ResourceLocation getTextureLocation(LaserMineEntity mine)
        {
            return TEXTURE;
        }
    }

    public static class SupplyDrop extends EntityRenderer<SupplyDropEntity>
    {
        private static final ResourceLocation[] TEXTURES = {
                ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/supplychute.png"),
                ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/supplychute_marines.png"),
                ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/supplychute_seegson.png") };
        private final SupplyChuteModel model;

        public SupplyDrop(EntityRendererProvider.Context context)
        {
            super(context);
            model = new SupplyChuteModel(context.bakeLayer(SupplyChuteModel.LAYER));
        }

        @Override
        public void render(SupplyDropEntity drop, float yaw, float partialTick, PoseStack pose, MultiBufferSource buffers, int light)
        {
            pose.pushPose();
            float sway = Mth.sin((drop.tickCount + partialTick) * 0.05F) * 6F;
            pose.mulPose(Axis.ZP.rotationDegrees(sway));
            pose.scale(-1F, -1F, 1F);
            pose.translate(0, -1.5F, 0);
            model.root().render(pose, buffers.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(drop))), light, OverlayTexture.NO_OVERLAY);
            pose.popPose();
            super.render(drop, yaw, partialTick, pose, buffers, light);
        }

        @Override
        public ResourceLocation getTextureLocation(SupplyDropEntity drop)
        {
            return TEXTURES[drop.kind()];
        }
    }

    private GearEntityRenderers()
    {
    }
}
