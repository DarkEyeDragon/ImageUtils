package me.darkeyedragon.imageutils.client.imageuploader;


import me.darkeyedragon.imageutils.client.ImageUtilsMain;
import net.minecraft.client.Minecraft;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * The base uploader class that implements sync and async uploading.
 * Usually you'd only want to override the constructor and add your own
 * headers and settings on top of the already existing ones.
 */
public abstract class Uploader {

    private final MultipartEntityBuilder builder;
    private final ExecutorService executorService;
    private HttpClient httpClient;
    private List<NameValuePair> params;
    private HttpPost httpPost;
    private HttpResponse httpResponse;

    /**
     * Abstract class to extend on. Should not be used as is.
     */
    public Uploader(ExecutorService executorService) {
        httpClient = HttpClients.createDefault();
        params = new ArrayList<>(1);
        httpPost = new HttpPost();
        this.executorService = executorService;
        builder = MultipartEntityBuilder.create();
        httpPost.addHeader(HttpHeaders.ACCEPT, "text/html,json");

        //TODO figure out why this header returns 500 on the imgur api
        //httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "image/png");

        httpPost.addHeader("Content-Transfer-Encoding", "binary");
        httpPost.addHeader(HttpHeaders.USER_AGENT, ImageUtilsMain.getMODID() + "/1.0.5");
    }

    /**
     * Abstract class to extend on. Should not be used as is.
     */
    public Uploader(String url, ExecutorService executorService) {
        this(executorService);
        setUrl(url);
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

    //TODO convert to binaryFile
    private HttpResponse upload(BufferedImage bufferedImage) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final byte[] imageInBytes = baos.toByteArray();
        File temp = File.createTempFile(ImageUtilsMain.getMODID(), null, null);
        ImageIO.write(bufferedImage, "png", temp);
        baos.flush();
        baos.close();
        builder.addPart("image", new FileBody(temp));
        builder.addTextBody("type", "file");
        httpPost.setEntity(builder.build());
        httpResponse = httpClient.execute(httpPost);
        System.out.println(Arrays.toString(httpPost.getAllHeaders()));
        return httpResponse;
    }

    public CompletableFuture<HttpResponse> uploadAsync(BufferedImage bufferedImage) {
        //showUploadStatus();
        return CompletableFuture.supplyAsync(() -> {
            try {
                return upload(bufferedImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void showUploadStatus() {
        Minecraft.getMinecraft().ingameGUI.getBossOverlay().renderBossHealth();
    }

    public String getUrl() {
        return httpPost.getURI().toString();
    }

    public void setUrl(String url) {
        httpPost.setURI(URI.create(url));
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public List<NameValuePair> getParams() {
        return params;
    }

    public void setParams(List<NameValuePair> params) {
        this.params = params;
    }

    public MultipartEntityBuilder getBuilder() {
        return builder;
    }

    public HttpPost getHttpPost() {
        return httpPost;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }
}
