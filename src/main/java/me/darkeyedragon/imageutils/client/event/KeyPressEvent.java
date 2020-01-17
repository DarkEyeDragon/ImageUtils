package me.darkeyedragon.imageutils.client.event;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.client.KeyBindings;
import me.darkeyedragon.imageutils.client.ScreenshotHandler;
import me.darkeyedragon.imageutils.client.gui.GuiLocalScreenshots;
import me.darkeyedragon.imageutils.client.gui.GuiPartialScreenshot;
import me.darkeyedragon.imageutils.client.imageuploader.ResponseFactory;
import me.darkeyedragon.imageutils.client.imageuploader.Uploader;
import me.darkeyedragon.imageutils.client.message.ResponseMessageFactory;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;


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
            Minecraft.getMinecraft().displayGuiScreen(new GuiLocalScreenshots(null, main));
        } else if (KeyBindings.screenshotUploadKey.isPressed()) {
            BufferedImage screenshot = ScreenshotHandler.full();
            Uploader uploader = main.getUploaderFactory().getUploader();
            uploader.uploadAsync(screenshot).thenAccept((httpResponse -> {
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(ResponseMessageFactory.getFormattedMessage(Objects.requireNonNull(ResponseFactory.getResponseAdaptor(httpResponse))));
                }
            })).exceptionally((error) -> {
                error.printStackTrace();
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(ResponseMessageFactory.getErrorMessage(error));
                return null;
            });
        }
    }
}