package me.darkeyedragon.imageutils.client.events;

import me.darkeyedragon.imageutils.client.KeyBindings;
import me.darkeyedragon.imageutils.client.ScreenshotHandler;
import me.darkeyedragon.imageutils.client.gui.GuiLocalScreenshots;
import me.darkeyedragon.imageutils.client.gui.GuiPartialScreenshot;
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
            /*ListItem list = new ListItem("Test", "Short Description");
            GuiFilter guiFilter = new GuiFilter();
            guiFilter.addListItem(list);
            guiFilter.addListItem(list);
            guiFilter.addListItem(list);
            guiFilter.addListItem(list);
            guiFilter.addListItem(list);
            Minecraft.getMinecraft().displayGuiScreen(guiFilter);*/
            Minecraft.getMinecraft().displayGuiScreen(new GuiLocalScreenshots(null));
        }else if (KeyBindings.screenshotUploadKey.isPressed()){
            BufferedImage screenshot = ScreenshotHandler.full();
            ScreenshotHandler.upload(screenshot);
        }
    }
}