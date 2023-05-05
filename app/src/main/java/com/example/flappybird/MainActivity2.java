package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity{

    //L'image du oiseau.
    ImageView im1, obsIm1,obsIm2;
    TextView tv4;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    Random r = new Random();
    MediaPlayer mediaPlayer;
    //La hauteur des obstacles
    int obstacleHeight;
    //La largeur des obstacles
    int obstacleWidth;
    int largeurEcran, hauteurEcran;
    float toucherEcranX, toucherEcranY;
    int amplitude = 40;
    float frq = (2f / 3f);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        im1 = findViewById(R.id.im1);
        obsIm1 = findViewById(R.id.obsIm1);
        obsIm2 = findViewById(R.id.obsIm2);
        tv4 = findViewById(R.id.tv4);

        // récupère la taille de l'écran (La hauteur et la largeur)
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        largeurEcran = displayMetrics.widthPixels;
        hauteurEcran = displayMetrics.heightPixels;

        // Récupère la taille des obstacles
        obstacleHeight = obsIm1.getHeight();
        obstacleWidth = obsIm1.getWidth();

        //mediaPlayer = MediaPlayer.create(this, R.raw.GameOverSound);

        demarrerAnimations();
    }
    private void placerObstacles() {
        //Position de l'obstacle supérieur en Y
        int obs1SupPosY = 0;
        int gapEntreObstacles = 380;
        // Position de l'obstacle inférieur en Y
        int obs2InfPosY = obs1SupPosY + obsIm1.getHeight() + gapEntreObstacles;

        // Durée d'animation
        int dureeAnimation = 4000;
        // Durée du timer
        int dureeTimer = 5000; // En secondes

        // Positionner les obstacles
        obsIm1.setY(obs1SupPosY);
        obsIm1.setX(largeurEcran);

        obsIm2.setY(obs2InfPosY);
        obsIm2.setX(largeurEcran);
        boolean isTimerStarted = false;

        //On bouge les obstacles de la droite vers la gauche avec un mouvement sinusoïdal
        //On aura besoin d'une amplitude et une frequence
        //Avec l'objet premieraAnimation on fait bouger l'obstacle superieur de la droite vers la gauche avec une translationX càd sur l axe X
        //Jusqu'a la postion (-largeurEcran) qui est l autre cote de l'écran.
        ObjectAnimator animationObsSup = ObjectAnimator.ofFloat(obsIm1, "translationX", -largeurEcran);
        animationObsSup.setDuration(dureeAnimation);
        animationObsSup.setRepeatCount(ValueAnimator.INFINITE);
        //Le updatListener sera appelé chaque fois que l'animation mise à jour
        animationObsSup.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            boolean isTimerStarted = false;
            int timer = 0;
            Random nbrAlea1 = new Random();
            //Pour avoir un nombre aleatoire en -400 et 699
            int accelereAnimation = nbrAlea1.nextInt(1100) - 400;

            //Le onAnimationUpdate sera utilisé pour mettre a jour la position de l'obstacle superieur sur l'axe Y
            //Amplitude = 40
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float time = (float) animation.getCurrentPlayTime() / 1000f;
                float angle = (float) (time * Math.PI * frq);
                // Déplacement vertical de l'obstacle supérieur
                float depVerticalObsSup = (float) (Math.sin(angle) * amplitude);
                //Apres ce calcul, on aura un deplacement de -40 à 40 pixels
                obsIm1.setY(obs1SupPosY + depVerticalObsSup);

                if (verifierIntersectionObstacleOiseau() == true) {
                    //mediaPlayer.start();
                    Intent terminer = new Intent(MainActivity2.this, MainActivity3.class);
                    terminer.putExtra("timer", timer);
                    startActivity(terminer);
                    //mediaPlayer.stop();
                } else {
                    if (!isTimerStarted && obsIm1.getX() < im1.getX()) {
                        isTimerStarted = true;
                        new CountDownTimer(dureeTimer * 1000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                timer++;
                                tv4.setText("Timer : " + timer);
                            }

                            public void onFinish() {
                                isTimerStarted = false;
                                Intent terminer = new Intent(MainActivity2.this, MainActivity3.class);
                                terminer.putExtra("timer", timer);
                                startActivity(terminer);
                            }
                        }.start();
                    }
                    animationObsSup.setDuration(dureeAnimation - accelereAnimation);
                }
            }
        });
        animationObsSup.start();

        ObjectAnimator animationObsInf = ObjectAnimator.ofFloat(obsIm2, "translationX", -largeurEcran);
        animationObsInf.setDuration(dureeAnimation);
        animationObsInf.setRepeatCount(ValueAnimator.INFINITE);
        animationObsInf.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            boolean isTimerStarted = false;
            int timer = 0;
            Random nbrAlea2 = new Random();
            //Pour avoir un nombre aleatoire en -500 et 499
            int accelereAnimation2 = nbrAlea2.nextInt(1000) - 500;
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float time = (float) animation.getCurrentPlayTime() / 1000f;
                float angle = (float) (time * Math.PI * frq);
                // Déplacement vertical de l'obstacle inférieur
                float depVerticalObsInf = (float) (Math.sin(angle) * amplitude);
                //Apres ce calcule, on aura un deplacement de -40 à 40 pixels
                obsIm2.setY(obs2InfPosY + depVerticalObsInf);

                if (verifierIntersectionObstacleOiseau() == true) {
                    Intent terminer = new Intent(MainActivity2.this, MainActivity3.class);
                    terminer.putExtra("timer", timer);
                    startActivity(terminer);
                } else {
                    if (!isTimerStarted && obsIm1.getX() < im1.getX()) {
                        isTimerStarted = true;
                        new CountDownTimer(dureeTimer * 1000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                timer++;
                                tv4.setText("Timer : " + timer);
                            }

                            public void onFinish() {
                                isTimerStarted = false;
                                Intent terminer = new Intent(MainActivity2.this, MainActivity3.class);
                                terminer.putExtra("timer", timer);
                                startActivity(terminer);
                            }
                        }.start();
                    }
                    animationObsInf.setDuration(dureeAnimation - accelereAnimation2);
                }
            }
        });
        animationObsInf.start();
    }
    public void demarrerAnimations() {
        ObjectAnimator animationObsSup = ObjectAnimator.ofFloat(obsIm1, "translationX", -obstacleWidth);
        ObjectAnimator animationObsInf = ObjectAnimator.ofFloat(obsIm2, "translationX", -obstacleWidth);
        // Listener pour l obstacle du top
        animationObsSup.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                placerObstacles();
            }
        });

        // Listener pour l obstacle inferieur
        animationObsInf.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //Le Listener de l'écouteur superieur prend le relais
            }
        });
        //Demarrer les animations
        animationObsSup.start();
        animationObsInf.start();
    }
    public boolean verifierIntersectionObstacleOiseau() {
        if (intersectionObsOiseau(obsIm1)||intersectionObsOiseau(obsIm2)) {
            return true;
        }
        return false;
    }
    public boolean intersectionObsOiseau(ImageView obs){
        //Rect = rectangle
        Rect oiseauIm = new Rect();
        Rect obstacleIm = new Rect();
        im1.getHitRect(oiseauIm);
        obs.getHitRect(obstacleIm);
        return Rect.intersects(oiseauIm,obstacleIm);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Lorsqu'on touche l'écran, on stocke les informations de la position de notre doigt
                toucherEcranX = x;
                toucherEcranY = y;
            case MotionEvent.ACTION_MOVE:
                //On calcule la distance parcourue depuis la position de notre dernier touché
                float distanceX = x - toucherEcranX;
                float distanceY = y - toucherEcranY;

                //L'oiseau se déplace en prenant en compte la direction de glissement du doigt
                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    if (distanceX > 0) {
                        //L'oiseau glisse vers la droite
                        im1.setX(im1.getX() + 10);
                    } else {
                        //L'oiseau glisse vers la gauche
                        im1.setX(im1.getX() - 10);
                    }
                } else {
                    if (distanceY < 0) {
                        //L'oiseau saute vers le haut
                        im1.setY(im1.getY() - 10);
                    } else {
                        //L'oiseau saute vers le bas
                        im1.setY(im1.getY() + 10);
                    }
                }
                //Mise à jour de la position du dernier toucher
                toucherEcranX = x;
                toucherEcranY = y;
        }
        return true;
    }
}