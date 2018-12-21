package com.darkeyedragon.imageutils.client.gui;

import com.darkeyedragon.imageutils.client.gui.lists.ListItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiFilter extends GuiScreen{
    private List<ListItem> listItems;
    private Minecraft mc;
    private FontRenderer fr;

    private final int OFFSET_X = 10;
    private final int OFFSET_Y = 10;


    public GuiFilter (){
        this(new ArrayList<>());
    }

    public GuiFilter (List<ListItem> listItems){
        this.listItems = listItems;
        this.mc = Minecraft.getMinecraft();
        this.fr = mc.fontRenderer;
    }

    @Override
    public void drawScreen (int mouseX, int mouseY, float partialTicks){
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (int y = 0; y < listItems.size(); y++){
            ListItem li = listItems.get(y);
            String title = li.getTitle();
            String description = li.getDescription();
            drawString(fr, title, OFFSET_X, 20 * y + 80, 0xffffffff);
            drawString(fr, description, OFFSET_X, 20 * y + 90, 0x00323232);
        }
    }

    @Override
    protected void mouseClicked (int mouseX, int mouseY, int mouseButton) throws IOException{
        super.mouseClicked(mouseX, mouseY, mouseButton);
        listItems.forEach((v) -> {
            int length;
            if (fr.getStringWidth(v.getDescription()) > fr.getStringWidth(v.getTitle())){
                length = fr.getStringWidth(v.getDescription());
            }else{
                length = fr.getStringWidth(v.getTitle());
            }
            if (mouseX >= OFFSET_X && mouseX <= OFFSET_X + length){
                //drawSelection(OFFSET_X, );
            }
        });
    }

    public void addListItem (ListItem listItem){
        listItems.add(listItem);
    }

    //TODO FINISH SETTING UP SELECTION
    private void drawSelection (int startX, int endX, int y, int color){
        drawHorizontalLine(startX, endX, y, color);
        drawHorizontalLine(startX, endX, y + OFFSET_Y, color);
    }
}
