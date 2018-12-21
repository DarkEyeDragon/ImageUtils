package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.message.Messages;
import com.darkeyedragon.imageutils.client.utils.StringFilter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ChatReceivedEvent{


    @SubscribeEvent
    public void ChatReceived (ClientChatReceivedEvent e){
        String text = e.getMessage().getUnformattedText();
        List<String> urls = StringFilter.extractUrls(text);
        if (urls.size() > 0){
            e.setCanceled(true);
            for (String link : urls){
                if (StringFilter.isValidImage(link)){
                    e.setCanceled(true);
                    Messages.imageLink(link, text);
                }else{
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(e.getMessage());
                }
            }
        }
    }
}
