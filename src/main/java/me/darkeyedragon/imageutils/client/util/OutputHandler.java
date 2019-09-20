package me.darkeyedragon.imageutils.client.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.darkeyedragon.imageutils.client.ModConfig;
import me.darkeyedragon.imageutils.client.gui.GuiConfirmAction;
import me.darkeyedragon.imageutils.client.message.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class OutputHandler {


    //TODO look into failed upload debugging

    /**
     * send a message to the user with a formatted response from a {@link HttpResponse HttpResponse}
     *
     * @param httpResponse the {@link HttpResponse HttpResponse} returned from {@link me.darkeyedragon.imageutils.client.imageuploader.Uploader Uploader}
     */
    public static void sendUploadResponseMessage(HttpResponse httpResponse, GuiScreen parent) throws IOException {
        String jsonResult = IOUtils.toString(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8);
        JsonObject jsonObject = new JsonParser().parse(jsonResult).getAsJsonObject();
        ITextComponent component;
        if (jsonObject.get("status").getAsInt() == 200) {

            String link = jsonObject.get("data").getAsJsonObject().get("link").getAsString();
            if (!ModConfig.debug) {
                component = new TextComponentTranslation("imageutil.message.upload.success").appendText(" ").appendSibling(new TextComponentString(link).setStyle(Message.getLinkStyle()));
                OutputHandler.sendMessage(component, parent);
            }
            component = new TextComponentString(JsonHelper.toImgurResponse(jsonResult).getData().toString());
            OutputHandler.sendMessage(component, parent);
            if (ModConfig.copyToClipboard) {
                if (ClipboardUtil.copy(link)) {
                    component = new TextComponentTranslation("imageutil.message.copy_to_clipboard");
                } else {
                    component = new TextComponentTranslation("imageutil.message.copy_to_clipboard_error");
                }
                OutputHandler.sendMessage(component, parent);
            }
        } else {
            component = new TextComponentTranslation("imageutil.message.upload.error")
                    .appendSibling(new TextComponentString(" "))
                    .appendSibling(new TextComponentString(httpResponse.getStatusLine().toString()));
            OutputHandler.sendMessage(component, parent);
            OutputHandler.sendMessage(new TextComponentTranslation("imageutil.message.upload.error1").appendSibling(new TextComponentTranslation("imageutil.message.upload.errorlink")), parent);
        }
    }

    /**
     * Checks if chat is available, if so send message through chat, otherwise create a gui window with the current window as parent.
     *
     * @param textComponent the message you wish to send
     */
    public static void sendMessage(ITextComponent textComponent, GuiScreen currentScreen) {
        Minecraft mc = Minecraft.getMinecraft();
        if (!mc.inGameHasFocus) {
            GuiConfirmAction guiConfirmDelete = new GuiConfirmAction((result, id) ->
                    mc.displayGuiScreen(currentScreen), "Image Upload", new TextComponentTranslation("imageutil.gui.delete_screenshot").getUnformattedComponentText(), new TextComponentTranslation("imageutil.gui.delete_screenshot_line2").getUnformattedComponentText(), 0, currentScreen) {
                @Override
                public void drawScreen(int mouseX, int mouseY, float partialTicks) {
                    currentScreen.drawScreen(-1, -1, partialTicks);
                    super.drawScreen(mouseX, mouseY, partialTicks);
                }
            };
            mc.displayGuiScreen(guiConfirmDelete);

        }
        mc.ingameGUI.getChatGUI().printChatMessage(textComponent);

    }
}
