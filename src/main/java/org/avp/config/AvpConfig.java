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
