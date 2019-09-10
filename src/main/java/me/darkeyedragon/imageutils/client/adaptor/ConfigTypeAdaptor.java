package me.darkeyedragon.imageutils.client.adaptor;

public class ConfigTypeAdaptor {
    private boolean Copy;
    private boolean CustomUploader;
    private boolean Override;
    private String Uploader;

    public ConfigTypeAdaptor(boolean copy, boolean customServer, boolean override, String uploader) {
        this.Override = override;
        this.Copy = copy;
        this.CustomUploader = customServer;
        this.Uploader = uploader;
    }

    public boolean isOverride() {
        return Override;
    }

    public boolean isCopy() {
        return Copy;
    }

    public boolean isCustomUploader() {
        return CustomUploader;
    }

    public String getUploader() {
        return Uploader;
    }
}
