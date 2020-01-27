package me.darkeyedragon.imageutils.client;

import me.darkeyedragon.imageutils.Keybinds;
import me.darkeyedragon.imageutils.client.event.CustomGuiOpenEvent;
import me.darkeyedragon.imageutils.client.event.PartialScreenshotEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.nio.file.Paths;

import static net.minecraft.world.biome.Biome.LOGGER;

@Mod("imageutils")
public class ImageUtilsMain {

    //Mod information
    public static final String MODID = "imageutils";
    public static final String VERSION = "@VERSION@";
    public static final String NAME = "Image Utils";
    static final String updateJSON = "https://darkeyedragon.me/mods/updates/imageutils.json";
    private Path path;

    public ImageUtilsMain() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    public static Path getLocalScreenshotDir() {
        return Paths.get(Minecraft.getInstance().gameDir.toString(), "screenshots");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        path = FMLPaths.MODSDIR.get().resolve(MODID);
        LOGGER.info(path);
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
        registerEvents();
        addKeyBinds();
    }

    /*public void registerKeybinds() {
        keybinds = new KeyBindings();
        keybinds.RegisterKeybinds();
    }*/

    public void registerEvents() {
        MinecraftForge.EVENT_BUS.addListener(new PartialScreenshotEvent()::keypressEvent);
        /*MinecraftForge.EVENT_BUS.register(new CustomScreenshotEvent());
        MinecraftForge.EVENT_BUS.register(new ChatReceivedEvent());*/
        MinecraftForge.EVENT_BUS.register(new CustomGuiOpenEvent());
        /*MinecraftForge.EVENT_BUS.register(new GuiMenuHook());
        MinecraftForge.EVENT_BUS.register(new IngameGuiEvent());
        MinecraftForge.EVENT_BUS.register(ConfigChanged.class);*/
    }

    public void addKeyBinds() {
        ClientRegistry.registerKeyBinding(Keybinds.PARTIAL_SCREENSHOT);
    }
}
