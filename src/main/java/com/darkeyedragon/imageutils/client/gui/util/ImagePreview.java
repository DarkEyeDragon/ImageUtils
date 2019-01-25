package com.darkeyedragon.imageutils.client.gui.util;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.utils.ImageResource;
import net.coobird.thumbnailator.Thumbnails;
import net.minecraft.client.gui.GuiScreen;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImagePreview extends GuiScreen {

    private final ImageResource imageResource;

    private int width;
    private int height;

    private Margin margin;

    public ImagePreview(ImageResource imageResource, int width, int height) {
        this.imageResource = imageResource;
        this.width = width;
        this.height = height;
    }
    public BufferedImage getPreview(){
        try {
            return Thumbnails.of(imageResource.getImage()).size(width, height).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
            ImageUtilsMain.logger.error(e);
        }
        return null;
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ImageResource getImageResource() {
        return imageResource;
    }

    public Margin getMargin() {
        return margin;
    }

    public void setMargin(Margin margin) {
        this.margin = margin;
    }
}
