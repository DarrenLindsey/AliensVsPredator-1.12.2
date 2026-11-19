package org.avp.client.render;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.avp.AliensVsPredator;
import org.avp.block.PowerMachineBlock;
import org.avp.block.PowerMachineBlock.Kind;
import org.avp.block.entity.PowerMachineBlockEntity;
import org.avp.client.model.geometry.PowercellModel;
import org.avp.client.model.geometry.RedstoneFluxGeneratorModel;
import org.avp.client.model.geometry.RepulsionGeneratorModel;
import org.avp.client.model.geometry.SatelliteDishModel;
import org.avp.client.model.geometry.SolarPanelModel;
import org.avp.client.model.geometry.TeslaCoilModel;
import org.avp.client.model.geometry.TransformerModel;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Draws the generators, transformers, power cell, satellite dish and tesla coil with their
 * 1.12.2 models. Each model is measured once and stood on the floor of its block, centred,
 * so they all line up no matter how the original was offset.
 */
public class PowerMachineRenderer implements BlockEntityRenderer<PowerMachineBlockEntity>
{
    private static final ResourceLocation SOLAR = tex("solarpanel.png");
    private static final ResourceLocation GENERATOR = tex("redstonefluxgenerator.png");
    private static final ResourceLocation REPULSION = tex("generator.png");
    private static final ResourceLocation TRANSFORMER = tex("transformer.png");
    private static final ResourceLocation POWERCELL = tex("powercell.png");
    private static final ResourceLocation POWERCELL_LIQUID = tex("powercellliquid.png");
    private static final ResourceLocation DISH = tex("satellite-dish.png");
    private static final ResourceLocation TESLA = tex("teslacoil.png");
    private static final ResourceLocation WORKSTATION = tex("workstation.png");
    private static final ResourceLocation WORKSTATION_ON = tex("workstation-on.png");

    private final SolarPanelModel solar;
    private final RedstoneFluxGeneratorModel generator;
    private final RepulsionGeneratorModel repulsion;
    private final TransformerModel transformer;
    private final PowercellModel powercell;
    private final SatelliteDishModel dish;
    private final TeslaCoilModel tesla;
    private final org.avp.client.model.geometry.WorkstationModel workstation;
    private final net.minecraft.client.gui.Font font;
    private final Map<ModelPart, float[]> bounds = new HashMap<>();

    public PowerMachineRenderer(BlockEntityRendererProvider.Context context)
    {
        solar = new SolarPanelModel(context.bakeLayer(SolarPanelModel.LAYER));
        generator = new RedstoneFluxGeneratorModel(context.bakeLayer(RedstoneFluxGeneratorModel.LAYER));
        repulsion = new RepulsionGeneratorModel(context.bakeLayer(RepulsionGeneratorModel.LAYER));
        transformer = new TransformerModel(context.bakeLayer(TransformerModel.LAYER));
        powercell = new PowercellModel(context.bakeLayer(PowercellModel.LAYER));
        dish = new SatelliteDishModel(context.bakeLayer(SatelliteDishModel.LAYER));
        tesla = new TeslaCoilModel(context.bakeLayer(TeslaCoilModel.LAYER));
        workstation = new org.avp.client.model.geometry.WorkstationModel(context.bakeLayer(org.avp.client.model.geometry.WorkstationModel.LAYER));
        font = context.getFont();
    }

    private static ResourceLocation tex(String name)
    {
        return ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/tile/" + name);
    }

