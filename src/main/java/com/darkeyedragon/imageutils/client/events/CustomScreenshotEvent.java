package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.imageuploaders.ImgurUploader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CustomScreenshotEvent{

    private ImgurUploader imgurUploader = new ImgurUploader();

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onScreenshot(net.minecraftforge.client.event.ScreenshotEvent event){
        //TODO CHANGE
        /*if(ModConfig.Override){
            BufferedImage screenshot = event.getImage();
            imgurUploader.uploadImage(screenshot);
        }*/
    }
}
