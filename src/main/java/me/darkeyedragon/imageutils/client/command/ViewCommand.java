package me.darkeyedragon.imageutils.client.command;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import me.darkeyedragon.imageutils.client.ModConfig;
import me.darkeyedragon.imageutils.client.gui.GuiImagePreviewer;
import me.darkeyedragon.imageutils.client.image.DownloadImage;
import me.darkeyedragon.imageutils.client.util.ImageResource;
import me.darkeyedragon.imageutils.client.util.ImageUtil;
import me.darkeyedragon.imageutils.client.util.OutputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewCommand implements ICommand {

    private final String name;
    private final String usage;
    private final ImageUtilsMain imageUtilsMain;
    private final List<String> aliases = new ArrayList<>();
    private final List<String> tabs = new ArrayList<>();

    public ViewCommand(ImageUtilsMain imageUtilsMain) {
        this.name = new TextComponentTranslation("imageutil.command.view.name").getUnformattedComponentText();
        this.usage = new TextComponentTranslation("imageutil.command.view.usage").getUnformattedComponentText();
        this.imageUtilsMain = imageUtilsMain;
        aliases.add("view");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return usage;
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer) {
            DownloadImage downloadImage;
            try {
                downloadImage = ImageUtil.downloadFromUrl(args[0], ModConfig.maxImageSize);
                if (downloadImage.getBufferedImage() == null) {
                    OutputHandler.sendMessage(new TextComponentString(downloadImage.getResponseMessage().toString()).setStyle(new Style().setColor(TextFormatting.RED)), null);
                    Minecraft.getMinecraft().addScheduledTask(()
                            -> Minecraft.getMinecraft().displayGuiScreen(new GuiConfirmOpenLink(Minecraft.getMinecraft().currentScreen, args[0], 0, false))
                    );
                    return;
                }
                BufferedImage finalImage = downloadImage.getBufferedImage();
                Minecraft.getMinecraft().addScheduledTask(()
                        -> Minecraft.getMinecraft().displayGuiScreen(new GuiImagePreviewer(new ImageResource(imageUtilsMain, args[0], finalImage, args[0])))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return tabs;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
