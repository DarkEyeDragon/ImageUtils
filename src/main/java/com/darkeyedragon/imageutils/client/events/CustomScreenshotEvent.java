package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.ModConfig;
import com.darkeyedragon.imageutils.client.ScreenshotHandler;
import com.darkeyedragon.imageutils.client.imageuploaders.ImgurUploader;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
                Path dir = Paths.get(Minecraft.getMinecraft().gameDir.getAbsolutePath(), "screenshots");
                File screenshotFile = ScreenshotHandler.getTimestampedPNGFileForDirectory(dir.toFile());
                //TODO PR Event to intercept Actions
                ImageUtilsMain.fixedThreadPool.submit(() -> {
                    try{
                        ImageIO.write(screenshot, "png", screenshotFile.getAbsoluteFile());
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                });
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    TextComponentTranslation prefix = new TextComponentTranslation("imageutil.message.screenshot_save");
                    ITextComponent itextcomponent = new TextComponentString(screenshotFile.getName());
                    //itextcomponent.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://LINK::"+screenshotFile.getName()));
                    itextcomponent.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/view " + screenshotFile.getName()));
                    ImageUtilsMain.validLinks.put(event.getScreenshotFile().getName(), screenshot);
                    itextcomponent.getStyle().setUnderlined(true);
                    itextcomponent.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("View image in-game")));
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(prefix.appendSibling(itextcomponent));
                });
            });
        }
    }
}
