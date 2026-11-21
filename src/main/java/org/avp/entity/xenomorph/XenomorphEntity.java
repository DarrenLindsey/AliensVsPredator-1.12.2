package org.avp.entity.xenomorph;

import java.util.EnumSet;
import java.util.List;

import javax.annotation.Nullable;

import org.avp.registry.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

/**
 * Base for every Xenomorph stage (1.12.2 SpeciesAlien + IMaturable + IRoyalOrganism):
 *  - Royal jelly: it seeks out and eats royal jelly lying around; each one adds to its jelly level.
 *  - Maturing: after a set time, or once it has eaten enough jelly, it turns into its next
 *    form (e.g. Chestburster -> Drone). If that form isn't ported yet it simply waits.
 *  - Immune to poison.
 */
public abstract class XenomorphEntity extends Monster
{
    /** Jelly gained per royal jelly item (1.12.2: 100). */
    public static final int JELLY_PER_ITEM = 100;

    private int jelly;
    private int age;
    // film-style behaviour state (see XenoBrain)
    @Nullable
    private net.minecraft.world.phys.Vec3 investigatePos;
    private int investigateTicks;
    private int retreatCooldown;
    @Nullable
    private net.minecraft.core.BlockPos knownTurret;
    private int knownTurretTicks;
    private int calmTicks;
    private int standTicks;
    /** Ticks before it may switch between crawling and standing again (stops it bobbing). */
    private int poseHold;
    /** Its pack leader (itself if it leads, or has no pack). Saved. */
    @Nullable
    private java.util.UUID packLeader;
    /** Set while a pack member hands it a target, so it isn't passed straight back. */
    private boolean fromPack;

    /** What it's doing (for the debug label). */
    private String state = "LURKING";
    private boolean debugNamed;

    /** Hidden hunger meter, 0 (starving) .. MAX_HUNGER (full). */
    private int hunger = 60 + new java.util.Random().nextInt(41);
    public static final int MAX_HUNGER = 100;

    /** The hive it belongs to (HiveData), or null. */
    @Nullable
    private java.util.UUID hiveId;
    /** Lab-grown and bonded to someone (see org.avp.entity.xenomorph.lab.XenoBond). */
    @Nullable
    private org.avp.entity.xenomorph.lab.XenoBond bond;

    @Nullable
    public org.avp.entity.xenomorph.lab.XenoBond bond()
    {
        return bond;
    }

    public boolean isBonded()
    {
        return bond != null;
    }

    /** Makes it a bonded pet of this player. */
    public org.avp.entity.xenomorph.lab.XenoBond bondTo(java.util.UUID owner)
    {
        bond = new org.avp.entity.xenomorph.lab.XenoBond(this, owner);
        bond.install();
        return bond;
    }

