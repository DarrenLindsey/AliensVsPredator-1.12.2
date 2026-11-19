package org.avp.client.model;

import org.avp.client.model.geometry.DroneModel;
import org.avp.entity.xenomorph.DroneEntity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

/** Drone animation, straight from 1.12.2 ModelDrone.render: walking limbs, turning head, swinging tail. */
public class DroneEntityModel extends AvpEntityModel<DroneEntity, DroneModel>
{
    public DroneEntityModel(ModelPart root)
    {
        super(new DroneModel(root));
    }

    @Override
    protected void animate(DroneEntity drone, float swing, float swingAmount, float age, float netHeadYaw, float headPitch)
    {
        DroneModel m = model;
        float angle = Mth.cos(age * 4.0F * 0.1F) * (float) Math.PI * 0.5F * swingAmount;
        float distMult = 0.25F;
        if (swingAmount < 0.01F)
        {
            angle = Mth.cos(age * 0.07F);
            distMult = 0.95F;
        }
        doTail(angle, distMult);

        float head = (float) Math.toRadians(netHeadYaw) * 0.75F;
        for (ModelPart part : new ModelPart[] { m.head1, m.head2, m.head3, m.head4, m.head5, m.head6 })
        {
            part.yRot = head;
        }
        float walk = Mth.cos(swing * 0.6662F) * 1.4F * swingAmount;
        float walkOpposite = Mth.cos(swing * 0.6662F + (float) Math.PI) * 1.4F * swingAmount;
        m.leftleg1.xRot = walk - 0.8028515F;
        m.leftleg2.xRot = walk - 0.4014257F;
        m.leftleg3.xRot = walk - 0.8028515F;
        m.leftleg4.xRot = walk;
        m.rightleg1.xRot = walkOpposite - 0.8028515F;
        m.rightleg2.xRot = walkOpposite - 0.4014257F;
        m.rightleg3.xRot = walkOpposite - 0.8028515F;
        m.rightleg4.xRot = walkOpposite;
        for (ModelPart arm : new ModelPart[] { m.rightarm1, m.rightarm2, m.rightarm3, m.rightarm4 })
        {
            arm.xRot = walk + 0.3665191F;
        }
        for (ModelPart arm : new ModelPart[] { m.leftarm1, m.leftarm2, m.leftarm3, m.leftarm4 })
        {
            arm.xRot = walkOpposite + 0.3665191F;
        }
    }

    /** 1.12.2 ModelDrone.doTail: each tail segment hangs off the end of the one before it. */
    private void doTail(float angle, float distMult)
    {
        DroneModel m = model;
        float multiplier = 0.25F;
        m.tail1.yRot = angle / 1.5F;
        m.tailSpikes1.yRot = angle / 1.5F;
        follow(m.tail2, m.tail1, angle * multiplier);
        follow(m.tailSpikes2, m.tail1, angle * multiplier);
        multiplier += distMult;
        follow(m.tail3, m.tail2, angle * multiplier);
        follow(m.tailSpikes3, m.tail2, angle * multiplier);
        multiplier += distMult;
        follow(m.tail4, m.tail3, angle * multiplier);
        follow(m.tailSpikes4, m.tail3, angle * multiplier);
        multiplier += distMult;
        follow(m.tail5, m.tail4, angle * multiplier);
        follow(m.tailSpikes5, m.tail4, angle * multiplier);
        multiplier += distMult;
        follow(m.stabber, m.tail5, angle * multiplier);
    }

    private static void follow(ModelPart part, ModelPart previous, float yaw)
    {
        part.yRot = yaw;
        part.z = previous.z + Mth.cos(previous.yRot) * 10.0F;
        part.x = previous.x + Mth.sin(previous.yRot) * 10.0F;
    }
}
