package org.avp.client.screen;

import java.util.ArrayList;
import java.util.List;

import org.avp.block.entity.TurretBlockEntity;
import org.avp.config.AvpConfig;
import org.avp.menu.TurretMenu;
import org.avp.network.TurretSettingPayload;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Sentry turret screen: ammo bay, upgrade slots, status read-out and the IFF target buttons.
 * Drawn with flat colours in the same green "W-Y terminal" style as the Assembler.
 */
public class TurretScreen extends AbstractContainerScreen<TurretMenu>
{
    private final List<Button> targetButtons = new ArrayList<>();

    public TurretScreen(TurretMenu menu, Inventory inventory, Component title)
    {
        super(menu, inventory, title);
        imageWidth = 256;
        imageHeight = 206;
        inventoryLabelY = TurretMenu.INVENTORY_Y - 11;
    }

    private TurretBlockEntity turret()
    {
        return minecraft != null && minecraft.level != null && minecraft.level.getBlockEntity(menu.pos()) instanceof TurretBlockEntity turret ? turret : null;
    }

    @Override
    protected void init()
    {
        super.init();
        targetButtons.clear();
        for (int i = 0; i < TurretBlockEntity.CATEGORIES.length; i++)
        {
            int category = i;
            Button button = Button.builder(Component.empty(), b -> PacketDistributor.sendToServer(new TurretSettingPayload(menu.pos(), category)))
                    .bounds(leftPos + 176, topPos + 28 + i * 19, 74, 17).build();
            targetButtons.add(addRenderableWidget(button));
        }
    }

    @Override
    protected void containerTick()
    {
        super.containerTick();
        TurretBlockEntity turret = turret();
        for (int i = 0; i < targetButtons.size(); i++)
        {
            boolean on = turret != null && turret.targets(i);
            targetButtons.get(i).setMessage(Component.translatable("screen.avp.turret.target." + TurretBlockEntity.CATEGORIES[i])
                    .append(on ? Component.literal(": ON").withStyle(net.minecraft.ChatFormatting.GREEN)
                            : Component.literal(": OFF").withStyle(net.minecraft.ChatFormatting.DARK_GRAY)));
        }
    }

    @Override
    protected void renderBg(GuiGraphics g, float partialTick, int mouseX, int mouseY)
    {
        g.fill(leftPos - 1, topPos - 1, leftPos + imageWidth + 1, topPos + imageHeight + 1, 0xFF3C8A3C);
        g.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xF0061006);
        g.fill(leftPos + 4, TurretMenu.INVENTORY_Y + topPos - 14, leftPos + imageWidth - 4, TurretMenu.INVENTORY_Y + topPos - 13, 0xFF1E4A1E);
        for (Slot slot : menu.slots)
        {
            g.fill(leftPos + slot.x - 1, topPos + slot.y - 1, leftPos + slot.x + 17, topPos + slot.y + 17, 0xFF1E4A1E);
            g.fill(leftPos + slot.x, topPos + slot.y, leftPos + slot.x + 16, topPos + slot.y + 16, 0xFF0A1A0C);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mouseX, int mouseY)
    {
        g.drawString(font, title, 8, 6, 0xFF55FF55, false);
        g.drawString(font, Component.translatable("screen.avp.turret.ammo"), 8, TurretMenu.AMMO_Y - 10, 0xFF6FBF6F, false);
        g.drawString(font, Component.translatable("screen.avp.turret.upgrades"), 8, TurretMenu.UPGRADE_Y - 10, 0xFF6FBF6F, false);
        g.drawString(font, Component.translatable("screen.avp.turret.iff"), 176, 18, 0xFF6FBF6F, false);
        g.drawString(font, playerInventoryTitle, 8, inventoryLabelY, 0xFF6FBF6F, false);

        TurretBlockEntity turret = turret();
        if (turret == null)
        {
            return;
        }
        // status read-out next to the upgrade slots
        int x = 66, y = TurretMenu.UPGRADE_Y - 10;
        boolean powered = turret.getBlockState().getValue(org.avp.block.TurretBlock.LIT);
        g.drawString(font, Component.translatable(powered ? "screen.avp.turret.online" : "screen.avp.turret.offline"),
                x, y, powered ? 0xFF55FF55 : 0xFFFF5555, false);
        g.drawString(font, Component.translatable("screen.avp.turret.rounds", turret.rounds(), turret.totalRounds()), x, y + 10, 0xFFB8FFB8, false);
        g.drawString(font, Component.translatable("screen.avp.turret.range", turret.range()), x, y + 20, 0xFFB8FFB8, false);
        g.drawString(font, Component.translatable("screen.avp.turret.loaded",
                Component.translatable("screen.avp.turret.ammo_type." + turret.loaded().key())), x, y + 40, turret.loaded().tracerColor, false);
        Entity target = turret.targetId() >= 0 && minecraft != null && minecraft.level != null ? minecraft.level.getEntity(turret.targetId()) : null;
        g.drawString(font, target == null ? Component.translatable("screen.avp.turret.scanning")
                : Component.translatable("screen.avp.turret.tracking", target.getDisplayName()), x, y + 30, target == null ? 0xFF6FBF6F : 0xFFFF7755, false);
        if (AvpConfig.server(AvpConfig.SERVER.machinesNeedPower))
        {
            g.drawString(font, Component.translatable("screen.avp.turret.energy", String.format("%,d", turret.getEnergy().getEnergyStored())),
                    176, 126, 0xFF6699BB, false);
        }
        if (!turret.ownerName().isEmpty())
        {
            g.drawString(font, Component.translatable("screen.avp.turret.owner", turret.ownerName()), 176, 138, 0xFF6FBF6F, false);
        }
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick)
    {
        super.render(g, mouseX, mouseY, partialTick);
        renderTooltip(g, mouseX, mouseY);
    }
}
