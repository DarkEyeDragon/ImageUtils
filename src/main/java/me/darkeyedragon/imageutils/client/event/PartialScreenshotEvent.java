package me.darkeyedragon.imageutils.client.event;

import me.darkeyedragon.imageutils.Keybinds;
import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.client.gui.overlay.PartialScreenshot;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ImageUtilsMain.MODID, value = Dist.CLIENT)

public class PartialScreenshotEvent {

    private PartialScreenshot partialScreenshot;

    @SubscribeEvent
    public void keypressEvent(GuiScreenEvent.KeyboardKeyPressedEvent event) {
        //GLFW_KEY_
        if (event.getKeyCode() == Keybinds.PARTIAL_SCREENSHOT.getKey().getKeyCode()) {
            Minecraft.getInstance().displayGuiScreen(null);
        }
    }
}
