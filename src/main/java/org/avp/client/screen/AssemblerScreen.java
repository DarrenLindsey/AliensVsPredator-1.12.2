package org.avp.client.screen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.avp.block.entity.AssemblerBlockEntity;
import org.avp.config.AvpConfig;
import org.avp.crafting.AssemblyRecipe;
import org.avp.network.AssemblePayload;
import org.avp.network.AssemblerSettingPayload;
import org.avp.registry.ModRecipes;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * The Assembler screen (1.12.2 GuiAssembler): a searchable list of schematics on the left,
 * the selected one's materials on the right (green = you have enough), and buttons to
 * build 1, 4, 16 or 64. Materials come from your inventory.
 */
public class AssemblerScreen extends Screen
{
    private static final int WIDTH = 280;
    private static final int HEIGHT = 236;
    private static final int ROWS = 9;
    private static final int ROW_HEIGHT = 18;
    private static final int[] AMOUNTS = { 1, 4, 16, 64 };

    private final BlockPos pos;
    private List<RecipeHolder<AssemblyRecipe>> all = List.of();
    private final List<RecipeHolder<AssemblyRecipe>> shown = new ArrayList<>();
    private RecipeHolder<AssemblyRecipe> selected;
    private EditBox search;
    private int scroll;
    private int left;
    private int top;
    private final List<Button> buildButtons = new ArrayList<>();
    private Button lockButton;
    private Button outputButton;

    public AssemblerScreen(BlockPos pos)
    {
        super(Component.translatable("block.avp.assembler"));
        this.pos = pos;
    }

    @Override
    protected void init()
    {
        left = (width - WIDTH) / 2;
        top = (height - HEIGHT) / 2;
        if (minecraft != null && minecraft.level != null)
        {
            all = minecraft.level.getRecipeManager().getAllRecipesFor(ModRecipes.ASSEMBLY.get()).stream()
                    .sorted(Comparator.comparing(h -> h.value().result().getHoverName().getString()))
                    .toList();
        }
        String previous = search == null ? "" : search.getValue();
        search = new EditBox(font, left + 6, top + 18, 120, 14, Component.translatable("screen.avp.assembler.search"));
        search.setHint(Component.translatable("screen.avp.assembler.search"));
        search.setValue(previous);
        search.setResponder(text -> refilter());
        addRenderableWidget(search);
        setInitialFocus(search);

        buildButtons.clear();
        for (int i = 0; i < AMOUNTS.length; i++)
        {
            int amount = AMOUNTS[i];
            Button button = Button.builder(Component.literal("x" + amount), b -> assemble(amount))
                    .bounds(left + 136 + i * 35, top + 174, 33, 20).build();
            buildButtons.add(addRenderableWidget(button));
        }
        // automation (AE2 pattern providers, Create, hoppers)
        lockButton = addRenderableWidget(Button.builder(Component.empty(), b -> setting(AssemblerSettingPayload.LOCK))
                .bounds(left + 6, top + 202, 120, 16).build());
        outputButton = addRenderableWidget(Button.builder(Component.empty(), b -> setting(AssemblerSettingPayload.TOGGLE_AUTO_OUTPUT))
                .bounds(left + 130, top + 202, 84, 16).build());
        addRenderableWidget(Button.builder(Component.translatable("screen.avp.assembler.empty"), b -> setting(AssemblerSettingPayload.EMPTY_BUFFERS))
                .bounds(left + 218, top + 202, 56, 16).build());
        refilter();
    }

    private void setting(int action)
    {
        String recipe = action == AssemblerSettingPayload.LOCK && selected != null ? selected.id().toString() : "";
        PacketDistributor.sendToServer(new AssemblerSettingPayload(pos, action, recipe));
    }

    private AssemblerBlockEntity assembler()
    {
        return minecraft != null && minecraft.level != null && minecraft.level.getBlockEntity(pos) instanceof AssemblerBlockEntity a ? a : null;
    }

    private void refilter()
    {
        String text = search.getValue().toLowerCase(Locale.ROOT).trim();
        shown.clear();
        for (RecipeHolder<AssemblyRecipe> holder : all)
        {
            if (text.isEmpty() || holder.value().result().getHoverName().getString().toLowerCase(Locale.ROOT).contains(text))
            {
                shown.add(holder);
            }
        }
        scroll = 0;
        if (selected == null || !shown.contains(selected))
        {
            selected = shown.isEmpty() ? null : shown.get(0);
        }
    }

