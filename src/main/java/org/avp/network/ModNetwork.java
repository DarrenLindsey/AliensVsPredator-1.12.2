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
                case BlastDoorActionPayload.TOGGLE_AUTO_OPEN -> door.toggleAutoOpen(player);
                case BlastDoorActionPayload.TOGGLE_THREAT_DETECTION -> door.toggleThreatDetection(player);
                default ->
                {
                }
            }
        });

        registrar.playToServer(AssemblePayload.TYPE, AssemblePayload.CODEC, (payload, context) ->
        {
            Player player = context.player();
            if (player.distanceToSqr(payload.pos().getCenter()) > 64
                    || !(player.level().getBlockEntity(payload.pos()) instanceof org.avp.block.entity.AssemblerBlockEntity assembler))
            {
                return;
            }
            player.level().getRecipeManager().byKey(payload.recipe())
                    .filter(holder -> holder.value() instanceof org.avp.crafting.AssemblyRecipe)
                    .ifPresent(holder -> assembler.assemble(player, castAssembly(holder), Math.max(1, Math.min(64, payload.count()))));
        });

        registrar.playToServer(AssemblerSettingPayload.TYPE, AssemblerSettingPayload.CODEC, (payload, context) ->
        {
            Player player = context.player();
            if (player.distanceToSqr(payload.pos().getCenter()) > 64
                    || !(player.level().getBlockEntity(payload.pos()) instanceof org.avp.block.entity.AssemblerBlockEntity assembler))
            {
                return;
            }
            switch (payload.action())
            {
                case AssemblerSettingPayload.LOCK -> assembler.lock(net.minecraft.resources.ResourceLocation.tryParse(payload.recipe()));
                case AssemblerSettingPayload.TOGGLE_AUTO_OUTPUT -> assembler.toggleAutoOutput();
                case AssemblerSettingPayload.EMPTY_BUFFERS -> assembler.emptyBuffers(player);
                default ->
                {
                }
            }
        });

        registrar.playToServer(GearActionPayload.TYPE, GearActionPayload.CODEC, (payload, context) ->
        {
            if (context.player() instanceof net.minecraft.server.level.ServerPlayer player)
            {
                org.avp.item.gear.GearActions.handle(player, payload.action());
            }
        });
        registrar.playToServer(TurretSettingPayload.TYPE, TurretSettingPayload.CODEC, (payload, context) ->
        {
            Player player = context.player();
            if (player.distanceToSqr(payload.pos().getCenter()) <= 64
                    && player.level().getBlockEntity(payload.pos()) instanceof org.avp.block.entity.TurretBlockEntity turret)
            {
                turret.toggleTarget(player, payload.category());
            }
        });

        // the handler body runs only on the client, so the client-only screen class is never loaded on a server
        registrar.playToClient(OpenBlastDoorScreenPayload.TYPE, OpenBlastDoorScreenPayload.CODEC,
                (payload, context) -> org.avp.client.ClientHooks.openBlastDoorScreen(payload));
    }

    @SuppressWarnings("unchecked")
    private static net.minecraft.world.item.crafting.RecipeHolder<org.avp.crafting.AssemblyRecipe> castAssembly(net.minecraft.world.item.crafting.RecipeHolder<?> holder)
    {
        return (net.minecraft.world.item.crafting.RecipeHolder<org.avp.crafting.AssemblyRecipe>) holder;
    }

    private ModNetwork()
    {
    }
}
