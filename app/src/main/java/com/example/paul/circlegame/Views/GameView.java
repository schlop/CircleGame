package com.example.paul.circlegame.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.paul.circlegame.Controllers.GameEngine;

/**
 * Created by Paul on 20/10/2015.
 * Contains the DisplayThread to render UI elements on the surfaceHolder plane
 */
public class GameView extends SurfaceView {
    Paint fpsPaint;
    private int fps;
    GameEngine gameEngine;
    private DisplayThread displayThread;

    public GameView(Context context, GameEngine gameEngine) {
        super(context);
        this.gameEngine = gameEngine;
        InitView();
    }

    /**
     * Initiate the view components
     */
    void InitView() {
        SurfaceHolder holder = getHolder();
        displayThread = new DisplayThread(this);
        fpsPaint = new Paint();
        fpsPaint.setColor(Color.WHITE);
        fpsPaint.setTextSize(20);
        setFocusable(true);


        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
                    /*DO NOTHING*/
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder arg0) {
                displayThread.setRunning(false);
                displayThread.setAlreadyStarted(true);
            }

            @Override
            public void surfaceCreated(SurfaceHolder arg0) {
                //Starts the display thread
                displayThread.setRunning(true);
                long time = System.currentTimeMillis();
                if (!displayThread.isAlreadyStarted()) {
                    displayThread.start();
                }
            }
        });
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public void stopDisplayThread() {
        displayThread.setRunning(false);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (displayThread.getRunning()) {
            gameEngine.update();
            canvas.drawColor(Color.BLACK);
            canvas.drawText(String.valueOf(fps), 20, 20, fpsPaint);
            gameEngine.draw(canvas);
        }
    }
}