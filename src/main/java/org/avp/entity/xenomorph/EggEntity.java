package org.avp.entity.xenomorph;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/** Other eggs (Dracomorph egg, H.R. Giger's egg): they open for a host and release a facehugger like the Ovamorph. */
public class EggEntity extends OvamorphEntity
{
    public EggEntity(EntityType<? extends EggEntity> type, Level level)
    {
        super(type, level);
    }
}
