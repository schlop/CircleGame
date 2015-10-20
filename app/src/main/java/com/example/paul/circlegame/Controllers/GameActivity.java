package com.example.paul.circlegame.Controllers;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.example.paul.circlegame.Models.AppConstants;
import com.example.paul.circlegame.Views.GameView;

/**
 * Created by Paul on 20/10/2015.
 * Main Activity that gets called after startup
 */
public class GameActivity extends Activity{

    @Override
    /**
     * Creates the surfaceView that is used to display the UI elements
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //sets the activity view as GameView class
        SurfaceView view = new GameView(this, AppConstants.GetEngine());

        setContentView(view);
        //getActionBar().hide();
    }

    @Override
    /**
     * Overwrites the onTouchEvent method and calls methods for each user event
     */
    public boolean onTouchEvent(MotionEvent event)
    {
        super.onTouchEvent(event);
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
            {
                OnActionDown(event);
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                OnActionUp(event);
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                OnActionMove(event);
                break;
            }
            default:break;
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
