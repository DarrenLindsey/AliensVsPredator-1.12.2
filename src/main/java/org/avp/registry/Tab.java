package org.avp.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.world.level.ItemLike;

/**
 * The mod's creative tabs (same groups as the 1.12.2 version).
 * Blocks and items add themselves to a tab when they are registered;
 * {@link ModCreativeTabs} then shows each tab's list.
 */
public enum Tab
{
    MAIN("main"),
    BLOCKS("blocks"),
    CRAFTING("crafting"),
    GUN_PARTS("gun_parts");

    public final String id;
    private final List<Supplier<? extends ItemLike>> contents = new ArrayList<>();

    Tab(String id)
    {
        this.id = id;
    }

    public void add(Supplier<? extends ItemLike> entry)
    {
        contents.add(entry);
    }

    public List<Supplier<? extends ItemLike>> contents()
    {
        return contents;
    }
}
