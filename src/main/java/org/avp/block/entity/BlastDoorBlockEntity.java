package org.avp.block.entity;

import java.util.UUID;

import org.avp.block.BlastDoorBlock;
import org.avp.config.AvpConfig;
import org.avp.network.OpenBlastDoorScreenPayload;
import org.avp.registry.ModBlockEntities;
import org.avp.registry.ModItems;
import org.avp.registry.ModSounds;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Everything a blast door does (it lives on the controller piece).
 *
 * - Needs power (FE or redstone, see EnergyBuffer) to open, close and use its keypad.
 * - No password: right-click to open/close.
 * - With a password: it's locked when closed. Right-click shows the password screen;
 *   the right password unlocks and opens it. Closing it locks it again, and with
 *   Auto-Lock on it closes and locks by itself after a while.
 * - Security Tuner: right-click to open the admin screen (password, Auto-Lock, bind).
 *   Walking up to a locked door holding a tuner bound to it unlocks and opens it.
 * - Maintenance Jack: when the door has NO power, keep right-clicking to pry it about
 *   40% open - enough to squeeze through. Uses up the jack.
 *
 * - Sensors (set on the admin screen): Auto-Open opens the door for nearby players and
 *   closes it after they leave; Threat Detection slams it shut and locks it down while a
 *   hostile creature is near, with a flashing red light and a redstone signal from every piece.
 *
 * The password stays on the server: it's saved with the world but never sent to clients.
 */
public class BlastDoorBlockEntity extends BlockEntity
{
    private static final float PRY_STEP = 0.05F;
    private static final float PRY_OPEN = 0.4F;

    private final EnergyBuffer energy = new EnergyBuffer(PoweredBlockEntity.CAPACITY);
    private String identifier;
    private String password = "";
    private String bindKey = "";
    private boolean autolock;
    private boolean open;
    private boolean unlocked;
    private boolean powered;
    private boolean pried;
    private float pryProgress;
    private int lockTimer;
    /** Sensor: opens for nearby players, closes when they've gone. */
    private boolean autoOpen;
    /** Sensor: closes and locks down when a threat is near. */
    private boolean threatDetection;
    /** Currently in lockdown because a threat is near. */
    private boolean lockdown;
    /** Ticks since a player was last seen by the Auto-Open sensor. */
    private int noPlayerTicks;
    /** True if the door was opened by the Auto-Open sensor (so it may close it again). */
    private boolean openedBySensor;
    /** Client only: whether the door has a password (the password itself is never sent). */
    private boolean clientHasPassword;
    /** Client only: animation 0 = closed, 1 = fully open. */
    private float progress;
    private float progressPrev;

