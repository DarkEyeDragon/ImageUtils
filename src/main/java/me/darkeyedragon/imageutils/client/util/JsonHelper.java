package me.darkeyedragon.imageutils.client.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.darkeyedragon.imageutils.client.adaptor.ImgurResponseTypeAdaptor;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonHelper {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static Gson readJsonFromUrl(String url) throws IOException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            Gson gson = new Gson();
            gson.fromJson(jsonText, Map.class);
            return gson;
        }
    }

    public static ImgurResponseTypeAdaptor toImgurResponse(String string) {
        return new Gson().fromJson(string, ImgurResponseTypeAdaptor.class);
    }

    //TODO implement custom uploader response

    public static Map<String, String> readJsonFromUrl(InputStream inputStream) throws IOException {
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            return gson.fromJson(jsonText, type);
        } finally {
            inputStream.close();
        }
    }
}
