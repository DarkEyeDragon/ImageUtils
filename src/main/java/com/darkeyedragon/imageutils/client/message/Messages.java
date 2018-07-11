package com.darkeyedragon.imageutils.client.message;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class Messages{
    public void uploadMessage(String result){
        ITextComponent uploadstr = new TextComponentTranslation("screenshot.message.upload.success").appendSibling(new TextComponentString(" "));
        ITextComponent linkText = new TextComponentString(result);
        linkText.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, result));
        linkText.getStyle().setUnderlined(true);
        linkText.getStyle().setColor(TextFormatting.AQUA);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(uploadstr.appendSibling(linkText));
    }
    public void errorMessage(int errorCode, String responseMessage){
        ITextComponent errorText = new TextComponentTranslation("screenshot.message.upload.error");
        errorText.getStyle().setColor(TextFormatting.RED);
        ITextComponent response = new TextComponentString(errorCode+": "+responseMessage);
        response.getStyle().setColor(TextFormatting.RED);
        ITextComponent errorMessage = new TextComponentTranslation("screenshot.message.upload.error1");
        ITextComponent link = new TextComponentTranslation("screenshot.message.upload.errorlink");
        ITextComponent hover = new TextComponentString("github.com/DarkEyeDragon/ScreenshotUploader");
        link.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/DarkEyeDragon/ScreenshotUploader"));
        link.getStyle().setColor(TextFormatting.AQUA);
        link.getStyle().setUnderlined(true);
        link.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(errorText);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(response);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(errorMessage.appendSibling(new TextComponentString(" ")).appendSibling(link));
    }
}
