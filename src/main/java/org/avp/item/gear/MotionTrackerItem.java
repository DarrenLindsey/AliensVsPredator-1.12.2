package org.avp.item.gear;

import org.avp.item.ModelledItem;

import net.minecraft.world.item.Item;

/**
 * The M314 motion tracker (1.12.2 itemMotionTracker). Held in either hand it shows the tracker
 * screen: anything moving within 30 blocks in front of you, pinging (client: MotionTrackerOverlay).
 */
public class MotionTrackerItem extends Item implements ModelledItem
{
    public MotionTrackerItem(Properties properties)
    {
        super(properties.stacksTo(1));
    }

    @Override
    public String model()
    {
        return "MotionTrackerModel";
    }

    @Override
    public String texture()
    {
        return "motiontracker.png";
    }

    @Override
    public boolean twoHanded()
    {
        return false;
    }
}
