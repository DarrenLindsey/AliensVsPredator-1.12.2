package org.avp.entity.yautja;

import java.util.EnumSet;

import org.avp.config.AvpConfig;
import org.avp.entity.creature.CreatureAbilities;
import org.avp.entity.creature.CreatureProjectile;
import org.avp.entity.creature.CreatureSpec;
import org.avp.entity.creature.Creatures;
import org.avp.entity.creature.Family;
import org.avp.registry.ModSounds;
import org.avp.registry.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * Yautja (Predators). A Warrior keeps the honour code: it hunts only worthy prey - Xenomorphs,
 * dangerous monsters, armed Marines, Engineers and armed or armoured players - and leaves the
 * unarmed and harmless alone unless they strike first. It stalks cloaked, fires its plasma
 * caster from range (which shows where it is for a moment), closes in with its wrist blades,
 * and pulls back cloaked to heal when badly hurt. A Berserker has given up the code.
 */
public class YautjaEntity extends Monster
{
    private static final EntityDataAccessor<Boolean> CLOAKED = SynchedEntityData.defineId(YautjaEntity.class, EntityDataSerializers.BOOLEAN);
    private CreatureSpec spec;
    private int revealed;
    private int retreatTicks;

    public YautjaEntity(EntityType<? extends YautjaEntity> type, Level level)
    {
        super(type, level);
        this.xpReward = spec().xp();
    }

    public CreatureSpec spec()
    {
        if (spec == null)
        {
            spec = Creatures.get(getType());
        }
        return spec;
    }

    public boolean isBerserker()
    {
        return spec().id().contains("berserker");
    }

    public boolean isCloaked()
    {
        return entityData.get(CLOAKED);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(CLOAKED, false);
    }

    @Override
    protected void registerGoals()
    {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new RetreatToHealGoal(this));
        goalSelector.addGoal(2, new CreatureAbilities.ShootGoal(this, CreatureProjectile.Kind.PLASMA,
                (float) spec().damage(), 8, 40, 80, 160, 1));
        goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.45F));
        goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.25, true));
        goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8));
        goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 16F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this, YautjaEntity.class).setAlertOthers());
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, this::isWorthyPrey));
    }

    /** The honour code: only prey that can fight back. */
    public boolean isWorthyPrey(LivingEntity e)
    {
        if (!e.isAlive() || e instanceof YautjaEntity || e.getType().is(ModTags.PREDATORS) || e instanceof Creeper)
        {
            return false;
        }
        if (e.getType().is(ModTags.XENOMORPHS))
        {
            return !(e instanceof org.avp.entity.xenomorph.OvamorphEntity); // eggs aren't sport
        }
        boolean honour = AvpConfig.server(AvpConfig.SERVER.yautjaHonourCode) && !isBerserker();
        if (e instanceof Player player)
        {
            return !player.isCreative() && !player.isSpectator() && (!honour || isArmed(player) || player.getArmorValue() >= 8);
        }
        CreatureSpec other = Creatures.get(e.getType());
        if (other != null && (other.family() == Family.HUMAN || other.family() == Family.ENGINEER))
        {
            return true; // soldiers and Engineers are always worthy
        }
        if (isBerserker() && e instanceof AbstractVillager)
        {
            return true;
        }
        return e instanceof Enemy && e.getMaxHealth() >= 24;
    }

    private static boolean isArmed(LivingEntity e)
    {
        ItemStack held = e.getMainHandItem();
        return held.getItem() instanceof SwordItem || held.getItem() instanceof AxeItem || held.getItem() instanceof BowItem
                || held.getItem() instanceof CrossbowItem || held.getItem() instanceof TridentItem || held.getItem() instanceof MaceItem;
    }

    @Override
    public void tick()
    {
        super.tick();
        if (level().isClientSide)
        {
            if (isCloaked() && random.nextInt(6) == 0)
            {
                // the shimmer of the cloak
                level().addParticle(ParticleTypes.REVERSE_PORTAL, getRandomX(0.6), getRandomY(), getRandomZ(0.6), 0, 0.02, 0);
            }
            return;
        }
        if (revealed > 0)
        {
            revealed--;
        }
        if (retreatTicks > 0)
        {
            retreatTicks--;
        }
        LivingEntity target = getTarget();
        boolean cloak = AvpConfig.server(AvpConfig.SERVER.yautjaCloak) && revealed == 0 && !isInWaterOrRain()
                && (target == null || distanceTo(target) > 6 || retreatTicks > 0);
        if (cloak != isCloaked())
        {
            entityData.set(CLOAKED, cloak);
            playSound(SoundEvents.BEACON_POWER_SELECT, 0.4F, cloak ? 1.6F : 1.2F);
        }
        if (getHealth() < getMaxHealth() && (target == null || retreatTicks > 0) && tickCount % 40 == 0)
        {
            heal(1.0F);
        }
    }

    /** Firing the plasma caster, being hit or getting wet shows it for a moment. */
    public void reveal(int ticks)
    {
        revealed = Math.max(revealed, ticks);
    }

    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        boolean hurt = super.hurt(source, amount);
        if (hurt && !level().isClientSide)
        {
            reveal(30);
            if (level() instanceof ServerLevel server)
            {
                // glowing green blood
                server.sendParticles(new net.minecraft.core.particles.DustParticleOptions(new org.joml.Vector3f(0.4F, 1.0F, 0.2F), 1.0F),
                        getX(), getY() + getBbHeight() * 0.6, getZ(), 6, 0.2, 0.3, 0.2, 0.0);
            }
            if (getHealth() < getMaxHealth() * 0.25F && retreatTicks == 0 && !isBerserker())
            {
                retreatTicks = 300;
            }
        }
        return hurt;
    }

    @Override
    public double getVisibilityPercent(Entity looking)
    {
        return super.getVisibilityPercent(looking) * (isCloaked() ? 0.1 : 1.0);
    }

    @Override
    public boolean doHurtTarget(Entity target)
    {
        reveal(40);
        return super.doHurtTarget(target);
    }

    @Override
    public boolean removeWhenFarAway(double distance)
    {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return ModSounds.creature("predator", "living");
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return ModSounds.creature("predator", "hurt");
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return ModSounds.creature("predator", "death");
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state)
    {
        if (!isCloaked())
        {
            super.playStepSound(pos, state);
        }
    }

    @Override
    public int getAmbientSoundInterval()
    {
        return 300;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.putInt("Retreat", retreatTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        retreatTicks = tag.getInt("Retreat");
    }

    /** Badly hurt: break off, cloaked, and heal before coming back. */
    static class RetreatToHealGoal extends Goal
    {
        private final YautjaEntity yautja;

        RetreatToHealGoal(YautjaEntity yautja)
        {
            this.yautja = yautja;
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            return yautja.retreatTicks > 0 && yautja.getTarget() != null;
        }

        @Override
        public void tick()
        {
            LivingEntity target = yautja.getTarget();
            if (target != null && yautja.getNavigation().isDone())
            {
                Vec3 away = DefaultRandomPos.getPosAway(yautja, 16, 7, target.position());
                if (away != null)
                {
                    yautja.getNavigation().moveTo(away.x, away.y, away.z, 1.3);
                }
            }
        }

        @Override
        public void stop()
        {
            yautja.setTarget(null);
        }
    }
}
