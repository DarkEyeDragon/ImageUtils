package me.darkeyedragon.imageutils.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class RegionSelector extends GuiScreen {

    private final int LINE_LENGTH;

    public RegionSelector(int lineLength) {
        this.LINE_LENGTH = lineLength;
    }

    public RegionSelector() {
        this(10);
    }

    public void drawRegion(int startX, int startY, int endX, int endY) {
        int length = endX - startX;
        int height = endY - startY;

        //Striped line

        //Draw top lines
        int color = 0xFFFFFFFF;
        for (int x = 0; x < length / LINE_LENGTH; x++) {
            if (x % 2 == 0) {
                drawHorizontalLine(startX + (x * LINE_LENGTH), startX + (x * LINE_LENGTH) + LINE_LENGTH, startY, color);
            }
        }
        //Draw left lines
        for (int y = 0; y < height / LINE_LENGTH; y++) {
            if (y % 2 == 0) {
                drawVerticalLine(startX, startY + (y * LINE_LENGTH), startY + (y * LINE_LENGTH) + LINE_LENGTH, color);
            }
        }
        //Draw bottom lines
        for (int x = 0; x < length / LINE_LENGTH; x++) {
            if (x % 2 == 0) {
                drawHorizontalLine(startX + (x * LINE_LENGTH), startX + (x * LINE_LENGTH) + LINE_LENGTH, endY, color);
            }
        }

        //Draw right lines
        for (int y = 0; y < height / LINE_LENGTH; y++) {
            if (y % 2 == 0) {
                drawVerticalLine(endX, startY + (y * LINE_LENGTH), startY + (y * LINE_LENGTH) + LINE_LENGTH, color);
            }
        }

        // Corners


        //Top left
        drawHorizontalLine(startX, startX + LINE_LENGTH, startY, color);
        drawVerticalLine(startX, startY, startY + LINE_LENGTH, color);

        //Top right
        drawHorizontalLine(endX - LINE_LENGTH, endX, startY, color);
        drawVerticalLine(endX, startY + LINE_LENGTH, startY, color);

        //Bottom right
        drawHorizontalLine(endX - LINE_LENGTH, endX, endY, color);
        drawVerticalLine(endX, endY, endY - LINE_LENGTH, color);

        //Bottom left
        drawHorizontalLine(startX, startX + LINE_LENGTH, endY, color);
        drawVerticalLine(startX, endY - LINE_LENGTH, endY, color);

        //Darken the outside
    }

    public void drawBackground(int startX, int startY, int endX, int endY) {
        int backgroundColor = 0x66000000;
        int height = Minecraft.getMinecraft().displayHeight;
        int width = Minecraft.getMinecraft().displayWidth;
        drawRect(0, 0, startX, height, backgroundColor);
        drawRect(startX, 0, width - startX, startY, backgroundColor);
        drawRect(width, startY, endX + 1, height, backgroundColor);
        drawRect(startX, height, endX + 1, endY + 1, backgroundColor);
    }
}
