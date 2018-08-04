package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.message.Messages;
import com.darkeyedragon.imageutils.client.utils.Filter;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

import java.util.List;

public class ChatReceivedEvent{


    @SubscribeEvent
    public void ChatReceived(ClientChatReceivedEvent e){
        String text = e.message.getUnformattedText();
        List<String> urls = Filter.extractUrls(text);
        if(urls.size() > 0){
            e.setCanceled(true);
            for (String link : urls){
                Messages.imageLink(link, text);
            }
        }
    }
}
