package me.darkeyedragon.imageutils.client.adaptor;

import java.util.Map;

public class ImgurResponseTypeAdaptor implements UploadResponseAdaptor {

    private Map<String, Object> data;
    private boolean success;
    private int status;

    public ImgurResponseTypeAdaptor(Map<String, Object> data, boolean success, int status) {
        this.data = data;
        this.success = success;
        this.status = status;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public int getId() {
        return (int) data.get("id");
    }

    public String getTitle() {
        return (String) data.get("title");
    }

    public String getDescription() {
        return (String) data.get("description");
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getURL() {
        return (String) data.get("url");
    }

    public int getStatus() {
        return status;
    }

}
