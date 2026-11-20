package org.avp.entity.xenomorph.lab;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.avp.AliensVsPredator;
import org.avp.registry.ModBlocks;
import org.avp.registry.ModItems;
import org.avp.registry.Tab;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

/** The Weyland-Yutani genetics programme: items and blocks, and reading/writing what's in a sample or template. */
public final class XenoLab
{
    public static final DeferredItem<Item> SYRINGE = ModItems.ITEMS.register("bio_syringe", () -> new SyringeItem(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> SAMPLE = ModItems.ITEMS.register("xeno_dna_sample", () -> new DataItem(new Item.Properties().stacksTo(16), false));
    public static final DeferredItem<Item> TEMPLATE = ModItems.ITEMS.register("xeno_embryo_template", () -> new DataItem(new Item.Properties().stacksTo(1), true));
    public static final Map<LabKind, DeferredBlock<LabBlock>> BLOCKS = new EnumMap<>(LabKind.class);

    static
    {
        for (LabKind k : LabKind.values())
        {
            BLOCKS.put(k, ModBlocks.register(k.id, Tab.BLOCKS, () -> new LabBlock(k, BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                    .sound(SoundType.METAL).strength(4F, 10F).requiresCorrectToolForDrops().noOcclusion()
                    .lightLevel(s -> s.getValue(LabBlock.LIT) ? 8 : 2))));
        }
        Tab.MAIN.add(SYRINGE);
        Tab.MAIN.add(SAMPLE);
        Tab.MAIN.add(TEMPLATE);
    }

    private XenoLab()
    {
    }

    public static void load()
    {
    }

    public static ItemStack sample(EntityType<?> type)
    {
        ItemStack s = new ItemStack(SAMPLE.get());
        CompoundTag tag = new CompoundTag();
        tag.putString("Species", BuiltInRegistries.ENTITY_TYPE.getKey(type).toString());
        s.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        return s;
    }

    public static CompoundTag data(ItemStack stack)
    {
        CustomData d = stack.get(DataComponents.CUSTOM_DATA);
        return d == null ? new CompoundTag() : d.copyTag();
    }

    @Nullable
    public static EntityType<?> species(ItemStack stack)
    {
        ResourceLocation id = ResourceLocation.tryParse(data(stack).getString("Species"));
        return id == null ? null : BuiltInRegistries.ENTITY_TYPE.getOptional(id).orElse(null);
    }

    public static List<XenoTrait> traits(ItemStack stack)
    {
        List<XenoTrait> out = new ArrayList<>();
        for (String s : data(stack).getString("Traits").split(","))
        {
            XenoTrait t = XenoTrait.byId(s.trim());
            if (t != null)
            {
                out.add(t);
            }
        }
        return out;
    }

    public static boolean isQueen(@Nullable EntityType<?> type)
    {
        if (type == null)
        {
            return false;
        }
        String id = BuiltInRegistries.ENTITY_TYPE.getKey(type).getPath();
        return id.contains("matriarch") || id.contains("queen");
    }

    /** Samples and templates show what they hold. */
    public static class DataItem extends Item
    {
        private final boolean template;

        public DataItem(Properties properties, boolean template)
        {
            super(properties);
            this.template = template;
        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
        {
            EntityType<?> type = species(stack);
            tooltip.add(type == null ? Component.literal("Blank").withStyle(ChatFormatting.DARK_GRAY)
                    : Component.literal("Species: ").withStyle(ChatFormatting.GRAY).append(type.getDescription().copy().withStyle(ChatFormatting.DARK_GREEN)));
            if (template)
            {
                for (XenoTrait t : traits(stack))
                {
                    tooltip.add(Component.literal(" + " + t.description).withStyle(ChatFormatting.AQUA));
                }
                int instability = data(stack).getInt("Instability");
                tooltip.add(Component.literal("Instability: " + instability + (instability >= 3 ? " (dangerous)" : "")).withStyle(instability >= 3
                        ? ChatFormatting.RED : instability > 0 ? ChatFormatting.GOLD : ChatFormatting.GREEN));
            }
        }

        @Override
        public boolean isFoil(ItemStack stack)
        {
            return template && !traits(stack).isEmpty();
        }
    }

    static ResourceLocation id(String path)
    {
        return ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, path);
    }
}
