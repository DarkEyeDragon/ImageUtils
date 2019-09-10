package me.darkeyedragon.imageutils.client.config;

import com.google.gson.Gson;
import me.darkeyedragon.imageutils.client.adaptor.ConfigTypeAdaptor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigFile {
    private File file;
    private ConfigTypeAdaptor configType;
    private String fileName;
    private String path;

    public ConfigFile(File file) throws Exception {
        //if(file.isFile()){
        Path path = Paths.get(file.getPath());
        String contents = new String(Files.readAllBytes(path));
        configType = new Gson().fromJson(contents, ConfigTypeAdaptor.class);
        this.file = file;
        fileName = file.getName();
        this.path = file.getPath();
        //}
    }

    public ConfigFile(Path path) throws Exception {
        //if(file.isFile()){
        String contents = new String(Files.readAllBytes(path));
        configType = new Gson().fromJson(contents, ConfigTypeAdaptor.class);
        fileName = path.toFile().getName();
        this.path = path.toString();
        file = path.toFile();
        //}
    }

    public boolean isCopy() {
        return configType.isCopy();
    }

    public boolean isCustomServer() {
        return configType.isCustomUploader();
    }

    public String getUploader() {
        if (configType.getUploader() == null) {
            return null;
        }
        return configType.getUploader();
    }

    public boolean isOverride() {
        return configType.isOverride();
    }

    public String getPath() {
        return path;
    }
}
