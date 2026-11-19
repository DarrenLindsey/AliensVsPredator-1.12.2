package org.avp.crafting;

import java.util.ArrayList;
import java.util.List;

import org.avp.registry.ModRecipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

/**
 * An Assembler schematic (1.12.2 "Schematic"/AssemblyManager), now a normal data-pack recipe:
 * data/avp/recipe/assembly/*.json with "type": "avp:assembly", a list of ingredients with
 * counts, and a result. Materials come straight out of the player's inventory, so there's no
 * crafting grid. Data packs and modpacks can add, change or remove schematics.
 */
public record AssemblyRecipe(List<SizedIngredient> ingredients, ItemStack result) implements Recipe<AssemblyRecipe.Input>
{
    /** The player's inventory as a recipe input. */
    public record Input(Inventory inventory) implements RecipeInput
    {
        @Override
        public ItemStack getItem(int index)
        {
            return inventory.getItem(index);
        }

        @Override
        public int size()
        {
            return inventory.items.size();
        }
    }

    public static final MapCodec<AssemblyRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            SizedIngredient.FLAT_CODEC.listOf().fieldOf("ingredients").forGetter(AssemblyRecipe::ingredients),
            ItemStack.STRICT_CODEC.fieldOf("result").forGetter(AssemblyRecipe::result)
    ).apply(i, AssemblyRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AssemblyRecipe> STREAM_CODEC = StreamCodec.composite(
            SizedIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()), AssemblyRecipe::ingredients,
            ItemStack.STREAM_CODEC, AssemblyRecipe::result,
            AssemblyRecipe::new);

    /** How many of an ingredient the player has (not counting what earlier ingredients use). */
    public int[] have(Inventory inventory)
    {
        List<ItemStack> copy = copyMain(inventory);
        int[] have = new int[ingredients.size()];
        for (int i = 0; i < ingredients.size(); i++)
        {
            SizedIngredient need = ingredients.get(i);
            have[i] = take(copy, need.ingredient(), need.count());
        }
        return have;
    }

    /** Can the player afford one more? */
    public boolean canAssemble(Inventory inventory)
    {
        List<ItemStack> copy = copyMain(inventory);
        for (SizedIngredient need : ingredients)
        {
            if (take(copy, need.ingredient(), need.count()) < need.count())
            {
                return false;
            }
        }
        return true;
    }

    /** Takes the materials for one out of the player's inventory (check canAssemble first). */
    public void consume(Inventory inventory)
    {
        for (SizedIngredient need : ingredients)
        {
            take(inventory.items, need.ingredient(), need.count());
        }
        inventory.setChanged();
    }

    // ---- the same checks on an automation buffer (the Assembler's input slots)

    /** Can one be made from the items in this inventory? */
    public boolean canAssemble(net.neoforged.neoforge.items.IItemHandler buffer)
    {
        List<ItemStack> copy = new ArrayList<>();
        for (int i = 0; i < buffer.getSlots(); i++)
        {
            copy.add(buffer.getStackInSlot(i).copy());
        }
        for (SizedIngredient need : ingredients)
        {
            if (take(copy, need.ingredient(), need.count()) < need.count())
            {
                return false;
            }
        }
        return true;
    }

    /** Does this recipe use every kind of item in the buffer? (So a full buffer isn't mistaken for a smaller recipe.) */
    public boolean usesEverything(net.neoforged.neoforge.items.IItemHandler buffer)
    {
        for (int i = 0; i < buffer.getSlots(); i++)
        {
            ItemStack stack = buffer.getStackInSlot(i);
            if (!stack.isEmpty() && ingredients.stream().noneMatch(need -> need.ingredient().test(stack)))
            {
                return false;
            }
        }
        return true;
    }

    /** Takes the materials for one out of the buffer (check canAssemble first). */
    public void consume(net.neoforged.neoforge.items.IItemHandler buffer)
    {
        for (SizedIngredient need : ingredients)
        {
            int left = need.count();
            for (int i = 0; i < buffer.getSlots() && left > 0; i++)
            {
                if (need.ingredient().test(buffer.getStackInSlot(i)))
                {
                    left -= buffer.extractItem(i, left, false).getCount();
                }
            }
        }
    }

    /** Is this item used by this recipe at all? */
    public boolean uses(ItemStack stack)
    {
        return ingredients.stream().anyMatch(need -> need.ingredient().test(stack));
    }

    private static List<ItemStack> copyMain(Inventory inventory)
    {
        List<ItemStack> copy = new ArrayList<>();
        inventory.items.forEach(stack -> copy.add(stack.copy()));
        return copy;
    }

    /** Removes up to count matching items from the list. @return how many were found */
    private static int take(List<ItemStack> stacks, Ingredient ingredient, int count)
    {
        int found = 0;
        for (ItemStack stack : stacks)
        {
            if (found >= count)
            {
                break;
            }
            if (!stack.isEmpty() && ingredient.test(stack))
            {
                int used = Math.min(stack.getCount(), count - found);
                stack.shrink(used);
                found += used;
            }
        }
        return found;
    }

    @Override
    public boolean matches(Input input, Level level)
    {
        return canAssemble(input.inventory());
    }

    @Override
    public ItemStack assemble(Input input, HolderLookup.Provider registries)
    {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries)
    {
        return result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        NonNullList<Ingredient> list = NonNullList.create();
        ingredients.forEach(i -> list.add(i.ingredient()));
        return list;
    }

    /** Keeps these out of the vanilla recipe book (they have their own screen). */
    @Override
    public boolean isSpecial()
    {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.ASSEMBLY_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipes.ASSEMBLY.get();
    }
}
