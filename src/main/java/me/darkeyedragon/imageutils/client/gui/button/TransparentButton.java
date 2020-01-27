package me.darkeyedragon.imageutils.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;

public class TransparentButton extends Button {
    public TransparentButton(int p_i51141_1_, int p_i51141_2_, int p_i51141_3_, int p_i51141_4_, String p_i51141_5_, IPressable p_i51141_6_) {
        super(p_i51141_1_, p_i51141_2_, p_i51141_3_, p_i51141_4_, p_i51141_5_, p_i51141_6_);
    }

    @Override
    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        //super.renderButton(p_renderButton_1_, p_renderButton_2_, p_renderButton_3_);
        this.drawCenteredString(Minecraft.getInstance().fontRenderer, this.getMessage(), this.x + this.width / 2 - 5, this.y + (this.height - 8) / 2, 16777215);
    }
}
