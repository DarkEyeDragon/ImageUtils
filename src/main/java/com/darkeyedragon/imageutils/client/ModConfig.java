package com.darkeyedragon.imageutils.client;

import net.minecraftforge.common.config.Config;

@Config (modid = ImageUtilsMain.MODID, name = ImageUtilsMain.MODID + "/general")
@Config.LangKey ("imageutil.config.general")
public class ModConfig{

    @Config.Name ("override")
    @Config.Comment ("Override the default imageutilkey, This will cause images to save and upload. ")
    @Config.LangKey ("imageutil.config.override")
    public static boolean Override = false;

    @Config.Name ("copyToClipboard")
    @Config.Comment ("Set to true if you want to copy the link to your clipboard")
    @Config.LangKey ("imageutil.config.copy")
    public static boolean copyToClipboard = true;

    @Config.Name ("useCustomServer")
    @Config.LangKey ("imageutil.config.custom_server") //TODO ADD
    @Config.Comment ("Set to true if you wan to use your own webserver")
    public static boolean customServer = false;

    @Config.Name ("uploaderFile")
    @Config.LangKey ("imageutil.config.uploader_file") //TODO ADD
    @Config.Comment ("works with any file extension, just make sure the content of the file is in a json format\n More info can be found in the docs")
    public static String uploader = "ExampleName.json";

    @Config.Name ("reloadUploaders")
    @Config.LangKey ("imageutil.config.reload_uploaders") //TODO ADD
    @Config.Comment ("Set this to true if you want to reload the uploader files when you close this config screen. Useful when you made changes to the config after the game was launched.")
    public static boolean reloadUploaders = false;

    @Config.Name ("debug")
    @Config.LangKey ("imageutil.config.debug") //TODO ADD
    @Config.Comment ("Set this to true if you want to reload the uploader files when you close this config screen. Useful when you made changes to the config after the game was launched.")
    public static boolean debug = false;
}

