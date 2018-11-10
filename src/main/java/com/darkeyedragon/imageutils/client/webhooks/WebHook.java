package com.darkeyedragon.imageutils.client.webhooks;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class WebHook{

    private final String url;

    public WebHook (String url){
        this.url = url;
    }

    public void sendImage (){
        try (CloseableHttpClient curClient = HttpClientBuilder.create()
                .setUserAgent("ScreenshotUploader/" + ImageUtilsMain.VERSION)
                .build()){
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            curClient.execute(httpPost);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
