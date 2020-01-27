package me.darkeyedragon.imageutils.image;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class Screenshot implements Closeable {
    private Path relativePath;
    private String name;
    private File file;
    private ResourceLocation resourceLocation;
    private int width;
    private int height;
    private Minecraft mc = Minecraft.getInstance();

    public Screenshot(String name, File file) throws IOException {
        this.name = name;
        this.file = file;
        this.relativePath = file.toPath();
        try (FileInputStream stream = new FileInputStream(file)) {
            this.resourceLocation = mc.getRenderManager().textureManager.getDynamicTextureLocation(file.getName(), new DynamicTexture(NativeImage.read(stream)));
        }
    }

    public Screenshot(File file) throws IOException {
        this(file.getName(), file);
    }

    public Path getRelativePath() {
        return relativePath;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public ResourceLocation getResourceLocation() {
        if (resourceLocation == null) {
            try (FileInputStream stream = new FileInputStream(file); NativeImage nativeImage = NativeImage.read(stream)) {
                this.resourceLocation = mc.getRenderManager().textureManager.getDynamicTextureLocation(file.getName(), new DynamicTexture(nativeImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resourceLocation;
    }

    @Override
    public void close() {
        mc.getTextureManager().deleteTexture(resourceLocation);
    }
}
