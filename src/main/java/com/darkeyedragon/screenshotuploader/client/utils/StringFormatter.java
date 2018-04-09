package com.darkeyedragon.screenshotuploader.client.utils;


import java.util.ArrayList;
import java.util.List;

public class StringFormatter{

    public static List<String[]> postData(String[] string){
        List<String[]> result = new ArrayList<String[]>(){
        };
        for (String aString : string){
            result.add(aString.split("="));
        }
        return result;
    }
}
