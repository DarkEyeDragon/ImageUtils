package com.darkeyedragon.screenshotuploader.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBindings{

    public static KeyBinding openScreenshotGuiKey;
    public static KeyBinding screenshotUploadKey;


    void RegisterKeybinds(){
        openScreenshotGuiKey = new KeyBinding("Screenshot Settings",Keyboard.KEY_U, "Screenshot Uploader");
        ClientRegistry.registerKeyBinding(openScreenshotGuiKey);

        screenshotUploadKey = new KeyBinding("Screenshot Uploader",Keyboard.KEY_F4, "Screenshot Uploader");
        ClientRegistry.registerKeyBinding(screenshotUploadKey);
    }

}
