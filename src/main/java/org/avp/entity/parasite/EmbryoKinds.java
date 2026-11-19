package org.avp.entity.parasite;

import java.util.Map;

import javax.annotation.Nullable;

import org.avp.AliensVsPredator;
import org.avp.registry.ModTags;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

/**
 * What grows inside a host (1.12.2 Embryo registry): the DNA of the host shapes the Xenomorph.
 * A creeper gives a Boiler, a llama or witch a Spitter, a cow/pig/horse/wolf a four-legged Runner,
 * a Yautja the Predalien, an Engineer the Ultramorph... A Royal Facehugger always plants a Queen.
 *
 * An embryo "kind" decides which chestburster climbs out ({@link #burster}) and, for the kinds
 * that come out as a normal chestburster, what that grows into ({@link #adult}).
 */
public final class EmbryoKinds
{
    public static final String STANDARD = "standard";
    public static final String QUEEN = "queen";
    public static final String DEACON = "deacon";

    /** kind -> chestburster that climbs out */
    private static final Map<String, String> BURSTER = Map.ofEntries(
            Map.entry(QUEEN, "chestburster_queen"),
            Map.entry("predalien", "chestburster_predalien"),
            Map.entry("runner", "chestburster_runner"),
            Map.entry("beluga", "belugaburster"),
            Map.entry("draco", "dracoburster"),
            Map.entry(DEACON, "deacon"));

    /** kind -> adult, for kinds that come out as a normal chestburster */
    private static final Map<String, String> ADULT = Map.ofEntries(
            Map.entry("boiler", "boiler"),
            Map.entry("spitter", "spitter"),
            Map.entry("myceliomorph", "myceliomorph"),
            Map.entry("pantheramorph", "pantheramorph"),
            Map.entry("bat", "bat_xenomorph"),
            Map.entry("nauticomorph", "nauticomorph"),
            Map.entry("ultramorph", "ultramorph"));

    /** host -> kind (1.12.2 Embryo hosts) */
    private static final Map<EntityType<?>, String> BY_HOST = Map.ofEntries(
            Map.entry(EntityType.CREEPER, "boiler"),
            Map.entry(EntityType.WITCH, "spitter"),
            Map.entry(EntityType.LLAMA, "spitter"),
            Map.entry(EntityType.TRADER_LLAMA, "spitter"),
            Map.entry(EntityType.ENDER_DRAGON, "draco"),
            Map.entry(EntityType.MOOSHROOM, "myceliomorph"),
            Map.entry(EntityType.OCELOT, "pantheramorph"),
            Map.entry(EntityType.BAT, "bat"),
            Map.entry(EntityType.SQUID, "nauticomorph"),
            Map.entry(EntityType.GLOW_SQUID, "nauticomorph"),
            Map.entry(EntityType.GUARDIAN, "nauticomorph"),
            Map.entry(EntityType.DOLPHIN, "beluga"),
            Map.entry(EntityType.COW, "runner"),
            Map.entry(EntityType.HORSE, "runner"),
            Map.entry(EntityType.WOLF, "runner"),
            Map.entry(EntityType.POLAR_BEAR, "runner"),
            Map.entry(EntityType.PIG, "runner"),
            Map.entry(EntityType.SHEEP, "runner"));

    /** The kind of embryo a parasite of the given kind plants in this host. */
    public static String forHost(LivingEntity host, String parasiteKind)
    {
        if (!STANDARD.equals(parasiteKind))
        {
            return parasiteKind; // royal facehuggers, trilobites... decide it themselves
        }
        if (host.getType().is(ModTags.PREDATORS))
        {
            return "predalien";
        }
        String id = BuiltInRegistries.ENTITY_TYPE.getKey(host.getType()).getPath();
        if (id.equals("engineer") || id.equals("space_jockey"))
        {
            return "ultramorph";
        }
        return BY_HOST.getOrDefault(host.getType(), STANDARD);
    }

    public static ResourceLocation burster(String kind)
    {
        return avp(BURSTER.getOrDefault(kind, "chestburster"));
    }

    /** The adult a plain chestburster of this kind grows into, or null for the normal Drone. */
    @Nullable
    public static ResourceLocation adult(String kind)
    {
        String adult = ADULT.get(kind);
        return adult == null ? null : avp(adult);
    }

    private static ResourceLocation avp(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, path);
    }

    private EmbryoKinds()
    {
    }
}
