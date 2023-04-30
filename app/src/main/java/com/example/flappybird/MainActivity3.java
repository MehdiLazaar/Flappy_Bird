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

public class MainActivity3 extends AppCompatActivity/* implements SensorEventListener */{

    //L'image du oiseau.
    ImageView im1, obsIm1,obsIm2;
    TextView tv4;
    float obstacleSpeed = 0.02f; // Vitesse des obstacles
    float toucherEcran;
    float distanceParcourus = 30f;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    Random r = new Random();
    int obstacleHeight; // La hauteur des obstacles (constante)
    int obstacleWidth; // La largeur des obstacles (constante)
    int largeurEcran, hauteurEcran;

    protected ObjectAnimator anim1, anim2;
    int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        im1 = findViewById(R.id.im1);
        obsIm1 = findViewById(R.id.obsIm1);
        obsIm2 = findViewById(R.id.obsIm2);
        tv4 = findViewById(R.id.tv4);

        Rect rectOiseau = new Rect();
        Rect premierObsIm1 = new Rect();
        Rect deuxiemeObsIm2 = new Rect();

        // récupère la taille de l'écran (La hauteur et la largeur)
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        largeurEcran = displayMetrics.widthPixels;
        hauteurEcran = displayMetrics.heightPixels;

        // Récupère la taille des obstacles
        obstacleHeight = obsIm1.getHeight();
        obstacleWidth = obsIm1.getWidth();

