package org.avp.registry;

import org.avp.AliensVsPredator;
import org.avp.block.entity.ModelBlockEntity;
import org.avp.block.entity.PoweredBlockEntity;
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

    /** Lockers and supply crates (64-slot inventory). */
    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StorageBlockEntity>> STORAGE = BLOCK_ENTITIES.register("storage",
            () -> BlockEntityType.Builder.of(StorageBlockEntity::new, ModBlocks.storageBlocks()).build(null));

    /** Lights and powered blocks (FE energy buffer). */
    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PoweredBlockEntity>> POWERED = BLOCK_ENTITIES.register("powered",
            () -> BlockEntityType.Builder.of(PoweredBlockEntity::new, ModBlocks.poweredBlocks()).build(null));

    private ModBlockEntities()
    {
    }
}
