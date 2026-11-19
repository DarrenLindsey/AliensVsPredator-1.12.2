package org.avp.registry;

import org.avp.AliensVsPredator;
import org.avp.entity.SeatEntity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Entity types. The creatures will be added here in the creatures phase. */
public final class ModEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, AliensVsPredator.MOD_ID);

    /** Invisible seat used to be inside lockers and medpods. */
    public static final DeferredHolder<EntityType<?>, EntityType<SeatEntity>> SEAT = ENTITIES.register("seat",
            () -> EntityType.Builder.<SeatEntity>of(SeatEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.1F).noSummon().clientTrackingRange(8).build("seat"));

    // ---- Creatures ----
    public static final DeferredHolder<EntityType<?>, EntityType<org.avp.entity.parasite.FacehuggerEntity>> FACEHUGGER = ENTITIES.register("facehugger",
            () -> EntityType.Builder.<org.avp.entity.parasite.FacehuggerEntity>of(org.avp.entity.parasite.FacehuggerEntity::new, MobCategory.MONSTER)
                    .sized(0.8F, 0.4F).eyeHeight(0.25F).clientTrackingRange(10).build("facehugger"));

    public static final DeferredHolder<EntityType<?>, EntityType<org.avp.entity.xenomorph.ChestbursterEntity>> CHESTBURSTER = ENTITIES.register("chestburster",
            () -> EntityType.Builder.<org.avp.entity.xenomorph.ChestbursterEntity>of(org.avp.entity.xenomorph.ChestbursterEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 0.5F).eyeHeight(0.35F).clientTrackingRange(10).build("chestburster"));

    public static final DeferredHolder<EntityType<?>, EntityType<org.avp.entity.xenomorph.OvamorphEntity>> OVAMORPH = ENTITIES.register("ovamorph",
            () -> EntityType.Builder.<org.avp.entity.xenomorph.OvamorphEntity>of(org.avp.entity.xenomorph.OvamorphEntity::new, MobCategory.MONSTER)
                    .sized(0.8F, 1.0F).eyeHeight(0.8F).clientTrackingRange(10).build("ovamorph"));

    public static final DeferredHolder<EntityType<?>, EntityType<org.avp.entity.xenomorph.DroneEntity>> DRONE = ENTITIES.register("drone",
            () -> EntityType.Builder.<org.avp.entity.xenomorph.DroneEntity>of(org.avp.entity.xenomorph.DroneEntity::new, MobCategory.MONSTER)
                    .sized(0.8F, 2.0F).eyeHeight(1.7F).clientTrackingRange(10).build("drone"));

    private ModEntities()
    {
    }
}
