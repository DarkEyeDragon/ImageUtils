package me.darkeyedragon.imageutils.client.image;

import net.minecraft.util.text.TextComponentTranslation;

public enum DownloadResponse {
    FILE_TO_BIG(new TextComponentTranslation("imageutil.message.download_response.file_to_big").getUnformattedComponentText()),
    SUCCESS("imageutil.message.download_response.success"),
    ERROR("imageutil.message.download_response.error");

    private final String message;

    DownloadResponse(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
