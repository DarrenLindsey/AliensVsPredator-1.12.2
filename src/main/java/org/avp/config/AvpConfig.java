package org.avp.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * All of the mod's settings. They're saved in the game's "config" folder and can
 * be changed in game: Mods -> AliensVsPredator -> Config.
 *
 * CLIENT settings only change what you see on your own computer.
 * SERVER settings change gameplay. They're saved per world (in
 * saves/<world>/serverconfig) and a multiplayer server's settings are sent to players.
 *
 * To add a setting: add a field in the right section below, give it a name and a
 * tooltip in lang/en_us.json ("avp.configuration.<name>" and "...<name>.tooltip"),
 * then read it with AvpConfig.server(AvpConfig.SERVER.<name>) (or AvpConfig.client(...)) where it's
 * used - never call .get() directly, because world settings aren't loaded until a world is open.
 */
public final class AvpConfig
{
    public static final Client CLIENT;
    public static final ModConfigSpec CLIENT_SPEC;
    public static final Server SERVER;
    public static final ModConfigSpec SERVER_SPEC;
    public static final Common COMMON;
    public static final ModConfigSpec COMMON_SPEC;

    static
    {
        var client = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT = client.getLeft();
        CLIENT_SPEC = client.getRight();
        var server = new ModConfigSpec.Builder().configure(Server::new);
        SERVER = server.getLeft();
        SERVER_SPEC = server.getRight();
        var common = new ModConfigSpec.Builder().configure(Common::new);
        COMMON = common.getLeft();
        COMMON_SPEC = common.getRight();
    }

    /**
     * Settings read before any world exists (avp-common.toml), such as world generation.
     * Changing them needs a restart; they only affect chunks generated afterwards.
     */
    public static final class Common
    {
        public final ModConfigSpec.BooleanValue generateOres;
        /** Per ore, by the name used in data/avp/neoforge/biome_modifier/ore_<name>.json. */
        public final java.util.Map<String, ModConfigSpec.BooleanValue> ores = new java.util.LinkedHashMap<>();
        /** Natural spawning per creature, by the name used in data/avp/neoforge/biome_modifier/spawn_<name>.json. */
        public final java.util.Map<String, ModConfigSpec.BooleanValue> spawns = new java.util.LinkedHashMap<>();
        public final ModConfigSpec.BooleanValue naturalSpawns;

        Common(ModConfigSpec.Builder b)
        {
            b.push("worldgen");
            generateOres = b
                    .comment("Generate AvP ores in new chunks. Needs a restart. (Vein size, height and rarity are in the data pack files data/avp/worldgen/.)")
                    .define("generateOres", true);
            String[][] list = {
                    { "bauxite", "Bauxite (aluminum) ore, most overworld biomes." },
                    { "copper", "AvP copper ore (vanilla copper ingots work in all AvP recipes too)." },
                    { "silicon", "Silicon ore, deep underground." },
                    { "monazite", "Monazite ore (neodymium), rare and deep." },
                    { "lithium", "Lithium ore, under oceans, beaches and rivers." },
                    { "cobalt", "Cobalt ore, under jungles." } };
            for (String[] ore : list)
            {
                ores.put(ore[0], b.comment(ore[1]).define("ore_" + ore[0], true));
            }
            b.pop();

            b.push("spawning");
            naturalSpawns = b
                    .comment("AvP creatures spawn naturally in the world. Needs a restart. (Biomes and weights: data/avp/neoforge/biome_modifier/spawn_*.json.)")
                    .define("naturalSpawns", true);
            String[][] creatures = { { "facehugger", "Facehuggers in jungles, swamps and dark forests (at night / in the dark)." },
                    { "drone", "Drones (adult Xenomorphs) in jungles, swamps and dark forests (in the dark)." },
                    { "warrior", "Warriors in jungles, swamps and dark forests (in the dark)." },
                    { "runner_drone", "Runners in savannas and badlands (in the dark)." },
                    { "nauticomorph", "Nauticomorphs in oceans." },
                    { "yautja_warrior", "Yautja hunting parties in jungles (rare)." },
                    { "marine", "Colonial Marine patrols on plains (rare)." },
                    { "engineer", "Engineers in deserts and badlands (very rare)." },
                    { "trilobite", "Trilobites in deep dark caves (rare)." },
                    { "deacon_shark", "Deacon sharks in deep oceans." },
                    { "scelemur", "Scelemurs (Varda monkeys) in jungles." },
                    { "ursuidae", "Ursuidae (Varda boars) in taigas and forests." },
                    { "hammerpede", "Hammerpedes in swamps and lush caves." },
                    { "goo_mutant", "Black goo mutants in the deep dark and badlands (in the dark)." } };
            for (String[] creature : creatures)
            {
                spawns.put(creature[0], b.comment(creature[1]).define("spawn_" + creature[0], true));
            }
            b.pop();
        }
    }

    public static final class Client
    {
        public final ModConfigSpec.DoubleValue fernBrightness;
        public final ModConfigSpec.BooleanValue showCopiedTextures;
        public final ModConfigSpec.DoubleValue trophySize;

        Client(ModConfigSpec.Builder b)
        {
            b.push("rendering");
            fernBrightness = b
                    .comment("Multiplies the colour of Paradisian ferns. 1.0 = the original 1.12.2 colours; raise it if ferns look too dark.")
                    .defineInRange("fernBrightness", 1.0, 0.1, 10.0);
            showCopiedTextures = b
                    .comment("Show the copied block texture on slopes, corners, ridges and pyramids. Turn off to always see the plain texture.")
                    .define("showCopiedTextures", true);
            trophySize = b
                    .comment("Size of placed skulls, biomasks and heads. 1.0 = normal; 2.0 = twice as big.")
                    .defineInRange("trophySize", 1.0, 0.25, 3.0);
            b.pop();
        }
    }

