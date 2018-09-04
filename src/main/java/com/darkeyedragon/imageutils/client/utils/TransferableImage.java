package com.darkeyedragon.imageutils.client.utils;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

public class TransferableImage implements Transferable{
    private Image i;

    TransferableImage (Image i){
        this.i = i;
    }

    public Object getTransferData (DataFlavor flavor) throws UnsupportedFlavorException{
        if (flavor.equals(DataFlavor.imageFlavor) && i != null){
            return i;
        }else{
            throw new UnsupportedFlavorException(flavor);
        }
    }

    public DataFlavor[] getTransferDataFlavors (){
        DataFlavor[] flavors = new DataFlavor[1];
        flavors[0] = DataFlavor.imageFlavor;
        return flavors;
    }

    public boolean isDataFlavorSupported (DataFlavor flavor){
        DataFlavor[] flavors = getTransferDataFlavors();
        for (DataFlavor flavor1 : flavors){
            if (flavor.equals(flavor1)){
                return true;
            }
        }
        return false;
    }
}
