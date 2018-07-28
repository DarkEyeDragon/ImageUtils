package com.darkeyedragon.imageutils.client;


import com.darkeyedragon.imageutils.client.config.ConfigFile;
import com.darkeyedragon.imageutils.client.config.UploaderFile;
import com.darkeyedragon.imageutils.client.events.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Mod(modid = ImageUtilsMain.MODID, version = ImageUtilsMain.VERSION, updateJSON = ImageUtilsMain.updateJSON, clientSideOnly = true)
public class ImageUtilsMain
{
    public static final String MODID = "imageutils";
    static final String VERSION = "1.0.0";
    static final String updateJSON = "https://darkeyedragon.me/mods/updates/imageutils.json";

    public static Logger logger;
    public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);

    public static WeakHashMap<String, BufferedImage> validLinks = new WeakHashMap<>();

    private KeyBindings keybinds;

    public static ConfigFile config;
    public static Path configPath;
    public static File uploadDir;
    public static File configLocation;
    public static List<UploaderFile> uploaders;
    public static UploaderFile activeUploader;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new KeyPressEvent());
        MinecraftForge.EVENT_BUS.register(new CustomScreenshotEvent());
        MinecraftForge.EVENT_BUS.register(new ChatReceivedEvent());
        MinecraftForge.EVENT_BUS.register(new CustomGuiOpenEvent());
        MinecraftForge.EVENT_BUS.register(new GuiOptionsHook());
        MinecraftForge.EVENT_BUS.register(ConfigChanged.class);
        keybinds = new KeyBindings();
        keybinds.RegisterKeybinds();

    }
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent pre){
        logger = pre.getModLog();
        configPath = Paths.get(pre.getModConfigurationDirectory().getPath(), MODID);
    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent post){
        uploaders = new ArrayList<>();
        uploadDir = new File(configPath.toFile(), "uploaders");
        if(!uploadDir.exists()){
            if(uploadDir.mkdir()){
                logger.info("No uploaders directory, creating one...");
            }else{
                logger.warn("Unable to create uploaders directory! This will cause problems later on.");
            }
        }else{
            loadUploaders();
            if(ModConfig.uploader != null || !ModConfig.uploader.equalsIgnoreCase("")){
                setActiveUploader();
            }
        }
    }
    public static void setActiveUploader(){
        if(ModConfig.customServer){

            ImageUtilsMain.uploaders.forEach(uf -> {
                if(uf.getFileName().equalsIgnoreCase(ModConfig.uploader)){
                    logger.info("Setting active uploader script.");
                    activeUploader = uf;
                }
            });
        }
    }
    public static void loadUploaders(){
        logger.info("Uploaders directory found, loading uploaders...");
        String[] list = uploadDir.list();
        if(list != null && list.length > 0){
            List<String> displayName = new ArrayList<>();
            for(File file : Objects.requireNonNull(uploadDir.listFiles())){
                try{
                    UploaderFile uf = new UploaderFile(file);
                    uploaders.add(uf);
                    displayName.add(uf.getDisplayName());
                    logger.info("Loaded: "+file.getName());
                }catch(Exception e){
                    logger.warn("Unable to load "+file.getName()+"! "+e.getMessage());
                }
            }
        }
    }
}