    private void assemble(int amount)
    {
        if (selected != null)
        {
            PacketDistributor.sendToServer(new AssemblePayload(pos, selected.id(), amount));
        }
    }

    @Override
    public void tick()
    {
        super.tick();
        if (minecraft != null && minecraft.player != null && minecraft.player.distanceToSqr(pos.getCenter()) > 64)
        {
            onClose();
            return;
        }
        boolean affordable = selected != null && minecraft != null && minecraft.player != null
                && selected.value().canAssemble(minecraft.player.getInventory());
        buildButtons.forEach(b -> b.active = affordable);
        AssemblerBlockEntity assembler = assembler();
        if (assembler != null)
        {
            boolean lockedToThis = selected != null && selected.id().equals(assembler.locked());
            lockButton.setMessage(Component.translatable(lockedToThis ? "screen.avp.assembler.unlock" : "screen.avp.assembler.lock"));
            lockButton.active = lockedToThis || selected != null;
            outputButton.setMessage(Component.translatable(assembler.autoOutput() ? "screen.avp.assembler.output_on" : "screen.avp.assembler.output_off"));
        }
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick)
    {
        super.render(g, mouseX, mouseY, partialTick);
        g.drawString(font, title, left + 6, top + 6, 0xFF55FF55, false);
        drawEnergy(g);

        // ---- schematic list
        int listTop = top + 36;
        g.fill(left + 5, listTop - 1, left + 127, listTop + ROWS * ROW_HEIGHT + 1, 0xFF0A120A);
        for (int row = 0; row < ROWS && scroll + row < shown.size(); row++)
        {
            RecipeHolder<AssemblyRecipe> holder = shown.get(scroll + row);
            int y = listTop + row * ROW_HEIGHT;
            boolean hover = mouseX >= left + 6 && mouseX < left + 126 && mouseY >= y && mouseY < y + ROW_HEIGHT;
            if (holder == selected)
            {
                g.fill(left + 6, y, left + 126, y + ROW_HEIGHT, 0xFF1E4A1E);
            }
            else if (hover)
            {
                g.fill(left + 6, y, left + 126, y + ROW_HEIGHT, 0xFF14301A);
            }
            ItemStack result = holder.value().result();
            g.renderItem(result, left + 7, y + 1);
            String name = font.plainSubstrByWidth(result.getHoverName().getString(), 100);
            g.drawString(font, name, left + 25, y + 5, 0xFFB8FFB8, false);
        }
        if (shown.size() > ROWS)
        {
            // scroll bar
            int barHeight = Math.max(10, ROWS * ROW_HEIGHT * ROWS / shown.size());
            int barY = listTop + (ROWS * ROW_HEIGHT - barHeight) * scroll / Math.max(1, shown.size() - ROWS);
            g.fill(left + 128, barY, left + 131, barY + barHeight, 0xFF3C8A3C);
        }

        // ---- selected schematic
        if (selected != null && minecraft != null && minecraft.player != null)
        {
            AssemblyRecipe recipe = selected.value();
            int x = left + 136;
            g.renderItem(recipe.result(), x, top + 18);
            g.renderItemDecorations(font, recipe.result(), x, top + 18);
            g.drawString(font, font.plainSubstrByWidth(recipe.result().getHoverName().getString(), WIDTH - 162), x + 20, top + 22, 0xFFFFFFFF, false);
            int[] have = recipe.have(minecraft.player.getInventory());
            long time = minecraft.level == null ? 0 : minecraft.level.getGameTime();
            for (int i = 0; i < recipe.ingredients().size(); i++)
            {
                SizedIngredient need = recipe.ingredients().get(i);
                ItemStack[] options = need.ingredient().getItems();
                ItemStack shownItem = options.length == 0 ? ItemStack.EMPTY : options[(int) (time / 20 % options.length)];
                int y = top + 40 + i * 11;
                g.pose().pushPose();
                g.pose().translate(x, y, 0);
                g.pose().scale(0.625F, 0.625F, 1F);
                g.renderItem(shownItem, 0, 0);
                g.pose().popPose();
                int color = have[i] >= need.count() ? 0xFF55FF55 : have[i] > 0 ? 0xFFFFAA00 : 0xFF888888;
                String amount = have[i] + "/" + need.count();
                g.drawString(font, amount, x + 13, y + 1, color, false);
                g.drawString(font, font.plainSubstrByWidth(shownItem.getHoverName().getString(), WIDTH - 192),
                        x + 46, y + 1, 0xFFAAAAAA, false);
                if (mouseX >= x && mouseX < WIDTH + left && mouseY >= y && mouseY < y + 11 && !shownItem.isEmpty())
                {
                    g.renderTooltip(font, shownItem, mouseX, mouseY);
                }
            }
        }
        else if (shown.isEmpty())
        {
            g.drawString(font, Component.translatable("screen.avp.assembler.none"), left + 136, top + 22, 0xFF888888, false);
        }

        // automation status line
        AssemblerBlockEntity assembler = assembler();
        if (assembler != null)
        {
            g.fill(left + 4, top + 198, left + WIDTH - 4, top + 199, 0xFF1E4A1E);
            Component lockedTo = assembler.lockedRecipe().map(h -> (Component) h.value().result().getHoverName())
                    .orElse(Component.translatable("screen.avp.assembler.any"));
            Component line = Component.translatable("screen.avp.assembler.automation", lockedTo, assembler.bufferedInput(), assembler.bufferedOutput());
            int room = WIDTH - 12;
            if (!assembler.status().isEmpty())
            {
                Component status = Component.translatable("screen.avp.assembler.status." + assembler.status());
                g.drawString(font, status, left + WIDTH - 6 - font.width(status), top + 222, 0xFFFFAA00, false);
                room -= font.width(status) + 8;
            }
            g.drawString(font, font.plainSubstrByWidth(line.getString(), room), left + 6, top + 222, 0xFF6FBF6F, false);
        }

        // tooltip for the list
        int row = (mouseY - listTop) / ROW_HEIGHT;
        if (mouseX >= left + 6 && mouseX < left + 126 && mouseY >= listTop && row < ROWS && scroll + row < shown.size())
        {
            g.renderTooltip(font, shown.get(scroll + row).value().result(), mouseX, mouseY);
        }
    }

