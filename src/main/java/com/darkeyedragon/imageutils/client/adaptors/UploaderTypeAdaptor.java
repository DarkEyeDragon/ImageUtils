package com.darkeyedragon.imageutils.client.adaptors;

import java.util.Map;

public class UploaderTypeAdaptor{
    private String RequestURL;
    private String FileFormName;
    private Map<String, Object> Arguments;

    public String getRequestUrl(){
        return RequestURL;
    }

    public String getFileFormName(){
        return FileFormName;
    }
    public Map<String, Object> getArgumentsTypeAdaptor(){
        return Arguments;
    }
}
