package org.avp.entity.xenomorph;

import java.util.UUID;

import org.avp.registry.ModAttachments;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

/** A short reservation while a drone closes in on prey it intends to carry home. */
public record PreyCapture(UUID owner, long expiresAt)
{
    static void reserve(DroneEntity drone, LivingEntity prey)
    {
        prey.setData(ModAttachments.PREY_CAPTURE, new PreyCapture(drone.getUUID(), prey.level().getGameTime() + 200));
    }

    static boolean blocksAttack(XenomorphEntity attacker, LivingEntity prey)
    {
        if (!(prey.level() instanceof ServerLevel level))
        {
            return false;
        }
        if (prey.getVehicle() instanceof DroneEntity)
        {
            return true;
        }
        if (!prey.hasData(ModAttachments.PREY_CAPTURE))
        {
            return false;
        }
        PreyCapture claim = prey.getData(ModAttachments.PREY_CAPTURE);
        if (level.getGameTime() >= claim.expiresAt || !(level.getEntity(claim.owner) instanceof DroneEntity carrier)
                || !carrier.isAlive() || carrier.getTarget() != prey || !carrier.canCapture(prey))
        {
            prey.removeData(ModAttachments.PREY_CAPTURE);
            return false;
        }
        return carrier != attacker;
    }
}
