package com.darkeyedragon.screenshotuploader.client;


import com.darkeyedragon.screenshotuploader.client.Events.ConfigChanged;
import com.darkeyedragon.screenshotuploader.client.Events.CustomScreenshotEvent;
import com.darkeyedragon.screenshotuploader.client.Events.KeyPressEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = ScreenshotMain.MODID, version = ScreenshotMain.VERSION, updateJSON = "http://darkeyedragon.me/mods/updates/screenshotuploader.json")
public class ScreenshotMain
{
    public static final String MODID = "screenshotuploader";
    static final String VERSION = "1.0";

    private static Configuration config;

    private KeyBindings keybinds = new KeyBindings();

    public static boolean overrideDefaultScreenshotKey;
    public static boolean copyToClipboard;


    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new KeyPressEvent());
        MinecraftForge.EVENT_BUS.register(new CustomScreenshotEvent());
        MinecraftForge.EVENT_BUS.register(new ModConfig());
        MinecraftForge.EVENT_BUS.register(new ConfigChanged());

        keybinds.RegisterKeybinds();
    }
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(new ConfigChanged());
        config = new Configuration(event.getSuggestedConfigurationFile());
        overrideDefaultScreenshotKey = config.getCategory(Configuration.CATEGORY_GENERAL).get("Override").getBoolean();
        copyToClipboard = config.getCategory(Configuration.CATEGORY_GENERAL).get("CopyToClipboard").getBoolean();
    }

    public static void updateConfig(){
        System.out.println("Config updated.");
        ConfigManager.sync(ScreenshotMain.MODID, Config.Type.INSTANCE);
        config = new Configuration(new File(config.getConfigFile().getAbsoluteFile().toURI()));
        overrideDefaultScreenshotKey = config.getCategory(Configuration.CATEGORY_GENERAL).get("Override").getBoolean();
        copyToClipboard = config.getCategory(Configuration.CATEGORY_GENERAL).get("CopyToClipboard").getBoolean();

        System.out.println("Default:"+overrideDefaultScreenshotKey);
        System.out.println("copyToClipboard:"+copyToClipboard);
        if(config.hasChanged()){
            ConfigManager.sync(MODID, Config.Type.INSTANCE);
        }
    }
}