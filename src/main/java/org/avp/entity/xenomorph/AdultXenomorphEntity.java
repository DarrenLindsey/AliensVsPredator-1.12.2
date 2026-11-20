package org.avp.entity.xenomorph;

import java.util.EnumSet;

import javax.annotation.Nullable;

import org.avp.AliensVsPredator;
import org.avp.config.AvpConfig;
import org.avp.entity.creature.Ability;
import org.avp.entity.creature.CreatureAbilities;
import org.avp.entity.creature.CreatureProjectile;
import org.avp.entity.creature.CreatureSpec;
import org.avp.entity.creature.Creatures;
import org.avp.registry.ModEntities;
import org.avp.registry.ModSounds;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Every adult Xenomorph other than the Drone: Warrior, Praetorian, Queen, Crusher, Spitter,
 * Boiler, Runners, Predalien, Ultramorph, the host-shaped variants... One class: what each one
 * is and can do comes from its catalog entry (Creatures), on top of the shared hive behaviour
 * of XenomorphEntity (senses, stalking, packs, hunger, acid blood, maturing).
 */
public class AdultXenomorphEntity extends XenomorphEntity
{
    private static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(AdultXenomorphEntity.class, EntityDataSerializers.BOOLEAN);
    private CreatureSpec spec;
    @Nullable
    private ServerBossEvent bossBar;
    private int burstFuse = -1;
    private boolean burst;
    private int eggTimer;

