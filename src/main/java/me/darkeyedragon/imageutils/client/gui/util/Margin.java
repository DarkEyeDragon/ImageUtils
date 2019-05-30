package me.darkeyedragon.imageutils.client.gui.util;

public class Margin {

    private int top;
    private int bottom;
    private int left;
    private int right;

    public Margin(int margin) {
        this(margin, margin, margin, margin);
    }

    public Margin(int vertical, int horizontal) {
        this(vertical, vertical, horizontal, horizontal);
    }

    public Margin(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    public int[] getMargin(){
        return new int[]{top, bottom, left, right};
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
