package org.avp.mining;

import java.util.Optional;

import javax.annotation.Nullable;

import org.avp.config.AvpConfig;
import org.avp.registry.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/**
 * The Caterpillar P-5000 style Power Loader (Aliens): a walking cargo lifter you climb into. Runs on a
 * battery. Inside it you mine 4x faster, 3x3 at a time, straight into its 27-slot hold; you punch for
 * 16 (Ripley's answer to the Queen); and you can lift a whole block - a full chest, say - and set it
 * down somewhere else.
 */
public class PowerLoaderEntity extends PathfinderMob
{
    private static final EntityDataAccessor<Optional<BlockState>> CARRIED = SynchedEntityData.defineId(PowerLoaderEntity.class,
            EntityDataSerializers.OPTIONAL_BLOCK_STATE);
    private static final EntityDataAccessor<Integer> CHARGE = SynchedEntityData.defineId(PowerLoaderEntity.class, EntityDataSerializers.INT);

    public final SimpleContainer cargo = new SimpleContainer(27);
    @Nullable
    private CompoundTag carriedData;
    private int punchCooldown;

    public PowerLoaderEntity(EntityType<? extends PathfinderMob> type, Level level)
    {
        super(type, level);
        setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return PathfinderMob.createMobAttributes().add(Attributes.MAX_HEALTH, 120).add(Attributes.ARMOR, 14).add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0).add(Attributes.STEP_HEIGHT, 1.5).add(Attributes.ATTACK_DAMAGE, 16)
                .add(Attributes.SAFE_FALL_DISTANCE, 64);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(CARRIED, Optional.empty());
        builder.define(CHARGE, 0);
    }

    public int charge()
    {
        return entityData.get(CHARGE);
    }

    public int chargePercent()
    {
        return 100 * charge() / Math.max(1, AvpConfig.server(AvpConfig.SERVER.loaderBattery));
    }

    public boolean needsCharge()
    {
        return charge() < AvpConfig.server(AvpConfig.SERVER.loaderBattery) * 0.98;
    }

    public int addCharge(int amount)
    {
        int take = Math.max(0, Math.min(amount, AvpConfig.server(AvpConfig.SERVER.loaderBattery) - charge()));
        entityData.set(CHARGE, charge() + take);
        return take;
    }

    /** Uses power; false if there isn't enough. */
    public boolean use(int amount)
    {
        if (charge() < amount)
        {
            return false;
        }
        entityData.set(CHARGE, charge() - amount);
        return true;
    }

    public Optional<BlockState> carried()
    {
        return entityData.get(CARRIED);
    }

    // ------------------------------------------------------------------ riding

    @Nullable
    @Override
    public LivingEntity getControllingPassenger()
    {
        return getFirstPassenger() instanceof Player p ? p : null;
    }

    @Override
    protected void tickRidden(Player player, Vec3 input)
    {
        super.tickRidden(player, input);
        setYRot(player.getYRot());
        yRotO = getYRot();
        setXRot(0);
        yBodyRot = getYRot();
        yHeadRot = getYRot();
        if (input.lengthSqr() > 0 && !level().isClientSide)
        {
            use(6);
        }
        if (!level().isClientSide && tickCount % 20 == 0)
        {
            player.displayClientMessage(Component.literal("POWER LOADER  |  BATTERY " + chargePercent() + "%" + (carried().isPresent() ? "  |  LIFTING" : ""))
                    .withStyle(chargePercent() < 15 ? net.minecraft.ChatFormatting.RED : net.minecraft.ChatFormatting.GOLD), true);
        }
    }

    @Override
    protected Vec3 getRiddenInput(Player player, Vec3 input)
    {
        if (charge() <= 0)
        {
            return Vec3.ZERO;
        }
        return new Vec3(player.xxa * 0.4, 0, player.zza > 0 ? player.zza : player.zza * 0.5);
    }

    @Override
    protected float getRiddenSpeed(Player player)
    {
        return (float) getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    @Override
    protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float scale)
    {
        return new Vec3(0, 1.05, 0.15).yRot(-getYRot() * ((float) Math.PI / 180F));
    }

    @Override
    protected void registerGoals()
    {
        // no AI: it's a machine
    }

    @Override
    public void aiStep()
    {
        super.aiStep();
        if (punchCooldown > 0)
        {
            punchCooldown--;
        }
        if (!level().isClientSide && getFirstPassenger() == null)
        {
            setDeltaMovement(getDeltaMovement().multiply(0, 1, 0));
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (level().isClientSide)
        {
            return InteractionResult.SUCCESS;
        }
        if (stack.is(ModItems.INGOT_LITHIUM.get()) && needsCharge())
        {
            addCharge(AvpConfig.server(AvpConfig.SERVER.loaderBattery) / 8);
            stack.consume(1, player);
            playSound(SoundEvents.BEACON_POWER_SELECT, 0.6F, 1.4F);
            return InteractionResult.CONSUME;
        }
        if (stack.is(ModItems.TOOL_MAINTENANCEJACK.get()) && player.isShiftKeyDown())
        {
            // pack it up: the hold spills, the battery stays in the item
            for (int i = 0; i < cargo.getContainerSize(); i++)
            {
                spawnAtLocation(cargo.removeItemNoUpdate(i));
            }
            dropCarried();
            ItemStack item = new ItemStack(ModItems.POWER_LOADER.get());
            CompoundTag tag = new CompoundTag();
            tag.putInt("Charge", charge());
            item.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.of(tag));
            spawnAtLocation(item);
            discard();
            return InteractionResult.CONSUME;
        }
        if (player.isShiftKeyDown())
        {
            player.openMenu(new SimpleMenuProvider((id, inv, p) -> ChestMenu.threeRows(id, inv, cargo), Component.literal("Power Loader hold")));
            return InteractionResult.CONSUME;
        }
        if (!isVehicle())
        {
            player.startRiding(this);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    // ------------------------------------------------------------------ what the pilot does

    /** A loader punch. */
    public void punch(LivingEntity target)
    {
        if (punchCooldown > 0 || !use(500))
        {
            return;
        }
        punchCooldown = 20;
        swing(InteractionHand.MAIN_HAND);
        Player pilot = getFirstPassenger() instanceof Player p ? p : null;
        DamageSource src = pilot != null ? damageSources().playerAttack(pilot) : damageSources().mobAttack(this);
        if (target.hurt(src, (float) getAttributeValue(Attributes.ATTACK_DAMAGE)))
        {
            Vec3 push = target.position().subtract(position()).normalize().scale(1.6);
            target.push(push.x, 0.4, push.z);
            playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.2F, 0.6F);
        }
    }

    /** Lift the block at a spot (contents and all). */
    public boolean lift(BlockPos pos, Player pilot)
    {
        if (carried().isPresent() || !(level() instanceof ServerLevel level))
        {
            return false;
        }
        BlockState state = level.getBlockState(pos);
        if (state.isAir() || state.getDestroySpeed(level, pos) < 0 || !state.getFluidState().isEmpty() || !use(800))
        {
            return false;
        }
        if (net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new net.neoforged.neoforge.event.level.BlockEvent.BreakEvent(level, pos, state, pilot)).isCanceled())
        {
            return false;
        }
        BlockEntity be = level.getBlockEntity(pos);
        carriedData = be != null ? be.saveWithoutMetadata(level.registryAccess()) : null;
        if (be != null)
        {
            level.removeBlockEntity(pos); // so its contents don't spill
        }
        level.setBlock(pos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);
        entityData.set(CARRIED, Optional.of(state));
        swing(InteractionHand.MAIN_HAND);
        playSound(SoundEvents.PISTON_EXTEND, 1F, 0.6F);
        return true;
    }

    /** Set the lifted block down at a spot. */
    public boolean setDown(BlockPos pos)
    {
        Optional<BlockState> held = carried();
        if (held.isEmpty() || !level().getBlockState(pos).canBeReplaced())
        {
            return false;
        }
        BlockState state = held.get();
        if (state.hasProperty(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING))
        {
            state = state.setValue(net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING, getDirection().getOpposite());
        }
        level().setBlock(pos, state, 3);
        if (carriedData != null)
        {
            BlockEntity be = level().getBlockEntity(pos);
            if (be != null)
            {
                be.loadWithComponents(carriedData, level().registryAccess());
                be.setChanged();
            }
        }
        carriedData = null;
        entityData.set(CARRIED, Optional.empty());
        swing(InteractionHand.MAIN_HAND);
        playSound(SoundEvents.PISTON_CONTRACT, 1F, 0.6F);
        return true;
    }

    private void dropCarried()
    {
        if (carried().isPresent() && !setDown(blockPosition().relative(getDirection())) && !setDown(blockPosition()))
        {
            // nowhere to put it: it breaks
            if (level() instanceof ServerLevel level)
            {
                level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, carried().get()), getX(), getY() + 1, getZ(), 20, 0.5, 0.5, 0.5, 0.1);
            }
            entityData.set(CARRIED, Optional.empty());
            carriedData = null;
        }
    }

    /** Mined things go into the hold. Returns what didn't fit. */
    public ItemStack stow(ItemStack stack)
    {
        return cargo.addItem(stack);
    }

    // ------------------------------------------------------------------ body

    @Override
    public boolean isPushable()
    {
        return false;
    }

    @Override
    public boolean canBeLeashed()
    {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distance)
    {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return SoundEvents.ANVIL_LAND;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state)
    {
        playSound(SoundEvents.IRON_GOLEM_STEP, 1.0F, 0.6F);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit)
    {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        for (int i = 0; i < cargo.getContainerSize(); i++)
        {
            spawnAtLocation(cargo.removeItemNoUpdate(i));
        }
        dropCarried();
        spawnAtLocation(new ItemStack(net.minecraft.world.item.Items.IRON_BLOCK, 2));
        spawnAtLocation(new ItemStack(ModItems.PART_SERVO.get(), 2));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.put("Cargo", cargo.createTag(registryAccess()));
        tag.putInt("Charge", charge());
        carried().ifPresent(s -> tag.put("Carried", NbtUtils.writeBlockState(s)));
        if (carriedData != null)
        {
            tag.put("CarriedData", carriedData);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        cargo.fromTag(tag.getList("Cargo", 10), registryAccess());
        entityData.set(CHARGE, tag.getInt("Charge"));
        if (tag.contains("Carried"))
        {
            entityData.set(CARRIED, Optional.of(NbtUtils.readBlockState(level().holderLookup(net.minecraft.core.registries.Registries.BLOCK), tag.getCompound("Carried"))));
        }
        carriedData = tag.contains("CarriedData") ? tag.getCompound("CarriedData") : null;
    }
}
