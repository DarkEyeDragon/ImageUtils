package com.darkeyedragon.screenshotuploader.client.gui;

import com.darkeyedragon.screenshotuploader.client.imageuploaders.ImgurUploader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class CustomGui extends GuiScreen{

    GuiButton button1;
    final int BUTTON1 = 1;
    GuiButton button2;
    final int BUTTON2 = 2;
    GuiButton button3;
    final int BUTTON3 = 3;


    private ImgurUploader imgurUploader = new ImgurUploader();



    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        drawDefaultBackground();
        fontRenderer.drawString("Where do you want to upload to?", 50,20, 0xfffff);

        super.drawScreen(mouseX,mouseY,partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame(){
        return false;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(button1 = new GuiButton(BUTTON1, 50, 50, fontRenderer.getStringWidth("Imgur.com")+15, 20,"Imgur.com"));
        this.buttonList.add(button2 = new GuiButton(BUTTON2, 65+fontRenderer.getStringWidth("Imgur.com"), 50, fontRenderer.getStringWidth("DarkEyeDragon.me")+15, 20,"DarkEyeDragon.me"));
        this.buttonList.add(button3 = new GuiButton(BUTTON3,80+fontRenderer.getStringWidth("Imgur.com") + fontRenderer.getStringWidth("DarkEyeDragon.me"), 50,  fontRenderer.getStringWidth("Custom Server")+15, 20,"Custom Server"));
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException{
        switch(button.id){
            case BUTTON1:
                try{
                    //imgurUploader.uploadImage();
                }catch (Exception e){
                    Minecraft.getMinecraft().player.sendChatMessage("Something went wrong! Unable to upload image");
                }
                break;
            default:
                break;
        }
        super.actionPerformed(button);
    }
}
