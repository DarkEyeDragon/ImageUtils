package me.darkeyedragon.imageutils.client.imageuploader;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.client.ModConfig;

public class UploaderFactory {

    private final ImageUtilsMain main;

    public UploaderFactory(ImageUtilsMain main) {
        this.main = main;
    }

    public Uploader getUploader() {
        if (ModConfig.customServer) {
            return new CustomUploader(main.getUploadHandler());
        } else {
            return new ImgurUploader(main);
        }
    }
}
