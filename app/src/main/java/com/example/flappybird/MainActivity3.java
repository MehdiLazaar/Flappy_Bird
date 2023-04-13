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

public class MainActivity3 extends AppCompatActivity{

    ImageView im1;
    //im2;
    float oiseauY;
    ObjectAnimator sautOiseau;
    ObjectAnimator chuteOiseau;
    //Declaration de AnimationDrawable pour animé la photo
    //AnimationDrawable animee;
    private DisplayMetrics displayMetrics = new DisplayMetrics(); // taille de l'écran
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        im1 = findViewById(R.id.im1);
        //im2 = findViewById(R.id.im2);
        //Mettre le classe xml bird_animation comme background de la photo
        //im2.setBackgroundResource(R.drawable.bird_animation);
        //animee = (AnimationDrawable) im2.getBackground();
        //On démarre l'animation en utilisant le boutton start
        //animee.start();
        // récupère la taille de l'écran
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //On recupere la position initial de l'oiseau
        oiseauY = im1.getY();
        //Creation de l'animation du saut.
        sautOiseau = ObjectAnimator.ofFloat(im1,"y",oiseauY,oiseauY - 100f);
        sautOiseau.setDuration(200);
        //Creation de l'animation de la chute
        chuteOiseau = ObjectAnimator.ofFloat(im1,"y",oiseauY,oiseauY + 100f);
        chuteOiseau.setDuration(200);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int evenement = event.getAction();
        //On teste si on a enlevé notre doigt de l'écran
        if(evenement == MotionEvent.ACTION_UP){
            chuteOiseau.start();
        }
        //On teste si on a touché l'écran
        if(evenement == MotionEvent.ACTION_DOWN){
            sautOiseau.start();
        }
        return super.onTouchEvent(event);
    }
    protected void onResume(){
        super.onResume();
    }
    protected void onPause(){
        super.onPause();
    }
}