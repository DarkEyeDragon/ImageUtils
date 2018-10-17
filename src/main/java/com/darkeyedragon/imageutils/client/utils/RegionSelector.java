package com.darkeyedragon.imageutils.client.utils;

import net.minecraft.client.gui.GuiScreen;


public class RegionSelector extends GuiScreen{

    public void drawRegion (int startX, int startY, int endX, int endY){
        int color = 0xFFFFFFFF;
        int length = endX-startX;
        int height = endY-startY;

        final int LINE_LENGTH = 10;

        //Striped line

        //Draw top lines
        for (int x = 0; x < length/LINE_LENGTH; x++){
            if ( x % 2 == 0){
                drawHorizontalLine(startX+(x*LINE_LENGTH), startX+(x*10)+LINE_LENGTH, startY, color);
            }
        }
        //Draw left lines
        for (int y = 0; y < height/LINE_LENGTH; y++){
            if ( y % 2 == 0){
                drawVerticalLine(startX, startY+(y*LINE_LENGTH), startY+(y*10)+LINE_LENGTH, color);
            }
        }
        //Draw bottom lines
        for (int x = 0; x < length/LINE_LENGTH; x++){
            if ( x % 2 == 0){
                drawHorizontalLine(startX+(x*LINE_LENGTH), startX+(x*10)+LINE_LENGTH, endY, color);
            }
        }

        //Draw right lines
        for (int y = 0; y < height/LINE_LENGTH; y++){
            if ( y % 2 == 0){
                drawVerticalLine(endX, startY+(y*LINE_LENGTH), startY+(y*10)+LINE_LENGTH, color);
            }
        }

        // Corners


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

        //Darken the outside
    }
}
