package org.avp.item.gear;

import org.avp.item.ModelledItem;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

/** Yautja wrist blades: a fast, vicious melee weapon. */
public class WristBladesItem extends SwordItem implements ModelledItem
{
    public WristBladesItem(Tier tier, Properties properties)
    {
        super(tier, properties);
    }

    @Override
    public String model()
    {
        return "WristBladeModel";
    }

    @Override
    public String texture()
    {
        return "wristblade.png";
    }

    @Override
    public boolean twoHanded()
    {
        return false;
    }
}
