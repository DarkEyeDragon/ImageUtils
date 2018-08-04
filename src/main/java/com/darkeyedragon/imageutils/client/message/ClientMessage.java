package com.darkeyedragon.imageutils.client.message;

import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;


public class ClientMessage{

    public static void basic(String message){
        ChatComponentText send = new ChatComponentText(message);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(send);
    }
    public ChatComponentText link(String displayMessage, String link, EnumChatFormatting color){
        ChatComponentText textLink = new ChatComponentText(displayMessage);
        textLink.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
        textLink.getChatStyle().setColor(color);
        textLink.getChatStyle().setUnderlined(true);
        textLink.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(link)));
        return textLink;
    }

}
