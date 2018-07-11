package com.darkeyedragon.imageutils.client.utils.text.event;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.HoverEvent;

public class CustomHoverEvent extends HoverEvent{

    public CustomHoverEvent(Action actionIn, ITextComponent valueIn, String urlString){
        super(actionIn, valueIn);
        /*if(urlString != null){
            Minecraft.getMinecraft().displayGuiScreen(new ImagePreviewer(urlString));
        }*/
    }
}
