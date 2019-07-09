package me.darkeyedragon.imageutils.client.message;

import me.darkeyedragon.imageutils.client.ImageUtils;
import me.darkeyedragon.imageutils.client.utils.Filter;
import me.darkeyedragon.imageutils.client.utils.ImageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

import static me.darkeyedragon.imageutils.client.utils.ImageUtil.addToLinkList;

public class Messages{


    public static void uploadMessage (String result){

        ITextComponent uploadstr = new TranslationTextComponent("imageutil.message.upload.success").appendSibling(new StringTextComponent(" "));
        if (!result.endsWith(".png") && !result.endsWith(".jpg")){
            result += ".png";
        }
        ITextComponent linkText = new StringTextComponent(result);
        try{
            ImageUtil.downloadFromUrl(new URL(result));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        linkText.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("imageutil.message.upload.hover")));
        linkText.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, result));
        linkText.getStyle().setUnderlined(true);
        linkText.getStyle().setColor(TextFormatting.AQUA);
        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(uploadstr.appendSibling(linkText));
    }

    public static void errorMessage (String errorMessage){
        ITextComponent errorText = new TranslationTextComponent("imageutil.message.upload.error");
        errorText.getStyle().setColor(TextFormatting.RED);
        ITextComponent response = new StringTextComponent(errorMessage);
        response.getStyle().setColor(TextFormatting.RED);
        ITextComponent infoMessage = new TranslationTextComponent("imageutil.message.upload.error1");
        ITextComponent link = new TranslationTextComponent("imageutil.message.upload.errorlink");
        ITextComponent hover = new StringTextComponent("https://github.com/DarkEyeDragon/ImageUtils");
        link.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/DarkEyeDragon/ImageUtils"));
        link.getStyle().setColor(TextFormatting.AQUA);
        link.getStyle().setUnderlined(true);
        link.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(errorText);
        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(response);
        Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(infoMessage.appendSibling(new StringTextComponent(" ")).appendSibling(link));
    }

    public static void imageLink (String link, String unformattedText){
        ImageUtils.fixedThreadPool.submit(() -> {
            if (Filter.isValidImage(link)) {
                try{
                    URL url = new URL(link);
                    BufferedImage downloadedImage = ImageUtil.downloadFromUrl(url);
                    addToLinkList(link, downloadedImage);
                    //TODO CLEAN UP
                    TranslationTextComponent textLink = new TranslationTextComponent("imageutil.message.view_image");
                    textLink.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
                    textLink.getStyle().setColor(TextFormatting.GOLD);
                    textLink.getStyle().setUnderlined(true);
                    textLink.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent(link)));
                    ITextComponent result;
                    String test[] = unformattedText.split(Pattern.quote(link));
                    if (test.length > 1){
                        result = new StringTextComponent(test[0].replace(Pattern.quote(link), "")).appendSibling(textLink).appendText(test[1]);
                    }else{
                        result = new StringTextComponent(test[0].replace(Pattern.quote(link), "")).appendSibling(textLink);
                    }
                    Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(result);
                }
                catch (IOException e){
                    e.printStackTrace();
                    Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(e.getMessage()));
                }
            }
        });
    }

    public static void imageLink (String link){
        ImageUtils.fixedThreadPool.submit(() -> {
            if (Filter.isValidImage(link)) {
                try{
                    URL url = new URL(link);
                    BufferedImage downloadedImage = ImageUtil.downloadFromUrl(url);
                    addToLinkList(link, downloadedImage);
                    //TODO CLEAN UP
                    TranslationTextComponent textLink = new TranslationTextComponent("imageutil.message.view_image");
                    textLink.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
                    textLink.getStyle().setColor(TextFormatting.GOLD);
                    textLink.getStyle().setUnderlined(true);
                    textLink.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new StringTextComponent(link)));
                    Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(textLink);
                }
                catch (IOException e){
                    e.printStackTrace();
                    Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(new StringTextComponent(e.getMessage()));
                }
            }
        });
    }
}
