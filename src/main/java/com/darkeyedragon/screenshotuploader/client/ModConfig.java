package com.darkeyedragon.screenshotuploader.client;

import net.minecraftforge.common.config.Config;

@Config(modid = ScreenshotMain.MODID, category = "General", name = ScreenshotMain.MODID+"/general")
@Config.LangKey("main.config.title")
public class ModConfig{

    @Config.Name("Override")
    @Config.Comment("Override the default screenshotkey, This will cause images to save and upload. ")
    public static boolean Override = false;

    @Config.Name("Copy To Clipboard")
    @Config.Comment("Set to true if you want to copy the link to your clipboard")
    public static boolean copyToClipboard = true;

    @Config.Name("Use Custom Server")
    @Config.Comment("Set to true if you wan to use your own webserver")
    public static boolean CustomServer = false;


    @Config(modid = ScreenshotMain.MODID, category = "Upload Settings", name = ScreenshotMain.MODID+"/serversettings")
    @Config.LangKey("main.config.title")
    public static class ServerSettings {

        @Config.Name("Link")
        @Config.Comment("Set the link to your image server. ")
        public static String server = "http://example.com/upload.php";

        @Config.Name("Post Values")
        @Config.Comment("add post data. You can just delete them if they're not needed\nview the docs for more information")
        public static String[] postData = {"image={image}","username=SomeUser", "password=somepass"};

    }

}
