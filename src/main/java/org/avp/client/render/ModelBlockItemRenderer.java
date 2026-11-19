package org.avp.client.render;

import java.util.IdentityHashMap;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Draws the 3D model for skull/biomask/head/fern items.
 *
 * In the world these models keep their real size (a tree fern is taller than a
 * block). For the item, the model is measured once and shrunk/centred to fit a
 * one-block space, so it sits inside inventory slots like a normal block
 * instead of spilling out of them (which also happened in 1.12.2).
 */
public class ModelBlockItemRenderer extends BlockEntityWithoutLevelRenderer
{
    /** How much of the one-block space the model fills (a little margin looks nicer). */
    private static final float FILL = 0.95F;

    private final Map<Block, float[]> boundsCache = new IdentityHashMap<>();

    public ModelBlockItemRenderer()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager)
    {
        boundsCache.clear();
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay)
    {
        if (!(stack.getItem() instanceof BlockItem blockItem))
        {
            return;
        }
        BlockState state = blockItem.getBlock().defaultBlockState();
        float[] b = boundsCache.computeIfAbsent(state.getBlock(),
                block -> ModelBlockRenderer.bounds(state, Minecraft.getInstance().getEntityModels()));
        poseStack.pushPose();
        if (b != null)
        {
            float size = Math.max(b[3] - b[0], Math.max(b[4] - b[1], b[5] - b[2]));
            float scale = size > 0 ? FILL / size : 1F;
            // centre the model in the block space, then scale it to fit
            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.scale(scale, scale, scale);
            poseStack.translate(-(b[0] + b[3]) / 2F, -(b[1] + b[4]) / 2F, -(b[2] + b[5]) / 2F);
        }
        ModelBlockRenderer.draw(state, null, Minecraft.getInstance().getEntityModels(), poseStack, buffers, light, overlay);
        poseStack.popPose();
    }
}
