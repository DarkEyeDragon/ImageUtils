package com.darkeyedragon.imageutils.client.gui;

import com.darkeyedragon.imageutils.client.ModConfig;
import com.darkeyedragon.imageutils.client.utils.CopyToClipboard;
import com.darkeyedragon.imageutils.client.utils.ImageResource;
import com.darkeyedragon.imageutils.client.utils.ImageUtil;
import com.darkeyedragon.imageutils.client.webhooks.DiscordWebhook;
import com.darkeyedragon.imageutils.client.webhooks.WebhookValidation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GuiImagePreviewer extends GuiScreen{
    private final String urlStr;
    private final ImageResource imgResource;
    private final int scale;
    private BufferedImage bufferedImage;
    private ResourceLocation resourceLocation;
    private boolean preview = true;
    private final Minecraft mc;
    private final GuiNewChat chat;

    public GuiImagePreviewer (ImageResource imgResource){
        mc = Minecraft.getMinecraft();
        chat = mc.ingameGUI.getChatGUI();
        scale = new ScaledResolution(mc).getScaleFactor();
        this.imgResource = imgResource;
        this.bufferedImage = imgResource.getImage();
        this.urlStr = imgResource.getUrl();
        generateImage();
    }

    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks){
        drawDefaultBackground();
        if (preview){
            int x = (width / 2 - (bufferedImage.getWidth() / 2) / scale);
            int y = (height / 2 - (bufferedImage.getHeight() / 2) / scale);
            mc.getTextureManager().bindTexture(resourceLocation);
            drawModalRectWithCustomSizedTexture(x, y, 0, 0, bufferedImage.getWidth() / scale, bufferedImage.getHeight() / scale, bufferedImage.getWidth() / scale, bufferedImage.getHeight() / scale);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked (int mouseX, int mouseY, int mouseButton){
        try{
            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        mc.displayGuiScreen(null);
    }

    @Override
    public boolean doesGuiPauseGame (){
        return false;
    }

    @Override
    public void initGui (){
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 50 - 105, 10, 100, 20, I18n.format("imageutil.gui.image_preview.copy_image")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 50, 10, 100, 20, I18n.format("imageutil.gui.image_preview.open_image")));
        GuiButton urlButton = new GuiButton(2, this.width / 2 - 50 + 105, 10, 100, 20, I18n.format("imageutil.gui.image_preview.copy_link"));
        GuiButton webhookButton = new GuiButton(3, this.width / 2 - 55, height - (height / 10), 110, 20, I18n.format("imageutil.gui.image_preview.upload_webhook"));
        if (urlStr == null){
            urlButton.enabled = false;
            webhookButton.enabled = false;
        }else if (!WebhookValidation.validate(urlStr)){
            webhookButton.enabled = false;
        }
        this.buttonList.add(urlButton);
        this.buttonList.add(webhookButton);
        if (bufferedImage == null){
            preview = false;
        }
    }

    @Override
    protected void actionPerformed (GuiButton button){
        TextComponentTranslation clipboard = new TextComponentTranslation("imageutil.message.copy_to_clipboard");
        TextComponentTranslation clipboard_error = new TextComponentTranslation("imageutil.message.copy_to_clipboard_error");
        if (button.id == 0){
            if (CopyToClipboard.copy(bufferedImage)){
                chat.printChatMessage(clipboard);
            }else{
                chat.printChatMessage(clipboard_error);
            }
        }else if (button.id == 1){
            if (imgResource.getPath() != null){
                try{
                    Desktop.getDesktop().open(new File(imgResource.getPath()));
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }else if (imgResource.getUrl() != null){
                try{
                    Desktop.getDesktop().browse(new URI(urlStr));
                }
                catch (IOException | URISyntaxException e){
                    e.printStackTrace();
                    chat.printChatMessage(new TextComponentTranslation("imageutil.message.browser_error"));
                }
            }
        }else if (button.id == 2){
            if (CopyToClipboard.copy(urlStr)){
                chat.printChatMessage(clipboard);
            }else{
                chat.printChatMessage(clipboard_error);
            }
        }else if (button.id == 3){
            EntityPlayer player = mc.player;
            if (!ModConfig.webhookUrl.isEmpty()){
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                DiscordWebhook discordWebhook = new DiscordWebhook(ModConfig.webhookUrl);
                discordWebhook.setUsername("Image Utils");
                DiscordWebhook.EmbedObject embedObject = new DiscordWebhook.EmbedObject();
                embedObject.setUrl(urlStr);
                embedObject.setImage(urlStr);
                embedObject.setTitle("Uploaded image");
                embedObject.setTimestamp(dateFormat.format(new Date()));
                embedObject.setFooter("Requested by " + player.getName(), "https://mc-heads.net/avatar/" + player.getUniqueID() + "/32");
                discordWebhook.setAvatarUrl("https://media.forgecdn.net/avatars/168/845/636711656195462582.png");
                discordWebhook.addEmbed(embedObject);
                try{
                    discordWebhook.execute();
                    chat.printChatMessage(new TextComponentTranslation("imageutil.message.webhook.sent"));
                    WebhookValidation.removeLink(urlStr);
                }
                catch (IOException e){
                    chat.printChatMessage(new TextComponentTranslation("imageutil.message.webhook.error").appendSibling(new TextComponentString(e.getMessage())));
                    e.printStackTrace();
                }
            }else{
                chat.printChatMessage(new TextComponentTranslation("imageutil.message.webhook.not_sent"));
            }
        }
    }

    private void generateImage (){
        int maxImgWidth = 1024;
        if (bufferedImage == null){
            return;
        }
        if (bufferedImage.getWidth() > maxImgWidth){
            bufferedImage = ImageUtil.resize(bufferedImage, maxImgWidth, maxImgWidth);
        }
        if (bufferedImage == null){
            return;
        }
        resourceLocation = mc.renderEngine.getDynamicTextureLocation("urlImage", new DynamicTexture(bufferedImage));
    }
}
