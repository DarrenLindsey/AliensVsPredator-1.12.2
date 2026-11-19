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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Chestburster (1.12.2 EntityChestburster): what comes out of an embryo.
 *
 * Newborn and weak, it runs from players and hides, picks on small animals, fights back if
 * cornered, climbs walls, and eats royal jelly. After a while (or enough jelly) it moults
 * into a Drone - once the Drone is ported; until then it just keeps growing.
 */
public class ChestbursterEntity extends XenomorphEntity
{
    public static final ResourceLocation DRONE = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "drone");
    private static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(ChestbursterEntity.class, EntityDataSerializers.BOOLEAN);

    public ChestbursterEntity(EntityType<? extends ChestbursterEntity> type, Level level)
    {
        super(type, level);
        this.xpReward = 16;
        setHunger(20); // newborns come out starving
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 14.0)
                .add(Attributes.MOVEMENT_SPEED, 0.34)
                .add(Attributes.ATTACK_DAMAGE, 2.0)
                .add(Attributes.FOLLOW_RANGE, 24.0)
                .add(Attributes.SAFE_FALL_DISTANCE, 16.0);
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
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 10.0F, 1.0, 1.35, e -> !e.isSpectator() && !((Player) e).isCreative()));
        goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
        goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, true));
        goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.9));
        goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        // newborns feed on small animals
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Animal.class, 20, true, false,
                e -> e.getBbHeight() < 1.0F && wantsToHunt(e)));
    }

    /** Set by the embryo when the host decides the adult form (a creeper host grows a Boiler...). */
    @javax.annotation.Nullable
    private ResourceLocation adult;

    public void setAdult(@javax.annotation.Nullable ResourceLocation adult)
    {
        this.adult = adult;
    }

    /** What this kind of chestburster grows into when the host didn't decide. */
    protected ResourceLocation defaultAdult()
    {
        return DRONE;
    }

    @Override
    protected ResourceLocation matureInto()
    {
        return adult != null ? adult : defaultAdult();
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        if (adult != null)
        {
            tag.putString("Adult", adult.toString());
        }
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        adult = tag.contains("Adult") ? ResourceLocation.tryParse(tag.getString("Adult")) : null;
    }

    @Override
    protected int maturityTime()
    {
        return AvpConfig.server(AvpConfig.SERVER.chestbursterMatureTicks);
    }

    @Override
    protected int maturityJelly()
    {
        return 6400; // 1.12.2: 64 royal jelly
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

    /** 1.12.2: chestbursters can't drown. */
    @Override
    public boolean canDrownInFluidType(net.neoforged.neoforge.fluids.FluidType type)
    {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return ModSounds.CHESTBURSTER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return ModSounds.CHESTBURSTER_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state)
    {
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target)
    {
        boolean hit = super.doHurtTarget(target);
        if (hit)
        {
            playSound(ModSounds.CHESTBURSTER_ATTACK.get(), 0.7F, 1.0F);
        }
        return hit;
    }

    /** Hissing as it comes out (played by the embryo burst). */
    public static SoundEvent emergeSound()
    {
        return ModSounds.CHESTBURSTER_BURST.get();
    }

    @Override
    public boolean removeWhenFarAway(double distance)
    {
        return false; // it came out of someone - keep it
    }

    /** It never goes looking for players - it only turns on one that hurt it. */
    @Override
    public void setTarget(LivingEntity target)
    {
        super.setTarget(target instanceof Player && getLastHurtByMob() != target ? null : target);
    }
}
