package org.avp.item;

import org.avp.config.AvpConfig;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Lithium ingot. Like real lithium (and the 1.12.2 version) it reacts with water: carried
 * into water, or dropped in it, it bursts with a small explosion. "Lithium Reacts With
 * Water" in the settings turns this off.
 */
public class LithiumItem extends Item
{
    public LithiumItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected)
    {
        if (!level.isClientSide && entity.isInWater() && AvpConfig.server(AvpConfig.SERVER.lithiumReactsWithWater))
        {
            stack.shrink(1);
            level.explode(entity, entity.getX(), entity.getY(), entity.getZ(), 1.0F, Level.ExplosionInteraction.NONE);
        }
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity)
    {
        Level level = entity.level();
        if (!level.isClientSide && entity.isInWater() && AvpConfig.server(AvpConfig.SERVER.lithiumReactsWithWater))
        {
            level.explode(entity, entity.getX(), entity.getY(), entity.getZ(), 1.0F + 0.1F * stack.getCount(), Level.ExplosionInteraction.NONE);
            entity.discard();
            return true;
        }
        return false;
    }
}
