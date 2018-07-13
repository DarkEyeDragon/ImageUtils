package com.darkeyedragon.imageutils.client.events;

import com.darkeyedragon.imageutils.client.ConfigHandler;
import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigChanged{

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(ImageUtilsMain.MODID)) {
            ConfigHandler.save();
            ConfigHandler.load();
        }
    }
}
