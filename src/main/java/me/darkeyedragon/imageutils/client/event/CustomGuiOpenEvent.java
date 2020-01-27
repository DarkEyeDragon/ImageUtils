package me.darkeyedragon.imageutils.client.event;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.client.gui.button.SlideButton;
import me.darkeyedragon.imageutils.image.Screenshot;
import me.darkeyedragon.imageutils.util.image.LocalScreenshots;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ImageUtilsMain.MODID, value = Dist.CLIENT)
public class CustomGuiOpenEvent {

    //private static final ResourceLocation LOGO = new ResourceLocation(ImageUtilsMain.MODID, "textures/widgets.png");

    private static boolean animating = false;
    private static boolean animationDone = false;
    private final Minecraft mc = Minecraft.getInstance();
    private int toX = -1;
    private int current = 0;
    private List<Screenshot> screenshots;
    private boolean reverse = false;


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void guiOpenEvent(GuiScreenEvent.InitGuiEvent initGuiEvent) {

        final Screen screen = initGuiEvent.getGui();
        animating = false;
        animationDone = false;
        initGuiEvent.getWidgetList();
        if (screen instanceof IngameMenuScreen || screen instanceof MainMenuScreen) {
            final int width = screen.width;
            final int height = screen.height;
            toX = width / 2 + width / 4;
            Button close = new SlideButton(toX, height / 2, 20, 20, new TranslationTextComponent("imageutil.special.arrow_right").getFormattedText(), button -> {
                animating = true;
                button.visible = false;
                //Minecraft.getInstance().displayGuiScreen(new OptionsScreen(screen, Minecraft.getInstance().gameSettings));
            });
            Button open = new SlideButton(width - 10, height / 2, 20, 20, new TranslationTextComponent("imageutil.special.arrow_left").getFormattedText(), button -> {
                animating = true;
                button.visible = false;
                close.visible = true;
                //Minecraft.getInstance().displayGuiScreen(new OptionsScreen(screen, Minecraft.getInstance().gameSettings));
            });
            /*initGuiEvent.addWidget(new ImageButton(width / 2 - 10, height / 4, 20, 20, 0, 0, 0, LOGO, button -> {
                Minecraft.getInstance().displayGuiScreen(new OptionsScreen(screen, Minecraft.getInstance().gameSettings));
            }));*/
            initGuiEvent.addWidget(open);
            initGuiEvent.addWidget(close);
            LocalScreenshots.getAsResourceLocationAsync().thenAccept((screenshotList -> screenshots = screenshotList));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void menuDrawEvent(GuiScreenEvent.DrawScreenEvent.Post drawEvent) {
        final Screen screen = drawEvent.getGui();
        final int width = screen.width;
        final int height = screen.height;
        if (!animating && !animationDone) {
            if (!reverse) {
                current = width - 10;
            } else {
                current = toX;
                toX = width - 10;
            }
            //AbstractGui.fill(width - 10, 0, width, height, 0x88000000);
        } else {
            if (!reverse)
                drawSlideOutAnimation(current -= width / 60, width, height);
            else
                drawSlideOutAnimation(current += width / 60, width, height);
        }
        if (animationDone) {
            AbstractGui.fill(toX, 0, width, height, 0x88000000);
            for (int i = 0; i < screenshots.size(); i++) {
                Screenshot screenshot = screenshots.get(i);
                mc.getTextureManager().bindTexture(screenshot.getResourceLocation());
                AbstractGui.blit(width - 120, 64 * i, 0, 0, 64, 64, 64, 64);
                screen.drawString(mc.fontRenderer, screenshot.getName(), width - 8, height / 2, 0xffffff);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void screenCloseEvent(GuiOpenEvent guiOpenEvent) {
        final Screen oldScreen = mc.currentScreen;
        if (oldScreen instanceof IngameMenuScreen || oldScreen instanceof MainMenuScreen) {
            screenshots.forEach(Screenshot::close);
            screenshots.clear();
        }
    }

    private void drawSlideOutAnimation(int current, int width, int height) {
        if (toX > 0 && toX < current) {
            AbstractGui.fill(current, 0, width, height, 0x88000000);
            //AbstractGui.fill(width-100, 0, width, height, 0x88000000);
        } else {
            animating = false;
            animationDone = true;
        }
    }

    private void drawSlideInAnimation(int current, int width, int height) {
        if (toX < 0 && toX > current) {
            AbstractGui.fill(current, 0, width, height, 0x88000000);
            //AbstractGui.fill(width-100, 0, width, height, 0x88000000);
        } else {
            animating = false;
            animationDone = true;
        }
    }
}
