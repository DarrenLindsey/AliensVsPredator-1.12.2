package org.avp.client.model;

import org.avp.client.model.geometry.DroneModel;
import org.avp.entity.xenomorph.BuilderDroneEntity;
import org.avp.entity.xenomorph.CoaterDroneEntity;
import org.avp.entity.xenomorph.DroneEntity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

/** Drone animation, straight from 1.12.2 ModelDrone.render: walking limbs, turning head, swinging tail. */
public class DroneEntityModel extends AvpEntityModel<DroneEntity, DroneModel>
{
    private final ModelPart[] headParts;
    private final ModelPart[] forearmParts;
    private final ModelPart[] dorsalParts;

    public DroneEntityModel(ModelPart root)
    {
        super(new DroneModel(root));
        DroneModel m = model;
        headParts = new ModelPart[] { m.head1, m.head2, m.head3, m.head4, m.head5, m.head6 };
        forearmParts = new ModelPart[] { m.leftarm2, m.rightarm2, m.leftarm3, m.rightarm3, m.leftarm4, m.rightarm4 };
        dorsalParts = new ModelPart[] { m.back1, m.back2, m.backhorn1, m.backhorn2, m.backhorn3, m.backhorn4,
                m.tailSpikes1, m.tailSpikes2, m.tailSpikes3, m.tailSpikes4, m.tailSpikes5 };
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
        // curl the tail up over the back when there's a wall behind (it's ~4 blocks long)
        float curl = drone.updateTailCurl(4.2F * drone.getScale());
        doTail(angle * (1F - curl * 0.7F), distMult, curl);

        float head = (float) Math.toRadians(netHeadYaw) * 0.75F;
        for (ModelPart part : headParts)
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
        if (drone instanceof BuilderDroneEntity)
        {
            shapeWeaver(1.13F, 1.32F, 0.68F);
        }
        else if (drone instanceof CoaterDroneEntity)
        {
            shapeWeaver(0.85F, 0.85F, 0.53F);
        }
    }

    /** Compact worker cowls and dorsal tubes, with caste-specific excavation or coating limbs. */
    private void shapeWeaver(float shellWidth, float forearmWidth, float dorsalHeight)
    {
        DroneModel m = model;
        m.body1.xScale = shellWidth;
        m.body2.xScale = shellWidth;
        for (ModelPart part : headParts)
        {
            part.xScale = shellWidth;
            part.yScale = 0.9F;
        }
        for (ModelPart part : forearmParts)
        {
            part.xScale = forearmWidth;
        }
        for (ModelPart part : dorsalParts)
        {
            part.yScale = dorsalHeight;
        }
    }

    /**
     * 1.12.2 ModelDrone.doTail: each tail segment hangs off the end of the one before it.
     * New: "curl" bends each segment a little further upwards (up to ~200 degrees in total),
     * so the tail rolls up over the back when there's a wall behind. With curl 0 this is exactly
     * the 1.12.2 tail.
     */
    private void doTail(float angle, float distMult, float curl)
    {
        DroneModel m = model;
        ModelPart[] tail = { m.tail1, m.tail2, m.tail3, m.tail4, m.tail5, m.stabber };
        ModelPart[] spikes = { m.tailSpikes1, m.tailSpikes2, m.tailSpikes3, m.tailSpikes4, m.tailSpikes5, null };
        // the modelled drop between segments (read before anything moves)
        float[] drop = new float[tail.length];
        for (int k = 1; k < tail.length; k++)
        {
            drop[k] = tail[k].y - tail[k - 1].y;
        }
        float bend = curl * 0.6F; // extra upward bend per segment, radians
        float multiplier = 0.25F;
        for (int k = 0; k < tail.length; k++)
        {
            if (k >= 2)
            {
                multiplier += distMult; // 1.12.2: 0.25, then + distMult for each segment after tail2
            }
            float yaw = k == 0 ? angle / 1.5F : angle * multiplier;
            float up = bend * (k + 1);
            tail[k].yRot = yaw;
            tail[k].xRot += up;
            if (k > 0)
            {
                place(tail[k], tail[k - 1], bend * k, drop[k]);
            }
            if (spikes[k] != null)
            {
                spikes[k].yRot = yaw;
                spikes[k].xRot += up;
                if (k > 0)
                {
                    place(spikes[k], tail[k - 1], bend * k, drop[k]);
                }
            }
        }
    }

    /** Puts a segment at the far end of the previous one (10 pixels along it), raised by its curl. */
    private static void place(ModelPart part, ModelPart previous, float previousUp, float drop)
    {
        float along = Mth.cos(previousUp) * 10.0F;
        part.z = previous.z + Mth.cos(previous.yRot) * along;
        part.x = previous.x + Mth.sin(previous.yRot) * along;
        part.y = previous.y + drop * Mth.cos(previousUp) - Mth.sin(previousUp) * 10.0F; // model y points down
    }
}
