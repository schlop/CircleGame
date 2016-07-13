package com.example.paul.circlegame.Controllers;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

/**
 * Created by Paul on 20/10/2015.
 * Contains all constants
 */
public class AppConstants {

    //properties of the device screen
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int DENSITY;

    //radius of the displayed circle
    public static final float RADIUS = 0.3F;

    //speed the displayed circle moves with
    public static final int SPEED = 10;

    //period of time circle is displayed
    public static final long DURATION_BLOCK = 60 * 1000;

    //Pictures drawn per second
    public static final int FPS = 30;
    public static final boolean SHOW_FPS = false;

    //Colors of touched/notTouched objects
    public static final int TOUCH_COLOR = Color.GREEN;
    public static final int UNTOUCH_COLOR = Color.RED;

    public static String IP_PREFIX = "192.168.178.";
    public static final String START_MESSAGE = "PT_TRUE";
    public static final String END_MESSAGE = "#END";

    /**
     * Set the screen properties
     */
    public static void initialize(Context context){
        setScreenSize(context);
        setScreenDensity(context);
        //setIpPrefix(context);
    }

    /**
     * Gets the measurements of the screen. Smaller value should always be saved as height because
     * we're using the device in the horizontal mode
     * @param context
     */
    private static void setScreenSize(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        if (width > height){
            SCREEN_WIDTH = width;
            SCREEN_HEIGHT = height;
        }
        else{
            SCREEN_WIDTH = height;
            SCREEN_HEIGHT = width;
        }
    }

    /**
     * Get screen density. Required to calculate the size for every screen individually
     * @param context
     */
    private static void setScreenDensity(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int density = metrics.densityDpi;
        DENSITY = density;
    }

    /**
     * Gets own IP address if device is connected to a WIFI network. Cuts the last digits to only safe the ip prefix
     * @param context
     */
    private static void setIpPrefix(Context context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endianif needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e("WIFIIP", "Unable to get host address.");
            ipAddressString = null;
        }
        String[] tokens = ipAddressString.split("\\.");
        IP_PREFIX = tokens[0] + "." + tokens[1] + "." + tokens[2] + ".";
    }


}
