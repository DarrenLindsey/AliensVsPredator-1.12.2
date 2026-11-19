package org.avp.client.model;

import org.avp.client.model.geometry.OvamorphModel;
import org.avp.entity.xenomorph.OvamorphEntity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

/** Ovamorph (1.12.2 ModelOvamorph): the four lobes peel open as it hatches; a slow pulse while it waits. */
public class OvamorphEntityModel extends AvpEntityModel<OvamorphEntity, OvamorphModel>
{
    public OvamorphEntityModel(ModelPart root)
    {
        super(new OvamorphModel(root));
    }

    @Override
    protected void animate(OvamorphEntity egg, float limbSwing, float limbSwingAmount, float age, float netHeadYaw, float headPitch)
    {
        float openAngle = 25F;
        float r = (float) Math.toRadians(openAngle + Mth.sin(egg.openProgress() * 0.075F) * openAngle);
        set(model.lFrontLobe2, r, r);
        set(model.lFrontLobe, r * 0.45F, r * 0.45F);
        set(model.rFrontLobe2, r, -r);
        set(model.rFrontLobe, r * 0.45F, -r * 0.45F);
        set(model.rBackLobe2, -r, -r);
        set(model.rBackLobe, -r * 0.45F, -r * 0.45F);
        set(model.lBackLobe2, -r, r);
        set(model.lBackLobe, -r * 0.45F, r * 0.45F);
        if (egg.isFull())
        {
            float pulse = 1F + Mth.sin(age * 0.1F) * 0.015F;
            model.base.xScale = pulse;
            model.base.zScale = pulse;
        }
    }

    private static void set(ModelPart part, float x, float z)
    {
        part.xRot = x;
        part.zRot = z;
    }
}
