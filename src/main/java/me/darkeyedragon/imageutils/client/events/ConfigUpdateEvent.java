package me.darkeyedragon.imageutils.client.events;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.client.ModConfig;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigUpdateEvent {

    @SubscribeEvent
    public static void onConfigChanged (ConfigChangedEvent.OnConfigChangedEvent event){
        if (event.getModID().equals(ImageUtilsMain.MODID)){
            ConfigManager.sync(ImageUtilsMain.MODID, Config.Type.INSTANCE);
            ImageUtilsMain.setActiveUploader();
            if (ModConfig.reloadUploaders){
                ImageUtilsMain.loadUploaders();
                ModConfig.reloadUploaders = false;
                ConfigManager.sync(ImageUtilsMain.MODID, Config.Type.INSTANCE);
            }
            //ImageUtilsMain.debug(ModConfig.debug);
        }
    }
}
