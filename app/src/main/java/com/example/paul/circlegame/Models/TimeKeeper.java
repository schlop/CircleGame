package com.example.paul.circlegame.Models;

import android.os.Environment;

import com.example.paul.circlegame.Controllers.AppConstants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;

/**
 * Created by Paul on 21/10/2015.
 *
 * Keeps track about how often and long the circle is touched
 * Exports a log file as .csv
 */
public class TimeKeeper {
    //Keeps the times when the circle is touched
    Stack<Long> touchedStack;
    //Keeps the times when the circle is not touched anymore
    Stack<Long> notTouchedStack;
    //Start time of the experiment
    long experimentStart;
    //This is true if circle was touched during the last call and false if circle wasn't
    boolean previousStatus;

    /**
     * Initializes the class variables and sets experiment Start time
     */
    public TimeKeeper() {
        experimentStart = System.currentTimeMillis();
        previousStatus = false;
        touchedStack = new Stack<Long>();
        notTouchedStack = new Stack<Long>();
    }

    /**
     * Called before rendering each frame. If touch status changes this will be saved in the two
     * stacks
     * @param touched
     */
    public void touched(boolean touched) {
        if (touched != previousStatus) {
            previousStatus = !previousStatus;
            long relativeExperimentTime = System.currentTimeMillis() - experimentStart;
            if (touched) {
                touchedStack.push(relativeExperimentTime);
            } else {
                notTouchedStack.push(relativeExperimentTime);
            }
        }
    }

    /**
     * Generate a .csv logfile and safe it to the SD card
     */
    public void generateLog() {
        //TODO: Change way of writing of SD card - Might not work with all android versions
        //Date is used for filename and in the logfile - Two different FileFormats are generated
        SimpleDateFormat fileNameFormat = new SimpleDateFormat("dd-MM_hh-mm-ss");
        SimpleDateFormat logFileFormat = new SimpleDateFormat("dd.MM HH:mm:ss");
        //Start and end time of the game
        Date startDate = new Date(experimentStart);
        Date endDate = new Date();
        //File path and name are generated as Strings
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = fileNameFormat.format(new Date()) + ".csv";
        String filePath = baseDir + File.separator + fileName;
        try {
            //New FileWriter that produces the .csv file
            FileWriter fileWriter = new FileWriter(filePath);
            //Write header with start and end time of the experiment
            fileWriter.append("start time: " + logFileFormat.format(startDate));
            fileWriter.append('\n');
            fileWriter.append("end time: " + logFileFormat.format(endDate));
            fileWriter.append('\n');
            fileWriter.append('\n');
            //Write down headings
            fileWriter.append("touch event" + "," + "start" + ',' + "end" + ',' + "duration" + ',' + "percentage");
            fileWriter.append('\n');
            //variables to count the total touch duration and percentage
            int totalDuration = 0;
            double totalPercentage = 0;
            //Amount of touch events (user touched the screen and holds finger for a certain period of time on circle)
            int touchEvents = touchedStack.size();
            //Did the user touch the circle when the game ended
            boolean endsWithTouchEvent = touchedStack.size() > notTouchedStack.size();
            //iterates over all touch events
            for (int i = 0; i < touchEvents; i++) {
                //for loop calculates duration and percentage of the touch events and writes them down
                String touchEvent = String.valueOf(i + 1);
                int startInt = touchedStack.get(i).intValue();
                String start = String.valueOf(startInt);
                int endInt;
                if (i + 1 == touchEvents && endsWithTouchEvent) {
                    //Do that if game ends with touchEvent
                    //End is experiment duration
                    endInt = (int)( AppConstants.DURATION_BLOCK);
                } else {
                    endInt = notTouchedStack.get(i).intValue();
                }
                String end = String.valueOf(endInt);
                int durationInt = endInt - startInt;
                String duration = String.valueOf(durationInt);
                float percentageDouble = ((float)(durationInt)) / ((float)AppConstants.DURATION_BLOCK);
                String percentage = String.valueOf(percentageDouble);

                //add duration and percentage to the already calculated values to get total in the end
                totalDuration += durationInt;
                totalPercentage += percentageDouble;

                fileWriter.append(touchEvent + ',' + start + ',' + end + ',' + duration + ',' + percentage);
                fileWriter.append('\n');
            }
            fileWriter.append(" , , ," + String.valueOf(totalDuration) + ',' + String.valueOf(totalPercentage));
            fileWriter.append('\n');

            //write file to disk
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
