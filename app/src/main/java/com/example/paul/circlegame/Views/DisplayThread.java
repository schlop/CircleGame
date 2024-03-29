package com.example.paul.circlegame.Views;

import android.graphics.Canvas;

import com.example.paul.circlegame.Controllers.AppConstants;

/**
 * Created by Paul on 20/10/2015.
 */
public class DisplayThread extends Thread {

    private int measuredFps;
    private long startFps;
    private GameView view;
    private boolean running = false;
    private boolean alreadyStarted = false;

    public DisplayThread(GameView view) {
        measuredFps = 0;
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    public boolean getRunning(){
        return running;
    }

    public void setAlreadyStarted(boolean alreadyStarted){
        this.alreadyStarted = alreadyStarted;
    }

    public boolean isAlreadyStarted() {
        return alreadyStarted;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / AppConstants.FPS;
        long startTime;
        long sleepTime;
        startFps = System.currentTimeMillis();
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            if (startTime - startFps > 999){
                startFps = System.currentTimeMillis();
                view.setFps(measuredFps);
                measuredFps = 0;
            }
            measuredFps++;
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    view.onDraw(c);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}
        }
    }
}
