package org.avp.entity.xenomorph;

import org.avp.AliensVsPredator;
import org.avp.config.AvpConfig;
import org.avp.registry.ModSounds;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Drone (1.12.2 EntityDrone): the first adult Xenomorph - what a Chestburster grows into.
 *
 * Hunts players and animals (never other Xenomorphs, and never a host carrying an embryo),
 * climbs walls, leaps, calls nearby Xenomorphs when attacked, bleeds acid on whoever hits it,
 * and eats royal jelly. Later it will build hive resin and mature into a Warrior.
 */
public class DroneEntity extends XenomorphEntity
{
    public static final ResourceLocation WARRIOR = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "warrior");
    private static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(DroneEntity.class, EntityDataSerializers.BOOLEAN);

    public DroneEntity(EntityType<? extends DroneEntity> type, Level level)
    {
        super(type, level);
        this.xpReward = 30;
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.ARMOR, 4.0)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75)
                .add(Attributes.SAFE_FALL_DISTANCE, 64.0);
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
        goalSelector.addGoal(1, new HiveGoals.CarryHostGoal(this));
        goalSelector.addGoal(5, new HiveGoals.BurrowGoal(this));
        goalSelector.addGoal(5, new HiveGoals.BuildResinGoal(this));
        goalSelector.addGoal(5, new HiveGoals.DigHiveGoal(this));
        goalSelector.addGoal(4, new HiveGoals.BuildHiveGoal(this));
        goalSelector.addGoal(4, new HiveGoals.CoatWallsGoal(this));
        goalSelector.addGoal(3, new HiveGoals.CarryEggGoal(this));
    }

    @Override
    protected ResourceLocation matureInto()
    {
        return WARRIOR;
    }

    @Override
    protected int maturityTime()
    {
        return AvpConfig.server(AvpConfig.SERVER.droneMatureTicks);
    }

    @Override
    protected int maturityJelly()
    {
        return 12_800;
    }

    @Override
    protected boolean canCrawl()
    {
        return true;
    }

    /** Film Drones stand taller than the classic ones. */
    @Override
    protected float filmScale()
    {
        return 1.25F;
    }

    @Override
    public void tick()
    {
        super.tick();
        if (!level().isClientSide && tickCount % 100 == 37 && level() instanceof net.minecraft.server.level.ServerLevel server)
        {
            takeUpCaste(server);
            if (isRemoved())
            {
                return;
            }
        }
        if (!level().isClientSide)
        {
            entityData.set(CLIMBING, wantsToClimb());
        }
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

    private boolean forceFound;

    /** The founding digger died: the nearest free Drone takes over the tunnel straight away. */
    @Override
    public void die(net.minecraft.world.damagesource.DamageSource source)
    {
        super.die(source);
        org.avp.world.hive.Hive hive = hive();
        if (hive == null || hive.state() != org.avp.world.hive.Hive.State.FOUNDING || !getUUID().equals(hive.digger())
                || !(level() instanceof net.minecraft.server.level.ServerLevel server))
        {
            return;
        }
        DroneEntity next = server.getEntitiesOfClass(DroneEntity.class, getBoundingBox().inflate(128),
                d -> d != this && d.isAlive() && (d.hiveId() == null || d.hiveId().equals(hive.id())))
                .stream().min(java.util.Comparator.comparingDouble(this::distanceToSqr)).orElse(null);
        hive.setDigger(null);
        if (next != null)
        {
            next.setHiveId(hive.id());
            next.setTarget(null);
            hive.setDigger(next.getUUID());
            next.setState("TAKING OVER THE DIG");
        }
        org.avp.world.hive.HiveData.get(server).changed();
    }

    /** Debug (/avp hive found): start founding a new hive right now, ignoring the hive spacing. */
    public void forceFoundHive()
    {
        forceFound = true;
        setHiveId(null);
        setTarget(null);
    }

    boolean takeForceFound()
    {
        boolean force = forceFound;
        forceFound = false;
        return force;
    }

    /** Its job in its hive: a plain Drone is a worker; Builder and Coater Drones are their own castes. */
    public String job()
    {
        return hive() == null ? "" : "WORKER";
    }

    /**
     * A founded hive keeps a fixed number of Builder and Coater Drones. When it's short of one, a
     * plain worker Drone (not the heir) changes into it - for good.
     */
    private void takeUpCaste(net.minecraft.server.level.ServerLevel level)
    {
        org.avp.world.hive.Hive hive = hive();
        if (getClass() != DroneEntity.class || hive == null || hive.state() != org.avp.world.hive.Hive.State.ACTIVE
                || getUUID().equals(hive.heir()) || getTarget() != null || isVehicle())
        {
            return;
        }
        java.util.List<DroneEntity> castes = level.getEntitiesOfClass(DroneEntity.class,
                new net.minecraft.world.phys.AABB(hive.core()).inflate(org.avp.world.hive.HiveData.maxRadius() + 32),
                d -> d.isAlive() && hive.id().equals(d.hiveId()) && d.getClass() != DroneEntity.class);
        long builders = castes.stream().filter(d -> d instanceof BuilderDroneEntity).count();
        long coaters = castes.stream().filter(d -> d instanceof CoaterDroneEntity).count();
        net.minecraft.world.entity.EntityType<? extends DroneEntity> next =
                builders < AvpConfig.server(AvpConfig.SERVER.hiveBuilders) ? org.avp.registry.ModEntities.BUILDER_DRONE.get()
                : coaters < AvpConfig.server(AvpConfig.SERVER.hiveCoaters) ? org.avp.registry.ModEntities.COATER_DRONE.get() : null;
        if (next == null)
        {
            return;
        }
        DroneEntity morph = next.create(level);
        if (morph == null)
        {
            return;
        }
        morph.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
        morph.setHiveId(hiveId());
        morph.feed(hunger() - morph.hunger());
        morph.setHealth(morph.getMaxHealth() * getHealth() / getMaxHealth());
        if (hasCustomName() && !isCustomNameVisible())
        {
            morph.setCustomName(getCustomName());
        }
        level.addFreshEntity(morph);
        level.sendParticles(net.minecraft.core.particles.ParticleTypes.ITEM_SLIME, getX(), getY() + 1, getZ(), 30, 0.4, 0.6, 0.4, 0.05);
        level.playSound(null, blockPosition(), net.minecraft.sounds.SoundEvents.SLIME_SQUISH, net.minecraft.sounds.SoundSource.HOSTILE, 1.0F, 0.5F);
        discard();
    }

    /** Builders lay hivestone floors, bridges and seals. */
    public boolean isBuilder()
    {
        return this instanceof BuilderDroneEntity && hive() != null;
    }

    /** Coaters climb the walls and ceilings and spray resin over them. */
    public boolean isCoater()
    {
        return this instanceof CoaterDroneEntity && hive() != null;
    }

    @Override
    public boolean isRoyalLine()
    {
        return true;
    }

    /** Set while it lets go of what it carried (so a carried player's dismount isn't blocked). */
    public boolean releasing;

    /**
     * A Drone from a hive doesn't kill prey it has worn down: it subdues it and carries it home -
     * hosts to be cocooned for the eggs, and (with the larder on) other prey as food. Players too,
     * with "Cocoon Players". A starving Drone just kills and eats.
     */
    private boolean trySubdue(Entity target)
    {
        org.avp.world.hive.Hive hive = hive();
        if (!(target instanceof net.minecraft.world.entity.LivingEntity prey) || hive == null || hive.state() != org.avp.world.hive.Hive.State.ACTIVE
                || hunger() < 20 || isVehicle() || prey.isVehicle() || prey.isPassenger()
                || prey.hasData(org.avp.registry.ModAttachments.COCOONED) || prey.getBbWidth() > 1.5F || prey.getBbHeight() > 2.3F
                || prey.getHealth() - getAttributeValue(Attributes.ATTACK_DAMAGE) > prey.getMaxHealth() * 0.4F)
        {
            return false;
        }
        boolean host = org.avp.entity.parasite.FacehuggerEntity.isValidHost(prey);
        if (prey instanceof net.minecraft.world.entity.player.Player player)
        {
            if (!AvpConfig.server(AvpConfig.SERVER.xenosCocoonPlayers) || player.isCreative() || player.isSpectator())
            {
                return false;
            }
            player.displayClientMessage(net.minecraft.network.chat.Component.translatable("message.avp.captured"), true);
        }
        else if (!(prey instanceof net.minecraft.world.entity.Mob) || !(host ? AvpConfig.server(AvpConfig.SERVER.xenosCocoonHosts)
                : AvpConfig.server(AvpConfig.SERVER.hiveLarder)) || prey.getType().is(org.avp.registry.ModTags.XENOMORPHS)
                || prey.getType().is(net.minecraft.tags.EntityTypeTags.UNDEAD))
        {
            return false;
        }
        prey.setHealth(Math.max(1.0F, prey.getMaxHealth() * 0.2F));
        // too weak to fight back while carried
        prey.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.WEAKNESS, 600, 4, false, false));
        prey.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.DIG_SLOWDOWN, 600, 4, false, false));
        prey.startRiding(this, true);
        playSound(ModSounds.ALIEN_ATTACK.get(), 0.8F, 0.7F);
        setTarget(null);
        return true;
    }

    @Override
    public boolean doHurtTarget(Entity target)
    {
        if (!level().isClientSide && trySubdue(target))
        {
            return true;
        }
        boolean hit = super.doHurtTarget(target);
        if (hit)
        {
            playSound(ModSounds.ALIEN_ATTACK.get(), 0.8F, 0.9F + random.nextFloat() * 0.2F);
        }
        return hit;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return ModSounds.ALIEN_LIVING.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return ModSounds.ALIEN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return ModSounds.ALIEN_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state)
    {
        playSound(net.minecraft.sounds.SoundEvents.SPIDER_STEP, 0.15F, 0.7F);
    }

    @Override
    public int getAmbientSoundInterval()
    {
        return 200;
    }
}
