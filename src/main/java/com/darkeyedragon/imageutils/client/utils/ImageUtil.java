package com.darkeyedragon.imageutils.client.utils;

import com.darkeyedragon.imageutils.client.message.SendClientMessage;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageUtil{
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        try{
            return Thumbnails.of(img).size(newW, newH).asBufferedImage();
        }catch (IOException e){
            SendClientMessage.basic("Unable to resize image for preview!");
            return null;
        }
    }
    public static BufferedImage downloadFromUrl(URL imgUrl) throws IOException{
        URLConnection conn;
        conn = imgUrl.openConnection();
        conn.setRequestProperty("User-Agent", "ScreenshotUploader/1.2");
        InputStream image = conn.getInputStream();
        return ImageIO.read(image);
    }
}