    public AdultXenomorphEntity(EntityType<? extends AdultXenomorphEntity> type, Level level)
    {
        super(type, level);
        this.xpReward = spec().xp();
        if (has(Ability.FLY))
        {
            this.moveControl = new FlyingMoveControl(this, 20, true);
        }
        if (has(Ability.BOSS) && !level.isClientSide)
        {
            bossBar = new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);
        }
    }

    /** Its catalog entry (works during construction too). */
    public CreatureSpec spec()
    {
        if (spec == null)
        {
            spec = Creatures.get(getType());
        }
        return spec;
    }

    public boolean has(Ability ability)
    {
        return spec() != null && spec().has(ability);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(CLIMBING, false);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        addHunterGoals(1.0);
        if (has(Ability.AQUATIC) || has(Ability.FLY))
        {
            goalSelector.removeAllGoals(g -> g instanceof FloatGoal); // swimmers dive and fliers don't need it
        }
        if (has(Ability.SPIT_ACID))
        {
            goalSelector.addGoal(2, new CreatureAbilities.ShootGoal(this, CreatureProjectile.Kind.ACID,
                    (float) spec().damage() * 0.7F, 4, 20, 40, 80, 1));
        }
        if (has(Ability.CHARGE))
        {
            goalSelector.addGoal(2, new CreatureAbilities.ChargeGoal(this));
        }
        if (has(Ability.GUARD_QUEEN))
        {
            goalSelector.addGoal(5, new GuardQueenGoal(this));
        }
    }

    @Override
    public boolean isQueen()
    {
        return has(Ability.QUEEN);
    }

    /** Warriors and Praetorians are in line for the throne (their growth leads to a Queen). */
    @Override
    public boolean isRoyalLine()
    {
        for (CreatureSpec s = spec(); s != null && s.matureInto() != null; s = Creatures.ALL.get(s.matureInto()))
        {
            CreatureSpec next = Creatures.ALL.get(s.matureInto());
            if (next != null && next.has(Ability.QUEEN))
            {
                return true;
            }
        }
        return false;
    }

    // ---------------------------------------------------------------- movement

    @Override
    protected PathNavigation createNavigation(Level level)
    {
        if (has(Ability.FLY))
        {
            FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
            nav.setCanOpenDoors(false);
            nav.setCanFloat(true);
            return nav;
        }
        if (has(Ability.AQUATIC))
        {
            return new AmphibiousPathNavigation(this, level);
        }
        return has(Ability.CLIMB) ? new WallClimberNavigation(this, level) : super.createNavigation(level);
    }

    @Override
    protected boolean canCrawl()
    {
        return has(Ability.CRAWL);
    }

    @Override
    public boolean onClimbable()
    {
        return entityData.get(CLIMBING);
    }

    @Override
    public boolean canDrownInFluidType(net.neoforged.neoforge.fluids.FluidType type)
    {
        return !has(Ability.AQUATIC) && super.canDrownInFluidType(type);
    }

    @Override
    public boolean isPushedByFluid()
    {
        return !has(Ability.AQUATIC);
    }

    @Override
    public void aiStep()
    {
        super.aiStep();
        LivingEntity target = getTarget();
        if (has(Ability.AQUATIC) && isInWater() && target != null)
        {
            // fast through the water, straight at its prey
            Vec3 to = target.position().add(0, target.getBbHeight() * 0.5, 0).subtract(position()).normalize();
            setDeltaMovement(getDeltaMovement().scale(0.9).add(to.scale(0.05)));
        }
        if (has(Ability.FLY) && target != null && !onGround())
        {
            Vec3 to = target.getEyePosition().subtract(position());
            if (to.lengthSqr() > 4)
            {
                getMoveControl().setWantedPosition(target.getX(), target.getY() + 1.0, target.getZ(), 1.2);
            }
        }
    }

    // ---------------------------------------------------------------- abilities

    @Override
    public void tick()
    {
        super.tick();
        if (level().isClientSide)
        {
            return;
        }
        entityData.set(CLIMBING, has(Ability.CLIMB) && wantsToClimb());
        if (has(Ability.ACID_BURST))
        {
            tickBurst();
        }
        if (has(Ability.QUEEN))
        {
            tickQueen();
        }
    }

    /** Boiler: close to its prey it swells up (1 s) and bursts. */
    private void tickBurst()
    {
        LivingEntity target = getTarget();
        if (burstFuse < 0)
        {
            if (target != null && distanceTo(target) < 2.5 && hasLineOfSight(target))
            {
                burstFuse = 20;
                playSound(net.minecraft.sounds.SoundEvents.CREEPER_PRIMED, 1.0F, 0.6F);
            }
        }
        else if (--burstFuse <= 0)
        {
            burst();
        }
    }

    private void burst()
    {
        if (burst)
        {
            return;
        }
        burst = true;
        CreatureAbilities.acidBurst(this, (float) spec().damage() * 2F, 4.0);
        if (isAlive())
        {
            hurt(damageSources().genericKill(), Float.MAX_VALUE);
        }
    }

    public float burstProgress(float partialTick)
    {
        return burstFuse < 0 ? 0 : (20 - burstFuse + partialTick) / 20F;
    }

    @Override
    public void die(DamageSource source)
    {
        if (has(Ability.ACID_BURST) && !level().isClientSide)
        {
            burst(); // a Boiler that dies still bursts
        }
        super.die(source);
    }

    /** The Queen lays eggs while well fed and not fighting, up to a dozen nearby. */
    private void tickQueen()
    {
        if (bossBar != null)
        {
            bossBar.setProgress(getHealth() / getMaxHealth());
        }
        if (getTarget() != null || hunger() < 40 || ++eggTimer < AvpConfig.server(AvpConfig.SERVER.queenEggTicks))
        {
            return;
        }
        eggTimer = 0;
        if (level().getEntitiesOfClass(OvamorphEntity.class, getBoundingBox().inflate(16)).size() >= 12)
        {
            return;
        }
        OvamorphEntity egg = ModEntities.OVAMORPH.get().create(level());
        if (egg != null)
        {
            // next to a cocooned host if the hive has one (the egg opens for it), else behind her
            Vec3 at = position().add(Vec3.directionFromRotation(0, yBodyRot).scale(-2.5));
            java.util.List<LivingEntity> cocooned = level().getEntitiesOfClass(LivingEntity.class,
                    getBoundingBox().inflate(16), m -> m.hasData(org.avp.registry.ModAttachments.COCOONED)
                            && org.avp.entity.parasite.FacehuggerEntity.isValidHost(m));
            if (!cocooned.isEmpty())
            {
                LivingEntity host = cocooned.get(random.nextInt(cocooned.size()));
                at = host.position().add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
            }
            egg.moveTo(at.x, at.y, at.z, random.nextFloat() * 360F, 0);
            level().addFreshEntity(egg);
            setHunger(hunger() - 15);
            playSound(net.minecraft.sounds.SoundEvents.SLIME_SQUISH, 1.0F, 0.5F);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        boolean hurt = super.hurt(source, amount);
        // the Queen calls the hive to her
        if (hurt && has(Ability.QUEEN) && !level().isClientSide && source.getEntity() instanceof LivingEntity attacker
                && !attacker.getType().is(org.avp.registry.ModTags.XENOMORPHS))
        {
            for (XenomorphEntity xeno : level().getEntitiesOfClass(XenomorphEntity.class, getBoundingBox().inflate(64), x -> x != this))
            {
                xeno.setTarget(attacker);
            }
        }
        return hurt;
    }

    @Override
    public boolean doHurtTarget(Entity target)
    {
        boolean hit = super.doHurtTarget(target);
        if (hit)
        {
            SoundEvent attack = ModSounds.creature(spec().sounds(), "attack");
            playSound(attack != null ? attack : ModSounds.ALIEN_ATTACK.get(), 0.8F, 0.9F + random.nextFloat() * 0.2F);
            if (has(Ability.IMPLANT_ON_BITE) && target instanceof LivingEntity victim && spec().embryo() != null)
            {
                CreatureAbilities.implantOnBite(this, victim, spec().embryo(), 0.25F);
            }
        }
        return hit;
    }

    // ---------------------------------------------------------------- growing up

    @Nullable
    @Override
    protected ResourceLocation matureInto()
    {
        return spec().matureInto() == null ? null : ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, spec().matureInto());
    }

    @Nullable
    private CreatureSpec next()
    {
        return spec().matureInto() == null ? null : Creatures.ALL.get(spec().matureInto());
    }

    /** Small forms grow up in a Drone's time; big ones take three times as long; a Queen only comes from royal jelly. */
    @Override
    protected int maturityTime()
    {
        CreatureSpec next = next();
        if (next == null || next.has(Ability.QUEEN))
        {
            return Integer.MAX_VALUE;
        }
        int drone = AvpConfig.server(AvpConfig.SERVER.droneMatureTicks);
        return next.health() <= 60 ? drone : drone * 3;
    }

    @Override
    protected int maturityJelly()
    {
        CreatureSpec next = next();
        if (next == null)
        {
            return Integer.MAX_VALUE;
        }
        return next.has(Ability.QUEEN) ? 51_200 : next.health() <= 60 ? 12_800 : 25_600;
    }

    /** Only one Queen to a hive. */
    @Override
    protected void mature()
    {
        CreatureSpec next = next();
        if (next != null && next.has(Ability.QUEEN) && !level().getEntitiesOfClass(AdultXenomorphEntity.class, getBoundingBox().inflate(64),
                x -> x.has(Ability.QUEEN)).isEmpty())
        {
            return;
        }
        // a Queen grows in the hive chamber (there's no room for her in the tunnels)
        org.avp.world.hive.Hive hive = hive();
        if (next != null && next.has(Ability.QUEEN) && hive != null && hive.state() == org.avp.world.hive.Hive.State.ACTIVE
                && blockPosition().distSqr(hive.core()) > 36)
        {
            return;
        }
        super.mature();
    }

    /** Queens (and boss forms) never spawn naturally: they're raised by a hive (royal jelly / succession) or a Royal Facehugger. */
    @Override
    public boolean checkSpawnRules(net.minecraft.world.level.LevelAccessor level, MobSpawnType reason)
    {
        if ((has(Ability.QUEEN) || has(Ability.BOSS)) && (reason == MobSpawnType.NATURAL || reason == MobSpawnType.CHUNK_GENERATION
                || reason == MobSpawnType.STRUCTURE || reason == MobSpawnType.PATROL || reason == MobSpawnType.REINFORCEMENT))
        {
            return false;
        }
        return super.checkSpawnRules(level, reason);
    }

    // ---------------------------------------------------------------- boss bar, sounds

    @Override
    public void startSeenByPlayer(ServerPlayer player)
    {
        super.startSeenByPlayer(player);
        if (bossBar != null)
        {
            bossBar.addPlayer(player);
        }
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player)
    {
        super.stopSeenByPlayer(player);
        if (bossBar != null)
        {
            bossBar.removePlayer(player);
        }
    }

    @Override
    public boolean removeWhenFarAway(double distance)
    {
        return !has(Ability.QUEEN) && !has(Ability.BOSS) && super.removeWhenFarAway(distance);
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        SoundEvent s = ModSounds.creature(spec().sounds(), "living");
        return s != null ? s : ModSounds.ALIEN_LIVING.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        SoundEvent s = ModSounds.creature(spec().sounds(), "hurt");
        return s != null ? s : ModSounds.ALIEN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        SoundEvent s = ModSounds.creature(spec().sounds(), "death");
        return s != null ? s : ModSounds.ALIEN_DEATH.get();
    }

    @Override
    protected float getSoundVolume()
    {
        return has(Ability.QUEEN) || has(Ability.BOSS) ? 2.0F : 1.0F;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state)
    {
        playSound(net.minecraft.sounds.SoundEvents.SPIDER_STEP, 0.15F, spec().health() > 90 ? 0.5F : 0.7F);
    }

    @Override
    public int getAmbientSoundInterval()
    {
        return 200;
    }

    /** Praetorian: stays near its Queen. */
    static class GuardQueenGoal extends Goal
    {
        private final AdultXenomorphEntity guard;
        @Nullable
        private AdultXenomorphEntity queen;

        GuardQueenGoal(AdultXenomorphEntity guard)
        {
            this.guard = guard;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            if (guard.getTarget() != null || guard.tickCount % 20 >= 2)
            {
                return false;
            }
            queen = guard.level().getEntitiesOfClass(AdultXenomorphEntity.class, guard.getBoundingBox().inflate(48), x -> x.has(Ability.QUEEN))
                    .stream().findFirst().orElse(null);
            return queen != null && guard.distanceTo(queen) > 10;
        }

        @Override
        public boolean canContinueToUse()
        {
            return queen != null && queen.isAlive() && guard.getTarget() == null && guard.distanceTo(queen) > 5;
        }

        @Override
        public void tick()
        {
            if (guard.tickCount % 10 < 2)
            {
                guard.getNavigation().moveTo(queen, 1.0);
            }
        }
    }
}
