package org.avp.entity.joe;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.avp.config.AvpConfig;
import org.avp.entity.joe.net.JoeTerminalActionPayload;
import org.avp.entity.joe.net.JoeTerminalDataPayload;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * The server side of the Seegson Terminal and tablet: carries out what the screen asks and sends back
 * what it shows. Reach: without a powered Network Server, a terminal sees its owner's Joes within 64
 * blocks (same dimension) and a tablet sees none; with one, everything, everywhere.
 */
public final class JoeTerminalService
{
    public static final int LOCAL_RANGE = 64, RADAR = 8;

    private JoeTerminalService()
    {
    }

    /** Opens the screen for a player (from a terminal block, or a tablet when pos is null). */
    public static void open(ServerPlayer player, @Nullable BlockPos terminal)
    {
        CompoundTag data = build(player, terminal, null, null);
        data.putBoolean("Open", true);
        PacketDistributor.sendToPlayer(player, new JoeTerminalDataPayload(data));
    }

    public static void handle(ServerPlayer player, JoeTerminalActionPayload p)
    {
        BlockPos terminal = p.tablet() ? null : p.pos();
        if (terminal != null && (player.distanceToSqr(terminal.getCenter()) > 64
                || !(player.level().getBlockEntity(terminal) instanceof SeegsonTerminalBlockEntity)))
        {
            return;
        }
        if (p.tablet() && !player.getMainHandItem().is(org.avp.registry.ModItems.JOE_TABLET.get())
                && !player.getOffhandItem().is(org.avp.registry.ModItems.JOE_TABLET.get()))
        {
            return;
        }
        UUID owner = player.getUUID();
        JoeLibrary lib = JoeLibrary.get(player.server);
        boolean server = JoeLibrary.serverOnline(owner, player.server.overworld().getGameTime());
        boolean powered = terminal == null || ((SeegsonTerminalBlockEntity) player.level().getBlockEntity(terminal)).powered();
        WorkingJoeEntity joe = p.joe().isEmpty() ? null : reachable(player, terminal, server, p.joe());
        String message = null, editor = null;
        if (!powered && p.action() != JoeTerminalActionPayload.REFRESH && p.action() != JoeTerminalActionPayload.CLOSE)
        {
            message = "!NO POWER - connect the terminal to the power network.";
        }
        else
        {
            switch (p.action())
            {
                case JoeTerminalActionPayload.UPLOAD ->
                {
                    if (joe == null)
                    {
                        message = "!No unit selected (or out of range).";
                    }
                    else if (joe.isRogue())
                    {
                        message = "!Unit not responding.";
                    }
                    else
                    {
                        List<String> errors = joe.loadProgram(p.text(), player);
                        message = errors.isEmpty() ? "Program uploaded to " + joe.joeName() + "." : "!" + String.join("\n!", errors.subList(0, Math.min(8, errors.size())));
                    }
                }
                case JoeTerminalActionPayload.UPLOAD_ALL ->
                {
                    int n = 0;
                    String first = null;
                    for (WorkingJoeEntity j : JoeNetwork.of(owner))
                    {
                        if (canReach(player, terminal, server, j) && !j.isRogue())
                        {
                            List<String> errors = j.loadProgram(p.text(), player);
                            if (!errors.isEmpty())
                            {
                                first = errors.get(0);
                                break;
                            }
                            n++;
                        }
                    }
                    message = first != null ? "!" + first : "Program uploaded to " + n + " unit(s).";
                }
                case JoeTerminalActionPayload.CHECK ->
                {
                    JoeProgram parsed = JoeProgram.parse(p.text(), player.blockPosition());
                    if (parsed.ok())
                    {
                        message = "Syntax OK: " + parsed.body.size() + " steps, " + parsed.handlers.size() + " responses, " + parsed.functions.size()
                                + " functions.";
                    }
                    else
                    {
                        StringBuilder sb = new StringBuilder();
                        parsed.errors.stream().limit(8).forEach(e -> sb.append(sb.isEmpty() ? "!" : "\n!").append("Line ").append(e.line()).append(": ").append(e.message()));
                        message = sb.toString();
                    }
                }
                case JoeTerminalActionPayload.FETCH_SOURCE -> editor = joe == null ? "" : joe.source();
                case JoeTerminalActionPayload.RECALL ->
                {
                    if (joe != null)
                    {
                        joe.recall();
                        message = joe.joeName() + " recalled.";
                    }
                }
                case JoeTerminalActionPayload.STOP ->
                {
                    if (joe != null)
                    {
                        joe.clearProgram();
                        message = joe.joeName() + ": program cleared.";
                    }
                }
                case JoeTerminalActionPayload.RESUME ->
                {
                    if (joe != null)
                    {
                        joe.resume();
                        joe.endRemote();
                    }
                }
                case JoeTerminalActionPayload.SECURITY ->
                {
                    if (joe != null)
                    {
                        joe.setSecurity(!joe.security());
                        message = joe.joeName() + ": security " + (joe.security() ? "ON" : "OFF") + ".";
                    }
                }
                case JoeTerminalActionPayload.RENAME ->
                {
                    if (joe != null && !p.text().isBlank() && p.text().length() <= 24)
                    {
                        joe.setCustomName(net.minecraft.network.chat.Component.literal(p.text().trim()));
                    }
                }
                case JoeTerminalActionPayload.CLEAR_ALARM -> JoeNetwork.clearAlarm(owner);
                case JoeTerminalActionPayload.SEND ->
                {
                    String[] parts = p.text().split(" ", 2);
                    JoeNetwork.sendFromTerminal(owner, joe == null ? null : joe.getUUID(), parts[0].toLowerCase(java.util.Locale.ROOT),
                            parts.length > 1 ? parts[1] : "");
                    message = "Message sent on channel '" + parts[0] + "'.";
                }
                case JoeTerminalActionPayload.DRIVE ->
                {
                    if (joe != null && !joe.isRogue() && joe.isActive())
                    {
                        joe.drive(p.text());
                    }
                }
                case JoeTerminalActionPayload.APOLLO_REQUEST, JoeTerminalActionPayload.APOLLO_DISPATCH ->
                {
                    org.avp.apollo.ApolloCoreBlockEntity core = org.avp.apollo.ApolloCoreBlockEntity.of(player.server, owner);
                    String[] parts = p.text().split(" ");
                    org.avp.apollo.ApolloCoreBlockEntity.Entry entry = null;
                    if (core != null && parts.length >= 2)
                    {
                        for (var e : core.manifest())
                        {
                            if (net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(e.proto.getItem()).toString().equals(parts[0]))
                            {
                                entry = e;
                                break;
                            }
                        }
                    }
                    int count = 1;
                    try
                    {
                        count = parts.length >= 2 ? Math.max(1, Math.min(576, Integer.parseInt(parts[1]))) : 1;
                    }
                    catch (NumberFormatException ignored)
                    {
                    }
                    if (core == null)
                    {
                        message = "!No APOLLO core found (place one and power it).";
                    }
                    else if (!server && core.getLevel() != player.level())
                    {
                        message = "!APOLLO is out of range (needs the Network Server).";
                    }
                    else if (entry == null)
                    {
                        message = "!Select an item first.";
                    }
                    else if (org.avp.apollo.ApolloCoreBlockEntity.isSpecimen(entry.proto))
                    {
                        core.say("REQUEST DENIED. SPECIMEN IS COMPANY PROPERTY.");
                        message = "!APOLLO: REQUEST DENIED. SPECIAL ORDER 937.";
                    }
                    else if (p.action() == JoeTerminalActionPayload.APOLLO_REQUEST)
                    {
                        core.queue(new org.avp.apollo.ApolloCoreBlockEntity.Job(entry.proto.copy(), count, owner, player.level().getGameTime()));
                        message = "Request logged. A courier Joe will bring it to you.";
                    }
                    else
                    {
                        message = core.dispatch(parts.length >= 3 ? parts[2].toUpperCase(java.util.Locale.ROOT) : "", entry.proto, count);
                    }
                }
                case JoeTerminalActionPayload.SAVE_FILE, JoeTerminalActionPayload.DELETE_FILE, JoeTerminalActionPayload.LOAD_FILE ->
                {
                    String[] parts = p.text().split("\n", 2);
                    String name = parts[0];
                    if (p.action() == JoeTerminalActionPayload.LOAD_FILE && name.contains(":"))
                    {
                        ResourceLocation id = ResourceLocation.tryParse(name);
                        editor = id == null ? null : JoeNetwork.readDatapackProgram(player.serverLevel(), id);
                    }
                    else if (!server)
                    {
                        message = "!The program library needs a powered Seegson Network Server.";
                    }
                    else if (p.action() == JoeTerminalActionPayload.SAVE_FILE)
                    {
                        String err = lib.save(owner, name, parts.length > 1 ? parts[1] : "");
                        message = err != null ? "!" + err : "Saved '" + name + "'.";
                    }
                    else if (p.action() == JoeTerminalActionPayload.DELETE_FILE)
                    {
                        lib.delete(owner, name);
                        message = "Deleted '" + name + "'.";
                    }
                    else
                    {
                        editor = lib.files(owner).get(name);
                    }
                }
                default ->
                {
                }
            }
        }
        if (p.action() == JoeTerminalActionPayload.CLOSE)
        {
            return;
        }
        PacketDistributor.sendToPlayer(player, new JoeTerminalDataPayload(build(player, terminal, joe, message, editor)));
    }

