package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.KeyBindings;
import com.darkeyedragon.imageutils.client.ScreenshotHandler;
import com.darkeyedragon.imageutils.client.gui.GuiPartialScreenshot;
import com.darkeyedragon.imageutils.client.gui.GuiSelectUploader;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.awt.*;
import java.awt.image.BufferedImage;


public class KeyPressEvent{
    @SubscribeEvent
    public void onKeyInput (InputEvent.KeyInputEvent event){
        if (KeyBindings.screenshotPartialUploadKey.isPressed()){
            Minecraft.getMinecraft().displayGuiScreen(new GuiPartialScreenshot());
            MouseInfo.getPointerInfo().getLocation();
        }else if (KeyBindings.screenshotViewer.isPressed()){
            //Minecraft.getMinecraft().displayGuiScreen(new GuiLocalScreenshots(Minecraft.getMinecraft().currentScreen));
            Minecraft.getMinecraft().displayGuiScreen(new GuiSelectUploader(null));

        }else if (KeyBindings.screenshotUploadKey.isPressed()){
            BufferedImage screenshot = ScreenshotHandler.full();
            ScreenshotHandler.upload(screenshot);
        }
    }
}