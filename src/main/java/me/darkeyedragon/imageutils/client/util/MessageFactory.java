package me.darkeyedragon.imageutils.client.util;

import net.minecraft.util.text.*;
import org.apache.http.HttpResponse;

public class MessageFactory {

    public static TextComponentString createText(String text) {
        return new TextComponentString(text);
    }

    public static ITextComponent createHttpError(HttpResponse response) {
        TextComponentTranslation errorText = new TextComponentTranslation("imageutil.message.upload.error");
        TextComponentTranslation errorInformation = new TextComponentTranslation("imageutil.message.upload.error1");
        ITextComponent errorClickHere = new TextComponentTranslation("imageutil.message.upload.errorlink");
        errorClickHere = errorClickHere.setStyle(new Style().setColor(TextFormatting.AQUA).setBold(true));
        errorText.appendText(response.getStatusLine().getStatusCode() + ": " + response.getStatusLine().getReasonPhrase());
        errorText.appendSibling(errorInformation).appendSibling(errorClickHere);
        return errorText;
    }
}
