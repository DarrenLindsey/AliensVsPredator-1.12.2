package org.avp.registry;

import java.util.function.Supplier;

import org.avp.AliensVsPredator;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Creative tabs. In 1.20 tabs are registered like blocks and items, and each
 * tab lists its contents in displayItems(...) instead of every item calling
 * setCreativeTab(...).
 */
public final class ModCreativeTabs
{
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AliensVsPredator.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN = tab(Tab.MAIN);
    public static final RegistryObject<CreativeModeTab> BLOCKS = tab(Tab.BLOCKS);
    public static final RegistryObject<CreativeModeTab> CRAFTING = tab(Tab.CRAFTING);
    public static final RegistryObject<CreativeModeTab> GUN_PARTS = tab(Tab.GUN_PARTS);

    private static RegistryObject<CreativeModeTab> tab(Tab tab)
    {
        return TABS.register(tab.id, () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.avp." + tab.id))
                // icon = first thing in the tab (a barrier if the tab is still empty)
                .icon(() -> tab.contents().isEmpty() ? new ItemStack(Items.BARRIER) : new ItemStack(tab.contents().get(0).get()))
                .displayItems((params, output) -> {
                    for (Supplier<? extends ItemLike> entry : tab.contents())
                    {
                        output.accept(entry.get());
                    }
                })
                .build());
    }

    private ModCreativeTabs()
    {
    }
}
