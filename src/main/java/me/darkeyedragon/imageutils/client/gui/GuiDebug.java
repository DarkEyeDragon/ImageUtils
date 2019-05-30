package me.darkeyedragon.imageutils.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;

public class GuiDebug extends GuiIngame{
    private FontRenderer fr = mc.fontRenderer;

    public GuiDebug (Minecraft mcIn){
        super(mcIn);
        drawCenteredString(fr, "This is a test", mc.displayWidth - 30, 20, 0xffffff);
    }

}
