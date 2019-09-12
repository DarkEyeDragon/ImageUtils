package me.darkeyedragon.imageutils.client.imageuploader;

import org.apache.http.HttpResponse;

import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;

public interface Uploader {
    HttpResponse upload(BufferedImage bufferedImage);
    HttpResponse getHttpResponse();
}
