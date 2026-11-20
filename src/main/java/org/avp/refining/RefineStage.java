package org.avp.refining;

import java.util.Locale;

/** The four intermediate forms, lowest first. */
public enum RefineStage
{
    CRUSHED("Crushed %s Ore"), WASHED("Washed %s Ore"), SHARD("Leached %s Shard"), CRYSTAL("Refined %s Crystal");

    public final String english;

    RefineStage(String english)
    {
        this.english = english;
    }

    public String prefix()
    {
        return name().toLowerCase(Locale.ROOT);
    }

    public String itemId(RefineMaterial m)
    {
        return prefix() + "_" + m.id();
    }
}
