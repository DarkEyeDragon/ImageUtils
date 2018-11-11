package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.gui.GuiImagePreviewer;
import com.darkeyedragon.imageutils.client.utils.ImageResource;
import com.darkeyedragon.imageutils.client.utils.ImageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class CustomGuiOpenEvent{

    @SubscribeEvent
    public void guiOpenEvent (net.minecraftforge.client.event.GuiOpenEvent e){
        if (e.getGui() instanceof GuiConfirmOpenLink){
            Minecraft mc = Minecraft.getMinecraft();
            ITextComponent textComponent = mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
            if (textComponent != null){
                String link = Objects.requireNonNull(textComponent.getStyle().getClickEvent()).getValue();
                ImageResource imgResource = null;
                if (ImageUtilsMain.validLinks.containsKey(link)){
                    imgResource = new ImageResource(link, ImageUtilsMain.validLinks.get(link), link);
                }else if (link.startsWith("http://LINK::")){
                    String splitStr = link.replace("http://LINK::", "");
                    try{
                        Path path = Paths.get(Minecraft.getMinecraft().gameDir.getCanonicalPath(), "screenshots", splitStr);
                        imgResource = new ImageResource(splitStr, ImageUtil.getLocal(path.toFile()), false, path.toString());
                    }
                    catch (IOException e1){
                        e1.printStackTrace();
                    }
                }
                if (imgResource != null){
                    e.setGui(new GuiImagePreviewer(imgResource));
                }
            }else{
                ImageUtilsMain.logger.warn("Something went wrong! Unable to get URL");
            }

        }
    }
}
