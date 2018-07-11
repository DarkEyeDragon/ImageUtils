package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.utils.Filter;
import com.darkeyedragon.imageutils.client.utils.ImageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

public class ChatReceivedEvent{

    @SubscribeEvent
    public void ChatReceived(ClientChatReceivedEvent e){
        String text = e.getMessage().getUnformattedText();
        List<String> urls = Filter.extractUrls(text);
        if(urls.size() > 0){
            e.setCanceled(true);
            for (String link : urls){
                ImageUtilsMain.fixedThreadPool.submit(()->{
                    if(Filter.isValidImage(link)){
                        try{
                            URL url = new URL(link);
                            BufferedImage downloadedImage = ImageUtil.downloadFromUrl(url);
                            addToList(link, downloadedImage);
                            ITextComponent textLink = new TextComponentString("view image");
                            textLink.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
                            textLink.getStyle().setColor(TextFormatting.GOLD);
                            textLink.getStyle().setUnderlined(true);
                            textLink.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(link)));
                            ITextComponent result;
                            String test[] = text.split(Pattern.quote(link));
                            if(test.length > 1){
                                result = new TextComponentString(test[0].replace(Pattern.quote(link), "")).appendSibling(textLink).appendText(test[1]);
                                System.out.println(result);
                            }else{
                                result = new TextComponentString(test[0].replace(Pattern.quote(link), "")).appendSibling(textLink);
                            }
                            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(result);
                        }catch (IOException e1){
                            e1.printStackTrace();
                            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(e.getMessage());
                        }
                    }else{
                        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(e.getMessage());
                    }
                });
            }
        }
    }
    private synchronized void addToList(String urlString, BufferedImage downloadedImage){
        Minecraft.getMinecraft().addScheduledTask(() -> {
            ImageUtilsMain.validLinks.put(urlString, downloadedImage);
        });
    }
}