    private static CompoundTag build(ServerPlayer player, @Nullable BlockPos terminal, @Nullable String message, @Nullable String editor)
    {
        return build(player, terminal, null, message, editor);
    }

    private static boolean canReach(Player player, @Nullable BlockPos terminal, boolean server, WorkingJoeEntity joe)
    {
        if (server)
        {
            return true;
        }
        return terminal != null && joe.level() == player.level() && joe.blockPosition().closerThan(terminal, LOCAL_RANGE);
    }

    @Nullable
    private static WorkingJoeEntity reachable(Player player, @Nullable BlockPos terminal, boolean server, String id)
    {
        try
        {
            WorkingJoeEntity joe = JoeNetwork.find(player.getUUID(), UUID.fromString(id));
            return joe != null && canReach(player, terminal, server, joe) ? joe : null;
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
    }

    private static CompoundTag build(ServerPlayer player, @Nullable BlockPos terminal, @Nullable WorkingJoeEntity selected, @Nullable String message,
            @Nullable String editor)
    {
        UUID owner = player.getUUID();
        boolean server = JoeLibrary.serverOnline(owner, player.server.overworld().getGameTime());
        CompoundTag data = new CompoundTag();
        data.putBoolean("Server", server);
        data.putBoolean("Tablet", terminal == null);
        if (terminal != null)
        {
            data.putLong("Pos", terminal.asLong());
            data.putBoolean("Power", player.level().getBlockEntity(terminal) instanceof SeegsonTerminalBlockEntity t && t.powered());
        }
        else
        {
            data.putBoolean("Power", true);
        }
        BlockPos alarm = JoeNetwork.alarmPos(owner);
        if (alarm != null)
        {
            data.putString("Alarm", WorkingJoeEntity.where(alarm));
        }
        ListTag units = new ListTag();
        for (WorkingJoeEntity joe : JoeNetwork.of(owner))
        {
            if (!canReach(player, terminal, server, joe))
            {
                continue;
            }
            CompoundTag u = new CompoundTag();
            u.putString("Id", joe.getUUID().toString());
            u.putString("Name", joe.joeName());
            u.putString("Status", joe.currentActivity());
            u.putInt("Charge", joe.chargePercent());
            u.putFloat("Health", joe.getHealth());
            u.putFloat("MaxHealth", joe.getMaxHealth());
            u.putString("Dim", joe.level().dimension().location().getPath());
            u.putString("At", WorkingJoeEntity.where(joe.blockPosition()));
            u.putByte("Mode", joe.mode());
            u.putBoolean("Security", joe.security());
            u.putBoolean("Remote", joe.isRemote());
            units.add(u);
        }
        data.put("Units", units);
        if (selected != null)
        {
            CompoundTag s = new CompoundTag();
            s.putString("Id", selected.getUUID().toString());
            ListTag log = new ListTag();
            selected.logLines().forEach(l -> log.add(StringTag.valueOf(l)));
            s.put("Log", log);
            ListTag lines = new ListTag();
            selected.status().forEach(c -> lines.add(StringTag.valueOf(c.getString())));
            s.put("Status", lines);
            ListTag vars = new ListTag();
            selected.vars.entrySet().stream().filter(e -> !e.getKey().startsWith("__")).limit(24)
                    .forEach(e -> vars.add(StringTag.valueOf(e.getKey() + " = " + JoeExpr.text(e.getValue()))));
            s.put("Vars", vars);
            s.putIntArray("Radar", radar(selected));
            s.put("Blips", blips(selected));
            s.putFloat("Yaw", selected.getYRot());
            data.put("Selected", s);
        }
        org.avp.apollo.ApolloCoreBlockEntity core = org.avp.apollo.ApolloCoreBlockEntity.of(player.server, owner);
        if (core != null && (server || core.getLevel() == player.level()))
        {
            CompoundTag a = new CompoundTag();
            a.putBoolean("Online", core.online());
            a.putLong("Stored", core.stored());
            a.putLong("Capacity", core.capacity());
            a.putInt("Types", core.manifest().size());
            a.putInt("TypeLimit", core.typeLimit());
            a.putInt("Jobs", core.jobs().size());
            ListTag items = new ListTag();
            core.manifest().stream().sorted((x, y) -> Long.compare(y.count, x.count)).limit(400).forEach(e ->
            {
                CompoundTag t = new CompoundTag();
                t.putString("Id", net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(e.proto.getItem()).toString());
                t.putString("Name", e.proto.getHoverName().getString());
                t.putLong("Count", e.count);
                items.add(t);
            });
            a.put("Items", items);
            ListTag alog = new ListTag();
            core.log().forEach(l -> alog.add(StringTag.valueOf(l)));
            a.put("Log", alog);
            data.put("Apollo", a);
        }
        ListTag files = new ListTag();
        if (server)
        {
            JoeLibrary.get(player.server).files(owner).keySet().forEach(f -> files.add(StringTag.valueOf(f)));
        }
        JoeNetwork.datapackPrograms(player.serverLevel()).forEach(id -> files.add(StringTag.valueOf(id.toString())));
        data.put("Files", files);
        if (message != null)
        {
            data.putString("Message", message);
        }
        if (editor != null)
        {
            data.putString("Editor", editor);
        }
        return data;
    }

    /** A top-down map around the Joe: walls in their map colour, floor darker, nothing black. */
    private static int[] radar(WorkingJoeEntity joe)
    {
        int size = RADAR * 2 + 1;
        int[] cells = new int[size * size];
        BlockPos c = joe.blockPosition();
        ServerLevel level = (ServerLevel) joe.level();
        for (int dz = -RADAR; dz <= RADAR; dz++)
        {
            for (int dx = -RADAR; dx <= RADAR; dx++)
            {
                BlockPos p = c.offset(dx, 0, dz);
                int colour = 0xFF000000;
                if (level.isLoaded(p))
                {
                    BlockState at = level.getBlockState(p), head = level.getBlockState(p.above()), below = level.getBlockState(p.below());
                    if (!at.getCollisionShape(level, p).isEmpty() || !head.getCollisionShape(level, p.above()).isEmpty())
                    {
                        BlockState wall = !head.isAir() ? head : at;
                        colour = 0xFF000000 | mapColour(wall, level, p, 1.0F);
                    }
                    else if (!below.isAir())
                    {
                        colour = 0xFF000000 | mapColour(below, level, p.below(), 0.45F);
                    }
                }
                cells[(dz + RADAR) * size + dx + RADAR] = colour;
            }
        }
        return cells;
    }

    private static int mapColour(BlockState state, ServerLevel level, BlockPos pos, float shade)
    {
        MapColor mc = state.getMapColor(level, pos);
        int rgb = mc == MapColor.NONE ? 0x404040 : mc.col;
        int r = (int) (((rgb >> 16) & 255) * shade), g = (int) (((rgb >> 8) & 255) * shade), b = (int) ((rgb & 255) * shade);
        return r << 16 | g << 8 | b;
    }

    private static ListTag blips(WorkingJoeEntity joe)
    {
        ListTag list = new ListTag();
        for (Entity e : joe.level().getEntities(joe, joe.getBoundingBox().inflate(RADAR, 4, RADAR)))
        {
            String kind = e instanceof WorkingJoeEntity ? "joe" : e instanceof Player ? "player" : e instanceof ItemEntity ? "item"
                    : e instanceof Enemy || e.getType().is(org.avp.registry.ModTags.XENOMORPHS) ? "hostile" : e instanceof LivingEntity ? "other" : null;
            if (kind == null)
            {
                continue;
            }
            CompoundTag b = new CompoundTag();
            b.putFloat("X", (float) (e.getX() - joe.getBlockX()));
            b.putFloat("Z", (float) (e.getZ() - joe.getBlockZ()));
            b.putString("K", kind);
            list.add(b);
        }
        return list;
    }

    static boolean needsPower()
    {
        return AvpConfig.server(AvpConfig.SERVER.joeTerminalNeedsPower);
    }
}
