package org.avp.client;

import org.avp.AliensVsPredator;
import org.avp.registry.ModItems;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

/** Client events that happen during play (the "game" event bus, not the mod bus). */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public final class ClientGameEvents
{
    /** Shows which blast door a Security Tuner is bound to. */
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event)
    {
        if (event.getItemStack().is(ModItems.TOOL_SECURITYTUNER.get()))
        {
            CustomData data = event.getItemStack().get(DataComponents.CUSTOM_DATA);
            String door = data == null ? "" : data.copyTag().getString("BoundDoor");
            event.getToolTip().add(door.isEmpty()
                    ? Component.translatable("tooltip.avp.securitytuner.unbound").withStyle(ChatFormatting.GRAY)
                    : Component.translatable("tooltip.avp.securitytuner.bound", door).withStyle(ChatFormatting.GREEN));
        }
        if (event.getItemStack().is(ModItems.TOOL_MAINTENANCEJACK.get()))
        {
            event.getToolTip().add(Component.translatable("tooltip.avp.maintenancejack").withStyle(ChatFormatting.GRAY));
        }
    }

    private ClientGameEvents()
    {
    }
}
