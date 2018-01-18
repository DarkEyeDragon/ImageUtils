package com.darkeyedragon.screenshotuploader.client.Events;

import com.darkeyedragon.screenshotuploader.client.ScreenshotMain;
import com.darkeyedragon.screenshotuploader.client.imageuploaders.ImgurUploader;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.image.BufferedImage;

public class CustomScreenshotEvent{

    private ImgurUploader imgurUploader = new ImgurUploader();

    @SubscribeEvent
    //@SideOnly(Side.CLIENT)
    public void onScreenshot(ScreenshotEvent event){
        System.out.println(ScreenshotMain.overrideDefaultScreenshotKey);
        if(ScreenshotMain.overrideDefaultScreenshotKey){
            System.out.print("SCREENSHOT OVERRIDE");

            BufferedImage screenshot = event.getImage();
            imgurUploader.uploadImage(screenshot);
            if(!ScreenshotMain.saveScreenshot){
                event.setCanceled(true);
            }
        }
    }
}
