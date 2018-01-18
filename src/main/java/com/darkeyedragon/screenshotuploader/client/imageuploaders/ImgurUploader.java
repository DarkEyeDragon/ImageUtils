package com.darkeyedragon.screenshotuploader.client.imageuploaders;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;

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

    public void uploadImage(BufferedImage bufferedImage){

        exService.execute(() -> {

            Thread.currentThread().setName("Imgur Image Uploading");

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
                System.out.println("Response Code: " + con.getResponseCode());
                wr.close();
                rd.close();
                JsonObject jsonObject = new JsonParser().parse(stb.toString()).getAsJsonObject();
                Minecraft.getMinecraft().player.sendChatMessage("Uploaded to "+jsonObject.get("data").getAsJsonObject().get("link").getAsString());
            }catch (IOException e){
                e.printStackTrace();

            }
        });
    }
}
