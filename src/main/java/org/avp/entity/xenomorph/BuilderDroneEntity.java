package org.avp.entity.xenomorph;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

/**
 * Builder Drone: a Drone that has taken up building for its hive. Heavier and slower, its jaws and claws
 * thick with hardened resin; it lays hivestone floors, bridges and seals and roofs open rooms over.
 * It never grows any further (Warriors come from worker Drones).
 */
public class BuilderDroneEntity extends DroneEntity
{
    public BuilderDroneEntity(EntityType<? extends BuilderDroneEntity> type, Level level)
    {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return DroneEntity.createAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.26)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.ARMOR, 7.0);
    }

    @Override
    public String job()
    {
        return hive() == null ? "" : "BUILDER";
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
