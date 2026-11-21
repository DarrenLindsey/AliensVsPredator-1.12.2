package org.avp.world.hive;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.avp.config.AvpConfig;
import org.avp.entity.xenomorph.DroneEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/** Builds the initial hive in bounded slices. Completed shaft layers are recorded in the hive save. */
public final class HiveFounding
{
    private static final int SERVER_BUDGET = 64;
    private static final int RADIUS = 7;
    private static final int HEIGHT = 8;
    private static final int MAX_BLUEPRINT_ATTEMPTS = 4;
    private static final Map<MinecraftServer, TickBudget> BUDGETS = new WeakHashMap<>();

    private static final class TickBudget
    {
        private int tick = -1;
        private int remaining;
    }

    private enum Phase { CHECK_LAYER, CUT_LAYER, RIM, CHECK_BLUEPRINT, CHAMBER, FINISH }

    private final Hive hive;
    private Phase phase = Phase.CHECK_LAYER;
    private List<BlockPos> layer = List.of();
    private int cursor;
    private BlockPos chamber;
    private Iterator<BlockPos> sites;
    private List<HiveBlueprint> candidates = List.of();
    private int candidateIndex;
    private int attempts;
    private HiveBlueprint candidate;

    public HiveFounding(Hive hive)
    {
        this.hive = hive;
    }

    public void tick(ServerLevel level, DroneEntity drone)
    {
        if (!drone.isBuilder())
        {
            drone.appointFoundingBuilder(level, hive);
            return;
        }
        BlockPos dig = hive.digPos();
        if (dig == null || !chunkAreaLoaded(level, dig, phase == Phase.CHAMBER || phase == Phase.FINISH ? RADIUS + 1 : 3))
        {
            return;
        }
        double rate = AvpConfig.server(AvpConfig.SERVER.foundingDigSpeed) * AvpConfig.server(AvpConfig.SERVER.xenoDigSpeed);
        int allowance = (int) Math.clamp(Math.round(12 * rate), 1, SERVER_BUDGET);
        for (int i = 0; i < allowance && hive.state() == Hive.State.FOUNDING && hive.digPos() != null
                && spend(level.getServer()); i++)
        {
            if (!advance(level, drone))
            {
                return; // Wait for the affected chunks to load. The plan never forces a chunk load.
            }
        }
    }

    private static boolean spend(MinecraftServer server)
    {
        TickBudget budget = BUDGETS.computeIfAbsent(server, ignored -> new TickBudget());
        if (budget.tick != server.getTickCount())
        {
            budget.tick = server.getTickCount();
            budget.remaining = SERVER_BUDGET;
        }
        if (budget.remaining == 0)
        {
            return false;
        }
        budget.remaining--;
        return true;
    }

    private boolean advance(ServerLevel level, DroneEntity drone)
    {
        switch (phase)
        {
            case CHECK_LAYER, CUT_LAYER -> { return workLayer(level, drone); }
            case RIM -> {
                if (sites.hasNext())
                {
                    HiveShaft.lineRimBlock(level, sites.next());
                }
                else
                {
                    recordLayer(level);
                }
            }
            case CHECK_BLUEPRINT -> checkBlueprintBlock(level);
            case CHAMBER -> { return carveBlock(level); }
            case FINISH -> finish(level, drone);
        }
        return true;
    }

    private boolean workLayer(ServerLevel level, DroneEntity drone)
    {
        BlockPos at = hive.digPos();
        if (!hive.hasVerticalShaft() || at.getY() <= hive.core().getY())
        {
            return beginChamber(level, drone, at);
        }
        if (layer.isEmpty())
        {
            layer = HiveShaft.layer(at.below());
        }
        BlockPos pos = layer.get(cursor);
        if (!level.isLoaded(pos))
        {
            return false;
        }
        if (!HiveShaft.canExcavate(level, pos)
                || phase == Phase.CUT_LAYER && !level.getBlockState(pos).isAir() && !level.destroyBlock(pos, false, drone))
        {
            if (hive.blueprint() != null)
            {
                return false;
            }
            return beginChamber(level, drone, at);
        }
        if (++cursor < layer.size())
        {
            return true;
        }
        cursor = 0;
        if (phase == Phase.CHECK_LAYER)
        {
            phase = Phase.CUT_LAYER;
        }
        else if (at.equals(hive.entrance()))
        {
            phase = Phase.RIM;
            sites = HiveShaft.rim(at).iterator();
        }
        else
        {
            recordLayer(level);
        }
        return true;
    }

    private void recordLayer(ServerLevel level)
    {
        hive.setDig(hive.digPos().below(), Direction.NORTH);
        HiveData.get(level).changed();
        layer = List.of();
        phase = Phase.CHECK_LAYER;
    }

