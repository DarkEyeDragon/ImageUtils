package com.darkeyedragon.imageutils.client.gui;

import net.minecraft.client.gui.*;

public class GuiConfirmAction extends GuiYesNo{

    final GuiScreen parent;
    private final String messageLine1;
    private final String messageLine2;

    GuiConfirmAction(GuiYesNoCallback parentScreenIn, String messageLine1In, String messageLine2In, int parentButtonClickedIdIn, GuiScreen parent){
        super(parentScreenIn, messageLine1In, messageLine2In, parentButtonClickedIdIn);
        this.parent = parent;
        messageLine1 =messageLine1In;
        messageLine2 =messageLine2In;
    }

    @Override
    public void initGui(){
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2-100, height/2+27, 80, 20, "Yes"));
        this.buttonList.add(new GuiButton(1, this.width / 2+20, height/2+27, 80, 20, "Cancel"));

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        drawRect(width/2-150,height/2-70, width/2+150, height/2+70, 0xff000000);
        drawHorizontalLine(width/2-150, width/2+149, height/2-70, 0x90ffffff);
        drawHorizontalLine(width/2-150, width/2+149, height/2+70, 0x90ffffff);
        drawVerticalLine(width/2-150, height/2-70, height/2+70, 0x90ffffff);
        drawVerticalLine(width/2+150, height/2-70, height/2+70, 0x90ffffff);
        drawCenteredString(mc.fontRenderer, messageLine1, width/2, height/4+45, 0xffffffff);
        drawCenteredString(mc.fontRenderer, messageLine2, width/2, height/4+70, 0xffffffff);
        //super.drawScreen(mouseX, mouseY, partialTicks);
        for(GuiButton aButtonList : this.buttonList){
            aButtonList.drawButton(this.mc, mouseX, mouseY, partialTicks);
        }
        for(GuiLabel aLabelList : this.labelList){
            aLabelList.drawLabel(this.mc, mouseX, mouseY);
        }
    }

    /*@Override
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (keyCode == 1)
        {
            this.mc.displayGuiScreen(parent);

            if (this.mc.currentScreen == null)
            {
                this.mc.setIngameFocus();
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if(button.id == 10){
            mc.displayGuiScreen(this);
            parentAction.confirm();
        }else if(button.id == 11){
            mc.displayGuiScreen(this);
        }
    }*/
}
