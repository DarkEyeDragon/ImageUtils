package me.darkeyedragon.imageutils.util.image;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.image.Screenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocalScreenshots {

    public static List<File> getAsFile() throws IOException {
        Path path = ImageUtilsMain.getLocalScreenshotDir();

        try (Stream<Path> paths = Files.walk(path)) {
            return paths
                    .filter(Files::isRegularFile).map(Path::toFile)
                    .collect(Collectors.toList());
        }

    }

    public static List<Screenshot> getAsResourceLocation() throws IOException {
        List<File> files = getAsFile();
        return files.stream().map(file -> {
            try {
                return new Screenshot(file.getName(), file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    public static CompletableFuture<List<Screenshot>> getAsResourceLocationAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getAsResourceLocation();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
}
