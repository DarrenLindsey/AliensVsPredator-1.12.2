package org.avp.entity.xenomorph;

import org.avp.AliensVsPredator;
import org.avp.config.AvpConfig;
import org.avp.registry.ModAttachments;
import org.avp.registry.ModEntities;
import org.avp.world.hive.HiveData;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

/**
 * Hosts cocooned in a hive: held fast in resin, waiting for an egg to open next to them. Left long
 * enough, a cocooned host turns into an egg itself ("eggmorphing", the deleted scene from Alien).
 * Sneak-use a cocoon with an empty hand to cut the host free (the hive won't like it).
 */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class CocoonEvents
{
    @SubscribeEvent
    public static void tick(EntityTickEvent.Post event)
    {
        if (!(event.getEntity() instanceof Mob host) || !host.hasData(ModAttachments.COCOONED) || !(host.level() instanceof ServerLevel level))
        {
            return;
        }
        if (host.tickCount % 20 != 0)
        {
            return;
        }
        int age = host.getData(ModAttachments.COCOONED) + 20;
        host.setData(ModAttachments.COCOONED, age);
        host.setNoAi(true);
        if (host.getRandom().nextInt(4) == 0)
        {
            level.sendParticles(ParticleTypes.DRIPPING_OBSIDIAN_TEAR, host.getX(), host.getY() + host.getBbHeight(), host.getZ(), 1, 0.2, 0.1, 0.2, 0);
        }
        if (AvpConfig.server(AvpConfig.SERVER.eggmorphing) && age >= AvpConfig.server(AvpConfig.SERVER.eggmorphTicks)
                && !host.hasData(ModAttachments.EMBRYO))
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

    @SubscribeEvent
    public static void cutFree(PlayerInteractEvent.EntityInteract event)
    {
        if (!(event.getTarget() instanceof Mob host) || !host.hasData(ModAttachments.COCOONED) || !event.getEntity().isShiftKeyDown()
                || !event.getEntity().getMainHandItem().isEmpty())
        {
            return;
        }
        if (host.level() instanceof ServerLevel level)
        {
            host.removeData(ModAttachments.COCOONED);
            host.setNoAi(false);
            level.playSound(null, host.blockPosition(), SoundEvents.SLIME_BLOCK_BREAK, SoundSource.PLAYERS, 1.0F, 0.8F);
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
