package org.avp.world.dimension;

import org.avp.AliensVsPredator;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

/** The dimensions (data: data/avp/dimension/*.json, written by tools/port_dimensions.py). */
public final class ModDimensions
{
    public static final ResourceKey<Level> VARDA = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "varda"));
    public static final ResourceKey<Level> ACHERON = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "acheron"));

    private ModDimensions()
    {
    }
}
