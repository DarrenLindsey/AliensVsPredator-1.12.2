package org.avp.entity.creature;

import static org.avp.entity.creature.Ability.*;
import static org.avp.entity.creature.Family.*;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;

/**
 * The catalog of creatures (other than the hand-written Facehugger, Chestburster, Ovamorph and
 * Drone). Health, size and xp are the 1.12.2 values (tools/survey_creatures.py). Speeds are
 * converted to modern units (x0.57, the same as the Drone's 0.53 -> 0.3) and Xenomorph damage is
 * raised (about x1.75) as for the Drone, so they're as dangerous as in the films.
 *
 * Adding a creature: add it here, add its data in tools/port_creatures.py. The entity type, spawn
 * egg, attributes and renderer are made from this entry.
 */
public final class Creatures
{
    public static final Map<String, CreatureSpec> ALL = new LinkedHashMap<>();

    static
    {
        // ---------------------------------------------------------------- the hive
        add(CreatureSpec.of("warrior", XENOMORPH).stats(50, 7, 0.285, 6).size(1.0F, 2.5F).xp(175)
                .can(CLIMB, CRAWL, LEAP).matures("praetorian").look("WarriorModel", "warrior.png", 1.0F)
                .egg(0x1a1a1f, 0x3d4250).lore("The hive's soldier. Grown from a Drone that fed well."));
        add(CreatureSpec.of("praetorian", XENOMORPH).stats(100, 9, 0.27, 10).size(1.2F, 3.0F).xp(300)
                .can(GUARD_QUEEN, LEAP).matures("matriarch").look("PraetorianModel", "praetorian.png", 1.4F).sounds("praetorian")
                .egg(0x141414, 0x5a4b3a).lore("The Queen's guard. Given enough royal jelly, one may become a Queen."));
        add(CreatureSpec.of("matriarch", XENOMORPH).stats(300, 14, 0.2, 14).size(2.0F, 5.0F).xp(4000)
                .can(QUEEN, BOSS).look("MatriarchModel", "matriarch.png", 1.75F).sounds("queen")
                .egg(0x0d0d0d, 0x6b1b1b).lore("The Queen. Lays eggs while well fed; every Xenomorph nearby comes when she calls."));
        add(CreatureSpec.of("crusher", XENOMORPH).stats(90, 9, 0.3, 16).size(1.4F, 2.4F).xp(300)
                .can(CHARGE).look("CrusherQuadModel", "crusher_quad.png", 1.6F)
                .egg(0x202020, 0x7a6a50).lore("Armoured head-plate, bred from the Runner line. Charges and bowls its prey over."));
        add(CreatureSpec.of("spitter", XENOMORPH).stats(30, 5, 0.31, 4).size(1.0F, 2.6F).xp(275)
                .can(SPIT_ACID, CLIMB, CRAWL).look("SpitterModel", "spitter.png", 1.0F)
                .egg(0x1f2a12, 0x8fd13f).lore("Grown in a llama or witch. Spits acid from a distance."));
        add(CreatureSpec.of("boiler", XENOMORPH).stats(30, 6, 0.31, 2).size(1.0F, 2.6F).xp(275)
                .can(ACID_BURST, CLIMB).look("BoilerModel", "boiler.png", 1.0F)
                .egg(0x2a2a10, 0xc8d13f).lore("Grown in a creeper. Swollen with acid; bursts over its prey."));
        add(CreatureSpec.of("runner_drone", XENOMORPH).stats(20, 4, 0.36, 2).size(0.9F, 1.3F).xp(100)
                .can(CLIMB, CRAWL, LEAP, FAST).matures("runner_warrior").look("RunnerDroneModel", "runner_drone.png", 0.8F)
                .egg(0x3a2a18, 0x8a6a40).lore("A four-legged Runner, grown in a beast. Fast, and climbs anything."));
        add(CreatureSpec.of("runner_warrior", XENOMORPH).stats(40, 6, 0.34, 4).size(1.0F, 1.6F).xp(175)
                .can(CLIMB, CRAWL, LEAP, FAST).matures("crusher").look("RunnerWarriorModel", "runner_warrior.png", 1.0F)
                .egg(0x3a2a18, 0x5a4020).lore("A grown Runner. Feed it enough and it becomes a Crusher."));
        add(CreatureSpec.of("nauticomorph", XENOMORPH).stats(40, 6, 0.31, 4).size(0.8F, 1.8F).xp(100)
                .can(AQUATIC, CLIMB).look("NauticomorphModel", "nauticomorph.png", 0.8F)
                .egg(0x12202a, 0x3f8fd1).lore("Grown in a squid or guardian. Hunts in the water."));
        add(CreatureSpec.of("predalien", XENOMORPH).stats(200, 10, 0.27, 10).size(1.0F, 2.5F).xp(225)
                .can(IMPLANT_ON_BITE, LEAP).embryo("standard").look("PredalienModel", "predalien.png", 0.75F).sounds("predalien")
                .egg(0x2a2a1a, 0x6a6a3a).lore("Born from a Yautja. Plants embryos straight into its victims with its bite."));
        add(CreatureSpec.of("ultramorph", XENOMORPH).stats(230, 11, 0.27, 10).size(1.5F, 4.0F).xp(300)
                .can(LEAP).look("UltramorphModel", "ultramorph.png", 1.5F)
                .egg(0x1a1a24, 0x6a6a90).lore("Born from an Engineer. Huge, pale and terribly strong."));
        add(CreatureSpec.of("bat_xenomorph", XENOMORPH).stats(20, 4, 0.31, 0).size(0.8F, 0.9F).xp(60)
                .can(FLY).look("BatXenomorphModel", "batxeno.png", 0.75F)
                .egg(0x161616, 0x4a3a3a).lore("Grown in a bat. Hunts from the air."));
        add(CreatureSpec.of("pantheramorph", XENOMORPH).stats(30, 6, 0.33, 4).size(1.0F, 1.4F).xp(100)
                .can(CLIMB, CRAWL, LEAP, FAST).look("PantheramorphModel", "pantheramorph.png", 0.75F)
                .egg(0x1a1a10, 0x7a7a30).lore("Grown in an ocelot. A pouncing hunter."));
        add(CreatureSpec.of("myceliomorph", XENOMORPH).stats(40, 5, 0.33, 4).size(0.8F, 1.8F).xp(100)
                .can(CLIMB, CRAWL).look("MyceliomorphModel", "myceliomorph.png", 1.0F)
                .egg(0x3a2a3a, 0x8a7a8a).lore("Grown in a mooshroom. Its claws carry spores."));
        add(CreatureSpec.of("belugamorph", XENOMORPH).stats(100, 8, 0.27, 6).size(0.7F, 2.25F).xp(100)
                .can(AQUATIC).look("BelugamorphModel", "belugamorph.png", 1.0F).sounds("prequelmorph")
                .egg(0xbfc8c8, 0x7a8a8a).lore("Grown in a dolphin. Pale and at home in the deep."));
        add(CreatureSpec.of("dracomorph", XENOMORPH).stats(400, 14, 0.26, 12).size(4.0F, 7.0F).xp(1000)
                .can(FLY, FIREPROOF, BOSS).look("DracomorphModel", "dracomorph.png", 1.0F)
                .egg(0x101010, 0x8a20a0).lore("Born from the Ender Dragon. A winged nightmare."));
        add(CreatureSpec.of("deacon", XENOMORPH).stats(60, 4, 0.27, 2).size(0.6F, 1.8F).xp(100)
                .can(CLIMB, CRAWL, LEAP).matures("deacon_adult").look("DeaconModel", "deacon.png", 1.0F).sounds("prequelmorph")
                .egg(0x5a6a7a, 0x9aaabb).lore("The Deacon: what comes out of an Engineer carrying a Trilobite's embryo."));
        add(CreatureSpec.of("deacon_adult", XENOMORPH).stats(120, 6, 0.27, 6).size(0.8F, 3.2F).xp(200)
                .can(LEAP).look("DeaconAdultModel", "deacon_adult.png", 1.0F).sounds("prequelmorph")
                .egg(0x4a5a6a, 0x8a9aab).lore("A fully grown Deacon."));

        // ---------------------------------------------------------------- chestbursters
        burster("chestburster_queen", "matriarch", "ChestbursterQueenModel", "chestburster_queen.png", 0.55F, 0x8a2020, "A Royal Facehugger's embryo: a future Queen.");
        burster("chestburster_predalien", "predalien", "ChestbursterPredalienModel", "chestburster_predalien.png", 0.55F, 0x6a6a3a, "Born from a Yautja host.");
        burster("chestburster_runner", "runner_drone", "ChestbursterRunnerModel", "chestburster_runner.png", 0.55F, 0x8a6a40, "Born from a beast; grows into a Runner.");
        add(CreatureSpec.of("belugaburster", BURSTER).stats(14, 2, 0.34, 0).size(0.6F, 0.5F).xp(16).can(AQUATIC)
                .matures("belugamorph").look("BelugabursterModel", "belugaburster.png", 0.55F).sounds("chestburster")
                .egg(0xbfc8c8, 0x7a8a8a).lore("Born from a dolphin."));
        add(CreatureSpec.of("dracoburster", BURSTER).stats(25, 3, 0.34, 2).size(0.8F, 0.6F).xp(16).can(FIREPROOF)
                .matures("dracomorph").look("DracobursterModel", "dracoburster.png", 1.0F).sounds("chestburster")
                .egg(0x101010, 0x8a20a0).lore("Born from the Ender Dragon."));

        // ---------------------------------------------------------------- parasites
        add(CreatureSpec.of("royal_facehugger", PARASITE).stats(80, 5, 0.34, 6).size(1.0F, 0.8F).xp(300)
                .embryo("queen").look("RoyalFacehuggerModel", "royalfacehugger.png", 1.0F).sounds("facehugger")
                .egg(0xc8a060, 0x6b1b1b).lore("Carries a Queen embryo. Big enough to fight its way to a host."));
        add(CreatureSpec.of("octohugger", PARASITE).stats(14, 1, 0.32, 0).size(0.4F, 0.6F).xp(10)
                .can(CLIMB).embryo("standard").look("OctohuggerModel", "octohugger.png", 1.0F).sounds("facehugger")
                .egg(0xc8a060, 0x3a5a3a).lore("Hangs from ceilings and drops onto passing hosts."));
        add(CreatureSpec.of("trilobite", BEAST).stats(44, 5, 0.33, 4).size(2.0F, 1.5F).xp(32)
                .can(IMPLANT_ON_BITE, CLIMB).embryo("deacon").look("TrilobiteModel", "trilobite.png", 1.0F).sounds("facehugger")
                .egg(0xd8c8b8, 0x8a6a6a).lore("The giant facehugger from the Prometheus. Its embryo becomes a Deacon."));

        // ---------------------------------------------------------------- eggs
        add(CreatureSpec.of("draco_ovamorph", EGG).stats(16, 0, 0, 2).size(0.8F, 1.0F).xp(10)
                .look("DracoOvamorphModel", "dracomorphegg.png", 1.75F).egg(0x101010, 0x8a20a0)
                .lore("A Dracomorph's egg."));
        add(CreatureSpec.of("ovamorph_giger", EGG).stats(8, 0, 0, 0).size(0.8F, 1.0F).xp(10)
                .look("OvamorphGigerModel", "gigeregg.png", 1.0F).egg(0x6a6a50, 0x2a2a20)
                .lore("An egg as H.R. Giger first drew it."));

        // ---------------------------------------------------------------- Yautja
        add(CreatureSpec.of("yautja_warrior", YAUTJA).stats(100, 8, 0.3, 8).size(0.9F, 2.4F).xp(300)
                .look("YautjaModel", "yautja.png", 1.0F).sounds("predator")
                .egg(0x6a6040, 0x3a3020).lore("Hunts only worthy prey: Xenomorphs, the armed and the dangerous. Cloaks while it stalks."));
        add(CreatureSpec.of("yautja_berserker", YAUTJA).stats(130, 10, 0.3, 12).size(1.0F, 2.6F).xp(450)
                .look("YautjaModel", "yautja_berserker.png", 1.0F).sounds("predator")
                .egg(0x3a3020, 0x1a1a1a).lore("An outcast that has given up the honour code. Hunts anything."));

        // ---------------------------------------------------------------- humans
        add(CreatureSpec.of("marine", HUMAN).stats(20, 3, 0.3, 6).size(0.6F, 1.8F).xp(5).neutral()
                .look("biped", "marine.png", 1.0F).sounds("marine")
                .egg(0x4a5a3a, 0x2a3a1a).lore("USCM Colonial Marine. On your side - fires on Xenomorphs on sight."));
        add(CreatureSpec.of("combat_synthetic", HUMAN).stats(50, 6, 0.3, 8).size(0.6F, 1.8F).xp(40).neutral()
                .look("biped", "combat_synthetic.png", 1.0F).sounds("marine")
                .egg(0xd8d8d8, 0x2a3a1a).lore("Weyland-Yutani combat synthetic. Fearless, and no use to a facehugger."));

        // ---------------------------------------------------------------- Engineers
        add(CreatureSpec.of("engineer", ENGINEER).stats(160, 8, 0.3, 6).size(0.9F, 2.8F).xp(250).neutral()
                .look("EngineerModel", "engineer_bio.png", 1.25F).sounds("")
                .egg(0xd8d8d0, 0x8a8a80).lore("A Space Jockey out of its suit. Leaves you alone - until you strike it."));
        add(CreatureSpec.of("space_jockey", ENGINEER).stats(160, 8, 0.25, 12).size(0.9F, 2.4F).xp(250).neutral()
                .look("EngineerModel", "engineer.png", 1.0F).sounds("")
                .egg(0x5a5a50, 0x2a2a20).lore("An Engineer in its biomechanical suit."));

        // ---------------------------------------------------------------- wildlife and mutants
        beast(CreatureSpec.of("goo_mutant", BEAST).stats(60, 4, 0.25, 2).size(0.8F, 2.0F).xp(150)
                .look("GooMutantModel", "goomutant.png", 1.0F).egg(0x1a1a1a, 0x3a3a50).lore("What the black goo leaves of a man."));
        beast(CreatureSpec.of("babyhead", BEAST).stats(50, 4, 0.3, 2).size(1.0F, 2.75F).xp(150)
                .look("BabyheadModel", "babyhead.png", 1.0F).egg(0xd8b8a8, 0x6a4a3a).lore("A black goo mutation."));
        beast(CreatureSpec.of("aethon", BEAST).stats(400, 8, 0.26, 8).size(3.0F, 4.0F).xp(500).can(FLY, BOSS, FIREPROOF)
                .look("AethonModel", "aethon.png", 1.0F).egg(0x5a1a10, 0xd88a20).lore("A great winged beast of the Engineers' worlds."));
        beast(CreatureSpec.of("scelemur", BEAST).stats(15, 2, 0.32, 0).size(0.5F, 1.0F).xp(5).can(CLIMB).neutral()
                .look("VardaMonkeyModel", "vardamonkey.png", 1.0F).egg(0x6a5a4a, 0x3a2a1a).lore("A Varda tree-climber. Bites if you corner it."));
        beast(CreatureSpec.of("ursuidae", BEAST).stats(30, 4, 0.3, 4).size(1.5F, 1.75F).xp(10).neutral()
                .look("UrsuidaeModel", "vardaboar.png", 1.0F).egg(0x4a3a2a, 0x8a7a5a).lore("A Varda boar. Leave it be."));
        beast(CreatureSpec.of("deacon_shark", BEAST).stats(30, 5, 0.3, 2).size(2.0F, 1.0F).xp(10).can(AQUATIC)
                .look("DeaconSharkModel", "deacon_shark.png", 1.0F).egg(0x3a4a5a, 0x9aaabb).lore("Hunts the seas of Varda."));
        beast(CreatureSpec.of("hammerpede", BEAST).stats(14, 2, 0.31, 0).size(0.5F, 0.5F).xp(16).can(AQUATIC)
                .look("HammerpedeModel", "hammerpede.png", 0.65F).sounds("chestburster")
                .egg(0xd8d8c8, 0x8a8a7a).lore("Lives in black goo. Its bite is venomous."));
        beast(CreatureSpec.of("yautja_mutant", BEAST).stats(180, 6, 0.28, 6).size(1.25F, 2.75F).xp(150)
                .look("MutantYautjaModel", "yautjamutant.png", 1.0F).egg(0x3a3a2a, 0x1a1a3a).lore("A Yautja ruined by the black goo."));
        beast(CreatureSpec.of("predator_hound", BEAST).stats(50, 5, 0.32, 4).size(1.5F, 1.75F).xp(20).neutral()
                .look("PredatorHoundModel", "predatorhound.png", 1.0F).egg(0x5a4a3a, 0x2a1a1a)
                .lore("A Yautja hunting beast. Tracks down Xenomorphs."));
    }

