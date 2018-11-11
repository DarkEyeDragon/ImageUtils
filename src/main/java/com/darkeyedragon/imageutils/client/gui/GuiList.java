package com.darkeyedragon.imageutils.client.gui;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;

import java.util.List;

public class GuiList extends GuiSlot{

    Minecraft mc;
    List<String> list;
    GuiScreen screen;
    int xStart;
    int yStart;

    GuiList (Minecraft mcIn, int width, int height, List<String> arrayList, GuiScreen guiScreen, int xStart, int yStart){
        super(mcIn, width, height, 32, height - 65 + 4, 20);
        this.xStart = xStart;
        this.yStart = yStart;
        screen = guiScreen;
        mc = mcIn;
        list = arrayList;
    }

    /**
     * Sets the left and right bounds of the slot. Param is the left bound, right is calculated as left + width.
     */
    @Override
    public void setSlotXBoundsFromLeft (int leftIn){
        this.left = 0;
        this.right = 0;
    }

    @Override
    protected int getScrollBarX (){
        return (int) Math.round(this.width / 2.5);
    }

    protected int getSize (){
        return list.size();
    }

    /**
     * The element in the slot that was clicked, boolean for whether it was double clicked or not
     */
    public void elementClicked (int slotIndex, boolean isDoubleClick, int mouseX, int mouseY){
        ImageUtilsMain.setActiveUploader();
    }

    @Override
    protected boolean isSelected (int slotIndex){
        return true;
    }

    @Override
    public void setDimensions (int widthIn, int heightIn, int topIn, int bottomIn){
        super.setDimensions(widthIn, heightIn, topIn, bottomIn);
    }

    @Override


    /**
     * Return the height of the content being scrolled
     */
    protected int getContentHeight (){
        return this.getSize() * 20;
    }

    @Override
    protected void drawBackground (){

    }

    protected void drawSlot (int slotIndex, int xPos, int yPos, int heightIn, int mouseXIn, int mouseYIn, float partialTicks){
        screen.drawString(mc.fontRenderer, list.get(slotIndex), this.width / 5 - (this.getListWidth() / 3) + 5, yPos + 1, 16777215);
    }
}
