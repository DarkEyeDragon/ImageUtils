package com.darkeyedragon.imageutils.client.utils;

import net.minecraft.client.gui.GuiScreen;

public class RegionSelector extends GuiScreen{
    public void drawRegion(int startX, int startY,int endX, int endY){
        int color = 0xFFFFFFFF;

        //Top left
        drawHorizontalLine(startX, startX + 10, startY, color);
        drawVerticalLine(startX, startY, startY + 10, color);

        //Top right
        drawHorizontalLine(endX-10, endX, startY, color);
        drawVerticalLine(endX, startY+10, startY, color);

        //Bottom right
        drawHorizontalLine(endX-10, endX, endY, color);
        drawVerticalLine(endX, endY, endY-10, color);

        //Bottom left
        drawHorizontalLine(startX, startX+10, endY, color);
        drawVerticalLine(startX, endY-10, endY, color);
    }
}
