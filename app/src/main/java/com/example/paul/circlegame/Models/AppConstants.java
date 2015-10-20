package com.example.paul.circlegame.Models;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.paul.circlegame.Controllers.GameEngine;

/**
 * Created by Paul on 20/10/2015.
 * Contains all constants and launches the game engine after startup
 */
public class AppConstants {

    static GameEngine _engine;

    public static int SCREEN_WIDTH, SCREEN_HEIGHT;

    /**
     * Initialization of the application constants
     */
    public static void Initialization(Context context){
        SetScrrenSize(context);
        _engine = new GameEngine();
    }

    private static void SetScrrenSize(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        AppConstants.SCREEN_WIDTH = width;
        AppConstants.SCREEN_HEIGHT = height;
    }

    /**
     * @return GameEngine instance
     * */
    public static GameEngine GetEngine()
    {
        return _engine;
    }

    /**
     * Stops the given thread
     * @param thread
     * 			thread to stop
     * */
    public static void StopThread(Thread thread)
    {
        boolean retry = true;
        while (retry)
        {
            try
            {
                thread.join();
                retry = false;
            }
            catch (InterruptedException e) {}
        }
    }


}
