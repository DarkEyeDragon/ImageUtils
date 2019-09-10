package me.darkeyedragon.imageutils.client.gui;

import me.darkeyedragon.imageutils.client.config.UploaderFile;
import me.darkeyedragon.imageutils.client.gui.lists.GuiList;

import java.util.List;

public class GuiSelectUploader extends GuiList {

    public GuiSelectUploader(List<UploaderFile> uploaderFileList) {
        super("Select uploader script", uploaderFileList);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawWorldBackground(0xffffffff);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
