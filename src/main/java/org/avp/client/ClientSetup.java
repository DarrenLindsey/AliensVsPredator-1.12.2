package org.avp.client;

import java.util.Arrays;

import org.avp.AliensVsPredator;
import org.avp.client.model.MimicBakedModel;
import org.avp.client.model.geometry.AllModelLayers;
import org.avp.client.render.BlockEntityItemRenderer;
import org.avp.client.render.ModelBlockItemRenderer;
import org.avp.client.render.PoweredBlockRenderer;
import org.avp.client.render.ModelBlockRenderInfo;
import org.avp.client.render.ModelBlockRenderer;
import org.avp.registry.ModBlockEntities;
import org.avp.registry.ModBlocks;
import org.avp.registry.ModEntities;
import org.avp.registry.ModMenus;
import org.avp.client.screen.StorageScreen;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

/**
 * Client-only setup (never runs on a dedicated server): model geometry,
 * block entity renderers, item renderers and the slope "mimic" models.
 */
@EventBusSubscriber(modid = AliensVsPredator.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetup
{
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(ModelBlockRenderInfo::init);
    }

    /** Geometry of every model converted from 1.12.2. */
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        AllModelLayers.register(event);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModBlockEntities.MODEL_BLOCK.get(), ModelBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.STORAGE.get(), ModelBlockRenderer::new);
        event.registerEntityRenderer(ModEntities.LOCKER_SEAT.get(), NoopRenderer::new); // invisible
        event.registerBlockEntityRenderer(ModBlockEntities.POWERED.get(), PoweredBlockRenderer::new);
    }

    /** Skull/biomask/head/fern items draw their 3D model too. */
    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event)
    {
        IClientItemExtensions extensions = new IClientItemExtensions()
        {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                if (renderer == null)
                {
                    renderer = new ModelBlockItemRenderer();
                }
                return renderer;
            }
        };
        event.registerItem(extensions, Arrays.stream(ModBlocks.modelBlocks()).map(Block::asItem).toArray(Item[]::new));
        event.registerItem(extensions, Arrays.stream(ModBlocks.storageBlocks()).map(Block::asItem).toArray(Item[]::new));
        IClientItemExtensions beItems = new IClientItemExtensions()
        {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                if (renderer == null)
                {
                    renderer = new BlockEntityItemRenderer();
                }
                return renderer;
            }
        };
        event.registerItem(beItems, Arrays.stream(ModBlocks.poweredBlocks()).map(Block::asItem).toArray(Item[]::new));
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event)
    {
        event.register(ModMenus.STORAGE.get(), StorageScreen::new);
    }

    /** Wrap the slope/corner/... models so they can show a copied block's texture. */
    @SubscribeEvent
    public static void wrapShapeModels(ModelEvent.ModifyBakingResult event)
    {
        var shapeIds = Arrays.stream(ModBlocks.shapeBlocks()).map(BuiltInRegistries.BLOCK::getKey).toList();
        event.getModels().replaceAll((location, model) ->
                shapeIds.contains(location.id()) && !"inventory".equals(location.variant()) ? new MimicBakedModel(model) : model);
    }

    private ClientSetup()
    {
    }
}
