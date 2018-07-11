package com.darkeyedragon.imageutils.client.utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;

public class CopyToClipboard{

    private static String copiedText;

    public static boolean copy(String message){
        if(message != null){
            StringSelection stringSelection = new StringSelection(message);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            copiedText = message;
            return true;
        }else{
            return false;
        }
    }
    public static boolean copy(BufferedImage image){
        if(image != null){
            TransferableImage trans = new TransferableImage(image);
            Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
            c.setContents(trans, null);
            return true;
        }else{
            return false;
        }
    }
    public String getCopy(){
        return copiedText;
    }
}
