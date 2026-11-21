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

    public static final DeferredHolder<EntityType<?>, EntityType<org.avp.mining.PowerLoaderEntity>> POWER_LOADER = ENTITIES.register("power_loader",
            () -> EntityType.Builder.<org.avp.mining.PowerLoaderEntity>of(org.avp.mining.PowerLoaderEntity::new, MobCategory.MISC)
                    .sized(1.8F, 3.0F).clientTrackingRange(10).build("power_loader"));

    public static final DeferredHolder<EntityType<?>, EntityType<org.avp.pneumatic.CapsuleEntity>> CAPSULE = ENTITIES.register("pneumatic_capsule",
            () -> EntityType.Builder.<org.avp.pneumatic.CapsuleEntity>of(org.avp.pneumatic.CapsuleEntity::new, MobCategory.MISC)
                    .sized(0.3F, 0.3F).clientTrackingRange(8).updateInterval(1).noSummon().build("pneumatic_capsule"));

    public static final DeferredHolder<EntityType<?>, EntityType<org.avp.entity.joe.WorkingJoeEntity>> WORKING_JOE = ENTITIES.register("working_joe",
            () -> EntityType.Builder.<org.avp.entity.joe.WorkingJoeEntity>of(org.avp.entity.joe.WorkingJoeEntity::new, MobCategory.MISC)
                    .sized(0.6F, 1.9F).eyeHeight(1.7F).clientTrackingRange(10).build("working_joe"));

    public static final DeferredHolder<EntityType<?>, EntityType<org.avp.entity.xenomorph.BuilderDroneEntity>> BUILDER_DRONE = ENTITIES.register("builder_drone",
            () -> EntityType.Builder.<org.avp.entity.xenomorph.BuilderDroneEntity>of(org.avp.entity.xenomorph.BuilderDroneEntity::new, MobCategory.MONSTER)
                    .sized(0.82F, 1.65F).eyeHeight(1.39F).clientTrackingRange(10).build("builder_drone"));

    public static final DeferredHolder<EntityType<?>, EntityType<org.avp.entity.xenomorph.CoaterDroneEntity>> COATER_DRONE = ENTITIES.register("coater_drone",
            () -> EntityType.Builder.<org.avp.entity.xenomorph.CoaterDroneEntity>of(org.avp.entity.xenomorph.CoaterDroneEntity::new, MobCategory.MONSTER)
                    .sized(0.71F, 1.50F).eyeHeight(1.27F).clientTrackingRange(10).build("coater_drone"));

    public static final DeferredHolder<EntityType<?>, EntityType<org.avp.entity.projectile.GrenadeEntity>> GRENADE = ENTITIES.register("grenade",
            () -> EntityType.Builder.<org.avp.entity.projectile.GrenadeEntity>of(org.avp.entity.projectile.GrenadeEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange(6).updateInterval(5).build("grenade"));

    public static final DeferredHolder<EntityType<?>, EntityType<org.avp.entity.projectile.ThrownWeaponEntity>> THROWN_WEAPON = ENTITIES.register("thrown_weapon",
            () -> EntityType.Builder.<org.avp.entity.projectile.ThrownWeaponEntity>of(org.avp.entity.projectile.ThrownWeaponEntity::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F).clientTrackingRange(8).updateInterval(2).build("thrown_weapon"));

    public static final DeferredHolder<EntityType<?>, EntityType<org.avp.entity.projectile.LaserMineEntity>> LASER_MINE = ENTITIES.register("laser_mine",
            () -> EntityType.Builder.<org.avp.entity.projectile.LaserMineEntity>of(org.avp.entity.projectile.LaserMineEntity::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F).clientTrackingRange(8).updateInterval(20).build("laser_mine"));

    public static final DeferredHolder<EntityType<?>, EntityType<org.avp.entity.projectile.SupplyDropEntity>> SUPPLY_DROP = ENTITIES.register("supply_drop",
            () -> EntityType.Builder.<org.avp.entity.projectile.SupplyDropEntity>of(org.avp.entity.projectile.SupplyDropEntity::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F).clientTrackingRange(16).updateInterval(2).build("supply_drop"));

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
