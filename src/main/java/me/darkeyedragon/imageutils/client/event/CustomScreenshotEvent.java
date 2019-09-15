package me.darkeyedragon.imageutils.client.event;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.client.ModConfig;
import me.darkeyedragon.imageutils.client.ScreenshotHandler;
import me.darkeyedragon.imageutils.client.UploadHandler;
import me.darkeyedragon.imageutils.client.imageuploader.Uploader;
import me.darkeyedragon.imageutils.client.util.OutputHandler;
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

public class CustomScreenshotEvent {

    private final Uploader uploader;
    private final UploadHandler uploadHandler;

    public CustomScreenshotEvent(ImageUtilsMain main) {
        this.uploader = main.getUploaderFactory().getUploader();
        this.uploadHandler = main.getUploadHandler();
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onScreenshot(net.minecraftforge.client.event.ScreenshotEvent event) {
        if (ModConfig.Override) {
            BufferedImage screenshot = event.getImage();
            uploader.uploadAsync(screenshot, (response, error) -> {
                ITextComponent errorComponent = new TextComponentTranslation("imageutil.message.upload.error").appendSibling(new TextComponentTranslation("imageutil.message.upload.error1"));
                if (response == null && error != null) {
                    OutputHandler.sendMessage(errorComponent.appendText(error.getMessage()), null);
                } else if (response != null) {
                    try {
                        OutputHandler.sendUploadResponseMessage(response, null);
                    } catch (IOException e) {
                        OutputHandler.sendMessage(errorComponent.appendText(e.getMessage()), null);
                    }
                }
            });
        } else {
            event.setCanceled(true);
            BufferedImage screenshot = ScreenshotHandler.full();
            uploadHandler.getFixedThreadPool().submit(() -> {
                Path dir = ImageUtilsMain.getScreenshotDir();
                File screenshotFile = ScreenshotHandler.getTimestampedPNGFileForDirectory(dir.toFile());
                //TODO PR Event to intercept Actions
                uploadHandler.getFixedThreadPool().submit(() -> {
                    try {
                        ImageIO.write(screenshot, "png", screenshotFile.getAbsoluteFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    TextComponentTranslation prefix = new TextComponentTranslation("imageutil.message.screenshot_save");
                    ITextComponent itextcomponent = new TextComponentString(screenshotFile.getName());
                    //itextcomponent.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://LINK::"+screenshotFile.getName()));
                    itextcomponent.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/view " + screenshotFile.getName()));
                    uploadHandler.getValidLinks().put(event.getScreenshotFile().getName(), screenshot);
                    itextcomponent.getStyle().setUnderlined(true);
                    itextcomponent.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("View image in-game")));
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(prefix.appendSibling(itextcomponent));
                });
            });
        }
    }
}
