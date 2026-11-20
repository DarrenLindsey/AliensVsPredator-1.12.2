package org.avp.refining;

import java.util.List;
import java.util.Locale;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * The metals the Weyland-Yutani refining line handles. Each has four intermediates:
 * crushed ore -> (smelt) ingot, washed ore -> crushed, leached shard -> washed, refined crystal -> shard.
 */
public enum RefineMaterial
{
    IRON(0xD8AF93, "minecraft:iron_ingot", List.of("c:ores/iron", "c:raw_materials/iron"), List.of()),
    GOLD(0xFCEE4B, "minecraft:gold_ingot", List.of("c:ores/gold", "c:raw_materials/gold"), List.of()),
    COPPER(0xE07A4A, "minecraft:copper_ingot", List.of("c:ores/copper", "c:raw_materials/copper"), List.of("avp:orecopper", "avp:deepslate_orecopper")),
    ALUMINUM(0xC8D0D8, "avp:ingot_aluminum", List.of("c:ores/aluminum", "c:raw_materials/aluminum", "c:ores/bauxite"),
            List.of("avp:orebauxite", "avp:deepslate_orebauxite")),
    LITHIUM(0xB8E0F0, "avp:ingot_lithium", List.of("c:ores/lithium"), List.of("avp:orelithium", "avp:deepslate_orelithium")),
    COBALT(0x3A5ED8, "avp:cobalt", List.of("c:ores/cobalt"), List.of("avp:orecobalt", "avp:deepslate_orecobalt")),
    NEODYMIUM(0x9A7AD0, "avp:neodymium", List.of("c:ores/monazite", "c:ores/neodymium"), List.of("avp:oremonazite", "avp:deepslate_oremonazite")),
    SILICON(0x8A949A, "avp:silicon", List.of("c:ores/silicon"), List.of("avp:oresilicon", "avp:deepslate_oresilicon"));

    public final int colour;
    public final ResourceLocation product;
    private final List<TagKey<Item>> tags;
    private final List<ResourceLocation> items;

    RefineMaterial(int colour, String product, List<String> tags, List<String> items)
    {
        this.colour = colour;
        this.product = ResourceLocation.parse(product);
        this.tags = tags.stream().map(t -> TagKey.create(Registries.ITEM, ResourceLocation.parse(t))).toList();
        this.items = items.stream().map(ResourceLocation::parse).toList();
    }

    public String id()
    {
        return name().toLowerCase(Locale.ROOT);
    }

    /** Is this an ore (or raw ore) of this metal? */
    public boolean isOre(ItemStack stack)
    {
        for (TagKey<Item> tag : tags)
        {
            if (stack.is(tag))
            {
                return true;
            }
        }
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return items.contains(key);
    }

    /** Every ore of this metal, as one ingredient (for recipe viewers). */
    public net.minecraft.world.item.crafting.Ingredient ingredient()
    {
        java.util.List<net.minecraft.world.item.crafting.Ingredient.Value> values = new java.util.ArrayList<>();
        tags.forEach(t -> values.add(new net.minecraft.world.item.crafting.Ingredient.TagValue(t)));
        items.forEach(id -> BuiltInRegistries.ITEM.getOptional(id).ifPresent(i -> values.add(new net.minecraft.world.item.crafting.Ingredient.ItemValue(new ItemStack(i)))));
        return net.minecraft.world.item.crafting.Ingredient.fromValues(values.stream());
    }

    public static RefineMaterial ofOre(ItemStack stack)
    {
        for (RefineMaterial m : values())
        {
            if (m.isOre(stack))
            {
                return m;
            }
        }
        return null;
    }
}
