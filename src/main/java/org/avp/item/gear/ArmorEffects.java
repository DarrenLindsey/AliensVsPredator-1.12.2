package org.avp.item.gear;

import java.util.Map;
import java.util.WeakHashMap;

import org.avp.AliensVsPredator;
import org.avp.config.AvpConfig;
import org.avp.registry.ModArmor;
import org.avp.registry.ModDamageTypes;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * What the armour sets do while worn:
 * - Colonial Marine helmet: a lamp that lights the dark where you look (a moving invisible light);
 * - Seegson MK50 and W-Y pressure suit (full set): sealed, so you can breathe underwater;
 * - Xenomorph chitin (full set): acid doesn't burn you.
 * (The Celtic helmet is a biomask: night vision and facehugger protection, see BiomaskEvents.)
 */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class ArmorEffects
{
    /** Where each player's helmet lamp light is right now. */
    private static final Map<Player, BlockPos> LAMPS = new WeakHashMap<>();

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event)
    {
        Player player = event.getEntity();
        if (!(player.level() instanceof ServerLevel level))
        {
            return;
        }
        lamp(level, player);
        if (player.tickCount % 20 == 0 && (ModArmor.wearsFull(player, "mk50") || ModArmor.wearsFull(player, "pressure")))
        {
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 60, 0, true, false, false));
        }
    }

    private static boolean wearsLamp(Player player)
    {
        return AvpConfig.server(AvpConfig.SERVER.helmetLamp) && !player.isSpectator()
                && player.getItemBySlot(EquipmentSlot.HEAD).is(ModArmor.SETS.get("marine").get(0).get());
    }

    /** The lamp: a light block a few blocks ahead of you, moved as you look around (only where it's dark). */
    private static void lamp(ServerLevel level, Player player)
    {
        BlockPos old = LAMPS.get(player);
        BlockPos next = null;
        if (wearsLamp(player))
        {
            Vec3 eye = player.getEyePosition();
            Vec3 look = player.getViewVector(1F);
            for (double d = 3.0; d >= 0.5 && next == null; d -= 0.5)
            {
                BlockPos p = BlockPos.containing(eye.add(look.scale(d)));
                if (level.getBlockState(p).isAir() || p.equals(old) && level.getBlockState(p).is(Blocks.LIGHT))
                {
                    next = p;
                }
            }
            if (next != null && !next.equals(old) && level.getBrightness(LightLayer.BLOCK, next) >= 12)
            {
                next = null; // already bright
            }
        }
        if (old != null && !old.equals(next))
        {
            if (level.getBlockState(old).is(Blocks.LIGHT))
            {
                level.setBlock(old, Blocks.AIR.defaultBlockState(), 3);
            }
            LAMPS.remove(player);
        }
        if (next != null && !next.equals(old))
        {
            level.setBlock(next, Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 13), 3);
            LAMPS.put(player, next);
        }
    }

    @SubscribeEvent
    public static void logout(PlayerEvent.PlayerLoggedOutEvent event)
    {
        BlockPos old = LAMPS.remove(event.getEntity());
        if (old != null && event.getEntity().level().getBlockState(old).is(Blocks.LIGHT))
        {
            event.getEntity().level().setBlock(old, Blocks.AIR.defaultBlockState(), 3);
        }
    }

    /** Xenomorph chitin shrugs off acid. */
    @SubscribeEvent
    public static void acid(LivingIncomingDamageEvent event)
    {
        if (event.getSource().is(ModDamageTypes.ACID) && ModArmor.wearsFull(event.getEntity(), "xeno"))
        {
            event.setCanceled(true);
        }
    }

    private ArmorEffects()
    {
    }
}
