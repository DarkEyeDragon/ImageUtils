package com.darkeyedragon.imageutils.client.gui;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.UploaderFile;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IUConfigGuiFactory implements  IModGuiFactory{

    public static class UIConfigGuiScreen extends GuiConfig
    {

        public UIConfigGuiScreen(GuiScreen parent)
        {
            super(parent, getConfigElements(), ImageUtilsMain.MODID, false, false, I18n.format("imageutil.config.general"));
        }

        private static List<IConfigElement> getConfigElements()
    {
        String[] uploaders = new String[ImageUtilsMain.uploaders.size()];
        for(int x=0; x < ImageUtilsMain.uploaders.size(); x++){
            List<UploaderFile> u = ImageUtilsMain.uploaders;
            uploaders[x] = u.get(x).getDisplayName();
        }
        List<IConfigElement> stringsList = new ArrayList<>();
        ConfigCategory uploadersCat = new ConfigCategory("Settings");
        uploadersCat.put("uploader", new Property("Uploader", "more test", Property.Type.STRING, uploaders));
        stringsList.add(new ConfigElement(uploadersCat));
        return stringsList;
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

    private static final Set<RuntimeOptionCategoryElement> fmlCategories = ImmutableSet.of(new RuntimeOptionCategoryElement("HELP", "FML"));

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return fmlCategories;
    }
}
