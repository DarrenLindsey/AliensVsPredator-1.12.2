package org.avp.registry;

import org.avp.AliensVsPredator;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

/**
 * Damage types. Each one is a data file (data/avp/damage_type/*.json) that sets the
 * death message; this class only names them for the code.
 */
public final class ModDamageTypes
{
    /** Tesla coils and (later) other electrical hazards. */
    public static final ResourceKey<DamageType> ELECTRICITY = ResourceKey.create(Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "electricity"));

    public static DamageSource electricity(Level level)
    {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ELECTRICITY));
    }

    private ModDamageTypes()
    {
    }
}
