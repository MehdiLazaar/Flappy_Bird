package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity3 extends AppCompatActivity/* implements SensorEventListener */{

    ImageView im1;
    //im2;
    float oiseauY;
    ObjectAnimator sautOiseau;
    ObjectAnimator chuteOiseau;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    //Declaration de AnimationDrawable pour animé la photo
    //AnimationDrawable animee;
    // taille de l'écran
    float postionOiseauY;
    float toucherEcran;
    float distanceParcourus = 20f;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        im1 = findViewById(R.id.im1);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // récupère la taille de l'écran
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                toucherEcran = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                float diff = im1.getY() - toucherEcran;
                if(diff < 0){
                    // J'ai cliqué en bas de l'écran donc l'oiseau descend
                    im1.setTranslationY(im1.getTranslationY() + distanceParcourus);
                } else {
                    // J'ai cliqué en haut de l'écran donc l'oiseau monte
                    im1.setTranslationY(im1.getTranslationY() - distanceParcourus);
                }
        }
        //return false;
        return super.onTouchEvent(event);
    }
}