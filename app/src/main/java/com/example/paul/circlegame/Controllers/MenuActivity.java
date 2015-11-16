package com.example.paul.circlegame.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;

import com.example.paul.circlegame.R;

/**
 * Created by Paul on 26/10/2015.
 * Activity for the start page that is visible before the game is started
 */
public class MenuActivity extends Activity {

    NumberPicker mIpPicker;

    /**
     * Set app to fullscreen mode and set contentView to XML defined layout
     * Setup NumberPicker so it contains all possible IP addresses in the prefix range
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

        mIpPicker = (NumberPicker) findViewById(R.id.ipPicker);
        String[] ipArray = new String[255];
        for (int i = 1; i < 256; i++) {
            ipArray[i-1] = AppConstants.IP_PREFIX + i;
        }
        mIpPicker.setMinValue(0);
        mIpPicker.setMaxValue(254);
        mIpPicker.setDisplayedValues(ipArray);
    }

    /**
     * Starts a demo that shows the moving circle but keeps logging deactivated
     * @param view
     */
    public void startDemo(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    /**
     * Starts the experiment and puts the selected IP address into the intent
     * @param view
     */
    public void startExperiment(View view){
        int position = mIpPicker.getValue();
        String ip = mIpPicker.getDisplayedValues()[position];
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("IP_ADDRESS", ip);
        startActivity(intent);
    }

}
