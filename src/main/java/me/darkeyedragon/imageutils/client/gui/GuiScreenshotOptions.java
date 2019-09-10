package me.darkeyedragon.imageutils.client.gui;

import me.darkeyedragon.imageutils.client.utils.ImageResource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiScreenshotOptions extends GuiConfirmAction {

    final GuiScreen parent;
    private GuiTextField imageName;
    private String title;
    private ImageResource imageResource;

    GuiScreenshotOptions(GuiYesNoCallback parentScreenIn, String title, String confirm, String cancel, int parentButtonClickedIdIn, GuiScreen parent) {
        super(parentScreenIn, title, "", "", confirm, cancel, parentButtonClickedIdIn, parent);
        this.title = title;
        this.parent = parent;
        GuiLocalScreenshots guiLocalScreenshots = (GuiLocalScreenshots) parent;
        imageResource = guiLocalScreenshots.getImageResource();
    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        labelList.add(new GuiLabel(mc.fontRenderer, width / 2 - 100, 66, 200, 20, 20, 20));
        imageName = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, height / 2 - 20, 200, 17);
        imageName.setText(imageResource.getName());
        buttonList.get(0).enabled = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GuiButton save = buttonList.get(0);
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawString(this.fontRenderer, I18n.format("imageutil.gui.screenshot_options.image_name"), this.width / 2 - 100, height / 2 - 32, 10526880);
        drawCenteredString(mc.fontRenderer, title, width / 2, height / 2 - 60, 0xffffff);
        if (imageName.getText().equalsIgnoreCase(imageResource.getName())) {
            save.enabled = false;
        } else {
            if (!save.enabled) {
                save.enabled = true;
            }
        }
        this.imageName.drawTextBox();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if (button.id == 0) {
            if (imageName.getText().equalsIgnoreCase("") || imageName.getText().length() > 255 || imageName.getText().equalsIgnoreCase(imageResource.getName())) {
                return;
            }
            imageResource.setName(imageName.getText());
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.imageName.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.imageName.textboxKeyTyped(typedChar, keyCode);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        mc.displayGuiScreen(parent);
        mc.displayGuiScreen(this);
    }

    public void updateScreen() {
        this.imageName.updateCursorCounter();
    }
}
