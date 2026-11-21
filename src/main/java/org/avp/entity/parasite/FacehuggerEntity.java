package org.avp.entity.parasite;

import java.util.EnumSet;

import javax.annotation.Nullable;

import org.avp.config.AvpConfig;
import org.avp.registry.ModAttachments;
import org.avp.registry.ModDamageTypes;
import org.avp.registry.ModSounds;
import org.avp.registry.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Facehugger (1.12.2 EntityFacehugger + EntityParasitoid).
 *
 * Life cycle:
 *  1. Fertile: hunts a host - any living thing big enough that isn't in avp:cannot_host -
 *     climbs walls like a spider and leaps at the host's face.
 *  2. Players wearing a helmet: its acid eats into the helmet first; it only gets through
 *     once the helmet breaks (unless the helmet is in avp:facehugger_proof).
 *  3. On the face: it rides the host, blinds and weakens it, and implants an Embryo.
 *     Hurting it there splashes acid blood on the host.
 *  4. After a while it lets go and drops off, spent (infertile): it lies on its back and dies.
 *
 * The embryo then grows inside the host (see EmbryoEvents) - the Chestburster comes next.
 */
public class FacehuggerEntity extends Monster
{
    private static final int GRIP_TICKS = 60;
    private static final EntityDataAccessor<Boolean> FERTILE = SynchedEntityData.defineId(FacehuggerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(FacehuggerEntity.class, EntityDataSerializers.BOOLEAN);
    /** Network id of the creature whose face it's on (-1 = none). Synced, so the client knows too. */
    private static final EntityDataAccessor<Integer> HOST = SynchedEntityData.defineId(FacehuggerEntity.class, EntityDataSerializers.INT);
    /** The host's UUID, to find it again after the world is reloaded. */
    @Nullable
    private java.util.UUID hostUuid;
    @Nullable
    private java.util.UUID reservedHost;

    private int ticksOnHost;
    private int ticksSpent;
    /** Ticks before it may try to latch on again (after bouncing off a helmet). */
    private int attachCooldown;

    public FacehuggerEntity(EntityType<? extends FacehuggerEntity> type, Level level)
    {
        super(type, level);
        this.xpReward = 10;
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 14.0)
                .add(Attributes.MOVEMENT_SPEED, 0.32)
                .add(Attributes.ATTACK_DAMAGE, 0.5)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.SAFE_FALL_DISTANCE, 64.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(FERTILE, true);
        builder.define(CLIMBING, false);
        builder.define(HOST, -1);
    }

