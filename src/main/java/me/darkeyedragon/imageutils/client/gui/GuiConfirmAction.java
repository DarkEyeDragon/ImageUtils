package me.darkeyedragon.imageutils.client.gui;

import net.minecraft.client.gui.*;

public class GuiConfirmAction extends GuiYesNo{

    final GuiScreen parent;
    private final String messageLine1;
    private final String messageLine2;
    private final String title;
    //TODO add possibility to translate
    private String buttonConfirm = "Yes";
    private String buttonCancel = "Cancel";

    GuiConfirmAction (GuiYesNoCallback parentScreenIn, String title, String messageLine1In, String messageLine2In, int parentButtonClickedIdIn, GuiScreen parent){
        super(parentScreenIn, messageLine1In, messageLine2In, parentButtonClickedIdIn);
        this.parent = parent;
        this.messageLine1 = messageLine1In;
        this.messageLine2 = messageLine2In;
        this.title = title;
    }

    GuiConfirmAction (GuiYesNoCallback parentScreenIn, String title, String messageLine1In, String messageLine2In, String buttonConfirm, String buttonCancel, int parentButtonClickedIdIn, GuiScreen parent){
        super(parentScreenIn, "", "", parentButtonClickedIdIn);
        this.messageLine1 = messageLine1In;
        this.messageLine2 = messageLine2In;
        this.buttonConfirm = buttonConfirm;
        this.buttonCancel = buttonCancel;
        this.parent = parent;
        this.title = title;
    }

    @Override
    public void initGui (){
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, height / 2 + 27, 80, 20, buttonConfirm));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 20, height / 2 + 27, 80, 20, buttonCancel));

    }

    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks){
        drawRect(width / 2 - 150, height / 2 - 70, width / 2 + 150, height / 2 + 70, 0xff000000);
        drawHorizontalLine(width / 2 - 150, width / 2 + 150, height / 2 - 70, 0xffffffff);
        drawHorizontalLine(width / 2 - 150, width / 2 + 150, height / 2 + 70, 0xffffffff);
        drawVerticalLine(width / 2 - 150, height / 2 - 70, height / 2 + 70, 0xffffffff);
        drawVerticalLine(width / 2 + 150, height / 2 - 70, height / 2 + 70, 0xffffffff);
        drawCenteredString(mc.fontRenderer, title, width / 2, height / 2 - 60, 0xffffffff);
        if (!messageLine1.isEmpty()){
            drawCenteredString(mc.fontRenderer, messageLine1, width / 2, height / 2 - 40, 0xffffffff);
        }
        if (!messageLine2.isEmpty()){
            drawCenteredString(mc.fontRenderer, messageLine2, width / 2, height / 2 - 30, 0xffffffff);
        }

        for (GuiButton aButtonList : this.buttonList){
            aButtonList.drawButton(this.mc, mouseX, mouseY, partialTicks);
        }
        for (GuiLabel aLabelList : this.labelList){
            aLabelList.drawLabel(this.mc, mouseX, mouseY);
        }
    }

    public String getButtonConfirm (){
        return buttonConfirm;
    }

    public void setButtonConfirm (String buttonConfirm){
        this.buttonConfirm = buttonConfirm;
    }

    public String getButtonCancel (){
        return buttonCancel;
    }

    public void setButtonCancel (String buttonCancel){
        this.buttonCancel = buttonCancel;
    }
}
