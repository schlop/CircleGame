package com.example.paul.circlegame.Controllers;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

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
    public static final long DURATION_BLOCK = 10 * 1000;

    //Pictures drawn per second
    public static final int FPS = 30;

    //Colors of touched/notTouched objects
    public static final int TOUCH_COLOR = Color.GREEN;
    public static final int UNTOUCH_COLOR = Color.RED;

    /**
     * Set the screen properties
     */
    public static void initialize(Context context){
        setScreenSize(context);
        setScreenDensity(context);
    }

    private static void setScreenSize(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        SCREEN_WIDTH = height;
        SCREEN_HEIGHT = width;
    }

    private static void setScreenDensity(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int density = metrics.densityDpi;
        DENSITY = density;
    }


}