    @Override
    public void render(PowerMachineBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay)
    {
        BlockState state = be.getBlockState();
        if (!(state.getBlock() instanceof PowerMachineBlock block))
        {
            return;
        }
        boolean lit = state.getValue(PowerMachineBlock.LIT);
        float yRot = state.getValue(PowerMachineBlock.FACING).toYRot();
        float faceTurn = 180F - yRot; // front of the model towards the player who placed it
        poseStack.pushPose();
        switch (block.kind())
        {
            case SOLAR_PANEL ->
            {
                // 1.12.2 RenderSolarPanel: the panel tilts to follow the sun
                poseStack.translate(0.5F, 0.5F, 0.5F);
                if (be.getLevel() != null)
                {
                    float angle = be.getLevel().getTimeOfDay(partialTick) * 360F;
                    poseStack.mulPose(Axis.ZP.rotationDegrees(angle > 90 && angle < 270 ? 90 : angle));
                }
                poseStack.translate(0F, -1.4F, 0F);
                solar.root().render(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(SOLAR)), light, overlay);
            }
            case GENERATOR ->
            {
                onFloor(generator.root(), faceTurn, poseStack);
                generator.root().render(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(GENERATOR)), light, overlay);
                if (lit)
                {
                    // the indicator lights glow while it burns fuel
                    VertexConsumer glow = buffers.getBuffer(RenderType.entityTranslucentEmissive(GENERATOR));
                    renderInPlace(generator.root(), generator.fluxLightOutput, poseStack, glow, overlay);
                    renderInPlace(generator.root(), generator.fluxLightInput, poseStack, glow, overlay);
                }
            }
            case REPULSION_GENERATOR ->
            {
                List<ModelPart> magnets = List.of(repulsion.magnet1, repulsion.magnet2, repulsion.magnet3, repulsion.magnet4,
                        repulsion.magnet5, repulsion.magnet6, repulsion.magnet7, repulsion.magnet8);
                onFloor(repulsion.root(), faceTurn, poseStack);
                for (int i = 0; i < magnets.size(); i++)
                {
                    magnets.get(i).visible = i < be.magnets();
                }
                repulsion.rotor.yRot = be.rotor(partialTick);
                repulsion.root().render(poseStack, buffers.getBuffer(RenderType.entityTranslucent(REPULSION)), light, overlay);
                repulsion.rotor.yRot = 0;
                magnets.forEach(m -> m.visible = true);
            }
            case TRANSFORMER, STEPDOWN_TRANSFORMER ->
            {
                // the model's output side is +z; turn it to face where the power goes
                onFloor(transformer.root(), -yRot, poseStack);
                transformer.root().render(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(TRANSFORMER)), light, overlay);
            }
            case POWERCELL ->
            {
                onFloor(powercell.root(), faceTurn, poseStack);
                powercell.root().render(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(POWERCELL)), light, overlay);
                if (be.fill() > 0)
                {
                    // the charge liquid glows brighter the fuller the cell is
                    int alpha = (int) (80 + 175 * be.fill());
                    powercell.root().render(poseStack, buffers.getBuffer(RenderType.entityTranslucentEmissive(POWERCELL_LIQUID)),
                            LightTexture.FULL_BRIGHT, overlay, (alpha << 24) | 0xFFFFFF);
                }
            }
            case SATELLITE_DISH ->
            {
                onFloor(dish.root(), faceTurn, poseStack);
                dish.root().render(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(DISH)), light, overlay);
            }
            case TESLA_COIL ->
            {
                onFloor(tesla.root(), faceTurn, poseStack);
                tesla.root().render(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(TESLA)), light, overlay);
            }
            case TERMINAL ->
            {
                // 1.12.2 RenderWorkstation, step for step (the text sits on the monitor)
                poseStack.translate(0.5F, 1.5F, 0.5F);
                poseStack.mulPose(Axis.YP.rotationDegrees(faceTurn));
                poseStack.scale(1F, -1F, 1F);
                workstation.root().render(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(WORKSTATION)), light, overlay);
                if (lit)
                {
                    workstation.root().render(poseStack, buffers.getBuffer(RenderType.entityTranslucentEmissive(WORKSTATION_ON)), LightTexture.FULL_BRIGHT, overlay);
                    poseStack.translate(0.45F, -0.58F, 0.254F);
                    poseStack.mulPose(Axis.XP.rotationDegrees(6.5F));
                    float textScale = 0.004F;
                    poseStack.scale(-textScale, textScale, textScale);
                    int line = 0;
                    for (net.minecraft.network.chat.Component text : be.terminalLines())
                    {
                        font.drawInBatch(text, 0, line++ * 10, 0xFF33FF66, false, poseStack.last().pose(), buffers,
                                net.minecraft.client.gui.Font.DisplayMode.POLYGON_OFFSET, 0, LightTexture.FULL_BRIGHT);
                    }
                }
            }
            default ->
            {
            }
        }
        poseStack.popPose();

        if (block.kind() == Kind.TESLA_COIL && be.getLevel() != null && !be.arcs().isEmpty())
        {
            drawArcs(be, partialTick, poseStack, buffers);
        }
    }

    /**
     * Stands a model on the block floor, centred, turned by the given angle.
     * Model space has y pointing down, so it is flipped; its lowest point goes to y = 0.
     */
    private void onFloor(ModelPart root, float turnDegrees, PoseStack poseStack)
    {
        float[] b = bounds.computeIfAbsent(root, PowerMachineRenderer::measure);
        poseStack.translate(0.5F, b[4], 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(turnDegrees));
        poseStack.scale(1F, -1F, 1F);
        poseStack.translate(-(b[0] + b[3]) / 2F, 0F, -(b[2] + b[5]) / 2F);
    }

    /** min x,y,z then max x,y,z, in blocks. */
    private static float[] measure(ModelPart root)
    {
        float[] b = { Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE };
        Vector3f corner = new Vector3f();
        root.visit(new PoseStack(), (pose, path, index, cube) ->
        {
            for (int i = 0; i < 8; i++)
            {
                corner.set((i & 1) == 0 ? cube.minX : cube.maxX, (i & 2) == 0 ? cube.minY : cube.maxY, (i & 4) == 0 ? cube.minZ : cube.maxZ).div(16F);
                pose.pose().transformPosition(corner);
                b[0] = Math.min(b[0], corner.x);
                b[1] = Math.min(b[1], corner.y);
                b[2] = Math.min(b[2], corner.z);
                b[3] = Math.max(b[3], corner.x);
                b[4] = Math.max(b[4], corner.y);
                b[5] = Math.max(b[5], corner.z);
            }
        });
        return b;
    }

    /** Draws one top-level part (with the root's own pose) full bright. */
    private static void renderInPlace(ModelPart root, ModelPart part, PoseStack poseStack, VertexConsumer vc, int overlay)
    {
        poseStack.pushPose();
        root.translateAndRotate(poseStack);
        part.render(poseStack, vc, LightTexture.FULL_BRIGHT, overlay);
        poseStack.popPose();
    }

    // ------------------------------------------------------------------ lightning arcs

    private static void drawArcs(PowerMachineBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffers)
    {
        VertexConsumer vc = buffers.getBuffer(RenderType.lightning());
        Vec3 base = Vec3.atLowerCornerOf(be.getBlockPos());
        Vec3 from = PowerMachineBlockEntity.coilTop(be.getBlockPos()).subtract(base);
        Matrix4f matrix = poseStack.last().pose();
        for (PowerMachineBlockEntity.Arc arc : be.arcs())
        {
            Vec3 to = arc.to();
            if (arc.entityId() >= 0)
            {
                Entity target = be.getLevel().getEntity(arc.entityId());
                if (target != null)
                {
                    to = target.getPosition(partialTick).add(0, target.getBbHeight() / 2, 0);
                }
            }
            boolean strike = arc.entityId() >= 0;
            drawArc(matrix, vc, from, to.subtract(base), RandomSource.create(arc.seed() + be.getLevel().getGameTime() / 2),
                    strike ? 0.035F : 0.015F, strike ? 10 : 4);
        }
    }

    /** A jagged line of thin glowing quads (two crossed quads per segment so it shows from any side). */
    private static void drawArc(Matrix4f matrix, VertexConsumer vc, Vec3 from, Vec3 to, RandomSource random, float width, int segments)
    {
        Vec3 prev = from;
        double jitter = from.distanceTo(to) * 0.12;
        for (int i = 1; i <= segments; i++)
        {
            Vec3 point = from.lerp(to, (double) i / segments);
            if (i < segments)
            {
                point = point.add((random.nextDouble() - 0.5) * jitter, (random.nextDouble() - 0.5) * jitter, (random.nextDouble() - 0.5) * jitter);
            }
            quad(matrix, vc, prev, point, width, 0, width);
            quad(matrix, vc, prev, point, -width, 0, width);
            quad(matrix, vc, prev, point, 0, width, 0);
            prev = point;
        }
    }

    private static void quad(Matrix4f matrix, VertexConsumer vc, Vec3 a, Vec3 b, float ox, float oy, float oz)
    {
        float r = 0.75F, g = 0.65F, bl = 1.0F, alpha = 0.8F;
        vc.addVertex(matrix, (float) a.x - ox, (float) a.y - oy, (float) a.z - oz).setColor(r, g, bl, alpha);
        vc.addVertex(matrix, (float) a.x + ox, (float) a.y + oy, (float) a.z + oz).setColor(r, g, bl, alpha);
        vc.addVertex(matrix, (float) b.x + ox, (float) b.y + oy, (float) b.z + oz).setColor(r, g, bl, alpha);
        vc.addVertex(matrix, (float) b.x - ox, (float) b.y - oy, (float) b.z - oz).setColor(r, g, bl, alpha);
    }

    /** The satellite dish and tesla coil are bigger than one block; arcs reach out further. */
    @Override
    public AABB getRenderBoundingBox(PowerMachineBlockEntity be)
    {
        return new AABB(be.getBlockPos()).inflate(be.kind() == Kind.TESLA_COIL ? 12 : 2);
    }
}
