package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.gui.ImagePreviewer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class CustomGuiOpenEvent{

    @SubscribeEvent
    public void guiOpenEvent(net.minecraftforge.client.event.GuiOpenEvent e) throws IOException{
        if(e.getGui() instanceof GuiConfirmOpenLink){
            Minecraft mc = Minecraft.getMinecraft();
            ITextComponent textComponent = mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
            if(textComponent != null){
                String link = Objects.requireNonNull(textComponent.getStyle().getClickEvent()).getValue();
                if(ImageUtilsMain.validLinks.containsKey(link)){
                    BufferedImage image = ImageUtilsMain.validLinks.get(link);
                    e.setGui(new ImagePreviewer(new URL(link),image));
                }
            }else{
                ImageUtilsMain.logger.warn("Something went wrong! Unable to get URL");
            }
        }
    }
}
