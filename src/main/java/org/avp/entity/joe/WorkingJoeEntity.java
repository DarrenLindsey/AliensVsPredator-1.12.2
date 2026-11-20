package org.avp.entity.joe;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import org.avp.config.AvpConfig;
import org.avp.entity.joe.JoeProgram.Cond;
import org.avp.entity.joe.JoeProgram.Instr;
import org.avp.registry.ModItems;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.Filterable;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WritableBookContent;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

/**
 * A Seegson Working Joe (Alien: Isolation): a cheap, slow, very strong service android. Joes belong to
 * whoever built them, run a JOE-SCRIPT program (see {@link JoeProgram}) written in a book and quill,
 * run on a battery they top up at a Joe charger, and report back to their owner over the Seegson
 * network. Xenomorphs ignore them (there's nothing in them to eat or breed in). Badly damaged, one
 * may malfunction and turn on the people around it, until someone fits it a new processor.
 */
public class WorkingJoeEntity extends PathfinderMob
{
    private static final EntityDataAccessor<Byte> MODE = SynchedEntityData.defineId(WorkingJoeEntity.class, EntityDataSerializers.BYTE);
    public static final byte ACTIVE = 0, POWERED_DOWN = 1, ROGUE = 2;

    private static final String[] CHATTER = { "Hello. How may I help you?", "Please keep your work area tidy.",
            "All systems are operating normally.", "Thank you for your cooperation.", "Please do not run in the corridors.",
            "I am here to help.", "Have a productive day." };
    private static final String[] ROGUE_LINES = { "You are not authorised to be here.", "Please stop resisting.",
            "You are becoming hysterical.", "Remain calm. This will not take long.", "There is nowhere to go." };

    @Nullable
    private UUID owner;
    private String source = "";
    private BlockPos anchor = BlockPos.ZERO;
    @Nullable
    private JoeProgram program;
    private int pc;
    private boolean halted;
    private int charge;
    private boolean security;
    @Nullable
    private BlockPos home;
    final SimpleContainer inventory = new SimpleContainer(9);
    /** What "maintain" remembers, per program line. */
    private final Map<Integer, Map<BlockPos, BlockState>> snapshots = new HashMap<>();

    // running state (not saved: a statement restarts after a reload)
    @Nullable
    private Task task, interrupt;
    @Nullable
    private BlockPos guardCenter;
    private int guardRadius;
    private boolean wasNight, wasLow, hurtFlag;
    private long seenAlarm;
    private final Map<Integer, Long> cooldown = new HashMap<>();
    private final Map<Integer, Boolean> redstoneWas = new HashMap<>();
    private int chatterIn = 600;

