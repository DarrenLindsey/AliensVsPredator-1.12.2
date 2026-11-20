package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.block.conveyor.spline.BeltAnchorBlockEntity;
import org.avp.block.conveyor.spline.HoseAnchorBlockEntity;
import org.avp.block.conveyor.spline.Spline;
import org.avp.block.conveyor.spline.SplineAnchorBlock;
import org.avp.block.conveyor.spline.SplineAnchorBlockEntity;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;

/** Draws spline lines from their start anchor: belts as a moving ribbon carrying items, cables and hoses as hanging tubes. */
public class SplineRenderer implements BlockEntityRenderer<SplineAnchorBlockEntity>
{
    private static final ResourceLocation BELT = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "block/conveyor_top");
    private static final ResourceLocation PLAIN = ResourceLocation.withDefaultNamespace("block/white_concrete");

    public SplineRenderer(BlockEntityRendererProvider.Context context)
    {
    }

    private static TextureAtlasSprite sprite(ResourceLocation id)
    {
        return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(id);
    }

    @Override
    public void render(SplineAnchorBlockEntity be, float pt, PoseStack pose, MultiBufferSource buffers, int light, int overlay)
    {
        Spline spline = be.spline();
        if (spline == null || be.getLevel() == null)
        {
            return;
        }
        BlockPos origin = be.getBlockPos();
        Vec3 base = Vec3.atLowerCornerOf(origin);
        SplineAnchorBlock.Kind kind = be.kind();
        int segments = Math.max(6, (int) Math.ceil(spline.length * 2));
        VertexConsumer vc = buffers.getBuffer(RenderType.cutout());
        switch (kind)
        {
            case BELT ->
            {
                TextureAtlasSprite belt = sprite(BELT);
                ribbon(be, pose, vc, spline, base, segments, 0.34, false, belt, 1F, 1F, 1F);
                // the frame under it
                ribbon(be, pose, vc, spline.offset(-0.08), base, segments, 0.38, false, sprite(PLAIN), 0.25F, 0.26F, 0.28F);
            }
            case CABLE -> tube(be, pose, vc, spline, base, segments, 0.06, sprite(PLAIN), 0.12F, 0.12F, 0.13F);
            case HOSE ->
            {
                float r = 0.75F, g = 0.38F, b = 0.12F; // empty: orange rubber
                if (be instanceof HoseAnchorBlockEntity hose && !hose.shown().isEmpty())
                {
                    int tint = IClientFluidTypeExtensions.of(hose.shown().getFluid()).getTintColor(hose.shown());
                    r = ((tint >> 16) & 255) / 255F * 0.8F + 0.2F;
                    g = ((tint >> 8) & 255) / 255F * 0.8F + 0.2F;
                    b = (tint & 255) / 255F * 0.8F + 0.2F;
                    if (hose.shown().getFluid().isSame(net.minecraft.world.level.material.Fluids.LAVA))
                    {
                        r = 1F;
                        g = 0.45F;
                        b = 0.1F;
                    }
                }
                tube(be, pose, vc, spline, base, segments, 0.11, sprite(PLAIN), r, g, b);
            }
        }
        if (be instanceof BeltAnchorBlockEntity anchor)
        {
            var items = Minecraft.getInstance().getItemRenderer();
            for (int i = 0; i < anchor.items.size(); i++)
            {
                BeltAnchorBlockEntity.Moving m = anchor.items.get(i);
                double at = Math.min(spline.length, m.at + anchor.speed() * pt);
                double t = spline.param(at);
                Vec3 p = spline.at(t).subtract(base);
                Vec3 dir = spline.tangent(t);
                pose.pushPose();
                pose.translate(p.x, p.y + 0.12, p.z);
                pose.mulPose(Axis.YP.rotation((float) Math.atan2(dir.x, dir.z)));
                pose.scale(0.5F, 0.5F, 0.5F);
                int l = LevelRenderer.getLightColor(be.getLevel(), BlockPos.containing(spline.at(t)));
                items.renderStatic(m.stack, ItemDisplayContext.GROUND, l, OverlayTexture.NO_OVERLAY, pose, buffers, be.getLevel(), i);
                pose.popPose();
            }
        }
    }

    /** A flat strip along the spline, facing up. */
    private static void ribbon(SplineAnchorBlockEntity be, PoseStack pose, VertexConsumer vc, Spline spline, Vec3 base, int segments, double half,
            boolean vertical, TextureAtlasSprite sprite, float r, float g, float b)
    {
        for (int i = 0; i < segments; i++)
        {
            double t0 = (double) i / segments, t1 = (double) (i + 1) / segments;
            Vec3 a = spline.at(t0), c = spline.at(t1);
            Vec3 sa = side(spline.tangent(t0), vertical).scale(half), sc = side(spline.tangent(t1), vertical).scale(half);
            int light = LevelRenderer.getLightColor(be.getLevel(), BlockPos.containing(a));
            quad(pose, vc, a.subtract(sa).subtract(base), a.add(sa).subtract(base), c.add(sc).subtract(base), c.subtract(sc).subtract(base), sprite, light,
                    r, g, b);
        }
    }

    /** A thin square tube along the spline (two crossed strips). */
    private static void tube(SplineAnchorBlockEntity be, PoseStack pose, VertexConsumer vc, Spline spline, Vec3 base, int segments, double half,
            TextureAtlasSprite sprite, float r, float g, float b)
    {
        ribbon(be, pose, vc, spline, base, segments, half, false, sprite, r, g, b);
        ribbon(be, pose, vc, spline, base, segments, half, true, sprite, r * 0.8F, g * 0.8F, b * 0.8F);
    }

    private static Vec3 side(Vec3 tangent, boolean vertical)
    {
        Vec3 s = tangent.cross(new Vec3(0, 1, 0));
        if (s.lengthSqr() < 1e-6)
        {
            s = new Vec3(1, 0, 0);
        }
        s = s.normalize();
        return vertical ? s.cross(tangent).normalize() : s;
    }

    private static void quad(PoseStack pose, VertexConsumer vc, Vec3 a, Vec3 b, Vec3 c, Vec3 d, TextureAtlasSprite sprite, int light, float r, float g, float bl)
    {
        Vec3 n = b.subtract(a).cross(d.subtract(a));
        Vector3f normal = n.lengthSqr() < 1e-9 ? new Vector3f(0, 1, 0) : new Vector3f((float) n.x, (float) n.y, (float) n.z).normalize();
        PoseStack.Pose last = pose.last();
        float u0 = sprite.getU(0), u1 = sprite.getU(1), v0 = sprite.getV(0), v1 = sprite.getV(1);
        // both faces, so it shows from above and below
        vertex(vc, last, a, u0, v0, light, r, g, bl, normal);
        vertex(vc, last, d, u0, v1, light, r, g, bl, normal);
        vertex(vc, last, c, u1, v1, light, r, g, bl, normal);
        vertex(vc, last, b, u1, v0, light, r, g, bl, normal);
        normal.negate();
        vertex(vc, last, b, u1, v0, light, r * 0.7F, g * 0.7F, bl * 0.7F, normal);
        vertex(vc, last, c, u1, v1, light, r * 0.7F, g * 0.7F, bl * 0.7F, normal);
        vertex(vc, last, d, u0, v1, light, r * 0.7F, g * 0.7F, bl * 0.7F, normal);
        vertex(vc, last, a, u0, v0, light, r * 0.7F, g * 0.7F, bl * 0.7F, normal);
    }

    private static void vertex(VertexConsumer vc, PoseStack.Pose pose, Vec3 p, float u, float v, int light, float r, float g, float b, Vector3f n)
    {
        vc.addVertex(pose, (float) p.x, (float) p.y, (float) p.z).setColor(r, g, b, 1F).setUv(u, v).setLight(light).setNormal(pose, n.x, n.y, n.z);
    }

    @Override
    public boolean shouldRenderOffScreen(SplineAnchorBlockEntity be)
    {
        return true;
    }

    @Override
    public int getViewDistance()
    {
        return 128;
    }

    @Override
    public AABB getRenderBoundingBox(SplineAnchorBlockEntity be)
    {
        return be.renderBox();
    }
}
