package me.darkeyedragon.imageutils.client.message;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class ClientMessage{

    public static void basic (String message){
        ITextComponent send = new TextComponentTranslation(message);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(send);
    }

    public ITextComponent link (String displayMessage, String link, TextFormatting color){
        ITextComponent textLink = new TextComponentString(displayMessage);
        textLink.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
        textLink.getStyle().setColor(color);
        textLink.getStyle().setUnderlined(true);
        textLink.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(link)));
        return textLink;
    }

}
