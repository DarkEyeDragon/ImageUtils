package me.darkeyedragon.imageutils.client.imageuploader;

import org.apache.http.HttpResponse;

import java.awt.image.BufferedImage;
import java.util.function.BiConsumer;

public interface Uploader {
    HttpResponse upload(BufferedImage bufferedImage);

    void uploadAsync(BufferedImage bufferedImage, BiConsumer<HttpResponse, Throwable> callback);

    HttpResponse getHttpResponse();
}
