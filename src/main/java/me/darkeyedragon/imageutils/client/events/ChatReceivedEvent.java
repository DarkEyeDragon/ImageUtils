package me.darkeyedragon.imageutils.client.events;

import me.darkeyedragon.imageutils.client.message.Messages;
import me.darkeyedragon.imageutils.client.utils.Filter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ChatReceivedEvent{

    @SubscribeEvent
    public void ChatReceived(ClientChatReceivedEvent e) {
        String text = e.getMessage().getUnformattedComponentText();
        List<String> urls = Filter.extractUrls(text);
        if (urls.size() > 0){
            e.setCanceled(true);
            for (String link : urls){
                if (Filter.isValidImage(link)) {
                    e.setCanceled(true);
                    Messages.imageLink(link, text);
                }else{
                    Minecraft.getInstance().ingameGUI.getChatGUI().printChatMessage(e.getMessage());
                }
            }
        }
    }
}
