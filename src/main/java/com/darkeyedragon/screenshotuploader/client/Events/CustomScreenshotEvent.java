package com.darkeyedragon.screenshotuploader.client.Events;

import com.darkeyedragon.screenshotuploader.client.ScreenshotMain;
import com.darkeyedragon.screenshotuploader.client.imageuploaders.ImgurUploader;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.image.BufferedImage;

public class CustomScreenshotEvent{

    private ImgurUploader imgurUploader = new ImgurUploader();

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onScreenshot(ScreenshotEvent event){
        System.out.println("Screenshot event");
        System.out.println(ScreenshotMain.overrideDefaultScreenshotKey);

        if(ScreenshotMain.overrideDefaultScreenshotKey){
            BufferedImage screenshot = event.getImage();
            imgurUploader.uploadImage(screenshot);
        }
    }
}
