package me.darkeyedragon.imageutils.client.gui.components;

public class Location{

    private final int X;
    private final int Y;
    private final int Z;


    public Location (int x, int y){
        this(x, y, 0);
    }

    public Location (int x, int y, int z){
        X = x;
        Y = y;
        Z = z;
    }
}
