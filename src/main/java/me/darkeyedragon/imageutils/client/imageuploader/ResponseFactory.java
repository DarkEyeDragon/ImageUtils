package me.darkeyedragon.imageutils.client.imageuploader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.darkeyedragon.imageutils.client.ModConfig;
import me.darkeyedragon.imageutils.client.adaptor.CustomResponseAdaptor;
import me.darkeyedragon.imageutils.client.adaptor.ImgurResponseTypeAdaptor;
import me.darkeyedragon.imageutils.client.adaptor.UploadResponseAdaptor;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResponseFactory {

    public static UploadResponseAdaptor getResponseAdaptor(HttpResponse response) {

        try {
            String jsonResult = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            if (ModConfig.customServer) {
                JsonObject jsonObject = new JsonParser().parse(jsonResult).getAsJsonObject();
                return new CustomResponseAdaptor(jsonObject.get("url").getAsString(), response.getStatusLine().getStatusCode());
            } else {
                return new Gson().fromJson(jsonResult, ImgurResponseTypeAdaptor.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
