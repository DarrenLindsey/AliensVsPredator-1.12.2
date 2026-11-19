package org.avp.network;

import org.avp.AliensVsPredator;
import org.avp.block.entity.BlastDoorBlockEntity;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/**
 * Custom network messages ("payloads"). Each one has a type, a codec that turns it into
 * bytes, and a handler that runs on the receiving side (on the main game thread).
 */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class ModNetwork
{
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event)
    {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(BlastDoorActionPayload.TYPE, BlastDoorActionPayload.CODEC, (payload, context) ->
        {
            Player player = context.player();
            // must be near the door, and it must really be a blast door controller
            if (player.distanceToSqr(payload.pos().getCenter()) > 64
                    || !(player.level().getBlockEntity(payload.pos()) instanceof BlastDoorBlockEntity door))
            {
                return;
            }
            switch (payload.action())
            {
                case BlastDoorActionPayload.TRY_PASSWORD -> door.tryPassword(player, payload.text());
                case BlastDoorActionPayload.SET_PASSWORD -> door.setPassword(player, payload.text());
                case BlastDoorActionPayload.TOGGLE_AUTOLOCK -> door.toggleAutolock(player);
                case BlastDoorActionPayload.BIND_TUNER -> door.bindTuner(player);
                default ->
                {
                }
            }
        });

        // the handler body runs only on the client, so the client-only screen class is never loaded on a server
        registrar.playToClient(OpenBlastDoorScreenPayload.TYPE, OpenBlastDoorScreenPayload.CODEC,
                (payload, context) -> org.avp.client.ClientHooks.openBlastDoorScreen(payload));
    }

    private ModNetwork()
    {
    }
}
