package me.darkeyedragon.imageutils.client.event;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.client.gui.GuiLocalScreenshots;
import me.darkeyedragon.imageutils.client.gui.buttons.GuiButtonWithImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiMenuHookEvent {

    private final ImageUtilsMain main;

    public GuiMenuHookEvent(ImageUtilsMain main) {
        this.main = main;
    }

    @SubscribeEvent
    public void onInit(GuiScreenEvent.InitGuiEvent e) {
        if (e.getGui() instanceof GuiIngameMenu || e.getGui() instanceof GuiMainMenu) {
            int width = e.getGui().width;
            e.getButtonList().add(new GuiButtonWithImage(50, width - 40, 20, 20, 20));
            e.setButtonList(e.getButtonList());
        }
    }

    @SubscribeEvent
    public void onButtonClick(GuiScreenEvent.ActionPerformedEvent e) {
        if (e.getGui() instanceof GuiIngameMenu || e.getGui() instanceof GuiMainMenu) {
            if (e.getButton().id == 50) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiLocalScreenshots(e.getGui(), main));
            }
        }
    }
}