    private boolean beginChamber(ServerLevel level, DroneEntity drone, BlockPos at)
    {
        if (hive.hasVerticalShaft() && (at.equals(hive.entrance()) || !HiveShaft.inHiveLayer(level, at.getY(), HEIGHT)))
        {
            hive.setDig(null, Direction.NORTH);
            HiveData.get(level).remove(hive);
            drone.setHiveId(null);
            return false; // A blocked shaft cannot found an Overworld hive above the deepslate layer.
        }
        if (!chunkAreaLoaded(level, at, RADIUS + 1))
        {
            return false;
        }
        chamber = at;
        if (hive.shouldChooseBlueprint() && hive.blueprint() == null)
        {
            candidates = HiveBlueprintLibrary.plans(level.getServer());
            if (!candidates.isEmpty())
            {
                candidateIndex = Math.floorMod(hive.id().hashCode(), candidates.size());
                phase = Phase.CHECK_BLUEPRINT;
                return true;
            }
            finishChoosing(level);
        }
        else
        {
            beginCarving();
        }
        return true;
    }

    private void checkBlueprintBlock(ServerLevel level)
    {
        if (candidate == null)
        {
            if (attempts >= Math.min(MAX_BLUEPRINT_ATTEMPTS, candidates.size()))
            {
                finishChoosing(level);
                return;
            }
            HiveBlueprint plan = candidates.get((candidateIndex + attempts++) % candidates.size());
            if (plan.withinBudget(hive.entrance().getY() - chamber.getY()) && plan.hasCoreFloor(level, chamber))
            {
                candidate = plan;
                sites = plan.blocksAt(chamber);
            }
        }
        else if (!sites.hasNext())
        {
            hive.setBlueprint(candidate);
            finishChoosing(level);
        }
        else if (!HiveBlueprint.canCarve(level, sites.next()))
        {
            candidate = null;
        }
    }

    private void finishChoosing(ServerLevel level)
    {
        hive.setChooseBlueprint(false);
        HiveData.get(level).changed();
        beginCarving();
    }

    private void beginCarving()
    {
        sites = hive.blueprint() != null ? hive.blueprint().blocksAt(chamber)
                : BlockPos.betweenClosed(chamber.offset(-RADIUS, 0, -RADIUS),
                        chamber.offset(RADIUS, HEIGHT - 1, RADIUS)).iterator();
        phase = Phase.CHAMBER;
    }

    private static boolean chunkAreaLoaded(ServerLevel level, BlockPos pos, int radius)
    {
        return level.isLoaded(pos.offset(-radius, 0, -radius)) && level.isLoaded(pos.offset(radius, 0, -radius))
                && level.isLoaded(pos.offset(-radius, 0, radius)) && level.isLoaded(pos.offset(radius, 0, radius));
    }

    @javax.annotation.Nullable
    private BlockPos pending;

    private boolean carveBlock(ServerLevel level)
    {
        if (pending == null && !sites.hasNext())
        {
            phase = Phase.FINISH;
            return true;
        }
        BlockPos pos = pending != null ? pending : sites.next();
        if (hive.blueprint() != null)
        {
            pending = pos;
            if (!HiveBlueprint.canCarve(level, pos))
            {
                return false;
            }
            if (!level.getBlockState(pos).isAir())
            {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
            }
            pending = null;
            return true;
        }
        // A shallow chamber must not cut away its own surface rim.
        if (hive.hasVerticalShaft() && pos.getY() >= hive.entrance().getY() - 1)
        {
            return true;
        }
        double x = (pos.getX() - chamber.getX()) / (double) RADIUS;
        double y = (pos.getY() - chamber.getY()) / (double) HEIGHT;
        double z = (pos.getZ() - chamber.getZ()) / (double) RADIUS;
        if (x * x + y * y + z * z <= 1 && level.isInWorldBounds(pos) && HiveShaft.canExcavate(level, pos)
                && HivePlanner.diggable(level, pos))
        {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
        }
        return true;
    }

    private void finish(ServerLevel level, DroneEntity drone)
    {
        if (hive.blueprint() != null)
        {
            hive.setCore(hive.blueprint().core(chamber));
            hive.rooms().addAll(hive.blueprint().rooms(chamber));
        }
        else
        {
            hive.setCore(chamber.above());
        }
        hive.setDig(null, Direction.NORTH);
        hive.setDigger(null);
        if (hive.rooms(Room.Type.QUEEN).isEmpty())
        {
            hive.rooms().add(0, new Room(Room.Type.QUEEN, chamber, chamber, RADIUS, HEIGHT, true));
        }
        hive.setState(Hive.State.ACTIVE);
        hive.placeCoreBlock(level);
        HiveData.get(level).changed();
        level.sendParticles(ParticleTypes.POOF, chamber.getX() + 0.5, chamber.getY() + 2, chamber.getZ() + 0.5,
                40, 3, 1.5, 3, 0.02);
        level.playSound(null, chamber, SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.HOSTILE, 1.0F, 0.6F);
        drone.setState("HIVE FOUNDED");
    }
}
