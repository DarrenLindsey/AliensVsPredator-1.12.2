package org.avp.client.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.avp.block.StorageBlock;
import org.avp.block.TrophyBlock;
import org.avp.client.model.GeneratedModel;
import org.avp.config.AvpConfig;

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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import org.joml.Vector3f;

/**
 * Draws skulls, biomasks, heads and ferns using models converted from 1.12.2.
 * The same {@link #draw} is used for the item in your hand/inventory (ModelBlockItemRenderer).
 */
public class ModelBlockRenderer<T extends BlockEntity> implements BlockEntityRenderer<T>
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
    public void render(T be, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay)
    {
        draw(be.getBlockState(), be.getBlockPos(), models, poseStack, buffers, light, overlay);
    }

    /** Lockers are two blocks tall: make sure they aren't hidden when only the top half is on screen. */
    @Override
    public AABB getRenderBoundingBox(T be)
    {
        return new AABB(be.getBlockPos()).expandTowards(0, 1, 0);
    }

    private static int channel(float value)
    {
        return Math.round(Mth.clamp(value, 0F, 1F) * 255);
    }

    /**
     * Draws the model for a block.
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
        if (info.kind() == ModelBlockRenderInfo.Kind.TROPHY && pos != null)
        {
            placeOnFloor(info, state, models, poseStack);
        }
        int color = transform(info, state, pos, poseStack);
        if (info.poser() != null)
        {
            info.poser().pose(model, state);
        }
        for (ModelPart part : partsToDraw(info, model))
        {
            part.render(poseStack, vc, light, overlay, color);
        }
        poseStack.popPose();
    }

    /**
     * Measures the model exactly as {@link #draw} would place it, in block units.
     * @return {minX, minY, minZ, maxX, maxY, maxZ}, or null if the block has no model
     */
    public static float[] bounds(BlockState state, EntityModelSet models)
    {
        ModelBlockRenderInfo.Info info = ModelBlockRenderInfo.get(state.getBlock());
        if (info == null)
        {
            return null;
        }
        GeneratedModel model = ModelBlockRenderInfo.model(info, models);
        PoseStack poseStack = new PoseStack();
        transform(info, state, null, poseStack);
        if (info.poser() != null)
        {
            info.poser().pose(model, state);
        }
        float[] b = { Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE };
        Vector3f corner = new Vector3f();
        for (ModelPart part : partsToDraw(info, model))
        {
            part.visit(poseStack, (pose, path, index, cube) ->
            {
                // the 8 corners of each box (box sizes are in pixels, 16 per block)
                for (int i = 0; i < 8; i++)
                {
                    corner.set((i & 1) == 0 ? cube.minX : cube.maxX, (i & 2) == 0 ? cube.minY : cube.maxY, (i & 4) == 0 ? cube.minZ : cube.maxZ)
                            .div(16F);
                    pose.pose().transformPosition(corner);
                    b[0] = Math.min(b[0], corner.x); b[1] = Math.min(b[1], corner.y); b[2] = Math.min(b[2], corner.z);
                    b[3] = Math.max(b[3], corner.x); b[4] = Math.max(b[4], corner.y); b[5] = Math.max(b[5], corner.z);
                }
            });
        }
        return b[0] == Float.MAX_VALUE ? null : b;
    }

    /** Measured size of each trophy block state (they don't change, so measure once). */
    private static final Map<BlockState, float[]> TROPHY_BOUNDS = new java.util.concurrent.ConcurrentHashMap<>();

    /**
     * Scales a placed trophy to its chosen size (Info.size x the "Trophy Size" setting),
     * centres it in the block and stands it on the block's floor. The old 1.12.2
     * per-skull offsets left many of them tiny, floating or sunk into the ground.
     */
    private static void placeOnFloor(ModelBlockRenderInfo.Info info, BlockState state, EntityModelSet models, PoseStack poseStack)
    {
        float[] b = TROPHY_BOUNDS.computeIfAbsent(state, s -> {
            float[] measured = bounds(s, models);
            return measured != null ? measured : new float[0];
        });
        if (b.length == 0)
        {
            return;
        }
        float longest = Math.max(b[3] - b[0], Math.max(b[4] - b[1], b[5] - b[2]));
        if (longest <= 0)
        {
            return;
        }
        float scale = info.size() * (float) AvpConfig.client(AvpConfig.CLIENT.trophySize) / longest;
        poseStack.translate(0.5F, 0, 0.5F);
        poseStack.scale(scale, scale, scale);
        poseStack.translate(-(b[0] + b[3]) / 2F, -b[1], -(b[2] + b[5]) / 2F);
    }

    private static List<ModelPart> partsToDraw(ModelBlockRenderInfo.Info info, GeneratedModel model)
    {
        if (info.parts().isEmpty())
        {
            return List.of(model.root());
        }
        List<ModelPart> parts = new ArrayList<>();
        for (String name : info.parts())
        {
            ModelPart part = model.parts().get(name);
            if (part != null)
            {
                parts.add(part);
            }
        }
        return parts;
    }

    /** Moves/turns/scales the pose like the 1.12.2 renderer did. Returns the tint colour. */
    private static int transform(ModelBlockRenderInfo.Info info, BlockState state, BlockPos pos, PoseStack poseStack)
    {
        int color = -1; // white = no tint
        if (info.kind() == ModelBlockRenderInfo.Kind.PROP)
        {
            // the 1.12.2 TESR transform: centre, lift, flip upside down, face the player
            Direction facing = state.hasProperty(StorageBlock.FACING) ? state.getValue(StorageBlock.FACING) : Direction.SOUTH;
            poseStack.translate(0.5F + info.x(), info.y(), 0.5F + info.z());
            poseStack.scale(info.scale(), -info.scale(), info.scale());
            poseStack.mulPose(Axis.YP.rotationDegrees(180F - facing.toYRot()));
            return color;
        }
        if (info.kind() == ModelBlockRenderInfo.Kind.PLANT)
        {
            // size/colour/turn picked from the position, so every fern looks a bit different but never changes
            RandomSource rand = RandomSource.create(pos == null ? 0 : Mth.getSeed(pos));
            float r = rand.nextFloat();
            float size = r < 0.16F ? 0.4F : r < 0.44F ? 0.6F : 1.0F; // same odds as 1.12.2 PlantSize.random
            float[] c = rand.nextInt(150) == 0 ? FERN_WHITE : FERN_COLORS[rand.nextInt(FERN_COLORS.length)];
            float offX = pos == null ? 0 : (rand.nextFloat() - 0.5F) * 0.3F;
            float offZ = pos == null ? 0 : (rand.nextFloat() - 0.5F) * 0.3F;
            float turn = pos == null ? 0 : rand.nextInt(4) * 90F;
            float bright = (float) AvpConfig.client(AvpConfig.CLIENT.fernBrightness);
            color = FastColor.ARGB32.color(255, channel(c[0] * bright), channel(c[1] * bright), channel(c[2] * bright));

            poseStack.translate(0.5F, 0, 0.5F);
            poseStack.scale(size, size, size);
            poseStack.translate(offX, info.y(), offZ);
            poseStack.mulPose(Axis.XP.rotationDegrees(180F));
            poseStack.mulPose(Axis.YP.rotationDegrees(turn));
            return color;
        }

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
        return color;
    }
}
