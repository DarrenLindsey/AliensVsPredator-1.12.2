package org.avp.registry;

import java.util.LinkedHashSet;

import org.avp.AliensVsPredator;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** One searchable AVP tab, with the existing categories determining the browsing order. */
public final class ModCreativeTabs
{
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AliensVsPredator.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.avp.main"))
            .icon(() -> Tab.MAIN.contents().isEmpty() ? new ItemStack(Items.BARRIER) : new ItemStack(Tab.MAIN.contents().getFirst().get()))
            .withSearchBar()
            .displayItems((params, output) -> {
                var items = new LinkedHashSet<Item>();
                for (Tab category : Tab.values())
                {
                    category.contents().forEach(entry -> items.add(entry.get().asItem()));
                }
                items.forEach(output::accept);
            })
            .build());

    private ModCreativeTabs()
    {
    }
}