    public static final class Server
    {
        public final ModConfigSpec.BooleanValue shapesCanCopyBlocks;
        public final ModConfigSpec.BooleanValue trophiesCanBeTipped;
        public final ModConfigSpec.BooleanValue lockersCanHide;
        public final ModConfigSpec.BooleanValue machinesNeedPower;
        public final ModConfigSpec.BooleanValue redstonePowersMachines;
        public final ModConfigSpec.IntValue lightEnergyPerTick;
        public final ModConfigSpec.IntValue cryostasisTubeEnergyPerTick;
        public final ModConfigSpec.IntValue medpodEnergyPerTick;
        public final ModConfigSpec.IntValue blastDoorEnergyPerTick;
        public final ModConfigSpec.IntValue blastDoorAutolockTicks;
        public final ModConfigSpec.BooleanValue blastDoorsCanBePried;
        public final ModConfigSpec.IntValue doorSensorRange;
        public final ModConfigSpec.IntValue doorAutoCloseTicks;
        public final ModConfigSpec.IntValue medpodHealInterval;
        public final ModConfigSpec.IntValue medpodHealAmount;
        public final ModConfigSpec.BooleanValue medpodCuresEffects;
        public final ModConfigSpec.BooleanValue medpodBoost;
        public final ModConfigSpec.IntValue medpodReentryCooldown;
        public final ModConfigSpec.DoubleValue medpodEjectStrength;
        public final ModConfigSpec.IntValue medpodBoostMinTicks;
        public final ModConfigSpec.IntValue medpodRegenerationTicks;
        public final ModConfigSpec.IntValue medpodAbsorptionTicks;

