package me.darkeyedragon.imageutils.client.imageuploader;


import io.netty.handler.codec.http.HttpHeaderValues;
import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
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
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;

public abstract class BaseUploader implements Uploader {

    private HttpClient httpClient;
    private List<NameValuePair> params;
    private HttpPost httpPost;
    private HttpResponse httpResponse;
    private final MultipartEntityBuilder builder;
    private final ExecutorService executorService;

    /**
     * Abstract class to extend on. Should not be used as is.
     */
    public BaseUploader(ExecutorService executorService) {
        httpClient = HttpClients.createDefault();
        params = new ArrayList<>(1);
        httpPost = new HttpPost();
        this.executorService = executorService;
        builder = MultipartEntityBuilder.create();
        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString());
        httpPost.addHeader(HttpHeaders.ACCEPT, "text/html,json");
        httpPost.addHeader(HttpHeaders.USER_AGENT, ImageUtilsMain.getMODID() + "/" + ImageUtilsMain.getVERSION());
    }

    /**
     * Abstract class to extend on. Should not be used as is.
     */
    public BaseUploader(String url, ExecutorService executorService) {
        this(executorService);
        setUrl(url);
    }

    public void setUrl(String url) {
        httpPost.setURI(URI.create(url));
    }

    /**
     * Add a parameter to the request body
     *
     * @param key   set a key for the request body
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

    //TODO convert to binaryFile
    @Override
    public HttpResponse upload(BufferedImage bufferedImage) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", baos);
            final byte[] imageInByte = baos.toByteArray();
            baos.flush();
            baos.close();
            //httpPost.setEntity(builder.addBinaryBody("image", imageInByte).build());
            addParam("image", Base64.getEncoder().encodeToString(imageInByte));
            return httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void uploadAsync(BufferedImage bufferedImage, BiConsumer<HttpResponse, Throwable> callback) {
        executorService.submit(() -> {
            try {
                callback.accept(upload(bufferedImage), null);
            } catch (Exception ex) {
                callback.accept(null, ex);
            }
        });
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
