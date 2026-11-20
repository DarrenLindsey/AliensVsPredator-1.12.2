package org.avp.registry;

import org.avp.AliensVsPredator;
import org.avp.block.entity.BlastDoorBlockEntity;
import org.avp.block.entity.ModelBlockEntity;
import org.avp.block.entity.PoweredBlockEntity;
import org.avp.block.entity.PowerMachineBlockEntity;
import org.avp.block.entity.PowerlineBlockEntity;
import org.avp.block.entity.ShapeBlockEntity;
import org.avp.block.entity.StorageBlockEntity;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Block entities ("tile entities" in 1.12.2). Each type lists the blocks allowed to have it. */
public final class ModBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, AliensVsPredator.MOD_ID);

    /** Blocks drawn with a box model: skulls, biomasks, heads, ferns. */
    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ModelBlockEntity>> MODEL_BLOCK = BLOCK_ENTITIES.register("model_block",
            () -> BlockEntityType.Builder.of(ModelBlockEntity::new, ModBlocks.modelBlocks()).build(null));

    /** Slopes/corners/ridges/pyramids that copy another block's texture. */
    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ShapeBlockEntity>> SHAPE = BLOCK_ENTITIES.register("shape",
            () -> BlockEntityType.Builder.of(ShapeBlockEntity::new, ModBlocks.shapeBlocks()).build(null));

    /** Hive resin over another block. */
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.block.entity.ResinBlockEntity>> RESIN = BLOCK_ENTITIES.register("resin",
            () -> BlockEntityType.Builder.of(org.avp.block.entity.ResinBlockEntity::new, ModBlocks.NATURAL_RESIN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.block.entity.FusionReactorBlockEntity>> FUSION_REACTOR = BLOCK_ENTITIES.register("fusion_reactor",
            () -> BlockEntityType.Builder.of(org.avp.block.entity.FusionReactorBlockEntity::new, ModBlocks.FUSION_REACTOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.block.entity.AtmosphereProcessorBlockEntity>> ATMOSPHERE_PROCESSOR = BLOCK_ENTITIES.register("atmosphere_processor",
            () -> BlockEntityType.Builder.of(org.avp.block.entity.AtmosphereProcessorBlockEntity::new, ModBlocks.ATMOSPHERE_PROCESSOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.entity.joe.JoeChargerBlockEntity>> JOE_CHARGER = BLOCK_ENTITIES.register("joe_charger",
            () -> BlockEntityType.Builder.of(org.avp.entity.joe.JoeChargerBlockEntity::new, ModBlocks.JOE_CHARGER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.entity.joe.SeegsonTerminalBlockEntity>> SEEGSON_TERMINAL = BLOCK_ENTITIES.register("seegson_terminal",
            () -> BlockEntityType.Builder.of(org.avp.entity.joe.SeegsonTerminalBlockEntity::new, ModBlocks.SEEGSON_TERMINAL.get(), ModBlocks.SEEGSON_SERVER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.block.conveyor.ConveyorBlockEntity>> CONVEYOR = BLOCK_ENTITIES.register("conveyor",
            () -> BlockEntityType.Builder.of(org.avp.block.conveyor.ConveyorBlockEntity::new, ModBlocks.CONVEYOR.get(), ModBlocks.CONVEYOR_EXPRESS.get(),
                    ModBlocks.CONVEYOR_SORTING.get(), ModBlocks.CONVEYOR_LOGIC.get(), ModBlocks.CONVEYOR_PROCESSING.get(), ModBlocks.CONVEYOR_CRAFTING.get(),
                    ModBlocks.CONVEYOR_STORAGE.get(), ModBlocks.CONVEYOR_EXTRACTOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.refining.RefineryBlockEntity>> REFINERY = BLOCK_ENTITIES.register("refinery",
            () -> BlockEntityType.Builder.of(org.avp.refining.RefineryBlockEntity::new,
                    org.avp.refining.Refining.BLOCKS.values().stream().map(b -> (net.minecraft.world.level.block.Block) b.get()).toArray(net.minecraft.world.level.block.Block[]::new)).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.pneumatic.PneumaticStationBlockEntity>> PNEUMATIC_STATION = BLOCK_ENTITIES.register("pneumatic_station",
            () -> BlockEntityType.Builder.of(org.avp.pneumatic.PneumaticStationBlockEntity::new, ModBlocks.PNEUMATIC_STATION.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.pneumatic.CompressorBlockEntity>> COMPRESSOR = BLOCK_ENTITIES.register("pneumatic_compressor",
            () -> BlockEntityType.Builder.of(org.avp.pneumatic.CompressorBlockEntity::new, ModBlocks.PNEUMATIC_COMPRESSOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.apollo.ApolloCoreBlockEntity>> APOLLO_CORE = BLOCK_ENTITIES.register("apollo_core",
            () -> BlockEntityType.Builder.of(org.avp.apollo.ApolloCoreBlockEntity::new, ModBlocks.APOLLO_CORE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.mining.SeismicDrillBlockEntity>> SEISMIC_DRILL = BLOCK_ENTITIES.register("seismic_drill",
            () -> BlockEntityType.Builder.of(org.avp.mining.SeismicDrillBlockEntity::new, ModBlocks.SEISMIC_DRILL.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.block.conveyor.spline.BeltAnchorBlockEntity>> BELT_ANCHOR = BLOCK_ENTITIES.register("belt_anchor",
            () -> BlockEntityType.Builder.of(org.avp.block.conveyor.spline.BeltAnchorBlockEntity::new, ModBlocks.BELT_ANCHOR.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.block.conveyor.spline.CablePylonBlockEntity>> CABLE_PYLON = BLOCK_ENTITIES.register("cable_pylon",
            () -> BlockEntityType.Builder.of(org.avp.block.conveyor.spline.CablePylonBlockEntity::new, ModBlocks.CABLE_PYLON.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.block.conveyor.spline.HoseAnchorBlockEntity>> HOSE_ANCHOR = BLOCK_ENTITIES.register("hose_anchor",
            () -> BlockEntityType.Builder.of(org.avp.block.conveyor.spline.HoseAnchorBlockEntity::new, ModBlocks.HOSE_ANCHOR.get()).build(null));

    /** Lockers and supply crates (64-slot inventory). */
    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StorageBlockEntity>> STORAGE = BLOCK_ENTITIES.register("storage",
            () -> BlockEntityType.Builder.of(StorageBlockEntity::new, ModBlocks.storageBlocks()).build(null));

    /** Lights and powered blocks (FE energy buffer). */
    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PoweredBlockEntity>> POWERED = BLOCK_ENTITIES.register("powered",
            () -> BlockEntityType.Builder.of(PoweredBlockEntity::new, ModBlocks.poweredBlocks()).build(null));

    /** Blast door controllers (power, password, locks). */
    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BlastDoorBlockEntity>> BLAST_DOOR = BLOCK_ENTITIES.register("blast_door",
            () -> BlockEntityType.Builder.of(BlastDoorBlockEntity::new, ModBlocks.BLASTDOOR.get(), ModBlocks.BLASTDOOR_SEVASTOPOL.get()).build(null));

    /** Powerlines (only so they can be drawn with their model). */
    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PowerlineBlockEntity>> POWERLINE = BLOCK_ENTITIES.register("powerline",
            () -> BlockEntityType.Builder.of(PowerlineBlockEntity::new, ModBlocks.POWERLINE.get()).build(null));

    /** Generators, transformers, power cell, redstone emitter, satellite dish, tesla coil. */
    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PowerMachineBlockEntity>> POWER_MACHINE = BLOCK_ENTITIES.register("power_machine",
            () -> BlockEntityType.Builder.of(PowerMachineBlockEntity::new, ModBlocks.powerMachines()).build(null));

    /** The Assembler (FE buffer, last item made). */
    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.block.entity.AssemblerBlockEntity>> ASSEMBLER = BLOCK_ENTITIES.register("assembler",
            () -> BlockEntityType.Builder.of(org.avp.block.entity.AssemblerBlockEntity::new, ModBlocks.ASSEMBLER.get()).build(null));

    /** Sentry turrets. */
    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<org.avp.block.entity.TurretBlockEntity>> TURRET = BLOCK_ENTITIES.register("turret",
            () -> BlockEntityType.Builder.of(org.avp.block.entity.TurretBlockEntity::new, ModBlocks.TURRET.get()).build(null));

    private ModBlockEntities()
    {
    }
}
