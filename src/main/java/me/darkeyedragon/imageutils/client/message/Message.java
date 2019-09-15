package me.darkeyedragon.imageutils.client.message;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class Message {
    private static final Style linkStyle = new Style().setUnderlined(true).setColor(TextFormatting.AQUA);

    public static Style getLinkStyle() {
        return linkStyle;
    }

}
