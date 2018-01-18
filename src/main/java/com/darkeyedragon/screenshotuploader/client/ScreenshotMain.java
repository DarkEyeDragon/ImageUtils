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

@Mod(modid = ScreenshotMain.MODID, version = ScreenshotMain.VERSION)
public class ScreenshotMain
{
    public static final String MODID = "screenshotuploader";
    public static final String VERSION = "1.0";

    public static Configuration config;

    private KeyBindings keybinds = new KeyBindings();

    public static boolean overrideDefaultScreenshotKey;
    public static boolean saveScreenshot;


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
        config = new Configuration(event.getSuggestedConfigurationFile());
        syncConfig();
    }

    public static void syncConfig() {
        ConfigManager.sync(MODID, Config.Type.INSTANCE);
        overrideDefaultScreenshotKey = config.getBoolean(Configuration.CATEGORY_GENERAL, "Override", false, "");
        saveScreenshot = config.getBoolean(Configuration.CATEGORY_GENERAL, "Save", true, "Set to true if you want your screenshots to save locally and upload to Imgur. Only has effect if Override is set to true");
        System.out.println(overrideDefaultScreenshotKey+":"+saveScreenshot);
    }

}