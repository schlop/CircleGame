package com.example.paul.circlegame.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.paul.circlegame.Controllers.AppConstants;
import com.example.paul.circlegame.Controllers.GameEngine;

/**
 * Created by Paul on 20/10/2015.
 * Contains the DisplayThread to render UI elements on the surfaceHolder plane
 */
public class GameView extends SurfaceView {
    Context _context;
    Paint fpsPaint;
    private int fps;
    private DisplayThread _displayThread;

    public GameView(Context context, GameEngine gEngine) {
        super(context);
        _context = context;
        InitView();
    }

    /**
     * Initiate the view components
     */
    void InitView() {
        SurfaceHolder holder = getHolder();
        _displayThread = new DisplayThread(this);
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
                _displayThread.setRunning(false);
                _displayThread.setAlreadyStarted(true);
            }

            @Override
            public void surfaceCreated(SurfaceHolder arg0) {
                //Starts the display thread
                _displayThread.setRunning(true);
                long time = System.currentTimeMillis();
                if (!_displayThread.isAlreadyStarted()) {
                    _displayThread.start();
                }
            }
        });
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public void stopDisplayThread() {
        _displayThread.setRunning(false);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (_displayThread.getRunning()) {
            AppConstants.GetEngine().Update();
            canvas.drawColor(Color.BLACK);
            canvas.drawText(String.valueOf(fps), 20, 20, fpsPaint);
            AppConstants.GetEngine().Draw(canvas);
        }
    }
}