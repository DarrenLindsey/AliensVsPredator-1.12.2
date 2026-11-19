package org.avp.client.model;

import org.avp.client.model.geometry.ChestbursterModel;
import org.avp.entity.xenomorph.ChestbursterEntity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

/**
 * Chestburster animation (1.12.2 ModelChestburster): the tail lashes faster as it runs and
 * sways gently when still; the jaw opens a little to hiss and when it attacks.
 */
public class ChestbursterEntityModel extends AvpEntityModel<ChestbursterEntity, ChestbursterModel>
{
    private final ModelPart[] tail;

    public ChestbursterEntityModel(ModelPart root)
    {
        super(new ChestbursterModel(root));
        tail = new ModelPart[] { model.tail1, model.tail2, model.tail3, model.tail4, model.tail5, model.tail6, model.tail7 };
    }

    @Override
    protected void animate(ChestbursterEntity burster, float limbSwing, float limbSwingAmount, float age, float netHeadYaw, float headPitch)
    {
        float lash = Mth.cos(limbSwing * 0.8F) * 0.35F * limbSwingAmount;
        float idle = Mth.cos(age * 0.15F) * 0.06F * (1F - limbSwingAmount);
        for (int i = 0; i < tail.length; i++)
        {
            tail[i].yRot += (lash + idle) * (0.6F + i * 0.1F);
        }
        float jaw = burster.getTarget() != null ? 0.12F + Mth.sin(age * 0.5F) * 0.06F : Math.max(0, Mth.sin(age * 0.05F)) * 0.05F;
        model.mouth.xRot += jaw;
    }
}
