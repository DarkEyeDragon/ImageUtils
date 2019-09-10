package me.darkeyedragon.imageutils.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBindings {

    public static KeyBinding screenshotUploadKey;
    public static KeyBinding screenshotPartialUploadKey;
    public static KeyBinding screenshotViewer;

    void RegisterKeybinds() {
        screenshotUploadKey = new KeyBinding("Upload screenshot", Keyboard.KEY_F4, "Screenshot UploaderFile");
        ClientRegistry.registerKeyBinding(screenshotUploadKey);
        screenshotPartialUploadKey = new KeyBinding("Partial Screenshots", Keyboard.KEY_F6, "Screenshot UploaderFile");
        ClientRegistry.registerKeyBinding(screenshotPartialUploadKey);
        screenshotViewer = new KeyBinding("View local screenshots", Keyboard.KEY_F7, "Screenshot UploaderFile");
        ClientRegistry.registerKeyBinding(screenshotViewer);
    }
}
