package com.darkeyedragon.imageutils.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiConfirmAction extends GuiScreen{

    private final IGuiConfirmAction parentAction;
    private final GuiScreen parent;

    public GuiConfirmAction(IGuiConfirmAction parentAction){
        this.parentAction = parentAction;
        this.parent = (GuiScreen)parentAction;
    }

    @Override
    public void initGui(){
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(10, this.width / 2-120, height/2+37, 80, 20, "Yes"));
        this.buttonList.add(new GuiButton(11, this.width / 2+40, height/2+37, 80, 20, "Cancel"));

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        drawRect(width/2-150,height/2-70, width/2+150, height/2+70, 0x40000000);
        drawCenteredString(mc.fontRenderer, "Are you sure you want to delete this screenshot?", width/2, height/4+30, 0xffffffff);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
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
            mc.displayGuiScreen(parent);
            parentAction.confirm();
        }else if(button.id == 11){
            mc.displayGuiScreen(parent);
        }
    }
}
