package com.darkeyedragon.imageutils.client.imageuploaders;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.message.Messages;
import net.minecraft.client.Minecraft;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class CustomUploader{



    public void uploadImage(BufferedImage bufferedImage, String link){

        ImageUtilsMain.fixedThreadPool.submit(() -> {

            Thread.currentThread().setName("Custom ImageUtil Uploading");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int responseCode = 0;
            String responseMessage = "";
            try{
                ImageIO.write(bufferedImage, "jpg", baos);
                byte[] bytes = baos.toByteArray();
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(link);
                Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Uploading image to custom server...", true);
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                for (String[] s: ImageUtilsMain.postData){
                    if(s[1].equals("{image}"))
                        builder.addBinaryBody(s[0], bytes, ContentType.IMAGE_JPEG, s[0]);
                    else{
                        builder.addTextBody(s[0], s[1]);
                    }
                }
                HttpEntity multipart = builder.build();

                httpPost.setEntity(multipart);

                CloseableHttpResponse response = client.execute(httpPost);
                responseCode = response.getStatusLine().getStatusCode();
                responseMessage = response.getStatusLine().getReasonPhrase();
                String result = response.getHeaders("Location")[0].toString().split("\\s+")[1];
                client.close();

                //Send result to player
                new Messages().uploadMessage(result);
                Minecraft.getMinecraft().player.sendChatMessage("");
                //TODO CHANGE
                /*if(ModConfig.copyToClipboard){
                    if(CopyToClipboard.copy(result)){
                        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("Copied to clipboard!"));
                        System.out.println("Copied "+result+" to clipboard");
                    }else{
                        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("Unable to save to clipboard"));
                        System.out.println("Unable to save "+result+"to clipboard");
                    }
                }*/

            }catch (Exception e){
                //In case something goes wrong!
                e.printStackTrace();
                new Messages().errorMessage(responseCode, responseMessage);
            }
        });
    }
}
