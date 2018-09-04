package com.darkeyedragon.imageutils.client.gui;

import net.minecraft.client.gui.Gui;

public class Progressbar extends Gui{
    //TODO implement progressbar
    public Progressbar (int x, int y, int length, int height, int progress){
        this.drawGradientRect(x, y, x + length, y + height, 0xffffff, 0xffffff);
        this.drawGradientRect(x, y, x + progress, y + height, 0xE24C19, 0xE24C19);
    }
}
