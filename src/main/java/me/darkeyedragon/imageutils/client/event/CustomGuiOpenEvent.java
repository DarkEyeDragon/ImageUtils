package me.darkeyedragon.imageutils.client.event;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.client.UploadHandler;
import me.darkeyedragon.imageutils.client.gui.GuiImagePreviewer;
import me.darkeyedragon.imageutils.client.imageuploader.Uploader;
import me.darkeyedragon.imageutils.client.utils.ImageResource;
import me.darkeyedragon.imageutils.client.utils.ImageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class CustomGuiOpenEvent {


    private final Uploader uploader;
    private final UploadHandler uploadHandler;
    private final ImageUtilsMain main;

    public CustomGuiOpenEvent(ImageUtilsMain main) {
        this.uploader = main.getUploaderFactory().getUploader();
        this.uploadHandler = main.getUploadHandler();
        this.main = main;
    }

    //TODO switch to command
    @SubscribeEvent
    public void guiOpenEvent(net.minecraftforge.client.event.GuiOpenEvent e) {
        if (e.getGui() instanceof GuiConfirmOpenLink) {
            Minecraft mc = Minecraft.getMinecraft();
            ITextComponent textComponent = mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
            if (textComponent != null) {
                String link = Objects.requireNonNull(textComponent.getStyle().getClickEvent()).getValue();
                ImageResource imgResource = null;
                if (uploadHandler.getValidLinks().containsKey(link)) {
                    imgResource = new ImageResource(main, link, uploadHandler.getValidLinks().get(link), link);
                } else if (link.startsWith("http://LINK::")) {
                    String splitStr = link.replace("http://LINK::", "");
                    try {
                        Path path = Paths.get(Minecraft.getMinecraft().gameDir.getCanonicalPath(), "screenshots", splitStr);
                        imgResource = new ImageResource(main, splitStr, ImageUtil.getLocal(path.toFile()), false, path.toString());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                if (imgResource != null) {
                    e.setGui(new GuiImagePreviewer(imgResource));
                }
            } else {
                main.getLogger().warn("Something went wrong! Unable to get URL");
            }

        }
    }
}
