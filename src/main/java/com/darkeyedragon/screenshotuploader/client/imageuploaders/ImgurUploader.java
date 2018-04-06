package com.darkeyedragon.screenshotuploader.client.imageuploaders;

import com.darkeyedragon.screenshotuploader.client.ModConfig;
import com.darkeyedragon.screenshotuploader.client.Utils.CopyToClipboard;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImgurUploader{

    private ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private ExecutorService exService = Executors.newCachedThreadPool();


    private CopyToClipboard copyToClipboard = new CopyToClipboard();


    public void uploadImage(BufferedImage bufferedImage){

        exService.execute(() -> {

            Thread.currentThread().setName("Imgur Image Uploading");
            int responseCode = 0;
            try{
                URL url = new URL("https://api.imgur.com/3/image");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization", "Client-ID bfea9c11835d95c");
                con.setRequestMethod("POST");
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
                System.out.println("Response Code: " + responseCode);
                wr.close();
                rd.close();
                JsonObject jsonObject = new JsonParser().parse(stb.toString()).getAsJsonObject();
                String result = jsonObject.get("data").getAsJsonObject().get("link").getAsString();

                //Send result to player
                ITextComponent uploadstr = new TextComponentString("Uploaded to ");
                ITextComponent linkText = new TextComponentString(result);
                linkText.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, result));
                linkText.getStyle().setUnderlined(true);
                linkText.getStyle().setColor(TextFormatting.AQUA);
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(uploadstr.appendSibling(linkText));

                if(ModConfig.copyToClipboard){
                    if(copyToClipboard.copy(result)){
                        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("Copied to clipboard!"));
                        System.out.println("Copied "+result+" to clipboard");
                    }else{
                        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("Unable to save to clipboard"));
                        System.out.println("Unable to save "+result+"to clipboard");
                    }
                }
            }catch (IOException e){
                //In case something goes wrong!
                e.printStackTrace();
                ITextComponent errorText = new TextComponentString("Something went wrong! Response code: "+responseCode);
                ITextComponent report = new TextComponentString("If this keeps happening please report the issue ");
                ITextComponent link = new TextComponentString("here");
                ITextComponent hover = new TextComponentString("github.com/DarkEyeDragon/ScreenshotUploader/issues");
                link.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/DarkEyeDragon/ScreenshotUploader/issues"));
                link.getStyle().setColor(TextFormatting.AQUA);
                link.getStyle().setUnderlined(true);
                link.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(errorText);
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(report.appendSibling(link));
            }
        });
    }
}
