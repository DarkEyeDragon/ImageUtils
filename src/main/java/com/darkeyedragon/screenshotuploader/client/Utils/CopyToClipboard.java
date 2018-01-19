package com.darkeyedragon.screenshotuploader.client.Utils;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class CopyToClipboard{

    private String copiedText;

    public boolean copy(String message){
        StringSelection stringSelection = new StringSelection(message);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        copiedText = message;
        return true;
    }

    public String getCopy(){
        return copiedText;
    }
}
