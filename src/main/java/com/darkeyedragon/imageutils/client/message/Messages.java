package com.darkeyedragon.imageutils.client.message;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.utils.Filter;
import com.darkeyedragon.imageutils.client.utils.ImageUtil;
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

public class Messages{
    public static void uploadMessage(String result){
        ITextComponent uploadstr = new TextComponentTranslation("imageutil.message.upload.success").appendSibling(new TextComponentString(" "));
        ITextComponent linkText = new TextComponentString(result);
        linkText.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, result));
        linkText.getStyle().setUnderlined(true);
        linkText.getStyle().setColor(TextFormatting.AQUA);
        imageLink(result);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(uploadstr.appendSibling(linkText));
    }
    public static void errorMessage(int errorCode, String responseMessage){
        ITextComponent errorText = new TextComponentTranslation("imageutil.message.upload.error");
        errorText.getStyle().setColor(TextFormatting.RED);
        ITextComponent response = new TextComponentString(errorCode+": "+responseMessage);
        response.getStyle().setColor(TextFormatting.RED);
        ITextComponent errorMessage = new TextComponentTranslation("imageutil.message.upload.error1");
        ITextComponent link = new TextComponentTranslation("imageutil.message.upload.errorlink");
        ITextComponent hover = new TextComponentString("https://github.com/DarkEyeDragon/ImageUtils");
        link.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/DarkEyeDragon/ImageUtils"));
        link.getStyle().setColor(TextFormatting.AQUA);
        link.getStyle().setUnderlined(true);
        link.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(errorText);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(response);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(errorMessage.appendSibling(new TextComponentString(" ")).appendSibling(link));
    }
    public static void imageLink(String link, String unformattedText){
        ImageUtilsMain.fixedThreadPool.submit(()->{
            if(Filter.isValidImage(link)){
                try{
                    URL url = new URL(link);
                    BufferedImage downloadedImage = ImageUtil.downloadFromUrl(url);
                    addToList(link, downloadedImage);
                    //TODO CLEAN UP
                    ITextComponent textLink = new TextComponentString("view image");
                    textLink.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
                    textLink.getStyle().setColor(TextFormatting.GOLD);
                    textLink.getStyle().setUnderlined(true);
                    textLink.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(link)));
                    ITextComponent result;
                    String test[] = unformattedText.split(Pattern.quote(link));
                    if(test.length > 1){
                        result = new TextComponentString(test[0].replace(Pattern.quote(link), "")).appendSibling(textLink).appendText(test[1]);
                        System.out.println(result);
                    }else{
                        result = new TextComponentString(test[0].replace(Pattern.quote(link), "")).appendSibling(textLink);
                    }
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(result);
                }catch (IOException e){
                    e.printStackTrace();
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(e.getMessage()));
                }
            }
        });
    }
    public static void imageLink(String link){
        ImageUtilsMain.fixedThreadPool.submit(()->{
            if(Filter.isValidImage(link)){
                try{
                    URL url = new URL(link);
                    BufferedImage downloadedImage = ImageUtil.downloadFromUrl(url);
                    addToList(link, downloadedImage);
                    //TODO CLEAN UP
                    ITextComponent textLink = new TextComponentString("view image");
                    textLink.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
                    textLink.getStyle().setColor(TextFormatting.GOLD);
                    textLink.getStyle().setUnderlined(true);
                    textLink.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(link)));
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(textLink);
                }catch (IOException e){
                    e.printStackTrace();
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(e.getMessage()));
                }
            }
        });
    }
    private static synchronized void addToList(String urlString, BufferedImage downloadedImage){
        Minecraft.getMinecraft().addScheduledTask(() -> {
            ImageUtilsMain.validLinks.put(urlString, downloadedImage);
        });
    }
}
