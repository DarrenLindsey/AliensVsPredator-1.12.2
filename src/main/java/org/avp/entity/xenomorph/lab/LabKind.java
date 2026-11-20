package org.avp.entity.xenomorph.lab;

/** The two lab machines. */
public enum LabKind
{
    /** Sample + empty phial + up to three splice items -> embryo template. */
    GENE_SEQUENCER("gene_sequencer", 6, 300, 128),
    /** Embryo template + royal jelly -> a living, bonded Xenomorph (grown on top of the tank). */
    INCUBATION_TANK("incubation_tank", 2, 1200, 256);

    public final String id;
    public final int slots, ticks, power;

    LabKind(String id, int slots, int ticks, int power)
    {
        this.id = id;
        this.slots = slots;
        this.ticks = ticks;
        this.power = power;
    }
}
