package org.avp.registry;

import org.avp.AliensVsPredator;
import org.avp.menu.StorageMenu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** Menu types (the 1.12.2 GuiHandler ids became registered MenuTypes). */
public final class ModMenus
{
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, AliensVsPredator.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<StorageMenu>> STORAGE = MENUS.register("storage",
            () -> IMenuTypeExtension.create(StorageMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<org.avp.menu.TurretMenu>> TURRET = MENUS.register("turret",
            () -> IMenuTypeExtension.create(org.avp.menu.TurretMenu::new));

    private ModMenus()
    {
    }
}
