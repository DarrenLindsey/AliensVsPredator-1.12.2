package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.block.PowerlineBlock;
import org.avp.block.entity.PowerlineBlockEntity;
import org.avp.client.model.geometry.CableModel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

/** Draws a powerline with the 1.12.2 cable model: an arm towards each connected side. */
public class PowerlineRenderer implements BlockEntityRenderer<PowerlineBlockEntity>
{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/tile/cable.png");

    private final CableModel cable;

    public PowerlineRenderer(BlockEntityRendererProvider.Context context)
    {
        cable = new CableModel(context.bakeLayer(CableModel.LAYER));
    }

    @Override
    public void render(PowerlineBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light, int overlay)
    {
        BlockState state = be.getBlockState();
        boolean up = on(state, Direction.UP), down = on(state, Direction.DOWN);
        boolean east = on(state, Direction.EAST), west = on(state, Direction.WEST);
        boolean south = on(state, Direction.SOUTH), north = on(state, Direction.NORTH);
        if (!(up || down || east || west || south || north))
        {
            // on its own (or as an item): a plain cross, like 1.12.2
            east = west = south = north = true;
        }
        // same part names as 1.12.2 ModelCable (its "west" arm points east, and so on)
        cable.cableTop.visible = up;
        cable.cableBottom.visible = down;
        cable.cableWest.visible = east;
        cable.cableEast.visible = west;
        cable.cableSouth.visible = south;
        cable.cableNorth.visible = north;
        cable.nodeX.visible = east || west;
        cable.nodeY.visible = up || down;
        cable.nodeZ.visible = north || south;

        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        poseStack.scale(1F, -1F, 1F);
        VertexConsumer vc = buffers.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        cable.root().render(poseStack, vc, light, overlay);
        poseStack.popPose();
    }

    private static boolean on(BlockState state, Direction dir)
    {
        return state.hasProperty(PowerlineBlock.CONNECTED.get(dir)) && state.getValue(PowerlineBlock.CONNECTED.get(dir));
    }
}
