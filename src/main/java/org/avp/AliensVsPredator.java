package org.avp;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.avp.registry.ModBlocks;
import org.avp.registry.ModCreativeTabs;
import org.avp.registry.ModItems;
import org.slf4j.Logger;

/**
 * Entry point of the mod. Forge finds this class through the @Mod annotation
 * (the id must match mod_id in gradle.properties / neoforge.mods.toml).
 *
 * In 1.12.2 this was done with preInit/init/postInit events. In 1.20.1 we just
 * hand our DeferredRegisters to the "mod event bus" and Forge registers
 * everything at the right time for us.
 */
@Mod(AliensVsPredator.MOD_ID)
public class AliensVsPredator
{
    public static final String MOD_ID = "avp";
    public static final Logger LOGGER = LogUtils.getLogger();

    /** NeoForge passes in the mod event bus and our mod container. */
    public AliensVsPredator(IEventBus modBus, ModContainer container)
    {
        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModCreativeTabs.TABS.register(modBus);

        LOGGER.info("AliensVsPredator loading - Building Better Worlds");
    }
}
