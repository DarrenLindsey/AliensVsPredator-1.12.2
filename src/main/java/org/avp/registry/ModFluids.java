package org.avp.registry;

import org.avp.AliensVsPredator;
import org.avp.fluid.BlackGooBlock;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/** Fluids: black goo (the Engineers' pathogen, Prometheus). */
public final class ModFluids
{
    public static final DeferredRegister<FluidType> TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, AliensVsPredator.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, AliensVsPredator.MOD_ID);

    public static final DeferredHolder<FluidType, FluidType> BLACK_GOO_TYPE = TYPES.register("blackgoo", () -> new FluidType(FluidType.Properties.create()
            .descriptionId("block.avp.blackgoo").density(3000).viscosity(6000).canSwim(false).canDrown(true).supportsBoating(false)
            .canExtinguish(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));

    public static final DeferredHolder<Fluid, FlowingFluid> BLACK_GOO = FLUIDS.register("blackgoo", () -> new BaseFlowingFluid.Source(props()));
    public static final DeferredHolder<Fluid, FlowingFluid> BLACK_GOO_FLOWING = FLUIDS.register("blackgoo_flowing", () -> new BaseFlowingFluid.Flowing(props()));

    public static final DeferredBlock<BlackGooBlock> BLACK_GOO_BLOCK = ModBlocks.BLOCKS.register("blackgoo", () -> new BlackGooBlock(BLACK_GOO.get(),
            BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).replaceable().noCollission().strength(100F).pushReaction(PushReaction.DESTROY)
                    .noLootTable().liquid()));
    public static final DeferredItem<Item> BLACK_GOO_BUCKET = ModItems.ITEMS.register("bucket_blackgoo",
            () -> new BucketItem(BLACK_GOO.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    static
    {
        Tab.MAIN.add(BLACK_GOO_BUCKET);
    }

    private static BaseFlowingFluid.Properties props()
    {
        return new BaseFlowingFluid.Properties(BLACK_GOO_TYPE, BLACK_GOO, BLACK_GOO_FLOWING).bucket(BLACK_GOO_BUCKET).block(BLACK_GOO_BLOCK)
                .slopeFindDistance(2).levelDecreasePerBlock(2).tickRate(30);
    }

    public static void load()
    {
    }

    private ModFluids()
    {
    }
}
