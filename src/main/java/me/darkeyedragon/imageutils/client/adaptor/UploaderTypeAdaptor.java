package me.darkeyedragon.imageutils.client.adaptor;

import java.util.Map;

public class UploaderTypeAdaptor {
    private String RequestURL;
    private String FileFormName;
    private String URL;
    private String DeletionURL;
    private Map<String, Object> Arguments;


    public String getRequestUrl() {
        return RequestURL;
    }

    public String getFileFormName() {
        return FileFormName;
    }

    public Map<String, Object> getArgumentsTypeAdaptor() {
        return Arguments;
    }

    public String getURL() {
        return URL;
    }

}