    public BlastDoorBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.BLAST_DOOR.get(), pos, state);
        boolean sevastopol = state.getBlock() instanceof BlastDoorBlock door && door.isSevastopol();
        this.identifier = (sevastopol ? "SBD" : "BD") + (1000 + new java.util.Random().nextInt(8999));
    }

    public EnergyBuffer getEnergy()
    {
        return energy;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public boolean isPowered()
    {
        return powered;
    }

    public boolean hasPassword()
    {
        return !password.isEmpty() || clientHasPassword;
    }

    public boolean isLocked()
    {
        return hasPassword() && !unlocked;
    }

    public boolean isAutolock()
    {
        return autolock;
    }

    public boolean isAutoOpen()
    {
        return autoOpen;
    }

    public boolean isThreatDetection()
    {
        return threatDetection;
    }

    public boolean isLockdown()
    {
        return lockdown;
    }

    /** Animation value between the last two ticks, for smooth drawing. */
    public float progress(float partialTick)
    {
        return progressPrev + (progress - progressPrev) * partialTick;
    }

    private BlastDoorBlock block()
    {
        return (BlastDoorBlock) getBlockState().getBlock();
    }

    // ---- ticking ----

    public static void serverTick(Level level, BlockPos pos, BlockState state, BlastDoorBlockEntity door)
    {
        boolean wasPowered = door.powered;
        door.powered = door.energy.tickPower(level, pos, AvpConfig.server(AvpConfig.SERVER.blastDoorEnergyPerTick));
        if (door.powered != wasPowered)
        {
            door.sync();
        }
        if (!door.powered)
        {
            return;
        }
        // walking up with a bound Security Tuner unlocks the door (like 1.12.2)
        if (door.isLocked() && !door.open && level.getGameTime() % 10 == 0)
        {
            for (Player player : level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(3, 2, 3)))
            {
                if (door.tunerMatches(player.getMainHandItem()))
                {
                    door.unlock(player);
                    break;
                }
            }
        }
        if (level.getGameTime() % 10 == 0)
        {
            door.runSensors(level, pos);
        }
        // Auto-Lock: close and lock again a while after being unlocked
        if (door.autolock && door.hasPassword() && door.unlocked && --door.lockTimer <= 0)
        {
            door.setOpen(false);
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, BlastDoorBlockEntity door)
    {
        door.progressPrev = door.progress;
        float target = door.open ? (door.pried ? PRY_OPEN : 1F) : 0F;
        float speed = door.block().speed();
        if (target > door.progress && (door.powered || door.pried))
        {
            door.progress = Math.min(target, door.progress + speed);
        }
        else if (target < door.progress)
        {
            door.progress = Math.max(target, door.progress - speed);
        }
    }

    // ---- sensors ----

    /** Creatures that trigger Threat Detection: hostile mobs, plus anything in the avp:door_threats tag. */
    public static final net.minecraft.tags.TagKey<net.minecraft.world.entity.EntityType<?>> THREATS = net.minecraft.tags.TagKey.create(
            net.minecraft.core.registries.Registries.ENTITY_TYPE,
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(org.avp.AliensVsPredator.MOD_ID, "door_threats"));

    private static boolean isThreat(net.minecraft.world.entity.LivingEntity entity)
    {
        return entity.isAlive() && (entity instanceof net.minecraft.world.entity.monster.Enemy || entity.getType().is(THREATS));
    }

    /** Runs every half second while powered. */
    private void runSensors(Level level, BlockPos pos)
    {
        int range = AvpConfig.server(AvpConfig.SERVER.doorSensorRange);
        AABB area = new AABB(pos).inflate(range + 2, 3, range + 2);

        if (threatDetection)
        {
            boolean threat = !level.getEntitiesOfClass(net.minecraft.world.entity.LivingEntity.class, area, BlastDoorBlockEntity::isThreat).isEmpty();
            if (threat != lockdown)
            {
                lockdown = threat;
                setAlarm(threat);
                if (threat && open)
                {
                    setOpen(false); // slam shut
                }
                sync();
            }
            if (lockdown)
            {
                return; // nothing else happens during lockdown (the renderer flashes a red light)
            }
        }
        else if (lockdown)
        {
            lockdown = false;
            setAlarm(false);
            sync();
        }

        if (autoOpen && !isLocked())
        {
            boolean playerNear = !level.getEntitiesOfClass(Player.class, area, p -> !p.isSpectator() && p.isAlive()).isEmpty();
            if (playerNear)
            {
                noPlayerTicks = 0;
                if (!open)
                {
                    setOpen(true);
                    openedBySensor = true;
                }
            }
            else if (open && openedBySensor && (noPlayerTicks += 10) >= AvpConfig.server(AvpConfig.SERVER.doorAutoCloseTicks))
            {
                setOpen(false);
            }
        }
    }

    /** Sets the ALARM state on every piece (each one then gives out a redstone signal). */
    private void setAlarm(boolean alarm)
    {
        if (level == null)
        {
            return;
        }
        BlockState state = getBlockState();
        for (BlockPos p : block().partPositions(worldPosition, state.getValue(BlastDoorBlock.FACING)))
        {
            BlockState piece = level.getBlockState(p);
            if (piece.is(state.getBlock()) && piece.getValue(BlastDoorBlock.ALARM) != alarm)
            {
                level.setBlock(p, piece.setValue(BlastDoorBlock.ALARM, alarm), Block.UPDATE_ALL);
            }
        }
    }

    // ---- using it ----

    public static boolean isDoorTool(ItemStack stack)
    {
        return stack.is(ModItems.TOOL_SECURITYTUNER.get()) || stack.is(ModItems.TOOL_MAINTENANCEJACK.get());
    }

    /** Right-click from any piece of the door (server side). */
    public void use(Player player, ItemStack stack)
    {
        if (stack.is(ModItems.TOOL_MAINTENANCEJACK.get()))
        {
            pry(player, stack);
            return;
        }
        if (!powered)
        {
            message(player, "message.avp.blastdoor.no_power");
            return;
        }
        if (stack.is(ModItems.TOOL_SECURITYTUNER.get()))
        {
            if (tunerMatches(stack))
            {
                openScreen(player, true);
            }
            else
            {
                message(player, "message.avp.blastdoor.wrong_tuner");
            }
            return;
        }
        if (lockdown)
        {
            playSound(ModSounds.BLASTDOOR_PASS_DENY.get());
            message(player, "message.avp.blastdoor.lockdown");
            return;
        }
        if (isLocked())
        {
            openScreen(player, false);
            return;
        }
        setOpen(!open);
        openedBySensor = false;
        message(player, open ? "message.avp.blastdoor.opened" : "message.avp.blastdoor.closed");
    }

    private void pry(Player player, ItemStack jack)
    {
        if (powered || open)
        {
            message(player, "message.avp.blastdoor.jack_only_unpowered");
            return;
        }
        if (!AvpConfig.server(AvpConfig.SERVER.blastDoorsCanBePried))
        {
            message(player, "message.avp.blastdoor.cannot_pry");
            return;
        }
        pryProgress += PRY_STEP;
        jack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
        int percent = Math.min(100, Math.round(pryProgress / PRY_OPEN * 100));
        player.displayClientMessage(Component.translatable("message.avp.blastdoor.prying", percent), true);
        if (pryProgress >= PRY_OPEN - 0.001F)
        {
            pried = true;
            setOpen(true);
        }
        else
        {
            setChanged();
        }
    }

    private void openScreen(Player player, boolean admin)
    {
        if (player instanceof ServerPlayer serverPlayer)
        {
            PacketDistributor.sendToPlayer(serverPlayer, new OpenBlastDoorScreenPayload(worldPosition, admin, identifier, autolock,
                    hasPassword(), autoOpen, threatDetection));
        }
    }

    // ---- actions from the screens (see BlastDoorActionPayload; all checked here on the server) ----

    /** Password typed on the unlock screen. */
    public void tryPassword(Player player, String attempt)
    {
        if (!powered || !isLocked())
        {
            return;
        }
        if (attempt.equals(password))
        {
            unlock(player);
        }
        else
        {
            playSound(ModSounds.BLASTDOOR_PASS_DENY.get());
            message(player, "message.avp.blastdoor.denied");
        }
    }

    /** Admin screen actions; only allowed while holding a tuner that's bound to this door. */
    public void setPassword(Player player, String newPassword)
    {
        if (!powered || !tunerMatches(player.getMainHandItem()))
        {
            return;
        }
        password = newPassword.trim();
        unlocked = open; // a door with a new password locks once it's closed
        message(player, password.isEmpty() ? "message.avp.blastdoor.password_removed" : "message.avp.blastdoor.password_set");
        sync();
    }

    public void toggleAutolock(Player player)
    {
        if (!powered || !tunerMatches(player.getMainHandItem()))
        {
            return;
        }
        autolock = !autolock;
        message(player, autolock ? "message.avp.blastdoor.autolock_on" : "message.avp.blastdoor.autolock_off");
        sync();
    }

    public void toggleAutoOpen(Player player)
    {
        if (!powered || !tunerMatches(player.getMainHandItem()))
        {
            return;
        }
        autoOpen = !autoOpen;
        message(player, autoOpen ? "message.avp.blastdoor.autoopen_on" : "message.avp.blastdoor.autoopen_off");
        sync();
    }

    public void toggleThreatDetection(Player player)
    {
        if (!powered || !tunerMatches(player.getMainHandItem()))
        {
            return;
        }
        threatDetection = !threatDetection;
        message(player, threatDetection ? "message.avp.blastdoor.threat_on" : "message.avp.blastdoor.threat_off");
        sync();
    }

    /** Binds the held tuner to this door: only bound tuners can manage or auto-unlock it from now on. */
    public void bindTuner(Player player)
    {
        ItemStack tuner = player.getMainHandItem();
        if (!powered || !tunerMatches(tuner))
        {
            return;
        }
        if (bindKey.isEmpty())
        {
            bindKey = UUID.randomUUID().toString();
        }
        CustomData.update(DataComponents.CUSTOM_DATA, tuner, tag ->
        {
            tag.putString("BindKey", bindKey);
            tag.putString("BoundDoor", identifier);
        });
        message(player, "message.avp.blastdoor.bound", identifier);
        sync();
    }

    /** A tuner works on this door if the door isn't bound yet, or the tuner was bound to it. */
    public boolean tunerMatches(ItemStack stack)
    {
        if (!stack.is(ModItems.TOOL_SECURITYTUNER.get()))
        {
            return false;
        }
        if (bindKey.isEmpty())
        {
            return true;
        }
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        return data != null && bindKey.equals(data.copyTag().getString("BindKey"));
    }

    // ---- opening, closing, locking ----

    private void unlock(Player player)
    {
        if (lockdown)
        {
            return;
        }
        unlocked = true;
        lockTimer = AvpConfig.server(AvpConfig.SERVER.blastDoorAutolockTicks);
        playSound(ModSounds.BLASTDOOR_UNLOCK.get());
        setOpen(true);
        message(player, "message.avp.blastdoor.unlocked");
    }

    public void setOpen(boolean open)
    {
        if (level == null)
        {
            return;
        }
        boolean wasOpen = this.open;
        this.open = open;
        if (!open)
        {
            pried = false;
            pryProgress = 0;
            if (unlocked && hasPassword())
            {
                unlocked = false; // closing a password door locks it again
                playSound(ModSounds.BLASTDOOR_LOCK.get());
            }
        }
        if (wasOpen != open)
        {
            boolean sevastopol = block().isSevastopol();
            playSound(open
                    ? (sevastopol ? ModSounds.BLASTDOOR_SEVASTOPOL_OPEN.get() : ModSounds.BLASTDOOR_OPEN.get())
                    : (sevastopol ? ModSounds.BLASTDOOR_SEVASTOPOL_CLOSE.get() : ModSounds.BLASTDOOR_CLOSE.get()));
            // every piece gets the OPEN state, which removes its collision
            BlockState state = getBlockState();
            for (BlockPos p : block().partPositions(worldPosition, state.getValue(BlastDoorBlock.FACING)))
            {
                BlockState piece = level.getBlockState(p);
                if (piece.is(state.getBlock()))
                {
                    level.setBlock(p, piece.setValue(BlastDoorBlock.OPEN, open), Block.UPDATE_ALL);
                }
            }
        }
        sync();
    }

    private void playSound(SoundEvent sound)
    {
        if (level != null)
        {
            level.playSound(null, worldPosition, sound, SoundSource.BLOCKS, 1F, 1F);
        }
    }

    private static void message(Player player, String key, Object... args)
    {
        player.displayClientMessage(Component.translatable(key, args), true);
    }

    private void sync()
    {
        setChanged();
        if (level != null)
        {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    // ---- saving and syncing ----

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Energy", energy.serializeNBT(registries));
        tag.putString("Identifier", identifier);
        tag.putString("Password", password);
        tag.putString("BindKey", bindKey);
        tag.putBoolean("Autolock", autolock);
        tag.putBoolean("Open", open);
        tag.putBoolean("Unlocked", unlocked);
        tag.putBoolean("Powered", powered);
        tag.putBoolean("Pried", pried);
        tag.putFloat("PryProgress", pryProgress);
        tag.putInt("LockTimer", lockTimer);
        tag.putBoolean("AutoOpen", autoOpen);
        tag.putBoolean("ThreatDetection", threatDetection);
        tag.putBoolean("Lockdown", lockdown);
        tag.putBoolean("OpenedBySensor", openedBySensor);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        if (tag.contains("Energy"))
        {
            energy.deserializeNBT(registries, tag.get("Energy"));
        }
        if (tag.contains("Identifier"))
        {
            identifier = tag.getString("Identifier");
        }
        password = tag.getString("Password");
        bindKey = tag.getString("BindKey");
        clientHasPassword = tag.getBoolean("HasPassword");
        autolock = tag.getBoolean("Autolock");
        open = tag.getBoolean("Open");
        unlocked = tag.getBoolean("Unlocked");
        powered = tag.getBoolean("Powered");
        pried = tag.getBoolean("Pried");
        pryProgress = tag.getFloat("PryProgress");
        lockTimer = tag.getInt("LockTimer");
        autoOpen = tag.getBoolean("AutoOpen");
        threatDetection = tag.getBoolean("ThreatDetection");
        lockdown = tag.getBoolean("Lockdown");
        openedBySensor = tag.getBoolean("OpenedBySensor");
    }

    /** What clients get: everything except the password and the bind key. */
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        CompoundTag tag = saveWithoutMetadata(registries);
        tag.remove("Password");
        tag.remove("BindKey");
        tag.putBoolean("HasPassword", !password.isEmpty());
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
