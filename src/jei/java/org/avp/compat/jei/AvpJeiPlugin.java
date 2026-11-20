package org.avp.compat.jei;

import java.util.EnumMap;
import java.util.Map;

import org.avp.AliensVsPredator;
import org.avp.refining.Refining;
import org.avp.refining.RefineryKind;
import org.avp.refining.RefiningRecipes;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/** JEI pages for the Weyland-Yutani refining line (built only with -Pwith_jei=true / with_jei=true). */
@JeiPlugin
public class AvpJeiPlugin implements IModPlugin
{
    private static final Map<RefineryKind, RecipeType<RefiningRecipes.Entry>> TYPES = new EnumMap<>(RefineryKind.class);
    static
    {
        for (RefineryKind kind : RefineryKind.values())
        {
            TYPES.put(kind, RecipeType.create(AliensVsPredator.MOD_ID, kind.id, RefiningRecipes.Entry.class));
        }
    }

    @Override
    public ResourceLocation getPluginUid()
    {
        return ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration)
    {
        IGuiHelper gui = registration.getJeiHelpers().getGuiHelper();
        for (RefineryKind kind : RefineryKind.values())
        {
            registration.addRecipeCategories(new Category(kind, gui));
        }
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration)
    {
        for (RefineryKind kind : RefineryKind.values())
        {
            registration.addRecipes(TYPES.get(kind), RefiningRecipes.of(kind));
        }
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        for (RefineryKind kind : RefineryKind.values())
        {
            registration.addRecipeCatalyst(new ItemStack(Refining.BLOCKS.get(kind).get()), TYPES.get(kind));
        }
    }

    private static final class Category implements IRecipeCategory<RefiningRecipes.Entry>
    {
        private final RefineryKind kind;
        private final IDrawable background, icon;

        Category(RefineryKind kind, IGuiHelper gui)
        {
            this.kind = kind;
            this.background = gui.createBlankDrawable(130, 34);
            this.icon = gui.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Refining.BLOCKS.get(kind).get()));
        }

        @Override
        public RecipeType<RefiningRecipes.Entry> getRecipeType()
        {
            return TYPES.get(kind);
        }

        @Override
        public Component getTitle()
        {
            return Component.translatable("block.avp." + kind.id);
        }

        @Override
        @SuppressWarnings("removal")
        public IDrawable getBackground()
        {
            return background;
        }

        public int getWidth()
        {
            return 130;
        }

        public int getHeight()
        {
            return 34;
        }

        @Override
        public IDrawable getIcon()
        {
            return icon;
        }

        @Override
        public void setRecipe(IRecipeLayoutBuilder builder, RefiningRecipes.Entry recipe, IFocusGroup focuses)
        {
            builder.addSlot(RecipeIngredientRole.INPUT, 8, 4).addIngredients(recipe.input());
            builder.addSlot(RecipeIngredientRole.OUTPUT, 70, 4).addItemStack(recipe.output());
        }

        @Override
        public void draw(RefiningRecipes.Entry recipe, IRecipeSlotsView slots, GuiGraphics g, double mouseX, double mouseY)
        {
            var font = Minecraft.getInstance().font;
            g.drawString(font, "->", 40, 8, 0xFF404040, false);
            String need = kind.power + " FE/t" + switch (kind.fluid)
            {
                case WATER -> " + water";
                case ACID -> " + acid";
                default -> "";
            };
            g.drawString(font, need, 8, 24, 0xFF606060, false);
            g.drawString(font, "x" + kind.multiplier, 100, 8, 0xFF2A6A20, false);
        }
    }
}
