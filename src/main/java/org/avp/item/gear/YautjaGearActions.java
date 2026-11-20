package org.avp.item.gear;

import org.avp.AliensVsPredator;
import org.avp.config.AvpConfig;
import org.avp.item.BiomaskEvents;
import org.avp.network.GearActionPayload;
import org.avp.network.VisionModePayload;
import org.avp.registry.ModAttachments;
import org.avp.registry.ModGear;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Yautja gear keys and their effects:
 * - V (wearing a biomask): vision mode - normal, night, thermal (warm bodies glow - not Xenomorphs,
 *   they're cold to it), EM (only Xenomorphs glow);
 * - C (wristbracer in your inventory): cloak - invisible for up to a minute; water shorts it out
 *   and being hurt breaks it; then it needs half a minute to recharge;
 * - K (wristbracer): self-destruct - press twice to arm; 10 seconds later it goes off (press again in
 *   the first 5 seconds to disarm). No honourable Yautja is ever taken.
 */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class YautjaGearActions
{
    public static final String[] VISION_NAMES = { "normal", "night", "thermal", "em" };
    private static final int CLOAK_TIME = 1200, CLOAK_RECHARGE = 600, COUNTDOWN = 200;
    /** Players who pressed self-destruct once (game time), waiting for the confirming press. */
    private static final java.util.Map<Player, Long> ARMING = new java.util.WeakHashMap<>();

    public static boolean hasWristbracer(Player player)
    {
        return player.getInventory().contains(ModGear.WRISTBRACER.get().getDefaultInstance());
    }

    public static void handle(ServerPlayer player, int action)
    {
        switch (action)
        {
            case GearActionPayload.VISION_MODE -> vision(player);
            case GearActionPayload.CLOAK -> cloak(player);
            case GearActionPayload.SELF_DESTRUCT -> selfDestruct(player);
            default ->
            {
            }
        }
    }

    private static void vision(ServerPlayer player)
    {
        if (!BiomaskEvents.isBiomask(player.getItemBySlot(EquipmentSlot.HEAD)))
        {
            player.displayClientMessage(Component.translatable("message.avp.need_biomask"), true);
            return;
        }
        int mode = (player.getData(ModAttachments.VISION) + 1) % VISION_NAMES.length;
        player.setData(ModAttachments.VISION, mode);
        PacketDistributor.sendToPlayer(player, new VisionModePayload(mode));
        player.level().playSound(null, player.blockPosition(), ModGear.VISION_SWITCH.get(), SoundSource.PLAYERS, 0.8F, 1.0F);
        player.displayClientMessage(Component.translatable("message.avp.vision." + VISION_NAMES[mode]).withStyle(ChatFormatting.GOLD), true);
        if (mode == 0)
        {
            player.removeEffect(MobEffects.NIGHT_VISION);
        }
    }

    private static void cloak(ServerPlayer player)
    {
        if (!hasWristbracer(player))
        {
            return;
        }
        if (player.getData(ModAttachments.CLOAK) > 0)
        {
            decloak(player);
            return;
        }
        int cooldown = player.getData(ModAttachments.CLOAK_COOLDOWN);
        if (cooldown > 0)
        {
            player.displayClientMessage(Component.translatable("message.avp.cloak_recharging", cooldown / 20 + 1), true);
            return;
        }
        if (player.isInWaterOrRain())
        {
            player.displayClientMessage(Component.translatable("message.avp.cloak_wet").withStyle(ChatFormatting.RED), true);
            return;
        }
        player.setData(ModAttachments.CLOAK, CLOAK_TIME);
        player.level().playSound(null, player.blockPosition(), net.minecraft.sounds.SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 0.6F, 1.6F);
    }

    private static void decloak(Player player)
    {
        player.setData(ModAttachments.CLOAK, 0);
        player.setData(ModAttachments.CLOAK_COOLDOWN, CLOAK_RECHARGE);
        player.removeEffect(MobEffects.INVISIBILITY);
        player.level().playSound(null, player.blockPosition(), net.minecraft.sounds.SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 0.6F, 1.6F);
    }

    private static void selfDestruct(ServerPlayer player)
    {
        if (!hasWristbracer(player) || !AvpConfig.server(AvpConfig.SERVER.selfDestruct))
        {
            return;
        }
        int countdown = player.getData(ModAttachments.SELF_DESTRUCT);
        long now = player.level().getGameTime();
        if (countdown > 0)
        {
            if (countdown > COUNTDOWN / 2)
            {
                player.setData(ModAttachments.SELF_DESTRUCT, 0);
                player.displayClientMessage(Component.translatable("message.avp.self_destruct_disarmed").withStyle(ChatFormatting.GREEN), true);
            }
            return;
        }
        Long first = ARMING.get(player);
        if (first == null || now - first > 60)
        {
            ARMING.put(player, now);
            player.displayClientMessage(Component.translatable("message.avp.self_destruct_confirm").withStyle(ChatFormatting.RED), true);
            return;
        }
        ARMING.remove(player);
        player.setData(ModAttachments.SELF_DESTRUCT, COUNTDOWN);
    }

    @SubscribeEvent
    public static void tick(PlayerTickEvent.Post event)
    {
        Player player = event.getEntity();
        if (!(player.level() instanceof ServerLevel level))
        {
            return;
        }
        // vision: night vision in every mode but normal (BiomaskEvents keeps it topped up)
        // cloak
        int cloak = player.getData(ModAttachments.CLOAK);
        if (cloak > 0)
        {
            if (player.isInWaterOrRain() || !hasWristbracer(player) || cloak == 1)
            {
                decloak(player);
            }
            else
            {
                player.setData(ModAttachments.CLOAK, cloak - 1);
                if (cloak % 20 == 0 || !player.hasEffect(MobEffects.INVISIBILITY))
                {
                    player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 40, 0, true, false, true));
                }
            }
        }
        int cooldown = player.getData(ModAttachments.CLOAK_COOLDOWN);
        if (cooldown > 0)
        {
            player.setData(ModAttachments.CLOAK_COOLDOWN, cooldown - 1);
        }
        // self-destruct countdown
        int countdown = player.getData(ModAttachments.SELF_DESTRUCT);
        if (countdown > 0)
        {
            player.setData(ModAttachments.SELF_DESTRUCT, countdown - 1);
            if (countdown % 20 == 0)
            {
                level.playSound(null, player.blockPosition(), ModGear.BLADES_ALARM.get(), SoundSource.PLAYERS, 2.0F, 1.0F + (COUNTDOWN - countdown) / 400F);
                player.displayClientMessage(Component.translatable("message.avp.self_destruct", countdown / 20).withStyle(ChatFormatting.DARK_RED), true);
            }
            if (countdown == 1)
            {
                double power = AvpConfig.server(AvpConfig.SERVER.selfDestructPower);
                level.explode(player, player.getX(), player.getY() + 1, player.getZ(), (float) power, false, Level.ExplosionInteraction.MOB);
                player.hurt(level.damageSources().explosion(player, player), Float.MAX_VALUE);
            }
        }
    }

    /** Tell a joining player's client which vision mode they're in. */
    @SubscribeEvent
    public static void login(net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer player)
        {
            PacketDistributor.sendToPlayer(player, new VisionModePayload(player.getData(ModAttachments.VISION)));
        }
    }

    /** Taking a hit breaks the cloak. */
    @SubscribeEvent
    public static void hurt(LivingDamageEvent.Post event)
    {
        if (event.getEntity() instanceof Player player && player.getData(ModAttachments.CLOAK) > 0 && event.getNewDamage() > 0)
        {
            decloak(player);
        }
    }

    private YautjaGearActions()
    {
    }
}
