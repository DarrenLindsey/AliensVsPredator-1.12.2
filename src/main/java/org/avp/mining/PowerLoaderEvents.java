package org.avp.mining;

import org.avp.AliensVsPredator;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

/** What a Power Loader pilot's hands do: punch, mine 3x3 into the hold, lift and set down blocks. */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class PowerLoaderEvents
{
    private static boolean busy;

    private PowerLoaderEvents()
    {
    }

    private static PowerLoaderEntity loader(Player player)
    {
        return player.getVehicle() instanceof PowerLoaderEntity l && l.charge() > 0 ? l : null;
    }

    @SubscribeEvent
    public static void attack(AttackEntityEvent event)
    {
        PowerLoaderEntity loader = loader(event.getEntity());
        if (loader != null && event.getTarget() instanceof LivingEntity target && target != loader)
        {
            event.setCanceled(true);
            if (!event.getEntity().level().isClientSide)
            {
                loader.punch(target);
            }
        }
    }

    @SubscribeEvent
    public static void speed(PlayerEvent.BreakSpeed event)
    {
        if (loader(event.getEntity()) != null)
        {
            event.setNewSpeed(Math.max(event.getNewSpeed(), 1F) * 4F * (event.getEntity().onGround() ? 1F : 5F));
        }
    }

    @SubscribeEvent
    public static void harvest(PlayerEvent.HarvestCheck event)
    {
        if (loader(event.getEntity()) != null)
        {
            event.setCanHarvest(true);
        }
    }

    /** Mining from the loader breaks a 3x3 face. */
    @SubscribeEvent
    public static void broke(BlockEvent.BreakEvent event)
    {
        Player player = event.getPlayer();
        PowerLoaderEntity loader = player == null ? null : loader(player);
        if (loader == null || busy || !(event.getLevel() instanceof ServerLevel level) || !loader.use(200))
        {
            return;
        }
        HitResult hit = player.pick(player.blockInteractionRange() + 1, 0, false);
        Direction face = hit instanceof BlockHitResult b ? b.getDirection() : Direction.UP;
        BlockPos centre = event.getPos();
        busy = true;
        try
        {
            for (int a = -1; a <= 1; a++)
            {
                for (int b = -1; b <= 1; b++)
                {
                    if (a == 0 && b == 0)
                    {
                        continue;
                    }
                    BlockPos p = switch (face.getAxis())
                    {
                        case X -> centre.offset(0, a, b);
                        case Y -> centre.offset(a, 0, b);
                        case Z -> centre.offset(a, b, 0);
                    };
                    BlockState s = level.getBlockState(p);
                    if (s.isAir() || s.getDestroySpeed(level, p) < 0 || level.getBlockEntity(p) != null || !s.getFluidState().isEmpty())
                    {
                        continue;
                    }
                    if (net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new BlockEvent.BreakEvent(level, p, s, player)).isCanceled())
                    {
                        continue;
                    }
                    for (ItemStack drop : Block.getDrops(s, level, p, null, player, new ItemStack(net.minecraft.world.item.Items.DIAMOND_PICKAXE)))
                    {
                        ItemStack left = loader.stow(drop);
                        if (!left.isEmpty())
                        {
                            Block.popResource(level, p, left);
                        }
                    }
                    level.destroyBlock(p, false, player);
                }
            }
        }
        finally
        {
            busy = false;
        }
    }

    /** The middle block's drops go in the hold too. */
    @SubscribeEvent
    public static void drops(BlockDropsEvent event)
    {
        if (event.getBreaker() instanceof Player player && loader(player) != null)
        {
            PowerLoaderEntity loader = loader(player);
            for (ItemEntity item : event.getDrops())
            {
                ItemStack left = loader.stow(item.getItem());
                item.setItem(left);
            }
            event.getDrops().removeIf(item -> item.getItem().isEmpty());
        }
    }

    /** Empty hand on a block: lift it (or set down what you're holding). */
    @SubscribeEvent
    public static void use(PlayerInteractEvent.RightClickBlock event)
    {
        Player player = event.getEntity();
        PowerLoaderEntity loader = loader(player);
        if (loader == null || !player.getMainHandItem().isEmpty() || event.getHand() != net.minecraft.world.InteractionHand.MAIN_HAND)
        {
            return;
        }
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
        if (player.level().isClientSide)
        {
            return;
        }
        if (loader.carried().isPresent())
        {
            BlockPos at = event.getPos();
            if (!player.level().getBlockState(at).canBeReplaced())
            {
                at = at.relative(event.getFace() == null ? Direction.UP : event.getFace());
            }
            loader.setDown(at);
        }
        else
        {
            loader.lift(event.getPos(), player);
        }
    }
}
