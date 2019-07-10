package me.darkeyedragon.imageutils.client.gui;

import me.darkeyedragon.imageutils.client.ImageUtils;
import me.darkeyedragon.imageutils.client.config.UploaderFile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

import java.util.ArrayList;
import java.util.List;

public class GuiSelectUploader extends GuiSideBarBase {

    GuiList guiList;
    Minecraft mc;
    int width;
    int height;
    List<String> stringList = new ArrayList<>();
    private Screen parent;

    //TODO implement
    public GuiSelectUploader(Screen parent) {
        mc = Minecraft.getInstance();
        this.parent = parent;
        width = mc.displayWidth;
        height = mc.displayHeight;
        stringList.add("Imgur");
        stringList.add("Flickr");
        for (UploaderFile uploaderFile : ImageUtils.uploaders) {
            stringList.add(uploaderFile.getDisplayName());
        }

    }

    @Override
    public void initGui() {
        super.initGui();
        guiList = new GuiList(mc, mc.displayWidth, mc.displayHeight, stringList, this, width - getWidth(), 0);
        guiList.setDimensions(200, height / 2, 0, 0);
    }

    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks){
        super.drawScreen(mouseX, mouseY, partialTicks);
        guiList.drawScreen(mouseX, mouseY, partialTicks);
        setTitle("Select Uploader");
    }
}
