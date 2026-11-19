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
                    .sized(0.95F, 2.0F).eyeHeight(1.7F).clientTrackingRange(10).build("drone"));

    public static final DeferredHolder<EntityType<?>, EntityType<org.avp.entity.creature.CreatureProjectile>> CREATURE_PROJECTILE = ENTITIES.register("creature_projectile",
            () -> EntityType.Builder.<org.avp.entity.creature.CreatureProjectile>of(org.avp.entity.creature.CreatureProjectile::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange(6).updateInterval(5).noSummon().build("creature_projectile"));

    /** Every creature in the catalog (org.avp.entity.creature.Creatures), by id. */
    public static final java.util.Map<String, DeferredHolder<EntityType<?>, EntityType<? extends net.minecraft.world.entity.Mob>>> CATALOG = new java.util.LinkedHashMap<>();

    static
    {
        for (org.avp.entity.creature.CreatureSpec spec : org.avp.entity.creature.Creatures.ALL.values())
        {
            CATALOG.put(spec.id(), ENTITIES.register(spec.id(), () -> build(spec)));
        }
    }

    private static EntityType<? extends net.minecraft.world.entity.Mob> build(org.avp.entity.creature.CreatureSpec spec)
    {
        EntityType.Builder<? extends net.minecraft.world.entity.Mob> builder = switch (spec.family())
        {
            case XENOMORPH -> EntityType.Builder.of(org.avp.entity.xenomorph.AdultXenomorphEntity::new, MobCategory.MONSTER);
            case BURSTER -> EntityType.Builder.of(org.avp.entity.xenomorph.BursterEntity::new, MobCategory.MONSTER);
            case PARASITE -> EntityType.Builder.of(org.avp.entity.parasite.ParasiteEntity::new, MobCategory.MONSTER);
            case EGG -> EntityType.Builder.of(org.avp.entity.xenomorph.EggEntity::new, MobCategory.MONSTER);
            case YAUTJA -> EntityType.Builder.of(org.avp.entity.yautja.YautjaEntity::new, MobCategory.MONSTER);
            case HUMAN -> EntityType.Builder.of(org.avp.entity.human.HumanEntity::new, MobCategory.CREATURE);
            case ENGINEER -> EntityType.Builder.of(org.avp.entity.creature.EngineerEntity::new, MobCategory.MONSTER);
            case BEAST -> EntityType.Builder.of(org.avp.entity.creature.BeastEntity::new,
                    spec.has(org.avp.entity.creature.Ability.AQUATIC) && !spec.hostile() ? MobCategory.WATER_CREATURE : MobCategory.MONSTER);
        };
        builder.sized(spec.width(), spec.height()).eyeHeight(spec.height() * 0.85F).clientTrackingRange(spec.width() > 2 ? 16 : 10);
        if (spec.has(org.avp.entity.creature.Ability.FIREPROOF))
        {
            builder.fireImmune();
        }
        return builder.build(spec.id());
    }

    private ModEntities()
    {
    }
}
