package org.avp.client;

import org.avp.AliensVsPredator;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/**
 * The client-only half of the mod's entry point (dist = CLIENT, so it never
 * loads on a dedicated server). Adds the "Config" button in the Mods list,
 * which opens NeoForge's built-in settings screen for AvpConfig.
 */
@Mod(value = AliensVsPredator.MOD_ID, dist = Dist.CLIENT)
public class AvpClient
{
    public AvpClient(ModContainer container)
    {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
