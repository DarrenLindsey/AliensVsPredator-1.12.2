package org.avp.mining;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.avp.config.AvpConfig;
import org.avp.power.MachineEnergy;
import org.avp.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * The Weyland-Yutani Seismic Drill: sits on a 3x3 platform of drill casing and works a square below
 * it, top down to the bottom of the world. QUARRY mode digs out everything; ORE-BORE mode only pulls
 * the ore out (and backfills it with stone), for three times the power. Before it starts it runs a
 * seismic survey of what's down there. Its haul goes into any container next to it (a PneuPost
 * station or APOLLO core, say), or its own 27-slot buffer.
 */
public class SeismicDrillBlockEntity extends BlockEntity implements MenuProvider
{
    public static final int IDLE = 0, RUNNING = 1, DONE = 2, NO_POWER = 3, BAD_FRAME = 4, FULL = 5;
    public static final int QUARRY = 0, ORE_BORE = 1;

    private final MachineEnergy energy = new MachineEnergy(400_000);
    final ItemStackHandler buffer = new ItemStackHandler(27)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }
    };
    private int state = IDLE, radius = 8, mode = QUARRY, cursor, layer = Integer.MIN_VALUE, mined, cooldown;
    private boolean running;
    private final int[] survey = new int[12];

    public final ContainerData data = new ContainerData()
    {
        @Override
        public int get(int i)
        {
            return switch (i)
            {
                case 0 -> state;
                case 1 -> radius;
                case 2 -> mode;
                case 3 -> layer == Integer.MIN_VALUE ? worldPosition.getY() - 2 : layer;
                case 4 -> Math.min(mined, 32_000);
                case 5 -> (int) (1000L * energy.getEnergyStored() / energy.getMaxEnergyStored());
                default -> i - 6 < survey.length ? survey[i - 6] : 0;
            };
        }

        @Override
        public void set(int i, int value)
        {
        }

        @Override
        public int getCount()
        {
            return 6 + survey.length;
        }
    };

    public SeismicDrillBlockEntity(BlockPos pos, BlockState s)
    {
        super(ModBlockEntities.SEISMIC_DRILL.get(), pos, s);
    }

    public IEnergyStorage energyFor()
    {
        return energy.view(true, false, Integer.MAX_VALUE);
    }

    /** Hoppers can take the haul out of the buffer. */
    public IItemHandler output()
    {
        return new IItemHandler()
        {
            @Override
            public int getSlots()
            {
                return buffer.getSlots();
            }

            @Override
            public ItemStack getStackInSlot(int slot)
            {
                return buffer.getStackInSlot(slot);
            }

            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
            {
                return stack;
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate)
            {
                return buffer.extractItem(slot, amount, simulate);
            }

            @Override
            public int getSlotLimit(int slot)
            {
                return 64;
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack)
            {
                return false;
            }
        };
    }

    /** Menu buttons: 0 start/stop, 1 radius -, 2 radius +, 3 mode, 4 survey, 5 reset. */
    public void button(int id)
    {
        switch (id)
        {
            case 0 -> running = !running;
            case 1 -> radius = Math.max(1, radius - 1);
            case 2 -> radius = Math.min(AvpConfig.server(AvpConfig.SERVER.drillMaxRadius), radius + 1);
            case 3 -> mode = mode == QUARRY ? ORE_BORE : QUARRY;
            case 4 -> survey();
            case 5 ->
            {
                layer = Integer.MIN_VALUE;
                cursor = 0;
                mined = 0;
                state = IDLE;
            }
            default ->
            {
            }
        }
        setChanged();
    }

    /** The 3x3 casing platform under the controller. */
    boolean frameOk()
    {
        if (level == null)
        {
            return false;
        }
        for (int dx = -1; dx <= 1; dx++)
        {
            for (int dz = -1; dz <= 1; dz++)
            {
                if (!(level.getBlockState(worldPosition.offset(dx, -1, dz)).getBlock() instanceof DrillCasingBlock))
                {
                    return false;
                }
            }
        }
        return true;
    }

    /** Seismic survey: the six most common ores below. */
    private void survey()
    {
        if (level == null)
        {
            return;
        }
        Map<Block, Integer> counts = new HashMap<>();
        BlockPos c = worldPosition;
        int top = c.getY() - 2;
        long checks = 0;
        for (int y = top; y >= level.getMinBuildHeight() && checks < 2_000_000; y--)
        {
            checks += (long) (radius * 2 + 1) * (radius * 2 + 1);
            for (int dx = -radius; dx <= radius; dx++)
            {
                for (int dz = -radius; dz <= radius; dz++)
                {
                    BlockPos p = new BlockPos(c.getX() + dx, y, c.getZ() + dz);
                    if (level.isLoaded(p))
                    {
                        BlockState s = level.getBlockState(p);
                        if (isOre(s))
                        {
                            counts.merge(s.getBlock(), 1, Integer::sum);
                        }
                    }
                }
            }
        }
        List<Map.Entry<Block, Integer>> top6 = counts.entrySet().stream().sorted((a, b) -> b.getValue() - a.getValue()).limit(6).toList();
        java.util.Arrays.fill(survey, 0);
        for (int i = 0; i < top6.size(); i++)
        {
            survey[i * 2] = BuiltInRegistries.BLOCK.getId(top6.get(i).getKey());
            survey[i * 2 + 1] = Math.min(32_000, top6.get(i).getValue());
        }
        level.playSound(null, c, SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.BLOCKS, 0.4F, 2.0F);
    }

    static boolean isOre(BlockState s)
    {
        return s.is(Tags.Blocks.ORES) || BuiltInRegistries.BLOCK.getKey(s.getBlock()).getPath().contains("ore");
    }

    public static void serverTick(Level level, BlockPos pos, BlockState st, SeismicDrillBlockEntity be)
    {
        be.pushOut();
        if (!be.running)
        {
            if (be.state == RUNNING)
            {
                be.state = IDLE;
            }
            return;
        }
        if (level.getGameTime() % 20 == 0 && !be.frameOk())
        {
            be.state = BAD_FRAME;
            return;
        }
        if (be.state == BAD_FRAME && be.frameOk())
        {
            be.state = RUNNING;
        }
        if (be.state == DONE)
        {
            return;
        }
        if (--be.cooldown > 0)
        {
            return;
        }
        int ops = AvpConfig.server(AvpConfig.SERVER.drillSpeed);
        for (int op = 0; op < ops; op++)
        {
            if (!be.step((ServerLevel) level))
            {
                break;
            }
        }
    }

    /** One block. Returns false to stop for this tick. */
    private boolean step(ServerLevel level)
    {
        if (layer == Integer.MIN_VALUE)
        {
            layer = worldPosition.getY() - 2;
            cursor = 0;
        }
        if (layer < level.getMinBuildHeight())
        {
            state = DONE;
            running = false;
            level.playSound(null, worldPosition, SoundEvents.NOTE_BLOCK_BELL.value(), SoundSource.BLOCKS, 1F, 0.8F);
            return false;
        }
        int side = radius * 2 + 1;
        if (cursor >= side * side)
        {
            cursor = 0;
            layer--;
            return true;
        }
        int dx = cursor % side - radius, dz = cursor / side - radius;
        BlockPos p = new BlockPos(worldPosition.getX() + dx, layer, worldPosition.getZ() + dz);
        if (!level.isLoaded(p))
        {
            return false;
        }
        BlockState s = level.getBlockState(p);
        float hardness = s.getDestroySpeed(level, p);
        boolean skip = s.isAir() || hardness < 0 || !s.getFluidState().isEmpty() || level.getBlockEntity(p) != null || s.is(Blocks.BEDROCK)
                || mode == ORE_BORE && !isOre(s);
        if (skip)
        {
            cursor++;
            return true;
        }
        int cost = (int) (AvpConfig.server(AvpConfig.SERVER.drillPowerPerBlock) * Math.max(1F, hardness) * (mode == ORE_BORE ? 3 : 1));
        if (!energy.consume(cost))
        {
            state = NO_POWER;
            return false;
        }
        if (!hasRoom())
        {
            energy.fill(cost);
            state = FULL;
            return false;
        }
        state = RUNNING;
        List<ItemStack> drops = Block.getDrops(s, level, p, null, null, new ItemStack(Items.NETHERITE_PICKAXE));
        level.setBlock(p, mode == ORE_BORE ? (p.getY() < 0 ? Blocks.DEEPSLATE : Blocks.STONE).defaultBlockState() : Blocks.AIR.defaultBlockState(), 3);
        for (ItemStack drop : drops)
        {
            ItemStack left = ItemHandlerHelper.insertItemStacked(buffer, drop, false);
            if (!left.isEmpty())
            {
                net.minecraft.world.Containers.dropItemStack(level, worldPosition.getX() + 0.5, worldPosition.getY() + 1, worldPosition.getZ() + 0.5, left);
            }
        }
        mined++;
        cursor++;
        if (mined % 4 == 0)
        {
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5, 6, 0.3, 0.3, 0.3, 0.05);
            level.sendParticles(ParticleTypes.END_ROD, worldPosition.getX() + 0.5, worldPosition.getY() - 1.5, worldPosition.getZ() + 0.5, 1, 0, -0.5, 0, 0.2);
        }
        if (mined % 16 == 0)
        {
            level.playSound(null, worldPosition, SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 0.5F, 0.6F);
        }
        cooldown = 0;
        return true;
    }

    private boolean hasRoom()
    {
        for (int i = 0; i < buffer.getSlots(); i++)
        {
            if (buffer.getStackInSlot(i).getCount() < 48)
            {
                return true;
            }
        }
        return false;
    }

    /** Hands the haul to any container next to it (not the casing). */
    private void pushOut()
    {
        if (level == null || level.getGameTime() % 10 != 0)
        {
            return;
        }
        for (Direction d : Direction.values())
        {
            if (d == Direction.DOWN)
            {
                continue;
            }
            IItemHandler into = level.getCapability(Capabilities.ItemHandler.BLOCK, worldPosition.relative(d), d.getOpposite());
            if (into == null)
            {
                continue;
            }
            for (int i = 0; i < buffer.getSlots(); i++)
            {
                ItemStack s = buffer.getStackInSlot(i);
                if (!s.isEmpty())
                {
                    ItemStack left = ItemHandlerHelper.insertItemStacked(into, s.copy(), false);
                    buffer.setStackInSlot(i, left);
                }
            }
            return;
        }
    }

    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block.avp.seismic_drill");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player)
    {
        return new SeismicDrillMenu(id, inv, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Buffer", buffer.serializeNBT(registries));
        tag.putInt("Energy", energy.getEnergyStored());
        tag.putInt("State", state);
        tag.putInt("Radius", radius);
        tag.putInt("Mode", mode);
        tag.putInt("Cursor", cursor);
        tag.putInt("Layer", layer);
        tag.putInt("Mined", mined);
        tag.putBoolean("Running", running);
        tag.putIntArray("Survey", survey);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        buffer.deserializeNBT(registries, tag.getCompound("Buffer"));
        energy.setStored(tag.getInt("Energy"));
        state = tag.getInt("State");
        radius = tag.contains("Radius") ? tag.getInt("Radius") : 8;
        mode = tag.getInt("Mode");
        cursor = tag.getInt("Cursor");
        layer = tag.contains("Layer") ? tag.getInt("Layer") : Integer.MIN_VALUE;
        mined = tag.getInt("Mined");
        running = tag.getBoolean("Running");
        int[] s = tag.getIntArray("Survey");
        System.arraycopy(s, 0, survey, 0, Math.min(s.length, survey.length));
    }
}
