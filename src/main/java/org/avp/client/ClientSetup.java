package org.avp.client;

import java.util.Arrays;

import org.avp.AliensVsPredator;
import org.avp.client.model.MimicBakedModel;
import org.avp.client.model.geometry.AllModelLayers;
import org.avp.client.render.BlastDoorRenderer;
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
        event.registerLayerDefinition(BlastDoorRenderer.ALARM_LAYER, BlastDoorRenderer::createAlarmLight);
        event.registerLayerDefinition(org.avp.client.render.HumanRenderer.LAYER, org.avp.client.render.HumanRenderer::createLayer);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void registerCreature(EntityRenderersEvent.RegisterRenderers event, net.minecraft.world.entity.EntityType type, String id)
    {
        if (org.avp.entity.creature.Creatures.ALL.get(id).family() == org.avp.entity.creature.Family.HUMAN)
        {
            event.registerEntityRenderer(type, context -> new org.avp.client.render.HumanRenderer(context, id));
        }
        else
        {
            event.registerEntityRenderer(type, context -> new org.avp.client.render.CreatureRenderer<>(context, id));
        }
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(ModBlockEntities.MODEL_BLOCK.get(), ModelBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.STORAGE.get(), ModelBlockRenderer::new);
        event.registerEntityRenderer(ModEntities.SEAT.get(), NoopRenderer::new); // invisible
        event.registerEntityRenderer(ModEntities.FACEHUGGER.get(), org.avp.client.render.FacehuggerRenderer::new);
        event.registerEntityRenderer(ModEntities.CHESTBURSTER.get(), org.avp.client.render.ChestbursterRenderer::new);
        event.registerEntityRenderer(ModEntities.OVAMORPH.get(), org.avp.client.render.OvamorphRenderer::new);
        event.registerEntityRenderer(ModEntities.DRONE.get(), org.avp.client.render.DroneRenderer::new);
        event.registerEntityRenderer(ModEntities.BUILDER_DRONE.get(), org.avp.client.render.DroneRenderer::new);
        event.registerEntityRenderer(ModEntities.COATER_DRONE.get(), org.avp.client.render.DroneRenderer::new);
        event.registerEntityRenderer(ModEntities.CREATURE_PROJECTILE.get(), NoopRenderer::new); // particles only
        ModEntities.CATALOG.forEach((id, type) -> registerCreature(event, type.get(), id));
        event.registerBlockEntityRenderer(ModBlockEntities.POWERED.get(), PoweredBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.BLAST_DOOR.get(), BlastDoorRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.TURRET.get(), org.avp.client.render.TurretRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.ASSEMBLER.get(), org.avp.client.render.AssemblerRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.POWERLINE.get(), org.avp.client.render.PowerlineRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.POWER_MACHINE.get(), org.avp.client.render.PowerMachineRenderer::new);
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
        event.registerItem(beItems, ModBlocks.BLASTDOOR.get().asItem(), ModBlocks.BLASTDOOR_SEVASTOPOL.get().asItem());
        // power blocks drawn by a renderer (the redstone emitter is a normal cube model)
        event.registerItem(beItems, Arrays.stream(ModBlocks.powerMachines()).filter(b -> b != ModBlocks.REDSTONEEMITTER.get())
                .map(Block::asItem).toArray(Item[]::new));
        event.registerItem(beItems, ModBlocks.POWERLINE.get().asItem(), ModBlocks.TURRET.get().asItem());
        // guns: their 3D model, held up and aimed in third person
        IClientItemExtensions guns = new IClientItemExtensions()
        {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer()
            {
                if (renderer == null)
                {
                    renderer = new org.avp.client.render.FirearmRenderer();
                }
                return renderer;
            }

            @Override
            public net.minecraft.client.model.HumanoidModel.ArmPose getArmPose(net.minecraft.world.entity.LivingEntity entity,
                    net.minecraft.world.InteractionHand hand, net.minecraft.world.item.ItemStack stack)
            {
                boolean twoHanded = stack.getItem() instanceof org.avp.item.firearm.FirearmItem gun && gun.firearm().twoHanded();
                if (twoHanded)
                {
                    return net.minecraft.client.model.HumanoidModel.ArmPose.CROSSBOW_HOLD; // rifles are held up, ready
                }
                return entity.isUsingItem() ? net.minecraft.client.model.HumanoidModel.ArmPose.BOW_AND_ARROW // pistol raised to fire
                        : net.minecraft.client.model.HumanoidModel.ArmPose.ITEM;
            }
        };
        event.registerItem(guns, org.avp.registry.ModItems.FIREARMS.values().stream().map(net.neoforged.neoforge.registries.DeferredItem::get)
                .toArray(Item[]::new));
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event)
    {
        event.register(ModMenus.STORAGE.get(), StorageScreen::new);
        event.register(ModMenus.TURRET.get(), org.avp.client.screen.TurretScreen::new);
    }

    /** Wrap the slope/corner/... models so they can show a copied block's texture. */
    @SubscribeEvent
    public static void wrapShapeModels(ModelEvent.ModifyBakingResult event)
    {
        var shapeIds = Arrays.stream(ModBlocks.shapeBlocks()).map(BuiltInRegistries.BLOCK::getKey).toList();
        event.getModels().replaceAll((location, model) ->
                shapeIds.contains(location.id()) && !"inventory".equals(location.variant()) ? new MimicBakedModel(model)
                        : location.id().equals(BuiltInRegistries.BLOCK.getKey(ModBlocks.NATURAL_RESIN.get())) ? new org.avp.client.model.ResinBakedModel(model)
                        : model);
    }

    private ClientSetup()
    {
    }
}
