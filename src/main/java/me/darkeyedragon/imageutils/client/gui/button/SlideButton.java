package me.darkeyedragon.imageutils.client.gui.button;

import net.minecraft.client.gui.AbstractGui;

public class SlideButton extends TransparentButton {
    public SlideButton(int x, int y, int width, int height, String message, IPressable iPressable) {
        super(x, y, width, height, message, iPressable);
        AbstractGui.fill(x, y, width, height, 0x88000000);
    }

    @Override
    public void onPress() {
        super.onPress();
    }
}
