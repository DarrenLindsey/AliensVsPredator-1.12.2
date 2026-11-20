package org.avp.registry;

import org.avp.AliensVsPredator;

import com.mojang.serialization.Codec;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Data stored on item stacks. */
public final class ModDataComponents
{
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, AliensVsPredator.MOD_ID);

    /** Rounds left in a firearm's magazine. */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> AMMO = COMPONENTS.register("ammo",
            () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());

    private ModDataComponents()
    {
    }
}
