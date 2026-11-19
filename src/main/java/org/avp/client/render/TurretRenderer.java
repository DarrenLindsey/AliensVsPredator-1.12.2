package org.avp.client.render;

import java.util.List;

import org.avp.AliensVsPredator;
import org.avp.block.TurretBlock;
import org.avp.block.entity.TurretBlockEntity;
import org.avp.client.model.geometry.TurretModel;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Draws the sentry turret (1.12.2 RenderTurret): the head and barrel turn to the aim,
 * a red targeting laser shows where it points, shots leave a brief tracer, and with an
 * LED Display upgrade a round counter floats above it.
 */
public class TurretRenderer implements BlockEntityRenderer<TurretBlockEntity>
{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/tile/turret.png");

    private final TurretModel model;
    private final List<ModelPart> head;
    private final float floor;
    private final Font font;

    public TurretRenderer(BlockEntityRendererProvider.Context context)
    {
        model = new TurretModel(context.bakeLayer(TurretModel.LAYER));
        head = List.of(model.barrel, model.headBase, model.headBase2, model.headBase3, model.barrelGuard, model.sightBase3);
        floor = lowestPoint(model.root());
        font = context.getFont();
    }

    /** Largest model y (model y points down), in blocks: where the feet are. */
    private static float lowestPoint(ModelPart root)
    {
        float[] max = { -Float.MAX_VALUE };
        Vector3f corner = new Vector3f();
        root.visit(new PoseStack(), (pose, path, index, cube) ->
        {
            for (int i = 0; i < 8; i++)
            {
                corner.set((i & 1) == 0 ? cube.minX : cube.maxX, (i & 2) == 0 ? cube.minY : cube.maxY, (i & 4) == 0 ? cube.minZ : cube.maxZ).div(16F);
                pose.pose().transformPosition(corner);
                max[0] = Math.max(max[0], corner.y);
            }
        });
        return max[0];
    }

    @Override
    public void render(TurretBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay)
    {
        float yaw = be.getLevel() == null ? be.getBlockState().getValue(TurretBlock.FACING).toYRot() : be.yaw(partialTick);
        float pitch = be.getLevel() == null ? 0 : be.pitch(partialTick);

        boolean hanging = be.getBlockState().getValue(TurretBlock.HANGING);
        poseStack.pushPose();
        if (hanging)
        {
            // upside down under the ceiling: the model's own "y points down" is already right
            poseStack.translate(0.5F, 1F - floor, 0.5F);
        }
        else
        {
            poseStack.translate(0.5F, floor, 0.5F);
            poseStack.scale(1F, -1F, 1F);
        }
        for (ModelPart part : head)
        {
            part.yRot = -yaw * Mth.DEG_TO_RAD;
            part.xRot = (hanging ? pitch : -pitch) * Mth.DEG_TO_RAD;
        }
        model.root().render(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(TEXTURE)), light, overlay);
        for (ModelPart part : head)
        {
            part.yRot = 0;
            part.xRot = 0;
        }
        poseStack.popPose();

        if (be.getLevel() == null || !be.getBlockState().getValue(TurretBlock.LIT))
        {
            return;
        }
        Vec3 base = Vec3.atLowerCornerOf(be.getBlockPos());
        Vec3 dir = Vec3.directionFromRotation(pitch, yaw);
        Vec3 muzzle = be.pivot().add(dir.scale(TurretBlockEntity.BARREL));
        Matrix4f matrix = poseStack.last().pose();
        VertexConsumer glow = buffers.getBuffer(RenderType.lightning());

        // targeting laser, stopped by the first block in the way
        Vec3 end = muzzle.add(dir.scale(be.range()));
        end = be.getLevel().clip(new ClipContext(muzzle, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
                net.minecraft.world.phys.shapes.CollisionContext.empty())).getLocation();
        line(matrix, glow, muzzle.subtract(base), end.subtract(base), 0.006F, 1F, 0.1F, 0.1F, 0.6F);

        // tracers
        int c = be.loaded().tracerColor;
        for (TurretBlockEntity.Shot shot : be.shots())
        {
            line(matrix, glow, muzzle.subtract(base), shot.to().subtract(base), 0.02F,
                    (c >> 16 & 255) / 255F, (c >> 8 & 255) / 255F, (c & 255) / 255F, 0.9F);
        }

        if (be.hasDisplay())
        {
            drawCounter(be, poseStack, buffers);
        }
    }

    /** "0420" in red above the turret, turned to face the player. */
    private void drawCounter(TurretBlockEntity be, PoseStack poseStack, MultiBufferSource buffers)
    {
        int total = be.totalRounds();
        String text = total > 9999 ? "9999" : String.format("%04d", total);
        poseStack.pushPose();
        poseStack.translate(0.5F, be.getBlockState().getValue(TurretBlock.HANGING) ? -0.35F : 1.2F, 0.5F);
        poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        poseStack.scale(-0.02F, -0.02F, 0.02F);
        float x = -font.width(text) / 2F;
        font.drawInBatch(text, x, 0, total == 0 ? 0xFFFF2020 : 0xFFFF5040, false, poseStack.last().pose(), buffers,
                Font.DisplayMode.SEE_THROUGH, 0x80000000, LightTexture.FULL_BRIGHT);
        poseStack.popPose();
    }

    /** A thin glowing beam from a to b (two crossed quads). */
    private static void line(Matrix4f m, VertexConsumer vc, Vec3 a, Vec3 b, float w, float r, float g, float bl, float alpha)
    {
        quad(m, vc, a, b, w, 0, w, r, g, bl, alpha);
        quad(m, vc, a, b, -w, 0, w, r, g, bl, alpha);
        quad(m, vc, a, b, 0, w, 0, r, g, bl, alpha);
    }

    private static void quad(Matrix4f m, VertexConsumer vc, Vec3 a, Vec3 b, float ox, float oy, float oz, float r, float g, float bl, float alpha)
    {
        vc.addVertex(m, (float) a.x - ox, (float) a.y - oy, (float) a.z - oz).setColor(r, g, bl, alpha);
        vc.addVertex(m, (float) a.x + ox, (float) a.y + oy, (float) a.z + oz).setColor(r, g, bl, alpha);
        vc.addVertex(m, (float) b.x + ox, (float) b.y + oy, (float) b.z + oz).setColor(r, g, bl, alpha * 0.3F);
        vc.addVertex(m, (float) b.x - ox, (float) b.y - oy, (float) b.z - oz).setColor(r, g, bl, alpha * 0.3F);
    }

    @Override
    public AABB getRenderBoundingBox(TurretBlockEntity be)
    {
        return new AABB(be.getBlockPos()).inflate(be.range());
    }

    @Override
    public boolean shouldRenderOffScreen(TurretBlockEntity be)
    {
        return true;
    }
}
