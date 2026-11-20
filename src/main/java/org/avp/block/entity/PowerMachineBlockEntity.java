package org.avp.block.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

import org.avp.block.PowerMachineBlock;
import org.avp.block.PowerMachineBlock.Kind;
import org.avp.config.AvpConfig;
import org.avp.power.MachineEnergy;
import org.avp.power.PowerNetwork;
import org.avp.registry.ModBlockEntities;
import org.avp.registry.ModDamageTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * Everything the power blocks do each tick. All of them hold FE in a {@link MachineEnergy}.
 *
 * Generators (solar panel, Universal Generator, Repulsion Generator) make FE and push it
 * into whatever is next to them - powerlines, machines, or other mods' cables. Other mods
 * can also pull FE out of them.
 *
 * Transformers take FE in at the back and push it out of the front, up to a limit per tick.
 * Power cells take FE in on any side and give it out on every side.
 * The emitter, satellite dish and tesla coil only use FE.
 */
public class PowerMachineBlockEntity extends BlockEntity
{
    /** What the client draws: a lightning arc from the coil top to a creature or a point. */
    public record Arc(Vec3 to, int entityId, long expires, long seed)
    {
    }

    private final MachineEnergy energy;
    private final ItemStackHandler fuel = new ItemStackHandler(1)
    {
        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return isFuel(stack);
        }

        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }
    };
    private int burnTime;
    private int burnTotal;
    private int magnets;
    private int cooldown;
    /** FE moved/made last tick (for the status message). */
    private int lastFlow;
    private int syncedEnergy = -1;
    private int lastComparator = -1;

    /** What a terminal shows: the power network next to it (worked out every 2 seconds). */
    private int netLines = -1;
    private int netDevices;
    private long netStored;
    private long netCapacity;
    private int netMaking;
    private int netLoad, netLoadCap;
    private boolean netOverload;

    /** Status-message details: solar array size, linked power cells. */
    private int arrayPanels = 1;
    private int arrayOutlets;
    private int linkedCells;
    private long bankStored;
    private long bankCapacity;

    /** Client only. */
    private final List<Arc> arcs = new ArrayList<>();
    private float rotor;
    private float rotorPrev;

    public PowerMachineBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.POWER_MACHINE.get(), pos, state);
        this.energy = new MachineEnergy(capacityFor(kindOf(state)));
    }

    private static Kind kindOf(BlockState state)
    {
        return state.getBlock() instanceof PowerMachineBlock block ? block.kind() : Kind.POWERCELL;
    }

    public Kind kind()
    {
        return kindOf(getBlockState());
    }

    private static int capacityFor(Kind kind)
    {
        return switch (kind)
        {
            case SOLAR_PANEL -> 10_000;
            case GENERATOR -> 50_000;
            case REPULSION_GENERATOR -> 20_000;
            case TRANSFORMER -> AvpConfig.server(AvpConfig.SERVER.transformerRate);
            case STEPDOWN_TRANSFORMER -> AvpConfig.server(AvpConfig.SERVER.stepdownTransformerRate);
            case POWERCELL -> AvpConfig.server(AvpConfig.SERVER.powercellCapacity);
            case REDSTONE_EMITTER -> Math.max(2, AvpConfig.server(AvpConfig.SERVER.redstoneEmitterEnergyPerTick) * 2);
            case SATELLITE_DISH -> 10_000;
            case TERMINAL -> 2_000;
            case TESLA_COIL -> Math.max(20_000, AvpConfig.server(AvpConfig.SERVER.teslaCoilEnergyPerZap) * 4);
        };
    }

    public static boolean isFuel(ItemStack stack)
    {
        return stack.getBurnTime(RecipeType.SMELTING) > 0;
    }

    public MachineEnergy getEnergy()
    {
        return energy;
    }

    public ItemStackHandler getFuel()
    {
        return fuel;
    }

    public int magnets()
    {
        return magnets;
    }

    public void setMagnets(int magnets)
    {
        this.magnets = Mth.clamp(magnets, 0, PowerMachineBlock.MAX_MAGNETS);
        setChanged();
        sync();
    }

    public int comparatorLevel()
    {
        return energy.getMaxEnergyStored() == 0 ? 0 : Math.round(15F * energy.getEnergyStored() / energy.getMaxEnergyStored());
    }

    /**
     * What another block or mod sees on one side.
     * Generators: take FE out. Transformers: put FE in at the back only. Power cell: both.
     * Machines that use power: put FE in.
     */
    @Nullable
    public IEnergyStorage energyFor(@Nullable Direction side)
    {
        Kind kind = kind();
        if (kind.isGenerator())
        {
            return energy.view(false, true, 0);
        }
        if (kind.isTransformer())
        {
            Direction back = getBlockState().getValue(PowerMachineBlock.FACING).getOpposite();
            return side == null || side == back ? energy.view(true, false, energy.getMaxEnergyStored()) : null;
        }
        if (kind == Kind.POWERCELL)
        {
            return energy.view(true, true, AvpConfig.server(AvpConfig.SERVER.powercellRate));
        }
        return energy.view(true, false, Integer.MAX_VALUE);
    }

    // ------------------------------------------------------------------ server

    public static void serverTick(Level level, BlockPos pos, BlockState state, PowerMachineBlockEntity be)
    {
        int before = be.energy.getEnergyStored();
        boolean on = switch (be.kind())
        {
            case SOLAR_PANEL -> be.solarTick(level, pos);
            case GENERATOR -> be.burnFuel(level, pos);
            case REPULSION_GENERATOR -> be.generate(level, pos, be.magnets * AvpConfig.server(AvpConfig.SERVER.repulsionOutputPerMagnet)) || be.magnets > 0;
            case TRANSFORMER, STEPDOWN_TRANSFORMER ->
            {
                be.lastFlow = PowerNetwork.pushOut(level, pos, be.energy, be.energy.getMaxEnergyStored(),
                        new Direction[] { state.getValue(PowerMachineBlock.FACING) }, false);
                yield be.lastFlow > 0;
            }
            case POWERCELL ->
            {
                be.lastFlow = PowerNetwork.pushOut(level, pos, be.energy, AvpConfig.server(AvpConfig.SERVER.powercellRate), null, true);
                be.levelWithLinkedCells(level, pos);
                yield be.energy.getEnergyStored() > 0;
            }
            case REDSTONE_EMITTER -> be.energy.consume(Math.max(1, AvpConfig.server(AvpConfig.SERVER.redstoneEmitterEnergyPerTick)));
            case SATELLITE_DISH -> be.tickPower(level, pos, AvpConfig.server(AvpConfig.SERVER.satelliteDishEnergyPerTick));
            case TESLA_COIL -> be.teslaTick(level, pos);
            case TERMINAL -> be.terminalTick(level, pos);
        };
        if (state.getValue(PowerMachineBlock.LIT) != on)
        {
            level.setBlock(pos, state.setValue(PowerMachineBlock.LIT, on), Block.UPDATE_ALL);
        }
        if (be.energy.getEnergyStored() != before)
        {
            be.setChanged();
        }
        int comparator = be.comparatorLevel();
        if (comparator != be.lastComparator)
        {
            be.lastComparator = comparator;
            level.updateNeighbourForOutputSignal(pos, state.getBlock());
        }
        // the power cell shows how full it is; send that to players now and then
        if (be.kind() == Kind.POWERCELL && level.getGameTime() % 20 == 0 && be.syncedEnergy != be.energy.getEnergyStored())
        {
            be.sync();
        }
    }

    /** Adds what a generator made this tick and pushes FE to its neighbours. */
    private boolean generate(Level level, BlockPos pos, int made)
    {
        energy.fill(made);
        lastFlow = made;
        PowerNetwork.pushOut(level, pos, energy, energy.getMaxEnergyStored(), null, false);
        return made > 0;
    }

    /**
     * Solar panels that touch form an array. Panels wired to something push their power out;
     * the others pass theirs to the wired ones, so one powerline can serve a whole field of panels.
     */
    private boolean solarTick(Level level, BlockPos pos)
    {
        int made = solarOutput(level, pos);
        energy.fill(made);
        lastFlow = made;
        PowerNetwork.SolarArray array = PowerNetwork.solarArray(level, pos);
        arrayPanels = array.panels().size();
        arrayOutlets = array.outlets().size();
        if (array.outlets().contains(pos))
        {
            PowerNetwork.pushOut(level, pos, energy, energy.getMaxEnergyStored(), null, false);
        }
        else
        {
            for (BlockPos outlet : array.outlets())
            {
                if (energy.getEnergyStored() <= 0)
                {
                    break;
                }
                if (level.getBlockEntity(outlet) instanceof PowerMachineBlockEntity panel)
                {
                    int moved = panel.energy.fill(energy.getEnergyStored());
                    energy.drain(moved);
                    if (moved > 0)
                    {
                        panel.setChanged();
                    }
                }
            }
        }
        return made > 0;
    }

    /**
     * Linked power cells (touching each other, or on the same powerlines) even out their charge,
     * so a bank of cells fills and empties together. Each pair is levelled by the fuller cell,
     * up to the cell output limit per tick.
     */
    private void levelWithLinkedCells(Level level, BlockPos pos)
    {
        if (level.getGameTime() % 5 != Math.floorMod(pos.asLong(), 5))
        {
            return; // every quarter second is plenty
        }
        List<BlockPos> linked = PowerNetwork.linkedCells(level, pos);
        linkedCells = linked.size();
        long bankStored = energy.getEnergyStored(), bankCapacity = energy.getMaxEnergyStored();
        int limit = AvpConfig.server(AvpConfig.SERVER.powercellRate) * 5;
        for (BlockPos other : linked)
        {
            if (!(level.getBlockEntity(other) instanceof PowerMachineBlockEntity cell))
            {
                continue;
            }
            bankStored += cell.energy.getEnergyStored();
            bankCapacity += cell.energy.getMaxEnergyStored();
            long mine = energy.getEnergyStored(), theirs = cell.energy.getEnergyStored();
            long myCap = energy.getMaxEnergyStored(), theirCap = cell.energy.getMaxEnergyStored();
            // amount that makes both cells equally full (as a fraction of their size)
            long even = (mine * theirCap - theirs * myCap) / (myCap + theirCap);
            int move = (int) Math.min(limit, even);
            if (move > 0)
            {
                move = cell.energy.fill(energy.drain(move));
                cell.setChanged();
            }
        }
        this.bankStored = bankStored;
        this.bankCapacity = bankCapacity;
    }

    /** Daylight on the panel, 0..1 of the full output (same sums as the daylight detector). */
    private static int solarOutput(Level level, BlockPos pos)
    {
        if (!level.dimensionType().hasSkyLight())
        {
            return 0;
        }
        int light = level.getBrightness(LightLayer.SKY, pos) - level.getSkyDarken();
        float angle = level.getSunAngle(1.0F);
        if (light > 0)
        {
            float toward = angle < (float) Math.PI ? 0.0F : (float) Math.PI * 2F;
            angle += (toward - angle) * 0.2F;
            light = Math.round(light * Mth.cos(angle));
        }
        light = Mth.clamp(light, 0, 15);
        return AvpConfig.server(AvpConfig.SERVER.solarPanelOutput) * light / 15;
    }

    private boolean burnFuel(Level level, BlockPos pos)
    {
        int output = AvpConfig.server(AvpConfig.SERVER.generatorOutput);
        boolean room = energy.getEnergyStored() + output <= energy.getMaxEnergyStored();
        if (burnTime <= 0 && room)
        {
            ItemStack stack = fuel.getStackInSlot(0);
            int time = stack.isEmpty() ? 0 : stack.getBurnTime(RecipeType.SMELTING);
            if (time > 0)
            {
                burnTime = burnTotal = Math.max(1, (int) (time * AvpConfig.server(AvpConfig.SERVER.generatorFuelMultiplier)));
                ItemStack remainder = stack.getCraftingRemainingItem(); // lava bucket -> bucket
                fuel.extractItem(0, 1, false);
                if (!remainder.isEmpty())
                {
                    if (fuel.getStackInSlot(0).isEmpty())
                    {
                        fuel.setStackInSlot(0, remainder);
                    }
                    else
                    {
                        net.minecraft.world.Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, remainder);
                    }
                }
            }
        }
        boolean burning = burnTime > 0 && room;
        if (burning)
        {
            burnTime--;
        }
        generate(level, pos, burning ? output : 0);
        return burning;
    }

    /** The usual rules for machines that use power (see EnergyBuffer.tickPower). */
    private boolean tickPower(Level level, BlockPos pos, int usePerTick)
    {
        if (!AvpConfig.server(AvpConfig.SERVER.machinesNeedPower))
        {
            return true;
        }
        if (energy.consume(usePerTick))
        {
            return true;
        }
        return AvpConfig.server(AvpConfig.SERVER.redstonePowersMachines) && level.hasNeighborSignal(pos);
    }

    /** Where arcs start: the ring at the top of the coil. */
    public static Vec3 coilTop(BlockPos pos)
    {
        return Vec3.atBottomCenterOf(pos).add(0, 2.05, 0);
    }

    private boolean teslaTick(Level level, BlockPos pos)
    {
        boolean on = tickPower(level, pos, AvpConfig.server(AvpConfig.SERVER.teslaCoilEnergyPerTick));
        if (cooldown > 0)
        {
            cooldown--;
        }
        if (!on || cooldown > 0)
        {
            return on;
        }
        int zapCost = AvpConfig.server(AvpConfig.SERVER.teslaCoilEnergyPerZap);
        boolean needsEnergy = AvpConfig.server(AvpConfig.SERVER.machinesNeedPower);
        if (needsEnergy && energy.getEnergyStored() < zapCost)
        {
            return on;
        }
        Vec3 top = coilTop(pos);
        int range = AvpConfig.server(AvpConfig.SERVER.teslaCoilRange);
        boolean hitPlayers = AvpConfig.server(AvpConfig.SERVER.teslaCoilTargetsPlayers);
        LivingEntity target = level.getEntitiesOfClass(LivingEntity.class, new AABB(top, top).inflate(range),
                        e -> e.isAlive() && !e.isSpectator() && isTarget(e, hitPlayers)
                                && e.distanceToSqr(top) <= range * range && canSee(level, top, e))
                .stream().min(Comparator.comparingDouble(e -> e.distanceToSqr(top))).orElse(null);
        if (target == null)
        {
            return on;
        }
        if (needsEnergy)
        {
            energy.consume(zapCost);
        }
        cooldown = AvpConfig.server(AvpConfig.SERVER.teslaCoilCooldown);
        target.hurt(ModDamageTypes.electricity(level), (float) AvpConfig.server(AvpConfig.SERVER.teslaCoilDamage));
        if (AvpConfig.server(AvpConfig.SERVER.teslaCoilSetsFire))
        {
            target.igniteForSeconds(3);
        }
        level.playSound(null, pos, SoundEvents.TRIDENT_THUNDER.value(), SoundSource.BLOCKS, 0.4F, 1.8F);
        level.blockEvent(pos, getBlockState().getBlock(), 1, target.getId());
        return on;
    }

    private boolean terminalTick(Level level, BlockPos pos)
    {
        boolean on = tickPower(level, pos, AvpConfig.server(AvpConfig.SERVER.terminalEnergyPerTick));
        if (on && level.getGameTime() % 40 == Math.floorMod(pos.asLong(), 40))
        {
            scanNetwork(level, pos);
        }
        return on;
    }

    /** Totals up the network attached to any powerline touching the terminal. */
    private void scanNetwork(Level level, BlockPos pos)
    {
        int lines = -1, devices = 0, making = 0, load = 0, loadCap = PowerNetwork.capacity();
        boolean overload = false;
        long stored = 0, capacity = 0;
        for (Direction dir : Direction.values())
        {
            if (!PowerNetwork.isConductor(level.getBlockState(pos.relative(dir))))
            {
                continue;
            }
            PowerNetwork.Network network = PowerNetwork.network(level, pos.relative(dir));
            lines = network.conductors().size();
            load = PowerNetwork.lastLoad(level, network);
            overload = PowerNetwork.isOverloaded(level, network);
            for (PowerNetwork.Target target : network.targets())
            {
                devices++;
                IEnergyStorage storage = level.getCapability(net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage.BLOCK, target.pos(), target.side());
                if (storage != null)
                {
                    stored += storage.getEnergyStored();
                    capacity += storage.getMaxEnergyStored();
                }
                if (level.getBlockEntity(target.pos()) instanceof PowerMachineBlockEntity machine && machine.kind().isGenerator())
                {
                    making += machine.lastFlow;
                }
            }
            break;
        }
        if (lines != netLines || devices != netDevices || stored != netStored || capacity != netCapacity || making != netMaking
                || Math.abs(load - netLoad) > 16 || overload != netOverload || loadCap != netLoadCap)
        {
            netLines = lines;
            netDevices = devices;
            netStored = stored;
            netCapacity = capacity;
            netMaking = making;
            netLoad = load;
            netLoadCap = loadCap;
            netOverload = overload;
            sync();
        }
    }

    /** Lines of text for the terminal screen (client). */
    public List<Component> terminalLines()
    {
        List<Component> lines = new ArrayList<>();
        lines.add(Component.translatable("terminal.avp.header"));
        lines.add(Component.translatable("terminal.avp.interface"));
        lines.add(Component.literal("--------------------------"));
        if (netLines < 0)
        {
            lines.add(Component.translatable("terminal.avp.no_network"));
            return lines;
        }
        lines.add(Component.translatable("terminal.avp.network", netLines, netDevices));
        lines.add(Component.translatable("terminal.avp.stored", String.format("%,d", netStored), String.format("%,d", netCapacity)));
        lines.add(Component.translatable("terminal.avp.making", netMaking));
        lines.add(Component.translatable("terminal.avp.load", String.format("%,d", netLoad), String.format("%,d", netLoadCap)));
        if (netOverload)
        {
            lines.add(Component.translatable("terminal.avp.overload").withStyle(net.minecraft.ChatFormatting.RED));
        }
        float fill = netCapacity == 0 ? 0 : (float) netStored / netCapacity;
        lines.add(Component.translatable(netMaking == 0 && fill < 0.1F ? "terminal.avp.status_low"
                : netMaking == 0 ? "terminal.avp.status_battery" : "terminal.avp.status_ok"));
        return lines;
    }

    private static boolean isTarget(LivingEntity entity, boolean hitPlayers)
    {
        if (entity instanceof Player player)
        {
            return hitPlayers && !player.isCreative();
        }
        return entity instanceof Enemy || entity.getType().is(org.avp.registry.ModTags.TESLA_COIL_TARGETS);
    }

    private static boolean canSee(Level level, Vec3 from, Entity target)
    {
        Vec3 to = target.getBoundingBox().getCenter();
        return level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, target)).getType() == HitResult.Type.MISS;
    }

    // ------------------------------------------------------------------ client

    public static void clientTick(Level level, BlockPos pos, BlockState state, PowerMachineBlockEntity be)
    {
        be.rotorPrev = be.rotor;
        if (be.kind() == Kind.REPULSION_GENERATOR)
        {
            be.rotor += be.magnets * 0.05F;
        }
        long now = level.getGameTime();
        be.arcs.removeIf(arc -> arc.expires() < now);
        // a lit coil crackles with small arcs into the air around it
        if (be.kind() == Kind.TESLA_COIL && state.getValue(PowerMachineBlock.LIT) && level.random.nextInt(3) == 0)
        {
            Vec3 top = coilTop(pos);
            Vec3 to = top.add(level.random.nextGaussian() * 0.8, level.random.nextGaussian() * 0.5, level.random.nextGaussian() * 0.8);
            be.arcs.add(new Arc(to, -1, now + 2, level.random.nextLong()));
        }
    }

    /** A strike (sent by the server with level.blockEvent): draw an arc to that creature. */
    @Override
    public boolean triggerEvent(int id, int param)
    {
        if (id == 1 && level != null)
        {
            if (!level.isClientSide)
            {
                return true; // true = send the event on to the players nearby
            }
            Entity target = level.getEntity(param);
            Vec3 at = target != null ? target.getBoundingBox().getCenter() : coilTop(worldPosition);
            for (int i = 0; i < 3; i++)
            {
                arcs.add(new Arc(at, param, level.getGameTime() + 6, level.random.nextLong()));
            }
            return true;
        }
        return super.triggerEvent(id, param);
    }

    public List<Arc> arcs()
    {
        return arcs;
    }

    public float rotor(float partialTick)
    {
        return rotorPrev + (rotor - rotorPrev) * partialTick;
    }

    /** Power cell fill, 0..1, as last sent by the server. */
    public float fill()
    {
        return energy.getMaxEnergyStored() == 0 ? 0 : (float) energy.getEnergyStored() / energy.getMaxEnergyStored();
    }

    // ------------------------------------------------------------------ status message

    public Component statusMessage()
    {
        Kind kind = kind();
        Component stored = Component.translatable("message.avp.power.stored",
                String.format("%,d", energy.getEnergyStored()), String.format("%,d", energy.getMaxEnergyStored()));
        Component extra = switch (kind)
        {
            case SOLAR_PANEL -> arrayPanels > 1
                    ? (arrayOutlets == 0
                            ? Component.translatable("message.avp.power.array_unwired", lastFlow, arrayPanels)
                            : Component.translatable("message.avp.power.array", lastFlow, arrayPanels, arrayOutlets))
                    : Component.translatable("message.avp.power.making", lastFlow);
            case GENERATOR -> burnTime > 0
                    ? Component.translatable("message.avp.power.burning", lastFlow, (burnTime + 19) / 20)
                    : Component.translatable("message.avp.power.no_fuel");
            case REPULSION_GENERATOR -> Component.translatable("message.avp.power.magnets", magnets, PowerMachineBlock.MAX_MAGNETS, lastFlow);
            case TRANSFORMER, STEPDOWN_TRANSFORMER -> Component.translatable("message.avp.power.passing", lastFlow, energy.getMaxEnergyStored());
            case POWERCELL -> linkedCells > 0
                    ? Component.translatable("message.avp.power.bank", lastFlow, linkedCells + 1,
                            String.format("%,d", bankStored), String.format("%,d", bankCapacity))
                    : Component.translatable("message.avp.power.giving", lastFlow);
            default -> getBlockState().getValue(PowerMachineBlock.LIT)
                    ? Component.translatable("message.avp.power.on")
                    : Component.translatable("message.avp.power.off");
        };
        return stored.copy().append(" - ").append(extra);
    }

    // ------------------------------------------------------------------ saving and syncing

    private void sync()
    {
        if (level != null && !level.isClientSide)
        {
            syncedEnergy = energy.getEnergyStored();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.putInt("Energy", energy.getEnergyStored());
        tag.put("Fuel", fuel.serializeNBT(registries));
        tag.putInt("BurnTime", burnTime);
        tag.putInt("BurnTotal", burnTotal);
        tag.putInt("Magnets", magnets);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        energy.setCapacity(capacityFor(kind()));
        energy.setStored(tag.getInt("Energy"));
        if (tag.contains("Fuel"))
        {
            fuel.deserializeNBT(registries, tag.getCompound("Fuel"));
        }
        burnTime = tag.getInt("BurnTime");
        burnTotal = tag.getInt("BurnTotal");
        magnets = tag.getInt("Magnets");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries)
    {
        CompoundTag tag = super.getUpdateTag(registries);
        tag.putInt("Energy", energy.getEnergyStored());
        tag.putInt("Magnets", magnets);
        tag.putInt("NetLines", netLines);
        tag.putInt("NetDevices", netDevices);
        tag.putLong("NetStored", netStored);
        tag.putLong("NetCapacity", netCapacity);
        tag.putInt("NetMaking", netMaking);
        tag.putInt("NetLoad", netLoad);
        tag.putInt("NetLoadCap", netLoadCap);
        tag.putBoolean("NetOverload", netOverload);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries)
    {
        energy.setStored(tag.getInt("Energy"));
        magnets = tag.getInt("Magnets");
        netLines = tag.contains("NetLines") ? tag.getInt("NetLines") : -1;
        netDevices = tag.getInt("NetDevices");
        netStored = tag.getLong("NetStored");
        netCapacity = tag.getLong("NetCapacity");
        netMaking = tag.getInt("NetMaking");
        netLoad = tag.getInt("NetLoad");
        netLoadCap = tag.getInt("NetLoadCap");
        netOverload = tag.getBoolean("NetOverload");
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
