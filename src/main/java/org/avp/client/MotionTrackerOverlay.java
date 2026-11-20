package org.avp.client;

import org.avp.AliensVsPredator;
import org.avp.item.gear.MotionTrackerItem;
import org.avp.registry.ModGear;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

/**
 * The motion tracker screen (held in either hand): a 180-degree sweep in front of you, 30 blocks
 * out. Anything moving shows as a blip; the tracker pings, and pongs higher as contacts get close,
 * with the range to the nearest one - "they're right on top of us".
 */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class MotionTrackerOverlay
{
    private static final double RANGE = 30;
    private static long lastPing;

    @SubscribeEvent
    public static void register(RegisterGuiLayersEvent event)
    {
        event.registerAbove(VanillaGuiLayers.HOTBAR, ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "motion_tracker"),
                (graphics, delta) -> draw(graphics));
    }

    private static void draw(GuiGraphics graphics)
    {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || mc.options.hideGui || !(player.getMainHandItem().getItem() instanceof MotionTrackerItem
                || player.getOffhandItem().getItem() instanceof MotionTrackerItem))
        {
            return;
        }
        int r = 50, cx = 12 + r, cy = graphics.guiHeight() - 30;
        // the screen: a dark half-disc with range rings and a sweep
        for (int y = 0; y <= r; y++)
        {
            int w = (int) Math.sqrt(r * r - y * y);
            graphics.fill(cx - w, cy - y, cx + w, cy - y + 1, 0xC0081808);
        }
        for (int ring = 1; ring <= 3; ring++)
        {
            int rr = r * ring / 3;
            for (int a = 0; a <= 180; a += 4)
            {
                double rad = Math.toRadians(a);
                graphics.fill(cx + (int) (Math.cos(rad) * rr), cy - (int) (Math.sin(rad) * rr), cx + (int) (Math.cos(rad) * rr) + 1,
                        cy - (int) (Math.sin(rad) * rr) + 1, 0x8040C040);
            }
        }
        long time = player.level().getGameTime();
        double sweep = (time % 40) / 40.0 * Math.PI;
        for (int i = 0; i < r; i++)
        {
            graphics.fill(cx + (int) (Math.cos(sweep) * i), cy - (int) (Math.sin(sweep) * i), cx + (int) (Math.cos(sweep) * i) + 1,
                    cy - (int) (Math.sin(sweep) * i) + 1, 0x6080FF80);
        }
        // contacts: anything that moved this tick, in front of you
        double nearest = Double.MAX_VALUE;
        float yaw = player.getYRot() * Mth.DEG_TO_RAD;
        for (LivingEntity e : player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(RANGE), e -> e != player))
        {
            double mx = e.getX() - e.xo, mz = e.getZ() - e.zo, my = e.getY() - e.yo;
            if (mx * mx + my * my + mz * mz < 0.0004)
            {
                continue; // standing still: the tracker can't see it
            }
            double dx = e.getX() - player.getX(), dz = e.getZ() - player.getZ();
            double distance = Math.sqrt(dx * dx + dz * dz);
            // into the player's frame: forward = up the screen
            double fwd = -Math.sin(yaw) * dx + Math.cos(yaw) * dz;
            double side = -Math.cos(yaw) * dx - Math.sin(yaw) * dz;
            if (distance > RANGE || fwd < -2)
            {
                continue;
            }
            nearest = Math.min(nearest, distance);
            int bx = cx + (int) (side / RANGE * r), by = cy - (int) (Math.max(0, fwd) / RANGE * r);
            graphics.fill(bx - 1, by - 1, bx + 2, by + 2, 0xFFB0FFB0);
        }
        String range = nearest == Double.MAX_VALUE ? "--" : String.format("%02dm", (int) nearest);
        graphics.drawString(mc.font, range, cx - mc.font.width(range) / 2, cy + 3, 0xFF60FF60, false);
        // ping... and pong, sooner and higher the closer they are
        int interval = nearest < RANGE ? Math.max(8, (int) (nearest * 1.2)) : 40;
        if (time - lastPing >= interval)
        {
            lastPing = time;
            boolean contact = nearest < RANGE;
            mc.getSoundManager().play(SimpleSoundInstance.forUI(contact ? ModGear.TRACKER_PONG.get() : ModGear.TRACKER_PING.get(), contact
                    ? 1.0F + (float) Math.max(0, (RANGE - nearest) / RANGE) * 0.5F : 1.0F, 0.35F));
        }
    }

    private MotionTrackerOverlay()
    {
    }
}
