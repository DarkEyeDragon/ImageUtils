package me.darkeyedragon.imageutils.client.image;

import java.awt.image.BufferedImage;

public class DownloadImage {

    private DownloadResponse downloadResponse;
    private BufferedImage bufferedImage;

    public DownloadImage(BufferedImage bufferedImage, DownloadResponse downloadResponse) {
        this.downloadResponse = downloadResponse;
        this.bufferedImage = bufferedImage;
    }

    public DownloadResponse getResponseMessage() {
        return downloadResponse;
    }

    public void setResponseMessage(DownloadResponse responseMessage) {
        this.downloadResponse = responseMessage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
