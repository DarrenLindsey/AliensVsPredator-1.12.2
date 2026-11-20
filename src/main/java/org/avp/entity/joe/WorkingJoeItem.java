package org.avp.entity.joe;

import java.util.List;

import org.avp.registry.ModEntities;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;

/**
 * A boxed Working Joe: use it on the ground to set it up (you become its owner). Packing a Joe back
 * up (sneak-use it with an empty hand) keeps its program, battery and load.
 */
public class WorkingJoeItem extends Item
{
    public WorkingJoeItem(Properties properties)
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
        WorkingJoeEntity joe = ModEntities.WORKING_JOE.get().create(level);
        if (joe == null)
        {
            return InteractionResult.FAIL;
        }
        ItemStack stack = context.getItemInHand();
        joe.moveTo(at.getX() + 0.5, at.getY(), at.getZ() + 0.5, context.getRotation() + 180F, 0F);
        joe.finalizeSpawn(level, level.getCurrentDifficultyAt(at), MobSpawnType.SPAWN_EGG, null);
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null)
        {
            joe.unpack(data.copyTag());
        }
        if (stack.has(DataComponents.CUSTOM_NAME))
        {
            joe.setCustomName(stack.getHoverName());
        }
        else if (!joe.hasCustomName())
        {
            joe.setCustomName(Component.literal(String.format("Joe-%02d", 1 + level.random.nextInt(99))));
        }
        if (context.getPlayer() != null && joe.owner() == null)
        {
            joe.setOwner(context.getPlayer().getUUID());
        }
        level.addFreshEntity(joe);
        joe.say("Hello. I am a Working Joe. Please give me a program.");
        if (context.getPlayer() != null && context.getPlayer().getInventory().items.stream().noneMatch(i -> i.is(net.minecraft.world.item.Items.WRITTEN_BOOK)
                && i.has(DataComponents.WRITTEN_BOOK_CONTENT) && "Seegson".equals(i.get(DataComponents.WRITTEN_BOOK_CONTENT).author())))
        {
            context.getPlayer().getInventory().placeItemBackInInventory(JoeCommands.manualBook()); // comes with the manual
        }
        stack.consume(1, context.getPlayer());
        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        tooltip.add(Component.translatable("item.avp.working_joe.desc").withStyle(ChatFormatting.GRAY));
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data != null && !data.copyTag().getString("Program").isEmpty())
        {
            tooltip.add(Component.translatable("item.avp.working_joe.programmed").withStyle(ChatFormatting.DARK_AQUA));
        }
    }
}
