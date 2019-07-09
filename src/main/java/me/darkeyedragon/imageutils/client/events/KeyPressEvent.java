package me.darkeyedragon.imageutils.client.events;

import me.darkeyedragon.imageutils.client.KeyBindings;
import me.darkeyedragon.imageutils.client.ScreenshotHandler;
import me.darkeyedragon.imageutils.client.gui.GuiPartialScreenshot;
import me.darkeyedragon.imageutils.client.gui.GuiSelectUploader;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.awt.*;
import java.awt.image.BufferedImage;


public class KeyPressEvent{
    @SubscribeEvent
    public void onKeyInput (InputEvent.KeyInputEvent event){
        if (KeyBindings.screenshotPartialUploadKey.isPressed()){
            Minecraft.getInstance().displayGuiScreen(new GuiPartialScreenshot());
            MouseInfo.getPointerInfo().getLocation();
        }else if (KeyBindings.screenshotViewer.isPressed()){
            Minecraft.getInstance().displayGuiScreen(new GuiSelectUploader(null));

        }else if (KeyBindings.screenshotUploadKey.isPressed()){
            BufferedImage screenshot = ScreenshotHandler.full();
            ScreenshotHandler.upload(screenshot);
        }
    }
}