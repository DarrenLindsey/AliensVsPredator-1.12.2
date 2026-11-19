package org.avp.client.model;

import org.avp.client.model.geometry.FacehuggerModel;
import org.avp.entity.parasite.FacehuggerEntity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

/**
 * Facehugger animation (new - the 1.12.2 model didn't move): the eight legs scuttle as it
 * runs, the long tail sways, and on a face the legs grip tight, the tail curls and the
 * air sacs pump.
 */
public class FacehuggerEntityModel extends AvpEntityModel<FacehuggerEntity, FacehuggerModel>
{
    private final ModelPart[] leftLegs;
    private final ModelPart[] rightLegs;
    private final ModelPart[] tail;

    public FacehuggerEntityModel(ModelPart root)
    {
        super(new FacehuggerModel(root));
        leftLegs = new ModelPart[] { model.lLeg1a, model.lLeg2a, model.lLeg3a, model.lLeg4a };
        rightLegs = new ModelPart[] { model.rLeg1a, model.rLeg2a, model.rLeg3a, model.rLeg4a };
        tail = new ModelPart[] { model.tail01, model.tail02, model.tail03, model.tail04, model.tail05, model.tail06, model.tail07,
                model.tail08, model.tail09, model.tail10, model.tail11, model.tail12, model.tail13, model.tail14, model.tail15 };
    }

    @Override
    protected void animate(FacehuggerEntity hugger, float limbSwing, float limbSwingAmount, float age, float netHeadYaw, float headPitch)
    {
        boolean attached = hugger.isAttached();
        boolean spent = !hugger.isFertile() && !attached;
        for (int i = 0; i < 4; i++)
        {
            float step = Mth.sin(limbSwing * 1.8F + i * Mth.HALF_PI) * 0.45F * limbSwingAmount;
            float grip = attached ? 0.45F + Mth.sin(age * 0.6F + i) * 0.03F : spent ? -0.35F : 0F;
            leftLegs[i].zRot += step + grip;
            rightLegs[i].zRot += step - grip;
            leftLegs[i].yRot += Mth.cos(limbSwing * 1.8F + i * Mth.HALF_PI) * 0.25F * limbSwingAmount;
            rightLegs[i].yRot -= Mth.cos(limbSwing * 1.8F + i * Mth.HALF_PI) * 0.25F * limbSwingAmount;
        }
        for (int k = 0; k < tail.length; k++)
        {
            if (spent)
            {
                break; // limp
            }
            float sway = attached ? 0.02F : 0.06F + limbSwingAmount * 0.08F;
            tail[k].yRot += Mth.sin(age * 0.15F - k * 0.45F) * sway;
            if (attached)
            {
                tail[k].xRot += 0.11F; // curled round the host's neck
            }
        }
        float breath = attached ? 1F + Mth.sin(age * 0.25F) * 0.12F : 1F + Mth.sin(age * 0.08F) * 0.03F;
        for (ModelPart sac : new ModelPart[] { model.lAirSac, model.rAirSac })
        {
            sac.xScale = breath;
            sac.zScale = breath;
        }
    }
}
