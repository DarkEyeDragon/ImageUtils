package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.gui.ImagePreviewer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.util.IChatComponent;
import org.lwjgl.input.Mouse;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class CustomGuiOpenEvent{

    @SubscribeEvent
    public void guiOpenEvent(net.minecraftforge.client.event.GuiOpenEvent e) throws IOException{
        if(e.gui instanceof GuiConfirmOpenLink){
            Minecraft mc = Minecraft.getMinecraft();
            IChatComponent textComponent = mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
            if(textComponent != null){
                //String link = Objects.requireNonNull(textComponent.getStyle().getClickEvent()).getValue();
                String link = textComponent.getChatStyle().getChatClickEvent().getValue();
                if(ImageUtilsMain.validLinks.containsKey(link)){
                    BufferedImage image = ImageUtilsMain.validLinks.get(link);
                    e.gui = new ImagePreviewer(new URL(link),image);
                }
            }else{
                ImageUtilsMain.logger.warn("Something went wrong! Unable to get URL");
            }
        }
    }
}
