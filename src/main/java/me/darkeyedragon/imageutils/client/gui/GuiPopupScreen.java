package me.darkeyedragon.imageutils.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public abstract class GuiPopupScreen extends GuiScreen {

    final GuiScreen parent;
    private final String messageLine1;
    private final String messageLine2;
    private String buttonConfirm = "Yes";
    private String buttonCancel = "Cancel";

    GuiPopupScreen(GuiScreen parent, String messageLine1, String messageLine2) {
        this.parent = parent;
        this.messageLine1 = messageLine1;
        this.messageLine2 = messageLine2;
    }

    GuiPopupScreen(GuiScreen parent, String messageLine1, String messageLine2, String buttonConfirm, String buttonCancel) {
        this.parent = parent;
        this.messageLine1 = messageLine1;
        this.messageLine2 = messageLine2;
        this.buttonConfirm = buttonConfirm;
        this.buttonCancel = buttonCancel;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, height / 2 + 27, 80, 20, buttonConfirm));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 20, height / 2 + 27, 80, 20, buttonCancel));

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color(1f, 1f, 1f, 1f);
        drawRect(width / 2 - 150, height / 2 - 70, width / 2 + 150, height / 2 + 70, 0xff000000);
        drawHorizontalLine(width / 2 - 150, width / 2 + 149, height / 2 - 70, 0x90ffffff);
        drawHorizontalLine(width / 2 - 149, width / 2 + 149, height / 2 + 69, 0x90ffffff);
        drawVerticalLine(width / 2 - 150, height / 2 - 70, height / 2 + 70, 0x90ffffff);
        drawVerticalLine(width / 2 + 149, height / 2 - 70, height / 2 + 69, 0x90ffffff);
        if (!messageLine1.equalsIgnoreCase("")) {
            drawCenteredString(mc.fontRenderer, messageLine1, width / 2, height / 4 + 45, 0xffffff);
        }
        if (!messageLine2.equalsIgnoreCase("")) {
            drawCenteredString(mc.fontRenderer, messageLine2, width / 2, height / 4 + 70, 0xffffff);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
