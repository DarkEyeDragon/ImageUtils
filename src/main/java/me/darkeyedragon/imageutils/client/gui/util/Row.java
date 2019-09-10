package me.darkeyedragon.imageutils.client.gui.util;


import me.darkeyedragon.imageutils.client.utils.ImageResource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Row extends GuiScreen {

    private final int WIDTH;
    private final int OFFSET_Y;
    private final List<ImagePreview> content;
    private short allowedImages;
    private Map<String, ResourceLocation> resources;


    public Row(int width, int offsetY, Map<String, ResourceLocation> resources) {
        this.WIDTH = width;
        this.OFFSET_Y = offsetY;
        this.content = new ArrayList<>();
        this.resources = resources;
    }

    public void addElement(ImagePreview imagePreview) {
        if (allowedImages > content.size()) {
            content.add(imagePreview);
        }
    }

    public short calculateAllowedImages(int width, int height) {
        int totalUsedSpace = 0;
        short index = 0;
        while (totalUsedSpace < WIDTH) {
            totalUsedSpace += width + height;
            index++;
        }
        return index;
    }

    public short getAllowedImages() {
        return allowedImages;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        ImagePreview imgPrev;
        ImageResource resource;
        for (byte x = 0; x < allowedImages; x++) {
            imgPrev = content.get(x);
            resource = imgPrev.getImageResource();
            String imgName = resource.getName();
            Margin margin = imgPrev.getMargin();
            int x_axis = (margin.getLeft() + imgPrev.getWidth() + imgPrev.getMargin().getRight()) * x;
            Minecraft.getMinecraft().getTextureManager().bindTexture(resources.get(imgName));
            drawModalRectWithCustomSizedTexture(x_axis, OFFSET_Y, 0, 0, imgPrev.getWidth(), imgPrev.getHeight(), imgPrev.getWidth(), imgPrev.getHeight());
            drawCenteredString(mc.fontRenderer, imgName, x + fontRenderer.getStringWidth(imgName) / 2, OFFSET_Y + imgPrev.getHeight() + 10, 0xffffff);
        }
    }
}
