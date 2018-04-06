package com.darkeyedragon.screenshotuploader.client.Events;

import com.darkeyedragon.screenshotuploader.client.ModConfig;
import com.darkeyedragon.screenshotuploader.client.ScreenshotMain;
import com.darkeyedragon.screenshotuploader.client.Utils.StringFormatter;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigChanged{
    @SubscribeEvent
    public static void OnConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(ScreenshotMain.MODID)) {
            ConfigManager.sync(ScreenshotMain.MODID, Config.Type.INSTANCE);
            ScreenshotMain.postData = StringFormatter.postData(ModConfig.ServerSettings.postData);
        }
    }
}
