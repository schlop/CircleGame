package com.example.paul.circlegame.Controllers;

import android.app.Application;

/**
 * Created by Paul on 20/10/2015.
 * Class to start the application. onCreate is called once and launches App Constants
 */
public class GameApplication extends Application {


    public GameApplication()
    {
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        //Initialization of the AppConstants class
        AppConstants.initialize(this.getApplicationContext());

    }
}
