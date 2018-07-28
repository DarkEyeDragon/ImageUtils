package com.darkeyedragon.imageutils.client.gui;

import com.darkeyedragon.imageutils.client.ConfigHandler;
import com.darkeyedragon.imageutils.client.gui.buttons.GuiConfigButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.util.HashMap;
import java.util.Map;

public class GuiConfig extends GuiScreen{

    Map<GuiButton, GuiConfigButton.Types> map = new HashMap<>();

    @Override
    public void initGui()
    {
        GuiButton override = new GuiButton(0, this.width / 2, 15, String.valueOf(ConfigHandler.override));
        GuiButton copy = new GuiButton(1, this.width / 2, 30, String.valueOf(ConfigHandler.copy));
        GuiButton customUploader = new GuiButton(2, this.width / 2, 45, String.valueOf(ConfigHandler.copy));
        GuiButton uploader = new GuiButton(3, this.width / 2, 60, ConfigHandler.uploader);
        this.buttonList.add(override);
        this.buttonList.add(copy);
        this.buttonList.add(customUploader);
        this.buttonList.add(uploader);
        map.put(override, GuiConfigButton.Types.BOOLEAN);
        map.put(copy, GuiConfigButton.Types.BOOLEAN);
        map.put(customUploader, GuiConfigButton.Types.BOOLEAN);
        map.put(uploader, GuiConfigButton.Types.CYCLE_LIST);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    protected void actionPerformed(GuiButton button){
        switch(map.get(button)){
            case BOOLEAN:
                button.displayString = String.valueOf(!Boolean.getBoolean(button.displayString));
                break;
            case CYCLE_LIST:

                break;
            case LIST:
                break;
        }
        if(button.id == 0){
            ConfigHandler.override = Boolean.valueOf(button.displayString);
        }else if(button.id == 1){
            ConfigHandler.copy = Boolean.valueOf(button.displayString);
        }else if(button.id == 2){
            ConfigHandler.customUploader = Boolean.valueOf(button.displayString);
        }else if(button.id == 3){
            ConfigHandler.uploader = button.displayString;
        }
    }
}
