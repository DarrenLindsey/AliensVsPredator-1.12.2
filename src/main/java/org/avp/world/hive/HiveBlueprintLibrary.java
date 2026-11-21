package org.avp.world.hive;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.avp.AliensVsPredator;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/** The world-local PNG library. Decode plans on startup, never on a founding tick. */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class HiveBlueprintLibrary
{
    private static final List<String> DEFAULTS = List.of("sample", "split-nursery", "crescent");
    private static final int MAX_CUSTOM_PLANS = 64;
    private static final Map<MinecraftServer, List<Entry>> LIBRARIES = new WeakHashMap<>();

    private record Entry(String name, HiveBlueprint plan) {}

    private HiveBlueprintLibrary() {}

    @SubscribeEvent
    public static void start(ServerAboutToStartEvent event)
    {
        MinecraftServer server = event.getServer();
        Path folder = folder(server);
        List<Entry> entries = new ArrayList<>();
        try
        {
            Files.createDirectories(folder);
            for (String name : DEFAULTS)
            {
                Path dest = folder.resolve(name + ".png");
                try (var bundled = bundled(name))
                {
                    Files.copy(bundled, dest);
                }
                catch (FileAlreadyExistsException ignored)
                {
                    // Preserve edits to a starter plan.
                }
                catch (IOException failure)
                {
                    AliensVsPredator.LOGGER.warn("Could not install starter hive blueprint {}", dest, failure);
                }
                load(entries, name, dest);
            }
            try (var files = Files.list(folder))
            {
                files.filter(Files::isRegularFile)
                        .map(p -> p.getFileName().toString())
                        .filter(n -> n.endsWith(".png"))
                        .map(n -> n.substring(0, n.length() - 4))
                        .filter(n -> HiveBlueprint.validName(n) && !DEFAULTS.contains(n))
                        .sorted().limit(MAX_CUSTOM_PLANS)
                        .forEach(n -> load(entries, n, folder.resolve(n + ".png")));
            }
        }
        catch (IOException | java.io.UncheckedIOException failure)
        {
            AliensVsPredator.LOGGER.warn("Could not read the hive blueprint folder {}", folder, failure);
        }
        LIBRARIES.put(server, List.copyOf(entries));
    }

    @SubscribeEvent
    public static void stop(ServerStoppedEvent event)
    {
        LIBRARIES.remove(event.getServer());
    }

    private static void load(List<Entry> entries, String name, Path file)
    {
        try
        {
            entries.add(new Entry(name, HiveBlueprint.readFile(file)));
        }
        catch (IOException | IllegalArgumentException failure)
        {
            AliensVsPredator.LOGGER.warn("Skipping hive blueprint {}: {}", file, failure.getMessage());
        }
    }

    static Path folder(MinecraftServer server)
    {
        return server.getWorldPath(LevelResource.ROOT).resolve("avp_blueprints");
    }

    static java.io.InputStream bundled(String name) throws IOException
    {
        if (DEFAULTS.contains(name))
        {
            var stream = HiveBlueprintLibrary.class.getResourceAsStream("/avp_blueprints/" + name + ".png");
            if (stream != null)
            {
                return stream;
            }
        }
        throw new IOException("Put " + name + ".png in this world's avp_blueprints folder.");
    }

    static List<HiveBlueprint> plans(MinecraftServer server)
    {
        return LIBRARIES.getOrDefault(server, List.of()).stream().map(Entry::plan).toList();
    }

    public static List<String> names(MinecraftServer server)
    {
        return LIBRARIES.getOrDefault(server, List.of()).stream().map(Entry::name).toList();
    }
}
