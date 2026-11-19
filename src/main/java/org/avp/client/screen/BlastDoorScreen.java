package org.avp.client.screen;

import org.avp.network.BlastDoorActionPayload;
import org.avp.network.OpenBlastDoorScreenPayload;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * The blast door terminal, in the green-on-black look of the 1.12.2 GuiBlastdoor.
 *
 * Unlock mode: type the password and press Enter.
 * Admin mode (opened with a Security Tuner): set or remove the password (Enter),
 * toggle Auto-Lock, and bind the tuner to this door.
 */
public class BlastDoorScreen extends Screen
{
    private static final int WIDTH = 200;
    private static final int GREEN = 0xFF00AA00;

    private final OpenBlastDoorScreenPayload door;
    private boolean autolock;
    private boolean autoOpen;
    private boolean threatDetection;
    private EditBox passwordBox;

    public BlastDoorScreen(OpenBlastDoorScreenPayload door)
    {
        super(Component.translatable("screen.avp.blastdoor.title", door.identifier()));
        this.door = door;
        this.autolock = door.autolock();
        this.autoOpen = door.autoOpen();
        this.threatDetection = door.threatDetection();
    }

    private int left()
    {
        return (width - WIDTH) / 2;
    }

    private int top()
    {
        return height / 2 - 50;
    }

    @Override
    protected void init()
    {
        passwordBox = new EditBox(font, left(), top() + 16, WIDTH, 18, Component.translatable("screen.avp.blastdoor.password"));
        passwordBox.setMaxLength(BlastDoorActionPayload.MAX_PASSWORD_LENGTH);
        passwordBox.setTextColor(GREEN);
        if (!door.admin())
        {
            // show stars instead of the password while typing
            passwordBox.setFormatter((text, start) -> FormattedCharSequence.forward("*".repeat(text.length()), Style.EMPTY));
        }
        passwordBox.setHint(Component.translatable(door.admin() ? "screen.avp.blastdoor.new_password_hint" : "screen.avp.blastdoor.password_hint"));
        addRenderableWidget(passwordBox);
        setInitialFocus(passwordBox);

        if (door.admin())
        {
            addRenderableWidget(Button.builder(autolockLabel(), b ->
            {
                send(BlastDoorActionPayload.TOGGLE_AUTOLOCK, "");
                autolock = !autolock;
                b.setMessage(autolockLabel());
            }).bounds(left(), top() + 40, WIDTH, 20).build());
            addRenderableWidget(Button.builder(sensorLabel("auto_open", autoOpen), b ->
            {
                send(BlastDoorActionPayload.TOGGLE_AUTO_OPEN, "");
                autoOpen = !autoOpen;
                b.setMessage(sensorLabel("auto_open", autoOpen));
            }).bounds(left(), top() + 62, WIDTH / 2 - 1, 20).build());
            addRenderableWidget(Button.builder(sensorLabel("threat", threatDetection), b ->
            {
                send(BlastDoorActionPayload.TOGGLE_THREAT_DETECTION, "");
                threatDetection = !threatDetection;
                b.setMessage(sensorLabel("threat", threatDetection));
            }).bounds(left() + WIDTH / 2 + 1, top() + 62, WIDTH / 2 - 1, 20).build());
            addRenderableWidget(Button.builder(Component.translatable("screen.avp.blastdoor.bind"),
                    b -> send(BlastDoorActionPayload.BIND_TUNER, "")).bounds(left(), top() + 84, WIDTH, 20).build());
        }
    }

    /** e.g. "Auto-Open: ON" */
    private static Component sensorLabel(String sensor, boolean on)
    {
        return Component.translatable("screen.avp.blastdoor." + sensor, Component.translatable(on ? "options.on" : "options.off"));
    }

    private Component autolockLabel()
    {
        return Component.translatable(autolock ? "screen.avp.blastdoor.autolock_disable" : "screen.avp.blastdoor.autolock_enable");
    }

    private void send(int action, String text)
    {
        PacketDistributor.sendToServer(new BlastDoorActionPayload(door.pos(), action, text));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (keyCode == InputConstants.KEY_RETURN || keyCode == InputConstants.KEY_NUMPADENTER)
        {
            if (door.admin())
            {
                send(BlastDoorActionPayload.SET_PASSWORD, passwordBox.getValue());
            }
            else if (!passwordBox.getValue().isEmpty())
            {
                send(BlastDoorActionPayload.TRY_PASSWORD, passwordBox.getValue());
            }
            onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    /** The dark terminal panel, drawn behind the text box and buttons. */
    @Override
    public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        super.renderBackground(graphics, mouseX, mouseY, partialTick);
        int height = door.admin() ? 122 : 40;
        graphics.fill(left() - 4, top() - 4, left() + WIDTH + 4, top() - 4 + height, 0xCC000000);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
    {
        super.render(graphics, mouseX, mouseY, partialTick); // background, panel, text box, buttons
        graphics.drawString(font, door.identifier(), left(), top(), 0xFFFFFFFF);
        Component status = Component.translatable(door.admin() ? "screen.avp.blastdoor.admin"
                : door.hasPassword() ? "screen.avp.blastdoor.locked" : "screen.avp.blastdoor.unlocked");
        graphics.drawString(font, status, left() + WIDTH - font.width(status), top(), door.admin() ? GREEN : 0xFFFF5555);
        if (door.admin())
        {
            graphics.drawString(font, Component.translatable("screen.avp.blastdoor.admin_help"), left(), top() + 108, 0xFFAAAAAA);
        }
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
