package com.example.paul.circlegame.Models;

import android.graphics.Paint;

import com.example.paul.circlegame.Controllers.AppConstants;

/**
 * Created by Paul on 20/10/2015.
 * Represents the moving circle and contains all information related
 */
public class Circle {

    //Coordinates and radius of the circle
    private float x;
    private float y;
    private float r;

    //Paint of the circle
    private Paint paint;

    //True if circle is touched
    private boolean touched;

    public Circle(float x, float y, float r){
        //Set given coordinates and radius
        this.x = x;
        this.y = y;
        this.r = r;
        //define paint of the circle
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        //Circle is not touched when created
        touched = false;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
        if (touched){
            paint.setColor(AppConstants.TOUCH_COLOR);
        }
        else{
            paint.setColor(AppConstants.UNTOUCH_COLOR);
        }
    }

    public Paint getPaint() {
        return paint;
    }
}

