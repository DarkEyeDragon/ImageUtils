package com.darkeyedragon.screenshotuploader.client.Events;

import com.darkeyedragon.screenshotuploader.client.KeyBindings;
import com.darkeyedragon.screenshotuploader.client.gui.CustomGui;
import com.darkeyedragon.screenshotuploader.client.imageuploaders.ImgurUploader;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ScreenShotHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.awt.image.BufferedImage;
import java.util.Timer;


public class KeyPressEvent{

    private Timer timer = new Timer();

    private ImgurUploader imgurUploader = new ImgurUploader();

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event){
        if(KeyBindings.openScreenshotGuiKey.isPressed()){
            System.out.println("Key "+ event.getResult() +" is pressed!" );
            Minecraft.getMinecraft().displayGuiScreen(new CustomGui());
        }
        else if(KeyBindings.screenshotUploadKey.isPressed()){
            BufferedImage screenshot = ScreenShotHelper.createScreenshot(Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight, Minecraft.getMinecraft().getFramebuffer());
            imgurUploader.uploadImage(screenshot);
        }
    }
}
