package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.client.model.FacehuggerEntityModel;
import org.avp.client.model.geometry.FacehuggerModel;
import org.avp.entity.parasite.FacehuggerEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Draws the Facehugger (1.12.2 RenderFacehuggers): 0.9 scale; tilted back mid-leap;
 * flipped on its back once spent; and standing up flat against the host's face while
 * attached (hidden from the host's own first-person view - they get the overlay instead).
 */
public class FacehuggerRenderer extends MobRenderer<FacehuggerEntity, FacehuggerEntityModel>
{
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/facehugger.png");

    public FacehuggerRenderer(EntityRendererProvider.Context context)
    {
        super(context, new FacehuggerEntityModel(context.bakeLayer(FacehuggerModel.LAYER)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(FacehuggerEntity hugger)
    {
        return TEXTURE;
    }

    @Override
    public boolean shouldRender(FacehuggerEntity hugger, Frustum frustum, double x, double y, double z)
    {
        Minecraft mc = Minecraft.getInstance();
        if (hugger.getVehicle() == mc.getCameraEntity() && mc.options.getCameraType().isFirstPerson())
        {
            return false;
        }
        return super.shouldRender(hugger, frustum, x, y, z);
    }

    @Override
    protected void setupRotations(FacehuggerEntity hugger, PoseStack poseStack, float bob, float yaw, float partialTick, float scale)
    {
        super.setupRotations(hugger, poseStack, bob, yaw, partialTick, scale);
        if (hugger.isAttached())
        {
            // stand up against the face: belly to the host, legs round the head, tail down
            poseStack.mulPose(Axis.XP.rotationDegrees(-90F));
        }
        else if (!hugger.isFertile())
        {
            // spent: on its back
            poseStack.translate(0F, 0.3F, 0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180F));
        }
        else if (!hugger.onGround() && !hugger.onClimbable())
        {
            poseStack.mulPose(Axis.XP.rotationDegrees(-30F)); // mid-leap
        }
    }

    @Override
    protected void scale(FacehuggerEntity hugger, PoseStack poseStack, float partialTick)
    {
        poseStack.scale(0.9F, 0.9F, 0.9F);
    }
}