    @Override
    protected net.minecraft.world.InteractionResult mobInteract(net.minecraft.world.entity.player.Player player, net.minecraft.world.InteractionHand hand)
    {
        if (bond != null)
        {
            net.minecraft.world.InteractionResult r = bond.interact(player, hand);
            if (r != net.minecraft.world.InteractionResult.PASS)
            {
                return r;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isAlliedTo(net.minecraft.world.entity.Entity other)
    {
        return bond != null && bond.allied(other) || super.isAlliedTo(other);
    }

    @Override
    public boolean removeWhenFarAway(double distance)
    {
        return bond == null && super.removeWhenFarAway(distance);
    }

    @Nullable
    public java.util.UUID hiveId()
    {
        return hiveId;
    }

    public void setHiveId(@Nullable java.util.UUID hiveId)
    {
        this.hiveId = hiveId;
    }

    /** Its hive, if it belongs to one (server only). */
    @Nullable
    public org.avp.world.hive.Hive hive()
    {
        return level() instanceof ServerLevel server && hiveId != null ? org.avp.world.hive.HiveData.get(server).get(hiveId) : null;
    }

    /** Digging the founding tunnel: an urge it can't resist - it doesn't hunt and doesn't get hungry. */
    private boolean founding;

    public boolean isFounding()
    {
        return founding;
    }

    public void setFounding(boolean founding)
    {
        this.founding = founding;
    }

    /** Is this the hive Queen? */
    public boolean isQueen()
    {
        return false;
    }

    /** Can it grow (eventually) into a Queen? Drone -> Warrior -> Praetorian -> Queen. */
    public boolean isRoyalLine()
    {
        return false;
    }

    public void addJelly(int amount)
    {
        jelly += amount;
    }

    /**
     * Every few seconds: joins the hive whose area it's in, keeps the hive's records up to date
     * (Queen, royal heir) and, with no Queen, picks an heir for royal succession.
     */
    private void updateHive(ServerLevel level)
    {
        if (!org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.hives))
        {
            return;
        }
        org.avp.world.hive.HiveData data = org.avp.world.hive.HiveData.get(level);
        long now = level.getGameTime();
        org.avp.world.hive.Hive hive = data.get(hiveId);
        if (hive == null)
        {
            hiveId = null;
            hive = data.nearest(blockPosition(), org.avp.world.hive.HiveData.spacing()).orElse(null);
            if (hive == null && isQueen() && !level.canSeeSky(blockPosition()))
            {
                // a Queen with no hive founds one where she stands - if she's underground
                hive = data.create(blockPosition(), blockPosition(), org.avp.world.hive.Hive.State.ACTIVE, now);
            }
            if (hive == null)
            {
                return;
            }
            hiveId = hive.id();
        }
        hive.touch(now);
        if (hive.state() == org.avp.world.hive.Hive.State.ACTIVE && random.nextInt(4) == 0 && hive.work().beginReview(now))
        {
            hive.placeCoreBlock(level);
            org.avp.world.hive.HivePlanner.plan(level, hive);
            // foraging: is the hive short of hosts (for the eggs) or food (for the larder)?
            java.util.List<net.minecraft.world.entity.LivingEntity> held = level.getEntitiesOfClass(net.minecraft.world.entity.LivingEntity.class,
                    new net.minecraft.world.phys.AABB(hive.core()).inflate(hive.buildRadius(now, org.avp.world.hive.HiveData.maxRadius()) + 2),
                    e -> e.hasData(org.avp.registry.ModAttachments.COCOONED));
            long hosts = held.stream().filter(org.avp.entity.parasite.FacehuggerEntity::isValidHost).count();
            hive.needsHosts = hive.state() == org.avp.world.hive.Hive.State.ACTIVE && hosts < 4
                    && org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenosCocoonHosts);
            hive.needsFood = hive.state() == org.avp.world.hive.Hive.State.ACTIVE && held.size() - hosts < 3
                    && org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.hiveLarder);
        }
        if (random.nextInt(200) == 0)
        {
            data.tidy(now);
        }
        if (isQueen() && (hive.queen() == null || hive.queen().equals(getUUID()) || !hive.hasQueen(now)))
        {
            hive.setQueen(getUUID(), now);
            if (getUUID().equals(hive.heir()))
            {
                hive.setHeir(null);
            }
            data.changed();
        }
        if (!hive.hasQueen(now) && org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.royalSuccession) && isRoyalLine())
        {
            // royal succession: the biggest royal-line member becomes the heir
            Entity heir = hive.heir() == null ? null : level.getEntity(hive.heir());
            if (heir == null || !heir.isAlive() || heir instanceof net.minecraft.world.entity.LivingEntity current && current.getMaxHealth() < getMaxHealth())
            {
                hive.setHeir(getUUID());
                data.changed();
            }
        }
    }

    /** What's left of a meal from the larder goes to the midden. */
    private void leaveRemains(ServerLevel level)
    {
        org.avp.world.hive.Hive hive = hive();
        if (hive == null)
        {
            return;
        }
        hive.rooms(org.avp.world.hive.Room.Type.MIDDEN).stream().filter(org.avp.world.hive.Room::done).findFirst().ifPresent(midden ->
        {
            BlockPos spot = org.avp.world.hive.HivePlanner.floorSpot(level, midden, false);
            if (spot != null)
            {
                level.setBlock(spot, random.nextInt(6) == 0 ? net.minecraft.world.level.block.Blocks.SKELETON_SKULL.defaultBlockState()
                        : net.minecraft.world.level.block.Blocks.BONE_BLOCK.defaultBlockState(), 3);
            }
        });
    }

    /** Royal succession: a kill by a member of a queenless hive feeds royal jelly to the heir. */
    private void feedHeir(ServerLevel level, net.minecraft.world.entity.LivingEntity victim)
    {
        org.avp.world.hive.Hive hive = hive();
        if (hive == null || hive.heir() == null || hive.hasQueen(level.getGameTime())
                || !org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.royalSuccession))
        {
            return;
        }
        if (level.getEntity(hive.heir()) instanceof XenomorphEntity heir)
        {
            heir.addJelly((int) (victim.getMaxHealth() * 150));
            level.sendParticles(ParticleTypes.HAPPY_VILLAGER, heir.getX(), heir.getY() + heir.getBbHeight(), heir.getZ(), 3, 0.3, 0.2, 0.3, 0);
        }
    }

    protected XenomorphEntity(EntityType<? extends XenomorphEntity> type, Level level)
    {
        super(type, level);
        // they keep away from fire and lava
        setPathfindingMalus(net.minecraft.world.level.pathfinder.PathType.DANGER_FIRE, 16.0F);
        setPathfindingMalus(net.minecraft.world.level.pathfinder.PathType.DAMAGE_FIRE, -1.0F);
        setPathfindingMalus(net.minecraft.world.level.pathfinder.PathType.LAVA, -1.0F);
    }

    // ---- client: how far the tail is curled up (0 = straight out behind, 1 = fully curled)
    private float tailCurl;

    /**
     * Client only: measures the room behind the creature (at tail height) and eases the tail curl
     * towards what fits, so the tail never pokes through a wall.
     *
     * @param tailLength how far the straight tail reaches behind the creature, in blocks
     */
    public float updateTailCurl(float tailLength)
    {
        net.minecraft.world.phys.Vec3 back = net.minecraft.world.phys.Vec3.directionFromRotation(0, yBodyRot).scale(-1);
        net.minecraft.world.phys.Vec3 from = position().add(0, Math.min(1.0, getBbHeight() * 0.5), 0);
        net.minecraft.world.phys.Vec3 to = from.add(back.scale(tailLength));
        var hit = level().clip(new net.minecraft.world.level.ClipContext(from, to, net.minecraft.world.level.ClipContext.Block.COLLIDER,
                net.minecraft.world.level.ClipContext.Fluid.NONE, this));
        double room = hit.getType() == net.minecraft.world.phys.HitResult.Type.MISS ? tailLength : hit.getLocation().distanceTo(from);
        float wanted = (float) Math.max(0, Math.min(1, (tailLength - room) / tailLength + 0.1));
        tailCurl += (wanted - tailCurl) * 0.15F;
        return tailCurl;
    }

    // ---- behaviour state used by XenoBrain

    public void setState(String state)
    {
        this.state = state;
    }

    // ---- packs

    /** Which pack it's in: the leader's UUID (its own if it leads or is alone). */
    public java.util.UUID packId()
    {
        return packLeader == null ? getUUID() : packLeader;
    }

    public boolean isPackLeader()
    {
        return packId().equals(getUUID());
    }

    /** Its packmates nearby (not counting itself). Eggs don't count. */
    public java.util.List<XenomorphEntity> packmates()
    {
        if (!org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenoPacks))
        {
            return java.util.List.of();
        }
        int range = org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenoPackRange);
        java.util.UUID id = packId();
        return level().getEntitiesOfClass(XenomorphEntity.class, getBoundingBox().inflate(range),
                x -> x != this && packsWith(x) && x.packId().equals(id));
    }

    /** Pack strength, for choosing a leader: the toughest, then the oldest. */
    /** Pack rank: the Queen first, then the strongest, then the oldest; ties broken by id so every pack has exactly one leader. */
    private static int compareRank(XenomorphEntity a, XenomorphEntity b)
    {
        int c = Boolean.compare(a.isQueen(), b.isQueen());
        if (c == 0)
        {
            c = Float.compare(a.getMaxHealth(), b.getMaxHealth());
        }
        if (c == 0)
        {
            c = Integer.compare(a.age, b.age);
        }
        return c != 0 ? c : a.getUUID().compareTo(b.getUUID());
    }

    /** Can it be in a pack with this one? Adults (not eggs or newborns) of the same hive, or both hiveless. */
    private boolean packsWith(XenomorphEntity other)
    {
        return other.isAlive() && canPack() && other.canPack() && java.util.Objects.equals(hiveId, other.hiveId);
    }

    private boolean canPack()
    {
        return !(this instanceof OvamorphEntity) && !(this instanceof ChestbursterEntity);
    }

    /**
     * Every 2 seconds: find (or form) a pack. Everyone in range follows the highest-ranked
     * Xenomorph among them (or that one's leader), so a group always settles on one leader -
     * even when they're all equally strong (they used to each lead themselves) - and a pack
     * never mixes members of different hives.
     */
    private void updatePack()
    {
        if (!canPack() || !org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenoPacks) || !(level() instanceof ServerLevel server))
        {
            packLeader = null;
            return;
        }
        int range = org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenoPackRange);
        XenomorphEntity best = this;
        for (XenomorphEntity other : level().getEntitiesOfClass(XenomorphEntity.class, getBoundingBox().inflate(range),
                x -> x != this && packsWith(x)))
        {
            if (compareRank(other, best) > 0)
            {
                best = other;
            }
        }
        // follow the top one's own leader if it has a better one (long strings of Xenomorphs end up as one pack)
        if (best != this && best.packLeader != null && !best.packLeader.equals(best.getUUID())
                && server.getEntity(best.packLeader) instanceof XenomorphEntity root && packsWith(root) && compareRank(root, best) > 0
                && root.distanceToSqr(this) < range * range * 4)
        {
            best = root;
        }
        packLeader = best.getUUID();
    }

    /** Spotted prey (or got hurt): the rest of the pack joins in. */
    @Override
    public void setTarget(@Nullable net.minecraft.world.entity.LivingEntity target)
    {
        net.minecraft.world.entity.LivingEntity before = getTarget();
        if (target != null && PreyCapture.blocksAttack(this, target))
        {
            return;
        }
        super.setTarget(target);
        if (target == null || getTarget() != target || target == before || level().isClientSide)
        {
            return;
        }
        if (this instanceof DroneEntity drone && drone.canCapture(target))
        {
            PreyCapture.reserve(drone, target);
            return;
        }
        if (fromPack)
        {
            return;
        }
        for (XenomorphEntity mate : packmates())
        {
            if (mate.getTarget() == null && !mate.isVehicle() && !"BURROWING".equals(mate.state)
                    && (mate.wantsToHunt(target) || target == getLastHurtByMob()))
            {
                mate.fromPack = true;
                mate.setTarget(target);
                mate.fromPack = false;
                mate.setState("JOINING PACK HUNT");
            }
        }
    }

    /** Shares a noise to investigate with the pack. */
    public void sharePackInvestigation(net.minecraft.world.phys.Vec3 at)
    {
        for (XenomorphEntity mate : packmates())
        {
            if (mate.getTarget() == null && mate.investigatePos() == null)
            {
                mate.investigate(at);
            }
        }
    }

    /** Adults can drop to a crawl to fit through 1-block gaps. */
    protected boolean canCrawl()
    {
        return false;
    }

    public boolean isCrawling()
    {
        return getPose() == net.minecraft.world.entity.Pose.SWIMMING;
    }

    /** Crawling: under a block tall. */
    @Override
    protected net.minecraft.world.entity.EntityDimensions getDefaultDimensions(net.minecraft.world.entity.Pose pose)
    {
        net.minecraft.world.entity.EntityDimensions standing = super.getDefaultDimensions(pose);
        if (pose == net.minecraft.world.entity.Pose.SWIMMING && canCrawl())
        {
            return net.minecraft.world.entity.EntityDimensions.scalable(standing.width(), 0.9F).withEyeHeight(0.7F);
        }
        return standing;
    }

    private void setCrawling(boolean crawl)
    {
        setPose(crawl ? net.minecraft.world.entity.Pose.SWIMMING : net.minecraft.world.entity.Pose.STANDING);
        refreshDimensions();
    }

    /**
     * Should it climb the wall it's touching? Only when where it's going is higher up (its
     * target, or the next step of its path) - not just because it brushed a wall, which made
     * it bob up and down in small rooms.
     */
    public boolean wantsToClimb()
    {
        if (!horizontalCollision)
        {
            return false;
        }
        net.minecraft.world.level.pathfinder.Path path = getNavigation().getPath();
        if (path != null && !path.isDone() && path.getNextNodePos().getY() > getBlockY())
        {
            return true;
        }
        net.minecraft.world.phys.Vec3 goal = goalPos();
        return goal != null && goal.y > getY() + 1.0 && !(getTarget() != null && distanceToSqr(getTarget()) < 4);
    }

    /** Where it's trying to get to: its target, or a noise it's investigating. */
    @Nullable
    public net.minecraft.world.phys.Vec3 goalPos()
    {
        if (getTarget() != null)
        {
            return getTarget().position();
        }
        if (investigatePos != null)
        {
            return investigatePos;
        }
        return destination != null && tickCount < destinationUntil ? destination : null;
    }

    /**
     * Whatever a Xenomorph carries (a captured host, an egg) never steers it - in vanilla a mob riding
     * another mob controls it (chicken jockeys), which left Drones carrying a bat frozen.
     */
    @javax.annotation.Nullable
    @Override
    public net.minecraft.world.entity.LivingEntity getControllingPassenger()
    {
        return null;
    }

    /** Currents don't push Xenomorphs around - they're far too strong (they still swim and sink as usual). */
    @Override
    public boolean isPushedByFluid()
    {
        return false;
    }

    /** Where a hive job is taking it (so it climbs walls to get there, e.g. back out of a pit it fell into). */
    @Nullable
    private net.minecraft.world.phys.Vec3 destination;
    private int destinationUntil;

    /**
     * Go somewhere for a job. Wall climbers head straight for it when there's no walking path,
     * and climb whatever wall is in the way if it's higher up.
     */
    public void navigateTo(double x, double y, double z, double speed)
    {
        destination = new net.minecraft.world.phys.Vec3(x, y, z);
        destinationUntil = tickCount + 200;
        getNavigation().moveTo(x, y, z, speed);
    }

    void stopJobNavigation()
    {
        destination = null;
        getNavigation().stop();
    }

    private int forageUntil;

    boolean isOnForagingTrip()
    {
        return tickCount < forageUntil;
    }

    void beginForagingTrip()
    {
        forageUntil = tickCount + 300;
    }

    /** Stuck: on its way somewhere for a job but hasn't got closer in the last 3 seconds. */
    private int stuckFor;
    private double lastDistance = Double.MAX_VALUE;

    public boolean isStuck()
    {
        return stuckFor >= 3;
    }

    private void updateStuck()
    {
        net.minecraft.world.phys.Vec3 goal = goalPos();
        if (goal == null)
        {
            stuckFor = 0;
            lastDistance = Double.MAX_VALUE;
            return;
        }
        double distance = position().distanceTo(goal);
        stuckFor = distance > 2.5 && distance > lastDistance - 0.5 ? stuckFor + 1 : 0;
        lastDistance = distance;
    }

    public void navigateTo(Entity entity, double speed)
    {
        navigateTo(entity.getX(), entity.getY(), entity.getZ(), speed);
    }

    /**
     * Crawling: if it can't reach its goal standing up but can when crawling, it drops down;
     * it stands up again once there's room. Also drops down when it bumps into a low gap.
     */
    private void updateCrawling()
    {
        if (!canCrawl() || !org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenosCrawl))
        {
            return;
        }
        if (poseHold > 0)
        {
            return;
        }
        if (isCrawling())
        {
            net.minecraft.world.phys.AABB standing = super.getDefaultDimensions(net.minecraft.world.entity.Pose.STANDING)
                    .scale(getScale()).makeBoundingBox(position());
            standTicks = level().noCollision(this, standing) ? standTicks + 1 : 0;
            if (standTicks >= 3)
            {
                setCrawling(false);
                standTicks = 0;
                poseHold = 40;
            }
            return;
        }
        net.minecraft.world.phys.Vec3 goal = goalPos();
        if (goal == null)
        {
            return;
        }
        net.minecraft.core.BlockPos goalBlock = net.minecraft.core.BlockPos.containing(goal);
        net.minecraft.world.level.pathfinder.Path upright = getNavigation().createPath(goalBlock, 1);
        if (upright != null && upright.canReach())
        {
            return;
        }
        setCrawling(true);
        net.minecraft.world.level.pathfinder.Path low = getNavigation().createPath(goalBlock, 1);
        boolean better = low != null && (low.canReach() || upright == null || low.getDistToTarget() + 1 < upright.getDistToTarget());
        if (better)
        {
            getNavigation().moveTo(low, 1.1);
            standTicks = 0;
            poseHold = 40;
        }
        else
        {
            setCrawling(false);
        }
    }

    /** Smashes glass (avp:xeno_breakable) between it and its goal. */
    private void smashGlass()
    {
        net.minecraft.world.phys.Vec3 goal = goalPos();
        if (goal == null || !org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenosBreakGlass)
                || !net.neoforged.neoforge.event.EventHooks.canEntityGrief((ServerLevel) level(), this))
        {
            return;
        }
        net.minecraft.world.phys.Vec3 from = position().add(0, 0.3, 0);
        net.minecraft.world.phys.Vec3 dir = goal.subtract(from).normalize();
        for (double d = 0.5; d <= 4; d += 0.5)
        {
            for (int up = 0; up <= 1; up++)
            {
                net.minecraft.core.BlockPos pos = net.minecraft.core.BlockPos.containing(from.add(dir.scale(d)).add(0, up, 0));
                if (level().getBlockState(pos).is(org.avp.registry.ModTags.XENO_BREAKABLE))
                {
                    if (distanceToSqr(net.minecraft.world.phys.Vec3.atCenterOf(pos)) < 2.5 * 2.5)
                    {
                        level().destroyBlock(pos, false, this);
                        swing(net.minecraft.world.InteractionHand.MAIN_HAND);
                    }
                    else if (getNavigation().isDone() || horizontalCollision)
                    {
                        getNavigation().moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 1.2);
                    }
                    return;
                }
            }
        }
    }

    /**
     * What it's really doing, for the debug label: the movement goal running right now (by name),
     * or the state a goal reported, or hunting/lurking.
     */
    private String activity()
    {
        net.minecraft.world.entity.ai.goal.WrappedGoal running = goalSelector.getAvailableGoals().stream()
                .filter(net.minecraft.world.entity.ai.goal.WrappedGoal::isRunning)
                .filter(g -> g.getFlags().contains(Goal.Flag.MOVE))
                .min(java.util.Comparator.comparingInt(net.minecraft.world.entity.ai.goal.WrappedGoal::getPriority)).orElse(null);
        if (running != null)
        {
            String name = running.getGoal().getClass().getSimpleName().replace("Goal", "");
            return name.replaceAll("([a-z])([A-Z])", "$1 $2").toUpperCase(java.util.Locale.ROOT);
        }
        return getTarget() != null ? "HUNTING" : state;
    }

    private void updateDebugLabel()
    {
        boolean debug = org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenoDebugLabels);
        if (debug)
        {
            if (!hasCustomName() || debugNamed)
            {
                String now = activity();
                int pack = packmates().size() + 1;
                org.avp.world.hive.Hive hive = hive();
                String role = hive == null ? "" : getUUID().equals(hive.heir()) ? " | HEIR" : getUUID().equals(hive.digger()) ? " | DIGGER"
                        : this instanceof DroneEntity drone && drone.isBuilder() ? " | BUILDER"
                        : this instanceof DroneEntity drone && drone.isCoater() ? " | COATER" : " | hive";
                // the kind of Xenomorph first (the name is translated on each player's side)
                setCustomName(net.minecraft.network.chat.Component.empty().append(getType().getDescription()).append(": ")
                        .append(net.minecraft.network.chat.Component.literal(now + (isCrawling() ? " (crawling)" : "")
                        + " | hunger " + hunger + (isHungry() ? " HUNGRY" : "")
                        + (pack > 1 ? " | pack " + pack + (isPackLeader() ? " LEADER" : "") : "") + role)));
                setCustomNameVisible(true);
                debugNamed = true;
            }
        }
        else if (debugNamed)
        {
            setCustomName(null);
            setCustomNameVisible(false);
            debugNamed = false;
        }
    }

    @Nullable
    public net.minecraft.world.phys.Vec3 investigatePos()
    {
        return investigatePos;
    }

    public void investigate(@Nullable net.minecraft.world.phys.Vec3 at)
    {
        investigatePos = at;
        investigateTicks = at == null ? 0 : 400;
    }

    public int retreatCooldown()
    {
        return retreatCooldown;
    }

    public void setRetreatCooldown(int ticks)
    {
        retreatCooldown = ticks;
    }

    @Nullable
    public net.minecraft.core.BlockPos knownTurret()
    {
        return knownTurret;
    }

    public void rememberTurret(net.minecraft.core.BlockPos turret)
    {
        knownTurret = turret;
        knownTurretTicks = 1200; // a minute
    }

    /** What it grows into, or null if this is a final form. */
    @Nullable
    protected ResourceLocation matureInto()
    {
        return null;
    }

    /** Ticks it takes to mature on its own. */
    protected int maturityTime()
    {
        return Integer.MAX_VALUE;
    }

    /** Jelly that makes it mature straight away. */
    protected int maturityJelly()
    {
        return Integer.MAX_VALUE;
    }

    public int jelly()
    {
        return jelly;
    }

    public int hunger()
    {
        return hunger;
    }

    protected void setHunger(int hunger)
    {
        this.hunger = Math.max(0, Math.min(MAX_HUNGER, hunger));
    }

    public void feed(int amount)
    {
        hunger = Math.min(MAX_HUNGER, hunger + amount);
    }

    public boolean isHungry()
    {
        return hunger < org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenoHungryBelow);
    }

    /** Would it go after this creature right now? (Players: see "Xenos Always Hunt Players".) */
    public boolean wantsToHunt(net.minecraft.world.entity.LivingEntity target)
    {
        if (founding)
        {
            return false; // nothing matters but the hive (it still fights back)
        }
        if (!isPrey(target) || PreyCapture.blocksAttack(this, target))
        {
            return false;
        }
        if (target instanceof net.minecraft.world.entity.player.Player)
        {
            return isHungry() || org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenosAlwaysHuntPlayers);
        }
        return isHungry() || foragesFor(target);
    }

    /**
     * A worker Drone of a founded hive also hunts for the hive, full or not: hosts while the hive is
     * short of cocooned hosts, other prey while the larder is low (it brings them back alive).
     */
    private boolean foragesFor(net.minecraft.world.entity.LivingEntity target)
    {
        if (getClass() != DroneEntity.class || !(target instanceof net.minecraft.world.entity.Mob) || target.getType().is(net.minecraft.tags.EntityTypeTags.UNDEAD)
                || target.getBbWidth() > 1.5F || target.getBbHeight() > 2.3F || !(level() instanceof ServerLevel))
        {
            return false;
        }
        org.avp.world.hive.Hive hive = hive();
        if (hive == null)
        {
            return false;
        }
        return org.avp.entity.parasite.FacehuggerEntity.isValidHost(target) ? hive.needsHosts : hive.needsFood;
    }

    /** Eating what it killed: bigger prey fills it up more. */
    @Override
    public boolean killedEntity(ServerLevel level, net.minecraft.world.entity.LivingEntity victim)
    {
        feed(Math.max(5, (int) (victim.getMaxHealth() * 2)));
        feedHeir(level, victim);
        if (victim.hasData(org.avp.registry.ModAttachments.COCOONED))
        {
            leaveRemains(level);
        }
        return super.killedEntity(level, victim);
    }

    @Override
    protected void registerGoals()
    {
        goalSelector.addGoal(4, new EatJellyGoal(this));
    }

    @Override
    public void tick()
    {
        super.tick();
        if (level().isClientSide)
        {
            return;
        }
        age++;
        if (investigateTicks > 0 && --investigateTicks == 0)
        {
            investigatePos = null;
        }
        if (retreatCooldown > 0)
        {
            retreatCooldown--;
        }
        if (poseHold > 0)
        {
            poseHold--;
        }
        if (knownTurretTicks > 0 && --knownTurretTicks == 0)
        {
            knownTurret = null;
        }
        if (tickCount % 40 == Math.floorMod(getId(), 40))
        {
            updatePack();
        }
        if (tickCount % 20 == 0)
        {
            updateStuck();
        }
        // watch for water and lava getting into the hive
        if (bond != null && level() instanceof ServerLevel bonded)
        {
            bond.serverTick(bonded);
        }
        if (tickCount % 40 == Math.floorMod(getId(), 40) && level() instanceof ServerLevel server && hiveId != null && bond == null)
        {
            org.avp.world.hive.Hive hive = hive();
            if (hive != null && blockPosition().distSqr(hive.core()) <= Math.pow(hive.buildRadius(server.getGameTime(),
                    org.avp.world.hive.HiveData.maxRadius()) + 6, 2))
            {
                hive.checkForLeaks(server, blockPosition(), 5);
            }
        }
        if (tickCount % 100 == Math.floorMod(getId(), 100) && level() instanceof ServerLevel server && bond == null)
        {
            updateHive(server);
        }
        if (tickCount % 10 == 0 && org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.smartXenomorphs))
        {
            updateCrawling();
            smashGlass();
        }
        if (tickCount % 10 == 0)
        {
            updateDebugLabel();
            if (getTarget() == null && investigatePos == null && !"RETREATING".equals(state) && !"AVOIDING TURRET".equals(state))
            {
                state = "LURKING";
            }
        }
        // out of combat they slowly heal
        calmTicks = getTarget() == null && hurtTime == 0 ? calmTicks + 1 : 0;
        if (calmTicks > 200 && tickCount % 40 == 0 && getHealth() < getMaxHealth()
                && org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenosRegenerate))
        {
            heal(1.0F);
        }
        // eat royal jelly it walks over
        if (tickCount % 5 == 0)
        {
            for (ItemEntity item : level().getEntitiesOfClass(ItemEntity.class, getBoundingBox().inflate(0.6),
                    e -> e.getItem().is(ModItems.ROYALJELLY.get())))
            {
                jelly += item.getItem().getCount() * JELLY_PER_ITEM;
                feed(10 * item.getItem().getCount());
                playSound(net.minecraft.sounds.SoundEvents.GENERIC_EAT, 0.6F, 1.4F);
                item.discard();
            }
        }
        // hunger: drains slowly; a full Xenomorph stops chasing animals (but still fights back)
        if (age % org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenoHungerDrainTicks) == 0 && hunger > 0 && !founding)
        {
            hunger--;
        }
        if (hunger <= 0 && org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenosStarve) && tickCount % 200 == 0)
        {
            hurt(damageSources().starve(), 1.0F);
        }
        var target = getTarget();
        if (target != null && (PreyCapture.blocksAttack(this, target)
                || tickCount % 20 == 0 && target != getLastHurtByMob() && !wantsToHunt(target)))
        {
            setTarget(null);
        }
        if (tickCount % 20 == 0 && matureInto() != null && (age >= maturityTime() || jelly >= maturityJelly()))
        {
            mature();
        }
    }

    /** Turns into the next form, if that creature exists yet. */
    protected void mature()
    {
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getOptional(matureInto()).orElse(null);
        if (type == null || !(level() instanceof ServerLevel level))
        {
            return; // next form not ported yet: keep waiting
        }
        Entity next = type.create(level, null, blockPosition(), MobSpawnType.CONVERSION, false, false);
        if (next == null)
        {
            return;
        }
        next.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
        if (next instanceof XenomorphEntity xeno && jelly >= maturityJelly())
        {
            xeno.jelly = jelly - maturityJelly(); // leftover jelly carries over
        }
        if (next instanceof XenomorphEntity xeno)
        {
            xeno.hunger = hunger;
            xeno.hiveId = hiveId;
        }
        if (next instanceof Mob mob && hasCustomName())
        {
            mob.setCustomName(getCustomName());
        }
        level.addFreshEntity(next);
        level.sendParticles(ParticleTypes.ITEM_SLIME, getX(), getY() + 0.3, getZ(), 12, 0.3, 0.3, 0.3, 0.05);
        discard();
    }

    // ---------------------------------------------------------------- shared adult behaviour

    /** Who a Xenomorph hunts: anything living that isn't one of them - but never a host carrying an embryo. */
    public static boolean isPrey(net.minecraft.world.entity.LivingEntity target)
    {
        if (!target.isAlive() || target.getType().is(org.avp.registry.ModTags.XENOMORPHS)
                || target instanceof net.minecraft.world.entity.decoration.ArmorStand
                || target.hasData(org.avp.registry.ModAttachments.EMBRYO) || target.hasData(org.avp.registry.ModAttachments.COCOONED)
                || target.getVehicle() instanceof XenomorphEntity
                || target instanceof org.avp.entity.joe.WorkingJoeEntity && org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenosIgnoreJoes))
        {
            return false;
        }
        return !(target instanceof net.minecraft.world.entity.player.Player player) || !player.isCreative() && !player.isSpectator();
    }

    /**
     * The adult Xenomorph AI. With "Smart Xenomorphs" on (default) it's the film-style behaviour in
     * XenoBrain; off, it's the simple 1.12.2 set (SpeciesXenomorph.addStandardXenomorphAISet).
     */
    protected void addHunterGoals(double speed)
    {
        boolean smart = org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.smartXenomorphs);
        goalSelector.addGoal(0, new net.minecraft.world.entity.ai.goal.FloatGoal(this));
        if (smart)
        {
            goalSelector.addGoal(1, new XenoBrain.RetreatGoal(this));
            goalSelector.addGoal(1, new XenoBrain.AvoidTurretGoal(this));
        }
        goalSelector.addGoal(2, new HiveGoals.ExitShaftGoal(this));
        goalSelector.addGoal(2, new net.minecraft.world.entity.ai.goal.LeapAtTargetGoal(this, 0.5F));
        goalSelector.addGoal(3, smart ? new XenoBrain.StalkAndStrikeGoal(this, speed * 1.2)
                : new net.minecraft.world.entity.ai.goal.MeleeAttackGoal(this, speed * 1.15, false));
        if (org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenosBreakDoors))
        {
            if (getNavigation() instanceof net.minecraft.world.entity.ai.navigation.GroundPathNavigation ground)
            {
                ground.setCanOpenDoors(true); // lets it path to doors (it can't open them - it smashes them)
            }
            goalSelector.addGoal(4, new net.minecraft.world.entity.ai.goal.BreakDoorGoal(this, 120, d -> true));
        }
        if (smart)
        {
            goalSelector.addGoal(4, new XenoBrain.InvestigateGoal(this));
            goalSelector.addGoal(2, new XenoBrain.AcidBreachGoal(this));
            goalSelector.addGoal(5, new XenoBrain.BreakLightsGoal(this));
            goalSelector.addGoal(2, new XenoBrain.DigThroughGoal(this));
            goalSelector.addGoal(6, new XenoBrain.FollowLeaderGoal(this));
            goalSelector.addGoal(6, new XenoBrain.LurkGoal(this, speed * 0.8));
        }
        else
        {
            goalSelector.addGoal(6, new net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal(this, speed * 0.8));
        }
        goalSelector.addGoal(5, new HiveGoals.ReturnToHiveGoal(this));
        goalSelector.addGoal(4, new HiveGoals.FeedFromLarderGoal(this));
        goalSelector.addGoal(7, new net.minecraft.world.entity.ai.goal.LookAtPlayerGoal(this, net.minecraft.world.entity.player.Player.class, 16F));
        goalSelector.addGoal(8, new net.minecraft.world.entity.ai.goal.RandomLookAroundGoal(this));

        targetSelector.addGoal(0, new net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal(this, XenomorphEntity.class).setAlertOthers());
        if (smart)
        {
            targetSelector.addGoal(1, new XenoBrain.SensePlayersGoal(this));
        }
        else
        {
            targetSelector.addGoal(1, new net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal<>(this,
                    net.minecraft.world.entity.player.Player.class, 10, true, false, this::wantsToHunt));
        }
        // food: only when hungry (see the hunger meter)
        targetSelector.addGoal(2, new net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal<>(this,
                net.minecraft.world.entity.LivingEntity.class, 20, true, false,
                e -> !(e instanceof net.minecraft.world.entity.player.Player) && wantsToHunt(e)
                        && !(e instanceof net.minecraft.world.entity.monster.Creeper)));
    }

    /** Size in the films compared with the classic 1.12.2 size (1 = same). */
    protected float filmScale()
    {
        return 1F;
    }

    /** "Film-Accurate Sizes": newly spawned creatures get their film size (scales the hitbox too). */
    @Override
    public net.minecraft.world.entity.SpawnGroupData finalizeSpawn(net.minecraft.world.level.ServerLevelAccessor level,
            net.minecraft.world.DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable net.minecraft.world.entity.SpawnGroupData data)
    {
        if (org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.filmAccurateSizes) && filmScale() != 1F)
        {
            var scale = getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.SCALE);
            if (scale != null)
            {
                scale.setBaseValue(filmScale());
            }
        }
        return super.finalizeSpawn(level, difficulty, spawnType, data);
    }

    /** Acid blood: hitting a Xenomorph up close splashes the attacker and eats into their weapon. */
    @Override
    public boolean hurt(net.minecraft.world.damagesource.DamageSource source, float amount)
    {
        boolean hurt = super.hurt(source, amount);
        if (hurt && !level().isClientSide)
        {
            XenoBrain.bleedOnto(this, amount); // acid blood eats into iron doors next to it
        }
        if (hurt && !level().isClientSide && (source.is(org.avp.registry.ModDamageTypes.SENTRY) || source.is(org.avp.registry.ModDamageTypes.SENTRY_AP))
                && knownTurretTicks < 1100)
        {
            XenoBrain.learnTurret(this);
        }
        if (hurt && !level().isClientSide && source.getDirectEntity() instanceof net.minecraft.world.entity.LivingEntity attacker
                && attacker.distanceToSqr(this) < 9 && !attacker.getType().is(org.avp.registry.ModTags.XENOMORPHS))
        {
            double acid = org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenoAcidSplashDamage);
            if (acid > 0)
            {
                attacker.hurt(org.avp.registry.ModDamageTypes.of(level(), org.avp.registry.ModDamageTypes.ACID), (float) acid);
            }
            int eat = org.avp.config.AvpConfig.server(org.avp.config.AvpConfig.SERVER.xenoAcidWeaponDamage);
            net.minecraft.world.item.ItemStack weapon = attacker.getMainHandItem();
            if (eat > 0 && weapon.isDamageableItem())
            {
                weapon.hurtAndBreak(eat, attacker, net.minecraft.world.entity.EquipmentSlot.MAINHAND);
            }
            ((ServerLevel) level()).sendParticles(ParticleTypes.ITEM_SLIME, getX(), getY() + getBbHeight() * 0.6, getZ(), 6, 0.2, 0.2, 0.2, 0.05);
        }
        return hurt;
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, net.minecraft.world.damagesource.DamageSource source)
    {
        return false; // 1.12.2: Xenomorphs take no fall damage
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect)
    {
        return effect.getEffect() != MobEffects.POISON && super.canBeAffected(effect);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.putInt("Jelly", jelly);
        tag.putInt("XenoAge", age);
        tag.putInt("Hunger", hunger);
        if (hiveId != null)
        {
            tag.putUUID("Hive", hiveId);
        }
        if (packLeader != null)
        {
            tag.putUUID("PackLeader", packLeader);
        }
        if (bond != null)
        {
            tag.put("Bond", bond.save());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        jelly = tag.getInt("Jelly");
        age = tag.getInt("XenoAge");
        if (tag.contains("Hunger"))
        {
            hunger = tag.getInt("Hunger");
        }
        packLeader = tag.hasUUID("PackLeader") ? tag.getUUID("PackLeader") : null;
        hiveId = tag.hasUUID("Hive") ? tag.getUUID("Hive") : null;
        if (tag.contains("Bond"))
        {
            bond = org.avp.entity.xenomorph.lab.XenoBond.load(this, tag.getCompound("Bond"));
            bond.install();
        }
    }

    /** Walks to royal jelly it can see nearby (when not busy fighting). */
    static class EatJellyGoal extends Goal
    {
        private final XenomorphEntity xeno;
        private ItemEntity target;

        EatJellyGoal(XenomorphEntity xeno)
        {
            this.xeno = xeno;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            if (xeno.getTarget() != null || xeno.matureInto() == null || xeno.tickCount % 10 >= 2)
            {
                return false;
            }
            List<ItemEntity> jelly = xeno.level().getEntitiesOfClass(ItemEntity.class, xeno.getBoundingBox().inflate(12),
                    e -> e.getItem().is(ModItems.ROYALJELLY.get()) && xeno.hasLineOfSight(e));
            target = jelly.isEmpty() ? null : jelly.get(0);
            return target != null;
        }

        @Override
        public boolean canContinueToUse()
        {
            return target != null && target.isAlive() && !xeno.getNavigation().isDone();
        }

        @Override
        public void start()
        {
            xeno.getNavigation().moveTo(target, 1.1);
        }
    }
}
