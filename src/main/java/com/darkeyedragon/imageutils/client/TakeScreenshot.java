package com.darkeyedragon.imageutils.client;

import com.darkeyedragon.imageutils.client.imageuploaders.CustomUploader;
import com.darkeyedragon.imageutils.client.imageuploaders.ImgurUploader;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ScreenShotHelper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TakeScreenshot{

    public void full (){
        BufferedImage screenshot = ScreenShotHelper.createScreenshot(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, Minecraft.getMinecraft().getFramebuffer());
        upload(screenshot);
    }

    public void partial (Point first, Point second){
        BufferedImage screenshot = ScreenShotHelper.createScreenshot(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, Minecraft.getMinecraft().getFramebuffer());
        System.out.println("Values: X:" + (first.x + 3) + " Y:" + (first.y + 3) + " W:" + (second.x - first.x - 3) + " H:" + (second.y - first.y - 3));
        int height = Math.abs(second.y - first.y - 3);
        int width = Math.abs(second.x - first.x - 3);
        BufferedImage alteredScreenshot = screenshot.getSubimage(first.x + 3, first.y + 3, width, height);
        upload(alteredScreenshot);

    }

    private void upload (BufferedImage screenshot){
        if (ModConfig.customServer){
            if (!ModConfig.uploader.isEmpty()){
                CustomUploader.uploadImage(screenshot);
            }else{
                System.out.println("Invalid URL");
            }
        }else{
            ImgurUploader.uploadImage(screenshot);
        }
    }

}
