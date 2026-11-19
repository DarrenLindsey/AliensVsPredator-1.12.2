package org.avp.client.render;

import org.avp.AliensVsPredator;
import org.avp.client.model.CreatureEntityModel;
import org.avp.client.model.GeneratedModel;
import org.avp.entity.creature.CreatureSpec;
import org.avp.entity.creature.Creatures;
import org.avp.entity.xenomorph.AdultXenomorphEntity;
import org.avp.entity.yautja.YautjaEntity;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

/**
 * Draws any catalog creature: its converted 1.12.2 model (found by name), its texture and its
 * 1.12.2 render scale. The model is centred on the hitbox (ignoring the tail) so heads and
 * bodies don't poke through walls. Cloaked Yautja are drawn as a faint shimmer; a Boiler about
 * to burst swells up.
 */
public class CreatureRenderer<T extends Mob> extends MobRenderer<T, CreatureEntityModel<T>>
{
    private final CreatureSpec spec;
    private final ResourceLocation texture;
    private final float centreX, centreZ;

    public CreatureRenderer(EntityRendererProvider.Context context, String id)
    {
        this(context, Creatures.ALL.get(id), bake(context, Creatures.ALL.get(id)));
    }

    private CreatureRenderer(EntityRendererProvider.Context context, CreatureSpec spec, GeneratedModel model)
    {
        super(context, new CreatureEntityModel<>(model), Math.min(2.0F, spec.width() * 0.6F));
        this.spec = spec;
        this.texture = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/entity/" + spec.texture());
        // centre of the model (without the tail), in blocks
        float[] min = { Float.MAX_VALUE, Float.MAX_VALUE }, max = { -Float.MAX_VALUE, -Float.MAX_VALUE };
        model.root().visit(new PoseStack(), (pose, path, index, cube) ->
        {
            if (CreatureEntityModel.isTail(path))
            {
                return;
            }
            for (float x : new float[] { cube.minX, cube.maxX })
            {
                for (float z : new float[] { cube.minZ, cube.maxZ })
                {
                    Vector3f v = pose.pose().transformPosition(new Vector3f(x / 16F, cube.minY / 16F, z / 16F));
                    min[0] = Math.min(min[0], v.x);
                    max[0] = Math.max(max[0], v.x);
                    min[1] = Math.min(min[1], v.z);
                    max[1] = Math.max(max[1], v.z);
                }
            }
        });
        boolean found = min[0] <= max[0];
        this.centreX = found ? (min[0] + max[0]) / 2F : 0;
        this.centreZ = found ? (min[1] + max[1]) / 2F : 0;
    }

    /** Finds the converted model class (org.avp.client.model.geometry.<name>) and bakes it. */
    private static GeneratedModel bake(EntityRendererProvider.Context context, CreatureSpec spec)
    {
        try
        {
            Class<?> type = Class.forName("org.avp.client.model.geometry." + spec.model());
            ModelLayerLocation layer = (ModelLayerLocation) type.getField("LAYER").get(null);
            return (GeneratedModel) type.getConstructor(ModelPart.class).newInstance(context.bakeLayer(layer));
        }
        catch (ReflectiveOperationException e)
        {
            throw new IllegalStateException("No converted model " + spec.model() + " for " + spec.id(), e);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(T entity)
    {
        return texture;
    }

    @Override
    protected void scale(T entity, PoseStack poseStack, float partialTick)
    {
        float s = spec.scale();
        poseStack.scale(s, s, s);
        poseStack.translate(-centreX, 0F, -centreZ);
        if (entity instanceof AdultXenomorphEntity xeno)
        {
            if (xeno.isCrawling())
            {
                poseStack.scale(1.0F, 0.42F, 1.15F);
            }
            float swell = xeno.burstProgress(partialTick);
            if (swell > 0)
            {
                float wobble = 1F + swell * 0.25F + net.minecraft.util.Mth.sin(swell * 60F) * 0.03F;
                poseStack.scale(wobble, 1F + swell * 0.1F, wobble);
            }
        }
    }

    @Override
    public void render(T entity, float yaw, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light)
    {
        model.alpha = entity instanceof YautjaEntity yautja && yautja.isCloaked() ? 40 : 255;
        super.render(entity, yaw, partialTick, poseStack, buffers, light);
    }

    @Override
    protected RenderType getRenderType(T entity, boolean bodyVisible, boolean translucent, boolean glowing)
    {
        if (entity instanceof YautjaEntity yautja && yautja.isCloaked())
        {
            return RenderType.entityTranslucent(texture);
        }
        return super.getRenderType(entity, bodyVisible, translucent, glowing);
    }
}
