package org.avp.block.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.avp.block.TurretBlock;
import org.avp.config.AvpConfig;
import org.avp.menu.TurretMenu;
import org.avp.power.MachineEnergy;
import org.avp.registry.ModBlockEntities;
import org.avp.registry.ModDamageTypes;
import org.avp.registry.ModItems;
import org.avp.registry.ModSounds;
import org.avp.registry.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * The sentry turret's brain (1.12.2 TileEntityTurret, rebuilt).
 *
 * Every tick while powered it turns towards its target (or sweeps slowly back and forth
 * when it has none), and fires when the barrel is lined up and it can see the target.
 * Rounds come from Sentry Ammo Boxes in the 9-slot ammo bay; hoppers can fill the bay.
 *
 * Upgrades (3 slots): each Processor turns it faster, each Motion Sensor Array adds range,
 * an LED Display shows the round counter above it.
 *
 * Targets are picked by category (hostile monsters, Xenomorphs, Yautja, other players,
 * animals). Its owner and the owner's team are never targeted.
 *
 * The client works out the turret's aim itself from the target the server sends it,
 * so turning looks smooth without sending the angles every tick.
 */
public class TurretBlockEntity extends BlockEntity implements MenuProvider
{
    public static final int AMMO_SLOTS = 9;
    public static final int UPGRADE_SLOTS = 3;
    /** Target categories, in the order of the buttons on the screen. */
    public static final String[] CATEGORIES = { "hostiles", "xenomorphs", "predators", "players", "animals" };
    private static final boolean[] DEFAULT_TARGETS = { true, true, false, false, false };
    /** Height of the gun's pivot above the block bottom, in blocks (from the 1.12.2 model). */
    public static final double PIVOT_HEIGHT = 0.23;
    /** Barrel length from the pivot to the muzzle, in blocks. */
    public static final double BARREL = 1.0;

    /**
     * 10x28mm caseless rounds (the UA 571-C's calibre):
     *  - standard: the baseline,
     *  - armour-piercing (AP): a dense cobalt core, ignores armour - made for Xenomorph chitin,
     *  - incendiary: sets targets alight, a little less punch.
     */
    public enum AmmoType
    {
        STANDARD(1.0F, 0, 0xFFFFD966),
        ARMOR_PIERCING(1.25F, 0, 0xFFB0E0FF),
        INCENDIARY(0.9F, 4, 0xFFFF7A1A);

        public final float damage;
        public final int fireSeconds;
        public final int tracerColor;

        AmmoType(float damage, int fireSeconds, int tracerColor)
        {
            this.damage = damage;
            this.fireSeconds = fireSeconds;
            this.tracerColor = tracerColor;
        }

        public static AmmoType of(ItemStack stack)
        {
            if (stack.is(ModItems.AMMO_SENTRY_AP.get()))
            {
                return ARMOR_PIERCING;
            }
            if (stack.is(ModItems.AMMO_SENTRY_INCENDIARY.get()))
            {
                return INCENDIARY;
            }
            return STANDARD;
        }

        public String key()
        {
            return name().toLowerCase(java.util.Locale.ROOT);
        }
    }

    public static boolean isAmmo(ItemStack stack)
    {
        return stack.is(ModItems.AMMO_SENTRY.get()) || stack.is(ModItems.AMMO_SENTRY_AP.get()) || stack.is(ModItems.AMMO_SENTRY_INCENDIARY.get());
    }

