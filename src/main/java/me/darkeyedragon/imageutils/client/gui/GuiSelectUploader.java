package me.darkeyedragon.imageutils.client.gui;

import me.darkeyedragon.imageutils.client.gui.lists.GuiList;

public class GuiSelectUploader extends GuiList{

    public GuiSelectUploader (){
        super("Select uploader script");
    }

    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks){
        drawWorldBackground(0xffffffff);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
