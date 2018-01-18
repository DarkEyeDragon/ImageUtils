package com.darkeyedragon.screenshotuploader.client;

import net.minecraftforge.common.config.Config;

@Config(modid = ScreenshotMain.MODID)
@Config.LangKey("main.config.title")
class ModConfig{


    @Config.Name("Override")
    @Config.Comment("Override the default screenshotkey")
    public static boolean Override = false;

    @Config.Name("SaveScreenshots")
    @Config.Comment("Set to true if you want your screenshots to save locally and upload to Imgur. Only has effect if Override is set to true")
    public static boolean SaveScreenshots = true;
}
