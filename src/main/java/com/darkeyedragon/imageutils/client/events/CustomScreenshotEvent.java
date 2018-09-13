package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.ModConfig;
import com.darkeyedragon.imageutils.client.ScreenshotHandler;
import com.darkeyedragon.imageutils.client.imageuploaders.ImgurUploader;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CustomScreenshotEvent{

    @SubscribeEvent
    @SideOnly (Side.CLIENT)
    public void onScreenshot (net.minecraftforge.client.event.ScreenshotEvent event){
        if (ModConfig.Override){
            BufferedImage screenshot = event.getImage();
            ImgurUploader.uploadImage(screenshot);
        }else{
            event.setCanceled(true);
            BufferedImage screenshot = ScreenshotHandler.full();
            ImageUtilsMain.fixedThreadPool.submit(() -> {
                File screenshotFile = ScreenshotHandler.getTimestampedPNGFileForDirectory(Minecraft.getMinecraft().gameDir);
                try{
                    //TODO PR Event to intercept Actions
                    ImageIO.write(screenshot, "png", screenshotFile.getAbsoluteFile());
                    Minecraft.getMinecraft().addScheduledTask(() -> {
                        ITextComponent prefix = new TextComponentString("Screenshot saved as: ");
                        ITextComponent itextcomponent = new TextComponentString(screenshotFile.getName());
                        itextcomponent.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, screenshotFile.getAbsolutePath()));
                        //ImageUtilsMain.validLinks.put(event.getScreenshotFile().getName(), screenshot);
                        itextcomponent.getStyle().setUnderlined(true);
                        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(prefix.appendSibling(itextcomponent));
                    });
                }
                catch (IOException e){
                    ImageUtilsMain.logger.error(e);
                }
            });
        }
    }
}
