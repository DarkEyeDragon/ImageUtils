package me.darkeyedragon.imageutils.client.imageuploader;

import me.darkeyedragon.imageutils.client.UploadHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.text.TextComponentString;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ImgurUploader extends BaseUploader {

    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private final UploadHandler uploadHandler;
    private int responseCode = -1;
    private GuiNewChat chat;

    public ImgurUploader(UploadHandler uploadHandler) {
        super("https://api.imgur.com/3/image");
        super.getHttpPost().addHeader(HttpHeaders.AUTHORIZATION, "Client-ID bfea9c11835d95c");
        this.uploadHandler = uploadHandler;
    }

    @Override
    public HttpResponse upload(BufferedImage bufferedImage) {
        HttpResponse response = super.upload(bufferedImage);
        try {
            super.addParam("type", "base64");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
        chat.printChatMessage(new TextComponentString(response.getStatusLine().getStatusCode() + ": " + response.getStatusLine().getReasonPhrase()));

        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            chat.printChatMessage(new TextComponentString(stringBuilder.toString()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return response;
        /*JsonObject jsonObject;
        try {
            jsonObject = new JsonParser().parse(super.getHttpResponse().getEntity().getContent()).getAsJsonObject();
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
            e.printStackTrace();
        }*/


    }



    /*public void upload(BufferedImage bufferedImage) {
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
                String data = URLEncoder.encode("image", Charset.forName("UTF-8").name()) + "=" + URLEncoder.encode(encoded, Charset.forName("UTF-8").name());
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
    }*/
}