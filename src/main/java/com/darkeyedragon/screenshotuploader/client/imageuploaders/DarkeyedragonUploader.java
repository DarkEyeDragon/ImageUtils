package com.darkeyedragon.screenshotuploader.client.imageuploaders;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class DarkeyedragonUploader{

    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    public void uploadImage(String base64String) throws Exception{

        URL url = new URL("http://api.darkeyedragon.me/php/upload.php");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.connect();

        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        String data = URLEncoder.encode(base64String, "utf-8");
        wr.write(data);
        System.out.print(data);
        wr.flush();

        //GIT THE RESPONSE
        /*BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String line;
        StringBuilder stb = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            stb.append(line).append("\n");
        }
        wr.close();
        rd.close();

        JsonObject jsonObject = new JsonParser().parse(stb.toString()).getAsJsonObject();
        Minecraft.getMinecraft().player.sendChatMessage("GUUD BOI");*/
    }
}
