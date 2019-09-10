package me.darkeyedragon.imageutils.client.imageuploader;

import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;

public interface Uploader {
    void upload(BufferedImage bufferedImage);

    int getResponse(HttpURLConnection urlConnection);

    void notifyPlayer();
}
