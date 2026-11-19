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

    private ModEntities()
    {
    }
}
