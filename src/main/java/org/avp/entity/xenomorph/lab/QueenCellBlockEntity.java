package org.avp.entity.xenomorph.lab;

import java.util.UUID;

import javax.annotation.Nullable;

import org.avp.config.AvpConfig;
import org.avp.entity.xenomorph.AdultXenomorphEntity;
import org.avp.power.MachineEnergy;
import org.avp.registry.ModBlockEntities;
import org.avp.registry.ModItems;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * A Weyland-Yutani Queen Containment Cell. It holds a Queen (captured when weakened, or grown in an
 * Incubation Tank underneath) restrained above it, and collects what she lays: contained ovamorphs and
 * now and then royal jelly. It needs power and meat. Starve it of either and containment integrity
 * falls; at 0% she breaks out.
 */
public class QueenCellBlockEntity extends BlockEntity
{
    private final MachineEnergy energy = new MachineEnergy(400_000);
    public final ItemStackHandler output = new ItemStackHandler(9)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
        }
    };
    @Nullable
    private UUID queen, owner;
    private float integrity = 100F;
    private int food, layTimer, laid;
    private int lastWarning = 100;

    public QueenCellBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.QUEEN_CELL.get(), pos, state);
    }

    public IEnergyStorage energyFor()
    {
        return energy.view(true, false, Integer.MAX_VALUE);
    }

    public void setOwner(UUID owner)
    {
        this.owner = owner;
    }

    public boolean occupied()
    {
        return queen != null;
    }

    /** Meat goes in (feeding her), eggs and jelly come out. */
    public final IItemHandler handler = new IItemHandler()
    {
        @Override
        public int getSlots()
        {
            return 10;
        }

        @Override
        public ItemStack getStackInSlot(int slot)
        {
            return slot == 0 ? ItemStack.EMPTY : output.getStackInSlot(slot - 1);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
        {
            if (slot != 0 || !stack.is(ItemTags.MEAT) || food >= 200)
            {
                return stack;
            }
            int take = Math.min(stack.getCount(), 200 - food);
            if (!simulate)
            {
                food += take;
                setChanged();
            }
            return stack.copyWithCount(stack.getCount() - take);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate)
        {
            return slot == 0 ? ItemStack.EMPTY : output.extractItem(slot - 1, amount, simulate);
        }

        @Override
        public int getSlotLimit(int slot)
        {
            return 64;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack)
        {
            return slot == 0 && stack.is(ItemTags.MEAT);
        }
    };

    @Nullable
    private AdultXenomorphEntity queenEntity()
    {
        return queen != null && level instanceof ServerLevel server && server.getEntity(queen) instanceof AdultXenomorphEntity q && q.isAlive() ? q : null;
    }

    /** A Queen grown by the Incubation Tank under the cell. */
    public boolean receiveQueen(EntityType<?> species, @Nullable UUID owner)
    {
        if (queen != null || !(level instanceof ServerLevel server) || !(species.create(server) instanceof AdultXenomorphEntity q))
        {
            return false;
        }
        BlockPos at = worldPosition.above();
        q.moveTo(at.getX() + 0.5, at.getY(), at.getZ() + 0.5, 0, 0);
        q.finalizeSpawn(server, server.getCurrentDifficultyAt(at), MobSpawnType.SPAWNER, null);
        server.addFreshEntity(q);
        restrain(q);
        if (owner != null)
        {
            this.owner = owner;
        }
        tell("A Queen has been grown into containment. COMPANY PRIORITY.", ChatFormatting.DARK_GREEN);
        return true;
    }

    /** Use the cell with a weakened Queen near it to restrain her. */
    public boolean capture(Player player)
    {
        if (queen != null || level == null)
        {
            return false;
        }
        for (AdultXenomorphEntity q : level.getEntitiesOfClass(AdultXenomorphEntity.class, new net.minecraft.world.phys.AABB(worldPosition).inflate(8)))
        {
            if (q.isQueen() && q.getHealth() < q.getMaxHealth() * 0.3F && !q.isBonded())
            {
                restrain(q);
                owner = player.getUUID();
                tell("Queen restrained. Containment integrity 100%.", ChatFormatting.DARK_GREEN);
                return true;
            }
        }
        return false;
    }

    private void restrain(Mob q)
    {
        queen = q.getUUID();
        q.setNoAi(true);
        q.setPersistenceRequired();
        q.setTarget(null);
        integrity = 100F;
        lastWarning = 100;
        q.teleportTo(worldPosition.getX() + 0.5, worldPosition.getY() + 1, worldPosition.getZ() + 0.5);
        setChanged();
        if (level instanceof ServerLevel server && owner != null)
        {
            var core = org.avp.apollo.ApolloCoreBlockEntity.of(server.getServer(), owner);
            if (core != null)
            {
                core.say("QUEEN SPECIMEN SECURED. COMPANY PRIORITY. ALL OTHER CONSIDERATIONS SECONDARY.");
            }
        }
    }

    private void tell(String text, ChatFormatting colour)
    {
        if (level != null && owner != null && level.getPlayerByUUID(owner) instanceof Player p)
        {
            p.sendSystemMessage(Component.literal("[W-Y Containment] " + text).withStyle(colour));
        }
    }

    public String status()
    {
        return queen == null ? "EMPTY - bring a weakened Queen near and use the cell, or grow one from an Incubation Tank below"
                : String.format("QUEEN CONTAINED | integrity %.0f%% | food %d | eggs laid %d | power %d%%", integrity, food, laid,
                        (int) (100L * energy.getEnergyStored() / energy.getMaxEnergyStored()));
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, QueenCellBlockEntity cell)
    {
        if (cell.queen == null)
        {
            return;
        }
        AdultXenomorphEntity q = cell.queenEntity();
        if (q == null)
        {
            if (level.getGameTime() % 100 == 0 && ((ServerLevel) level).getEntity(cell.queen) == null && level.isLoaded(pos.above()))
            {
                cell.queen = null; // she died (or was never loaded back)
                cell.setChanged();
            }
            return;
        }
        // keep her pinned
        if (level.getGameTime() % 5 == 0 && q.distanceToSqr(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5) > 1)
        {
            q.teleportTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
        }
        q.setNoAi(true);
        boolean powered = cell.energy.consume(AvpConfig.server(AvpConfig.SERVER.queenCellPower));
        if (level.getGameTime() % 100 == 0 && cell.food > 0)
        {
            cell.food--;
        }
        boolean fed = cell.food > 0;
        if (!powered)
        {
            cell.integrity -= 0.02F;
        }
        if (!fed)
        {
            cell.integrity -= 0.01F;
        }
        if (powered && fed)
        {
            cell.integrity = Math.min(100F, cell.integrity + 0.01F);
        }
        for (int warn : new int[] { 50, 25, 10 })
        {
            if (cell.integrity < warn && cell.lastWarning > warn)
            {
                cell.lastWarning = warn;
                cell.tell("WARNING: containment integrity " + warn + "%" + (!powered ? " - NO POWER" : "") + (!fed ? " - SPECIMEN UNFED" : ""),
                        ChatFormatting.RED);
                level.playSound(null, pos, SoundEvents.NOTE_BLOCK_BASS.value(), SoundSource.BLOCKS, 3F, 0.5F);
            }
        }
        if (cell.integrity >= 60)
        {
            cell.lastWarning = 100;
        }
        if (cell.integrity <= 0)
        {
            cell.breakout(q);
            return;
        }
        // she lays
        if (fed && powered && ++cell.layTimer >= AvpConfig.server(AvpConfig.SERVER.queenCellEggTicks))
        {
            cell.layTimer = 0;
            ItemStack egg = new ItemStack(ModItems.CONTAINED_OVAMORPH.get());
            if (ItemHandlerHelper.insertItemStacked(cell.output, egg, false).isEmpty())
            {
                cell.laid++;
                if (cell.laid % 3 == 0)
                {
                    ItemHandlerHelper.insertItemStacked(cell.output, new ItemStack(ModItems.ROYALJELLY.get(), 2), false);
                }
                level.playSound(null, pos, SoundEvents.SLIME_SQUISH, SoundSource.BLOCKS, 1F, 0.5F);
            }
        }
        if (level instanceof ServerLevel server && level.getGameTime() % 40 == 0)
        {
            server.sendParticles(cell.integrity < 25 ? ParticleTypes.ELECTRIC_SPARK : ParticleTypes.END_ROD, pos.getX() + 0.5, pos.getY() + 1.1,
                    pos.getZ() + 0.5, 2, 0.4, 0.1, 0.4, 0.01);
        }
        cell.setChanged();
    }

    private void breakout(AdultXenomorphEntity q)
    {
        q.setNoAi(false);
        queen = null;
        integrity = 0;
        level.playSound(null, worldPosition, SoundEvents.RAVAGER_ROAR, SoundSource.HOSTILE, 3F, 0.5F);
        level.playSound(null, worldPosition, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 2F, 0.6F);
        tell("CONTAINMENT FAILURE. THE QUEEN IS LOOSE.", ChatFormatting.DARK_RED);
        if (level instanceof ServerLevel server && owner != null)
        {
            var core = org.avp.apollo.ApolloCoreBlockEntity.of(server.getServer(), owner);
            if (core != null)
            {
                core.say("CONTAINMENT BREACH. CREW EXPENDABLE.");
            }
        }
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.saveAdditional(tag, registries);
        tag.put("Output", output.serializeNBT(registries));
        tag.putInt("Energy", energy.getEnergyStored());
        if (queen != null)
        {
            tag.putUUID("Queen", queen);
        }
        if (owner != null)
        {
            tag.putUUID("Owner", owner);
        }
        tag.putFloat("Integrity", integrity);
        tag.putInt("Food", food);
        tag.putInt("Lay", layTimer);
        tag.putInt("Laid", laid);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries)
    {
        super.loadAdditional(tag, registries);
        output.deserializeNBT(registries, tag.getCompound("Output"));
        energy.setStored(tag.getInt("Energy"));
        queen = tag.hasUUID("Queen") ? tag.getUUID("Queen") : null;
        owner = tag.hasUUID("Owner") ? tag.getUUID("Owner") : null;
        integrity = tag.contains("Integrity") ? tag.getFloat("Integrity") : 100F;
        food = tag.getInt("Food");
        layTimer = tag.getInt("Lay");
        laid = tag.getInt("Laid");
    }
}