        public final ModConfigSpec.IntValue solarPanelOutput;
        public final ModConfigSpec.IntValue generatorOutput;
        public final ModConfigSpec.DoubleValue generatorFuelMultiplier;
        public final ModConfigSpec.IntValue repulsionOutputPerMagnet;
        public final ModConfigSpec.IntValue transformerRate;
        public final ModConfigSpec.IntValue stepdownTransformerRate;
        public final ModConfigSpec.IntValue powercellCapacity;
        public final ModConfigSpec.IntValue powercellRate;
        public final ModConfigSpec.IntValue maxCableNetworkSize;
        public final ModConfigSpec.IntValue redstoneEmitterEnergyPerTick;
        public final ModConfigSpec.IntValue satelliteDishEnergyPerTick;
        public final ModConfigSpec.IntValue terminalEnergyPerTick;
        public final ModConfigSpec.IntValue teslaCoilEnergyPerTick;
        public final ModConfigSpec.IntValue teslaCoilEnergyPerZap;
        public final ModConfigSpec.DoubleValue teslaCoilDamage;
        public final ModConfigSpec.IntValue teslaCoilRange;
        public final ModConfigSpec.IntValue teslaCoilCooldown;
        public final ModConfigSpec.BooleanValue teslaCoilTargetsPlayers;
        public final ModConfigSpec.BooleanValue teslaCoilSetsFire;
        public final ModConfigSpec.IntValue assemblerEnergyPerItem;
        public final ModConfigSpec.IntValue turretEnergyPerTick;
        public final ModConfigSpec.IntValue turretRange;
        public final ModConfigSpec.IntValue turretRangePerSensor;
        public final ModConfigSpec.DoubleValue turretDamage;
        public final ModConfigSpec.IntValue turretFireInterval;
        public final ModConfigSpec.IntValue turretRoundsPerBox;
        public final ModConfigSpec.IntValue turretTurnSpeed;
        public final ModConfigSpec.BooleanValue turretBypassesInvulnerability;
        public final ModConfigSpec.BooleanValue lithiumReactsWithWater;
        public final ModConfigSpec.IntValue facehuggerAttachTicks;
        public final ModConfigSpec.IntValue facehuggerLifetimeAfter;
        public final ModConfigSpec.IntValue facehuggerHelmetMelt;
        public final ModConfigSpec.DoubleValue acidBloodDamage;
        public final ModConfigSpec.BooleanValue facehuggersTargetPlayers;
        public final ModConfigSpec.IntValue embryoGestationTicks;
        public final ModConfigSpec.BooleanValue embryoSymptoms;
        public final ModConfigSpec.BooleanValue medpodRemovesEmbryo;
        public final ModConfigSpec.IntValue chestbursterMatureTicks;
        public final ModConfigSpec.IntValue emptyOvamorphLifetime;
        public final ModConfigSpec.IntValue cryotubeHarvestTicks;
        public final ModConfigSpec.IntValue cryotubeThawTicks;
        public final ModConfigSpec.BooleanValue filmAccurateSizes;
        public final ModConfigSpec.DoubleValue xenoAcidSplashDamage;
        public final ModConfigSpec.IntValue xenoAcidWeaponDamage;
        public final ModConfigSpec.IntValue droneMatureTicks;
        public final ModConfigSpec.IntValue queenEggTicks;
        public final ModConfigSpec.BooleanValue hives;
        public final ModConfigSpec.IntValue hiveSpacing;
        public final ModConfigSpec.IntValue hiveRadius;
        public final ModConfigSpec.DoubleValue resinBuildSpeed;
        public final ModConfigSpec.IntValue hiveMaxDugBlocks;
        public final ModConfigSpec.DoubleValue resinCreepRate;
        public final ModConfigSpec.IntValue hiveBuilders;
        public final ModConfigSpec.IntValue hiveCoaters;
        public final ModConfigSpec.DoubleValue foundingDigSpeed;
        public final ModConfigSpec.BooleanValue resinCoversBuiltBlocks;
        public final ModConfigSpec.BooleanValue xenosCocoonHosts;
        public final ModConfigSpec.BooleanValue xenosCocoonPlayers;
        public final ModConfigSpec.IntValue cocoonStruggles;
        public final ModConfigSpec.BooleanValue hiveLarder;
        public final ModConfigSpec.BooleanValue royalSuccession;
        public final ModConfigSpec.BooleanValue eggmorphing;
        public final ModConfigSpec.IntValue eggmorphTicks;
        public final ModConfigSpec.BooleanValue yautjaHonourCode;
        public final ModConfigSpec.BooleanValue biomaskNightVision;
        public final ModConfigSpec.BooleanValue grenadesBreakBlocks;
        public final ModConfigSpec.IntValue powerlineCapacity;
        public final ModConfigSpec.IntValue joeBattery;
        public final ModConfigSpec.IntValue joeChargeRate;
        public final ModConfigSpec.BooleanValue joeNeedsPower;
        public final ModConfigSpec.BooleanValue joeReports;
        public final ModConfigSpec.BooleanValue joeChatter;
        public final ModConfigSpec.BooleanValue joesMalfunction;
        public final ModConfigSpec.IntValue joeMalfunctionChance;
        public final ModConfigSpec.BooleanValue joesFightXenos;
        public final ModConfigSpec.BooleanValue xenosIgnoreJoes;
        public final ModConfigSpec.BooleanValue joeChunkLoading;
        public final ModConfigSpec.BooleanValue joeTerminalNeedsPower;
        public final ModConfigSpec.IntValue joeServerPower;
        public final ModConfigSpec.IntValue highVoltageMultiplier;
        public final ModConfigSpec.IntValue lowVoltageLossPer16;
        public final ModConfigSpec.BooleanValue highVoltageBurnsDevices;
        public final ModConfigSpec.BooleanValue powerlinesBurnOut;
        public final ModConfigSpec.BooleanValue xenosCutPower;
        public final ModConfigSpec.IntValue reactorOutput;
        public final ModConfigSpec.IntValue processorPower;
        public final ModConfigSpec.IntValue processorRadius;
        public final ModConfigSpec.BooleanValue processorMeltdown;
        public final ModConfigSpec.BooleanValue acheronUnbreathable;
        public final ModConfigSpec.BooleanValue reactorMeltdown;
        public final ModConfigSpec.BooleanValue blackGooMutation;
        public final ModConfigSpec.DoubleValue ampuleLeakChance;
        public final ModConfigSpec.BooleanValue selfDestruct;
        public final ModConfigSpec.DoubleValue selfDestructPower;
        public final ModConfigSpec.BooleanValue flamethrowerIgnites;
        public final ModConfigSpec.BooleanValue helmetLamp;
        public final ModConfigSpec.BooleanValue yautjaCloak;
        public final ModConfigSpec.BooleanValue marinesAlliedToPlayers;
        public final ModConfigSpec.DoubleValue marineRifleDamage;
        public final ModConfigSpec.IntValue xenoHungerDrainTicks;
        public final ModConfigSpec.IntValue xenoHungryBelow;
        public final ModConfigSpec.BooleanValue xenosAlwaysHuntPlayers;
        public final ModConfigSpec.BooleanValue xenosStarve;
        public final ModConfigSpec.BooleanValue smartXenomorphs;
        public final ModConfigSpec.IntValue xenoHearingRange;
        public final ModConfigSpec.IntValue xenoSightRange;
        public final ModConfigSpec.BooleanValue xenosRetreatWhenHurt;
        public final ModConfigSpec.BooleanValue xenosRegenerate;
        public final ModConfigSpec.BooleanValue xenosBreakLights;
        public final ModConfigSpec.BooleanValue xenosBreakDoors;
        public final ModConfigSpec.BooleanValue xenosLearnTurrets;
        public final ModConfigSpec.BooleanValue xenosAcidBreach;
        public final ModConfigSpec.BooleanValue xenosBreakGlass;
        public final ModConfigSpec.BooleanValue xenosCrawl;
        public final ModConfigSpec.BooleanValue xenoDebugLabels;
        public final ModConfigSpec.BooleanValue xenosDigThroughBlocks;
        public final ModConfigSpec.DoubleValue xenoDigMaxHardness;
        public final ModConfigSpec.DoubleValue xenoDigSpeed;
        public final ModConfigSpec.BooleanValue xenoPacks;
        public final ModConfigSpec.IntValue xenoPackRange;
        public final ModConfigSpec.IntValue acidMeltAmount;
        public final ModConfigSpec.IntValue acidBreachInterval;

