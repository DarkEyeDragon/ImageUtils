package me.darkeyedragon.imageutils.client;


import me.darkeyedragon.imageutils.client.command.ViewCommand;
import me.darkeyedragon.imageutils.client.command.ViewLocalCommand;
import me.darkeyedragon.imageutils.client.event.*;
import me.darkeyedragon.imageutils.client.imageuploader.UploaderFactory;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod(modid = ImageUtilsMain.MODID, version = ImageUtilsMain.VERSION, updateJSON = ImageUtilsMain.updateJSON, clientSideOnly = true)
public class ImageUtilsMain {


    //Mod information
    public static final String MODID = "imageutils";
    public static final String VERSION = "@VERSION@";
    public static final String NAME = "Image Utils";
    static final String updateJSON = "https://darkeyedragon.me/mods/updates/imageutils.json";
    //keep track of webhooks to prevent spam on discord
    public static List<String> webhookLinks = new ArrayList<>();
    private final UploaderFactory uploaderFactory = new UploaderFactory(this);
    private Logger logger;
    private Path configPath;
    private File uploadDir;
    private KeyBindings keybinds;
    private UploadHandler uploadHandler = new UploadHandler(this);

    public static String getMODID() {
        return MODID;
    }

    public static String getVersion() {
        return VERSION;
    }

    public static String getUpdateJSON() {
        return updateJSON;
    }

    public static List<String> getWebhookLinks() {
        return webhookLinks;
    }

    public static Path getScreenshotDir() {
        return Paths.get(Minecraft.getMinecraft().gameDir.getAbsolutePath(), "screenshots");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent init) {
        uploadDir = new File(configPath.toFile(), "uploaders");
        if (!uploadDir.exists()) {
            if (uploadDir.mkdir()) {
                logger.info("No uploaders directory, creating one...");
            } else {
                logger.warn("Unable to create uploaders directory! This will cause problems later on.");
            }
        } else {
            logger.warn(uploadHandler);
            uploadHandler.loadUploaders();
            if (ModConfig.uploader != null) {
                if (!ModConfig.uploader.equalsIgnoreCase("")) {
                    uploadHandler.setActiveUploader();
                }
            }
        }

        registerEvents(new KeyPressEvent(this),
                new CustomScreenshotEvent(this),
                new ChatReceivedEvent(),
                new CustomGuiOpenEvent(this),
                new GuiMenuHookEvent(this),
                new IngameGuiEvent(),
                new ConfigUpdateEvent(getUploadHandler())
        );

        keybinds = new KeyBindings();
        keybinds.RegisterKeybinds();

    }

    private void registerEvents(Object... events) {
        Arrays.stream(events).forEach(MinecraftForge.EVENT_BUS::register);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent pre) {
        logger = pre.getModLog();
        configPath = Paths.get(pre.getModConfigurationDirectory().getPath(), MODID);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new ViewCommand(this));
        event.registerServerCommand(new ViewLocalCommand(this));
    }

    public Logger getLogger() {
        return logger;
    }

    public Path getConfigPath() {
        return configPath;
    }

    public File getUploadDir() {
        return uploadDir;
    }

    public KeyBindings getKeybinds() {
        return keybinds;
    }

    public UploadHandler getUploadHandler() {
        return uploadHandler;
    }

    public UploaderFactory getUploaderFactory() {
        return uploaderFactory;
    }
}