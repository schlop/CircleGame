package com.example.paul.circlegame.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.paul.circlegame.Views.GameView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Paul on 20/10/2015.
 * Main Activity that gets called after startup
 */
public class GameActivity extends Activity {

    GameView view;

    @Override
    /**
     * Creates the surfaceView that is used to display the UI elements
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the activity view as GameView class

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        AppConstants.Initialization(this);
        view = new GameView(this, AppConstants.GetEngine());
        setContentView(view);
        new Timer().schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        view.stopDisplayThread();
                        startActivity(new Intent(GameActivity.this, MenuActivity.class));
                    }
                });
            }
        }, 300 * 1000);

    }

    
    @Override
    /**
     * Overwrites the onTouchEvent method and calls methods for each user event
     */
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                OnActionDown(event);
                break;
            }
            case MotionEvent.ACTION_UP: {
                OnActionUp(event);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                OnActionMove(event);
                break;
            }
            default:
                break;
        }
        return false;
    }

    /*activates on touch move event*/
    private void OnActionMove(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        AppConstants.GetEngine().SetLastTouched(x, y);
    }

    /*activates on touch down event*/
    private void OnActionDown(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        AppConstants.GetEngine().SetLastTouched(x, y);
    }

    /*activates on touch up event*/
    private void OnActionUp(MotionEvent event) {
        //Circle should be never counted as touched if finger is not on screen
        AppConstants.GetEngine().SetLastTouched(-1, -1);
    }


}
