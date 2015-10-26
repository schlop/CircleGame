package com.example.paul.circlegame.Models;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Paul on 21/10/2015.
 * true = touched
 * false = not touched
 */
public class TimeKeeper {
    HashMap<Long, Boolean> map;
    long experimentStart;
    boolean previousStatus;
    FileWriter mFileWriter;

    TimeKeeper(){
        experimentStart = System.currentTimeMillis();
        map = new HashMap<Long, Boolean>();
        previousStatus = false;
        map.put(0l, false);
    }

    public void touched(boolean touched){
        if(touched != previousStatus){
            previousStatus = !previousStatus;
            long relativeExperimentTime = System.currentTimeMillis() - experimentStart;
            map.put(relativeExperimentTime, touched);
        }
    }

    public void generateLog(){
        String baseDir = android.os.Environment.getRootDirectory().getAbsolutePath();
        SimpleDateFormat format = new SimpleDateFormat("dd_MM_hh:mm:ss");
        String fileName = format.format(new Date());
        String filePath = baseDir + File.separator + fileName;
        File f = new File(filePath);
        //TODO: finish Logger
    }

}
