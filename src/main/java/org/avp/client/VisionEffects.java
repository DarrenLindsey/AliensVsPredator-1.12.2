package org.avp.client;

import java.util.HashSet;
import java.util.Set;

import org.avp.AliensVsPredator;
import org.avp.item.BiomaskEvents;
import org.avp.registry.ModTags;

import net.minecraft.client.Minecraft;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

/**
 * Biomask vision on this client: in thermal mode warm bodies glow through walls (Xenomorphs and
 * the undead are cold - invisible to it, as in the films); in EM mode only Xenomorphs glow. The
 * glow is set on this client's copies of the creatures only, so nobody else sees it.
 */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public final class VisionEffects
{
    public static int mode = 1;
    private static final Set<Entity> GLOWING = new HashSet<>();

    @SubscribeEvent
    public static void tick(ClientTickEvent.Post event)
    {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null)
        {
            GLOWING.clear();
            return;
        }
        boolean masked = BiomaskEvents.isBiomask(mc.player.getItemBySlot(EquipmentSlot.HEAD));
        Set<Entity> now = new HashSet<>();
        if (masked && (mode == 2 || mode == 3))
        {
            for (LivingEntity e : mc.level.getEntitiesOfClass(LivingEntity.class, mc.player.getBoundingBox().inflate(48), e -> e != mc.player))
            {
                boolean xeno = e.getType().is(ModTags.XENOMORPHS);
                boolean warm = !xeno && !e.getType().is(EntityTypeTags.UNDEAD);
                if (mode == 2 ? warm : xeno)
                {
                    now.add(e);
                }
            }
        }
        for (Entity e : GLOWING)
        {
            if (!now.contains(e))
            {
                e.setGlowingTag(false);
            }
        }
        for (Entity e : now)
        {
            e.setGlowingTag(true);
        }
        GLOWING.clear();
        GLOWING.addAll(now);
    }

    private VisionEffects()
    {
    }
}
