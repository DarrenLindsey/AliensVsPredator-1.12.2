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

    @Override
    public boolean isRoyalLine()
    {
        return true;
    }

    /**
     * A Drone from a hive doesn't kill a host it has worn down: it subdues it and carries it home
     * to be cocooned for the eggs (players are always fought).
     */
    private boolean trySubdue(Entity target)
    {
        org.avp.world.hive.Hive hive = hive();
        if (!(target instanceof net.minecraft.world.entity.Mob host) || hive == null || hive.state() != org.avp.world.hive.Hive.State.ACTIVE
                || !AvpConfig.server(AvpConfig.SERVER.xenosCocoonHosts) || isVehicle() || host.isVehicle() || host.isPassenger()
                || !org.avp.entity.parasite.FacehuggerEntity.isValidHost(host) || host.getBbWidth() > 1.5F || host.getBbHeight() > 2.3F
                || host.getHealth() - getAttributeValue(Attributes.ATTACK_DAMAGE) > host.getMaxHealth() * 0.4F)
        {
            return false;
        }
        host.setHealth(Math.max(1.0F, host.getMaxHealth() * 0.2F));
        host.startRiding(this, true);
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
