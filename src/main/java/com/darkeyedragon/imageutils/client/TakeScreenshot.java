package com.darkeyedragon.imageutils.client;

import com.darkeyedragon.imageutils.client.imageuploaders.CustomUploader;
import com.darkeyedragon.imageutils.client.imageuploaders.ImgurUploader;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ScreenShotHelper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TakeScreenshot{

    private ImgurUploader imgurUploader = new ImgurUploader();
    private CustomUploader customUploader = new CustomUploader();

    public void full(){
        BufferedImage screenshot = ScreenShotHelper.createScreenshot(Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight, Minecraft.getMinecraft().getFramebuffer());
        upload(screenshot);
    }
    public void partial(Point first, Point second){
            BufferedImage screenshot = ScreenShotHelper.createScreenshot(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, Minecraft.getMinecraft().getFramebuffer());
            System.out.println("Values: X:"+(first.x+3)+" Y:"+(first.y+3)+" W:"+(second.x - first.x-3)+" H:"+(second.y - first.y-3));
            int height = Math.abs(second.y - first.y-3);
            int width = Math.abs(second.x - first.x-3);
            BufferedImage alteredScreenshot = screenshot.getSubimage(first.x+3, first.y+3, width, height);
            upload(alteredScreenshot);
        /*Graphics2D g2d = screenshot.createGraphics();
        g2d.setColor(Color.red);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(first.x,first.y,second.x,second.y);*/
    }

    private void upload(BufferedImage screenshot){

        //TODO CHANGE
        /*if(ModConfig.CustomServer){
            //if(!ModConfig.ServerSettings.server.isEmpty())
                /*if(ModConfig.ServerSettings.postData.length > 0)
                    customUploader.uploadImage(screenshot, ModConfig.ServerSettings.server);
                else
                    customUploader.uploadImage(screenshot, ModConfig.ServerSettings.server);

            else
                System.out.println("Invalid URL");
        }else
            imgurUploader.uploadImage(screenshot);*/
    }

}
