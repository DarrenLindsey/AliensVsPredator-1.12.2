package org.avp.mining;

import org.avp.registry.ModEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.component.CustomData;

/** A Power Loader, crated. Use it on the ground to unpack it. */
public class PowerLoaderItem extends Item
{
    public PowerLoaderItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        if (!(context.getLevel() instanceof ServerLevel level))
        {
            return InteractionResult.SUCCESS;
        }
        BlockPos at = context.getClickedPos().relative(context.getClickedFace());
        PowerLoaderEntity loader = ModEntities.POWER_LOADER.get().create(level);
        if (loader == null)
        {
            return InteractionResult.FAIL;
        }
        loader.moveTo(at.getX() + 0.5, at.getY(), at.getZ() + 0.5, context.getRotation() + 180F, 0F);
        loader.finalizeSpawn(level, level.getCurrentDifficultyAt(at), MobSpawnType.SPAWN_EGG, null);
        CustomData data = context.getItemInHand().get(DataComponents.CUSTOM_DATA);
        loader.addCharge(data != null ? data.copyTag().getInt("Charge") : 0);
        level.addFreshEntity(loader);
        context.getItemInHand().consume(1, context.getPlayer());
        return InteractionResult.CONSUME;
    }
}
