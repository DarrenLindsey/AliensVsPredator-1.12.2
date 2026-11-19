package org.avp.entity;

import org.avp.AliensVsPredator;
import org.avp.entity.parasite.FacehuggerEntity;
import org.avp.registry.ModEntities;

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
        event.put(ModEntities.CHESTBURSTER.get(), org.avp.entity.xenomorph.ChestbursterEntity.createAttributes().build());
    }

    /** Which biomes and how often: data/avp/neoforge/biome_modifier/spawn_*.json. This only says "on the ground, in the dark". */
    @SubscribeEvent
    public static void spawnPlacements(RegisterSpawnPlacementsEvent event)
    {
        event.register(ModEntities.FACEHUGGER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    private ModEntityEvents()
    {
    }
}
