package me.darkeyedragon.imageutils.client;

import me.darkeyedragon.imageutils.client.config.UploaderFile;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UploadHandler {
    //Create a thread pool for the uploaders
    private final ExecutorService fixedThreadPool;

    //Keep a list of valid image links but only keep track of the 7 most recent ones
    private final LinkedHashMap<String, BufferedImage> validLinks;

    private final List<UploaderFile> uploaders;
    private final ImageUtilsMain main;
    private UploaderFile activeUploader;

    public UploadHandler(ImageUtilsMain main) {
        this.main = main;
        fixedThreadPool = Executors.newFixedThreadPool(2);
        validLinks = new LinkedHashMap<String, BufferedImage>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, BufferedImage> eldest) {
                return size() > 7;
            }
        };
        uploaders = new ArrayList<>();
    }

    public void setActiveUploader(String uploaderName) {
        uploaders.forEach(uf -> {
            if (uf.getFileName().equalsIgnoreCase(uploaderName) || uf.getDisplayName().equalsIgnoreCase(uploaderName)) {
                getLogger().info("Setting active uploader script.");
                setActiveUploader(uf);
            } else {
                throw new IllegalArgumentException();
            }
        });
    }

    public List<UploaderFile> getUploaders() {
        return uploaders;
    }

    public UploaderFile getActiveUploader() {
        return activeUploader;
    }

    public void setActiveUploader(UploaderFile activeUploader) {
        this.activeUploader = activeUploader;
    }

    public ImageUtilsMain getMain() {
        return main;
    }

    public Logger getLogger() {
        return main.getLogger();
    }

    public void loadUploaders() {
        unloadUploaders();
        getLogger().info("Uploaders directory found, loading uploaders...");
        String[] list = main.getUploadDir().list();
        if (list != null && list.length > 0) {
            List<String> displayName = new ArrayList<>();
            for (File file : Objects.requireNonNull(main.getUploadDir().listFiles())) {
                try {
                    UploaderFile uf = new UploaderFile(file);
                    uploaders.add(uf);
                    displayName.add(uf.getDisplayName());
                    getLogger().info("Loaded: " + file.getName());
                } catch (Exception e) {
                    getLogger().warn("Unable to load " + file.getName() + e.getMessage() + "!");
                }
            }
        }
    }

    public void unloadUploaders() {
        uploaders.clear();
    }

    public ExecutorService getFixedThreadPool() {
        return fixedThreadPool;
    }

    public LinkedHashMap<String, BufferedImage> getValidLinks() {
        return validLinks;
    }
}
