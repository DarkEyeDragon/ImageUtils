package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.gui.LocalScreenshots;
import com.darkeyedragon.imageutils.client.gui.buttons.GuiButtonWithImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptions;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiOptionsHook{

    @SubscribeEvent
    public void onInit(GuiScreenEvent.InitGuiEvent e){
       if(e.getGui() instanceof GuiOptions){
           int width = e.getGui().width;
           int height = e.getGui().height;
                                                        //ID,        x,                 y,        buttonWidth,    buttonHeight,     ????        ????         ????        ResourceLocation
           e.getButtonList().add(new GuiButtonWithImage(5, width-40, 20, 20, 20));
           e.setButtonList(e.getButtonList());
       }
    }
    @SubscribeEvent
    public void onButtonClick(GuiScreenEvent.ActionPerformedEvent e){
        if(e.getGui() instanceof GuiOptions){
            if(e.getButton().id == 5){
                Minecraft.getMinecraft().displayGuiScreen(new LocalScreenshots(e.getGui()));
            }
        }
    }
}
