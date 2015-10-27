package com.example.paul.circlegame.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.paul.circlegame.Models.TimeKeeper;
import com.example.paul.circlegame.Views.GameView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Paul on 20/10/2015.
 * Main Activity that gets called after startup
 */
public class GameActivity extends Activity {

    private GameView view;
    private GameEngine gameEngine;
    private TimeKeeper timeKeeper;

    @Override
    /**
     * Creates the surfaceView that is used to display the game and sets app to fullscreen
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Enable fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Create new Timekeeper that keeps track about touch events
        timeKeeper = new TimeKeeper();
        //Create new GameEngine object
        gameEngine = new GameEngine(timeKeeper);
        //Create new GameView with created GameEngine
        view = new GameView(this, gameEngine);
        setContentView(view);
        //End game after a predefined period of time and return to the launch menu
        new Timer().schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        view.stopDisplayThread();
                        timeKeeper.generateLog();
                        startActivity(new Intent(GameActivity.this, MenuActivity.class));
                    }
                });
            }
        }, AppConstants.DURATION_BLOCK);

    }

    
    @Override
    /**
     * Overwrites the onTouchEvent method and calls methods for each touch event
     */
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                onActionDown(event);
                break;
            }
            case MotionEvent.ACTION_UP: {
                onActionUp(event);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                onActionMove(event);
                break;
            }
            default:
                break;
        }
        return false;
    }

    /*activates on touch move event*/
    private void onActionMove(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        gameEngine.setTouchPosition(x, y);
    }

    /*activates on touch down event*/
    private void onActionDown(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        gameEngine.setTouchPosition(x, y);
    }

    /*activates on touch up event*/
    private void onActionUp(MotionEvent event) {
        //Circle should be never counted as touched if finger is not on screen
        gameEngine.setTouchPosition(-1, -1);
    }


}
