package com.darkeyedragon.imageutils.client.message;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class SendClientMessage{

    public static void basic(String message){
        ITextComponent send = new TextComponentTranslation(message);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(send);
    }

}
