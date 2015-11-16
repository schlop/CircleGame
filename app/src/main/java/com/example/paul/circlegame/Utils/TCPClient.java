package com.example.paul.circlegame.Utils;

/**
 * Created by ismail on 20/10/2014.
 * From http://www.myandroidsolutions.com/2012/07/20/android-tcp-connection-tutorial/
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {

    private String serverMessage;
    //public static final String SERVERIP = "172.20.10.2"; //your computer IP address
    //169.254.183.2 - on local wifi - penny's macbook air
    //public static final String SERVERIP = "192.168.2.1"; //this was on shared network on Ismail's Macbook Air
    public String serverIp = "10.0.1.2"; //this is only a startup value - it is overridden in MainActivity ConnectTask constructor


    public static final int SERVERPORT = 4445;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    private Socket socket;

    PrintWriter out;
    BufferedReader in;

    /**
     *  Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public TCPClient(String serverIp, OnMessageReceived listener) {
        this.serverIp = serverIp;
        mMessageListener = listener;
    }

    /**
     * Sends the message entered by client to the server
     * @param message text entered by client
     */
    public void sendMessage(String message){
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        } else if (out != null && out.checkError()) {
            Log.e("TCPClient", "SERVER DISCONNECTED");
            stopClient();
        }
    }

    public void setServerIp (String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerIp () {
        return serverIp;
    }

    public void stopClient(){
        mRun = false;
    }

    public void run() {

        mRun = true;

        try {
            //here you must put your computer's IP address.
            InetAddress serverAddr = InetAddress.getByName(serverIp);

            Log.e("TCP Client", "C: Connecting to ..." + serverIp);

            //create a socket to make the connection with the server
            socket = new Socket();
            socket.connect(new InetSocketAddress(serverAddr, SERVERPORT), 2000);

            try {

                //send the message to the server
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                Log.e("TCP Client", "C: Sent.");

                Log.e("TCP Client", "C: Done.");

                //receive the message which the server sends back
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                mMessageListener.statusUpdated("Connected");

                //in this while the client listens for the messages sent by the server
                while (mRun) {
                    // Check if there is a disconnect
                    int firstChar = in.read();
                    if (firstChar == -1) {
                        Log.e("TCPClient", "SERVER DISCONNECTED");
                        stopClient();
                        releaseSocket();
                    }

                    serverMessage = (char)firstChar + in.readLine();



                    if (serverMessage != null && mMessageListener != null) {
                        //call the method messageReceived from MyActivity class
                        mMessageListener.messageReceived(serverMessage);
                    }
                    serverMessage = null;

                }

                Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");

            } catch (Exception e) {

                Log.e("TCP", "S: Error", e);

            } finally {
                //the socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }

        } catch (Exception e) {
            //mRun = false;
            Log.e("TCP", "C: Error", e);

        }

    }

    public void releaseSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public boolean isSocketOpen() {
        if (socket == null) return false;
        return !socket.isBound();
    }

    //Declare the interface. The method messageReceived(String message) will must be implemented in the MyActivity
    //class at on asyncTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
        public void statusUpdated(String message);
    }
}