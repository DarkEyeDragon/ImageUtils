package me.darkeyedragon.imageutils.client.imageuploader;


import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public abstract class BaseUploader implements Uploader {

    private HttpClient httpClient;
    private List<NameValuePair> params;
    private HttpPost httpPost;
    private HttpResponse httpResponse;


    /**
     * Abstract class to extend on. Should not be used as is.
     *
     */
    public BaseUploader() {
        httpClient = HttpClients.createDefault();
        params = new ArrayList<>(1);
        httpPost = new HttpPost();
        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        httpPost.addHeader(HttpHeaders.ACCEPT, "text/html,json");
        httpPost.addHeader(HttpHeaders.USER_AGENT, ImageUtilsMain.getMODID()+"/"+ImageUtilsMain.getVERSION());
    }

    /**
     * Abstract class to extend on. Should not be used as is.
     *
     */
    public BaseUploader(String url) {
        this();
        setUrl(url);
    }

    public void setUrl(String url) {
        httpPost.setURI(URI.create(url));
    }

    /**
     * Add a parameter to the request body
     * @param key set a key for the request body
     * @param value set a value for the request body
     */
    public void addParam(String key, String value) throws UnsupportedEncodingException {
        addParam(new BasicNameValuePair(key, value));
    }

    /**
     * @param nameValuePair set a {@link org.apache.http.NameValuePair NameValuePair}
     */
    public void addParam(NameValuePair nameValuePair) throws UnsupportedEncodingException {
        params.add(nameValuePair);
        httpPost.setEntity(new UrlEncodedFormEntity(params));
    }

    public void setParams(List<NameValuePair> params) {
        this.params = params;
    }

    @Override
    public HttpResponse upload(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            addParam("image", Base64.getEncoder().encodeToString(imageInByte));

            return httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUrl() {
        return httpPost.getURI().toString();
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public List<NameValuePair> getParams() {
        return params;
    }

    public HttpPost getHttpPost() {
        return httpPost;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

}
