package org.avp.registry;

import org.avp.AliensVsPredator;
import org.avp.crafting.AssemblyRecipe;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Recipe types of the mod: the Assembler's schematics. */
public final class ModRecipes
{
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, AliensVsPredator.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, AliensVsPredator.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<AssemblyRecipe>> ASSEMBLY = TYPES.register("assembly",
            () -> RecipeType.simple(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "assembly")));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AssemblyRecipe>> ASSEMBLY_SERIALIZER = SERIALIZERS.register("assembly",
            () -> new RecipeSerializer<>()
            {
                @Override
                public MapCodec<AssemblyRecipe> codec()
                {
                    return AssemblyRecipe.CODEC;
                }

                @Override
                public StreamCodec<RegistryFriendlyByteBuf, AssemblyRecipe> streamCodec()
                {
                    return AssemblyRecipe.STREAM_CODEC;
                }
            });

    private ModRecipes()
    {
    }
}
