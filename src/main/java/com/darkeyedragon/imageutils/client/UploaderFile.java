package com.darkeyedragon.imageutils.client;

import com.darkeyedragon.imageutils.client.adaptors.UploaderTypeAdaptor;
import com.google.gson.Gson;
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

    public UploaderFile(File file) throws Exception{
        if(file.isFile()){
            Path path = Paths.get(file.getPath());
            String contents = new String(Files.readAllBytes(path));
            uploader = new Gson().fromJson(contents, UploaderTypeAdaptor.class);
            arguments = uploader.getArgumentsTypeAdaptor();
            fileName = file.getName();
            String cap = fileName.substring(0, 1).toUpperCase() + fileName.substring(1);
            displayName = FilenameUtils.removeExtension(cap);
        }
    }
    public Map<String, Object> getArguments(){
        return arguments;
    }
    public UploaderTypeAdaptor getUploader(){
        return uploader;
    }

    public String getFileName(){
        return fileName;
    }
    public String getDisplayName() { return  displayName; }
}
