package me.darkeyedragon.imageutils.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiYesNoCallback;

import java.io.IOException;

public class GuiWebhook extends GuiConfirmAction {


    private static GuiTextField textField;

    GuiWebhook(GuiYesNoCallback parentScreenIn, String title, int parentButtonClickedIdIn, GuiScreen parent) {
        super(parentScreenIn, title, "", "", "Confirm", "Cancel", parentButtonClickedIdIn, parent);
    }

    public static String getDescription() {
        return textField.getText();
    }

    @Override
    public void initGui() {
        super.initGui();
        textField = new GuiTextField(1, Minecraft.getMinecraft().fontRenderer, width / 2 - 100, height / 2 - 20, 200, 20);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        textField.drawTextBox();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        textField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        mc.displayGuiScreen(parent);
        mc.displayGuiScreen(this);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        textField.textboxKeyTyped(typedChar, keyCode);
    }

    public void updateScreen() {
        textField.updateCursorCounter();
    }
}
