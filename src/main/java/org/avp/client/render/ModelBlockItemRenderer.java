package org.avp.client.render;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/** Draws the 3D model for skull/biomask/head/fern items (their item model points to "builtin/entity"). */
public class ModelBlockItemRenderer extends BlockEntityWithoutLevelRenderer
{
    public ModelBlockItemRenderer()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay)
    {
        if (stack.getItem() instanceof BlockItem blockItem)
        {
            ModelBlockRenderer.draw(blockItem.getBlock().defaultBlockState(), null, Minecraft.getInstance().getEntityModels(),
                    poseStack, buffers, light, overlay);
        }
    }
}
