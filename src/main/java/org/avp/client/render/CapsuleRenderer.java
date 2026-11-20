package org.avp.client.render;

import org.avp.pneumatic.CapsuleEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/** A PneuPost canister: the canister item, spinning gently as it shoots through the tube. */
public class CapsuleRenderer extends EntityRenderer<CapsuleEntity>
{
    private final ItemRenderer items;
    private ItemStack look;

    public CapsuleRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        items = context.getItemRenderer();
    }

    @Override
    public void render(CapsuleEntity capsule, float yaw, float pt, PoseStack pose, MultiBufferSource buffers, int light)
    {
        if (look == null)
        {
            look = new ItemStack(org.avp.pneumatic.Pneumatics.CANISTER.get());
        }
        pose.pushPose();
        pose.translate(0, 0.15, 0);
        pose.mulPose(Axis.YP.rotationDegrees((capsule.tickCount + pt) * 20F));
        pose.scale(0.45F, 0.45F, 0.45F);
        items.renderStatic(look, ItemDisplayContext.FIXED, light, OverlayTexture.NO_OVERLAY, pose, buffers, capsule.level(), capsule.getId());
        pose.popPose();
        super.render(capsule, yaw, pt, pose, buffers, light);
    }

    @Override
    public ResourceLocation getTextureLocation(CapsuleEntity entity)
    {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
