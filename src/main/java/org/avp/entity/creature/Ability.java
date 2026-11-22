package org.avp.entity.creature;

/** Special abilities a creature in the catalog can have (see Creatures). */
public enum Ability
{
    /** Climbs walls like a spider (when its goal is higher up). */
    CLIMB,
    /** Drops to a crawl to fit through 1-block gaps. */
    CRAWL,
    /** Spits acid at range (Spitter). */
    SPIT_ACID,
    /** Bursts in a spray of acid when it reaches its prey or dies (Boiler). */
    ACID_BURST,
    /** Charges at its prey and bowls it over (Crusher). */
    CHARGE,
    /** At home in water: swims fast, breathes underwater (Nauticomorph, Belugamorph, Deacon Shark). */
    AQUATIC,
    /** Flies (Bat Xenomorph, Aethon). */
    FLY,
    /** Its bite can plant an embryo straight into its victim (Predalien, Trilobite). */
    IMPLANT_ON_BITE,
    /** The hive Queen: lays eggs when well fed; packs rally round her. */
    QUEEN,
    /** Stays close to a Queen and guards her (Praetorian). */
    GUARD_QUEEN,
    /** Patrols and defends its own hive (Warrior). */
    DEFEND_HIVE,
    /** Leaps at prey from a distance. */
    LEAP,
    /** Fast: a Runner. */
    FAST,
    /** Fire-resistant (from a Dracomorph's dragon host, Engineers' suits...). */
    FIREPROOF,
    /** Very big and tough: a boss (shown with a boss bar). */
    BOSS
}
