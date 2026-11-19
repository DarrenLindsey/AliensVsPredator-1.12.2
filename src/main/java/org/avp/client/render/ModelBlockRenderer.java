package org.avp.client.render;

import org.avp.block.TrophyBlock;
import org.avp.block.entity.ModelBlockEntity;
import org.avp.client.model.GeneratedModel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Draws skulls, biomasks, heads and ferns using models converted from 1.12.2.
 * The same {@link #draw} is used for the item in your hand/inventory (ModelBlockItemRenderer).
 */
public class ModelBlockRenderer implements BlockEntityRenderer<ModelBlockEntity>
{
    // the three greens from the 1.12.2 TileEntityPlant, plus the rare white fern
    private static final float[][] FERN_COLORS = { { 0.1F, 0.2F, 0.05F }, { 0.15F, 0.25F, 0.05F }, { 0.1F, 0.25F, 0.05F } };
    private static final float[] FERN_WHITE = { 1F, 1F, 1F };

    private final EntityModelSet models;

    public ModelBlockRenderer(BlockEntityRendererProvider.Context context)
    {
        this.models = context.getModelSet();
    }

    @Override
    public void render(ModelBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay)
    {
        draw(be.getBlockState(), be.getBlockPos(), models, poseStack, buffers, light, overlay);
    }

    /**
     * @param pos the block position, or null when drawing the item
     */
    public static void draw(BlockState state, BlockPos pos, EntityModelSet models, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay)
    {
        ModelBlockRenderInfo.Info info = ModelBlockRenderInfo.get(state.getBlock());
        if (info == null)
        {
            return;
        }
        GeneratedModel model = ModelBlockRenderInfo.model(info, models);
        VertexConsumer vc = buffers.getBuffer(RenderType.entityCutoutNoCull(info.texture()));
        poseStack.pushPose();
        int color = -1; // white = no tint

        if (info.plant())
        {
            // size/colour/turn picked from the position, so every fern looks a bit different but never changes
            RandomSource rand = RandomSource.create(pos == null ? 0 : Mth.getSeed(pos));
            float r = rand.nextFloat();
            float size = r < 0.16F ? 0.4F : r < 0.44F ? 0.6F : 1.0F; // same odds as 1.12.2 PlantSize.random
            float[] c = rand.nextInt(150) == 0 ? FERN_WHITE : FERN_COLORS[rand.nextInt(FERN_COLORS.length)];
            float offX = pos == null ? 0 : (rand.nextFloat() - 0.5F) * 0.3F;
            float offZ = pos == null ? 0 : (rand.nextFloat() - 0.5F) * 0.3F;
            float turn = pos == null ? 0 : rand.nextInt(4) * 90F;
            color = FastColor.ARGB32.color(255, Math.round(c[0] * 255), Math.round(c[1] * 255), Math.round(c[2] * 255));

            poseStack.translate(0.5F, 0, 0.5F);
            poseStack.scale(size, size, size);
            poseStack.translate(offX, info.y(), offZ);
            poseStack.mulPose(Axis.XP.rotationDegrees(180F));
            poseStack.mulPose(Axis.YP.rotationDegrees(turn));
        }
        else
        {
            Direction facing = state.hasProperty(TrophyBlock.FACING) ? state.getValue(TrophyBlock.FACING) : Direction.SOUTH;
            poseStack.translate(0.5F, 0, 0.5F);
            poseStack.scale(0.64F, -0.64F, 0.64F);
            poseStack.mulPose(Axis.YP.rotationDegrees(180F - facing.toYRot()));
            poseStack.scale(info.scale(), info.scale(), info.scale());
            if (info.turnY() != 0)
            {
                poseStack.mulPose(Axis.YP.rotationDegrees(info.turnY()));
            }
            poseStack.translate(info.x(), info.y(), info.z());
            if (state.hasProperty(TrophyBlock.ORIENTATION))
            {
                switch (state.getValue(TrophyBlock.ORIENTATION))
                {
                    case FLAT ->
                    {
                        poseStack.translate(-0.4F, 0.71F, 0);
                        poseStack.mulPose(Axis.ZP.rotationDegrees(-53.5F));
                    }
                    case WALL ->
                    {
                        poseStack.translate(-0.25F, -0.1F, 0);
                        poseStack.mulPose(Axis.ZP.rotationDegrees(37.5F));
                    }
                    default ->
                    {
                    }
                }
            }
        }

        if (info.parts().isEmpty())
        {
            model.root().render(poseStack, vc, light, overlay, color);
        }
        else
        {
            for (String name : info.parts())
            {
                ModelPart part = model.parts().get(name);
                if (part != null)
                {
                    part.render(poseStack, vc, light, overlay, color);
                }
            }
        }
        poseStack.popPose();
    }
}
