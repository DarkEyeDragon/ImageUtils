package com.darkeyedragon.screenshotuploader.client;

import net.minecraftforge.common.config.Config;

@Config(modid = ScreenshotMain.MODID)
@Config.LangKey("main.config.title")
public class ModConfig{


    @Config.Name("Override")
    @Config.Comment("Override the default screenshotkey, This will cause images to save and upload. ")
    public static boolean Override = false;

    @Config.Name("CopyToClipboard")
    @Config.Comment("Set to true if you want to copy the link to your clipboard")
    public static boolean SaveScreenshots = true;
}
