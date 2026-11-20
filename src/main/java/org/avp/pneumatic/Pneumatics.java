package org.avp.pneumatic;

import org.avp.registry.ModItems;
import org.avp.registry.Tab;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

/** PneuPost items. */
public final class Pneumatics
{
    /** The canister (what the capsule looks like; also a crafting part). */
    public static final DeferredItem<Item> CANISTER = ModItems.ITEMS.register("pneumatic_canister", () -> new Item(new Item.Properties()));

    static
    {
        Tab.CRAFTING.add(CANISTER);
    }

    private Pneumatics()
    {
    }

    public static void load()
    {
    }
}
