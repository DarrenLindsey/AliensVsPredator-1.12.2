package org.avp.refining;

import java.util.EnumMap;
import java.util.Map;

import org.avp.registry.ModBlocks;
import org.avp.registry.ModItems;
import org.avp.registry.Tab;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

/** Registers the refining line and works out what each machine makes from what. */
public final class Refining
{
    public static final Map<RefineMaterial, Map<RefineStage, DeferredItem<Item>>> ITEMS = new EnumMap<>(RefineMaterial.class);
    public static final Map<RefineryKind, DeferredBlock<RefineryBlock>> BLOCKS = new EnumMap<>(RefineryKind.class);
    public static final DeferredItem<Item> ACID_VIAL = ModItems.ITEMS.register("xeno_acid_vial", () -> new Item(new Item.Properties().stacksTo(16)));

    static
    {
        for (RefineMaterial m : RefineMaterial.values())
        {
            Map<RefineStage, DeferredItem<Item>> stages = new EnumMap<>(RefineStage.class);
            for (RefineStage s : RefineStage.values())
            {
                DeferredItem<Item> item = ModItems.ITEMS.register(s.itemId(m), () -> new Item(new Item.Properties()));
                stages.put(s, item);
                Tab.CRAFTING.add(item);
            }
            ITEMS.put(m, stages);
        }
        for (RefineryKind k : RefineryKind.values())
        {
            BLOCKS.put(k, ModBlocks.register(k.id, Tab.BLOCKS, () -> new RefineryBlock(k, BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .sound(SoundType.METAL).strength(4.0F, 10.0F).requiresCorrectToolForDrops()
                    .lightLevel(s -> s.getValue(RefineryBlock.LIT) ? 6 : 0))));
        }
        Tab.MAIN.add(ACID_VIAL);
    }

    private Refining()
    {
    }

    public static void load()
    {
        // class loading registers everything above
    }

    public static Item item(RefineMaterial m, RefineStage s)
    {
        return ITEMS.get(m).get(s).get();
    }

    /** What a machine makes from an input (EMPTY if nothing). */
    public static ItemStack result(RefineryKind kind, ItemStack input)
    {
        if (input.isEmpty())
        {
            return ItemStack.EMPTY;
        }
        RefineMaterial ore = RefineMaterial.ofOre(input);
        if (ore != null)
        {
            return new ItemStack(item(ore, kind.makes), kind.multiplier);
        }
        RefineStage accepts = kind.accepts();
        if (accepts != null)
        {
            for (RefineMaterial m : RefineMaterial.values())
            {
                if (input.is(item(m, accepts)))
                {
                    return new ItemStack(item(m, RefineStage.values()[accepts.ordinal() - 1]), 1);
                }
            }
        }
        if (kind == RefineryKind.CRUSHER)
        {
            // the crusher also does stone: cobble -> gravel -> sand, and bones -> bonemeal
            if (input.is(Items.COBBLESTONE) || input.is(Items.STONE) || input.is(Items.COBBLED_DEEPSLATE))
            {
                return new ItemStack(Items.GRAVEL);
            }
            if (input.is(Items.GRAVEL))
            {
                return new ItemStack(Items.SAND);
            }
            if (input.is(Items.BONE))
            {
                return new ItemStack(Items.BONE_MEAL, 5);
            }
            if (input.is(Blocks.GLOWSTONE.asItem()))
            {
                return new ItemStack(Items.GLOWSTONE_DUST, 4);
            }
        }
        return ItemStack.EMPTY;
    }

    /** How much of its fluid an item is worth (water buckets, acid vials). */
    public static int fluidValue(RefineryKind kind, ItemStack stack)
    {
        return switch (kind.fluid)
        {
            case WATER -> stack.is(Items.WATER_BUCKET) ? 1000 : 0;
            case ACID -> stack.is(ACID_VIAL.get()) ? 1000 : 0;
            default -> 0;
        };
    }
}
