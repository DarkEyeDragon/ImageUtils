package me.darkeyedragon.imageutils.client.image;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Screenshot {

    private String imageName;
    private final File file;
    private Path path;

    public Screenshot(String imageName) {
        this.imageName = imageName;
        file = new File(imageName);
        path = ImageUtilsMain.getScreenshotDir();
    }

    public boolean exists(){
        return file.exists();
    }

    public void getAsBufferedImage() throws IOException {
        BufferedImage image = ImageIO.read(file);
    }

    public String getImageName() {
        return imageName;
    }

    public File getFile() {
        return file;
    }

    /*public boolean setImageName(String imageName) {
        if(file.renameTo(new File(n imageName))){
            this.imageName = imageName;
            return true;
        }
        return false;
    }*/

    public Path getPath() {
        return path;
    }
}
