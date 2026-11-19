package org.avp.item;

import org.avp.entity.xenomorph.OvamorphEntity;
import org.avp.registry.ModEntities;
import org.avp.registry.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/**
 * An Ovamorph sealed in a Specimen Containment Unit (W-Y Special Order 937: "bring back a
 * specimen"). Use it on a block to release the egg there (it starts out shut and dormant);
 * the empty unit comes back. Or put it in a cryostasis tube to keep it frozen and harvest
 * royal jelly from it.
 */
public class ContainedOvamorphItem extends Item
{
    public ContainedOvamorphItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel server))
        {
            return InteractionResult.SUCCESS;
        }
        BlockPos at = context.getClickedPos().relative(context.getClickedFace());
        OvamorphEntity egg = ModEntities.OVAMORPH.get().create(server, null, at, MobSpawnType.SPAWN_EGG, false, false);
        if (egg == null)
        {
            return InteractionResult.FAIL;
        }
        egg.moveTo(at.getX() + 0.5, at.getY(), at.getZ() + 0.5, context.getRotation(), 0);
        server.addFreshEntity(egg);
        level.playSound(null, at, SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.PLAYERS, 0.8F, 0.7F);
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        if (player == null || !player.getAbilities().instabuild)
        {
            stack.shrink(1);
            if (player != null)
            {
                player.getInventory().placeItemBackInInventory(new ItemStack(ModItems.SPECIMEN_CONTAINER.get()));
            }
        }
        return InteractionResult.CONSUME;
    }
}
