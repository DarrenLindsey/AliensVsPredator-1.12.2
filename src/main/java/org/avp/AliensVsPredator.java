package org.avp;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.avp.config.AvpConfig;
import org.avp.registry.ModBlockEntities;
import org.avp.registry.ModBlocks;
import org.avp.registry.ModCreativeTabs;
import org.avp.registry.ModEntities;
import org.avp.registry.ModItems;
import org.avp.registry.ModMenus;
import org.avp.registry.ModSounds;
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
        org.avp.registry.ModArmor.load();
        org.avp.registry.ModGear.load();
        org.avp.registry.ModArmor.MATERIALS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modBus);
        ModMenus.MENUS.register(modBus);
        ModEntities.ENTITIES.register(modBus);
        ModSounds.SOUNDS.register(modBus);
        org.avp.registry.ModDataComponents.COMPONENTS.register(modBus);
        org.avp.registry.ModAttachments.ATTACHMENTS.register(modBus);
        org.avp.registry.ModRecipes.TYPES.register(modBus);
        org.avp.registry.ModRecipes.SERIALIZERS.register(modBus);
        ModCreativeTabs.TABS.register(modBus);

        // settings files (config/avp-client.toml and the per-world avp-server.toml)
        container.registerConfig(ModConfig.Type.CLIENT, AvpConfig.CLIENT_SPEC);
        container.registerConfig(ModConfig.Type.SERVER, AvpConfig.SERVER_SPEC);
        container.registerConfig(ModConfig.Type.COMMON, AvpConfig.COMMON_SPEC);
        org.avp.config.ConfigCondition.CONDITIONS.register(modBus);

        LOGGER.info("AliensVsPredator loading - Building Better Worlds");
    }
}
