package me.darkeyedragon.imageutils.client.gui.lists;

import me.darkeyedragon.imageutils.client.config.UploaderFile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiList extends GuiScreen {

    private final String title;
    private final FontRenderer fr;
    private final List<UploaderFile> uploaders;
    private List<GuiListElement> selectionList;

    public GuiList(String title, List<UploaderFile> uploaders) {
        this.title = title;
        this.uploaders = uploaders;
        fr = Minecraft.getMinecraft().fontRenderer;
    }

    @Override
    public void initGui() {
        super.initGui();
        selectionList = new ArrayList<>();
        int offsetY = 0;
        for (UploaderFile uploaderFile : uploaders) {
            String message = uploaderFile.getDisplayName();
            GuiListElement selection = new GuiListElement(width / 2, 50 + 30 * offsetY, mc.fontRenderer.getStringWidth(message), 20, 5, 5);
            selection.setMessage(message);
            selectionList.add(selection);
            offsetY++;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawCenteredString(mc.fontRenderer, title, width / 2, 20 + 1, 0x000000);
        drawCenteredString(mc.fontRenderer, title, width / 2, 20, 0xffffff);
        for (GuiListElement selection : selectionList) {
            if (selection.isSelected()) {
                drawSelection(selection);
            }
            drawString(mc.fontRenderer, selection.getMessage(), selection.getX(), selection.getY(), Color.WHITE.getRGB());
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (GuiListElement selection : selectionList) {
            if ((mouseX > selection.getSelectionX1() && mouseX < selection.getSelectionX2()) && (mouseY > selection.getSelectionY1() && mouseY < selection.getSelectionY2())) {
                for (GuiListElement toDeselect : selectionList) {
                    if (toDeselect.isSelected()) {
                        toDeselect.setSelected(false);
                    }
                }
                selection.setSelected(!selection.isSelected());
            }
        }
    }

    private void drawSelection(GuiListElement element) {
        drawRect(element.getSelectionX1(), element.getSelectionY1(), element.getSelectionX2(), element.getSelectionY2(), 0xAA000000);
        //drawHoveringText(x+"x"+y, x, y);
    }
}
