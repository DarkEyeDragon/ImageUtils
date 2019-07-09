package me.darkeyedragon.imageutils.client.webhooks;

import me.darkeyedragon.imageutils.client.ImageUtils;

/*Prevent abuse of the webhook*/
public class WebhookValidation{
    public static boolean validate (String url){
        return ImageUtils.webhookLinks.contains(url);
    }

    public static void removeLink (String url){
        ImageUtils.webhookLinks.remove(url);
    }

    public static void addLink (String url){
        ImageUtils.webhookLinks.add(url);
    }
}
