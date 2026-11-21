package org.avp.entity.xenomorph;

import org.avp.AliensVsPredator;
import org.avp.config.AvpConfig;
import org.avp.network.CocoonStatePayload;
import org.avp.registry.ModAttachments;
import org.avp.registry.ModEntities;
import org.avp.world.hive.HiveData;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Hosts cocooned in a hive: held fast in resin, waiting for an egg to open next to them. Left long
 * enough, a cocooned host turns into an egg itself ("eggmorphing", the deleted scene from Alien).
 * Sneak-use a cocoon with an empty hand to cut the host free (the hive won't like it).
 * Players can be cocooned too: held on the wall until they struggle free (mash sneak), a friend
 * cuts them free, or an egg opens in front of them.
 */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class CocoonEvents
{
    /** Last sneak state of each cocooned player (a press = sneak going down). */
    private static final java.util.Map<Player, Boolean> SNEAKING = new java.util.WeakHashMap<>();

    @SubscribeEvent
    public static void protectCapture(LivingIncomingDamageEvent event)
    {
        if (event.getSource().getEntity() instanceof XenomorphEntity xeno && PreyCapture.blocksAttack(xeno, event.getEntity()))
        {
            event.setCanceled(true);
        }
    }

    static void syncWrap(LivingEntity host)
    {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(host, new CocoonStatePayload(host.getId(), host.hasData(ModAttachments.COCOONED)));
    }

    @SubscribeEvent
    public static void startTracking(PlayerEvent.StartTracking event)
    {
        if (event.getEntity() instanceof ServerPlayer player && event.getTarget() instanceof LivingEntity host
                && host.hasData(ModAttachments.COCOONED))
        {
            PacketDistributor.sendToPlayer(player, new CocoonStatePayload(host.getId(), true));
        }
    }

    @SubscribeEvent
    public static void playerJoined(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer player && player.hasData(ModAttachments.COCOONED))
        {
            syncWrap(player);
        }
    }

    @SubscribeEvent
    public static void tick(EntityTickEvent.Post event)
    {
        if (!(event.getEntity() instanceof LivingEntity host) || !host.hasData(ModAttachments.COCOONED) || !(host.level() instanceof ServerLevel level))
        {
            return;
        }
        if (host instanceof Player player)
        {
            holdPlayer(level, player);
        }
        if (host.tickCount % 20 != 0)
        {
            return;
        }
        int age = host.getData(ModAttachments.COCOONED) + 20;
        host.setData(ModAttachments.COCOONED, age);
        if (host instanceof Mob mob)
        {
            mob.setNoAi(true);
        }
        if (host.getRandom().nextInt(4) == 0)
        {
            level.sendParticles(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, host.getX(), host.getY() + host.getBbHeight(), host.getZ(), 1, 0.2, 0.1, 0.2, 0);
        }
        if (host instanceof Mob && AvpConfig.server(AvpConfig.SERVER.eggmorphing) && age >= AvpConfig.server(AvpConfig.SERVER.eggmorphTicks)
                && !host.hasData(ModAttachments.EMBRYO) && org.avp.entity.parasite.FacehuggerEntity.isValidHost(host))
        {
            OvamorphEntity egg = ModEntities.OVAMORPH.get().create(level);
            if (egg != null)
            {
                egg.moveTo(host.getX(), host.getY(), host.getZ(), host.getYRot(), 0);
                level.addFreshEntity(egg);
                level.sendParticles(ParticleTypes.ITEM_SLIME, host.getX(), host.getY() + 0.5, host.getZ(), 30, 0.3, 0.4, 0.3, 0.05);
                level.playSound(null, host.blockPosition(), SoundEvents.SLIME_BLOCK_BREAK, SoundSource.HOSTILE, 1.0F, 0.5F);
                host.discard();
            }
        }
    }

    /**
     * A cocooned player is held fast on the wall (looking around is all they can do) and too weak
     * to fight. Each press of sneak tears at the resin; enough and they break free - which the
     * hive hears.
     */
    private static void holdPlayer(ServerLevel level, Player player)
    {
        if (player.isCreative() || player.isSpectator())
        {
            release(level, player, false);
            return;
        }
        BlockPos at = player.getData(ModAttachments.COCOON_POS);
        if (player.position().distanceToSqr(at.getX() + 0.5, at.getY(), at.getZ() + 0.5) > 0.09 && !player.isPassenger())
        {
            player.teleportTo(at.getX() + 0.5, at.getY(), at.getZ() + 0.5);
        }
        player.setDeltaMovement(0, Math.min(0, player.getDeltaMovement().y), 0);
        if (player.tickCount % 40 == 0)
        {
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 9, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 4, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 4, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 60, 128, false, false)); // 128+ = can't jump
        }
        boolean sneaking = player.isShiftKeyDown();
        boolean was = SNEAKING.getOrDefault(player, false);
        SNEAKING.put(player, sneaking);
        int needed = AvpConfig.server(AvpConfig.SERVER.cocoonStruggles);
        int struggle = player.getData(ModAttachments.STRUGGLE);
        if (sneaking && !was)
        {
            struggle++;
            player.setData(ModAttachments.STRUGGLE, struggle);
            level.playSound(null, player.blockPosition(), SoundEvents.SLIME_BLOCK_HIT, SoundSource.PLAYERS, 0.6F, 0.6F + level.random.nextFloat() * 0.3F);
            if (struggle == 1 || struggle % 10 == 0)
            {
                HiveData.alert(level, player.blockPosition(), player); // the hive hears it
            }
            if (struggle >= needed)
            {
                release(level, player, true);
                return;
            }
        }
        if (player.tickCount % 10 == 0)
        {
            player.displayClientMessage(Component.translatable("message.avp.struggle", struggle * 100 / needed), true);
        }
    }

    /** Lets a host out of its cocoon. */
    public static void release(ServerLevel level, LivingEntity host, boolean brokeFree)
    {
        host.removeData(ModAttachments.COCOONED);
        host.removeData(ModAttachments.COCOON_POS);
        syncWrap(host);
        host.removeData(ModAttachments.STRUGGLE);
        if (host instanceof Mob mob)
        {
            mob.setNoAi(false);
        }
        else
        {
            host.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            host.removeEffect(MobEffects.JUMP);
            host.removeEffect(MobEffects.WEAKNESS);
            host.removeEffect(MobEffects.DIG_SLOWDOWN);
        }
        level.playSound(null, host.blockPosition(), SoundEvents.SLIME_BLOCK_BREAK, SoundSource.PLAYERS, 1.0F, 0.8F);
        level.sendParticles(ParticleTypes.ITEM_SLIME, host.getX(), host.getY() + 1, host.getZ(), 20, 0.3, 0.5, 0.3, 0.05);
        if (brokeFree && host instanceof Player player)
        {
            player.displayClientMessage(Component.translatable("message.avp.broke_free"), true);
        }
    }

    /** A Drone carrying a player doesn't let go because they pressed sneak. */
    @SubscribeEvent
    public static void noEscape(EntityMountEvent event)
    {
        if (event.isDismounting() && event.getEntityMounting() instanceof Player && event.getEntityBeingMounted() instanceof DroneEntity drone
                && drone.isAlive() && !drone.releasing && !drone.isRemoved())
        {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void cutFree(PlayerInteractEvent.EntityInteract event)
    {
        if (!(event.getTarget() instanceof LivingEntity host) || !host.hasData(ModAttachments.COCOONED) || !event.getEntity().isShiftKeyDown()
                || !event.getEntity().getMainHandItem().isEmpty())
        {
            return;
        }
        if (host.level() instanceof ServerLevel level)
        {
            release(level, host, false);
            event.getEntity().displayClientMessage(Component.translatable("message.avp.cocoon_freed"), true);
            HiveData.alert(level, host.blockPosition(), event.getEntity());
        }
        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }

    private CocoonEvents()
    {
    }
}
