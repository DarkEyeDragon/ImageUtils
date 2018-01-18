package com.darkeyedragon.screenshotuploader.client.Events;

import com.darkeyedragon.screenshotuploader.client.ScreenshotMain;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigChanged{

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.getModID().equals("screenshotuploader")){
            System.out.println("Config changed!");
            ScreenshotMain.syncConfig();
        }
    }

}