    @Override
    protected void registerGoals()
    {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(2, new LeapAtFaceGoal(this));
        goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, true));
        goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.9));
        goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, this::canHost));
    }

    // ------------------------------------------------------------------ state

    public boolean isFertile()
    {
        return entityData.get(FERTILE);
    }

    public void setFertile(boolean fertile)
    {
        entityData.set(FERTILE, fertile);
    }

    /** The creature whose face it's clamped to, or null. */
    @Nullable
    public LivingEntity host()
    {
        int id = entityData.get(HOST);
        return id >= 0 && level().getEntity(id) instanceof LivingEntity host && host.isAlive() ? host : null;
    }

    public boolean isAttached()
    {
        return entityData.get(HOST) >= 0;
    }

    /** Is a facehugger already on this creature's face? */
    public static boolean hasFacehugger(LivingEntity host)
    {
        return !host.level().getEntitiesOfClass(FacehuggerEntity.class, host.getBoundingBox().inflate(2),
                h -> h.entityData.get(HOST) == host.getId()).isEmpty();
    }

    private void attachTo(LivingEntity host)
    {
        entityData.set(HOST, host.getId());
        hostUuid = host.getUUID();
        setNoGravity(true);
        noPhysics = true;
        ticksOnHost = 0;
        setTarget(null);
        clampToFace(host);
    }

    private void detach()
    {
        entityData.set(HOST, -1);
        hostUuid = null;
        setNoGravity(false);
        noPhysics = false;
    }

    /** Could this creature carry an embryo right now? */
    public boolean canHost(LivingEntity host)
    {
        return host != this && (reservedHost == null || reservedHost.equals(host.getUUID())) && isValidHost(host);
    }

    public void preferCaptive(LivingEntity host)
    {
        reservedHost = host.getUUID();
        setTarget(host);
    }

    /** Shared with the Ovamorph (it opens for the same creatures a facehugger would go for). */
    public static boolean isValidHost(LivingEntity host)
    {
        if (!host.isAlive() || host instanceof FacehuggerEntity || host.getType().is(ModTags.CANNOT_HOST)
                || host.isBaby() || host.hasData(ModAttachments.EMBRYO) || hasFacehugger(host))
        {
            return false;
        }
        if (host instanceof Player player)
        {
            return !player.isCreative() && !player.isSpectator() && AvpConfig.server(AvpConfig.SERVER.facehuggersTargetPlayers)
                    && !player.getItemBySlot(EquipmentSlot.HEAD).is(ModTags.FACEHUGGER_PROOF);
        }
        return true;
    }

    // ------------------------------------------------------------------ ticking

    @Override
    public void tick()
    {
        super.tick();
        LivingEntity host = host();
        if (host != null)
        {
            clampToFace(host);
        }
        if (level().isClientSide)
        {
            return;
        }
        if (isAttached() && host == null)
        {
            // host gone (died, unloaded): find it again by UUID after a reload, else let go
            if (hostUuid != null && level() instanceof net.minecraft.server.level.ServerLevel server
                    && server.getEntity(hostUuid) instanceof LivingEntity found && found.isAlive())
            {
                entityData.set(HOST, found.getId());
                host = found;
            }
            else
            {
                detach();
            }
        }
        if (host == null && reservedHost != null && level() instanceof net.minecraft.server.level.ServerLevel server)
        {
            if (!(server.getEntity(reservedHost) instanceof LivingEntity captive) || !captive.hasData(ModAttachments.COCOONED)
                    || distanceToSqr(captive) > 24 * 24 || !isValidHost(captive))
            {
                reservedHost = null;
                setTarget(null);
            }
            else if (getTarget() != captive)
            {
                setTarget(captive);
            }
        }
        setClimbing(horizontalCollision && isFertile() && !isAttached());
        if (attachCooldown > 0)
        {
            attachCooldown--;
        }
        if (getTarget() != null && !(getTarget() instanceof LivingEntity target && canHost(target)))
        {
            setTarget(null);
        }
        if (host != null)
        {
            tickOnHost(host);
        }
        else if (!isFertile())
        {
            // spent: lies on its back, then dies
            if (++ticksSpent >= AvpConfig.server(AvpConfig.SERVER.facehuggerLifetimeAfter))
            {
                kill();
            }
        }
        else if (attachCooldown <= 0)
        {
            LivingEntity target = getTarget();
            if (target != null && canHost(target) && withinFaceReach(target))
            {
                tryAttach(target);
            }
            else if (reservedHost == null)
            {
                for (LivingEntity candidate : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(1), this::canHost))
                {
                    if (candidate != target && withinFaceReach(candidate))
                    {
                        tryAttach(candidate);
                        break;
                    }
                }
            }
        }
    }

    private boolean withinFaceReach(LivingEntity target)
    {
        return position().add(0, getBbHeight() * 0.5, 0).distanceToSqr(faceAnchor(target)) <= 0.65 * 0.65 && hasLineOfSight(target);
    }

    private void tryAttach(LivingEntity host)
    {
        if (host instanceof Player player)
        {
            ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
            if (!helmet.isEmpty())
            {
                // like Kane's helmet in Alien: the acid eats through the visor first
                helmet.hurtAndBreak(AvpConfig.server(AvpConfig.SERVER.facehuggerHelmetMelt), player, EquipmentSlot.HEAD);
                level().playSound(null, blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.HOSTILE, 0.8F, 1.6F);
                if (!player.getItemBySlot(EquipmentSlot.HEAD).isEmpty())
                {
                    Vec3 away = position().subtract(player.position()).normalize().scale(0.5);
                    setDeltaMovement(away.x, 0.3, away.z);
                    attachCooldown = 40;
                    return;
                }
            }
        }
        attachTo(host);
    }

    /** The embryo this parasite plants: "standard" (the host decides), "queen" for a Royal Facehugger... */
    protected String embryoKind()
    {
        return EmbryoKinds.STANDARD;
    }

    private void implant(LivingEntity host)
    {
        host.setData(ModAttachments.EMBRYO, new Embryo(0, AvpConfig.server(AvpConfig.SERVER.embryoGestationTicks), EmbryoKinds.forHost(host, embryoKind())));
        playSound(ModSounds.FACEHUGGER_IMPLANT.get(), 0.6F, 1.0F);
        setFertile(false);
        reservedHost = null;
        ticksOnHost = 0;
        setTarget(null);
    }

    private void tickOnHost(LivingEntity host)
    {
        if (!host.isAlive() || host instanceof Player player && (player.isCreative() || player.isSpectator()
                || isFertile() && player.getItemBySlot(EquipmentSlot.HEAD).is(ModTags.FACEHUGGER_PROOF))
                || isFertile() && host.hasData(ModAttachments.EMBRYO))
        {
            if (host instanceof Player player && player.isCreative())
            {
                host.removeData(ModAttachments.EMBRYO);
            }
            detach();
            return;
        }
        // the host is out cold: can't see, barely moves
        if (tickCount % 20 == 0)
        {
            host.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0, false, false));
            host.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4, false, false));
            host.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 2, false, false));
        }
        if (host instanceof Mob mob)
        {
            mob.getNavigation().stop();
            mob.setTarget(null);
        }
        ticksOnHost++;
        if (isFertile() && ticksOnHost >= GRIP_TICKS)
        {
            implant(host);
        }
        else if (!isFertile() && ticksOnHost >= AvpConfig.server(AvpConfig.SERVER.facehuggerAttachTicks))
        {
            detach();
            setDeltaMovement(host.getLookAngle().scale(0.2).add(0, 0.2, 0));
        }
    }

    /** Face position shared by the leap, contact check and attached pose. */
    public static Vec3 faceAnchor(LivingEntity host)
    {
        float yaw = host.getYHeadRot();
        Vec3 anchor;
        if (host instanceof Cow)
        {
            // Vanilla's cow head pivots eight model pixels forward of the torso. Its face
            // is another six pixels forward and follows head yaw and pitch from that pivot.
            float pitch = host.getXRot();
            Vec3 neck = Vec3.directionFromRotation(0, host.yBodyRot).scale(8.0 / 16.0).add(0, 20.0 / 16.0, 0);
            Vec3 muzzle = Vec3.directionFromRotation(pitch, yaw).scale(6.6 / 16.0)
                    .add(Vec3.directionFromRotation(pitch + 90F, yaw).scale(1.0 / 16.0));
            anchor = host.position().add(neck.add(muzzle).scale(host.getScale()));
        }
        else
        {
            Vec3 look = Vec3.directionFromRotation(0, yaw);
            anchor = new Vec3(host.getX(), host.getEyeY() - 0.1, host.getZ())
                    .add(look.scale(host.getBbWidth() / 2 + 0.05));
        }
        return anchor;
    }

    private void clampToFace(LivingEntity host)
    {
        Vec3 anchor = faceAnchor(host);
        setPos(anchor.x, anchor.y, anchor.z);
        setYRot(host.getYHeadRot() + 180F);
        yRotO = getYRot();
        yBodyRot = getYRot();
        yBodyRotO = getYRot();
        yHeadRot = getYRot();
        yHeadRotO = getYRot();
        setDeltaMovement(Vec3.ZERO);
        fallDistance = 0;
    }

    @Override
    public boolean isPushable()
    {
        return !isAttached() && super.isPushable();
    }

    @Override
    protected void doPush(Entity entity)
    {
        if (!isAttached())
        {
            super.doPush(entity);
        }
    }

    // ------------------------------------------------------------------ behaviour tweaks

    /** No AI once it's spent or on a face. */
    @Override
    protected boolean isImmobile()
    {
        return super.isImmobile() || !isFertile() || isAttached();
    }

    @Override
    public boolean doHurtTarget(Entity target)
    {
        return false; // it doesn't bite: it latches on (see tick)
    }

    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        if (isAttached() && source.is(net.minecraft.world.damagesource.DamageTypes.IN_WALL))
        {
            return false;
        }
        boolean hurt = super.hurt(source, amount);
        LivingEntity host = host();
        if (hurt && !level().isClientSide && host != null)
        {
            double acid = AvpConfig.server(AvpConfig.SERVER.acidBloodDamage);
            if (acid > 0)
            {
                host.hurt(ModDamageTypes.of(level(), ModDamageTypes.ACID), (float) acid);
                level().playSound(null, host.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.HOSTILE, 0.6F, 1.8F);
            }
            if (isAlive() && isFertile())
            {
                detach();
                attachCooldown = 60;
                setDeltaMovement(host.getLookAngle().scale(0.4).add(0, 0.25, 0));
            }
        }
        return hurt;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect)
    {
        return effect.getEffect() != MobEffects.POISON && super.canBeAffected(effect);
    }

    @Override
    protected PathNavigation createNavigation(Level level)
    {
        return new WallClimberNavigation(this, level);
    }

    @Override
    public boolean onClimbable()
    {
        return entityData.get(CLIMBING);
    }

    private void setClimbing(boolean climbing)
    {
        entityData.set(CLIMBING, climbing);
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, DamageSource source)
    {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distance)
    {
        return isFertile() && !isAttached() && super.removeWhenFarAway(distance);
    }

    // ------------------------------------------------------------------ sounds

    @Nullable
    @Override
    protected SoundEvent getAmbientSound()
    {
        return isFertile() && !isAttached() ? ModSounds.FACEHUGGER_LIVING.get() : null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return ModSounds.FACEHUGGER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return ModSounds.FACEHUGGER_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state)
    {
        playSound(SoundEvents.SPIDER_STEP, 0.06F, 3.0F);
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.6F;
    }

    // ------------------------------------------------------------------ saving

    @Override
    public void addAdditionalSaveData(CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Fertile", isFertile());
        tag.putInt("TicksOnHost", ticksOnHost);
        tag.putInt("TicksSpent", ticksSpent);
        if (hostUuid != null)
        {
            tag.putUUID("Host", hostUuid);
        }
        if (reservedHost != null)
        {
            tag.putUUID("ReservedHost", reservedHost);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Fertile"))
        {
            setFertile(tag.getBoolean("Fertile"));
        }
        ticksOnHost = tag.getInt("TicksOnHost");
        ticksSpent = tag.getInt("TicksSpent");
        reservedHost = tag.hasUUID("ReservedHost") ? tag.getUUID("ReservedHost") : null;
        if (tag.hasUUID("Host"))
        {
            hostUuid = tag.getUUID("Host");
            entityData.set(HOST, Integer.MAX_VALUE); // "attached, host to be found" until the host loads
            setNoGravity(true);
            noPhysics = true;
        }
    }

    // ------------------------------------------------------------------ AI

    /** 1.12.2 EntityAIFacehuggerLeap: from up to 8 blocks away, spring at the target's face. */
    static class LeapAtFaceGoal extends Goal
    {
        private final FacehuggerEntity hugger;
        private int cooldown;

        LeapAtFaceGoal(FacehuggerEntity hugger)
        {
            this.hugger = hugger;
            setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            if (cooldown > 0)
            {
                cooldown--;
                return false;
            }
            LivingEntity target = hugger.getTarget();
            if (target == null || !hugger.onGround() || !hugger.isFertile())
            {
                return false;
            }
            double distance = hugger.distanceToSqr(target);
            return hugger.attachCooldown == 0 && distance < 8 * 8 && hugger.hasLineOfSight(target);
        }

        @Override
        public boolean canContinueToUse()
        {
            return false;
        }

        @Override
        public void start()
        {
            LivingEntity target = hugger.getTarget();
            Vec3 to = faceAnchor(target).subtract(hugger.position());
            Vec3 flat = new Vec3(to.x, 0, to.z).normalize();
            double strength = Math.min(1.2, Math.sqrt(to.x * to.x + to.z * to.z) * 0.18);
            hugger.setDeltaMovement(flat.x * strength, 0.35 + Math.max(0, to.y) * 0.14, flat.z * strength);
            hugger.getLookControl().setLookAt(target);
            cooldown = 50 + hugger.getRandom().nextInt(30);
        }
    }
}
