package com.darkeyedragon.imageutils.client.gui;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import com.darkeyedragon.imageutils.client.config.UploaderFile;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO look if this is a viable solution
public class GuiUploaders extends GuiScreen{

    private Map<String, List<GuiTextField>> mapTextFields = new HashMap<>();

    @Override
    public void initGui (){
        int id = 0;
        int offset = 40;
        for (UploaderFile uf : ImageUtilsMain.uploaders){
            List<GuiTextField> textFields = new ArrayList<>();
            GuiTextField requestUrl = new GuiTextField(id, mc.fontRenderer, width / 2 - (100), offset += 30, 200, 20);
            requestUrl.setText(uf.getUploader().getRequestUrl());
            textFields.add(requestUrl);
            id++;
            GuiTextField imageName = new GuiTextField(id, mc.fontRenderer, width / 2 - 100, offset += 25, 200, 20);
            imageName.setText(uf.getUploader().getFileFormName());
            textFields.add(imageName);
            id++;
            GuiTextField arguments = new GuiTextField(id, mc.fontRenderer, width / 2 - 100, offset += 25, 200, 20);
            arguments.setMaxStringLength(2000);
            arguments.setText(uf.getArguments().toString());
            offset += 20;
            textFields.add(arguments);
            mapTextFields.put(uf.getFileName(), textFields);
        }
        super.initGui();
    }

    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks){
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawDefaultBackground();
        drawCenteredString(mc.fontRenderer, "Uploaders", width / 2, 10, 0xffffff);

        int counter = 0;
        for (UploaderFile uf : ImageUtilsMain.uploaders){
            List<GuiTextField> guiTextFields = mapTextFields.get(uf.getFileName());
            mc.fontRenderer.drawString("UploadUrl:", width / 2 - 170, counter * 100 + 75, 0xffffff, true);
            mc.fontRenderer.drawString("Image name:", width / 2 - 170, counter * 100 + 75 + 27, 0xffffff, true);
            mc.fontRenderer.drawString("Arguments:", width / 2 - 170, counter * 100 + 75 + 52, 0xffffff, true);
            drawCenteredString(mc.fontRenderer, uf.getFileName(), width / 2, counter * 100 + 50, 0xffffff);
            counter++;
            for (GuiTextField guiTextField : guiTextFields){
                guiTextField.drawTextBox();
            }
        }
    }

    protected void mouseClicked (int mouseX, int mouseY, int mouseButton) throws IOException{
        for (UploaderFile uf : ImageUtilsMain.uploaders){
            List<GuiTextField> guiTextFields = mapTextFields.get(uf.getFileName());
            for (GuiTextField guiTextField : guiTextFields){
                guiTextField.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped (char typedChar, int keyCode) throws IOException{
        for (UploaderFile uf : ImageUtilsMain.uploaders){
            List<GuiTextField> guiTextFields = mapTextFields.get(uf.getFileName());
            for (GuiTextField guiTextField : guiTextFields){
                guiTextField.textboxKeyTyped(typedChar, keyCode);
            }
        }
        super.keyTyped(typedChar, keyCode);
    }

    public void updateScreen (){
        for (UploaderFile uf : ImageUtilsMain.uploaders){
            List<GuiTextField> guiTextFields = mapTextFields.get(uf.getFileName());
            for (GuiTextField guiTextField : guiTextFields){
                guiTextField.updateCursorCounter();
            }
        }
        super.updateScreen();
    }
}
