package org.avp.entity.xenomorph.lab;

import java.util.Locale;

import javax.annotation.Nullable;

import org.avp.registry.ModItems;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/** Genetic enhancements the Gene Sequencer splices into an embryo template, and what each takes. */
public enum XenoTrait
{
    ARMOUR("Reinforced chitin: +8 armour"),
    VITALITY("Vitality: +50% health"),
    STRENGTH("Strength: +50% attack"),
    SPEED("Speed: +25% movement"),
    SIZE("Gigantism: +30% size and health"),
    ACID("Hyper-acid: its bite burns"),
    REGENERATION("Regeneration: heals over time"),
    OBEDIENCE("Neural lace: obeys programs (WORK mode), steadier");

    public final String description;

    XenoTrait(String description)
    {
        this.description = description;
    }

    public String id()
    {
        return name().toLowerCase(Locale.ROOT);
    }

    /** The splice item for this trait. */
    public Item item()
    {
        return switch (this)
        {
            case ARMOUR -> Items.IRON_BLOCK;
            case VITALITY -> Items.GOLDEN_APPLE;
            case STRENGTH -> ModItems.COBALT.get();
            case SPEED -> Items.RABBIT_FOOT;
            case SIZE -> ModItems.ROYALJELLY.get();
            case ACID -> org.avp.refining.Refining.ACID_VIAL.get();
            case REGENERATION -> Items.GHAST_TEAR;
            case OBEDIENCE -> ModItems.PART_PROCESSOR.get();
        };
    }

    @Nullable
    public static XenoTrait of(ItemStack stack)
    {
        for (XenoTrait t : values())
        {
            if (stack.is(t.item()))
            {
                return t;
            }
        }
        return null;
    }

    @Nullable
    public static XenoTrait byId(String id)
    {
        for (XenoTrait t : values())
        {
            if (t.id().equals(id))
            {
                return t;
            }
        }
        return null;
    }
}
