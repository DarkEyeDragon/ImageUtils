package com.darkeyedragon.imageutils.client.utils;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.message.ClientMessage;
import net.coobird.thumbnailator.Thumbnails;
import net.minecraft.client.Minecraft;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageUtil{
    public static BufferedImage resize (BufferedImage img, int newW, int newH){
        try{
            return Thumbnails.of(img).size(newW, newH).asBufferedImage();
        }
        catch (IOException e){
            ClientMessage.basic("Unable to resize image for preview!");
            return null;
        }
    }

    public static BufferedImage downloadFromUrl (String url) throws IOException{

        if (StringFilter.isValidUrl(url)){
            URL imgUrl = new URL(url);
            if (!StringFilter.isValidImage(url)){
                imgUrl = new URL(url + ".png");
            }
            if (StringFilter.isValidImage(url)){
                URLConnection conn;
                conn = imgUrl.openConnection();
                conn.setRequestProperty("User-Agent", "ScreenshotUploader/" + ImageUtilsMain.VERSION);
                InputStream image = conn.getInputStream();
                BufferedImage img = ImageIO.read(image);
                addToLinkList(imgUrl.toString(), img);
                return img;
            }
        }
        return null;
    }

    public static synchronized void addToLinkList (String urlString, BufferedImage downloadedImage){
        Minecraft.getMinecraft().addScheduledTask(() -> {
            ImageUtilsMain.validLinks.put(urlString, downloadedImage);
        });
    }

    public static BufferedImage getLocal (File location) throws IOException{
        return ImageIO.read(location);
    }
}
