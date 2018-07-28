package com.darkeyedragon.imageutils.client.gui.buttons;

import net.minecraft.client.gui.GuiButton;

import java.util.List;

public class GuiConfigButton extends  GuiButton{

    public enum Types{
        BOOLEAN, CYCLE_LIST, LIST
    }

    private Types type;
    private GuiButton button;
    private List<ListElement> list;

    public GuiConfigButton(int buttonId, int x, int y, String buttonText, Types type){
        super(buttonId, x, y, buttonText);
        this.type = type;
    }
    public GuiConfigButton(int buttonId, int x, int y, String buttonText, List<String> list, Types type){
        super(buttonId, x, y, buttonText);
        this.type = type;
        int index = list.indexOf(buttonText);
        if(index > 0){
            this.list.add(new ListElement(true, buttonText));
        }else{
            this.list.add(new ListElement(false, list.get(index)));
        }
    }
    public Types getType(){
        return  type;
    }
    public GuiButton getButton(){
        return button;
    }

    public List<ListElement> getList(){
        return list;
    }
}
