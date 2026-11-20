package org.avp.entity.xenomorph;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

/**
 * Coater Drone: a Drone that has taken up coating the hive. Light and quick, always on the walls and
 * ceilings, spraying resin over every bare surface. It never grows any further.
 */
public class CoaterDroneEntity extends DroneEntity
{
    public CoaterDroneEntity(EntityType<? extends CoaterDroneEntity> type, Level level)
    {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return DroneEntity.createAttributes()
                .add(Attributes.MAX_HEALTH, 24.0)
                .add(Attributes.MOVEMENT_SPEED, 0.34)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ARMOR, 2.0);
    }

    @Override
    public String job()
    {
        return hive() == null ? "" : "COATER";
    }

    @Nullable
    @Override
    protected ResourceLocation matureInto()
    {
        return null;
    }

    @Override
    public boolean isRoyalLine()
    {
        return false;
    }
}
