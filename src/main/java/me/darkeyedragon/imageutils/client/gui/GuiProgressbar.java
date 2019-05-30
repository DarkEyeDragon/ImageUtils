package me.darkeyedragon.imageutils.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiProgressbar extends GuiScreen{

    private GuiScreen parent;

    public GuiProgressbar (GuiScreen parent){
        this.parent = parent;
    }

    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks){
        parent.drawScreen(mouseX, mouseY, partialTicks);
        drawDefaultBackground();
        drawProgressbar();
    }

    public void drawProgressbar (){
        drawHorizontalLine(20, 80, 50, 0xff00bfff);
        drawRect(width / 2 - 100, height / 2 - 2, width / 2 + 100, height / 2 + 10, 0xff00bfff);
        drawCenteredString(Minecraft.getMinecraft().fontRenderer, "Uploading...", width / 2, height / 2, 0xffffffff);
    }
}
