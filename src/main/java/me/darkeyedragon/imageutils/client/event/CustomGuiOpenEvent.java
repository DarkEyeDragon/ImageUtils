package me.darkeyedragon.imageutils.client.event;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ImageUtilsMain.MODID, value = Dist.CLIENT)
public class CustomGuiOpenEvent {

    private static final ResourceLocation LOGO = new ResourceLocation(ImageUtilsMain.MODID, "textures/widgets.png");

    @SubscribeEvent
    public void guiOpenEvent(GuiScreenEvent.InitGuiEvent initGuiEvent) {
        final Screen screen = initGuiEvent.getGui();
        if (screen instanceof IngameMenuScreen || screen instanceof MainMenuScreen) {
            final int width = initGuiEvent.getGui().width;
            final int height = initGuiEvent.getGui().height;
            initGuiEvent.addWidget(new ImageButton(width / 2 - 10, height / 4, 20, 20, 0, 0, 0, LOGO, button -> {
                Minecraft.getInstance().displayGuiScreen(new OptionsScreen(initGuiEvent.getGui(), Minecraft.getInstance().gameSettings));
            }));
        }
    }
}
