package org.avp.refining;

/**
 * The Weyland-Yutani ore processing line. Each machine takes raw ore (or the form from the stage above
 * it) and multiplies it: Ore Crusher x2, Hydro-Washer x3 (needs water), Acid Leaching Vat x4 (needs
 * Xenomorph acid), Electrolytic Refinery x6, Nostromo-class Fusion Refinery x8 (needs huge power).
 */
public enum RefineryKind
{
    CRUSHER("ore_crusher", 100, 24, 2, RefineStage.CRUSHED, null, Fluid.NONE, 0),
    WASHER("hydro_washer", 120, 48, 3, RefineStage.WASHED, RefineStage.WASHED, Fluid.WATER, 200),
    LEACH_VAT("acid_leach_vat", 140, 96, 4, RefineStage.SHARD, RefineStage.SHARD, Fluid.ACID, 100),
    ELECTROLYTIC("electrolytic_refinery", 160, 384, 6, RefineStage.CRYSTAL, RefineStage.CRYSTAL, Fluid.NONE, 0),
    FUSION("fusion_refinery", 200, 1536, 8, RefineStage.CRYSTAL, null, Fluid.NONE, 0);

    public enum Fluid
    {
        NONE, WATER, ACID
    }

    public final String id;
    public final int ticks, power, multiplier;
    /** What it makes from ore. */
    public final RefineStage makes;
    /** The intermediate it turns back into the stage below (null: none). Crusher: washed -> crushed; washer: shard -> washed... */
    public final RefineStage downgrades;
    public final Fluid fluid;
    public final int fluidPerJob;

    RefineryKind(String id, int ticks, int power, int multiplier, RefineStage makes, RefineStage downgradesFrom, Fluid fluid, int fluidPerJob)
    {
        this.id = id;
        this.ticks = ticks;
        this.power = power;
        this.multiplier = multiplier;
        this.makes = makes;
        this.downgrades = downgradesFrom;
        this.fluid = fluid;
        this.fluidPerJob = fluidPerJob;
    }

    /** The intermediate this machine accepts from higher stages: crusher takes washed, washer takes shards, vat takes crystals. */
    public RefineStage accepts()
    {
        return switch (this)
        {
            case CRUSHER -> RefineStage.WASHED;
            case WASHER -> RefineStage.SHARD;
            case LEACH_VAT -> RefineStage.CRYSTAL;
            default -> null;
        };
    }

    public boolean highVoltage()
    {
        return this == ELECTROLYTIC || this == FUSION;
    }
}
