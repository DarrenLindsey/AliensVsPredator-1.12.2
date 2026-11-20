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
