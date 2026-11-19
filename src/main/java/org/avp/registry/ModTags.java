package org.avp.registry;

import org.avp.AliensVsPredator;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

/** Tags that data packs (or the player) can change to adjust what the mod does. */
public final class ModTags
{
    /** Extra creatures a tesla coil strikes (it always strikes hostile mobs). */
    public static final TagKey<EntityType<?>> TESLA_COIL_TARGETS = TagKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "tesla_coil_targets"));

    /** Xenomorphs of every kind (filled in as the creatures are ported). Sentry turrets can target them. */
    public static final TagKey<EntityType<?>> XENOMORPHS = TagKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "xenomorphs"));

    /** Yautja (Predators). */
    public static final TagKey<EntityType<?>> PREDATORS = TagKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "predators"));

    /** Creatures a facehugger can't use as a host (undead, golems, small animals, other aliens...). */
    public static final TagKey<EntityType<?>> CANNOT_HOST = TagKey.create(Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "cannot_host"));

    /** Helmets a facehugger can't get through (sealed masks, later the Predator biomask). */
    public static final TagKey<net.minecraft.world.item.Item> FACEHUGGER_PROOF = TagKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "facehugger_proof"));

    /** Blocks Xenomorph acid blood melts through (iron doors, trapdoors, bars...). */
    public static final TagKey<net.minecraft.world.level.block.Block> ACID_MELTABLE = TagKey.create(Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "acid_meltable"));

    private ModTags()
    {
    }
}
