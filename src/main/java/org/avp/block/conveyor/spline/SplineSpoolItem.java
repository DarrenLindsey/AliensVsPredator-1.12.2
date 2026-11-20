package org.avp.block.conveyor.spline;

import java.util.List;

import org.avp.block.conveyor.ConveyorBlock;
import org.avp.config.AvpConfig;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * A spool of belt, cable or hose. Use it on an anchor to start a line, then on where it should go (for
 * belts, another belt anchor or any belt block; cables: another pylon; hoses: another hose anchor).
 * Every 8 blocks of line uses one spool. Sneak-use on an anchor to take its line down.
 */
public class SplineSpoolItem extends Item
{
    public final SplineAnchorBlock.Kind kind;
    private final double speed;

    public SplineSpoolItem(SplineAnchorBlock.Kind kind, double speed, Properties properties)
    {
        super(properties);
        this.kind = kind;
        this.speed = speed;
    }

    private boolean isStart(Level level, BlockPos pos)
    {
        return level.getBlockState(pos).getBlock() instanceof SplineAnchorBlock a && a.kind == kind;
    }

    private boolean isEnd(Level level, BlockPos pos)
    {
        return isStart(level, pos) || kind == SplineAnchorBlock.Kind.BELT && level.getBlockState(pos).getBlock() instanceof ConveyorBlock;
    }

    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        if (level.isClientSide)
        {
            return isEnd(level, pos) ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        if (player != null && player.isShiftKeyDown() && level.getBlockEntity(pos) instanceof SplineAnchorBlockEntity be && isStart(level, pos))
        {
            be.unlink();
            say(player, "Line taken down.", false);
            return InteractionResult.CONSUME;
        }
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        CompoundTag tag = data == null ? new CompoundTag() : data.copyTag();
        if (!tag.contains("From"))
        {
            if (!isStart(level, pos))
            {
                return InteractionResult.PASS;
            }
            tag.putLong("From", pos.asLong());
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            say(player, "Line started. Now use it where it should go.", false);
            return InteractionResult.CONSUME;
        }
        BlockPos from = BlockPos.of(tag.getLong("From"));
        stack.remove(DataComponents.CUSTOM_DATA);
        if (!isEnd(level, pos) || pos.equals(from) || !(level.getBlockEntity(from) instanceof SplineAnchorBlockEntity start) || !isStart(level, from))
        {
            say(player, "That isn't somewhere this line can go.", true);
            return InteractionResult.FAIL;
        }
        double length = Vec3.atCenterOf(from).distanceTo(Vec3.atCenterOf(pos));
        int max = AvpConfig.server(AvpConfig.SERVER.splineMaxLength);
        if (length > max)
        {
            say(player, "Too long (" + (int) length + " blocks; at most " + max + ").", true);
            return InteractionResult.FAIL;
        }
        int cost = Math.max(1, (int) Math.ceil(length / 8));
        if (player != null && !player.getAbilities().instabuild && stack.getCount() < cost)
        {
            say(player, "Needs " + cost + " spools for " + (int) length + " blocks.", true);
            return InteractionResult.FAIL;
        }
        start.link(pos);
        if (start instanceof BeltAnchorBlockEntity belt)
        {
            belt.setSpeed(speed);
        }
        if (player == null || !player.getAbilities().instabuild)
        {
            stack.shrink(cost);
        }
        say(player, "Line strung: " + (int) Math.round(start.spline() != null ? start.spline().length : length) + " blocks.", false);
        return InteractionResult.CONSUME;
    }

    private static void say(Player player, String text, boolean bad)
    {
        if (player != null)
        {
            player.displayClientMessage(Component.literal(text).withStyle(bad ? ChatFormatting.RED : ChatFormatting.GOLD), true);
        }
    }

    @Override
    public boolean isFoil(ItemStack stack)
    {
        return stack.has(DataComponents.CUSTOM_DATA);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        tooltip.add(Component.literal(switch (kind)
        {
            case BELT -> "Use on a belt anchor, then on another anchor or a belt.";
            case CABLE -> "Use on a cable pylon, then on another pylon.";
            case HOSE -> "Use on a hose anchor, then on another hose anchor.";
        }).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("One spool per 8 blocks. Sneak-use to take a line down.").withStyle(ChatFormatting.DARK_GRAY));
    }
}
