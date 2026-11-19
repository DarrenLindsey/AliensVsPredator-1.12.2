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

    static
    {
        var client = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT = client.getLeft();
        CLIENT_SPEC = client.getRight();
        var server = new ModConfigSpec.Builder().configure(Server::new);
        SERVER = server.getLeft();
        SERVER_SPEC = server.getRight();
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
        public final ModConfigSpec.BooleanValue lithiumReactsWithWater;

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
                    .defineInRange("transformerRate", 4096, 1, 1_000_000);
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
