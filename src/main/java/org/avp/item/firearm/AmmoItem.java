package org.avp.item.firearm;

import net.minecraft.world.item.Item;

/** A magazine for one kind of gun (1.12.2 ItemAmmunition). */
public class AmmoItem extends Item
{
    private final AmmoType type;

    public AmmoItem(AmmoType type, Properties properties)
    {
        super(properties.stacksTo(16));
        this.type = type;
    }

    public AmmoType type()
    {
        return type;
    }
}
