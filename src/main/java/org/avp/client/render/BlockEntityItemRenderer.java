package org.avp.client.render;

import java.util.Map;

import org.avp.registry.ModBlocks;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Draws items for blocks that have their own block entity renderer (lights, cryostasis tube)
 * by drawing a stand-in block entity - the same trick vanilla uses for chest items.
 */
public class BlockEntityItemRenderer extends BlockEntityWithoutLevelRenderer
{
    public BlockEntityItemRenderer()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    /** Blocks whose model is bigger than one block get shrunk to fit inventory slots. */
    private static float itemScale(Block block)
    {
        return Map.of(ModBlocks.CRYOSTASISTUBE.get(), 0.5F, ModBlocks.MEDPOD.get(), 0.35F,
                ModBlocks.BLASTDOOR.get(), 0.25F, ModBlocks.BLASTDOOR_SEVASTOPOL.get(), 0.33F).getOrDefault(block, 1F);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay)
    {
        if (!(stack.getItem() instanceof BlockItem blockItem))
        {
            return;
        }
        Block block = blockItem.getBlock();
        float scale = itemScale(block);
        poseStack.pushPose();
        poseStack.translate(0.5F, 0, 0.5F);
        poseStack.scale(scale, scale, scale);
        poseStack.translate(-0.5F, 0, -0.5F);
        if (block instanceof EntityBlock entityBlock)
        {
            BlockEntity be = entityBlock.newBlockEntity(BlockPos.ZERO, block.defaultBlockState());
            if (be != null)
            {
                Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(be, poseStack, buffers, light, overlay);
            }
        }
        poseStack.popPose();
    }
}
