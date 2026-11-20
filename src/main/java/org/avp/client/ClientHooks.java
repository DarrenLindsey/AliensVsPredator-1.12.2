package org.avp.client;

import org.avp.client.screen.BlastDoorScreen;
import org.avp.network.OpenBlastDoorScreenPayload;

import net.minecraft.client.Minecraft;

/** Client-only actions triggered from common code (only ever called on the client). */
public final class ClientHooks
{
    public static void openBlastDoorScreen(OpenBlastDoorScreenPayload payload)
    {
        Minecraft.getInstance().setScreen(new BlastDoorScreen(payload));
    }

    public static void openAssembler(net.minecraft.core.BlockPos pos)
    {
        Minecraft.getInstance().setScreen(new org.avp.client.screen.AssemblerScreen(pos));
    }

    private ClientHooks()
    {
    }

    /** Seegson Terminal data: update the open screen, or open one. */
    public static void joeTerminal(net.minecraft.nbt.CompoundTag data)
    {
        var mc = net.minecraft.client.Minecraft.getInstance();
        if (mc.screen instanceof org.avp.client.screen.SeegsonTerminalScreen screen)
        {
            screen.update(data);
        }
        else if (data.getBoolean("Open"))
        {
            mc.setScreen(new org.avp.client.screen.SeegsonTerminalScreen(data));
        }
    }

    /** The biomask vision mode changed (see VisionEffects). */
    public static void visionMode(int mode)
    {
        VisionEffects.mode = mode;
    }
}
