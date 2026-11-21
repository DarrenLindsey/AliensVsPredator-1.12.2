package org.avp.world.hive;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import org.avp.config.AvpConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * Every hive in a dimension (1.12.2 HiveHandler). Only one hive may exist within "Hive Spacing"
 * blocks of another: a Xenomorph with no hive joins the nearest one in range, or (a Drone) digs
 * down and founds a new one.
 */
public class HiveData extends SavedData
{
    private static final String NAME = "avp_hives";
    /** Hives nothing has lived in for this long (5 in-game days) are forgotten. */
    private static final long FORGET_AFTER = 120_000;

    private final Map<UUID, Hive> hives = new LinkedHashMap<>();

    public static HiveData get(ServerLevel level)
    {
        return level.getDataStorage().computeIfAbsent(new SavedData.Factory<>(HiveData::new, HiveData::load, null), NAME);
    }

    public static int spacing()
    {
        return AvpConfig.server(AvpConfig.SERVER.hiveSpacing);
    }

    public static int maxRadius()
    {
        return AvpConfig.server(AvpConfig.SERVER.hiveRadius);
    }

    public java.util.Collection<Hive> all()
    {
        return hives.values();
    }

    @Nullable
    public Hive get(@Nullable UUID id)
    {
        return id == null ? null : hives.get(id);
    }

    /** The hive whose area (spacing) this point is in, if any. */
    public Optional<Hive> nearest(BlockPos pos, double within)
    {
        return hives.values().stream().filter(h -> h.core().distSqr(pos) <= within * within)
                .min(Comparator.comparingDouble(h -> h.core().distSqr(pos)));
    }

    /** The hive whose built area contains this point. */
    public Optional<Hive> containing(BlockPos pos, long now)
    {
        return hives.values().stream().filter(h -> h.core().distSqr(pos) <= sq(h.buildRadius(now, maxRadius()) + 4))
                .findFirst();
    }

    /** Nest territory includes the entire shaft and rim even when the Core is far below them. */
    public boolean protectsTerrain(BlockPos pos, long now)
    {
        for (Hive hive : hives.values())
        {
            BlockPos entrance = hive.entrance();
            if (hive.hasVerticalShaft() && pos.getY() >= hive.core().getY() - 2 && pos.getY() <= entrance.getY() + 1
                    && Math.abs(pos.getX() - entrance.getX()) <= 3 && Math.abs(pos.getZ() - entrance.getZ()) <= 3)
            {
                return true;
            }
            if (hive.core().distSqr(pos) <= sq(hive.buildRadius(now, maxRadius()) + 4) || hive.roomAt(pos).isPresent())
            {
                return true;
            }
        }
        return false;
    }

    public Hive create(BlockPos core, BlockPos entrance, Hive.State state, long now)
    {
        Hive hive = new Hive(UUID.randomUUID(), core, entrance, state, now);
        hives.put(hive.id(), hive);
        setDirty();
        return hive;
    }

    public void remove(Hive hive)
    {
        hives.remove(hive.id());
        setDirty();
    }

    /** Forgets hives nothing has been alive in for a long time. */
    public void tidy(long now)
    {
        if (hives.values().removeIf(h -> now - h.lastActive() > FORGET_AFTER))
        {
            setDirty();
        }
    }

    /** Someone attacked the hive: every member nearby turns on them. */
    public static void alert(ServerLevel level, BlockPos pos, LivingEntity attacker)
    {
        if (attacker instanceof net.minecraft.world.entity.player.Player player && (player.isCreative() || player.isSpectator()))
        {
            return;
        }
        HiveData data = get(level);
        data.containing(pos, level.getGameTime()).ifPresent(hive ->
        {
            int range = hive.buildRadius(level.getGameTime(), maxRadius()) + 24;
            for (org.avp.entity.xenomorph.XenomorphEntity xeno : level.getEntitiesOfClass(org.avp.entity.xenomorph.XenomorphEntity.class,
                    new net.minecraft.world.phys.AABB(hive.core()).inflate(range), x -> hive.id().equals(x.hiveId())))
            {
                if (xeno.getTarget() == null && xeno.canAttack(attacker))
                {
                    xeno.setTarget(attacker);
                }
            }
        });
    }

    private static double sq(double d)
    {
        return d * d;
    }

    public void changed()
    {
        setDirty();
    }

    // ---------------------------------------------------------------- saving

    private static HiveData load(CompoundTag tag, HolderLookup.Provider registries)
    {
        HiveData data = new HiveData();
        for (Tag t : tag.getList("Hives", Tag.TAG_COMPOUND))
        {
            Hive hive = Hive.load((CompoundTag) t);
            data.hives.put(hive.id(), hive);
        }
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries)
    {
        ListTag list = new ListTag();
        hives.values().forEach(h -> list.add(h.save()));
        tag.put("Hives", list);
        return tag;
    }
}
