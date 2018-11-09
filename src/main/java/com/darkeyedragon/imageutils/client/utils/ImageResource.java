package com.darkeyedragon.imageutils.client.utils;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import org.apache.commons.io.FilenameUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

public class ImageResource{
    private String name;
    private BufferedImage image;
    private boolean selected;
    private String path;
    private String url;

    private String extension;

    public ImageResource (String name, BufferedImage image){
        this.name = name;
        this.image = image;
    }

    public ImageResource (String name, BufferedImage image, String url){
        this.name = name;
        this.image = image;
        this.url = url;
    }

    public ImageResource (String name, BufferedImage image, boolean selected, String path){
        this.name = name;
        this.image = image;
        this.selected = selected;
        this.path = path;
        this.extension = FilenameUtils.getExtension(name);
    }

    public String getExtension (){
        return extension;
    }

    public String getName (){
        return name;
    }

    public boolean setName (String name){
        this.name = name;
        File oldFile = new File(path);
        File newFile = Paths.get(oldFile.getParent(), name).toFile();
        boolean addExtention = !FilenameUtils.getExtension(name).endsWith("png");
        if (newFile.exists()){
            ImageUtilsMain.logger.warn("Image already exists!");
        }else{
            if (addExtention){
                newFile = Paths.get(oldFile.getParent(), name + ".png").toFile();
            }
            if (oldFile.renameTo(newFile)){
                ImageUtilsMain.logger.info("Image " + oldFile.getName() + " renamed to " + newFile.getName() + " successfully.");
                return true;
            }else{
                ImageUtilsMain.logger.warn("Could not rename " + oldFile.getName() + "!");
                return false;
            }
        }
        return false;
    }

    public BufferedImage getImage (){
        return image;
    }

    public boolean isSelected (){
        return selected;
    }

    public void setSelected (boolean value){
        selected = value;
    }

    public String getPath (){
        return path;
    }

    public String getUrl (){
        return url;
    }

    public void setUrl (String url){
        this.url = url;
    }
}
