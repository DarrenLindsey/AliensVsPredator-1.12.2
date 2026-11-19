package org.avp.entity.parasite;

import org.avp.entity.creature.CreatureSpec;
import org.avp.entity.creature.Creatures;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.level.Level;

/**
 * Facehugger variants: the Royal Facehugger (big, fights back, plants a Queen embryo) and the
 * Octohugger. They latch onto faces like the Facehugger.
 */
public class ParasiteEntity extends FacehuggerEntity
{
    private CreatureSpec spec;

    public ParasiteEntity(EntityType<? extends ParasiteEntity> type, Level level)
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
    protected void registerGoals()
    {
        super.registerGoals();
        if (spec().health() > 30)
        {
            targetSelector.addGoal(1, new HurtByTargetGoal(this)); // a Royal Facehugger fights its way to a host
        }
    }

    @Override
    protected String embryoKind()
    {
        return spec().embryo() == null ? EmbryoKinds.STANDARD : spec().embryo();
    }
}
