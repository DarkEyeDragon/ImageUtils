package com.darkeyedragon.imageutils.client.gui.lists;

public class GuiListElement{
    private int x;
    private int y;
    private int length;
    private int height;
    private boolean selected;
    private String message;
    private int selectionX1;
    private int selectionX2;
    private int selectionY1;
    private int selectionY2;

    public GuiListElement (int x, int y, int length, int height, int selectionOffsetX, int selectionOffsetY){
        this.x = x;
        this.y = y;
        this.length = length;
        this.height = height;
        selectionX1 = x - selectionOffsetX;
        selectionX2 = x + length + selectionOffsetX;
        selectionY1 = y - selectionOffsetY;
        selectionY2 = y + height / 2 + selectionOffsetY;
    }

    public int getSelectionX1 (){
        return selectionX1;
    }

    public void setSelectionX1 (int selectionX1){
        this.selectionX1 = selectionX1;
    }

    public int getSelectionX2 (){
        return selectionX2;
    }

    public void setSelectionX2 (int selectionX2){
        this.selectionX2 = selectionX2;
    }

    public int getSelectionY1 (){
        return selectionY1;
    }

    public void setSelectionY1 (int selectionY1){
        this.selectionY1 = selectionY1;
    }

    public int getSelectionY2 (){
        return selectionY2;
    }

    public void setSelectionY2 (int selectionY2){
        this.selectionY2 = selectionY2;
    }

    public int getX (){
        return x;
    }

    public void setX (int x){
        this.x = x;
    }

    public int getY (){
        return y;
    }

    public void setY (int y){
        this.y = y;
    }

    public int getLength (){
        return length;
    }

    public void setLength (int length){
        this.length = length;
    }

    public int getHeight (){
        return height;
    }

    public void setHeight (int height){
        this.height = height;
    }

    public boolean isSelected (){
        return selected;
    }

    public void setSelected (boolean selected){
        this.selected = selected;
    }

    public String getMessage (){
        return message;
    }

    public void setMessage (String message){
        this.message = message;
    }
}
