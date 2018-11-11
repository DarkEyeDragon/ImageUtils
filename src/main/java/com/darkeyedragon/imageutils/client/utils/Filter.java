package com.darkeyedragon.imageutils.client.utils;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter{

    private static final String urlRegex = "(https?:((//)|(\\\\))+[\\w\\d:#@%/;$()~_?+-=\\\\.&]*)";
    private static final Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);

    public static List<String> extractUrls (String text){
        List<String> containedUrls = new ArrayList<>();
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find()){
            containedUrls.add(urlMatcher.group());
        }
        return containedUrls;
    }

    public static boolean isValidUrl (String stringToCheck){
        Matcher urlMatcher = pattern.matcher(stringToCheck);
        return urlMatcher.matches();
    }

    public static boolean isValidImage (String stringToCheck){
        try (CloseableHttpClient curClient = HttpClientBuilder.create().setUserAgent("ScreenshotUploader/" + ImageUtilsMain.VERSION).build()){
            return curClient.execute(new HttpGet(stringToCheck)).getFirstHeader("Content-Type").getValue().startsWith("image/");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    static String getHostName (String link) throws URISyntaxException{
        URI uri = new URI(link);
        return uri.getHost();
    }
}
