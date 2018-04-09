package com.darkeyedragon.screenshotuploader.client;

import net.minecraftforge.common.config.Config;

@Config(modid = ScreenshotMain.MODID, category = "general", name = ScreenshotMain.MODID+"/general")
@Config.LangKey("screenshot.config.general")
public class ModConfig{

    @Config.Name("override")
    @Config.Comment("Override the default screenshotkey, This will cause images to save and upload. ")
    @Config.LangKey("screenshot.config.override")
    public static boolean Override = false;

    @Config.Name("copytoclipboard")
    @Config.Comment("Set to true if you want to copy the link to your clipboard")
    @Config.LangKey("screenshot.config.copy")
    public static boolean copyToClipboard = true;

    @Config.Name("Use Custom Server")
    @Config.Comment("Set to true if you wan to use your own webserver")
    public static boolean CustomServer = false;


    @Config(modid = ScreenshotMain.MODID, category = "uploadsettings", name = ScreenshotMain.MODID+"/serversettings")
    @Config.LangKey("screenshot.config.server_settings")
    public static class ServerSettings {

        @Config.Name("link")
        @Config.Comment("Set the link to your image server. ")
        @Config.LangKey("screenshot.config.link")
        public static String server = "http://example.com/upload.php";

        @Config.Name("postdata")
        @Config.Comment("add post data. You can just delete them if they're not needed\nview the docs for more information")
        @Config.LangKey("screenshot.config.post_data")
        public static String[] postData = {"image={image}","username=SomeUser", "password=somepass"};

    }

}
