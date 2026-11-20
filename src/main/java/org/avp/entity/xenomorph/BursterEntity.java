package org.avp.entity.xenomorph;

import org.avp.AliensVsPredator;
import org.avp.entity.creature.Ability;
import org.avp.entity.creature.CreatureSpec;
import org.avp.entity.creature.Creatures;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

/**
 * Chestburster variants: Queen, Predalien, Runner, Belugaburster, Dracoburster. They behave like
 * the normal Chestburster (hide from players, feed on small animals, grow up) but grow into
 * their own adult form (from the catalog).
 */
public class BursterEntity extends ChestbursterEntity
{
    private CreatureSpec spec;

    public BursterEntity(EntityType<? extends BursterEntity> type, Level level)
    {
        super(type, level);
        this.xpReward = spec().xp();
    }

    public CreatureSpec spec()
    {
        if (spec == null)
        {
            spec = Creatures.get(getType());
        }
        return spec;
    }

    @Override
    protected ResourceLocation defaultAdult()
    {
        return ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, spec().matureInto());
    }

}
