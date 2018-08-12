package com.darkeyedragon.imageutils.client.gui;

import com.darkeyedragon.imageutils.client.message.ClientMessage;
import com.darkeyedragon.imageutils.client.utils.CopyToClipboard;
import com.darkeyedragon.imageutils.client.utils.ImageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class GuiImagePreviewer extends GuiScreen{
    private URL url;
    private BufferedImage bufferedImage;
    private ResourceLocation resourceLocation;
    private int scale = new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
    public GuiImagePreviewer(URL url, BufferedImage image){
        this.url = url;
        this.bufferedImage = image;
        generateImage();
    }
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        drawDefaultBackground();
        int x = (width/2-(bufferedImage.getWidth()/2)/scale);
        int y = (height/2-(bufferedImage.getHeight()/2)/scale);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        drawModalRectWithCustomSizedTexture(x, y, 0, 0, bufferedImage.getWidth()/scale, bufferedImage.getHeight()/scale, bufferedImage.getWidth()/scale, bufferedImage.getHeight()/scale);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        try{
            super.mouseClicked(mouseX,mouseY,mouseButton);
        }catch (IOException e){
            e.printStackTrace();
        }
        mc.displayGuiScreen(null);
    }
    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }
    @Override
    public void initGui(){
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 50 - 105, 10, 100, 20, "Copy Image"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 50, 10, 100, 20, "Open Image"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 50 + 105, 10, 100, 20, "Copy Url"));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if(button.id == 0){
            if(CopyToClipboard.copy(bufferedImage))
                ClientMessage.basic("Copied image to clipboard");
            else
                ClientMessage.basic("Unable to copy image to clipboard");
        }else if(button.id == 1){
            try{
                Desktop.getDesktop().browse(new URI(url.toString()));
            }catch (IOException | URISyntaxException e){
                e.printStackTrace();
            }
        }
        else if (button.id == 2)
        {
            if(CopyToClipboard.copy(url.toString()))
                ClientMessage.basic("Copied link to clipboard");
            else
                ClientMessage.basic("Unable to copy link to clipboard");
        }
    }
    private void generateImage(){
        int maxImgWidth = 1024;
        if (bufferedImage == null) return;
        if(bufferedImage.getWidth() > maxImgWidth)
            bufferedImage = ImageUtil.resize(bufferedImage, maxImgWidth, maxImgWidth);
        if(bufferedImage == null) return;
        resourceLocation = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation("urlImage", new DynamicTexture(bufferedImage));
    }
}
