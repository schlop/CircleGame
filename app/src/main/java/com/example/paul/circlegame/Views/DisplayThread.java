package com.example.paul.circlegame.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.example.paul.circlegame.Models.AppConstants;

/**
 * Created by Paul on 20/10/2015.
 */
public class DisplayThread extends Thread {

    SurfaceHolder _surfaceHolder;
    Paint _backgroundPaint;

    long _startTime;
    long _wakeUpTime;

    boolean _isOnRun;

    public DisplayThread(SurfaceHolder surfaceHolder, Context context) {
        _surfaceHolder = surfaceHolder;

        //black painter below to clear the screen before the game is rendered
        _backgroundPaint = new Paint();
        _backgroundPaint.setARGB(255, 0, 0, 0);
        _isOnRun = true;
    }


    /**
     * This is the main nucleus of our program.
     * From here will be called all the method that are associated with the display in GameEngine object
     */
    @Override
    public void run() {
        //Safe current time first to calculate sleep time later
        _startTime = System.currentTimeMillis();
        _wakeUpTime = _startTime + 40;
        //Looping until the boolean is false
        while (_isOnRun) {
            //Updates the game objects buisiness logic
            AppConstants.GetEngine().Update();

            //locking the canvas
            Canvas canvas = _surfaceHolder.lockCanvas(null);
            if (canvas != null) {
                //Clears the screen with black paint and draws object on the canvas
                synchronized (_surfaceHolder) {

                    canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), _backgroundPaint);
                    AppConstants.GetEngine().Draw(canvas);
                }

                    //unlocking the Canvas
                    _surfaceHolder.unlockCanvasAndPost(canvas);

            }

            //delay time
            try {
                Thread.sleep(_wakeUpTime - _startTime);
            } catch (InterruptedException ex) {
                //TODO: Log
            }
        }
    }

    /**
     * @return whether the thread is running
     */
    public boolean IsRunning() {
        return _isOnRun;
    }

    /**
     * Sets the thread state, false = stoped, true = running
     */
    public void SetIsRunning(boolean state) {
        _isOnRun = state;
    }
}
