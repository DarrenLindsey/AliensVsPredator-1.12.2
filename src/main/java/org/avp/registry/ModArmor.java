package org.avp.registry;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.avp.AliensVsPredator;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Armour sets (1.12.2 MaterialHandler + ItemArmor*): Colonial Marine, Yautja (Celtic), Seegson MK50
 * compression suit, W-Y pressure suit and Xenomorph chitin. Each set's special effect while worn
 * is in org.avp.item.gear.ArmorEffects.
 */
public final class ModArmor
{
    public static final DeferredRegister<ArmorMaterial> MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, AliensVsPredator.MOD_ID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> MARINE = material("marine", 2, 6, 5, 2, 1.0F, 0F, 12,
            SoundEvents.ARMOR_EQUIP_IRON, "avp:polycarbonate");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CELTIC = material("celtic", 3, 8, 6, 3, 3.0F, 0.1F, 18,
            SoundEvents.ARMOR_EQUIP_NETHERITE, "avp:artifact_tech");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> MK50 = material("mk50", 2, 5, 4, 2, 0.5F, 0F, 8,
            SoundEvents.ARMOR_EQUIP_CHAIN, "avp:polycarbonate");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> PRESSURE = material("pressure", 2, 4, 3, 1, 0F, 0F, 8,
            SoundEvents.ARMOR_EQUIP_LEATHER, "avp:polycarbonate");
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> XENO = material("xeno", 3, 7, 6, 3, 2.0F, 0.05F, 15,
            SoundEvents.ARMOR_EQUIP_TURTLE, "avp:royaljelly");

    /** set name -> [helmet, chestplate, leggings, boots] */
    public static final Map<String, List<DeferredItem<Item>>> SETS = new java.util.LinkedHashMap<>();

    static
    {
        set("marine", MARINE, 22);
        set("celtic", CELTIC, 37);
        set("mk50", MK50, 15);
        set("pressure", PRESSURE, 12);
        set("xeno", XENO, 30);
    }

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> material(String name, int helm, int chest, int legs, int boots,
            float toughness, float knockback, int enchant, Holder<net.minecraft.sounds.SoundEvent> sound, String repair)
    {
        return MATERIALS.register(name, () ->
        {
            Map<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
            defense.put(ArmorItem.Type.HELMET, helm);
            defense.put(ArmorItem.Type.CHESTPLATE, chest);
            defense.put(ArmorItem.Type.LEGGINGS, legs);
            defense.put(ArmorItem.Type.BOOTS, boots);
            defense.put(ArmorItem.Type.BODY, chest);
            return new ArmorMaterial(defense, enchant, sound,
                    () -> Ingredient.of(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(ResourceLocation.parse(repair))),
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, name))), toughness, knockback);
        });
    }

    private static void set(String name, DeferredHolder<ArmorMaterial, ArmorMaterial> material, int durability)
    {
        List<DeferredItem<Item>> items = new java.util.ArrayList<>();
        for (ArmorItem.Type type : new ArmorItem.Type[] { ArmorItem.Type.HELMET, ArmorItem.Type.CHESTPLATE, ArmorItem.Type.LEGGINGS, ArmorItem.Type.BOOTS })
        {
            String prefix = switch (type)
            {
                case HELMET -> "helm";
                case CHESTPLATE -> "body";
                case LEGGINGS -> "legs";
                default -> "boots";
            };
            DeferredItem<Item> item = ModItems.ITEMS.register(prefix + "_" + name,
                    () -> new ArmorItem(material, type, new Item.Properties().durability(type.getDurability(durability))));
            items.add(item);
            Tab.MAIN.add(item);
        }
        SETS.put(name, items);
    }

    /** Is the wearer in the full set? */
    public static boolean wearsFull(net.minecraft.world.entity.LivingEntity entity, String set)
    {
        List<DeferredItem<Item>> items = SETS.get(set);
        return entity.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.HEAD).is(items.get(0).get())
                && entity.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.CHEST).is(items.get(1).get())
                && entity.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.LEGS).is(items.get(2).get())
                && entity.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.FEET).is(items.get(3).get());
    }

    public static void load()
    {
        // class loading registers everything above
    }

    private ModArmor()
    {
    }
}
