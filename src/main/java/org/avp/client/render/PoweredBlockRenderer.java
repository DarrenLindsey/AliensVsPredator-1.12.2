package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.block.PoweredBlock;
import org.avp.block.entity.PoweredBlockEntity;
import org.avp.client.model.geometry.CCFLModel;
import org.avp.client.model.geometry.CryostasisTubeModel;
import org.avp.client.model.geometry.LightPanelModel;
import org.avp.registry.ModBlocks;

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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * Draws the light panel, CCFL tube and cryostasis tube with their converted 1.12.2
 * models. Transforms are copied from RenderLightPanel / RenderCCFLTube /
 * RenderCryostasisTube. Lit parts are drawn "full bright" so they glow in the dark.
 */
public class PoweredBlockRenderer implements BlockEntityRenderer<PoweredBlockEntity>
{
    private static final ResourceLocation LIGHT_PANEL = tex("lightpanel.png");
    private static final ResourceLocation CCFL_BALLAST = tex("ccfl_ballast.png");
    private static final ResourceLocation CCFL_ON = tex("ccfl_on.png");
    private static final ResourceLocation CCFL_OFF = tex("ccfl_off.png");
    private static final ResourceLocation CRYO = tex("cryostasistube.png");
    private static final ResourceLocation[] CRYO_GLASS = { tex("cryostasistube-mask.png"), tex("cryostasistube-cracked-mask.png"),
            tex("cryostasistube-shattered-mask.png") };

    private final LightPanelModel lightPanel;
    private final CCFLModel ccfl;
    private final CryostasisTubeModel cryo;

    public PoweredBlockRenderer(BlockEntityRendererProvider.Context context)
    {
        lightPanel = new LightPanelModel(context.bakeLayer(LightPanelModel.LAYER));
        ccfl = new CCFLModel(context.bakeLayer(CCFLModel.LAYER));
        cryo = new CryostasisTubeModel(context.bakeLayer(CryostasisTubeModel.LAYER));
    }

    private static ResourceLocation tex(String name)
    {
        return ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/tile/" + name);
    }

    @Override
    public void render(PoweredBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay)
    {
        BlockState state = be.getBlockState();
        boolean lit = state.getValue(PoweredBlock.LIT);
        int glow = lit ? LightTexture.FULL_BRIGHT : light;
        float turn = 180F - state.getValue(PoweredBlock.FACING).toYRot(); // same facing rule as the skulls

        poseStack.pushPose();
        if (state.is(ModBlocks.LIGHTPANEL.get()))
        {
            poseStack.translate(0.5F, -0.5F, 0.5F);
            lightPanel.root().render(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(LIGHT_PANEL)), glow, overlay);
        }
        else if (state.is(ModBlocks.CCFLTUBE.get()))
        {
            poseStack.translate(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(90F));
            poseStack.mulPose(Axis.YP.rotationDegrees(turn));
            PoweredBlock.Tilt tilt = state.getValue(PoweredBlock.TILT);
            if (tilt == PoweredBlock.Tilt.DOWN)
            {
                poseStack.mulPose(Axis.XP.rotationDegrees(-180F));
            }
            if (tilt == PoweredBlock.Tilt.LEVEL)
            {
                poseStack.translate(0F, -0.5F, 0F);
            }
            poseStack.translate(0F, -0.1F, 0F);
            // two passes like 1.12.2: the ballast texture, then the tube texture (on = glowing)
            drawCcfl(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(CCFL_BALLAST)), light, overlay);
            drawCcfl(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(lit ? CCFL_ON : CCFL_OFF)), glow, overlay);
        }
        else if (state.is(ModBlocks.CRYOSTASISTUBE.get()))
        {
            poseStack.translate(0.5F, 1.125F, 0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(turn));
            poseStack.scale(0.75F, -0.75F, 0.75F);
            cryo.root().render(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(CRYO)), light, overlay);
            // the glass, drawn see-through on top; cracked/shattered versions come with the stasis creatures
            ResourceLocation glass = CRYO_GLASS[state.getValue(PoweredBlock.DAMAGE)];
            cryo.root().render(poseStack, buffers.getBuffer(RenderType.entityTranslucent(glass)), glow, overlay);
        }
        poseStack.popPose();
    }

    /** ModelCCFL.render: some parts are drawn slightly scaled around their pivot. */
    private void drawCcfl(PoseStack poseStack, VertexConsumer vc, int light, int overlay)
    {
        ccfl.middleRod.render(poseStack, vc, light, overlay);
        scaled(ccfl.lightTube, 1.15F, 1.2F, 1.2F, poseStack, vc, light, overlay);
        scaled(ccfl.lCap, 0.7F, 0.6F, 0.6F, poseStack, vc, light, overlay);
        scaled(ccfl.lightAura, 1.3F, 1.5F, 1.5F, poseStack, vc, light, overlay);
        scaled(ccfl.rCap, 0.7F, 0.6F, 0.6F, poseStack, vc, light, overlay);
    }

    private static void scaled(ModelPart part, float sx, float sy, float sz, PoseStack poseStack, VertexConsumer vc, int light, int overlay)
    {
        poseStack.pushPose();
        poseStack.translate(part.x / 16F, part.y / 16F, part.z / 16F);
        poseStack.scale(sx, sy, sz);
        poseStack.translate(-part.x / 16F, -part.y / 16F, -part.z / 16F);
        part.render(poseStack, vc, light, overlay);
        poseStack.popPose();
    }

    /** The cryostasis tube is taller than one block. */
    @Override
    public AABB getRenderBoundingBox(PoweredBlockEntity be)
    {
        return new AABB(be.getBlockPos()).expandTowards(0, 1, 0);
    }
}
