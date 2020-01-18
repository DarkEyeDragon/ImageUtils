package me.darkeyedragon.imageutils.client.util;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.client.ScreenshotHandler;
import me.darkeyedragon.imageutils.client.image.DownloadImage;
import me.darkeyedragon.imageutils.client.image.DownloadResponse;
import me.darkeyedragon.imageutils.client.image.ImageType;
import me.darkeyedragon.imageutils.client.message.ClientMessage;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImageUtil {

    private static final byte BYTES_TO_READ = 12;

    /**
     * @param img  the image to resize
     * @param newW the new width
     * @param newH the new height
     * @return the resized image
     */
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        try {
            return Thumbnails.of(img).size(newW, newH).asBufferedImage();
        } catch (IOException e) {
            ClientMessage.basic("Unable to resize image for preview!");
            return null;
        }
    }


    /**
     * Checks if an url contains a valid image by checking the first 8 bytes of the content body. They represent an {@link ImageType}
     *
     * @param imageUrl the url of the image
     * @return returns true if the first bytes represent a image type
     */
    public static boolean isValidImage(String imageUrl) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(imageUrl);
        httpGet.addHeader(HttpHeaders.USER_AGENT, ImageUtilsMain.NAME + "/" + ImageUtilsMain.VERSION);
        httpGet.addHeader(HttpHeaders.RANGE, "bytes=0-" + (BYTES_TO_READ - 1));
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity.getContentLength() > BYTES_TO_READ) return false;
            List<Integer> bytes = new ArrayList<>(4);
            int byteValue = 0;
            while (byteValue != -1) {
                int byt = httpEntity.getContent().read();
                bytes.add(byt);
                byteValue = byt;
            }

            for (int i = 0; i < ImageType.values().length; i++) {
                if (ImageType.values()[i].compare(bytes)) {
                    return true;
                }
            }
            System.out.println(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param url the url to download the image from
     * @return the downloaded image
     * @throws IOException thrown when no connection could be made to the url
     */
    public static DownloadImage downloadFromUrl(String url, int maxSize) throws IOException {

        Map<String, BufferedImage> downloads = ScreenshotHandler.getDownloadList();
        if (downloads.containsKey(url)) {
            return new DownloadImage(downloads.get(url), DownloadResponse.SUCCESS);
        }

        if (!StringFilter.isValidUrl(url) || !isValidImage(url)) {
            return new DownloadImage(null, DownloadResponse.ERROR);
        }

        URL imgUrl = new URL(url);
        if (isValidImage(url)) {
            URLConnection conn;
            conn = imgUrl.openConnection();
            conn.setRequestProperty(HttpHeaders.USER_AGENT, ImageUtilsMain.NAME + "/" + ImageUtilsMain.VERSION);
            //Devide by 1000000 to get MB
            if (conn.getContentLength() / 1000000 > maxSize) {
                return new DownloadImage(null, DownloadResponse.FILE_TO_BIG);
            }
            InputStream image = conn.getInputStream();
            BufferedImage img = ImageIO.read(image);
            downloads.put(imgUrl.toString(), img);
            return new DownloadImage(img, DownloadResponse.SUCCESS);
        }
        return new DownloadImage(null, DownloadResponse.ERROR);
    }

    public void getImageSize(String url) {

    }

    public static BufferedImage getLocal(File location) throws IOException {
        return ImageIO.read(location);
    }
}
