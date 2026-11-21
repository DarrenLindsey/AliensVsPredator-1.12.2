package org.avp.command;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.avp.AliensVsPredator;
import org.avp.entity.xenomorph.DroneEntity;
import org.avp.world.hive.Hive;
import org.avp.world.hive.HiveBlueprint;
import org.avp.world.hive.HiveData;
import org.joml.Vector3f;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/** Player-only projection. The world is changed only by the separate founding command. */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class HiveBlueprintCommands
{
    private record Preview(ServerLevel level, BlockPos floor, List<HiveBlueprint.Tile> tiles, int expires) {}
    private static final Map<ServerPlayer, Preview> PREVIEWS = new WeakHashMap<>();

    static LiteralArgumentBuilder<CommandSourceStack> commands()
    {
        return Commands.literal("blueprint")
                .then(blueprintAction("preview", true))
                .then(blueprintAction("found", false))
                .then(Commands.literal("clear").executes(c -> {
                    PREVIEWS.remove(c.getSource().getPlayerOrException());
                    return 1;
                }));
    }

    private static LiteralArgumentBuilder<CommandSourceStack> blueprintAction(String action, boolean preview)
    {
        return Commands.literal(action).then(Commands.argument("name", StringArgumentType.word())
                .suggests((context, builder) -> builder.suggest("sample").buildFuture())
                .executes(c -> run(c.getSource(), StringArgumentType.getString(c, "name"), 0, preview))
                .then(Commands.argument("depth", IntegerArgumentType.integer(9, 384))
                        .executes(c -> run(c.getSource(), StringArgumentType.getString(c, "name"), IntegerArgumentType.getInteger(c, "depth"), preview))));
    }

    private static int run(CommandSourceStack source, String name, int requestedDepth, boolean preview) throws CommandSyntaxException
    {
        ServerPlayer player = source.getPlayerOrException();
        ServerLevel level = source.getLevel();
        BlockPos mouth = BlockPos.containing(source.getPosition());
        int depth = requestedDepth == 0 ? HiveBlueprint.defaultDepth(level, mouth) : requestedDepth;
        try
        {
            HiveBlueprint plan = HiveBlueprint.named(level, name);
            HiveBlueprint.checkDepth(level, mouth, depth);
            if (preview)
            {
                var tiles = plan.tiles();
                PREVIEWS.put(player, new Preview(level, mouth, tiles, level.getServer().getTickCount() + 400));
                draw(player, PREVIEWS.get(player));
                source.sendSuccess(() -> Component.literal("Projected over the surface for 20 seconds. Shaft at " + mouth.toShortString()
                        + "; floor Y " + mouth.below(depth).getY() + "; Core at " + plan.core(mouth.below(depth)).toShortString()
                        + ". Blue shaft, red Core, purple Queen, green hatchery, brown larder, white passage."), false);
            }
            else
            {
                if (!org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.hives))
                {
                    throw new IllegalArgumentException("Enable hives before founding a blueprint.");
                }
                plan.checkSite(level, mouth, depth);
                HiveData data = HiveData.get(level);
                if (data.nearest(mouth.below(depth), HiveData.spacing()).isPresent())
                {
                    throw new IllegalArgumentException("Choose a site outside the spacing of existing hives.");
                }
                DroneEntity drone = level.getEntitiesOfClass(DroneEntity.class, new AABB(mouth).inflate(6),
                        d -> d.hive() == null && d.canFound() && d.getTarget() == null)
                        .stream().min(java.util.Comparator.comparingDouble(d -> d.blockPosition().distSqr(mouth))).orElse(null);
                if (drone == null)
                {
                    throw new IllegalArgumentException("Place a free Drone or Builder within six blocks of the shaft marker.");
                }
                Hive hive = data.create(mouth.below(depth), mouth, Hive.State.FOUNDING, level.getGameTime());
                hive.setBlueprint(plan);
                hive.beginShaft();
                hive.setDigger(drone.getUUID());
                drone.setHiveId(hive.id());
                data.changed();
                PREVIEWS.remove(player);
                source.sendSuccess(() -> Component.literal("Drone assigned to build " + name + " at " + mouth.toShortString() + "."), true);
            }
            return 1;
        }
        catch (IllegalArgumentException | IOException ex)
        {
            source.sendFailure(Component.literal("Blueprint: " + ex.getMessage()));
            return 0;
        }
    }

    @SubscribeEvent
    public static void tick(ServerTickEvent.Post event)
    {
        int now = event.getServer().getTickCount();
        if (now % 12 != 0)
        {
            return;
        }
        PREVIEWS.entrySet().removeIf(entry -> {
            ServerPlayer player = entry.getKey();
            Preview preview = entry.getValue();
            if (player.isRemoved() || player.serverLevel() != preview.level() || now >= preview.expires())
            {
                return true;
            }
            draw(player, preview);
            return false;
        });
    }

    private static void draw(ServerPlayer player, Preview preview)
    {
        int stride = Math.max(1, (preview.tiles().size() + 239) / 240);
        for (int i = 0; i < preview.tiles().size(); i++)
        {
            HiveBlueprint.Tile tile = preview.tiles().get(i);
            boolean marker = tile.kind() == 'S' || tile.kind() == 'C';
            if (!marker && i % stride != 0)
            {
                continue;
            }
            BlockPos at = preview.floor().offset(tile.offset());
            int color = tile.color();
            Vector3f rgb = new Vector3f((color >> 16 & 255) / 255F, (color >> 8 & 255) / 255F, (color & 255) / 255F);
            preview.level().sendParticles(player, new DustParticleOptions(rgb, marker ? 2.5F : 1.3F), true,
                    at.getX() + .5, at.getY() + .3, at.getZ() + .5, 1, 0, 0, 0, 0);
        }
    }

    private HiveBlueprintCommands() {}
}
