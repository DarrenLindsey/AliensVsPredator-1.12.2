package org.avp.command;

import java.util.Comparator;
import java.util.List;

import org.avp.AliensVsPredator;
import org.avp.entity.xenomorph.DroneEntity;
import org.avp.entity.xenomorph.XenomorphEntity;
import org.avp.world.hive.Hive;
import org.avp.world.hive.HiveData;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * Debug commands (operators only):
 * /avp hive nearest - details of the nearest hive (click the coordinates to teleport)
 * /avp hive list    - every hive in this dimension
 * /avp hive found   - the Drone you're looking at starts founding a hive right now
 * /avp hive forget  - deletes the nearest hive's record (its members will found or join another)
 */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class AvpCommands
{
    @SubscribeEvent
    public static void register(RegisterCommandsEvent event)
    {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("avp").requires(s -> s.hasPermission(2))
                .then(Commands.literal("hive")
                        .then(Commands.literal("nearest").executes(c -> nearest(c.getSource())))
                        .then(Commands.literal("list").executes(c -> list(c.getSource())))
                        .then(Commands.literal("found").executes(c -> found(c.getSource())))
                        .then(Commands.literal("forget").executes(c -> forget(c.getSource())))));
    }

    private static Hive nearestHive(CommandSourceStack source)
    {
        BlockPos at = BlockPos.containing(source.getPosition());
        return HiveData.get(source.getLevel()).nearest(at, Double.MAX_VALUE / 4).orElse(null);
    }

    private static int nearest(CommandSourceStack source)
    {
        ServerLevel level = source.getLevel();
        Hive hive = nearestHive(source);
        if (hive == null)
        {
            source.sendFailure(Component.literal("No hives in this dimension."));
            return 0;
        }
        long now = level.getGameTime();
        int radius = hive.buildRadius(now, HiveData.maxRadius());
        List<XenomorphEntity> members = level.getEntitiesOfClass(XenomorphEntity.class, new AABB(hive.core()).inflate(HiveData.spacing()),
                x -> hive.id().equals(x.hiveId()));
        Entity heir = hive.heir() == null ? null : level.getEntity(hive.heir());
        double distance = Math.sqrt(hive.core().distToCenterSqr(source.getPosition()));
        source.sendSuccess(() -> Component.literal("Hive " + hive.id().toString().substring(0, 8)).withStyle(ChatFormatting.GOLD)
                .append(Component.literal(" (" + hive.state() + ")").withStyle(ChatFormatting.GRAY)), false);
        source.sendSuccess(() -> Component.literal("  Core: ").append(teleport(hive.core()))
                .append(Component.literal(String.format(" - %.0f blocks away", distance))), false);
        if (hive.state() == Hive.State.FOUNDING && hive.digPos() != null)
        {
            source.sendSuccess(() -> Component.literal("  Digging at: ").append(teleport(hive.digPos()))
                    .append(Component.literal(" heading " + (hive.hasVerticalShaft() ? "straight down" : hive.digDir().getName())
                            + " to y=" + hive.core().getY())), false);
        }
        source.sendSuccess(() -> Component.literal("  Entrance: ").append(teleport(hive.entrance())), false);
        source.sendSuccess(() -> Component.literal("  Resin: " + hive.resin() + " blocks, building out to " + radius + " blocks"), false);
        source.sendSuccess(() -> Component.literal("  Queen: " + (hive.hasQueen(now) ? "yes" : "none")
                + (hive.hasQueen(now) ? "" : ", heir: " + (heir == null ? "none" : heir.getType().getDescription().getString()
                        + (heir instanceof XenomorphEntity x ? " (" + x.jelly() + " jelly)" : "")))), false);
        source.sendSuccess(() -> Component.literal("  Members: " + members.size() + " " + summary(members)), false);
        if (hive.state() == Hive.State.ACTIVE)
        {
            source.sendSuccess(() -> Component.literal("  Work: " + hive.work().directive(level, hive).label()
                    + " (" + hive.work().claimedCount(now) + " assigned)"), false);
        }
        source.sendSuccess(() -> Component.literal("  Dug out: " + hive.dug() + " / " + org.avp.world.hive.HivePlanner.maxDug()
                + " blocks, reach " + hive.maxReach(now, HiveData.maxRadius())), false);
        for (org.avp.world.hive.Room room : hive.rooms())
        {
            int total = org.avp.world.hive.HivePlanner.digList(room).size();
            String status = room.done() ? "done" : "digging " + (total == 0 ? 0 : room.cursor() * 100 / total) + "%";
            source.sendSuccess(() -> Component.literal("    " + room.type().name().toLowerCase(java.util.Locale.ROOT) + " (" + status + ") ")
                    .append(teleport(room.centre())), false);
        }
        if (hive.state() == Hive.State.FOUNDING)
        {
            Entity digger = hive.digger() == null ? null : level.getEntity(hive.digger());
            source.sendSuccess(() -> Component.literal("  Digger: " + (digger == null ? "none (the next Drone to come along takes over)"
                    : digger.getType().getDescription().getString() + " at " + digger.blockPosition().toShortString())), false);
        }
        return 1;
    }

    private static String summary(List<XenomorphEntity> members)
    {
        java.util.Map<String, Integer> counts = new java.util.TreeMap<>();
        members.forEach(m -> counts.merge(m.getType().getDescription().getString(), 1, Integer::sum));
        return counts.toString();
    }

    private static int list(CommandSourceStack source)
    {
        ServerLevel level = source.getLevel();
        HiveData data = HiveData.get(level);
        java.util.List<Hive> all = new java.util.ArrayList<>(data.all());
        all.sort(Comparator.comparingDouble(h -> h.core().distToCenterSqr(source.getPosition())));
        if (all.isEmpty())
        {
            source.sendFailure(Component.literal("No hives in this dimension."));
            return 0;
        }
        source.sendSuccess(() -> Component.literal(all.size() + " hive(s):").withStyle(ChatFormatting.GOLD), false);
        for (Hive hive : all)
        {
            source.sendSuccess(() -> Component.literal("  " + hive.id().toString().substring(0, 8) + " " + hive.state() + " at ")
                    .append(teleport(hive.core()))
                    .append(Component.literal(" resin " + hive.resin() + (hive.hasQueen(level.getGameTime()) ? ", has Queen" : ""))), false);
        }
        return all.size();
    }

    private static int found(CommandSourceStack source) throws CommandSyntaxException
    {
        ServerPlayer player = source.getPlayerOrException();
        Vec3 eye = player.getEyePosition();
        Vec3 end = eye.add(player.getLookAngle().scale(64));
        EntityHitResult hit = ProjectileUtil.getEntityHitResult(player, eye, end, player.getBoundingBox().expandTowards(end.subtract(eye)).inflate(1),
                e -> e instanceof DroneEntity, 64 * 64);
        if (hit == null || !(hit.getEntity() instanceof DroneEntity drone))
        {
            source.sendFailure(Component.literal("Look at a Drone (within 64 blocks)."));
            return 0;
        }
        drone.forceFoundHive();
        source.sendSuccess(() -> Component.literal("That Drone will start digging a new hive now (spacing ignored). Use /avp hive nearest to follow it."), false);
        return 1;
    }

    private static int forget(CommandSourceStack source)
    {
        Hive hive = nearestHive(source);
        if (hive == null)
        {
            source.sendFailure(Component.literal("No hives in this dimension."));
            return 0;
        }
        HiveData.get(source.getLevel()).remove(hive);
        source.sendSuccess(() -> Component.literal("Forgot hive " + hive.id().toString().substring(0, 8) + " (the resin stays)."), true);
        return 1;
    }

    private static MutableComponent teleport(BlockPos pos)
    {
        String coords = pos.getX() + " " + pos.getY() + " " + pos.getZ();
        return Component.literal("[" + coords + "]").withStyle(s -> s.withColor(ChatFormatting.GREEN)
                .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + coords))
                .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("Click to teleport"))));
    }

    private AvpCommands()
    {
    }
}
