package me.darkeyedragon.imageutils.client.adaptor;

public class CustomResponseAdaptor implements UploadResponseAdaptor {

    private final String url;
    private final int status;

    public CustomResponseAdaptor(String url, int status) {
        this.url = url;
        this.status = status;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public int getStatus() {
        return status;
    }
}