    private void drawEnergy(GuiGraphics g)
    {
        if (minecraft == null || minecraft.level == null || !(minecraft.level.getBlockEntity(pos) instanceof AssemblerBlockEntity assembler))
        {
            return;
        }
        int cost = AvpConfig.server(AvpConfig.SERVER.assemblerEnergyPerItem);
        if (cost <= 0 || !AvpConfig.server(AvpConfig.SERVER.machinesNeedPower))
        {
            return;
        }
        int stored = assembler.getEnergy().getEnergyStored();
        int barX = left + 136, barY = top + 14, barW = WIDTH - 142;
        g.fill(barX, barY, barX + barW, barY + 2, 0xFF202020);
        g.fill(barX, barY, barX + barW * stored / AssemblerBlockEntity.CAPACITY, barY + 2, stored >= cost ? 0xFF33AAFF : 0xFFFF3333);
        Component label = Component.translatable("screen.avp.assembler.energy", String.format("%,d", stored), cost);
        g.drawString(font, label, left + WIDTH - 6 - font.width(label), top + 5, 0xFF6699BB, false);
    }

    @Override
    public void renderBackground(GuiGraphics g, int mouseX, int mouseY, float partialTick)
    {
        super.renderBackground(g, mouseX, mouseY, partialTick);
        // a dark green "terminal" panel
        g.fill(left - 1, top - 1, left + WIDTH + 1, top + HEIGHT + 1, 0xFF3C8A3C);
        g.fill(left, top, left + WIDTH, top + HEIGHT, 0xF0061006);
        g.fill(left + 132, top + 16, left + 133, top + HEIGHT - 4, 0xFF1E4A1E);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button)
    {
        int listTop = top + 36;
        if (mouseX >= left + 6 && mouseX < left + 126 && mouseY >= listTop && mouseY < listTop + ROWS * ROW_HEIGHT)
        {
            int index = scroll + (int) ((mouseY - listTop) / ROW_HEIGHT);
            if (index < shown.size())
            {
                selected = shown.get(index);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY)
    {
        scroll = Math.max(0, Math.min(Math.max(0, shown.size() - ROWS), scroll - (int) Math.signum(scrollY)));
        return true;
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
