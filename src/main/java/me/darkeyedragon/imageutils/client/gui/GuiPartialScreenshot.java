package me.darkeyedragon.imageutils.client.gui;

import me.darkeyedragon.imageutils.client.ScreenshotHandler;
import me.darkeyedragon.imageutils.client.imageuploader.ResponseFactory;
import me.darkeyedragon.imageutils.client.imageuploader.Uploader;
import me.darkeyedragon.imageutils.client.message.ResponseMessageFactory;
import me.darkeyedragon.imageutils.client.util.RegionSelector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class GuiPartialScreenshot extends GuiScreen {

    private final int res = new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
    private final Uploader uploader;
    private boolean dragging = false;
    private Point mouseClicked;
    private RegionSelector regionSelector = new RegionSelector(5);


    public GuiPartialScreenshot(Uploader uploader) {
        this.uploader = uploader;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (dragging) {
            regionSelector.drawBackground(Math.min(mouseClicked.x, mouseX), Math.min(mouseClicked.y, mouseY), Math.max(mouseClicked.x, mouseX), Math.max(mouseClicked.y, mouseY));
            regionSelector.drawRegion(Math.min(mouseClicked.x, mouseX), Math.min(mouseClicked.y, mouseY), Math.max(mouseClicked.x, mouseX), Math.max(mouseClicked.y, mouseY));

            int x = Math.abs(mouseClicked.x - mouseX);
            int y = Math.abs(mouseClicked.y - mouseY);
            drawHoveringText(x + "x" + y, mouseClicked.x, mouseClicked.y);
        } else {
            //GlStateManager.disableTexture2D();
            //GlStateManager.enableAlpha();
            //drawBackground(0xAB000000);
            drawRect(0, 0, width, height, 0x66000000);
            drawHoveringText(I18n.format("imageutil.gui.partial_screenshot"), mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) {

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            dragging = true;
            mouseClicked = new Point(mouseX, mouseY);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        Point mouseReleased = new Point(mouseX, mouseY);
        dragging = false;
        mouseClicked.y *= res;
        mouseClicked.x *= res;
        mouseReleased.y *= res;
        mouseReleased.x *= res;

        BufferedImage partialScreenshot = ScreenshotHandler.partial(new Point(Math.min(mouseClicked.x, mouseReleased.x), Math.min(mouseClicked.y, mouseReleased.y)), new Point(Math.max(mouseClicked.x, mouseReleased.x), Math.max(mouseClicked.y, mouseReleased.y)));
        //TODO FIX
        uploader.uploadAsync(partialScreenshot).thenAccept((httpResponse -> {
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(ResponseMessageFactory.getFormattedMessage(Objects.requireNonNull(ResponseFactory.getResponseAdaptor(httpResponse))));
            }
        })).exceptionally((error) -> {
            error.printStackTrace();
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(ResponseMessageFactory.getErrorMessage(error));
            return null;
        });

        mc.displayGuiScreen(null);
    }
}