package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Handler;

public class MainActivity3 extends AppCompatActivity/* implements SensorEventListener */{

    //L'image du oiseau.
    ImageView im1, obsIm1,obsIm2;
    float obstacleSpeed = 0.02f; // Vitesse des obstacles
    float toucherEcran;
    float distanceParcourus = 30f;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    Random r = new Random();
    int obstacleHeight; // La hauteur des obstacles (constante)
    int obstacleWidth; // La largeur des obstacles (constante)
    int largeurEcran, hauteurEcran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        im1 = findViewById(R.id.im1);
        obsIm1 = findViewById(R.id.obsIm1);
        obsIm2 = findViewById(R.id.obsIm2);

        // récupère la taille de l'écran (La hauteur et la largeur)
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        largeurEcran = displayMetrics.widthPixels;
        hauteurEcran = displayMetrics.heightPixels;

        // Récupère la taille des obstacles
        obstacleHeight = obsIm1.getHeight();
        obstacleWidth = obsIm1.getWidth();

        placerObstacles();
        demarrerAnimations();
    }
    private void placerObstacles() {
        int distanceMin = 200; // La distance minimale entre les obstacles et le haut/bas de l'écran
        int distanceMax = hauteurEcran - 400; // La distance maximale entre les obstacles et le haut/bas de l'écran
        int distanceAleatoire = r.nextInt(distanceMax - distanceMin) + distanceMin; // Calculer une distance aléatoire

        // Positionner l'obstacle supérieur
        obsIm1.setY(0);
        obsIm1.setX(largeurEcran);

        // Positionner l'obstacle inférieur
        obsIm2.setY(distanceAleatoire);
        obsIm2.setX(largeurEcran);

        // Faire bouger les obstacles de la droite vers la gauche
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(obsIm1, "translationX", -largeurEcran);
        anim1.setDuration(2000);
        anim1.setRepeatCount(ValueAnimator.INFINITE);
        anim1.start();

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(obsIm2, "translationX", -largeurEcran);
        anim2.setDuration(2000);
        anim2.setRepeatCount(ValueAnimator.INFINITE);
        anim2.start();
    }
    private void demarrerAnimations() {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(obsIm1, "translationX", -obstacleWidth);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(obsIm2, "translationX", -obstacleWidth);
        // Ecouteur d'animation pour l'obstacle supérieur
        anim1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                placerObstacles();
            }
        });

        // Ecouteur d'animation pour l'obstacle inférieur
        anim2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Ne rien faire ici car l'écouteur de l'obstacle supérieur prendra le relais
            }
        });

        // Démarrer les animations
        anim1.start();
        anim2.start();
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
        return super.onTouchEvent(event);
    }
}