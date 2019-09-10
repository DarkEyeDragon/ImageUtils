package me.darkeyedragon.imageutils.client.imageuploader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.darkeyedragon.imageutils.client.ModConfig;
import me.darkeyedragon.imageutils.client.UploadHandler;
import me.darkeyedragon.imageutils.client.message.Messages;
import me.darkeyedragon.imageutils.client.utils.CopyToClipboard;
import me.darkeyedragon.imageutils.client.webhooks.WebhookValidation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ImgurUploader implements Uploader {

    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private final UploadHandler uploadHandler;
    private int responseCode = -1;
    private GuiNewChat chat;

    public ImgurUploader(UploadHandler uploadHandler) {
        this.uploadHandler = uploadHandler;
    }

    public void upload(BufferedImage bufferedImage) {
        uploadHandler.getFixedThreadPool().submit(() -> {
            chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
            Thread.currentThread().setName("Uploader (" + this.getClass().getName() + ")");
            try {
                URL url = new URL("https://api.imgur.com/3/image");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization", "Client-ID bfea9c11835d95c");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.connect();
                Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Uploading image...", true);


                ImageIO.write(bufferedImage, "png", baos);
                baos.flush();


                byte[] imageInByte = baos.toByteArray();
                String encoded = Base64.getEncoder().encodeToString(imageInByte);


                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                String data = URLEncoder.encode("image", StandardCharsets.UTF_8.name()) + "=" + URLEncoder.encode(encoded, StandardCharsets.UTF_8.name());
                wr.write(data);
                wr.flush();


                BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                StringBuilder stb = new StringBuilder();
                while ((line = rd.readLine()) != null) {
                    stb.append(line).append("\n");
                }
                // Get the response
                responseCode = con.getResponseCode();
                wr.close();
                rd.close();
                JsonObject jsonObject = new JsonParser().parse(stb.toString()).getAsJsonObject();
                String result = jsonObject.get("data").getAsJsonObject().get("link").getAsString();

                //Send result to player
                Messages.uploadMessage(result);
                if (ModConfig.copyToClipboard) {
                    if (CopyToClipboard.copy(result)) {
                        chat.printChatMessage(new TextComponentTranslation("imageutil.message.copy_to_clipboard"));
                    } else {
                        chat.printChatMessage(new TextComponentTranslation("imageutil.message.copy_to_clipboard_error"));
                    }
                }
                WebhookValidation.addLink(result);
            } catch (IOException e) {
                //In case something goes wrong!
                e.printStackTrace();
                ITextComponent errorText = new TextComponentTranslation("imageutil.message.upload.error").appendText(" " + responseCode);
                ITextComponent report = new TextComponentTranslation("imageutil.message.upload.report");
                ITextComponent link = new TextComponentTranslation("imageutil.message.upload.errorlink");
                ITextComponent hover = new TextComponentString("github.com/DarkEyeDragon/ImageUtils/issues");
                link.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/DarkEyeDragon/ImageUtils/issues"));
                link.getStyle().setColor(TextFormatting.AQUA);
                link.getStyle().setUnderlined(true);
                link.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(errorText);
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(report.appendSibling(link));
                Messages.errorMessage(e.getMessage());
            }
        });
    }

    @Override
    public int getResponse(HttpURLConnection urlConnection) {
        return 0;
    }

    @Override
    public void notifyPlayer() {

    }

}