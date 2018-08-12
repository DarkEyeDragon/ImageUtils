package com.darkeyedragon.imageutils.client.utils;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter{

    private static final String urlRegex = "(https?:((//)|(\\\\))+[\\w\\d:#@%/;$()~_?+-=\\\\.&]*)";
    private static Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);

    public static List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<>();
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(urlMatcher.group());
        }
        return containedUrls;
    }
    public static boolean isValidUrl(String stringToCheck){
        Matcher urlMatcher = pattern.matcher(stringToCheck);
        return urlMatcher.matches();
    }
    public static boolean isValidImage(String stringToCheck){
        URLConnection conn;
        try{
            conn = new URL(stringToCheck).openConnection();
            conn.setRequestProperty("User-Agent", "ScreenshotUploader/1.2");
            InputStream image = conn.getInputStream();
            return ImageIO.read(image) != null;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
    public static String getHostName(String link) throws URISyntaxException{
        URI uri = new URI(link);
        return uri.getHost();
    }
}
