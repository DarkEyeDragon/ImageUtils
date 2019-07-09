package me.darkeyedragon.imageutils.client.imageuploaders;

import me.darkeyedragon.imageutils.client.ImageUtils;
import me.darkeyedragon.imageutils.client.ModConfig;
import me.darkeyedragon.imageutils.client.config.UploaderFile;
import me.darkeyedragon.imageutils.client.message.Messages;
import me.darkeyedragon.imageutils.client.utils.CopyToClipboard;
import me.darkeyedragon.imageutils.client.utils.Filter;
import me.darkeyedragon.imageutils.client.utils.JsonHelper;
import me.darkeyedragon.imageutils.client.webhooks.WebhookValidation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CustomUploader{

    private static MultipartEntityBuilder builder;
    private static UploaderFile uploaderFile;
    private static HttpPost httpPost;
    private static CloseableHttpClient client;
    private static String urlString;
    private static Minecraft mc;
    private static GuiNewChat chat;

    public static void uploadImage (BufferedImage bufferedImage){
        ImageUtils.fixedThreadPool.submit(() -> {
            Thread.currentThread().setName(ImageUtils.MODID + "/uploader");
            mc = Minecraft.getInstance();
            chat = mc.ingameGUI.getChatGUI();
            uploaderFile = ImageUtils.activeUploader;
            if (uploaderFile == null || uploaderFile.getUploader() == null){
                chat.printChatMessage(new TranslationTextComponent("imageutil.message.invalid_config"));
                return;
            }
            builder = MultipartEntityBuilder.create();
            client = HttpClients.createDefault();
            httpPost = new HttpPost(uploaderFile.getUploader().getRequestUrl());
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            if (uploaderFile.getArguments() != null){
                uploaderFile.getArguments().forEach((k, v) ->
                        builder.addTextBody(k, (String) v)
                );
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //int responseCode = 0;
            //String responseMessage = "";
            try{
                ImageIO.write(bufferedImage, "png", baos);
                byte[] bytes = baos.toByteArray();
                mc.ingameGUI.setOverlayMessage(I18n.format("imageutil.message.overlay_message.upload") + " " + ImageUtils.activeUploader.getDisplayName(), true);
                builder.addBinaryBody("image", bytes, ContentType.IMAGE_JPEG, uploaderFile.getUploader().getFileFormName());
                HttpEntity multipart = builder.build();
                httpPost.setEntity(multipart);
                CloseableHttpResponse response = client.execute(httpPost);

                if (uploaderFile.isJsonResponse()){
                    Map<String, String> responseJson = JsonHelper.readJsonFromUrl(response.getEntity().getContent());
                    urlString = responseJson.get(uploaderFile.getJsonResponseKey());
                    Messages.uploadMessage(urlString);
                }else{
                    //TODO do some further testing
                    Header header = response.getFirstHeader("Content-Type");
                    if (header.toString().contains("html")){
                        HttpEntity body = response.getEntity();
                        String content = EntityUtils.toString(body);
                        List<String> contentUrl = Filter.extractUrls(content);
                        mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent("Response: " + content));
                        mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent("Possible matches: " + Arrays.toString(contentUrl.toArray())));

                    }
                    Messages.uploadMessage(urlString);
                }
                if (ModConfig.copyToClipboard){
                    if (CopyToClipboard.copy(urlString)){
                        chat.printChatMessage(new TranslationTextComponent("imageutil.message.copy_to_clipboard"));
                    }else{
                        chat.printChatMessage(new TranslationTextComponent("imageutil.message.copy_to_clipboard_error"));
                    }
                }
                WebhookValidation.addLink(urlString);
            }
            catch (IOException ex){
                //In case something goes wrong!
                ex.printStackTrace();
                Messages.errorMessage(ex.getMessage());
            }finally{
                try{
                    client.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                    Messages.errorMessage(e.getMessage());
                }
            }
        });
    }
}
