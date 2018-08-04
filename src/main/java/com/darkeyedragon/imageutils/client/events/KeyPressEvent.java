package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.KeyBindings;
import com.darkeyedragon.imageutils.client.TakeScreenshot;
import com.darkeyedragon.imageutils.client.gui.GuiLocalScreenshots;
import com.darkeyedragon.imageutils.client.gui.PartialScreenshotGui;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

import java.awt.*;


public class KeyPressEvent{

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onKeyInput(InputEvent.KeyInputEvent event){
        TakeScreenshot takeScreenshot =  new TakeScreenshot();
        if(KeyBindings.screenshotUploadKey.isPressed()){
            takeScreenshot.full();
        }else if(KeyBindings.screenshotPartialUploadKey.isPressed()){
            Minecraft.getMinecraft().displayGuiScreen( new PartialScreenshotGui());
            MouseInfo.getPointerInfo().getLocation();
        }else if(KeyBindings.screenshotViewer.isPressed()){
            Minecraft.getMinecraft().displayGuiScreen( new GuiLocalScreenshots(null));
        }
    }
}
