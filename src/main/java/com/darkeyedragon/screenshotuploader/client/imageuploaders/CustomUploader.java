package com.darkeyedragon.screenshotuploader.client.imageuploaders;

import com.darkeyedragon.screenshotuploader.client.ModConfig;
import com.darkeyedragon.screenshotuploader.client.ScreenshotMain;
import com.darkeyedragon.screenshotuploader.client.Utils.CopyToClipboard;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomUploader{

    private ExecutorService exService = Executors.newCachedThreadPool();
    private CopyToClipboard copyToClipboard = new CopyToClipboard();


    public void uploadImage(BufferedImage bufferedImage, String link){

        exService.execute(() -> {

            Thread.currentThread().setName("Custom Image Uploading");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int responseCode = 0;
            String responseMessage = "";
            System.out.println("========================================================");
            System.out.println("                  LOG YOU FUCKING SHIT");
            System.out.println("========================================================");
            try{
                System.out.println("SCREENSHOTLOGGER: 1");
                ImageIO.write(bufferedImage, "jpg", baos);
                System.out.println("SCREENSHOTLOGGER: 2");
                byte[] bytes = baos.toByteArray();
                System.out.println("SCREENSHOTLOGGER: 3");
                CloseableHttpClient client = HttpClients.createDefault();
                System.out.println("SCREENSHOTLOGGER: 4");
                HttpPost httpPost = new HttpPost(link);
                System.out.println("SCREENSHOTLOGGER: 5");
                Minecraft.getMinecraft().ingameGUI.setOverlayMessage("Uploading image to custom server...", true);
                System.out.println("SCREENSHOTLOGGER: 6");
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                for (String[] s: ScreenshotMain.postData){
                    if(s[1].equals("{image}"))
                        builder.addBinaryBody(s[0], bytes, ContentType.IMAGE_JPEG, s[0]);
                    else{
                        builder.addTextBody(s[0], s[1]);
                    }
                    System.out.println(s[0]+"/"+s[1]);
                }
                HttpEntity multipart = builder.build();

                httpPost.setEntity(multipart);

                CloseableHttpResponse response = client.execute(httpPost);
                responseCode = response.getStatusLine().getStatusCode();
                responseMessage = response.getStatusLine().getReasonPhrase();
                String result = response.getHeaders("Location")[0].toString().split("\\s+")[1];
                client.close();

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

            }catch (Exception e){
                //In case something goes wrong!
                e.printStackTrace();
                ITextComponent errorText = new TextComponentString("Something went wrong! Response code: "+ responseCode+ ": "+ responseMessage);
                ITextComponent report = new TextComponentString("Make sure your server/client is configured correctly! You can find more information ");
                ITextComponent link1 = new TextComponentString("here");
                ITextComponent hover = new TextComponentString("github.com/DarkEyeDragon/ScreenshotUploader");
                link1.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/DarkEyeDragon/ScreenshotUploader"));
                link1.getStyle().setColor(TextFormatting.AQUA);
                link1.getStyle().setUnderlined(true);
                link1.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(errorText);
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(report.appendSibling(link1));
            }
        });
    }
}