    private static void add(CreatureSpec.Builder builder)
    {
        CreatureSpec spec = builder.build();
        ALL.put(spec.id(), spec);
    }

    private static void beast(CreatureSpec.Builder builder)
    {
        add(builder.sounds(""));
    }

    private static void burster(String id, String adult, String model, String texture, float scale, int color, String lore)
    {
        add(CreatureSpec.of(id, BURSTER).stats(14, 2, 0.34, 0).size(0.6F, 0.5F).xp(16).can(CLIMB).matures(adult)
                .look(model, texture, scale).sounds("chestburster").egg(0xc8a090, color).lore(lore));
    }

    @Nullable
    public static CreatureSpec get(EntityType<?> type)
    {
        return ALL.get(BuiltInRegistries.ENTITY_TYPE.getKey(type).getPath());
    }

    /** Attributes from the catalog entry. */
    public static AttributeSupplier.Builder attributes(CreatureSpec spec)
    {
        AttributeSupplier.Builder builder = Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, spec.health())
                .add(Attributes.MOVEMENT_SPEED, spec.speed())
                .add(Attributes.ATTACK_DAMAGE, Math.max(0.5, spec.damage()))
                .add(Attributes.ARMOR, spec.armor())
                .add(Attributes.FOLLOW_RANGE, spec.family() == Family.YAUTJA ? 64.0 : 48.0)
                .add(Attributes.FLYING_SPEED, spec.speed() * 1.6)
                .add(Attributes.KNOCKBACK_RESISTANCE, Math.min(1.0, spec.health() / 200.0))
                .add(Attributes.SAFE_FALL_DISTANCE, spec.family() == Family.XENOMORPH || spec.family() == Family.YAUTJA ? 32.0 : 3.0);
        return builder;
    }

    private Creatures()
    {
    }
}
