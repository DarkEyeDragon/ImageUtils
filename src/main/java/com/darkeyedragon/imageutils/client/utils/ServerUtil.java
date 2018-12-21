package com.darkeyedragon.imageutils.client.utils;

import com.darkeyedragon.imageutils.client.ImageUtilsMain;

import java.net.InetAddress;

public class ServerUtil{
    private static boolean isPinging = false;
    private static long lastPing = System.currentTimeMillis();
    private static int ping = 0;
    private static boolean isReachable;

    public static void ping (){
        if (!isPinging && (lastPing + 300 < System.currentTimeMillis())){
            long startTime = System.currentTimeMillis();
            reachable();
            ping = isReachable ? ((int) (System.currentTimeMillis() - startTime)) : -1;
        }
    }

    private static void reachable (){
        long currentTime = System.currentTimeMillis();
        try{
            isPinging = true;
            lastPing = currentTime;
            InetAddress address = InetAddress.getByName(StringFilter.getHostName(ImageUtilsMain.activeUploader.getUploader().getRequestUrl()));
            isReachable = address.isReachable(300);
            isPinging = false;
        }
        catch (Exception e){
            e.printStackTrace();
            isPinging = false;
        }
    }

    public static int getPing (){
        return ping;
    }

    public static boolean isReachable (){
        return isReachable;
    }
}