        demarrerAnimations();
    }

    /*private void placerObstacles() {
        int obs1Y = 0; // Position de l'obstacle supérieur en Y
        int gap = 200;
        int obs2Y = obs1Y + obsIm1.getHeight() + gap; // Position de l'obstacle inférieur en Y

        //Durée d'animation
        int dureeAnimation = 4000;

        // Positionner les obstacles
        obsIm1.setY(obs1Y);
        obsIm1.setX(largeurEcran);

        obsIm2.setY(obs2Y);
        obsIm2.setX(largeurEcran);
        boolean collisionChecked = false; // Variable pour empêcher la vérification de collision multiple

        // Faire bouger les obstacles de la droite vers la gauche en mouvement sinusoïdal
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(obsIm1, "translationX", -largeurEcran);
        anim1.setDuration(dureeAnimation);
        anim1.setRepeatCount(ValueAnimator.INFINITE);
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float time = (float) animation.getCurrentPlayTime() / 1000f;
                float angle = (float) (time * Math.PI * 2f / 3f);
                float yOffset = (float) (Math.sin(angle) * 40); // Déplacement vertical de l'obstacle supérieur
                obsIm1.setY(obs1Y + yOffset);
                if(!collisionChecked && verifierCollisions()){
                    Intent terminer = new Intent(MainActivity3.this,MainActivity4.class);
                    terminer.putExtra("score", score);
                    startActivity(terminer);
                } else {
                    score++;
                    tv4.setText("Score : " + score);
                    anim1.setDuration(dureeAnimation - 100);
                }
            }
        });
        anim1.start();

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(obsIm2, "translationX", -largeurEcran);
        anim2.setDuration(4000);
        anim2.setRepeatCount(ValueAnimator.INFINITE);
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            // Réinitialiser la variable collisionChecked pour la deuxième animation
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                boolean collisionChecked = false;
                float time = (float) animation.getCurrentPlayTime() / 1000f;
                float angle = (float) (time * Math.PI * 2f / 3f);
                float yOffset = (float) (Math.sin(angle) * 40); // Déplacement vertical de l'obstacle inférieur
                obsIm2.setY(obs2Y + yOffset);
                if(!collisionChecked && verifierCollisions()){
                    Intent terminer = new Intent(MainActivity3.this,MainActivity4.class);
                    terminer.putExtra("score", score);
                    startActivity(terminer);
                } else {
                    score++;
                    tv4.setText("Score : " + score);
                    anim2.setDuration(dureeAnimation - 100);
                }
            }
        });
        anim2.start();
    }*/
    /*private void placerObstacles() {
        int obs1Y = 0; // Position de l'obstacle supérieur en Y
        int gap = 380;
        int obs2Y = obs1Y + obsIm1.getHeight() + gap; // Position de l'obstacle inférieur en Y

        //Durée d'animation
        int dureeAnimation = 4000;

        // Positionner les obstacles
        obsIm1.setY(obs1Y);
        obsIm1.setX(largeurEcran);

        obsIm2.setY(obs2Y);
        obsIm2.setX(largeurEcran);

        // Faire bouger les obstacles de la droite vers la gauche en mouvement sinusoïdal
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(obsIm1, "translationX", -largeurEcran);
        anim1.setDuration(dureeAnimation);
        anim1.setRepeatCount(ValueAnimator.INFINITE);
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            boolean isScoreIncremented = false;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float time = (float) animation.getCurrentPlayTime() / 1000f;
                float angle = (float) (time * Math.PI * 2f / 3f);
                float yOffset = (float) (Math.sin(angle) * 40); // Déplacement vertical de l'obstacle supérieur
                obsIm1.setY(obs1Y + yOffset);

                if (verifierCollisions() == true) {
                    Intent terminer = new Intent(MainActivity3.this, MainActivity4.class);
                    terminer.putExtra("score", score);
                    startActivity(terminer);
                } else {
                    if (!isScoreIncremented && obsIm1.getX() < im1.getX()) {
                        isScoreIncremented = true;
                        score++;
                        tv4.setText("Score : " + score);
                        isScoreIncremented = false;
                    }
                    anim1.setDuration(dureeAnimation - 100);
                }
            }
        });
        anim1.start();

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(obsIm2, "translationX", -largeurEcran);
        anim2.setDuration(4000);
        anim2.setRepeatCount(ValueAnimator.INFINITE);
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            boolean isScoreIncremented = false;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float time = (float) animation.getCurrentPlayTime() / 1000f;
                float angle = (float) (time * Math.PI * 2f / 3f);
                float yOffset = (float) (Math.sin(angle) * 40); // Déplacement vertical de l'obstacle inférieur
                obsIm2.setY(obs2Y + yOffset);

                if (verifierCollisions() == true) {
                    Intent terminer = new Intent(MainActivity3.this, MainActivity4.class);
                    terminer.putExtra("score", score);
                    startActivity(terminer);
                } else {
                    if (!isScoreIncremented && obsIm2.getX() < im1.getX()) {
                        isScoreIncremented = true;
                        score++;
                        tv4.setText("Score : " + score);
                        isScoreIncremented = false;
                    }
                    anim2.setDuration(dureeAnimation - 100);
                }
            }
        });
        anim2.start();
    }*/

    private void placerObstacles() {
        int obs1Y = 0; // Position de l'obstacle supérieur en Y
        int gap = 380;
        int obs2Y = obs1Y + obsIm1.getHeight() + gap; // Position de l'obstacle inférieur en Y

        // Durée d'animation
        int dureeAnimation = 4000;
        // Durée du timer
        int dureeTimer = 30; // En secondes

        // Positionner les obstacles
        obsIm1.setY(obs1Y);
        obsIm1.setX(largeurEcran);

        obsIm2.setY(obs2Y);
        obsIm2.setX(largeurEcran);

        // Faire bouger les obstacles de la droite vers la gauche en mouvement sinusoïdal
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(obsIm1, "translationX", -largeurEcran);
        anim1.setDuration(dureeAnimation);
        anim1.setRepeatCount(ValueAnimator.INFINITE);
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            boolean isTimerStarted = false;
            int timer = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float time = (float) animation.getCurrentPlayTime() / 1000f;
                float angle = (float) (time * Math.PI * 2f / 3f);
                float yOffset = (float) (Math.sin(angle) * 40); // Déplacement vertical de l'obstacle supérieur
                obsIm1.setY(obs1Y + yOffset);

                if (verifierCollisions() == true) {
                    Intent terminer = new Intent(MainActivity3.this, MainActivity4.class);
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
                                Intent terminer = new Intent(MainActivity3.this, MainActivity4.class);
                                terminer.putExtra("timer", timer);
                                startActivity(terminer);
                            }
                        }.start();
                    }
                    anim1.setDuration(dureeAnimation - 100);
                }
            }
        });
        anim1.start();

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(obsIm2, "translationX", -largeurEcran);
        anim2.setDuration(dureeAnimation);
        anim2.setRepeatCount(ValueAnimator.INFINITE);
        anim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            boolean isTimerStarted = false;
            int timer = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float time = (float) animation.getCurrentPlayTime() / 1000f;
                float angle = (float) (time * Math.PI * 2f / 3f);
                float yOffset = (float) (Math.sin(angle) * 40); // Déplacement vertical de l'obstacle inférieur
                obsIm2.setY(obs2Y + yOffset);

                if (verifierCollisions() == true) {
                    Intent terminer = new Intent(MainActivity3.this, MainActivity4.class);
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
                                Intent terminer = new Intent(MainActivity3.this, MainActivity4.class);
                                terminer.putExtra("timer", timer);
                                startActivity(terminer);
                            }
                        }.start();
                    }
                    anim2.setDuration(dureeAnimation - 100);
                }
            }
        });
        anim2.start();
    }
    public void demarrerAnimations() {
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
    public boolean verifierCollisions() {
        //Rect rectOiseau = new Rect((int) im1.getX(), (int) im1.getY(), (int) (im1.getX() + im1.getWidth()), (int) (im1.getY() + im1.getHeight()));

        //Rect rectObstacle1 = new Rect((int) obsIm1.getX(), (int) obsIm1.getY(), (int) (obsIm1.getX() + obsIm1.getWidth()), (int) (obsIm1.getY() + obsIm1.getHeight()));

        //Rect rectObstacle2 = new Rect((int) obsIm2.getX(), (int) obsIm2.getY(), (int) (obsIm2.getX() + obsIm2.getWidth()), (int) (obsIm2.getY() + obsIm2.getHeight()));

        if (collision(obsIm1)||collision(obsIm2)) {
            return true;
        }
        return false;
    }
    public boolean collision(ImageView obs){
        Rect oiseauRect = new Rect();
        Rect obstacleRect = new Rect();
        im1.getHitRect(oiseauRect);
        obs.getHitRect(obstacleRect);
        return Rect.intersects(oiseauRect,obstacleRect);
    }
    public void afficherGameOver() {
        // Créer une AlertDialog avec un message Game Over
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Game Over!")
                .setCancelable(false)
                .setPositiveButton("Nouvelle partie", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss(); // Fermer la fenêtre Game Over
                        resetJeu(); // Réinitialiser le jeu
                    }
                })
                .setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish(); // Quitter l'application
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void resetJeu() {
        // Réinitialiser la position des obstacles et de l'oiseau
        placerObstacles();
        im1.setTranslationY(0);
        // Redémarrer les animations
        demarrerAnimations();
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