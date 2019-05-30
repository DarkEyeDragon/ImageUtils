package me.darkeyedragon.imageutils.client.gui.buttons;

import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonWithImage extends GuiButton{

    public GuiButtonWithImage (int buttonId, int xPos, int yPos, int width, int height){
        super(buttonId, xPos, yPos, width, height, "");
    }

    public void drawButton (Minecraft mc, int mouseX, int mouseY, float partialTicks){
        if (this.visible){
            mc.getTextureManager().bindTexture(new ResourceLocation(ImageUtilsMain.MODID, "textures/widgets.png"));
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            this.drawTexturedModalRect(this.x, this.y, 0, 0, this.width, this.height);
        }
    }
}
