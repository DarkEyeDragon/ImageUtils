package me.darkeyedragon.imageutils.client.event;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.client.KeyBindings;
import me.darkeyedragon.imageutils.client.ScreenshotHandler;
import me.darkeyedragon.imageutils.client.gui.GuiLocalScreenshots;
import me.darkeyedragon.imageutils.client.gui.GuiPartialScreenshot;
import me.darkeyedragon.imageutils.client.util.OutputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class KeyPressEvent {

    private final ImageUtilsMain main;

    public KeyPressEvent(ImageUtilsMain main) {
        this.main = main;
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.screenshotPartialUploadKey.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiPartialScreenshot(main.getUploaderFactory().getUploader()));
            MouseInfo.getPointerInfo().getLocation();
        } else if (KeyBindings.screenshotViewer.isPressed()) {
            /*ListItem list = new ListItem("Test", "Short Description");
            GuiFilter guiFilter = new GuiFilter();
            guiFilter.addListItem(list);
            guiFilter.addListItem(list);
            guiFilter.addListItem(list);
            guiFilter.addListItem(list);
            guiFilter.addListItem(list);
            Minecraft.getMinecraft().displayGuiScreen(guiFilter);*/
            Minecraft.getMinecraft().displayGuiScreen(new GuiLocalScreenshots(null, main));
        } else if (KeyBindings.screenshotUploadKey.isPressed()) {
            BufferedImage screenshot = ScreenshotHandler.full();
            main.getUploaderFactory().getUploader().uploadAsync(screenshot, (response, error) -> {
                ITextComponent errorComponent = new TextComponentTranslation("imageutil.message.upload.error").appendSibling(new TextComponentTranslation("imageutil.message.upload.error1"));
                if (response == null && error != null) {
                    OutputHandler.sendMessage(errorComponent.appendText(error.getMessage()), null);
                } else if (response != null) {
                    try {
                        OutputHandler.sendUploadResponseMessage(response, null);
                    } catch (IOException e) {
                        OutputHandler.sendMessage(errorComponent.appendText(e.getMessage()), null);
                    }
                }
            });
        }
    }
}