package com.example.paul.circlegame.Controllers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.paul.circlegame.Models.Circle;

/**
 * Created by Paul on 20/10/2015.
 */
public class GameEngine {

    /*MEMBERS*/
    static final int CIRCLE_SPEED = 1;
    static Circle _circle;
    Paint _paint;
    static final Object _sync = new Object();
    static float _lastTouchedX, _lastTouchedY;

    public GameEngine(){
        _paint = new Paint();
        _circle = new Circle();
    }

    private void SetupPaint(){
        _paint.setColor(Color.RED);
        _paint.setAntiAlias(true);
        _paint.setStrokeWidth(5);
        _paint.setStyle(Paint.Style.FILL);
        _paint.setStrokeJoin(Paint.Join.ROUND);
        _paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void Update(){
        MoveCircle();
    }

    private void MoveCircle(){
        synchronized (_sync){
            _circle.Move(CIRCLE_SPEED);
        }
    }

    public void Draw(Canvas canvas){
        DrawCircle(canvas);
    }

    private void DrawCircle(Canvas canvas){
        synchronized (_sync){
            if (_circle.Includes(_lastTouchedX, _lastTouchedY)){
                _paint.setColor(Color.GREEN);
                canvas.drawCircle(_circle.GetX(), _circle.GetY(), (float) _circle.GetR(), _paint);
            }
            else {
                _paint.setColor(Color.RED);
                canvas.drawCircle(_circle.GetX(), _circle.GetY(), (float) _circle.GetR(), _paint);
            }
        }
    }

    public void SetLastTouched(float x, float y){
        _lastTouchedX = x;
        _lastTouchedY = y;
    }
}
