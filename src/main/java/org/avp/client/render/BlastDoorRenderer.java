package org.avp.client.render;

import java.util.List;

import org.avp.AliensVsPredator;
import org.avp.block.BlastDoorBlock;
import org.avp.block.entity.BlastDoorBlockEntity;
import org.avp.client.model.geometry.BlastdoorModel;
import org.avp.client.model.geometry.SevastopolBlastdoorModel;

import com.mojang.blaze3d.vertex.PoseStack;
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
 * Draws blast doors from their controller piece, with the doors sliding open.
 * 1.12.2: RenderBlastdoor / RenderSevastopolBlastdoor and the models' render() methods,
 * which moved door parts with offsetX/offsetY (in blocks). In 1.21 a part's position is
 * its pivot in pixels, so the offset is added to the pivot (x 16).
 */
public class BlastDoorRenderer implements BlockEntityRenderer<BlastDoorBlockEntity>
{
    private static final ResourceLocation BIG = tex("blastdoor.png");
    private static final ResourceLocation SEVASTOPOL = tex("sevastopolblastdoor.png");
    private static final ResourceLocation SEVASTOPOL_ON = tex("sevastopolblastdoor_power_on.png");
    private static final ResourceLocation SEVASTOPOL_OFF = tex("sevastopolblastdoor_power_off.png");
    private static final ResourceLocation SEVASTOPOL_LOCKED = tex("sevastopolblastdoor_locked.png");
    private static final ResourceLocation SEVASTOPOL_UNLOCKED = tex("sevastopolblastdoor_unlocked.png");

    private final BlastdoorModel big;
    private final SevastopolBlastdoorModel sevastopol;

    public BlastDoorRenderer(BlockEntityRendererProvider.Context context)
    {
        big = new BlastdoorModel(context.bakeLayer(BlastdoorModel.LAYER));
        sevastopol = new SevastopolBlastdoorModel(context.bakeLayer(SevastopolBlastdoorModel.LAYER));
    }

    private static ResourceLocation tex(String name)
    {
        return ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/tile/" + name);
    }

    @Override
    public void render(BlastDoorBlockEntity door, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay)
    {
        BlockState state = door.getBlockState();
        if (!(state.getBlock() instanceof BlastDoorBlock block))
        {
            return;
        }
        float slide = door.progress(partialTick) * 2F * 16F; // 1.12.2: progress x 2 blocks, in pixels

        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.scale(1F, -1F, 1F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180F - state.getValue(BlastDoorBlock.FACING).toYRot()));

        if (!block.isSevastopol())
        {
            // the two halves slide apart sideways
            big.root().getAllParts().forEach(ModelPart::resetPose);
            for (ModelPart part : List.of(big.doorRightMain, big.doorRightArrow))
            {
                part.x += slide;
            }
            for (ModelPart part : List.of(big.doorLeftMain, big.doorLeftCorner1, big.doorLeftCorner2, big.doorLeftDiagonal1, big.doorLeftDiagonal2))
            {
                part.x -= slide;
            }
            big.root().render(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(BIG)), light, overlay);
        }
        else
        {
            // the door slides up into the frame
            sevastopol.root().getAllParts().forEach(ModelPart::resetPose);
            sevastopol.doorMain.y -= slide;
            drawSevastopol(poseStack, buffers.getBuffer(RenderType.entityCutoutNoCull(SEVASTOPOL)), light, overlay);
            if (door.isPowered())
            {
                // the glowing panels: power on, plus a locked (red) or unlocked (green) light
                drawSevastopol(poseStack, buffers.getBuffer(RenderType.entityTranslucent(SEVASTOPOL_ON)), LightTexture.FULL_BRIGHT, overlay);
                ResourceLocation lock = door.isLocked() || door.isLockdown() ? SEVASTOPOL_LOCKED : SEVASTOPOL_UNLOCKED;
                drawSevastopol(poseStack, buffers.getBuffer(RenderType.entityTranslucent(lock)), LightTexture.FULL_BRIGHT, overlay);
            }
            else
            {
                drawSevastopol(poseStack, buffers.getBuffer(RenderType.entityTranslucent(SEVASTOPOL_OFF)), light, overlay);
            }
        }
        poseStack.popPose();
    }

    /** ModelSevastopolBlastdoor.render draws only the frame and the door (with their children). */
    private void drawSevastopol(PoseStack poseStack, com.mojang.blaze3d.vertex.VertexConsumer vc, int light, int overlay)
    {
        sevastopol.frameL.render(poseStack, vc, light, overlay);
        sevastopol.doorMain.render(poseStack, vc, light, overlay);
    }

    /** The door is several blocks wide and tall. */
    @Override
    public AABB getRenderBoundingBox(BlastDoorBlockEntity door)
    {
        return new AABB(door.getBlockPos()).inflate(4, 0, 4).expandTowards(0, 3, 0);
    }
}
