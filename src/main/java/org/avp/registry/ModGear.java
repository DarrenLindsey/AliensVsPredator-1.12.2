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
