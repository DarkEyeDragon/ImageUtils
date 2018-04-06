package com.darkeyedragon.screenshotuploader.client.Events;

import com.darkeyedragon.screenshotuploader.client.KeyBindings;
import com.darkeyedragon.screenshotuploader.client.ModConfig;
import com.darkeyedragon.screenshotuploader.client.gui.CustomGui;
import com.darkeyedragon.screenshotuploader.client.imageuploaders.CustomUploader;
import com.darkeyedragon.screenshotuploader.client.imageuploaders.ImgurUploader;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ScreenShotHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.image.BufferedImage;
import java.util.Timer;


public class KeyPressEvent{

    private Timer timer = new Timer();

    private ImgurUploader imgurUploader = new ImgurUploader();
    private CustomUploader customUploader = new CustomUploader();

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onKeyInput(InputEvent.KeyInputEvent event){
        if(KeyBindings.openScreenshotGuiKey.isPressed()){
            System.out.println("Key "+ event.getResult() +" is pressed!" );
            Minecraft.getMinecraft().displayGuiScreen(new CustomGui());
        }
        else if(KeyBindings.screenshotUploadKey.isPressed()){
            BufferedImage screenshot = ScreenShotHelper.createScreenshot(Minecraft.getMinecraft().displayWidth,Minecraft.getMinecraft().displayHeight, Minecraft.getMinecraft().getFramebuffer());
            if(ModConfig.CustomServer){
                if(!ModConfig.ServerSettings.server.isEmpty())
                    if(ModConfig.ServerSettings.postData.length > 0)
                        //for (String s: ModConfig.ServerSettings.postData){
                            customUploader.uploadImage(screenshot, ModConfig.ServerSettings.server);
                        //}
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