    private final MachineEnergy energy = new MachineEnergy(20_000);
    private final ItemStackHandler ammo = new ItemStackHandler(AMMO_SLOTS)
    {
        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return isAmmo(stack);
        }

        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }
    };
    private final ItemStackHandler upgrades = new ItemStackHandler(UPGRADE_SLOTS)
    {
        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return isUpgrade(stack);
        }

        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
            sync();
        }
    };

    private final boolean[] targets = DEFAULT_TARGETS.clone();
    @Nullable
    private UUID owner;
    private String ownerName = "";
    private int rounds;
    private AmmoType loaded = AmmoType.STANDARD;
    private int cooldown;
    private int targetId = -1;
    private int syncedRounds = -1;

    // aim (both sides)
    private float yaw;
    private float pitch;
    private float yawPrev;
    private float pitchPrev;
    private boolean initialised;

    /** Client: recent shots to draw as tracers. */
    public record Shot(Vec3 to, long until)
    {
    }

    private final List<Shot> shots = new ArrayList<>();

    public TurretBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.TURRET.get(), pos, state);
    }

    public static boolean isUpgrade(ItemStack stack)
    {
        return stack.is(ModItems.PART_PROCESSOR.get()) || stack.is(ModItems.PART_SENSOR_ARRAY.get()) || stack.is(ModItems.PART_LED_DISPLAY.get());
    }

    // ------------------------------------------------------------------ accessors

    public ItemStackHandler getAmmo()
    {
        return ammo;
    }

    public ItemStackHandler getUpgrades()
    {
        return upgrades;
    }

    /** What hoppers and pipes see: they can load ammo but not take it out. */
    public net.neoforged.neoforge.items.IItemHandler ammoInput()
    {
        return new net.neoforged.neoforge.items.IItemHandler()
        {
            @Override
            public int getSlots()
            {
                return ammo.getSlots();
            }

            @Override
            public ItemStack getStackInSlot(int slot)
            {
                return ammo.getStackInSlot(slot);
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
            {
                return ammo.insertItem(slot, stack, simulate);
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate)
            {
                return ItemStack.EMPTY;
            }

            @Override
            public int getSlotLimit(int slot)
            {
                return ammo.getSlotLimit(slot);
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack)
            {
                return ammo.isItemValid(slot, stack);
            }
        };
    }

    public IEnergyStorage energyFor()
    {
        return energy.view(true, false, Integer.MAX_VALUE);
    }

    public MachineEnergy getEnergy()
    {
        return energy;
    }

    public int rounds()
    {
        return rounds;
    }

    public AmmoType loaded()
    {
        return loaded;
    }

    public boolean hanging()
    {
        return getBlockState().getValue(TurretBlock.HANGING);
    }

    public int boxes()
    {
        int boxes = 0;
        for (int i = 0; i < AMMO_SLOTS; i++)
        {
            boxes += ammo.getStackInSlot(i).getCount();
        }
        return boxes;
    }

    public int totalRounds()
    {
        return rounds + boxes() * AvpConfig.server(AvpConfig.SERVER.turretRoundsPerBox);
    }

    public int comparatorLevel()
    {
        int max = AMMO_SLOTS * 16 * AvpConfig.server(AvpConfig.SERVER.turretRoundsPerBox);
        int total = totalRounds();
        return total == 0 ? 0 : Math.max(1, Math.round(15F * total / max));
    }

    public boolean targets(int category)
    {
        return targets[category];
    }

    public String ownerName()
    {
        return ownerName;
    }

    public int targetId()
    {
        return targetId;
    }

    public void setOwner(Player player)
    {
        owner = player.getUUID();
        ownerName = player.getGameProfile().getName();
        setChanged();
        sync();
    }

    /** Only the owner (or a creative-mode admin) may change the IFF settings. */
    public boolean mayConfigure(Player player)
    {
        return owner == null || owner.equals(player.getUUID()) || player.isCreative();
    }

    public void toggleTarget(Player player, int category)
    {
        if (category < 0 || category >= targets.length)
        {
            return;
        }
        if (!mayConfigure(player))
        {
            player.displayClientMessage(Component.translatable("message.avp.turret.not_owner", ownerName), true);
            return;
        }
        targets[category] = !targets[category];
        setTarget(null);
        setChanged();
        sync();
    }

    private int countUpgrade(net.minecraft.world.item.Item item)
    {
        int count = 0;
        for (int i = 0; i < UPGRADE_SLOTS; i++)
        {
            if (upgrades.getStackInSlot(i).is(item))
            {
                count += upgrades.getStackInSlot(i).getCount();
            }
        }
        return count;
    }

    public int range()
    {
        return AvpConfig.server(AvpConfig.SERVER.turretRange)
                + countUpgrade(ModItems.PART_SENSOR_ARRAY.get()) * AvpConfig.server(AvpConfig.SERVER.turretRangePerSensor);
    }

    public boolean hasDisplay()
    {
        return countUpgrade(ModItems.PART_LED_DISPLAY.get()) > 0;
    }

    private float turnSpeed()
    {
        float base = AvpConfig.server(AvpConfig.SERVER.turretTurnSpeed);
        return base + base * 0.5F * countUpgrade(ModItems.PART_PROCESSOR.get());
    }

    // ------------------------------------------------------------------ aim

    public Vec3 pivot()
    {
        return Vec3.atBottomCenterOf(worldPosition).add(0, hanging() ? 1 - PIVOT_HEIGHT : PIVOT_HEIGHT, 0);
    }

    public float yaw(float partialTick)
    {
        return yawPrev + Mth.wrapDegrees(yaw - yawPrev) * partialTick;
    }

    public float pitch(float partialTick)
    {
        return pitchPrev + (pitch - pitchPrev) * partialTick;
    }

    public Vec3 muzzle()
    {
        return pivot().add(Vec3.directionFromRotation(pitch, yaw).scale(BARREL));
    }

    private static Vec3 aimPoint(Entity entity)
    {
        return entity.getBoundingBox().getCenter();
    }

    /** Turns a step towards the target, or sweeps back and forth when idle. Same on both sides. */
    private void turn(Level level, BlockState state)
    {
        float facing = state.getValue(TurretBlock.FACING).toYRot();
        if (!initialised)
        {
            yaw = yawPrev = facing;
            initialised = true;
        }
        yawPrev = yaw;
        pitchPrev = pitch;
        float wantYaw, wantPitch;
        Entity target = targetId >= 0 ? level.getEntity(targetId) : null;
        if (target != null && state.getValue(TurretBlock.LIT))
        {
            Vec3 d = aimPoint(target).subtract(pivot());
            wantYaw = (float) (Mth.atan2(d.z, d.x) * Mth.RAD_TO_DEG) - 90F;
            wantPitch = (float) -(Mth.atan2(d.y, Math.sqrt(d.x * d.x + d.z * d.z)) * Mth.RAD_TO_DEG);
        }
        else if (state.getValue(TurretBlock.LIT))
        {
            // scanning: sweep side to side and nod up and down
            float t = level.getGameTime();
            wantYaw = facing + 40F * Mth.sin(t * 0.03F);
            wantPitch = (state.getValue(TurretBlock.HANGING) ? 20F : -5F) + 12F * Mth.sin(t * 0.071F);
        }
        else
        {
            wantYaw = yaw;
            wantPitch = state.getValue(TurretBlock.HANGING) ? 70F : 25F; // powered down: the barrel droops
        }
        // how far it can tilt: a floor turret can aim high, a ceiling turret can aim low
        wantPitch = state.getValue(TurretBlock.HANGING) ? Mth.clamp(wantPitch, -45F, 85F) : Mth.clamp(wantPitch, -85F, 45F);
        float speed = turnSpeed();
        yaw += Mth.clamp(Mth.wrapDegrees(wantYaw - yaw), -speed, speed);
        pitch += Mth.clamp(wantPitch - pitch, -speed, speed);
        yaw = Mth.wrapDegrees(yaw);
    }

    private boolean aimedAt(Entity target)
    {
        Vec3 d = aimPoint(target).subtract(pivot());
        float wantYaw = (float) (Mth.atan2(d.z, d.x) * Mth.RAD_TO_DEG) - 90F;
        float wantPitch = (float) -(Mth.atan2(d.y, Math.sqrt(d.x * d.x + d.z * d.z)) * Mth.RAD_TO_DEG);
        return Math.abs(Mth.wrapDegrees(wantYaw - yaw)) < 4F && Math.abs(wantPitch - pitch) < 6F;
    }

    // ------------------------------------------------------------------ server

    public static void serverTick(Level level, BlockPos pos, BlockState state, TurretBlockEntity be)
    {
        boolean on = be.tickPower(level, pos);
        if (state.getValue(TurretBlock.LIT) != on)
        {
            state = state.setValue(TurretBlock.LIT, on);
            level.setBlock(pos, state, Block.UPDATE_ALL);
            if (!on)
            {
                be.setTarget(null);
            }
        }
        if (on)
        {
            Entity target = be.targetId >= 0 ? level.getEntity(be.targetId) : null;
            if (target != null && !be.isValidTarget(level, target))
            {
                be.setTarget(null);
                target = null;
            }
            if (target == null && level.getGameTime() % 5 == Math.floorMod(pos.asLong(), 5))
            {
                target = be.findTarget(level);
                be.setTarget(target);
            }
        }
        be.turn(level, state);
        if (be.cooldown > 0)
        {
            be.cooldown--;
        }
        Entity target = be.targetId >= 0 ? level.getEntity(be.targetId) : null;
        if (on && target instanceof LivingEntity living && be.cooldown <= 0 && be.aimedAt(target))
        {
            if (be.rounds <= 0)
            {
                be.reload();
            }
            if (be.rounds > 0)
            {
                be.fire(level, pos, living);
            }
        }
        if (level.getGameTime() % 10 == 0 && be.syncedRounds != be.rounds)
        {
            be.setChanged();
            be.sync();
            level.updateNeighbourForOutputSignal(pos, state.getBlock());
        }
    }

    private boolean tickPower(Level level, BlockPos pos)
    {
        if (!AvpConfig.server(AvpConfig.SERVER.machinesNeedPower))
        {
            return true;
        }
        if (energy.consume(AvpConfig.server(AvpConfig.SERVER.turretEnergyPerTick)))
        {
            return true;
        }
        return AvpConfig.server(AvpConfig.SERVER.redstonePowersMachines) && level.hasNeighborSignal(pos);
    }

    private void setTarget(@Nullable Entity target)
    {
        int id = target == null ? -1 : target.getId();
        if (id != targetId)
        {
            targetId = id;
            if (level != null && !level.isClientSide)
            {
                level.blockEvent(worldPosition, getBlockState().getBlock(), 3, id + 1);
            }
        }
    }

    @Nullable
    private LivingEntity findTarget(Level level)
    {
        Vec3 pivot = pivot();
        int range = range();
        return level.getEntitiesOfClass(LivingEntity.class, new AABB(pivot, pivot).inflate(range), e -> isValidTarget(level, e))
                .stream().min(Comparator.comparingDouble(e -> e.distanceToSqr(pivot))).orElse(null);
    }

    private boolean isValidTarget(Level level, Entity entity)
    {
        if (!(entity instanceof LivingEntity living) || !living.isAlive() || living.isSpectator())
        {
            return false;
        }
        int range = range();
        if (living.distanceToSqr(pivot()) > range * range || !wanted(living))
        {
            return false;
        }
        return canSee(level, aimPoint(living), living);
    }

    /**
     * Clear line of fire? The line starts a block out from the gun's pivot: the pivot is inside
     * the turret's own block, and a line that starts inside a block always "hits" it.
     */
    private boolean canSee(Level level, Vec3 point, Entity target)
    {
        Vec3 from = pivot();
        Vec3 toTarget = point.subtract(from);
        double distance = toTarget.length();
        if (distance <= 1.0)
        {
            return true;
        }
        Vec3 start = from.add(toTarget.scale(1.0 / distance));
        return level.clip(new ClipContext(start, point, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, target))
                .getType() == HitResult.Type.MISS;
    }

    /** IFF: is this creature on the turret's list, and not friendly? */
    private boolean wanted(LivingEntity entity)
    {
        if (entity instanceof Player player)
        {
            if (player.isCreative() || player.getUUID().equals(owner))
            {
                return false;
            }
            Player ownerPlayer = owner == null || level == null ? null : level.getPlayerByUUID(owner);
            if (ownerPlayer != null && player.isAlliedTo(ownerPlayer))
            {
                return false;
            }
            return targets[3];
        }
        if (entity.getType().is(ModTags.XENOMORPHS))
        {
            return targets[1];
        }
        if (entity.getType().is(ModTags.PREDATORS))
        {
            return targets[2];
        }
        if (entity instanceof Enemy)
        {
            return targets[0];
        }
        if (entity instanceof Animal)
        {
            return targets[4];
        }
        return false;
    }

    private void reload()
    {
        for (int i = 0; i < AMMO_SLOTS; i++)
        {
            ItemStack box = ammo.extractItem(i, 1, false);
            if (!box.isEmpty())
            {
                loaded = AmmoType.of(box);
                rounds += AvpConfig.server(AvpConfig.SERVER.turretRoundsPerBox);
                sync();
                level.playSound(null, worldPosition, net.minecraft.sounds.SoundEvents.IRON_DOOR_CLOSE, SoundSource.BLOCKS, 0.5F, 1.8F);
                return;
            }
        }
    }

    private void fire(Level level, BlockPos pos, LivingEntity target)
    {
        rounds--;
        cooldown = AvpConfig.server(AvpConfig.SERVER.turretFireInterval);
        if (AvpConfig.server(AvpConfig.SERVER.turretBypassesInvulnerability))
        {
            target.invulnerableTime = 0;
        }
        float damage = (float) AvpConfig.server(AvpConfig.SERVER.turretDamage) * loaded.damage;
        target.hurt(loaded == AmmoType.ARMOR_PIERCING ? ModDamageTypes.sentryArmorPiercing(level) : ModDamageTypes.sentry(level), damage);
        if (loaded.fireSeconds > 0)
        {
            target.igniteForSeconds(loaded.fireSeconds);
        }
        level.playSound(null, pos, ModSounds.TURRET_FIRE.get(), SoundSource.BLOCKS, 0.8F, 0.9F + level.random.nextFloat() * 0.2F);
        level.blockEvent(pos, getBlockState().getBlock(), 2, target.getId());
    }

    // ------------------------------------------------------------------ client

    public static void clientTick(Level level, BlockPos pos, BlockState state, TurretBlockEntity be)
    {
        be.turn(level, state);
        long now = level.getGameTime();
        be.shots.removeIf(shot -> shot.until() < now);
    }

    /** 2 = a shot at entity param; 3 = new target (param = entity id + 1, 0 = none). */
    @Override
    public boolean triggerEvent(int id, int param)
    {
        if (level == null || id != 2 && id != 3)
        {
            return super.triggerEvent(id, param);
        }
        if (!level.isClientSide)
        {
            return true;
        }
        if (id == 3)
        {
            targetId = param - 1;
        }
        else
        {
            Entity target = level.getEntity(param);
            Vec3 muzzle = muzzle();
            Vec3 to = target != null ? aimPoint(target) : muzzle.add(Vec3.directionFromRotation(pitch, yaw).scale(range()));
            shots.add(new Shot(to.add(level.random.nextGaussian() * 0.1, level.random.nextGaussian() * 0.1, level.random.nextGaussian() * 0.1),
                    level.getGameTime() + 1));
            level.addParticle(loaded == AmmoType.INCENDIARY ? ParticleTypes.FLAME : ParticleTypes.SMOKE, muzzle.x, muzzle.y, muzzle.z, 0, 0.02, 0);
            if (target != null)
            {
                Vec3 hit = aimPoint(target);
                level.addParticle(ParticleTypes.CRIT, hit.x, hit.y, hit.z, 0, 0, 0);
            }
        }
        return true;
    }

    public List<Shot> shots()
    {
        return shots;
    }

    // ------------------------------------------------------------------ menu

    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block.avp.turret");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        return new TurretMenu(id, inventory, this);
    }

    // ------------------------------------------------------------------ saving and syncing

    private void sync()
    {
        if (level != null && !level.isClientSide)
        {
            syncedRounds = rounds;
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    private void writeShared(CompoundTag tag)
    {
        tag.putInt("Rounds", rounds);
        tag.putString("Loaded", loaded.name());
        tag.putInt("Energy", energy.getEnergyStored());
        for (int i = 0; i < targets.length; i++)
        {
            tag.putBoolean("Target_" + CATEGORIES[i], targets[i]);
        }
        tag.putString("OwnerName", ownerName);
    }

    private void readShared(CompoundTag tag)
    {
        rounds = tag.getInt("Rounds");
        try
        {
            loaded = AmmoType.valueOf(tag.getString("Loaded"));
        }
        catch (IllegalArgumentException e)
        {
            loaded = AmmoType.STANDARD;
        }
        energy.setStored(tag.getInt("Energy"));
        for (int i = 0; i < targets.length; i++)
        {
            String key = "Target_" + CATEGORIES[i];
            targets[i] = tag.contains(key) ? tag.getBoolean(key) : DEFAULT_TARGETS[i];
        }
        ownerName = tag.getString("OwnerName");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        writeShared(tag);
        tag.put("Ammo", ammo.serializeNBT(registries));
        tag.put("Upgrades", upgrades.serializeNBT(registries));
        if (owner != null)
        {
            tag.putUUID("Owner", owner);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        readShared(tag);
        if (tag.contains("Ammo"))
        {
            ammo.deserializeNBT(registries, tag.getCompound("Ammo"));
        }
        if (tag.contains("Upgrades"))
        {
            upgrades.deserializeNBT(registries, tag.getCompound("Upgrades"));
        }
        owner = tag.hasUUID("Owner") ? tag.getUUID("Owner") : null;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        CompoundTag tag = super.getUpdateTag(registries);
        writeShared(tag);
        tag.put("Upgrades", upgrades.serializeNBT(registries)); // the client draws the ammo counter if there's a display
        tag.putInt("TargetId", targetId);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries)
    {
        readShared(tag);
        if (tag.contains("Upgrades"))
        {
            upgrades.deserializeNBT(registries, tag.getCompound("Upgrades"));
        }
        if (tag.contains("TargetId"))
        {
            targetId = tag.getInt("TargetId");
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(net.minecraft.network.Connection net, ClientboundBlockEntityDataPacket packet, HolderLookup.Provider registries)
    {
        handleUpdateTag(packet.getTag(), registries);
    }
}
