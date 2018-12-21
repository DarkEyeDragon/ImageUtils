package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.KeyBindings;
import com.darkeyedragon.imageutils.client.ScreenshotHandler;
import com.darkeyedragon.imageutils.client.gui.GuiFilter;
import com.darkeyedragon.imageutils.client.gui.GuiPartialScreenshot;
import com.darkeyedragon.imageutils.client.gui.lists.ListItem;
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
            ListItem list = new ListItem("Test", "Short Description");
            GuiFilter guiFilter = new GuiFilter();
            guiFilter.addListItem(list);
            guiFilter.addListItem(list);
            guiFilter.addListItem(list);
            guiFilter.addListItem(list);
            guiFilter.addListItem(list);
            Minecraft.getMinecraft().displayGuiScreen(guiFilter);
        }else if (KeyBindings.screenshotUploadKey.isPressed()){
            BufferedImage screenshot = ScreenshotHandler.full();
            ScreenshotHandler.upload(screenshot);
        }
    }
}