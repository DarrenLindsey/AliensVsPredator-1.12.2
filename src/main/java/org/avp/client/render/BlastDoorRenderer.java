package org.avp.client.render;

import java.util.List;

import org.avp.AliensVsPredator;
import org.avp.block.BlastDoorBlock;
import org.avp.block.entity.BlastDoorBlockEntity;
import org.avp.client.model.geometry.BlastdoorModel;
import org.avp.client.model.geometry.SevastopolBlastdoorModel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import org.joml.Vector3f;

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

    private static final ResourceLocation ALARM_TEXTURE = tex("alarm_light.png");
    /** A one-pixel box, scaled into the warning light that flashes during lockdown. */
    public static final ModelLayerLocation ALARM_LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "blast_door_alarm"), "main");
    private static final int ALARM_RED = 0xFFFF2020;

    private final BlastdoorModel big;
    private final SevastopolBlastdoorModel sevastopol;
    private final ModelPart alarmLight;
    /** Where the light goes on each door (model space, in blocks): {centreX, top, centreZ, depth}. */
    private final float[] bigLight;
    private final float[] sevastopolLight;

    public BlastDoorRenderer(BlockEntityRendererProvider.Context context)
    {
        big = new BlastdoorModel(context.bakeLayer(BlastdoorModel.LAYER));
        sevastopol = new SevastopolBlastdoorModel(context.bakeLayer(SevastopolBlastdoorModel.LAYER));
        alarmLight = context.bakeLayer(ALARM_LAYER);
        bigLight = lightSpot(List.of(big.root()));
        sevastopolLight = lightSpot(List.of(sevastopol.frameL, sevastopol.doorMain));
    }

    public static LayerDefinition createAlarmLight()
    {
        MeshDefinition mesh = new MeshDefinition();
        mesh.getRoot().addOrReplaceChild("light", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -0.5F, 1, 1, 1), PartPose.ZERO);
        return LayerDefinition.create(mesh, 16, 16);
    }

    /** Measures a door model and returns the top-centre spot for the warning light. */
    private static float[] lightSpot(List<ModelPart> parts)
    {
        float[] b = { Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE };
        Vector3f corner = new Vector3f();
        PoseStack pose = new PoseStack();
        for (ModelPart part : parts)
        {
            part.visit(pose, (p, path, index, cube) ->
            {
                for (int i = 0; i < 8; i++)
                {
                    corner.set((i & 1) == 0 ? cube.minX : cube.maxX, (i & 2) == 0 ? cube.minY : cube.maxY, (i & 4) == 0 ? cube.minZ : cube.maxZ).div(16F);
                    p.pose().transformPosition(corner);
                    b[0] = Math.min(b[0], corner.x); b[1] = Math.min(b[1], corner.y); b[2] = Math.min(b[2], corner.z);
                    b[3] = Math.max(b[3], corner.x); b[4] = Math.max(b[4], corner.y); b[5] = Math.max(b[5], corner.z);
                }
            });
        }
        // model space has y pointing down, so the top of the door is the smallest y
        return new float[] { (b[0] + b[3]) / 2F, b[1], (b[2] + b[5]) / 2F, (b[5] - b[2]) + 0.1F };
    }

    /** Draws the flashing red warning light on top of the door (glows in the dark). */
    private void drawAlarm(float[] spot, PoseStack poseStack, MultiBufferSource buffers, int overlay)
    {
        poseStack.pushPose();
        poseStack.translate(spot[0], spot[1] - 0.08F, spot[2]);
        poseStack.scale(0.4F * 16F, 0.16F * 16F, spot[3] * 16F); // the box is 1 pixel; size it in pixels
        alarmLight.render(poseStack, buffers.getBuffer(RenderType.entityTranslucentEmissive(ALARM_TEXTURE)), LightTexture.FULL_BRIGHT, overlay, ALARM_RED);
        poseStack.popPose();
    }

    /** On for half a second, off for half a second. */
    private static boolean flashOn(BlastDoorBlockEntity door, float partialTick)
    {
        return door.getLevel() != null && ((door.getLevel().getGameTime() + partialTick) / 10F) % 2F < 1F;
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
            if (door.isLockdown() && flashOn(door, partialTick))
            {
                drawAlarm(bigLight, poseStack, buffers, overlay);
            }
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
                if (door.isLockdown())
                {
                    // lockdown: the red lock light flashes, plus the warning light on top
                    if (flashOn(door, partialTick))
                    {
                        drawSevastopol(poseStack, buffers.getBuffer(RenderType.entityTranslucent(SEVASTOPOL_LOCKED)), LightTexture.FULL_BRIGHT, overlay);
                        drawAlarm(sevastopolLight, poseStack, buffers, overlay);
                    }
                }
                else
                {
                    ResourceLocation lock = door.isLocked() ? SEVASTOPOL_LOCKED : SEVASTOPOL_UNLOCKED;
                    drawSevastopol(poseStack, buffers.getBuffer(RenderType.entityTranslucent(lock)), LightTexture.FULL_BRIGHT, overlay);
                }
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
