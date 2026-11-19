package org.avp.entity.creature;

/** Which kind of creature: decides its entity class and basic behaviour. */
public enum Family
{
    /** Adult Xenomorph (AdultXenomorphEntity): packs, senses, hunger, acid blood... */
    XENOMORPH,
    /** A chestburster variant: grows into its adult form. */
    BURSTER,
    /** A facehugger variant (attaches to faces and implants an embryo). */
    PARASITE,
    /** An egg variant. */
    EGG,
    /** Yautja (Predators): hunters with an honour code. */
    YAUTJA,
    /** Humans and synthetics: Colonial Marines, W-Y combat synthetics. */
    HUMAN,
    /** Engineers. */
    ENGINEER,
    /** Wildlife and other monsters (Varda creatures, mutants...). */
    BEAST
}
