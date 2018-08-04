package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.ModConfig;
import com.darkeyedragon.imageutils.client.imageuploaders.ImgurUploader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.SideOnly;

import java.awt.image.BufferedImage;

public class CustomScreenshotEvent{

    private static final Object Side = ;

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onScreenshot(KeyPressEvent event){
        if(ModConfig.Override){
            BufferedImage screenshot = event.getImage();
            ImgurUploader.uploadImage(screenshot);
        }
    }
}
