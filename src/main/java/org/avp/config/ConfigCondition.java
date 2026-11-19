package org.avp.config;

import org.avp.AliensVsPredator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * A data-pack condition that reads the mod's common settings, so data files can be switched
 * on and off from the config, e.g. in a biome modifier:
 *
 * <pre>"neoforge:conditions": [ { "type": "avp:ore_enabled", "ore": "bauxite" } ]</pre>
 */
public record ConfigCondition(String ore) implements ICondition
{
    public static final MapCodec<ConfigCondition> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.STRING.fieldOf("ore").forGetter(ConfigCondition::ore)
    ).apply(i, ConfigCondition::new));

    /** "avp:spawn_enabled" with "creature": natural spawning switches. */
    public record Spawn(String creature) implements ICondition
    {
        public static final MapCodec<Spawn> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                Codec.STRING.fieldOf("creature").forGetter(Spawn::creature)
        ).apply(i, Spawn::new));

        @Override
        public boolean test(IContext context)
        {
            var value = AvpConfig.COMMON.spawns.get(creature);
            return AvpConfig.common(AvpConfig.COMMON.naturalSpawns) && value != null && AvpConfig.common(value);
        }

        @Override
        public MapCodec<? extends ICondition> codec()
        {
            return CODEC;
        }
    }

    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS =
            DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, AliensVsPredator.MOD_ID);

    static
    {
        CONDITIONS.register("ore_enabled", () -> CODEC);
        CONDITIONS.register("spawn_enabled", () -> Spawn.CODEC);
    }

    @Override
    public boolean test(IContext context)
    {
        var value = AvpConfig.COMMON.ores.get(ore);
        return AvpConfig.common(AvpConfig.COMMON.generateOres) && value != null && AvpConfig.common(value);
    }

    @Override
    public MapCodec<? extends ICondition> codec()
    {
        return CODEC;
    }
}
