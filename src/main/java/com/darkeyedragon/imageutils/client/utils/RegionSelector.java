package com.darkeyedragon.imageutils.client.utils;

import net.minecraft.client.gui.GuiScreen;


public class RegionSelector extends GuiScreen{

    public void drawRegion (int startX, int startY, int endX, int endY){
        int color = 0xFFFFFFFF;
        int length = endX-startX;
        int height = endY-startY;

        final int LINE_LENGTH = 10;


        for (int x = 0; x < length; x++){
            if(x %2 == 0){
                if(x>0)
                    //TODO FIX SELECTION
                    drawHorizontalLine(startX, startX*x-LINE_LENGTH, startY, color);
            }
        }
        for (int y = 0; y < height/(2* LINE_LENGTH); y++){
            if(y %2 == 0){
                if(y>0)
                    drawVerticalLine(startX, startY*y, startY*y-LINE_LENGTH, color);
            }
        }
        //Top left
        drawHorizontalLine(startX, startX + 10, startY, color);
        drawVerticalLine(startX, startY, startY + 10, color);

        //Top right
        drawHorizontalLine(endX - 10, endX, startY, color);
        drawVerticalLine(endX, startY + 10, startY, color);

        //Bottom right
        drawHorizontalLine(endX - 10, endX, endY, color);
        drawVerticalLine(endX, endY, endY - 10, color);

        //Bottom left
        drawHorizontalLine(startX, startX + 10, endY, color);
        drawVerticalLine(startX, endY - 10, endY, color);
    }
}
