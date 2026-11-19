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
}
