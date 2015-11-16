package com.example.paul.circlegame.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.paul.circlegame.Models.TimeKeeper;
import com.example.paul.circlegame.R;
import com.example.paul.circlegame.Utils.TCPClient;
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

    private TCPClient mTcpClient;
    private ConnectTask mConnectTask;

    private boolean gameRunning;

    @Override
    /**
     * Sets app to fullscreen
     * Creates the server object with the selected IP address
     * Sets the content view to a blank XML layout with black background (experiment active)
     * Sets the content view to the GameView if Demo is active
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Enable fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            //End game after a predefined period of time and return to the launch menu
            new Timer().schedule(new TimerTask() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            startMenu();
                        }
                    });
                }
            }, AppConstants.DURATION_BLOCK);
            startGame();
        }
        else{
            String ipAddress = extras.getString("IP_ADDRESS");
            mConnectTask = new ConnectTask(ipAddress);
            mConnectTask.execute("");
            mConnectTask.getStatus();

            setContentView(R.layout.blank);
        }

    }

    /**
     * The AsyncTask that handles communication with the server
     */
    public class ConnectTask extends AsyncTask<String, String, TCPClient> {

        private String serverIp;

        public ConnectTask(String serverIp) {
            super();
            this.serverIp = "192.168.178.81";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected TCPClient doInBackground(String... message) {

            //we create a TCPClient object and define CallBackInterface
            mTcpClient = new TCPClient(serverIp, new TCPClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
                //We send the name of our device to the TCP server to make direct communication possible
                public void statusUpdated(String status){
                    if (status.contains("Connected")){
                        mTcpClient.sendMessage("PT");
                    }
                }
            });
            //Launch TCP client after Interface was defined
            mTcpClient.run();
            return null;
        }


        @Override
        /**
         * Processes the received messages sent by TCP server
         */
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            //We just react to START and END messages
            final String tcpMsg = values[0];
            if (tcpMsg.startsWith(AppConstants.START_MESSAGE)){
                startGame();
            }
            if (tcpMsg.startsWith(AppConstants.END_MESSAGE) && gameRunning){
                startPause();
            }
        }
    }

    /**
     * Starts teh game and displays the moving circle
     * Timekeeper is initiated for logging
     */
    private void startGame(){
        gameRunning = true;
        //Create new Timekeeper that keeps track about touch events
        timeKeeper = new TimeKeeper();
        //Create new GameEngine object
        gameEngine = new GameEngine(timeKeeper);
        //Create new GameView with created GameEngine
        view = new GameView(this, gameEngine);
        setContentView(view);
    }

    /**
     * Displays a blank screen and creates the logFile
     * This is called after one block has finished
     */
    private void startPause(){
        gameRunning = false;
        view.stopDisplayThread();
        timeKeeper.generateLog();
        setContentView(R.layout.blank);
    }

    /**
     * Returns to the start menu. This is called when the demo finishes
     */
    private void startMenu(){
        view.stopDisplayThread();
        startActivity(new Intent(GameActivity.this, MenuActivity.class));
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
