package com.darkeyedragon.imageutils.client.webhooks;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;

/*Prevent abuse of the webhook*/
public class WebhookValidation{
    public static boolean validate (String url){
        return ImageUtilsMain.webhookLinks.contains(url);
    }

    public static void removeLink (String url){
        ImageUtilsMain.webhookLinks.remove(url);
    }

    public static void addLink (String url){
        ImageUtilsMain.webhookLinks.add(url);
    }
}
