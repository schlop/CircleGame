package com.example.paul.circlegame.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.paul.circlegame.R;

/**
 * Created by Paul on 26/10/2015.
 * Activity for the start page that is visible before the game is started
 */
public class MenuActivity extends Activity {

    /**
     * Set app to fullscreen mode and set contentView to XML defined layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Enable fullscreen
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Connect activity with XML based layout
        setContentView(R.layout.activity_menu);
    }

    /**
     * Gets called if start Button is pressed and launches the game
     * @param view
     */
    public void startGame(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
