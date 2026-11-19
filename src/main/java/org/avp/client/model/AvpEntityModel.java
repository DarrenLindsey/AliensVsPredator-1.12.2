package org.avp.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

/**
 * Turns a model converted from 1.12.2 (a {@link GeneratedModel}) into an entity model.
 * Each creature supplies its own animation in {@link #animate}; the parts start from the
 * pose they were modelled in every frame, so animations just add movement on top.
 */
public abstract class AvpEntityModel<T extends Entity, M extends GeneratedModel> extends EntityModel<T>
{
    protected final M model;

    protected AvpEntityModel(M model)
    {
        this.model = model;
    }

    @Override
    public final void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        model.root().getAllParts().forEach(ModelPart::resetPose);
        animate(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }

    protected abstract void animate(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch);

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int light, int overlay, int color)
    {
        model.root().render(poseStack, buffer, light, overlay, color);
    }
}
