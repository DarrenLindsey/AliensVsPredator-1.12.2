package org.avp.registry;

import org.avp.AliensVsPredator;
import org.avp.entity.LockerSeatEntity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Entity types. The creatures will be added here in the creatures phase. */
public final class ModEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, AliensVsPredator.MOD_ID);

    /** Invisible seat used to hide inside lockers. */
    public static final DeferredHolder<EntityType<?>, EntityType<LockerSeatEntity>> LOCKER_SEAT = ENTITIES.register("locker_seat",
            () -> EntityType.Builder.<LockerSeatEntity>of(LockerSeatEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.1F).noSummon().clientTrackingRange(8).build("locker_seat"));

    private ModEntities()
    {
    }
}
