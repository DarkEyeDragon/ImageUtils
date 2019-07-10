package me.darkeyedragon.imageutils.client.gui;

import me.darkeyedragon.imageutils.client.ImageUtils;
import me.darkeyedragon.imageutils.client.config.UploaderFile;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public abstract class GuiSideBarBase extends Screen {

    int barWidth = 150;
    private String title = "title";
    private int titleColor = 0xffffff;

    protected GuiSideBarBase(ITextComponent p_i51108_1_) {
        super(p_i51108_1_);
    }

    @Override
    public void initGui (){
        super.initGui();
        int index = 0;
        GuiLabel label = new GuiLabel(mc.fontRenderer, index++, width - barWidth, 15, barWidth, 30, titleColor);
        label.addLine("Imgur");
        label.setCentered();
        labelList.add(label);
        for (UploaderFile uploaderFile : ImageUtils.uploaders) {
            label = new GuiLabel(mc.fontRenderer, index++, width - barWidth, index * 10 + 15, 100, 30, titleColor);
            label.addLine(uploaderFile.getFileName());
            label.setCentered();
            labelList.add(label);
        }
    }

    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks){
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawRect(width - barWidth, 0, width, height, 0x55000000);
        drawCenteredString(mc.fontRenderer, title, width - barWidth / 2, 10, titleColor);
    }

    public String getTitle (){
        return title;
    }

    public void setTitle (String title){
        this.title = title;
    }

    public int getTitleColor (){
        return titleColor;
    }

    public void setTitleColor (int titleColor){
        this.titleColor = titleColor;
    }

    public int getWidth (){
        return barWidth;
    }

    public void setWidth (int width){
        this.barWidth = width;
    }

    @Override
    public boolean doesGuiPauseGame (){
        return false;
    }
}
