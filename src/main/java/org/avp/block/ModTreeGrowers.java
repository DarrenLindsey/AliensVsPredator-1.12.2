package org.avp.block;

import java.util.Optional;

import org.avp.AliensVsPredator;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

/**
 * Which tree a sapling grows into. A TreeGrower only points at "configured
 * features" by name; the trees themselves are added with world generation.
 * Until then bonemealing the sapling just does nothing (no crash).
 */
public final class ModTreeGrowers
{
    public static final ResourceKey<ConfiguredFeature<?, ?>> UNITREE = feature("unitree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> UNITREE_TALL = feature("unitree_tall");

    public static final TreeGrower UNIDENTIFIED = new TreeGrower(AliensVsPredator.MOD_ID + ":unidentified",
            0.1F, Optional.empty(), Optional.empty(), Optional.of(UNITREE), Optional.of(UNITREE_TALL), Optional.empty(), Optional.empty());

    private static ResourceKey<ConfiguredFeature<?, ?>> feature(String name)
    {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, name));
    }

    private ModTreeGrowers()
    {
    }
}
