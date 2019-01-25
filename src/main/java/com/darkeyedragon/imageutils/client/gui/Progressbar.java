package com.darkeyedragon.imageutils.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class Progressbar extends GuiScreen {
    //TODO implement progressbar

    private int x;
    private int y;
    private int length;
    private int height;
    private float maxProgress;
    private float progress;
    private float actualValue;
    public Progressbar(int x, int y, int length, int height) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.height = height;
    }

    @Override
    public void initGui() {
        super.initGui();
        mc = Minecraft.getMinecraft();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawGradientRect(x, y, x + length, y + height, 0xffffffff, 0xffffffff);
        int value = (int)((progress/maxProgress)*length);
        this.drawGradientRect(x, y, x+value, y + height, 0xFFE24C19, 0xFFE24C19);
        this.drawCenteredString(mc.fontRenderer, (int)progress+"/"+(int)maxProgress, x+(length/2), y + (height/2)-4, 0xFFFFFF);
        this.drawCenteredString(mc.fontRenderer, I18n.format("imageutil.gui.local_screenshots.loading"),x+(length/2), y - (height/2)-4, 0xFFFFFF);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }
}
