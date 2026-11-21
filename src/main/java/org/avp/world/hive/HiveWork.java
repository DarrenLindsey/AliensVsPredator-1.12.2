package org.avp.world.hive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nullable;

import org.avp.entity.xenomorph.AdultXenomorphEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

/** The Core assigns work for its hive. Reservations are short-lived and rebuilt after a reload. */
public final class HiveWork
{
    public enum Kind { BUILD, DIG, COAT, EGG, SEAL }

    public enum Directive
    {
        CORE("Hive Core — stabilize and expand", List.of(Kind.BUILD, Kind.DIG, Kind.COAT)),
        BROOD("Queen — brood and expansion", List.of(Kind.EGG, Kind.DIG, Kind.BUILD, Kind.COAT)),
        DEFEND("Queen — repair and defend", List.of(Kind.BUILD, Kind.COAT, Kind.EGG, Kind.DIG));

        private final String label;
        private final List<Kind> order;

        Directive(String label, List<Kind> order)
        {
            this.label = label;
            this.order = order;
        }

        public String label() { return label; }
        public List<Kind> order() { return order; }
    }

    public static final class Job
    {
        private final UUID worker;
        private final Kind kind;
        private final BlockPos target;
        @Nullable
        private final UUID subject;
        private long expiry;

        private Job(UUID worker, Kind kind, BlockPos target, @Nullable UUID subject, long expiry)
        {
            this.worker = worker;
            this.kind = kind;
            this.target = target.immutable();
            this.subject = subject;
            this.expiry = expiry;
        }

        public Kind kind() { return kind; }
        public BlockPos target() { return target; }
    }

    private static final int LEASE_TICKS = 60;
    private final Map<UUID, Job> workers = new HashMap<>();
    private final Map<BlockPos, Job> targets = new HashMap<>();
    private final Map<BlockPos, Job> patches = new HashMap<>();
    private final Map<UUID, Job> subjects = new HashMap<>();
    private long nextCleanup;
    private long nextReview;
    private long nextCaveProbe;
    private long nextDirective;
    @Nullable
    private UUID directiveQueen;
    private Directive directive = Directive.CORE;

    public Directive directive(ServerLevel level, Hive hive)
    {
        long now = level.getGameTime();
        UUID queen = hive.hasQueen(now) ? hive.queen() : null;
        if (!Objects.equals(queen, directiveQueen) || now >= nextDirective)
        {
            directiveQueen = queen;
            nextDirective = now + 20;
            directive = Directive.CORE;
            if (queen != null && level.getEntity(queen) instanceof AdultXenomorphEntity adult && adult.isAlive() && adult.isQueen())
            {
                // Combat hits can be shorter than the refresh interval. Keep repairs urgent for a few seconds.
                boolean recentlyAttacked = adult.getLastHurtByMob() != null && adult.tickCount - adult.getLastHurtByMobTimestamp() < 100;
                directive = adult.getTarget() != null || recentlyAttacked ? Directive.DEFEND : Directive.BROOD;
            }
        }
        return directive;
    }

    public boolean beginReview(long now)
    {
        if (now < nextReview)
        {
            return false;
        }
        nextReview = now + 40;
        return true;
    }

    public boolean beginCaveProbe(long now)
    {
        if (now < nextCaveProbe)
        {
            return false;
        }
        nextCaveProbe = now + 40;
        return true;
    }

    @Nullable
    public Job job(UUID worker, long now)
    {
        Job job = workers.get(worker);
        return job != null && job.expiry > now ? job : null;
    }

    public boolean available(UUID worker, Kind kind, BlockPos target, long now)
    {
        return free(targets.get(target), worker, now) && (kind != Kind.COAT || free(patches.get(patch(target)), worker, now));
    }

    public boolean subjectAvailable(UUID worker, UUID subject, long now)
    {
        return free(subjects.get(subject), worker, now);
    }

    @Nullable
    public Job assign(UUID worker, Kind kind, BlockPos target, @Nullable UUID subject, long now)
    {
        cleanup(now);
        if (!available(worker, kind, target, now) || subject != null && !subjectAvailable(worker, subject, now))
        {
            return null;
        }
        Job previous = workers.get(worker);
        if (previous != null)
        {
            if (previous.expiry > now && previous.kind != kind && kind != Kind.SEAL)
            {
                return null;
            }
            release(previous);
        }
        Job job = new Job(worker, kind, target, subject, now + LEASE_TICKS);
        workers.put(worker, job);
        targets.put(job.target, job);
        if (kind == Kind.COAT)
        {
            patches.put(patch(job.target), job);
        }
        if (subject != null)
        {
            subjects.put(subject, job);
        }
        return job;
    }

    public boolean renew(Job job, long now)
    {
        if (job(job.worker, now) != job)
        {
            return false;
        }
        if (job.expiry - now <= 40)
        {
            job.expiry = now + LEASE_TICKS;
        }
        return true;
    }

    public void release(Job job)
    {
        if (workers.remove(job.worker, job))
        {
            removeTargets(job);
        }
    }

    public int claimedCount(long now)
    {
        cleanup(now);
        return workers.size();
    }

    private void cleanup(long now)
    {
        if (now < nextCleanup)
        {
            return;
        }
        nextCleanup = now + 20;
        workers.values().removeIf(job -> {
            if (job.expiry > now)
            {
                return false;
            }
            removeTargets(job);
            return true;
        });
    }

    private void removeTargets(Job job)
    {
        targets.remove(job.target, job);
        if (job.kind == Kind.COAT)
        {
            patches.remove(patch(job.target), job);
        }
        if (job.subject != null)
        {
            subjects.remove(job.subject, job);
        }
    }

    private static boolean free(@Nullable Job job, UUID worker, long now)
    {
        return job == null || job.expiry <= now || job.worker.equals(worker);
    }

    private static BlockPos patch(BlockPos pos)
    {
        return new BlockPos(Math.floorDiv(pos.getX(), 8), Math.floorDiv(pos.getY(), 8), Math.floorDiv(pos.getZ(), 8));
    }
}
