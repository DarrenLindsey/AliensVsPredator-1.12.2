package org.avp.refining;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

/** Every refining "recipe", for recipe viewers (JEI): what each machine turns into what. */
public final class RefiningRecipes
{
    public record Entry(RefineryKind kind, Ingredient input, ItemStack output)
    {
    }

    private RefiningRecipes()
    {
    }

    public static List<Entry> of(RefineryKind kind)
    {
        List<Entry> list = new ArrayList<>();
        for (RefineMaterial m : RefineMaterial.values())
        {
            list.add(new Entry(kind, m.ingredient(), new ItemStack(Refining.item(m, kind.makes), kind.multiplier)));
            RefineStage accepts = kind.accepts();
            if (accepts != null)
            {
                list.add(new Entry(kind, Ingredient.of(Refining.item(m, accepts)), new ItemStack(Refining.item(m, RefineStage.values()[accepts.ordinal() - 1]))));
            }
        }
        if (kind == RefineryKind.CRUSHER)
        {
            list.add(new Entry(kind, Ingredient.of(Items.COBBLESTONE, Items.STONE, Items.COBBLED_DEEPSLATE), new ItemStack(Items.GRAVEL)));
            list.add(new Entry(kind, Ingredient.of(Items.GRAVEL), new ItemStack(Items.SAND)));
            list.add(new Entry(kind, Ingredient.of(Items.BONE), new ItemStack(Items.BONE_MEAL, 5)));
            list.add(new Entry(kind, Ingredient.of(Items.GLOWSTONE), new ItemStack(Items.GLOWSTONE_DUST, 4)));
        }
        return list;
    }
}
