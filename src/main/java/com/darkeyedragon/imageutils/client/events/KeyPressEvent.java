package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.KeyBindings;
import com.darkeyedragon.imageutils.client.TakeScreenshot;
import com.darkeyedragon.imageutils.client.gui.GuiLocalScreenshots;
import com.darkeyedragon.imageutils.client.gui.PartialScreenshotGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
