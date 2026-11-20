package org.avp.registry;

import org.avp.AliensVsPredator;
import org.avp.item.gear.FlamethrowerItem;
import org.avp.item.gear.GrenadeItem;
import org.avp.item.gear.MotionTrackerItem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

/** Weapons and equipment other than the firearms (batch F): Marine kit, Yautja gear, W-Y kit. */
public final class ModGear
{
    // ---- F2: Colonial Marine kit
    public static final DeferredItem<Item> GRENADE_M40 = item("grenade_m40", () -> new GrenadeItem(false, new Item.Properties()));
    public static final DeferredItem<Item> GRENADE_INCENDIARY = item("grenade_incendiary", () -> new GrenadeItem(true, new Item.Properties()));
    public static final DeferredItem<Item> PROPANE_TANK = item("ammo_flamethrower", () -> new Item(new Item.Properties().durability(400)));
    public static final DeferredItem<Item> M240ICU = item("gun_m240icu",
            () -> new FlamethrowerItem("M240ICUModel", "m240icu.png", 8.0, new Item.Properties()));
    public static final DeferredItem<Item> FLAMETHROWER_SEVASTOPOL = item("gun_flamethrower_sevastopol",
            () -> new FlamethrowerItem("SevastopolFlamethrowerModel", "flamethrower.sevastopol.png", 6.0, new Item.Properties()));
    public static final DeferredItem<Item> MOTION_TRACKER = item("motiontracker", () -> new MotionTrackerItem(new Item.Properties()));

    // ---- F3: Yautja gear
    /** Yautja alloy: tougher than diamond (1.12.2 "celtic" tool material). */
    public static final net.neoforged.neoforge.common.SimpleTier CELTIC = new net.neoforged.neoforge.common.SimpleTier(
            net.minecraft.tags.BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 2400, 10F, 4F, 18,
            () -> net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "artifact_tech"))));
    public static final DeferredItem<Item> CELTIC_SWORD = item("tool_celtic_sword", () -> new net.minecraft.world.item.SwordItem(CELTIC,
            new Item.Properties().attributes(net.minecraft.world.item.SwordItem.createAttributes(CELTIC, 3, -2.4F))));
    public static final DeferredItem<Item> CELTIC_PICKAXE = item("tool_celtic_pickaxe", () -> new net.minecraft.world.item.PickaxeItem(CELTIC,
            new Item.Properties().attributes(net.minecraft.world.item.DiggerItem.createAttributes(CELTIC, 1, -2.8F))));
    public static final DeferredItem<Item> CELTIC_AXE = item("tool_celtic_axe", () -> new net.minecraft.world.item.AxeItem(CELTIC,
            new Item.Properties().attributes(net.minecraft.world.item.DiggerItem.createAttributes(CELTIC, 5F, -3.0F))));
    public static final DeferredItem<Item> CELTIC_SHOVEL = item("tool_celtic_shovel", () -> new net.minecraft.world.item.ShovelItem(CELTIC,
            new Item.Properties().attributes(net.minecraft.world.item.DiggerItem.createAttributes(CELTIC, 1.5F, -3.0F))));
    public static final DeferredItem<Item> CELTIC_HOE = item("tool_celtic_hoe", () -> new net.minecraft.world.item.HoeItem(CELTIC,
            new Item.Properties().attributes(net.minecraft.world.item.DiggerItem.createAttributes(CELTIC, -4F, 0F))));
    public static final DeferredItem<Item> CELTIC_SPEAR = item("tool_celtic_spear", () -> new org.avp.item.gear.SpearItem(CELTIC,
            new Item.Properties().attributes(net.minecraft.world.item.SwordItem.createAttributes(CELTIC, 5, -2.9F))));
    public static final DeferredItem<Item> WRIST_BLADES = item("wristbracer_blades", () -> new org.avp.item.gear.WristBladesItem(CELTIC,
            new Item.Properties().attributes(net.minecraft.world.item.SwordItem.createAttributes(CELTIC, 4, -1.6F))));
    public static final DeferredItem<Item> WRISTBRACER = item("wristbracer", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> PLASMA_CASTER = item("plasmacannon", () -> new org.avp.item.gear.PlasmaCasterItem(new Item.Properties()));
    public static final DeferredItem<Item> SMART_DISC = item("smartdisc",
            () -> new org.avp.item.gear.ThrowableWeaponItem(10F, 1.6F, true, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SHURIKEN = item("shuriken",
            () -> new org.avp.item.gear.ThrowableWeaponItem(7F, 2.0F, false, new Item.Properties().stacksTo(16)));

    public static final DeferredHolder<SoundEvent, SoundEvent> PLASMA_SHOT = sound("weapons.plasmacaster");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLASMA_CHARGE = sound("weapons.plasmacaster.charge");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLADES_ALARM = sound("weapons.blades.alarm");
    public static final DeferredHolder<SoundEvent, SoundEvent> VISION_SWITCH = sound("predator.vision.switch");

    public static final DeferredHolder<SoundEvent, SoundEvent> FLAMETHROWER_SOUND = sound("weapons.flamethrower");
    public static final DeferredHolder<SoundEvent, SoundEvent> TRACKER_PING = sound("motiontracker.ping");
    public static final DeferredHolder<SoundEvent, SoundEvent> TRACKER_PONG = sound("motiontracker.pong");

    private static DeferredItem<Item> item(String id, java.util.function.Supplier<Item> factory)
    {
        DeferredItem<Item> item = ModItems.ITEMS.register(id, factory);
        Tab.MAIN.add(item);
        return item;
    }

    private static DeferredHolder<SoundEvent, SoundEvent> sound(String name)
    {
        return ModSounds.SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, name)));
    }

    public static void load()
    {
        // class loading registers everything above
    }

    private ModGear()
    {
    }
}
