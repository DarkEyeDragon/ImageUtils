package me.darkeyedragon.imageutils.client.event;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.client.ModConfig;
import me.darkeyedragon.imageutils.client.UploadHandler;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigUpdateEvent {

    private final UploadHandler uploadHandler;

    public ConfigUpdateEvent(UploadHandler uploadHandler) {
        this.uploadHandler = uploadHandler;
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(ImageUtilsMain.MODID)) {
            ConfigManager.sync(ImageUtilsMain.MODID, Config.Type.INSTANCE);
            if (ModConfig.customServer) {
                uploadHandler.loadUploaders();
                uploadHandler.setActiveUploader(ModConfig.uploader); //old non valid way
            } else {
                uploadHandler.unloadUploaders();
            }
            if (ModConfig.reloadUploaders) {
                ModConfig.reloadUploaders = false;
                ConfigManager.sync(ImageUtilsMain.MODID, Config.Type.INSTANCE);
                uploadHandler.loadUploaders(); //old non valid way
            }
            //ImageUtilsMain.debug(ModConfig.debug);
        }
    }
}
