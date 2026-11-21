package org.avp.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.world.level.ItemLike;

/**
 * Browsing groups for the AVP Creative tab. Blocks and items join a group when registered.
 */
public enum Tab
{
    MAIN,
    BLOCKS,
    CRAFTING,
    GUN_PARTS;

    private final List<Supplier<? extends ItemLike>> contents = new ArrayList<>();

    public void add(Supplier<? extends ItemLike> entry)
    {
        contents.add(entry);
    }

    public List<Supplier<? extends ItemLike>> contents()
    {
        return contents;
    }
}
