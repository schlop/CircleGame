package com.example.paul.circlegame.Views;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.paul.circlegame.Models.AppConstants;
import com.example.paul.circlegame.Controllers.GameEngine;

/**
 * Created by Paul on 20/10/2015.
 * Contains the DisplayThread to render UI elements on the surfaceHolder plane
 */
    public class GameView extends SurfaceView implements SurfaceHolder.Callback
    {
        SurfaceHolder _surfaceHolder;
        Context _context;

        private DisplayThread _displayThread;

        public GameView(Context context, GameEngine gEngine)
        {
            super(context);

            _context = context;
            InitView();
        }

        /**
         * Initiate the view components
         * */
        void InitView()
        {
            SurfaceHolder holder = getHolder();
            holder.addCallback( this);

            _displayThread = new DisplayThread(holder, _context);
            setFocusable(true);
        }

        @Override
        public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3)
        {
	   /*DO NOTHING*/
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder arg0)
        {
            //Stop the display thread
            _displayThread.SetIsRunning(false);
            AppConstants.StopThread(_displayThread);
        }

        @Override
        public void surfaceCreated(SurfaceHolder arg0)
        {
            //Starts the display thread
            if(!_displayThread.IsRunning())
            {
                _displayThread = new DisplayThread(getHolder(), _context);
                _displayThread.start();
            }
            else
            {
                _displayThread.start();
            }
        }
    }