        Server(ModConfigSpec.Builder b)
        {
            b.push("blocks");
            shapesCanCopyBlocks = b
                    .comment("Players can right-click slopes/corners/ridges/pyramids with a block to copy its look.")
                    .define("shapesCanCopyBlocks", true);
            trophiesCanBeTipped = b
                    .comment("Players can right-click trophy heads that support it (the Giger alien head) to lay them flat or tilt them.")
                    .define("trophiesCanBeTipped", true);
            lockersCanHide = b
                    .comment("Players can hide inside lockers: sneak + right-click with an empty hand to get in, sneak to get out.")
                    .define("lockersCanHide", true);
            b.pop();

            b.push("power");
            machinesNeedPower = b
                    .comment("Lights and machines only work when powered. Turn off to have them always on.")
                    .define("machinesNeedPower", true);
            redstonePowersMachines = b
                    .comment("A redstone signal also powers lights and machines (no FE needed).")
                    .define("redstonePowersMachines", true);
            lightEnergyPerTick = b
                    .comment("FE (Forge Energy) a light panel or fluorescent light uses per tick while on.")
                    .defineInRange("lightEnergyPerTick", 2, 0, 10_000);
            cryostasisTubeEnergyPerTick = b
                    .comment("FE a cryostasis tube uses per tick to keep its contents frozen.")
                    .defineInRange("cryostasisTubeEnergyPerTick", 10, 0, 10_000);
            medpodEnergyPerTick = b
                    .comment("FE a medpod uses per tick while powered.")
                    .defineInRange("medpodEnergyPerTick", 20, 0, 10_000);
            blastDoorEnergyPerTick = b
                    .comment("FE a blast door uses per tick while powered.")
                    .defineInRange("blastDoorEnergyPerTick", 5, 0, 10_000);
            redstoneEmitterEnergyPerTick = b
                    .comment("FE a redstone emitter uses per tick while it gives a redstone signal.")
                    .defineInRange("redstoneEmitterEnergyPerTick", 1, 0, 10_000);
            satelliteDishEnergyPerTick = b
                    .comment("FE a satellite dish uses per tick while powered.")
                    .defineInRange("satelliteDishEnergyPerTick", 8, 0, 10_000);
            terminalEnergyPerTick = b
                    .comment("FE a terminal uses per tick while its screen is on.")
                    .defineInRange("terminalEnergyPerTick", 2, 0, 10_000);
            b.pop();

            b.push("powerNetwork");
            solarPanelOutput = b
                    .comment("FE per tick a solar panel makes at noon in clear weather (less in the morning, evening and rain; none at night).")
                    .defineInRange("solarPanelOutput", 40, 0, 100_000);
            generatorOutput = b
                    .comment("FE per tick the Universal Generator makes while burning fuel.")
                    .defineInRange("generatorOutput", 80, 0, 100_000);
            generatorFuelMultiplier = b
                    .comment("How long fuel lasts in the Universal Generator compared to a furnace (1.0 = same as a furnace).")
                    .defineInRange("generatorFuelMultiplier", 1.0, 0.01, 100.0);
            repulsionOutputPerMagnet = b
                    .comment("FE per tick the Repulsion Generator makes for each neodymium magnet inside (it holds up to 8).")
                    .defineInRange("repulsionOutputPerMagnet", 10, 0, 100_000);
            transformerRate = b
                    .comment("Most FE per tick a transformer passes from its back to its front.")
                    .defineInRange("transformerRate", 16384, 1, 1_000_000);
            stepdownTransformerRate = b
                    .comment("Most FE per tick a step-down transformer passes (use it to limit how much power flows).")
                    .defineInRange("stepdownTransformerRate", 256, 1, 1_000_000);
            powercellCapacity = b
                    .comment("How much FE a power cell stores.")
                    .defineInRange("powercellCapacity", 1_000_000, 1000, 1_000_000_000);
            powercellRate = b
                    .comment("Most FE per tick a power cell gives out.")
                    .defineInRange("powercellRate", 1024, 1, 1_000_000);
            maxCableNetworkSize = b
                    .comment("Largest number of connected powerlines that still carry power (limits lag from huge networks).")
                    .defineInRange("maxCableNetworkSize", 2048, 16, 65_536);
            b.pop();

            b.push("teslaCoil");
            teslaCoilEnergyPerTick = b
                    .comment("FE a tesla coil uses per tick while switched on.")
                    .defineInRange("teslaCoilEnergyPerTick", 4, 0, 10_000);
            teslaCoilEnergyPerZap = b
                    .comment("Extra FE a tesla coil uses each time it strikes a creature.")
                    .defineInRange("teslaCoilEnergyPerZap", 400, 0, 1_000_000);
            teslaCoilDamage = b
                    .comment("Damage per strike (2 = one heart).")
                    .defineInRange("teslaCoilDamage", 6.0, 0.0, 1000.0);
            teslaCoilRange = b
                    .comment("How far (in blocks) a tesla coil can strike.")
                    .defineInRange("teslaCoilRange", 8, 1, 32);
            teslaCoilCooldown = b
                    .comment("Ticks between strikes (20 = 1 second).")
                    .defineInRange("teslaCoilCooldown", 20, 1, 1200);
            teslaCoilTargetsPlayers = b
                    .comment("Tesla coils also strike players (never players in creative or spectator mode).")
                    .define("teslaCoilTargetsPlayers", false);
            teslaCoilSetsFire = b
                    .comment("Strikes set the target on fire for a few seconds.")
                    .define("teslaCoilSetsFire", true);
            b.pop();

            b.push("turret");
            turretEnergyPerTick = b
                    .comment("FE a sentry turret uses per tick while switched on.")
                    .defineInRange("turretEnergyPerTick", 10, 0, 10_000);
            turretRange = b
                    .comment("How far (in blocks) a sentry turret sees and shoots.")
                    .defineInRange("turretRange", 24, 4, 64);
            turretRangePerSensor = b
                    .comment("Extra range for each Motion Sensor Array in a turret's upgrade slots.")
                    .defineInRange("turretRangePerSensor", 8, 0, 64);
            turretDamage = b
                    .comment("Damage per shot (2 = one heart).")
                    .defineInRange("turretDamage", 2.0, 0.0, 1000.0);
            turretFireInterval = b
                    .comment("Ticks between shots (2 = 10 shots a second).")
                    .defineInRange("turretFireInterval", 2, 1, 100);
            turretRoundsPerBox = b
                    .comment("Rounds in one Sentry Ammo Box.")
                    .defineInRange("turretRoundsPerBox", 100, 1, 10_000);
            turretTurnSpeed = b
                    .comment("Degrees per tick the turret turns (each Processor upgrade adds half of this again).")
                    .defineInRange("turretTurnSpeed", 8, 1, 180);
            turretBypassesInvulnerability = b
                    .comment("Every shot lands, even during the short invulnerability after being hit (like 1.12.2).")
                    .define("turretBypassesInvulnerability", true);
            b.pop();

            b.push("parasites");
            facehuggerAttachTicks = b
                    .comment("How long a facehugger stays on its host's face (20 ticks = 1 second; 1.12.2: 2 minutes).")
                    .defineInRange("facehuggerAttachTicks", 2400, 20, 72_000);
            facehuggerLifetimeAfter = b
                    .comment("How long a spent facehugger lies on its back before it dies (ticks).")
                    .defineInRange("facehuggerLifetimeAfter", 6000, 20, 72_000);
            facehuggerHelmetMelt = b
                    .comment("Durability a facehugger's acid takes off a helmet each time it lunges at a player's face. It only latches on once the helmet breaks.")
                    .defineInRange("facehuggerHelmetMelt", 60, 0, 10_000);
            acidBloodDamage = b
                    .comment("Damage to the host when a facehugger attached to their face is hurt (its acid blood splashes). 0 = off.")
                    .defineInRange("acidBloodDamage", 4.0, 0.0, 1000.0);
            facehuggersTargetPlayers = b
                    .comment("Facehuggers go after players (players in creative or spectator mode are never targeted).")
                    .define("facehuggersTargetPlayers", true);
            embryoGestationTicks = b
                    .comment("How long an embryo grows before it bursts out (20 ticks = 1 second; 1.12.2: 5 minutes).")
                    .defineInRange("embryoGestationTicks", 6000, 200, 1_728_000);
            embryoSymptoms = b
                    .comment("Hosts feel the embryo near the end: nausea, weakness and hunger.")
                    .define("embryoSymptoms", true);
            medpodRemovesEmbryo = b
                    .comment("A powered, closed medpod surgically removes an embryo - unless it's nearly fully grown.")
                    .define("medpodRemovesEmbryo", true);
            chestbursterMatureTicks = b
                    .comment("How long a chestburster takes to grow into a Drone on its own (1.12.2: 15 minutes). 64 royal jelly does it at once.")
                    .defineInRange("chestbursterMatureTicks", 18_000, 200, 1_728_000);
            emptyOvamorphLifetime = b
                    .comment("How long an empty (hatched) egg stays before it withers away (ticks; 1.12.2: 5 minutes).")
                    .defineInRange("emptyOvamorphLifetime", 6000, 20, 1_728_000);
            cryotubeHarvestTicks = b
                    .comment("A frozen specimen in a powered cryostasis tube gives one royal jelly this often (ticks; 6000 = 5 minutes).")
                    .defineInRange("cryotubeHarvestTicks", 6000, 20, 1_728_000);
            cryotubeThawTicks = b
                    .comment("How long a cryostasis tube can be without power before the specimen thaws and breaks out (ticks).")
                    .defineInRange("cryotubeThawTicks", 600, 20, 72_000);
            b.pop();

            b.push("creatures");
            filmAccurateSizes = b
                    .comment("Creatures spawn at their size in the films (bigger adult Xenomorphs) instead of the classic 1.12.2 size. Affects newly spawned creatures.")
                    .define("filmAccurateSizes", false);
            xenoAcidSplashDamage = b
                    .comment("Damage acid blood does to whoever hits a Xenomorph up close. 0 = off.")
                    .defineInRange("xenoAcidSplashDamage", 2.0, 0.0, 1000.0);
            xenoAcidWeaponDamage = b
                    .comment("Durability acid blood eats off the weapon that hit a Xenomorph. 0 = off.")
                    .defineInRange("xenoAcidWeaponDamage", 3, 0, 10_000);
            droneMatureTicks = b
                    .comment("How long a Drone takes to mature into a Warrior. Small forms (Runner, Deacon) take the same; big ones (Praetorian, Crusher) three times as long. A Queen only comes from royal jelly.")
                    .defineInRange("droneMatureTicks", 72_000, 200, 1_728_000);
            queenEggTicks = b
                    .comment("A well-fed Queen lays an egg this often (ticks) while she isn't fighting, up to 12 nearby.")
                    .defineInRange("queenEggTicks", 6_000, 100, 1_728_000);
            hives = b
                    .comment("Xenomorphs found and build hives: Drones dig down to deepslate, coat the hive in resin and bring hosts back alive.")
                    .define("hives", true);
            hiveSpacing = b
                    .comment("Only one hive within this many blocks. Xenomorphs inside this range join it instead of starting another.")
                    .defineInRange("hiveSpacing", 256, 32, 4096);
            hiveRadius = b
                    .comment("How far from its core a hive with a Queen digs its rooms and galleries (a hive without a Queen stays a small nest, up to 16).")
                    .defineInRange("hiveRadius", 24, 6, 64);
            hiveBuilders = b
                    .comment("Drones a founded hive keeps as builders (hivestone floors, bridges, seals, roofs).")
                    .defineInRange("hiveBuilders", 2, 0, 32);
            hiveCoaters = b
                    .comment("Drones a founded hive keeps as wall coaters (climb walls and ceilings spraying resin). The rest are workers: digging rooms, resin, hunting.")
                    .defineInRange("hiveCoaters", 2, 0, 32);
            resinCreepRate = b
                    .comment("How fast resin in a living hive spreads by itself onto the blocks next to it (0 = never, 1 = normal).")
                    .defineInRange("resinCreepRate", 1.0, 0.0, 20.0);
            hiveMaxDugBlocks = b
                    .comment("Most blocks one hive may dig out for its rooms and galleries (the founding tunnel and chamber don't count).")
                    .defineInRange("hiveMaxDugBlocks", 6_000, 0, 1_000_000);
            foundingDigSpeed = b
                    .comment("How much faster a Drone digs the founding tunnel than normal digging - an urge it can't resist (it doesn't hunt or get hungry while digging).")
                    .defineInRange("foundingDigSpeed", 3.0, 0.1, 50.0);
            resinBuildSpeed = b
                    .comment("How fast Drones spread resin (1.0 = normal, 0 = never).")
                    .defineInRange("resinBuildSpeed", 1.0, 0.0, 20.0);
            resinCoversBuiltBlocks = b
                    .comment("Resin may coat any plain solid block, including player-built ones. Off: only natural blocks (avp:resin_coverable).")
                    .define("resinCoversBuiltBlocks", false);
            xenosCocoonHosts = b
                    .comment("Drones subdue animals and villagers instead of killing them, carry them back and cocoon them in the hive for the eggs.")
                    .define("xenosCocoonHosts", true);
            xenosCocoonPlayers = b
                    .comment("Drones capture worn-down players too: carried to the hive and held in resin on the wall until they struggle free, are cut free, or an egg opens.")
                    .define("xenosCocoonPlayers", true);
            cocoonStruggles = b
                    .comment("How many times a cocooned player must press sneak to break free.")
                    .defineInRange("cocoonStruggles", 30, 1, 1000);
            hiveLarder = b
                    .comment("Drones also bring back prey that can't carry an embryo, as food: hungry hive members feed on it instead of going out to hunt.")
                    .define("hiveLarder", true);
            royalSuccession = b
                    .comment("A hive without a Queen picks an heir and feeds it royal jelly from every kill, until it grows into a Queen.")
                    .define("royalSuccession", true);
            eggmorphing = b
                    .comment("Cocooned hosts slowly turn into eggs (the deleted scene from Alien), so a hive can spread without a Queen.")
                    .define("eggmorphing", true);
            eggmorphTicks = b
                    .comment("How long a cocooned host takes to turn into an egg (ticks; 24000 = one day).")
                    .defineInRange("eggmorphTicks", 24_000, 200, 1_728_000);
            selfDestruct = b
                    .comment("The wristbracer's self-destruct can be armed (K, twice).")
                    .define("selfDestruct", true);
            selfDestructPower = b
                    .comment("Size of the self-destruct explosion (TNT is 4). Breaks blocks if mobGriefing is on.")
                    .defineInRange("selfDestructPower", 8.0, 1.0, 30.0);
            blackGooMutation = b
                    .comment("Black goo mutates creatures in it (villagers -> goo mutants, silverfish -> Hammerpedes, livestock -> babyheads...).")
                    .define("blackGooMutation", true);
            ampuleLeakChance = b
                    .comment("Chance an Engineer ampule cracks and leaks black goo each time a phial is filled from it (0 = never).")
                    .defineInRange("ampuleLeakChance", 0.2, 0.0, 1.0);
            joeBattery = b
                    .comment("Working Joe battery size (FE). A Joe uses 1 FE/t standing, 4 walking, 8 fighting.")
                    .defineInRange("joeBattery", 240_000, 1000, 100_000_000);
            joeChargeRate = b
                    .comment("FE per tick a Joe charger gives a Joe.")
                    .defineInRange("joeChargeRate", 600, 1, 1_000_000);
            joeNeedsPower = b
                    .comment("Working Joes run on a battery (and power down when it's empty). Off: they run forever.")
                    .define("joeNeedsPower", true);
            joeReports = b
                    .comment("Working Joes send status messages to their owner over the Seegson network (alarms, faults, job reports).")
                    .define("joeReports", true);
            joeChatter = b
                    .comment("Working Joes now and then say something to people near them.")
                    .define("joeChatter", true);
            joesMalfunction = b
                    .comment("A badly damaged Working Joe may malfunction and attack people (as in Alien: Isolation). Fix it with a processor.")
                    .define("joesMalfunction", true);
            joeMalfunctionChance = b
                    .comment("Percent chance, per hit below 35% health, that a Joe malfunctions.")
                    .defineInRange("joeMalfunctionChance", 15, 0, 100);
            joesFightXenos = b
                    .comment("Joes on security duty also attack Xenomorphs (they mostly lose).")
                    .define("joesFightXenos", false);
            joeTerminalNeedsPower = b
                    .comment("Seegson Terminals (4 FE/t) and Network Servers need power to work.")
                    .define("joeTerminalNeedsPower", true);
            joeServerPower = b
                    .comment("FE per tick a Seegson Network Server draws.")
                    .defineInRange("joeServerPower", 64, 0, 100_000);
            joeChunkLoading = b
                    .comment("Joes with a chunk anchor module keep their chunk loaded (so they work anywhere). Off: the module does nothing.")
                    .define("joeChunkLoading", true);
            xenosIgnoreJoes = b
                    .comment("Xenomorphs ignore Working Joes (nothing alive in them to hunt).")
                    .define("xenosIgnoreJoes", true);
            powerlineCapacity = b
                    .comment("FE per tick one powerline network can carry. Forcing more through it overloads it: it sparks, arcs, and may burn out (a breaker trips instead).")
                    .defineInRange("powerlineCapacity", 4096, 64, 1_000_000);
            highVoltageMultiplier = b
                    .comment("A line fed by a step-up transformer's front is high voltage: it carries this many times more, loses nothing over distance - and fries any device on it that isn't a transformer or power cell. Step it down to use it.")
                    .defineInRange("highVoltageMultiplier", 4, 1, 64);
            lowVoltageLossPer16 = b
                    .comment("Percent of power lost per 16 powerlines on a low-voltage line (up to 50%). 0 = no loss.")
                    .defineInRange("lowVoltageLossPer16", 2, 0, 50);
            highVoltageBurnsDevices = b
                    .comment("Devices left on a high-voltage line can burn out (break) instead of only sparking and refusing the power.")
                    .define("highVoltageBurnsDevices", false);
            powerlinesBurnOut = b
                    .comment("Overloaded powerlines can burn out (a section breaks).")
                    .define("powerlinesBurnOut", true);
            xenosCutPower = b
                    .comment("Xenomorphs tear out powerlines near them to kill the power, as in Aliens.")
                    .define("xenosCutPower", true);
            processorPower = b
                    .comment("FE per tick an atmosphere processor draws while running.")
                    .defineInRange("processorPower", 1024, 0, 1_000_000);
            processorRadius = b
                    .comment("How far (blocks, square) a running atmosphere processor makes the air breathable.")
                    .defineInRange("processorRadius", 64, 8, 512);
            processorMeltdown = b
                    .comment("An atmosphere processor running with no water touching it heats up, and at 100% explodes like Hadley's Hope. Off: it shuts itself down.")
                    .define("processorMeltdown", true);
            acheronUnbreathable = b
                    .comment("Acheron (LV-426) air can't be breathed: you suffocate unless you wear a sealed suit (MK50, pressure suit), a biomask, or stand near a running atmosphere processor.")
                    .define("acheronUnbreathable", true);
            reactorOutput = b
                    .comment("FE per tick a compact fusion reactor makes.")
                    .defineInRange("reactorOutput", 2048, 1, 1_000_000);
            reactorMeltdown = b
                    .comment("A fusion reactor whose power has nowhere to go overheats - and at 100% it melts down (explodes). Off: it just shuts down.")
                    .define("reactorMeltdown", true);
            grenadesBreakBlocks = b
                    .comment("Grenades blow up blocks (off: they only hurt creatures).")
                    .define("grenadesBreakBlocks", false);
            flamethrowerIgnites = b
                    .comment("Flamethrowers set blocks alight where the flames land (they always burn hive resin).")
                    .define("flamethrowerIgnites", true);
            helmetLamp = b
                    .comment("The Colonial Marine helmet's lamp lights up the dark where you look.")
                    .define("helmetLamp", true);
            biomaskNightVision = b
                    .comment("Wearing a Yautja biomask gives night vision (Yautja vision; more vision modes come with the Predator gear).")
                    .define("biomaskNightVision", true);
            yautjaHonourCode = b
                    .comment("Yautja only hunt worthy prey: Xenomorphs, dangerous monsters and armed players. Off: they hunt every player.")
                    .define("yautjaHonourCode", true);
            yautjaCloak = b
                    .comment("Yautja cloak while they stalk (nearly invisible, and other creatures lose track of them).")
                    .define("yautjaCloak", true);
            marinesAlliedToPlayers = b
                    .comment("Colonial Marines and combat synthetics fight alongside players (only turning on one that attacks them).")
                    .define("marinesAlliedToPlayers", true);
            marineRifleDamage = b
                    .comment("Damage of each pulse rifle round a Marine fires (bursts of 3).")
                    .defineInRange("marineRifleDamage", 3.0, 0.0, 1000.0);
            xenoHungerDrainTicks = b
                    .comment("Xenomorphs have a hidden hunger meter (0-100). It drops by 1 this often (ticks; 600 = 30 seconds).")
                    .defineInRange("xenoHungerDrainTicks", 600, 20, 72_000);
            xenoHungryBelow = b
                    .comment("Below this hunger they hunt animals and other creatures for food. Above it they leave them alone unless attacked.")
                    .defineInRange("xenoHungryBelow", 60, 0, 100);
            xenosAlwaysHuntPlayers = b
                    .comment("Xenomorphs go after players whether hungry or not (in the films people are hosts and threats, not food). Off: only when hungry or attacked.")
                    .define("xenosAlwaysHuntPlayers", true);
            xenosStarve = b
                    .comment("Xenomorphs with an empty hunger meter slowly lose health.")
                    .define("xenosStarve", false);
            b.push("behaviour");
            smartXenomorphs = b
                    .comment("Film-style Xenomorph behaviour: senses (sight + hearing), investigating noises, stalking and striking when you look away,",
                            "flanking in packs, retreating to heal, avoiding fire and remembering turrets. Off: simple 'run at the nearest player' AI.")
                    .define("smartXenomorphs", true);
            xenoHearingRange = b
                    .comment("How far (blocks) they hear a sprinting or fighting player. Walking is heard at half this, sneaking at a quarter.")
                    .defineInRange("xenoHearingRange", 20, 0, 64);
            xenoSightRange = b
                    .comment("How far (blocks) they spot a player in plain view. A sneaking player is spotted at a third of this.")
                    .defineInRange("xenoSightRange", 28, 0, 64);
            xenosRetreatWhenHurt = b
                    .comment("Badly hurt (or burning) Xenomorphs break off and pull back into the dark.")
                    .define("xenosRetreatWhenHurt", true);
            xenosRegenerate = b
                    .comment("Out of combat, Xenomorphs slowly heal.")
                    .define("xenosRegenerate", true);
            xenosBreakLights = b
                    .comment("Xenomorphs sometimes smash torches and lanterns near them to make it darker.")
                    .define("xenosBreakLights", true);
            xenosBreakDoors = b
                    .comment("Xenomorphs can smash through wooden doors (iron doors and blast doors stop them).")
                    .define("xenosBreakDoors", true);
            xenosLearnTurrets = b
                    .comment("A Xenomorph shot by a sentry turret remembers it, warns the others nearby, and they keep out of its range for a while.")
                    .define("xenosLearnTurrets", true);
            xenosAcidBreach = b
                    .comment("Xenomorphs stuck behind an iron door wound a packmate so its acid blood melts through (Alien Resurrection).",
                            "Any Xenomorph bleeding next to such a door eats into it too. Which blocks: the avp:acid_meltable block tag.")
                    .define("xenosAcidBreach", true);
            xenosBreakGlass = b
                    .comment("Xenomorphs smash glass (blocks and panes - the avp:xeno_breakable block tag) that stands between them and their prey.")
                    .define("xenosBreakGlass", true);
            xenosCrawl = b
                    .comment("Xenomorphs drop to a crawl (under 1 block tall) to squeeze through vents and 1-block-high gaps.")
                    .define("xenosCrawl", true);
            xenoDebugLabels = b
                    .comment("Show what each Xenomorph is doing above its head (STALKING, INVESTIGATING, ...). For testing.")
                    .define("xenoDebugLabels", false);
            xenosDigThroughBlocks = b
                    .comment("When there's no other way to their prey, Xenomorphs tear through the blocks in the way.")
                    .define("xenosDigThroughBlocks", true);
            xenoDigMaxHardness = b
                    .comment("Hardest block they can tear through (stone 1.5, iron block 5, obsidian 50). Blocks in avp:xeno_unbreakable are always safe.")
                    .defineInRange("xenoDigMaxHardness", 5.0, 0.0, 100.0);
            xenoDigSpeed = b
                    .comment("How fast they tear through blocks (1.0 = about as fast as a player with a stone pickaxe on stone).")
                    .defineInRange("xenoDigSpeed", 1.0, 0.05, 20.0);
            xenoPacks = b
                    .comment("Xenomorphs near each other form packs: a leader, shared targets and knowledge, following the leader, bolder in numbers.")
                    .define("xenoPacks", true);
            xenoPackRange = b
                    .comment("How close (blocks) Xenomorphs must be to join or alert a pack.")
                    .defineInRange("xenoPackRange", 24, 4, 64);
            acidMeltAmount = b
                    .comment("How much acid (damage dealt to bleeding Xenomorphs next to it) melts through a door.")
                    .defineInRange("acidMeltAmount", 12, 1, 1000);
            acidBreachInterval = b
                    .comment("Ticks between the wounds a Xenomorph inflicts on a packmate to melt a door (20 = 1 second).")
                    .defineInRange("acidBreachInterval", 40, 5, 1200);
            b.pop();
            b.pop();

            b.push("crafting");
            assemblerEnergyPerItem = b
                    .comment("FE the Assembler uses for each item it makes (0 = needs no power). Ignored when \"Machines Need Power\" is off.")
                    .defineInRange("assemblerEnergyPerItem", 100, 0, 1_000_000);
            lithiumReactsWithWater = b
                    .comment("Lithium ingots explode when they touch water (carried or dropped).")
                    .define("lithiumReactsWithWater", true);
            b.pop();

            b.push("blastDoors");
            blastDoorAutolockTicks = b
                    .comment("With Auto-Lock on, an unlocked blast door closes and locks again after this many ticks (20 = 1 second).")
                    .defineInRange("blastDoorAutolockTicks", 200, 20, 72_000);
            blastDoorsCanBePried = b
                    .comment("Unpowered blast doors can be forced partly open with a Maintenance Jack.")
                    .define("blastDoorsCanBePried", true);
            doorSensorRange = b
                    .comment("How far (in blocks) a door's sensors reach: Auto-Open and Threat Detection.")
                    .defineInRange("doorSensorRange", 4, 1, 16);
            doorAutoCloseTicks = b
                    .comment("Auto-Open closes the door this many ticks after the last player left (20 = 1 second).")
                    .defineInRange("doorAutoCloseTicks", 60, 10, 1200);
            b.pop();

            b.push("medpod");
            medpodHealInterval = b
                    .comment("Ticks between heals while inside a closed, powered medpod (20 ticks = 1 second).")
                    .defineInRange("medpodHealInterval", 40, 1, 1200);
            medpodHealAmount = b
                    .comment("Health restored each time (2 = one heart).")
                    .defineInRange("medpodHealAmount", 1, 0, 40);
            medpodCuresEffects = b
                    .comment("The medpod also removes harmful effects like poison and wither.")
                    .define("medpodCuresEffects", true);
            medpodReentryCooldown = b
                    .comment("After getting out, the medpod won't take you back in for this many ticks (20 = 1 second).")
                    .defineInRange("medpodReentryCooldown", 20, 0, 1200);
            medpodEjectStrength = b
                    .comment("How hard you're pushed out of the medpod when you get out (0 = no push).")
                    .defineInRange("medpodEjectStrength", 0.3, 0.0, 2.0);
            medpodBoost = b
                    .comment("Getting out after a treatment gives a short golden-apple-style boost (Regeneration II + Absorption).")
                    .define("medpodBoost", true);
            medpodBoostMinTicks = b
                    .comment("How long you must stay in a closed, powered medpod to get the boost (20 ticks = 1 second).")
                    .defineInRange("medpodBoostMinTicks", 100, 0, 12_000);
            medpodRegenerationTicks = b
                    .comment("How long the Regeneration II lasts (golden apple: 100 = 5 seconds).")
                    .defineInRange("medpodRegenerationTicks", 100, 0, 12_000);
            medpodAbsorptionTicks = b
                    .comment("How long the Absorption lasts (golden apple: 2400 = 2 minutes).")
                    .defineInRange("medpodAbsorptionTicks", 600, 0, 12_000);
            b.pop();
        }
    }

    /**
     * Reads a setting safely. World (SERVER) settings only exist once a world is open, and
     * Minecraft asks blocks some questions (like their collision shape) earlier than that.
     * Before the settings are loaded this returns the setting's default instead of crashing.
     */
    public static <T> T get(ModConfigSpec spec, ModConfigSpec.ConfigValue<T> value)
    {
        return spec.isLoaded() ? value.get() : value.getDefault();
    }

    public static boolean server(ModConfigSpec.BooleanValue value)
    {
        return get(SERVER_SPEC, value);
    }

    public static boolean common(ModConfigSpec.BooleanValue value)
    {
        return get(COMMON_SPEC, value);
    }

    public static boolean client(ModConfigSpec.BooleanValue value)
    {
        return get(CLIENT_SPEC, value);
    }

    public static double server(ModConfigSpec.DoubleValue value)
    {
        return get(SERVER_SPEC, value);
    }

    public static int server(ModConfigSpec.IntValue value)
    {
        return get(SERVER_SPEC, value);
    }

    public static double client(ModConfigSpec.DoubleValue value)
    {
        return get(CLIENT_SPEC, value);
    }

    private AvpConfig()
    {
    }
}
