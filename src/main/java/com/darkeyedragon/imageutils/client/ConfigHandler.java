package com.darkeyedragon.imageutils.client;

import com.darkeyedragon.imageutils.client.adaptors.ConfigTypeAdaptor;
import com.darkeyedragon.imageutils.client.config.ConfigFile;
import com.darkeyedragon.imageutils.client.gui.IUConfigGuiFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigHandler{
    // This values below you can access elsewhere in your mod:
    public static boolean override = false;
    public static String uploader = "";
    public static boolean copy = true;
    public static boolean customUploader = false;
    public static ConfigFile configFile = ImageUtilsMain.config;

    // Call this from CommonProxy.preInit(). It will create our config if it doesn't
    // exist yet and read the values if it does exist.

    public static void load() {
        try {
            if(exists()){
                create();
            }
            configFile = ImageUtilsMain.config;
            override = configFile.isOverride();
            uploader = configFile.getUploader();
            customUploader = configFile.isCustomServer();
            ImageUtilsMain.logger.info("Loaded config file");
        } catch (Exception e1) {
           ImageUtilsMain.logger.error("Problem loading config file!", e1);
        }
    }
    public static void save(){
        try{
            if(!exists()){
                create();
            }
            setGuiSettings();
            ConfigTypeAdaptor typeAdaptor = new ConfigTypeAdaptor(copy, customUploader, override , uploader);
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            String json = gson.toJson(typeAdaptor);
            FileWriter fileWriter = new FileWriter(ImageUtilsMain.configLocation);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.write(json);
            writer.close();
            ImageUtilsMain.logger.info("Saved config file");
        }catch(Exception e){
            ImageUtilsMain.logger.error("Problem saving config file!", e);
        }
    }
    public static void create(){
        File file = ImageUtilsMain.configLocation;
        ImageUtilsMain.logger.info("No config found! Creating default one...");
        try{
            if(file.createNewFile()){
                save();
            }else{
                ImageUtilsMain.logger.info("Unable to create config!");
            }
        }catch(IOException e){
            ImageUtilsMain.logger.error("Unable to check if config exists or not!", e);
        }
    }
    public static boolean exists(){
        File file = ImageUtilsMain.configLocation;
        return (file.exists());
    }
    public static void setGuiSettings(){
        IUConfigGuiFactory.UIConfigGuiScreen.getConfigElements().forEach((element) ->{
            switch(element.getType()){
                case STRING:
                    if(element.getName().equalsIgnoreCase("uploader")) System.out.println(element.get());
                    break;
                case INTEGER:
                    break;
                case BOOLEAN:
                    if(element.getName().equalsIgnoreCase("copy")) copy = Boolean.getBoolean(element.get().toString());
                    else if(element.getName().equalsIgnoreCase("customuploader")) customUploader = Boolean.getBoolean(element.get().toString());
                    else if(element.getName().equalsIgnoreCase("override")) override = Boolean.getBoolean(element.get().toString());
                    break;
                case DOUBLE:
                    break;
                case COLOR:
                    break;
                case MOD_ID:
                    break;
                case CONFIG_CATEGORY:
                    break;
            }
        });
    }
}
