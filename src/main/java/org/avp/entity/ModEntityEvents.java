package org.avp.entity;

import org.avp.AliensVsPredator;
import org.avp.entity.parasite.FacehuggerEntity;
import org.avp.registry.ModEntities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

/** Creature setup: health/speed/... (attributes) and where natural spawns may appear. */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEntityEvents
{
    @SubscribeEvent
    public static void attributes(EntityAttributeCreationEvent event)
    {
        event.put(ModEntities.FACEHUGGER.get(), FacehuggerEntity.createAttributes().build());
        event.put(ModEntities.DRONE.get(), org.avp.entity.xenomorph.DroneEntity.createAttributes().build());
        event.put(ModEntities.WORKING_JOE.get(), org.avp.entity.joe.WorkingJoeEntity.createAttributes().build());
        event.put(ModEntities.BUILDER_DRONE.get(), org.avp.entity.xenomorph.BuilderDroneEntity.createAttributes().build());
        event.put(ModEntities.COATER_DRONE.get(), org.avp.entity.xenomorph.CoaterDroneEntity.createAttributes().build());
        event.put(ModEntities.OVAMORPH.get(), org.avp.entity.xenomorph.OvamorphEntity.createAttributes().build());
        event.put(ModEntities.CHESTBURSTER.get(), org.avp.entity.xenomorph.ChestbursterEntity.createAttributes().build());
        ModEntities.CATALOG.forEach((id, type) -> event.put(type.get(),
                org.avp.entity.creature.Creatures.attributes(org.avp.entity.creature.Creatures.ALL.get(id)).build()));
    }

    /** Which biomes and how often: data/avp/neoforge/biome_modifier/spawn_*.json. This only says "on the ground, in the dark". */
    @SubscribeEvent
    public static void spawnPlacements(RegisterSpawnPlacementsEvent event)
    {
        event.register(ModEntities.FACEHUGGER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.DRONE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        // catalog creatures with natural spawns (see tools/port_creatures.py)
        // every catalog creature (they also spawn from the dimensions' biomes)
        for (String id : ModEntities.CATALOG.keySet())
        {
            register(event, ModEntities.CATALOG.get(id).get(), id);
        }
    }

    /** Catalog creatures that spawn naturally (each has a spawn_<id> biome modifier and a config switch). */
    public static final java.util.List<String> NATURAL = java.util.List.of("warrior", "runner_drone", "nauticomorph", "yautja_warrior",
            "marine", "engineer", "trilobite", "deacon_shark", "scelemur", "ursuidae", "hammerpede", "goo_mutant");

    @SuppressWarnings("unchecked")
    private static <T extends net.minecraft.world.entity.Mob> void register(RegisterSpawnPlacementsEvent event, EntityType<T> type, String id)
    {
        org.avp.entity.creature.CreatureSpec spec = org.avp.entity.creature.Creatures.ALL.get(id);
        if (spec.has(org.avp.entity.creature.Ability.AQUATIC))
        {
            event.register(type, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    (t, level, reason, pos, random) -> level.getFluidState(pos).is(net.minecraft.tags.FluidTags.WATER),
                    RegisterSpawnPlacementsEvent.Operation.REPLACE);
        }
        else if (spec.family() == org.avp.entity.creature.Family.HUMAN || !spec.hostile())
        {
            event.register(type, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    net.minecraft.world.entity.Mob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        }
        else
        {
            event.register((EntityType<Monster>) (EntityType<?>) type, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        }
    }

    private ModEntityEvents()
    {
    }
}
