package org.avp.client.render;

import java.util.HashMap;
import java.util.Map;

import org.avp.AliensVsPredator;
import org.avp.client.model.GeneratedModel;
import org.avp.item.ModelledItem;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * Draws a gun (or other modelled item: flamethrower, motion tracker...) with its 1.12.2 model. The models have their muzzle towards +Z and "up"
 * towards -Y; each view turns that the right way: side-on with the muzzle to the right in
 * inventories and item frames, pointing forward out of the hand when held. The model is measured
 * once so it's centred and (in inventories) sized to fit the slot.
 */
public class FirearmRenderer extends BlockEntityWithoutLevelRenderer
{
    private record Baked(ModelPart root, ResourceLocation texture, float cx, float cy, float cz, float length)
    {
    }

    private final Map<String, Baked> cache = new HashMap<>();

    public FirearmRenderer()
    {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void onResourceManagerReload(ResourceManager manager)
    {
        cache.clear();
    }

    private Baked bake(ModelledItem f)
    {
        return cache.computeIfAbsent(f.model() + "|" + f.texture(), key ->
        {
            try
            {
                Class<?> type = Class.forName("org.avp.client.model.geometry." + f.model());
                ModelLayerLocation layer = (ModelLayerLocation) type.getField("LAYER").get(null);
                GeneratedModel model = (GeneratedModel) type.getConstructor(ModelPart.class)
                        .newInstance(Minecraft.getInstance().getEntityModels().bakeLayer(layer));
                float[] min = { Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE }, max = { -Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE };
                model.root().visit(new PoseStack(), (pose, path, index, cube) ->
                {
                    for (float x : new float[] { cube.minX, cube.maxX })
                    {
                        for (float y : new float[] { cube.minY, cube.maxY })
                        {
                            for (float z : new float[] { cube.minZ, cube.maxZ })
                            {
                                Vector3f v = pose.pose().transformPosition(new Vector3f(x / 16F, y / 16F, z / 16F));
                                min[0] = Math.min(min[0], v.x);
                                min[1] = Math.min(min[1], v.y);
                                min[2] = Math.min(min[2], v.z);
                                max[0] = Math.max(max[0], v.x);
                                max[1] = Math.max(max[1], v.y);
                                max[2] = Math.max(max[2], v.z);
                            }
                        }
                    }
                });
                float length = Math.max(max[2] - min[2], Math.max(max[0] - min[0], max[1] - min[1]));
                return new Baked(model.root(), ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/item/models/" + f.texture()),
                        (min[0] + max[0]) / 2, (min[1] + max[1]) / 2, (min[2] + max[2]) / 2, length);
            }
            catch (ReflectiveOperationException e)
            {
                throw new IllegalStateException("No gun model " + f.model(), e);
            }
        });
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack pose, MultiBufferSource buffers, int light, int overlay)
    {
        if (!(stack.getItem() instanceof ModelledItem item))
        {
            return;
        }
        Baked gun = bake(item);
        pose.pushPose();
        pose.translate(0.5F, 0.5F, 0.5F);
        switch (context)
        {
            case GUI, GROUND, FIXED ->
            {
                // side-on, muzzle to the right, fitted to the slot
                float fit = (context == ItemDisplayContext.GROUND ? 0.6F : 0.95F) / gun.length();
                if (context == ItemDisplayContext.GUI)
                {
                    pose.mulPose(Axis.ZP.rotationDegrees(25F)); // a jaunty angle, as in 1.12.2
                }
                pose.mulPose(Axis.YP.rotationDegrees(90F));
                pose.scale(fit, fit, fit);
            }
            case FIRST_PERSON_RIGHT_HAND, FIRST_PERSON_LEFT_HAND ->
            {
                // pointing forward, held low at the side
                boolean left = context == ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
                pose.translate(left ? 0.1F : -0.1F, 0.1F, -0.35F);
                pose.mulPose(Axis.YP.rotationDegrees(180F));
                pose.scale(0.85F, 0.85F, 0.85F);
            }
            default ->
            {
                // in someone's hands (third person): forward along the raised arm
                pose.translate(0F, 0.05F, -0.2F);
                pose.mulPose(Axis.YP.rotationDegrees(180F));
                pose.scale(0.8F, 0.8F, 0.8F);
            }
        }
        // model space: y down, muzzle +Z (as the entity renderers do: flip x and y)
        pose.scale(-1F, -1F, 1F);
        pose.translate(-gun.cx(), -gun.cy(), -gun.cz());
        gun.root().render(pose, buffers.getBuffer(RenderType.entityCutoutNoCull(gun.texture())), light,
                overlay == 0 ? OverlayTexture.NO_OVERLAY : overlay);
        pose.popPose();
    }
}
