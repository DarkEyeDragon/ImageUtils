package com.darkeyedragon.screenshotuploader.client.gui;

import com.darkeyedragon.screenshotuploader.client.ScreenshotMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class CustomGui extends GuiScreen{

    private ResourceLocation icons = new ResourceLocation(ScreenshotMain.MODID, "some path");


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        System.out.println(icons);
        Minecraft.getMinecraft().renderEngine.bindTexture(icons);
        int iconWidth = 165;
        int iconHeight = 165;
        drawTexturedModalRect(0,0,0,0, iconWidth, iconHeight);
    }

    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }

    @Override
    public void initGui() {

        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button){

    }
}