    public WorkingJoeEntity(EntityType<? extends PathfinderMob> type, Level level)
    {
        super(type, level);
        charge = capacity();
        if (getNavigation() instanceof GroundPathNavigation ground)
        {
            ground.setCanOpenDoors(true);
            ground.setCanPassDoors(true);
        }
        setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return PathfinderMob.createMobAttributes().add(Attributes.MAX_HEALTH, 40).add(Attributes.ARMOR, 6).add(Attributes.ATTACK_DAMAGE, 7)
                .add(Attributes.MOVEMENT_SPEED, 0.24).add(Attributes.KNOCKBACK_RESISTANCE, 0.7).add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    public static int capacity()
    {
        return AvpConfig.server(AvpConfig.SERVER.joeBattery);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(MODE, ACTIVE);
    }

    public byte mode()
    {
        return entityData.get(MODE);
    }

    public boolean isRogue()
    {
        return mode() == ROGUE;
    }

    public boolean isActive()
    {
        return mode() != POWERED_DOWN;
    }

    @Nullable
    public UUID owner()
    {
        return owner;
    }

    public void setOwner(@Nullable UUID owner)
    {
        JoeNetwork.leave(this);
        this.owner = owner;
        JoeNetwork.join(this);
    }

    public boolean isOwner(Player player)
    {
        return player.getUUID().equals(owner);
    }

    public String joeName()
    {
        return hasCustomName() ? getCustomName().getString() : "Working Joe";
    }

    public int chargePercent()
    {
        return Math.round(100F * charge / Math.max(1, capacity()));
    }

    public boolean needsCharge()
    {
        return charge < capacity() * 0.95F;
    }

    /** A charger gives this Joe power; returns how much it took. */
    public int addCharge(int amount)
    {
        int take = Math.max(0, Math.min(amount, capacity() - charge));
        charge += take;
        return take;
    }

    public boolean isLowPower()
    {
        return AvpConfig.server(AvpConfig.SERVER.joeNeedsPower) && charge < capacity() * 0.15F;
    }

    // ------------------------------------------------------------------ goals

    @Override
    protected void registerGoals()
    {
        goalSelector.addGoal(0, new PoweredDownGoal());
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new ChargeGoal());
        goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.15, true));
        goalSelector.addGoal(4, new ProgramGoal());
        goalSelector.addGoal(5, new OpenDoorGoal(this, true));
        goalSelector.addGoal(6, new IdleGoal());
        goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8F));
        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new HurtByTargetGoal(this, WorkingJoeEntity.class)
        {
            @Override
            public boolean canUse()
            {
                return super.canUse() && (isRogue() || !(getLastHurtByMob() instanceof Player player && isOwner(player)));
            }
        });
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 5, true, false, this::isIntruder));
    }

    /** Who the Joe goes for by itself. */
    private boolean isIntruder(LivingEntity e)
    {
        if (e instanceof WorkingJoeEntity || e instanceof Player p && (p.isCreative() || p.isSpectator()))
        {
            return false;
        }
        if (isRogue())
        {
            return e instanceof Player || e instanceof org.avp.entity.human.HumanEntity;
        }
        if (guardCenter != null && e instanceof Enemy && e.blockPosition().closerThan(guardCenter, guardRadius))
        {
            return true;
        }
        if (!security)
        {
            return false;
        }
        if (e instanceof Player player)
        {
            return owner != null && !isOwner(player) && !player.isAlliedTo(this) && distanceTo(player) < 10;
        }
        return e instanceof Enemy && !e.getType().is(org.avp.registry.ModTags.XENOMORPHS)
                || e.getType().is(org.avp.registry.ModTags.XENOMORPHS) && AvpConfig.server(AvpConfig.SERVER.joesFightXenos);
    }

    /** No battery: stands still with dark eyes. Holds every movement flag so nothing else runs. */
    private class PoweredDownGoal extends Goal
    {
        PoweredDownGoal()
        {
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
        }

        @Override
        public boolean canUse()
        {
            return !isActive();
        }

        @Override
        public void start()
        {
            getNavigation().stop();
            setTarget(null);
        }

        @Override
        public boolean isInterruptable()
        {
            return false;
        }
    }

    /** Low battery: walk to the nearest charger and stand at it until full. */
    private class ChargeGoal extends Goal
    {
        @Nullable
        private BlockPos charger;

        ChargeGoal()
        {
            setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse()
        {
            if (!isActive() || isRogue() || !isLowPower() || getTarget() != null)
            {
                return false;
            }
            charger = JoeChargerBlockEntity.nearest(level(), blockPosition(), 96);
            return charger != null;
        }

        @Override
        public boolean canContinueToUse()
        {
            return charger != null && needsCharge() && JoeChargerBlockEntity.isCharger(level(), charger) && getTarget() == null;
        }

        @Override
        public void tick()
        {
            if (charger != null && distanceToSqr(Vec3.atBottomCenterOf(charger)) > 4 && (getNavigation().isDone() || tickCount % 40 < 2))
            {
                getNavigation().moveTo(charger.getX() + 0.5, charger.getY() + 1, charger.getZ() + 0.5, 1.0);
            }
        }

        @Override
        public void stop()
        {
            getNavigation().stop();
        }
    }

    /** Runs the program. */
    private class ProgramGoal extends Goal
    {
        ProgramGoal()
        {
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse()
        {
            return isActive() && !isRogue() && program != null && !halted && getTarget() == null;
        }

        @Override
        public boolean requiresUpdateEveryTick()
        {
            return true;
        }

        @Override
        public void tick()
        {
            runProgram();
        }

        @Override
        public void stop()
        {
            getNavigation().stop();
        }
    }

    /** No program: potter about near home. */
    private class IdleGoal extends WaterAvoidingRandomStrollGoal
    {
        IdleGoal()
        {
            super(WorkingJoeEntity.this, 0.7);
        }

        @Override
        public boolean canUse()
        {
            return isActive() && (program == null || halted) && super.canUse();
        }
    }

    // ------------------------------------------------------------------ ticking

    @Override
    public void aiStep()
    {
        super.aiStep();
        if (!(level() instanceof ServerLevel server))
        {
            return;
        }
        // battery
        if (AvpConfig.server(AvpConfig.SERVER.joeNeedsPower))
        {
            int draw = 1 + (getNavigation().isInProgress() ? 3 : 0) + (getTarget() != null ? 4 : 0);
            charge = Math.max(0, charge - draw);
        }
        byte mode = mode();
        if (mode == ACTIVE && charge <= 0 && AvpConfig.server(AvpConfig.SERVER.joeNeedsPower))
        {
            entityData.set(MODE, POWERED_DOWN);
            playSound(SoundEvents.BEACON_DEACTIVATE, 0.6F, 1.6F);
            report("Battery depleted. Powering down at " + where(blockPosition()) + ".");
        }
        else if (mode == POWERED_DOWN && (charge > capacity() * 0.1F || !AvpConfig.server(AvpConfig.SERVER.joeNeedsPower)))
        {
            entityData.set(MODE, ACTIVE);
            playSound(SoundEvents.BEACON_ACTIVATE, 0.6F, 1.6F);
        }
        if (!isActive())
        {
            return;
        }
        // speech
        if (--chatterIn <= 0)
        {
            chatterIn = 1200 + random.nextInt(2400);
            if (isRogue())
            {
                say(ROGUE_LINES[random.nextInt(ROGUE_LINES.length)]);
            }
            else if (AvpConfig.server(AvpConfig.SERVER.joeChatter) && server.getNearestPlayer(this, 6) != null)
            {
                say(CHATTER[random.nextInt(CHATTER.length)]);
            }
        }
        if (isRogue() && getTarget() != null && tickCount % 100 == 0)
        {
            say(ROGUE_LINES[random.nextInt(ROGUE_LINES.length)]);
        }
        // seeing a hostile raises the alarm for the whole network
        if (tickCount % 20 == 0 && !isRogue() && owner != null)
        {
            LivingEntity threat = nearestHostile(12);
            if (threat != null)
            {
                JoeNetwork.raiseAlarm(this, threat.blockPosition(), threat.getName().getString());
            }
        }
        if (tickCount % 10 == 0 && program != null && !isRogue())
        {
            checkEvents(server);
        }
        // show what it carries
        ItemStack carried = firstCarried();
        if (!ItemStack.isSameItem(carried, getMainHandItem()))
        {
            setItemSlot(EquipmentSlot.MAINHAND, carried.copyWithCount(1));
        }
    }

    private ItemStack firstCarried()
    {
        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            if (!inventory.getItem(i).isEmpty())
            {
                return inventory.getItem(i);
            }
        }
        return ItemStack.EMPTY;
    }

    @Nullable
    private LivingEntity nearestHostile(double range)
    {
        LivingEntity best = null;
        double bestDist = range * range;
        for (LivingEntity e : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(range)))
        {
            boolean hostile = e instanceof Enemy || e.getType().is(org.avp.registry.ModTags.XENOMORPHS)
                    || e.getType().is(org.avp.registry.ModTags.PREDATORS) || e instanceof WorkingJoeEntity joe && joe.isRogue();
            if (hostile && e.isAlive() && e != this && hasLineOfSight(e) && distanceToSqr(e) < bestDist)
            {
                best = e;
                bestDist = distanceToSqr(e);
            }
        }
        return best;
    }

    // ------------------------------------------------------------------ talking

    public void say(String text)
    {
        if (!(level() instanceof ServerLevel server))
        {
            return;
        }
        Component line = Component.literal("<" + joeName() + "> ").withStyle(isRogue() ? ChatFormatting.RED : ChatFormatting.GOLD)
                .append(Component.literal(text).withStyle(ChatFormatting.GRAY));
        for (ServerPlayer player : server.players())
        {
            if (player.distanceToSqr(this) < 24 * 24)
            {
                player.sendSystemMessage(line);
            }
        }
        playSound(SoundEvents.VILLAGER_AMBIENT, 0.5F, 0.6F);
    }

    /** A message to the owner over the Seegson network (if they're online). */
    public void report(String text)
    {
        if (owner == null || !AvpConfig.server(AvpConfig.SERVER.joeReports) || !(level() instanceof ServerLevel server))
        {
            return;
        }
        ServerPlayer player = server.getServer().getPlayerList().getPlayer(owner);
        if (player != null)
        {
            player.sendSystemMessage(Component.literal("[Seegson] ").withStyle(ChatFormatting.DARK_AQUA)
                    .append(Component.literal(joeName() + ": ").withStyle(ChatFormatting.GOLD))
                    .append(Component.literal(text).withStyle(ChatFormatting.GRAY)));
        }
    }

    static String where(BlockPos pos)
    {
        return pos.getX() + " " + pos.getY() + " " + pos.getZ();
    }

    // ------------------------------------------------------------------ programs

    /** Loads a program from text. Returns the errors (empty if it was loaded). */
    public List<String> loadProgram(String text, @Nullable Player by)
    {
        List<String> problems = new ArrayList<>();
        BlockPos at = blockPosition();
        JoeProgram parsed = JoeProgram.parse(text, at);
        if (parsed.ok() && parsed.load != null && level() instanceof ServerLevel server)
        {
            String file = JoeNetwork.readDatapackProgram(server, parsed.load);
            if (file == null)
            {
                problems.add("No program called " + parsed.load + " was found in the data packs.");
                return problems;
            }
            text = file;
            parsed = JoeProgram.parse(text, at);
        }
        if (!parsed.ok())
        {
            for (JoeProgram.Error e : parsed.errors)
            {
                problems.add("Line " + e.line() + ": " + e.message());
            }
            return problems;
        }
        source = text;
        anchor = at;
        install(parsed);
        snapshots.clear();
        if (parsed.name != null)
        {
            setCustomName(Component.literal(parsed.name));
        }
        if (by != null && owner == null)
        {
            setOwner(by.getUUID());
        }
        say("Program accepted. " + parsed.body.size() + " instructions, " + parsed.handlers.size() + " responses.");
        playSound(SoundEvents.NOTE_BLOCK_CHIME.value(), 0.8F, 1.4F);
        return problems;
    }

    private void install(JoeProgram parsed)
    {
        program = parsed;
        home = parsed.home;
        pc = 0;
        halted = false;
        task = null;
        interrupt = null;
        guardCenter = null;
        cooldown.clear();
        redstoneWas.clear();
        wasNight = level().isNight();
        seenAlarm = JoeNetwork.alarmSeq(owner);
    }

    public void clearProgram()
    {
        program = null;
        source = "";
        task = null;
        interrupt = null;
        guardCenter = null;
        getNavigation().stop();
    }

    /** "Recall": stop what you're doing and walk home. */
    public void recall()
    {
        task = null;
        interrupt = new GotoTask(home != null ? home : anchor, 2.0);
        halted = false;
    }

    public void setSecurity(boolean on)
    {
        security = on;
    }

    public void resume()
    {
        halted = false;
    }

    public String currentActivity()
    {
        if (!isActive())
        {
            return "powered down";
        }
        if (isRogue())
        {
            return "MALFUNCTIONING";
        }
        if (getTarget() != null)
        {
            return "engaging " + getTarget().getName().getString();
        }
        if (program == null)
        {
            return "idle (no program)";
        }
        if (halted)
        {
            return "program finished";
        }
        Task t = interrupt != null ? interrupt : task;
        return t == null ? "running" : t.describe();
    }

    private void runProgram()
    {
        if (program == null || program.body.isEmpty() && interrupt == null)
        {
            return;
        }
        if (interrupt != null)
        {
            if (interrupt.tick())
            {
                interrupt = null;
            }
            return;
        }
        if (task == null)
        {
            if (pc >= program.body.size())
            {
                pc = 0; // programs loop
            }
            Instr instr = program.body.get(pc++);
            task = taskFor(instr);
        }
        if (task == null || task.tick())
        {
            task = null;
        }
    }

    /** "when" handlers. */
    private void checkEvents(ServerLevel server)
    {
        if (interrupt != null)
        {
            return;
        }
        boolean night = server.isNight();
        boolean low = isLowPower();
        boolean hurt = hurtFlag;
        hurtFlag = false;
        long alarm = JoeNetwork.alarmSeq(owner);
        boolean newAlarm = alarm != seenAlarm && JoeNetwork.alarmPos(owner) != null;
        seenAlarm = alarm;
        boolean handled = false;
        for (int i = 0; i < program.handlers.size() && !handled; i++)
        {
            JoeProgram.Handler h = program.handlers.get(i);
            boolean fire = switch (h.event())
            {
                case NIGHT -> night && !wasNight;
                case DAY -> !night && wasNight;
                case LOWPOWER -> low && !wasLow;
                case HURT -> hurt;
                case ALARM -> newAlarm;
                case HOSTILE -> nearestHostile(12) != null && ready(i, 200);
                case PLAYER -> strangerNear() && ready(i, 400);
                case REDSTONE ->
                {
                    boolean on = powered(h.pos());
                    boolean was = redstoneWas.getOrDefault(i, on);
                    redstoneWas.put(i, on);
                    yield on && !was;
                }
                default -> false;
            };
            if (fire)
            {
                cooldown.put(i, level().getGameTime());
                interrupt = taskFor(h.action());
                handled = interrupt != null;
            }
        }
        // a Joe on security with no alarm response of its own goes to see
        if (!handled && newAlarm && security && program.handlers.stream().noneMatch(h -> h.event() == Cond.ALARM))
        {
            BlockPos at = JoeNetwork.alarmPos(owner);
            if (at != null && at.closerThan(blockPosition(), 64))
            {
                interrupt = new GotoTask(at, 3.0);
            }
        }
        wasNight = night;
        wasLow = low;
    }

    private boolean ready(int handler, int ticks)
    {
        return level().getGameTime() - cooldown.getOrDefault(handler, -100_000L) > ticks;
    }

    private boolean strangerNear()
    {
        for (Player player : level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(6)))
        {
            if (!isOwner(player) && !player.isSpectator())
            {
                return true;
            }
        }
        return false;
    }

    private boolean powered(@Nullable BlockPos pos)
    {
        if (pos == null || !level().isLoaded(pos))
        {
            return false;
        }
        BlockState state = level().getBlockState(pos);
        return level().hasNeighborSignal(pos) || state.hasProperty(BlockStateProperties.POWERED) && state.getValue(BlockStateProperties.POWERED)
                || state.hasProperty(BlockStateProperties.LIT) && state.getValue(BlockStateProperties.LIT);
    }

    private boolean test(Instr instr)
    {
        return switch (instr.cond)
        {
            case NIGHT -> level().isNight();
            case DAY -> !level().isNight();
            case LOWPOWER -> isLowPower();
            case FULL -> !hasRoom();
            case EMPTY -> firstCarried().isEmpty();
            case ALARM -> JoeNetwork.alarmPos(owner) != null;
            case HURT -> getHealth() < getMaxHealth() * 0.5F;
            case RAINING -> level().isRaining();
            case PLAYER -> strangerNear();
            case HOSTILE -> nearestHostile(12) != null;
            case POWERED -> powered(instr.a);
            case HAS -> count(instr.item) >= instr.n;
            case CHANCE -> random.nextInt(100) < instr.n;
            default -> false;
        };
    }

    private boolean hasRoom()
    {
        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            if (inventory.getItem(i).isEmpty())
            {
                return true;
            }
        }
        return false;
    }

    private int count(@Nullable Item item)
    {
        int n = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            ItemStack stack = inventory.getItem(i);
            if (item == null ? !stack.isEmpty() : stack.is(item))
            {
                n += stack.getCount();
            }
        }
        return n;
    }

    @Nullable
    private Task taskFor(Instr instr)
    {
        switch (instr.op)
        {
            case GOTO ->
            {
                BlockPos target = switch (instr.place)
                {
                    case HOME -> home != null ? home : anchor;
                    case ALARM -> JoeNetwork.alarmPos(owner);
                    case OWNER -> ownerPlayer() != null ? ownerPlayer().blockPosition() : null;
                    case POINT -> instr.points.get(0);
                };
                return target == null ? null : new GotoTask(target, 1.5);
            }
            case PATROL ->
            {
                return new PatrolTask(instr.points);
            }
            case WAIT ->
            {
                return new WaitTask(instr.n);
            }
            case SAY ->
            {
                say(instr.text);
                return null;
            }
            case REPORT ->
            {
                report(instr.text);
                return null;
            }
            case HAUL ->
            {
                return new HaulTask(instr);
            }
            case FARM ->
            {
                return new FarmTask(instr);
            }
            case MAINTAIN ->
            {
                return new MaintainTask(instr);
            }
            case LEVER, PRESS, OPEN, CLOSE ->
            {
                return new SwitchTask(instr);
            }
            case GUARD ->
            {
                return new GuardTask(instr);
            }
            case FOLLOW ->
            {
                return new FollowTask(instr.m);
            }
            case CHARGE ->
            {
                BlockPos charger = JoeChargerBlockEntity.nearest(level(), blockPosition(), 96);
                if (charger == null)
                {
                    report("Unable to recharge: no Joe charger within range.");
                    return null;
                }
                return new ChargeTask(charger);
            }
            case SECURITY ->
            {
                security = instr.flag;
                return null;
            }
            case ALARM ->
            {
                JoeNetwork.raiseAlarm(this, blockPosition(), "an alarm");
                return null;
            }
            case LABEL ->
            {
                return null;
            }
            case JUMP ->
            {
                pc = program.labels.getOrDefault(instr.text, pc);
                return new WaitTask(1); // a loop with nothing in it can't hang the server
            }
            case STOP ->
            {
                halted = true;
                pc = 0;
                report("Program complete.");
                return null;
            }
            case IF, UNLESS ->
            {
                boolean yes = test(instr) == (instr.op == JoeProgram.Op.IF);
                return yes ? taskFor(instr.inner) : null;
            }
        }
        return null;
    }

    @Nullable
    private Player ownerPlayer()
    {
        return owner == null ? null : level().getPlayerByUUID(owner);
    }

    // ------------------------------------------------------------------ tasks

    /** A running statement. tick() returns true when it's finished. */
    interface Task
    {
        boolean tick();

        String describe();
    }

    /** Walks to within range of a spot; gives up (and reports) if it can't get there. */
    private class Walker
    {
        private BlockPos to = BlockPos.ZERO;
        private int tries;
        private double best;
        boolean failed;

        void target(BlockPos pos)
        {
            if (!pos.equals(to))
            {
                to = pos;
                tries = 0;
                best = Double.MAX_VALUE;
                failed = false;
            }
        }

        boolean reached(BlockPos pos, double range)
        {
            target(pos);
            double dist = distanceToSqr(Vec3.atBottomCenterOf(pos));
            if (dist <= range * range)
            {
                getNavigation().stop();
                return true;
            }
            if (getNavigation().isDone() || tickCount % 40 == 0)
            {
                if (dist < best - 1)
                {
                    best = dist;
                    tries = 0;
                }
                else if (++tries > 12)
                {
                    failed = true;
                    report("Unable to reach " + where(pos) + ". Route is obstructed.");
                    return true;
                }
                getNavigation().moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 1.0);
            }
            return false;
        }
    }

    private class GotoTask implements Task
    {
        private final BlockPos to;
        private final double range;
        private final Walker walker = new Walker();

        GotoTask(BlockPos to, double range)
        {
            this.to = to;
            this.range = range;
        }

        @Override
        public boolean tick()
        {
            return walker.reached(to, range);
        }

        @Override
        public String describe()
        {
            return "going to " + where(to);
        }
    }

    private class PatrolTask implements Task
    {
        private final List<BlockPos> route;
        private int at;
        private final Walker walker = new Walker();

        PatrolTask(List<BlockPos> route)
        {
            this.route = route;
        }

        @Override
        public boolean tick()
        {
            if (walker.reached(route.get(at), 1.5))
            {
                at++;
            }
            return at >= route.size();
        }

        @Override
        public String describe()
        {
            return "patrolling (" + (at + 1) + "/" + route.size() + ")";
        }
    }

    private class WaitTask implements Task
    {
        private int left;

        WaitTask(int ticks)
        {
            left = ticks;
        }

        @Override
        public boolean tick()
        {
            return --left <= 0;
        }

        @Override
        public String describe()
        {
            return "waiting (" + left / 20 + "s)";
        }
    }

    @Nullable
    private IItemHandler container(BlockPos pos)
    {
        IItemHandler handler = level().getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
        return handler != null ? handler : level().getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.UP);
    }

    /** Puts everything it carries (that matches) into a container. Returns true if it could put it all. */
    private boolean unload(IItemHandler into, @Nullable Item only, int keepSeeds)
    {
        boolean all = true;
        Map<Item, Integer> kept = new HashMap<>();
        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty() || only != null && !stack.is(only))
            {
                continue;
            }
            if (keepSeeds > 0 && isSeed(stack))
            {
                int have = kept.getOrDefault(stack.getItem(), 0);
                if (have < keepSeeds)
                {
                    int keep = Math.min(stack.getCount(), keepSeeds - have);
                    kept.put(stack.getItem(), have + keep);
                    if (keep == stack.getCount())
                    {
                        continue;
                    }
                    ItemStack rest = stack.split(stack.getCount() - keep);
                    ItemStack left = ItemHandlerHelper.insertItemStacked(into, rest, false);
                    stack.grow(left.getCount());
                    all &= left.isEmpty();
                    continue;
                }
            }
            ItemStack left = ItemHandlerHelper.insertItemStacked(into, stack, false);
            inventory.setItem(i, left);
            all &= left.isEmpty();
        }
        return all;
    }

    private static boolean isSeed(ItemStack stack)
    {
        return stack.getItem() instanceof BlockItem block && block.getBlock() instanceof CropBlock;
    }

    private class HaulTask implements Task
    {
        private final Instr instr;
        private final Walker walker = new Walker();
        private boolean loaded;

        HaulTask(Instr instr)
        {
            this.instr = instr;
        }

        @Override
        public boolean tick()
        {
            if (!loaded)
            {
                if (!walker.reached(instr.a, 2.2))
                {
                    return false;
                }
                if (walker.failed)
                {
                    return true;
                }
                IItemHandler from = container(instr.a);
                if (from == null)
                {
                    report("Haul: there is no container at " + where(instr.a) + ".");
                    return true;
                }
                int wanted = instr.n;
                for (int slot = 0; slot < from.getSlots() && wanted > 0; slot++)
                {
                    ItemStack in = from.getStackInSlot(slot);
                    if (in.isEmpty() || instr.item != null && !in.is(instr.item))
                    {
                        continue;
                    }
                    ItemStack taken = from.extractItem(slot, Math.min(wanted, in.getMaxStackSize()), true);
                    ItemStack left = inventory.addItem(taken.copy());
                    int moved = taken.getCount() - left.getCount();
                    if (moved > 0)
                    {
                        from.extractItem(slot, moved, false);
                        wanted -= moved;
                    }
                }
                swing(InteractionHand.MAIN_HAND);
                loaded = true;
                if (wanted == instr.n)
                {
                    return true; // nothing to carry
                }
            }
            if (!walker.reached(instr.b, 2.2))
            {
                return false;
            }
            IItemHandler to = container(instr.b);
            if (to == null)
            {
                report("Haul: there is no container at " + where(instr.b) + ".");
                return true;
            }
            if (!unload(to, instr.item, 0))
            {
                report("Haul: the container at " + where(instr.b) + " is full.");
            }
            swing(InteractionHand.MAIN_HAND);
            return true;
        }

        @Override
        public String describe()
        {
            return loaded ? "delivering to " + where(instr.b) : "collecting from " + where(instr.a);
        }
    }

    private class FarmTask implements Task
    {
        private final Instr instr;
        private final Walker walker = new Walker();
        @Nullable
        private BlockPos job;
        private boolean planting;
        private int done;

        FarmTask(Instr instr)
        {
            this.instr = instr;
        }

        @Override
        public boolean tick()
        {
            ServerLevel level = (ServerLevel) level();
            if (job == null)
            {
                planting = false;
                job = findRipe();
                if (job == null && seedSlot() >= 0)
                {
                    job = findBareFarmland();
                    planting = job != null;
                }
                if (job == null)
                {
                    if (instr.store != null && !firstCarried().isEmpty() && (count(null) > 16 || done > 0))
                    {
                        if (!walker.reached(instr.store, 2.2))
                        {
                            return false;
                        }
                        IItemHandler store = container(instr.store);
                        if (store != null && !unload(store, null, 16))
                        {
                            report("Farm: the store at " + where(instr.store) + " is full.");
                        }
                    }
                    return true;
                }
            }
            if (!walker.reached(job, 2.5))
            {
                return false;
            }
            BlockState state = level.getBlockState(job);
            if (planting)
            {
                int slot = seedSlot();
                if (slot >= 0 && state.isAir() && level.getBlockState(job.below()).getBlock() instanceof FarmBlock)
                {
                    ItemStack seed = inventory.getItem(slot);
                    level.setBlock(job, ((CropBlock) ((BlockItem) seed.getItem()).getBlock()).getStateForAge(0), 3);
                    seed.shrink(1);
                    swing(InteractionHand.MAIN_HAND);
                }
            }
            else if (state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state))
            {
                List<ItemStack> drops = Block.getDrops(state, level, job, null, WorkingJoeEntity.this, ItemStack.EMPTY);
                ItemStack seedItem = crop.getCloneItemStack(level, job, state);
                boolean replanted = false;
                for (ItemStack drop : drops)
                {
                    if (!replanted && ItemStack.isSameItem(drop, seedItem))
                    {
                        drop.shrink(1);
                        replanted = true;
                    }
                    ItemStack left = inventory.addItem(drop);
                    if (!left.isEmpty())
                    {
                        spawnAtLocation(left);
                    }
                }
                level.levelEvent(2001, job, Block.getId(state));
                level.setBlock(job, replanted ? crop.getStateForAge(0) : Blocks.AIR.defaultBlockState(), 3);
                swing(InteractionHand.MAIN_HAND);
                done++;
            }
            job = null;
            return false;
        }

        private int seedSlot()
        {
            for (int i = 0; i < inventory.getContainerSize(); i++)
            {
                if (isSeed(inventory.getItem(i)))
                {
                    return i;
                }
            }
            return -1;
        }

        @Nullable
        private BlockPos findRipe()
        {
            return nearestIn(instr, pos ->
            {
                BlockState s = level().getBlockState(pos);
                return s.getBlock() instanceof CropBlock crop && crop.isMaxAge(s);
            });
        }

        @Nullable
        private BlockPos findBareFarmland()
        {
            return nearestIn(instr, pos -> level().getBlockState(pos).isAir() && level().getBlockState(pos.below()).getBlock() instanceof FarmBlock);
        }

        @Override
        public String describe()
        {
            return job == null ? "tending crops" : planting ? "planting at " + where(job) : "harvesting at " + where(job);
        }
    }

    @Nullable
    private BlockPos nearestIn(Instr instr, java.util.function.Predicate<BlockPos> test)
    {
        BlockPos best = null;
        double bestDist = Double.MAX_VALUE;
        for (BlockPos pos : BlockPos.betweenClosed(instr.a, instr.b))
        {
            if (level().isLoaded(pos) && test.test(pos))
            {
                double d = distanceToSqr(Vec3.atCenterOf(pos));
                if (d < bestDist)
                {
                    best = pos.immutable();
                    bestDist = d;
                }
            }
        }
        return best;
    }

    /** What "maintain" looks after: powerlines, power gear, lights. */
    private static boolean maintained(BlockState state)
    {
        Block block = state.getBlock();
        return block instanceof org.avp.block.PowerlineBlock || block instanceof org.avp.block.BreakerBlock || block instanceof TorchBlock
                || block instanceof LanternBlock || block instanceof org.avp.block.PoweredBlock && state.getLightEmission() > 0
                || block instanceof org.avp.block.PoweredBlock && state.hasProperty(BlockStateProperties.LIT);
    }

    private class MaintainTask implements Task
    {
        private final Instr instr;
        private final Walker walker = new Walker();
        @Nullable
        private BlockPos job;
        private boolean fetched;
        private int fixed;
        private final List<String> missing = new ArrayList<>();

        MaintainTask(Instr instr)
        {
            this.instr = instr;
            if (!snapshots.containsKey(instr.line))
            {
                Map<BlockPos, BlockState> snap = new HashMap<>();
                for (BlockPos pos : BlockPos.betweenClosed(instr.a, instr.b))
                {
                    BlockState state = level().getBlockState(pos);
                    if (maintained(state) && snap.size() < 512)
                    {
                        snap.put(pos.immutable(), state);
                    }
                }
                snapshots.put(instr.line, snap);
                report("Maintenance survey: watching " + snap.size() + " fittings.");
            }
        }

        private Map<BlockPos, BlockState> snap()
        {
            return snapshots.getOrDefault(instr.line, Map.of());
        }

        @Nullable
        private BlockPos nextBroken()
        {
            BlockPos best = null;
            double bestDist = Double.MAX_VALUE;
            for (Map.Entry<BlockPos, BlockState> e : snap().entrySet())
            {
                BlockState now = level().getBlockState(e.getKey());
                if (now.getBlock() != e.getValue().getBlock() && now.canBeReplaced() && count(e.getValue().getBlock().asItem()) > 0)
                {
                    double d = distanceToSqr(Vec3.atCenterOf(e.getKey()));
                    if (d < bestDist)
                    {
                        best = e.getKey();
                        bestDist = d;
                    }
                }
            }
            return best;
        }

        /** Parts needed for everything that's broken, by item. */
        private Map<Item, Integer> needed()
        {
            Map<Item, Integer> need = new HashMap<>();
            for (Map.Entry<BlockPos, BlockState> e : snap().entrySet())
            {
                BlockState now = level().getBlockState(e.getKey());
                if (now.getBlock() != e.getValue().getBlock() && now.canBeReplaced())
                {
                    need.merge(e.getValue().getBlock().asItem(), 1, Integer::sum);
                }
            }
            return need;
        }

        @Override
        public boolean tick()
        {
            if (!fetched)
            {
                fetched = true;
                Map<Item, Integer> need = needed();
                need.replaceAll((item, n) -> Math.max(0, n - count(item)));
                need.values().removeIf(n -> n <= 0);
                if (!need.isEmpty() && instr.store != null)
                {
                    task = new FetchThenTask(instr.store, need, this);
                    return false;
                }
            }
            if (job == null)
            {
                job = nextBroken();
                if (job == null)
                {
                    Map<Item, Integer> need = needed();
                    if (!need.isEmpty())
                    {
                        need.forEach((item, n) -> missing.add(n + "x " + item.getDescription().getString()));
                        report("Maintenance: unable to repair - need " + String.join(", ", missing) + ".");
                    }
                    else if (fixed > 0)
                    {
                        report("Maintenance complete: repaired " + fixed + " fittings.");
                    }
                    return true;
                }
            }
            if (!walker.reached(job, 3.0))
            {
                return false;
            }
            BlockState want = snap().get(job);
            BlockState now = level().getBlockState(job);
            Item part = want == null ? Items.AIR : want.getBlock().asItem();
            if (want != null && now.canBeReplaced() && now.getBlock() != want.getBlock() && take(part))
            {
                BlockState placed = Block.updateFromNeighbourShapes(want, level(), job);
                if (placed.canSurvive(level(), job))
                {
                    level().setBlock(job, placed, 3);
                    playSound(SoundEvents.METAL_PLACE, 1F, 1F);
                    fixed++;
                }
                else
                {
                    inventory.addItem(new ItemStack(part));
                    snapshots.get(instr.line).remove(job);
                }
                swing(InteractionHand.MAIN_HAND);
            }
            job = null;
            return false;
        }

        @Override
        public String describe()
        {
            return job == null ? "inspecting fittings" : "repairing at " + where(job);
        }
    }

    private boolean take(Item item)
    {
        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            if (inventory.getItem(i).is(item))
            {
                inventory.getItem(i).shrink(1);
                return true;
            }
        }
        return false;
    }

    /** Collects parts from a store, then goes back to the task it was fetching for. */
    private class FetchThenTask implements Task
    {
        private final BlockPos store;
        private final Map<Item, Integer> need;
        private final Task then;
        private final Walker walker = new Walker();

        FetchThenTask(BlockPos store, Map<Item, Integer> need, Task then)
        {
            this.store = store;
            this.need = need;
            this.then = then;
        }

        @Override
        public boolean tick()
        {
            if (!walker.reached(store, 2.2))
            {
                return false;
            }
            IItemHandler from = container(store);
            if (from != null)
            {
                for (int slot = 0; slot < from.getSlots(); slot++)
                {
                    ItemStack in = from.getStackInSlot(slot);
                    int want = in.isEmpty() ? 0 : need.getOrDefault(in.getItem(), 0);
                    if (want > 0)
                    {
                        ItemStack got = from.extractItem(slot, want, false);
                        ItemStack left = inventory.addItem(got);
                        if (!left.isEmpty())
                        {
                            from.insertItem(slot, left, false);
                        }
                        need.merge(in.getItem(), -(got.getCount() - left.getCount()), Integer::sum);
                    }
                }
            }
            task = then;
            return false;
        }

        @Override
        public String describe()
        {
            return "collecting parts from " + where(store);
        }
    }

    private class SwitchTask implements Task
    {
        private final Instr instr;
        private final Walker walker = new Walker();

        SwitchTask(Instr instr)
        {
            this.instr = instr;
        }

        @Override
        public boolean tick()
        {
            if (!walker.reached(instr.a, 2.8))
            {
                return false;
            }
            Level level = level();
            BlockState state = level.getBlockState(instr.a);
            switch (instr.op)
            {
                case LEVER ->
                {
                    if (state.getBlock() instanceof LeverBlock lever)
                    {
                        if (state.getValue(LeverBlock.POWERED) != instr.flag)
                        {
                            lever.pull(state, level, instr.a, null);
                        }
                    }
                    else
                    {
                        report("There is no lever at " + where(instr.a) + ".");
                    }
                }
                case PRESS ->
                {
                    if (state.getBlock() instanceof ButtonBlock button)
                    {
                        if (!state.getValue(ButtonBlock.POWERED))
                        {
                            button.press(state, level, instr.a, null);
                            level.playSound(null, instr.a, SoundEvents.STONE_BUTTON_CLICK_ON, net.minecraft.sounds.SoundSource.BLOCKS, 1F, 1F);
                        }
                    }
                    else
                    {
                        report("There is no button at " + where(instr.a) + ".");
                    }
                }
                default ->
                {
                    boolean open = instr.op == JoeProgram.Op.OPEN;
                    if (state.getBlock() instanceof DoorBlock door)
                    {
                        door.setOpen(WorkingJoeEntity.this, level, state, instr.a, open);
                    }
                    else if (state.hasProperty(BlockStateProperties.OPEN))
                    {
                        level.setBlock(instr.a, state.setValue(BlockStateProperties.OPEN, open), 10);
                    }
                    else
                    {
                        report("There is no door at " + where(instr.a) + ".");
                    }
                }
            }
            swing(InteractionHand.MAIN_HAND);
            return true;
        }

        @Override
        public String describe()
        {
            return instr.op.name().toLowerCase(java.util.Locale.ROOT) + " at " + where(instr.a);
        }
    }

    private class GuardTask implements Task
    {
        private final Instr instr;
        private final Walker walker = new Walker();
        private int left;

        GuardTask(Instr instr)
        {
            this.instr = instr;
            this.left = instr.m;
        }

        @Override
        public boolean tick()
        {
            guardCenter = instr.a;
            guardRadius = instr.n;
            if (!blockPosition().closerThan(instr.a, 2.5))
            {
                walker.reached(instr.a, 1.5);
            }
            else if (tickCount % 60 == 0)
            {
                getLookControl().setLookAt(getX() + random.nextInt(9) - 4, getEyeY(), getZ() + random.nextInt(9) - 4);
            }
            if (instr.m > 0 && --left <= 0)
            {
                guardCenter = null;
                return true;
            }
            return false;
        }

        @Override
        public String describe()
        {
            return "guarding " + where(instr.a) + (instr.m > 0 ? " (" + left / 20 + "s)" : "");
        }
    }

    private class FollowTask implements Task
    {
        private final int ticks;
        private int left;

        FollowTask(int ticks)
        {
            this.ticks = ticks;
            this.left = ticks;
        }

        @Override
        public boolean tick()
        {
            Player player = ownerPlayer();
            if (player == null)
            {
                return true;
            }
            double dist = distanceToSqr(player);
            if (dist > 32 * 32 && player.onGround())
            {
                teleportTo(player.getX(), player.getY(), player.getZ());
                getNavigation().stop();
            }
            else if (dist > 9 && (getNavigation().isDone() || tickCount % 20 == 0))
            {
                getNavigation().moveTo(player, 1.1);
            }
            else if (dist <= 9)
            {
                getNavigation().stop();
                getLookControl().setLookAt(player);
            }
            return ticks > 0 && --left <= 0;
        }

        @Override
        public String describe()
        {
            return "following its owner";
        }
    }

    private class ChargeTask implements Task
    {
        private final BlockPos charger;
        private final Walker walker = new Walker();

        ChargeTask(BlockPos charger)
        {
            this.charger = charger;
        }

        @Override
        public boolean tick()
        {
            if (!walker.reached(charger.above(), 1.8))
            {
                return false;
            }
            return walker.failed || !needsCharge() || !JoeChargerBlockEntity.isCharger(level(), charger);
        }

        @Override
        public String describe()
        {
            return "recharging (" + chargePercent() + "%)";
        }
    }

    // ------------------------------------------------------------------ interaction

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (level().isClientSide)
        {
            return InteractionResult.SUCCESS;
        }
        boolean mine = owner == null || isOwner(player) || player.hasPermissions(2) && player.isCreative();
        if (isRogue())
        {
            if (mine && stack.is(ModItems.PART_PROCESSOR.get()))
            {
                entityData.set(MODE, ACTIVE);
                setTarget(null);
                setHealth(Math.max(getHealth(), getMaxHealth() * 0.5F));
                stack.consume(1, player);
                say("Processor replaced. Restoring normal operation.");
                report("Repaired and back in service.");
                return InteractionResult.CONSUME;
            }
            return InteractionResult.PASS;
        }
        if (stack.is(Items.WRITABLE_BOOK) || stack.is(Items.WRITTEN_BOOK))
        {
            if (!mine)
            {
                say("I am sorry. You are not authorised to reprogram this unit.");
                return InteractionResult.CONSUME;
            }
            List<String> problems = loadProgram(bookText(stack), player);
            if (!problems.isEmpty())
            {
                say("Program rejected.");
                for (String p : problems.subList(0, Math.min(6, problems.size())))
                {
                    player.sendSystemMessage(Component.literal("  " + p).withStyle(ChatFormatting.RED));
                }
            }
            return InteractionResult.CONSUME;
        }
        if (stack.is(Items.REDSTONE_BLOCK) && mine)
        {
            addCharge(capacity() / 4);
            stack.consume(1, player);
            playSound(SoundEvents.BEACON_POWER_SELECT, 0.6F, 1.6F);
            return InteractionResult.CONSUME;
        }
        if (stack.isEmpty() && player.isShiftKeyDown() && mine)
        {
            // pack it up (it keeps its program, battery and what it's carrying)
            ItemStack item = new ItemStack(ModItems.WORKING_JOE.get());
            item.set(DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.of(pack()));
            if (hasCustomName())
            {
                item.set(DataComponents.CUSTOM_NAME, getCustomName());
            }
            player.setItemInHand(hand, item);
            JoeNetwork.leave(this);
            discard();
            return InteractionResult.CONSUME;
        }
        if (stack.isEmpty())
        {
            for (Component line : status())
            {
                player.sendSystemMessage(line);
            }
            return InteractionResult.CONSUME;
        }
        return super.mobInteract(player, hand);
    }

    public List<Component> status()
    {
        List<Component> lines = new ArrayList<>();
        lines.add(Component.literal("== " + joeName() + " | Seegson Working Joe ==").withStyle(ChatFormatting.GOLD));
        String ownerName = owner == null ? "unassigned" : level().getPlayerByUUID(owner) != null ? level().getPlayerByUUID(owner).getName().getString() : "offline";
        lines.add(Component.literal("Owner: " + ownerName + "   Battery: " + chargePercent() + "%   Health: " + Math.round(getHealth()) + "/"
                + Math.round(getMaxHealth())).withStyle(ChatFormatting.GRAY));
        lines.add(Component.literal("Status: " + currentActivity() + (security ? "   [SECURITY]" : "")).withStyle(ChatFormatting.GRAY));
        if (program != null)
        {
            lines.add(Component.literal("Program: " + program.body.size() + " instructions, " + program.handlers.size() + " responses, at line "
                    + (pc > 0 && pc <= program.body.size() ? program.body.get(pc - 1).line : 1)).withStyle(ChatFormatting.GRAY));
        }
        StringBuilder carrying = new StringBuilder();
        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            ItemStack s = inventory.getItem(i);
            if (!s.isEmpty())
            {
                carrying.append(carrying.isEmpty() ? "" : ", ").append(s.getCount()).append("x ").append(s.getHoverName().getString());
            }
        }
        if (!carrying.isEmpty())
        {
            lines.add(Component.literal("Carrying: " + carrying).withStyle(ChatFormatting.GRAY));
        }
        return lines;
    }

    static String bookText(ItemStack stack)
    {
        StringBuilder text = new StringBuilder();
        WritableBookContent writable = stack.get(DataComponents.WRITABLE_BOOK_CONTENT);
        if (writable != null)
        {
            for (Filterable<String> page : writable.pages())
            {
                text.append(page.raw()).append('\n');
            }
        }
        WrittenBookContent written = stack.get(DataComponents.WRITTEN_BOOK_CONTENT);
        if (written != null)
        {
            for (Filterable<Component> page : written.pages())
            {
                text.append(page.raw().getString()).append('\n');
            }
        }
        return text.toString();
    }

    // ------------------------------------------------------------------ damage, malfunction

    @Override
    public boolean hurt(DamageSource source, float amount)
    {
        boolean hurt = super.hurt(source, amount);
        if (hurt && level() instanceof ServerLevel server)
        {
            hurtFlag = true;
            server.sendParticles(new net.minecraft.core.particles.ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.MILK_BUCKET)),
                    getX(), getY() + 1.2, getZ(), 6, 0.2, 0.3, 0.2, 0.05);
            if (!isRogue() && isAlive() && getHealth() < getMaxHealth() * 0.35F && AvpConfig.server(AvpConfig.SERVER.joesMalfunction)
                    && random.nextInt(100) < AvpConfig.server(AvpConfig.SERVER.joeMalfunctionChance))
            {
                entityData.set(MODE, ROGUE);
                program = program == null ? null : program; // kept for after repair
                task = null;
                interrupt = null;
                guardCenter = null;
                say("Error. Error. You are becoming hysterical.");
                report("SYSTEM FAULT. Unit is no longer responding to commands.");
                server.sendParticles(ParticleTypes.ELECTRIC_SPARK, getX(), getY() + 1.5, getZ(), 20, 0.3, 0.3, 0.3, 0.2);
            }
            else if (getHealth() < getMaxHealth() * 0.5F && tickCount % 4 == 0)
            {
                report("Unit damaged at " + where(blockPosition()) + ".");
            }
        }
        return hurt;
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target)
    {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living && isRogue())
        {
            living.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
        }
        return hit;
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit)
    {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        for (int i = 0; i < inventory.getContainerSize(); i++)
        {
            spawnAtLocation(inventory.removeItemNoUpdate(i));
        }
        spawnAtLocation(new ItemStack(ModItems.PART_SERVO.get(), 1 + random.nextInt(2)));
        if (random.nextBoolean())
        {
            spawnAtLocation(new ItemStack(ModItems.PART_PROCESSOR.get()));
        }
        spawnAtLocation(new ItemStack(Items.IRON_INGOT, 2 + random.nextInt(3)));
        report("Unit destroyed at " + where(blockPosition()) + ".");
    }

    @Override
    public void die(DamageSource source)
    {
        super.die(source);
        JoeNetwork.leave(this);
    }

    @Override
    public boolean removeWhenFarAway(double distance)
    {
        return false;
    }

    @Override
    public boolean canBeLeashed()
    {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    public float getVoicePitch()
    {
        return 1.4F;
    }

    // ------------------------------------------------------------------ network membership

    @Override
    public void onAddedToLevel()
    {
        super.onAddedToLevel();
        if (!level().isClientSide)
        {
            JoeNetwork.join(this);
        }
    }

    @Override
    public void onRemovedFromLevel()
    {
        super.onRemovedFromLevel();
        JoeNetwork.leave(this);
    }

    // ------------------------------------------------------------------ saving

    public CompoundTag pack()
    {
        CompoundTag tag = new CompoundTag();
        addAdditionalSaveData(tag);
        tag.putFloat("Health", getHealth());
        return tag;
    }

    public void unpack(CompoundTag tag)
    {
        readAdditionalSaveData(tag);
        if (tag.contains("Health"))
        {
            setHealth(tag.getFloat("Health"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        if (owner != null)
        {
            tag.putUUID("JoeOwner", owner);
        }
        tag.putString("Program", source);
        tag.put("Anchor", NbtUtils.writeBlockPos(anchor));
        tag.putInt("PC", pc);
        tag.putBoolean("Halted", halted);
        tag.putInt("Charge", charge);
        tag.putBoolean("Security", security);
        tag.putByte("Mode", mode());
        tag.put("Inventory", inventory.createTag(registryAccess()));
        ListTag snaps = new ListTag();
        snapshots.forEach((line, map) ->
        {
            CompoundTag s = new CompoundTag();
            s.putInt("Line", line);
            ListTag entries = new ListTag();
            map.forEach((pos, state) ->
            {
                CompoundTag e = NbtUtils.writeBlockState(state);
                e.putLong("At", pos.asLong());
                entries.add(e);
            });
            s.put("Blocks", entries);
            snaps.add(s);
        });
        tag.put("Maintain", snaps);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        owner = tag.hasUUID("JoeOwner") ? tag.getUUID("JoeOwner") : null;
        source = tag.getString("Program");
        anchor = NbtUtils.readBlockPos(tag, "Anchor").orElse(blockPosition());
        charge = tag.contains("Charge") ? tag.getInt("Charge") : capacity();
        security = tag.getBoolean("Security");
        entityData.set(MODE, tag.getByte("Mode"));
        inventory.fromTag(tag.getList("Inventory", Tag.TAG_COMPOUND), registryAccess());
        if (!source.isEmpty())
        {
            JoeProgram parsed = JoeProgram.parse(source, anchor);
            if (parsed.ok())
            {
                install(parsed);
                pc = Math.min(tag.getInt("PC"), parsed.body.size());
                halted = tag.getBoolean("Halted");
            }
        }
        snapshots.clear();
        var blocks = level().holderLookup(Registries.BLOCK);
        for (Tag t : tag.getList("Maintain", Tag.TAG_COMPOUND))
        {
            CompoundTag s = (CompoundTag) t;
            Map<BlockPos, BlockState> map = new HashMap<>();
            for (Tag e : s.getList("Blocks", Tag.TAG_COMPOUND))
            {
                CompoundTag entry = (CompoundTag) e;
                map.put(BlockPos.of(entry.getLong("At")), NbtUtils.readBlockState(blocks, entry));
            }
            snapshots.put(s.getInt("Line"), map);
        }
    }

    /** For the network list. */
    public BlockPos homeOrAnchor()
    {
        return home != null ? home : anchor;
    }

    static ResourceLocation id(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(org.avp.AliensVsPredator.MOD_ID, path);
    }

    @Override
    protected AABB getAttackBoundingBox()
    {
        return super.getAttackBoundingBox().inflate(0.3, 0, 0.3);
    }
}
