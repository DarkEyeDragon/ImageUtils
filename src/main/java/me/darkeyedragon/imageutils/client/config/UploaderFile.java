package me.darkeyedragon.imageutils.client.config;

import com.google.gson.Gson;
import me.darkeyedragon.imageutils.client.adaptors.UploaderTypeAdaptor;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class UploaderFile{
    private UploaderTypeAdaptor uploader;
    private Map<String, Object> arguments;
    private String fileName;
    private String displayName;
    private String jsonResponseKey;
    private boolean jsonResponse;
    private boolean loaded;

    public UploaderFile (File file) throws Exception{
        if (file.isFile()){
            Path path = Paths.get(file.getPath());
            String contents = new String(Files.readAllBytes(path));
            uploader = new Gson().fromJson(contents, UploaderTypeAdaptor.class);
            arguments = uploader.getArgumentsTypeAdaptor();
            fileName = file.getName();
            String responseString = uploader.getURL();
            String cap = fileName.substring(0, 1).toUpperCase() + fileName.substring(1);
            displayName = FilenameUtils.removeExtension(cap);
            if (uploader.getURL() != null){
                jsonResponse = responseString.startsWith("$json:");
            }
            if (jsonResponse){
                jsonResponseKey = responseString.substring(responseString.indexOf(":") + 1, responseString.lastIndexOf("$"));
            }
            loaded = true;
        }
    }

    public Map<String, Object> getArguments (){
        return arguments;
    }

    public UploaderTypeAdaptor getUploader (){
        return uploader;
    }

    public String getFileName (){
        return fileName;
    }

    public String getDisplayName (){
        return displayName;
    }

    public boolean isJsonResponse (){
        return jsonResponse;
    }

    public String getJsonResponseKey (){
        return jsonResponseKey;
    }

    public boolean isLoaded (){
        return loaded;
    }
}
