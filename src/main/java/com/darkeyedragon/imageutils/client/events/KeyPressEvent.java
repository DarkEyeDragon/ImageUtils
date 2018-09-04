package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.KeyBindings;
import com.darkeyedragon.imageutils.client.TakeScreenshot;
import com.darkeyedragon.imageutils.client.gui.GuiLocalScreenshots;
import com.darkeyedragon.imageutils.client.gui.GuiPartialScreenshot;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.awt.*;


public class KeyPressEvent{

    @SubscribeEvent
    public void onKeyInput (InputEvent.KeyInputEvent event){
        TakeScreenshot takeScreenshot = new TakeScreenshot();
        if (KeyBindings.screenshotUploadKey.isPressed()){
            takeScreenshot.full();
        }else if (KeyBindings.screenshotPartialUploadKey.isPressed()){
            Minecraft.getMinecraft().displayGuiScreen(new GuiPartialScreenshot());
            MouseInfo.getPointerInfo().getLocation();
        }else if (KeyBindings.screenshotViewer.isPressed()){
            Minecraft.getMinecraft().displayGuiScreen(new GuiLocalScreenshots(Minecraft.getMinecraft().currentScreen));
        }
    }
}
