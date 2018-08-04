package com.darkeyedragon.imageutils.client.gui;

import com.darkeyedragon.imageutils.client.ConfigHandler;
import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.config.UploaderFile;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IUConfigGuiFactory implements IModGuiFactory{

    public static class UIConfigGuiScreen extends GuiConfig{

        public UIConfigGuiScreen(GuiScreen parent){
            super(parent, getConfigElements(), ImageUtilsMain.MODID, false, false, I18n.format("imageutil.config.general"));
        }


        public static List<IConfigElement> getConfigElements(){
            List<String> uploadersList = new ArrayList<>();
            for(UploaderFile uf : ImageUtilsMain.uploaders){
                uploadersList.add(uf.getDisplayName());
            }
            String[] uploadersArray = uploadersList.toArray(new String[0]);
            List<IConfigElement> elements = new ArrayList<>();
            //PROPERTIES
            Property uploader = new Property("Uploader", uploadersArray[0], Property.Type.STRING, uploadersArray);
            Property override = new Property("Override", String.valueOf(ConfigHandler.override), Property.Type.BOOLEAN);
            Property copy = new Property("Copy",String.valueOf(ConfigHandler.copy), Property.Type.BOOLEAN);
            Property customUploader = new Property("Custom Uploader",String.valueOf(ConfigHandler.customUploader), Property.Type.BOOLEAN);


            ConfigCategory generalCC = new ConfigCategory("General");
            generalCC.put("uploader", uploader);
            generalCC.put("override", override);
            generalCC.put("copy", copy);
            generalCC.put("customUploader", customUploader);
            ConfigElement generalCE = new ConfigElement(generalCC);
            elements.add(generalCE);

            return elements;
        }
    }


    @Override
    public boolean hasConfigGui()
    {
        return true;
    }

    @Override
    public void initialize(Minecraft minecraftInstance)
    {
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen)
    {
        return new IUConfigGuiFactory.UIConfigGuiScreen(parentScreen);
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return null;
    }
}
