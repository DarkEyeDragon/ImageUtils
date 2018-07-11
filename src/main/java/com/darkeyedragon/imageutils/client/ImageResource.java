package com.darkeyedragon.imageutils.client;

import java.awt.image.BufferedImage;

public class ImageResource{
    private String name;
    private BufferedImage image;
    private boolean selected;
    private String path;
    public ImageResource(String name, BufferedImage image){
        this.name = name;
        this.image = image;
    }
    public ImageResource(String name, BufferedImage image, boolean selected, String path){
        this.name = name;
        this.image = image;
        this.selected = selected;
        this.path = path;
    }
    public String getName(){
        return name;
    }

    public BufferedImage getImage(){
        return image;
    }

    public boolean isSelected(){
        return selected;
    }
    public void setSelected(boolean value){
        selected = value;
    }
    public String getPath(){
        return path;
    }
}
