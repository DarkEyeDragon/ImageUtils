package com.darkeyedragon.screenshotuploader.client;


import com.darkeyedragon.screenshotuploader.client.Events.ConfigChanged;
import com.darkeyedragon.screenshotuploader.client.Events.CustomScreenshotEvent;
import com.darkeyedragon.screenshotuploader.client.Events.KeyPressEvent;
import com.darkeyedragon.screenshotuploader.client.Utils.StringFormatter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;

@Mod(modid = ScreenshotMain.MODID, version = ScreenshotMain.VERSION, updateJSON = ScreenshotMain.updateJSON)
public class ScreenshotMain
{
    public static final String MODID = "screenshotuploader";
    static final String VERSION = "1.1.0";
    static final String updateJSON = "http://darkeyedragon.me/mods/updates/screenshotuploader.json";
    public KeyBindings keybinds;

    public static List<String[]> postData;


    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new KeyPressEvent());
        MinecraftForge.EVENT_BUS.register(new CustomScreenshotEvent());
        MinecraftForge.EVENT_BUS.register(new ModConfig());
        MinecraftForge.EVENT_BUS.register(ConfigChanged.class);
        keybinds = new KeyBindings();
        keybinds.RegisterKeybinds();
    }
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        ConfigManager.sync(MODID, Config.Type.INSTANCE);
        postData = StringFormatter.postData(ModConfig.ServerSettings.postData);
    }
}