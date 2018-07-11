package com.darkeyedragon.imageutils.client.utils;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class Stickers extends GuiScreen{
    private ResourceLocation icons = new ResourceLocation(ImageUtilsMain.MODID, "assets/stickers/");


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        Minecraft.getMinecraft().renderEngine.bindTexture(icons);
        int iconWidth = 165;
        int iconHeight = 165;
        drawTexturedModalRect(0,0,0,0, iconWidth, iconHeight);
    }

}
