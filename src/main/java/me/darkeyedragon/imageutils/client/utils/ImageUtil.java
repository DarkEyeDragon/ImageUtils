package me.darkeyedragon.imageutils.client.utils;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.client.message.ClientMessage;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class ImageUtil {


    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        try {
            return Thumbnails.of(img).size(newW, newH).asBufferedImage();
        } catch (IOException e) {
            ClientMessage.basic("Unable to resize image for preview!");
            return null;
        }
    }

    public static BufferedImage downloadFromUrl(String url, Map<String, BufferedImage> linkList) throws IOException {

        if (StringFilter.isValidUrl(url)) {
            URL imgUrl = new URL(url);
            if (!StringFilter.isValidImage(url)) {
                imgUrl = new URL(url + ".png");
            }
            if (StringFilter.isValidImage(url)) {
                URLConnection conn;
                conn = imgUrl.openConnection();
                conn.setRequestProperty("User-Agent", "ScreenshotUploader/" + ImageUtilsMain.VERSION);
                InputStream image = conn.getInputStream();
                BufferedImage img = ImageIO.read(image);
                linkList.put(imgUrl.toString(), img);
                return img;
            }
        }
        return null;
    }

    private static void addToLinkList(String urlString, BufferedImage downloadedImage) {
    }

    public static BufferedImage getLocal(File location) throws IOException {
        return ImageIO.read(location);
    }
}
