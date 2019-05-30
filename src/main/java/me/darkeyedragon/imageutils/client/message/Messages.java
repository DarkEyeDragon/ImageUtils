package me.darkeyedragon.imageutils.client.message;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.client.utils.ImageUtil;
import me.darkeyedragon.imageutils.client.utils.StringFilter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

import static me.darkeyedragon.imageutils.client.utils.ImageUtil.addToLinkList;

public class Messages{


    public static void uploadMessage (String result){

        ITextComponent uploadstr = new TextComponentTranslation("imageutil.message.upload.success").appendSibling(new TextComponentString(" "));
        if (!result.endsWith(".png") && !result.endsWith(".jpg")){
            result += ".png";
        }
        ITextComponent linkText = new TextComponentString(result);
        try{
            ImageUtil.downloadFromUrl(result);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        linkText.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("imageutil.message.upload.hover")));
        linkText.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, result));
        linkText.getStyle().setUnderlined(true);
        linkText.getStyle().setColor(TextFormatting.AQUA);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(uploadstr.appendSibling(linkText));
    }

    public static void errorMessage (String errorMessage){
        ITextComponent errorText = new TextComponentTranslation("imageutil.message.upload.error");
        errorText.getStyle().setColor(TextFormatting.RED);
        ITextComponent response = new TextComponentString(errorMessage);
        response.getStyle().setColor(TextFormatting.RED);
        ITextComponent infoMessage = new TextComponentTranslation("imageutil.message.upload.error1");
        ITextComponent link = new TextComponentTranslation("imageutil.message.upload.errorlink");
        ITextComponent hover = new TextComponentString("https://github.com/DarkEyeDragon/ImageUtils");
        link.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/DarkEyeDragon/ImageUtils"));
        link.getStyle().setColor(TextFormatting.AQUA);
        link.getStyle().setUnderlined(true);
        link.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(errorText);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(response);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(infoMessage.appendSibling(new TextComponentString(" ")).appendSibling(link));
    }

    public static void imageLink (String link, String unformattedText){
        ImageUtilsMain.fixedThreadPool.submit(() -> {
            if (StringFilter.isValidImage(link)){
                try{
                    BufferedImage downloadedImage = ImageUtil.downloadFromUrl(link);
                    addToLinkList(link, downloadedImage);
                    //TODO CLEAN UP
                    TextComponentTranslation textLink = new TextComponentTranslation("imageutil.message.view_image");
                    textLink.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
                    textLink.getStyle().setColor(TextFormatting.GOLD);
                    textLink.getStyle().setUnderlined(true);
                    textLink.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(link)));
                    ITextComponent result;
                    String test[] = unformattedText.split(Pattern.quote(link));
                    if (test.length > 1){
                        result = new TextComponentString(test[0].replace(Pattern.quote(link), "")).appendSibling(textLink).appendText(test[1]);
                    }else{
                        result = new TextComponentString(test[0].replace(Pattern.quote(link), "")).appendSibling(textLink);
                    }
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(result);
                }
                catch (IOException e){
                    e.printStackTrace();
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(e.getMessage()));
                }
            }
        });
    }

    public static void imageLink (String link){
        ImageUtilsMain.fixedThreadPool.submit(() -> {
            if (StringFilter.isValidImage(link)){
                try{
                    URL url = new URL(link);
                    BufferedImage downloadedImage = ImageUtil.downloadFromUrl(link);
                    addToLinkList(link, downloadedImage);
                    //TODO CLEAN UP
                    TextComponentTranslation textLink = new TextComponentTranslation("imageutil.message.view_image");
                    textLink.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
                    textLink.getStyle().setColor(TextFormatting.GOLD);
                    textLink.getStyle().setUnderlined(true);
                    textLink.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(link)));
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(textLink);
                }
                catch (IOException e){
                    e.printStackTrace();
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(e.getMessage()));
                }
            }
        });
    }
}
