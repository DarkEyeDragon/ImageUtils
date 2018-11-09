package com.darkeyedragon.imageutils.client.imageuploaders;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.ModConfig;
import com.darkeyedragon.imageutils.client.message.Messages;
import com.darkeyedragon.imageutils.client.utils.CopyToClipboard;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import java.util.Base64;

public class ImgurUploader{


    public static void uploadImage (BufferedImage bufferedImage){

        ImageUtilsMain.fixedThreadPool.submit(() -> {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Thread.currentThread().setName("Imgur ImageUtil Uploading");
            int responseCode = 0;
            try{
                GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
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
                String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(encoded, "UTF-8");
                wr.write(data);
                wr.flush();


                BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                StringBuilder stb = new StringBuilder();
                while ((line = rd.readLine()) != null){
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
                //TODO CHANGE
                if (ModConfig.copyToClipboard){
                    if (CopyToClipboard.copy(result)){
                        chat.printChatMessage(new TextComponentTranslation("imageutil.message.copy_to_clipboard"));
                    }else{
                        chat.printChatMessage(new TextComponentTranslation("imageutil.message.copy_to_clipboard_error"));
                    }
                }
            }
            catch (IOException e){
                //In case something goes wrong!
                e.printStackTrace();
                ITextComponent errorText = new TextComponentTranslation("imageutil.message.upload.error").appendText(" " + responseCode);
                ITextComponent report = new TextComponentString("If this keeps happening please report the issue ");
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
}
