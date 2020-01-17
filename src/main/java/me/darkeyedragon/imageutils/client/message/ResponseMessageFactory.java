package me.darkeyedragon.imageutils.client.message;

import me.darkeyedragon.imageutils.client.adaptor.UploadResponseAdaptor;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;

public class ResponseMessageFactory {

    private static final Style linkStyle;

    static {
        linkStyle = new Style().setUnderlined(true).setColor(TextFormatting.AQUA);
    }

    public static ITextComponent getFormattedMessage(UploadResponseAdaptor adaptor) {
        TextComponentTranslation component;
        if (adaptor.getStatus() == 200) {
            //Set the link of the image if the response code is 200
            linkStyle.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, adaptor.getURL()));

            component = new TextComponentTranslation("imageutil.message.upload.success");
            component.appendText(" ").appendSibling(new TextComponentString(adaptor.getURL()).setStyle(linkStyle));
        } else {
            component = new TextComponentTranslation("imageutil.message.upload.error");
        }
        return component;
    }

    public static ITextComponent getErrorMessage(Throwable ex) {

        TextComponentTranslation translation = new TextComponentTranslation("imageutil.message.upload.error");
        translation.setStyle(new Style().setColor(TextFormatting.WHITE));
        translation.appendText(" ");
        translation.appendSibling(new TextComponentString(ex.getLocalizedMessage()).setStyle(new Style().setColor(TextFormatting.RED)));
        translation.appendText("\n");
        translation.appendSibling(new TextComponentTranslation("imageutil.message.upload.error1").setStyle(new Style().setColor(TextFormatting.WHITE)));
        translation.appendText(" ");
        translation.appendSibling(new TextComponentTranslation("imageutil.message.upload.errorlink").setStyle(linkStyle));
        return translation;
    }
    //public void addLink(string, Format)
}
