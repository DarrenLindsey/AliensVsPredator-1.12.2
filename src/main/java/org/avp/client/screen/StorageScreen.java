package org.avp.client.screen;

import org.avp.AliensVsPredator;
import org.avp.menu.StorageMenu;
import org.avp.registry.ModBlocks;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/** The locker / supply crate inventory screen. The title is part of the background image. */
public class StorageScreen extends AbstractContainerScreen<StorageMenu>
{
    private static final ResourceLocation LOCKER = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/gui/locker.png");
    private static final ResourceLocation CRATE = ResourceLocation.fromNamespaceAndPath(AliensVsPredator.MOD_ID, "textures/gui/supplycrate.png");

    private final ResourceLocation background;

    public StorageScreen(StorageMenu menu, Inventory playerInventory, Component title)
    {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 266;
        // the menu title is the block name; crates use the "SUPPLY CRATE" background
        boolean crate = title.getString().equals(ModBlocks.SUPPLYCRATE.get().getName().getString())
                || title.getString().equals(ModBlocks.SUPPLYCRATE_MARINES.get().getName().getString())
                || title.getString().equals(ModBlocks.SUPPLYCRATE_SEEGSON.get().getName().getString());
        this.background = crate ? CRATE : LOCKER;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY)
    {
        // the image is 256x512 (the menu is taller than 256 pixels)
        graphics.blit(background, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 512);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY)
    {
        // no text: the title is drawn into the background image, like in 1.12.2
    }
}
