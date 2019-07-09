package me.darkeyedragon.imageutils.client;


import me.darkeyedragon.imageutils.client.config.UploaderFile;
import me.darkeyedragon.imageutils.client.events.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Mod(ImageUtils.MODID)
public class ImageUtils {
    public static final String MODID = "imageutils";
    public static final String VERSION = "@VERSION@";
    static final String updateJSON = "https://darkeyedragon.me/mods/updates/imageutils.json";

    public static Logger logger;
    public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
    public static List<String> webhookLinks = new ArrayList<>();
    public static LinkedHashMap<String, BufferedImage> validLinks = new LinkedHashMap<String, BufferedImage>(){
        @Override
        protected boolean removeEldestEntry (Map.Entry<String, BufferedImage> eldest){
            return size() > 7;
        }
    };
    public static List<UploaderFile> uploaders;
    public static UploaderFile activeUploader;
    private static Path configPath;
    private static File uploadDir;
    private KeyBindings keybinds;

    public ImageUtils() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    public static void setActiveUploader (){
        if (ModConfig.customServer){

            ImageUtils.uploaders.forEach(uf -> {
                if (uf.getFileName().equalsIgnoreCase(ModConfig.uploader)){
                    logger.info("Setting active uploader script.");
                    activeUploader = uf;
                }
            });
        }
    }

    public void clientSetup(final FMLCommonSetupEvent event) {
        logger = LogManager.getLogger();
        //configPath = Paths.get(Minecraft.getInstance().gameDir.getPath(), MODID);
        configPath = FMLPaths.MODSDIR.get().resolve(MODID);
        //configPath = Paths.get(event.getModConfigurationDirectory().getPath(), MODID);

        registerEvents();
        registerKeybinds();
        registerUploaders();
    }

    public void registerEvents() {
        MinecraftForge.EVENT_BUS.register(new KeyPressEvent());
        MinecraftForge.EVENT_BUS.register(new CustomScreenshotEvent());
        MinecraftForge.EVENT_BUS.register(new ChatReceivedEvent());
        MinecraftForge.EVENT_BUS.register(new CustomGuiOpenEvent());
        MinecraftForge.EVENT_BUS.register(new GuiMenuHook());
        MinecraftForge.EVENT_BUS.register(new IngameGuiEvent());
        MinecraftForge.EVENT_BUS.register(ConfigChanged.class);
    }

    public void registerKeybinds() {
        keybinds = new KeyBindings();
        keybinds.RegisterKeybinds();
    }

    public static void loadUploaders (){
        logger.info("Uploaders directory found, loading uploaders...");
        String[] list = uploadDir.list();
        if (list != null && list.length > 0){
            List<String> displayName = new ArrayList<>();
            for (File file : Objects.requireNonNull(uploadDir.listFiles())){
                try{
                    UploaderFile uf = new UploaderFile(file);
                    uploaders.add(uf);
                    displayName.add(uf.getDisplayName());
                    logger.info("Loaded: " + file.getName());
                }
                catch (Exception e){
                    logger.warn("Unable to load " + file.getName() + e.getMessage() + "!");
                }
            }
        }
    }

    public void registerUploaders() {
        uploaders = new ArrayList<>();
        uploadDir = new File(configPath.toFile(), "uploaders");
        if (!uploadDir.exists()){
            if (uploadDir.mkdir()){
                logger.info("No uploaders directory, creating one...");
            }else{
                logger.warn("Unable to create uploaders directory! This will cause problems later on.");
            }
        }else{
            loadUploaders();
            if (ModConfig.uploader != null){
                if (!ModConfig.uploader.equalsIgnoreCase("")){
                    setActiveUploader();
                }
            }
        }
        //debug(ModConfig.debug);
    }
    //TODO CHANGE TO FUTURETASK
    /*public static void debug(boolean enable){
        ScheduledExecutorService timedTask = Executors.newScheduledThreadPool(1);
        if(enable){
            if(timedTask.isShutdown() || timedTask.isTerminated()){
                logger.info("Enabling debug mode...");
                timedTask.scheduleAtFixedRate(() -> Minecraft.getInstance().addScheduledTask(ServerUtil::ping), 0, 30L, TimeUnit.SECONDS);
            }
        }else{
            //timedTask.shutdown();
        }
    }*/
}