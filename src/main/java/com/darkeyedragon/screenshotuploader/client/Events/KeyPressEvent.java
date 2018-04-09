package com.darkeyedragon.screenshotuploader.client.events;

import com.darkeyedragon.screenshotuploader.client.KeyBindings;
import com.darkeyedragon.screenshotuploader.client.ModConfig;
import com.darkeyedragon.screenshotuploader.client.imageuploaders.CustomUploader;
import com.darkeyedragon.screenshotuploader.client.imageuploaders.ImgurUploader;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ScreenShotHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.image.BufferedImage;


public class KeyPressEvent{

    private ImgurUploader imgurUploader = new ImgurUploader();
    private CustomUploader customUploader = new CustomUploader();

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onKeyInput(InputEvent.KeyInputEvent event){
        if(KeyBindings.screenshotUploadKey.isPressed()){
            BufferedImage screenshot = ScreenShotHelper.createScreenshot(Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight, Minecraft.getMinecraft().getFramebuffer());
            if(ModConfig.CustomServer){
                if(!ModConfig.ServerSettings.server.isEmpty())
                    if(ModConfig.ServerSettings.postData.length > 0)
                            customUploader.uploadImage(screenshot, ModConfig.ServerSettings.server);
                    else
                        customUploader.uploadImage(screenshot, ModConfig.ServerSettings.server);
                else
                    System.out.println("Invalid URL");
            }else{
                imgurUploader.uploadImage(screenshot);
            }
        }
    }
}
