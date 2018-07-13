package com.darkeyedragon.imageutils.client.gui;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.utils.ImageResource;
import com.darkeyedragon.imageutils.client.utils.ImageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiLocalScreenshots extends GuiScreen{


    private File screendir = new File(Minecraft.getMinecraft().mcDataDir, "screenshots");
    private volatile List<ImageResource> images = new ArrayList<>();
    private int scale = new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
    private volatile List<DynamicTexture> dynamicTextures = new ArrayList<>();
    private volatile int totalScreenshots;
    private int amount = 12;
    private int pages = 0;
    private int currentPage = 0;
    private GuiButton back;
    private GuiButton next;
    @Override
    public void initGui()
    {
        int oldScale = scale;
        scale = new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
        super.initGui();
        images.clear();
        System.out.println("Scale:"+oldScale+"/"+scale);
        if(images.size() < amount || oldScale != scale){
            drawCenteredString(mc.fontRenderer, "Loading...", width/2, height/2, Integer.parseInt("FFAA00", 16));
            ImageUtilsMain.fixedThreadPool.submit(this::listFilesFromFolder);
        }
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 50 - 105, height-30, 100, 20, "View Image"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 50 + 105, height-30, 100, 20, "Upload To Imgur"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 50, height-30, 100, 20, "DELET"));
        this.buttonList.add(new GuiButton(3, 20, height/2, 45, 20, "<< Back"));
        this.buttonList.add(new GuiButton(4, width-65, height/2, 45, 20, "Next >>"));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 50 + 210, height-30, 100, 20, "Close"));
        back = buttonList.get(3);
        next = buttonList.get(4);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        back.enabled = currentPage > 0;
        next.enabled = currentPage < pages - 1;

        //TODO FIX LAST IMAGES NOT SHOWING
        this.drawBackground(0);
        this.drawCenteredString(this.fontRenderer, "Screenshots", width / 2, 20, 16777215);
        if(images.size() == 0 || dynamicTextures.size() == 0) return;
        int index=0;
        for(int row =0; row < Math.ceil(images.size()/4); row++){
            for(int column=0; column < Math.floor(images.size()/3); column++){

                ImageResource imageResource = images.get(row);
                BufferedImage img = imageResource.getImage();
                String name = imageResource.getName();
                int imgWidth =img.getWidth();
                int imgHeight =img.getHeight();
                int posX = (width/2-((imgWidth)*2)-30)+((imgWidth+20)*column);
                int posY = (height/2-(imgHeight*2))+((imgHeight+20)*row);
                ResourceLocation resource = Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation(name, dynamicTextures.get(index));
                Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
                drawModalRectWithCustomSizedTexture(posX,posY, 0,0, img.getWidth(), img.getHeight(), img.getWidth(),img.getHeight());
                index++;
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    @Override
    public void onGuiClosed()
    {
        for (DynamicTexture texture: dynamicTextures){
            texture.deleteGlTexture();
        }
    }
    protected void actionPerformed(GuiButton button)
    {
        if(button.id==3){
            if(currentPage > 0){
                currentPage = currentPage - 1;
                ImageUtilsMain.fixedThreadPool.submit(this::listFilesFromFolder);
                System.out.println(currentPage+"/"+pages);
            }
        }
        else if(button.id==4){
            if(currentPage < pages-1){
                currentPage = currentPage + 1;
                ImageUtilsMain.fixedThreadPool.submit(this::listFilesFromFolder);
                System.out.println(currentPage+"/"+pages);
            }
        }
        else if(button.id==5){
            mc.displayGuiScreen(null);
        }
    }

    private void listFilesFromFolder() {
        List<ImageResource> processedImages = new ArrayList<>();
        File[] files = screendir.listFiles();
        if (files == null) return;
        for (int i = amount; i > 0; i--){
            if(amount*currentPage<0) return;
            try{
                BufferedImage bufferedImage;
                bufferedImage = ImageIO.read(files[amount*currentPage+i]);
                ImageResource imgres =  new ImageResource(files[i].getName(), ImageUtil.resize(bufferedImage, 100, 100));
                processedImages.add(imgres);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        setImageList(processedImages);
        setPages((int)Math.ceil(files.length/12+1));
    }
    private synchronized void setImageList(List<ImageResource> imgs){
        Minecraft.getMinecraft().addScheduledTask(() -> {
            images = imgs;
            if(dynamicTextures.size() > 0)
                dynamicTextures.clear();
            for (ImageResource imageResource: images){
                dynamicTextures.add(new DynamicTexture(imageResource.getImage()));
            }
        });
    }
    private synchronized void setPages(int pages){
        Minecraft.getMinecraft().addScheduledTask(() -> {
            if(pages == 0) return;
            this.pages = pages;
        });
    }
}
