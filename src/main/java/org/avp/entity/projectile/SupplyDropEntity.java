package org.avp.entity.projectile;

import org.avp.AliensVsPredator;
import org.avp.block.entity.StorageBlockEntity;
import org.avp.registry.ModBlocks;
import org.avp.registry.ModEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

/**
 * A supply drop under its parachute (1.12.2 supply chute): it drifts down, and where it lands it
 * becomes a supply crate filled from its loot table (data/avp/loot_table/supply/<kind>.json).
 * Kinds: 0 unbranded, 1 USCM Marines, 2 Seegson.
 */
public class SupplyDropEntity extends Entity
{
    private static final EntityDataAccessor<Integer> KIND = SynchedEntityData.defineId(SupplyDropEntity.class, EntityDataSerializers.INT);
    public static final String[] KINDS = { "unbranded", "marines", "seegson" };

    public SupplyDropEntity(EntityType<? extends SupplyDropEntity> type, Level level)
    {
        super(type, level);
    }

    public static SupplyDropEntity drop(ServerLevel level, double x, double z, int kind, int fromY)
    {
        SupplyDropEntity drop = new SupplyDropEntity(ModEntities.SUPPLY_DROP.get(), level);
        drop.setPos(x, fromY, z);
        drop.entityData.set(KIND, kind);
        level.addFreshEntity(drop);
        return drop;
    }

    public int kind()
    {
        return Math.floorMod(entityData.get(KIND), KINDS.length);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        builder.define(KIND, 0);
    }

    @Override
    public void tick()
    {
        super.tick();
        // drifting down under the chute
        Vec3 v = getDeltaMovement();
        setDeltaMovement(Math.sin(tickCount * 0.05) * 0.02, Math.max(-0.12, v.y - 0.01), Math.cos(tickCount * 0.04) * 0.02);
        move(MoverType.SELF, getDeltaMovement());
        if (level().isClientSide)
        {
            return;
        }
        if (onGround() || isInWater() || tickCount > 2400)
        {
            land((ServerLevel) level());
        }
    }

    private void land(ServerLevel level)
    {
        BlockPos at = blockPosition();
        while (!level.getBlockState(at).canBeReplaced() && at.getY() < level.getMaxBuildHeight() - 1)
        {
            at = at.above();
        }
        Block crate = switch (kind())
        {
            case 1 -> ModBlocks.SUPPLYCRATE_MARINES.get();
            case 2 -> ModBlocks.SUPPLYCRATE_SEEGSON.get();
            default -> ModBlocks.SUPPLYCRATE.get();
        };
        level.setBlock(at, crate.defaultBlockState(), 3);
        if (level.getBlockEntity(at) instanceof StorageBlockEntity storage)
        {
            ResourceKey<LootTable> key = ResourceKey.create(Registries.LOOT_TABLE,
                    ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "supply/" + KINDS[kind()]));
            LootTable table = level.getServer().reloadableRegistries().getLootTable(key);
            int slot = 0;
            for (ItemStack stack : table.getRandomItems(new LootParams.Builder(level).withParameter(LootContextParams.ORIGIN, position())
                    .create(LootContextParamSets.CHEST)))
            {
                if (slot < storage.getItems().getSlots())
                {
                    storage.getItems().setStackInSlot(slot++, stack);
                }
            }
        }
        level.playSound(null, at, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 0.6F);
        level.sendParticles(ParticleTypes.CLOUD, at.getX() + 0.5, at.getY() + 1, at.getZ() + 0.5, 12, 0.6, 0.3, 0.6, 0.02);
        discard();
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance)
    {
        return distance < 256 * 256;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag)
    {
        entityData.set(KIND, tag.getInt("Kind"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag)
    {
        tag.putInt("Kind", entityData.get(KIND));
    }
}
